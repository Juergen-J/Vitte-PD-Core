package ru.vitte.online.helpdesk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.mapper.PersonMapper;
import ru.vitte.online.helpdesk.repository.PersonRepository;
import ru.vitte.online.helpdesk.service.api.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PersonRepository personRepository;
    private final KeycloakService keycloakService;
    private final PersonMapper mapper = PersonMapper.INSTANCE;

    @Override
    public PersonDto addUser(PersonDto userDto, String userId) {
        // Сохранение пользователя в локальной базе данных
        final var savedUser = personRepository.save(mapper.mapPerson(userDto));
        log.debug("New user was successfully saved {}", savedUser);

        keycloakService.createUser(
                userDto.getEmail(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                "12345678"
        );

        return mapper.mapPerson(savedUser);
    }


    @Override
    public void deleteUser(Long id) {

    }

    public List<PersonDto> getAllUsers() {
        return personRepository.findAll().stream()
                .map(mapper::mapPerson)
                .collect(Collectors.toList());
    }
}
