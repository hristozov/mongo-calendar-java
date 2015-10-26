package di;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class TasksCollectionProvider implements Provider<MongoCollection<Document>> {
    private static final String COLLECTION_NAME = "tasks";
    private final MongoDatabase database;

    @Inject
    public TasksCollectionProvider(MongoDatabase database) {
        this.database = database;
    }

    @Override
    public MongoCollection<Document> get() {
        return database.getCollection(COLLECTION_NAME);
    }
}
