package com.todomanagement.app.controller;

import com.todomanagement.app.dto.TodoDto;
import com.todomanagement.app.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    //build post api for add todo
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto){
        TodoDto todoDto1 = todoService.createTodo(todoDto);
        return new ResponseEntity<>(todoDto1, HttpStatus.CREATED);
    }

    //build get api for getTodoBy id;
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable("id") Long id){
        return ResponseEntity.ok(todoService.getTodoById(id));

    }
    // build get All Todo api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodo());
    }
    // update Todo
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodoById(@PathVariable ("id") Long id ,@RequestBody TodoDto todoDto){
        TodoDto updatedTodo = todoService.updateTodo(id,todoDto);
        return new ResponseEntity<>(updatedTodo,HttpStatus.OK);

    }
    // deleted Todo
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoById(@PathVariable Long id){
        todoService.deleteTodoById(id);
        return ResponseEntity.ok("Todo is successfully Deleted");
    }
    // Build complete Todo api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
     @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo (@PathVariable("id") Long id){
        TodoDto todoDto=todoService.completeTodo(id);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);

    }

    // Build in-complete Todo api
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("/{id}/in-complete")
    public ResponseEntity<TodoDto> incompleteTodo (@PathVariable("id") Long id){
        TodoDto todoDto=todoService.incompleteTodo(id);
        return new ResponseEntity<>(todoDto,HttpStatus.OK);

    }



}
