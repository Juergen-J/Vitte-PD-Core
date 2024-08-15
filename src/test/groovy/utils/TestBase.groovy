package utils

import ru.vitte.online.helpdesk.dto.FileDto
import ru.vitte.online.helpdesk.dto.IncomingIssueDto
import ru.vitte.online.helpdesk.dto.IssueCommentDto
import ru.vitte.online.helpdesk.entity.FileEntity
import ru.vitte.online.helpdesk.entity.IssueCommentEntity
import ru.vitte.online.helpdesk.entity.IssueEntity
import ru.vitte.online.helpdesk.entity.PersonEntity
import ru.vitte.online.helpdesk.entity.enums.Role
import ru.vitte.online.helpdesk.entity.enums.Status
import spock.lang.Specification

import java.time.LocalDateTime

class TestBase extends Specification {

    static final String ISSUE_TEXT = "Issue text"
    static final String ISSUE_TITLE = "Issue title"
    static final Long TEST_PERS_ID = 3L
    static final String TEST_FIRSTNAME = "Firstname"
    static final String TEST_LASTNAME = "Lastname"
    static final String TEST_EMAIL = "email@example.com"


// DTO`s
    def issueCommentDto(Map args = [:]) {
        new IssueCommentDto(
                text: args.get('text', ISSUE_TEXT),
                file: args.get('file', fileDto())
        )
    }

    def incomingIssueDto(Map args = [:]) {
        new IncomingIssueDto(
                id: args.get('id', 0L),
                title: args.get('title', ISSUE_TITLE),
                issueComment: args.get('issueComment', issueCommentDto())
        )
    }

    def fileDto(Map args = [:]) {
        new FileDto(
                name: args.get('name', "test_file_name"),
                path: args.get('path', "test_file_path")
        )
    }


//    Entities

    def personEntity(Map args = [:]) {
        new PersonEntity(
                id: args.get('id', TEST_PERS_ID),
                firstName: args.get('firstName', TEST_FIRSTNAME),
                lastName: args.get('lastName', TEST_LASTNAME),
                email: args.get('email', TEST_EMAIL),
                role: args.get('role', Role.USER)
        )
    }

    def fileEntity(Map args = [:]) {
        new FileEntity(
                id: args.get('id', 0L),
                name: args.get('name', "test_file_name"),
                path: args.get('path', "test_file_path")
        )
    }

    def issueCommentEntity(Map args = [:]) {
        new IssueCommentEntity(
                id: args.get('id', 0L),
                text: args.get('text', ISSUE_TEXT),
                file: args.get('file', fileEntity())
        )
    }

    def issueEntity(Map args = [:]) {
        new IssueEntity(
                id: args.get('id', 0L),
                status: args.get('status', Status.NEW),
                title: args.get('title', ISSUE_TITLE),
                user: args.get('user', personEntity(role: Role.USER)),
                employee: args.get('employee', personEntity(role: Role.EMPLOYEE)),
                issueComment: args.get('issueComment', [issueCommentEntity()]),
                createdAt: args.get('createdAt', LocalDateTime.now()),
                updatedAt: args.get('updatedAt', LocalDateTime.now())
        )
    }


}
