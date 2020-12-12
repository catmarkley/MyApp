package com.projects.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

	//private String message;

	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}

	// Will get rid of this, it is the cs template example I used
	@RequestMapping("/testpage")
	public String testpage(){
		return "Page-2";
	}

	@RequestMapping("/home")
	public String home(){
		return "home";
	}

	@RequestMapping("/mycookbook")
	public String mycookbook(){
		return "mycookbook";
	}

	@RequestMapping("/new")
	public String new_entry(){
		return "new_entry";
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

	@RequestMapping("/recipe/{id}")
	public ModelAndView toRecipe(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView("recipe");
		modelAndView.addObject("EntryID", id);
		return modelAndView;
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView toEditRecipe(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView("edit_recipe");
		modelAndView.addObject("EntryID", id);
		return modelAndView;
	}
}