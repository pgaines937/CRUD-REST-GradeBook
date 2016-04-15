/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.restws;

import com.patrickgaines.crud.gradebook.jaxb.model.GradeBook;
import com.patrickgaines.crud.gradebook.jaxb.model.GradeBookDao;
import com.patrickgaines.crud.gradebook.jaxb.model.GradingItem;
import com.patrickgaines.crud.gradebook.jaxb.model.GradingItemDao;
import com.patrickgaines.crud.gradebook.jaxb.utils.Converter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Web Service
 * 
 * @author pgaines
 */
@Path("GradeBook")
public class GradeBookResource {
       
    private static final Logger LOG = LoggerFactory.getLogger(GradeBookResource.class);
 
    private static List<GradeBook> gradeBooks;    
    private static GradeBook gradeBook;   
    private static int newID;                              // Maintains the currest highest ID number
    
    @Context
    private UriInfo context;    
 
    /**
     * Creates a new instance of GradeBookResource
     */
    public GradeBookResource() {
        LOG.info("Creating a GradeBook Resource");
    }   
    
    /**
     * POST method for creating an instance of ScratchResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */   
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createResource(String content) {
        GradeBook gradeBook = new GradeBook(); 
        String id;        
        LOG.info("Creating the instance GradeBook Resource {}", gradeBook);
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);
        
        Response response;
        
        LOG.debug("Attempting to create an GradeBook Resource and setting it to {}", content);

        try {
            gradeBook = (GradeBook) Converter.convertFromXmlToObject(content, GradeBook.class);

            LOG.debug("The XML {} was converted to the object {}", content, gradeBook);

            LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());

            // Id for newly created resource
            id = this.getNewID(); 
            gradeBook.setGradeBookId(id);
            
            GradeBookDao.instance.getModel().putIfAbsent(id, gradeBook);            

            String xmlString = Converter.convertFromObjectToXml(gradeBook, GradeBook.class);

            URI locationURI = URI.create(context.getAbsolutePath() + "/" + id);

            response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
        } catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with GradeBook Resource", content);

            response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
        } catch (RuntimeException e) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }
                   
        LOG.debug("Generated response {}", response);
        
        return response;
    }  

   
    /**
     * Retrieves representation of a list of com.patrickgaines.crud.gradebook.jaxb.model.GradingItemResource
     * @param null
     * @return an instance of java.lang.List
     */    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<GradeBook> getGradeBooks() {
        gradeBooks = new ArrayList<GradeBook>();
        gradeBooks.addAll(GradeBookDao.instance.getModel().values());
        return gradeBooks;
    }

    /**
     * Retrieves the count of instances of com.patrickgaines.crud.gradebook.jaxb.model.GradingItemResource
     * Use http://localhost:8080/CRUD-RESTws-GradeBook-1205565669-NetBeans8-1/webresources/newid
     * to get the total number of records
     * @param null
     * @return an instance of java.lang.int
     */    
    @GET
    @Path("newid")
    @Produces(MediaType.APPLICATION_XML)
    public String getNewID() {
        LOG.info("Retrieving the GradingItem Count");
        LOG.debug("GET request"); 
        
        if (newID == 0) {
            newID = GradingItemDao.instance.getModel().size();               
        } else {
            newID += 1;            
        }
        
        return String.valueOf(newID);
    }      
    
    /**
     * Retrieves representation of an instance of com.patrickgaines.crud.gradebook.jaxb.model.GradeBookResource
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getResource(@PathParam("id") String id) {
        
        GradeBook gradeBook = GradeBookDao.instance.getModel().get(id);        
        
        LOG.info("Retrieving the GradeBook Resource {}", gradeBook);
        LOG.debug("GET request");
        LOG.debug("PathParam id = {}", id);
        
        Response response;
        
        if (gradeBook == null){
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No GradeBook Resource to return");
            
            response = Response.status(Response.Status.GONE).entity("No GradeBook Resource to return").build();
        } else {
            LOG.debug("gradeBook.getGradeBookId() = {}", gradeBook.getGradeBookId());
            if (gradeBook.getGradeBookId().equals(id)){
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                LOG.debug("Retrieving the GradeBook Resource {}", gradeBook);
                
                String xmlString = Converter.convertFromObjectToXml(gradeBook, GradeBook.class);
                
                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.debug("Retrieving the GradeBook Resource {}", gradeBook);
                
                response = Response.status(Response.Status.NOT_FOUND).entity("No GradeBook Resource to return").build();
            }
        }        
        
        LOG.debug("Returning the value {}", response);
        
        return response;
    }    


    /**
     * PUT method for updating an instance of ScratchResource
     * @param id
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateResource(@PathParam("id") String id, String content) {
        GradeBook gradeBook = GradeBookDao.instance.getModel().get(id);            
        
        LOG.info("Updating the GradeBook Resource {} with {}", gradeBook, content);
        LOG.debug("PUT request");
        LOG.debug("PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);
        
        Response response;
        
        if (gradeBook != null){
            LOG.debug("Attempting to update the GradeBook Resource {}", gradeBook);
            
            try {
                gradeBook = (GradeBook) Converter.convertFromXmlToObject(content, GradeBook.class);
                
                LOG.debug("The XML {} was converted to the object {}", content, gradeBook);         
                LOG.debug("Updated GradeBook Resource to {}", gradeBook);
                
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
            
                String xmlString = Converter.convertFromObjectToXml(gradeBook, GradeBook.class);
                
                response = Response.status(Response.Status.OK).entity(content).build();
            } catch (JAXBException e) {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with GradeBook Resource", content);
                
                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } catch (RuntimeException e) {
                LOG.debug("Catch All exception");
                
                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
                
                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        } else {
            LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
            LOG.debug("Cannot update GradeBook Resource {} as it has not yet been created", gradeBook);
                      
            response = Response.status(Response.Status.CONFLICT).entity(content).build();
        }

        LOG.debug("Generated response {}", response);
        
        return response;
    }

    /**
     * Retrieves representation of an instance of com.patrickgaines.crud.gradebook.jaxb.model.GradeBookResource
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteResource(@PathParam("id") String id) {        
        GradeBook gradeBook = GradeBookDao.instance.getModel().get(id);            
        LOG.info("Removing the GradeBook Resource {}", gradeBook);
        LOG.debug("DELETE request");
        LOG.debug("PathParam id = {}", id);
        
        Response response;
        
        if (gradeBook == null){
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No GradeBook Resource to delete");
            
            response = Response.status(Response.Status.GONE).build();
        } else {
            if (gradeBook.getGradeBookId().equals(id)){
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                LOG.debug("Deleting the GradeBook Resource {}", gradeBook);
                
                gradeBook = null;
                 
                GradeBookDao.instance.getModel().remove(id);   
                
                response = Response.status(Response.Status.NO_CONTENT).build();
            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.debug("Retrieving the GradeBook Resource {}", gradeBook);
                
                response = Response.status(Response.Status.NOT_FOUND).build();
            }
        }        
        
        LOG.debug("Generated response {}", response);
        
        return response;
    }    
    
}
