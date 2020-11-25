package com.ysk.springbootshiro.mapper;

import com.ysk.springbootshiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    User queryUserByName(String userName);
}
