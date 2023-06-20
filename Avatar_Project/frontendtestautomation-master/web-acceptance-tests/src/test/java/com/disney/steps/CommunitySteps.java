package com.disney.steps;

import com.disney.common.SmartNav;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

public class CommunitySteps extends SmartNav {

	@Then("^user moves to the \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_moves_to(String element, String type) throws Throwable {

		waitFor(1000);
		tree.mouseOver(sanitize(type),sanitize(element));
	}
	@Then("^user can view the \"([^\"]*)\" \"([^\"]*)\" in separate window$")
	public void user_can_view_the_in_separate_window(String element, String type) throws Throwable {

		String parentWindow =tree.getDriver().getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				tree.getDriver().switchTo().window(childWindowHandle);}
		}
		Assert.assertTrue(element + " " + type +" is not present in current page",isPresent(sanitize(type),sanitize(element)));
	}
}
