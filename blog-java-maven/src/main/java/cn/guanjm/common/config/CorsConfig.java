package cn.guanjm.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {

        // 1. 添加配置信息
        CorsConfiguration config = new CorsConfiguration();

        // 允许的头
        config.addAllowedHeader("*");

        // 允许跨的域，不能写*，不然cookie就不能用了
        config.addAllowedOrigin("http://127.0.0.1:3000");
        config.addAllowedOrigin("http://43.138.23.58");
        config.addAllowedOrigin("https://blog.guanjm.cn");

        // 设置是否发送cookie
        config.setAllowCredentials(true);

        // 允许跨域的方式
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");

        // 2 添加映射路径 ， 这里我们拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }
}
