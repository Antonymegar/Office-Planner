package com.example.officeplanner.controllers;

import com.example.officeplanner.Repositories.MeetingRepo;
import com.example.officeplanner.Repositories.RoomRepository;
import com.example.officeplanner.config.EmployeeDetails;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Meeting;
import com.example.officeplanner.model.Organization;
import com.example.officeplanner.model.Room;
import com.example.officeplanner.service.MeetingService;
import com.example.officeplanner.service.OrganizationService;
import com.example.officeplanner.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MeetingController {
    @Autowired
    private MeetingRepo mRepo;
    @Autowired
    private EmployeeService eService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private OrganizationService orgService;
    @Autowired
    private MeetingService meetingService;

    @GetMapping("/list_rooms")
    public String listRooms(Model model){
        List<Room> rooms= roomService.listRooms();
        model.addAttribute("rooms",rooms);
        return "rooms";
    }
    @GetMapping("/list_meetings")
    public String listMeetings( Model model){
        List<Meeting>meetings= meetingService.listMeetings();
        model.addAttribute("meetings",meetings);

        return "meetings";
    }
    @GetMapping("/view_meetings")
    public String listEmployeeMeetings(@AuthenticationPrincipal EmployeeDetails loggedEmployee, Model model){
        String username = loggedEmployee.getUsername();
        Employee employee = eService.getEmployeeByUsername(username);
        Integer organization_id = employee.getOrganization().getId();

        List<Room>showRooms=roomService.showRoomsByOrganization(organization_id);
        List<Meeting>meetings= meetingService.listMeetingByOrganization(organization_id);
        model.addAttribute("showRooms",showRooms);
        model.addAttribute("meetings",meetings);
        model.addAttribute("loggedEmployee", employee);
        return "Employee_dashboard";
    }


    @GetMapping("/create_meeting")
    public String getEventPage(@AuthenticationPrincipal EmployeeDetails loggedEmployee, Model model){

        String username = loggedEmployee.getUsername();
        Employee employee = eService.getEmployeeByUsername(username);


        Integer organization_id = employee.getOrganization().getId();
//        Integer employee_id= employee.getId();
        List<Employee>listCoOwners=eService.ShowEmployeesByOrg(organization_id);
        List<Room>showRooms=roomService.showRoomsByOrganization(organization_id);

//        List<Room>listRooms=roomService.listRooms();
        List<Organization>listOrganizations= orgService.listOrganizations();
        model.addAttribute("listCoOwners", listCoOwners);
        model.addAttribute("showRooms",showRooms);
        model.addAttribute("loggedEmployee", employee);
//        model.addAttribute("listRooms",listRooms);
        model.addAttribute("listOrganizations",listOrganizations);
        model.addAttribute("meeting", new Meeting());
        return "meeting";
    }

    @GetMapping("/create_room")
    public String getRoomPage(@AuthenticationPrincipal EmployeeDetails loggedEmployee, Model model){
        String username = loggedEmployee.getUsername();
        Employee employee = eService.getEmployeeByUsername(username);
        Integer organization_id = employee.getOrganization().getId();

        List<Organization>listOrganizations= orgService.listOrganizations();
        model.addAttribute("listOrganizations",listOrganizations);
        model.addAttribute("loggedEmployee", employee);
        model.addAttribute("room", new Room());
        return "room";
    }
    @PostMapping("/save_room")
    public String saveRoom(Room room){
        roomService.saveRoom(room);
        return "redirect:/list_rooms";
    }
    @PostMapping("save_meeting")
    public String saveMeeting(Meeting meeting){
        meetingService.saveMeeting(meeting);
        return "redirect:/list_meetings";
    }
    @RequestMapping("/edit_room/{id}")
    public ModelAndView showEditUser(@PathVariable( "id") Integer id){
        ModelAndView umv = new ModelAndView("edit_room");
       Room um = roomService.updateRoom(id);
        umv.addObject("um",um);
        return  umv;
    }
    @RequestMapping("/delete_room/{id}")
    public String deleteUser(@PathVariable(name ="id" ) Integer id){
        roomService.deleteRoom(id);
        return "redirect:/list_rooms";
    }
}

