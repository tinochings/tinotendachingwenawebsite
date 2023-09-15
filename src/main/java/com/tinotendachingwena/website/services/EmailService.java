package com.tinotendachingwena.website.services;


import com.tinotendachingwena.website.models.EmailDetails;

public interface EmailService {

   int sendSimpleMail(EmailDetails details);

    int sendMailWithAttachment(EmailDetails details);
}