package com.match.oim.context.domain.entity;

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
 * @Date 2019/5/24 15:15
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class DynamicAssist {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private Date createTime;

    @Column
    private String dynamicId;

    @Column
    private String peopleId;

}
