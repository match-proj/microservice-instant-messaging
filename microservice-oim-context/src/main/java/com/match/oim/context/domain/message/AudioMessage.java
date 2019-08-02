package com.match.oim.context.domain.message;

import com.match.oim.client.bean.MessageType;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author zhangchao
 * @Date 2019/5/28 17:16
 * @Version v1.0
 */
@Slf4j
@Messager(MessageType.AUDIO)
public class AudioMessage {

    private String url;
    private Integer duration;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
