package com.projects.myapp.controller;

import java.util.List;

import com.projects.myapp.User;
import com.projects.myapp.repository.UserRepository;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/user/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = passwordEncoder.encode(obj.get("password").toString());

		User new_user = new User(obj.get("first_name").toString(), obj.get("last_name").toString(), obj.get("username").toString(), password, true);
		int resultCreate = userRepository.save(new_user);

		if (resultCreate == 1){
			int resultRole = userRepository.saveRole(new_user.getUsername());
			if(resultRole == 1){
				return new ResponseEntity<>("User is created successfully", HttpStatus.CREATED);
			}
		}
		
		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}