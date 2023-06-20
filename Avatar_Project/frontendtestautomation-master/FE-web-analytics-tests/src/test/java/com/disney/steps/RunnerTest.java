package com.disney.steps;

import com.disney.config.WebConfig;
import com.disney.common.SauceLabsTestHelper;
import com.disney.commons.WebSequoia;
import com.github.mkolisnyk.cucumber.runner.RetryAcceptance;
import com.saucelabs.ci.sauceconnect.SauceConnectFourManager;
import net.lightbody.bmp.BrowserMobProxy;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

@SuppressWarnings({"WeakerAccess"})
public class RunnerTest
{
    private static final Logger logger = LoggerFactory.getLogger(RunnerTest.class);

    public static boolean usingSauceLabs = false;
    public static boolean isSignedIn = false;
    public static boolean optimizelyOptOut = true;
    public static boolean isFirstRun = true;
    public static boolean usingProxy = true;
    public static ApplicationContext context;
    public static WebSequoia tree;
    public static String environment;
    public static String browser;
    public static Map<String, String> capturedEvent;
    public static boolean retry = true;
    public static SauceLabsTestHelper sauceLabsTestHelper;
    public static BrowserMobProxy proxy;
    public static SauceConnectFourManager sauceConnectMgr;
    public static String runnerName = "RunnerTest";

    @RetryAcceptance
    public static boolean retryCheck(Throwable e)
    {
        logger.debug("Retry? - {}", retry);
        return retry;
    }

    @BeforeClass
    public static void setup()
    {
    }

    public static void runnerSetup()
    {
        context = new AnnotationConfigApplicationContext(WebConfig.class);
        if (usingProxy)
        {
            proxy = (BrowserMobProxy) context.getBean("provideProxy");
            if (Boolean.valueOf(System.getProperty("use_sauce_labs", "false")))
                sauceConnectMgr = (SauceConnectFourManager) context.getBean("provideSauceConnectManager");
        }
        else
        {
            if (Boolean.valueOf(System.getProperty("use_sauce_labs", "false")))
                sauceConnectMgr = (SauceConnectFourManager) context.getBean("provideSauceConnectManagerWithNoProxy");
        }
        tree = (WebSequoia) context.getBean("provideTree");
        logger.info("{}", ((RemoteWebDriver) tree.getDriver()).getCapabilities());
        browser = ((RemoteWebDriver) tree.getDriver()).getCapabilities().getBrowserName();
      
    }

    @AfterClass
    public static void tearDown()
    {
        try
        {
            runnerTearDown();
        }
        catch (UnreachableBrowserException | NoSuchWindowException e)
        {
            logger.info("\n\nCan't exit browser\n");
        }
    }

    public static void runnerTearDown()
    {
        tree.killDriver();
    }

    public static void resetStates()
    {
        isSignedIn = false;
        capturedEvent = null;
    }

    public static void setNewDriverInTree()
    {
        WebDriver driver;

        try
        {
            logger.info("Attempting to get new driver...");
            driver = (WebDriver) context.getBean("provideWorkingBrowser");
        }
        catch (WebDriverException | BeanCreationException e)
        {
            logger.info("Re-attempting to get new driver...");
            driver = (WebDriver) context.getBean("provideWorkingBrowser");
        }

        logger.info("Successfully retrieved driver");
        tree.setDriver(driver);
    }

}
