package com.project.entity;
import com.project.entity.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_version")
@Getter
@Setter
@ToString
public class ProjectVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_version_id")
    private Long projectVersionId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "version")
    private String version;

    @Column(name = "version_description")
    private String versionDescription;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "estimated_end_date")
    private LocalDateTime estimatedEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "progress")
    private Integer progress;


}

