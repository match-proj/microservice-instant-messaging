package com.match.oim.context.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @Author zhangchao
 * @Date 2019/5/24 15:17
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class ConversationUser {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private String messageUserId;//会话用户

    @Column
    private String conversationId;

    @Column
    private String backgroudPath;//地址

    @Column
    private Integer top;//置顶

    @Column
    private Integer dontDisturb;//免打扰

}
