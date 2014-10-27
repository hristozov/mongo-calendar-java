import com.google.inject.Guice;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import di.InjectionModule;
import org.glassfish.grizzly.http.server.HttpServer;
import utils.CORSFilter;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Server {
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(8080).build();
    }

    public static void main(String[] args) {
        PackagesResourceConfig rc = new PackagesResourceConfig("service");
        rc.getContainerResponseFilters().add(new CORSFilter());
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc,
                Guice.createInjector(new InjectionModule()));
        HttpServer server = null;
        try {
            server = GrizzlyServerFactory.createHttpServer(getBaseURI(), rc, ioc);
            server.start();
            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.stop();
            }
        }
    }
}