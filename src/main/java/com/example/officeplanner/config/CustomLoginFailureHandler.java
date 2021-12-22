package com.example.officeplanner.config;


import com.example.officeplanner.model.Employee;
import com.example.officeplanner.service.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Autowired
    private EmployeeServices employeeServices;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        Employee employee = employeeServices.getByUsername(username);

        if (employee!= null) {

            if (employee.isEnabled() && employee.isAccountNonLocked()) {
                if (employee.getFailedAttempt() < employeeServices.MAX_FAILED_ATTEMPTS - 1) {
                    employeeServices.increaseFailedAttempts(employee);
                } else {
                    employeeServices.lock(employee);
                    exception = new LockedException("Your account has been locked due to 4 failed attempts."
                            + " It will be unlocked after 30 Minutes.");
                }
            } else if (!employee.isAccountNonLocked()) {
                if (employeeServices.unlockWhenTimeExpired(employee)) {
                    exception = new LockedException("Your account has been unlocked. Please try to login again.");
                }
            }

        }

        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);
    }
}
