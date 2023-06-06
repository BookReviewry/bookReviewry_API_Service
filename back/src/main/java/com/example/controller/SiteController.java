package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SiteController {
	
	@GetMapping("/test")
	public String test() {
		System.out.println("hello");
		return "test!";
	}
}
