package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entity.Result;
import cn.itcast.utils.SMSUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/sms")
public class SmsController {

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping(path = "/sendCode")
    public Result sendCode(String phone) {
        try {
            //生成6位随机数字验证码
            String numeric = RandomStringUtils.randomNumeric(6);
            //调用工具类发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, phone, numeric);

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @PostMapping(path = "/sendLoginCode")
    public Result sendLoginCode(String phone) {
        try {
            //生成6位随机数字验证码
            String numeric = RandomStringUtils.randomNumeric(6);
            //调用工具类发送验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, phone, numeric);

            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
