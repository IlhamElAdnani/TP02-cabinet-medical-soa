package ma.fsr.soa.cabesb.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ApiRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        // Exemple REST pour patients
        rest("/api/patients")
            .get("/{id}")
            .to("direct:getPatientById");

        from("direct:getPatientById")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .toD("http://localhost:8082/internal/api/v1/patients/${header.id}")
            .log("${body}");
    }
}
 