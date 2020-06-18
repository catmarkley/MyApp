package com.projects.myapp.controller;

import java.util.List;

import com.projects.myapp.Entry;
import com.projects.myapp.Recipe;
import com.projects.myapp.repository.EntryRepository;
import com.projects.myapp.repository.IngredientRepository;
import com.projects.myapp.repository.PhotoRepository;
import com.projects.myapp.repository.RecipeRepository;
import com.projects.myapp.repository.UserRepository;

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

	private long userId = -1;

	private void setUserId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString().split(": |; ")[2];
		userId = userRepository.getIdByUsername(username);
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

	@RequestMapping(value = "/entry", method = RequestMethod.GET)
	public List<Entry> getAll() throws ParseException {
		if(userId < 0){ setUserId(); }
		List<Entry> results = entryRepository.findAll(userId);
		return results;
	}

	@RequestMapping(value = "/entry", method = RequestMethod.POST)
	public int addEntry(@RequestBody String jsonString) throws ParseException {
		JSONObject obj = (JSONObject) new JSONParser().parse(jsonString);
		Recipe recipe = new Recipe(obj.get("name").toString(), obj.get("instructions").toString(), Integer.parseInt(obj.get("yield").toString()));
		long recipeId = recipeRepository.saveRecipe(recipe);
		recipe.setId(recipeId);

		if(userId < 0){ setUserId(); }

		Entry entry = new Entry(recipe, obj.get("comments").toString(), obj.get("collection").toString(), obj.get("category").toString(), userId);
		int result = entryRepository.saveEntry(entry);
		return result;
	}

	@RequestMapping(value = "/entry/count", method = RequestMethod.GET)
	public int getCount() throws ParseException {
		if(userId < 0){ setUserId(); }
		int result = entryRepository.count(userId);
		return result;
	}
}