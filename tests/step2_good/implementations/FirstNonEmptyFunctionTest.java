package step2_good.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import step2_good.base.FunctionAssert;
import step2_good.base.Invocations;

class FirstNonEmptyFunctionTest {
    private static final JsonNode ABC = TextNode.valueOf("abc");
    private static final JsonNode EMPTY = TextNode.valueOf("");
    private static final JsonNode DEF = TextNode.valueOf("def");
    private static final JsonNode NUM_4 = IntNode.valueOf(4);
    private static final JsonNode NULL = NullNode.getInstance();
    private static final JsonNode MISSING = MissingNode.getInstance();

    private FunctionAssert function = FunctionAssert.assertThat(new FirstNonEmptyFunction());

    @Test
    void failsOnMap() {
        function.whenInvokedWith(Invocations.ofMap(ImmutableMap.of())).isThrowing();
    }

    @Test
    void failsOnNoInputs() {
        function.whenInvokedWith(Invocations.ofNoInputs()).isThrowing();
    }

    @Test
    void worksWithNoValues() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of())).returns(NULL);
    }

    @Test
    void providesFirstValue() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of(ABC, DEF))).returns(ABC);
    }

    @Test
    void skipsAnEmptyValue() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of(EMPTY, DEF))).returns(DEF);
    }

    @Test
    void skipsAllEmptyValues() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of(EMPTY, EMPTY))).returns(NULL);
    }

    @Test
    void skipsNullValues() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of(NULL, ABC))).returns(ABC);
    }

    @Test
    void skipsMissingValues() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of(MISSING, ABC))).returns(ABC);
    }

    @Test
    void returnsNumber() {
        function.whenInvokedWith(Invocations.ofList(ImmutableList.of(NULL, NUM_4))).returns(NUM_4);
    }
}
