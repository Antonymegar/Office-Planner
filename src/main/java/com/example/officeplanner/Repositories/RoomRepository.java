package com.example.officeplanner.Repositories;

import com.example.officeplanner.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer > {
    @Query("SELECT r FROM Room r WHERE r.id=?1")
    public Room findRoomById(Integer id);

    @Query("SELECT r FROM Room r  WHERE r.organization.id = ?1")
    List<Room> findAllVenuesByOrganization(int organization);


    @Query("SELECT COUNT(r.id) FROM Room r")
    int numberOfRooms();
}
