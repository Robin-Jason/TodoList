package com.classroom.rbac.demo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    User findUserByUsername(String username);
    User findUserByUserId(Integer userid);
}


