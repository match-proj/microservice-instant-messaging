package com.match.oim.client.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Author zhangchao
 * @Date 2019/6/6 10:38
 * @Version v1.0
 */
@Getter
@Setter
public class MessageDTO {
    private String id;
    private MessageType messageType;
    private String body;
    private Date sendTime;
    private Integer view;
}
