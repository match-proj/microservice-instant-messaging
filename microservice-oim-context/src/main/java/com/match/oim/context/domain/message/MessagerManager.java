package com.match.oim.context.domain.message;

import com.match.oim.client.bean.MessageType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author zhangchao
 * @Date 2019/5/29 16:05
 * @Version v1.0
// */
//@Component
public class MessagerManager implements ApplicationContextAware {

    Map<MessageType,Object> typeObjectMap = new HashMap<>();

    public ApplicationContext $;

//    @PostConstruct
    public void init(){
        for (Map.Entry<String, Object> entry : $.getBeansWithAnnotation(Messager.class).entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        $ = applicationContext;
    }
}
