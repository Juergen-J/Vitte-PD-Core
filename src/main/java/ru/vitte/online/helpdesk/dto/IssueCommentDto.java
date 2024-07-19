package ru.vitte.online.helpdesk.dto;

import lombok.*;

@Data
public class IssueCommentDto {

    private String text;

    private FileDto file;
}
