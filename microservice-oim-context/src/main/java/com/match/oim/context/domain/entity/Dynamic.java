package com.match.oim.context.domain.entity;

import com.match.oim.client.bean.DynamicVisibility;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 动态
 * @Author zhangchao
 * @Date 2019/5/24 15:10
 * @Version v1.0
 */
@Getter
@Setter
@Entity
public class Dynamic {

    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Column
    private Date createTime;

    @Column
    private String peopleId;

    @Column
    private String text;

    @Column
    private String images;//List<String>

    @Column
    private String location;//位置

    @Column
    @Enumerated(value = EnumType.STRING)
    private DynamicVisibility visibility;


}
