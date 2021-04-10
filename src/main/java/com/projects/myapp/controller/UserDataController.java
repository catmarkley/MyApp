package com.projects.myapp.controller;

import java.util.List;

import com.projects.myapp.objects.User;
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
public class UserDataController {
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

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public List<User> getUsers() throws ParseException {

		List<User> users = userRepository.findAll();

		return users;
	}

	@RequestMapping(value = "/user/disable", method = RequestMethod.POST)
	public ResponseEntity<Object> disableUser(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		String username = obj.get("username").toString();

		int result = userRepository.disable(username);

		if(result == 1){
			return new ResponseEntity<>("User is disabled successfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/user/enable", method = RequestMethod.POST)
	public ResponseEntity<Object> enableUser(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		String username = obj.get("username").toString();

		int result = userRepository.enable(username);

		if(result == 1){
			return new ResponseEntity<>("User is enabled successfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/user/password", method = RequestMethod.POST)
	public ResponseEntity<Object> changeUserPassword(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		String username = obj.get("username").toString();
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String new_password = passwordEncoder.encode(obj.get("password").toString());

		int result = userRepository.changePassword(username, new_password);

		if(result == 1){
			return new ResponseEntity<>("Password changed successfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/user/firstName", method = RequestMethod.POST)
	public ResponseEntity<Object> changeUserFirstName(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		String username = obj.get("username").toString();
		String firstName = obj.get("firstName").toString();

		int result = userRepository.changeFirstName(username, firstName);

		if(result == 1){
			return new ResponseEntity<>("First name changed successfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/user/lastName", method = RequestMethod.POST)
	public ResponseEntity<Object> changeUserLastName(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		String username = obj.get("username").toString();
		String lastName = obj.get("lastName").toString();

		int result = userRepository.changeLastName(username, lastName);

		if(result == 1){
			return new ResponseEntity<>("Last name changed successfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value = "/user/role", method = RequestMethod.POST)
	public ResponseEntity<Object> changeUserRole(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		String username = obj.get("username").toString();
		String role = obj.get("role").toString();

		int result = userRepository.changeRole(username, role);

		if(result == 1){
			return new ResponseEntity<>("Role changed successfully", HttpStatus.CREATED);
		}

		return new ResponseEntity<>("There was an issue", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}