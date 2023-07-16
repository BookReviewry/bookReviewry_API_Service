package com.brvr.back.utils;

import java.util.Map;

public interface ResponseWraper {
	Map<String, Object> getProcessedResponse(Map<String, Object> json, int stat);
}
