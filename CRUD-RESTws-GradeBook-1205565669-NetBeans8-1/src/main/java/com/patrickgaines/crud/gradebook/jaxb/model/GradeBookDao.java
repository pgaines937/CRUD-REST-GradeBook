/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.jaxb.model;

import com.patrickgaines.crud.gradebook.jaxb.model.GradeBook;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Patrick
 */
public enum GradeBookDao {
    
    instance;

    private Map<String, GradeBook> gradeBooks = new HashMap<String, GradeBook>();
    
    private GradeBookDao() {
        GradeBook gradeBook = new GradeBook();
        gradeBook.setGradeBookId("0");
        gradeBook.setTitle("CSE 564");
        gradeBooks.put("0", gradeBook);
        gradeBook = new GradeBook();
        gradeBook.setGradeBookId("1");
        gradeBook.setTitle("CSE 572");  
        gradeBooks.put("1", gradeBook);        
    }
       
    public Map<String, GradeBook> getModel() {
        return gradeBooks;
    }
    
}
