package com.houseWork.controller.front;

import com.houseWork.entity.response.ResponseResult;
import com.houseWork.service.test.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api(tags = "测试用")
public class testController {

    @Autowired
    TestService testService;


    @PostMapping("/test")
    @ApiOperation(value = "测试",notes = "测试")
    public ResponseEntity selectAll(@RequestParam(value = "id", required = false, defaultValue = "") int id){
        return new ResponseEntity(ResponseResult.successResponse(testService.select(id)), HttpStatus.OK);
    }

    @PostMapping("/test11")
    @ApiOperation(value = "测试1", notes = "测试1")
    public ResponseEntity selectRole(@RequestParam(value = "rid", required = false, defaultValue = "") int rid){
        return new ResponseEntity(ResponseResult.successResponse(testService.selectRole(rid)),HttpStatus.OK);
    }
}
