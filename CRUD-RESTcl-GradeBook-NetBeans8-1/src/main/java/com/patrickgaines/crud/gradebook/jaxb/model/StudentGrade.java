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
    "studentGradeId",
    "name",
    "earnedPoints",
    "feedback",
    "appeal"})
public class StudentGrade {
    
    private static final Logger LOG = LoggerFactory.getLogger(StudentGrade.class);
        
    private String      gradeBookId;             // ID number assigned by the server
    private String      gradingItemId;             // ID number assigned by the server
    private String      workTaskId;             // ID number assigned by the server 
    private String      studentGradeId;             // ID number assigned by the server     
    private String      name;          // Name of the student    
    private int         earnedPoints;   // How many points the student earned on the work task
    private String      feedback;       // The instructor's feedback to the student
    private String      appeal;         // The student appeals the instructor's grade    

    public StudentGrade() {
        LOG.info("Creating a Student object");
    }

    public String getGradeBookId() {
        return gradeBookId;
    }
    
    @XmlAttribute
    public void setGradeBookId(String gradeBookId) {
        LOG.info("Setting the gradeBookId to {}", gradeBookId);
        
        this.gradeBookId = gradeBookId;
        
        LOG.debug("The updated StudentGrade = {}", this);
    }
    
    public String getGradingItemId() {
        return gradingItemId;
    }

    @XmlAttribute    
    public void setGradingItemId(String gradingItemId) {
        LOG.info("Setting the gradingItemId to {}", gradingItemId);        
        this.gradingItemId = gradingItemId;
        LOG.debug("The updated StudentGrade = {}", this);        
    }

    public String getWorkTaskId() {
        return workTaskId;
    }
    
    @XmlAttribute
    public void setWorkTaskId(String workTaskId) {
        LOG.info("Setting the workTaskId to {}", workTaskId);        
        this.workTaskId = workTaskId;
        LOG.debug("The updated StudentGrade = {}", this);   
    } 
    
    public String getStudentGradeId() {
        return studentGradeId;
    }

    @XmlAttribute    
    public void setStudentGradeId(String studentGradeId) {
        LOG.info("Setting the studentGradeId to {}", studentGradeId);        
        this.studentGradeId = studentGradeId;
        LOG.debug("The updated StudentGrade = {}", this);   
    }    
    
    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        LOG.info("Setting the name to {}", name);
        
        this.name = name;
        
        LOG.debug("The updated student = {}", this);
    }    
    
    public int getEarnedPoints() {
        return earnedPoints;
    }

    @XmlElement
    public void setEarnedPoints(int earnedPoints) {
        LOG.info("Setting the earnedPoints to {}", earnedPoints);
        
        this.earnedPoints = earnedPoints;
        
        LOG.debug("The updated grade = {}", this);
    }  

    public String getFeedback() {
        return feedback;
    }
    
    @XmlElement
    public void setFeedback(String feedback) {
        LOG.info("Setting the feedback to {}", feedback);
        
        this.feedback = feedback;
        
        LOG.debug("The updated grade = {}", this);
    } 
    
    public String getAppeal() {
        return appeal;
    }
    
    @XmlElement
    public void setAppeal(String appeal) {
        LOG.info("Setting the appeal to {}", appeal);
        
        this.appeal = appeal;
        
        LOG.debug("The updated grade = {}", this);
    }          

    @Override
    public String toString() {
        return "StudentGrade{" + "gradeBookId=" + gradeBookId + ", gradingItemId=" + gradingItemId  + ", workTaskId=" + workTaskId  + ", studentGradeId=" + studentGradeId  + ", name=" + name + ", earnedPoints=" + earnedPoints + ", feedback=" + feedback + ", appeal=" + appeal + '}';
    }    
       
}
