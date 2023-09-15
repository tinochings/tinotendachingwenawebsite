package com.tinotendachingwena.website.services;

import com.tinotendachingwena.website.models.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender implements EmailService{

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String sender;

    /**
     *
     * @param details
     * @return 0 if mail successfully sent and -1 if not
     */
    @Override
    public int sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(details.getRecipient());
            simpleMailMessage.setText(details.getMsgBody());
            simpleMailMessage.setSubject(details.getSubject());

            mailSender.send(simpleMailMessage);
        } catch (MailException mailException){
            mailException.printStackTrace();
            return -1;
        }
        return 0;
    }


    @Override
    public int sendMailWithAttachment(EmailDetails details) {
        return -1;
    }
}
