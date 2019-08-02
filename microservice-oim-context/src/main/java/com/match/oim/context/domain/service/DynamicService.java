package com.match.oim.context.domain.service;

import com.match.common.PageResult;
import com.match.oim.client.bean.DynamicAddDTO;
import com.match.oim.client.bean.DynamicDTO;

import java.util.List;

/**
 * Created by jagua on 2019/5/27.
 */
public interface DynamicService {

    void publishDynamic(String peopleId, DynamicAddDTO dynamic);

    PageResult<DynamicDTO> list(Integer page, Integer size, List<String> peopleId);

    PageResult<DynamicDTO> listSelf(Integer page, Integer size, String peopleId);

    PageResult<DynamicDTO> listFriend(Integer page, Integer size, String peopleId);
}
