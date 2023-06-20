package com.disney.steps;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

import com.disney.common.SmartNav;

import cucumber.api.java.en.When;

public class AvatarWorldOfAvatarSteps extends SmartNav{

	@When("^user confirm that \"([^\"]*)\" \"([^\"]*)\" in avatar world avatar page$")
	public void user_confirm_that_in_avatar_world_avatar_page(String index, String element) throws Throwable {
		  waitFor(2000);
		  tree.mouseOver(index, element);
//		  tree.focusElement(index, element);
		  waitFor(2000);
		  log.info("clicked on the " + element + " " + index);
				Assert.assertTrue("user is able to see image",
						element.contains(element) && isPresent(index,element));	
	}
}