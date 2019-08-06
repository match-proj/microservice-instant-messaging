package com.match.oim.client.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhangchao
 * @Date 2019/5/28 17:13
 * @Version v1.0
 */
@Getter
@Setter
public class PublishMessageDTO {
    private String conversationId;
    private String body;
    private MessageType messageType;
}
