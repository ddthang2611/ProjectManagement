package com.project.controller;

import com.project.entity.*;
import com.project.service.ProjectVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/version")
public class ProjectVersionController {
    @Autowired
    private ProjectVersionService projectVersionService;

    @Autowired
    public ProjectVersionController(ProjectVersionService projectVersionService) {
        this.projectVersionService = projectVersionService;
    }

//    @GetMapping
//    public String getAllProjectVersions(Model model) {
//        List<ProjectVersion> projectVersions = projectVersionService.get;
//        model.addAttribute("projectVersions", projectVersions);
//        return "project-version/project-versions";
//    }

    @GetMapping("/{projectVersionId}")
    public String getProjectVersionById(@PathVariable Integer projectVersionId, Model model) {
        ProjectVersion projectVersion = projectVersionService.getProjectVersionById(projectVersionId);
        List<FeatureDTO> features = projectVersionService.findByProjectVersionId(projectVersionId);
        System.out.println(projectVersion.toString());
        model.addAttribute("projectVersion", projectVersion);
        model.addAttribute("features", features);
        return "version/projectVersion";
    }

    @GetMapping("/{projectVersionId}/edit")
    public String showEditProjectVersionForm(@PathVariable Integer projectVersionId, Model model) {
        ProjectVersion projectVersion = projectVersionService.getProjectVersionById(projectVersionId);
        model.addAttribute("projectVersion", projectVersion);
        return "version/edit";
    }

    @PostMapping("/{projectVersionId}/edit")
    public String updateProjectVersion(@PathVariable Integer projectVersionId,
                                       @ModelAttribute("projectVersion") ProjectVersion projectVersion,
                                       RedirectAttributes redirectAttributes) {

        try {
            projectVersion.setProjectVersionId(projectVersionId);
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
        System.out.println("hi");
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

}

