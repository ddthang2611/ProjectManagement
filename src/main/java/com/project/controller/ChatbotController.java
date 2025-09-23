package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.entity.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/chatbot")
public class ChatbotController {

    private final List<Message> messages = new ArrayList<>();

    @GetMapping
    public String getChatPage(Model model) {
        model.addAttribute("messages", messages);
        return "chatbot/chatbot"; 
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam("message") String content) {
        // Thêm message từ user
        messages.add(new Message("user", content));

        // Tạo phản hồi tạm thời từ bot
        String botReply = "Bot trả lời: " + content;
        messages.add(new Message("bot", botReply));

        // Redirect để tránh lỗi F5 gửi lại form
        return "redirect:/chat";
    }
}
