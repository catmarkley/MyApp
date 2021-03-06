package com.projects.myapp.objects;

public class User {

    private Long id;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private boolean enabled;
	private String role;
	
	public User(Long id, String first_name, String last_name, String username, String password, boolean enabled){
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(Long id, String first_name, String last_name, String username, String password, boolean enabled, String role){
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	public User(String first_name, String last_name, String username, String password, boolean enabled){
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String first_name, String last_name, String username, String password, boolean enabled, String role){
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.role = role;
	}

	public Long getId(){
		return id;
	}

	public String getFirstName(){
		return first_name;
	}

	public String getLastName(){
		return last_name;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}

	public boolean getEnabled(){
		return enabled;
	}

	public String getRole(){
		return role;
	}

	public void setId(Long id){
		this.id = id;
	}

	public void setFirstName(String first_name){
		this.first_name = first_name;
	}

	public void setLastName(String last_name){
		this.last_name = last_name;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setRole(String role){
		this.role = role;
	}

	public void enable(){
		this.enabled = true;
	}

	public void disable(){
		this.enabled = false;
	}
}