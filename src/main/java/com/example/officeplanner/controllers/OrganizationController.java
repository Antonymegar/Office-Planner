package com.example.officeplanner.controllers;

import com.example.officeplanner.Repositories.OrganizationRepo;
import com.example.officeplanner.Repositories.RoomRepository;
import com.example.officeplanner.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrganizationController {
    @Autowired
    private OrganizationRepo orgRepo;

    @GetMapping("/create_organization")
    public String createOrganization(Model model){
        model.addAttribute("organization",new Organization());
        return "organization";
    }
    @PostMapping("/save_organization")
    public String saveOrganization(Organization organization){
        orgRepo.save(organization);
        return "redirect:/create_room";
    }

}
