package com.match.oim.client.configuration;

import com.match.common.feign.FeignBasicAuthRequestInterceptor;
import com.match.common.feign.FeignConfig;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author zhangchao
 * @Date 2019/8/1 14:26
 * @Version v1.0
 */
@Configuration
public class FeignSupportConfig extends FeignConfig {
    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();

    }
}
