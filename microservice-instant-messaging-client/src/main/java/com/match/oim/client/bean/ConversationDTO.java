package com.match.oim.client.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/8/2 17:52
 * @Version v1.0
 */
@Getter
@Setter
public class ConversationDTO {
    private String id;
    private ConversationType type;
    private String name;//名称  单聊的话需要查询 动态查询
    private String icon;//图标  单聊的话需要查询 动态查询
    private String tags;//标签
    private Date createTime;//创建时间
    private Date lastUpdateTime;//最后更新时间
    private String messageUserId;//会话拥有者
    private List<ConversationUserDTO> conversationUserList;
}
