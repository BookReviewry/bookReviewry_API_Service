package com.brvr.back.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.brvr.back.entity.User;
import com.brvr.back.service.NaverBookService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/books")
@RestController
public class BookController {
	
	private final NaverBookService naverBookService;
	
	@RequestMapping(value="/",  produces="application/json;charset=UTF-8", method=RequestMethod.GET)
    public @ResponseBody String getBooks(@RequestParam String query, Integer offset) {
		System.out.println("getUserProfile invoked");
		// Naver API 호출해서 검색 결과 가져오기, offset 영향 받음
		// DB 조회해서 검색 결과 가져오기, 전체 결과 가져옴
		
//    	Map<String, Object> map = naverBookService.getBooks(query, offset);
		String result = naverBookService.getBooks(query, offset);

    	return result;
    }
}
