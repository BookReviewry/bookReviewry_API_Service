package com.brvr.back.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brvr.back.entity.User;
import com.brvr.back.oauth2.GoogleOAuth2User;
import com.brvr.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class userController {
	
	 private final UserRepository userRepository;
	
    @GetMapping("/profile")
    public Map<String, Object> getUserProfile() {

    	String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	Optional<User> user = userRepository.findByEmail(name);
    	
    	if(!user.isEmpty()) {
    		map.put("name", user.get().getName());
    		map.put("email", user.get().getEmail());
    	}
    	
    	return map;
        
    }

}
