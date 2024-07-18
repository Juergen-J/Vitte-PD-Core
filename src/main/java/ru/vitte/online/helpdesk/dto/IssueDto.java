package ru.vitte.online.helpdesk.dto;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.vitte.online.helpdesk.entity.IssueCommentEntity;
import ru.vitte.online.helpdesk.entity.PersonEntity;
import ru.vitte.online.helpdesk.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class IssueDto {

    private Long id;

    private Status status;

    private String title;

    private PersonDto user;

    private PersonDto employee;

    private List<IssueCommentDto> issueCommentEntity;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
