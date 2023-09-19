package com.tinotendachingwena.website.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "url")
@XmlAccessorType(XmlAccessType.NONE)
public class XmlUrl {

    @XmlElement
    private String loc;

    @XmlElement
    private String lastmod;
    @XmlElement
    private String priority;
    public XmlUrl(){
    }
    public XmlUrl(String loc, String date, String priority) {
        this.loc = loc;
        this.lastmod = date;
        this.priority = priority;
    }

    public String getLoc() {
        return loc;
    }

    public String getLastmod() {
        return lastmod;
    }

    public String getPriority() {return priority;}
}