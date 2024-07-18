package ru.vitte.online.helpdesk.service;

import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueDto;
import ru.vitte.online.helpdesk.entity.IssueEntity;
import ru.vitte.online.helpdesk.entity.PersonEntity;
import ru.vitte.online.helpdesk.entity.enums.Role;
import ru.vitte.online.helpdesk.entity.enums.Status;
import ru.vitte.online.helpdesk.mapper.IssueMapper;
import ru.vitte.online.helpdesk.repository.IssueRepository;
import ru.vitte.online.helpdesk.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;
    private final PersonRepository personRepository;
    private final IssueMapper issueMapper = Mappers.getMapper(IssueMapper.class);

    @Override
    public IssueDto createIssue(long persId, IncomingIssueDto issueDto) {
        IssueEntity issueEntity = issueMapper.mapIssue(issueDto);

        PersonEntity user = personRepository.findById(persId).orElseThrow(() -> new RuntimeException("User not found"));
        issueEntity.setUser(user);
        issueEntity.setStatus(Status.NEW);

        IssueEntity savedIssue = issueRepository.save(issueEntity);

        return issueMapper.mapIssue(savedIssue);
    }

    @Override
    public IssueDto getIssueById(long persId, long issueId, boolean isEmployee) {
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        if (!isEmployee) {
            if (!issue.getUser().getId().equals(persId)) {
                throw new RuntimeException("Access denied");
            }
        }

        return issueMapper.mapIssue(issue);
    }

    @Override
    public List<IssueDto> getAllIssues(long persId, boolean isEmployee) {
        List<IssueEntity> issues;

        if (isEmployee) {
            issues = issueRepository.findByStatus(Status.NEW);
        } else {
            PersonEntity user = personRepository.findById(persId).orElseThrow(() -> new RuntimeException("User not found"));
            issues = issueRepository.findByUser(user);
        }

        return issues.stream()
                .map(issueMapper::mapIssue)
                .collect(Collectors.toList());
    }

    @Override
    public IssueDto updateIssue(long persId, long issueId, IncomingIssueDto issueDto) {
        PersonEntity user = personRepository.findById(persId).orElseThrow(() -> new RuntimeException("User not found"));
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        if (user.getRole() == Role.EMPLOYEE) {
            issue.setEmployee(user);
        } else if (issue.getUser().getId().equals(persId)) {
        } else {
            throw new RuntimeException("Access denied");
        }

        issue.setStatus(Status.IN_PROGRESS);
        issue.getIssueComment().add(issueMapper.mapIssueComment(issueDto.getIssueComment()));

        IssueEntity updatedIssue = issueRepository.save(issue);
        return issueMapper.mapIssue(updatedIssue);
    }

    @Override
    public void deleteIssue(long persId, long issueId) {
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));

        if (issue.getUser().getId().equals(persId)) {
            issueRepository.delete(issue);
        } else {
            throw new RuntimeException("Access denied");
        }
    }

    @Override
    public void closeIssue(Long persId, Long issueId) {
        PersonEntity user = personRepository.findById(persId).orElseThrow(() -> new RuntimeException("User not found"));
        IssueEntity issue = issueRepository.findById(issueId).orElseThrow(() -> new RuntimeException("Issue not found"));
        Role userRole = user.getRole();
        if (issue.getUser().getId().equals(persId) || userRole == Role.EMPLOYEE || userRole == Role.ADMIN) {
            issue.setStatus(Status.CLOSED);
            issueRepository.save(issue);
        } else {
            throw new RuntimeException("Access denied");
        }
    }
}
