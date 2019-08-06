package com.match.oim.client.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/5/28 11:18
 * @Version v1.0
 */
@Getter
@Setter
public class EditConversationDTO {


    private String name;
    private String icon;
    private List<String> tags;
    private Integer top;//置顶
    private Integer dontDisturb;//免打扰
}
