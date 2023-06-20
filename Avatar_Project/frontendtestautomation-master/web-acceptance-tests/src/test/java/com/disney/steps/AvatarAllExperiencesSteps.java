package com.disney.steps;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

import com.disney.common.SmartNav;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AvatarAllExperiencesSteps extends SmartNav{

	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" experiences button$")
	public void user_click_on_the_experiences_button(String type, String element) throws Throwable {
		mouseOverElement(type, element);
		waitFor(1000);
		clickElement(type,element);
		log.info("clicked on the " + element + " " + type);
	}

	@When("^user confirm that \"([^\"]*)\" \"([^\"]*)\" displays correctly all experiences page$")
	public void user_confirm_that_displays_correctly_all_experiences_page(String type, String element) throws Throwable {
		waitFor(2000);
		tree.mouseOver(type, element);
		waitFor(2000);
		log.info("clicked on the " + element + " " + type);
		Assert.assertTrue("user is able to see image",
				element.contains(element) && isPresent(type,element));
	}

	@Given("^user click on the \"([^\"]*)\" \"([^\"]*)\" in avatar all experiences page$")
	public void user_click_on_the_in_avatar_all_experiences_page(String element, String type) {
		waitFor(2000);
		tree.focusElement(sanitize(type), sanitize(element));
		clickElement(type, element);
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
	}
	
	@Then("^user should navigates to the \"([^\"]*)\" in avatar all experiences page$")
	public void user_should_navigates_to_the_in_avatar_all_experiences_page(String element) {
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

	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" in all experiences page$")
	public void user_click_on_the_in_all_experiences_page(String type, String element) {
		waitFor(5000);
		tree.focusElement(type,element);
		clickElement(type,element);
		log.info("clicked on the " + element + " " + type);
		waitFor(5000); 
		Assert.assertTrue("user is able to click button",
				element.contains(element) && isPresent(type,element));
	}
	
	@When("^user click on \"([^\"]*)\" to navigate into learnmore from all experiences page$")
	public void user_click_on_to_navigate_into_learnmore_from_all_experiences_page(String element) throws Throwable {
//		waitFor(2000);
//		tree.focusElement("click",element);
//		tree.mouseOver("allexperiences", element);
		waitFor(2000);
		String parentWindow =tree.getDriver().getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
		if (!childWindowHandle.equals(parentWindow)) {
		tree.getDriver().switchTo().window(childWindowHandle);}
		}
		clickElement("exhibition",element);
		log.info("clicked on the " + element );
	}
	
	@Then("^user confirm \"([^\"]*)\" \"([^\"]*)\" navigates to experiences page$")
	public void user_confirm_navigates_to_experiences_page(String type,String element) {
		//	tree.focusElement(type,element);
		waitFor(2000);
		Assert.assertTrue("user navigated to correct page",
				element.contains(element) && isPresent(type,element));
		waitFor(2000);
	}
}
