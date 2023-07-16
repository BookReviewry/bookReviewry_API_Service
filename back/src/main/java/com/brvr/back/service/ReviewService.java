package com.brvr.back.service;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.brvr.back.dao.ReviewDAO;
import com.brvr.back.utils.ResponseWraper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ResponseWraper responseWraper;
	private final ReviewDAO reviewDAO;
	
	public String postReview(Map<String,Object> requestBody){
		
		String jsonData = "";
		Map<String,Object> result;
		Gson gson = new Gson();
		
    	String email = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	// 계정이 없으면 401 리턴
    	if(email.isBlank() || email == "anonymousUser") {
    		result = responseWraper.getProcessedResponse(null, 401);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
    	}
    	
    	
		String author = email;
		String isbn = (String) requestBody.get("isbn");
		String content = (String) requestBody.get("content");
		String category = (String) requestBody.get("category");
		
    	// 이미 해당 도서에 댓글이 있을 경우 리턴
		boolean isExist = reviewDAO.checkIsExist(isbn, author);
		if(isExist) {
    		result = responseWraper.getProcessedResponse(null, 401);

    		jsonData = gson.toJson(result).toString();
    		
    		return jsonData;
		}
		
		boolean resultCode = reviewDAO.createReview(author, isbn, content, category);
		
		if(resultCode) {
    		result = responseWraper.getProcessedResponse(null, 200);
		}else {
    		result = responseWraper.getProcessedResponse(null, 500);
		}
		
		jsonData = gson.toJson(result).toString();
		
		return jsonData;
	}
}