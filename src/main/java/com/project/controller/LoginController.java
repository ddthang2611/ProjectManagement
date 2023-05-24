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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public String login(HttpSession session, @ModelAttribute User user, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = userService.checkLogin(user);
        if (isAuthenticated) {
            User userDetail = userService.getUserByUsername(user.getUsername());
            System.out.println(userDetail.getRole());
            String jwtToken = jwtTokenService.generateToken(userDetail.getUserId(), userDetail.getRole());
            System.out.println("Role "+ userDetail.getRole());
//            JwtResponse jwtResponse = new JwtResponse(jwtToken,userDetail.getUserId(), String.valueOf(userDetail.getRole()));
//            System.out.println(String.valueOf(userDetail.getRole()));
//            System.out.println("token"+jwtResponse.token);
//            System.out.println("id"+jwtResponse.userId);
//            System.out.println("role "+ jwtResponse.userRole);
            // Tạo cookie chứa token
            Cookie cookie = new Cookie("jwtToken", jwtToken);
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60); // Thời gian sống của cookie (7 ngày)
            cookie.setHttpOnly(true);
            cookie.setSecure(true); // Nếu trang web chạy trên HTTPS, hãy đặt giá trị true

            response.addCookie(cookie);
            if(userDetail.getRole().equals(UserRole.ADMIN)){
                return "redirect:/user";
            } else {
                String userRole = "ADMIN"; // Giá trị userRole bạn muốn truyền
                        session.setAttribute("userRole", userRole);

                return "redirect:/project";
            }
        } else {
            System.out.println("redirect to login");
            model.addAttribute("message", "Invalid username or password");
            model.addAttribute("messageType","error");
            return "login";
        }
    }



}

