package ru.vitte.online.helpdesk.ai.service;

import ru.vitte.online.helpdesk.dto.IssueDto;

public interface AutoAnswerGenerator {

    void generateAndSendAnswer(IssueDto issueDto);
}
