package ru.vitte.online.helpdesk.service.api;

import ru.vitte.online.helpdesk.dto.PersonDto;

public interface UserService {

    PersonDto addUser(PersonDto userDto, String userId);

    void deleteUser(Long id);
}
