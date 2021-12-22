package com.example.officeplanner;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword ="heretodevelop";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(encodedPassword);

    }
}
