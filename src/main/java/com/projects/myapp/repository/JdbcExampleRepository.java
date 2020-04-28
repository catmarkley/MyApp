package com.projects.myapp.repository;

import com.projects.myapp.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcExampleRepository implements ExampleRepository{
	@Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from example", Integer.class);
    }

    @Override
    public int save(Example example) {
        return jdbcTemplate.update(
                "insert into example (recipe_name) values(?)",
                example.getRecipeName());
    }

    @Override
    public int update(Example example) {
        return jdbcTemplate.update(
                "update example set recipe_name = ? where id = ?",
                example.getRecipeName(), example.getId());
    }


    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete example where id = ?",
                id);
    }

    @Override
    public List<Example> findAll() {
        return jdbcTemplate.query(
                "select * from example",
                (rs, rowNum) ->
                        new Example(
                                rs.getLong("id"),
                                rs.getString("recipe_name")
                        )
        );
    }

    @Override
    public Optional<Example> findById(Long id) {
        return jdbcTemplate.queryForObject(
                "select * from example where id = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        Optional.of(new Example(
                                rs.getLong("id"),
                                rs.getString("recipe_name")
                        ))
        );
    }

    @Override
    public List<Example> findByName(String recipeName) {
        return jdbcTemplate.query(
                "select * from example where recipe_name like ?",
                new Object[]{recipeName},
                (rs, rowNum) ->
                        new Example(
                                rs.getLong("id"),
                                rs.getString("recipe_name")
                        )
        );
    }

    @Override
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select recipe_name from example where id = ?",
                new Object[]{id},
                String.class
        );
    }

}