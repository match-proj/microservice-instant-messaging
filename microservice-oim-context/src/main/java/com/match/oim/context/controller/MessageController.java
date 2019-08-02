package com.match.oim.context.controller;

import com.match.common.PageResult;
import com.match.common.context.UserContext;
import com.match.oim.client.MessageClient;
import com.match.oim.client.bean.MessageDTO;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.client.bean.PublishMessageDTO;
import com.match.oim.context.domain.entity.Message;
import com.match.oim.context.domain.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhangchao
 * @Date 2019/5/28 17:11
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class MessageController implements MessageClient {


    @Autowired
    private MessageService messageService;


    public MessageUserSimpleInfoDTO getPeopleSimpleInfoByPeopleId(@RequestParam("peopleId")String peopleId){
        return messageService.getPeopleSimpleInfoByPeopleId(peopleId);
    }


    public MessageUserSimpleInfoDTO getPeopleSimpleInfo(@RequestParam("messageUserId") String messageUserId){
        return messageService.getPeopleSimpleInfo(messageUserId);
    }


    public void publishMessage(@RequestParam("userId")String userId,@RequestBody PublishMessageDTO publishMessageDto){
        messageService.publishMessage(userId,publishMessageDto);
    }


    public PageResult<MessageDTO> list(@RequestParam(required = false,name = "page",defaultValue = "1") Integer page,
                                       @RequestParam(required = false,name = "size",defaultValue = "10") Integer size,
                                       @PathVariable("conversationId") String conversationId) {
        return messageService.list(page, size, conversationId);
    }

}
