package com.mb.mbqx.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mb.mbqx.entity.Users;
import com.mb.mbqx.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * @description: 自定义UserDetailsService
 * @author chenjunliang
 * @date 2022/6/2 17:49
 */
@Service("userDetailsService")
@Configuration
public class MyUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    private final UserService userService;

    public MyUserDetailsService(UserMapper userMapper, UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<Users> wrapper = new QueryWrapper();
        wrapper.eq("username",username);
        Users users = userMapper.selectOne(wrapper);
        if(users == null) {
            throw new BadCredentialsException("用户名不存在！");
        }
        BCryptPasswordEncoder encoder = passwordEncoder();
        //生成用户时候密码一定要是使用new BCryptPasswordEncoder().encode()进行密码加密
        //固定权限放开注释
        //List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("del,ROLE_sale1");
        return new User(users.getUsername(),users.getPassword(),getUserAuthority(users));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * @description: 获取用户权限
     * @param: user
     * @return: java.util.Set<org.springframework.security.core.GrantedAuthority>
     * @author chenjunliang
     * @date: 2022/6/2 17:46
     */
    private Set<GrantedAuthority> getUserAuthority(Users user) {
        // 获取用户权限标识
        Set<String> permsSet = userService.getUserAuthority(user);

        // 封装权限标识
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.addAll(permsSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));

        return authorities;
    }

}
