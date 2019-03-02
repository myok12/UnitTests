package step1_good.implementations;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

class FirstNonEmptyFunctionTest {
    private static final JsonNode ABC = TextNode.valueOf("abc");
    private static final JsonNode EMPTY = TextNode.valueOf("");
    private static final JsonNode DEF = TextNode.valueOf("def");
    private static final JsonNode NUM_4 = IntNode.valueOf(4);
    private static final JsonNode NULL = NullNode.getInstance();
    private static final JsonNode MISSING = MissingNode.getInstance();

    private FirstNonEmptyFunction fn = new FirstNonEmptyFunction();

    @Test
    void worksWithNoValues() {
        assertThat(fn.calculate(ImmutableList.of())).isEqualTo(NULL);
    }

    @Test
    void providesFirstValue() {
        assertThat(fn.calculate(ImmutableList.of(ABC, DEF))).isEqualTo(ABC);
    }

    @Test
    void skipsAnEmptyValue() {
        assertThat(fn.calculate(ImmutableList.of(EMPTY, DEF))).isEqualTo(DEF);
    }

    @Test
    void skipsAllEmptyValues() {
        assertThat(fn.calculate(ImmutableList.of(EMPTY, EMPTY))).isEqualTo(NULL);
    }

    @Test
    void skipsNullValues() {
        assertThat(fn.calculate(ImmutableList.of(NULL, ABC))).isEqualTo(ABC);
    }

    @Test
    void skipsMissingValues() {
        assertThat(fn.calculate(ImmutableList.of(MISSING, ABC))).isEqualTo(ABC);
    }

    @Test
    void returnsNumber() {
        assertThat(fn.calculate(ImmutableList.of(NULL, NUM_4))).isEqualTo(NUM_4);
    }
}
