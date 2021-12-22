package com.example.officeplanner.service;

import com.example.officeplanner.Repositories.RoleRepository;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepo;

    public List<Role> listRoles(){
        return roleRepo.findAll();
    }
}
