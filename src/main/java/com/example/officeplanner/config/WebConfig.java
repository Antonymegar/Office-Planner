package com.example.officeplanner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomLoginSuccessHandler successHandler;
    @Autowired
    private CustomLoginFailureHandler loginFailureHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new EmployeeDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
   @Bean
    public DaoAuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       authProvider.setUserDetailsService(userDetailsService());
       authProvider.setPasswordEncoder(passwordEncoder());
       return authProvider;
   }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
       .antMatchers("/login", "/register_employee","/save_employee","/home","/css/**","/images/**","/forgot_password","/reset_password").permitAll()
                .antMatchers("/create_meeting").hasAnyAuthority("ADMIN", "OFFICER")
                .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "OFFICER")
                .antMatchers("/view_meetings/").hasAnyAuthority("ADMIN", "OFFICER","EMPLOYEE")
                .antMatchers("/delete/**").hasAuthority("ADMIN")
                .antMatchers("/admin").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(
                        form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/home")
                                .loginProcessingUrl("/perform-login")
                                .usernameParameter("username").permitAll()
                                .successHandler(successHandler)
                                .failureHandler(loginFailureHandler)

                )
//                .successHandler(successHandler)
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/AccesDenied");
//                .antMatchers("/").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
//                .antMatchers("/new").hasAnyAuthority("ADMIN", "OFFICER")
//                .antMatchers("/edit/**").hasAnyAuthority("ADMIN", "OFFICER")
//                .antMatchers("/delete/**").hasAnyAuthority("ADMIN","OFFICER" )
//                .antMatchers("/admin").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().permitAll()
//                .successHandler(successHandler)
//                .and()
//                .logout().permitAll()
//                .and()
//                .exceptionHandling().accessDeniedPage("/AccesDenied")

        ;
    }


}
