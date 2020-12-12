package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.objects.Photo;

public interface PhotoRepository {

	List<Photo> findByEntry(Long entryId);

	List<String> deletePhotos(Long entryId);

	int savePhoto(Photo photo);

	String deletePhoto(Long photoId);
	
}