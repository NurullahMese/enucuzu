package com.example.ymgk.repository;

import java.util.UUID;

import com.example.ymgk.entity.SubCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "subcategory")
public interface SubCategoryRepository extends JpaRepository<SubCategory,UUID> 
{}