package example.service.hello.controller;

import example.service.hello.controller.model.HelloRequest;
import example.service.hello.controller.model.Setup;
import example.service.hello.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * Controller responsible for creating hello messages.
 */
@Controller
public class HelloController {
    private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private HelloService helloService;

    private Locale locale;  // value is assigned during SETUP frame

    @ConnectMapping
    public Mono<Void> setup(Setup setup) {
        return helloService.isSupportedLocale(new Locale(setup.getLanguage(), setup.getCountry()))
                .map(isSupported -> {
                    if (isSupported) {
                        LOG.info("Configuring service for locale: {}", locale.toString());
                        this.locale = new Locale(setup.getLanguage(), setup.getCountry());
                        return Mono.empty();
                    } else {
                        LOG.error("Unsupported locale [local: '{}']", locale.toString());
                        return Mono.error(new RuntimeException(String.format("Unsupported locale [locale: '%s']", locale.toString())));
                    }
                })
                .then();
    }

    @MessageMapping("hello")
    public Flux<String> hello(HelloRequest request) {
        return Flux.fromIterable(request.getNames())
                .zipWith(helloService.findMessageFormat(locale))
                .map(objects -> String.format(objects.getT2(), objects.getT1()));
    }
}
