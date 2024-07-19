package ru.vitte.online.helpdesk.ai.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeneratedAnswerPublisher {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;

    private static final String TOPIC_NAME = "new.autogenerated.answer";


    public void sendToTopic(IncomingIssueDto message) {
        try {
            String jsonMessage = mapper.writeValueAsString(message);
            jmsTemplate.convertAndSend(TOPIC_NAME, jsonMessage);
            log.info("Message sent to topic!");
        } catch (JsonProcessingException e) {
            log.error("Failed to send message", e);
        }
    }
}