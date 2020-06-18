package com.projects.myapp.repository;

import com.projects.myapp.Recipe;

public interface RecipeRepository {
	long saveRecipe(Recipe recipe);

	int changeName(String name, long recipeId);

	int changeInstructions(String instructions, long recipeId);

	int changeYield(int yield, long recipeId);
}