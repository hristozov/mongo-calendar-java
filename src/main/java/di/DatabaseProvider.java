package di;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.net.UnknownHostException;

public class DatabaseProvider implements Provider<MongoDatabase> {
    private static final String DATABASE_NAME = "calendar";
    private static final String server = "localhost";
    private static MongoDatabase instance;

    public DatabaseProvider() {
        initializeInstanceIfNecessary();
    }

    private void initializeInstanceIfNecessary() {
        if (instance != null) {
            return;
        }
        MongoClient mongoClient;
        mongoClient = new MongoClient(server);
        instance = mongoClient.getDatabase(DATABASE_NAME);
    }

    @Override
    public MongoDatabase get() {
        return instance;
    }
}
