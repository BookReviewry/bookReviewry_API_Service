package com.brvr.back.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brvr.back.dao.UserDAO;
import com.brvr.back.entity.User;
import com.brvr.back.oauth2.GoogleOAuth2User;
import com.brvr.back.repository.UserRepository;
import com.brvr.back.service.ReviewService;
import com.brvr.back.service.UserService;
import com.brvr.back.utils.ResponseWraper;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
	
	private final UserRepository userRepository;
	private final UserDAO userDAO;
	private final ResponseWraper responseWraper;
	private final UserService userService;
	private final ReviewService reviewService;
	
	/**
	 * METHOD: GET
	 * PATH: user/profile
	 * Description: return current user's profile
	 * @return
	 */
	@GetMapping(value="/profile",  produces="application/json;charset=UTF-8")
    public String getUserProfile(@RequestParam String email) {
    	return userService.getUserProfile(email);
	}
	
	/**
	 * METHOD: GET
	 * PATH: user/profile
	 * Description: return current user's profile
	 * @return
	 */
	@GetMapping(value="/profile/personal",  produces="application/json;charset=UTF-8")
    public String getUserPersonalProfile() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	return userService.getUserProfile(name);
	}
	/**
	 * METHOD: PUT
	 * PATH: user/profile
	 * Description: update user profile and return result
	 * @return
	 */
    @PutMapping("/profile")
    public String updateUserProfile(@RequestBody Map<String,Object> requestBody) {
    	
    	// get Auth from securityContextHoder
    	String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	// update User Profile
    	return userDAO.updateUserProfile(name, (String)requestBody.get("profile"));
    	
    }
    
    @GetMapping(value="/reviews/my",  produces="application/json;charset=UTF-8")
    public String getMyReview(@RequestParam String isbn) {
    	
    	String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	return reviewService.getReview(name, isbn);
    }
    
    @GetMapping("/reviews/my/all")
    public String getMyAllReview() {
    	
    	return "";
    	
    }
    
    @GetMapping(value="/bookcase",  produces="application/json;charset=UTF-8")
    public String getUserBookCase(@RequestParam String email) {
    	
    	return reviewService.getBookCase(email);
    }

}
