package com.disney.steps;

import com.disney.common.SauceLabsTestHelper;
import com.disney.commons.SeleniumGridUtilities;
import com.disney.commons.WebSequoia;
import com.disney.config.AuthConfig;
import com.typesafe.config.Config;
import org.json.JSONException;
import org.openqa.grid.common.exception.GridException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;

/**
 * Main class that drives the test automation. Run this class to run your tests
 * if you prefer the GUI.
 * <p>
 * DO NOT CHANGE THIS CLASS
 */
@SuppressWarnings({ "WeakerAccess" })
public class RunnerTest {
	private static final Logger logger = LoggerFactory.getLogger(RunnerTest.class);

	public static boolean ranFromRunner = true;
	public static boolean runContinuously = true;
	public static boolean usingGrid = false;
	public static boolean usingSauceLabs = false;
	public static boolean isThroughHub = false;
	public static boolean isSignedIn = false;
	public static boolean isPopupClosed = false;
	public static boolean isSafariStateClean = false;
	public static boolean isFirstRun = true;
	public static ApplicationContext context;
	public static WebSequoia tree;
	public static String environment;
	public static String browser;
	public static String ip = null;
	public static SauceLabsTestHelper sauceLabsTestHelper;
	public static Config authTree;
	public static String runnerName = "Runner1";
	public static String multiThreadAccountNumber;
	public static String email;

	public static void runnerSetup() throws IOException {
		context = new AnnotationConfigApplicationContext(AuthConfig.class);
		tree = (WebSequoia) context.getBean("provideTree");
		runContinuously = (Boolean) context.getBean("provideRunContinuously");
		logger.info("{}", ((RemoteWebDriver) tree.getDriver()).getCapabilities());
		browser = ((RemoteWebDriver) tree.getDriver()).getCapabilities().getBrowserName();
		logger.info("BROWSER = {}", browser);

		if (usingGrid) {
			try {
				logger.info("\n\nAttempting to get node IP...\n");
				ip = SeleniumGridUtilities.getIpOfCurrentNode(tree.getDriver(),
						(String) context.getBean("provideHubAddress"), 4444);
				logger.info("\n\nCurrent node IP: {}\n", ip);
			} catch (JSONException | GridException e) {
				logger.info("\n\nCouldn't get current node IP.\n");
			}
		}
	}

	public static void runnerTearDown() {
		if (!tree.getDriver().toString().contains("(null)")) {
			logger.debug("Attempting to kill the driver on teardown");
			try {
				tree.killDriver();
			} catch (WebDriverException e) {
				logger.debug("Unable to kill the driver", e);
			}
		}

		if (usingGrid) {
			try {
				SeleniumGridUtilities.sendBrowserCloseRequest(ip, browser);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
        
		resetStates();
	}

	public static void resetStates() {
		isFirstRun = true;
		isThroughHub = false;
		isSignedIn = false;
		isPopupClosed = false;
		isSafariStateClean = false;
	}

	public static void setNewDriverInTree() {
		WebDriver driver;

		try {
			logger.info("Attempting to get new driver...");
			driver = (WebDriver) context.getBean("provideWorkingBrowser");
			logger.info("Successfully retrieved driver");
		} catch (WebDriverException | BeanCreationException e) {
			if (usingGrid) {
				logger.info("Sending {} close request....", browser);
				try {
					SeleniumGridUtilities.sendBrowserCloseRequest(ip, browser);
				} catch (JSONException er) {
					// ignore exception
				}
				tree.waitFor(10000);
				try {
					tree.killDriver();
				} catch (WebDriverException er) {
					// ignore exception
				}
			}

			logger.info("Re-attempting to get new driver...");
			driver = (WebDriver) context.getBean("provideWorkingBrowser");
		}

		logger.info("Setting driver in all pages...");
		tree.setDriver(driver);

		if (usingGrid) {
			try {
				logger.info("\n\nAttempting to get node IP...\n");
				ip = SeleniumGridUtilities.getIpOfCurrentNode(tree.getDriver(),
						(String) context.getBean("provideHubAddress"), 4444);
				logger.info("\n\nCurrent node IP: {}\n", ip);
			} catch (JSONException | GridException e) {
				logger.info("\n\nCouldn't get current node IP.\n");
			}
		}
	}
}
