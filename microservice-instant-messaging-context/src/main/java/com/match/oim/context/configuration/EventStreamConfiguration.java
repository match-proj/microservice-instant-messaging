package com.match.oim.context.configuration;

import com.github.middleware.channel.ChannelProvider;
import com.github.middleware.config.EventConfigItem;
import com.github.middleware.redis.RedisChannelConfig;
import com.github.middleware.redis.RedisChannelProvider;
import com.match.user.event.EventUserCreateDTO;
import com.match.user.event.EventUserModifyDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class EventStreamConfiguration {


    @Bean
    public RedisChannelConfig getRedisChannelConfig(){
        Properties properties = new Properties();
        properties.setProperty("host","localhost:6379");
        return new RedisChannelConfig(properties);
    }

    @Bean
    public ChannelProvider getChannelProvider(){
        RedisChannelProvider redisChannelProvider = new RedisChannelProvider(getRedisChannelConfig());
        redisChannelProvider.init();
        return redisChannelProvider;
    }

    @Bean
    public EventConfigItem getEventUserModifyEventConfigItem(){
        EventConfigItem b = new EventConfigItem();
        b.setChannelProvider(getChannelProvider());
        b.setEventName(EventUserModifyDTO.EVENT_NAME);
        return b;
    }

    @Bean
    public EventConfigItem getEventUserCreateEventConfigItem(){
        EventConfigItem b = new EventConfigItem();
        b.setChannelProvider(getChannelProvider());
        b.setEventName(EventUserCreateDTO.EVENT_NAME);
        return b;
    }

}
