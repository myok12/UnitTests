package step3_good.implementations;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import step3_good.base.Function;
import step3_good.base.Invocation;

public class FirstNonEmptyFunction implements Function {
    @Override
    public JsonNode calculate(Invocation invocation) {
        List<JsonNode> list = invocation.getList();
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
