package com.match.oim.client;

import com.match.common.PageResult;
import com.match.common.context.UserContext;
import com.match.oim.client.bean.DynamicAddDTO;
import com.match.oim.client.bean.DynamicDTO;
import com.match.oim.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author zhangchao
 * @Date 2019/8/2 18:00
 * @Version v1.0
 */
@FeignClient(name = "microservice-oim",configuration = FeignSupportConfig.class)
public interface DynamicClient {
    @PostMapping("/dynamic/publish")
    public void publishDynamic(@RequestParam("userId") String userId,@RequestBody DynamicAddDTO dynamic);


    @GetMapping("/dynamic/list/self")
    public PageResult<DynamicDTO> listSelf(@RequestParam("userId") String userId,
                                           @RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                           @RequestParam(required = false,name = "size",defaultValue = "10") Integer size);


    @GetMapping("/dynamic/list/friend")
    public PageResult<DynamicDTO> listFriend(@RequestParam("userId") String userId,
                                             @RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                             @RequestParam(required = false,name = "size",defaultValue = "10") Integer size) ;
}
