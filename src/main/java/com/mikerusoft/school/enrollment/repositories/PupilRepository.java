package com.mikerusoft.school.enrollment.repositories;

import com.mikerusoft.school.enrollment.model.Pupil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PupilRepository extends JpaRepository<Pupil, Long> {
}
