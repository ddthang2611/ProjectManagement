package com.project.controller;

import com.project.entity.JwtResponse;
import com.project.entity.User;
import com.project.entity.UserDTO;
import com.project.entity.enums.UserRole;
import com.project.service.JwtTokenService;
import com.project.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static org.jvnet.fastinfoset.EncodingAlgorithmIndexes.UUID;
@Component
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    JwtTokenService jwtTokenService;
    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = userService.checkLogin(user);
        if (isAuthenticated) {
            user = userService.getUserByUsername(user.getUsername());
            String jwtToken = jwtTokenService.generateToken(user.getUserId(), user.getRole());
            JwtResponse response = new JwtResponse(jwtToken);
            redirectAttributes.addFlashAttribute("response", response);
            System.out.println("hi"+jwtToken);
            return "redirect:/user";
        } else {
            System.out.println("redirect to login");
            redirectAttributes.addFlashAttribute("message", "Invalid username or password");
            return "redirect:/login";
        }
    }



}

