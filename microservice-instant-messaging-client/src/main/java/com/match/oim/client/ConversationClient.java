package com.match.oim.client;

import com.match.common.ResponseData;
import com.match.oim.client.bean.*;
import com.match.oim.client.configuration.FeignSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/8/2 17:50
 * @Version v1.0
 */
@FeignClient(name = "microservice-oim",configuration = FeignSupportConfig.class)
public interface ConversationClient {

    @PostMapping("/conversation/createSingleConversation")
    ResponseData createSingleConversation(@RequestParam("userId") String userId,@Valid @RequestBody CreateConversationDTO createConversationDTO);

    @PostMapping("/conversation/createGroupConversation")
    ResponseData createGroupConversation(@RequestParam("userId") String userId,@Valid @RequestBody CreateGroupConversationDTO createConversationDTO);

    @PostMapping("/conversation/editConversation/{conversationId}")
    void editConversation(@PathVariable("conversationId") String conversationId ,@RequestParam("userId") String userId, @RequestBody EditConversationDTO editConversationDTO);

    @PostMapping("/conversation/list")
    List<ConversationListDTO> list(@RequestParam("userId") String userId);

    @GetMapping("/conversation/getConversation")
    ConversationDTO getConversation(@RequestParam("conversationId") String conversationId);
}
