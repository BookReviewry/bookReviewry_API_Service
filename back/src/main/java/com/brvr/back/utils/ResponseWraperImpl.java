package com.brvr.back.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ResponseWraperImpl implements ResponseWraper {

	@Override
	public Map<String, Object> getProcessedResponse(Map<String, Object> json, int stat) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("stat", stat);
		result.put("res", json);
		
		
		return result;
	}

}
