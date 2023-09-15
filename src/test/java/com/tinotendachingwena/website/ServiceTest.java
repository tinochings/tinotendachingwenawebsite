package com.tinotendachingwena.website;

import com.tinotendachingwena.website.models.ServicesRequest;
import com.tinotendachingwena.website.utils.StringResources;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * NB Rate limiting affects running tests as a suite
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MockMvc mockMvc2;

    private final ServicesRequest servicesReq = new ServicesRequest();

    @AfterEach
    @BeforeEach
    public void resetServiceReq() {
        servicesReq.setName("");
        servicesReq.setBudget("");;
        servicesReq.setEmail("");
        servicesReq.setService("");
        servicesReq.setDetails("");
        servicesReq.setDate("");
    }

        /**
     * Tests that all possible validations are sent as a response to user
     */
    @Test
    public void emptyServiceReq() throws Exception {
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(content().string
                        (Matchers.containsString("Name cannot be empty")),
                content().string(Matchers.containsString("Budget value has been tampered with")),
                content().string(Matchers.containsString("Email can not be empty")),
                content().string(Matchers.containsString("Email is not valid")),
                content().string(Matchers.containsString("Service has been tampered with")),
                content().string(Matchers.containsString("Please provide details of desired service")),
                content().string(Matchers.containsString("Date can not be empty")),
                content().string(Matchers.containsString("Date has been tampered with")));
    }
        /**
     * Tests that all possible validations are sent as a response to user except name and email validation responses
     */
    @Test
    public void emptyServiceReqExcludingDetails() throws Exception {

        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setBudget("$0 - $150");
        servicesReq.setService("Android Application");
        servicesReq.setDate("01/01/2023, 19:53:49");
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(not(Matchers.containsString("Budget value has been tampered with"))),
                content().string(not(Matchers.containsString("Service has been tampered with"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))),
                content().string(Matchers.containsString("Please provide details of desired service")));
    }

        /**
     * Tests that all possible validations are sent as a response to user except name and email validation responses
     */
    @Test
    public void emptyServiceReqWithNameEmail() throws Exception {

        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(Matchers.containsString("Budget value has been tampered with")),
                content().string(Matchers.containsString("Service has been tampered with")),
                content().string(Matchers.containsString("Please provide details of desired service")),
                content().string(Matchers.containsString("Date can not be empty")),
                content().string(Matchers.containsString("Date has been tampered with")));
    }

        /**
     * Tests that all possible validations are sent as a response to user except name and email validation responses
     */
    @Test
    public void testSuccessfulServiceRequest() throws Exception {

        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setBudget("$0 - $150");
        servicesReq.setService("Android Application");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");

        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isOk()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(not(Matchers.containsString("Budget value has been tampered with"))),
                content().string(not(Matchers.containsString("Service has been tampered with"))),
                content().string(not(Matchers.containsString("Please provide details of desired service"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))),
                content().string(Matchers.containsString(StringResources.goodServicesRequest)));
    }

    /**
     * Tests invalid Budget field when it is null
     */
    @Test
    public void budgetValidatorNull() throws Exception {

        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setService("Android Application");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");

        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(Matchers.containsString("Budget value has been tampered with")),
                content().string(not(Matchers.containsString("Service has been tampered with"))),
                content().string(not(Matchers.containsString("Please provide details of desired service"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))) );
    }

    /**
     * Tests invalid budget field when it is there but is incorrect
     */
    @Test
    public void budgetValidatorNotExists() throws Exception {

        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setService("Android Application");
        servicesReq.setBudget("Uhdsuhaushdad");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(Matchers.containsString("Budget value has been tampered with")),
                content().string(not(Matchers.containsString("Service has been tampered with"))),
                content().string(not(Matchers.containsString("Please provide details of desired service"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))));
    }

    /**
     * Tests that an incorrect date returns Date has not been tampered with
     */
    @Test
    public void dateIncorrect() throws Exception {

        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setService("Android Application");
        servicesReq.setBudget("Uhdsuhaushdad");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("/01/2023, 19:53:49");
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(Matchers.containsString("Budget value has been tampered with")),
                content().string(not(Matchers.containsString("Service has been tampered with"))),
                content().string(not(Matchers.containsString("Please provide details of desired service"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(Matchers.containsString("Date has been tampered with")));
    }
    /**
     * Tests services field when it is null
     */
    @Test
    public void serviceValidatorNull() throws Exception {

        servicesReq.setName("name");
        servicesReq.setBudget("$0 - $150");
        servicesReq.setEmail("name@name.com");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(not(Matchers.containsString("Budget value has been tampered with"))),
                content().string(Matchers.containsString("Service has been tampered with")),
                content().string(not(Matchers.containsString("Please provide details of desired service"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))) );
    }

    /**
     * Tests service field when it is invalid
     */
    @Test
    public void serviceValidatorIncorrect() throws Exception {

        servicesReq.setName("name");
        servicesReq.setBudget("$0 - $150");
        servicesReq.setEmail("name@name.com");
        servicesReq.setService("Android Applications");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).
                content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isBadRequest()).andExpectAll(
                content().string(not(Matchers.containsString("Name cannot be empty"))),
                content().string(not(Matchers.containsString("Email can not be empty"))),
                content().string(not(Matchers.containsString("Email is not valid"))),
                content().string(not(Matchers.containsString("Budget value has been tampered with"))),
                content().string(Matchers.containsString("Service has been tampered with")),
                content().string(not(Matchers.containsString("Please provide details of desired service"))),
                content().string(not(Matchers.containsString("Date can not be empty"))),
                content().string(not(Matchers.containsString("Date has been tampered with"))) );
    }

    /**
     * Tests that a user should successfully make 5 requests within a minute however the 6th try is denied
     */
    @Test
    public void testRateLimiting() throws Exception {
        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setBudget("$0 - $150");
        servicesReq.setService("Android Application");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");

        for (int i = 0; i < 5; i++){
            this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isOk()).andExpect(
                    content().string(StringResources.goodServicesRequest));
        }
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(quoteAsUrlEncoded(servicesReq))).andExpect(status().isTooManyRequests()).andExpect(
                        content().string(StringResources.tooManyRequest)
        );
    }

    /**
     * Simulates two users making five requests within a minute. The purpose of this is to make sure that different users
     * have different keys and do not share the exact same limits
     */
    @Test
    public void testRateLimitingDifferentUsers() throws Exception {
        servicesReq.setName("name");
        servicesReq.setEmail("name@name.com");
        servicesReq.setBudget("$0 - $150");
        servicesReq.setService("Android Application");
        servicesReq.setDetails("Sdfdsjhkfkjdshfksdjhfk dskjfhkdsjf");
        servicesReq.setDate("01/01/2023, 19:53:49");

        for (int i = 0; i < 5; i++){
            this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .content(quoteAsUrlEncoded(servicesReq)).with(request -> {request.setRemoteAddr("192.198.18555");
                        return request;})).andExpect(status().isOk()).andExpect(
                    content().string(StringResources.goodServicesRequest));
        }
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(quoteAsUrlEncoded(servicesReq)).with(request -> {request.setRemoteAddr("192.198.18555");
                    return request;})).andExpect(status().isTooManyRequests()).andExpect(
                content().string(StringResources.tooManyRequest)
        );
        for (int i = 0; i < 5; i++){
            this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .content(quoteAsUrlEncoded(servicesReq)).with(request -> {request.setRemoteAddr("192.198.18556");
                        return request;})).andExpect(status().isOk()).andExpect(
                    content().string(StringResources.goodServicesRequest));
        }
        this.mockMvc.perform(post("/service").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content(quoteAsUrlEncoded(servicesReq)).with(request -> {request.setRemoteAddr("192.198.18556");
                    return request;})).andExpect(status().isTooManyRequests()).andExpect(
                content().string(StringResources.tooManyRequest)
        );
    }
    private String encodeValue(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, String.valueOf(StandardCharsets.UTF_8));
    }

    public String quoteAsUrlEncoded(ServicesRequest servicesRequest) throws UnsupportedEncodingException {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("name", encodeValue(servicesRequest.getName()));
        requestParams.put("email", encodeValue(servicesRequest.getEmail()));
        requestParams.put("budget", encodeValue(servicesRequest.getBudget()));
        requestParams.put("service", encodeValue(servicesRequest.getService()));
        requestParams.put("details", encodeValue(servicesRequest.getDetails()));
        requestParams.put("date", encodeValue(servicesRequest.getDate()));

        return "name=" + requestParams.get("name") + "&" +
                "email=" + requestParams.get("email") +
                "&" + "budget=" + requestParams.get("budget") +
                "&" + "service=" + requestParams.get("service") +
                "&" + "details=" + requestParams.get("details") +
                "&" + "date=" + requestParams.get("date");
    }
}
