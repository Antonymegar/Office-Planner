package com.example.officeplanner.config;

import com.example.officeplanner.model.Employee;
import com.example.officeplanner.service.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private EmployeeServices employeeServices;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        EmployeeDetails userDetails =  (EmployeeDetails) authentication.getPrincipal();
        Employee employee = userDetails.getEmployee();
        if (employee.getFailedAttempt() > 0) {
            employeeServices.resetFailedAttempts(employee.getUsername());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        String targeturl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targeturl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        String url = "/login?error=true";
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
            if (roles.contains("ADMIN")) {
                url = "/admin";
            } else if (roles.contains("OFFICER")) {
                url = "/";
            } else if (roles.contains("EMPLOYEE")) {
                url = "/view_meetings/";
            }

        }
        return url;
    }
}
