package com.classroom.rbac.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("用户添加成功");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginUser) {
        Map<String, Object> result = new HashMap<>();
        boolean success = userService.loginUser(loginUser.getUsername(), loginUser.getPassword());

        if (success) {
            // 从数据库中获取用户信息，以获取用户的 ID
            User user = userService.getUserByUsername(loginUser.getUsername());
            Integer userId = user.getId();
            // System.out.println(userId); //调试使用

            // 生成 token
            String token = JwtUtil.generateToken(userId);

            result.put("success", true);
            result.put("message", "登录成功");
            result.put("token", token);
            return ResponseEntity.ok(result);
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        try {
            User userInfo = userService.getUserInfoByToken(token);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
