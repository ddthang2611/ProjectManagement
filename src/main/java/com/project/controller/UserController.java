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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    JwtTokenService jwtTokenService;

    @GetMapping("/add")
    public String addUser(Model model) {
        User userRequest = new User();
        model.addAttribute("userRequest", userRequest);
        return "user/add";
    }

    @PostMapping("/add")
    public String addUser(HttpServletRequest request, @ModelAttribute("userRequest") User userRequest, RedirectAttributes redirectAttributes) {
        try {
            if(userRequest.getRole() == UserRole.ADMIN){
                throw new Exception("You do not right to create Admin account");
            }
            User user = userService.addUser(userRequest.getUsername(), userRequest.getPassword(), userRequest.getRole());    redirectAttributes.addFlashAttribute("message", "User has been added successfully");
            return "redirect:/user";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("message", errorMessage);
            return "redirect:/user";
        }
    }






    @PostMapping("/update-role/{id}")
    public String updateUserRole(HttpServletRequest request, @PathVariable Integer id,
                                 @RequestParam("newRole") UserRole newRole,
                                 RedirectAttributes redirectAttributes) {
        try {
            System.out.println(newRole);
            if(newRole == UserRole.ADMIN){
                throw new Exception("Access Denied");
            }
            User updatedUser = userService.updateUserRole(id, newRole);
            redirectAttributes.addFlashAttribute("message", "User role has been updated successfully");
            return "redirect:/user";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("message", errorMessage);
            return "redirect:/user";
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updateUserPassword(@RequestBody String password, HttpServletRequest request) {
//        try {
//            // Lấy người dùng hiện tại từ token
//            User currentUser = jwtTokenService.getUserFromToken(request);
//
//            // Cập nhật mật khẩu của người dùng
//            User updatedUser = userService.updatePassword(currentUser.getUserId(), password);
//
//            return ResponseEntity.ok(updatedUser);
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return  null;
    }
    @GetMapping
    public String getAllUsers(Model model,HttpServletRequest request, HttpServletResponse response) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }
    @PostMapping("/deactivate/{id}")
    public String deleteUser(HttpServletRequest request, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        System.out.println("delete");
        try {
            userService.deactivateUser(id);
            redirectAttributes.addFlashAttribute("message", "User has been deactivated successfully");
            return "redirect:/user";
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            redirectAttributes.addFlashAttribute("message", errorMessage);
            return "redirect:/user";
        }
    }


}
