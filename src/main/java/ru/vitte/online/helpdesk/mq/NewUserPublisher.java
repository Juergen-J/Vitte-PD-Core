package ru.vitte.online.helpdesk.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.vitte.online.helpdesk.dto.IssueDto;
import ru.vitte.online.helpdesk.dto.PersonDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewUserPublisher {
    private final JmsTemplate jmsTemplate;

    private final ObjectMapper mapper;

    private static final String TOPIC_NAME = "new.user";


    public void sendToTopic(PersonDto personDto) {
        try {
            String jsonMessage = mapper.writeValueAsString(personDto);
            jmsTemplate.convertAndSend(TOPIC_NAME, jsonMessage);
            log.info("New user sent to topic!");
        } catch (JsonProcessingException e) {
            log.error("Failed to send message", e);
        }
    }
}
