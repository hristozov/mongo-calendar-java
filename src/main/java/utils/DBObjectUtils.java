package utils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.std.SerializerBase;

import java.io.IOException;
import java.util.Map;

public class DBObjectUtils {
    public static ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule dbObjectModule = new SimpleModule("DBObjectModule", new Version(1, 0, 0, null));
        dbObjectModule.addDeserializer(DBObject.class, new DBObjectDeserializer());
        dbObjectModule.addSerializer(DBObject.class, new DBObjectSerializer());
        objectMapper.registerModule(dbObjectModule);
        return objectMapper;
    }

    private static class DBObjectSerializer extends SerializerBase<DBObject> {
        protected DBObjectSerializer() {
            super(DBObject.class);
        }

        @Override
        public void serialize(DBObject dbObject,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            Map result = dbObject.toMap();
            Object id = result.get("_id");
            if (id != null && id instanceof ObjectId) {
                result.put("_id", id.toString());
            }
            jsonGenerator.writeObject(result);
        }
    }

    private static class DBObjectDeserializer extends StdDeserializer<DBObject> {
        protected DBObjectDeserializer() {
            super(DBObject.class);
        }

        @Override
        public DBObject deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            DBObject result = jsonParser.readValueAs(DBObject.class);
            Object id = result.get("_id");
            if (id != null && id instanceof String) {
                result.put("_id", new ObjectId((String) id));
            }
            return result;
        }
    }
}
