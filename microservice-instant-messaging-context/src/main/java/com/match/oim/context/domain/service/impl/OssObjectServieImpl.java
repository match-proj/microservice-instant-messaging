package com.match.oim.context.domain.service.impl;

import com.match.common.file.ObjectManagerFactory;
import com.match.common.icon.IconServiceProvider;
import com.match.common.icon.group.GroupIconServiceProvider;
import com.match.common.icon.user.UserIconServiceProvider;
import com.match.oim.context.domain.service.OssObjectServie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Author zhangchao
 * @Date 2019/5/28 16:54
 * @Version v1.0
 */
@Service
public class OssObjectServieImpl implements OssObjectServie {

    @Autowired
    ObjectManagerFactory objectManagerFactory;

    @Resource(type = UserIconServiceProvider.class)
    IconServiceProvider<String> userIconServiceProvider;

    @Resource(type = GroupIconServiceProvider.class)
    IconServiceProvider<List<String>> groupIconServiceProvider;


    @Override
    public String generateUserIcon(String prefix_,String name) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            userIconServiceProvider.generate(name, outputStream);
            String path = objectManagerFactory.upload(new ByteArrayInputStream(outputStream.toByteArray()), getOssImageName(prefix_));
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String generateGroupIcon(String prefix_, List<String> userAvatars) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            groupIconServiceProvider.generate(userAvatars, byteArrayOutputStream);
            String path = objectManagerFactory.upload(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()), getOssImageName(prefix_));
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getOssImageName(String prefix_){
        String prefix = "default" ;
        if(StringUtils.isNotEmpty(prefix_)){
            prefix = prefix_;
        }

        return prefix + "/" + UUID.randomUUID().toString().replace("-","") + ".jpg";
    }
}
