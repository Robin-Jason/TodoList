package com.classroom.rbac.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoItemController {

    @Autowired
    private TodoItemService todoItemService;

    @Autowired
    private JwtUtil jwtUtil;

    // 添加事项的POST请求
    @PostMapping("/add")
    public ResponseEntity<String> addTodoItem(@RequestBody TodoItem todoItem, HttpServletRequest request) {
        // 验证用户身份
        String token = request.getHeader("Authorization");
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户未登录或身份验证失败");
        }

        try {
            // 添加待办事项
            todoItemService.addTodoItem(todoItem, token);
            return ResponseEntity.ok("待办事项添加成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加待办事项失败：" + e.getMessage());
        }
    }

    // 获取用户对应的所有事项的GET请求
    @GetMapping("/list")
    public ResponseEntity<?> getAllTodoItems(HttpServletRequest request) {
        // 验证用户身份
        String token = request.getHeader("Authorization");
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户未登录或身份验证失败");
        }

        try {
            // 获取当前登录用户的待办事项列表
            List<TodoItem> todoItems = todoItemService.getAllTodoItems(token);
            return ResponseEntity.ok(todoItems);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取待办事项列表失败：" + e.getMessage());
        }
    }

    // 通过事项id获取对应事项的GET请求
    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Integer id, HttpServletRequest request) {
        // 验证用户身份
        String token = request.getHeader("Authorization");
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        // 获取指定ID的待办事项
        TodoItem todoItem = todoItemService.getTodoItemById(id);
        if (todoItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(todoItem);
        }
    }

    // 通过事项id和token更新事项的PUT请求
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTodoItem(@PathVariable Integer id, @RequestBody TodoItem todoItem, HttpServletRequest request) {
        // 验证用户身份
        String token = request.getHeader("Authorization");
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户未登录或身份验证失败");
        }
        try {
            // 更新待办事项
            // System.out.println("事项id为" + id);   //调试使用
            // System.out.println("更新后的事项为" + todoItem); //调试使用
            todoItem.setItemId(id);
            todoItemService.updateTodoItem(todoItem, token);
            return ResponseEntity.ok("待办事项更新成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新待办事项失败：" + e.getMessage());
        }
    }
}


