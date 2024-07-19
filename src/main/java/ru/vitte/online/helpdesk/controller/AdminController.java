package ru.vitte.online.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.mock.SecurityModuleMock;
import ru.vitte.online.helpdesk.service.api.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/helpdesk/admin")
public class AdminController {

    private final UserService userService;

    private final SecurityModuleMock securityModuleMock;

    @PostMapping("/users")
    public ResponseEntity<PersonDto> addUser(@RequestBody PersonDto userDto) {
        if (securityModuleMock.isAdmin()) {
            PersonDto createdUser = userService.addUser(userDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (securityModuleMock.isAdmin()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
