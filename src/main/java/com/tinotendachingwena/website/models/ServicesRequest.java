package com.tinotendachingwena.website.models;

import com.tinotendachingwena.website.validators.BudgetValidatorInterface;
import com.tinotendachingwena.website.validators.ServiceValidatorInterface;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
public class ServicesRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @ServiceValidatorInterface
    private String service;
    @NotEmpty(message = "Please provide details of desired service")
    private String details;
    @BudgetValidatorInterface
    private String budget;

    @NotEmpty(message = "Date can not be empty")
    @Pattern(regexp = "^[0-9]{1,2}/[0-9]{1,2}/[0-9]{4},\\s[0-9]{2}:[0-9]{2}:[0-9]{2}", message = "Date has been tampered with")
    private String date;
    public ServicesRequest() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof  ServicesRequest)) {
            return false;
        }

        ServicesRequest other = (ServicesRequest) obj;

        if (this.name.equals(other.name) &&  this.email.equals(other.email) && this.service.equals(other.service)) {
            if (this.details != null && other.details != null) {
                return this.details.equals(other.details);
            }
        }
        return false;
    }
}
