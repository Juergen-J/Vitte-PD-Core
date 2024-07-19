package ru.vitte.online.helpdesk.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vitte.online.helpdesk.dto.IssueDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewIssuePublisher {

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;

    private static final String TOPIC_NAME = "new.user";


    public void sendToTopic(IssueDto message) {
        try {
            String jsonMessage = mapper.writeValueAsString(message);
            jmsTemplate.convertAndSend(TOPIC_NAME, jsonMessage);
            log.info("Message sent to topic!");
        } catch (JsonProcessingException e) {
            log.error("Failed to send message", e);
        }
    }
}
