package service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/tasks")
public class TasksService {
    @Inject
    @Named("tasks")
    public DBCollection tasks;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> listTasks() {
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject getTask(String id) {
        return null;
    }

    @GET
    @Path("/for_date/{year}-{month}-{day}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> getTasksByData(@PathParam("year") Integer year,
                                         @PathParam("month") Integer month,
                                         @PathParam("day") Integer day) {
        return null;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DBObject createTask(DBObject task) {
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DBObject saveTask(DBObject task) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject deleteTask(@PathParam("id") String id) {
        return null;
    }
}
