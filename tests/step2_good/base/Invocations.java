package step2_good.base;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class Invocations {
    public static Invocation ofNoInputs() {
        return new Invocation(null, null);
    }

    public static Invocation ofList(final List<JsonNode> list) {
        return new Invocation(list, null);
    }

    public static Invocation ofMap(final Map<String, JsonNode> map) {
        return new Invocation(null, map);
    }
}
