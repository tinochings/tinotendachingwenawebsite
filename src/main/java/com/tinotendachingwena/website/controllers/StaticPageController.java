package com.tinotendachingwena.website.controllers;

import com.tinotendachingwena.website.models.ServicesRequest;
import com.tinotendachingwena.website.services.XmlUrl;
import com.tinotendachingwena.website.services.XmlUrlSet;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class StaticPageController {

    @GetMapping("/")
    public String homepage(){
        return "html/homepage";
    }
    @GetMapping("/sn/homepage")
    public String homepageSn(){
        return "html/sn/homepage";
    }

    @GetMapping("/experiences")
    public String experiences(){
        return "html/experiences";
    }
    @GetMapping("/sn/experiences")
    public String experiencesSn(){
        return "html/sn/experiences";
    }

    @GetMapping("/services")
    public String services(Model model) {
        model.addAttribute("serviceRequest", new ServicesRequest());
        return "html/services";
    }

    @GetMapping("/sn/services")
    public String servicesSn(Model model) {
        model.addAttribute("serviceRequest", new ServicesRequest());
        return "html/sn/services";
    }

    @RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<XmlUrlSet> sitemap(){
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        XmlUrl xmlUrl = new XmlUrl("https://www.trin3media.com", "2023-09-11");

        xmlUrlSet.addUrl(xmlUrl);

        return ResponseEntity.ok(xmlUrlSet);
    }
}
