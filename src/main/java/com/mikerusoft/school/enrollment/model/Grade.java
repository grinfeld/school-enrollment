package com.mikerusoft.school.enrollment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder", toBuilder = true)
@Entity(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column
    private Long id;
    private Long pupil_id;
    private String couseName;
    private Integer grade;
}
