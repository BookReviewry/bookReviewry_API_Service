package com.brvr.back.dao.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.brvr.back.dao.TodoDAO;
import com.brvr.back.entity.Todo;
import com.brvr.back.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoDAOImpl implements TodoDAO {
	
	private final TodoRepository todoRepository;
	
	@Override
	public boolean createTodo(String isbn) {
		System.out.println(">>createTodo");
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		try {
			todoRepository.save(
					Todo.builder()
					.email(email)
					.isbn(isbn).build());
		} catch (Exception e) {
			return false;
		}
		
		return true;
    	
	}

	@Override
	public ArrayList<Optional<Todo>> readTodo() {
		// TODO Auto-generated method stub
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		ArrayList<Optional<Todo>> todolist = todoRepository.findAllByEmail(email);
		
		return todolist;
	}
	
	@Override
	public ArrayList<Optional<Todo>> readTodo(String email) {
		// TODO Auto-generated method stub
		ArrayList<Optional<Todo>> todolist = todoRepository.findAllByEmail(email);
		
		return todolist;
	}

	@Override
	public boolean deleteTodo(String todoId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Todo> todo = todoRepository.findById(Long.parseLong(todoId));
		
		if(todo.get().getEmail().equals(email)) {			
			todoRepository.deleteById(Long.parseLong(todoId));
			return true;
		}

		return false;
	}

}
