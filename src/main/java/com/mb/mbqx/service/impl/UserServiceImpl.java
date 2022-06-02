package com.mb.mbqx.service.impl;

import com.mb.mbqx.entity.Users;
import com.mb.mbqx.mapper.UserMapper;
import com.mb.mbqx.service.UserService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Set<String> getUserAuthority(Users user) {
        // 系统管理员，拥有最高权限
        List<String> authorityList;
        if(1==user.getSuperadmin()) {
            authorityList = userMapper.getAuthorityList();
        }else{
            authorityList = userMapper.getUserAuthorityList(user.getId());
        }

        // 用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String authority : authorityList){
            if(StringUtils.isNullOrEmpty(authority)){
                continue;
            }
            permsSet.addAll(Arrays.asList(authority.trim().split(",")));
        }

        return permsSet;
    }
}
