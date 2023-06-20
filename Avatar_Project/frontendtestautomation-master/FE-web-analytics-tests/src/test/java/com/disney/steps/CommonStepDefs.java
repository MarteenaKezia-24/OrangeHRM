package com.disney.steps;

import com.disney.common.SmartNav;
import com.disney.config.WebConfig;
import com.disney.parameters.TrackPageParameters;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import static com.disney.steps.RunnerTest.*;
import static org.junit.Assert.*;

/**
 * Created by Disney on 4/6/17.
 */
public class CommonStepDefs extends SmartNav {
	String collectionURL = null;
	public static String song;
	WebConfig config = new WebConfig();
	private static final Logger logger = LoggerFactory.getLogger(CommonStepDefs.class);
	Actions action = new Actions(tree.getDriver());

	@Given("^the user is on the \"([^\"]*)\" page$")
	public void theUserIsOnThePage(String page) {
		logger.info("-------- The user is on the \"{}\" page --------", page);
		navigateTo(page);
	}

	@And("^the user clicks on the \"([^\"]*)\" button$")
	public void theUserClicksOnTheButton(String button) {
		logger.info("-------- The user clicks on the \"{}\" button --------", button);
		clickButton(button);
	}

	@And("^the user clicks on the \"([^\"]*)\" \"([^\"]*)\"$")
	public void theUserClicksOnTheElement(String name, String type) {
		logger.info("-------- The user clicks on the \"{}\" {} --------", name, type);
		clickElement(type, name);
	}

	@And("^the user should be on the \"([^\"]*)\" page with validation$")
	public void theUserShouldBeOnThePageWithValidation(String page) {
		logger.info("-------- The user should be on the {} page with validation --------", page);
		assertTrue(isOnPage(page));
	}

	@And("^click on the \"([^\"]*)\" \"([^\"]*)\" on the video$")
	public void clickOnThePlayButtonOnTheVideo(String name, String type) {
		logger.info("-------- The user clicks on the play button--------");
		String isplay = tree.getAttribute("button", "player", "class");
		while (isplay.contains("playkit-pre-playback")) {
			try {
				tree.click(type, name, 5000);// waiting for the play icon to be displayed
			} catch (Exception e) {
				isplay = tree.getAttribute("button", "player", "class");
			}
		}
		WebElement player = tree.getDriver().findElement(By.cssSelector(".playkit-player"));
		action.moveToElement(player).build().perform();
	}

	@And("^navigate to \"([^\"]*)\" \"([^\"]*)\" and mouse hover on the options$")
	public void navigateToHeaderMenuOtion(String name, String type) {
		logger.info("-------- The user navigates to Header menu--------");
		WebElement header = tree.getElement(type, name);
		action.moveToElement(header).build().perform();

	}

	@And("^selects the \"([^\"]*)\" \"([^\"]*)\" option and do ctrl click$")
	public void selectsTheOptionAndDoCtrlClick(String name, String type) {
		logger.info("-------- The user clicks on the parks option--------");
		waitFor(2000);// wait for options menu get displayed
		action.keyDown(Keys.CONTROL).build().perform();
		clickElement(type, name);
		waitFor(10000);// wait for the clicked link get loaded in the new tab
	}

	@And("^click on the \"([^\"]*)\" \"([^\"]*)\"$")
	public void clickOnThePauseButton(String name, String type) {
		logger.info("-------- The user clicks on the pause button--------");
		tree.waitUntilClickable(type, name);
		clickElement(type, name);
	}

	@And("^the user navigates to the \"([^\"]*)\" page$")
	public void theUserNavigatesToThePage(String collection) {
		try {
			collectionURL = config.provideWorkingEnvironment(collection);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		tree.setBaseUrl(collectionURL);
		logger.info("Navigating to the page for the collection type " + collection + " with the URL " + collectionURL);
		navigateTo(collectionURL);
		if (collection.contains("disney_us"))
			setPage("home");
		else if (collection.contains("disney_video"))
			setPage("video");
		else if (collection.contains("radio"))
			setPage("radio");
		else
			setPage("home");
		try {
			if (tree.isPresent("cookie", "close", 2000))
				tree.click("cookie", "close", 6000);
		} catch (WebDriverException e) {
			logger.info("wait for Home Page cookie..but not found");
		}
	}

	@Then("^capture the all disney_us tracking events$")
	public void captureTheAllTrackEvents() {
		logger.info("-------- \"{}\" all the network events should be sent --------", "disney_us");
		Map<String, String> capturedEvents = getAnalyticsEvent("di-dtaectolog");
		Map<String, String> commonEvents = getAnalyticsEvent("sw88");
		capturedEvents.putAll(commonEvents);
		boolean mainParams = compareEventslist(TrackPageParameters.provideLandingEvents());
		if (mainParams)
			logger.info("....Tracking Events are captured successfully.....");
		else
			logger.info("....Tracking Events are not captured properly.....");
		Map<String, String> commonParams = TrackPageParameters.provideCommonParameters();
		assertTrue("Some of the Tracking Events did not match",
				compareEvents(capturedEvents, commonParams) && mainParams);
	}

	@And("^click on the Request a Song \"([^\"]*)\" to \"([^\"]*)\" the song$")
	public void clickOnTheRequestSong(String type, String name) {
		logger.info("-------- The user clicks on the Request a Song link --------");
		tree.waitUntilClickable(type, name);
		clickElement(type, name);
		waitFor(2000);// wait for the page redirect
	}

	@And("^click on the Heart symbol \"([^\"]*)\" to \"([^\"]*)\" the song$")
	public void clickOnTheHeartSymbol(String type, String name) {
		logger.info("-------- The user clicks on the Heart Symbol for like --------");
		tree.isPageReadyState();
		tree.findElements(type, name).get(1).click();
		waitFor(2000);// wait for the page redirect
	}

	@Then("^capture the radio_disney tracking events$")
	public void captureAllTheRadioTrackEvents() {
		logger.info("-------- \"{}\" all the network events should be sent --------", "radio_disney");
		Map<String, String> capturedEvent = getRadioAnalyticsEvent("sw88");
		song = capturedEvent.get("c9").replaceAll("songs/top radio disney songs/mod2/link/like-song/", "");
		boolean mainParams = compareEventslist(TrackPageParameters.provideRadioEvents());
		if (mainParams)
			logger.info("....Tracking Events are captured successfully.....");
		else
			logger.info("....Tracking Events are not captured properly.......");
		Map<String, String> expectedParams = TrackPageParameters.provideRadioCommonParameters();
		assertTrue("Some of the Tracking Events did not match",
				compareEvents(capturedEvent, expectedParams) && mainParams);
	}

	@And("^the video player is loaded$")
	public void theVideoPlayerIsLoaded() {
		logger.info("-------- Waiting for the video to be loaded--------");
		isVideoPlayerLoaded();
	}

	@And("^the user clicks on the Video pause button$")
	public void theUserClicksOnTheVideoPauseButton() {
		logger.info("-------- Waiting for the video to be click on pause--------");
		pause();
	}

	@And("^the user clicks on the Video play button$")
	public void theUserClicksOnTheVideoPlayButton() {
		logger.info("-------- Waiting for the video to be click on play--------");
		play();
	}

	@And("^the user clicks on the Video fullscreen button$")
	public void theUserClicksOnTheVideoFullscreenButton() {
		logger.info("-------- Waiting for the video to be click on fullscreen--------");
		fullscreen();
	}

	@And("^the user clicks on the Video collapse fullscreen button$")
	public void theUserClicksOnTheVideoExitFullscreenButton() {
		logger.info("-------- Waiting for the video to be click on exit screen--------");
		exitFullscreen();
	}

	@And("^the user clicks on the Video mute button$")
	public void theUserClicksOnTheVideoMuteButton() {
		logger.info("-------- Waiting for the video to be click on mute--------");
		mute();
	}

	@And("^the user clicks on the Video unmute button$")
	public void theUserClicksOnTheVideoUnmuteButton() {
		logger.info("-------- Waiting for the video to be click on unmute--------");
		unmute();
	}

	@And("^the user drags the Video player progress slider to the left$")
	public void theUserDragsTheVideoPlayerProgressSliderToTheLeft() {
		logger.info("-------- Waiting for the video to drag the scrubber--------");
		// Play the video for some time..
		waitFor(2000);

		// get initial scrub width..
		int initialScrubWidth = getScrubberWidth();
		logger.info("initialScrubWidth: " + initialScrubWidth);

		// get initial scrub time..
		String initialScrubTime = getScrubberTime();
		logger.info("initialScrubTime: " + initialScrubTime);

		// move scrubber back..
		dragScrubberLeft();
		logger.info("****Clicked on V3 Drag left Button***");
	}

	@And("^the video clip ends$")
	public void theVideoClipEnds() {
		logger.info("-------- Waiting for the video to be end--------");
		waitTillVideoEnds();
		waitFor(1000);// wait for to video to be end
	}

	@Then("^capture all the disney_video tracking events$")
	public void captureAllTheVideoTrackEvents() {
		logger.info("-------- \"{}\" all the network events should be sent --------", "video_disney");
		Map<String, String> capturedEvent = getVideoAnalyticsEvent("di-dtaectolog");
		boolean mainParams = compareEventslist(TrackPageParameters.provideVideoEvents());
		if (mainParams)
			logger.info("....Tracking Events are captured successfully.....");
		else
			logger.info("....Tracking Events are not captured properly.......");
		Map<String, String> expectedParams = TrackPageParameters.provideCommonParameters();
		assertTrue("Some of the parameters did not match", compareEvents(capturedEvent, expectedParams) && mainParams);
	}

	@Then("^capture all the \"([^\"]*)\" track events$")
	public void captureAllTheTrackEvents(String page) {
		logger.info("-------- \"{}\" all the network events should be sent --------", page);
		Map<String, String> capturedEvent = getAllAnalyticsEvents("w88");
		Map<String, String> expectedParams = TrackPageParameters.provideEvents(page);
		assertTrue("Some of the Tracking Events did not match", comparePageEvents(capturedEvent, expectedParams));

	}
}