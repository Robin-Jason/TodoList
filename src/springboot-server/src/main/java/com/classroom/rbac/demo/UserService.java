package com.classroom.rbac.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void addUser(User user) {
        user.setPassword(getMD5(user.getPassword(),"aaa")); //盐值为aaa
        userMapper.insertUser(user);
    }

    public static String getMD5(String input, String salt) {
        try {
            input = input + salt; // 将密码和盐结合
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //登录
    public boolean loginUser(String username, String password) {
        User user = userMapper.findUserByUsername(username);
        if (user != null) {
            // 验证密码
            String saltedPassword = getMD5(password, "aaa");  //盐值要一样
            return saltedPassword.equals(user.getPassword());
        }
        return false;
    }

    //根据token获取用户信息
    public User getUserInfoByToken(String token) throws Exception {
        // 解析Token获取用户id的字符串，将字符串转化为用户id
        Integer userid = Integer.parseInt(JwtUtil.getUserIdFromToken(token));
        //System.out.println(userid);   //调试使用

        // 根据用户id获取用户信息
        User user = userMapper.findUserByUserId(userid);
        //System.out.println(user);     //调试使用
        if (user == null) {
            throw new Exception("用户不存在");
        }
        return user;
    }

    public User getUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}


