package com.brvr.back.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brvr.back.service.TodoService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoController {
	public static final Logger logger = LogManager.getLogger(TodoController.class);
	private final TodoService todoService;
	
	@GetMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String getTodo() {
    	return todoService.getTodo();
    }
	
	@PostMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String addTodo(@RequestParam String isbn) {
    	return todoService.createTodo(isbn);
    }
	
	@DeleteMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String deleteTodo(@RequestParam String todoId) {
		logger.info("del: " + todoId);
		return todoService.deleteTodo(todoId);
    }

}

