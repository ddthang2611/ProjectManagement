package com.project.controller;


import com.project.entity.*;
import com.project.service.FeatureService;
import com.project.service.ProjectVersionService;
import com.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/feature")
public class FeatureController {
    private FeatureService featureService;
    private ProjectVersionService projectVersionService;
    private TaskService taskService;


    @Autowired
    public FeatureController(FeatureService featureService, ProjectVersionService projectVersionService, TaskService taskService) {
        this.featureService = featureService;
        this.projectVersionService = projectVersionService;
        this.taskService = taskService;
    }

    @GetMapping("/{featureId}")
    public String getFeatureById(@PathVariable Integer featureId, Model model) {
        Feature feature = featureService.getFeatureById(featureId);
        List<Task> tasks = featureService.getTasksByFeatureId(featureId);
        model.addAttribute("feature", feature);
        model.addAttribute("tasks", tasks);
        return "feature/feature";
    }

    @GetMapping("/{featureId}/edit")
    public String showEditFeatureForm(@PathVariable Integer featureId, Model model) {
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
    public String showAddTaskForm(@PathVariable Integer featureId, Model model) {
        Task task = new Task();
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

