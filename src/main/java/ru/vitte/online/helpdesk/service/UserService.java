package ru.vitte.online.helpdesk.service;

import ru.vitte.online.helpdesk.dto.PersonDto;

import java.util.List;

public interface UserService {

    PersonDto addUser(PersonDto userDto);

    void deleteUser(Long id);
}
