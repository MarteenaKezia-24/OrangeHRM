package com.disney.steps;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

import com.disney.common.SmartNav;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class AvatarFlexGrideModuleSteps extends SmartNav {

	@Given("^user confirm that \"([^\"]*)\" \"([^\"]*)\" in avatar home page$")
	public void user_confirm_that_in_avatar_home_page(String index, String element) {
	  waitFor(2000);
	  tree.mouseOver(index, element);
//	  tree.focusElement(index, element);
	  waitFor(2000);
	  log.info("clicked on the " + element + " " + index);
			Assert.assertTrue("user is able to see image",
					element.contains(element) && isPresent(index,element));
	}

	@Then("^user confirm that \"([^\"]*)\" displays title on image$")
	public void user_confirm_that_displays_title_on_image(String element) {
		 waitFor(2000);
		  tree.mouseOver("text", element);
		  waitFor(2000);
				Assert.assertTrue("user is able to see title",
						element.contains(element) && isPresent("text",element));
	}

	@Then("^user confirm that \"([^\"]*)\" displays description on image$")
	public void user_confirm_that_displays_description_on_image(String element) {
		 waitFor(2000);
		  tree.mouseOver("text", element);
		  waitFor(2000);
				Assert.assertTrue("user is able to see description",
						element.contains(element) && isPresent("text",element));
	}

}
