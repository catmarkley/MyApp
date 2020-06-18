package com.projects.myapp;

import java.util.List;

public class Recipe {
	private Long id;
	private String name;
	private String instructions;
	private int yield;
	private List<Ingredient> ingredients;

	public Recipe(Long id, String name, String instructions, int yield){
		this.id = id;
		this.name = name;
		this.instructions = instructions;
		this.yield = yield;
	}

	public Recipe(String name, String instructions, int yield){
		this.name = name;
		this.instructions = instructions;
		this.yield = yield;
	}

	public Long getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public String getInstructions(){
		return instructions;
	}
	
	public int getYield(){
		return yield;
	}

	public List<Ingredient> getIngredients(){
		return ingredients;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setInstructions(String instructions){
		this.instructions = instructions;
	}

	public void setYield(int yield){
		this.yield = yield;
	}

	public void addIngredient(Ingredient ingredient){
		this.ingredients.add(ingredient);
	}

	public void setIngredients(List<Ingredient> ingredients){
		this.ingredients = ingredients;
	}
}