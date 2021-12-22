package com.example.officeplanner.Repositories;

import com.example.officeplanner.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingRepo  extends JpaRepository<Meeting, Integer> {
    @Query("SELECT m FROM Meeting m WHERE m.id=?1")
    public Meeting findMeetingById(Integer id);

    @Query("SELECT COUNT(m.id) FROM Meeting m")
    int numberOfMeetings();

    @Query("SELECT m FROM Meeting m WHERE m.organization = ?1")
     public Meeting findByOrganization(Integer id);

    @Query("SELECT m FROM Meeting m WHERE m.organization.id = ?1")
    List<Meeting> findAllMeetingsByOrganization(Integer id);


    @Query("SELECT m FROM Meeting m WHERE m.startTime = ?1 AND m.endTime = ?1 " +
            "AND m.room.id = ?1 AND m.organization.id = ?1 AND m.id =?1")
    List<Meeting> findAllScheduledMeetings(Integer id);

    @Query("SELECT m FROM Meeting m WHERE m.Date = CURRENT_DATE ")
    List<Meeting> findTodayMeetings();
}
