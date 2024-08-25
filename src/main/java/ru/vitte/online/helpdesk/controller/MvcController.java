package ru.vitte.online.helpdesk.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.vitte.online.helpdesk.dto.FileDto;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueCommentDto;
import ru.vitte.online.helpdesk.dto.IssueDto;
import ru.vitte.online.helpdesk.service.FileService;
import ru.vitte.online.helpdesk.service.api.IssueService;
import ru.vitte.online.helpdesk.utils.TokenExtractor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MvcController {

    private final LogoutHandler keycloakLogoutHandler;
    private final IssueService issueService;

    private final FileService fileService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "index";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpServletRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser) {
            keycloakLogoutHandler.logout(request, null, authentication);
        }
        request.logout();
        return new RedirectView("/");
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String username = oidcUser != null ? oidcUser.getName() : "Guest";
        String email = oidcUser != null ? oidcUser.getEmail() : "No Email";
        var isEmployee = TokenExtractor.isEmployee(oidcUser);

        List<IssueDto> issues = issueService.getAllIssues(email, isEmployee);

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("issues", issues);
        return "dashboard";
    }

    @GetMapping("/issues/{id}")
    public String viewIssue(@PathVariable Long id, @AuthenticationPrincipal OidcUser oidcUser, Model model) {
        String email = oidcUser != null ? oidcUser.getEmail() : "No Email";
        var isEmployee = TokenExtractor.isEmployee(oidcUser);
        IssueDto issue = issueService.getIssueById(email, id, isEmployee);
        model.addAttribute("issue", issue);
        model.addAttribute("userEmail", oidcUser != null ? oidcUser.getEmail() : "");
        model.addAttribute("isEmployee", isEmployee);
        return "view-issue";
    }


    @PostMapping("/issues/{id}/comments")
    public String addComment(@PathVariable Long id,
                             @RequestParam("text") String text,
                             @RequestParam("file") MultipartFile file,
                             @AuthenticationPrincipal OidcUser oidcUser,
                             RedirectAttributes redirectAttributes) throws IOException {

        String email = oidcUser != null ? oidcUser.getEmail() : "No Email";
        IncomingIssueDto incomingIssueDto = new IncomingIssueDto();
        incomingIssueDto.setId(id);

        IssueCommentDto commentDto = new IssueCommentDto();
        commentDto.setText(text);

        if (!file.isEmpty()) {
            FileDto fileDto = fileService.saveFile(file);
            commentDto.setFile(fileDto);
        }
        incomingIssueDto.setIssueComment(commentDto);
        var isEmployee = TokenExtractor.isEmployee(oidcUser);
        issueService.updateIssue(email, id, isEmployee, incomingIssueDto);

        redirectAttributes.addFlashAttribute("message", "Комментарий успешно добавлен!");

        return "redirect:/issues/" + id;
    }

    @GetMapping("/issues/new")
    public String showCreateIssueForm(Model model) {
        model.addAttribute("issue", new IncomingIssueDto());
        return "new-issue";
    }

    @PostMapping("/issues/new")
    public String createIssue(@RequestParam("title") String title,
                              @RequestParam("text") String text,
                              @RequestParam("file") MultipartFile file,
                              @AuthenticationPrincipal OidcUser oidcUser,
                              RedirectAttributes redirectAttributes) throws IOException {

        String email = oidcUser != null ? oidcUser.getEmail() : "No Email";

        IncomingIssueDto incomingIssueDto = new IncomingIssueDto();
        incomingIssueDto.setTitle(title);

        IssueCommentDto commentDto = new IssueCommentDto();
        commentDto.setText(text);

        if (!file.isEmpty()) {
            FileDto fileDto = fileService.saveFile(file);
            commentDto.setFile(fileDto);
        }

        incomingIssueDto.setIssueComment(commentDto);

        issueService.createIssue(email, incomingIssueDto);

        redirectAttributes.addFlashAttribute("message", "Задание успешно создано!");

        return "redirect:/dashboard";
    }

    @PostMapping("/issues/{id}/delete")
    public String deleteIssue(@PathVariable Long id,
                              @AuthenticationPrincipal OidcUser oidcUser,
                              RedirectAttributes redirectAttributes) {

        String email = oidcUser != null ? oidcUser.getEmail() : "No Email";

        try {
            issueService.deleteIssue(email, id);
            redirectAttributes.addFlashAttribute("message", "Задание успешно удалено!");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при удалении задания: " + ex.getMessage());
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/issues/{id}/close")
    public String closeIssue(@PathVariable Long id,
                             @AuthenticationPrincipal OidcUser oidcUser,
                             RedirectAttributes redirectAttributes) {

        String email = oidcUser != null ? oidcUser.getEmail() : "No Email";
        boolean isEmployee = TokenExtractor.isEmployee(oidcUser);

        try {
            issueService.closeIssue(email, id, isEmployee);
            redirectAttributes.addFlashAttribute("message", "Задание успешно закрыто!");
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при закрытии задания: " + ex.getMessage());
        }

        return "redirect:/issues/" + id;
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) throws IOException {
        FileDto fileDto = fileService.getFile(id);
        if (fileDto == null) {
            return ResponseEntity.notFound().build();
        }

        Path path = Paths.get(fileDto.getPath());
        Resource resource = new FileSystemResource(path.toFile());

        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("File not found or not readable");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.probeContentType(path)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getName()+ "\"")
                .body(resource);
    }



}
