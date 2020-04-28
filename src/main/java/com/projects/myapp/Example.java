package com.projects.myapp;

public class Example {

    private Long id;
	private String recipe_name;
	
	public Example(Long id, String recipeName){
		this.id = id;
		this.recipe_name = recipeName;
	}

	public Long getId(){
		return id;
	}

	public String getRecipeName(){
		return recipe_name;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setRecipeName(String recipeName){
		this.recipe_name = recipeName;
	}
}