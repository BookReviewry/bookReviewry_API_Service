package com.brvr.back.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
	
	private final UserRepository userRepository;
	private final UserDAO userDAO;
	
	/**
	 * METHOD: GET
	 * PATH: user/profile
	 * Description: return current user's profile
	 * @return
	 */
    @GetMapping("/profile")
    public Map<String, Object> getUserProfile(@RequestParam String email) {
    	
    	Map<String, Object> map = new HashMap<>();
    	Optional<User> user = userRepository.findByEmail(email);
    	
    	if(!user.isEmpty()) {
    		map.put("name", user.get().getName());
    		map.put("email", user.get().getEmail());
    		map.put("profile", user.get().getProfile());
    	}
    	
    	return map;
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
    	return userDAO.updateUserProfile(name, (String)requestBody.get("profileTxt"));
    	
    }

}
