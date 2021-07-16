package cn.itcast.config;


import cn.itcast.interceptor.OrderSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private OrderSubmitInterceptor orderSubmitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(orderSubmitInterceptor)
                .addPathPatterns("/order/submit", "/member/*","/order/user/**")
                .excludePathPatterns("/member/login");
    }
}