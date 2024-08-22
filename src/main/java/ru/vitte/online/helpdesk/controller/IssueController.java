package ru.vitte.online.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueDto;
import ru.vitte.online.helpdesk.mock.SecurityModuleMock;
import ru.vitte.online.helpdesk.service.api.IssueService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/helpdesk/issue")
public class IssueController {
    private final IssueService issueService;
    private final SecurityModuleMock securityModuleMock;


//    @PostMapping
//    public ResponseEntity<IssueDto> createIssue(@RequestBody IncomingIssueDto issueDto) {
//        var persId = securityModuleMock.getUserPersId();
//        IssueDto createdIssue = issueService.createIssue(persId, issueDto);
//        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
//    }

//    @GetMapping("/{issueId}")
//    public ResponseEntity<IssueDto> getIssueById(@PathVariable Long issueId) {
//        var persId = securityModuleMock.getUserPersId();
//        var isEmployee = securityModuleMock.isEmployee();
//        IssueDto issueDto = issueService.getIssueById(persId, issueId, isEmployee);
//        return ResponseEntity.ok(issueDto);
//    }

//    @GetMapping
//    public ResponseEntity<List<IssueDto>> getAllIssues() {
//        var persId = securityModuleMock.getUserPersId();
//        var isEmployee = securityModuleMock.isEmployee();
//        List<IssueDto> issues = issueService.getAllIssues(persId, isEmployee);
//        return ResponseEntity.ok();
//    }

//    @PutMapping("/{issueId}")
//    public ResponseEntity<IssueDto> updateIssue(@PathVariable Long issueId, @RequestBody IncomingIssueDto issueDto) {
//        var persId = securityModuleMock.getUserPersId();
//        IssueDto updatedIssue = issueService.updateIssue(persId, issueId, issueDto);
//        return ResponseEntity.ok(updatedIssue);
//    }

//    @DeleteMapping("/{issueId}")
//    public ResponseEntity<Void> deleteIssue(@PathVariable Long issueId) {
//        var persId = securityModuleMock.getUserPersId();
//        issueService.deleteIssue(persId, issueId);
//        return ResponseEntity.noContent().build();
//    }

//    @PostMapping("/{issueId}")
//    public ResponseEntity<Void> closeIssue(@PathVariable Long issueId) {
//        var persId = securityModuleMock.getUserPersId();
//        issueService.closeIssue(persId, issueId);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
