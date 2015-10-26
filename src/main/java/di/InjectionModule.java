package di;

import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.research.ws.wadl.Doc;
import org.bson.Document;
import utils.JsonMessageBodyReader;
import utils.JsonMessageBodyWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InjectionModule extends ServletModule {
    @Override
    protected void configureServlets() {
        this.bind(JsonMessageBodyWriter.class);
        this.bind(JsonMessageBodyReader.class);
        this.bind(MongoDatabase.class)
                .toProvider(new DatabaseProvider());
        this.bind(new TypeLiteral<MongoCollection<Document>>() {})
                .annotatedWith(Names.named("tasks"))
                .toProvider(TasksCollectionProvider.class);
    }
}
