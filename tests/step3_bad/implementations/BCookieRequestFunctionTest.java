package step3_bad.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;

import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BCookieRequestFunctionTest {
    private ContainerRequestContext mockRequest = Mockito.mock(ContainerRequestContext.class);

    @Test
    void explodesIfInvokedWithoutRequest() {
        assertThatThrownBy(() ->
                new BCookieFunction().calculate(null, null, null)
        );
    }

    @Test
    void extractsHeaderCorrectly() {
        Map<String, Cookie> cookies = new HashMap<>();
        cookies.put("b", new Cookie("b", "somewhere over"));
        when(mockRequest.getCookies()).thenReturn(cookies);
        assertThat(
                new BCookieFunction().calculate(null, null, mockRequest)
        ).isEqualTo(TextNode.valueOf("somewhere over"));
    }

    @Test
    void doesNotExplodeWithoutCookies() {
        assertThat(
                new BCookieFunction().calculate(null, null, mockRequest)
        ).isEqualTo(NullNode.getInstance());
    }

    @Test
    void doesNotExplodeWithoutABCookie() {
        Map<String, Cookie> cookies = new HashMap<>();
        cookies.put("_ga", new Cookie("_ga", "the rainbow"));
        when(mockRequest.getCookies()).thenReturn(cookies);
        assertThat(
                new BCookieFunction().calculate(null, null, mockRequest)
        ).isEqualTo(NullNode.getInstance());
    }
}
