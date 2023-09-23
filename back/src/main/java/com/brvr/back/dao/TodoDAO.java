package com.brvr.back.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.brvr.back.entity.Review;
import com.brvr.back.entity.Todo;

public interface TodoDAO {
	boolean createTodo(String isbn);
	ArrayList<Optional<Todo>> readTodo();
	boolean deleteTodo(String isbn);
	ArrayList<Optional<Todo>> readTodo(String email);
}
