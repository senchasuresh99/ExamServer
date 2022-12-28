package com.exam.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.entity.model.User;
import com.exam.entity.model.UserRole;
import com.exam.helper.UserFoundException;
import com.exam.repo.RoleRepository;
import com.exam.repo.UserRepository;
import com.exam.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	//creating user
	@Override
	public User createUser(User user, Set<UserRole> userRoles)  {
		User local = this.userRepository.findByUsername(user.getUsername());
		if(local !=null) {
			System.out.println("User is alrady there !!");
			try {
//				throw new UserFoundException("User  alrady Present !!");
				throw new UserFoundException();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// user create 
			for(UserRole ur: userRoles) {
				roleRepository.save(ur.getRole());
			}
			user.getUserRoles().addAll(userRoles);
			local = this.userRepository.save(user);
		}
		return local;
	}

	//getting username by username
	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
	}

	@Override
	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

}
