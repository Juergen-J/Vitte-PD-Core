package ru.vitte.online.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    @PostMapping
    public String submitContactForm(@RequestParam("name") String name,
                                    @RequestParam("email") String email,
                                    @RequestParam("message") String message,
                                    RedirectAttributes redirectAttributes) {
        // Здесь вы можете добавить логику для обработки сообщения, например, отправить email
        // Например, вызвать emailService.sendEmail(...);

        redirectAttributes.addFlashAttribute("message", "Ваше сообщение успешно отправлено!");
        return "redirect:/contact";
    }

    @GetMapping
    public String showContactForm() {
        return "contact";
    }
}
