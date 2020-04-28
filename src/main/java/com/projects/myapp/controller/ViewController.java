package com.projects.myapp.controller;

import java.util.List;

import com.projects.myapp.Example;
import com.projects.myapp.repository.ExampleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {

	//@Autowired
	//JdbcTemplate jdbcTemplate;

	@Autowired
	//@Qualifier("jdbcBookRepository")
	private ExampleRepository exampleRepository;

	@RequestMapping("/")
	public String testThis() {

		return "index";
	}

	@RequestMapping("/testing")
	@ResponseBody
   	public String testThis2() {
    	return runJDBC();
	}

	String runJDBC() {
		// count
	   /*System.out.print("[COUNT] Total examples: {}");
	   System.out.println(exampleRepository.count());

	   return exampleRepository.count();*/

	   /*// find all
	   System.out.print("[FIND_ALL] {}");
	   System.out.println(exampleRepository.findAll());
	   return exampleRepository.findAll();
*/
	   // find by id
	   System.out.println("[FIND_BY_ID] :1L");
	   Example example = exampleRepository.findById(1L).orElseThrow(IllegalArgumentException::new);
	   System.out.println(example);
	   return example.getRecipeName();
		/*
	   // find by name
	   System.out.println("[FIND_BY_NAME] : like '%Test Recipe%'");
	   System.out.println(exampleRepository.findByName("Test Recipe"));

	   // update
	   System.out.println("[UPDATE] :1L");
	   example.setRecipeName("Recipe 1");
	   System.out.print("rows affected: {}");
	   System.out.println(exampleRepository.update(example));
	*/   
   }

}