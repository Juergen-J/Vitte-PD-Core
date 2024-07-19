package ru.vitte.online.helpdesk.ai.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.vitte.online.helpdesk.ai.service.AutoAnswerGenerator;
import ru.vitte.online.helpdesk.dto.IssueDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewIssueSubscriber {
    private static final String TOPIC_NAME = "new.user";
    private final ObjectMapper objectMapper;
    private final AutoAnswerGenerator autoAnswerGenerator;

    @JmsListener(destination = TOPIC_NAME)
    public void receiveTopic(String jsonMessage) {
        try {
            IssueDto message = objectMapper.readValue(jsonMessage, IssueDto.class);
            log.info("Received message from topic: {}", message);
            autoAnswerGenerator.generateAndSendAnswer(message);

        } catch (Exception e) {
            log.error("Error", e);
        }
    }
}
