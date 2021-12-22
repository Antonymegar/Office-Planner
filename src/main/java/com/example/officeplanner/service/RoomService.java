package com.example.officeplanner.service;

import com.example.officeplanner.Repositories.OrganizationRepo;
import com.example.officeplanner.Repositories.RoomRepository;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Organization;
import com.example.officeplanner.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepo;

    public List<Room> listRooms(){
        return roomRepo.findAll();
    }
    public void saveRoom(Room room) {
        roomRepo.save(room);
    }
    public Room updateRoom(Integer id){
        return roomRepo.findById(id).get();
    }
    //DELETE
    public void deleteRoom(Integer id){
        roomRepo.deleteById(id);
    }
    public List<Room>showRoomsByOrganization(Integer id){
        return  roomRepo.findAllVenuesByOrganization(id);
    }
}
