package com.match.oim.context.domain.entity;

import com.match.oim.client.bean.FriendsApplyRecordStatus;
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
 * @Date 2019/6/13 11:13
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class FriendsApplyRecord {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private String applyMessageUserId;

    @Column
    private String messageUserId;

    @Column
    private Date applyTime;

    @Column
    private Date handlerTime;

    @Column
    private FriendsApplyRecordStatus status;


}
