package com.example.officeplanner.service;

import com.example.officeplanner.Repositories.EmployeeRepository;
import com.example.officeplanner.config.EmployeeNotFoundException;
import com.example.officeplanner.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class EmployeeServices {

    public static final int MAX_FAILED_ATTEMPTS = 4;
    private static final long LOCK_TIME_DURATION = 30 * 60 * 1000; // 30 minutes

    @Autowired
    private EmployeeRepository eRepo;

    public void updateResetPasswordToken(String token, String email) throws EmployeeNotFoundException {
        Employee employee = eRepo.findEmployeeByEmail(email);
        if (employee != null) {
            employee.setResetPasswordToken(token);
            eRepo.save(employee);
        } else {
            throw new EmployeeNotFoundException("Could not find any customer with the email " + email);
        }
    }

    public Employee getByResetPasswordToken(String resetPasswordToken) {
        return eRepo.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePasswordToken(Employee employee, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        employee.setPassword(encodedPassword);

        employee.setResetPasswordToken(null);
        eRepo.save(employee);
    }

    public Employee getByUsername( String username){
        return eRepo.getEmployeeByUsername(username);

    }

    public void lock(Employee employee) {
        employee.setAccountNonLocked(false);
        employee.setLockTime(new Date());
        eRepo.save(employee);
    }

    public void increaseFailedAttempts(Employee employee) {
        int newFailAttempts = employee.getFailedAttempt() + 1;
        eRepo.updateFailedAttempt(newFailAttempts, employee.getUsername());;
    }

    public boolean unlockWhenTimeExpired(Employee employee) {
        long lockTimeInMillis = employee.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            employee.setAccountNonLocked(true);
            employee.setLockTime(null);
            employee.setFailedAttempt(0);

            eRepo.save(employee);

            return true;
        }

        return false;

    }

    public void resetFailedAttempts(String username) {
        eRepo.updateFailedAttempt(0,username);
    }
}
