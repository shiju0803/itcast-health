package cn.itcast.config;

import com.github.wxpay.sdk.MyConfig;
import com.github.wxpay.sdk.WXPay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WxPayConfig {

    @Bean
    public WXPay wxPay() {
        try {
            return new WXPay(new MyConfig());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
