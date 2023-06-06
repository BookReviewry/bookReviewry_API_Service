package com.example.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HelloService {

    public String getHello(){
        return "{\"projectName\" : \"bookReiviewry\"}";
    }

}