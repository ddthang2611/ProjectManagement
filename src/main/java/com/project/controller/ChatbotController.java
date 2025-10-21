package com.project.controller;

import com.project.entity.ChatMessage;
import com.project.service.ChatbotService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@RestController
@RequestMapping("/chat")
public class ChatbotController {

    @Autowired
    private ChatbotService chatbotService;

    @PostMapping("/send")
    public List<ChatMessage> sendMessage(@RequestBody Map<String, String> payload) {
        Long projectId = Long.valueOf(payload.get("projectId"));
        String message = payload.get("message");

        chatbotService.sendMessage(projectId, message);
        return chatbotService.getHistory(projectId);
    }

    @GetMapping("/history/{projectId}")
    public List<ChatMessage> getHistory(@PathVariable Long projectId) {
        return chatbotService.getHistory(projectId);
    }
}
