package com.projects.myapp.objects;

public class Photo {
	private Long id;
	private Long entryId;
	private String url;

	public Photo(Long id, Long entryId, String url){
		this.id = id;
		this.entryId = entryId;
		this.url = url;
	}

	public Photo(Long entryId, String url){
		this.entryId = entryId;
		this.url = url;
	}

	public Long getId(){
		return id;
	}

	public Long getEntryId(){
		return entryId;
	}

	public String getUrl(){
		return url;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setEntryId(Long entryId){
		this.entryId = entryId;
	}

	public void setUrl(String url){
		this.url = url;
	}
}