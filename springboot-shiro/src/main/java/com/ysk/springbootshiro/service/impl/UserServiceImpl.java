package com.ysk.springbootshiro.service.impl;

import com.ysk.springbootshiro.mapper.UserMapper;
import com.ysk.springbootshiro.pojo.User;
import com.ysk.springbootshiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserByName(String userName) {
        return userMapper.queryUserByName(userName);
    }
}
