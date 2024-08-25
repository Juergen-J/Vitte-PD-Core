package ru.vitte.online.helpdesk.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vitte.online.helpdesk.config.AIProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatGPTClient implements AIClient {

    private final AIProperties aiProperties;

    @Override
    public String fetchAutoAnswer(String prompt) {

        try {
            URL obj = new URL(aiProperties.url());
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + aiProperties.apiKey());
            connection.setRequestProperty("Content-Type", "application/json");

            prompt = "Ответ дай по русски:" + prompt;
            String cleanedPrompt = prompt.replaceAll("\\s+", " ").trim();
            String body = "{\"model\": \"" + aiProperties.model() + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + cleanedPrompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content") + 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }
}
