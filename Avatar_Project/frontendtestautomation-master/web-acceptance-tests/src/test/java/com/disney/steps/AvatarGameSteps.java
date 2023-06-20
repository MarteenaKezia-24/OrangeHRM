package com.disney.steps;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

import com.disney.common.SmartNav;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AvatarGameSteps extends SmartNav {


	@Given("^user confirm that \"([^\"]*)\" \"([^\"]*)\" displays correctly in avatar games page$")
	public void user_confirm_that_displays_correctly_in_avatar_games_page(String type, String element) {
		waitFor(2000);
		tree.mouseOver(type, element);
		waitFor(2000);
		log.info("clicked on the " + element + " " + type);
		Assert.assertTrue("user is able to see image",
				element.contains(element) && isPresent(type,element));
	}


	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" in games page$")
	public void user_click_on_the_in_games_page(String type, String element) {
		waitFor(5000);
		tree.focusElement(type,element);
		clickElement(type,element);
		log.info("clicked on the " + element + " " + type);
		waitFor(5000); 
		Assert.assertTrue("user is able to click button",
				element.contains(element) && isPresent(type,element));
	}

	@When("^user click on \"([^\"]*)\" to navigate into learnmore page$")
	public void user_click_on_to_navigate_into_learnmore_page(String element) throws Throwable {
		waitFor(2000);
//		tree.focusElement("click",element);
		String parentWindow =tree.getDriver().getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
		if (!childWindowHandle.equals(parentWindow)) {
		tree.getDriver().switchTo().window(childWindowHandle);}
		}
		tree.mouseOver("click", element);
		clickElement("click",element);
		log.info("clicked on the " + element );
	}
	
	@Then("^user confirm \"([^\"]*)\" navigates to correct page$")
	public void user_confirm_navigates_to_correct_page(String element) {
		//	tree.focusElement(type,element);
		waitFor(2000);
		Assert.assertTrue("user navigated to correct page",
				element.contains(element) && isPresent("input",element));
		waitFor(2000);
		
	}


	@Then("^user should \"([^\"]*)\" \"([^\"]*)\" page$")
	public void user_should_page(String type, String element) {
		waitFor(2000);
		tree.mouseOver(type,element);
		clickElement(type,element);
		log.info("clicked on the " + element + " " + type);
	}


	@Given("^user click on the \"([^\"]*)\" \"([^\"]*)\" in avatar games page$")
	public void user_click_on_the_in_avatar_games_page(String element, String type) {
		waitFor(2000);
		tree.focusElement(sanitize(type), sanitize(element));
		clickElement(type, element);
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);

	}

	@Then("^user should navigates to the \"([^\"]*)\" in avatar games page$")
	public void user_should_navigates_to_the_in_avatar_games_page(String element) {
		waitFor(2000);
		String parentWindow =tree.getDriver().getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				tree.getDriver().switchTo().window(childWindowHandle);}
		}
		waitFor(3000);
		if (element.contains("Avatar"))
			Assert.assertTrue("user is not redirected to a valid URL",
					element.contains(element) && isPresent("text", element));

	}

}
