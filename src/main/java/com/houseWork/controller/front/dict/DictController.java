package com.houseWork.controller.front.dict;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.houseWork.entity.dict.Dict;
import com.houseWork.entity.response.ResponseResult;
import com.houseWork.service.dict.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/dict")
@Api(tags = "字典", description = "字典")
public class DictController {

    @Autowired
    private DictService dictService;


    @PostMapping("/getAll")
    @ApiOperation(value = "所有字典", notes = "所有字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页显示数量", required = false, dataType = "int")
    })
    public ResponseEntity dictList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Map map = new HashMap();
        PageHelper.startPage(pageNum, pageSize);
        List< Dict > list = dictService.selectByMap(map);
        PageInfo< Dict > pageInfo = new PageInfo<>(list);
        return new ResponseEntity(ResponseResult.successResponse(pageInfo), HttpStatus.OK);
    }


    @PostMapping("/insertDict")
    @ApiOperation(value = "新增字典", notes = "新增字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "k", value = "k", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderby", value = "orderby", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "type", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "v", value = "v", dataType = "String")
    })
    public ResponseEntity insertDict(
                                     @RequestParam String k,
                                     @RequestParam String orderby,
                                     @RequestParam String type,
                                     @RequestParam String v){
        dictService.insertDict(Dict.builder()
                .k(k)
                .orderby(orderby)
                .type(type)
                .v(v)
                .build());
        return new ResponseEntity(ResponseResult.successResponse(),HttpStatus.OK);
    }

    @PostMapping("/updateDict")
    @ApiOperation(value = "修改字典", notes = "修改字典")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "k", value = "k", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "orderby", value = "orderby", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "type", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "v", value = "v", dataType = "String")
    })
    public ResponseEntity updateDict(@RequestParam Integer id,
                                     @RequestParam String k,
                                     @RequestParam String orderby,
                                     @RequestParam String type,
                                     @RequestParam String v){
        Map map = new HashMap();
        map.put("id",id);
        map.put("k",k);
        map.put("orderby",orderby);
        map.put("type",type);
        map.put("v",v);
        dictService.updateDict(map);
        return new ResponseEntity(ResponseResult.successResponse(),HttpStatus.OK);
    }
}