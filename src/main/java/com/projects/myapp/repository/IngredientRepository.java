package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.Ingredient;

public interface IngredientRepository {

	List<Ingredient> findByRecipe(Long recipeId);

	int saveIngredient(String food, String unit, Float amount, Long recipeId);

	int deleteIngredient(Long id);

	int changeFood(String food, Long id);

	int changeAmount(Float amount, Long id);
	
	int changeUnit(String unit, Long id);
}