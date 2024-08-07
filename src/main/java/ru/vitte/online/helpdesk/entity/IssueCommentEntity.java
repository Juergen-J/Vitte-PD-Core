package ru.vitte.online.helpdesk.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IssueCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000)
    private String text;
    @OneToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

//    @ManyToOne
//    @JoinColumn(name = "issue_id", nullable = false)
//    private IssueEntity issue;
}
