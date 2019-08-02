package com.match.oim.context.domain.message;

import com.match.oim.client.bean.MessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * 好友申请消息
 *
 * @Author zhangchao
 * @Date 2019/5/28 17:16
 * @Version v1.0
 */
@Slf4j
@Messager(MessageType.FRIENDS_APPLY)
public class FriendsApplyMessage {

    private String applyMessageUserId;
    private String applyNickName;
    private String applyEncodedPrincipal;
    private String status;//
    private String reject;

    public String getApplyMessageUserId() {
        return applyMessageUserId;
    }

    public void setApplyMessageUserId(String applyMessageUserId) {
        this.applyMessageUserId = applyMessageUserId;
    }

    public String getApplyNickName() {
        return applyNickName;
    }

    public void setApplyNickName(String applyNickName) {
        this.applyNickName = applyNickName;
    }

    public String getApplyEncodedPrincipal() {
        return applyEncodedPrincipal;
    }

    public void setApplyEncodedPrincipal(String applyEncodedPrincipal) {
        this.applyEncodedPrincipal = applyEncodedPrincipal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }
}
