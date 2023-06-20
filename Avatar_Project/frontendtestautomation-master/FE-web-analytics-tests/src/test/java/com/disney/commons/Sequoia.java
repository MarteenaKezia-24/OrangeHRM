package com.disney.commons;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * This is the Abstract Base class in which all Sequoia children will inherit.
 * It contains all common functionality as well as contracts that are to be
 * implemented by children. This class has no default constructor. Created by
 * Trammel May on 12/22/16.
 */
public abstract class Sequoia {
	/*
	 **************************
	 ** Private Data Members **
	 **************************
	 */
	private static Logger logger = LoggerFactory.getLogger(Sequoia.class); // Class Level Logger
	private Dimension screenSize = null; // Singleton Dimension Object
	/*
	 ****************************
	 ** Protected Data Members **
	 ****************************
	 */
	protected WebDriver driver; // only one driver can be instantiated a time
	protected Config tree; // properties file as a tree
	protected String currentPage; // The current page node in which the user wants to access elements on the tree
	protected String treeRoot;
	/*
	 *************************
	 ** Public Data Members **
	 *************************
	 */
	public static long SHORT_WAIT = 500; // short wait time of 500 milliseconds
	public static long MEDIUM_WAIT = 1500; // medium wait time of 1500 milliseconds
	public static long LONG_WAIT = 60000; // long wait time of 60000 milliseconds

	/*
	 *******************
	 ** Constructors **
	 *******************
	 */

	/**
	 * <h>Parameterized Constructor</h>
	 * <p>
	 * this constructor will create a new instance of a sequoia object according to
	 * the arguments
	 * </p>
	 *
	 * @param treePath relative path to the tree properties file from the resources
	 *                 directory
	 * @param driver   instantiated web driver object, cannot be null
	 * @throws IOException this will be thrown if there are any issues loading the
	 *                     properties file
	 */
	public Sequoia(String treePath, WebDriver driver) throws IOException {
		this(treePath, driver, null, null);
	}

	/**
	 * <h>Parameterized Constructor</h>
	 * <p>
	 * this constructor will create a new instance of a sequoia object according to
	 * the arguments
	 * </p>
	 *
	 * @param treePath     relative path to the tree properties file from the
	 *                     resources directory
	 * @param driver       instantiated web driver object, cannot be null
	 * @param treeRoot     the root element of the tree from which all other
	 *                     elements are accessed
	 * @param startingPage the starting page node in which the tree elements will be
	 *                     accessed
	 * @throws IOException this will be thrown if there are any issues loading the
	 *                     properties file
	 */
	public Sequoia(String treePath, WebDriver driver, String treeRoot, String startingPage) throws IOException {
		setDriver(driver);
		setPage(startingPage);
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		this.treeRoot = treeRoot;
		tree = ConfigFactory.parseFile(resourceLoader.getResource(String.format("classpath:/%s", treePath)).getFile());
	}

	/**
	 * <h>Parameterized Constructor</h>
	 * <p>
	 * this constructor will create a new instance of a sequoia object according to
	 * the arguments
	 * </p>
	 *
	 * @param file   properties file containing application elements
	 * @param driver instantiated web driver object, cannot be null
	 */
	public Sequoia(File file, WebDriver driver) {
		this(file, driver, null, null);
	}

	/**
	 * <h>Parameterized Constructor</h>
	 * <p>
	 * this constructor will create a new instance of a sequoia object according to
	 * the arguments
	 * </p>
	 *
	 * @param file         properties file containing application elements
	 * @param driver       instantiated web driver object, cannot be null
	 * @param treeRoot     the root element of the tree from which all other
	 *                     elements are accessed
	 * @param startingPage the starting page node in which the tree elements will be
	 *                     accessed
	 */
	public Sequoia(File file, WebDriver driver, String treeRoot, String startingPage) {
		setDriver(driver);
		setPage(startingPage);
		this.treeRoot = treeRoot;
		tree = ConfigFactory.parseFile(file);
	}

	/*
	 ***************
	 ** Contracts **
	 ***************
	 */

	/**
	 * <h>Public Abstract Method - Get Element</h>
	 * <p>
	 * This method returns a web element, if available, matching the type and name
	 * of the arguments against the properties file. This method will wait for the
	 * argument duration, in milliseconds, for the element to be found.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement getElement(String type, String name, long milliseconds);

	/**
	 * <h>Public Abstract Method - Get Element</h>
	 * <p>
	 * This method returns a web element, if available, matching the type and name
	 * of the arguments against the properties file. This method will wait the
	 * LONG_WAIT time in milliseconds for the element to be found.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement getElement(String type, String name);

	/**
	 * <h>Public Abstract Method - Get Element</h>
	 * <p>
	 * This method returns a web element, if available, matching the type and name
	 * of the arguments against the properties file. This method will wait the
	 * LONG_WAIT time in milliseconds for the element to be found.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement getElement(String page, String type, String name);

	/**
	 * <h>Public Abstract Method - Get Element</h>
	 * <p>
	 * This method returns a web element, if available, matching the type and name
	 * of the arguments against the properties file. This method will wait the
	 * LONG_WAIT time in milliseconds for the element to be found.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement getElement(String page, String type, String name, long milliseconds);

	/**
	 * <h>Public Abstract Method - Click</h>
	 * <p>
	 * This method clicks and returns a web element, if available, matching the type
	 * and name of the arguments against the properties file. This method will wait
	 * for the argument duration, in milliseconds, for the element to be found.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement click(String type, String name, long milliseconds);

	/**
	 * <h>Public Abstract Method - Click</h>
	 * <p>
	 * This method clicks and returns a web element, if available, matching the type
	 * and name of the arguments against the properties file. This method will wait
	 * the LONG_WAIT time in milliseconds for the element to be found.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement click(String type, String name);

	/**
	 * <h>Public Abstract Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait for the argument duration, in milliseconds, for the element
	 * to be found.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement input(String type, String name, String text, long milliseconds);

	/**
	 * <h>Public Abstract Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement input(String type, String name, String text);

	/**
	 * <h>Public Abstract Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement input(String page, String type, String name, String text, long milliseconds);

	/**
	 * <h>Public Abstract Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public abstract WebElement input(String page, String type, String name, String text);

	/*
	 *************
	 ** Methods **
	 *************
	 */

	/**
	 * <h>Public Mutator Method - Set Driver</h>
	 * <p>
	 * This method sets the driver to the argument driver object. This method will
	 * not accept a null argument. If there is a driver already being used by the
	 * object, it will be killed. All errors that can occur during driver clean up
	 * are caught and logged along with stack traces. The driver will be killed even
	 * if such events occur.
	 * </p>
	 *
	 * @param driver instantiated web driver object, cannot be null
	 */
	public void setDriver(WebDriver driver) {
		if (driver == null)
			throw new NullPointerException("You must pass in a non-null WebDriver object");
		try {
			if (this.driver != null)
				killDriver();
		} catch (UnreachableBrowserException e) {
			logger.error(
					"Driver has been killed, however an UnreachableBrowserException was caught: {}, (printing stack trace now)",
					e.getMessage());
			e.printStackTrace();
		} catch (WebDriverException e) {
			logger.error(
					"Driver has been killed, however a WebDriverException has been caught: {}, (printing stack trace now)",
					e.getMessage());
			e.printStackTrace();
		}
		this.driver = driver;
	}

	/**
	 * <h>Public Mutator Method - Set Page</h>
	 * <p>
	 * This method sets the current page node to the argument string
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 */
	public void setPage(String page) {
		currentPage = page;
	}

	/**
	 * <h>Public Static Mutator Method - Set Short Wait</h>
	 * <p>
	 * This method sets the SHORT_WAIT time to the argument long.
	 * </p>
	 *
	 * @param shortWait wait time in milliseconds
	 */
	public static void setShortWait(long shortWait) {
		SHORT_WAIT = shortWait;
	}

	/**
	 * <h>Public Static Mutator Method - Set Medium Wait</h>
	 * <p>
	 * This method sets the MEDIUM_WAIT time to the argument long.
	 * </p>
	 *
	 * @param mediumWait wait time in milliseconds
	 */
	public static void setMediumWait(long mediumWait) {
		MEDIUM_WAIT = mediumWait;
	}

	/**
	 * <h>Public Static Mutator Method - Set Long Wait</h>
	 * <p>
	 * This method sets the LONG_WAIT time to the argument long.
	 * </p>
	 *
	 * @param longWait wait time in milliseconds
	 */
	public static void setLongWait(long longWait) {
		LONG_WAIT = longWait;
	}

	/**
	 * <h>Public Accessor Method - Get Driver</h>
	 * <p>
	 * This method returns the driver object
	 * </p>
	 *
	 * @return Selenium WebDriver parent object.
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * <h>Public Method - Get Screen Size</h>
	 * <p>
	 * This Method returns and caches the screen size of the device. The result is
	 * cached in {@link Sequoia#screenSize} to prevent redundant calls to the
	 * driver.
	 * </p>
	 *
	 * @return The screen dimension
	 */
	public Dimension getScreenSize() {
		return (screenSize == null) ? screenSize = driver.manage().window().getSize() : screenSize;
	}

	/**
	 * <h>Public Method - Get Page Source</h>
	 * <p>
	 * This method retrieves the page sources from the driver object and returns it
	 * in a string.
	 * </p>
	 *
	 * @return drivers page source in a single string.
	 */
	public String getPageSource() {
		return driver.getPageSource();
	}

	/**
	 * <h>Public Method - Get Page</h>
	 * <p>
	 * This method returns the currently active page node as a string.
	 * </p>
	 *
	 * @return active properties file page node
	 */
	public String getPage() {
		return currentPage;
	}

	/**
	 * <h>Public Method - Kill Driver</h>
	 * <p>
	 * This method kills the WebDriver by calling driver.quit and resets the current
	 * page node and screen size to null.
	 * </p>
	 */
	public void killDriver() {
		if (driver != null)
			driver.quit();
		currentPage = null;
		screenSize = null;
	}

	/**
	 * <h>Public Method - Refresh Screen Size</h>
	 * <p>
	 * Request the screen size from the driver. This will always request the driver
	 * and reset the cached {@link Sequoia#screenSize}
	 * </p>
	 *
	 * @return The screen dimension
	 */
	public Dimension refreshScreenSize() {
		return screenSize = driver.manage().window().getSize();
	}

	/**
	 * <h>Public Method - Wait For</h>
	 * <p>
	 * This method waits for a fraction of a second. This wait method is preferred
	 * to {@link Thread#sleep(long)} to avoid thread locking issues.
	 * </p>
	 */
	public void waitFor() {
		waitFor(SHORT_WAIT);
	}

	/**
	 * <h>Public Method - Wait For</h>
	 * <p>
	 * This method waits for the given amount of time in milliseconds. This wait
	 * method is preferred to {@link Thread#sleep(long)} to avoid thread locking
	 * issues.
	 * </p>
	 *
	 * @param milliseconds Time in milliseconds to wait for
	 */
	public void waitFor(long milliseconds) {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime <= milliseconds)
			;
	}

	/**
	 * <h>Public Method - Wait Until Available</h>
	 * <p>
	 * This method waits for the LONG_WAIT time for an element matching the
	 * arguments to be available.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of element, specified in the tree properties file
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilAvailable(String type, String name) {
		return waitUntilAvailable(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Available</h>
	 * <p>
	 * This method waits for the argument milliseconds for an element matching the
	 * arguments to be available.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilAvailable(String type, String name, long milliseconds) {
		return waitUntilAvailable(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Wait Until Available</h>
	 * <p>
	 * This method waits for the argument milliseconds for an element matching the
	 * arguments to be available.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilAvailable(String page, String type, String name) {
		return waitUntilAvailable(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Available</h>
	 * <p>
	 * This method waits for the argument milliseconds for an element matching the
	 * arguments to be available.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilAvailable(String page, String type, String name, long milliseconds) {
		By selector = getBySelector(getRawPropertyValue(page, type, name));
		return waitUntilAvailable(selector, milliseconds);
	}

	/**
	 * <h>Public Method - Wait Until Available</h>
	 * <p>
	 * This method waits for the argument milliseconds for an element matching the
	 * arguments to be available.
	 * </p>
	 *
	 * @param selector By object which is used to find the element.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilAvailable(By selector) {
		return waitUntilAvailable(selector, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Available</h>
	 * <p>
	 * This method waits for the argument milliseconds for an element matching the
	 * arguments to be available.
	 * </p>
	 *
	 * @param selector     By object which is used to find the element.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilAvailable(By selector, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				return driver.findElement(selector);
			} catch (WebDriverException e) {
				logger.debug("Element not found, checking again...");
				waitFor();
				message = e.getMessage();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Element not found: \"%s\" after %s seconds of waiting and %s attempts. Original Error Message: %s",
				selector, ((System.currentTimeMillis() - startTime) / 1000), attempts, message));

	}

	/**
	 * <h>Public Method - Wait Until Clickable</h>
	 * <p>
	 * Waits for the LONG_WAIT time for an element matching the arguments to be
	 * clickable.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilClickable(String type, String name) {
		return waitUntilClickable(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Clickable</h>
	 * <p>
	 * Waits for the LONG_WAIT time for an element matching the arguments to be
	 * clickable.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilClickable(String page, String type, String name) {
		return waitUntilClickable(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Clickable</h>
	 * <p>
	 * Waits for the for the argument milliseconds time for an element matching the
	 * arguments to be clickable.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilClickable(String type, String name, long milliseconds) {
		return waitUntilClickable(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Wait Until Clickable</h>
	 * <p>
	 * Waits for the for the argument milliseconds time for an element matching the
	 * arguments to be clickable.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilClickable(String page, String type, String name, long milliseconds) {
		String message = "no error found";
		boolean isDisplayException = false;
		boolean isEnabledException = false;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);

				if (element.isEnabled() && element.isDisplayed()) {
					return element;
				}

				isDisplayException = !element.isDisplayed();
				isEnabledException = !element.isEnabled();
			} catch (WebDriverException e) {
				message = e.getMessage();
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		if (isDisplayException && isEnabledException) {
			throw new WebDriverException(String.format(
					"The element was not displayed or enabled: \"%s\" after %s seconds of trying and %s attempts.",
					String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
					attempts));
		} else if (isDisplayException) {
			throw new ElementNotVisibleException(
					String.format("The element was not displayed: \"%s\" after %s seconds of trying and %s attempts.",
							String.format("%s, %s, %s", page, type, name),
							((System.currentTimeMillis() - startTime) / 1000), attempts));
		} else if (isEnabledException) {
			throw new DisabledElementException(
					String.format("The element was not enabled: \"%s\" after %s seconds of trying and %s attempts.",
							String.format("%s, %s, %s", page, type, name),
							((System.currentTimeMillis() - startTime) / 1000), attempts));
		} else {
			throw new NoSuchElementException(String.format(
					"No such element: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
					String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
					attempts, message));
		}
	}

	/**
	 * <h>Public Method - Wait Until Displayed</h>
	 * <p>
	 * Waits for the LONG_WAIT time for an element matching the arguments to be
	 * displayed.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilDisplayed(String type, String name) {
		return waitUntilDisplayed(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Displayed</h>
	 * <p>
	 * Waits for the LONG_WAIT time for an element matching the arguments to be
	 * displayed.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilDisplayed(String page, String type, String name) {
		return waitUntilDisplayed(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Wait Until Displayed</h>
	 * <p>
	 * Waits for the for the argument milliseconds time for an element matching the
	 * arguments to be displayed.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilDisplayed(String type, String name, long milliseconds) {
		return waitUntilDisplayed(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Wait Until Displayed</h>
	 * <p>
	 * Waits for the for the argument milliseconds time for an element matching the
	 * arguments to be displayed.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement waitUntilDisplayed(String page, String type, String name, long milliseconds) {
		String message = "no error found";
		boolean isDisplayException = false;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);

				if (element.isDisplayed()) {
					return element;
				}

				isDisplayException = !element.isDisplayed();
			} catch (WebDriverException e) {
				message = e.getMessage();
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		if (isDisplayException) {
			throw new ElementNotVisibleException(
					String.format("The element was not displayed: \"%s\" after %s seconds of trying and %s attempts.",
							String.format("%s, %s, %s", page, type, name),
							((System.currentTimeMillis() - startTime) / 1000), attempts));
		} else {
			throw new NoSuchElementException(String.format(
					"No such element: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
					String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
					attempts, message));
		}
	}

	/**
	 * Identify current page
	 * <p>
	 * Identify which page the driver is on based on which element is present. Put
	 * more likely pages first to save time! Assumes the page is on a stable state.
	 * <p>
	 * Typical use: {@code
	 * tree.setPage(tree.identifyPage(SequoiaElementKey...))
	 * }
	 *
	 * @param validationElements Elements to check if present
	 * @return Name of the page the driver is on
	 */
	public String identifyPage(SequoiaElementKey... validationElements) {
		return identifyPage(LONG_WAIT, validationElements);
	}

	/**
	 * <h>Public Method - Identify Page</h>
	 * <p>
	 * Identify which page the driver is on based on which element is present.
	 * </p>
	 *
	 * @param milliseconds       Length of time to retry in milliseconds until
	 *                           timing out.
	 * @param validationElements Elements to check if present
	 * @return Name of the page the driver is on
	 */
	public String identifyPage(long milliseconds, SequoiaElementKey... validationElements) {
		long startTime = System.currentTimeMillis();

		do {
			for (SequoiaElementKey validationElement : validationElements) {
				String[] temp;
				String validationMethod;
				String parameter = "";
				String page = validationElement.pageName;
				String type = validationElement.elementType;
				String name = validationElement.elementName;

				temp = getRawPropertyValue(page, type, name).split(":");

				validationMethod = temp[0];

				for (int index = 1; index < temp.length; ++index) {
					if (index == (temp.length - 1))
						parameter += temp[index];
					else
						parameter += temp[index] + ":";
				}

				logger.debug("Checking if we're on page {} by validation method: {}, validation parameter: {} ", page,
						validationMethod, parameter);

				if (validationMethod.equals("endpoint")) {
					if (validateEndpoint(parameter, 0)) {
						return page;
					}
				} else if (validationMethod.equals("title")) {
					if (validateTitle(parameter, 0)) {
						return page;
					}
				} else if (validationMethod.equals("id") || validationMethod.equals("css")
						|| validationMethod.equals("xpath") || validationMethod.equals("linktext")) {
					if (validateBySelector(getBySelector(page, type, name), 0)) {
						return page;
					}
				} else if (validationMethod.equals("url")) {
					if (validateUrl(parameter, 0)) {
						return page;
					}
				} else {
					throw new InvalidSelectorException(String.format("No such selector as %s", validationMethod));
				}
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new InvalidPageException(
				"Could not identify the current page name from " + Arrays.toString(validationElements));
	}

	/**
	 * <h>Public Method - Identify Page</h>
	 * <p>
	 * Identify which page the driver is on based on which element is present.
	 * </p>
	 *
	 * @param pageNames Array of pageNames as String values.
	 * @return Name of page the driver is currently on.
	 */
	public String identifyPage(String... pageNames) {
		return identifyPage(LONG_WAIT, pageNames);
	}

	/**
	 * <h>Public Method - Identify Page</h>
	 * <p>
	 * Identify which page the driver is on based on which element is present.
	 * </p>
	 *
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param pageNames    Array of pageNames as String values.
	 * @return Name of page the driver is currently on.
	 */
	public String identifyPage(long milliseconds, String... pageNames) {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < milliseconds) {
			for (String pageName : pageNames) {
				if (validate((pageName), 0)) {
					logger.debug("Page identified as \"{}\"", pageName);
					return pageName;
				}
			}
		}
		throw new InvalidPageException("Could not identify the current page name from " + Arrays.toString(pageNames));
	}

	/**
	 * <h>Public Method - Validate</h>
	 * <p>
	 * Validates the driver is on the page using the default validation method
	 * specified in the properties file.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the specified page.
	 * </p>
	 *
	 * @param pageName name of the page to validate the driver is on.
	 * @return True if the driver is on the page, False otherwise.
	 */
	public boolean validate(String pageName) {
		return validate(pageName, LONG_WAIT);
	}

	// TODO: This was copy and pasted from the previous validate method. no
	// guarantees on proper functionality.

	/**
	 * <h>Public Method - Validate</h>
	 * <p>
	 * Validates the driver is on the page using the default validation method
	 * specified in the properties file.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the specified page.
	 * </p>
	 *
	 * @param pageName     name of the page to validate the driver is on.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the driver is on the page, False otherwise.
	 */
	public boolean validate(String pageName, long milliseconds) {
		logger.debug("validating that we are on the {} page...", pageName);

		String[] temp;

		if (treeRoot != null) {
			temp = tree.getString(String.format("%s.%s.validate", treeRoot, pageName)).split(":");
		} else {
			temp = tree.getString(String.format("%s.validate", pageName)).split(":");
		}

		String validationMethod = temp[0], parameter = "";

		for (int index = 1; index < temp.length; ++index) {
			if (index == (temp.length - 1))
				parameter += temp[index];
			else
				parameter += temp[index] + ":";
		}

		logger.debug("validation method: {}, validation parameter: {} ", validationMethod, parameter);

		try {
			if (validationMethod.equals("endpoint"))
				return validateEndpoint(parameter, milliseconds);
			else if (validationMethod.equals("title"))
				return validateTitle(parameter, milliseconds);
			else if (validationMethod.equals("id"))
				return validateBySelector(By.id(parameter), milliseconds);
			else if (validationMethod.equals("css"))
				return validateBySelector(By.cssSelector(parameter), milliseconds);
			else if (validationMethod.equals("xpath"))
				return validateBySelector(By.xpath(parameter), milliseconds);
			else if (validationMethod.equals("linktext"))
				return validateBySelector(By.linkText(parameter), milliseconds);
			else if (validationMethod.equals("url"))
				return validateUrl(parameter, milliseconds);
			else {
				throw new InvalidSelectorException(String.format("No such selector as %s", validationMethod));
			}
		} catch (Exception e) {
			logger.debug("Returning false because: {}...", e.getMessage());
			return false;
		}
	}

	/**
	 * <h>Private Method - Validate By Selector</h>
	 * <p>
	 * Validates the driver is on the page by checking if the specified By selector
	 * is present on the page.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the specified page.
	 * </p>
	 *
	 * @param selector By object which is used to find the element.
	 * @return True if the By selector is present on the page, False otherwise.
	 */
	private boolean validateBySelector(By selector) {
		return validateBySelector(selector, LONG_WAIT);
	}

	/**
	 * <h>Private Method - Validate By Selector</h>
	 * <p>
	 * Validates the driver is on the page by checking if the specified By selector
	 * is present on the page.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the specified page.
	 * </p>
	 *
	 * @param selector     By object which is used to find the element.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the By selector is present on the page, False otherwise.
	 */
	private boolean validateBySelector(By selector, long milliseconds) {
		try {
			getElementBySelector(selector, milliseconds);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * <h>Private Method - Get Element By Selector</h>
	 * <p>
	 * Returns the element found using the specified By selector.
	 * </p>
	 *
	 * @param selector By object which is used to find the element.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	private WebElement getElementBySelector(By selector) {
		return getElementBySelector(selector, LONG_WAIT);
	}

	/**
	 * <h>Private Method - Get Element By Selector</h>
	 * <p>
	 * Returns the element found using the specified By selector.
	 * </p>
	 *
	 * @param selector     By object which is used to find the element.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	private WebElement getElementBySelector(By selector, long milliseconds) {
		String message;
		long attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				WebElement element = driver.findElement(selector);
				return element;
			} catch (WebDriverException e) {
				waitFor();
				message = e.getMessage();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Element not found: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				selector.toString(), ((System.currentTimeMillis() - startTime) / 1000), attempts, message));
	}

	/**
	 * <h>Protected Method - Validate Endpoint</h>
	 * <p>
	 * Validates the driver is on the page by checking if the page has the specified
	 * endpoint.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the specified
	 * endpoint.
	 * </p>
	 *
	 * @param endpoint String value of the endpoint
	 * @return True if the page has the specified endpoint, False otherwise.
	 */
	protected boolean validateEndpoint(String endpoint) {
		return validateEndpoint(endpoint, LONG_WAIT);
	}

	/**
	 * <h>Protected Method - Validate Endpoint</h>
	 * <p>
	 * Validates the driver is on the page by checking if the page has the specified
	 * endpoint.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the specified
	 * endpoint.
	 * </p>
	 *
	 * @param endpoint     String value of the endpoint
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the page has the specified endpoint, False otherwise.
	 */
	protected boolean validateEndpoint(String endpoint, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (driver.getCurrentUrl().endsWith(endpoint)) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Protected Method - Validate Title</h>
	 * <p>
	 * Validates the driver is on the page by checking if the page has the specified
	 * title.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the page with the
	 * specified title.
	 * </p>
	 *
	 * @param title String value of the title
	 * @return True if the page has the specified title, False otherwise.
	 */
	protected boolean validateTitle(String title) {
		return validateTitle(title, LONG_WAIT);
	}

	/**
	 * <h>Protected Method - Validate Title</h>
	 * <p>
	 * Validates the driver is on the page by checking if the page has the specified
	 * title.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the page with the
	 * specified title.
	 * </p>
	 *
	 * @param title        String value of the title
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the page has the specified title, False otherwise.
	 */
	protected boolean validateTitle(String title, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (driver.getTitle().toLowerCase().contains(title.toLowerCase())) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Protected Method - Validate Url</h>
	 * <p>
	 * Validates the driver is on the page by checking if the page has the specified
	 * url.
	 * </p>
	 * <p>
	 * Returns a boolean value indicating if the driver is on the page with the
	 * specified title.
	 * </p>
	 *
	 * @param url          String value of the url
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the page has the specified title, False otherwise.
	 */
	protected boolean validateUrl(String url, long milliseconds) {
		long startTime = System.currentTimeMillis();
		String currentUrl;

		logger.debug("Looking for url: {}", url);

		do {
			currentUrl = driver.getCurrentUrl();

			if (currentUrl.equals(url)) {
				logger.debug("Current url: {}", currentUrl);
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		logger.debug("Current url: {}", currentUrl);
		return false;
	}

	/**
	 * <h>Public Method - Is Attribute Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is present. This method will wait for the LONG_WAIT
	 * time for the attribute to be present.
	 * </p>
	 *
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of element, specified in the tree properties file
	 * @param attribute Attribute such as href, class, etc.
	 * @return True if the attribute is present on the page, False otherwise.
	 */
	public boolean isAttributePresent(String type, String name, String attribute) {
		return isAttributePresent(currentPage, type, name, attribute, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is present. This method will wait for the LONG_WAIT
	 * time for the attribute to be present.
	 * </p>
	 *
	 * @param page      Page node where the specified element should be in the
	 *                  properties file.
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param attribute Attribute such as href, class, etc.
	 * @param name      Name of the item, specified in the tree properties file.
	 * @return True if the attribute is present on the page, False otherwise.
	 */
	public boolean isAttributePresent(String page, String type, String attribute, String name) {
		return isAttributePresent(page, type, name, attribute, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is present. This method will wait for the argument
	 * milliseconds time for the attribute to be present.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param attribute    Attribute such as href, class, etc.
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if the attribute is present on the page, False otherwise.
	 */
	public boolean isAttributePresent(String type, String name, String attribute, long milliseconds) {
		return isAttributePresent(currentPage, type, name, attribute, milliseconds);
	}

	/**
	 * <h>Public Method - Is Attribute Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is present. This method will wait for the LONG_WAIT
	 * time for the attribute to be present.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param attribute    Attribute such as href, class, etc.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the attribute is present on the page, False otherwise.
	 */
	public boolean isAttributePresent(String page, String type, String name, String attribute, long milliseconds) {
		logger.debug("Checking if {}.{}.{} value attribute {} exists", page, type, name, attribute);
		long startTime = System.currentTimeMillis();

		do {
			try {
				waitUntilAvailable(page, type, name, milliseconds).getAttribute(attribute);
				return true;
			} catch (WebDriverException | NullPointerException | DisabledElementException e) {
				logger.debug("Attribute does not exist...");
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Attribute Not Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is not present. This method will wait for the
	 * LONG_WAIT time for the attribute to be no longer be present.
	 * </p>
	 *
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of element, specified in the tree properties file
	 * @param attribute Attribute such as href, class, etc.
	 * @return True if the attribute is not present on the page, False otherwise.
	 */
	public boolean isAttributeNotPresent(String type, String name, String attribute) {
		return isAttributeNotPresent(currentPage, type, name, attribute, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Not Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is not present. This method will wait for the
	 * LONG_WAIT time for the attribute to be no longer be present.
	 * </p>
	 *
	 * @param page      Page node where the specified element should be in the
	 *                  properties file.
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of the item, specified in the tree properties file.
	 * @param attribute Attribute such as href, class, etc.
	 * @return True if the attribute is not present on the page, False otherwise.
	 */
	public boolean isAttributeNotPresent(String page, String type, String name, String attribute) {
		return isAttributeNotPresent(page, type, name, attribute, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Not Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is not present. This method will wait for the argument
	 * milliseconds time for the attribute to be no longer be present.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param attribute    Attribute such as href, class, etc.
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if the attribute is not present on the page, False otherwise.
	 */
	public boolean isAttributeNotPresent(String type, String name, String attribute, long milliseconds) {
		return isAttributeNotPresent(currentPage, type, name, attribute, milliseconds);
	}

	/**
	 * <h>Public Method - Is Attribute Not Present</h>
	 * <p>
	 * This method will return a boolean indicating whether or not an attribute
	 * matching the arguments is not present. This method will wait for the
	 * LONG_WAIT time for the attribute to be no longer be present.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param attribute    Attribute such as href, class, etc.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the attribute is not present on the page, False otherwise.
	 */
	public boolean isAttributeNotPresent(String page, String type, String name, String attribute, long milliseconds) {
		logger.debug("Checking if {}.{}.{} value attribute {} exists", page, type, name, attribute);

		long startTime = System.currentTimeMillis();

		do {
			try {
				waitUntilAvailable(page, type, name, milliseconds).getAttribute(attribute);
			} catch (NullPointerException | DisabledElementException | WebDriverException e) {
				logger.debug("Attribute does not exist...");
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Attribute Value Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is not present inside the argument attribute. It will wait for the
	 * LONG_WAIT time for the value to no longer be not present.
	 * </p>
	 *
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of element, specified in the tree properties file
	 * @param attribute Attribute such as href, class, etc.
	 * @param value     Expected value inside the attribute
	 * @return True if the attribute value is present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueContained(String type, String name, String attribute, String value) {
		return isAttributeValueContained(currentPage, type, name, attribute, value, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Value Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is not present inside the argument attribute. It will wait for the
	 * LONG_WAIT time for the value to no longer be not present.
	 * </p>
	 *
	 * @param page      Page node where the specified element should be in the
	 *                  properties file.
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of the item, specified in the tree properties file.
	 * @param attribute Attribute such as href, class, etc.
	 * @param value     Expected value inside the attribute
	 * @return True if the attribute value is present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueContained(String page, String type, String name, String attribute, String value) {
		return isAttributeValueContained(page, type, name, attribute, value, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Value Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is not present inside the argument attribute. It will wait for the
	 * argument milliseconds for the value to no longer be not present.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param attribute    Attribute such as href, class, etc.
	 * @param value        Expected value inside the attribute
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if the attribute value is present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueContained(String type, String name, String attribute, String value,
			long milliseconds) {
		return isAttributeValueContained(currentPage, type, name, attribute, value, milliseconds);
	}

	/**
	 * <h>Public Method - Is Attribute Value Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is not present inside the argument attribute. It will wait for the
	 * LONG_WAIT time for the value to no longer be not present.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param attribute    Attribute such as href, class, etc.
	 * @param value        Expected value inside the attribute
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the attribute value is present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueContained(String page, String type, String name, String attribute, String value,
			long milliseconds) {
		logger.debug("Checking if {} value attribute {} exists", formatKey(page, type, name), attribute);

		long startTime = System.currentTimeMillis();

		do {
			try {
				if (waitUntilAvailable(page, type, name, milliseconds).getAttribute(attribute).contains(value)) {
					return true;
				}
			} catch (NullPointerException | DisabledElementException | WebDriverException e) {
				logger.debug("Attribute does not exist...");
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Attribute Value Not Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is present inside the argument attribute. It will wait for the argument
	 * milliseconds for the value to no longer be present.
	 * </p>
	 *
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of element, specified in the tree properties file
	 * @param attribute Attribute such as href, class, etc.
	 * @param value     Value that shouldn't be inside the attribute
	 * @return True if the attribute value is not present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueNotContained(String type, String name, String attribute, String value) {
		return isAttributeValueNotContained(currentPage, type, name, attribute, value, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Value Not Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is present inside the argument attribute. It will wait for the argument
	 * milliseconds for the value to no longer be present.
	 * </p>
	 *
	 * @param page      Page node where the specified element should be in the
	 *                  properties file.
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of the item, specified in the tree properties file.
	 * @param attribute Attribute such as href, class, etc.
	 * @param value     Value that shouldn't be inside the attribute
	 * @return True if the attribute value is not present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueNotContained(String page, String type, String name, String attribute, String value) {
		return isAttributeValueNotContained(page, type, name, attribute, value, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Attribute Value Not Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is present inside the argument attribute. It will wait for the argument
	 * milliseconds for the value to no longer be present.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param attribute    Attribute such as href, class, etc.
	 * @param value        Value that shouldn't be inside the attribute
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if the attribute value is not present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueNotContained(String type, String name, String attribute, String value,
			long milliseconds) {
		return isAttributeValueNotContained(currentPage, type, name, attribute, value, milliseconds);
	}

	/**
	 * <h>Public Method - Is Attribute Value Not Contained</h>
	 * <p>
	 * This method will return a boolean indicating whether or not the argument
	 * value is present inside the argument attribute. It will wait for the argument
	 * milliseconds for the value to no longer be present.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param attribute    Attribute such as href, class, etc.
	 * @param value        Value that shouldn't be inside the attribute
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the attribute value is not present in the argument attribute,
	 *         otherwise False
	 */
	public boolean isAttributeValueNotContained(String page, String type, String name, String attribute, String value,
			long milliseconds) {
		logger.debug("Checking if {}.{}.{} value attribute {} exists", page, type, name, attribute);
		long startTime = System.currentTimeMillis();

		do {
			try {
				if (!waitUntilAvailable(page, type, name, milliseconds).getAttribute(attribute).contains(value)) {
					return true;
				}
			} catch (NullPointerException | DisabledElementException | WebDriverException e) {
				logger.debug("Attribute does not exist...");
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Get Attribute</h>
	 * <p>
	 * This method returns the value inside the attribute matching the arguments.
	 * This will wait for the LONG_WAIT duration for the attribute to be present
	 * before returning the value.
	 * </p>
	 *
	 * @param type      Type of the element, specified in the tree properties file.
	 * @param name      Name of element, specified in the tree properties file
	 * @param attribute Attribute such as href, class, etc.
	 * @return the value of the attribute
	 */
	public String getAttribute(String type, String name, String attribute) {
		return getAttribute(type, name, attribute, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Get Attribute</h>
	 * <p>
	 * This method returns the value inside the attribute matching the arguments.
	 * This will wait for the argument milliseconds duration for the attribute to be
	 * present before returning the value.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param attribute    Attribute such as href, class, etc.
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return the value of the attribute
	 */
	public String getAttribute(String type, String name, String attribute, long milliseconds) {
		return getAttribute(currentPage, type, name, attribute, milliseconds);
	}

	/**
	 * <h>Public Method - Get Attribute</h>
	 * <p>
	 * This method returns the value inside the attribute matching the arguments.
	 * This will wait for the LONG_WAIT duration for the attribute to be present
	 * before returning the value.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param attribute    Attribute such as href, class, etc.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return the value of the attribute
	 */
	public String getAttribute(String page, String type, String name, String attribute, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Trying to get attribute \"{}\" from element: {}={}", attribute,
						formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);

				return element.getAttribute(attribute);
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Get \"%s\"attribute failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				attribute, formatKey(page, type, name), ((System.currentTimeMillis() - startTime) / 1000), attempts,
				message));
	}

	/**
	 * <h>Public Method - Is Present</h>
	 * <p>
	 * Test whether an element identified by the locator is present, using wait time
	 * specified in parameter.
	 * </p>
	 *
	 * @param locator      locator in format bySelectorType:bySelectorParameter
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is present on page, False otherwise
	 */
	public boolean isPresent(String locator, long milliseconds) {
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				driver.findElement(getBySelector(locator));
				logger.debug("Found element, return true. Number of times checked: {}", attempts);
				return true;
			} catch (WebDriverException e) {
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		logger.debug("Element still not present! Returning false. Number of times checked: {}", attempts);
		return false;
	}

	/**
	 * <h>Public Method - Is Present</h>
	 * <p>
	 * Test whether an element identified by the locator string is present, using
	 * default wait time.
	 * </p>
	 *
	 * @param locator locator in format bySelectorType:bySelectorParameter
	 * @return True if element is present on page, False otherwise
	 */
	public boolean isPresent(String locator) {
		return isPresent(locator, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Present</h>
	 * <p>
	 * This method searches for whether or not an element matching the arguments is
	 * present on the current page. It will continue searching for the element for
	 * the argument milliseconds duration.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if element is present on page, False otherwise
	 */
	public boolean isPresent(String page, String type, String name, long milliseconds) {
		final String elementKey = formatKey(page, type, name);
		final String locator = getRawPropertyValue(page, type, name);
		logger.debug("Checking for presence of {}={}...", elementKey, locator);
		return isPresent(locator, milliseconds);
	}

	/**
	 * <h>Public Method - Is Present</h>
	 * <p>
	 * This method searches for whether or not an element matching the arguments is
	 * present on the current page. It will continue searching for the element for
	 * the LONG_WAIT duration.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of element, specified in the tree properties file
	 * @return True if element is present on page, False otherwise
	 */
	public boolean isPresent(String type, String name) {
		return isPresent(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Present</h>
	 * <p>
	 * This method searches for whether or not an element matching the arguments is
	 * present on the current page. It will continue searching for the element for
	 * the argument milliseconds duration.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if element is present on page, False otherwise
	 */
	public boolean isPresent(String type, String name, long milliseconds) {
		return isPresent(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Present</h>
	 * <p>
	 * This method searches for whether or not an element matching the arguments is
	 * present on the current page. It will continue searching for the element for
	 * the LONG_WAIT duration.
	 * </p>
	 *
	 * @param page Page element is in, specified in the tree properties file
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of element, specified in the tree properties file
	 * @return True if element is present on page, False otherwise
	 */
	public boolean isPresent(String page, String type, String name) {
		return isPresent(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Present</h>
	 * <p>
	 * Check and wait for an element not to be present. If you expect an element to
	 * be present, but then disappear, use this method to wait until it is not
	 * present any longer.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if element is not present on page, False otherwise
	 */
	public boolean isNotPresent(String page, String type, String name, long milliseconds) {
		final String elementProperty = formatKey(page, type, name);
		final String locator = getRawPropertyValue(page, type, name);
		logger.debug("Checking for absence of {}={}", elementProperty, locator);

		long startTime = System.currentTimeMillis();
		int attempts = 0;

		do {
			try {
				++attempts;
				driver.findElement(getBySelector(locator));
			} catch (NoSuchElementException e) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		logger.debug("Element still present! Returning false. Number of times checked: {}", attempts);
		return false;
	}

	/**
	 * <h>Public Method - Is Not Present</h>
	 * <p>
	 * Check and wait for an element not to be present. If you expect an element to
	 * be present, but then disappear, use this method to wait until it is not
	 * present any longer.
	 * </p>
	 *
	 * @param page Page element is in, specified in the tree properties file
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of element, specified in the tree properties file
	 * @return True if element is not present on page, False otherwise
	 */
	public boolean isNotPresent(String page, String type, String name) {
		return isNotPresent(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Present</h>
	 * <p>
	 * Check and wait for an element not to be present. If you expect an element to
	 * be present, but then disappear, use this method to wait until it is not
	 * present any longer.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of element, specified in the tree properties file
	 * @return True if element is not present on page, False otherwise
	 */
	public boolean isNotPresent(String type, String name) {
		return isNotPresent(currentPage, type, name);
	}

	/**
	 * <h>Public Method - Is Not Present</h>
	 * <p>
	 * Check and wait for an element not to be present. If you expect an element to
	 * be present, but then disappear, use this method to wait until it is not
	 * present any longer.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of element, specified in the tree properties file
	 * @param milliseconds Length of time to retry in milliseconds until timing out
	 * @return True if element is not present on page, False otherwise
	 */
	public boolean isNotPresent(String type, String name, long milliseconds) {
		return isNotPresent(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Find Elements</h>
	 * <p>
	 * This method finds multiple elements on the page. This method will search for
	 * up to the LONG_WAIT time
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element set
	 * @return The list of elements found
	 */
	public List<WebElement> findElements(String type, String name) {
		return findElements(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Find Elements</h>
	 * <p>
	 * This method finds multiple elements on the page. This method will search for
	 * up to the LONG_WAIT time
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return The list of elements found
	 */
	public List<WebElement> findElements(String page, String type, String name) {
		return findElements(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Find Elements</h>
	 * <p>
	 * This method finds multiple elements on the page. This method will search for
	 * up to the specified milliseconds.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return The list of elements found
	 */
	public List<WebElement> findElements(String type, String name, long milliseconds) {
		return findElements(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Find Elements</h>
	 * <p>
	 * This method finds multiple elements on the page. This method will search for
	 * up to the specified milliseconds.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return The list of elements found
	 */
	public List<WebElement> findElements(String page, String type, String name, long milliseconds) {
		logger.debug("Getting list of elements found by {}", formatKey(page, type, name));

		List<WebElement> elements = null;
		long startTime = System.currentTimeMillis();

		do {
			elements = driver.findElements(getBySelector(page, type, name));

			if (!elements.isEmpty()) {
				return elements;
			} else {
				logger.debug("Elements not found, checking again...");
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return elements;
	}

	/**
	 * <h>Public Method - Get Raw Property Value</h> Retrieve the raw text value of
	 * a property.
	 * <p>
	 * Retrieve the raw text value of a property, based on the current page.
	 * </p>
	 * <p>
	 * Note: "text" differs from "statictext" as a type as "text" simply holds a raw
	 * string value while "statictext" holds an identifier for a text UI element.
	 * </p>
	 *
	 * @param page Page the element property is specified under
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return The string value of that property.
	 */
	public String getRawPropertyValue(String page, String type, String name) {
		return tree.getString(formatKey(page, type, name));
	}

	/**
	 * <h>Public Method - Get Raw Property Value</h>
	 * <p>
	 * Retrieve the raw text value of a property, based on the current page.
	 * </p>
	 * <p>
	 * Note: "text" differs from "statictext" as a type as "text" simply holds a raw
	 * string value while "statictext" holds an identifier for a text UI element.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return The string value of that property.
	 */
	public String getRawPropertyValue(String type, String name) {
		return getRawPropertyValue(currentPage, type, name);
	}

	/**
	 * <h>Public Method - Take Screenshot</h>
	 * <p>
	 * This method captures a screen shot of the device screen
	 * </p>
	 *
	 * @return The screenshot image in bytes
	 */
	public byte[] takeScreenshot() {
		logger.debug("Taking screenshot");
		byte[] srcFile = ((RemoteWebDriver) driver).getScreenshotAs(OutputType.BYTES);
		logger.debug("Finished taking screenshot");
		return srcFile;
	}

	/**
	 * <h>Protected Method - Extract Selector Type</h>
	 * <p>
	 * Returns selector type String value from locator String
	 * </p>
	 *
	 * @param rawSelectorPlusParameter locator in format
	 *                                 bySelectorType:bySelectorParameter
	 * @return String value of bySelectorType
	 */
	protected String extractSelectorType(String rawSelectorPlusParameter) {
		StringBuilder b = new StringBuilder(rawSelectorPlusParameter);
		String selector;

		try {
			selector = b.toString().substring(0, b.indexOf(":"));
		} catch (StringIndexOutOfBoundsException e) {
			throw new NoSelectorException(
					String.format("There was a problem parsing the selector %s.", rawSelectorPlusParameter));
		}
		return selector;
	}

	/**
	 * <h>Protected Method - Extract Selector Parameter</h>
	 * <p>
	 * Returns selector parameter String value from locator String
	 * </p>
	 *
	 * @param rawSelectorPlusParameter locator in format
	 *                                 bySelectorType:bySelectorParameter
	 * @return String value of bySelectorParameter
	 */
	protected String extractSelectorParameter(String rawSelectorPlusParameter) {
		StringBuilder b = new StringBuilder(rawSelectorPlusParameter);
		String parameter;

		// +1 to ignore the `:`
		parameter = b.toString().substring(b.indexOf(":") + 1);

		return parameter;
	}

	/**
	 * <h>Protected Helper Method - Get By Selector</h>
	 * <p>
	 * This method returns the by selector of the properties file element,
	 * correlating to the argument
	 * </p>
	 *
	 * @param selectorPlusParameter this string represents a key value pair of a
	 *                              selector type, such as "id", and the value
	 *                              represented by the selector type.
	 * @return By selector of the specified element in the properties file.
	 */
	protected By getBySelector(String selectorPlusParameter) {
		String parameter = extractSelectorParameter(selectorPlusParameter);
		String selector = extractSelectorType(selectorPlusParameter);
		switch (selector) {
		case "tag":
			return By.tagName(parameter);
		case "class":
			return By.className(parameter);
		case "css":
			return By.cssSelector(parameter);
		case "id":
			return By.id(parameter);
		case "linktext":
			return By.linkText(parameter);
		case "name":
			return By.name(parameter);
		case "partiallink":
			return By.partialLinkText(parameter);
		case "xpath":
			return By.xpath(parameter);
		default:
			throw new InvalidSelectorException(selector + " is an invalid selector! Could not create a By object.");
		}
	}

	/**
	 * <h>Public Method - Switch To Alert</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to an alert
	 * window (if present).
	 * </p>
	 *
	 * @return Selenium method call to switch the current window handle to an alert
	 *         window.
	 */
	public Alert switchToAlert() {
		return switchToAlert(LONG_WAIT);
	}

	/**
	 * <h>Public Method - Switch To Alert</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to an alert
	 * window (if present).
	 * </p>
	 *
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Selenium method call to switch the current window handle to an alert
	 *         window.
	 */
	public Alert switchToAlert(long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				logger.debug("Switching to alert");

				return driver.switchTo().alert();
			} catch (UnreachableBrowserException | NoAlertPresentException e) {
				e.printStackTrace();
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoAlertPresentException(
				String.format("Unable to switch to Alert after %s and %s attemps. Original Error Message: %s ",
						((System.currentTimeMillis() - startTime) / 1000), attempts, message));
	}

	/**
	 * <h>Public Helper Method - Get By Selector</h>
	 * <p>
	 * This method returns the by selector of the properties file element,
	 * correlating to the arguments
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return By selector of the specified element in the properties file.
	 */
	public By getBySelector(String type, String name) {
		return getBySelector(getRawPropertyValue(currentPage, type, name));
	}

	/**
	 * <h>Public Helper Method - Get By Selector</h>
	 * <p>
	 * This method returns the by selector of the properties file element,
	 * correlating to the arguments
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return By selector of the specified element in the properties file.
	 */
	public By getBySelector(String page, String type, String name) {
		return getBySelector(getRawPropertyValue(page, type, name));
	}

	/**
	 * <h>Protected Helper Method - Format Key</h>
	 * <p>
	 * Format the element property key to be used for querying the tree.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return The property key string
	 */
	protected String formatKey(String page, String type, String name) {
		if (treeRoot == null)
			return String.format("%s.%s.%s", page, type, name);
		else
			return String.format("%s.%s.%s.%s", treeRoot, page, type, name);
	}

	/**
	 * <h>Protected Helper Method - Format Key</h>
	 * <p>
	 * Format the element property key to be used for querying the tree.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return The property key string
	 */
	protected String formatKey(String type, String name) {
		return formatKey(currentPage, type, name);
	}

	/**
	 * <h>Public Method - Driver Status</h>
	 * <p>
	 * Returns a String value indicating if a driver has been instantiated or not.
	 * </p>
	 *
	 * @return "active" if not null, otherwise "inactive"
	 */
	public String driverStatus() {
		return driver == null ? "inactive" : "active";
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @param keys Keys object which is used to specify which keys are to be typed
	 *             into the input.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement input(String type, String name, Keys keys) {
		return input(currentPage, type, name, keys, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @param keys Keys object which is used to specify which keys are to be typed
	 *             into the input.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement input(String page, String type, String name, Keys keys) {
		return input(page, type, name, keys, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param keys         Keys object which is used to specify which keys are to be
	 *                     typed into the input.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement input(String type, String name, Keys keys, long milliseconds) {
		return input(currentPage, type, name, keys, milliseconds);
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * This method enters text into and returns a web element, if available,
	 * matching the type and name of the arguments against the properties file. This
	 * method will wait the LONG_WAIT time in milliseconds for the element to be
	 * found.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param keys         Keys object which is used to specify which keys are to be
	 *                     typed into the input.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement or Child given the By selector specified in the properties
	 *         file correlating to the arguments.
	 */
	public WebElement input(String page, String type, String name, Keys keys, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				logger.debug("Trying type in to {}={}", formatKey(page, type, name),
						getRawPropertyValue(page, type, name));
				WebElement onElement = waitUntilAvailable(page, type, name, milliseconds);
				onElement.sendKeys(keys);
				return onElement;
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Element not found for input: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	/**
	 * <h>Public Method - Is Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is displayed.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is displayed, False otherwise.
	 */
	public boolean isDisplayed(String type, String name) {
		return isDisplayed(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is displayed.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is displayed, False otherwise.
	 */
	public boolean isDisplayed(String type, String name, long milliseconds) {
		return isDisplayed(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is displayed.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is displayed, False otherwise.
	 */
	public boolean isDisplayed(String page, String type, String name) {
		return isDisplayed(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is displayed.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is displayed, False otherwise.
	 */
	public boolean isDisplayed(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (waitUntilAvailable(page, type, name, milliseconds).isDisplayed()) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Not Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not displayed.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is not displayed, False otherwise.
	 */
	public boolean isNotDisplayed(String type, String name) {
		return isNotDisplayed(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not displayed.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is not displayed, False otherwise.
	 */
	public boolean isNotDisplayed(String type, String name, long milliseconds) {
		return isNotDisplayed(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Not Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not displayed.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is not displayed, False otherwise.
	 */
	public boolean isNotDisplayed(String page, String type, String name) {
		return isNotDisplayed(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Displayed</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not displayed.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is not displayed, False otherwise.
	 */
	public boolean isNotDisplayed(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (!waitUntilAvailable(page, type, name, milliseconds).isDisplayed()) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is enabled.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is enabled, False otherwise.
	 */
	public boolean isEnabled(String type, String name) {
		return isEnabled(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is enabled.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is enabled, False otherwise.
	 */
	public boolean isEnabled(String type, String name, long milliseconds) {
		return isEnabled(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is enabled.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is enabled, False otherwise.
	 */
	public boolean isEnabled(String page, String type, String name) {
		return isEnabled(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is enabled.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is enabled, False otherwise.
	 */
	public boolean isEnabled(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (waitUntilAvailable(page, type, name, milliseconds).isEnabled()) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Not Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not enabled.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is not enabled, False otherwise.
	 */
	public boolean isNotEnabled(String type, String name) {
		return isNotEnabled(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not enabled.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is not enabled, False otherwise.
	 */
	public boolean isNotEnabled(String type, String name, long milliseconds) {
		return isNotEnabled(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Not Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not enabled.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is not enabled, False otherwise.
	 */
	public boolean isNotEnabled(String page, String type, String name) {
		return isNotEnabled(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Enabled</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not enabled.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is not enabled, False otherwise.
	 */
	public boolean isNotEnabled(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (!waitUntilAvailable(page, type, name, milliseconds).isEnabled()) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is selected.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is selected, False otherwise.
	 */
	public boolean isSelected(String type, String name) {
		return isSelected(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is selected.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is selected, False otherwise.
	 */
	public boolean isSelected(String type, String name, long milliseconds) {
		return isSelected(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is selected.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is selected, False otherwise.
	 */
	public boolean isSelected(String page, String type, String name) {
		return isSelected(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is selected.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is selected, False otherwise.
	 */
	public boolean isSelected(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (waitUntilAvailable(page, type, name, milliseconds).isSelected()) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is Not Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not selected.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is not selected, False otherwise.
	 */
	public boolean isNotSelected(String type, String name) {
		return isNotSelected(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not selected.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is not selected, False otherwise.
	 */
	public boolean isNotSelected(String type, String name, long milliseconds) {
		return isNotSelected(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Is Not Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not selected.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the item, specified in the tree properties file.
	 * @return True if element is not selected, False otherwise.
	 */
	public boolean isNotSelected(String page, String type, String name) {
		return isNotSelected(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not Selected</h>
	 * <p>
	 * Returns a boolean value indicating if the element is not selected.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the item, specified in the tree properties file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if element is not selected, False otherwise.
	 */
	public boolean isNotSelected(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (!waitUntilAvailable(page, type, name, milliseconds).isSelected()) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Get Actions</h>
	 * <p>
	 * Returns the Actions available to the driver
	 * </p>
	 *
	 * @return Actions object of the driver
	 */
	public Actions getActions() {
		return new Actions(driver);
	}

	/**
	 * <h>Public Method - Execute Script</h>
	 * <p>
	 * Executes a specified JS script
	 * </p>
	 *
	 * @param script String value of the JS script to be executed
	 * @return JavascriptExecutor that runs the specified script
	 */
	public Object executeScript(String script) {
		logger.debug("Attempting to execute script \n{}", script);
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		return jse.executeScript(script);
	}

	/**
	 * <h>Public Method - Execute Script</h>
	 * <p>
	 * Executes a specified JS script
	 * </p>
	 *
	 * @param script  String value of the JS script to be executed
	 * @param objects Array of Objects to be passed into the JavascriptExecutor
	 * @return JavascriptExecutor that runs the specified script
	 */
	public Object executeScript(String script, Object... objects) {
		logger.debug("Attempting to execute script \n{}", script);
		JavascriptExecutor jse = ((JavascriptExecutor) driver);
		return jse.executeScript(script, objects);
	}

	/**
	 * <h>Public Method - Is Page Ready State</h>
	 * <p>
	 * Returns a boolean value indicating if the page is in the ready state.
	 * </p>
	 *
	 * @return True if the page is in the ready state, False otherwise.
	 */
	public boolean isPageReadyState() {
		return isPageReadyState(LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Page Ready State</h>
	 * <p>
	 * Returns a boolean value indicating if the page is in the ready state.
	 * </p>
	 *
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the page is in the ready state, False otherwise.
	 */
	public boolean isPageReadyState(long milliseconds) {
		logger.debug("Checking document.readyState");
		long startTime = System.currentTimeMillis();

		do {
			if (((String) executeScript("return document.readyState")).equals("complete")) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		logger.debug("Returning {} after {} milliseconds", false, System.currentTimeMillis() - startTime);
		return false;
	}

	/**
	 * <h>Public Method - Is Element Stale</h>
	 * <p>
	 * Returns a boolean value indicating if the element is stale.
	 * </p>
	 *
	 * @param element WebElement object to be checked if stale.
	 * @return True if the element is stale, False otherwise.
	 */
	public boolean isElementStale(WebElement element) {
		return isElementStale(element, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Element Stale</h>
	 * <p>
	 * Returns a boolean value indicating if the element is stale.
	 * </p>
	 *
	 * @param element      WebElement object to be checked if stale.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return True if the element is stale, False otherwise.
	 */
	public boolean isElementStale(WebElement element, long milliseconds) {
		long startTime = System.currentTimeMillis();
		int attempts = 0;

		do {
			try {
				++attempts;
				element.isDisplayed();
			} catch (StaleElementReferenceException e) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		logger.debug("Element not stale! Returning false. Number of times checked: {} over {} seconds", attempts,
				((System.currentTimeMillis() - startTime) / 1000));
		return false;
	}

	public boolean isTextContained(String type, String name, String expectedText) {
		return isTextContained(currentPage, type, name, expectedText, LONG_WAIT);
	}

	public boolean isTextContained(String type, String name, String expectedText, long milliseconds) {
		return isTextContained(currentPage, type, name, expectedText, milliseconds);
	}

	public boolean isTextContained(String page, String type, String name, String expectedText) {
		return isTextContained(page, type, name, expectedText, LONG_WAIT);
	}

	public boolean isTextContained(String page, String type, String name, String expectedText, long milliseconds) {
		logger.debug("Checking if {} text \"{}\" is as expected", formatKey(page, type, name), expectedText);

		long startTime = System.currentTimeMillis();

		do {
			try {
				if (waitUntilAvailable(page, type, name, milliseconds).getText().contains(expectedText)) {
					return true;
				}
			} catch (NullPointerException | DisabledElementException | WebDriverException e) {
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	public boolean isTextNotContained(String type, String name, String expectedText) {
		return isTextNotContained(currentPage, type, name, expectedText, LONG_WAIT);
	}

	public boolean isTextNotContained(String type, String name, String expectedText, long milliseconds) {
		return isTextNotContained(currentPage, type, name, expectedText, milliseconds);
	}

	public boolean isTextNotContained(String page, String type, String name, String expectedText) {
		return isTextNotContained(page, type, name, expectedText, LONG_WAIT);
	}

	public boolean isTextNotContained(String page, String type, String name, String expectedText, long milliseconds) {
		logger.debug("Checking if {} text \"{}\" is as expected", formatKey(page, type, name), expectedText);

		long startTime = System.currentTimeMillis();

		do {
			try {
				if (!waitUntilAvailable(page, type, name, milliseconds).getText().contains(expectedText)) {
					return true;
				}
			} catch (NullPointerException | DisabledElementException | WebDriverException e) {
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	public void acceptAlert() {
		acceptAlert(LONG_WAIT);
	}

	public void acceptAlert(long milliseconds) {
		switchToAlert(milliseconds).accept();
	}

	public void dismissAlert() {
		dismissAlert(LONG_WAIT);
	}

	public void dismissAlert(long milliseconds) {
		switchToAlert(milliseconds).dismiss();
	}

	public String getTextFromAlert() {
		return getTextFromAlert(LONG_WAIT);
	}

	public String getTextFromAlert(long milliseconds) {
		return switchToAlert(milliseconds).getText();
	}

	public void sendKeysToAlert(String keys) {
		sendKeysToAlert(keys, LONG_WAIT);
	}

	public void sendKeysToAlert(String keys, long milliseconds) {
		switchToAlert(milliseconds).sendKeys(keys);
	}
}