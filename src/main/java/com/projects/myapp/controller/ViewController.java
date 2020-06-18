package com.projects.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

	//private String message;

	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}

	@RequestMapping("/login")
	public String loginPage(@RequestParam(value = "error", required = false) String error,
							@RequestParam(value = "logout", required = false) String logout,
							Model model) {

		//model.addAttribute("Here is my message", message);
		/*String errorMessge = null;
		if(error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if(logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		model.addAttribute("errorMessge", errorMessge);*/
		return "/auth/login";
	}

	@RequestMapping("/register")
	public String createUserPage() {
		return "/auth/create_user";
	}

	@RequestMapping("/admin/user")
	public String manageUserPage(){
		return "/auth/user_management";
	}

	@RequestMapping("/mycookbook")
	public String myCookbook(){
		return "mycookbook.html";
	}
}