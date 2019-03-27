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
@Entity(name = "pupils")
public class Pupil {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long id;

    @JsonProperty("Lon")
    @Column(name = "Lon")
    private Double lon;

    @JsonProperty("Lat")
    @Column(name = "Lat")
    private Double lat;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pupil_id")
    private List<Grade> grades;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private Pupil friend;
}
