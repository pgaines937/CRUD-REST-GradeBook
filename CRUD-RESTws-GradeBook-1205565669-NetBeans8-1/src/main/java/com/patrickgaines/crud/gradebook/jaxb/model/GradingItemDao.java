/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.jaxb.model;

import com.patrickgaines.crud.gradebook.jaxb.model.GradingItem;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick
 */
public enum GradingItemDao {
    
    instance;

    private Map<String, GradingItem> gradingItems = new HashMap<String, GradingItem>();
    
    private GradingItemDao() {
        GradingItem gradingItem = new GradingItem();
        gradingItem.setGradeBookId("0");
        gradingItem.setGradingItemId("0");        
        gradingItem.setTitle("Assignments");
        gradingItem.setWeight(50);
        gradingItems.put("0", gradingItem);
        gradingItem = new GradingItem();
        gradingItem.setGradeBookId("0");
        gradingItem.setGradingItemId("1");      
        gradingItem.setTitle("Quizzes");
        gradingItem.setWeight(10);       
        gradingItems.put("1", gradingItem);        
    }
    
    
    public Map<String, GradingItem> getModel() {
        return gradingItems;
    }
    
}
