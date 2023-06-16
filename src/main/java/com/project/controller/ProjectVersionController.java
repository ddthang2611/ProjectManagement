package com.project.controller;

import com.project.entity.*;
import com.project.helper.CookieHelper;
import com.project.service.FeatureService;
import com.project.service.ProjectVersionService;
import com.project.service.UserProjectVersionService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/version")
public class ProjectVersionController {
    @Autowired
    private ProjectVersionService projectVersionService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private UserProjectVersionService userProjectVersionService;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieHelper cookieHelper;



//    @GetMapping
//    public String getAllProjectVersions(Model model) {
//        List<ProjectVersion> projectVersions = projectVersionService.get;
//        model.addAttribute("projectVersions", projectVersions);
//        return "project-version/project-versions";
//    }

    @GetMapping("/{projectVersionId}")
    public String getProjectVersionById(@PathVariable Integer projectVersionId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        ProjectVersion projectVersion = projectVersionService.getProjectVersionById(projectVersionId);
        List<FeatureDTO> features = projectVersionService.findByProjectVersionId(projectVersionId);
        List<UserDTO> attendees = userProjectVersionService.findUsersByProjectVersionId(projectVersionId);
        List<UserDTO> users = userService.getAllUsers();

        List<UserDTO> remainingUsers = new ArrayList<>();
        for (UserDTO user : users) {
            boolean isAttendee = false;
            for (UserDTO attendee : attendees) {
                if (user.getUserId() == attendee.getUserId()) {
                    isAttendee = true;
                    break;
                }
            }
            if (!isAttendee) {
                remainingUsers.add(user);
            }
        }

        model.addAttribute("attendees", attendees);
        model.addAttribute("users", remainingUsers);
        model.addAttribute("projectVersion", projectVersion);
        model.addAttribute("features", features);
        return "version/projectVersion";
    }

    @GetMapping("/{projectVersionId}/edit")
    public String showEditProjectVersionForm(@PathVariable Integer projectVersionId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        ProjectVersion projectVersion = projectVersionService.getProjectVersionById(projectVersionId);
        model.addAttribute("projectVersion", projectVersion);
        return "version/edit";
    }

    @PostMapping("/{projectVersionId}/edit")
    public String updateProjectVersion(@PathVariable Integer projectVersionId,
                                       @ModelAttribute("projectVersion") ProjectVersion projectVersion,
                                       RedirectAttributes redirectAttributes) {
        System.out.println(projectVersion.toString());
        try {
            projectVersion.setProjectVersionId(projectVersionId);
            projectVersion.setEnable(true);
            projectVersionService.updateProjectVersion(projectVersion);

            redirectAttributes.addFlashAttribute("message", "Updated Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/version/" + projectVersionId;
        }
        return "redirect:/version/" + projectVersionId;
    }


    @PostMapping("/{projectVersionId}/disable")
    public String disableProjectVersion(@PathVariable Integer projectVersionId, RedirectAttributes redirectAttributes) {
        try {
            projectVersionService.disableProjectVersion(projectVersionId);
            redirectAttributes.addFlashAttribute("message", "Disabled Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/project";
    }

    @GetMapping("/{projectVersionId}/add-feature")
    public String showAddFeatureForm(@PathVariable Integer projectVersionId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        Feature feature = new Feature();
        feature.setProjectVersion(projectVersionService.getProjectVersionById(projectVersionId));
        model.addAttribute("feature", feature);
        return "feature/add";
    }

    @PostMapping("/{projectVersionId}/add-feature")
    public String addFeature(@PathVariable Integer projectVersionId,
                             @ModelAttribute("feature") Feature feature,
                             RedirectAttributes redirectAttributes) {

        try {
            feature.setProjectVersion(projectVersionService.getProjectVersionById(projectVersionId));
            feature.setEnable(true);
            featureService.addFeature(feature);
            redirectAttributes.addFlashAttribute("message", "Added Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/version/" + projectVersionId + "/add-feature";
        }
        return "redirect:/version/" + projectVersionId;
    }

    @PostMapping("/{projectVersionId}/add-attendee")
    public String addAttendee(@PathVariable Integer projectVersionId,
                              @RequestParam Integer userId,
                              RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(userId);
            ProjectVersion projectVersion = projectVersionService.getProjectVersionById(projectVersionId);
            UserProjectVersion userProjectVersion = new UserProjectVersion();
            userProjectVersion.setUser(user);
            userProjectVersion.setProjectVersion(projectVersion);
            userProjectVersionService.add(userProjectVersion);
            redirectAttributes.addFlashAttribute("message", "Added Attendee Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/version/" + projectVersionId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/version/" + projectVersionId;
    }
    @GetMapping("/user/{userId}")
    public String getProjectVersionsByUserId(@PathVariable Integer userId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        List<ProjectVersion> projectVersions = userProjectVersionService.findProjectVersionsByUserId(userId);
        List<ProjectVersionDTO> projectVersionDTOs = new ArrayList<>(); // Danh sách ProjectVersionDTO

        for (ProjectVersion projectVersion : projectVersions) {

            // Lấy danh sách Feature của ProjectVersion
            List<FeatureDTO> features = projectVersionService.findByProjectVersionId(projectVersion.getProjectVersionId());

            List<UserDTO> attendees = userProjectVersionService.findUsersByProjectVersionId(projectVersion.getProjectVersionId());

            ProjectVersionDTO projectVersionDTO = new ProjectVersionDTO(projectVersion, features, attendees);
            projectVersionDTOs.add(projectVersionDTO);
        }



        model.addAttribute("projectVersionDTOs", projectVersionDTOs); // Truyền danh sách ProjectVersionDTO

        return "version/userProjectVersion";
    }



}

