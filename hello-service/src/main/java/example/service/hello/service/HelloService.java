package example.service.hello.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Service for getting message formats.
 */
@Component
public class HelloService {

    private final Map<Locale, String> messages = new HashMap<>();

    public HelloService() {
        messages.put(Locale.US, "Hello, %s!");
        messages.put(Locale.FRANCE, "Bonjour, %s!");
        messages.put(Locale.GERMANY, "Hallo, %s!");
        messages.put(new Locale("es","MX"), "Hola, %s!");
    }

    /**
     * Gets a message format based on locale.
     *
     * @param locale locale for message format
     * @return message format
     */
    public Mono<String> findMessageFormat(Locale locale) {
        return isSupportedLocale(locale)
                .map(isSupported -> {
                    if (isSupported) {
                        return messages.get(locale);
                    } else {
                        throw new RuntimeException(String.format("Unsupported locale [locale: '%s']", locale.toString()));
                    }
                });
    }

    /**
     * Checks to see if the supplied {@link Locale} is supported by this service.
     *
     * @param locale locale to check
     * @return <code>true</code> if the locale is supported; otherwise <code>false</code>
     */
    public Mono<Boolean> isSupportedLocale(Locale locale) {
        return Mono.just(messages.containsKey(locale));
    }

    /**
     * Returns a stream of the supported locales and their message formats.
     *
     * @return a {@link Flux} of supported locales and message formats
     */
    public Flux<Map.Entry<Locale, String>> getSupportedMessageFormats() {
        return Flux.from(s -> messages.entrySet().forEach(s::onNext));
    }
}
