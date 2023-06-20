package com.disney.common;

import com.disney.commons.Sequoia;
import com.disney.exceptions.EventNotFoundException;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static com.disney.common.WorldManager.scenario;
import static com.disney.steps.RunnerTest.*;

/**
 * Created by Disney on 4/4/17.
 */
@SuppressWarnings({ "WeakerAccess" })
public class CommonHelpers {
	private final Logger logger = LoggerFactory.getLogger(CommonHelpers.class);
	private Map<String, HashSet<String>> actualData = new HashMap<String, HashSet<String>>();
	public static String dolWAVer = "";
	private static final String EVENT = "trckTp";
	public static final long LONG_WAIT = Sequoia.LONG_WAIT;

	public void navigateToEndpoint(String page) {
		navigateToEndpoint(page, "main");
	}

	public void navigateToEndpoint(String page, String name) {
		tree.navigateToEndpoint(sanitize(page), "endpoint", sanitize(name));
	}

	public String getRawPropertyValue(String page, String type, String name) {
		return tree.getRawPropertyValue(sanitize(page), sanitize(type), sanitize(name));
	}

	public By getBySelector(String type, String name) {
		return getBySelector(getPage(), type, name);
	}

	public By getBySelector(String page, String type, String name) {
		return tree.getBySelector(page, sanitize(type), sanitize(name));
	}

	public void clickButton(String name) {
		clickElement("button", name);
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

	public String getPage() {
		return tree.getPage();
	}

	public boolean isPresent(String type, String name) {
		return tree.isPresent(sanitize(type), sanitize(name));
	}

	public boolean isPresent(String type, String name, long milliseconds) {
		return tree.isPresent(sanitize(type), sanitize(name), milliseconds);
	}

	public boolean isNotPresent(String type, String name) {
		return tree.isNotPresent(sanitize(type), sanitize(name));
	}

	public boolean isNotPresent(String type, String name, long milliseconds) {
		return tree.isNotPresent(sanitize(type), sanitize(name), milliseconds);
	}

	public void clickPreferredOption(String type, String name) {
		tree.executeScript("arguments[0].click();", getElement(type, name));
	}

	public void selectRadioButton(String name) {
		tree.executeScript("arguments[0].click();", getElement("radio", sanitize(name)));
	}

	public WebElement getElement(String type, String name) {
		return tree.getElement(sanitize(type), sanitize(name));
	}

	public List<WebElement> getElements(String type, String name) {
		return tree.findElements(type, sanitize(name));
	}

	public String getText(String type, String name) {
		return tree.getText(sanitize(type), sanitize(name));
	}

	public void waitFor(long time) {
		tree.waitFor(time);
	}

	public void selectDropdownItem(String name, int index) {
		tree.selectDropdownByIndex("dropdown", sanitize(name), index);
	}

	public static String sanitize(String elementName) {
		return elementName.toLowerCase().replaceAll("[^a-zA-Z0-9]+", "");
	}

	public boolean isOnPage(String pageName) {
		return isOnPage(pageName, LONG_WAIT);
	}

	public boolean isOnPage(String pageName, long milliseconds) {
		tree.setPage(sanitize(pageName));
		return tree.validate(sanitize(pageName), milliseconds);
	}

	public boolean isTreePage(String pageName) {
		return tree.getPage().equalsIgnoreCase(sanitize(pageName));
	}

	public String getWindowHandle() {
		return tree.getWindowHandle();
	}

	public List<String> getWindowHandles() {
		return tree.getWindowHandles();
	}

	public void switchToWindow(String windowName) {
		tree.switchTo(windowName);
	}

	public WebDriver getDriver() {
		return tree.getDriver();
	}

	public void switchToFrame(String frameName) {
		tree.switchToIframe("iframe", sanitize(frameName));
	}

	public void switchToDefaultContent() {
		getDriver().switchTo().defaultContent();
	}

	public void waitForPage() {
		waitForPage(9000);// wait for page loading
	}

	public void waitForPage(long delay) {
		logger.info("Waiting for page to load");
		long startTime = System.currentTimeMillis();
		String pageSourceBefore = Objects.toString(getDriver().getPageSource(), "before");
		waitFor(delay);
		String pageSourceAfter = Objects.toString(getDriver().getPageSource(), "after");
		while (!pageSourceBefore.equals(pageSourceAfter) && System.currentTimeMillis() - startTime <= LONG_WAIT) {
			pageSourceBefore = pageSourceAfter;
			waitFor(1000);// wait for the page source to load
			pageSourceAfter = getDriver().getPageSource();
		}
		logger.debug("Page loaded in {} milliseconds. Current url - {}", System.currentTimeMillis() - startTime,
				tree.getCurrentUrl());
	}

	public void mouseOver(String type, String name) {
		if (browser.equalsIgnoreCase("safari") || browser.equalsIgnoreCase("firefox"))
			tree.mouseOverJs(sanitize(type), sanitize(name));
		else
			tree.mouseOver(sanitize(type), sanitize(name));
	}

	public Map<String, String> getAnalyticsEvent(String curl) {
		List<HarEntry> entries = proxy.getHar().getLog().getEntries();

		Set<String> timeOnPageEvent = new HashSet<String>();
		Set<String> engmtTpEvent = new HashSet<String>();
		Set<String> dolWaverEvent = new HashSet<String>();
		Set<String> kdpEvent = new HashSet<String>();
		Set<String> trckTpEvent = new HashSet<String>();
		Map<String, String> urlParams = new HashMap<String, String>();
		Map<String, String> allParams = new HashMap<String, String>();
		switch (curl) {
		case "di-dtaectolog":
			for (HarEntry entry : entries) {
				String url = entry.getRequest().getUrl();

				if (url.contains("di-dtaectolog") && url.contains(EVENT)) {
					urlParams = entry.getRequest().getQueryString().stream()
							.collect(Collectors.toMap(HarNameValuePair::getName, HarNameValuePair::getValue));

					String queryParam = urlParams.get(EVENT);
					if (queryParam.contains("trackevent")) {
						timeOnPageEvent.add(urlParams.get("timeOnPage"));
						engmtTpEvent.add(urlParams.get("engmtTp"));
						trckTpEvent.add(urlParams.get(EVENT));
					} else if (queryParam.contains("trackpage")) {
						dolWaverEvent.add(urlParams.get("dolWAVer"));
						dolWAVer = urlParams.get("dolWAVer");
						trckTpEvent.add(urlParams.get(EVENT));
					} else if (queryParam.contains("trackvideo")) {
						kdpEvent.add(urlParams.get("KDPEVNT"));
						trckTpEvent.add(urlParams.get(EVENT));
					} else if (queryParam.contains("tracklink"))
						trckTpEvent.add(urlParams.get(EVENT));
				}
				urlParams.forEach(allParams::putIfAbsent);
				allParams = new HashMap<String, String>(urlParams);
			}
			actualData.put("trackTp:", (HashSet<String>) trckTpEvent);
			actualData.put("dolWAVer:", (HashSet<String>) dolWaverEvent);
			actualData.put("engmtTp:", (HashSet<String>) engmtTpEvent);
			actualData.put("timeOnPage:", (HashSet<String>) timeOnPageEvent);
			actualData.put("KDPEVNT:", (HashSet<String>) kdpEvent);
			logger.info("Track: " + trckTpEvent);
			logger.info("Time: " + timeOnPageEvent);
			logger.info("engmtTp: " + engmtTpEvent);
			logger.info("dolWAVer: " + dolWaverEvent);
			logger.info("KDPEVNT: " + kdpEvent);
			logger.info("AllQueryParams:: " + allParams);
			break;

		case "sw88":
			for (HarEntry entry : entries) {
				String url = entry.getRequest().getUrl();

				if (url.contains("sw88") && url.contains("c69")) {
					urlParams = entry.getRequest().getQueryString().stream()
							.collect(Collectors.toMap(HarNameValuePair::getName, HarNameValuePair::getValue));
					String queryParam = urlParams.get("c69");
					trckTpEvent.add(queryParam);
					if (queryParam.contains("trackevent")) {
						engmtTpEvent.add(urlParams.get("v8"));
						trckTpEvent.add(urlParams.get("c69"));
					} else if (queryParam.contains("trackpage")) {
						dolWaverEvent.add(urlParams.get("c72"));
						dolWAVer = urlParams.get("c72");
						trckTpEvent.add(urlParams.get("c69"));
					} else if (queryParam.contains("trackvideo"))
						trckTpEvent.add(urlParams.get("c69"));
					else if (queryParam.contains("tracklink"))
						trckTpEvent.add(urlParams.get("c69"));
				}
				urlParams.forEach(allParams::putIfAbsent);
				allParams = new HashMap<String, String>(urlParams);
			}
			actualData.put("c69:", (HashSet<String>) trckTpEvent);
			actualData.put("c72:", (HashSet<String>) dolWaverEvent);
			actualData.put("v8:", (HashSet<String>) engmtTpEvent);
			logger.info("c69: " + trckTpEvent);
			logger.info("dolWAVer: " + dolWaverEvent);
			logger.info("time: " + engmtTpEvent);
			logger.info("AllQueryParams: " + allParams);
			break;

		default:
			try {
				throw new EventNotFoundException("No Events are loaded for the Given URL...");
			} catch (EventNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
		return allParams;
	}

	public boolean compareEventslist(Map<String, HashSet<String>> expected) {
		boolean compareResult = true;
		ArrayList<String> result = new ArrayList<>();
		Map<String, String> exp = new HashMap<String, String>();
		int notMatched = 0;
		int count = 0;
		for (String key : actualData.keySet()) {
			HashSet<String> actualValue = actualData.get(key);
			if (key.contains("percentReached:")) {
				exp.put("percentReached:", "0|10|20|30|40|50|60|70|80|90|100");
				String expectedValue = exp.get(key);
				result.add(String.format("%s:\n\n      Expected: %s\n      Actual:   %s\n", "#! " + key, expectedValue,
						actualValue));
				for (String cvalue : actualValue) {
					boolean pass = Pattern.matches(expectedValue, cvalue);
					if (pass)
						count++;
				}
				if (count > 2)
					logger.info("captured PercentReached Event....And count is : " + count);
			} else {
				HashSet<String> expectedValue = expected.get(key);
				result.add(String.format("%s:\n\n      Expected: %s\n      Actual:   %s\n", "#! " + key, expectedValue,
						actualValue));

				for (String currentValue : expectedValue) {
					if (actualValue.contains(currentValue))
						logger.info(key + " " + currentValue + " value is captured");
					else {
						logger.info(key + " " + currentValue + " value is not captured");
						notMatched++;
					}
				}
				if (notMatched > 0) {
					logger.info(key + ": event is not captured properly");
					notMatched = 0;
					compareResult = false;
				}
			}
		}
		actualData.clear();
		Collections.sort(result);
		result.forEach(t -> scenario.write(t));
		return compareResult;
	}

	public static String provideBaseUrl() {
		return tree.getBaseUrl().replaceAll("https://", "");
	}

	public boolean compareEvents(Map<String, String> actual, Map<String, String> expected) {
		boolean compareResult = true;
		boolean pass = true;
		ArrayList<String> result = new ArrayList<>();
		for (String key : actual.keySet()) {
			String actualValue = actual.get(key);
			String expectedValue = Objects.toString(expected.get(key), "");
			if ((expected.get(key) != null)) {
				pass = Pattern.matches(expectedValue, actualValue);
				result.add(String.format("%s:\n\n      Expected: %s\n      Actual:   %s\n", pass ? key : "#! " + key,
						expectedValue, actualValue));
			}
			compareResult = compareResult && pass;
		}
		Collections.sort(result);
		result.forEach(t -> scenario.write(t));
		return compareResult;
	}

	public boolean isTextNotContained(String type, String name, String text) {
		return tree.isTextNotContained(type, name, text);
	}

	public Map<String, String> getRadioAnalyticsEvent(String curl) {
		List<HarEntry> entries = proxy.getHar().getLog().getEntries();
		Set<String> event = new HashSet<String>();
		Map<String, String> urlParams = new HashMap<String, String>();
		Map<String, String> allParams = new HashMap<String, String>();
		for (HarEntry entry : entries) {
			String url = entry.getRequest().getUrl();
			if (url.contains(curl) && url.contains("event12")) {
				urlParams = entry.getRequest().getQueryString().stream()
						.collect(Collectors.toMap(HarNameValuePair::getName, HarNameValuePair::getValue));

				event.add(urlParams.get("c9"));
				dolWAVer = urlParams.get("c72");
				logger.info("c9:" + event);
			}
			urlParams.forEach(allParams::putIfAbsent);
			allParams = new HashMap<String, String>(urlParams);
		}
		actualData.put("c9:", (HashSet<String>) event);
		logger.info("AllQueryParams: " + allParams);
		return allParams;
	}

	public boolean isVideoPlayerLoaded() {
		boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 5000);
		boolean playContainerFound = tree.isDisplayed("button", "container");
		String playContainer = tree.getAttribute("button", "player", "class");
		while (playContainerFound && !isPlay) {
			try {
				logger.info("checking for play icon !!!");
				tree.click("button", "playicon", 2000);
			} catch (NoSuchElementException | WebDriverException e) {
				playContainer = tree.getAttribute("button", "container", "class");
				if (!playContainer.contains("playkit-pre-playback"))
					logger.info("Time out for play icon !!!" + e.getMessage());
			}
			isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 2000);
		}
		isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
		if (isPlay) {
			playContainer = tree.getAttribute("button", "container", "class");
			try {
				while (playContainer.contains("playkit-ad-break")) {
					logger.info("Ad Identified!!");
					tree.isAttributeValueContained("button", "adtext", "class", "playkit-ad-notice", 3000);
					logger.info("Ad Text: " + getText("button", "adtext").toUpperCase());
					String adText = getText("button", "adtext").toUpperCase();
					if (adText != null && !adText.isEmpty()) {
						isPresent("button", "timer");
						tree.mouseOver("button", "timer");
						int adDuration = Integer
								.parseInt(getText("button", "timer").split("/")[1].split(":")[1].trim());
						tree.waitFor((adDuration * 1000) + 200);
						playContainer = tree.getAttribute("button", "container", "class");
					}
				}
			} catch (NoSuchElementException | WebDriverException e) {
				logger.info("Ad stamp displayed but ad not performed: " + e.getMessage());
			}
		}
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			tree.waitFor(1000);// wait for the player to load
			boolean videoElementFound = isPresent("button", "playerelement");
			String isMute = tree.getAttribute("button", "mute", "class");
			if (videoElementFound && isMute.contains("playkit-is-muted")) {
				tree.mouseOver("button", "container");
				tree.click("button", "mute", 3000);
				logger.info("Video is in mute mode!!!");
			}
			return videoElementFound;
		}
		return playerFound;
	}

	public boolean pause() {
		boolean isPause = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			while (isPlay) {
				logger.info("..video is playing....");
				try {
					tree.mouseOver("button", "container");
					tree.click("button", "pause", 3000);
					waitFor(1000);// wait for the pause action to perform
				} catch (NoSuchElementException | WebDriverException e) {
					isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 3000);
				}
				isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 3000);
			}
			isPlay = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused");
			if (isPlay)
				logger.info("..Clicked on pause button....");
			else
				logger.info("..unable to click on pause button....");
		}
		return isPause;
	}

	public boolean play() {
		boolean isPlay = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused");
			while (isPause) {
				logger.info("..video is paused....");
				try {
					tree.mouseOver("button", "container");
					tree.click("button", "pause", 3000);
					waitFor(1000);// wait for the play action to perform
				} catch (NoSuchElementException | WebDriverException e) {
					isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused",
							3000);
				}
				isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused", 3000);
			}
			isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay)
				logger.info("..Clicked on play button....");
			else
				logger.info("..unable to click on play button....");
		}
		return isPlay;
	}

	public boolean fullscreen() {
		boolean isFullscreen = false;
		boolean isNormalscreen = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isNormalscreen = tree.getAttribute("button", "fullscreen", "class").equals("playkit-control-button");
				while (isNormalscreen) {
					logger.info("..video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "fullscreen", 3000);
						waitFor(1000);// wait for the fullscreen to view the video
					} catch (NoSuchElementException | WebDriverException e) {
						isNormalscreen = tree.getAttribute("button", "fullscreen", "class")
								.equals("playkit-control-button");
					}
					isNormalscreen = tree.getAttribute("button", "fullscreen", "class")
							.equals("playkit-control-button");
				}
			}
			isFullscreen = tree.isAttributeValueContained("button", "container", "class", "playkit-fullscreen");
			if (isFullscreen)
				logger.info("..Clicked on fullscreen button....");
			else
				logger.info("..unable to click on fullscreen button....");
		}
		return isFullscreen;
	}

	public boolean exitFullscreen() {
		boolean isFullscreen = false;
		boolean isNormalscreen = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isFullscreen = tree.isAttributeValueContained("button", "container", "class", "playkit-fullscreen");
				while (isFullscreen) {
					logger.info("..video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "fullscreen", 3000);
						waitFor(1000);// wait for video to display in normal
										// screen
					} catch (NoSuchElementException | WebDriverException e) {
						isFullscreen = tree.getAttribute("button", "fullscreen", "class")
								.equals("playkit-control-button");
					}
					isFullscreen = tree.getAttribute("button", "fullscreen", "class").equals("playkit-control-button");
				}
			}
			isNormalscreen = tree.getAttribute("button", "fullscreen", "class").equals("playkit-control-button");
			if (isNormalscreen)
				logger.info("..Clicked on exit fullscreen button....");
			else
				logger.info("..unable to click on exit fullscreen button....");
		}
		return isNormalscreen;
	}

	public boolean mute() {
		boolean isMute = false;
		boolean isMuted = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isMute = tree.getAttribute("button", "mute", "class")
						.equals("playkit-control-button-container playkit-volume-control");
				while (isMute) {
					logger.info("..video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "mute", 3000);
						waitFor(1000);// wait for mute action to perform
					} catch (NoSuchElementException | WebDriverException e) {
						isMute = tree.getAttribute("button", "mute", "class")
								.equals("playkit-control-button-container playkit-volume-control");
					}
					isMute = tree.getAttribute("button", "mute", "class")
							.equals("playkit-control-button-container playkit-volume-control");
				}
			}
			isMuted = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted");
			if (isMuted)
				logger.info("..Clicked on mute button....");
			else
				logger.info("..unable to click on mute button....");
		}
		return isMuted;
	}

	public boolean unmute() {
		boolean isUnmute = false;
		boolean isUnmuted = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isUnmute = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted");
				while (isUnmute) {
					logger.info("..video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "mute", 3000);
						waitFor(1000);
					} catch (NoSuchElementException | WebDriverException e) {
						isUnmute = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted", 3000);
					}
					isUnmute = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted", 3000);
				}
			}
			isUnmuted = tree.getAttribute("button", "mute", "class")
					.equals("playkit-control-button-container playkit-volume-control");
			if (isUnmuted)
				logger.info("..Clicked on unmute button....");
			else
				logger.info("..unable to click on unmute button....");
		}
		return isUnmuted;
	}

	public int getScrubberWidth() {

		int scrubberWidth = 0;
		boolean playStateFound = isPresent("button", "player");
		if (playStateFound) {
			waitFor(150);// wait for to view the container
			tree.mouseOver("button", "container");
			logger.info("Mouse hover on Player");
			tree.isPresent("button", "scrubber");
			scrubberWidth = getScrubberWidth("button", "scrubber");
		}
		return scrubberWidth;
	}

	public String getScrubberTime() {
		String scrubberTime = null;
		boolean playStateFound = isPresent("button", "player");
		if (playStateFound) {
			waitFor(150);// wait for to view the scrubber
			tree.mouseOver("button", "scrubber");
			logger.info("Mouse hover on Player");
			tree.isPresent("button", "timer");
			scrubberTime = tree.getText("button", "timer").replaceAll("/.*", "");
		}
		return scrubberTime;
	}

	public void dragScrubberLeft() {

		boolean scrubberFound = tree.isPresent("button", "scrubber");
		if (scrubberFound) {

			// scrollToViewElement("button", "container");
			tree.mouseOver("button", "container");
			waitFor(150);// wait for the scrubber to drag
			mouseOverElement("button", "scrubber", 250, 0);
			waitFor(150);// wait for the scrubber to release
		}
	}

	public int getScrubberWidth(String type, String name) {
		int width = tree.getElement(type, name).getSize().getWidth();
		return width;
	}

	public void scrollToViewElement(String type, String name) {
		WebElement element = tree.getElement(type, name);
		tree.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void mouseOverElement(String type, String name, int x, int y) {
		Actions actions = new Actions(tree.getDriver());
		WebElement element = tree.getElement(type, name);
		actions.clickAndHold(element).moveByOffset(x, y).release().build().perform();
	}

	public boolean waitTillVideoEnds() {
		boolean playEnd = false;
		tree.isPresent("button", "slider");
		// scrollToViewElement("button", "container");
		tree.mouseOver("button", "container");
		boolean scrubberFound = false;
		scrubberFound = tree.isPresent("button", "scrubber");
		if (scrubberFound) {
			String scrubberLength = tree.getAttribute("button", "scrubber", "aria-valuenow");
			String scrubberMaxLength = tree.getAttribute("button", "scrubber", "aria-valuemax");
			int remainingVideoLength = Integer.parseInt(scrubberMaxLength) - Integer.parseInt(scrubberLength);
			while (remainingVideoLength > 1 && remainingVideoLength < Integer.parseInt(scrubberMaxLength)) {
				tree.mouseOver("button", "container");
				try {
					remainingVideoLength = Integer.parseInt((tree.getAttribute("button", "scrubber", "aria-valuemax")))
							- Integer.parseInt((tree.getAttribute("button", "scrubber", "aria-valuenow")));
				} catch (NoSuchElementException | WebDriverException e) {
					remainingVideoLength = 0;
					logger.info("### V3 Video clip ends successfully ###");
				}
			}
			playEnd = true;
			if (playEnd)
				logger.info("****V3 Video clip ends successfully***");
		}
		return playEnd;
	}

	public Map<String, String> getVideoAnalyticsEvent(String curl) {
		List<HarEntry> entries = proxy.getHar().getLog().getEntries();

		Set<String> timeOnPageEvent = new HashSet<String>();
		Set<String> engmtTpEvent = new HashSet<String>();
		Set<String> dolWaverEvent = new HashSet<String>();
		Set<String> kdpEvent = new HashSet<String>();
		Set<String> trckTpEvent = new HashSet<String>();
		Map<String, String> urlParams = new HashMap<String, String>();
		Map<String, String> allParams = new HashMap<String, String>();
		for (HarEntry entry : entries) {
			String url = entry.getRequest().getUrl();

			if (url.contains("di-dtaectolog") && url.contains("trckTp")) {
				urlParams = entry.getRequest().getQueryString().stream()
						.collect(Collectors.toMap(HarNameValuePair::getName, HarNameValuePair::getValue));

				String queryParam = urlParams.get("trckTp");
				if (queryParam.contains("trackevent")) {
					trckTpEvent.add(urlParams.get("trckTp"));
				} else if (queryParam.contains("trackpage")) {
					dolWaverEvent.add(urlParams.get("dolWAVer"));
					dolWAVer = urlParams.get("dolWAVer");
					trckTpEvent.add(urlParams.get("trckTp"));
				} else if (queryParam.contains("trackvideo")) {
					dolWaverEvent.add(urlParams.get("dolWAVer"));
					kdpEvent.add(urlParams.get("KDPEVNT"));
					trckTpEvent.add(urlParams.get("trckTp"));
					if (urlParams.get("KDPEVNT").contains("changeVolume"))
						timeOnPageEvent.add(urlParams.get("KDPDAT_VALUE"));
					if (urlParams.get("KDPEVNT").contains("percentReached"))
						engmtTpEvent.add(urlParams.get("KDPDAT_VALUE"));
				}
			}
			urlParams.forEach(allParams::putIfAbsent);
			allParams = new HashMap<String, String>(urlParams);
		}
		actualData.put("trackTp:", (HashSet<String>) trckTpEvent);
		actualData.put("dolWAVer:", (HashSet<String>) dolWaverEvent);
		actualData.put("percentReached:", (HashSet<String>) engmtTpEvent);
		actualData.put("changeVolume:", (HashSet<String>) timeOnPageEvent);
		actualData.put("KDPEVNT:", (HashSet<String>) kdpEvent);
		logger.info("Track: " + trckTpEvent);
		logger.info("Change Volume:" + timeOnPageEvent);
		logger.info("PercentReached:" + engmtTpEvent);
		logger.info("dolWAVer: " + dolWaverEvent);
		logger.info("KDPEVNT: " + kdpEvent);
		logger.info("AllQueryParams:: " + allParams);
		return allParams;
	}

	public Map<String, String> getDisneyAnalyticsEvent(String curl) {
		List<HarEntry> entries = proxy.getHar().getLog().getEntries();
		Set<String> event = new HashSet<String>();
		Map<String, String> urlParams = new HashMap<String, String>();
		Map<String, String> allParams = new HashMap<String, String>();
		for (HarEntry entry : entries) {
			String url = entry.getRequest().getUrl();
			if (url.contains(curl) && url.contains("event12")) {
				urlParams = entry.getRequest().getQueryString().stream()
						.collect(Collectors.toMap(HarNameValuePair::getName, HarNameValuePair::getValue));

				event.add(urlParams.get("c9"));
				dolWAVer = urlParams.get("c72");
				logger.info("c9:" + event);
			}
			urlParams.forEach(allParams::putIfAbsent);
			allParams = new HashMap<String, String>(urlParams);
		}
		actualData.put("c9:", (HashSet<String>) event);
		logger.info("AllQueryParams: " + allParams);
		return allParams;
	}

	public Map<String, String> getAllAnalyticsEvents(String curl) {
		List<HarEntry> entries = proxy.getHar().getLog().getEntries();
		Set<String> event = new HashSet<String>();
		Map<String, String> urlParams = new HashMap<String, String>();
		Map<String, String> allParams = new HashMap<String, String>();
		String urlOmniParams = "";
		int count = 0;
		for (HarEntry entry : entries) {
			String url = entry.getRequest().getUrl();
			if (url.contains(curl) && url.contains("wdgdsec")) {
				if (count == 0) {
					urlOmniParams = url.replaceAll(".*ss/", "").replaceAll(".1/.*", "");
					System.out.println("omniParams: " + urlOmniParams);
					count++;
				}
				urlParams = entry.getRequest().getQueryString().stream()
						.collect(Collectors.toMap(HarNameValuePair::getName, HarNameValuePair::getValue));

				String queryParam = urlParams.get("c69");
				if (queryParam.contains("trackevent"))
					event.add(urlParams.get("c69"));
				else if (queryParam.contains("trackpage"))
					event.add(urlParams.get("c69"));
				else if (queryParam.contains("trackvideo"))
					event.add(urlParams.get("c69"));

			}
			urlParams.forEach(allParams::putIfAbsent);
			allParams = new HashMap<String, String>(urlParams);
		}
		String updatec69 = String.join("|", event);
		allParams.put("OmniParams", urlOmniParams);
		allParams.put("c69", updatec69);
		logger.info("AllQueryParams: " + allParams);
		return allParams;
	}

	public static String getBaseUrl() {
		return tree.getBaseUrl();
	}

	public boolean comparePageEvents(Map<String, String> actual, Map<String, String> expected) {
		boolean compareResult = true;
		boolean pass = true;
		ArrayList<String> result = new ArrayList<>();
		for (String key : actual.keySet()) {
			String actualValue = actual.get(key);
			String expectedValue = Objects.toString(expected.get(key), "");
			if ((expected.get(key) != null)) {
				if (key.equals("pageName"))
					pass = expectedValue.contains(actualValue);
				else if (key.equals("c69"))
					pass = actualValue.contains(expectedValue);
				else
					pass = Pattern.matches(expectedValue, actualValue);
				result.add(String.format("%s:\n\n      Expected: %s\n      Actual:   %s\n", pass ? key : "#! " + key,
						expectedValue, actualValue));
			}
			compareResult = compareResult && pass;
		}
		Collections.sort(result);
		result.forEach(t -> scenario.write(t));
		return compareResult;
	}
}