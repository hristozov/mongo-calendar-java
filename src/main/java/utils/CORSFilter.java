package utils;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.core.MultivaluedMap;

public class CORSFilter implements ContainerResponseFilter {
    @Override
    public ContainerResponse filter(ContainerRequest request, ContainerResponse response) {
        MultivaluedMap<String, Object> httpHeaders = response.getHttpHeaders();
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE");
        httpHeaders.add("Access-Control-Allow-Headers", "Accept, Origin, Content-type");
        return response;
    }
}
