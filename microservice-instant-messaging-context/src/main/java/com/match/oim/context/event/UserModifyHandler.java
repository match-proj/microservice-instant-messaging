package com.match.oim.context.event;

import com.github.middleware.event.EventHandler;
import com.match.common.utils.JsonUtils;
import com.match.user.event.EventUserCreateDTO;
import com.match.user.event.EventUserModifyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author zhangchao
 * @Date 2019/8/5 17:07
 * @Version v1.0
 */
@Component
public class UserModifyHandler implements EventHandler<EventUserModifyDTO> {

    Logger logger = LoggerFactory.getLogger(getEventName());

    @Override
    public void handler(EventUserModifyDTO eventUserModifyDTO) {
        logger.info("handler => {}", JsonUtils.obj2json(eventUserModifyDTO));
    }

    @Override
    public String getEventName() {
        return EventUserModifyDTO.EVENT_NAME;
    }
}
