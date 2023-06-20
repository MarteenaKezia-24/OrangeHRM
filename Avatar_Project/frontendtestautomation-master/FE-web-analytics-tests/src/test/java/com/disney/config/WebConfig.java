package com.disney.config;

import com.disney.common.SauceLabsTestHelper;
import com.disney.commons.WebSequoia;
import com.disney.utilities.managers.ExternalPropertiesManager;
import com.saucelabs.ci.sauceconnect.AbstractSauceTunnelManager;
import com.saucelabs.ci.sauceconnect.SauceConnectFourManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;

import static com.disney.steps.RunnerTest.*;

public class WebConfig extends ConfigVariables {
	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Bean
	@Scope("prototype")
	public ExternalPropertiesManager provideExternalProperties() {
		return new ExternalPropertiesManager();
	}

	// ********************
	// ** Helper Methods **
	// ********************

	@Bean
	@Lazy
	public WebSequoia provideTree() throws IOException {
		logger.info("building web tree");
		WebSequoia tree = new WebSequoia(provideElements(), provideWorkingBrowser(), "com.disney", null);
		// tree.setBaseUrl(provideWorkingEnvironment());
		logger.info("setting driver");
		return tree;
	}

	@Bean
	@Scope("prototype")
	public BrowserMobProxy provideProxy() {
		BrowserMobProxy proxy = new BrowserMobProxyServer();

		proxy.setTrustAllServers(true);
		if (provideExternalProperties().getExternalUseTestJSProperty())
			proxy.getHostNameResolver().remapHost("global.go.com", "go.static.mh.disney.io");
		proxy.start(0);

		//proxy.blacklistRequests(".*zopim.*", 404);

		return proxy;
	}

	@Bean
	@Scope("prototype")
	public SauceConnectFourManager provideSauceConnectManager() throws Exception {
		File currDir = new File("*");
		String dirPath = currDir.getAbsolutePath().replace("*", "");
		String js = dirPath + "sauce" + File.separator + "trial.js";
		try {
			PrintWriter writer = new PrintWriter(js, "UTF-8");
			writer.println(
					"function FindProxyForURL(url, host) {if (shExpMatch(host, \"*.miso.saucelabs.com\") || shExpMatch(host, \"*.api.testobject.com\") || shExpMatch(host, \"saucelabs.com\")|| shExpMatch(host, \"*.saucelabs.com\")) {return \"DIRECT\";}return \"PROXY localhost:"
							+ proxy.getPort() + "\";}");
			writer.close();
			String sauceDriverPath = "";

			if (System.getProperty("os.name").toUpperCase().contains("WIN"))
				sauceDriverPath = dirPath + "sauce" + File.separator + "sc.exe";
			else if (System.getProperty("os.name").toUpperCase().contains("LINUX"))
				sauceDriverPath = dirPath + "sauce" + File.separator + "sc_linux";
			else if (System.getProperty("os.name").toUpperCase().contains("MAC"))
				sauceDriverPath = dirPath + "sauce" + File.separator + "sc_mac";
			else
				throw new Exception("Unsupported Platform: " + System.getProperty("os.name"));
			String browserMobJSpath = dirPath + "sauce" + File.separator + "trial.js";
			browserMobJSpath = browserMobJSpath.replace("\\", "/");
			sauceConnectMgr = new SauceConnectFourManager();
			sauceConnectMgr.openConnection(provideExternalProperties().getExternalSauceLabsUsernameProperty(),
					provideExternalProperties().getExternalSauceLabsTokenProperty(), proxy.getPort() + 1, null,
					"-i " + provideExternalProperties().getExternalSauceLabsTunnelId() + runnerName +" --no-remove-colliding-tunnels"+ " --pac file://"
							+ browserMobJSpath + " -F zopim",
					null, false, sauceDriverPath);
		}

		catch (AbstractSauceTunnelManager.SauceConnectException e) {
			e.printStackTrace();
			sauceConnectMgr.closeTunnelsForPlan(provideExternalProperties().getExternalSauceLabsUsernameProperty(),
					"-i " + provideExternalProperties().getExternalSauceLabsTunnelId() + runnerName, null);
		}
		return sauceConnectMgr;
	}

	@Bean
	@Scope("prototype")
	public SauceConnectFourManager provideSauceConnectManagerWithNoProxy() {
		sauceConnectMgr = new SauceConnectFourManager();
		try {
			sauceConnectMgr.openConnection(provideExternalProperties().getExternalSauceLabsUsernameProperty(),
					provideExternalProperties().getExternalSauceLabsTokenProperty(), 0, null,
					"-i " + provideExternalProperties().getExternalSauceLabsTunnelId() + runnerName + " -F zopim", null,
					false, null);
		} catch (AbstractSauceTunnelManager.SauceConnectException e) {
			e.printStackTrace();
			sauceConnectMgr.closeTunnelsForPlan(provideExternalProperties().getExternalSauceLabsUsernameProperty(),
					"-i " + provideExternalProperties().getExternalSauceLabsTunnelId() + runnerName, null);
		}

		return sauceConnectMgr;
	}

	@Bean
	@Scope("prototype")
	public String provideWorkingEnvironment(String collectionType)
			throws ParserConfigurationException, SAXException, IOException {
		environment = provideExternalProperties().getExternalEnvironmentProperty().toLowerCase();
		System.out.println("FILE READER");

		String loadedClassPath = System.getProperty("user.dir") + System.getProperty("file.separator");
		StreamResult res = new StreamResult(
				new File(loadedClassPath + "//src//test//resources//properties//web_env.xml"));

		File currDir = new File(".");
		String dirPath = currDir.getAbsolutePath().replace(".", "");

		dirPath = dirPath
				+ "\\src\\test\\resources\\com\\disney\\properties\\web_env.xml".replace("\\", File.separator);

		File file = new File(dirPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
		Document doc = dbBuilder.parse(file);

		NodeList mainChilds = doc.getElementsByTagName("environment");

		for (int i = 0; i < mainChilds.getLength(); i++) {

			if (mainChilds.item(i).getAttributes().item(0).getNodeValue().equals(environment)) {
				NodeList urlList = mainChilds.item(i).getChildNodes();

				for (int k = 0; k < urlList.getLength(); k++) {

					if (!urlList.item(k).getNodeName().contains("#text")) {
						String name = urlList.item(k).getNodeName().substring(26);
						if (collectionType.equals(name)) {
							return urlList.item(k).getTextContent();
						}
					}
				}
			}
		}
		logger.info("No URL found for the collection " + collectionType);
		return null;
	}

	@Bean
	@Scope("prototype")
	public WebDriver provideWorkingBrowser() throws IOException {
		usingSauceLabs = Boolean.valueOf(provideExternalProperties().getExternalUseSauceLabsProperty());
		if (usingSauceLabs) {
			WebDriver driver = provideSauceLabsBrowser();
			sauceLabsTestHelper = new SauceLabsTestHelper((RemoteWebDriver) driver,
					provideExternalProperties().getExternalSauceLabsUsernameProperty(),
					provideExternalProperties().getExternalSauceLabsTokenProperty());
			return driver;
		} else {
			return provideLocalBrowser();
		}

	}

	@Bean
	@Scope("prototype")
	public WebDriver provideSauceLabsBrowser() throws MalformedURLException {
		String browser = provideExternalProperties().getExternalBrowserProperty();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME,
				provideExternalProperties().getExternalBrowserProperty());
		capabilities.setCapability("version", provideExternalProperties().getExternalBrowserVersionProperty());
		capabilities.setCapability("platform", providePlatform());
		capabilities.setCapability("name",
				"fronend_analytics_" + provideExternalProperties().getExternalEnvironmentProperty());
		capabilities.setCapability("build", provideExternalProperties().getExternalSauceLabsBuildProperty());
		capabilities.setCapability("screenResolution", "1600x1200");
		capabilities.setCapability("maxDuration", "7200");
		capabilities.setCapability("tunnelIdentifier",
				provideExternalProperties().getExternalSauceLabsTunnelId() + runnerName);

		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.INFO);
		logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

		switch (browser) {
		case "internet explorer":
			capabilities.setCapability("iedriverVersion", "3.4.0");
			break;
		case "firefox":
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("http.response.timeout", "5");
			capabilities.setCapability(FirefoxDriver.PROFILE, profile);
			capabilities.setCapability("acceptInsecureCerts", true);
			break;
		}

		logger.info("Creating SAUCELABS webdriver with capabilities:\n\n{}\n", capabilities);
		return (new RemoteWebDriver(new URL(provideSauceLabsHubAddress()), capabilities));
	}

	@Bean
	public String provideSauceLabsHubAddress() {
		return "https://" + provideExternalProperties().getExternalSauceLabsUsernameProperty() + ":"
				+ provideExternalProperties().getExternalSauceLabsTokenProperty()
				+ "@ondemand.saucelabs.com:443/wd/hub";
	}

	@Bean
	public Platform providePlatform() {
		switch (provideExternalProperties().getExternalPlatformProperty().toLowerCase()) {
		case "win":
		case "windows":
			return Platform.WINDOWS;
		case "vista":
			return Platform.VISTA;
		case "xp":
			return Platform.XP;
		case "win8":
			return Platform.WIN8;
		case "win8.1":
		case "win8_1":
			return Platform.WIN8_1;
		case "win10":
			return Platform.WIN10;
		case "mac":
			return Platform.MAC;
		case "yosemite":
			return Platform.YOSEMITE;
		case "mavericks":
			return Platform.MAVERICKS;
		case "mountain lion":
		case "mountain_lion":
			return Platform.MOUNTAIN_LION;
		case "el capitan":
		case "el_capitan":
			return Platform.EL_CAPITAN;
		case "snow leopard":
		case "snow_leopard":
			return Platform.SNOW_LEOPARD;
		case "unix":
			return Platform.UNIX;
		case "linux":
			return Platform.LINUX;
		case "android":
			return Platform.ANDROID;
		case "any":
			return Platform.ANY;
		default:
			return Platform.ANY;
		}
	}

	@Bean
	@Scope("prototype")
	public WebDriver provideLocalBrowser() throws IOException {
		WebDriver driver;
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (usingProxy) {
			Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
			capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);

		}

		String osName = System.getProperty("os.name").toLowerCase();
		String driverDirectory = resourceLoader.getResource("classpath:/com/disney/drivers").getFile()
				.getAbsolutePath();

		logger.info("getting local {} driver", provideExternalProperties().getExternalBrowserProperty());
		switch (provideExternalProperties().getExternalBrowserProperty().toLowerCase()) {
		case "firefox":
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			WebDriverManager.firefoxdriver().setup();
			firefoxOptions.merge(capabilities);
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("-incognito");
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--ignore-certificate-errors");
			// chromeOptions.addArguments("--window-size=2000,1000");
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.INFO);
			logPrefs.enable(LogType.PERFORMANCE, Level.INFO);
			capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			driver = new ChromeDriver(chromeOptions.merge(capabilities));
			break;
		case "internet explorer":
			System.setProperty("webdriver.ie.driver", String.format("%s/IEDriverServer.exe", driverDirectory));
			InternetExplorerOptions ieOptions = new InternetExplorerOptions();
			ieOptions.destructivelyEnsureCleanSession();
			ieOptions.introduceFlakinessByIgnoringSecurityDomains();
			ieOptions.setCapability(InternetExplorerDriver.ELEMENT_SCROLL_BEHAVIOR, 1);
			ieOptions.merge(capabilities);
			driver = new InternetExplorerDriver(ieOptions);
			break;
		case "edge":
			System.setProperty("webdriver.edge.driver", String.format("%s/MicrosoftWebDriver.exe", driverDirectory));
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.merge(capabilities);
			driver = new EdgeDriver(edgeOptions);
			break;
		case "safari":
			SafariOptions safariOptions = new SafariOptions();
			safariOptions.useCleanSession(true);
			safariOptions.merge(capabilities);
			driver = new SafariDriver(safariOptions);
			driver.manage().window().maximize();
			break;
		case "htmlunit":
			driver = new HtmlUnitDriver(capabilities);
			break;
		default:
			throw new IllegalArgumentException(
					"Parameter: " + provideExternalProperties().getExternalBrowserProperty() + " is invalid.");
		}

		return driver;
	}

	@Bean
	@Scope("prototype")
	public File provideElements() throws IOException {
		logger.info("extracting elements properties file");
		return resourceLoader.getResource("classpath:com/disney/properties/web_elements.properties").getFile();
	}
}