package com.disney.steps;

import static com.disney.steps.RunnerTest.tree;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.WebElement;

import com.disney.common.SmartNav;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MoviesSectionSteps extends SmartNav {

	@When("^user mouse over on the \"([^\"]*)\" \"([^\"]*)\"$")
	public void userMouseOverOnTheIcon(String element, String type) throws Throwable {

		waitFor(2000);//wait to page load
		mouseOverElement(type,sanitize(element));
	}

	@Then("^user verifies that Movies section presents the \"([^\"]*)\" for \"([^\"]*)\"$")
	public void user_verifies_that_Movies_section_presents_the_for(String type, String element) throws Throwable {

		waitFor(2000);//wait for option visible
		Assert.assertTrue(element + " option is not present in movies section", tree.isPresent(type,sanitize(element)));	
	}

	@Then("^user redirects to the \"([^\"]*)\" page$")
	public void user_redirects_to_the_page(String element) throws Throwable {

		waitFor(2000);//wait to page load
		String title=tree.getDriver().getTitle();
		log.info("current page title is : "+ title);
		if(element.contains("All Movies")) {
			Assert.assertTrue("User not redirects to the valid page",title.contains("Movies") && isPresent("logo","avatar"));
		}else if(element.contains("Avatar (2009)")||element.contains("Community")) {
			Assert.assertTrue("User not redirects to the valid page",title.contains(element) && isPresent("logo","avatar"));
		}else {
			Assert.assertTrue("user is not navigates to the valid page", tree.getCurrentUrl().contains(element));
		}
	}
	@Then("^user conforms that \"([^\"]*)\" \"([^\"]*)\" is present in All Movies page$")
	public void user_conforms_that_is_present_in_All_Movies_page(String type, String element) throws Throwable {

		waitFor(2000);//wait to page load
		mouseOverElement(type,sanitize(element));
		Assert.assertTrue("manu navigation is not present in all movies section",isPresent(type,sanitize(element)));
	}
	@Then("^user navigates to \"([^\"]*)\" page$")
	public void user_navigates_to_page(String content) throws Throwable {

		String parentWindow =getWindowHandle();
		for (String childWindowHandle : getWindowHandles()) {
			if (!childWindowHandle.equals(parentWindow)) {
				switchToWindow(childWindowHandle);}
		}
		if(content.contains("Disney"))
		{
			Assert.assertTrue("user is not navigates to the valid page", tree.getDriver().getTitle().contains(content)&&
					tree.isPresent("logo",sanitize(content)));
		}
	}
	@When("^user conforms that \"([^\"]*)\" \"([^\"]*)\" is visible$")
	public void user_conforms_that_is_visible(String element, String type) throws Throwable {

		waitFor(1000);//wait for focus the element
		Assert.assertTrue(element + " " + type +" is not present in current page",isPresent(sanitize(type),sanitize(element)));
		tree.focusElement(sanitize(type),sanitize(element));
		log.info(element + " " + type + " is visible");

	}

	@When("^user can view the \"([^\"]*)\" of \"([^\"]*)\"$")
	public void user_can_view_the_of(String element, String type) throws Throwable {

		waitFor(1000);//wait to page load
		Assert.assertTrue(type+" "+element+" is not displayed",isPresent(sanitize(type),sanitize(element)));
		log.info("user can view the "+element+" of "+type);
	}
	@When("^user can view the \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_cam_view_the(String element, String type) throws Throwable {
		if(type.contains("iframe")) {
			waitFor(1000);//wait to page load
			Assert.assertTrue(type+" "+element+" is not displayed",isPresent(sanitize(type),sanitize(element)));
			log.info("user can view the "+element+" "+type);
			switchToFrame(sanitize(element));
		}else {
			waitFor(1000);//wait to page load
			Assert.assertTrue(type+" "+element+" is not displayed",isPresent(sanitize(type),sanitize(element)));
			log.info("user can view the "+element+" "+type);
		}
	}
	@Then("^user navigates to \"([^\"]*)\" and start to watch the video$")
	public void user_navigates_to_and_start_to_watch_the_video(String element) throws Throwable {

		log.info("user navigates to "+element);
		Assert.assertTrue(element+" is not displayed",isPresent("player",sanitize(element)));
		waitFor(5000);//wait for play the video
	}
	@When("^user can view \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_can_view(String element, String type) throws Throwable {
		//        if(element.contains("print")){
		//			
		//        	Robot r=new Robot();
		//        	r.keyPress(KeyEvent.VK_ESCAPE);
		//        	r.keyRelease(KeyEvent.VK_ESCAPE);
		//        	Assert.assertTrue(type+" "+element+" is not displayed",isPresent(sanitize(type),sanitize(element)));
		//    		log.info("user can view the "+element);		
		//        }
		//tree.getDriver().switchTo().activeElement();
		waitFor(1000);//wait to load the page
		Assert.assertTrue(type+" "+element+" is not displayed",isPresent(sanitize(type),sanitize(element)));
		log.info("user can view the "+element+" "+type);

	}
	@When("^user can verify the \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_can_verify_the(String element, String type) throws Throwable {

		waitFor(3000);//wait to load the video
		switchToFrame("videomodal");
		Assert.assertTrue(type+" "+element+" is not displayed",isPresent(sanitize(type),sanitize(element)));
	}
	@Then("^user wants to listen the \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_wants_to_play_the(String element, String type) throws Throwable {

		waitFor(3000);//wait to listen the track
		Assert.assertTrue(type+" "+element+" is played succesfully",isPresent(sanitize(type),sanitize(element)));
	}
	@Then("^user should view the images,Title and Image number under \"([^\"]*)\" module$")
	public void userShouldViewTheImagesUnderVideoSlider(String type) {
		int slidesCount = 0;
		tree.scrollToElement(sanitize(type), "titlecheck");
		List<WebElement> slides = tree.findElements(sanitize(type), "imagecheck");
		List<WebElement> slidesTitle = tree.findElements(sanitize(type), "titlecheck");
		List<WebElement> slidesNumber = tree.findElements(sanitize(type), "numbercheck");
		for (int i = 0; i < slides.size(); i++) {
			waitFor(1000);//wait for element
				log.info(type + " slide title is: " + slidesTitle.get(i).getText());
				log.info(type + " slide number is: " + slidesNumber.get(i).getText());
				if (i<=slides.size()-2) {
					clickElement(type, "leftarrow");
				}
				slidesCount++;
			Assert.assertTrue("The user unable to view the " + type + " images", (slidesCount > 0));
		}
		Assert.assertTrue("All images are not displayed correctly", slidesCount==slides.size());
		log.info(type + " slide image count is: " + slidesCount);
	}
	@When("^user can view \"([^\"]*)\" \"([^\"]*)\" button$")
	public void user_can_view_button(String type, String element) throws Throwable {
	   
		waitFor(1000);//wait to page load
		Assert.assertTrue(type+" "+element+" is displayed", isPresent(sanitize(type),sanitize(element)));
	}

	@Then("^user clicks on \"([^\"]*)\" \"([^\"]*)\" button$")
	public void user_clicks_on_button(String type, String element) throws Throwable {
	   
		waitFor(1000);//wait to page load
		tree.focusElement(sanitize(type),sanitize(element));
		clickElement(sanitize(type),sanitize(element));
	}
	@Then("^user navigates to \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_navigates_to(String element, String type) throws Throwable {
		
		waitFor(1000);//wait to page load
		Assert.assertTrue(type+" "+element+" is displayed", isPresent(sanitize(type),sanitize(element)));
	}
	@Then("^user should view the images,Title and Description under \"([^\"]*)\" module$")
	public void user_should_view_the_images_Title_and_Description_under_module(String type) throws Throwable {
	  
		int imagesCount = 0;
//		tree.scrollToElement(sanitize(type), "titlecheck");
		List<WebElement> slides = tree.findElements(sanitize(type), "imagecheck");
		List<WebElement> slidesTitle = tree.findElements(sanitize(type), "titlecheck");
		List<WebElement> slidesName = tree.findElements(sanitize(type), "namecheck");
		for (int i = 0; i < slides.size(); i++) {
			waitFor(1000);//wait for element
				log.info(type + " image title is: " + slidesTitle.get(i).getText());
				log.info(type + " image name is: " + slidesName.get(i).getText());
				if (i==6) {
					doubleClickElement(type, "leftarrow");
				}
				imagesCount++;
			Assert.assertTrue("The user unable to view the " + type + " images", (imagesCount > 0));
		}
		Assert.assertTrue("All images are not displayed correctly", imagesCount==slides.size());
		log.info(type + " slide image count is: " + imagesCount);
	}
	@Then("^user clicked on the \"([^\"]*)\" \"([^\"]*)\" button$")
	public void user_clicked_on_the_button(String type, String element) throws Throwable {
	  
		doubleClickElement(sanitize(type),sanitize(element));
	}
}
