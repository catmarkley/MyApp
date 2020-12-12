package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.objects.Entry;

public interface EntryRepository {
	int count(long userId);

	long saveEntry(Entry entry);

	long updateEntry(Entry entry);
	
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