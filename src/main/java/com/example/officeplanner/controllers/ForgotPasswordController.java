package com.example.officeplanner.controllers;

import com.example.officeplanner.config.EmployeeNotFoundException;
import com.example.officeplanner.config.Utility;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.service.EmployeeServices;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class ForgotPasswordController {
    @Autowired
    private EmployeeServices employeeServices;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm( Model model) {
        model.addAttribute("Forgot Password", "Forgot Password");
        return "password_forgot";
    }
    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(45);

        try {
            employeeServices.updateResetPasswordToken(token, email);
         String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
         sendEmail(email, resetPasswordLink);
        model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (EmployeeNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email,please ensure that you have entered correct email !");
        }
        model.addAttribute("Forgot Password", "Forgot Password");
        return "password_forgot";
    }
//} catch (UnsupportedEncodingException | MessagingException e) {
//            model.addAttribute("error", "Error while sending email,please ensure that you have entered correct email !");
//            return "password_forgot";
//        }
//        model.addAttribute("Forgot Password", "Forgot Password");
//        return "password_forgot";
//    }
//
    private void sendEmail(String email, String resetPasswordLink) throws UnsupportedEncodingException,  MessagingException {
             MimeMessage message = mailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message);
             helper.setFrom("trucalleramg@gmail.com", "Office Support");
             helper.setTo(email);

             String subject = "Here's the link to reset your password";

             String content = "<p>Hello,</p>"
                     + "<p>You have requested to reset your password.</p>"
                     + "<p>Click the link below to change your password:</p>"
                     + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                     + "<br>"
                     + "<p>Ignore this email if you do remember your password, "
                     + "or you have not made the request.</p>";

             helper.setSubject(subject);

             helper.setText(content, true);

             mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Employee employee = employeeServices.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (employee == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

       Employee employee= employeeServices.getByResetPasswordToken(token);
       model.addAttribute("title", "Reset your password");

        if (employee == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            employeeServices.updatePasswordToken(employee, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "login";
    }

}
