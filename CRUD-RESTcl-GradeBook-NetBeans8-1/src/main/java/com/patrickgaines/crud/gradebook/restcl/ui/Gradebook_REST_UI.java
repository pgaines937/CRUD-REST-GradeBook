/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.restcl.ui;

import com.patrickgaines.crud.gradebook.jaxb.model.GradeBook;
import com.patrickgaines.crud.gradebook.jaxb.model.GradeBookDao;
import com.patrickgaines.crud.gradebook.jaxb.model.WorkTask;
import com.patrickgaines.crud.gradebook.jaxb.model.GradingItem;
import com.patrickgaines.crud.gradebook.jaxb.model.GradingItemDao;
import com.patrickgaines.crud.gradebook.jaxb.model.StudentGrade;
import com.patrickgaines.crud.gradebook.jaxb.model.StudentGradeDao;
import com.patrickgaines.crud.gradebook.jaxb.model.WorkTaskDao;
import com.patrickgaines.crud.gradebook.jaxb.utils.Converter;
import com.patrickgaines.crud.gradebook.restcl.GradeBook_CRUD_cl;
import com.patrickgaines.crud.gradebook.restcl.WorkTask_CRUD_cl;
import com.patrickgaines.crud.gradebook.restcl.GradingItem_CRUD_cl;
import com.patrickgaines.crud.gradebook.restcl.StudentGrade_CRUD_cl;
import com.sun.jersey.api.client.ClientResponse;

import javax.ws.rs.core.Response;

import javax.swing.JFrame;

import javax.xml.bind.JAXBException;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author pgaines
 */
public class Gradebook_REST_UI extends javax.swing.JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(Gradebook_REST_UI.class);

    private GradeBook_CRUD_cl gradeBook_CRUD_rest_client;
    private GradingItem_CRUD_cl gradingItem_CRUD_rest_client;
    private WorkTask_CRUD_cl workTask_CRUD_rest_client;
    private StudentGrade_CRUD_cl studentGrade_CRUD_rest_client;

    private URI resourceURI;

    /**
     * Creates new form Gradebook_REST_UI
     */
    public Gradebook_REST_UI() {
        LOG.info("Creating a GradingItem_REST_UI object");
        initComponents();

        gradeBook_CRUD_rest_client = new GradeBook_CRUD_cl();
        gradingItem_CRUD_rest_client = new GradingItem_CRUD_cl();
        workTask_CRUD_rest_client = new WorkTask_CRUD_cl();
        studentGrade_CRUD_rest_client = new StudentGrade_CRUD_cl();
    }

    private String convertGradeBookFormToXMLString() {
        GradeBook gradeBook = new GradeBook();
        if (!jTextFieldGBID.getText().equals("")) {
            gradeBook.setGradeBookId(jTextFieldGBID.getText());
        }
        gradeBook.setTitle(jTextField14.getText());

        String xmlString = Converter.convertFromObjectToXml(gradeBook, gradeBook.getClass());

        return xmlString;
    }

    private void populateGradeBookForm(ClientResponse clientResponse) {
        LOG.info("Populating the UI with the GradeBook info");

        String entity = clientResponse.getEntity(String.class);

        LOG.debug("The Client Response entity is {}", entity);

        try {
            if ((clientResponse.getStatus() == Response.Status.OK.getStatusCode())
                    || (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode())) {
                GradeBook gradeBook = (GradeBook) Converter.convertFromXmlToObject(entity, GradeBook.class);

                GradeBookDao.instance.getModel().putIfAbsent(gradeBook.getGradeBookId(), gradeBook);

                LOG.debug("The Client Response gradeBook object is {}", gradeBook);

                // Populate GradeBook info
                jTextFieldGBID.setText(gradeBook.getGradeBookId());
                jTextFieldGIID.setText("");
                jTextFieldWTID.setText("");
                jTextFieldSGID.setText("");
                jTextField14.setText(gradeBook.getTitle());
            } else {
                jTextFieldGBID.setText("");
                jTextFieldGIID.setText("");
                jTextFieldWTID.setText("");
                jTextFieldSGID.setText("");
                jTextField14.setText("");
            }

            // Populate HTTP Header Information
            jTextField1.setText(Integer.toString(clientResponse.getStatus()));
            jTextField2.setText(clientResponse.getType().toString());

            // The Location filed is only populated when a Resource is created
            if (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
                jTextField3.setText(clientResponse.getLocation().toString());
            } else {
                jTextField3.setText("");
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String convertGradingItemFormToXMLString() {
        GradingItem gradingItem = new GradingItem();
        if (!jTextFieldGBID.getText().equals("")) {
            gradingItem.setGradeBookId(jTextFieldGBID.getText());
        }
        if (!jTextFieldGIID.getText().equals("")) {
            gradingItem.setGradingItemId(jTextFieldGIID.getText());
        }
        gradingItem.setTitle(jTextField5.getText());
        gradingItem.setWeight(Integer.parseInt(jTextField6.getText()));

        String xmlString = Converter.convertFromObjectToXml(gradingItem, gradingItem.getClass());

        return xmlString;
    }

    private void populateGradingItemForm(ClientResponse clientResponse) {
        LOG.info("Populating the UI with the GradingItem info");

        String entity = clientResponse.getEntity(String.class);

        LOG.debug("The Client Response entity is {}", entity);

        try {
            if ((clientResponse.getStatus() == Response.Status.OK.getStatusCode())
                    || (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode())) {
                GradingItem gradingItem = (GradingItem) Converter.convertFromXmlToObject(entity, GradingItem.class);
                LOG.debug("The Client Response gradingItem object is {}", gradingItem);

                GradingItemDao.instance.getModel().putIfAbsent(gradingItem.getGradingItemId(), gradingItem);

                // Populate GradingItem info
                jTextFieldGBID.setText(gradingItem.getGradeBookId());
                jTextFieldGIID.setText(gradingItem.getGradingItemId());
                jTextFieldWTID.setText("");
                jTextFieldSGID.setText("");
                jTextField5.setText(gradingItem.getTitle());
                jTextField6.setText(Integer.toString(gradingItem.getWeight()));
            } else {
                jTextFieldGBID.setText("");
                jTextFieldGIID.setText("");
                jTextFieldWTID.setText("");
                jTextFieldSGID.setText("");
                jTextField5.setText("");
                jTextField6.setText("");
            }

            // Populate HTTP Header Information
            jTextField1.setText(Integer.toString(clientResponse.getStatus()));
            jTextField2.setText(clientResponse.getType().toString());

            // The Location filed is only populated when a Resource is created
            if (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
                jTextField3.setText(clientResponse.getLocation().toString());
            } else {
                jTextField3.setText("");
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String convertWorkTaskFormToXMLString() {
        WorkTask workTask = new WorkTask();
        if (!jTextFieldGBID.getText().equals("")) {
            workTask.setGradeBookId(jTextFieldGBID.getText());
        }
        if (!jTextFieldGIID.getText().equals("")) {
            workTask.setGradingItemId(jTextFieldGIID.getText());
        }
        if (!jTextFieldWTID.getText().equals("")) {
            workTask.setWorkTaskId(jTextFieldWTID.getText());
        }
        workTask.setTitle(jTextField8.getText());
        workTask.setMaxPoints(Integer.parseInt(jTextField10.getText()));

        String xmlString = Converter.convertFromObjectToXml(workTask, workTask.getClass());

        return xmlString;
    }

    private void populateWorkTaskForm(ClientResponse clientResponse) {
        LOG.info("Populating the UI with the WorkTask info");

        String entity = clientResponse.getEntity(String.class);

        LOG.debug("The Client Response entity is {}", entity);

        try {
            if ((clientResponse.getStatus() == Response.Status.OK.getStatusCode())
                    || (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode())) {
                WorkTask workTask = (WorkTask) Converter.convertFromXmlToObject(entity, WorkTask.class);
                LOG.debug("The Client Response workTask object is {}", workTask);

                WorkTaskDao.instance.getModel().putIfAbsent(workTask.getWorkTaskId(), workTask);

                // Populate WorkTask info
                jTextFieldGBID.setText(workTask.getGradeBookId());
                jTextFieldGIID.setText(workTask.getGradingItemId());
                jTextFieldWTID.setText(workTask.getWorkTaskId());
                jTextFieldSGID.setText("");
                jTextField8.setText(workTask.getTitle());
                jTextField10.setText(Integer.toString(workTask.getMaxPoints()));

            } else {
                jTextFieldGBID.setText("");
                jTextFieldGIID.setText("");
                jTextFieldWTID.setText("");
                jTextFieldSGID.setText("");
                jTextField8.setText("");
                jTextField10.setText("");
            }

            // Populate HTTP Header Information
            jTextField1.setText(Integer.toString(clientResponse.getStatus()));
            jTextField2.setText(clientResponse.getType().toString());

            // The Location field is only populated when a Resource is created
            if (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
                jTextField3.setText(clientResponse.getLocation().toString());
            } else {
                jTextField3.setText("");
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private String convertStudentGradeFormToXMLString() {
        StudentGrade studentGrade = new StudentGrade();
        if (!jTextFieldGBID.getText().equals("")) {
            studentGrade.setGradeBookId(jTextFieldGBID.getText());
        }
        if (!jTextFieldGIID.getText().equals("")) {
            studentGrade.setGradingItemId(jTextFieldGIID.getText());
        }
        if (!jTextFieldWTID.getText().equals("")) {
            studentGrade.setWorkTaskId(jTextFieldWTID.getText());
        }
        if (!jTextFieldSGID.getText().equals("")) {
            studentGrade.setStudentGradeId(jTextFieldSGID.getText());
        }
        studentGrade.setName(jTextField15.getText());
        studentGrade.setEarnedPoints(Integer.parseInt(jTextField17.getText()));
        studentGrade.setFeedback(jTextArea1.getText());
        studentGrade.setAppeal(jTextArea2.getText());

        String xmlString = Converter.convertFromObjectToXml(studentGrade, studentGrade.getClass());

        return xmlString;
    }

    private void populateStudentGradeForm(ClientResponse clientResponse) {
        LOG.info("Populating the UI with the Student info");

        String entity = clientResponse.getEntity(String.class);

        LOG.debug("The Client Response entity is {}", entity);

        try {
            if ((clientResponse.getStatus() == Response.Status.OK.getStatusCode())
                    || (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode())) {
                StudentGrade studentGrade = (StudentGrade) Converter.convertFromXmlToObject(entity, StudentGrade.class);
                LOG.debug("The Client Response studentGrade object is {}", studentGrade);

                StudentGradeDao.instance.getModel().putIfAbsent(studentGrade.getStudentGradeId(), studentGrade);

                // Populate StudentGrade info
                jTextFieldGBID.setText(studentGrade.getGradeBookId());
                jTextFieldGIID.setText(studentGrade.getGradingItemId());
                jTextFieldWTID.setText(studentGrade.getWorkTaskId());
                jTextFieldSGID.setText(studentGrade.getStudentGradeId());
                jTextField15.setText(studentGrade.getName());
                jTextField17.setText(Integer.toString(studentGrade.getEarnedPoints()));
                jTextArea1.setText(studentGrade.getFeedback());
                jTextArea2.setText(studentGrade.getAppeal());
            } else {
                jTextFieldGBID.setText("");
                jTextFieldGIID.setText("");
                jTextFieldWTID.setText("");
                jTextFieldSGID.setText("");
                jTextField15.setText("");
                jTextField17.setText("");
                jTextArea1.setText("");
                jTextArea2.setText("");
            }

            // Populate HTTP Header Information
            jTextField1.setText(Integer.toString(clientResponse.getStatus()));
            jTextField2.setText(clientResponse.getType().toString());

            // The Location field is only populated when a Resource is created
            if (clientResponse.getStatus() == Response.Status.CREATED.getStatusCode()) {
                jTextField3.setText(clientResponse.getLocation().toString());
            } else {
                jTextField3.setText("");
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField14 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jRadioButton16 = new javax.swing.JRadioButton();
        jLabel50 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jPanelGradingItems = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel33 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanelWorkTask = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jLabel41 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jPanelStudentGrades = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel32 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jLabel43 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanelHTTPHeaderInfo = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldGBID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldGIID = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldWTID = new javax.swing.JTextField();
        jTextFieldSGID = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Grade Book Values");

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel48.setText("Title");

        buttonGroup4.add(jRadioButton13);
        jRadioButton13.setText("Create");
        jRadioButton13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton13ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton14);
        jRadioButton14.setText("Read");
        jRadioButton14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton14ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton15);
        jRadioButton15.setText("Update");
        jRadioButton15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton15ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton16);
        jRadioButton16.setText("Delete");
        jRadioButton16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton16ActionPerformed(evt);
            }
        });

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("Action");

        jButton4.setText("Submit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jRadioButton14)
                        .addComponent(jRadioButton15)
                        .addComponent(jRadioButton16)
                        .addComponent(jRadioButton13))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addGap(4, 4, 4)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(jLabel50, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel50)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addComponent(jButton4))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField14)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("GradeBook", jPanel1);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Grading Item Values");

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel40.setText("Weight");

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel44.setText("Title");

        buttonGroup4.add(jRadioButton1);
        jRadioButton1.setText("Create");
        jRadioButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton2);
        jRadioButton2.setText("Read");
        jRadioButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton3);
        jRadioButton3.setText("Update");
        jRadioButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton3ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton4);
        jRadioButton4.setText("Delete");
        jRadioButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton4ActionPerformed(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Action");

        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jRadioButton2)
                        .addComponent(jRadioButton3)
                        .addComponent(jRadioButton4)
                        .addComponent(jRadioButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(4, 4, 4)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel33)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        javax.swing.GroupLayout jPanelGradingItemsLayout = new javax.swing.GroupLayout(jPanelGradingItems);
        jPanelGradingItems.setLayout(jPanelGradingItemsLayout);
        jPanelGradingItemsLayout.setHorizontalGroup(
            jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradingItemsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
                    .addComponent(jTextField6))
                .addGap(134, 134, 134))
        );
        jPanelGradingItemsLayout.setVerticalGroup(
            jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGradingItemsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelGradingItemsLayout.createSequentialGroup()
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelGradingItemsLayout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelGradingItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Grading Items", jPanelGradingItems);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Work Task Values");

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Title");

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel28.setText("Max Points");

        buttonGroup4.add(jRadioButton5);
        jRadioButton5.setText("Create");
        jRadioButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton6);
        jRadioButton6.setText("Read");
        jRadioButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton6ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton7);
        jRadioButton7.setText("Update");
        jRadioButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton7ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton8);
        jRadioButton8.setText("Delete");
        jRadioButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton8ActionPerformed(evt);
            }
        });

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Action");

        jButton2.setText("Submit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jRadioButton6)
                        .addComponent(jRadioButton7)
                        .addComponent(jRadioButton8)
                        .addComponent(jRadioButton5))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(4, 4, 4)))
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addComponent(jButton2))
        );

        javax.swing.GroupLayout jPanelWorkTaskLayout = new javax.swing.GroupLayout(jPanelWorkTask);
        jPanelWorkTask.setLayout(jPanelWorkTaskLayout);
        jPanelWorkTaskLayout.setHorizontalGroup(
            jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWorkTaskLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                    .addGroup(jPanelWorkTaskLayout.createSequentialGroup()
                        .addGroup(jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField8)
                            .addComponent(jTextField10))))
                .addContainerGap())
        );
        jPanelWorkTaskLayout.setVerticalGroup(
            jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelWorkTaskLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelWorkTaskLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelWorkTaskLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelWorkTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(370, 370, 370))))
        );

        jTabbedPane1.addTab("Work Task", jPanelWorkTask);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel32.setText("Appeal");

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel30.setText("Feedback");

        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("Student Grade Values");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Student Name");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Earned Points");

        buttonGroup4.add(jRadioButton9);
        jRadioButton9.setText("Create");
        jRadioButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton9ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton10);
        jRadioButton10.setText("Read");
        jRadioButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton10ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton11);
        jRadioButton11.setText("Update");
        jRadioButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton11ActionPerformed(evt);
            }
        });

        buttonGroup4.add(jRadioButton12);
        jRadioButton12.setText("Delete");
        jRadioButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton12ActionPerformed(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Action");

        jButton3.setText("Submit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jRadioButton10)
                        .addComponent(jRadioButton11)
                        .addComponent(jRadioButton12)
                        .addComponent(jRadioButton9))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(4, 4, 4)))
                .addContainerGap(36, Short.MAX_VALUE))
            .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel43)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addComponent(jButton3))
        );

        javax.swing.GroupLayout jPanelStudentGradesLayout = new javax.swing.GroupLayout(jPanelStudentGrades);
        jPanelStudentGrades.setLayout(jPanelStudentGradesLayout);
        jPanelStudentGradesLayout.setHorizontalGroup(
            jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStudentGradesLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStudentGradesLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                            .addComponent(jTextField17, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)))
                    .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 613, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(135, 135, 135))
        );
        jPanelStudentGradesLayout.setVerticalGroup(
            jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelStudentGradesLayout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelStudentGradesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel32)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Student Grades", jPanelStudentGrades);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("HTTP Status Code");

        jLabel38.setText("Media Type");

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel37.setText("Location");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("HTTP Header Info");

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelHTTPHeaderInfoLayout = new javax.swing.GroupLayout(jPanelHTTPHeaderInfo);
        jPanelHTTPHeaderInfo.setLayout(jPanelHTTPHeaderInfoLayout);
        jPanelHTTPHeaderInfoLayout.setHorizontalGroup(
            jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHTTPHeaderInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelHTTPHeaderInfoLayout.createSequentialGroup()
                        .addGroup(jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelHTTPHeaderInfoLayout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2))
                            .addGroup(jPanelHTTPHeaderInfoLayout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanelHTTPHeaderInfoLayout.setVerticalGroup(
            jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelHTTPHeaderInfoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel38)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addGroup(jPanelHTTPHeaderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)))
        );

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("StudentGrade ID");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("WorkTask ID");

        jTextFieldGBID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGBIDActionPerformed(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("GradeBook ID");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("GradingItem ID");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Path IDs");

        jTextFieldSGID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSGIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldWTID, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(jTextFieldGBID)
                            .addComponent(jTextFieldGIID)
                            .addComponent(jTextFieldSGID))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldGBID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldGIID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldWTID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(jTextFieldSGID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanelHTTPHeaderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelHTTPHeaderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton4ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton4.getText());
    }//GEN-LAST:event_jRadioButton4ActionPerformed

    private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton3ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton3.getText());
    }//GEN-LAST:event_jRadioButton3ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton2.getText());
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton1.getText());
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LOG.info("Invoking REST Client based on selection");

        String gradeBookID = jTextFieldGBID.getText();
        String gradingItemID = jTextFieldGIID.getText();

        if (jRadioButton1.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton1.getText());//Create

            ClientResponse clientResponse = gradingItem_CRUD_rest_client.createGradingItem(this.convertGradingItemFormToXMLString(), gradeBookID);

            resourceURI = clientResponse.getLocation();
            LOG.debug("Retrieved location {}", resourceURI);

            this.populateGradingItemForm(clientResponse);
        } else if (jRadioButton2.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton2.getText());// Read

            ClientResponse clientResponse = gradingItem_CRUD_rest_client.retrieveGradingItem(ClientResponse.class, gradeBookID, gradingItemID);

            this.populateGradingItemForm(clientResponse);
        } else if (jRadioButton3.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton3.getText());//Update

            ClientResponse clientResponse = gradingItem_CRUD_rest_client.updateGradingItem(this.convertGradingItemFormToXMLString(), gradeBookID, gradingItemID);

            this.populateGradingItemForm(clientResponse);
        } else if (jRadioButton4.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton4.getText());//Delete

            ClientResponse clientResponse = gradingItem_CRUD_rest_client.deleteGradingItem(gradeBookID, gradingItemID);

            GradingItemDao.instance.getModel().remove(gradingItemID);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton5.getText());
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jRadioButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton6ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton6.getText());
    }//GEN-LAST:event_jRadioButton6ActionPerformed

    private void jRadioButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton7ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton7.getText());
    }//GEN-LAST:event_jRadioButton7ActionPerformed

    private void jRadioButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton8ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton8.getText());
    }//GEN-LAST:event_jRadioButton8ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        LOG.info("Invoking REST Client based on selection");

        String gradeBookID = jTextFieldGBID.getText();
        String gradingItemID = jTextFieldGIID.getText();
        String workTaskID = jTextFieldWTID.getText();

        if (jRadioButton5.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton5.getText());//Create

            ClientResponse clientResponse = workTask_CRUD_rest_client.createWorkTask(this.convertWorkTaskFormToXMLString(), gradeBookID, gradingItemID);

            resourceURI = clientResponse.getLocation();
            LOG.debug("Retrieved location {}", resourceURI);

            this.populateWorkTaskForm(clientResponse);
        } else if (jRadioButton6.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton6.getText());// Read

            ClientResponse clientResponse = workTask_CRUD_rest_client.retrieveWorkTask(ClientResponse.class, gradeBookID, gradingItemID, workTaskID);

            this.populateWorkTaskForm(clientResponse);
        } else if (jRadioButton7.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton7.getText());//Update

            ClientResponse clientResponse = workTask_CRUD_rest_client.updateWorkTask(this.convertWorkTaskFormToXMLString(), gradeBookID, gradingItemID, workTaskID);

            this.populateWorkTaskForm(clientResponse);
        } else if (jRadioButton8.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton8.getText());//Delete

            ClientResponse clientResponse = workTask_CRUD_rest_client.deleteWorkTask(gradeBookID, gradingItemID, workTaskID);

            WorkTaskDao.instance.getModel().remove(workTaskID);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jRadioButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton9ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton9.getText());
    }//GEN-LAST:event_jRadioButton9ActionPerformed

    private void jRadioButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton10ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton10.getText());
    }//GEN-LAST:event_jRadioButton10ActionPerformed

    private void jRadioButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton11ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton11.getText());
    }//GEN-LAST:event_jRadioButton11ActionPerformed

    private void jRadioButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton12ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton12.getText());
    }//GEN-LAST:event_jRadioButton12ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        LOG.info("Invoking REST Client based on selection");

        String gradeBookID = jTextFieldGBID.getText();
        String gradingItemID = jTextFieldGIID.getText();
        String workTaskID = jTextFieldWTID.getText();
        String studentGradeID = jTextFieldSGID.getText();

        if (jRadioButton9.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton9.getText());//Create

            ClientResponse clientResponse = studentGrade_CRUD_rest_client.createStudentGrade(this.convertStudentGradeFormToXMLString(), gradeBookID, gradingItemID, workTaskID);

            resourceURI = clientResponse.getLocation();
            LOG.debug("Retrieved location {}", resourceURI);

            this.populateStudentGradeForm(clientResponse);
        } else if (jRadioButton10.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton10.getText());// Read

            ClientResponse clientResponse = studentGrade_CRUD_rest_client.retrieveStudentGrade(ClientResponse.class, gradeBookID, gradingItemID, workTaskID, studentGradeID);

            this.populateStudentGradeForm(clientResponse);
        } else if (jRadioButton11.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton11.getText());//Update

            ClientResponse clientResponse = studentGrade_CRUD_rest_client.updateStudentGrade(this.convertStudentGradeFormToXMLString(), gradeBookID, gradingItemID, workTaskID, studentGradeID);

            this.populateStudentGradeForm(clientResponse);
        } else if (jRadioButton12.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton12.getText());//Delete

            ClientResponse clientResponse = studentGrade_CRUD_rest_client.deleteStudentGrade(gradeBookID, gradingItemID, workTaskID, studentGradeID);

            StudentGradeDao.instance.getModel().remove(studentGradeID);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextFieldSGIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSGIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSGIDActionPerformed

    private void jRadioButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton13ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton13.getText());
    }//GEN-LAST:event_jRadioButton13ActionPerformed

    private void jRadioButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton14ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton14.getText());
    }//GEN-LAST:event_jRadioButton14ActionPerformed

    private void jRadioButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton15ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton15.getText());
    }//GEN-LAST:event_jRadioButton15ActionPerformed

    private void jRadioButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton16ActionPerformed
        LOG.info("Selecting radio button {}", jRadioButton16.getText());
    }//GEN-LAST:event_jRadioButton16ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        LOG.info("Invoking REST Client based on selection");

        String gradeBookID = jTextFieldGBID.getText();

        if (jRadioButton13.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton13.getText());//Create

            ClientResponse clientResponse = gradeBook_CRUD_rest_client.createGradeBook(this.convertGradeBookFormToXMLString());

            resourceURI = clientResponse.getLocation();
            LOG.debug("Retrieved location {}", resourceURI);

            this.populateGradeBookForm(clientResponse);
        } else if (jRadioButton14.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton14.getText());// Read

            ClientResponse clientResponse = gradeBook_CRUD_rest_client.retrieveGradeBook(ClientResponse.class, gradeBookID);

            this.populateGradeBookForm(clientResponse);
        } else if (jRadioButton15.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton15.getText());//Update

            ClientResponse clientResponse = gradeBook_CRUD_rest_client.updateGradeBook(this.convertGradeBookFormToXMLString(), gradeBookID);

            this.populateGradeBookForm(clientResponse);
        } else if (jRadioButton16.isSelected()) {
            LOG.debug("Invoking {} action", jRadioButton16.getText());//Delete

            ClientResponse clientResponse = gradeBook_CRUD_rest_client.deleteGradeBook(gradeBookID);

            GradeBookDao.instance.getModel().remove(gradeBookID);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextFieldGBIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGBIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGBIDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gradebook_REST_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gradebook_REST_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gradebook_REST_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gradebook_REST_UI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gradebook_REST_UI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelGradingItems;
    private javax.swing.JPanel jPanelHTTPHeaderInfo;
    private javax.swing.JPanel jPanelStudentGrades;
    private javax.swing.JPanel jPanelWorkTask;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextFieldGBID;
    private javax.swing.JTextField jTextFieldGIID;
    private javax.swing.JTextField jTextFieldSGID;
    private javax.swing.JTextField jTextFieldWTID;
    // End of variables declaration//GEN-END:variables
}
