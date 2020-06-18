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
                "delete from user where id = ?",
                id);
    }

    @Override
    public int disable(String username){
        return jdbcTemplate.update(
                "update user set Enabled = 0 where Username = ?",
                username);
    }

    @Override
    public int enable(String username){
        return jdbcTemplate.update(
                "update user set Enabled = 1 where Username = ?",
                username);
    }

    @Override
    public int changePassword(String username, String password){
        return jdbcTemplate.update(
            "update user set Password = ? where Username = ?",
            password, username);
    }

    @Override
    public int changeFirstName(String username, String firstName){
        return jdbcTemplate.update(
            "update user set FirstName = ? where Username = ?",
            firstName, username);
    }

    @Override
    public int changeLastName(String username, String lastName){
        return jdbcTemplate.update(
            "update user set LastName = ? where Username = ?",
            lastName, username);
    }

    @Override
    public int changeRole(String username, String role){
        return jdbcTemplate.update(
            "update Authority set Authority = ? where Username = ?",
            role, username);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(
                "select ID, LastName, FirstName, User.Username, Password, Enabled, Authority from User left join Authority on User.Username = Authority.Username",
                (rs, rowNum) ->
                        new User(
                                rs.getLong("ID"),
								rs.getString("FirstName"),
								rs.getString("LastName"),
								rs.getString("User.Username"),
                                rs.getString("Password"),
                                rs.getBoolean("Enabled"),
                                rs.getString("Authority")
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

    @Override
    public long getIdByUsername(String username){
        return jdbcTemplate.queryForObject(
            "select id from user where username = ?",
            new Object[]{username},
            Long.class
        );
    }

}