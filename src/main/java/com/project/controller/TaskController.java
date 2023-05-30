package com.project.controller;

import com.project.entity.Issue;
import com.project.entity.Task;
import com.project.service.IssueService;
import com.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final IssueService issueService;

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
        Task task = taskService.getTaskById(taskId);
        model.addAttribute("task", task);
        return "task/editTask";
    }

    @PostMapping("/{taskId}/edit")
    public String updateTask(@PathVariable Integer taskId,
                             @ModelAttribute("task") Task task,
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
        try {
            taskService.deleteTask(taskId);
            redirectAttributes.addFlashAttribute("message", "Deleted Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/task";
    }

    // Other methods related to TaskController

}

