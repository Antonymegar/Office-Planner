package com.example.officeplanner.controllers;

import com.example.officeplanner.Repositories.EmployeeRepository;
import com.example.officeplanner.Repositories.RoleRepository;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository eRepo;
    @Autowired
    private RoleRepository roleRepo;

//    public void saveEmployeeWithDefaultRole(Employee employee){
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodedPassword = encoder.encode(employee.getPassword());
//        employee.setPassword(encodedPassword);
//
//        Role roleEmployee= roleRepo.getRoleByname("Employee");
//        employee.addRole(roleEmployee);
//        eRepo.save(employee);
//    }

    public List<Employee> listAll(){
        return eRepo.findAll();
    }

   public void saveEmployee(Employee employee){
        eRepo.save(employee);
   }

    public Employee updateEmp(Integer id){
        return eRepo.findById(id).get();
    }
    //DELETE
    public void deleteEmp(Integer id){
        eRepo.deleteById(id);
    }

    public Employee getEmployeeByEmail(String email) {
         return  eRepo.findEmployeeByEmail(email);
    }
//    public Employee getEmployeeById(String username){
//        return eRepo.findEmployeeById(username);
//    }
    public Employee getEmployeeByUsername(String username){
        return  eRepo.getEmployeeByUsername(username);
    }

    public List<Employee> ShowEmployeesByOrg(Integer id ){
        return eRepo.findAllEmployeesByOrganization(id);
    }
}
