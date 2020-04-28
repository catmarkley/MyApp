package com.projects.myapp.repository;

import com.projects.myapp.Example;

import java.util.List;
import java.util.Optional;

public interface ExampleRepository {

    int count();

    int save(Example example);

    int update(Example example);

    int deleteById(Long id);

    List<Example> findAll();

    List<Example> findByName(String recipeName);

    Optional<Example> findById(Long id);

    String getNameById(Long id);

}