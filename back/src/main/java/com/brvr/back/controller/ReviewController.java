package com.brvr.back.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brvr.back.dao.ReviewDAO;
import com.brvr.back.entity.Review;
import com.brvr.back.entity.User;
import com.brvr.back.utils.ResponseWraper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController {
	
	private final ReviewDAO reviewDAO;
	private final ResponseWraper responseWraper;
	
	@GetMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String getReviewByISBN(@RequestParam String isbn) {
		/**
		 * isbn 으로 검색
		 */
		Gson gson = new Gson();
		
		// 나중에 서비스로 빼두기 **
		ArrayList<Optional<Review>> optionalReviews = reviewDAO.readReview(isbn);
		ArrayList<Map<String, Object>> reviews = new ArrayList<>();
		Map<String,Object> result = null;
		Map<String, Object> map = new HashMap<>();
		/**
		 *     	Optional<User> user = userRepository.findByEmail(email);
    	
    	if(!user.isEmpty()) {
    		map.put("name", user.get().getName());
    		map.put("email", user.get().getEmail());
    		map.put("profile", user.get().getProfile());
    	}
		 */
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
		System.out.println(">> getReviewByISBN invoked  # 3");
		
		
		
		if(reviews.size() > 0) {
			result = responseWraper.getProcessedResponse(map, 200);
		}else {
			result = responseWraper.getProcessedResponse(null, 401);
		}
		String jsonData = gson.toJson(result).toString();
    	return jsonData;
    }
	
	@PostMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String postReview(@RequestBody Map<String,Object> review) {
		/**
		 * isbn 으로 검색
		 */
		
		System.out.println(review.toString());
		System.out.println(">> postReview invoked");
		// 나중에 서비스로 빼두기 **
		String jsonData = "";
		
		Gson gson = new Gson();
		
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	if(email.isBlank() || email == "anonymousUser") {
    		Map<String,Object> result = responseWraper.getProcessedResponse(null, 401);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
    	}
    	
		String author = email;
		String isbn = (String) review.get("isbn");
		String content = (String) review.get("content");
		String category = (String) review.get("category");
		
		boolean resultCode = reviewDAO.createReview(author, isbn, content, category);
		
		if(resultCode) {
    		Map<String,Object> result = responseWraper.getProcessedResponse(null, 200);
    		jsonData = gson.toJson(result).toString();
		}else {
    		Map<String,Object> result = responseWraper.getProcessedResponse(null, 500);
    		jsonData = gson.toJson(result).toString();
		}
		
    	return jsonData;
    }
	
	//TODO
	@DeleteMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String deleteReview(@RequestParam String isbn, @RequestParam String id) {
		/**
		 * isbn 으로 검색
		 */
		Gson gson = new Gson();
		String jsonData = "";
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
    	if(email.isBlank() || email == "anonymousUser") {
    		Map<String,Object> result = responseWraper.getProcessedResponse(null, 401);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
    	}
		
    	// email, id 값 일치하는 것 찾아서 삭제하기
    	
    	
		Map<String,Object> result = responseWraper.getProcessedResponse(null, 200);
		jsonData = gson.toJson(result).toString();

		
    	return jsonData;
    }

}
