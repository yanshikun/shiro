package com.ysk.springbootshiro.service;

import com.ysk.springbootshiro.pojo.User;

public interface UserService {
    User queryUserByName(String userName);
}
