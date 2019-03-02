package step3_bad.implementations;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import step3_bad.base.Function;

public class FirstNonEmptyFunction implements Function {
    @Override
    public JsonNode calculate(final List<JsonNode> list, final Map<String, JsonNode> map, final ContainerRequestContext request) {
        assert list != null : "Should have received a list as inputs";
        assert map == null : "Should have only received a list as inputs, but received map inputs";
        assert request == null : "Should have not received a request context";

        for (int i = 0; i < list.size(); i++) {
            final JsonNode input = list.get(i);
            if (!(input.isNull() || input.isMissingNode())) {
                if (input.isTextual()) {
                    if (!isNullOrEmpty(input.textValue())) {
                        return input;
                    }
                } else if (input.isNumber() || input.isBoolean()) {
                    // No way for these types to not contain a valid input.
                    return input;
                } else {
                    throw new RuntimeException("Unexpected type " + input.getNodeType() + " in input #" + (i + 1));
                }
            }
        }
        return NullNode.getInstance();
    }
}
