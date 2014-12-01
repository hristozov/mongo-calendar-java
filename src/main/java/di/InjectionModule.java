package di;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import utils.JsonMessageBodyReader;
import utils.JsonMessageBodyWriter;

public class InjectionModule extends ServletModule {
    @Override
    protected void configureServlets() {
        this.bind(JsonMessageBodyWriter.class);
        this.bind(JsonMessageBodyReader.class);
        this.bind(Morphia.class).toProvider(MorphiaProvider.class);
        this.bind(Datastore.class).toProvider(DatastoreProvider.class);
    }
}
