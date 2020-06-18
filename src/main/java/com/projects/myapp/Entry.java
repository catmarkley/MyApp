package com.projects.myapp;

import java.util.List;

public class Entry {
	private Long id;
	private Recipe recipe;
	private String comments;
	private String collectionType;
	private String category;
	private Long userId;
	private List<Photo> photos;

	public Entry(Long id, Recipe recipe, String comments, String collectionType, String category, Long userId){
		this.id = id;
		this.recipe = recipe;
		this.comments = comments;
		this.category = category;
		this.collectionType = collectionType;
		this.userId = userId;
	}

	public Entry(Recipe recipe, String comments, String collectionType, String category, Long userId){
		this.recipe = recipe;
		this.comments = comments;
		this.category = category;
		this.collectionType = collectionType;
		this.userId = userId;
	}

	public Entry(Recipe recipe, String comments, String collectionType, String category){
		this.recipe = recipe;
		this.comments = comments;
		this.category = category;
		this.collectionType = collectionType;
	}

	public Long getId(){
		return id;
	}

	public Recipe getRecipe(){
		return recipe;
	}

	public String getComments(){
		return comments;
	}

	public String getType(){
		return collectionType;
	}

	public String getCategory(){
		return category;
	}

	public Long getUserId(){
		return userId;
	}

	public List<Photo> getPhotos(){
		return photos;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setRecipe(Recipe recipe){
		this.recipe = recipe;
	}

	public void setComments(String comments){
		this.comments = comments;
	}

	public void setType(String collectionType){
		this.collectionType = collectionType;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public void setUserId(Long userId){
		this.userId = userId;
	}

	public void addPhoto(Photo photo){
		this.photos.add(photo);
	}

	public void setPhotos(List<Photo> photos){
		this.photos = photos;
	}
}