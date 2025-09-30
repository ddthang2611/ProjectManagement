package com.project.controller;

import com.project.entity.ChatMessage;
import com.project.helper.CookieHelper;
import com.project.repository.ChatMessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/chatbot/project")
public class ProjectChatbotController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private CookieHelper cookieHelper;

    @GetMapping("/{projectId}")
    public String getProjectChat(@PathVariable Long projectId, Model model, javax.servlet.http.HttpServletRequest request) {
        cookieHelper.addCookieAttributes(request, model);
        List<ChatMessage> messages = chatMessageRepository.findByProjectIdOrderByCreatedAtAsc(projectId);
        model.addAttribute("projectId", projectId);
        model.addAttribute("messages", messages);
        return "project/chatbot";
    }

    @PostMapping("/{projectId}/send")
    public String sendProjectMessage(@PathVariable Long projectId,
            @RequestParam("message") String content) {
        // User message
        chatMessageRepository.save(new ChatMessage(null, projectId, "user", content, LocalDateTime.now()));

        // Bot reply
        String botReply = "Bot (project " + projectId + ") trả lời: " + content;
        chatMessageRepository.save(new ChatMessage(null, projectId, "bot", botReply, LocalDateTime.now()));

        return "redirect:/chatbot/project/" + projectId;
    }

}
