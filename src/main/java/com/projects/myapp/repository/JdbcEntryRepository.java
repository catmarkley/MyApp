package com.projects.myapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.projects.myapp.Entry;
import com.projects.myapp.Ingredient;
import com.projects.myapp.Photo;
import com.projects.myapp.Recipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcEntryRepository implements EntryRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int count(long userId){
		return jdbcTemplate
                .queryForObject(
					"select count(*) from Entry where Entry.UserID = ?", 
					Integer.class,
					new Object[]{userId});
	}

	@Override
	public int saveEntry(Entry entry){
		return jdbcTemplate.update(
			"insert into entry (RecipeID, Comments, CollectionType, UserID, Category) values(?, ?, ?, ?, ?)",
			entry.getRecipe().getId(), entry.getComments(), entry.getType(), entry.getUserId(), entry.getCategory());
	}

	@Override
	public int deleteById(long id){
		String queryString1 = "delete from Photo where EntryID = ?";
		String queryString2 = "delete from Ingredient where RecipeID = (select RecipeID from Entry where ID = ?)";
		String queryString3 = "delete from Recipe where ID = (select RecipeID from Entry where ID = ?";
		String queryString4 = "delete from Entry where ID = ?";

		int result = -1;
		result = jdbcTemplate.update(queryString1, id);
		if(result == 1){
			result = jdbcTemplate.update(queryString2, id);
			if(result == 1){
				result = jdbcTemplate.update(queryString3, id);
				if(result == 1){
					result = jdbcTemplate.update(queryString4, id);
				}
			}
		}

		return result;
	}

	@Override
	public List<Entry> findAll(long userId) {
		String queryString = "";
		queryString = queryString + "select entry.ID, entry.RecipeID, entry.Comments, entry.CollectionType, entry.Category, entry.UserID, ";
		queryString = queryString + "recipe.Name, recipe.Instructions, recipe.Yield ";
		queryString = queryString + "from entry, recipe where entry.RecipeID = recipe.id and entry.UserID = ?";

        List<Entry> entries = jdbcTemplate.query(
				queryString,
                new Object[]{userId},
                (rs, rowNum) ->
                        new Entry(
								rs.getLong("entry.ID"),
								new Recipe(
									rs.getLong("entry.RecipeID"),
									rs.getString("recipe.Name"),
									rs.getString("recipe.Instructions"),
									rs.getInt("recipe.Yield")
									),
								rs.getString("entry.Comments"),
								rs.getString("entry.CollectionType"),
								rs.getString("entry.Category"),
								rs.getLong("entry.UserID")
                        )
		);

		getIngredientsForRecipe(entries);
		getPhotosForRecipe(entries);
		
		return entries;
	}

	@Override
    public Entry findById(long id) {
		String queryString = "";
		queryString = queryString + "select entry.ID, entry.RecipeID, entry.Comments, entry.CollectionType, entry.Category, entry.UserID, ";
		queryString = queryString + "recipe.Name, recipe.Instructions, recipe.Yield ";
		queryString = queryString + "from entry, recipe where entry.id = ? and entry.RecipeID = recipe.ID";

        Optional<Entry> result = jdbcTemplate.queryForObject(
				queryString,
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Entry(
								rs.getLong("entry.ID"),
								new Recipe(
									rs.getLong("entry.RecipeID"),
									rs.getString("recipe.Name"),
									rs.getString("recipe.Instructions"),
									rs.getInt("recipe.Yield")
									),
								rs.getString("entry.Comments"),
								rs.getString("entry.CollectionType"),
								rs.getString("entry.Category"),
								rs.getLong("entry.UserID")
                        ))
		);
		
		if(result.isPresent()){
			Entry entry = result.get();
			List<Entry> entries = new ArrayList<Entry>();
			entries.add(entry);
			getIngredientsForRecipe(entries);
			getPhotosForRecipe(entries);
			return entries.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Entry> findByCollection(long userId, String collectionType){
		String queryString = "";
		queryString = queryString + "select entry.ID, entry.RecipeID, entry.Comments, entry.CollectionType, entry.Category, entry.UserID, ";
		queryString = queryString + "recipe.Name, recipe.Instructions, recipe.Yield ";
		queryString = queryString + "from entry, recipe where entry.RecipeID = recipe.id and entry.UserID = ? and entry.CollectionType = ?";

        List<Entry> entries = jdbcTemplate.query(
				queryString,
                new Object[]{userId, collectionType},
                (rs, rowNum) ->
                        new Entry(
								rs.getLong("entry.ID"),
								new Recipe(
									rs.getLong("entry.RecipeID"),
									rs.getString("recipe.Name"),
									rs.getString("recipe.Instructions"),
									rs.getInt("recipe.Yield")
									),
								rs.getString("entry.Comments"),
								rs.getString("entry.CollectionType"),
								rs.getString("entry.Category"),
								rs.getLong("entry.UserID")
                        )
		);

		getIngredientsForRecipe(entries);
		getPhotosForRecipe(entries);
		
		return entries;	
	}

	@Override
	public List<Entry> findByRecipeName(String name, long userId){
		String queryString = "select Recipe.ID, Recipe.Name, Recipe.Instructions, Recipe.Yield, ";
		queryString = queryString + "Entry.ID, Entry.RecipeID, Entry.Comments, Entry.CollectionType, Entry.UserID, Entry.Category ";
		queryString = queryString + "from Recipe, Entry where Entry.RecipeID = Recipe.ID and Entry.UserID = ? and Recipe.Name like '%?%'";

        List<Entry> entries = jdbcTemplate.query(
			queryString,
			new Object[]{userId, name},
			(rs, rowNum) ->
				new Entry(
					rs.getLong("Entry.ID"),
					new Recipe(
						rs.getLong("Entry.RecipeID"),
						rs.getString("Recipe.Name"),
						rs.getString("Recipe.Instructions"),
						rs.getInt("Recipe.Yield")
						),
					rs.getString("Entry.Comments"),
					rs.getString("Entry.CollectionType"),
					rs.getString("Entry.Category"),
					userId
				)
			);
			
		getIngredientsForRecipe(entries);
		getPhotosForRecipe(entries);

		return entries;
	}

	@Override
	public List<Entry> findByIngredient(String food, long userId){
		String queryString = "select Recipe.ID, Recipe.Name, Recipe.Instructions, Recipe.Yield, ";
		queryString = queryString + "Entry.ID, Entry.RecipeID, Entry.Comments, Entry.CollectionType, Entry.UserID, Entry.Category ";
		queryString = queryString + "from Recipe, Entry where Entry.RecipeID = Recipe.ID and Entry.UserID = ? and ";
		queryString = queryString + "Recipe.ID in (select RecipeID from Ingredient where Food like '%?%' group by RecipeID)";

        List<Entry> entries = jdbcTemplate.query(
			queryString,
			new Object[]{userId, food},
			(rs, rowNum) ->
				new Entry(
					rs.getLong("Entry.ID"),
					new Recipe(
						rs.getLong("Entry.RecipeID"),
						rs.getString("Recipe.Name"),
						rs.getString("Recipe.Instructions"),
						rs.getInt("Recipe.Yield")
						),
					rs.getString("Entry.Comments"),
					rs.getString("Entry.CollectionType"),
					rs.getString("Entry.Category"),
					userId
				)
			);
			
		getIngredientsForRecipe(entries);
		getPhotosForRecipe(entries);

		return entries;
	}

	@Override
	public int changeComments(String comments, long entryId){
		return jdbcTemplate.update(
            "update Entry set Comments = ? where ID = ?",
            comments, entryId);
	}

	@Override
	public int changeCollection(String collectionType, long entryId){
		return jdbcTemplate.update(
            "update Entry set CollectionType = ? where ID = ?",
            collectionType, entryId);
	}

	@Override
	public int changeCategory(String category, long entryId){
		return jdbcTemplate.update(
            "update Entry set Category = ? where ID = ?",
            category, entryId);
	}

	// --------------------------------------------------------------------------------------------------------------
	// Helper methods
	// --------------------------------------------------------------------------------------------------------------

	private void getIngredientsForRecipe(List<Entry> entries){
		String ingredQueryString = "";
		ingredQueryString += "select ID, Food, Unit, RecipeID, Amount ";
		ingredQueryString += "from ingredient where RecipeID = ?";

		for(int i=0; i<entries.size(); i++){
			Long id = entries.get(i).getRecipe().getId();
			List<Ingredient> ingreds = jdbcTemplate.query(
				ingredQueryString,
                new Object[]{id},
				(rs, rowNum) ->
						new Ingredient(
								rs.getLong("ID"),
								rs.getString("Food"),
								rs.getFloat("Amount"),
								rs.getString("Unit"),
								rs.getLong("RecipeID")
						)
                        
			);
			entries.get(i).getRecipe().setIngredients(ingreds);
		}
	}

	private void getPhotosForRecipe(List<Entry> entries){
		String photoQueryString = "";
		photoQueryString += "select ID, url, EntryID ";
		photoQueryString += "from photo where EntryID = ?";

		for(int i=0; i<entries.size(); i++){
			Long id = entries.get(i).getId();
			List<Photo> photos = jdbcTemplate.query(
				photoQueryString,
                new Object[]{id},
				(rs, rowNum) ->
						new Photo(
								rs.getLong("ID"),
								rs.getLong("EntryID"),
								rs.getString("url")
						)
                        
			);
			entries.get(i).setPhotos(photos);
		}
	}
}