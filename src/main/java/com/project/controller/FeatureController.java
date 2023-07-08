package com.project.controller;


import com.project.entity.*;
import com.project.entity.enums.UserRole;
import com.project.helper.CookieHelper;
import com.project.service.FeatureService;
import com.project.service.JwtTokenService;
import com.project.service.ProjectVersionService;
import com.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/feature")
public class FeatureController {
    @Autowired
    private FeatureService featureService;
    @Autowired
    private ProjectVersionService projectVersionService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private CookieHelper cookieHelper;
    @Autowired
    private JwtTokenService jwtTokenService;
    @GetMapping("/{featureId}")
    public String getFeatureById(@PathVariable Integer featureId, Model model, HttpServletRequest request) throws Exception {
        cookieHelper.addCookieAttributes(request, model);
        Feature feature = featureService.getFeatureById(featureId);
        List<Task> tasks = featureService.getTasksByFeatureId(featureId);

        String token = jwtTokenService.getTokenFromRequest(request);
        User user = jwtTokenService.getUserFromToken(token);
        if (user.getRole().equals(UserRole.USER)) {
            UserProjectVersion upv = featureService.getUPVByFeatureIdAndUserId(featureId, user.getUserId());
            model.addAttribute("upv", upv);
        }
//        Phân quyền cho các user
        model.addAttribute("feature", feature);
        model.addAttribute("tasks", tasks);
        return "feature/feature";
    }

    @GetMapping("/{featureId}/edit")
    public String showEditFeatureForm(@PathVariable Integer featureId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        Feature feature = featureService.getFeatureById(featureId);
        model.addAttribute("feature", feature);
        return "feature/edit";
    }

    @PostMapping("/{featureId}/edit")
    public String updateFeature(@PathVariable Integer featureId,
                                @ModelAttribute("feature") Feature feature,
                                RedirectAttributes redirectAttributes) {
        try {
            feature.setId(featureId);
            feature.setEnable(true);
            featureService.updateFeature(feature);
            redirectAttributes.addFlashAttribute("message", "Updated Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/feature/" + featureId + "/edit";
        }
        return "redirect:/feature/" + featureId;
    }

    @PostMapping("/{featureId}/delete")
    public String deleteFeature(@PathVariable Integer featureId, RedirectAttributes redirectAttributes) {
        Integer projectVersionId = featureService.getFeatureById(featureId).getProjectVersion().getProjectVersionId();
        try {
             featureService.deleteFeature(featureId);
            redirectAttributes.addFlashAttribute("message", "Deleted Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/version/"+ projectVersionId ;
    }

    @GetMapping("/{featureId}/add-task")
    public String showAddTaskForm(@PathVariable Integer featureId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        Task task = new Task();
        task.setEnable(true);
        task.setFeature(featureService.getFeatureById(featureId));
        model.addAttribute("task", task);
        return "task/add";
    }

    @PostMapping("/{featureId}/add-task")
    public String addTask(@PathVariable Integer featureId,
                          @ModelAttribute("task") Task task,
                          RedirectAttributes redirectAttributes) {
        try {
            task.setFeature(featureService.getFeatureById(featureId));
            task.setEnable(true);
            taskService.addTask(task);
            redirectAttributes.addFlashAttribute("message", "Added Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/feature/" + featureId + "/add-task";
        }
        return "redirect:/feature/" + featureId;
    }
}

