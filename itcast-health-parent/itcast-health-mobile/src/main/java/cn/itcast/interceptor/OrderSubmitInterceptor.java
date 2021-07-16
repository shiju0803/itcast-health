package cn.itcast.interceptor;

import cn.itcast.pojo.Member;
import cn.itcast.utils.ThreadLocalUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单提交的拦截器 , 如果用户没有登录不允许提交订单
 */
@Component
public class OrderSubmitInterceptor extends HandlerInterceptorAdapter {

    @Reference
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求中的cookie
        String telephone = request.getHeader("login_member_telephone");
        //如果cookie不存在, 用户未登录
        if (StringUtils.isEmpty(telephone)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Member member = memberService.findByTelephone(telephone);

        if (member == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        ThreadLocalUtils.setUser(member);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
