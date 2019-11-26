package com.houseWork.service.dict.Impl;

import com.houseWork.entity.dict.Dict;
import com.houseWork.mapper.dict.DictDao;
import com.houseWork.service.dict.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("dictService")
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;
    @Override
    public List< Dict > selectByMap(Map map) { return dictDao.selectByMap(map); }

    @Override
    public void insertDict(Dict dict) {
        dictDao.insert(dict);
    }
    @Override
    public void updateDict(Map map) { dictDao.update(map);}
    @Override
    public void delete(Dict build) { dictDao.deleteById(build); }

}
