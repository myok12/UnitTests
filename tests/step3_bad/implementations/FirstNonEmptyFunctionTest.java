package step3_bad.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

class FirstNonEmptyFunctionTest {
    @Test
    void failsOnMap() {
        assertThatThrownBy(() -> new FirstNonEmptyFunction().calculate(null, ImmutableMap.of(), null));
    }

    @Test
    void failsOnNoInputs() {
        assertThatThrownBy(() -> new FirstNonEmptyFunction().calculate(null, null, null));
    }

    @Test
    void worksWithNoValues() {
        assertThat(
                new FirstNonEmptyFunction().calculate(Collections.emptyList(), null, null)
        ).isEqualTo(NullNode.getInstance());
    }

    @Test
    void providesFirstValue() {
        List<JsonNode> list = new ArrayList<>();
        list.add(TextNode.valueOf("abc"));
        list.add(TextNode.valueOf("def"));
        assertThat(new FirstNonEmptyFunction().calculate(list, null, null)).isEqualTo(TextNode.valueOf("abc"));
    }


    @Test
    void skipsAnEmptyValue() {
        List<JsonNode> list = new ArrayList<>();
        list.add(TextNode.valueOf(""));
        list.add(TextNode.valueOf("def"));
        assertThat(new FirstNonEmptyFunction().calculate(list, null, null)).isEqualTo(TextNode.valueOf("def"));
    }

    @Test
    void skipsAllEmptyValues() {
        List<JsonNode> list = new ArrayList<>();
        list.add(TextNode.valueOf(""));
        list.add(TextNode.valueOf(""));
        assertThat(new FirstNonEmptyFunction().calculate(list, null, null)).isEqualTo(NullNode.getInstance());
    }

    @Test
    void skipsNullValues() {
        List<JsonNode> list = new ArrayList<>();
        list.add(NullNode.getInstance());
        list.add(TextNode.valueOf("abc"));
        assertThat(new FirstNonEmptyFunction().calculate(list, null, null)).isEqualTo(TextNode.valueOf("abc"));
    }

    @Test
    void skipsMissingValues() {
        List<JsonNode> list = new ArrayList<>();
        list.add(MissingNode.getInstance());
        list.add(TextNode.valueOf("abc"));
        assertThat(new FirstNonEmptyFunction().calculate(list, null, null)).isEqualTo(TextNode.valueOf("abc"));
    }

    @Test
    void returnsNumber() {
        List<JsonNode> list = new ArrayList<>();
        list.add(NullNode.getInstance());
        list.add(IntNode.valueOf(4));
        assertThat(new FirstNonEmptyFunction().calculate(list, null, null)).isEqualTo(IntNode.valueOf(4));
    }
}
