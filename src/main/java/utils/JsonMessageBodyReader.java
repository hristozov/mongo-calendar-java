package utils;

import com.google.inject.Singleton;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Singleton
@Consumes("application/json")
public class JsonMessageBodyReader implements MessageBodyReader {
    private ObjectMapper objectMapper = DBObjectUtils.getMapper();

    @Override
    public boolean isReadable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(Class type, Type genericType, Annotation[] annotations, MediaType mediaType,
                           MultivaluedMap httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        return objectMapper.readValue(entityStream, type);
    }
}
