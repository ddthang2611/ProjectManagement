package com.project.controller;

import com.project.entity.Issue;
import com.project.entity.Task;
import com.project.entity.User;
import com.project.service.IssueService;
import com.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;
    @Autowired
    private TaskService taskService;


    @PostMapping("/{issueId}/delete")
    public String deleteIssue(@PathVariable Integer issueId, RedirectAttributes redirectAttributes) {
        Issue issue = issueService.getIssueById(issueId);
        Integer taskId = issue.getTask().getTaskId();

        try {
            issueService.deleteIssue(issueId);
            redirectAttributes.addFlashAttribute("message", "Deleted Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        // Redirect to the appropriate page
        return "redirect:/task/"+taskId;
    }

    @GetMapping("/{issueId}/edit")
    public String showEditIssueForm(@PathVariable Integer issueId, Model model) {
        Issue issue = issueService.getIssueById(issueId);
        model.addAttribute("issue", issue);
        return "issue/edit";
    }

    @PostMapping("/{issueId}/edit")
    public String updateIssue(@PathVariable Integer issueId,
                              @ModelAttribute("issue") Issue issue,
                              RedirectAttributes redirectAttributes, HttpSession session) {
        System.out.println("1");
        User user = (User) session.getAttribute("user");
        System.out.println("2");
        System.out.println("3"+user.getUserId());

        try {
            issue.setIssueId(issueId);
            issue.setReporter(user);
            issueService.updateIssue(issue);
            redirectAttributes.addFlashAttribute("message", "Updated Successfully");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/issue/" + issueId + "/edit";
        }
        // Redirect to the appropriate page
        return "redirect:/issue/" + issueId;
    }

    @GetMapping("/{issueId}")
    public String getIssueById(@PathVariable Integer issueId, Model model) {
        Issue issue = issueService.getIssueById(issueId);
        model.addAttribute("issue", issue);
        return "issue/issue";
    }


}

