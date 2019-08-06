package com.match.oim.context.event;

import com.github.middleware.event.EventHandler;
import com.match.common.utils.JsonUtils;
import com.match.oim.context.domain.entity.MessageUser;
import com.match.oim.context.domain.repostory.MessageUserRepository;
import com.match.user.event.EventUserCreateDTO;
import com.match.user.event.EventUserModifyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author zhangchao
 * @Date 2019/8/5 17:07
 * @Version v1.0
 */
@Component
public class UserModifyHandler implements EventHandler<EventUserModifyDTO> {

    Logger logger = LoggerFactory.getLogger(getEventName());


    @Autowired
    MessageUserRepository messageUserRepository;


    @Override
    public void handler(EventUserModifyDTO e) {
        logger.info("handler => {}", JsonUtils.obj2json(e));
        MessageUser messageUser = messageUserRepository.findByPeopleId(e.getUserId());
        if(messageUser == null){
            messageUser = new MessageUser();
        }
        messageUser.setPeopleId(e.getUserId());
        messageUser.setNickName(e.getUsername());
        messageUser.setEncodedPrincipal(e.getIcon());
        messageUserRepository.saveAndFlush(messageUser);
    }

    @Override
    public String getEventName() {
        return EventUserModifyDTO.EVENT_NAME;
    }
}
