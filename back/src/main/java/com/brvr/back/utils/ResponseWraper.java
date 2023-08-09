package com.brvr.back.utils;

import java.util.Map;

import org.springframework.http.HttpStatus;

public interface ResponseWraper {
	Map<String, Object> getProcessedResponse(Map<String, Object> json, HttpStatus status);
}
