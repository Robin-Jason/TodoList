package com.classroom.rbac.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TodoItemService {

    @Autowired
    private TodoItemMapper todoItemMapper;

    //添加事项
    public void addTodoItem(TodoItem todoItem, String token) throws Exception {
        // 解析 token 获取用户 ID
        String userIdString = JwtUtil.getUserIdFromToken(token);
        int userId = Integer.parseInt(userIdString);
        // 设置用户 ID
        todoItem.setUserId(userId);
        // 调用 Mapper 方法添加待办事项
        todoItemMapper.addTodoItem(todoItem);
    }

    // 获取用户对应的所有事项
    public List<TodoItem> getAllTodoItems(String token) throws Exception {
        // 解析 token 获取用户 ID
        String userIdString = JwtUtil.getUserIdFromToken(token);
        int userId = Integer.parseInt(userIdString);

        // 根据用户 ID 获取该用户的待办事项列表
        List<TodoItem> todoItems = todoItemMapper.getTodoItemsByUserID(userId);

        // 对待办事项列表中的日期进行处理
        for (TodoItem item : todoItems) {
            Date creationTime = item.getCreationTime();
            Date completionTime = item.getCompletionTime();

            // 如果日期为 null，则设置为 null
            if (creationTime != null) {
                item.setCreationTime(new Date(creationTime.getTime()));
            }
            if (completionTime != null) {
                item.setCompletionTime(new Date(completionTime.getTime()));
            }
        }

        return todoItems;
    }

    public TodoItem getTodoItemById(Integer id) {
        return todoItemMapper.getTodoItemById(id);
    }

    // 更新事项
    public void updateTodoItem(TodoItem todoItem, String token) throws Exception {
        // 解析 token 获取用户 ID
        String userIdString = JwtUtil.getUserIdFromToken(token);
        int userId = Integer.parseInt(userIdString);
        // 设置用户 ID
        todoItem.setUserId(userId);
        // 调用 Mapper 方法更新待办事项
        todoItemMapper.updateTodoItem(todoItem);
    }
}


