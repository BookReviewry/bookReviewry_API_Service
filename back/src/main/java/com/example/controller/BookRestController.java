package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BookRestController {

    @GetMapping("/test")
	public String testAPI() {
		return "test passed";
	}
    
    @GetMapping("/login/google")
    public String loginWithGoogle() {
        return "redirect:https://accounts.google.com/o/oauth2/auth";
    }

    @GetMapping("/login/google/callback")
    public String loginWithGoogleCallback(@AuthenticationPrincipal OAuth2User principal) {
        // Handle the user data received from Google OAuth
        // You can access user information using `principal` object
        return "redirect:hello"; // Redirect to home page or any other URL
    }
    
    @GetMapping("/books")
    @ResponseBody
    public Object books() {
		return null;
    }
    
    @GetMapping("/reviews")
    @ResponseBody
    public Object reviews() {
		return null;
    }
    
    @GetMapping("/myLibrary")
    @ResponseBody
    public Object myLibrary() {
		return null;
    }
    
    @GetMapping("/userProfile")
    @ResponseBody
    public Object userProfile() {
		return null;
    }

}
