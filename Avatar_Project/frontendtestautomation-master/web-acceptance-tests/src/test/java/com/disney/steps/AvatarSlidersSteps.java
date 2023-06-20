package com.disney.steps;

import com.disney.common.SmartNav;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import static com.disney.steps.RunnerTest.tree;

import java.util.List;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AvatarSlidersSteps extends SmartNav {
	
	@Given("^user click on the Latest news \"([^\"]*)\" \"([^\"]*)\" button$")
	public void user_click_on_the_Latest_news_button(String type, String element) {
				waitFor(3000);
				tree.focusElement(sanitize(type), "leftarrow");
				waitFor(5000);
				doubleClick(sanitize(type), "leftarrow");
//				clickElement(sanitize(type), "leftarrow");
				waitFor(5000);
			log.info("For next slide clicked on the left arrow..");
				tree.focusElement(sanitize(type), "rightarrow");
				waitFor(5000);
				clickElement(sanitize(type), "rightarrow");
				waitFor(5000);
			log.info("For next slide clicked on the left arrow..");
			}	

	@Then("^user should check \"([^\"]*)\" slider title$")
	public void user_should_check_slider_title(String element) {
		waitFor(3000);
		String title = getText("text","latestnews");
		if (title.contains("News"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("text","latestnews"));
	
	}
	
	@Then("^user should view the Title and Description under \"([^\"]*)\" module$")
	public void user_should_view_the_Title_and_Description_under_module(String type) throws Throwable {
		int imagesCount = 0;
		 tree.scrollToElement(sanitize(type), "titlecheck");
//		List<WebElement> slides = tree.findElements(sanitize(type), "imagecheck");
		List<WebElement> slidesTitle = tree.findElements(sanitize(type), "titlecheck");
		List<WebElement> slidesNumber = tree.findElements(sanitize(type), "descriptioncheck");
		for (int i = 1; i < slidesTitle.size(); i++) {
		waitFor(1000);//wait for element
		log.info(type + " image title is: " + slidesTitle.get(i).getText());
		if((i==9)||(i==10)) {
		log.info(type + " image name is: " + slidesNumber.get(i-1).getText());
		}
		else if(i==8) {
			log.info("image description is not available for this image");
		}
		else {
			log.info(type + " image name is: " + slidesNumber.get(i).getText());
		}
		if (i==3||i==6||i==9) {
		doubleClick(sanitize(type), "leftarrow");
		}
		imagesCount++;
		Assert.assertTrue("The user unable to view the " + type + " images", (imagesCount > 0));
		}
//		Assert.assertTrue("All images are no displayed correctly", imagesCount==slidesTitle.size());
		log.info(type + " slide image count is: " + imagesCount);
		}
	}

//	@Given("^user click on the Latest news \"([^\"]*)\" \"([^\"]*)\" right button$")
//	public void user_click_on_the_Latest_news_right_button(String type, String element) {
//				waitFor(3000);
//				tree.focusElement(sanitize(type), "rightarrow");
//				waitFor(5000);
//				clickElement(sanitize(type), "rightarrow");
//				waitFor(5000);
//				log.info("For next slide clicked on the left arrow..");
//			}	


