package org.example.regression.common;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import io.cucumber.core.resource.ClasspathScanner;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import static org.example.regression.common.enums.TestContext.APP_URL;
import static org.example.regression.common.enums.TestContext.CONTEXT;

@Slf4j
public class PlaywrightInit {
 private static final String PROPERTIES = "acceptance-test.properties";
 private static final String BROWSER = "browser";
 private static Properties properties;
 private static String applicationUrl;
 private static final ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();
 private static final ThreadLocal<Browser> tlBrowser = new ThreadLocal<>();
 private static final ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
 private static final ThreadLocal<Page> tlPage = new ThreadLocal<>();
 public static synchronized Playwright getPlaywright() { return tlPlaywright.get(); }
 public static synchronized Browser getBrowser() { return tlBrowser.get(); }
 public static synchronized BrowserContext getBrowserContext() { return tlBrowserContext.get(); }
 public static synchronized Page getPage() { return tlPage.get(); }

 public static void initBrowser(String browserName) {
  String[] chromiumArgs = {"--ignore-certificate-errors"};
  BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
          .setHeadless(false)
          .setArgs(Arrays.asList(chromiumArgs));
  launchBrowser(browserName, options);
 }

 private static void launchBrowser(String browserName, BrowserType.LaunchOptions options) {
  switch (browserName.toLowerCase()) {
   case "chromium":
    log.info("Launching chromium");
    tlBrowser.set(getPlaywright().chromium().launch(options));
    break;
   case "chrome":
    log.info("Launching chrome");
    tlBrowser.set(getPlaywright().chromium().launch(options.setChannel("chrome")));
    break;
   case "msedge":
    log.info("Launching microsoft edge");
    tlBrowser.set(getPlaywright().chromium().launch(options.setChannel("msedge")));
    break;
   case "firefox":
    log.info("Launching firefox");
    tlBrowser.set(getPlaywright().chromium().launch(options.setChannel("firefox")));
    break;
   case "webkit":
    log.info("Launching webkit");
    tlBrowser.set(getPlaywright().chromium().launch(options.setChannel("webkit")));
    break;
   default:
    log.info("Correct browser name is not used");
    throw new UnsupportedOperationException("Unsupported browser :" + browserName);
  }
 }

 public static void initPage() {
  properties.putAll(System.getProperties());
  applicationUrl = properties.getProperty(APP_URL);
  CONTEXT.set(APP_URL, applicationUrl);
  if(getBrowser()!=null) {
   tlBrowserContext.set(getBrowser().newContext());
   tlPage.set(getBrowserContext().newPage());
   getPage().navigate(CONTEXT.get(APP_URL), new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
  } else { log.info("Thread local browser is not set.");}
 }

 @BeforeAll
 public static void setup() {
  Resource resource = new ClassPathResource(PROPERTIES);
  try (InputStream inputStream = resource.getInputStream()) {
   properties = new Properties();
   properties.load(inputStream);
  } catch (IOException ex) {
   log.error("Error in loading test property file");
  }
  String browserChoice = properties.getProperty(BROWSER);
  initBrowser(browserChoice);
  }

  @Before
  public void setupPage() { initPage(); }

}
