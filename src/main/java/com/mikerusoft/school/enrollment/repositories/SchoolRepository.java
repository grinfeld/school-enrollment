package com.mikerusoft.school.enrollment.repositories;

import com.mikerusoft.school.enrollment.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("SELECT s from School s WHERE s.maxNumberOfPupils >= (SELECT COUNT(p) FROM Pupil p WHERE p.school=s) AND s.minimumGpa < ?1")
    List<School> findSchoolsWithFreeSpaces(int gpa);

}
