package com.match.oim.client;

import com.match.common.PageResult;
import com.match.oim.client.bean.MessageDTO;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.client.bean.PublishMessageDTO;
import com.match.oim.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhangchao
 * @Date 2019/8/2 18:05
 * @Version v1.0
 */
@FeignClient(name = "microservice-instant-messaging",configuration = FeignSupportConfig.class)
public interface MessageClient {

    @GetMapping("/message/getPeopleSimpleInfoByPeopleId")
    MessageUserSimpleInfoDTO getPeopleSimpleInfoByPeopleId(@RequestParam("peopleId")String peopleId);

    @GetMapping("/message/getMessageUserSimpleInfo")
    MessageUserSimpleInfoDTO getPeopleSimpleInfo(@RequestParam("messageUserId") String messageUserId);

    @PostMapping("/message/publish")
    void publishMessage(@RequestParam("userId")String userId,@RequestBody PublishMessageDTO publishMessageDto);

    @GetMapping("/message/{conversationId}/list")
    PageResult<MessageDTO> list(@RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                       @RequestParam(required = false,name = "size",defaultValue = "10") Integer size,
                                       @PathVariable("conversationId") String conversationId);
}
