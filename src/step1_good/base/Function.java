package step1_good.base;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public interface Function {
    JsonNode calculate(List<JsonNode> list);
}
