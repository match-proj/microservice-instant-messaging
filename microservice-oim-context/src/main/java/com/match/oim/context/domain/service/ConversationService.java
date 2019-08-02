package com.match.oim.context.domain.service;

import com.match.oim.client.bean.ConversationDTO;
import com.match.oim.client.bean.ConversationListDTO;
import com.match.oim.client.bean.EditConversationDTO;
import com.match.oim.context.domain.entity.Conversation;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/5/28 11:21
 * @Version v1.0
 */
public interface ConversationService {

    String createSingleConversation(String peopleId, String firendsMessageUserId);

    String createGroupConversation(String peopleId, List<String> firendsMessageUserIds);

    String addMessageUserToGroupConversation(String conversationId, List<String> firendsMessageUserIds);

    List<ConversationListDTO> list(String peopleId);

    void editConversation(String peopleId, String conversationId, EditConversationDTO editConversationDto);

    ConversationDTO getConversation(String conversationId);
}
