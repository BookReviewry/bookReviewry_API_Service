package com.brvr.back.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.brvr.back.dao.ReviewDAO;
import com.brvr.back.dao.TodoDAO;
import com.brvr.back.entity.Review;
import com.brvr.back.entity.Todo;
import com.brvr.back.utils.ResponseWraper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService {
	
	private final TodoDAO todoDAO;
	private final ResponseWraper responseWraper;
	
	public String getTodo() {
		
		Gson gson = new Gson();
		
		ArrayList<Optional<Todo>> optionalTodos = todoDAO.readTodo();
		HttpStatus status = null;
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();
		List<Map<String,Object>> arr = new ArrayList<>();
		
		if(optionalTodos.size() > 0) {
			for (Optional<Todo> todo : optionalTodos) {
				Map<String, Object> currentTodo = new HashMap<>();
				
				currentTodo.put("isbn", todo.get().getIsbn());
				currentTodo.put("id", todo.get().getId());
				currentTodo.put("email", todo.get().getEmail());
				
				arr.add(currentTodo);
			}
			map.put("todoList", arr);
			status = HttpStatus.OK;
			result = responseWraper.getProcessedResponse(map, status);
		
		}else {
			status = HttpStatus.NO_CONTENT;
			result = responseWraper.getProcessedResponse(null, status);
		}
		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
	
	public String createTodo(String isbn) {
		
		Gson gson = new Gson();
		

		boolean resultFlag = todoDAO.createTodo(isbn);
		HttpStatus status = HttpStatus.NO_CONTENT;
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();
		
		if(resultFlag) {
			status = HttpStatus.OK;
			result = responseWraper.getProcessedResponse(map, status);
		}

		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
	
	public String deleteTodo(String todoId) {
		Gson gson = new Gson();
		
		boolean resultFlag = todoDAO.deleteTodo(todoId);
		HttpStatus status = HttpStatus.NO_CONTENT;
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();
		
		if(resultFlag) {
			status = HttpStatus.OK;
			result = responseWraper.getProcessedResponse(map, status);
		}

		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
	
	public Integer getTodoCount(String email) {
		ArrayList<Optional<Todo>> optionalTodos = todoDAO.readTodo(email);
		return optionalTodos.size();
	}
}
