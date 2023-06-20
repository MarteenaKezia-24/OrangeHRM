package com.disney.config;

import com.disney.common.SauceLabsTestHelper;
import com.disney.commons.AutomationDriver;
import com.disney.commons.WebSequoia;
import com.disney.utilities.managers.ExternalPropertiesManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static com.disney.steps.RunnerTest.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.TimeoutException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;

public class WebConfig extends ConfigVariables {
	private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Bean
	public static ExternalPropertiesManager provideExternalProperties() {
		return new ExternalPropertiesManager();
	}

	// ********************
	// ** Helper Methods **
	// ********************

	@Bean
	public boolean provideRunContinuously() {
		return provideExternalProperties().getExternalRunContinuouslyProperty();
	}

	@Bean
	public WebSequoia provideTree() throws IOException, InterruptedException, ExecutionException, TimeoutException {
		logger.info("building web tree");
		WebSequoia tree = new WebSequoia(provideElements(), provideWorkingBrowser(), "com.disney", null);
		logger.info("setting driver");
		return tree;
	}
    public String getTestUrls(String content)
    {
    	String url=null;
    	Properties prop = new Properties();

		try {	
			URL res = getClass().getClassLoader().getResource("com/disney/properties/testURLs.properties");
			prop.load(res.openStream());
		} catch (IOException e) {
				e.printStackTrace();
		}
		content=content.replaceAll(" ","_");
		url=prop.getProperty(content);
		return url;
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

		File currDir = new File("");
		String dirPath = currDir.getAbsolutePath();//.replace(".", "");

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
	public WebDriver provideWorkingBrowser()
			throws InterruptedException, ExecutionException, TimeoutException, IOException {
		usingGrid = Boolean.valueOf(provideExternalProperties().getExternalUseGridProperty());
		usingSauceLabs = Boolean.valueOf(provideExternalProperties().getExternalUseSauceLabsProperty());
		if (usingGrid) {
			return provideGridBrowser();
		} else if (usingSauceLabs) {
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
	public WebDriver provideSauceLabsBrowser()
			throws InterruptedException, ExecutionException, TimeoutException, IOException {
		WebDriver driver;
		String browser = provideExternalProperties().getExternalBrowserProperty();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		MutableCapabilities sauceCaps = new MutableCapabilities();
		if (browser.equalsIgnoreCase("firefox") || browser.equalsIgnoreCase("safari")) {
			sauceCaps.setCapability("tunnelIdentifier", provideExternalProperties().getSauceLabsTunnelId());
			sauceCaps.setCapability("username", provideExternalProperties().getExternalSauceLabsUsernameProperty());
			sauceCaps.setCapability("accessKey", provideExternalProperties().getExternalSauceLabsTokenProperty());
			sauceCaps.setCapability("seleniumVersion", "3.14.0");
			sauceCaps.setCapability("screenResolution", "1600x1200");
			sauceCaps.setCapability("name",
					"FrondEnd_web" + provideExternalProperties().getExternalEnvironmentProperty());
			sauceCaps.setCapability("maxDuration", "7200");
			sauceCaps.setCapability("extendedDebugging",
					provideExternalProperties().getExternalExtendedDebuggingProperty());
			capabilities.setCapability("sauce:options", sauceCaps);
			capabilities.setCapability("browserVersion",
					provideExternalProperties().getExternalBrowserVersionProperty());

		} else {
			capabilities.setCapability("tunnelIdentifier", provideExternalProperties().getSauceLabsTunnelId());
			sauceCaps.setCapability("username", provideExternalProperties().getExternalSauceLabsUsernameProperty());
			sauceCaps.setCapability("accessKey", provideExternalProperties().getExternalSauceLabsTokenProperty());
			capabilities.setCapability("seleniumVersion", "3.14.0");
			capabilities.setCapability("screenResolution", "1600x1200");
			sauceCaps.setCapability("name",
					"FrondEnd_web" + provideExternalProperties().getExternalEnvironmentProperty());
			capabilities.setCapability("maxDuration", "7200");
			capabilities.setCapability("extendedDebugging",
					provideExternalProperties().getExternalExtendedDebuggingProperty());
			capabilities.setCapability("version", provideExternalProperties().getExternalBrowserVersionProperty());
		}

		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("platform", providePlatform());

		switch (browser) {
		case "internet explorer":
			capabilities.setCapability("iedriverVersion", "3.4.0");
			break;
		case "firefox":
			capabilities.merge(new FirefoxOptions());
			break;
		case "safari":
			capabilities.merge(new SafariOptions());
			break;
		}

		logger.info("Creating SAUCELABS webdriver with capabilities:\n\n{}\n", capabilities);

		ExecutorService executor = Executors.newSingleThreadExecutor();
		Callable<WebDriver> task = () -> {
			return new RemoteWebDriver(new URL(provideSauceLabsHubAddress()), capabilities);
		};

		try {
			driver = executor.submit(task).get(3, TimeUnit.MINUTES);
		} catch (TimeoutException e) {
			executor.shutdownNow();
			executor = Executors.newSingleThreadExecutor();
			logger.debug("{}: Failed to instantiate RemoteWebDriver. Retrying...", runnerName);
			driver = executor.submit(task).get(3, TimeUnit.MINUTES);
		}
		executor.shutdownNow();
		return driver;
	}

	@Bean
	public String provideSauceLabsHubAddress() {
		return "https://" + provideExternalProperties().getExternalSauceLabsUsernameProperty() + ":"
				+ provideExternalProperties().getExternalSauceLabsTokenProperty()
				+ "@ondemand.saucelabs.com:443/wd/hub";
	}

	@Bean
	@Scope("prototype")
	public WebDriver provideGridBrowser() throws MalformedURLException {
		AutomationDriver automationDriver = new AutomationDriver();
		String platform = provideExternalProperties().getExternalPlatformProperty().toLowerCase();
		String browser = provideExternalProperties().getExternalBrowserProperty();

		switch (browser) {
		case "firefox":
			break;
		case "chrome":
			automationDriver.addToOptionsList("disable-component-update");
			automationDriver.setChromeExperimentalOption("excludeSwitches");
			if (platform.contains("win")) {
				automationDriver.setChromeSettingsAndExtensionsLocation("C:\\tmp");
			} else {
				automationDriver.setChromeSettingsAndExtensionsLocation(
						"./web-acceptance-tests/src/test/resources/com/disney/assets");
			}
			automationDriver.addChromeArgument("--dns-prefetch-disable");
			automationDriver.addChromeArgument("--allow-http-screen-capture");
			automationDriver.addChromeArgument("-incognito");
			break;
		case "internet explorer":
			break;
		case "MicrosoftEdge":
			break;
		case "safari":
			automationDriver.setSafariUseCleanSession(true);
			break;
		case "htmlunit":
			break;
		default:
			throw new IllegalArgumentException(
					"Parameter: " + provideExternalProperties().getExternalBrowserProperty() + " is invalid.");
		}
		automationDriver.activateLoggingCapabilities();
		WebDriver driver = automationDriver.instantiateRemoteWebDriver(browser, providePlatform(), hubUrl);

		if (!browser.equalsIgnoreCase("safari") && !browser.equalsIgnoreCase("MicrosoftEdge")) {
			driver.manage().window().setPosition(new Point(0, 20));
			driver.manage().window().setSize(new Dimension(1600, 1000));
		}
		return driver;
	}

	@Bean
	public String provideHubAddress() {
		return hubUrl.replace("http://", "").replace(":4444/wd/hub", "");
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
		case "sierra":
			return Platform.SIERRA;
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
		String osName = System.getProperty("os.name").toLowerCase();
		String driverDirectory = resourceLoader.getResource("classpath:/com/disney/drivers").getFile()
				.getAbsolutePath();

		logger.info("getting local {} driver", provideExternalProperties().getExternalBrowserProperty());
		switch (provideExternalProperties().getExternalBrowserProperty().toLowerCase()) {
		case "firefox":
			if (osName.contains("win")) {
				System.setProperty("webdriver.gecko.driver", String.format("%s/geckodriver.exe", driverDirectory));
			} else if (osName.contains("linux")) {
				System.setProperty("webdriver.gecko.driver", String.format("%s/geckodriverlinux", driverDirectory));
			} else {
				System.setProperty("webdriver.gecko.driver", String.format("%s/geckodriver", driverDirectory));
			}

			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.merge(capabilities);
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("-incognito");
			chromeOptions.addArguments("--window-size=2000,1000");
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
			// safariOptions.useCleanSession(true);
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