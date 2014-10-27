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
        return tasks.find().toArray();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject getTask(String id) {
        return tasks.findOne(new BasicDBObject("_id", new ObjectId(id)));
    }

    @GET
    @Path("/for_date/{year}-{month}-{day}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DBObject> getTasksByData(@PathParam("year") Integer year,
                                         @PathParam("month") Integer month,
                                         @PathParam("day") Integer day) {
        DBObject dateRange = new BasicDBObject();
        dateRange.put("$lt", new Date(year-1900, month-1, day+20, 0, 0));
        dateRange.put("$gt", new Date(year-1900, month-1, day, 0, 0));
        DBObject query = new BasicDBObject("date", dateRange);

        System.out.println(query + " " + tasks.count(query));

        return tasks.find(query).toArray();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DBObject createTask(DBObject task) {
        ObjectId taskId = new ObjectId();
        task.put("_id", taskId);

        tasks.insert(task);
        return tasks.findOne(new BasicDBObject("_id", taskId));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DBObject saveTask(DBObject task) {
        ObjectId taskId = (ObjectId) task.get("_id");
        tasks.update(new BasicDBObject("_id", taskId), task);
        return tasks.findOne(new BasicDBObject("_id", taskId));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject deleteTask(@PathParam("id") String id) {
        return tasks.findAndRemove(new BasicDBObject("_id", new ObjectId(id)));
    }
}
