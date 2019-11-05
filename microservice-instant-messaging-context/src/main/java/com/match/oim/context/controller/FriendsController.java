package com.match.oim.context.controller;

import com.match.common.context.UserContext;
import com.match.oim.client.FriendsClient;
import com.match.oim.client.bean.FriendsApplyRecordDTO;
import com.match.oim.client.bean.FriendsApplyRecordStatus;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.context.domain.entity.FriendsApplyRecord;
import com.match.oim.context.domain.service.FriendsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/6/13 11:42
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class FriendsController implements FriendsClient {


    @Autowired
    private FriendsService friendsService;


    @Override
    @PostMapping("/applyFriends")
    public void applyFriends(@RequestParam("userId") String userId,@RequestParam("messageUserId")String messageUserId){
        friendsService.applyFriends(userId,messageUserId);
    }

    @Override
    @PostMapping("/handlerFriendsApply")
    public void handlerFriendsApply(@RequestParam("userId") String userId,@RequestParam("id")String id, @RequestParam("status") FriendsApplyRecordStatus status){
        friendsService.handlerFriendsApply(userId,id,status);
    }

    @Override
    @GetMapping("/getFriendsApplyList")
    public List<FriendsApplyRecordDTO> getFriendsApplyList(@RequestParam("userId") String userId){
        return friendsService.getFriendsApplyList(userId);
    }

    @Override
    @GetMapping("/getFriendsList")
    public List<MessageUserSimpleInfoDTO> getFriendsList(@RequestParam("userId") String userId){
        return friendsService.getFriendsList(userId);
    }


}
