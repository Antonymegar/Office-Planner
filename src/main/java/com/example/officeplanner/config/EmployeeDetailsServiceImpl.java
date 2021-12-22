package com.example.officeplanner.config;

import com.example.officeplanner.Repositories.EmployeeRepository;
import com.example.officeplanner.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeeDetailsServiceImpl implements UserDetailsService {

        @Autowired
        private EmployeeRepository Erepo;
        @Override
        public UserDetails loadUserByUsername (String username)
            throws UsernameNotFoundException {
            Employee employee = Erepo.getEmployeeByUsername(username);

            if (employee == null) {
                throw new UsernameNotFoundException("Could not find user");
            }

            return new EmployeeDetails(employee);
        }
    }

