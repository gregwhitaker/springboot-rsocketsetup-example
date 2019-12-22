package example.service.hello.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class HelloService {
    private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);

    private final Map<Locale, String> messages = new HashMap<>();

    public HelloService() {
        messages.put(Locale.US, "Hello, %s!");
        messages.put(Locale.FRANCE, "Bonjour, %s!");
        messages.put(Locale.GERMANY, "Hallo, %s!");
        messages.put(new Locale("es","MX"), "Hola, %s!");
    }

    public Mono<String> findMessage(Locale locale) {
        return isSupportedLocale(locale)
                .map(isSupported -> {
                    if (isSupported) {
                        return messages.get(locale);
                    } else {
                        throw new RuntimeException("Unsupported locale");
                    }
                });
    }

    public Mono<Boolean> isSupportedLocale(Locale locale) {
        return Mono.just(messages.containsKey(locale));
    }

    public Flux<Map.Entry<Locale, String>> getSupportedLocales() {
        return Flux.from(s -> messages.entrySet().forEach(s::onNext));
    }
}
