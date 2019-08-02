package com.match.oim.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author zhangchao
 * @Date 2019/7/31 17:18
 * @Version v1.0
 */
@EnableDiscoveryClient
@EnableFeignClients("com.match.*.client")
@EnableHystrix                // 开启断路器
@EnableHystrixDashboard
@SpringBootApplication
@ComponentScan({"com.match.oim","com.match.*.client.fallback","com.match.common"})
@EnableEurekaClient
@ServletComponentScan
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
