package com.match.oim.context.domain.entity;

import com.match.oim.client.bean.MessageType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author zhangchao
 * @Date 2019/5/24 15:22
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class Message {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private String conversationId;

    @Column
    private String messageUserId;

    @Column
    private MessageType messageType;

    @Column
    private String body;

    @Column
    private Date sendTime;

    @Column
    private Integer view;

}
