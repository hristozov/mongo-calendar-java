package utils;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
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
import java.util.*;

public class DocumentUtils {
    private static String dateToTZString(Date date) {
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
        SimpleModule documentModule = new SimpleModule("DocumentModule", new Version(1, 0, 0, null));
        documentModule.addDeserializer(Document.class, new DocumentDeserializer());
        documentModule.addSerializer(Document.class, new DocumentSerializer());
        objectMapper.registerModule(documentModule);
        return objectMapper;
    }

    private static class DocumentSerializer extends SerializerBase<Document> {
        protected DocumentSerializer() {
            super(Document.class);
        }

        @Override
        public void serialize(Document document,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            Map result = new HashMap(document);
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

    private static class DocumentDeserializer extends StdDeserializer<Document> {
        protected DocumentDeserializer() {
            super(Document.class);
        }

        @Override
        public Document deserialize(JsonParser jsonParser,
                                    DeserializationContext deserializationContext) throws IOException {
            Map map = jsonParser.readValueAs(Map.class);
            Document result = new Document(map);
            Object id = result.get("id");
            if (id != null && id instanceof String) {
                result.put("_id", new ObjectId((String) id));
                result.remove("id");
            }
            Object date = result.get("date");
            if (date instanceof String) {
                result.put("date", TZStringToDate((String) date));
            }
            return result;
        }
    }
}
