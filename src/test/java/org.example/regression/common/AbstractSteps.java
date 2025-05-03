package org.example.regression.common;

import org.example.regression.common.enums.TestContext;

public class AbstractSteps {
    private final String defaultUrl = "https://www.saucedemo.com/";
    private final String appTestUrl = System.getProperty("applicationTestUrl", defaultUrl);

    public String getAppTestUrl() { return appTestUrl; }
    public TestContext testContext() { return TestContext.CONTEXT; }
}
