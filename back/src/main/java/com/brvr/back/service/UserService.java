package com.brvr.back.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brvr.back.dao.UserDAO;
import com.brvr.back.entity.User;
import com.brvr.back.repository.UserRepository;
import com.brvr.back.utils.ResponseWraper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final ResponseWraper responseWraper;
	private final UserRepository userRepository;
	private final UserDAO userDAO;
	private final ReviewService reviewService;
	private final TodoService todoService;
	
	public String getUserProfile(String email) {
		

    	Map<String, Object> map = new HashMap<>();
    	Optional<User> user = userRepository.findByEmail(email);
    	int reviewCount = reviewService.getReviewCount(email);
    	int todoCount = todoService.getTodoCount(email);
		Map<String,Object> result = null;
		
    	HttpStatus status = HttpStatus.BAD_REQUEST;
    	if(!user.isEmpty()) {
    		map.put("name", user.get().getName());
    		map.put("email", user.get().getEmail());
    		map.put("profile", user.get().getProfile());
    		map.put("reviewCount", reviewCount);
    		map.put("todoCount", todoCount);
    		status =  HttpStatus.OK;
    	}
    	
    	result = responseWraper.getProcessedResponse(map, status);
    	
		Gson gson = new Gson();
		String jsonData = gson.toJson(result).toString();
		
    	return jsonData;
	}
}
