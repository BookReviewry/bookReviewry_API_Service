package com.brvr.back.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brvr.back.entity.Review;
import com.brvr.back.repository.BookRepository;
import com.brvr.back.repository.ReviewRepostory;
import com.brvr.back.repository.UserRepository;
import com.brvr.back.utils.ResponseWraper;
import com.google.gson.Gson;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NaverBookService {
	@Value("${naver.api.id}") String clientId;
	@Value("${naver.api.secret}") String secret;
	
	final int DISPLAY_COUNT = 30;
	private final BookRepository bookRepository;
	private final ResponseWraper responseWraper;
	private final ReviewRepostory reviewRepostory;
	
	public String getBooks(String query,Integer offset) {
		
		System.out.println("getBooks invoked");
		String encodedQuery = null;
		HttpStatus resultCode = HttpStatus.OK;
        try {
        	encodedQuery = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }
        String apiURL = "https://openapi.naver.com/v1/search/book?query=" + encodedQuery + "&display=" + DISPLAY_COUNT + "&start=" + (offset*DISPLAY_COUNT > 0 ? offset*DISPLAY_COUNT : 1) ;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", secret);
        String responseBody = get(apiURL,requestHeaders);
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(responseBody, Map.class);
 
        /*
         * 값이 데이터 베이스에 있으면 우선 적으로 노출시키기 위해 추가
         */
	   ArrayList<Map<String, Object>> items = (ArrayList) map.get("items");
	   if(!items.isEmpty()) {
		   for(Map<String, Object> book : items) {
			   String isbn = (String) book.get("isbn");

			   int eqSum = 0;
			   ArrayList<Optional<Review>> reviewList = reviewRepostory.findAllByIsbn(isbn);
			   for (Optional<Review> review : reviewList) {
				   if(!review.isEmpty()) {
					   int eq = review.get().getEq();
					   eqSum += eq;
				   }
			   }
			   if(reviewList.size() > 0) {				   
				   book.put("eq", eqSum/reviewList.size());
				   book.put("isExist", true);
			   }else {
				   book.put("eq", 0);
				   book.put("isExist", false);
			   }
			   
		   }
	   }else {
		   resultCode = HttpStatus.NO_CONTENT;
	   }
        
        Map<String, Object> result = responseWraper.getProcessedResponse(map, resultCode);
        
        String jsonData = gson.toJson(result).toString();

		return jsonData;
	}
	
	public String getBookDetail(String isbn) {
		System.out.println("getBookDetail invoked");
		
		String encodedQuery = null;
        try {
        	encodedQuery = URLEncoder.encode(isbn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("ISBN 인코딩 실패",e);
        }
        HttpStatus resultCode = HttpStatus.OK;
        String apiURL = "https://openapi.naver.com/v1/search/book?query=" + encodedQuery;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", secret);
        String responseBody = get(apiURL,requestHeaders);
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(responseBody, Map.class);
        
        ArrayList<Map<String, Object>> items = (ArrayList) map.get("items");
 	   if(!items.isEmpty()) {
 		   for(Map<String, Object> book : items) {
 			   
 			   int eqSum = 0;
 			   ArrayList<Optional<Review>> reviewList = reviewRepostory.findAllByIsbn(isbn);
 			   for (Optional<Review> review : reviewList) {
 				   if(!review.isEmpty()) {
 					   int eq = review.get().getEq();
 					   eqSum += eq;
 				   }
 			   }
 			   if(reviewList.size() > 0) {				   
 				   book.put("eq", eqSum/reviewList.size());
 				   book.put("reviewCount", reviewList.size());
 				   book.put("isExist", true);
 			   }else {
 				   book.put("eq", 0);
 				   book.put("isExist", false);
 				   book.put("reviewCount", 0);
 			   }
 			   
 		   }
 	   }else {
 		  resultCode = HttpStatus.NO_CONTENT;
 	   }
 	   
        Map<String, Object> result = responseWraper.getProcessedResponse(map, resultCode);
        
        String jsonData = gson.toJson(result).toString();

		return jsonData;
	}
	
    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream());
            } else {
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }
    
    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }
    
    private static String readBody(InputStream body) throws UnsupportedEncodingException{
        InputStreamReader streamReader = new InputStreamReader(body, "UTF-8");

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
                
                
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
