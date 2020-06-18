package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.Ingredient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Ingredient> findByRecipe(Long recipeId){
		return jdbcTemplate.query(
			"select ID, Food, Amount, Unit from ingredient where RecipeID = ?",
			new Object[]{recipeId},
			(rs, rowNum) ->
					new Ingredient(
							rs.getLong("ID"),
							rs.getString("Food"),
							rs.getFloat("Amount"),
							rs.getString("Unit"),
							recipeId
					)
		);
	}

	@Override
	public int saveIngredient(String food, String unit, Float amount, Long recipeId){
		return jdbcTemplate.update(
            "insert into Ingredient (Food, Unit, Amount, RecipeID) values (?, ?, ?, ?)", 
            food, unit, amount, recipeId);
	}

	@Override
	public int deleteIngredient(Long id){
		return jdbcTemplate.update(
			"delete from Ingredient where id = ?",
			id);
	}

	@Override
	public int changeFood(String food, Long id){
		return jdbcTemplate.update(
            "update Ingredient set Food = ? where ID = ?",
            food, id);
	}

	@Override
	public int changeAmount(Float amount, Long id){
		return jdbcTemplate.update(
            "update Ingredient set Amount = ? where ID = ?",
            amount, id);
	}
	
	@Override
	public int changeUnit(String unit, Long id){
		return jdbcTemplate.update(
            "update Ingredient set Unit = ? where ID = ?",
            unit, id);
	}
	
}