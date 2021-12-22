package com.example.officeplanner.service;

import com.example.officeplanner.Repositories.OrganizationRepo;
import com.example.officeplanner.Repositories.RoleRepository;
import com.example.officeplanner.model.Organization;
import com.example.officeplanner.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepo orgRepo;

    public List<Organization> listOrganizations(){
        return orgRepo.findAll();
    }
}
