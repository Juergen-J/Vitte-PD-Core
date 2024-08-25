package ru.vitte.online.helpdesk.service.api;

import ru.vitte.online.helpdesk.dto.PersonDto;

public interface UserService {

    PersonDto addUser(PersonDto userDto, String userId);

    void deleteUser(Long id);

    PersonDto getUserById(Long id);

    void updateUser(PersonDto userDto);

    PersonDto findByEmail(String email);
}
