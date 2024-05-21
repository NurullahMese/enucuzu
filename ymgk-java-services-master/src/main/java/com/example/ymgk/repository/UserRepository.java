package com.example.ymgk.repository;

import java.util.UUID;
import java.util.List;

import com.example.ymgk.entity.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
// import org.springframework.web.bind.annotation.RequestBody;

@Repository()
public interface UserRepository extends JpaRepository<Customer, UUID> {

    @Query("Select c from Customer c Where c.userName=:userName and c.password=:password")
    List<Customer> getProduct(@Param("userName") String userName,
            @Param("password") String password);
    // List<Customer> getProduct(@RequestBody Customer user);

}
