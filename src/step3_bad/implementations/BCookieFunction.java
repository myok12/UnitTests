package step3_bad.implementations;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ws.rs.container.ContainerRequestContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import step3_bad.base.Function;

class BCookieFunction implements Function {
    @Override
    public JsonNode calculate(List<JsonNode> list, Map<String, JsonNode> map, ContainerRequestContext requestContext) {
        final ContainerRequestContext request = requireNonNull(requestContext, "request was requested (no pun intended), but none was provided");
        return Optional.ofNullable(request.getCookies().get("b"))
                .map(bCookie -> (JsonNode) TextNode.valueOf(bCookie.getValue()))
                .orElse(NullNode.getInstance());
    }
}
