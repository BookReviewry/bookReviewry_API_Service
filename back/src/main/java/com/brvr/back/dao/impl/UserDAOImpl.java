package com.brvr.back.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.brvr.back.dao.UserDAO;
import com.brvr.back.entity.User;
import com.brvr.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDAOImpl implements UserDAO {

	private final UserRepository userRepository;
	
	@Override
	public String updateUserProfile(String userName, String profile) {
		
    	Optional<User> user = getUser(userName);
    	
    	if(user.isEmpty()) {
    		return "FAIL";
    	}else {
    		userRepository.save(user.get().updateProfile(profile));
    	}
    	return "SUCCESS";
	}
	
	private Optional<User> getUser(String userName) {
		Optional<User> user = userRepository.findByEmail(userName);
		return user;
	}

}
