package com.disney.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.restassured.response.Response;

import static com.disney.steps.RunnerTest.tree;
import static io.restassured.RestAssured.*;

/**
 * Created by Disney on 4/4/17.
 */
public class CommonStepHelpers {
	public final Logger log = LoggerFactory.getLogger(CommonStepHelpers.class);
	public long waitTime = 60000;
	private static String username = System.getProperty("username");
	private static String password = System.getProperty("password");

	public void clickButton(String name) {
		tree.click("button", sanitize(name));
	}

	public void clickElement(String type, String name) {
		tree.click(sanitize(type), sanitize(name));
	}

	public void inputText(String name, String text) {
		tree.input("input", sanitize(name), text);
	}

	public void inputTextWithEnter(String name, String text) {
		tree.inputWithEnter("input", sanitize(name), text);
	}

	public void setPage(String page) {
		tree.setPage(sanitize(page));
	}

	public boolean isPresent(String type, String name) {
		return tree.isPresent(sanitize(type), sanitize(name));
	}

	public boolean isPresentWithWait(String type, String name, long wait) {
		return tree.isPresent(sanitize(type), sanitize(name), wait);
	}

	public boolean isNotPresent(String type, String name) {
		return tree.isNotPresent(sanitize(type), sanitize(name));
	}

	public void clickPreferredOption(String type, String name) {
		WebElement element = tree.getElement(sanitize(type), sanitize(name));
		JavascriptExecutor ex = (JavascriptExecutor) tree.getDriver();
		ex.executeScript("arguments[0].click();", element);
	}
	public void doubleClickElement(String type, String name) {
		WebElement element = tree.getElement(sanitize(type), sanitize(name));
		Actions actions = new Actions(tree.getDriver());
		actions.doubleClick(element).build().perform();;
	}
	public void clickElementByFocus(String type, String name) {
		WebElement element = tree.getElement(sanitize(type), sanitize(name));
		Actions actions = new Actions(tree.getDriver());
		actions.moveToElement(element).clickAndHold().build().perform();
		actions.release().perform();
	}
//	public void clickElementByFocus(String type, String name) {
//		WebElement element = tree.getElement(sanitize(type), sanitize(name));
//		Actions actions = new Actions(tree.getDriver());
//		actions.moveToElement(element).click().build().perform();
//	}

	public WebElement getElement(String type, String name) {
		return tree.getElement(sanitize(type), sanitize(name));
	}

	public String getText(String type, String name) {
		return tree.getText(sanitize(type), sanitize(name));
	}

	public String getAttributeValue(String type, String name, String attribute) {
		return tree.getAttribute(type, name, attribute);
	}

	public void waitFor(long time) {
		tree.waitFor(time);
	}

	public void selectDropdownItem(String name, int index) {
		tree.selectDropdownByIndex("button", sanitize(name), index);
	}

	public void waitLoader() {
		long startTime = System.currentTimeMillis();
		while (tree.isPresent("image", "loader", 10) && System.currentTimeMillis() - startTime <= waitTime) {
		}
		waitFor(5000);
	}

	public static String sanitize(String elementName) {
		return elementName.toLowerCase().replaceAll("[^a-zA-Z0-9]+", "");
	}

	public boolean isOnPage(String pageName) {
		return tree.validate(sanitize(pageName));
	}

	public String getWindowHandle() {
		return tree.getWindowHandle();
	}

	public ArrayList<String> getWindowHandles() {
		return tree.getWindowHandles();
	}

	public void switchToWindow(String windowName) {
		tree.switchTo(windowName);
	}

	public WebDriver getDriver() {
		return tree.getDriver();
	}

	public void switchToFrame(int frameNumber) {
		tree.getDriver().switchTo().frame(frameNumber);
	}

	public void switchToFrame(String frameName) {
		tree.switchToIframe("iframe", frameName);
	}

	public void switchToParentFrame() {
		tree.getDriver().switchTo().parentFrame();
	}

	public void switchToDefault() {
		tree.getDriver().switchTo().defaultContent();
	}

	public void switchToDefault(String winHandleBefore) {
		tree.getDriver().switchTo().window(winHandleBefore);
	}

	public void acceptAlert() {
		tree.getDriver().switchTo().alert().accept();
	}

	public void dismissAlert() {
		tree.getDriver().switchTo().alert().accept();
	}

	public String getAlertText() {
		return tree.getDriver().switchTo().alert().getText();
	}

	public String getCurrentURL() {
		return tree.getCurrentUrl();
	}

	public List<WebElement> findElements(String type, String name) {
		return tree.findElements(type, name);
	}

	public void findElement(By type) {
		tree.getDriver().findElement(type).click();
	}

	public void navigateBack() {
		tree.getDriver().navigate().back();
	}

	public void scrollDown() {
		Actions actions = new Actions(tree.getDriver());
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
	}

	public void pressKeys() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_F12);
		robot.keyRelease(KeyEvent.VK_F12);
		Actions action = new Actions(tree.getDriver());
		action.sendKeys(Keys.chord(Keys.CONTROL, Keys.SHIFT, "M"));
	}

	public void handleMailPopup() {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
		} catch (AWTException e) {
			e.printStackTrace();
		}

	}

	public void closeNewtab() {
		tree.closeActiveTab();
	}

	public void refresh() {
		tree.refresh();
	}

	public void myIdLogin() {
		if (tree.isNotPresent("element", "welcomeuser", 100)) {
			clickButton("logindd");
			waitFor(200);
			clickButton("login");
			inputText("username", username.toUpperCase());
			clickButton("next");
			inputText("password", password);
			clickButton("signin");
			waitFor(3000);
			verifyUserLogin();
		} else {
			log.info("user is already logged in!");
		}
	}

	public void verifyUserLogin() {
		if (tree.isPresent("element", "welcomeuser", 200)) {
			log.info("user is logged in!");
		} else {
			oktaVerify();
			log.info("User Credentials validated via Okta!! Successfully logged in to the application!!");
		}
	}

	public void clickElement(WebElement element) {
		Actions actions = new Actions(tree.getDriver());
		actions.moveToElement(element).clickAndHold().build().perform();
		actions.release().perform();
	}

	public void mouseOverElement(String type, String name) {
		WebElement element = getElement(type, name);
		Actions actions = new Actions(tree.getDriver());
		actions.moveToElement(element).build().perform();
	}

	public void maximize() {
		tree.getDriver().manage().window().maximize();
	}

	public boolean clickPlay() {
		boolean isPlay;
		String playContainer = tree.getAttribute("container", "video", "class");
		if (playContainer.contains("playkit-pre-playback"))
			tree.focusElement("button", "playicon");
		clickElement("button", "playicon");
		isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 8000);
		return isPlay;
	}

	public void validateSearchTableContent(String key, String value) {
		List<String> attrValues = new ArrayList<String>();
		List<WebElement> searchTermsList;
		List<WebElement> noOfSearchesList;
		int searchTermsLength;
		int noOfSearchesLength;

		searchTermsList = findElements("searchmetrics", key);
		for (WebElement e : searchTermsList)
			attrValues.add(e.getText());

		searchTermsLength = searchTermsList.size();
		Assert.assertFalse("The Search terms are not displayed under Search tab! :", attrValues.isEmpty());
		attrValues.clear();

		noOfSearchesList = findElements("searchmetrics", value);
		for (WebElement e : noOfSearchesList)
			attrValues.add(e.getText());

		noOfSearchesLength = noOfSearchesList.size() - 2;
		Assert.assertFalse("The Search terms are not displayed under Search tab! :", attrValues.isEmpty());
		attrValues.clear();

		Assert.assertTrue("Search Terms and No of Searches value pairs does not match!",
				searchTermsLength == noOfSearchesLength);
	}

	public boolean scrollToClickable(String type, String name) {
		WebDriverWait wait = new WebDriverWait(tree.getDriver(), 10);
		boolean isClickable = wait
				.until(ExpectedConditions.elementToBeClickable(tree.getElement(type, sanitize(name)))) != null;
		return isClickable;
	}

	public void clickElementByScroll(String type, String name) {
		boolean isClickable = false;
		while (!isClickable) {
			tree.scrollVertical(3000);
			waitFor(2000);// wait for to be scrolled
			isClickable = scrollToClickable(type, name);
			if (isClickable) {
				tree.click(type, sanitize(name), 2000);
				isClickable = true;
			}
		}
	}

	public void focusElement(WebElement element) {
		JavascriptExecutor ex = (JavascriptExecutor) tree.getDriver();
		ex.executeScript("arguments[0].focus();", element);
	}

	public void oktaVerify() {
		clickButton("oktapush");
		waitFor(3000);
		clickButton("trustbrowser");
	}

	public List<WebElement> getElementsByTagName(String tagName) {
		return tree.getDriver().findElements(By.tagName(tagName));
	}

	public boolean isAttributeContained(WebElement element, String attribute, String value, long milliseconds) {
		long startTime = System.currentTimeMillis();
		do {
			try {
				if (element.getAttribute(attribute).contains(value)) {
					return true;
				}
			} catch (WebDriverException e) {
				log.debug("Attribute does not exist...");
				waitFor(500);// wait for iteration
			}
		} while (System.currentTimeMillis() - startTime <= milliseconds);
		return false;
	}

	public Response getResponseWithHeader(String url, String hname, String hvalue) {
		return given().when().headers(hname, hvalue).get(url);
	}

	public Response getResponse(String url) {
		return given().when().get(url);
	}

	public int getStatusCode(Response res) {
		return res.getStatusCode();
	}
}
