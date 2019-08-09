package com.houseWork.controller.front.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.houseWork.entity.response.ResponseResult;
import com.houseWork.entity.user.User;
import com.houseWork.entity.user.UserInfo;
import com.houseWork.security.bean.AuthenticationInfo;
import com.houseWork.security.bean.AuthorizationUser;
import com.houseWork.security.config.SelfUserDetailsService;
import com.houseWork.service.user.UserService;
import com.houseWork.service.weixin.WeixinAppService;
import com.houseWork.service.weixin.domin.WeixinGeneralResult;
import com.houseWork.utils.JwtTokenUtil;
import com.jpay.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Api(tags = "用户接口", description = "用户接口")
public class UserController {

    @Autowired
    private SelfUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @PostMapping("/userList")
    @ApiOperation(value = "查找用户列表",notes = "查找用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "名字", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = false, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页显示数量", required = false, dataType = "int")
    })
    public ResponseEntity userList(@RequestParam(required = false) Integer id,
                                   @RequestParam(required = false) String username,
                                   @RequestParam(defaultValue = "0") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize){
        Map map = new HashMap();
        PageHelper.startPage(pageNum,pageSize);
        map.put("id",id);
        map.put("username",username);
        List<User> list = userService.selectByMap(map);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return new ResponseEntity(ResponseResult.successResponse(pageInfo),HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "openId", value = "openId", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "telephone", value = "电话", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "role", value = "用户角色", dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "image", value = "用户头像", dataType = "String")
    })
    public ResponseEntity updateUser(@RequestParam Integer id,
                                     @RequestParam String username,
                                     @RequestParam String openId,
                                     @RequestParam String password,
                                     @RequestParam String telephone,
                                     @RequestParam String role,
                                     @RequestParam String image){
        Map map = new HashMap();
        map.put("id",id);
        map.put("username",username);
        map.put("openId",openId);
        map.put("password",password);
        map.put("telephone",telephone);
        map.put("role",role);
        map.put("image",image);
        userService.updateUser(map);
        return new ResponseEntity(ResponseResult.successResponse(),HttpStatus.OK);
    }

    @PostMapping(value = "/jwtLogin")
    @ApiOperation(value = "登录授权", notes = "登录授权")
//    @Cacheable(value = "proUserLogin", key = "#authorizationUser.getUsername()")
    public ResponseEntity login(@Validated @RequestBody AuthorizationUser authorizationUser) {
        //小程序登录
        if(!StringUtil.isEmpty(authorizationUser.getPlatCode())){

            WeixinGeneralResult<UserInfo> result;
            Map<String,String> platUserInfoMap = authorizationUser.getPlatUserInfoMap();
            if((platUserInfoMap != null)&&(platUserInfoMap.isEmpty())) {
                if(StringUtils.isBlank(platUserInfoMap.get("encryptedData")) || StringUtils.isBlank(platUserInfoMap.get("iv"))){
                    return new ResponseEntity("登录异常，缺少必要参数",HttpStatus.PRECONDITION_FAILED);
                }
                result = WeixinAppService.getUserInfo(authorizationUser.getPlatCode(), authorizationUser.getPlatUserInfoMap());
            }else {
                result = null;
            }
            UserInfo userInfo = result.getDataResult();
            userService.addUser(User.builder()
                    .image(userInfo.getHeadimgurl())
                    .openId(userInfo.getOpenId())
                    .username(userInfo.getNickName())
                    .sex(userInfo.getSex())
                    .build());

            // 生成令牌
            final String token = JwtTokenUtil.generateToken(userInfo.getOpenId(), "_secret");

            // 返回 token
            return new ResponseEntity(ResponseResult.successResponse(new AuthenticationInfo(token,userInfo)),HttpStatus.OK);
        }

        final UserDetails jwtUser  = userDetailsService.loadUserByUsername(authorizationUser.getUsername());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(authorizationUser.getPassword(),jwtUser.getPassword())){
            throw new AccountExpiredException("密码错误");
        }

        // 生成令牌
        final String token = JwtTokenUtil.generateToken(jwtUser.getUsername(), "_secret");

        // 返回 token
        return new ResponseEntity(ResponseResult.successResponse(new AuthenticationInfo(token,jwtUser)),HttpStatus.OK);
    }

    @PostMapping("/deciphering")
    @ApiOperation(value = "解密并且获取用户手机号码", notes = "解密并且获取用户手机号码")
    @ApiImplicitParams(
            @ApiImplicitParam(paramType = "query", name = "role", value = "用户角色", dataType = "String")
    )
    public ResponseEntity deciphering(){
        return new ResponseEntity(ResponseResult.successResponse(),HttpStatus.OK);
    }
}
