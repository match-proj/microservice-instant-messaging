package com.match.oim.context.domain.service;

import com.match.oim.client.bean.FriendsApplyRecordDTO;
import com.match.oim.client.bean.FriendsApplyRecordStatus;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.context.domain.entity.FriendsApplyRecord;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/6/13 11:41
 * @Version v1.0
 */
public interface FriendsService {

    void applyFriends(String peopleId, String messageUserId);

    void handlerFriendsApply(String peopleId, String id, FriendsApplyRecordStatus status);

    List<FriendsApplyRecordDTO> getFriendsApplyList(String peopleId);

    List<MessageUserSimpleInfoDTO> getFriendsList(String peopleId);

}
