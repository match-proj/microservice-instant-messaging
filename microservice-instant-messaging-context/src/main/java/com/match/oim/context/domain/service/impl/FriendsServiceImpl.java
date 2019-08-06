package com.match.oim.context.domain.service.impl;

import com.match.oim.client.bean.FriendsApplyRecordDTO;
import com.match.oim.client.bean.FriendsApplyRecordStatus;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.context.domain.entity.FriendsApplyRecord;
import com.match.oim.context.domain.entity.MessageFirends;
import com.match.oim.context.domain.entity.MessageUser;
import com.match.oim.context.domain.repostory.FriendsApplyRecordRepository;
import com.match.oim.context.domain.repostory.MessageFirendsRepository;
import com.match.oim.context.domain.repostory.MessageUserRepository;
import com.match.oim.context.domain.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhangchao
 * @Date 2019/6/13 11:41
 * @Version v1.0
 */
@Service
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    FriendsApplyRecordRepository friendsApplyRecordRepository;

    @Autowired
    MessageFirendsRepository messageFirendsRepository;

    @Autowired
    MessageUserRepository messageUserRepository;

    @Override
    public void applyFriends(String peopleId,String messageUserId) {

        MessageUser messageUser = messageUserRepository.findByPeopleId(peopleId);
        FriendsApplyRecord friendsApplyRecord = new FriendsApplyRecord();
        friendsApplyRecord.setApplyMessageUserId(messageUser.getId());
        friendsApplyRecord.setMessageUserId(messageUserId);
        friendsApplyRecord.setApplyTime(new Date());
        friendsApplyRecord.setStatus(FriendsApplyRecordStatus.APPLY);
        friendsApplyRecordRepository.save(friendsApplyRecord);

        // todo  推送消息给  friendsApplyRecord.getMessageUserId()
    }

    @Override
    public void handlerFriendsApply(String peopleId,String id, FriendsApplyRecordStatus status) {
        Optional<FriendsApplyRecord> friendsApplyRecordOptional = friendsApplyRecordRepository.findById(id);
        if(friendsApplyRecordOptional.isPresent()){
            FriendsApplyRecord friendsApplyRecord = friendsApplyRecordOptional.get();

            Assert.isTrue(friendsApplyRecord.getStatus() == FriendsApplyRecordStatus.APPLY,"请求已处理");

            friendsApplyRecord.setStatus(status);
            friendsApplyRecord.setHandlerTime(new Date());
            friendsApplyRecordRepository.saveAndFlush(friendsApplyRecord);
            // todo  推送消息给  friendsApplyRecord.getApplyMessageUserId()

            if(status == FriendsApplyRecordStatus.AGREE ){
                if(!messageFirendsRepository.existsByMyMessageUserIdAndFirendsMessageUserId(friendsApplyRecord.getApplyMessageUserId(),friendsApplyRecord.getMessageUserId())){
                    MessageFirends messageFirends = new MessageFirends();
                    messageFirends.setMyMessageUserId(friendsApplyRecord.getApplyMessageUserId());
                    messageFirends.setFirendsMessageUserId(friendsApplyRecord.getMessageUserId());
                    messageFirendsRepository.save(messageFirends);
                }
                if(!messageFirendsRepository.existsByMyMessageUserIdAndFirendsMessageUserId(friendsApplyRecord.getMessageUserId(),friendsApplyRecord.getApplyMessageUserId())){
                    MessageFirends messageFirends = new MessageFirends();
                    messageFirends.setMyMessageUserId(friendsApplyRecord.getMessageUserId());
                    messageFirends.setFirendsMessageUserId(friendsApplyRecord.getApplyMessageUserId());
                    messageFirendsRepository.save(messageFirends);
                }
            }
        }
    }

    @Override
    public List<MessageUserSimpleInfoDTO> getFriendsList(String peopleId) {
        MessageUser messageUser = messageUserRepository.findByPeopleId(peopleId);
        Assert.notNull(messageUser,"message user is null");
        List<MessageFirends> messageFirends = messageFirendsRepository.findAllByMyMessageUserId(messageUser.getId());

        return messageFirends.stream().map(item ->{
            MessageUser friendsMessageUser = messageUserRepository.findById(item.getFirendsMessageUserId()).get();
            return new MessageUserSimpleInfoDTO(friendsMessageUser.getId(),friendsMessageUser.getNickName(),friendsMessageUser.getEncodedPrincipal() );
        }).collect(Collectors.toList());
    }


    @Override
    public List<FriendsApplyRecordDTO> getFriendsApplyList(String peopleId) {
        MessageUser messageUser = messageUserRepository.findByPeopleId(peopleId);

        Assert.notNull(messageUser,"message user is null");

        List<FriendsApplyRecord> list = friendsApplyRecordRepository.findAllByMessageUserIdOrderByApplyTimeDesc(messageUser.getId());
        List<FriendsApplyRecordDTO> collect = list.stream().map(item -> {

            MessageUser applyMessageUser = messageUserRepository.findById(item.getApplyMessageUserId()).get();

            FriendsApplyRecordDTO record = new FriendsApplyRecordDTO();
            record.setId(item.getId());
            record.setApplyMessageUserId(item.getApplyMessageUserId());
            record.setApplyMessageNickName(applyMessageUser.getNickName());
            record.setApplyMessageEncodedPrincipal(applyMessageUser.getEncodedPrincipal());
            record.setMessageUserId(item.getMessageUserId());
            record.setApplyTime(item.getApplyTime());
            record.setHandlerTime(item.getHandlerTime());
            record.setStatus(item.getStatus().name());
            return record;
        }).collect(Collectors.toList());
        return collect;
    }
}
