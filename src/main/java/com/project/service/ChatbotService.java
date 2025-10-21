package com.project.service;

import com.project.entity.ChatMessage;
import com.project.entity.ChatResponse;
import com.project.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatbotService {

    private static final String CHAT_API_URL = "http://localhost:2100/api/chat"; // API thật của bạn

    @Autowired
    private ChatMessageRepository chatRepo;

    public String sendMessage(Long projectId, String userMessage) {
        // 1️⃣ Lưu tin nhắn người dùng
        ChatMessage userMsg = new ChatMessage();
        userMsg.setProjectId(projectId);
        userMsg.setSender("user");
        userMsg.setContent(userMessage);
        userMsg.setCreatedAt(LocalDateTime.now());
        chatRepo.save(userMsg);

        // 2️⃣ Gửi request tới API thật
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("projectId", projectId);
        requestBody.put("message", userMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String botReply;
        try {
            ResponseEntity<ChatResponse> response = restTemplate.postForEntity(CHAT_API_URL, entity, ChatResponse.class);
            botReply = response.getBody().getData();
        } catch (Exception e) {
            botReply = "⚠️ Không thể kết nối đến API chatbot.";
        }

        // 3️⃣ Lưu phản hồi bot
        ChatMessage botMsg = new ChatMessage();
        botMsg.setProjectId(projectId);
        botMsg.setSender("bot");
        botMsg.setContent(botReply);
        botMsg.setCreatedAt(LocalDateTime.now());
        chatRepo.save(botMsg);

        return botReply;
    }

    public List<ChatMessage> getHistory(Long projectId) {
        return chatRepo.findByProjectIdOrderByCreatedAtAsc(projectId);
    }
}
