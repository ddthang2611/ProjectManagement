package com.project.controller;
import com.project.entity.*;
import com.project.entity.enums.UserRole;
import com.project.helper.CookieHelper;
import com.project.service.EmployeeAnalysisService;
import com.project.service.ProjectVersionAnalysisService;
import com.project.service.ProjectVersionService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AnalysisController {

    @Autowired
    private EmployeeAnalysisService employeeAnalysisService;
    @Autowired
    private ProjectVersionService projectVersionService;
    @Autowired
    private ProjectVersionAnalysisService projectVersionAnalysisService;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieHelper cookieHelper;

    @GetMapping("/analysis")
    public String getAnalysisMainPage(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        return "analysis/MainPage";
    }

    @GetMapping("/analysis/user")
    public String getUserAnalysis(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        List<UserDTO> users = userService.getAllUsers();
        users.removeIf(user -> user.getRole().equals(UserRole.MANAGER));
        List<EmployeeAnalysis> employeeAnalyses = new ArrayList<>();

        for (UserDTO user : users) {
            EmployeeAnalysis employeeAnalysis = employeeAnalysisService.getEmployeeAnalysis(user.getUserId());
            employeeAnalyses.add(employeeAnalysis);
        }

        model.addAttribute("employeeAnalyses", employeeAnalyses);

        return "analysis/EmployeeAnalysis";
    }
    @GetMapping("/analysis/pie-chart/user")
    public String getUserAnalysisPieChart(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        List<UserDTO> users = userService.getAllUsers();
        users.removeIf(user -> user.getRole().equals(UserRole.MANAGER));
        List<Map<String, Object>> employeeTasks = new ArrayList<>();

        for (UserDTO user : users) {
            int employeeId = user.getUserId();
            int tasksCount = employeeAnalysisService.countTasksByEmployeeId(employeeId);
            String employeeName = user.getUsername();
            System.out.println(employeeId+":"+tasksCount);
            Map<String, Object> employeeTaskMap = new HashMap<>();
            employeeTaskMap.put("userName", employeeName);
            employeeTaskMap.put("taskCount", tasksCount);
            employeeTasks.add(employeeTaskMap);
        }

        model.addAttribute("employeeTasks", employeeTasks);

        int totalTasks = employeeAnalysisService.getTotalTask();
        System.out.println(totalTasks);
        model.addAttribute("totalTasks", totalTasks);

        return "analysis/EmployeePieChart";
    }
    @GetMapping("/analysis/projectVersion")
    public String getProjectVersionAnalysis(Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);

        List<ProjectVersion> projectVersions = projectVersionService.findAll();
        List<ProjectVersionAnalysis> projectVersionAnalyses = new ArrayList<>();

        for (ProjectVersion projectVersion : projectVersions) {
            ProjectVersionAnalysis projectVersionAnalysis = projectVersionAnalysisService.getProjectVersionAnalysis(projectVersion.getProjectVersionId());
            projectVersionAnalyses.add(projectVersionAnalysis);
        }

        model.addAttribute("projectVersionAnalyses", projectVersionAnalyses);

        return "analysis/VersionAnalysis";
    }


}

