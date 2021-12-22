package com.example.officeplanner.config;

import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class EmployeeDetails  implements UserDetails {
     private Employee employee;

     public EmployeeDetails (Employee employee){
         this.employee= employee;
     }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles =employee.getRoles();
        List<SimpleGrantedAuthority> authorities= new ArrayList();
        for (Role role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return  authorities;
    }

    @Override
    public String getPassword() {
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        return employee.getUsername();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return employee.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return employee.isEnabled();
    }

    public Employee getEmployee() {
        return this.employee;
    }
}
