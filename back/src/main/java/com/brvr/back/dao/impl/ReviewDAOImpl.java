package com.brvr.back.dao.impl;

import java.util.ArrayList;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brvr.back.dao.ReviewDAO;
import com.brvr.back.entity.Review;
import com.brvr.back.entity.User;
import com.brvr.back.repository.ReviewRepostory;
import com.brvr.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewDAOImpl implements ReviewDAO {
	private final ReviewRepostory reviewRepostory;
	
	//CREATE

	@Override
	@Transactional
	public boolean createReview(String author, String isbn, String content, String category, Integer eq) {
		
		try {
			reviewRepostory.save(
					Review.builder()
					.author(author)
					.isbn(isbn)
					.content(content)
					.category(category)
					.eq(eq).build());
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Transactional
	public boolean updateReview(String reviewCd, String content) {
		
		Optional<Review> review = reviewRepostory.findById(Long.parseLong(reviewCd));
		
		
    	if(review.isEmpty()) {
    		return false;
    	}else {
    		reviewRepostory.save(review.get().updateContent(content));
    		return true;
    	}
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

	@Override
	public boolean checkIsExist(String isbn, String author) {
		Optional<Review> review = reviewRepostory.findAllByIsbnAndAuthor(isbn, author);
		if(!review.isEmpty()) {
			return true;
		}
		return false;
	}


	@Override
	public ArrayList<Optional<Review>> deleteReview(String isbn, String reviewCd) {
		reviewRepostory.deleteById(Long.parseLong(reviewCd));
		return null;
	}


	@Override
	public Optional<Review> readReviewByReviewCd(String reviewCd) {
		Optional<Review> review = reviewRepostory.findById(Long.parseLong(reviewCd));
		return review;
	}

}
