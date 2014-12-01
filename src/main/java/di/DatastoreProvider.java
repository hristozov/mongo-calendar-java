package di;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.net.UnknownHostException;

public class DatastoreProvider implements Provider<Datastore> {
    private static final String SERVER = "localhost";
    private static final String DATABASE_NAME = "calendar";
    private final Morphia morphia;
    private Datastore instance;

    @Inject
    public DatastoreProvider(Morphia morphia) {
        this.morphia = morphia;
        initializeInstanceIfNecessary();
    }

    private void initializeInstanceIfNecessary() {
        if (instance != null) {
            return;
        }
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(SERVER);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        instance = morphia.createDatastore(mongoClient, DATABASE_NAME);
    }

    @Override
    public Datastore get() {
        return instance;
    }
}
