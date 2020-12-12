package com.projects.myapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.projects.myapp.objects.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRecipeRepository implements RecipeRepository{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveRecipe(Recipe recipe){

		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement("insert into recipe (Name, Instructions, Yield) values(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, recipe.getName());
				statement.setString(2, recipe.getInstructions());
				statement.setInt(3, recipe.getYield());
				return statement;
			}
		}, holder);

		long primaryKey = holder.getKey().longValue();

		return primaryKey;

	}

	@Override
	public int changeName(String name, long recipeId){
		return jdbcTemplate.update(
            "update Recipe set Name = ? where ID = ?",
            name, recipeId);
	}

	@Override
	public int changeInstructions(String instructions, long recipeId){
		return jdbcTemplate.update(
            "update Recipe set Instructions = ? where ID = ?",
            instructions, recipeId);
	}

	@Override
	public int changeYield(int yield, long recipeId){
		return jdbcTemplate.update(
            "update Recipe set Yield = ? where ID = ?",
            yield, recipeId);
	}

}