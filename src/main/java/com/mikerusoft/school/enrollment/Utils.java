package com.mikerusoft.school.enrollment;

import com.mikerusoft.school.enrollment.model.Grade;
import com.mikerusoft.school.enrollment.model.Pupil;
import com.mikerusoft.school.enrollment.model.School;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Utils {

    private static final double RADIUS = 6371;

    public static int numberOfFriends(School school) {
        List<Pupil> pupils = school.getPupils();
        if (pupils == null || pupils.size() == 0)
            return 0;

        Set<Long> friends = new HashSet<>();
        for (Pupil p : pupils) {
            if (p.getFriend() != null) {
                if (inSameSchool(p)) {
                    // none of friends already inserted into set
                    if (!(friends.contains(p.getId()) || friends.contains(p.getFriend().getId())))
                        friends.add(p.getId());
                }
            }
        }
        return friends.size();
    }

    private static boolean inSameSchool(Pupil p) {
        return p.getSchool() != null && p.getFriend().getSchool() != null && Objects.equals(p.getSchool().getId(), p.getFriend().getSchool().getId());
    }

    public static int gpa(Pupil pupil) {
        List<Grade> grades = pupil.getGrades();
        if (grades == null || grades.size() == 0)
            return 0;

        return (int)Math.round(grades.stream().mapToInt(Grade::getGrade).average().orElse(0));
    }

    public static double calculateHaversine(Point p1, Point p2) {
        double lat1 = Math.toRadians(p1.getLat());
        double lon1 = Math.toRadians(p1.getLon());

        double lat2 = Math.toRadians(p2.getLat());
        double lon2 = Math.toRadians(p2.getLon());

        double fi = Math.toRadians(lat2-lat1);
        double gamma = Math.toRadians(lon2-lon1);

        double a = Math.sin(fi/2) * Math.sin(fi/2) *
                Math.sin(gamma/2) * Math.sin(gamma/2) *
                Math.cos(lat1) * Math.cos(lat2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return RADIUS * c;
    }

    @Data
    @Builder(builderClassName = "Builder", toBuilder = true)
    @Value
    public static class Point {
        private double lat;
        private double lon;
    }
}
