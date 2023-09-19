package com.tinotendachingwena.website.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
@Service
public class SitemapServices {

    public ResponseEntity<XmlUrlSet>  generateSiteMap(){
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        XmlUrl xmlUrlHome = new XmlUrl("https://www.tinotendachingwena.com/", findLastMod("homepage"), "1.00");
        XmlUrl xmlUrlExperiences = new XmlUrl("https://www.tinotendachingwena.com/experiences", findLastMod("experiences"), "0.80");
        XmlUrl xmlUrlServices = new XmlUrl("https://www.tinotendachingwena.com/services", findLastMod("services"), "0.80");

        XmlUrl xmlUrlHomeSn = new XmlUrl("https://www.tinotendachingwena.com/sn/", findLastMod("sn/homepage"), "0.80");
        XmlUrl xmlUrlExperiencesSn = new XmlUrl("https://www.tinotendachingwena.com/sn/experiences", findLastMod("sn/experiences"), "0.70");
        XmlUrl xmlUrlServicesSn = new XmlUrl("https://www.tinotendachingwena.com/sn/services", findLastMod("sn/services"), "0.70");

        xmlUrlSet.addUrl(xmlUrlHome);
        xmlUrlSet.addUrl(xmlUrlExperiences);
        xmlUrlSet.addUrl(xmlUrlServices);

        xmlUrlSet.addUrl(xmlUrlHomeSn);
        xmlUrlSet.addUrl(xmlUrlExperiencesSn);
        xmlUrlSet.addUrl(xmlUrlServicesSn);

        return ResponseEntity.ok(xmlUrlSet);
    }

    private String findLastMod(String fileName){
        File file = new File("src/main/resources/templates/html" + File.separator + fileName + ".html");
        if (file.exists()){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(file.lastModified());
        }

        return "";
    }
}
