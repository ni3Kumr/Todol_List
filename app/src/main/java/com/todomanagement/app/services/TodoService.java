package com.todomanagement.app.services;

import com.todomanagement.app.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto createTodo(TodoDto todoDto);
    TodoDto getTodoById(Long id);
    List<TodoDto> getAllTodo();
    TodoDto updateTodo(Long id,TodoDto todoDto);
    void deleteTodoById(Long id);
    TodoDto completeTodo(Long id);
    TodoDto incompleteTodo(Long id);

}
