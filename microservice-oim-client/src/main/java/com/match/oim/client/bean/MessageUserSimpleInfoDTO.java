package com.match.oim.client.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author zhangchao
 * @Date 2019/5/27 17:14
 * @Version v1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageUserSimpleInfoDTO {
    private String id;
    private String nickName;
    private String encodedPrincipal;

    public String getSmallEncodedPrincipal(){
//        return MessageFormat.format("{0}{1}",encodedPrincipal, Constents.IMAGE_STYLE_96);
        return encodedPrincipal;
    }
}
