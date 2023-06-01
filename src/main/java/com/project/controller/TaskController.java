package com.project.controller;

import com.project.entity.Issue;
import com.project.entity.Task;
import com.project.entity.TaskDTO;
import com.project.entity.User;
import com.project.service.IssueService;
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
    public TaskController(TaskService taskService, IssueService issueService) {
        this.taskService = taskService;
        this.issueService = issueService;
    }

    @GetMapping("/{taskId}")
    public String getTaskById(@PathVariable Integer taskId, Model model) {
        Task task = taskService.getTaskById(taskId);
        List<Issue> issues = taskService.getIssuesByTaskId(taskId);
        model.addAttribute("task", task);
        model.addAttribute("issues", issues);
        return "task/task";
    }

    @GetMapping("/{taskId}/edit")
    public String showEditTaskForm(@PathVariable Integer taskId, Model model) {
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
            return "redirect:/task/" + taskId;
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
    public String showAddIssueForm(@PathVariable Integer taskId, Model model) {
        Issue issue = new Issue();
        issue.setTask(taskService.getTaskById(taskId));
        model.addAttribute("issue", issue);
        return "issue/add";
    }

    @PostMapping("/{taskId}/add-issue")
    public String addIssue(@PathVariable Integer taskId,
                           @ModelAttribute("issue") Issue issue,
                           RedirectAttributes redirectAttributes,
                           HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userId = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    userId = cookie.getValue();
                    break;
                }
            }
        User user = userService.getUserById(Integer.parseInt(userId));
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
        return "redirect:/task/" + taskId;
    }

}

