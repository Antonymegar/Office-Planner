package com.example.officeplanner.Repositories;

import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository  extends JpaRepository<Employee, Integer> {
    @Query("SELECT e FROM Employee e WHERE e.username = :username")
    public Employee getEmployeeByUsername(@Param("username") String username);

    @Query("SELECT e FROM Employee e WHERE e.organization = ?1")
    List<Employee> findAllEmployeesWithOrganization(Integer id);

    @Query("SELECT e FROM Employee  e  WHERE e.email IS NOT NULL AND " +
            "e.password IS NOT NULL AND e.phone IS NOT NULL AND e.organization.id = ?1")
    List<Employee> findAllEmployeesByOrganization(Integer id);

    @Query("SELECT e FROM Employee  e WHERE e.username=?1")
    public Employee  findEmployeeById(String username);

    @Query("SELECT e FROM Employee e WHERE e.email= ?1")
    public Employee findEmployeeByEmail(String email);

    public Employee findByResetPasswordToken(String token);

    @Query("UPDATE Employee  e SET e.failedAttempt =?1 WHERE e.username=?2")
    @Modifying
    public void updateFailedAttempt(Integer failedAttempt, String username );

}
