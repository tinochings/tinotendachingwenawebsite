package com.tinotendachingwena.website.controllers;

import com.tinotendachingwena.website.models.ServicesRequest;
import com.tinotendachingwena.website.services.SitemapServices;
import com.tinotendachingwena.website.services.XmlUrlSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StaticPageController {
    @Autowired
    private SitemapServices sitemapServices;

    @GetMapping("/")
    public String homepage() {
        return "html/homepage";
    }

    @GetMapping("/sn/")
    public String homepageSn() {
        return "html/sn/homepage";
    }

    @GetMapping("/experiences")
    public String experiences() {
        return "html/experiences";
    }

    @GetMapping("/sn/experiences")
    public String experiencesSn() {
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
    public ResponseEntity<XmlUrlSet> sitemap() {
        return sitemapServices.generateSiteMap();
    }
}
