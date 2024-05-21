package com.example.ymgk.repository;

import java.util.List;
import java.util.UUID;

import com.example.ymgk.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "product")
public interface ProductRepository extends JpaRepository<Product,UUID>
{
    @Query("SELECT p from Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', concat(:name, concat('%')))) order by p.price asc")
    Page<Product> findCheapestProduct(@Param("name") String name, Pageable p);

    @Query(value = "SELECT p.name,min(p.price) from Product p Group by p.name",nativeQuery = true)
    Page<Product> getProducts(Pageable p);

    @RestResource(path = "getProductForMinPrice")
    Page<Product> findAllByOrderByPrice(Pageable p);

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    List<Product> getProduct(@Param("id") UUID id);
}
