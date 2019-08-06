package com.match.oim.context.domain.service;

import java.util.List;

/**
 * @Author zhangchao
 * @Date 2019/5/28 16:53
 * @Version v1.0
 */
public interface OssObjectServie {

    String generateUserIcon(String prefix_, String name);

    String generateGroupIcon(String prefix_, List<String> userAvatars);

}
