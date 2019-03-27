package com.mikerusoft.school.enrollment.controllers;

import com.mikerusoft.school.enrollment.Utils;
import com.mikerusoft.school.enrollment.model.Pupil;
import com.mikerusoft.school.enrollment.model.School;
import com.mikerusoft.school.enrollment.repositories.PupilRepository;
import com.mikerusoft.school.enrollment.repositories.SchoolRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class PupilController {

    private PupilRepository pupilRepository;
    private SchoolRepository schoolRepository;

    public PupilController(PupilRepository pupilRepository, SchoolRepository schoolRepository) {
        this.pupilRepository = pupilRepository;
        this.schoolRepository = schoolRepository;
    }

    @PostMapping(path = "/school")
    public Long createSchool(@RequestBody School school) {
        return schoolRepository.saveAndFlush(school).getId();
    }

    @PostMapping(path = "/pupil")
    public Long createPupil(@RequestBody Pupil pupil) {
        return pupilRepository.saveAndFlush(pupil).getId();
    }

    @PostMapping(path = "/setFriendShip/{firstPupilId}/{secondPupilId}")
    public void setFriendShip(@PathVariable("firstPupilId") Long firstPupilId, @PathVariable("secondPupilId") Long secondPupilId) {
        // actually it should be moved in some service and set @Transactional annotation
        Pupil pupil1st = pupilRepository.getOne(firstPupilId);
        Pupil firstFriend = pupil1st.getFriend();
        if (firstFriend != null) {
            firstFriend.setFriend(null);
            pupilRepository.saveAndFlush(firstFriend);
        }
        Pupil pupil2nd = pupilRepository.getOne(secondPupilId);
        pupil1st.setFriend(pupil2nd);
        pupil2nd.setFriend(pupil1st);

        pupilRepository.saveAndFlush(pupil1st);
    }

    @PostMapping(path = "/enroll/{pupilId}")
    public void enroll(@PathVariable("pupilId") long pupilId) {
        // same as previous method: it should be moved in some service and set @Transactional annotation
        Pupil pupil = pupilRepository.getOne(pupilId);

        List<School> schoolWithFreeSpaces = schoolRepository.findSchoolsWithFreeSpaces(Utils.gpa(pupil));
        if (schoolWithFreeSpaces == null || schoolWithFreeSpaces.size() == 0)
            throw new RuntimeException("There is no school for you child");

        double maxDistance = Double.MIN_VALUE;
        School bestSchool = null;
        for (School school : schoolWithFreeSpaces) {
            double haversine = Utils.calculateHaversine(
                    Utils.Point.builder().lat(school.getLat()).lon(school.getLon()).build(),
                    Utils.Point.builder().lat(pupil.getLat()).lon(pupil.getLon()).build()
            );
            int numOfFriends = Utils.numberOfFriends(school);
            if (numOfFriends / haversine > maxDistance) {
                maxDistance = numOfFriends / haversine;
                bestSchool = school;
            }
        }
        if (bestSchool == null) {
            bestSchool = schoolWithFreeSpaces.get(random.nextInt(schoolWithFreeSpaces.size()));
        }
        pupil.setSchool(bestSchool);
        List<Pupil> pupils = Optional.ofNullable(bestSchool.getPupils()).orElseGet(ArrayList::new);
        bestSchool.setPupils(pupils);
        schoolRepository.saveAndFlush(bestSchool);
    }

    private static final Random random = new Random();

}
