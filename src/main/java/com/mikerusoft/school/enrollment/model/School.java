package com.mikerusoft.school.enrollment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@Entity(name = "schools")
public class School {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long id;

    @JsonProperty("Lat")
    @Column(name = "Lat")
    private Double lat;
    @JsonProperty("Long")
    @Column(name = "Lon")
    private Double lon;

    private Integer minimumGpa;
    private Integer maxNumberOfPupils;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pupil_id")
    private List<Pupil> pupils;
}
