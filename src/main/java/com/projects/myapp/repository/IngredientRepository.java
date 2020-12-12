package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.objects.Ingredient;

public interface IngredientRepository {

	List<Ingredient> findByRecipe(Long recipeId);

	int saveIngredient(Ingredient ingredient);

	int deleteIngredient(Long id);

	int deleteIngredients(Long recipeID);

	int changeFood(String food, Long id);

	int changeAmount(Float amount, Long id);
	
	int changeUnit(String unit, Long id);
}