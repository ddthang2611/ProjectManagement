package com.project.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class ProjectVersionDTO {
    private ProjectVersion projectVersion;
    private List<FeatureDTO> features;
    private List<UserDTO> attendees;


    public ProjectVersionDTO(ProjectVersion projectVersion, List<FeatureDTO> features, List<UserDTO> attendees) {
        this.projectVersion = projectVersion;
        this.features = features;
        this.attendees = attendees;
    }
}
