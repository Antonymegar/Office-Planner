package com.example.officeplanner;

import com.example.officeplanner.Repositories.EmployeeRepository;
import com.example.officeplanner.Repositories.RoleRepository;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EmployeeCreateTest {
    @Autowired
    private TestEntityManager entitymanager;
    @Autowired
    private EmployeeRepository Repo;
    @Autowired
    private RoleRepository RoleRepo;
    @Test
    public void createEmployeetest() {
        Employee employee = new Employee();
        employee.setFullname("Deneiro");
        employee.setUsername("Ofcourse");
        employee.setEmail("Ofcourse@gmail.com");
        employee.setGender("Male");
        employee.setPhone("07986543");
        employee.setPassword("idontknowyou");

        Employee savedEmployee = Repo.save(employee);
        Employee existEmployee = entitymanager.find(Employee.class, savedEmployee.getId());
        assertThat(employee.getEmail()).isEqualTo(existEmployee.getEmail());
    }

    @Test
    public void addRoleToEmployee(){
        Role roleAdmin = RoleRepo. getRoleByname("ADMIN");

        Employee employee = new Employee();
        employee.setFullname("Andrew ");
        employee.setUsername("Andrewkibe");
        employee.setEmail("Andrew@gmail.com");
        employee.setGender("Male");
        employee.setPhone("07567987");
        employee.setPassword("heretodevelop");
        employee.setEnabled(1);
        employee.addRole(roleAdmin);

        Employee savedEmployee= Repo.save(employee);
        assertThat(savedEmployee.getRoles().size()).isEqualTo(1);
    }
    @Test
    public void testAddRoleToExistingUser() {
        Employee employee= Repo.findById(2).get();
//        Role roleEmployee= RoleRepo.getRoleByname("Officer");
        Role roleStaff= RoleRepo.getRoleByname("Employee");
        employee.addRole(roleStaff);
//        employee.addRole(roleEmployee);

        Employee savedEmployee = Repo.save(employee);
        assertThat(((Employee) savedEmployee).getRoles().size()).isEqualTo(2);
    }
    @Test
    public void testFailedAttempts(){
        String username= "AndrewKibe";
        int failedAttempt= 2;

        Repo.updateFailedAttempt(failedAttempt, username);
        Integer id = 1;
        Employee employee = entitymanager.find(Employee.class, id);
        assertEquals(failedAttempt,employee.getFailedAttempt());

    }
}
