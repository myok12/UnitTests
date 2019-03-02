package step3_good.implementations;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import step3_good.base.Function;
import step3_good.base.Invocation;

class BCookieFunction implements Function {
    @Override
    public JsonNode calculate(Invocation invocation) {
        return Optional.ofNullable(invocation.getRequest().getCookies().get("b"))
                .map(bCookie -> (JsonNode) TextNode.valueOf(bCookie.getValue()))
                .orElse(NullNode.getInstance());
    }
}
