package com.tinotendachingwena.website.controllers;

import com.tinotendachingwena.website.models.EmailDetails;
import com.tinotendachingwena.website.models.ServicesRequest;
import com.tinotendachingwena.website.services.EmailService;
import com.tinotendachingwena.website.services.RateLimitingService;
import com.tinotendachingwena.website.services.ServicesService;
import com.tinotendachingwena.website.utils.StringResources;
import io.github.bucket4j.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ServicesController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private RateLimitingService rateLimitingService;

    private final EmailDetails emailDetails = new EmailDetails("tinotendachings@gmail.com", "", "", "");
    private final ResponseEntity<String> responseOk = new ResponseEntity<>(StringResources.goodServicesRequest, HttpStatus.OK);
    private final ResponseEntity<String> responseOkSn = new ResponseEntity<>(StringResources.goodServicesRequestSn, HttpStatus.OK);
    private final ResponseEntity<String> responseFailure = new ResponseEntity<>(StringResources.badServicesRequest,
            HttpStatus.BAD_REQUEST);
    private final ResponseEntity<String> tooManyRequests = new ResponseEntity<>(StringResources.tooManyRequest,
            HttpStatus.TOO_MANY_REQUESTS);
    private final ResponseEntity<String> tooManyRequestsSn = new ResponseEntity<>(StringResources.tooManyRequestSn,
            HttpStatus.TOO_MANY_REQUESTS);
    private final ResponseEntity<String> responseFailureSn = new ResponseEntity<>(StringResources.badServicesRequestSn,
            HttpStatus.BAD_REQUEST);
    private final ResponseEntity<String> emailResponseFailure = new ResponseEntity<>(StringResources.badServicesRequest,
            HttpStatus.BAD_REQUEST);
    private final ResponseEntity<String> emailResponseFailureSn = new ResponseEntity<>(StringResources.badServicesRequestSn,
            HttpStatus.BAD_REQUEST);

    @PostMapping(path = "/service", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> servicesPost(@Valid ServicesRequest servicesRequest, BindingResult bindingResult,
                                               HttpServletRequest request) {

        String ipAddress = request.getRemoteAddr();
        Bucket bucket = rateLimitingService.resolveBucket(ipAddress);

        if (bucket.tryConsume(1)) {
            if (bindingResult.hasErrors()) {
                StringBuilder inputRetString = new StringBuilder();
                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    inputRetString.append(objectError.getDefaultMessage()).append("\n");
                }
                return new ResponseEntity<>(inputRetString.toString(), HttpStatus.BAD_REQUEST);
            }
            if (servicesService.saveService(servicesRequest).equals(servicesRequest)) {
                emailDetails.setSubject("Service Request");
                emailDetails.setMsgBody("Name: " + servicesRequest.getName() + "\n" +
                        "Email: " + servicesRequest.getEmail() + "\n" +
                        "Service: " + servicesRequest.getService() + "\n" +
                        "Budget: " + servicesRequest.getBudget() + "\n" +
                        "Details: " + servicesRequest.getDetails());
                if (emailService.sendSimpleMail(emailDetails) == 0) {
                    return responseOk;
                } else {
                    return emailResponseFailure;
                }
            } else {
                return responseFailure;
            }
        }
        return tooManyRequests;
    }

    @PostMapping(path = "/sn/service", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE},
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> servicesPostSn(@Valid ServicesRequest servicesRequest, BindingResult bindingResult,
                                                 HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        Bucket bucket = rateLimitingService.resolveBucket(ipAddress);
        if (bucket.tryConsume(1)) {
            if (bindingResult.hasErrors()) {
                StringBuilder inputRetString = new StringBuilder();
                for (ObjectError objectError : bindingResult.getAllErrors()) {
                    inputRetString.append(objectError.getDefaultMessage()).append("\n");
                }
                return new ResponseEntity<>(inputRetString.toString(), HttpStatus.BAD_REQUEST);
            }

            if (servicesService.saveService(servicesRequest).equals(servicesRequest)) {
                emailDetails.setSubject("Service Request");
                emailDetails.setMsgBody("Name: " + servicesRequest.getName() + "\n" +
                        "Email: " + servicesRequest.getEmail() + "\n" +
                        "Service: " + servicesRequest.getService() + "\n" +
                        "Budget: " + servicesRequest.getBudget() + "\n" +
                        "Details: " + servicesRequest.getDetails());
                if (emailService.sendSimpleMail(emailDetails) == 0) {
                    return responseOkSn;
                } else {
                    return emailResponseFailureSn;
                }
            } else {
                return responseFailureSn;
            }
        }
        return tooManyRequestsSn;
    }
}
