package step3_good.implementations;

import static step3_good.base.FunctionAssert.assertThat;

import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import step3_good.base.FunctionAssert;
import step3_good.base.InvocationBuilder;

class BCookieRequestFunctionTest {
    private static final String TEXT = "00000000-FEED-DADA-ICED-C0FFEE000000";
    private static final TextNode TEXT_NODE = TextNode.valueOf(TEXT);
    private static final NullNode NULL = NullNode.getInstance();
    private FunctionAssert functionAssert = assertThat(new BCookieFunction());

    @Test
    void explodesIfInvokedWithoutRequest() {
        functionAssert.whenInvokedWith(InvocationBuilder.ofNoInputs().build()).isThrowing();
    }

    @Test
    void extractsHeaderCorrectly() {
        functionAssert.whenInvokedWith(
                InvocationBuilder.ofNoInputs().withRequest().putCookie("b", TEXT).build()
        ).returns(TEXT_NODE);
    }

    @Test
    void doesNotExplodeWithoutCookies() {
        functionAssert.whenInvokedWith(InvocationBuilder.ofNoInputs().withRequest().build()).returns(NULL);
    }

    @Test
    void doesNotExplodeWithoutABCookie() {
        functionAssert.whenInvokedWith(
                InvocationBuilder.ofNoInputs().withRequest().putCookie("_ga", TEXT).build()
        ).returns(NULL);
    }

//         Showing off:
//
//        assertThat(new BCookieFunction())
//                .whenInvokedWith(
//                        InvocationBuilder.ofNoInputs().withRequest()
//                                .addQueryParam("q", "1234")
//                                .putHeader("Auth", "5678")
//                                .addAcceptLanguage(Locale.US)
//                                .putCookie("b", "90ab")
//                                .putCookie("_ga", "cdef")
//                                .build())
//                .returns(TEXT_NODE);
}
