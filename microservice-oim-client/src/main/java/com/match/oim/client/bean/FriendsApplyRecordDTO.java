package com.match.oim.client.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @Author zhangchao
 * @Date 2019/6/13 12:14
 * @Version v1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class FriendsApplyRecordDTO {

    private String id;
    private String applyMessageUserId;
    private String applyMessageNickName;
    private String applyMessageEncodedPrincipal;
    private String messageUserId;
    private Date applyTime;
    private Date handlerTime;
    private String status;

}
