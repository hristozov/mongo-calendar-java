package di;

import com.google.inject.Provider;
import model.Task;
import org.mongodb.morphia.Morphia;

public class MorphiaProvider implements Provider<Morphia> {
    @Override
    public Morphia get() {
        Morphia morphia = new Morphia();
        morphia.map(Task.class);
        return morphia;
    }
}
