package di;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class DatabaseProvider implements Provider<DB> {
    private static final String DATABASE_NAME = "calendar";
    private static final String server = "localhost";
    private static DB instance;

    public DatabaseProvider() {
        initializeInstanceIfNecessary();
    }

    private void initializeInstanceIfNecessary() {
        if (instance != null) {
            return;
        }
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(server);
        } catch (UnknownHostException e) {
            // Oh shit. Give up.
            throw new RuntimeException(e);
        }
        instance = mongoClient.getDB(DATABASE_NAME);
    }

    @Override
    public DB get() {
        return instance;
    }
}
