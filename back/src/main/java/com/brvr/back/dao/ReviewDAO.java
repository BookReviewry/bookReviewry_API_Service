package com.brvr.back.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.brvr.back.entity.Review;

public interface ReviewDAO {
	boolean createReview(String author, String isbn, String content, String category, Integer eq);
	ArrayList<Optional<Review>> readReview(String isbn);
	Optional<Review> readReviewByReviewCd(String reviewCd);
	ArrayList<Optional<Review>> deleteReview(String isbn, String reviewCd);
	boolean checkIsExist(String isbn, String author);
	boolean updateReview(String reviewCd, String content);
}