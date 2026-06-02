package com.example.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AiService {

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AiService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String summarizeMedicalReport(String extractedText) throws Exception {
        if (geminiApiKey == null || geminiApiKey.trim().isEmpty()) {
            throw new RuntimeException("Chave da API do Gemini não configurada.");
        }

        String prompt = "Atue como um assistente médico especialista. " +
                "Leia o texto do seguinte laudo médico e extraia os pontos de atenção principais em um resumo claro. " +
                "Retorne os pontos de atenção EXATAMENTE como um array JSON de strings, sem marcação markdown adicional, apenas o array JSON. " +
                "Exemplo: [\"Ponto 1\", \"Ponto 2\"].\n\n" +
                "Texto do Laudo:\n" + extractedText;

        // Build request body
        String requestBody = """
                {
                  "contents": [{
                    "parts":[{"text": %s}]
                  }]
                }
                """.formatted(objectMapper.writeValueAsString(prompt));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = null;
        int maxRetries = 3;
        for (int i = 0; i < maxRetries; i++) {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                break;
            }
            if (response.statusCode() == 503 || response.statusCode() == 429) {
                if (i == maxRetries - 1) {
                    throw new RuntimeException("Erro na API do Gemini após tentativas: " + response.body());
                }
                Thread.sleep(2000); // Wait 2 seconds before retry
            } else {
                throw new RuntimeException("Erro na API do Gemini: " + response.body());
            }
        }
        
        JsonNode rootNode = objectMapper.readTree(response.body());
        JsonNode textNode = rootNode.path("candidates").path(0).path("content").path("parts").path(0).path("text");
        
        String result = textNode.asText();
        // Limpar possíveis formatações markdown de código se a IA mandar por engano
        if (result.startsWith("```json")) {
            result = result.replace("```json", "").replace("```", "").trim();
        } else if (result.startsWith("```")) {
            result = result.replace("```", "").trim();
        }

        return result;
    }
}
