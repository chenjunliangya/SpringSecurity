package com.mb.mbqx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mb.mbqx.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<Users> {

    /**
     * 查询用户权限列表
     * @param userId  用户ID
     */
    List<String> getUserAuthorityList(@Param("userId") Integer userId);

    /**
     * 查询所有权限列表
     */
    List<String> getAuthorityList();
}
