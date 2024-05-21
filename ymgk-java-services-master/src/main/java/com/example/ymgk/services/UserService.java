package com.example.ymgk.services;

import com.example.ymgk.repository.UserRepository;

import java.util.List;

import com.example.ymgk.entity.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void add(Customer user) {
        userRepository.save(user);
    }

    public boolean control(String userName, String password) {
        List<Customer> liste = userRepository.getProduct(userName, password);
        if (liste.size() >= 1)
            return true;
        return false;
    }
}
