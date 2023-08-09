package com.brvr.back.controller;

import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brvr.back.service.ReviewService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/reviews")
@RestController
public class ReviewController {

	private final ReviewService reviewService;
	
	@GetMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String getReviewByISBN(@RequestParam String isbn) {
		
    	return reviewService.getReview(isbn);
    }
	
	@Transactional
	@PostMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String postReview(@RequestBody Map<String,Object> requestBody) {
		
    	return  reviewService.postReview(requestBody);
    }
	
	@PutMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String updateReview(@RequestBody Map<String,Object> requestBody) {
		
    	return reviewService.updateReview((String)requestBody.get("reviewCd") , (String)requestBody.get("content"));
    }
	
	@DeleteMapping(value="/",  produces="application/json;charset=UTF-8")
    public @ResponseBody String deleteReview(@RequestParam String isbn, @RequestParam String reviewCd) {
		
    	return reviewService.deleteReview(isbn, reviewCd);
    }

}
