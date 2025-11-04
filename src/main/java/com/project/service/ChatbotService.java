package com.project.service;

import com.project.entity.ChatMessage;
import com.project.entity.ChatResponse;
import com.project.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatbotService {

    // Cấu hình endpoint riêng biệt
    private String USER_CHAT_API_URL = "http://localhost:2100/api/v1/user/chat";
    private String MANAGER_CHAT_API_URL = "http://localhost:2100/api/chat";

    @Autowired
    private ChatMessageRepository chatRepo;

    // RestTemplate riêng cho từng role (dễ mở rộng cấu hình, interceptor)
    private final RestTemplate userRestTemplate = new RestTemplate();
    private final RestTemplate managerRestTemplate = new RestTemplate();

    /**
     * Gửi tin nhắn tới chatbot, chọn RestTemplate theo role
     */
    public String sendMessage(Long projectId, String userMessage, String role, Long userId) {
        // 1️⃣ Lưu tin nhắn người dùng
        ChatMessage userMsg = new ChatMessage();
        userMsg.setProjectId(projectId);
        userMsg.setSender(role.toLowerCase());
        userMsg.setContent(userMessage);
        userMsg.setCreatedAt(LocalDateTime.now());
        chatRepo.save(userMsg);

        // 2️⃣ Chọn RestTemplate & API URL dựa theo role
        String apiUrl;
        RestTemplate restTemplate;
        if (role.equalsIgnoreCase("manager")) {
            apiUrl = MANAGER_CHAT_API_URL;
            restTemplate = managerRestTemplate;
        } else {
            apiUrl = USER_CHAT_API_URL;
            restTemplate = userRestTemplate;
        }

        // 3️⃣ Chuẩn bị request body và headers
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("project_id", projectId);
        requestBody.put("user_id", userId);
        requestBody.put("message", userMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // 4️⃣ Gọi API thật
        String botReply;
        try {
            ResponseEntity<ChatResponse> response =
                    restTemplate.postForEntity(apiUrl, entity, ChatResponse.class);

            if (response.getBody() != null && response.getBody().getData() != null) {
                botReply = response.getBody().getData();
            } else {
                botReply = "⚠️ Chatbot không trả về phản hồi hợp lệ.";
            }
        } catch (Exception e) {
            botReply = "⚠️ Không thể kết nối đến API chatbot (" + role + ").";
        }

        // 5️⃣ Lưu phản hồi bot
        ChatMessage botMsg = new ChatMessage();
        botMsg.setProjectId(projectId);
        botMsg.setSender("bot");
        botMsg.setContent(botReply);
        botMsg.setCreatedAt(LocalDateTime.now());
        chatRepo.save(botMsg);

        return botReply;
    }

    /**
     * Lấy toàn bộ lịch sử hội thoại theo project
     */
    public List<ChatMessage> getHistory(Long projectId) {
        return chatRepo.findByProjectIdOrderByCreatedAtAsc(projectId);
    }
}
