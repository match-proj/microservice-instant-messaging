package com.match.oim.client.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhangchao
 * @Date 2019/8/2 17:53
 * @Version v1.0
 */
@Getter
@Setter
public class ConversationUserDTO {

    private String id;

    private String messageUserId;//会话用户

    private String conversationId;
    private String backgroudPath;//地址

    private Integer top;//置顶

    private Integer dontDisturb;//免打扰
}
