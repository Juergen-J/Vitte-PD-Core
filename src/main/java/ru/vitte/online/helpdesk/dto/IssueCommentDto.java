package ru.vitte.online.helpdesk.dto;

import jakarta.persistence.*;
import lombok.*;
import ru.vitte.online.helpdesk.entity.FileEntity;

@Data
public class IssueCommentDto {

    private String text;

    private FileDto fileEntity;
}
