package ru.vitte.online.helpdesk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.entity.PersonEntity;
import ru.vitte.online.helpdesk.mapper.PersonMapper;
import ru.vitte.online.helpdesk.mq.NewUserPublisher;
import ru.vitte.online.helpdesk.repository.PersonRepository;
import ru.vitte.online.helpdesk.service.api.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final static String DEFAULT_PASSWORD = "12345678";
    private final PersonRepository personRepository;
    private final KeycloakService keycloakService;
    private final NewUserPublisher newUserPublisher;
    private final PersonMapper mapper = PersonMapper.INSTANCE;

    @Override
    public PersonDto addUser(PersonDto userDto, String userId) {
        final var savedUser = personRepository.save(mapper.mapPerson(userDto));
        log.debug("New user was successfully saved {}", savedUser);

        String keycloakUserId = keycloakService.createUser(
                userDto.getEmail(),
                userDto.getEmail(),
                userDto.getFirstName(),
                userDto.getLastName(),
                DEFAULT_PASSWORD,
                userDto.getRole()
        );

        if (keycloakUserId != null) {
            savedUser.setKeycloakUserId(keycloakUserId);
            personRepository.save(savedUser);
        }

        PersonDto personDto = mapper.mapPerson(savedUser);
        this.newUserPublisher.sendToTopic(personDto);
        return personDto;
    }


    @Override
    public void deleteUser(Long id) {
        PersonEntity userEntity = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String keycloakUserId = userEntity.getKeycloakUserId();
        if (keycloakUserId != null) {
            keycloakService.deleteUser(keycloakUserId);
        }

        personRepository.delete(userEntity);
        log.info("User deleted successfully from local database");
    }


    @Override
    public PersonDto getUserById(Long id) {
        var person = personRepository.findById(id);
        return person.map(mapper::mapPerson).orElse(null);
    }

    @Override
    public void updateUser(PersonDto userDto) {
        keycloakService.updateUser(userDto.getKeycloakUserId(), userDto.getEmail(), userDto.getFirstName(), userDto.getLastName(), userDto.getRole());
        personRepository.save(mapper.mapPerson(userDto));
    }

    @Override
    public PersonDto findByEmail(String email) {
        PersonEntity userEntity = personRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.mapPerson(userEntity);
    }

    public List<PersonDto> getAllUsers() {
        return personRepository.findAll().stream()
                .map(mapper::mapPerson)
                .collect(Collectors.toList());
    }
}
