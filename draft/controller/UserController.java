package com.sapo.edu.demo.controller;

import com.sapo.edu.demo.entity.JwtResponse;
import com.sapo.edu.demo.entity.User;
import com.sapo.edu.demo.service.JwtTokenService;
import com.sapo.edu.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    JwtTokenService jwtTokenService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("login");
        boolean isAuthenticated = userService.checkLogin(user);
        if (isAuthenticated) {
            String jwtToken = jwtTokenService.generateToken(user.getUsername());
            JwtResponse response = new JwtResponse(jwtToken);
            String token = response.getToken();

            return ResponseEntity.ok(token);

        } else {
            return new ResponseEntity<>("Login Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user, HttpServletRequest request) {
        // Kiểm tra xem user đã đăng nhập chưa

        // Thực hiện thêm user vào database


        return ResponseEntity.ok().build();
    }
}
