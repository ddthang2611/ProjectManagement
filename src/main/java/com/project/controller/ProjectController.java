package com.project.controller;

import com.project.entity.Project;
import com.project.entity.ProjectDTO;
import com.project.entity.ProjectVersion;
import com.project.service.ProjectService;
import com.project.service.ProjectVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private ProjectService projectService;

    private ProjectVersionService projectVersionService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectVersionService projectVersionService) {
        this.projectService = projectService;
        this.projectVersionService = projectVersionService;
    }

    @GetMapping
    public String getAllProjects(Model model) {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for (Project project : projects) {
            List<ProjectVersion> projectVersions = projectVersionService.getProjectVersionsByProjectId(project.getProjectId());
            ProjectDTO projectDTO = new ProjectDTO(project, projectVersions);
            projectDTOs.add(projectDTO);
        }
        model.addAttribute("projectDTOs", projectDTOs);
        return "project/projects";
    }


    @GetMapping("/{projectId}")
    public String getProjectById(@PathVariable Integer projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        List<ProjectVersion> projectVersions = projectVersionService.getProjectVersionsByProjectId(project.getProjectId());
        ProjectDTO projectDTO = new ProjectDTO(project, projectVersions);
        model.addAttribute("projectDTO", projectDTO);
        return "project/project";
    }

    @GetMapping("/create")
    public String showCreateProjectForm(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "project/create";
    }

    @PostMapping("/create")
    public String createProject(@ModelAttribute("project") Project project) {
        projectService.createProject(project);
        return "redirect:/project/list";
    }

    @GetMapping("/{projectId}/edit")
    public String showEditProjectForm(@PathVariable Integer projectId, Model model) {
        Project project = projectService.getProjectById(projectId);
        List<ProjectVersion> projectVersions = projectVersionService.getProjectVersionsByProjectId(project.getProjectId());
        ProjectDTO projectDTO = new ProjectDTO(project, projectVersions);
        model.addAttribute("projectDTO", projectDTO);
        return "project/edit";
    }

    @PostMapping("/{projectId}/edit")
    public String updateProject(@ModelAttribute("project") Project project) {
        projectService.updateProject(project);
        return "redirect:/project/list";
    }

    @PostMapping("/{projectId}/disable")
    public String disableProject(@PathVariable Integer projectId) {
        projectService.disableProject(projectId);
        return "redirect:/project/list";
    }

    @GetMapping("/{projectId}/versions")
    public String getProjectVersionsByProjectId(@PathVariable Integer projectId, Model model) {
        List<ProjectVersion> projectVersions = projectService.getProjectVersionsByProjectId(projectId);
        model.addAttribute("projectVersions", projectVersions);
        return "project/versions";
    }

    @PostMapping("/version/{projectVersionId}/disable")
    public String disableProjectVersion(@PathVariable Integer projectVersionId) {
        projectVersionService.disableProjectVersion(projectVersionId);
        return "redirect:/project/list";
    }
}
