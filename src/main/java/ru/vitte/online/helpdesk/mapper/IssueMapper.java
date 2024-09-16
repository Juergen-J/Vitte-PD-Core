package ru.vitte.online.helpdesk.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.vitte.online.helpdesk.dto.IncomingIssueDto;
import ru.vitte.online.helpdesk.dto.IssueCommentDto;
import ru.vitte.online.helpdesk.dto.IssueDto;
import ru.vitte.online.helpdesk.dto.NotificationDto;
import ru.vitte.online.helpdesk.entity.IssueCommentEntity;
import ru.vitte.online.helpdesk.entity.IssueEntity;

import java.util.List;

@Mapper
public interface IssueMapper {


    @Mapping(target = "issueComment", source = "issueComment", qualifiedByName = "wrapList")
    IssueEntity mapIssue(IncomingIssueDto issueDto);

    IssueDto mapIssue(IssueEntity issueEntity);

    IssueCommentEntity mapIssueComment(IssueCommentDto issueCommentDto);

    IssueCommentDto mapIssueComment(IssueCommentEntity issueCommentEntity);

    @Named("wrapList")
    default List<IssueCommentEntity> wrapList(IssueCommentDto object) {
        return List.of(mapIssueComment(object));
    }

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "linkToConversation", source = "id", qualifiedByName = "mapLink")
    @Mapping(target = "correlationId", source = "id")
    NotificationDto mapToNotificationDto(IssueEntity updatedIssue);

    @Named("mapLink")
    default String mapLink(Long id) {
        return "/issues/" + id;
    }
}
