package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.Photo;

public interface PhotoRepository {

	List<Photo> findByEntry(Long entryId);

	int savePhoto(String url, Long entryId);

	int deletePhoto(Long photoId);
	
}