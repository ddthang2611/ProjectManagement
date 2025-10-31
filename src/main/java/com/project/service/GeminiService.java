package com.project.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class GeminiService {

        @Value("${gemini.api.key}")
        private String apiKey;

        public String generateSQLFromPrompt(String prompt, String xAxis, String yAxis) {
                try {
                        String fullPrompt = String.format(
                                        "Hãy sinh ra câu truy vấn SQL MySQL để tạo dữ liệu cho biểu đồ.\n" +
                                                        "CSDL có các bảng: project, project_version, feature, task, issue, user.\n"
                                                        +
                                                        "Trục hoành: %s, trục tung: %s.\n" +
                                                        "Yêu cầu: %s.\n" +
                                                        "Chỉ trả lại câu SQL hợp lệ, alias các cột là x và y.",
                                        xAxis, yAxis, prompt);

                        JSONObject content = new JSONObject()
                                        .put("contents", List.of(
                                                        new JSONObject().put("parts", List.of(
                                                                        new JSONObject().put("text", fullPrompt)))));

                        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-pro-preview-03-25:generateContent?key="
                                                        + apiKey))

                                        .header("Content-Type", "application/json")
                                        .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                                        .build();
                        HttpClient client = HttpClient.newHttpClient();
                        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                        System.out.println("Gemini raw response: " + response.body());

                        if (response.statusCode() != 200 || response.body().contains("\"error\"")) {
                                return "SELECT 'Gemini API error' AS x, 0 AS y;";
                        }

                        JSONObject json = new JSONObject(response.body());
                        JSONArray parts = json
                                        .getJSONArray("candidates")
                                        .getJSONObject(0)
                                        .getJSONObject("content")
                                        .getJSONArray("parts");

                        String text = parts.getJSONObject(0).getString("text");
                        return text.replaceAll("```sql", "").replaceAll("```", "").trim();

                } catch (Exception e) {
                        e.printStackTrace();
                        return "SELECT 'Lỗi sinh SQL' AS x, 0 AS y;";
                }
        }
}
