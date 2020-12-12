package com.projects.myapp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.projects.myapp.repository.PhotoRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoDataController {
	@Autowired
	private PhotoRepository photoRepository;

	// TODO: create method to add photo

	@RequestMapping(value = "/photo/delete", method = RequestMethod.POST)
	public int photosDelete(@RequestBody String jsonPhotoIDs) throws ParseException, IOException {
		// for every photo ID in the request, this method delete the photo from the database
		// and then deletes its corresponding local file

		int result = 0;

		JSONObject obj = (JSONObject) new JSONParser().parse(jsonPhotoIDs);
		JSONArray ids = (JSONArray) obj.get("IDs");

		List<String> urls = new ArrayList<String>();

		for(int i=0; i<ids.size(); i++){
			String url = photoRepository.deletePhoto(Long.parseLong(ids.get(i).toString()));
			urls.add(url);
		}

		// delete stored photos
		for(int i=0; i<urls.size(); i++){
			try  {         
				File f= new File("/var/tmp/"+urls.get(i));
				if(f.delete()) {
					System.out.println(f.getName() + " deleted"); 
				}  
				else {  
					System.out.println("Failed to delete file");  
				}  
			}  catch(Exception e)  {  
				e.printStackTrace(); 
				result = -1; 
			} 
		}

		return result;
	}
}