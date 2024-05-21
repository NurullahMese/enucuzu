package com.example.ymgk.repository;


import java.util.UUID;

import com.example.ymgk.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "category")
public interface CategoryRepository extends JpaRepository<Category,UUID> 
{}
