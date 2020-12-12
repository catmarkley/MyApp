package com.projects.myapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.projects.myapp.repository.PhotoRepository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {

	@Autowired
	private PhotoRepository photoRepository;

	@RequestMapping(value = "/img", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public List<String> fileUpload(@RequestParam("file") MultipartFile[] files) throws IOException {

		if(files.length == 0){
			return null;
		}

		Random rand = new Random();
		int rand_int = rand.nextInt(10000);

		List<String> paths = new ArrayList<String>();
		for(int i=0; i< files.length; i++){
			MultipartFile file = files[i];
			String name = rand_int + "_" + file.getOriginalFilename();
			File convertFile = new File("/var/tmp/"+name);
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			paths.add(name);
		}
		return paths;
	}

	@RequestMapping(value = "/img/delete", method = RequestMethod.POST)
	public int fileDeleteLocal(@RequestBody String jsonFilenames) throws ParseException, IOException {
		// this method deletes locally created files 
		// and is used when the file paths were not able to get stored in the database

		int result = 0;

		JSONObject obj = (JSONObject) new JSONParser().parse(jsonFilenames);
		JSONArray names = (JSONArray) obj.get("names");

		// delete stored photos
		for(int i=0; i<names.size(); i++){
			try  {         
				File f= new File("/var/tmp/"+names.get(i).toString());
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

	@RequestMapping(value = "/img/{entryID}", method = RequestMethod.DELETE)
	public int fileDelete(@PathVariable("entryID") Long entryID) throws ParseException, IOException {
		// this method deletes all photos associated with a specific entry, locally and in the database

		// start with the database
		List<String> deletedURLs = photoRepository.deletePhotos(entryID);

		int result = 0;

		// delete stored photos
		for(int i=0; i<deletedURLs.size(); i++){
			try  {         
				File f= new File("/var/tmp/"+deletedURLs.get(i).toString());
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

	@RequestMapping(value = "/img/{name}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getEntryPhotoById(@PathVariable("name") String name) throws ParseException, IOException {
		File file = Paths.get("/var/tmp/"+name).toFile();
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		
		ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(
		   file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
		
		return responseEntity;
	}
}