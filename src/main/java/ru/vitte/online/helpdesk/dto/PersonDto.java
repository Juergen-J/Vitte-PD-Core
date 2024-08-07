package ru.vitte.online.helpdesk.dto;

import jakarta.persistence.*;
import lombok.*;
import ru.vitte.online.helpdesk.entity.enums.Role;

@Data
public class PersonDto {

    private String firstName;

    private String lastName;

    private String email;

    private Role role;
}
