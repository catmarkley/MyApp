package com.projects.myapp.repository;

import java.util.List;

import com.projects.myapp.objects.Photo;

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
	public List<String> deletePhotos(Long entryID){
		List<String> URLs = jdbcTemplate.query(
			"select url from Photo where EntryID  = ?",
			new Object[]{entryID},
			(rs, rowNum) ->
					rs.getString("url")
		);

		int result = jdbcTemplate.update(
			"delete from Photo where EntryID = ?",
			entryID);

		return URLs;
	}

	@Override
	public int savePhoto(Photo photo) {
		return jdbcTemplate.update(
            "insert into Photo (url, entryId) values (?, ?)", 
            photo.getUrl(), photo.getEntryId());
	}

	@Override
	public String deletePhoto(Long photoId){
		String url = jdbcTemplate.queryForObject(
			"select url from Photo where id=?", 
			String.class,
			photoId);
	
		int result = jdbcTemplate.update(
                "delete from Photo where id = ?",
				photoId);
				
		return url;
	}
	
}