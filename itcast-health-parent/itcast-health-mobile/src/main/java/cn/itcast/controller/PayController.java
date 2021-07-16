package cn.itcast.controller;

import cn.itcast.config.WebSocket;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Order;
import cn.itcast.pojo.PayLog;
import cn.itcast.utils.ConvertUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/pay")
public class PayController {

    @Autowired
    private WXPay wxPay;

    @Reference
    private PayService payService;

    @Reference
    private OrderService orderService;

    @Reference
    private PayLogService payLogService;

    @Value("${wx.pay.notify_url}")
    private String notify_url;

    @Autowired
    private WebSocket webSocket;

    @RequestMapping("/notify")
    public void notifyLogic(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("支付成功回调");
        try {
            //输入流转换为字符串
            String xml = ConvertUtils.convertToString(request.getInputStream());
            System.out.println(xml);
            //基于微信发送的通知内容,完成后续的业务逻辑处理
            Map<String, String> map = WXPayUtil.xmlToMap(xml);
            if (!"SUCCESS".equals(map.get("result_code"))) {
                //输出错误原因
                System.out.println(map.get("err_code_des"));
                return;
            }

            //根据外部商户订单号查询订单
            String orderId = map.get("out_trade_no");

            Map<String, String> params = new HashMap();
            params.put("out_trade_no", map.get("out_trade_no"));
            Map<String, String> result = wxPay.orderQuery(params);

            if (!"SUCCESS".equals(result.get("result_code")) || !"SUCCESS".equals(result.get("return_code")) || !"SUCCESS".equals(result.get("trade_state"))) {
                //输出错误原因
                System.out.println(map.get("err_code_des"));
                return;
            }

            //修改订单支付状态
            payService.updatePayStatus(result);

            //通过webSocket向客户端响应数据
            webSocket.sendMessage(orderId, "SUCCESS");

            //给微信一个结果通知
            response.setContentType("text/xml");
            String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            response.getWriter().write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 微信统一下单 , 获取微信支付URL
     *
     * @param orderId
     * @return
     */
    @RequestMapping(path = "/wxPay")
    public Result wxPay(String orderId) {
        try {
            Map<String, String> result = null;
            //3.基于payFeign调用统计下单接口,并获取返回结果
            //1. 查询订单信息
            Order order = orderService.findById(orderId);
            if (order == null) {
                throw new RuntimeException("支付订单不存在");
            }
            if (order.getPayStatus() != 0) {
                throw new RuntimeException("订单状态异常");
            }

            //2. 封装请求参数
            Map<String, String> map = new HashMap<>();
            map.put("body", order.getSetmeal().getName());
            map.put("out_trade_no", order.getId());

            //BigDecimal payMoney = new BigDecimal(order.getSetmeal().getPrice());
            BigDecimal payMoney = new BigDecimal(0.01, new MathContext(2));
            BigDecimal fen = payMoney.multiply(new BigDecimal("100"));
            fen = fen.setScale(0, BigDecimal.ROUND_UP);
            map.put("total_fee", String.valueOf(fen));

            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", notify_url);
            map.put("trade_type", "NATIVE");

            //2.基于wxpay完成统一下单接口的调用,并获取返回结果
            result = wxPay.unifiedOrder(map);

            if (!"SUCCESS".equalsIgnoreCase(result.get("result_code"))) {
                return new Result(false, "统一下单失败");
            }

            //保存订单支付日志
            PayLog payLog = new PayLog();
            payLog.setOrderId(orderId);
            payLog.setPayStatus(0);
            payLog.setCreateTime(new Date());
            payLog.setMoney(fen.doubleValue());

            payLogService.add(payLog);

            return new Result(true, "微信统一下单成功", result.get("code_url"));
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false, "微信统一下单失败");
        }
    }
}
