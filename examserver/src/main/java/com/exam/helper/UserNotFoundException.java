package com.exam.helper;

public class UserNotFoundException extends Exception{
 
	public UserNotFoundException() {
		super("User with this Username not found  in DataBase !!");
	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
