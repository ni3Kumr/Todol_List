package com.todomanagement.app.services;

import com.todomanagement.app.dto.TodoDto;
import com.todomanagement.app.entity.Todo;
import com.todomanagement.app.exception.ResourceNotFoundException;
import com.todomanagement.app.repository.TodoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ModelMapper modelMapper;

    // add todo in the db
    public TodoDto createTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    // get todo by id
    public TodoDto getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo", "id", id));
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;
    }

    // get all todo
    public List<TodoDto> getAllTodo() {
        List<Todo> todoList = todoRepository.findAll();
        List<TodoDto> todoDtoList = todoList.stream().map((list)
                -> modelMapper.map(list, TodoDto.class)).collect(Collectors.toList());
        return todoDtoList;
    }

    // update Todo

    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        Todo saveTodo = todoRepository.save(todo);
        TodoDto todoDto1 = modelMapper.map(saveTodo, TodoDto.class);
        return todoDto1;

    }
    public void deleteTodoById(Long id){
        Todo savedTodo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id));
        todoRepository.delete(savedTodo);
    }

    // complete Todo
    public TodoDto completeTodo(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id));;
                todo.setCompleted(Boolean.TRUE);
                Todo savedTodo = todoRepository.save(todo);
                TodoDto todoDto = modelMapper.map(savedTodo,TodoDto.class);
                return todoDto;
    }
    public TodoDto incompleteTodo(Long id){
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id));;
        todo.setCompleted(Boolean.FALSE);
        Todo savedTodo = todoRepository.save(todo);
        TodoDto todoDto = modelMapper.map(savedTodo,TodoDto.class);
        return todoDto;
    }


}
