package com.match.oim.context.domain.service.impl;

import com.match.common.PageResult;
import com.match.common.exception.BusinessException;
import com.match.oim.client.bean.MessageDTO;
import com.match.oim.client.bean.MessageType;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.client.bean.PublishMessageDTO;
import com.match.oim.context.domain.entity.Conversation;
import com.match.oim.context.domain.entity.Message;
import com.match.oim.context.domain.entity.MessageUser;
import com.match.oim.context.domain.repostory.ConversationRepository;
import com.match.oim.context.domain.repostory.MessageRepository;
import com.match.oim.context.domain.repostory.MessageUserRepository;
import com.match.oim.context.domain.service.MessageService;
import com.match.oim.context.domain.service.OssObjectServie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhangchao
 * @Date 2019/5/24 16:22
 * @Version v1.0
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageUserRepository messageUserRepository;


    @Autowired
    OssObjectServie ossObjectServie;

    @Override
    public void publishMessage(String peopleId, PublishMessageDTO publishMessage) {
        MessageUser messageUser = messageUserRepository.findByPeopleId(peopleId);
        String selfMessageUserId = messageUser.getId();

        Optional<Conversation> conversationOptional = conversationRepository.findById(publishMessage.getConversationId());

        if (!conversationOptional.isPresent()) {
            throw new BusinessException("会话不存在");
        }
        Conversation conversation = conversationOptional.get();

        MessageType messageType = publishMessage.getMessageType();

        Message message = new Message();
        message.setConversationId(conversation.getId());
        message.setMessageUserId(selfMessageUserId);
        message.setBody(publishMessage.getBody());
        message.setMessageType(messageType);
        message.setSendTime(new Date());
        message.setView(0);
        messageRepository.save(message);


        // todo 推送消息
    }


    @Override
    public PageResult<MessageDTO> list(Integer page, Integer size, String conversationId) {

        PageRequest of = PageRequest.of(page - 1, size,  Sort.by(Sort.Order.desc("sendTime")));
        Specification spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> pr = new ArrayList<>();
                pr.add(cb.equal(root.get("conversationId").as(String.class),conversationId));
                return cb.and(pr.toArray(new Predicate[pr.size()]));
            }
        };

        Page<Message> all = messageRepository.findAll(spec, of);
        long totalElements = all.getTotalElements();
        List<MessageDTO> content = all.getContent().stream().map(item ->{
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(item.getId());
            messageDTO.setMessageType(item.getMessageType());
            messageDTO.setBody(item.getBody());
            messageDTO.setSendTime(item.getSendTime());
            messageDTO.setView(item.getView());
            return messageDTO;
        }).collect(Collectors.toList());
        return new PageResult(totalElements,content);
    }

    @Override
    public MessageUserSimpleInfoDTO getPeopleSimpleInfo(String messageUserId) {
        Optional<MessageUser> messageUserOptional = messageUserRepository.findById(messageUserId);
        if(!messageUserOptional.isPresent()){
            throw new BusinessException("用户不存在");
        }
        MessageUser messageUser = messageUserOptional.get();

        MessageUserSimpleInfoDTO peopleSimpleDto = new MessageUserSimpleInfoDTO();
        peopleSimpleDto.setId(messageUser.getId());
        peopleSimpleDto.setNickName(messageUser.getNickName());
        peopleSimpleDto.setEncodedPrincipal(messageUser.getEncodedPrincipal());
        return peopleSimpleDto;
    }

    @Override
    public MessageUserSimpleInfoDTO getPeopleSimpleInfoByPeopleId(String peopleId) {
        MessageUser messageUser = messageUserRepository.findByPeopleId(peopleId);
        return getPeopleSimpleInfo(messageUser.getId());
    }
}
