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
    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println("login");
        boolean isAuthenticated = userService.checkLogin(user);
        if (isAuthenticated) {
            user = userService.getUserByUsername(user.getUsername());
            String jwtToken = jwtTokenService.generateToken(user.getUserId(), user.getRole());
            System.out.println("login user id"+ user.getUserId());
            JwtResponse response = new JwtResponse(jwtToken);
            String token = response.getToken();
            return ResponseEntity.ok(token);

        } else {
            return new ResponseEntity<>("Login Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
        @PostMapping
        public ResponseEntity<User> addUser(@RequestBody User userRequest) {
            User user = userService.addUser(userRequest.getUsername(), userRequest.getPassword(), userRequest.getRole());
            return ResponseEntity.ok(user);
        }


    @GetMapping("/hi")
    public ResponseEntity<?> create(HttpServletRequest request) throws Exception {
//        User currentUser = jwtTokenService.getUserFromToken(request);
//        UserRole userRole = currentUser.getRole();
//        if(userRole != UserRole.ADMIN){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }

        System.out.println("create");

        return ResponseEntity.ok().build();
    }
    @GetMapping("/hi1")
    public ResponseEntity<?> create1(HttpServletRequest request) throws Exception {
//        User currentUser = jwtTokenService.getUserFromToken(request);
//        UserRole userRole = currentUser.getRole();
//        if(userRole != UserRole.ADMIN){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
//        }

        return ResponseEntity.ok().build();
    }



//    @PostMapping("delete/{id}")
//    public String deleteUser(HttpServletRequest request, @PathVariable Integer id, Model model) {
//        //request.setAttribute("Authorization", );
//
//        try {
//            userService.deleteUser(id);
//            model.addAttribute("message", "User has been deleted successfully");
//            System.out.println("delete successful");
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            model.addAttribute("message", "User has been deleted successfully");
//
//        }
//        return "fragments/hi";
//    }

    @PostMapping("/delete/{id}")
    public String deleteUser(HttpServletRequest request, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        System.out.println("delete");
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User has been deleted successfully");
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
        try {
            // Lấy người dùng hiện tại từ token
            User currentUser = jwtTokenService.getUserFromToken(request);

            // Cập nhật mật khẩu của người dùng
            User updatedUser = userService.updatePassword(currentUser.getUserId(), password);

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user/users";
    }



}
