package service;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/tasks")
public class TasksService {
    @Inject
    @Named("tasks")
    public MongoCollection<Document> tasks;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> listTasks() {
        return tasks.find().into(new ArrayList<Document>());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Document getTask(String id) {
        return tasks.find(new Document("_id", new ObjectId(id))).first();
    }

    @GET
    @Path("/for_date/{year}-{month}-{day}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Document> getTasksByData(@PathParam("year") Integer year,
                                              @PathParam("month") Integer month,
                                              @PathParam("day") Integer day) {
        Document dateRange = new Document();
        dateRange.put("$lt", new Date(year-1900, month-1, day+1, 0, 0));
        dateRange.put("$gte", new Date(year-1900, month-1, day, 0, 0));
        Document query = new Document("date", dateRange);
        return tasks.find(query).into(new ArrayList<Document>());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Document createTask(Document task) {
        ObjectId taskId = new ObjectId();
        task.put("_id", taskId);

        tasks.insertOne(task);
        return tasks.find(new Document("_id", taskId)).first();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Document saveTask(Document task) {
        ObjectId taskId = (ObjectId) task.get("_id");
        tasks.replaceOne(new Document("_id", taskId), task);
        return tasks.find(new Document("_id", taskId)).first();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Document deleteTask(@PathParam("id") String id) {
        return tasks.findOneAndDelete(new Document("_id", new ObjectId(id)));
    }
}
