package com.example.officeplanner.controllers;

import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Organization;
import com.example.officeplanner.model.Role;
import com.example.officeplanner.service.OrganizationService;
import com.example.officeplanner.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService Eservice;
    @Autowired
    private RoleService rService;
    @Autowired
    private OrganizationService oService;

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/admin")
    public String getAdminPage(Model model) {
        List<Employee>employees=Eservice.listAll();
        model.addAttribute("employees",employees);
        return "dashboard";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }


    @GetMapping("/edit/")
    public String getEditPage() {
        return "editrole";
    }


    /********                  CRUD EMPLOYEES                          ********/

    @GetMapping("/register_employee")
    public String showRegistrationForm(Model model) {
       List<Role> listRoles= rService.listRoles();
       List<Organization>listOrganizations=oService.listOrganizations();
       model.addAttribute("listRoles", listRoles);
       model.addAttribute("listOrganizations", listOrganizations);
       model.addAttribute("employee",new Employee());
        return "register";
    }
    @PostMapping("/save_employee")
    public String SaveEmployee(Employee employee){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        Eservice.saveEmployee(employee);
        return "redirect:/admin";
    }
//    @PostMapping("/save_employee")
//    public String SaveEmployee(Employee employee){
//        Eservice.saveEmployee(employee);
//        return "redirect:/admin";
//    }

    @GetMapping("/edit_employee/{id}")
    public String showEditEmployee(@PathVariable("id") Integer id , Model model){
        Employee employee = Eservice.updateEmp(id);
        List<Role> listRoles= rService.listRoles();
        List<Organization>listOrganizations=oService.listOrganizations();
        model.addAttribute("employee", employee);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("listOrganizations", listOrganizations);

        return "editrole";
//        ModelAndView umv = new ModelAndView("editrole");
//        Employee um = Eservice.updateEmp(id);
//        umv.addObject("um",um);
//        return  umv;
    }
    @GetMapping("/deleteemployee/{id}")
    public String deleteUser(@PathVariable(name ="id" ) Integer id){
        Eservice.deleteEmp(id);
        return "dashboard";
    }
}