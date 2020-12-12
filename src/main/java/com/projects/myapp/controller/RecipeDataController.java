package com.projects.myapp.controller;

import com.projects.myapp.repository.RecipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeDataController {
	@Autowired
	private RecipeRepository recipeRepository;

	// TODO: create method to change recipe name

	// TODO: create method to change recipe instructions

	// TODO: create method to change recipe yield
}