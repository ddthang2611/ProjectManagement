package com.project.controller;

import com.project.entity.ChatMessage;
import com.project.service.ChatbotService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/api/v1/user")
public class ChatbotUserController {

    @Autowired
    private ChatbotService chatbotService;

    // API tương ứng với: POST /api/v1/user/chat
    @PostMapping("/chat")
    public List<ChatMessage> chat(@RequestBody Map<String, Object> payload) {
        Long projectId = Long.valueOf(payload.get("project_id").toString());
        Long userId = Long.valueOf(payload.get("user_id").toString());
        String message = payload.get("message").toString();

        // role có thể bỏ qua nếu không truyền từ client
        String role = payload.containsKey("role") ? payload.get("role").toString() : "user";

        chatbotService.sendMessage(projectId, message, role, userId);
        return chatbotService.getHistory(projectId);
    }

    @GetMapping("/chat/history/{projectId}")
    public List<ChatMessage> getHistory(@PathVariable Long projectId) {
        return chatbotService.getHistory(projectId);
    }
}
