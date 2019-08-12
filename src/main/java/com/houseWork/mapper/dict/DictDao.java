package com.houseWork.mapper.dict;

import com.houseWork.entity.dict.Dict;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 用户 mapper
 * </pre>
 *
 */
@org.apache.ibatis.annotations.Mapper
@Repository

public interface DictDao extends Mapper<Dict>, MySqlMapper<Dict> {
    List<Dict> selectByMap(Map map);
    int insert(Dict dict);
}
