package org.example.regression.common.enums;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;
import java.util.HashMap;
import static java.lang.ThreadLocal.withInitial;

public enum TestContext {
    CONTEXT;
    public static final String APP_URL = "applicationTestUrl";
    public static final String ACCESSIBILITY_SPEC_KEY = "accessibilitySpec";
    public static final String PAYLOAD = "PAYLOAD";
    public static final String REQUEST = "REQUEST";
    public static final String RESPONSE = "RESPONSE";
    private final ThreadLocal<Map<String, Object>> testContexts = withInitial(HashMap::new);
    public <T> T get(String name) { return (T) testContexts.get().get(name); }

    public <T> T set(String name, T object) {
        testContexts.get().put(name, object);
        return object;
    }

    public RequestSpecification getRequest() {
        if (null == get(REQUEST)) {
            RequestSpecification request = new RequestSpecBuilder().build()
                    .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames().relaxedHTTPSValidation()));
            set(REQUEST, request.given().log().all());
        }
        return get(REQUEST);
    }

    public Response getResponse() {
        return get(RESPONSE);
    }

    public Response setResponse(Response response) { return set(RESPONSE, response); }

    public Object getPayload() { return get(PAYLOAD); }

    public <T> void setPayload(T object) { set(PAYLOAD, object); }

    public void reset() { testContexts.get().clear(); }

}
