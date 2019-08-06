package com.match.oim.context.domain.service.impl;

import com.match.common.exception.BusinessException;
import com.match.oim.client.bean.*;
import com.match.oim.context.domain.entity.Conversation;
import com.match.oim.context.domain.entity.ConversationUser;
import com.match.oim.context.domain.entity.Message;
import com.match.oim.context.domain.entity.MessageUser;
import com.match.oim.context.domain.repostory.ConversationRepository;
import com.match.oim.context.domain.repostory.ConversationUserRepository;
import com.match.oim.context.domain.repostory.MessageRepository;
import com.match.oim.context.domain.repostory.MessageUserRepository;
import com.match.oim.context.domain.service.ConversationService;
import com.match.oim.context.domain.service.OssObjectServie;
import com.match.user.client.UserClient;
import com.match.user.client.bean.UserInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhangchao
 * @Date 2019/5/28 11:21
 * @Version v1.0
 */
@Service
public class ConversationServiceImpl implements ConversationService {


    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    ConversationUserRepository conversationUserRepository;

    @Autowired
    MessageUserRepository messageUserRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired(required = false)
    UserClient userClient;

    @Autowired
    OssObjectServie ossObjectServie;


    @Override
    public String createSingleConversation(String peopleId, String firendsMessageUserId) {
        MessageUser slefMessageUser = messageUserRepository.findByPeopleId(peopleId);
        String slefMessageUserId = slefMessageUser.getId();
        if (StringUtils.equals(slefMessageUserId, firendsMessageUserId)) {
            return null;
        }

        String conversationId = conversationRepository.getConversationBySelfAndFirends(slefMessageUserId, firendsMessageUserId);
        if (StringUtils.isNotEmpty(conversationId)) {
            return conversationId;
        }

        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.SINGLE);
        conversation.setMessageUserId(slefMessageUserId);
        conversation.setCreateTime(new Date());
        conversation.setLastUpdateTime(new Date());

        conversationRepository.save(conversation);

        ConversationUser conversationUserSlef = new ConversationUser();
        conversationUserSlef.setBackgroudPath("");
        conversationUserSlef.setTop(0);
        conversationUserSlef.setDontDisturb(0);
        conversationUserSlef.setMessageUserId(slefMessageUserId);
        conversationUserSlef.setConversationId(conversation.getId());
        conversationUserRepository.save(conversationUserSlef);


        ConversationUser conversationUserFirends = new ConversationUser();
        conversationUserFirends.setBackgroudPath("");
        conversationUserFirends.setTop(0);
        conversationUserFirends.setDontDisturb(0);
        conversationUserFirends.setMessageUserId(firendsMessageUserId);
        conversationUserFirends.setConversationId(conversation.getId());
        conversationUserRepository.save(conversationUserFirends);
        return conversation.getId();
    }

    @Override
    public String createGroupConversation(String peopleId, List<String> firendsMessageUserIds) {
        String conversationId = createSingleConversation(peopleId, firendsMessageUserIds.get(0));

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if (conversationOptional.isPresent()) {
            Conversation conversation = conversationOptional.get();
            conversation.setType(ConversationType.GROUP);
            conversation.setLastUpdateTime(new Date());

            List<ConversationUser> conversationUserList = conversation.getConversationUserList();

            List<String> userAvatars = new ArrayList<>();
            if (conversationUserList.size() >= 9) {
                conversationUserList = conversationUserList.subList(0, 9);
            }

            List<String> collect = conversationUserList.stream()
                    .map(item -> item.getMessageUserId())
                    .map(id -> messageUserRepository.findById(id))
                    .map(item -> item.get())
                    .map(item -> item.getPeopleId())
                    .map(id -> userClient.getUser(id))
                    .map(p -> {
                        userAvatars.add(p.getEncodedPrincipal());
                        return p.getNickName();
                    })
                    .collect(Collectors.toList());

            String groupName = String.join("@", collect);
            if (groupName.length() > 20) {
                groupName = groupName.substring(0, 20);
            }

            String icon = ossObjectServie.generateGroupIcon("conversation", userAvatars);
            conversation.setIcon(icon);
            conversation.setName(groupName);
            conversationRepository.saveAndFlush(conversation);
        }

        addMessageUserToGroupConversation(conversationId, firendsMessageUserIds);
        return conversationId;
    }


    @Override
    public String addMessageUserToGroupConversation(String conversationId, List<String> firendsMessageUserIds) {
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        Conversation conversation = conversationOptional.get();

        for (String firendsMessageUserId : firendsMessageUserIds) {
            if (conversationUserRepository.findByMessageUserIdAndConversationId(firendsMessageUserId, conversationId) == null) {
                ConversationUser conversationUser = new ConversationUser();
                conversationUser.setMessageUserId(firendsMessageUserId);
                conversationUser.setConversationId(conversationId);
                conversationUserRepository.save(conversationUser);
            }
        }

        return conversation.getId();
    }

    @Override
    public List<ConversationListDTO> list(String peopleId) {
        MessageUser slefMessageUser = messageUserRepository.findByPeopleId(peopleId);
        String slefMessageUserId = slefMessageUser.getId();
        List<String> conversationIds = conversationUserRepository.findAllByMessageUserId(slefMessageUserId).stream().map(i -> i.getConversationId()).collect(Collectors.toList());

        //拉去people的会话
        List<Conversation> conversationList = conversationRepository.findAllById(conversationIds);


        //填充会话用户的 头像和昵称
        conversationList.stream().forEach(item -> {
            List<ConversationUser> conversationUserList = item.getConversationUserList();
            //拉对方的头像
            Optional<UserInfoDTO> optionalPeople = conversationUserList.stream()
                    .filter(i -> !StringUtils.equals(i.getMessageUserId(), slefMessageUserId))
                    .map(i -> i.getMessageUserId())
                    .map(id -> messageUserRepository.findById(id))
                    .map(mu -> userClient.getUser(mu.get().getPeopleId())).findFirst();


//            conversationUserList.stream().map(i -> i.getMessageUserId())
//                    .map(i -> messageUserRepository.findById(i).get().getPeopleId())
//                    .map(i -> peopleService.findPeopleById(i).get())
//                    .forEach(people -> {
//                        //todo 要删除的
//                        if (people.getEncodedPrincipal().contains("@")) {
//                            String path = ossObjectServie.generateUserIcon("headimg", people.getNickName());
//                            people.setEncodedPrincipal(path);
//                            PeopleInfoDto peopleInfoDto = new PeopleInfoDto();
//                            peopleInfoDto.setEncodedPrincipal(path);
//                            peopleService.updatePeopleInfo(people.getId(), peopleInfoDto);
//
//                            MessageUser messageUser = messageUserRepository.findByPeopleId(people.getId());
//                            messageUser.setEncodedPrincipal(path);
//                            messageUserRepository.saveAndFlush(messageUser);
//                        }
//                    });

            if (optionalPeople.isPresent()) {
                UserInfoDTO people = optionalPeople.get();
                item.setName(people.getNickName());
                item.setIcon(people.getEncodedPrincipal());
            }
        });


        conversationList.stream().forEach(item -> {
            if (item.getType() == ConversationType.SINGLE) {
                List<ConversationUser> conversationUserList = item.getConversationUserList();
                //拉对方的头像
                Optional<UserInfoDTO> optionalPeople = conversationUserList.stream()
                        .filter(i -> !StringUtils.equals(i.getMessageUserId(), slefMessageUserId))
                        .map(i -> i.getMessageUserId())
                        .map(id -> messageUserRepository.findById(id))
                        .map(mu -> userClient.getUser(mu.get().getPeopleId())).findFirst();

                if (optionalPeople.isPresent()) {
                    UserInfoDTO people = optionalPeople.get();
                    item.setName(people.getNickName());
                    item.setIcon(people.getEncodedPrincipal());
                }
            }
        });


        List<ConversationListDTO> collect = conversationList.stream()
            .map(item -> {
                ConversationListDTO conversationListDto = new ConversationListDTO();
                conversationListDto.setId(item.getId());
                conversationListDto.setType(item.getType());
                conversationListDto.setName(item.getName());
                conversationListDto.setIcon(item.getIcon());
                conversationListDto.setTags(item.getTags());

                Optional<ConversationUser> first = item.getConversationUserList().stream().filter(i -> i.getMessageUserId().equals(slefMessageUserId)).findFirst();
                if(first.isPresent()){
                    ConversationUser conversationUser = first.get();
                    conversationListDto.setTop(conversationUser.getTop());
                    conversationListDto.setDontDisturb(conversationUser.getDontDisturb());
                }
                conversationListDto.setMessageUserId(item.getMessageUserId());
                Message lastMessage = messageRepository.findFirstByConversationIdOrderBySendTimeDesc(item.getId());
                if (lastMessage != null) {
                    MessageDTO messageDto = new MessageDTO();
                    messageDto.setId(lastMessage.getId());
                    messageDto.setMessageType(lastMessage.getMessageType());
                    messageDto.setBody(lastMessage.getBody());
                    messageDto.setSendTime(lastMessage.getSendTime());
                    messageDto.setView(lastMessage.getView());
                    conversationListDto.setLastMessage(messageDto);
                }

            return conversationListDto;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public void editConversation(String peopleId, String conversationId, EditConversationDTO editConversationDto) {
        MessageUser slefMessageUser = messageUserRepository.findByPeopleId(peopleId);
        String slefMessageUserId = slefMessageUser.getId();

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if (!conversationOptional.isPresent()) {
            throw new BusinessException("会话不存在");
        }

        Conversation conversation = conversationOptional.get();
        if (!StringUtils.equals(conversation.getMessageUserId(), slefMessageUserId)) {
            throw new BusinessException("只能由群主修改");
        }

        if (StringUtils.isNotEmpty(editConversationDto.getName())) {
            conversation.setName(editConversationDto.getName());
        }

        if (StringUtils.isNotEmpty(editConversationDto.getIcon())) {
            conversation.setIcon(editConversationDto.getIcon());
        }


        if (editConversationDto.getTags() != null) {
            conversation.setTags(String.join("@", editConversationDto.getTags()));
        }
        conversationRepository.saveAndFlush(conversation);

        if (editConversationDto.getTop() != null) {
            ConversationUser messageUser = conversationUserRepository.findByMessageUserIdAndConversationId(slefMessageUserId, conversationId);
            messageUser.setTop(editConversationDto.getTop());
            conversationUserRepository.saveAndFlush(messageUser);
        }

        if (editConversationDto.getDontDisturb() != null) {
            ConversationUser messageUser = conversationUserRepository.findByMessageUserIdAndConversationId(slefMessageUserId, conversationId);
            messageUser.setDontDisturb(editConversationDto.getDontDisturb());
            conversationUserRepository.saveAndFlush(messageUser);
        }
    }


    @Override
    public ConversationDTO getConversation(String conversationId) {
        Optional<Conversation> optionalConversation = conversationRepository.findById(conversationId);
        Conversation item = optionalConversation.get();


        if (item.getType() == ConversationType.GROUP) {
//            //todo 要删除的
//            if (item.getIcon().contains("@")) {
//                Conversation conversation = item;
//                List<String> userAvatars = conversation.getConversationUserList().stream().map(citem -> citem.getMessageUserId())
//                        .map(id -> messageUserRepository.findById(id))
//                        .map(citem -> citem.get())
//                        .map(citem -> citem.getPeopleId())
//                        .map(id -> userClient.getUser(id))
//                        .map(p -> p.getEncodedPrincipal())
//                        .collect(Collectors.toList());
//                String icon = ossObjectServie.generateGroupIcon("conversation", userAvatars);
//                item.setIcon(icon);
//                conversationRepository.saveAndFlush(item);
//            }
        }

        return optionalConversation.map(i ->{
            ConversationDTO conversationDTO = new ConversationDTO();
            conversationDTO.setId(i.getId());
            conversationDTO.setType(i.getType());
            conversationDTO.setName(i.getName());
            conversationDTO.setIcon(i.getIcon());
            conversationDTO.setTags(i.getTags());
            conversationDTO.setCreateTime(i.getCreateTime());
            conversationDTO.setLastUpdateTime(i.getLastUpdateTime());
            conversationDTO.setMessageUserId(i.getMessageUserId());

            List<ConversationUserDTO> conversationUserList = new ArrayList<>();

            for (ConversationUser conversationUser : i.getConversationUserList()) {
                ConversationUserDTO cud = new ConversationUserDTO();
                cud.setId(conversationUser.getId());
                cud.setMessageUserId(conversationUser.getMessageUserId());
                cud.setConversationId(conversationUser.getConversationId());
                cud.setBackgroudPath(conversationUser.getBackgroudPath());
                cud.setTop(conversationUser.getTop());
                cud.setDontDisturb(conversationUser.getDontDisturb());
                conversationUserList.add(cud);
            }

            conversationDTO.setConversationUserList(conversationUserList);
            return conversationDTO;
        }).get();
    }
}
