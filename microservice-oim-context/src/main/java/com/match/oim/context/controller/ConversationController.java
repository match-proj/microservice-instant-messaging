package com.match.oim.context.controller;

import com.match.common.ResponseData;
import com.match.common.context.UserContext;
import com.match.common.utils.ResponseDataUtils;
import com.match.oim.client.ConversationClient;
import com.match.oim.client.bean.*;
import com.match.oim.context.domain.service.ConversationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * App会话
 * @Author zhangchao
 * @Date 2019/5/28 11:16
 * @Version v1.0
 */
@RestController
@RequestMapping
@Slf4j
public class ConversationController implements ConversationClient {

    @Autowired
    ConversationService conversationService;


    public ResponseData createSingleConversation(@RequestParam("userId") String userId,@Valid @RequestBody CreateConversationDTO createConversationDTO){
        String conversationId = conversationService.createSingleConversation(userId, createConversationDTO.getFirendsMessageUserId());
        return ResponseDataUtils.buildSuccess(conversationId);
    }

    public ResponseData createGroupConversation(@RequestParam("userId") String userId,@Valid @RequestBody CreateGroupConversationDTO createConversationDTO){
        String conversationId = conversationService.createGroupConversation(userId,createConversationDTO.getMessageUserIds());
        return ResponseDataUtils.buildSuccess(conversationId);
    }


    public void editConversation(@PathVariable("conversationId") String conversationId ,@RequestParam("userId") String userId, @RequestBody EditConversationDTO editConversationDTO){
        conversationService.editConversation(userId,conversationId ,editConversationDTO);
    }


    public List<ConversationListDTO> list(@RequestParam("userId") String userId){
        return conversationService.list(userId);
    }

    public ConversationDTO getConversation(@RequestParam("conversationId") String conversationId){
        return conversationService.getConversation(conversationId);
    }

}
