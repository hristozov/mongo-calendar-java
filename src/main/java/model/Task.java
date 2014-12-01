package model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

@Entity("tasks")
public class Task {
    @Id
    public String id;

    public Date date;

    public String title;

    public String text;

    public int priority;
}
