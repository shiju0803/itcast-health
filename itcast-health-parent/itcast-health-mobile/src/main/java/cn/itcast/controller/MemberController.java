package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.pojo.Member;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(path = "/member")
public class MemberController {

    @Reference
    private MemberService memberService ;

    /**
     * 用户登录请求
     * @param map
     * @param response
     * @return
     */
    @PostMapping(path = "/login")
    public Result login(@RequestBody Map<String,String> map , HttpServletResponse response){
        String phone = (String) map.get("phone");
        String validateCode = (String) map.get("validateCode");

        //验证码输入正确
        //判断当前用户是否为会员
        Member member = memberService.findByTelephone(phone);
        if(member == null){
            //当前用户不是会员，自动完成注册
            member = new Member();
            member.setPhoneNumber(phone);
            member.setRegTime(new Date());
            member.setStatus(0);
            memberService.add(member);
        }
        //登录成功
        //写入Cookie，跟踪用户
        Cookie cookie = new Cookie("login_member_telephone",phone);
        cookie.setPath("/");//路径
        cookie.setMaxAge(60*60*24*30);//有效期30天
        response.addCookie(cookie);

        return new Result(true,MessageConstant.LOGIN_SUCCESS);
    }
}
