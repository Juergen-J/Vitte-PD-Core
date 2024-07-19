package ru.vitte.online.helpdesk.dto;

import lombok.*;
import ru.vitte.online.helpdesk.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IncomingIssueDto {

    private Long id;

    private String title;

    private IssueCommentDto issueComment;
}
