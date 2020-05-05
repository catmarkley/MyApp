package com.projects.myapp.repository;

import com.projects.myapp.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository{
	@Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from user", Integer.class);
    }

    @Override
    public int save(User user) {
        return jdbcTemplate.update(
                "insert into user (FirstName, LastName, Username, Password, Enabled) values(?, ?, ?, ?, 1)",
                user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
    }

    @Override
    public int saveRole(String username){
        return jdbcTemplate.update(
            "insert into Authority (Username, Authority) values (?, ?)", 
            username, "ROLE_USER");
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete user where id = ?",
                id);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "select * from user",
                (rs, rowNum) ->
                        new User(
                                rs.getLong("ID"),
								rs.getString("FirstName"),
								rs.getString("LastName"),
								rs.getString("Username"),
                                rs.getString("Password"),
                                rs.getBoolean("Enabled")
                        )
        );
	}
	
	@Override
    public List<User> findByUsername(String username) {
        return jdbcTemplate.query(
                "select * from user where Username = ?",
                new Object[]{username},
                (rs, rowNum) ->
                        new User(
                                rs.getLong("id"),
								rs.getString("FirstName"),
								rs.getString("LastName"),
								rs.getString("Username"),
                                rs.getString("Password"),
                                rs.getBoolean("Enabled")
                        )
        );
	}
	
	@Override
    public List<User> findByFirstName(String first_name) {
        return jdbcTemplate.query(
                "select * from user where FirstName = ?",
                new Object[]{first_name},
                (rs, rowNum) ->
                        new User(
                                rs.getLong("id"),
								rs.getString("FirstName"),
								rs.getString("LastName"),
								rs.getString("Username"),
                                rs.getString("Password"),
                                rs.getBoolean("Enabled")
                        )
        );
    }

    @Override
    public Optional<User> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from user where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new User(
                                rs.getLong("id"),
								rs.getString("FirstName"),
								rs.getString("LastName"),
								rs.getString("Username"),
                                rs.getString("Password"),
                                rs.getBoolean("Enabled")
                        ))
        );
    }

    @Override
    public String getUsernameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select Username from user where id = ?",
                new Object[]{id},
                String.class
        );
    }

}