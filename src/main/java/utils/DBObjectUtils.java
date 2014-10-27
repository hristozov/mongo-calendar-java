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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class DBObjectUtils {
    private static String dateToTZString(Date date) {
        System.out.println("1 " + date + " -> " + getFormat().format(date).toString());
        return getFormat().format(date).toString();
    }

    private static Date TZStringToDate(String string) {
        Calendar cal = getCalendar();
        DateFormat format = getFormat();
        format.setCalendar(cal);
        try {
            cal.setTime(format.parse(string));
        } catch (ParseException e) {
            return null;
        }
        System.out.println("1 " + string + " -> " + cal.getTime());
        return cal.getTime();
    }

    private static Calendar getCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("EEST"));
    }

    private static DateFormat getFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        simpleDateFormat.setCalendar(getCalendar());
        return simpleDateFormat;
    }

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
            if (id instanceof ObjectId) {
                result.put("id", id.toString());
                result.remove("_id");
            }
            Object date = result.get("date");
            if (date instanceof Date) {
                result.put("date", dateToTZString((Date) date));
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
            Map map = jsonParser.readValueAs(Map.class);
            DBObject result = new BasicDBObject(map);
            Object id = result.get("id");
            if (id != null && id instanceof String) {
                result.put("_id", new ObjectId((String) id));
                result.removeField("id");
            }
            Object date = result.get("date");
            if (date instanceof String) {
                result.put("date", TZStringToDate((String) date));
            }
            return result;
        }
    }
}
