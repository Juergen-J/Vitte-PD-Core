package ru.vitte.online.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vitte.online.helpdesk.dto.PersonDto;
import ru.vitte.online.helpdesk.entity.enums.Role;
import ru.vitte.online.helpdesk.service.UserServiceImpl;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdmController {

    private final UserServiceImpl userService;

    @GetMapping("/admin/users/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new PersonDto());
        return "add-user";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@AuthenticationPrincipal OidcUser oidcUser,
                          @RequestParam("firstName") String firstName,
                          @RequestParam("lastName") String lastName,
                          @RequestParam("email") String email,
                          @RequestParam("role") Role role,
                          RedirectAttributes redirectAttributes) {

        PersonDto userDto = new PersonDto();
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setRole(role);

        userService.addUser(userDto, oidcUser.getSubject());

        redirectAttributes.addFlashAttribute("message", "Пользователь успешно добавлен!");
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        List<PersonDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin-dashboard";
    }

    @PostMapping("/admin/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("message", "Пользователь успешно удален!");
        return "redirect:/admin/dashboard";
    }



}
