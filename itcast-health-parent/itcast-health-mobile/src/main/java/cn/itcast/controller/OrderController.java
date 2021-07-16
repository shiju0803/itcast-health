package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import cn.itcast.pojo.Order;
import cn.itcast.utils.ThreadLocalUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {


    @Reference
    private OrderService orderService;

    /**
     * 提交预约信息   ---- 会员信息与预约信息
     *
     * @param order
     * @return
     */
    @PostMapping(path = "/submit")
    public Result submit(@RequestBody Order order) {
        try {
            //1. 校验验证码是否正确

            //校验验证码是否正确

            //2. 校验通过 , 调用服务提交预约数据提交
            Member member = ThreadLocalUtils.getUser();
            if (member == null) {
                return new Result(false, MessageConstant.NEED_LOGIN);
            }
            //设置当前登录用户ID
            order.setMemberId(member.getId());
            //设置预约方式
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
            //提交预约数据

            return orderService.submit(order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SERVER_BUSY);
        }
    }

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Result findById(@PathVariable("id") String id) {
        try {
            Order order = orderService.findById(id);
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, order);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }


    /**
     * 查询当前登录用户订单列表
     *
     * @return
     */
    @GetMapping(path = "/user/findAll")
    public Result findUserAll() {
        try {
            Member member = ThreadLocalUtils.getUser();
            if(member==null){
                return new Result(false, MessageConstant.NEED_LOGIN);
            }

            List<Order> orderList = orderService.findUserAll(member.getId());
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
        }
    }

}
