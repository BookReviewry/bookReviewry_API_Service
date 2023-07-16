package com.brvr.back.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.brvr.back.entity.Review;

public interface ReviewDAO {
	 abstract boolean createReview(String author, String isbn, String content, String category);
	 abstract ArrayList<Optional<Review>> readReview(String isbn);
	 abstract boolean checkIsExist(String isbn, String author);
}