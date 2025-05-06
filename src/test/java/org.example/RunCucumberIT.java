package org.example;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/features"},
        plugin = {"pretty", "json:target/cucumber-report.json","html:target/cucumber-reports"},
        glue = {"org.example.regression.steps", "org.example.regression.common"},
        tags = "" )
public class RunCucumberIT  extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider()
    public Object[][] scenarios() { return super.scenarios(); }

}
