package com.match.oim.client;

import com.match.common.context.UserContext;
import com.match.oim.client.bean.FriendsApplyRecordDTO;
import com.match.oim.client.bean.FriendsApplyRecordStatus;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/8/2 18:01
 * @Version v1.0
 */
@FeignClient(name = "microservice-oim",configuration = FeignSupportConfig.class)
public interface FriendsClient {

    @PostMapping("/applyFriends")
    public void applyFriends(@RequestParam("userId") String userId,@RequestParam("messageUserId")String messageUserId);

    @PostMapping("/handlerFriendsApply")
    public void handlerFriendsApply(@RequestParam("userId") String userId,@RequestParam("id")String id,@RequestParam("status") FriendsApplyRecordStatus status);

    @GetMapping("/getFriendsApplyList")
    public List<FriendsApplyRecordDTO> getFriendsApplyList(@RequestParam("userId") String userId);

    @GetMapping("/getFriendsList")
    public List<MessageUserSimpleInfoDTO> getFriendsList(@RequestParam("userId") String userId);

}
