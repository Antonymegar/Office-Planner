package com.example.officeplanner.service;

import com.example.officeplanner.Repositories.MeetingRepo;
import com.example.officeplanner.Repositories.RoomRepository;
import com.example.officeplanner.model.Meeting;
import com.example.officeplanner.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MeetingService {

    @Autowired
    private MeetingRepo meetingRepo;

    public List<Meeting> listMeetings(){
        return meetingRepo.findAll();
    }
    public void saveMeeting(Meeting meeting) {
        meetingRepo.save(meeting);
    }
    public List<Meeting>listMeetingByOrganization(Integer id){
        return meetingRepo.findAllMeetingsByOrganization(id);
    }
    public List<Meeting>listScheduledMeetings(Integer id){
        return meetingRepo.findAllScheduledMeetings(id);
    }

}
