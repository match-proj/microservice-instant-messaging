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
 * @Date 2019/5/24 15:22
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class MessageUser {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private String peopleId;

    @Column
    private String encodedPrincipal;

    @Column
    private String nickName;

    @Column
    private String token;

    @Column
    private String channelId;

    @Column
    private String host;

}
