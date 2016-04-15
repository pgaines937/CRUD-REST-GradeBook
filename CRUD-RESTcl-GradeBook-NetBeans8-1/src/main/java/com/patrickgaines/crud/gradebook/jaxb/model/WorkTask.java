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
    "workTaskId",
    "title",
    "maxPoints",})
public class WorkTask {
    
    private static final Logger LOG = LoggerFactory.getLogger(WorkTask.class);
        
    private String      gradeBookId;             // ID number assigned by the server
    private String      gradingItemId;             // ID number assigned by the server
    private String      workTaskId;             // ID number assigned by the server   
    private String      title;          // Name of the student    
    private int         maxPoints;   // How many points the student earned on the work task

    public WorkTask() {
        LOG.info("Creating a WorkTask object");
    }

    public String getGradeBookId() {
        return gradeBookId;
    }
    
    @XmlAttribute
    public void setGradeBookId(String gradeBookId) {
        LOG.info("Setting the gradeBookId to {}", gradeBookId);
        
        this.gradeBookId = gradeBookId;
        
        LOG.debug("The updated WorkTask = {}", this);
    }
    
    public String getGradingItemId() {
        return gradingItemId;
    }

    @XmlAttribute    
    public void setGradingItemId(String gradingItemId) {
        LOG.info("Setting the gradingItemId to {}", gradingItemId);        
        this.gradingItemId = gradingItemId;
        LOG.debug("The updated WorkTask = {}", this);        
    }

    public String getWorkTaskId() {
        return workTaskId;
    }
    
    @XmlAttribute
    public void setWorkTaskId(String workTaskId) {
        LOG.info("Setting the workTaskId to {}", workTaskId);        
        this.workTaskId = workTaskId;
        LOG.debug("The updated WorkTask = {}", this);   
    }   
    
    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        LOG.info("Setting the name to {}", title);
        
        this.title = title;
        
        LOG.debug("The updated WorkTask = {}", this);
    }    
    
    public int getMaxPoints() {
        return maxPoints;
    }

    @XmlElement
    public void setMaxPoints(int maxPoints) {
        LOG.info("Setting the earnedPoints to {}", maxPoints);
        
        this.maxPoints = maxPoints;
        
        LOG.debug("The updated WorkTask = {}", this);
    }           

    @Override
    public String toString() {
        return "WorkTask{" + "gradeBookId=" + gradeBookId + ", gradingItemId=" + gradingItemId  + ", workTaskId=" + workTaskId + ", title=" + title + ", maxPoints=" + maxPoints + '}';
    }    
       
}
