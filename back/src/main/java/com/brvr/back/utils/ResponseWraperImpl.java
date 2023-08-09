package com.brvr.back.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ResponseWraperImpl implements ResponseWraper {

	@Override
	public Map<String, Object> getProcessedResponse(Map<String, Object> json, HttpStatus status) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("stat", status);
		result.put("res", json);
		
		
		return result;
	}

}
