package di;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class TasksCollectionProvider implements Provider<DBCollection> {
    private static final String COLLECTION_NAME = "tasks";
    private final DB database;

    @Inject
    public TasksCollectionProvider(DB database) {
        this.database = database;
    }

    @Override
    public DBCollection get() {
        return database.getCollection(COLLECTION_NAME);
    }
}
