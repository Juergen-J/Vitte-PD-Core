package ru.vitte.online.helpdesk.dto;

import jakarta.persistence.*;
import lombok.*;
import ru.vitte.online.helpdesk.entity.IssueEntity;

@Data
public class FileDto {

    private String name;

    private String path;
}
