package com.disney.common;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.disney.steps.RunnerTest.*;

@SuppressWarnings({ "WeakerAccess" })
public class WorldManager {
	private static final Logger logger = LoggerFactory.getLogger(WorldManager.class);
	protected static Scenario scenario;
	public static String tempTitle;
	public static String outHeader;
	public static int titleLength;
	public static int bufferLength;
	public static int actualLength;

	@Before
	public void before(Scenario scenario) {
		WorldManager.scenario = scenario;

		if (isFirstRun) {
			runnerSetup();

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				Thread.currentThread().setName(runnerName + "-shutdown");
				try {
					if (proxy != null && !((BrowserMobProxyServer) proxy).isStopped()) {
						logger.info("stopping down proxy");
						proxy.stop();
					}
					if (sauceConnectMgr != null) {
						logger.info("shutting down Sauce Connect");
						sauceConnectMgr.closeTunnelsForPlan("sso-dcpi-PALLS018",
								"-i " + System.getProperty("sauce_tunnel_id") + runnerName, null);
					}
				} catch (Exception e) {
					logger.debug("Caught exception in shutdown", e);
				} finally {
					logger.info("tearing down browsers");
					runnerTearDown();
				}
			}));

			isFirstRun = false;
		}

		if (tree.getDriver().toString().contains("(null)")) {
			tree.waitFor(5000);
			setNewDriverInTree();
			logger.info("setting up a new driver");
		}

		setupScenario();
		resetStates();

		if (usingSauceLabs) {
			tree.executeScript(String.format("sauce:job-name=analytics_frontenddisney_%s [%s] %s", environment,
					getFeatureName(), scenario.getName()));
		}

		if (usingProxy)
			proxy.newHar();
	}

	private String getFeatureName() {
		return WordUtils.capitalize(scenario.getId().split(";")[0].replaceAll("-+", " "));
	}

	private void setupScenario() {
		startTestCase(scenario.getName());
	}

	@After
	public void cleanUp(Scenario scenario) {
		endTestCase();
		if (scenario.isFailed()) {
			if (usingSauceLabs) {
				String link = sauceLabsTestHelper.getSauceLabsVideoLink();
				scenario.write("******************** URL to video of test: ********************\n" + link + "\n");
				scenario.write(String.format(
						"\n![screenshot](https://saucelabs.com/rest/v1/disney_qe/jobs/%s/assets/final_screenshot.png?auth=%s)",
						sauceLabsTestHelper.getSessionId(), link.substring(link.indexOf('=') + 1)));
				sauceLabsTestHelper.setSauceLabsJobFailure();
			}
			try {
				byte[] screenshot = tree.takeScreenshot();
				embedCucumberScreenshot(scenario, screenshot);
			} catch (WebDriverException e) {
				logger.info("\n\nUnable to take screenshot. Unreachable browser\n");
			}

			try {
				scenario.write(String.format("\nURL: %s\n", tree.getCurrentUrl()));
			} catch (Exception e) {
				// ignore exception
			}
		} else {
			if (usingSauceLabs) {
				sauceLabsTestHelper.setSauceLabsJobPass();
				scenario.write("******************** URL to video of test: ********************\n"
						+ sauceLabsTestHelper.getSauceLabsVideoLink() + "\n");
			}
		}

		resetStates();

		if (usingProxy)
			proxy.endHar();
		runnerTearDown();
	}

	public static void startTestCase(String testCaseName) {
		tempTitle = "  " + testCaseName + "  ";
		titleLength = tempTitle.length();
		bufferLength = Math.abs(100 - titleLength) / 2;
		actualLength = titleLength + 2 * bufferLength;
		logger.info(
				"\n            .d8888. d888888b  .d8b.  d8888b. d888888b                \n            88\'  YP `~~88~~\' d8\' `8b 88  `8D `~~88~~\'                \n            `8bo.      88    88ooo88 88oobY\'    88                   \n              `Y8b.    88    88~~~88 88`8b      88                   \n            db   8D    88    88   88 88 `88.    88                   \n            `8888Y\'    YP    YP   YP 88   YD    YP                   \n                                                                     \n                                                                     \n  .d8888.  .o88b. d88888b d8b   db  .d8b.  d8888b. d888888b  .d88b.  \n  88\'  YP d8P  Y8 88\'     888o  88 d8\' `8b 88  `8D   `88\'   .8P  Y8. \n  `8bo.   8P      88ooooo 88V8o 88 88ooo88 88oobY\'    88    88    88 \n    `Y8b. 8b      88~~~~~ 88 V8o88 88~~~88 88`8b      88    88    88 \n  db   8D Y8b  d8 88.     88  V888 88   88 88 `88.   .88.   `8b  d8\' \n  `8888Y\'  `Y88P\' Y88888P VP   V8P YP   YP 88   YD Y888888P  `Y88P\'  \n\n");
		logger.info(createHeader());
	}

	public void endTestCase() {
		logger.info(
				"\n                     d88888b d8b   db d8888b.                        \n                     88\'     888o  88 88  `8D                        \n                     88ooooo 88V8o 88 88   88                        \n                     88~~~~~ 88 V8o88 88   88                        \n                     88.     88  V888 88  .8D                        \n                     Y88888P VP   V8P Y8888D\'                        \n                                                                     \n                                                                     \n  .d8888.  .o88b. d88888b d8b   db  .d8b.  d8888b. d888888b  .d88b.  \n  88\'  YP d8P  Y8 88\'     888o  88 d8\' `8b 88  `8D   `88\'   .8P  Y8. \n  `8bo.   8P      88ooooo 88V8o 88 88ooo88 88oobY\'    88    88    88 \n    `Y8b. 8b      88~~~~~ 88 V8o88 88~~~88 88`8b      88    88    88 \n  db   8D Y8b  d8 88.     88  V888 88   88 88 `88.   .88.   `8b  d8\' \n  `8888Y\'  `Y88P\' Y88888P VP   V8P YP   YP 88   YD Y888888P  `Y88P\'  \n \n");
	}

	private static String createHeader() {
		outHeader = "\n";
		buildChunk();
		buildMiddle();
		buildChunk();
		return outHeader;
	}

	private static void buildMiddle() {
		String buffer = StringUtils.repeat("*", bufferLength);
		outHeader += buffer + tempTitle + buffer + "\n";
	}

	private static void buildChunk() {
		String filler = StringUtils.repeat("*", actualLength);
		outHeader += filler + " \n" + filler + "\n";
	}

	public static void embedCucumberScreenshot(Scenario scenario, byte[] srcFile) {
		logger.debug("********************** Embedding screenshot **********************");
		scenario.embed(srcFile, "image/png");
		logger.debug("********************* Finished embedding of screenshot *********************");
	}
}
