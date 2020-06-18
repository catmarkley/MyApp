package com.projects.myapp.repository;

import java.util.List;
import java.util.Optional;

import com.projects.myapp.Entry;

public interface EntryRepository {
	int count(long userId);

	int saveEntry(Entry entry);
	
	int deleteById(long id); // deletes all child information (recipe, ingredient, photo)

	List<Entry> findAll(long userId);

	Entry findById(long id);

	List<Entry> findByCollection(long userId, String collectionType);

	List<Entry> findByRecipeName(String name, long userId);

	List<Entry> findByIngredient(String food, long userId);

	int changeComments(String comments, long entryId);

	int changeCollection(String collectionType, long entryId);

	int changeCategory(String category, long entryId);
}