package com.match.oim.context.domain.message;

import com.match.oim.client.bean.MessageType;

/**
 * @Author zhangchao
 * @Date 2019/5/29 15:34
 * @Version v1.0
 */
public @interface Messager {
    MessageType value();
}
