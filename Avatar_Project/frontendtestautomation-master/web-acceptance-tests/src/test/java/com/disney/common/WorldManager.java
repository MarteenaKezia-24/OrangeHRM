package com.disney.common;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.disney.steps.RunnerTest.*;

import java.io.IOException;

/**
 * Manages the environment that will be carried through entire test suite.
 * <p>
 * DO NOT CHANGE THIS CLASS
 */
public class WorldManager {
	private static final Logger logger = LoggerFactory.getLogger(WorldManager.class);
	public static Scenario scenario;
	public static String tempTitle;
	public static String outHeader;
	public static int titleLength;
	public static int bufferLength;
	public static int actualLength;

	@Before
	public void before(Scenario scenario) throws IOException {
		WorldManager.scenario = scenario;

		if (!scenario.getSourceTagNames().toString().contains("api")) {
			if (isFirstRun) {
				runnerSetup();

				Runtime.getRuntime().addShutdownHook(new Thread(() -> {
					logger.info("tearing down browsers");
					runnerTearDown();
				}));
				isFirstRun = false;
			}

			if (tree.getDriver().toString().contains("(null)")) {
				tree.waitFor(5000);
				setNewDriverInTree();
				logger.info("setting up a new driver");
			}

			setupScenario();

			if (usingSauceLabs) {
				if (System.getProperty("parallel.scheme") != null
						&& System.getProperty("parallel.scheme").equals("SCENARIO"))
					tree.executeScript(String.format("sauce:job-name=[%s] %s", getFeatureName(), scenario.getName()));
				else
					tree.executeScript(String.format("sauce:job-name=[%s]", getFeatureName()));
			}
		}
	}

	private String getFeatureName() {
		return WordUtils.capitalize(scenario.getId().split(";")[0].replaceAll("-+", " "));
	}

	private void setupScenario() {
		startTestCase(scenario.getName());
	}

	/**
	 *
	 */
	@After
	public void cleanUp(Scenario scenario) {
		endTestCase();
		if (scenario.isFailed()) {
			logger.info("Using grid: {}\n", usingGrid);
			if (usingGrid) {
				logger.info("\n\nCurrent IP: {}\n", ip);
				writeVideoLocation(scenario, tree.getSessionId(), ip, "3000");
			} else if (usingSauceLabs) {
				String link = sauceLabsTestHelper.getSauceLabsVideoLink();
				scenario.write("******************** URL to video of test: ********************\n" + link + "\n");
				scenario.write(String.format(
						"\n![screenshot](https://saucelabs.com/rest/v1/disney_qe/jobs/%s/assets/final_screenshot.png?auth=%s)",
						sauceLabsTestHelper.getSessionId(), link.substring(link.indexOf('=') + 1)));
				sauceLabsTestHelper.setSauceLabsJobFailure();
			}
			try {
				embedCucumberScreenshot(scenario, tree.takeScreenshot());
			} catch (WebDriverException e) {
				logger.info("\n\nUnable to take screenshot. Unreachable browser\n");
			}

			try {
				scenario.write(String.format("\nURL: %s\n", tree.getCurrentUrl()));
			} catch (Exception e) {
			}

			runnerTearDown();
		} else {
			if (usingSauceLabs) {
				sauceLabsTestHelper.setSauceLabsJobPass();
				scenario.write("******************** URL to video of test: ********************\n"
						+ sauceLabsTestHelper.getSauceLabsVideoLink() + "\n");
			}

			if (!runContinuously) {
				runnerTearDown();
			}
		}
	}

	public static void startTestCase(final String testCaseName) {
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

	public static void writeVideoLocation(Scenario scenario, String sessionId, String ipAddressOfNode,
			String portOfNode) {
		String link = "******************** Link to video of test: ********************\n\n";
		link += String.format(
				"<a href=http://%s:%s/download_video/%s.mp4 download>http://%s:%s/download_video/%s.mp4</a>",
				ipAddressOfNode, portOfNode, sessionId, ipAddressOfNode, portOfNode, sessionId);
		scenario.embed(link.getBytes(), "text/html");
	}
}
