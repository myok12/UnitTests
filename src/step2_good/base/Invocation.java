package step2_good.base;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class Invocation {
    private final List<JsonNode> list;
    private final Map<String, JsonNode> map;

    Invocation(final List<JsonNode> list, final Map<String, JsonNode> map) {
        this.list = list;
        this.map = map;
    }

    public List<JsonNode> getList() {
        assert map == null : "Should have not been provided with map inputs for a list input-based function";
        return requireNonNull(list, "list was requested, but none was provided");
    }

    public Map<String, JsonNode> getMap() {
        assert list == null : "Should have not been provided with map inputs for a list input-based function";
        return requireNonNull(map, "map was requested, but none was provided");
    }
}
