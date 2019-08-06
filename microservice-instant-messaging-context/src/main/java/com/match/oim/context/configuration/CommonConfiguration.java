package com.match.oim.context.configuration;

import com.match.common.file.aliyun.AliyunOssObjectManagerFactory;
import com.match.common.icon.group.GroupIconServiceProvider;
import com.match.common.icon.user.UserIconServiceProvider;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class CommonConfiguration {


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(1024L * 1024L*1024L);
        factory.setMaxRequestSize(1024L*1024L*1024L);
        return factory.createMultipartConfig();
    }

    @Bean
    public AliyunOssObjectManagerFactory getAliyunOssObjectManagerFactory(){
        return new AliyunOssObjectManagerFactory();
    }

    @Bean
    public UserIconServiceProvider getUserIconServiceProvider(){
        return new UserIconServiceProvider();
    }

    @Bean
    public GroupIconServiceProvider getGroupIconServiceProvider(){
        return new GroupIconServiceProvider();
    }
}
