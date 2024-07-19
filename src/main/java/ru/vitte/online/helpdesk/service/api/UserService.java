package ru.vitte.online.helpdesk.service.api;

import ru.vitte.online.helpdesk.dto.PersonDto;

public interface UserService {

    PersonDto addUser(PersonDto userDto);

    void deleteUser(Long id);
}
