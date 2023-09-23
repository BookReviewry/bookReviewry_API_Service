package com.brvr.back.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.brvr.back.dao.ReviewDAO;
import com.brvr.back.entity.Review;
import com.brvr.back.utils.ResponseWraper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ResponseWraper responseWraper;
	private final ReviewDAO reviewDAO;
	private final NaverBookService naverBookService;
	
	public String getReview(String isbn) {
		Gson gson = new Gson();
		
		ArrayList<Optional<Review>> optionalReviews = reviewDAO.readReview(isbn);
		ArrayList<Map<String, Object>> reviews = new ArrayList<>();
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();

		if(optionalReviews.size() > 0) {
			for (Optional<Review> review : optionalReviews) {
				if(!review.isEmpty()) {
					String author = review.get().getAuthor();
					String content = review.get().getContent();
					String category = review.get().getCategory();
					String createAt = review.get().getCreatedAt().toString();
					String id = review.get().getId().toString();
					
					Map<String, Object> reviewMap = new HashMap();
					
					reviewMap.put("id", id);
					reviewMap.put("author", author);
					reviewMap.put("content", content);
					reviewMap.put("category", category);
					reviewMap.put("createAt", createAt);
					
					reviews.add(reviewMap);
				}
			}
		}
		
		map.put("reviews", reviews);	
		
		if(reviews.size() > 0) {
			result = responseWraper.getProcessedResponse(map, HttpStatus.OK);
		}else {
			result = responseWraper.getProcessedResponse(null, HttpStatus.NO_CONTENT);
		}
		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
	
	public String postReview(Map<String,Object> requestBody){
		
		String jsonData = "";
		Map<String,Object> result;
		Gson gson = new Gson();
		
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	// 계정이 없으면 401 리턴
    	if(email.isBlank() || email == "anonymousUser") {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.UNAUTHORIZED);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
    	}
    	
    	
		String author = email;
		String isbn = (String) requestBody.get("isbn");
		String content = (String) requestBody.get("content");
		String category = (String) requestBody.get("category");
		Integer eq = (Integer) requestBody.get("eq");
		
    	// 이미 해당 도서에 댓글이 있을 경우 리턴
		boolean isExist = reviewDAO.checkIsExist(isbn, author);
		if(isExist) {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.CONFLICT);
    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
		}
		
		boolean resultCode = reviewDAO.createReview(author, isbn, content, category, eq);
		
		if(resultCode) {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.OK);
		}else {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.CONFLICT);
		}
		
		jsonData = gson.toJson(result).toString();
		
		return jsonData;
	}
	
	public String updateReview(String reviewCd, String content) {
		
		System.out.println("deleteReview invoked");
		String jsonData = "";
		Map<String,Object> result;
		Gson gson = new Gson();
		
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	// 계정이 없으면 401 리턴
    	if(email.isBlank() || email == "anonymousUser") {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.UNAUTHORIZED);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
    	}
    	Optional<Review> review = reviewDAO.readReviewByReviewCd(reviewCd);
    	
    	if(review.get().getAuthor().equals(email)) { // 작성자와 아이디가 같으면 삭제	    		
    		reviewDAO.updateReview(reviewCd, content);
    		result = responseWraper.getProcessedResponse(null, HttpStatus.OK);
    	}else {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.FORBIDDEN);
    		jsonData = gson.toJson(result).toString();
    	}
		
		return jsonData;
	}
	
	public String deleteReview(String isbn, String reviewCd) {
		
		System.out.println("deleteReview invoked");
		String jsonData = "";
		Map<String,Object> result;
		Gson gson = new Gson();
		
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	// 계정이 없으면 401 리턴
    	if(email.isBlank() || email == "anonymousUser") {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.UNAUTHORIZED);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
    	}
    	Optional<Review> review = reviewDAO.readReviewByReviewCd(reviewCd);
    	
    	if(review.get().getAuthor().equals(email)) { // 작성자와 아이디가 같으면 삭제	    		
    		reviewDAO.deleteReview(isbn, reviewCd);
    		result = responseWraper.getProcessedResponse(null, HttpStatus.OK);
    	}else {
    		result = responseWraper.getProcessedResponse(null, HttpStatus.FORBIDDEN);
    		jsonData = gson.toJson(result).toString();
    	}
		
		return jsonData;
	}
	
	public String getReview(String email, String isbn) {
		Gson gson = new Gson();
		
		Optional<Review> review = reviewDAO.readReview(email, isbn);
		ArrayList<Map<String, Object>> reviews = new ArrayList<>();
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();

		if(!review.isEmpty()) {
			String author = review.get().getAuthor();
			String content = review.get().getContent();
			String category = review.get().getCategory();
			String createAt = review.get().getCreatedAt().toString();
			String id = review.get().getId().toString();
			
			Map<String, Object> reviewMap = new HashMap();
			
			reviewMap.put("id", id);
			reviewMap.put("author", author);
			reviewMap.put("content", content);
			reviewMap.put("category", category);
			reviewMap.put("createAt", createAt);
			
			reviews.add(reviewMap);
		}
		
		map.put("reviews", reviews);	
		
		if(reviews.size() > 0) {
			result = responseWraper.getProcessedResponse(map, HttpStatus.OK);
		}else {
			result = responseWraper.getProcessedResponse(null, HttpStatus.NO_CONTENT);
		}
		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
	
	public Integer getReviewCount(String email) {
		ArrayList<Optional<Review>> optionalReviews = reviewDAO.readReviewByEmail(email);
		return optionalReviews.size();
	}
	
	public String getBookCase(String email) {
	Gson gson = new Gson();
		
		ArrayList<Optional<Review>> optionalReviews = reviewDAO.readReviewByEmail(email);
		ArrayList<Map<String, Object>> reviews = new ArrayList<>();
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();

		if(optionalReviews.size() > 0) {
			for (Optional<Review> review : optionalReviews) {
				if(!review.isEmpty()) {
					String author = review.get().getAuthor();
					String content = review.get().getContent();
					String category = review.get().getCategory();
					String createAt = review.get().getCreatedAt().toString();
					String isbn = review.get().getIsbn().toString();
					String id = review.get().getId().toString();
					String eq = review.get().getEq().toString();
					
					Map<String, Object> reviewMap = new HashMap();
					
					reviewMap.put("id", id);
					reviewMap.put("isbn", isbn);
					reviewMap.put("author", author);
					reviewMap.put("content", content);
					reviewMap.put("category", category);
					reviewMap.put("createAt", createAt);
					reviewMap.put("eq", eq);
					reviewMap.put("bookDetail", naverBookService.getBookDetailMap(isbn));
					
					reviews.add(reviewMap);
				}
			}
		}
		
		map.put("reviews", reviews);	
		
		if(reviews.size() > 0) {
			result = responseWraper.getProcessedResponse(map, HttpStatus.OK);
		}else {
			result = responseWraper.getProcessedResponse(null, HttpStatus.NO_CONTENT);
		}
		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
	
	
}