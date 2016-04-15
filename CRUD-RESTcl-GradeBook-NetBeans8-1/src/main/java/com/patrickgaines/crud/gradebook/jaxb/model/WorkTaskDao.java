/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.jaxb.model;

import com.patrickgaines.crud.gradebook.jaxb.model.WorkTask;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick
 */
public enum WorkTaskDao {
    
    instance;

    private Map<String, WorkTask> workTasks = new HashMap<String, WorkTask>();
    
    private WorkTaskDao() {
        WorkTask workTask = new WorkTask();
        workTask.setGradeBookId("0");
        workTask.setGradingItemId("0");
        workTask.setWorkTaskId("0");            
        workTask.setTitle("Assignment 1");
        workTask.setMaxPoints(50);
        workTasks.put("0", workTask);
        workTask = new WorkTask();
        workTask.setGradeBookId("0");
        workTask.setGradingItemId("0");
        workTask.setWorkTaskId("1");           
        workTask.setTitle("Quiz 1");
        workTask.setMaxPoints(10);       
        workTasks.put("1", workTask);        
    }
    
    
    public Map<String, WorkTask> getModel() {
        return workTasks;
    }
    
}
