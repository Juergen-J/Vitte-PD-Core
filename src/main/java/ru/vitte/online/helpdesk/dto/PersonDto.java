package ru.vitte.online.helpdesk.dto;

import lombok.*;
import ru.vitte.online.helpdesk.entity.enums.Role;

@Data
public class PersonDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Role role;

    private String keycloakUserId;
}
