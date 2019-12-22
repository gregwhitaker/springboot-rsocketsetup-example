package example.client.hello.model;

import java.util.List;

/**
 * Request object sent to the hello message.
 */
public class HelloRequest {

    private List<String> names;

    public HelloRequest() {
        // Noop
    }

    public HelloRequest(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
