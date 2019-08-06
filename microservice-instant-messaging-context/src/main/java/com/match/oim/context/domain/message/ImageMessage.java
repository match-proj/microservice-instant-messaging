package com.match.oim.context.domain.message;

import com.match.oim.client.bean.MessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhangchao
 * @Date 2019/5/28 17:16
 * @Version v1.0
 */
@Slf4j
@Messager(MessageType.IMAGE)
public class ImageMessage {

    private String small;
    private String source;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
