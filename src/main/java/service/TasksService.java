package service;

import com.google.inject.Inject;
import model.Task;
import org.mongodb.morphia.Datastore;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

@Path("/tasks")
public class TasksService {
    @Inject
    public Datastore datastore;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> listTasks() {
        return datastore.find(Task.class).asList();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTask(String id) {
        return datastore.get(Task.class, id);
    }

    @GET
    @Path("/for_date/{year}-{month}-{day}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getTasksByData(@PathParam("year") Integer year,
                               @PathParam("month") Integer month,
                               @PathParam("day") Integer day) {
        return datastore.createQuery(Task.class)
                .filter("date >= ", new Date(year - 1900, month - 1, day, 0, 0))
                .filter("date <", new Date(year - 1900, month - 1, day + 1, 0, 0))
                .get();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Task createTask(Task task) {
        datastore.save(task);
        return task;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Task saveTask(Task task) {
        datastore.save(task);
        return task;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task deleteTask(@PathParam("id") String id) {
        Task deleted = datastore.get(Task.class, id);
        datastore.delete(Task.class, id);
        return deleted;
    }
}
