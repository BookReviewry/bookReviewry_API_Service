package com.brvr.back.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brvr.back.entity.Review;
import com.brvr.back.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
	 ArrayList<Optional<Todo>> findAllByEmail(String email);
}
