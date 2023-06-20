package com.disney.steps;

import org.junit.Assert;
import static com.disney.steps.RunnerTest.tree;
import com.disney.common.SmartNav;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HeroBannerSteps extends SmartNav {

	@When("^user conforms that \"([^\"]*)\" is displayed$")
	public void user_conforms_that_is_displeyed(String element){

		waitFor(1000);//wait for focus the element 
		Assert.assertTrue("Hero banner is not diaplayed", tree.isPresent("img",sanitize(element)));
		log.info(element+" is displayed");
	}

	@Then("^user checks the multiple banners by clicking left or \"([^\"]*)\" navigation \"([^\"]*)\"$")
	public void user_wants_to_check_the_multiple_banners_by_clicking_left_or_right_navigation(String element, String type) {

		doubleClickElement(type,sanitize(element));
		//		clickElementByFocus(type,sanitize(element));
		log.info("clicked on the " + element+" "+type);
		waitFor(2000);//wait for page to load
		Assert.assertTrue("Hero banner is not diaplayed", tree.isPresent("img","herobanner2"));		
		clickElementByFocus(type,sanitize(element));
		log.info("clicked on the " + element+" "+type);
		waitFor(2000);//wait for page to load
		Assert.assertTrue("Hero banner is not diaplayed", tree.isPresent("img","herobanner3"));
	}
	@When("^user clicks on \\\"([^\\\"]*)\\\" \\\"([^\\\"]*)\\\"$")
	public void user_clicks_on_Watch_Game_Triller(String type,String element) throws Throwable {

		if(element.equalsIgnoreCase("button")||element.contains("option")||element.contains("link")) {
			tree.focusElement(sanitize(element),sanitize(type));
			clickElement(sanitize(element),sanitize(type));
			log.info("clicked on the " + element+" "+type);
			waitFor(3000);//wait for page to load
		}else {
			tree.focusElement(sanitize(type),sanitize(element));
			clickElement(sanitize(type),sanitize(element));
			log.info("clicked on the " + element+" "+type);
			waitFor(3000);//wait for page to load
		}
	}
	@Then("^user should navigate to the avatar \"([^\"]*)\" page$")
	public void user_should_navigate_to_the_avatar_page(String element) throws Throwable {

		waitFor(2000);// wait for page navigate
		String title=tree.getDriver().getTitle();
		log.info("user navigated to the page: " + title);
		if(element.contains("Experiences")||(element.contains("Partnerships"))) {
			Assert.assertTrue("user is not navigated to the valide page",title.contains(element) &&
					tree.isPresent("logo","avatar"));
		}else {
			Assert.assertTrue("user is not navigated to the valide page" ,tree.getCurrentUrl().contains(element)||
					tree.isPresent("logo","avatar"));
		}
	}
	@Then("^user navigates to the avatar Watch game triller window$")
	public void user_navigates_to_the_avatar_watech_game_triller_window() throws Throwable {

		waitFor(3000);// wait for page navigate
		Assert.assertTrue("user is not navigated to the valide page",tree.isPresent("movie","player"));
	}
	@When("^user clicks on \"([^\"]*)\" nagivation menu option in avatar page$")
	public void user_clicks_on_nagivation_menu_option_in_avatar_page(String content) throws Throwable {

		waitFor(2000);//wait for page to load
		clickElementByScroll(content,"option");
	}

	@Then("^user navigated to \"([^\"]*)\" page$")
	public void user_navigated_to_page(String content) throws Throwable {
		waitFor(7000);//wait for page to load
		String parentWindow =getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				switchToWindow(childWindowHandle);}
		}
		String title=tree.getDriver().getTitle();
		System.out.println(title);
		Assert.assertTrue("user is not navigated to a valid page!",title.contains(content));
	}
}
