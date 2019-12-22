package example.service.hello.controller.model;

import java.util.List;

public class HelloRequest {

    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
