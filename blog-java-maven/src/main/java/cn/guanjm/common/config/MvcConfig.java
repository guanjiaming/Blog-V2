package cn.guanjm.common.config;

import cn.guanjm.common.interceptor.AuthInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private JwtProperties prop;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(prop))
                .addPathPatterns("/apis/**")
                .excludePathPatterns("/apis/queryArticleList")
                .excludePathPatterns("/apis/article/detail")
                .excludePathPatterns("/apis/article/addviews")
                .excludePathPatterns("/apis/tags")
                .excludePathPatterns("/apis/login")
                .excludePathPatterns("/apis/logout")
                .excludePathPatterns("/apis/verify")
        ;
    }
}
