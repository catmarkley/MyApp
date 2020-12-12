package com.projects.myapp.controller;

import java.io.File;
import java.util.List;

import com.projects.myapp.objects.Entry;
import com.projects.myapp.objects.Ingredient;
import com.projects.myapp.objects.Photo;
import com.projects.myapp.objects.Recipe;
import com.projects.myapp.repository.EntryRepository;
import com.projects.myapp.repository.IngredientRepository;
import com.projects.myapp.repository.PhotoRepository;
import com.projects.myapp.repository.RecipeRepository;
import com.projects.myapp.repository.UserRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntryDataController {
	@Autowired
	private EntryRepository entryRepository;
	@Autowired
	private RecipeRepository recipeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private IngredientRepository ingredRepository;
	@Autowired
	private PhotoRepository photoRepository;

	private long userId = -1;

	private void setUserId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString().split(": |; ")[2];
		userId = userRepository.getIdByUsername(username);
	}


	@RequestMapping(value = "/entry/count", method = RequestMethod.GET)
	public int getCount() throws ParseException {
		if(userId < 0){ setUserId(); }
		int result = entryRepository.count(userId);
		return result;
	}

	@RequestMapping(value = "/entry", method = RequestMethod.POST)
	public long addEntry(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		Recipe recipe = new Recipe(obj.get("name").toString(), obj.get("instructions").toString(), Integer.parseInt(obj.get("yield").toString()));
		long recipeId = recipeRepository.saveRecipe(recipe);
		recipe.setId(recipeId);

		JSONArray ingreds = (JSONArray) obj.get("ingredients");
		for(int i=0; i<ingreds.size(); i++){
			JSONObject ingred = (JSONObject)ingreds.get(i);
			String food = ingred.get("food").toString();
			String unit = ingred.get("unit").toString();
			Float amount = Float.parseFloat(ingred.get("amount").toString());
			Ingredient ingredient = new Ingredient(food, amount, unit, recipeId);
			int result = ingredRepository.saveIngredient(ingredient);
		}

		if(userId < 0){ setUserId(); }

		Entry entry = new Entry(recipe, obj.get("comments").toString(), obj.get("collection").toString(), obj.get("category").toString(), userId);
		long entry_id = entryRepository.saveEntry(entry);

		JSONArray photos = (JSONArray) obj.get("photos");
		for(int i=0; i<photos.size(); i++){
			JSONObject photo_json = (JSONObject)photos.get(i);
			Photo photo = new Photo(entry_id, photo_json.get("path").toString());
			int result = photoRepository.savePhoto(photo);
		}

		return entry_id;
	}

	@RequestMapping(value = "/entry/{id}", method = RequestMethod.DELETE)
	public int deleteEntry(@PathVariable("id") long id) throws ParseException {
		List<Photo> photos = photoRepository.findByEntry(id);

		// delete from database
		int result = entryRepository.deleteById(id);

		// delete stored photos
		for(int i=0; i<photos.size(); i++){
			try  {         
				File f= new File("/var/tmp/"+photos.get(i).getUrl());
				if(f.delete()) {
					System.out.println(f.getName() + " deleted"); 
				}  
				else {  
					System.out.println("Failed to delete file");  
				}  
			}  catch(Exception e)  {  
				e.printStackTrace();  
			} 
		}

		return result;
	}

	@RequestMapping(value = "/entry/{id}", method = RequestMethod.PUT)
	public int updateEntry(@PathVariable("id") long id, @RequestBody String jsonString) throws ParseException {
		if(userId < 0){ setUserId(); }

		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		Recipe recipe = new Recipe(obj.get("name").toString(), obj.get("instructions").toString(), Integer.parseInt(obj.get("yield").toString()));
		Entry entry = new Entry(recipe, obj.get("comments").toString(), obj.get("collection").toString(), obj.get("category").toString(), userId);
		entry.setId(id);
		long recipeID = entryRepository.updateEntry(entry);
		recipe.setId(recipeID);

		int result = -1;

		// delete existing ingredients
		result = ingredRepository.deleteIngredients(recipeID);

		JSONArray ingreds = (JSONArray) obj.get("ingredients");
		for(int i=0; i<ingreds.size(); i++){
			JSONObject ingred = (JSONObject)ingreds.get(i);
			String food = ingred.get("food").toString();
			String unit = ingred.get("unit").toString();
			Float amount = Float.parseFloat(ingred.get("amount").toString());
			Ingredient ingredient = new Ingredient(food, amount, unit, recipeID);
			result = ingredRepository.saveIngredient(ingredient);
		}

		JSONArray photos = (JSONArray) obj.get("photos");
		for(int i=0; i<photos.size(); i++){
			JSONObject photo_json = (JSONObject)photos.get(i);
			Photo photo = new Photo(id, photo_json.get("path").toString());
			result = photoRepository.savePhoto(photo);
		}

		return result;
	}

	@RequestMapping(value = "/entry", method = RequestMethod.GET)
	public List<Entry> getAll() throws ParseException {
		if(userId < 0){ setUserId(); }
		List<Entry> results = entryRepository.findAll(userId);
		return results;
	}

	@RequestMapping(value = "/entry/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Entry getEntryById(@PathVariable("id") long id) throws ParseException {
		Entry result = entryRepository.findById(id);
		return result;
	}
	
	@RequestMapping(value = "/entry/collection/{collectionType}", method = RequestMethod.GET)
	@ResponseBody
	public List<Entry> getCollection(@PathVariable("collectionType") String type) throws ParseException {
		if(userId < 0){ setUserId(); }
		List<Entry> results = entryRepository.findByCollection(userId, type);
		return results;
	}

	// TODO: method to find by recipe name

	// TODO: method to find by ingredient

	// TODO: method to change comments

	// TODO: method to change collection

	// TODO: method to change category
}