package ru.vitte.online.helpdesk.service;

import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueDto;

import java.util.List;

public interface IssueService {

    IssueDto createIssue(long persId, IncomingIssueDto issueDto);

    IssueDto getIssueById(long persId, long issueId, boolean isEmployee);

    List<IssueDto> getAllIssues(long persId, boolean isEmployee);

    IssueDto updateIssue(long persId, long issueId, IncomingIssueDto issueDto);

    void deleteIssue(long persId, long issueId);

    void closeIssue(Long persId, Long issueId);
}
