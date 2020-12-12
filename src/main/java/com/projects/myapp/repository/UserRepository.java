package com.projects.myapp.repository;

import java.util.List;
import java.util.Optional;

import com.projects.myapp.objects.User;

public interface UserRepository {

    int count();

    int save(User user);

    int saveRole(String username);

    int deleteById(Long id);
    
    int disable(String username);

    int enable(String username);

    int changePassword(String username, String password);

    int changeFirstName(String username, String firstName);

    int changeLastName(String username, String lastName);

    int changeRole(String username, String role);

    List<User> findAll();

	List<User> findByUsername(String username);
	
	List<User> findByFirstName(String first_name);

    Optional<User> findById(Long id);

    String getUsernameById(Long id);

    long getIdByUsername(String username);

}