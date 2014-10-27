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
        final Calendar startDate = Calendar.getInstance();
        startDate.set(year, month, day, 0, 0);
        final Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DATE, 1);

        DBObject query = new BasicDBObject("date", new BasicDBObject() {{
            put("$lt", endDate.getTime());
            put("$gt", startDate.getTime());
        }});
        return tasks.find(query).toArray();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DBObject createTask(Map<String, Object> args) {
        ObjectId taskId = new ObjectId();

        DBObject task = new BasicDBObject(args);
        task.put("_id", taskId);

        tasks.insert(task);
        return tasks.findOne(new BasicDBObject("_id", taskId));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public DBObject saveTask(Map<String, Object> args) {
        ObjectId taskId = new ObjectId((String) args.get("_id"));

        DBObject task = new BasicDBObject(args);
        task.put("_id", taskId);

        tasks.update(new BasicDBObject("_id", taskId), task);
        return tasks.findOne(new BasicDBObject("_id", taskId));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject deleteTask(String id) {
        return tasks.findAndRemove(new BasicDBObject("_id", new ObjectId(id)));
    }
}
