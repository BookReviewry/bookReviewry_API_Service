package com.brvr.back.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.brvr.back.entity.Review;

public interface ReviewRepostory extends JpaRepository<Review, Long>{
	 Optional<Review> findByIsbn(String isbn);
	 ArrayList<Optional<Review>> findAllByIsbn(String isbn);
}