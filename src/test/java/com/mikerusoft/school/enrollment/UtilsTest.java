package com.mikerusoft.school.enrollment;

import com.mikerusoft.school.enrollment.model.Pupil;
import com.mikerusoft.school.enrollment.model.School;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Nested
    class NumberOfFriendsTests {

        @Test
        void noPupils_expected0Friends() {
            School school = new School();
            assertEquals(0, Utils.numberOfFriends(school));
        }

        @Test
        void pupilWithoutFirends_expected0Friends() {
            School school = new School();
            school.setPupils(Collections.singletonList(createPupil(1L, school)));
            assertEquals(0, Utils.numberOfFriends(school));
        }

        @Test
        void twoPupilsWithoutFirends_expected0Friends() {
            School school = new School();
            school.setPupils(Arrays.asList(createPupil(1L, school), createPupil(2L, school)));
            assertEquals(0, Utils.numberOfFriends(school));
        }

        @Test
        void twoFriendPupils_expected1Friends() {
            School school = new School();
            Pupil pupil1 = createPupil(1L, school);
            Pupil pupil2 = createPupil(2L, school);
            pupil1.setFriend(pupil2);
            pupil2.setFriend(pupil1);
            school.setPupils(Arrays.asList(pupil1, pupil2));
            assertEquals(1, Utils.numberOfFriends(school));
        }

        @Test
        void fourFriendPupils_expected2Friends() {
            School school = new School();
            Pupil pupil1 = createPupil(1L, school);
            Pupil pupil2 = createPupil(2L, school);
            Pupil pupil3 = createPupil(3L, school);
            Pupil pupil4 = createPupil(4L, school);
            pupil1.setFriend(pupil2);
            pupil2.setFriend(pupil1);
            pupil3.setFriend(pupil4);
            pupil4.setFriend(pupil3);
            school.setPupils(Arrays.asList(pupil1, pupil2, pupil3, pupil4));
            assertEquals(2, Utils.numberOfFriends(school));
        }

    }

    private static Pupil createPupil(long id, School school) {
        Pupil pupil = new Pupil();
        pupil.setId(id);
        pupil.setSchool(school);
        return pupil;
    }

}