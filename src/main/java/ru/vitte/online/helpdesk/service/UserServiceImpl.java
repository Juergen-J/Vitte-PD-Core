package ru.vitte.online.helpdesk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.mapper.PersonMapper;
import ru.vitte.online.helpdesk.repository.PersonRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PersonRepository personRepository;

    private final PersonMapper mapper = PersonMapper.INSTANCE;

    @Override
    public PersonDto addUser(PersonDto userDto) {
        final var savedUser = personRepository.save(mapper.mapPerson(userDto));
        log.debug("new user was successful saved {}", savedUser);
        return mapper.mapPerson(savedUser);
    }

    @Override
    public void deleteUser(Long id) {

    }
}
