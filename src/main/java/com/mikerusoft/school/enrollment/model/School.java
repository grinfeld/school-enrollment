package com.mikerusoft.school.enrollment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@Entity
@Table(name = "school")
public class School {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long id;

    @JsonProperty("Lat")
    @Column(name = "Lat")
    private Double lat;
    @JsonProperty("Lon")
    @Column(name = "Lon")
    private Double lon;

    private Integer minimumGpa;
    private Integer maxNumberOfPupils;

    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL, orphanRemoval = true)
    @lombok.Builder.Default
    private List<Pupil> pupils = new ArrayList<>();
}
