/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.restcl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jersey REST client generated for REST resource:WorkTaskResource
 * [WorkTask]<br>
 * USAGE:
 * <pre>
        WorkTask_CRUD_cl client = new WorkTask_CRUD_cl();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author pgaines
 */
public class WorkTask_CRUD_cl {
    
    private static final Logger LOG = LoggerFactory.getLogger(WorkTask_CRUD_cl.class);
    
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/CRUD-RESTws-GradeBook-1205565669-NetBeans8-1/webresources";

    public WorkTask_CRUD_cl() {        
        LOG.info("Creating a WorkTask_CRUD REST client");

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI).path("Gradebook");
        LOG.debug("webResource = {}", webResource.getURI());
    }

    public ClientResponse createWorkTask(Object requestEntity, String gradeBookId, String gradingItemId) throws UniformInterfaceException {
        LOG.info("Initiating a Create request");
        LOG.debug("XML String = {}", (String) requestEntity);
        
        return webResource.path(gradeBookId).path("GradingItem").path(gradingItemId).path("WorkTask").type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse deleteWorkTask(String gradeBookId, String gradingItemId, String workTaskId) throws UniformInterfaceException {
        LOG.info("Initiating a Delete request");
        LOG.debug("Id = {}", workTaskId);
        
        return webResource.path(gradeBookId).path("GradingItem").path(gradingItemId).path("WorkTask").path(workTaskId).delete(ClientResponse.class);
    }

    public ClientResponse updateWorkTask(Object requestEntity, String gradeBookId, String gradingItemId, String workTaskId) throws UniformInterfaceException {
        LOG.info("Initiating an Update request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", workTaskId);
        
        return webResource.path(gradeBookId).path("GradingItem").path(gradingItemId).path("WorkTask").path(workTaskId).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public <T> T retrieveWorkTask(Class<T> responseType, String gradeBookId, String gradingItemId, String workTaskId) throws UniformInterfaceException {
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", workTaskId);
        
        //WebResource resource = webResource;
        //resource = resource.path(id);
        
        return webResource.path(gradeBookId).path("GradingItem").path(gradingItemId).path("WorkTask").path(workTaskId).accept(MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        LOG.info("Closing the REST Client");
        
        client.destroy();
    }
    
}
