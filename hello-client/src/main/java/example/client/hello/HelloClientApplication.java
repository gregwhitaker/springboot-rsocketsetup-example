package example.client.hello;

import example.client.hello.model.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.util.List;

import static picocli.CommandLine.Parameters;
import static picocli.CommandLine.populateCommand;

@SpringBootApplication
public class HelloClientApplication {
    private static final Logger LOG = LoggerFactory.getLogger(HelloClientApplication.class);

    public static void main(String... args) {
        SpringApplication.run(HelloClientApplication.class);
    }

    /**
     * Runs the application.
     */
    @Component
    public class Runner implements CommandLineRunner {

        @Value("${example.service.hello.hostname}")
        private String helloServiceHostname;

        @Value("${example.service.hello.port}")
        private int helloServicePort;

        @Override
        public void run(String... args) throws Exception {
            ClientArguments params = populateCommand(new ClientArguments(), args);

            RSocketRequester rSocketRequester = connectToHelloService(params);

            LOG.info("Requesting '{}' hello message(s)...", params.names.size());
        }

        private RSocketRequester connectToHelloService(ClientArguments params) {
            LOG.info("Connecting to hello-service and configuring locale [language: '{}', country: '{}']", params.language, params.country);

            return RSocketRequester.builder()
                    .setupRoute("hello.setup")
                    .setupData(new Setup(params.language, params.country))
                    .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                    .rsocketStrategies(builder -> {
                        builder.encoder(new Jackson2JsonEncoder());
                        builder.encoder(new Jackson2CborEncoder());
                    })
                    .connectTcp(helloServiceHostname, helloServicePort)
                    .block();
        }
    }

    /**
     * Hello client command line arguments.
     */
    public static class ClientArguments {

        /**
         * locale language of hello message
         */
        @Parameters(index = "0", arity = "1", description = "locale language")
        private String language;

        /**
         * local country of hello message
         */
        @Parameters(index = "1", arity = "1", description = "locale country")
        private String country;

        /**
         * "names" argument to send to the method
         */
        @Parameters(index = "2", arity = "1...", description = "names for which to stream hello messages")
        public List<String> names;
    }
}
