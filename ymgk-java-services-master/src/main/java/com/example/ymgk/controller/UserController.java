package com.example.ymgk.controller;
import com.example.ymgk.entity.Customer;
import com.example.ymgk.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RepositoryRestController
@RequestMapping("/user/search")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("signIn")
    public ResponseEntity<String> addUser(@RequestBody Customer user) {
        userService.add(user);
        return new ResponseEntity<>("User successfully registered",
                HttpStatus.CREATED);
    }

    @GetMapping("signUp")
    public ResponseEntity<Boolean> controlUser(@RequestParam("userName") String userName,
            @RequestParam("password") String password) {

        return new ResponseEntity<>(userService.control(userName, password), HttpStatus.OK);
    }
}
