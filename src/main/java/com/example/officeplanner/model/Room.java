package com.example.officeplanner.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length=55)
    private String room_name;
    @Min(value= 7, message= "{room.capacity}")
    @Max(value=18, message="{room.capacity}")
    private Integer capacity;
    @Column(nullable=false, length = 55)
    private String block;
    @Column(name="tv")
    private boolean Tv =false;
    @Column(name="board")
    private boolean whiteboard= false;
    @Column (name="phone")
    private boolean conference_phone= false;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public Room(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @OneToMany(mappedBy = "room")
    private List<Meeting> meetings = new ArrayList<Meeting>();

    public Room(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Room() {

    }

    public boolean isTv() {
        return Tv;
    }

    public void setTv(boolean tv) {
        Tv = tv;
    }

    public boolean isWhiteboard() {
        return whiteboard;
    }

    public void setWhiteboard(boolean whiteboard) {
        this.whiteboard = whiteboard;
    }

    public boolean isConference_phone() {
        return conference_phone;
    }

    public void setConference_phone(boolean conference_phone) {
        this.conference_phone = conference_phone;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }
}
