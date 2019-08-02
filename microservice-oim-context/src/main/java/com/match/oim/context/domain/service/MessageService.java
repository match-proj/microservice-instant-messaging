package com.match.oim.context.domain.service;

import com.match.common.PageResult;
import com.match.oim.client.bean.MessageDTO;
import com.match.oim.client.bean.MessageUserSimpleInfoDTO;
import com.match.oim.client.bean.PublishMessageDTO;
import com.match.oim.context.domain.entity.Message;

/**
 * @Author zhangchao
 * @Date 2019/5/24 16:22
 * @Version v1.0
 */
public interface MessageService {

    void publishMessage(String peopleId, PublishMessageDTO dynamic);

    PageResult<MessageDTO> list(Integer page, Integer size, String conversationId);

    MessageUserSimpleInfoDTO getPeopleSimpleInfo(String messageUserId);

    MessageUserSimpleInfoDTO getPeopleSimpleInfoByPeopleId(String peopleId);
}
