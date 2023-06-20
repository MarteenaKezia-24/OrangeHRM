package com.disney.common;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import com.saucelabs.saucerest.SauceREST;
import cucumber.api.Scenario;
import org.junit.Rule;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 * This class is mainly used for controlling how the test status is updated on
 * the SauceLabs dashboard. Tests that have compiled successfully will be marked
 * as passed until a scenario fails, in which case it will be updated to be
 * false.
 */
public class SauceLabsTestHelper implements SauceOnDemandSessionIdProvider {
	@Rule
	private static SauceOnDemandTestWatcher sauceLabsWatcher;

	private static final Logger logger = LoggerFactory.getLogger(SauceLabsTestHelper.class);
	private static RemoteWebDriver driver;
	private static SauceOnDemandAuthentication auth;
	private static SauceREST sauceLabStatus;

	/**
	 * Constructor for a TestObject helper
	 *
	 * @param testObjectDriver
	 *            instantiated TestObject driver
	 */
	public SauceLabsTestHelper(RemoteWebDriver testObjectDriver) {
		logger.info("setting TestObject driver");
		driver = testObjectDriver;
	}

	/**
	 * Constructor for a SauceLabs helper. A username and access key is required
	 * in order to set the job status on the dashboard.
	 *
	 * @param sauceLabsDriver
	 *            instantiated SauceLabs driver
	 * @param sauceUsername
	 *            SauceLabs username
	 * @param sauceKey
	 *            SauceLabs access key
	 */
	public SauceLabsTestHelper(RemoteWebDriver sauceLabsDriver, String sauceUsername, String sauceKey) {
		logger.info("setting SauceLabs driver");
		driver = sauceLabsDriver;
		auth = new SauceOnDemandAuthentication(sauceUsername, sauceKey);
		initSauceLabsJobStatus();
	}

	public String getSessionId() {
		return driver.getSessionId().toString();
	}

	/**
	 * Sets the job status to passed upon successful helper instantiation.
	 */
	private void initSauceLabsJobStatus() {
		sauceLabsWatcher = new SauceOnDemandTestWatcher(this, auth);
		logger.info("initializing SauceLabs job status");
		sauceLabStatus = new SauceREST(auth.getUsername(), auth.getAccessKey());
		sauceLabStatus.jobPassed(getSessionId());
	}

	/**
	 * Checks the json from the SauceREST to see if the job has already been
	 * marked as a failure.
	 *
	 * @return boolean of the current status of the test.
	 */
	public boolean isFailed() {
		return sauceLabStatus.getJobInfo(getSessionId()).contains("\"passed\": false");
	}

	/**
	 * Sets the test as a failure which is reflected on the SauceLabs dashboard.
	 */
	public void setSauceLabsJobFailure() {
		if (!isFailed())
			sauceLabStatus.jobFailed(getSessionId());
	}

	public String getSauceLabsVideoLink() {
		return sauceLabStatus.getPublicJobLink(getSessionId());
	}

	public void setSauceLabsJobPass() {
		sauceLabStatus.jobPassed(getSessionId());
	}

	/**
	 * Adds a link to the TestObject test report to a scenario
	 *
	 * @param scenario
	 *            the current scenario
	 */
	public void embedTestObjectReport(Scenario scenario) {
		logger.info("Getting TestObject test report");
		String link = "******************** URL to test report: ********************\n\n";
		link = link.concat((String) driver.getCapabilities().getCapability("testobject_test_report_url"));

		logger.debug("Test Report Debugging: \n\n" + link);
		scenario.write(link);
	}

	/**
	 * Adds a link to the video recorded on the SauceLabs. A job token is
	 * created in order to preserve more sensitive information from being
	 * displayed in the URL.
	 *
	 * @param scenario
	 *            the current scenario
	 */
	public void embedSauceLabsVideo(Scenario scenario) {
		logger.info("Getting SauceLabs video");
		String link = String.format("https://saucelabs.com/jobs/%s?auth=%s", getSessionId(), getJobToken());

		String output = "******************** URL to video of test: ********************\n\n";
		output += "<a href=" + link + " target=\"_blank\">" + link + "</a>";
		scenario.embed(output.getBytes(), "text/html");

		logger.debug("Test Report Debugging: \n\n {}\n\n", link);
	}

	/**
	 * Creates a job token that is used to embed the video captured from
	 * SauceLabs
	 */
	private String getJobToken() {
		String algo = "HmacMD5";
		String token = null;
		try {
			SecretKeySpec key = new SecretKeySpec((auth.getUsername() + ":" + auth.getAccessKey()).getBytes("UTF-8"),
					algo);
			Mac mac = Mac.getInstance(algo);
			mac.init(key);

			byte[] bytes = mac.doFinal(getSessionId().getBytes("ASCII"));

			StringBuilder hash = new StringBuilder();
			for (byte b : bytes) {
				String hex = Integer.toHexString(0xFF & b);
				if (hex.length() == 1) {
					hash.append('0');
				}
				hash.append(hex);
			}
			token = hash.toString();
		} catch (UnsupportedEncodingException e) {
		} catch (InvalidKeyException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return token;
	}
}
