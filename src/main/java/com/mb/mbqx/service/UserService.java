package com.mb.mbqx.service;

import com.mb.mbqx.entity.Users;

import java.util.Set;

public interface UserService {

    Set<String> getUserAuthority(Users user);
}
