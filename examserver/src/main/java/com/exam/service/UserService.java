package com.exam.service;

import java.util.Set;

import com.exam.entity.model.User;
import com.exam.entity.model.UserRole;

public interface UserService {

	//creating user
	public User createUser(User user, Set<UserRole> userRole);
	
	//get user by username
	public User getUser(String username);
	
	//Delete user by id
	public void deleteUser(Long id);
}
