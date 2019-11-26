package com.houseWork.service.dict;

import com.houseWork.entity.dict.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {

    List<Dict> selectByMap(Map map);
    void insertDict(Dict dict);

    void updateDict(Map map);
    /**
     * 删除字典
     * @params [map]
     * @return void
     * @date 2019/7/24 14:34
     */
    void delete(Dict build);
}
