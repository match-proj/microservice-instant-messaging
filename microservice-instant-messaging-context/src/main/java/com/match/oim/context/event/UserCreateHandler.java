package com.match.oim.context.event;

import com.github.middleware.event.EventHandler;
import com.match.common.utils.JsonUtils;
import com.match.user.event.EventUserCreateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author zhangchao
 * @Date 2019/8/5 17:07
 * @Version v1.0
 */
@Component
public class UserCreateHandler implements EventHandler<EventUserCreateDTO> {

    Logger logger = LoggerFactory.getLogger(getEventName());

    @Override
    public void handler(EventUserCreateDTO eventUserCreateDTO) {
        logger.info("handler => {}", JsonUtils.obj2json(eventUserCreateDTO));
    }

    @Override
    public String getEventName() {
        return EventUserCreateDTO.EVENT_NAME;
    }
}
