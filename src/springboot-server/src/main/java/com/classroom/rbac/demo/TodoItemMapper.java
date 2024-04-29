package com.classroom.rbac.demo;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TodoItemMapper {
    void addTodoItem(TodoItem todoItem);
    TodoItem getTodoItemById(Integer id);
    void updateTodoItem(TodoItem todoItem);
    List<TodoItem> getTodoItemsByUserID(Integer userId);
}
