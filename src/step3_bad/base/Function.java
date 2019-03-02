package step3_bad.base;

import java.util.List;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.istack.internal.Nullable;

public interface Function {
    JsonNode calculate(@Nullable final List<JsonNode> list, @Nullable final Map<String, JsonNode> map, @Nullable final ContainerRequestContext requestContext);
}
