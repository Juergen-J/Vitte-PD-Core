package ru.vitte.online.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.service.KeycloakService;
import ru.vitte.online.helpdesk.service.api.UserService;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserService userService;
    private final KeycloakService keycloakService;

    @GetMapping
    public String getProfile(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String email = oidcUser.getEmail();
        PersonDto user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@AuthenticationPrincipal OidcUser oidcUser,
                                @RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                RedirectAttributes redirectAttributes) {

        String email = oidcUser.getEmail();
        PersonDto user = userService.findByEmail(email);

        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            userService.updateUser(user);

            keycloakService.updateUser(user.getKeycloakUserId(), email, firstName, lastName, user.getRole());

            redirectAttributes.addFlashAttribute("message", "Данные успешно обновлены!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден.");
        }

        return "redirect:/profile";
    }
}
