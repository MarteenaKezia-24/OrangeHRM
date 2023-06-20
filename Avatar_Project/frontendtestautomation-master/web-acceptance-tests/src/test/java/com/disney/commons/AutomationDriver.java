package com.disney.commons;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Class for setting up driver for use with project.
 *
 * @author James Graham
 */
public class AutomationDriver {
	private ChromeOptions chromeOptions = new ChromeOptions();
	private SafariOptions safariOptions = new SafariOptions();
	private EdgeOptions edgeOptions = new EdgeOptions();
	private DesiredCapabilities capabilities = new DesiredCapabilities();
	private List<String> list = new ArrayList<>();
	private WebDriver driver;
	private boolean logging = false;
	private Proxy proxy = null;

	/**
	 * Any options that you would like injected into experimental options for
	 * Chrome. Set this up before setting an experimental option.
	 * <p>
	 * Make sure to use the clearList() method if you wish to set up an additional
	 * clean list for the experimental options.
	 *
	 * @param option the option to be added the list to be used with experimental
	 *               options.
	 */
	public void addToOptionsList(String option) {
		list.add(option);
	}

	/**
	 * Clears the list that is to be used with the Chrome experimental options.
	 */
	public void clearList() {
		list.clear();
	}

	/**
	 * Set binary path to the path specified in the string parameter
	 */
	public void setBinary(String path) {
		chromeOptions.setBinary(path);
	}

	/**
	 * Set an experimental Chrome option using the list. List must have items in it
	 * before this method is called.
	 *
	 * @param experimentalOption String containing the experimental option you Wish
	 *                           to set
	 */
	public void setChromeExperimentalOption(String experimentalOption) {
		chromeOptions.setExperimentalOption(experimentalOption, list);
	}

	/**
	 * Set the chrome SettingsAndExtensions location. Use this to specify a location
	 * for a folder that will download and save the addons you need over time such
	 * as Widevine or PNaCL for DRM. This will however save session data and
	 * passwords. For fresh sessions make sure to also use
	 * activateChromeIncognito().
	 *
	 * @param settingsAndExtensionsLocation String containing filepath to where the
	 *                                      file should be stored or accessed.
	 *                                      <p>
	 *                                      example:
	 *                                      ./src/test/resources/com/disney/assets
	 */
	public void setChromeSettingsAndExtensionsLocation(String settingsAndExtensionsLocation) {
		chromeOptions.addArguments(
				String.format("user-data-dir=%s%s", settingsAndExtensionsLocation, "/ChromeSettingsAndExtensions"));
	}

	/**
	 * Specify the location that your chromeDriver is stored for running automated
	 * tests with Chrome on Mac. Do this before instantiating your WebDriver.
	 *
	 * @param chromeDriverLocation
	 */
	public void setChromeDriverLocation(String chromeDriverLocation) {
		System.setProperty("webdriver.chrome.driver", String.format("%s/chromedriver", chromeDriverLocation));
	}

	/**
	 * Specify the location that your IEDriverServer is stored for running automated
	 * tests with Internet explorer. Do this before instantiating your WebDriver.
	 *
	 * @param IEDriverServerLocation
	 */
	public void setIEDriverServerLocation(String IEDriverServerLocation) {
		System.setProperty("webdriver.ie.driver", String.format("%s/IEDriverServer.exe", IEDriverServerLocation));
	}

	/**
	 * Specify the location that your Edge Driver is stored for running automated
	 * tests with Edge. Do this before instantiating your WebDriver.
	 *
	 * @param EdgeDriverLocation
	 */
	public void setEdgeDriverLocation(String EdgeDriverLocation) {
		System.setProperty("webdriver.edge.driver", String.format("%s/MicrosoftWebDriver.exe", EdgeDriverLocation));
	}

	/**
	 * Specify the location that your Edge Driver is stored for running automated
	 * tests with Edge. Do this before instantiating your WebDriver.
	 *
	 * @param EdgeDriverLocation
	 */
	public void setEdgeDriverLocationMSI(String EdgeDriverLocation) {
		System.setProperty("webdriver.edge.driver", String.format("%s/MicrosoftWebDriver.msi", EdgeDriverLocation));
	}

	/**
	 * Specify the location that your chromeDriver.exe is stored for running
	 * automated tests with Chrome on Windows. Do this before instantiating your
	 * WebDriver.
	 *
	 * @param chromeDriverLocation
	 */
	public void setChromeDriverLocationWindows(String chromeDriverLocation) {
		System.setProperty("webdriver.chrome.driver", String.format("%s/chromedriver.exe", chromeDriverLocation));
	}

	/**
	 * Specify the location that your IEDriverServer.exe is stored for running
	 * automated tests with Internet Explorer on Windows. Do this before
	 * instantiating your WebDriver.
	 *
	 * @param ieDriverLocation
	 */
	public void setIeDriverLocationWindows(String ieDriverLocation) {
		System.setProperty("webdriver.ie.driver", String.format("%s/IEDriverServer.exe", ieDriverLocation));
	}

	/**
	 * Add an option into Chrome. Do this before instantiating the WebDriver.
	 *
	 * @param chromeArgument String with argument to be added to chromeOptions.
	 */
	public void addChromeArgument(String chromeArgument) {
		chromeOptions.addArguments(chromeArgument);
	}

	/**
	 * Turn on the Chrome Incognito mode so that it doesn't save passwords or Log-In
	 * states. This becomes necessary after specifying a folder save location in
	 * order to simulate original chromedriver state.
	 */
	public void activateChromeIncognito() {
		chromeOptions.addArguments("-incognito");
	}

	/**
	 * Sets Safari to use a clean session so that it starts without saved data.
	 * (Currently the option doesn't work properly. Known Safari Driver issue.)
	 *
	 * @param useCleanSession Boolean with true or false.
	 */
	public void setSafariUseCleanSession(boolean useCleanSession) {
		// safariOptions.setUseCleanSession(useCleanSession);
	}

	/**
	 * Use this method in order to activate the browsers logging capabilities
	 */
	public void activateLoggingCapabilities() {
		logging = true;
	}

	/**
	 * Instantiate and return the WebDriver for a local browser. All desired options
	 * must be set BEFORE driver instantiation.
	 *
	 * @param browserName String with name of browser to be instantiated.
	 *                    <p>
	 *                    Examples: chrome, safari, firefox, internet explorer,
	 *                    edge, and htmlunit.
	 * @return WebDriver that was instantiated.
	 */
	public WebDriver instantiateLocalWebDriver(String browserName) {
		Map<String, Runnable> desiredCapabilitiesMap = new HashMap<>();

		desiredCapabilitiesMap.put("chrome", () -> setChromeCapabilities());
		desiredCapabilitiesMap.put("safari", () -> setSafariCapabilities());
		desiredCapabilitiesMap.put("firefox", () -> setFirefoxCapabilities());
		desiredCapabilitiesMap.put("internet explorer", () -> setInternetExplorerCapabilites());
		desiredCapabilitiesMap.put("edge", () -> setEdgeCapabilities());
		desiredCapabilitiesMap.put("htmlunit", () -> setHtmlUnitCapabilities());

		desiredCapabilitiesMap.get(browserName.toLowerCase()).run();

		if (proxy != null) {
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}

		if (logging) {
			initializeLoggingCapabilities();
		}

		switch (browserName.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver(capabilities);
			break;
		case "safari":
			driver = new SafariDriver(capabilities);
			break;
		case "firefox":
			driver = new FirefoxDriver(capabilities);
			break;
		case "internet explorer":
			driver = new InternetExplorerDriver(capabilities);
			break;
		case "edge":
			driver = new EdgeDriver(capabilities);
			break;
		case "htmlunit":
			driver = new HtmlUnitDriver(capabilities);
		}

		return driver;
	}

	/**
	 * Instantiates and returns a WebDriver for a remote WebDriver location. For use
	 * with a grid normally. All desired options must be set BEFORE driver
	 * instantiation.
	 *
	 * @param browserName String with name of browser to be instantiated.
	 *                    <p>
	 *                    Examples: chrome, safari, firefox, internet explorer,
	 *                    edge, and htmlunit.
	 * @param platform    Platform object with the platform information for the
	 *                    grid.
	 * @param hubUrl      String containing the URL for the grid.
	 * @return Remote WebDriver that was instantiated.
	 * @throws MalformedURLException
	 */
	public WebDriver instantiateRemoteWebDriver(String browserName, Platform platform, String hubUrl)
			throws MalformedURLException {
		Map<String, Runnable> desiredCapabilitiesMap = new HashMap<>();

		desiredCapabilitiesMap.put("chrome", () -> setChromeCapabilities());
		desiredCapabilitiesMap.put("safari", () -> setSafariCapabilities());
		desiredCapabilitiesMap.put("firefox", () -> setFirefoxCapabilities());
		desiredCapabilitiesMap.put("internet explorer", () -> setInternetExplorerCapabilites());
		desiredCapabilitiesMap.put("MicrosoftEdge", () -> setEdgeCapabilities());
		desiredCapabilitiesMap.put("htmlunit", () -> setHtmlUnitCapabilities());

		desiredCapabilitiesMap.get(browserName).run();

		capabilities.setBrowserName(browserName);
		capabilities.setPlatform(platform);

		if (proxy != null) {
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}

		if (logging) {
			initializeLoggingCapabilities();
		}

		return driver = (new RemoteWebDriver(new URL(hubUrl), capabilities));
	}

	/**
	 * Activates browsers ability to retain logs for printing out the console.
	 */
	private void initializeLoggingCapabilities() {
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.INFO);
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
	}

	/**
	 * Sets the necessary capabilities for Chrome.
	 */
	private void setChromeCapabilities() {
		capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

	}

	/**
	 * Sets the necessary capabilities for Safari.
	 */
	private void setSafariCapabilities() {
		capabilities = DesiredCapabilities.safari();
		capabilities.setCapability(SafariOptions.CAPABILITY, safariOptions);
	}

	/**
	 * Sets the necessary capabilities for Firefox.
	 */
	private void setFirefoxCapabilities() {
		capabilities = DesiredCapabilities.firefox();
	}

	/**
	 * Sets the necessary capabilities for Internet Explorer.
	 */
	private void setInternetExplorerCapabilites() {
		capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		capabilities.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
	}

	/**
	 * Sets the necessary capabilities for Edge.
	 */
	private void setEdgeCapabilities() {
		capabilities = DesiredCapabilities.edge();
		// capabilities.setCapability(EdgeOptions.setP, edgeOptions);
	}

	/**
	 * Sets the necessary capalities for HTMLUnit.
	 */
	private void setHtmlUnitCapabilities() {
		capabilities = DesiredCapabilities.htmlUnit();
	}

	/**
	 * Only to be used after driver instantiation if you want to get the driver.
	 *
	 * @return previosly instantiated WebDriver.
	 */
	public WebDriver getDriver() {
		return driver;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
}
