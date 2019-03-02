package step2_good.base;

import com.fasterxml.jackson.databind.JsonNode;

public interface Function {
    JsonNode calculate(Invocation invocation);
}
