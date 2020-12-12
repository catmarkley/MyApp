package com.projects.myapp.objects;

public class Ingredient {
	private Long id;
	private String food;
	private Float amount;
	private String unit;
	private Long recipeId;

	public Ingredient(Long id, String food, Float amount, String unit, Long recipeId){
		this.id = id;
		this.food = food;
		this.amount = amount;
		this.unit = unit;
		this.recipeId = recipeId;
	}

	public Ingredient(String food, Float amount, String unit, Long recipeId){
		this.food = food;
		this.amount = amount;
		this.unit = unit;
		this.recipeId = recipeId;
	}

	public Long getId(){
		return id;
	}

	public String getFood(){
		return food;
	}

	public Float getAmount(){
		return amount;
	}

	public String getUnit(){
		return unit;
	}

	public Long getRecipeId(){
		return recipeId;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setFood(String food){
		this.food = food;
	}

	public void setAmount(Float amount){
		this.amount = amount;
	}

	public void setUnit(String unit){
		this.unit = unit;
	}
	
	public void setRecipeId(Long recipeId){
		this.recipeId = recipeId;
	}
}