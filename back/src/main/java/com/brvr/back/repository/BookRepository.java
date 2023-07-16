package com.brvr.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brvr.back.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	 Optional<Book> findByIsbn(String isbn);
}
