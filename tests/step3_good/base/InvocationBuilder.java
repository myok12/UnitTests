package step3_good.base;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.JsonNode;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.mockito.Mockito;

public class InvocationBuilder {
    private final List<JsonNode> list;
    private final Map<String, JsonNode> map;
    private ContainerRequestContext mockRequest;
    private MultivaluedStringMap headers;
    private MultivaluedStringMap queryParameters;
    private MultivaluedStringMap pathParameters;
    private UriInfo mockUriInfo;
    private List<Locale> acceptableLanguages;
    private Map<String, Cookie> cookies;

    private InvocationBuilder(final List<JsonNode> list, final Map<String, JsonNode> map) {
        this.list = list;
        this.map = map;
    }

    public static InvocationBuilder ofList(final List<JsonNode> inputList) {
        return new InvocationBuilder(inputList, null);
    }

    public static InvocationBuilder ofMap(final Map<String, JsonNode> map) {
        return new InvocationBuilder(null, map);
    }

    public static InvocationBuilder ofNoInputs() {
        return new InvocationBuilder(null, null);
    }

    public InvocationBuilder withRequest() {
        // The following ALWAYS exist when a real request is made.
        mockRequest = Mockito.mock(ContainerRequestContext.class);
        mockUriInfo = Mockito.mock(UriInfo.class);
        headers = new MultivaluedStringMap();
        return this;
    }

    public InvocationBuilder putHeader(String headerName, String headerValue) {
        assert mockRequest != null : "Cannot add header without request";
        headers.add(headerName, headerValue);
        return this;
    }

    public InvocationBuilder addQueryParam(String paramName, String paramValue) {
        assert mockRequest != null : "Cannot add query param without request";
        if (queryParameters == null) {
            queryParameters = new MultivaluedStringMap();
        }
        queryParameters.add(paramName, paramValue);
        return this;
    }

    public InvocationBuilder addPathParam(String paramName, String paramValue) {
        assert mockRequest != null : "Cannot add query param without request";
        if (pathParameters == null) {
            pathParameters = new MultivaluedStringMap();
        }
        pathParameters.add(paramName, paramValue);
        return this;
    }

    public InvocationBuilder addAcceptLanguage(Locale locale) {
        assert mockRequest != null : "Cannot add acceptable language without request";
        if (acceptableLanguages == null) {
            acceptableLanguages = new ArrayList<>();
        }
        acceptableLanguages.add(locale);
        return this;
    }

    public InvocationBuilder putCookie(final String cookieName, final String cookieValue) {
        assert mockRequest != null : "cannot add cookie without request";
        if (cookies == null) {
            cookies = new HashMap<>();
        }
        cookies.put(cookieName, new Cookie(cookieName, cookieValue));

        return this;
    }

    public Invocation build() {
        if (headers != null) {
            when(mockRequest.getHeaders()).thenReturn(headers);
        }
        if (pathParameters != null) {
            when(mockUriInfo.getPathParameters()).thenReturn(pathParameters);
        }
        if (queryParameters != null) {
            when(mockUriInfo.getQueryParameters()).thenReturn(queryParameters);
        }
        if (cookies != null) {
            when(mockRequest.getCookies()).thenReturn(cookies);
        }
        if (mockUriInfo != null) {
            when(mockRequest.getUriInfo()).thenReturn(mockUriInfo);
        }
        if (acceptableLanguages != null) {
            when(mockRequest.getAcceptableLanguages()).thenReturn(acceptableLanguages);
        }
        return new Invocation(list, map, mockRequest);
    }
}
