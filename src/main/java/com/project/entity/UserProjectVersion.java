package com.project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
@Entity
@Table(name = "user_project_version")
@Getter
@Setter
@ToString
public class UserProjectVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_version_id")
    private ProjectVersion projectVersion;

    @Column(name = "add_permission")
    private boolean add;
    @Column(name = "edit_permission")
    private boolean edit;
    @Column(name = "delete_permission")
    private boolean delete;
}

