package com.disney.steps;

import com.disney.common.SmartNav;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class FooterLinksSteps extends SmartNav{

	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\"$")
	public void userClickedOnTheIcon(String element, String type) {
		
		waitFor(1000);//wait to scroll the page
		tree.focusElement(sanitize(type), sanitize(element));
		clickElement(type, element);
		waitFor(1000);// wait for page redirect
		log.info("clicked on the " + element + " " + type);
	}
	
	@Then("^user should navigates to the \"([^\"]*)\" page$")
	public void user_should_navigates_to_the_page(String content) throws Throwable {
		 
		waitFor(1000);// wait for page navigate
		String parentWindow =getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				switchToWindow(childWindowHandle);}
		}
		log.info("Url of the current page : "+ getCurrentURL());
		if(content.contains("facebook")||content.contains("instagram")||content.contains("twitter")||content.contains("youtube")) {
			
			Assert.assertTrue("user is not redirected to valid page", getCurrentURL().contains(content));
		}else {
			Assert.assertTrue("user is not redirected to valid page", isPresent("element",sanitize(content)));
		}
	}

}
