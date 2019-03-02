package step3_good.base;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;

import com.fasterxml.jackson.databind.JsonNode;

public class Invocation {
    private final List<JsonNode> list;
    private final Map<String, JsonNode> map;
    private final ContainerRequestContext request;

    Invocation(final List<JsonNode> list, final Map<String, JsonNode> map, final ContainerRequestContext request) {
        this.list = list;
        this.map = map;
        this.request = request;
    }

    public List<JsonNode> getList() {
        assert map == null : "Should have not been provided with map inputs for a list input-based function";
        return requireNonNull(list, "list inputs requested, but none were provided");
    }

    public Map<String, JsonNode> getMap() {
        assert list == null : "Should have not been provided with map inputs for a list input-based function";
        return requireNonNull(map, "named inputs requested, but none were provided");
    }

    public ContainerRequestContext getRequest() {
        return requireNonNull(request, "request was requested (no pun intended), but none was provided");
    }
}
