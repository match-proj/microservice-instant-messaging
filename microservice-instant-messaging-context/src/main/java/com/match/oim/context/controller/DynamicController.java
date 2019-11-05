package com.match.oim.context.controller;

import com.match.common.PageResult;
import com.match.common.context.UserContext;
import com.match.oim.client.DynamicClient;
import com.match.oim.client.bean.DynamicAddDTO;
import com.match.oim.client.bean.DynamicDTO;
import com.match.oim.context.domain.service.DynamicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jagua on 2019/5/27.
 */
@RestController
@RequestMapping
@Slf4j
public class DynamicController implements DynamicClient {


    @Autowired
    private DynamicService dynamicService;


    @Override
    public void publishDynamic(@RequestParam("userId") String userId, @RequestBody DynamicAddDTO dynamic){
        dynamicService.publishDynamic(userId,dynamic);
    }


    @Override
    public PageResult<DynamicDTO> listSelf(@RequestParam("userId") String userId,
                                           @RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                           @RequestParam(required = false,name = "size",defaultValue = "10") Integer size) {
        return dynamicService.listSelf(page, size, userId);
    }


    @Override
    public PageResult<DynamicDTO> listFriend(@RequestParam("userId") String userId,
                                             @RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                             @RequestParam(required = false,name = "size",defaultValue = "10") Integer size) {
        return dynamicService.listFriend(page, size, userId);
    }

}
