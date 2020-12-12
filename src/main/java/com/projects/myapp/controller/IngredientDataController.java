package com.projects.myapp.controller;

import com.projects.myapp.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngredientDataController {
	@Autowired
	private IngredientRepository ingredientRepository;

	// TODO: create method to add ingredient

	// TODO: create method to delete ingredient

	// TODO: create method to change ingredient food

	// TODO: create method to change ingredient amount

	// TODO: create method to change ingredient unit
}