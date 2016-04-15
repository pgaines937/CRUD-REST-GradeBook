/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
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
    "gradingItemId",
    "title",
    "weight"})
public class GradingItem {
    
    private static final Logger LOG = LoggerFactory.getLogger(GradingItem.class);
        
    private String          gradeBookId;
    private String          gradingItemId;    
    private String          title;  
    private int             weight; // Percentage of total grade

    public GradingItem() {
        LOG.info("Creating a GradingItem object");
    }

    public String getGradeBookId() {
        return gradeBookId;
    }

    @XmlAttribute
    public void setGradeBookId(String gradeBookId) {
        LOG.info("Setting the gradeBookId to {}", gradeBookId);
        
        this.gradeBookId = gradeBookId;
        
        LOG.debug("The updated GradingItem = {}", this);
    }
    

    public String getGradingItemId() {
        return gradingItemId;
    }

    @XmlAttribute
    public void setGradingItemId(String gradingItemId) {
        LOG.info("Setting the gradingItemId to {}", gradingItemId);
        
        this.gradingItemId = gradingItemId;
        
        LOG.debug("The updated GradingItem = {}", this);
    }    

    public String getTitle() {
        return title;
    }
    
    @XmlElement
    public void setTitle(String title) {
        LOG.info("Setting the title to {}", title);
        
        this.title = title;
        
        LOG.debug("The updated GradingItem = {}", this);
    }

    public int getWeight() {
        return weight;
    }

    @XmlElement
    public void setWeight(int weight) {
        LOG.info("Setting the weight to {}", weight);
        
        this.weight = weight;
        
        LOG.debug("The updated GradingItem = {}", this);
    }
        
    @Override
    public String toString() {
        return "GradingItem{" + "gradeBookId=" + gradeBookId + ", gradingItemId=" + gradingItemId + ", title=" + title + ", weight=" + weight + '}';
    }        
    
}
