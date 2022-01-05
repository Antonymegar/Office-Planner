package com.example.officeplanner.notifications;

import com.example.officeplanner.Repositories.MeetingRepo;
import com.example.officeplanner.model.Employee;
import com.example.officeplanner.model.Meeting;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.*;

@EnableScheduling
@Component

@Transactional
@Service
public class NotificationService {
    @Autowired
    private MeetingRepo mRepo;

    @Autowired
    private JavaMailSender mailSender;

    //An ExecutorService that can schedule commands to run after a given delay, or to execute periodically.
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
    //A delayed result-bearing action that can be cancelled. Usually a scheduled future is the result of scheduling a task with a ScheduledExecutorService
    private List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();


    /** Sending email notification **/
    private void sendMail1(Employee employee, Meeting meeting) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        /** Getting Full name(s) and email(s) of the Owner(s)  **/
        String email = employee.getEmail();
        String fName = employee.getUsername();
        String lName = employee.getFullname();


        helper.setFrom("trucalleramg@gmail.com", "Office Support");
        helper.setTo(email);

        String subject = "Meeting Notification";

        String content = "<p>Hello " + fName + " " + lName + ",</p>"
                + "<p>The " + meeting.getMeeting_name() + " for  " + meeting.getOrganization().getOrg_name() +" is scheduled to commence today at " + meeting.getStartTime() + " .</p>"
                + "<p>The venue of the meeting will be at " + meeting.getRoom().getRoom_name() + " .</p>"
                + "<p>Kindly avail yourself in time.</p>"
                + "<p><br></p>"
                + "<p>Regards,</p>"
                + "<p>Office Meeting Planner Support.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
    /** End of Sending email notifications **/


    public void notificationTimer(Meeting meeting, Employee employee) throws ExecutionException, InterruptedException {
        LocalDateTime time = LocalDateTime.of(meeting.getDate(),
                meeting.getStartTime()).minusMinutes(15);

        long sendTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println("Scheduling....");

        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule((Runnable) new Notification(employee, meeting),sendTime, TimeUnit.MILLISECONDS);
        scheduledFutures.add(scheduledFuture);
    }

    @Scheduled(fixedDelay = 17 * 60 * 1000)
    public void sendNotifications(){
        try {
            //Attempts to cancel execution of this task. This attempt will fail if the task has already completed, has already been cancelled, or could not be cancelled for
            // some other reason.
            scheduledFutures.forEach(f -> f.cancel(true));
        }catch (Exception e){
            e.getMessage();
        }
        List<Meeting> meetings = mRepo.findAll();
        // TODO: 12/1/2021 Filter to load current Dates
        meetings.forEach(r ->{
            if (r.getStartTime().isAfter(LocalTime.now().plusMinutes(16))) {
                r.getEmployees().forEach(u -> {
                    try {
                        notificationTimer(r,u);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    public class Notification extends TimerTask {

       Employee employee;
       Meeting meet;

        public Notification(Employee employee, Meeting meet) {
            this.employee = employee;
            this.meet = meet;
        }


        @SneakyThrows
        @Override
        public void run() {
            System.out.println("Scheduling the meeting");
            try {
                sendMail1(employee,meet);
                System.out.println("Sent the mail");
            }finally
            {
                System.out.println("Failed to send mail  ");
                try {
                    sendMail1(employee,meet);
                    System.out.println("Sent the sms");
                }finally {
                    System.out.println("Failed to send the SMS");
                }
            }
        }
    }


}
