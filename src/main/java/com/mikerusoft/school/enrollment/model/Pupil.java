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
@Entity
@Table(name = "pupils")
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
    @JsonProperty("Grades")
    private List<Grade> grades;

    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "friend_id", referencedColumnName = "id")
    private Pupil friend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    @Override
    public String toString() {
        return "Pupil{" +
                "id=" + id +
                ", lon=" + lon +
                ", lat=" + lat +
                ", grades=" + grades +
                ", friend=" + (friend != null ? friend.getId() : "") +
                ", school=" + school +
                '}';
    }

    private String schoolToString(School school) {
        if (school == null)
            return "[]";
        return school.toBuilder().pupils(null).toString();
    }
}
