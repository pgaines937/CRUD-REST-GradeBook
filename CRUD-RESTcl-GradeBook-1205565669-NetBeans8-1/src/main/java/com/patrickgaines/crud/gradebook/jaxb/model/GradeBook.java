/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.jaxb.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pgaines
 */

@XmlRootElement
@XmlType(propOrder={
    "gradeBookId",
    "title",
    "gradingItems"})
public class GradeBook {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradeBook.class);
        
    private String                          gradeBookId;
    private String                          title;  
    private List<GradingItem>               gradingItems;


    public GradeBook() {
        LOG.info("Creating a GradeBook object");
    }

    public String getGradeBookId() {
        return gradeBookId;
    }

    @XmlAttribute
    public void setGradeBookId(String gradeBookId) {
        LOG.info("Setting the id to {}", gradeBookId);
        
        this.gradeBookId = gradeBookId;
        
        LOG.debug("The updated GradeBook = {}", this);
    }

    public String getTitle() {
        return title;
    }
    
    @XmlElement
    public void setTitle(String title) {
        LOG.info("Setting the title to {}", title);
        
        this.title = title;
        
        LOG.debug("The updated GradeBook = {}", this);
    }
    
    @XmlElementWrapper
    @XmlElement(name="gradingItems")
    public List<GradingItem> getGradingItems() {
        return gradingItems;
    }

    public void setGradingItems(List<GradingItem> gradingItems) {
        this.gradingItems = gradingItems;
    }      
        
    @Override
    public String toString() {
        return "GradeBook{" + "gradeBookId=" + gradeBookId + ", title=" + title + '}';
    }        
    
}