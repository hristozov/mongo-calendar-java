package di;

import com.google.inject.name.Names;
import com.google.inject.servlet.ServletModule;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import utils.JsonMessageBodyReader;
import utils.JsonMessageBodyWriter;

public class InjectionModule extends ServletModule {
    @Override
    protected void configureServlets() {
        this.bind(JsonMessageBodyWriter.class);
        this.bind(JsonMessageBodyReader.class);
        this.bind(DB.class)
                .toProvider(new DatabaseProvider());
        this.bind(DBCollection.class)
                .annotatedWith(Names.named("tasks"))
                .toProvider(TasksCollectionProvider.class);
    }
}
