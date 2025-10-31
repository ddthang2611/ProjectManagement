package com.project.controller;

import com.project.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/ai")
public class AIChartController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // --- API chính: sinh dữ liệu biểu đồ từ prompt ---
    @PostMapping("/chart")
    public Map<String, Object> generateChart(@RequestBody Map<String, String> payload) {
    Map<String, Object> result = new HashMap<>();

    List<String> labels = Arrays.asList("Project A", "Project B", "Project C");
    List<Double> values = Arrays.asList(75.0, 50.0, 90.0);

    result.put("labels", labels);
    result.put("values", values);
    result.put("chartType", payload.get("chartType"));
    result.put("sql", "SELECT project_name AS x, AVG(progress) AS y FROM project_version GROUP BY project_name");

    return result;
}

    // --- API test riêng để xem Gemini hoạt động chưa ---
    @GetMapping("/test")
    public String testGemini() {
        return geminiService.generateSQLFromPrompt(
                "Cho tôi biểu đồ số lượng task theo user",
                "username", "task_count"
        );
    }
}
