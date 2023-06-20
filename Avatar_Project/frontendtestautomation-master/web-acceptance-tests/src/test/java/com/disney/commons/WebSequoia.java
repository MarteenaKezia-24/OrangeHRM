package com.disney.commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Trammel on 8/11/16. Edited by Jared on 8/30/16.
 * <p>
 * TODO: Fix logging messages
 */
public class WebSequoia extends Sequoia {
	private Logger logger = LoggerFactory.getLogger(WebSequoia.class);
	private String baseURL;

	/**
	 * <h>Parametrized Constructor </h>
	 * <p>
	 * this constructor will create a new instance of a web sequoia object according
	 * to the arguments
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
	public WebSequoia(String treePath, WebDriver driver, String treeRoot, String startingPage) throws IOException {
		super(treePath, driver, treeRoot, startingPage);
	}

	/**
	 * <h>Parametrized Constructor </h>
	 * <p>
	 * this constructor will create a new instance of a web sequoia object according
	 * to the arguments
	 * </p>
	 *
	 * @param file         properties file containing application elements
	 * @param driver       instantiated web driver object, cannot be null
	 * @param treeRoot     the root element of the tree from which all other
	 *                     elements are accessed
	 * @param startingPage the starting page node in which the tree elements will be
	 *                     accessed
	 */
	public WebSequoia(File file, WebDriver driver, String treeRoot, String startingPage) {
		super(file, driver, treeRoot, startingPage);
	}

	/**
	 * <h>Public Method - Delete All Cookies</h>
	 * <p>
	 * Deletes all cookies from the current web browser session.
	 * </p>
	 */
	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}

	/**
	 * <h>Public Method - Refresh</h>
	 * <p>
	 * Refreshes the web browser page.
	 * </p>
	 */
	public void refresh() {
		driver.navigate().refresh();
	}

	/**
	 * <h>Public Method - Navigate Forward</h>
	 * <p>
	 * Navigates the browser forward one page if a page ahead is available.
	 * </p>
	 */
	public void navigateForward() {
		driver.navigate().forward();
	}

	/**
	 * <h>Public Method - Navigate Back</h>
	 * <p>
	 * Navigates the browser back one page if a page behind is available.
	 * </p>
	 */
	public void navigateBack() {
		driver.navigate().back();
	}

	/**
	 * <h>Public Method - Navigate To URL</h>
	 * <p>
	 * Navigates the browser page to the provided URL.
	 * </p>
	 *
	 * @param url String value of URL to redirect to.
	 */
	public void navigateToUrl(String url) {
		driver.navigate().to(url);
	}

	/**
	 * <h>Public Method - Navigate To URL</h>
	 * <p>
	 * Navigates the browser page to the provided URL.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void navigateToUrl(String page, String type, String name) {
		driver.navigate().to(getRawPropertyValue(page, type, name).replace("url:", ""));
	}

	/**
	 * <h>Public Accessor Method - Get Current URL</h>
	 * <p>
	 * Returns the URL of the current page in the web browser session.
	 * </p>
	 *
	 * @return String value of current url.
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * <h>Private Accessor Method - Get Current Page Title</h>
	 * <p>
	 * Returns the page title of the current page in the web browser session.
	 * </p>
	 *
	 * @return String value of current page title.
	 */
	private String getCurrentPageTitle() {
		return driver.getTitle();
	}

	/**
	 * <h>Public Accessor Method - Get Window Handle</h>
	 * <p>
	 * Returns the window handle of the current page in the web browser session.
	 * </p>
	 *
	 * @return String value of current window handle name.
	 */
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * <h>Public Accessor Method - Get Window Handles</h>
	 * <p>
	 * Returns a list of the window handles available in the web browser session.
	 * </p>
	 *
	 * @return ArrayList containing the String values of all window handles in the
	 *         current browser session.
	 */
	public ArrayList<String> getWindowHandles() {
		return new ArrayList<>(driver.getWindowHandles());
	}

	/**
	 * <h>Public Method - Switch To</h>
	 * <p>
	 * Switches the current window of the web browser session to the provided window
	 * handle.
	 * </p>
	 *
	 * @param windowName String value of the window handle name to switch to.
	 * @throws NoSuchWindowException Throws this error when provided window handle
	 *                               name does not exist in the current browser
	 *                               session.
	 */
	public void switchTo(String windowName) throws NoSuchWindowException {
		driver.switchTo().window(windowName);
	}

	/**
	 * <h>Public Method - Close Active Tab</h>
	 * <p>
	 * Closes the active tab of the web browser session.
	 * </p>
	 */
	public void closeActiveTab() {
		driver.close();
	}

	/**
	 * <h>Public Method - Click</h>
	 * <p>
	 * Clicks on the specified web element.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return Selenium method call to click the provided web element.
	 */
	@Override
	public WebElement click(String type, String name) {
		return click(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Click</h>
	 * <p>
	 * Clicks on the specified web element.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return Selenium method call to click the provided web element.
	 */
	public WebElement click(String page, String type, String name) {
		return click(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Click</h>
	 * <p>
	 * Clicks on the specified web element.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Selenium method call to click the provided web element.
	 */
	@Override
	public WebElement click(String type, String name, long milliseconds) {
		return click(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Click</h>
	 * <p>
	 * Clicks on the specified web element.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Selenium method call to click the provided web element.
	 */
	public WebElement click(String page, String type, String name, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Click attempt number {}", attempts);
				logger.debug("Trying to click: {}.value={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilClickable(page, type, name, milliseconds);

				waitFor();

				if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase().contains("firefox")) {
					try {
						element.click();
					} catch (WebDriverException er) {
						logger.debug("Scrolling up for firefox");
						scrollVertical(-100);
						waitFor();
						logger.debug("Re-attempting firefox click");
						element.click();
					}
				} else {
					element.click();
				}
				return element;
			} catch (DisabledElementException | WebDriverException e) {
				logger.debug("Click attempt failed...");
				waitFor();
				message = e.getMessage();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Clicking element failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	/**
	 * <h>Public Method - Select Dropdown By Text</h>
	 * <p>
	 * Selects the option, with the text attribute equal to the specified text, from
	 * the dropdown element provided.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Text value equal to the text attribute of the option to be
	 *             selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByText(String type, String name, String text) {
		return selectDropdownByText(currentPage, type, name, LONG_WAIT, text);
	}

	/**
	 * <h>Public Method - Select Dropdown By Text</h>
	 * <p>
	 * Selects the option, with the text attribute equal to the specified text, from
	 * the dropdown element provided.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Text value equal to the text attribute of the option to be
	 *             selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByText(String page, String type, String name, String text) {
		return selectDropdownByText(page, type, name, LONG_WAIT, text);
	}

	/**
	 * <h>Public Method - Select Dropdown By Text</h>
	 * <p>
	 * Selects the option, with the text attribute equal to the specified text, from
	 * the dropdown element provided.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param text         Text value equal to the text attribute of the option to
	 *                     be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByText(String type, String name, long milliseconds, String text) {
		return selectDropdownByText(currentPage, type, name, milliseconds, text);
	}

	/**
	 * <h>Public Method - Select Dropdown By Text</h>
	 * <p>
	 * Selects the option, with the text attribute equal to the specified text, from
	 * the dropdown element provided.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param text         Text value equal to the text attribute of the option to
	 *                     be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByText(String page, String type, String name, long milliseconds, String text) {
		WebElement element = waitUntilAvailable(page, type, name, milliseconds);
		logger.debug("Trying to select dropdown by text {}, {}", formatKey(page, type, name), text);

		Select selectBox = new Select(element);
		selectBox.selectByVisibleText(text);

		return element;
	}

	/**
	 * <h>Public Method - Select Dropdown By Value</h>
	 * <p>
	 * Selects the option, with the value attribute equal to the specified value,
	 * from the dropdown element provided.
	 * </p>
	 *
	 * @param type  Type of the element, specified in the tree properties file.
	 * @param name  Name of the element, specified in the tree properties file.
	 * @param value Value equal to the value attribute of the option to be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByValue(String type, String name, String value) {
		return selectDropdownByValue(currentPage, type, name, LONG_WAIT, value);
	}

	/**
	 * <h>Public Method - Select Dropdown By Value</h>
	 * <p>
	 * Selects the option, with the value attribute equal to the specified value,
	 * from the dropdown element provided.
	 * </p>
	 *
	 * @param page  Page node where the specified element should be in the
	 *              properties file.
	 * @param type  Type of the element, specified in the tree properties file.
	 * @param name  Name of the element, specified in the tree properties file.
	 * @param value Value equal to the value attribute of the option to be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByValue(String page, String type, String name, String value) {
		return selectDropdownByValue(page, type, name, LONG_WAIT, value);
	}

	/**
	 * <h>Public Method - Select Dropdown By Value</h>
	 * <p>
	 * Selects the option, with the value attribute equal to the specified value,
	 * from the dropdown element provided.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param value        Value equal to the value attribute of the option to be
	 *                     selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByValue(String type, String name, long milliseconds, String value) {
		return selectDropdownByValue(currentPage, type, name, milliseconds, value);
	}

	/**
	 * <h>Public Method - Select Dropdown By Value</h>
	 * <p>
	 * Selects the option, with the value attribute equal to the specified value,
	 * from the dropdown element provided.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param value        Value equal to the value attribute of the option to be
	 *                     selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByValue(String page, String type, String name, long milliseconds, String value) {
		WebElement element = waitUntilAvailable(page, type, name, milliseconds);
		logger.debug("Trying to select dropdown by value {}, {}", formatKey(page, type, name), value);

		Select selectBox = new Select(element);
		selectBox.selectByValue(value);

		return element;
	}

	/**
	 * <h>Public Method - Select Dropdown By Index</h>
	 * <p>
	 * Selects the option, with the index equal to the specified index, from the
	 * dropdown element provided.
	 * </p>
	 *
	 * @param type  Type of the element, specified in the tree properties file.
	 * @param name  Name of the element, specified in the tree properties file.
	 * @param index Index equal to the index of the option to be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByIndex(String type, String name, int index) {
		return selectDropdownByIndex(currentPage, type, name, LONG_WAIT, index);
	}

	/**
	 * <h>Public Method - Select Dropdown By Index</h>
	 * <p>
	 * Selects the option, with the index equal to the specified index, from the
	 * dropdown element provided.
	 * </p>
	 *
	 * @param page  Page node where the specified element should be in the
	 *              properties file.
	 * @param type  Type of the element, specified in the tree properties file.
	 * @param name  Name of the element, specified in the tree properties file.
	 * @param index Index equal to the index of the option to be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByIndex(String page, String type, String name, int index) {
		return selectDropdownByIndex(page, type, name, LONG_WAIT, index);
	}

	/**
	 * <h>Public Method - Select Dropdown By Index</h>
	 * <p>
	 * Selects the option, with the index equal to the specified index, from the
	 * dropdown element provided.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param index        Index equal to the index of the option to be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByIndex(String type, String name, long milliseconds, int index) {
		return selectDropdownByIndex(currentPage, type, name, milliseconds, index);
	}

	/**
	 * <h>Public Method - Select Dropdown By Index</h>
	 * <p>
	 * Selects the option, with the index equal to the specified index, from the
	 * dropdown element provided.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @param index        Index equal to the index of the option to be selected.
	 * @return Selenium method call to select the option for the provided dropdown
	 *         web element.
	 */
	public WebElement selectDropdownByIndex(String page, String type, String name, long milliseconds, int index) {
		WebElement element = waitUntilAvailable(page, type, name, milliseconds);
		logger.debug("Trying to select dropdown by index {}, {}", formatKey(page, type, name), index);

		Select selectBox = new Select(element);
		selectBox.selectByIndex(index);

		return element;
	}

	/**
	 * <h>Public Method - Switch To iFrame</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the
	 * specified iFrame window.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void switchToIframe(String type, String name) {
		switchToIframe(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Switch To iFrame</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the
	 * specified iFrame window.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void switchToIframe(String page, String type, String name) {
		switchToIframe(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Switch To iFrame</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the
	 * specified iFrame window.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 */
	public void switchToIframe(String type, String name, long milliseconds) {
		switchToIframe(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Switch To iFrame</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the
	 * specified iFrame window.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 */
	public void switchToIframe(String page, String type, String name, long milliseconds) {
		logger.debug("Switching to iFrame {}", formatKey(page, type, name));

		driver.switchTo().frame(waitUntilAvailable(page, type, name, milliseconds));
	}

	/**
	 * <h>Public Method - Switch To Default Content</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the
	 * default window handle.
	 * </p>
	 */
	public void switchToDefaultContent() {
		logger.debug("Switching to default content");

		driver.switchTo().defaultContent();
	}

	/**
	 * <h>Public Method - Switch To Parent Frame</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the parent
	 * frame (parent window handle) if it exists.
	 * </p>
	 */
	public void switchToParentFrame() {
		logger.debug("Switching to parent frame");

		driver.switchTo().parentFrame();
	}

	/**
	 * If you're finding you have to use this alot drop us a line. You
	 * implementation might be functionality we would like to add to this framework.
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return WebElement object correlating to the type and name, specified in the
	 *         tree properties file.
	 */
	@Override
	public WebElement getElement(String type, String name) {
		return getElement(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Get Element</h>
	 * <p>
	 * Returns the WebElement object that correlates to the type and name of the
	 * element, specified in the tree properties file.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return WebElement object correlating to the type and name, specified in the
	 *         tree properties file.
	 */
	@Override
	public WebElement getElement(String page, String type, String name) {
		return getElement(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Get Element</h>
	 * <p>
	 * Returns the WebElement object that correlates to the type and name of the
	 * element, specified in the tree properties file.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement object correlating to the type and name, specified in the
	 *         tree properties file.
	 */
	@Override
	public WebElement getElement(String type, String name, long milliseconds) {
		return getElement(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Get Element</h>
	 * <p>
	 * Returns the WebElement object that correlates to the type and name of the
	 * element, specified in the tree properties file.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement object correlating to the type and name, specified in the
	 *         tree properties file.
	 */
	@Override
	public WebElement getElement(String page, String type, String name, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);
				logger.debug("Trying to get element: {}={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);
				return element;
			} catch (DisabledElementException | WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Element not found: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
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
	@Override
	public Alert switchToAlert() {
		return switchToAlert(LONG_WAIT);
	}

	/**
	 * <h>Public Method - Switch To Alert</h>
	 * <p>
	 * Switches the current window handle, of the web browser session, to the alert
	 * window (if present).
	 * </p>
	 *
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Selenium method call to switch the current window handle to an alert
	 *         window.
	 */
	@Override
	public Alert switchToAlert(long milliseconds) {
		String message;
		long startTime = System.currentTimeMillis();
		int attempts = 0;

		do {
			try {
				++attempts;
				logger.debug("Switching to alert");

				return driver.switchTo().alert();
			} catch (UnreachableBrowserException | NoAlertPresentException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoAlertPresentException(
				String.format("Unable to switch to Alert after %s and %s attemps. Original Error Message: %s ",
						((System.currentTimeMillis() - startTime) / 1000), attempts, message));
	}

	/**
	 * <h>Public Method - Mouse Over</h>
	 * <p>
	 * Moves the mouse to hover over the specified element.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void mouseOver(String type, String name) {
		mouseOver(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Mouse Over</h>
	 * <p>
	 * Moves the mouse to hover over the specified element.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void mouseOver(String page, String type, String name) {
		mouseOver(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Mouse Over</h>
	 * <p>
	 * Moves the mouse to hover over the specified element.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 */
	public void mouseOver(String type, String name, long milliseconds) {
		mouseOver(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Mouse Over</h>
	 * <p>
	 * Moves the mouse to hover over the specified element.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 */
	public void mouseOver(String page, String type, String name, long milliseconds) {
		Actions menuBuilder = new Actions(driver);
		menuBuilder.moveToElement(waitUntilDisplayed(page, type, name, milliseconds)).build().perform();
	}

	/**
	 * <h>Public Method - Mouse Over JS</h>
	 * <p>
	 * Moves the mouse to hover over the specified element by executing a Javascript
	 * function.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void mouseOverJs(String type, String name) {
		mouseOverJs(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Mouse Over JS</h>
	 * <p>
	 * Moves the mouse to hover over the specified element by executing a Javascript
	 * function.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void mouseOverJs(String page, String type, String name) {
		mouseOverJs(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Mouse Over JS</h>
	 * <p>
	 * Moves the mouse to hover over the specified element by executing a Javascript
	 * function.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 */
	public void mouseOverJs(String type, String name, long milliseconds) {
		mouseOverJs(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Mouse Over JS</h>
	 * <p>
	 * Moves the mouse to hover over the specified element by executing a Javascript
	 * function.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 */
	public void mouseOverJs(String page, String type, String name, long milliseconds) {
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		executeScript(mouseOverScript, waitUntilDisplayed(page, type, name, milliseconds));
	}

	/**
	 * <h>Public Method - Jiggle Mouse</h>
	 * <p>
	 * Jiggles the mouse left and right.
	 * </p>
	 */
	public void jiggleMouse() {
		Actions builder = new Actions(driver);
		builder.moveByOffset(10, 10).build().perform();
		builder.moveByOffset(-10, -10).build().perform();
		builder.moveByOffset(-10, -10).build().perform();
		builder.moveByOffset(10, 10).build().perform();
	}

	/**
	 * <h>Public Method - Close Previous Tab</h>
	 * <p>
	 * Closes the previously active tab of the web browser session.
	 * </p>
	 */
	public void closePreviousTab() {
		List<String> temp = new ArrayList(driver.getWindowHandles());

		int currentWindowIndex = temp.indexOf(driver.getWindowHandle());

		int counter = 0;

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			if (counter == currentWindowIndex) {
				driver.close();
			}
			++counter;
		}
	}

	/**
	 * <h>Public Method - Get Text</h>
	 * <p>
	 * Extracts the inner text of the specified element.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return String value of the inner text for the specified element.
	 */
	public String getText(String type, String name) {
		return getText(currentPage, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Get Text</h>
	 * <p>
	 * Extracts the inner text of the specified element.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return String value of the inner text for the specified element.
	 */
	public String getText(String type, String name, long milliseconds) {
		return getText(currentPage, type, name, milliseconds);
	}

	/**
	 * <h>Public Method - Get Text</h>
	 * <p>
	 * Extracts the inner text of the specified element.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return String value of the inner text for the specified element.
	 */
	public String getText(String page, String type, String name) {
		return getText(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Get Text</h>
	 * <p>
	 * Extracts the inner text of the specified element.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return String value of the inner text for the specified element.
	 */
	public String getText(String page, String type, String name, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Trying to get text from element: {}={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);

				return element.getText();
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Get text failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * Enters the specified text into the specified input element.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement that literal text will be input into.
	 */
	@Override
	public WebElement input(String type, String name, String text) {
		return input(currentPage, type, name, text, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * Enters the specified text into the specified input element.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement that literal text will be input into.
	 */
	@Override
	public WebElement input(String page, String type, String name, String text) {
		return input(page, type, name, text, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * Enters the specified text into the specified input element.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement that literal text will be input into.
	 */
	@Override
	public WebElement input(String type, String name, String text, long milliseconds) {
		return input(currentPage, type, name, text, milliseconds);
	}

	/**
	 * <h>Public Method - Input</h>
	 * <p>
	 * Enters the specified text into the specified input element.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement that literal text will be input into.
	 */
	@Override
	public WebElement input(String page, String type, String name, String text, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Trying to send input to element: {}={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);
				element.clear();
				waitFor(1000);
				element.sendKeys(text);
				return element;
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Input failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	/**
	 * <h>Public Method - Input No Clear</h>
	 * <p>
	 * Enters the specified text into the specified input element without clearing
	 * the original value of the input field.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputNoClear(String type, String name, String text) {
		return inputNoClear(currentPage, type, name, text, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input No Clear</h>
	 * <p>
	 * Enters the specified text into the specified input element without clearing
	 * the original value of the input field.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputNoClear(String page, String type, String name, String text) {
		return inputNoClear(page, type, name, text, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input No Clear</h>
	 * <p>
	 * Enters the specified text into the specified input element without clearing
	 * the original value of the input field.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputNoClear(String type, String name, String text, long milliseconds) {
		return inputNoClear(currentPage, type, name, text, milliseconds);
	}

	/**
	 * <h>Public Method - Input No Clear</h>
	 * <p>
	 * Enters the specified text into the specified input element without clearing
	 * the original value of the input field.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputNoClear(String page, String type, String name, String text, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Trying to send input to element: {}={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);
				element.sendKeys(text);
				return element;
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Input failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	/**
	 * <h>Public Method - Input With Enter</h>
	 * <p>
	 * Enters the specified text into the specified input element and then presses
	 * the ENTER key.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputWithEnter(String type, String name, String text) {
		return inputWithEnter(type, name, text, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input With Enter</h>
	 * <p>
	 * Enters the specified text into the specified input element and then presses
	 * the ENTER key.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @param text Literal text that will be input into the specified WebElement.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputWithEnter(String page, String type, String name, String text) {
		return inputWithEnter(page, type, name, text, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Input With Enter</h>
	 * <p>
	 * Enters the specified text into the specified input element and then presses
	 * the ENTER key.
	 * </p>
	 *
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputWithEnter(String type, String name, String text, long milliseconds) {
		return inputWithEnter(currentPage, type, name, text, milliseconds);
	}

	/**
	 * <h>Public Method - Input With Enter</h>
	 * <p>
	 * Enters the specified text into the specified input element and then presses
	 * the ENTER key.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param text         Literal text that will be input into the specified
	 *                     WebElement.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return WebElement that literal text will be input into.
	 */
	public WebElement inputWithEnter(String page, String type, String name, String text, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Trying to send input element with enter: {}={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);
				element.clear();
				waitFor(1000);
				element.sendKeys(text);
				waitFor(1000);
				element.sendKeys(Keys.ENTER);
				return element;
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Input with enter failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	/**
	 * <h>Public Mutator Method - Set Base URL</h>
	 * <p>
	 * Sets the baseURL variable to the new url specified.
	 * </p>
	 *
	 * @param url new baseURL String value
	 */
	public void setBaseUrl(String url) {
		baseURL = url;
	}

	/**
	 * <h>Public Accessor Method - Get Base URL</h>
	 * <p>
	 * Gets the baseURL variable.
	 * </p>
	 *
	 * @return baseURL String value
	 */
	public String getBaseUrl() {
		return baseURL;
	}

	/**
	 * <h>Public Method - Navigate To Base</h>
	 * <p>
	 * Navigates the web browser session to the base URL.
	 * </p>
	 */
	public void navigateToBase() {
		driver.navigate().to(baseURL);
	}

	/**
	 * <h>Public Method - Navigate To</h>
	 * <p>
	 * Navigates the web browser session to the specified endpoint of the base URL.
	 * </p>
	 * <p>
	 * If the base URL is null, then will navigate to the specified endpoint as if
	 * it were a complete URL.
	 * </p>
	 *
	 * @param endPoint URL endpoint to be either added to the end of the base URL
	 *                 and evaluated or evaluated as a standalone URL.
	 */
	public void navigateTo(String endPoint) {
		driver.navigate().to(baseURL != null ? baseURL + endPoint : endPoint);
	}

	/**
	 * <h>Public Method - Navigate To Endpoint</h>
	 * <p>
	 * Navigates the web browser session to the specified endpoint of the base URL.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 */
	public void navigateToEndpoint(String page, String type, String name) {
		driver.navigate().to(baseURL + getRawPropertyValue(page, type, name).replace("endpoint:", ""));
	}

	/**
	 * <h>Public Method - Get Current Endpoint</h>
	 * <p>
	 * Returns the endpoint of the current web browser session.
	 * </p>
	 *
	 * @return String value of the endpoint for the current web browser session.
	 */
	public String getCurrentEndpoint() {
		return driver.getCurrentUrl().replace(baseURL, "");
	}

	/**
	 * <h>Public Method - Flush Logs</h>
	 * <p>
	 * Gets the logs of the current web browser session.
	 * </p>
	 *
	 * @return Logs of the current web browser session.
	 */
	public LogEntries flushLogs() {
		return driver.manage().logs().get(LogType.BROWSER);
	}

	/**
	 * <h>Public Method - Focus Window</h>
	 * <p>
	 * Switches the current web browser session to the window that is in focus.
	 * </p>
	 */
	public void focusWindow() {
		List<String> temp = new ArrayList(driver.getWindowHandles());

		int currentWindowIndex = temp.indexOf(driver.getWindowHandle());

		String currentWindow = temp.get(currentWindowIndex);

		driver.switchTo().window(currentWindow);
	}

	/**
	 * <h>Public Accessor Method - Get Session ID</h>
	 * <p>
	 * Returns the sessionID of the WebDriver object.
	 * </p>
	 *
	 * @return String value of sessionID
	 */
	public String getSessionId() {
		return ((RemoteWebDriver) driver).getSessionId().toString();
	}

	/**
	 * <h>Public Method - Is On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is on the specified page by
	 * checking the page title.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return Whether the web browser session is on the specified page.
	 */
	public boolean isOnPageByTitle(String page, String type, String name) {
		return isOnPageByTitle(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Whether the web browser session is on the specified page.
	 */
	public boolean isOnPageByTitle(String page, String type, String name, long milliseconds) {
		return isOnPageByTitle(getRawPropertyValue(page, type, name).replace("title:", ""), milliseconds);
	}

	/**
	 * <h>Public Method - Is On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param pageTitle Title of page that the web browser session should be on.
	 * @return Whether the web browser session is on the specified page.
	 */
	public boolean isOnPageByTitle(String pageTitle) {
		return isOnPageByTitle(pageTitle, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param pageTitle    Title of page that the web browser session should be on.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Whether the web browser session is on the specified page.
	 */
	public boolean isOnPageByTitle(String pageTitle, long milliseconds) {
		return validateTitle(pageTitle, milliseconds);
	}

	/**
	 * <h>Public Method - Is Not On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is not on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return Whether the web browser session is not on the specified page.
	 */
	public boolean isNotOnPageByTitle(String page, String type, String name) {
		return isNotOnPageByTitle(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is not on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param page        Page node where the specified element should be in the
	 *                    properties file.
	 * @param type        Type of the element, specified in the tree properties
	 *                    file.
	 * @param name        Name of the element, specified in the tree properties
	 *                    file.
	 * @param millisecond Length of time to retry in milliseconds until timing out.
	 * @return Whether the web browser session is not on the specified page.
	 */
	public boolean isNotOnPageByTitle(String page, String type, String name, long millisecond) {
		return isNotOnPageByTitle(getRawPropertyValue(page, type, name).replace("title:", ""), millisecond);
	}

	/**
	 * <h>Public Method - Is Not On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is not on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param pageTitle Title of page that the web browser session should not be on.
	 * @return Whether the web browser session is not on the specified page.
	 */
	public boolean isNotOnPageByTitle(String pageTitle) {
		return isNotOnPageByTitle(pageTitle, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not On Page By Title</h>
	 * <p>
	 * Checks if the current web browser session is not on the specified page by
	 * validating the page title.
	 * </p>
	 *
	 * @param pageTitle    Title of page that the web browser session should not be
	 *                     on.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Whether the web browser session is not on the specified page.
	 */
	public boolean isNotOnPageByTitle(String pageTitle, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (!driver.getTitle().toLowerCase().contains(pageTitle.toLowerCase())) {
				return true;
			}

			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Method - Is On Page By Endpoint</h>
	 * <p>
	 * Checks if the current web browser session is on the specified page by
	 * validating the page URL endpoint.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return Whether the web browser session is on the specified page.
	 */
	public boolean isOnPageByEndpoint(String page, String type, String name) {
		return isOnPageByEndpoint(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is On Page By Endpoint</h>
	 * <p>
	 * Checks if the current web browser session is on the specified page by
	 * validating the page URL endpoint.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Whether the web browser session is on the specified page.
	 */
	public boolean isOnPageByEndpoint(String page, String type, String name, long milliseconds) {
		return validateEndpoint(getRawPropertyValue(page, type, name).replace("endpoint:", ""), milliseconds);
	}

	/**
	 * <h>Public Method - Is Not On Page By Endpoint</h>
	 * <p>
	 * Checks if the current web browser session is not on the specified page by
	 * validating the page URL endpoint.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return Whether the web browser session is not on the specified page.
	 */
	public boolean isNotOnPageByEndpoint(String page, String type, String name) {
		return isNotOnPageByEndpoint(page, type, name, LONG_WAIT);
	}

	/**
	 * <h>Public Method - Is Not On Page By Endpoint</h>
	 * <p>
	 * Checks if the current web browser session is not on the specified page by
	 * validating the page URL endpoint.
	 * </p>
	 *
	 * @param page         Page node where the specified element should be in the
	 *                     properties file.
	 * @param type         Type of the element, specified in the tree properties
	 *                     file.
	 * @param name         Name of the element, specified in the tree properties
	 *                     file.
	 * @param milliseconds Length of time to retry in milliseconds until timing out.
	 * @return Whether the web browser session is not on the specified page.
	 */
	public boolean isNotOnPageByEndpoint(String page, String type, String name, long milliseconds) {
		long startTime = System.currentTimeMillis();

		do {
			if (!driver.getCurrentUrl().replace(baseURL, "")
					.endsWith(getRawPropertyValue(page, type, name).replace("endpoint:", ""))) {
				return true;
			}
			waitFor();
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		return false;
	}

	/**
	 * <h>Public Accessor Method - Get Selector</h>
	 * <p>
	 * Gets the By selector for the specified element.
	 * </p>
	 *
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return String value of the By selector
	 */
	public String getSelector(String type, String name) {
		return getSelector(currentPage, type, name);
	}

	/**
	 * <h>Public Accessor Method - Get Selector</h>
	 * <p>
	 * Gets the By selector for the specified element.
	 * </p>
	 *
	 * @param page Page node where the specified element should be in the properties
	 *             file.
	 * @param type Type of the element, specified in the tree properties file.
	 * @param name Name of the element, specified in the tree properties file.
	 * @return String value of the By selector
	 */
	public String getSelector(String page, String type, String name) {
		return extractSelectorParameter(getRawPropertyValue(page, type, name));
	}

	/**
	 * <h>Public Method - Scroll Vertical</h>
	 * <p>
	 * Scrolls the down the page of the current web browser session.
	 * </p>
	 *
	 * @param pixels the distance in pixels to scroll the page down
	 */
	public void scrollVertical(long pixels) {
		executeScript(String.format("window.scrollBy(0, %s);", pixels));
	}

	/**
	 * <h>Public Method - Scroll Horizontal</h>
	 * <p>
	 * Scrolls the down the page of the current web browser session.
	 * </p>
	 *
	 * @param pixels the distance in pixels to scroll the page down
	 */
	public void scrollHorizontal(long pixels) {
		executeScript(String.format("window.scrollBy(%s, 0);", pixels));
	}

	public void scrollToElement(String type, String name) {
		scrollToElement(currentPage, type, name, LONG_WAIT, false);
	}

	public void scrollToElement(String type, String name, boolean alignToTop) {
		scrollToElement(currentPage, type, name, LONG_WAIT, alignToTop);
	}

	public void scrollToElement(String type, String name, long milliseconds) {
		scrollToElement(currentPage, type, name, milliseconds, false);
	}

	public void scrollToElement(String page, String type, String name) {
		scrollToElement(page, type, name, LONG_WAIT, false);
	}

	public void scrollToElement(String page, String type, String name, long milliseconds) {
		scrollToElement(page, type, name, milliseconds, false);
	}

	public void scrollToElement(String type, String name, long milliseconds, boolean alignToTop) {
		scrollToElement(currentPage, type, name, milliseconds, alignToTop);
	}

	public void scrollToElement(String page, String type, String name, boolean alignToTop) {
		scrollToElement(page, type, name, LONG_WAIT, alignToTop);
	}

	public void scrollToElement(String page, String type, String name, long milliseconds, boolean alignToTop) {
		executeScript(String.format("arguments[0].scrollIntoView(%s);", alignToTop),
				waitUntilAvailable(page, type, name, milliseconds));
	}

	public WebElement inputNoClear(String type, String name, Keys keys) {
		return inputNoClear(currentPage, type, name, keys, LONG_WAIT);
	}

	public WebElement inputNoClear(String type, String name, Keys keys, long milliseconds) {
		return inputNoClear(currentPage, type, name, keys, milliseconds);
	}

	public WebElement inputNoClear(String page, String type, String name, Keys keys) {
		return inputNoClear(page, type, name, keys, LONG_WAIT);
	}

	public WebElement inputNoClear(String page, String type, String name, Keys keys, long milliseconds) {
		String message;
		int attempts = 0;
		long startTime = System.currentTimeMillis();

		do {
			try {
				++attempts;
				String selector = getRawPropertyValue(page, type, name);

				logger.debug("Trying to send input to element: {}={}", formatKey(page, type, name), selector);
				WebElement element = waitUntilAvailable(page, type, name, milliseconds);
				element.sendKeys(keys);
				return element;
			} catch (WebDriverException e) {
				message = e.getMessage();
				waitFor();
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);

		throw new NoSuchElementException(String.format(
				"Input failed: \"%s\" after %s seconds of trying and %s attempts. Original Error Message: %s",
				String.format("%s, %s, %s", page, type, name), ((System.currentTimeMillis() - startTime) / 1000),
				attempts, message));
	}

	public void addCookie(Cookie cookie) {
		driver.manage().addCookie(cookie);
	}

	public void deleteCookie(Cookie cookie) {
		driver.manage().deleteCookie(cookie);
	}

	public void deleteCookie(String cookieName) {
		driver.manage().deleteCookieNamed(cookieName);
	}

	public Cookie getCookie(String cookieName) {
		return driver.manage().getCookieNamed(cookieName);
	}

	public Set<Cookie> getCookies() {
		return driver.manage().getCookies();
	}

	public void focusElement(String type, String name) {
		focusElement(currentPage, type, name, LONG_WAIT);
	}

	public void focusElement(String type, String name, long milliseconds) {
		focusElement(currentPage, type, name, milliseconds);
	}

	public void focusElement(String page, String type, String name) {
		focusElement(page, type, name, LONG_WAIT);
	}

	public void focusElement(String page, String type, String name, long milliseconds) {
		executeScript("arguments[0].focus();", waitUntilAvailable(page, type, name, milliseconds));
	}
}