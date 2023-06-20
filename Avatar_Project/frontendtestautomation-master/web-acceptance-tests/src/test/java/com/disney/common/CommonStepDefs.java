package com.disney.common;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.disney.steps.RunnerTest.tree;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.openqa.selenium.WebDriverException;
import org.xml.sax.SAXException;

import com.disney.config.WebConfig;

public class CommonStepDefs extends SmartNav {

	String collectionURL = null;
	WebConfig config = new WebConfig();

	@When("^the user is on the Disney \"([^\"]*)\" page$")
	public void theUserIsOnThePage(String collection) {
		try {
			collectionURL = config.provideWorkingEnvironment(collection);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		tree.setBaseUrl(collectionURL);
		log.info("Navigating to the page for the collection type " + collection + " with the URL " + collectionURL);
		navigateTo(collectionURL);
		setPage("home");
		if (collection.contains("search")) {
			setPage("search");
		}
		maximize();
		try {
			if (tree.isPresent("cookie", "close", 2000))
				tree.click("cookie", "close", 6000);
		} catch (WebDriverException e) {
			log.info("Waiting for Home page cookie..but not found..");
		}
	}

	@When("^the user is on the \"([^\"]*)\" page$")
	public void theUserIsOnTheNatGeoPage(String collection) {
		try {
			collectionURL = config.provideWorkingEnvironment(collection);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		tree.setBaseUrl(collectionURL);
		log.info("Navigating to the page for the collection type " + collection + " with the URL " + collectionURL);
		navigateTo(collectionURL);
		setPage("home");
		maximize();
		try {
			if (tree.isPresent("cookie", "close", 2000))
				tree.click("cookie", "close", 6000);
		} catch (WebDriverException e) {
			log.info("Waiting for Home page cookie..but not found..");
		}
	}

	@And("^the user clicks on the \"([^\"]*)\" button$")
	public void theUserClicksOnTheButton(String button) {
		clickButton(button);
	}

	@And("^the user clicks on the \"([^\"]*)\" \"([^\"]*)\"$")
	public void theUserClicksOnTheElement(String name, String type) {
		clickElement(type, name);
	}

	@Then("^the user should be on the \"([^\"]*)\" page$")
	public void theUserShouldBeOnThePage(String page) {
		setPage(page);
	}

	@And("^the user waits \"([^\"]*)\" seconds$")
	public void theUserWaitsSeconds(String seconds) {
		waitFor(1000 * Integer.valueOf(seconds));
	}

	@And("^the user should be on the \"([^\"]*)\" page with validation$")
	public void theUserShouldBeOnThePageWithValidation(String page) {
		waitFor(3000);//wait for base page
		setPage(page);
		assertTrue(isOnPage(page));
		waitFor(5000);//wait for page redirect
	}

	@Then("^the \"([^\"]*)\" \"([^\"]*)\" should be present$")
	public void theShouldBePresent(String name, String type) {
		waitFor(1000);//wait for element 
		assertTrue(isPresent(type, name));
		log.info(type + " is present with text " + getText(type, name));
		waitFor(1000);//wait for element text
	}

	@Then("^the \"([^\"]*)\" \"([^\"]*)\" should not be present$")
	public void theShouldNotBePresent(String name, String type) {
		assertTrue(isNotPresent(type, name));
		log.info(type + " is not present");
	}

	@Then("^the \"([^\"]*)\" link should be opened in new tab$")
	public void theLinkShouldBeOpenedInNewTab(String link) {
		String parentWindow = getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				switchToWindow(childWindowHandle);
				log.info(link + " is opened in a new tab");
			}
		}
		waitFor(3000);//wait for page load
		closeNewtab();
		waitFor(3000);//wait for parent web page to load
		switchToWindow(parentWindow);
		log.info("After closing the new tab");
		waitFor(3000);//wait for switch window
	}

	@Then("^an \"([^\"]*)\" error message should be displayed$")
	public void anErrorMessageShouldBeDisplayed(String name) {
		assertTrue(isPresent("errormessage", name));
	}

	@Then("^an \"([^\"]*)\" error message should be displayed in the \"([^\"]*)\" page$")
	public void anErrorMessageShouldBeDisplayedInTheParentPage(String name, String pageName) {
		waitFor(2000);//wait for page load
		switchToParentFrame();
		assertTrue(getText("errormessage", name).contentEquals("Check your email address and password and try again."));
	}

	@Then("^the print screen window pops up$")
	public void thePrintScreenWindowPopsUp() {
		String parentWindow = getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				switchToWindow(childWindowHandle);
				getDriver().getTitle();
				log.info("Title is :" + getDriver().getTitle());
				assertTrue("Print Pop up is not displayed", getDriver().getTitle() != null);
			}
		}
	}
}