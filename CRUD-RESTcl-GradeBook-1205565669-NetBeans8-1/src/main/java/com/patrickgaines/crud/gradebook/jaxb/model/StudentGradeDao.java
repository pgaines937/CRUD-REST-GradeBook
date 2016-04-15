/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.jaxb.model;

import com.patrickgaines.crud.gradebook.jaxb.model.StudentGrade;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick
 */
public enum StudentGradeDao {
    
    instance;

    private Map<String, StudentGrade> workTasks = new HashMap<String, StudentGrade>();
    
    private StudentGradeDao() {
        StudentGrade studentGrade = new StudentGrade();
        studentGrade.setGradeBookId("0");
        studentGrade.setGradingItemId("0");
        studentGrade.setWorkTaskId("0");
        studentGrade.setStudentGradeId("0");         
        studentGrade.setEarnedPoints(50);
        studentGrade.setFeedback("Perfect Score!");   
        studentGrade.setAppeal("");          
        workTasks.put("0", studentGrade);
        studentGrade = new StudentGrade();
        studentGrade.setGradeBookId("0");
        studentGrade.setGradingItemId("0");
        studentGrade.setWorkTaskId("0");
        studentGrade.setStudentGradeId("1");         
        studentGrade.setEarnedPoints(45);
        studentGrade.setFeedback("Made a mistake");   
        studentGrade.setAppeal("");       
        workTasks.put("1", studentGrade);        
    }
    
    public Map<String, StudentGrade> getModel() {
        return workTasks;
    }
    
}
