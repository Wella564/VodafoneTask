package com.task.Vodafone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//@configure/@componentscan/@enableautoconfiguration
public class VodafoneApplication {
/*Basic Logic of the code:

1-Load the welcome page with either login or register
2-Collect user registration info through DTO
3-Create new user "Entity" from collected DTO through Service Layer Save method
4-That user is already part of the database now and can login now
5-You can search for that user by email to match his password
6-Security package connects to springsecurity for authentication

MVC design pattern is used here for the controller handling CRUD tasks.
DTO design patter is used for data collection.
 */
	public static void main(String[] args) {
		SpringApplication.run(VodafoneApplication.class, args);
	}

}
