package ru.vitte.online.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vitte.online.helpdesk.entity.IssueEntity;
import ru.vitte.online.helpdesk.entity.PersonEntity;
import ru.vitte.online.helpdesk.entity.enums.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

    List<IssueEntity> findByStatus(Status status);

    List<IssueEntity> findByUser(PersonEntity user);
}
