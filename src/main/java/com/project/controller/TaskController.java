package com.project.controller;

import com.project.entity.*;
import com.project.entity.enums.UserRole;
import com.project.helper.CookieHelper;
import com.project.service.IssueService;
import com.project.service.JwtTokenService;
import com.project.service.TaskService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private IssueService issueService;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieHelper cookieHelper;

    @Autowired
    private JwtTokenService jwtTokenService;

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable Integer taskId, Model model, HttpServletRequest request) throws Exception {
        cookieHelper.addCookieAttributes(request, model);
        Task task = taskService.getTaskById(taskId);
        List<Issue> issues = taskService.getIssuesByTaskId(taskId);
        List<User> attendees = taskService.findAttendeesByTaskId(taskId);

        String token = jwtTokenService.getTokenFromRequest(request);
        User user = jwtTokenService.getUserFromToken(token);
        if (user.getRole().equals(UserRole.USER)) {
            UserProjectVersion upv = taskService.getUPVByTaskIdAndUserId(taskId,user.getUserId());
            model.addAttribute("upv", upv);
        }
//        Phân quyền cho các user
        model.addAttribute("task", task);
        model.addAttribute("issues", issues);
        model.addAttribute("attendees",attendees);
        return "task/task";
    }

    @GetMapping("/{taskId}/edit")
    public String showEditTaskForm(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        TaskDTO task = taskService.getTaskDTOById(taskId);
        model.addAttribute("task", task);
        return "task/edit";
    }

    @PostMapping("/{taskId}/edit")
    public String updateTask(@PathVariable Integer taskId,
                             @ModelAttribute("task") TaskDTO task,
                             RedirectAttributes redirectAttributes) {
        try {
            task.setTaskId(taskId);
            taskService.updateTask(task);
            redirectAttributes.addFlashAttribute("message", "Updated Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/task/" + taskId +"/edit";
        }
        return "redirect:/task/" + taskId;
    }

    @PostMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Integer taskId, RedirectAttributes redirectAttributes) {
        Task task = taskService.getTaskById(taskId);
        int featureId = task.getFeature().getId();
        try {

            taskService.deleteTask(taskId);
            redirectAttributes.addFlashAttribute("message", "Deleted Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/feature/"+featureId;
    }

    @GetMapping("/{taskId}/add-issue")
    public String showAddIssueForm(@PathVariable Integer taskId, Model model, HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        Issue issue = new Issue();
        model.addAttribute("issue", issue);
        return "issue/add";
    }

    @PostMapping("/{taskId}/add-issue")
    public String addIssue(@PathVariable Integer taskId,
                           @ModelAttribute("issue") Issue issue,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) throws Exception {
        String token = jwtTokenService.getTokenFromRequest(request);
        User user = jwtTokenService.getUserFromToken(token);

        try {
            issue.setTask(taskService.getTaskById(taskId));
            issue.setReporter(user);
            issueService.addIssue(issue);
            redirectAttributes.addFlashAttribute("message", "Added Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/task/" + taskId + "/add-issue";
        }
        System.out.println("redirect:/task/" + taskId);
        return "redirect:/task/" + taskId;
    }
    @PostMapping("/{taskId}/assign")
    public String assignTask(@PathVariable Integer taskId, @RequestParam Integer userId, RedirectAttributes redirectAttributes) {
        Task task = taskService.getTaskById(taskId);
        User assignedUser = userService.getUserById(userId);

        try {
            // Gán người dùng được chọn vào task
            task.setAssignedTo(assignedUser);
            taskService.save(task);

            redirectAttributes.addFlashAttribute("message", "Task assigned successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/task/" + taskId;
    }


}

