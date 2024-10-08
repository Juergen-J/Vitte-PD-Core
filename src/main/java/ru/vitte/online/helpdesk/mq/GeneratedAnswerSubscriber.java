package ru.vitte.online.helpdesk.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.vitte.online.helpdesk.ai.service.AutoAnswerGenerator;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueDto;
import ru.vitte.online.helpdesk.service.api.IssueService;

@Component
@RequiredArgsConstructor
@Slf4j
public class GeneratedAnswerSubscriber {
    private static final String TOPIC_NAME = "new.autogenerated.answer";
    private final ObjectMapper objectMapper;
    private final IssueService issueService;

    @JmsListener(destination = TOPIC_NAME)
    public void receiveTopic(String jsonMessage) {
        try {
            IncomingIssueDto message = objectMapper.readValue(jsonMessage, IncomingIssueDto.class);
            log.info("Received message from topic: {}", message);
            issueService.updateIssueWithAutogeneratedAnswer(message);

        } catch (Exception e) {
            log.error("Error", e);
        }
    }
}
