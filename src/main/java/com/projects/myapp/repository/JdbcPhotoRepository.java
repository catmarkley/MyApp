package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.Photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPhotoRepository implements PhotoRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
    public List<Photo> findByEntry(Long EntryId) {
		return jdbcTemplate.query(
			"select ID, url from photo where EntryID = ?",
			new Object[]{EntryId},
			(rs, rowNum) ->
					new Photo(
							rs.getLong("ID"),
							EntryId,
							rs.getString("url")
					)
		);
	}

	@Override
	public int savePhoto(String url, Long entryId) {
		return jdbcTemplate.update(
            "insert into Photo (url, entryId) values (?, ?)", 
            url, entryId);
	}

	@Override
	public int deletePhoto(Long photoId){
		return jdbcTemplate.update(
                "delete from Photo where id = ?",
                photoId);
	}
	
}