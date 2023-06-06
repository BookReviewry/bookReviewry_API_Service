package com.example.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/oauth")
@RestController
public class LoginController {
    @GetMapping("/result")
    public void getJson(Authentication authentication, HttpServletResponse httpServletResponse) throws IOException {

    	System.out.println(authentication.toString());
    	OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Cookie myCookie = new Cookie("testCookie",   attributes.get("email").toString());
        myCookie.setSecure(true);
        myCookie.setHttpOnly(true);
        myCookie.setPath("/");

        httpServletResponse.addCookie(myCookie);
        
        httpServletResponse.sendRedirect("http://localhost:3000");
        
    }
}