package step3_good.implementations;

import static step3_good.base.InvocationBuilder.ofList;
import static step3_good.base.InvocationBuilder.ofMap;
import static step3_good.base.InvocationBuilder.ofNoInputs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import step3_good.base.FunctionAssert;

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
        function.whenInvokedWith(ofMap(ImmutableMap.of()).build()).isThrowing();
    }

    @Test
    void failsOnNoInputs() {
        function.whenInvokedWith(ofNoInputs().build()).isThrowing();
    }

    @Test
    void worksWithNoValues() {
        function.whenInvokedWith(ofList(ImmutableList.of()).build()).returns(NULL);
    }

    @Test
    void providesFirstValue() {
        function.whenInvokedWith(ofList(ImmutableList.of(ABC, DEF)).build()).returns(ABC);
    }

    @Test
    void skipsAnEmptyValue() {
        function.whenInvokedWith(ofList(ImmutableList.of(EMPTY, DEF)).build()).returns(DEF);
    }

    @Test
    void skipsAllEmptyValues() {
        function.whenInvokedWith(ofList(ImmutableList.of(EMPTY, EMPTY)).build()).returns(NULL);
    }

    @Test
    void skipsNullValues() {
        function.whenInvokedWith(ofList(ImmutableList.of(NULL, ABC)).build()).returns(ABC);
    }

    @Test
    void skipsMissingValues() {
        function.whenInvokedWith(ofList(ImmutableList.of(MISSING, ABC)).build()).returns(ABC);
    }

    @Test
    void returnsNumber() {
        function.whenInvokedWith(ofList(ImmutableList.of(NULL, NUM_4)).build()).returns(NUM_4);
    }
}
