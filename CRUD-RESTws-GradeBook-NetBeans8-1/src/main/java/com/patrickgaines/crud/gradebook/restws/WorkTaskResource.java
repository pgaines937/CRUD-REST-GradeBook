/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.patrickgaines.crud.gradebook.restws;

import com.patrickgaines.crud.gradebook.jaxb.model.GradingItem;
import com.patrickgaines.crud.gradebook.jaxb.model.GradingItemDao;
import com.patrickgaines.crud.gradebook.jaxb.model.WorkTask;
import com.patrickgaines.crud.gradebook.jaxb.model.WorkTaskDao;
import com.patrickgaines.crud.gradebook.jaxb.utils.Converter;
import java.net.URI;
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
 *
 * @author Patrick
 */
@Path("Gradebook/{gradingItemId}/GradingItem/{gradingItemId}/WorkTask")
public class WorkTaskResource {

    private static final Logger LOG = LoggerFactory.getLogger(WorkTaskResource.class);
    private static List<WorkTask> workTasks;
    private static int newID;                              // Maintains the currest highest ID number

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WorkTaskResource
     */
    public WorkTaskResource() {
        LOG.info("Creating a WorkTask Resource");
    }

    /**
     * POST method for creating an instance of ScratchResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response createResource(String content) {
        WorkTask workTask = new WorkTask();
        String id;
        LOG.info("Creating the instance WorkTask Resource {}", workTask);
        LOG.debug("POST request");
        LOG.debug("Request Content = {}", content);

        Response response;

        try {
            workTask = (WorkTask) Converter.convertFromXmlToObject(content, WorkTask.class);

            LOG.debug("The XML {} was converted to the object {}", content, workTask);

            LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());

            // Id for newly created resource
            id = this.getNewID();
            workTask.setWorkTaskId(id);

            WorkTaskDao.instance.getModel().putIfAbsent(id, workTask);

            String xmlString = Converter.convertFromObjectToXml(workTask, WorkTask.class);

            URI locationURI = URI.create(context.getAbsolutePath() + "/" + id);

            response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();
        } catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with WorkTask Resource", content);

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
     * Retrieves the count of instances of
     * com.patrickgaines.crud.gradebook.jaxb.model.GradingItemResource Use
     * http://localhost:8080/CRUD-RESTws-GradeBook-1205565669-NetBeans8-1/webresources/Gradebook/0/GradingItem/0/WorkTask/newid
     * to get the total number of records
     *
     * @param null
     * @return an instance of java.lang.int
     */
    @GET
    @Path("newid")
    @Produces(MediaType.APPLICATION_XML)
    public String getNewID() {
        LOG.info("Retrieving the WorkTask Count");
        LOG.debug("GET request");

        if (newID == 0) {
            newID = WorkTaskDao.instance.getModel().size();
        } else {
            newID += 1;
        }

        return String.valueOf(newID);
    }

    /**
     * Retrieves representation of an instance of
     * com.patrickgaines.crud.gradebook.jaxb.model.WorkTaskResource
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getResource(@PathParam("id") String id) {

        WorkTask workTask = WorkTaskDao.instance.getModel().get(id);
        LOG.info("Retrieving the WorkTask Resource {}", workTask);
        LOG.debug("GET request");
        LOG.debug("PathParam id = {}", id);

        Response response;

        if (workTask == null) {
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No WorkTask Resource to return");

            response = Response.status(Response.Status.GONE).entity("No WorkTask Resource to return").build();
        } else {
            LOG.debug("workTask.getWorkTaskId() = {}", workTask.getWorkTaskId());
            if (workTask.getWorkTaskId().equals(id)) {
                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                LOG.debug("Retrieving the WorkTask Resource {}", workTask);

                String xmlString = Converter.convertFromObjectToXml(workTask, WorkTask.class);

                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.debug("Retrieving the WorkTask Resource {}", workTask);

                response = Response.status(Response.Status.NOT_FOUND).entity("No WorkTask Resource to return").build();
            }
        }

        LOG.debug("Returning the value {}", response);

        return response;
    }

    /**
     * PUT method for updating an instance of ScratchResource
     *
     * @param id
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateResource(@PathParam("id") String id, String content) {
        WorkTask workTask = WorkTaskDao.instance.getModel().get(id);
        LOG.info("Updating the WorkTask Resource {} with {}", workTask, content);
        LOG.debug("PUT request");
        LOG.debug("PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);

        Response response;

        if (workTask != null) {
            LOG.debug("Attempting to update the WorkTask Resource {}", workTask);

            try {
                workTask = (WorkTask) Converter.convertFromXmlToObject(content, WorkTask.class);
                WorkTaskDao.instance.getModel().put(id, workTask);

                LOG.debug("The XML {} was converted to the object {}", content, workTask);
                LOG.debug("Updated WorkTask Resource to {}", workTask);

                LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());

                String xmlString = Converter.convertFromObjectToXml(workTask, WorkTask.class);

                response = Response.status(Response.Status.OK).entity(content).build();
            } catch (JAXBException e) {
                LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                LOG.debug("XML is {} is incompatible with WorkTask Resource", content);

                response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
            } catch (RuntimeException e) {
                LOG.debug("Catch All exception");

                LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

                response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
            }
        } else {
            LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
            LOG.debug("Cannot update WorkTask Resource {} as it has not yet been created", workTask);

            response = Response.status(Response.Status.CONFLICT).entity(content).build();
        }

        LOG.debug("Generated response {}", response);

        return response;
    }

    /**
     * Retrieves representation of an instance of
     * com.patrickgaines.crud.gradebook.jaxb.model.WorkTaskResource
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteResource(@PathParam("id") String id) {
        WorkTask workTask = WorkTaskDao.instance.getModel().get(id);
        LOG.info("Removing the WorkTask Resource {}", workTask);
        LOG.debug("DELETE request");
        LOG.debug("PathParam id = {}", id);

        Response response;

        if (workTask == null) {
            LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
            LOG.debug("No WorkTask Resource to delete");

            response = Response.status(Response.Status.GONE).build();
        } else if (workTask.getWorkTaskId().equals(id)) {
            LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
            LOG.debug("Deleting the WorkTask Resource {}", workTask);

            workTask = null;
            WorkTaskDao.instance.getModel().remove(id);

            response = Response.status(Response.Status.NO_CONTENT).build();
        } else {
            LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
            LOG.debug("Retrieving the WorkTask Resource {}", workTask);

            response = Response.status(Response.Status.NOT_FOUND).build();
        }

        LOG.debug("Generated response {}", response);

        return response;
    }
}
