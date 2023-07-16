package com.brvr.back.dao.impl;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brvr.back.dao.ReviewDAO;
import com.brvr.back.entity.Review;
import com.brvr.back.repository.ReviewRepostory;
import com.brvr.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewDAOImpl implements ReviewDAO {
	private final ReviewRepostory reviewRepostory;
	
	//CREATE
	@Transactional
	public boolean createReview(String author, String isbn, String content, String category) {
		
		try {
			reviewRepostory.save(
					Review.builder()
					.author(author)
					.isbn(isbn)
					.content(content)
					.category(category).build());
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	@Transactional
	public ArrayList<Optional<Review>> readReview(String isbn) {
		ArrayList<Optional<Review>> review = reviewRepostory.findAllByIsbn(isbn);
		return review;
	}
	
	//UPDATE
	@Transactional
	public Optional<Review> updateReview(String isbn) {
		Optional<Review> review = reviewRepostory.findByIsbn(isbn);
		return review;
	}
	
	//DELETE
	@Transactional
	public Optional<Review> deleteReview(String isbn) {
		Optional<Review> review = reviewRepostory.findByIsbn(isbn);
		return review;
	}

	@Override
	public boolean checkIsExist(String isbn, String author) {
		Optional<Review> review = reviewRepostory.findAllByIsbnAndAuthor(isbn, author);
		if(!review.isEmpty()) {
			return true;
		}
		return false;
	}
}
