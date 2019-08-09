package com.houseWork.security.config;

import com.houseWork.mapper.Jwt.JwtAuthenticationTokenMapper;
import com.houseWork.entity.user.User;
import com.houseWork.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户认证，权限
 * @params
 * @return
 * @date 2019/7/11 13:50
 * @author zjw
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {
    @Autowired
    JwtAuthenticationTokenMapper jwtAuthenticationTokenMapper;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //构建用户信息的逻辑(取数据库/LDAP等用户信息)

        User userinfo = userService.findByname(username);

        SelfUserDetails userInfo = new SelfUserDetails();
        userInfo.setUsername(userinfo.getUsername());
        userInfo.setPassword(new BCryptPasswordEncoder().encode(userinfo.getPassword()));

        Set authoritiesSet = new HashSet();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + userinfo.getRole());
        authoritiesSet.add(authority);
        userInfo.setAuthorities(authoritiesSet);

        return userInfo;
    }
}
