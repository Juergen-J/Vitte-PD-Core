package ru.vitte.online.helpdesk.ai.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vitte.online.helpdesk.ai.mq.GeneratedAnswerPublisher;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueCommentDto;
import ru.vitte.online.helpdesk.dto.IssueDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoAnswerGeneratorImpl implements AutoAnswerGenerator {

    private final AIClient aiClient;
    private final GeneratedAnswerPublisher answerPublisher;

    @Override
    public void generateAndSendAnswer(IssueDto issueDto) {
        String autoAnswer = aiClient.fetchAutoAnswer(issueDto.getIssueComment().getFirst().getText());


        var newComment = new IssueCommentDto();
        newComment.setText(autoAnswer);

        var newIssueDto = new IncomingIssueDto();
        newIssueDto.setIssueComment(newComment);
        newIssueDto.setId(issueDto.getId());

        answerPublisher.sendToTopic(newIssueDto);
    }
}
