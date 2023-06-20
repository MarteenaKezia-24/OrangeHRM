package com.disney.steps;

import com.disney.common.SmartNav;
import static com.disney.steps.RunnerTest.tree;
import static com.disney.common.WorldManager.scenario;
import java.util.ArrayList;
import java.util.List;
import com.disney.common.WorldManager;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AvatarNavigationSteps extends SmartNav {
	public static String href = "";
	
	@When("^user click on \"([^\"]*)\" \"([^\"]*)\" button$")
	public void user_click_on_button(String type, String element) {
		waitFor(2000);
		if (type.contains("Avatar"))
			{
			    tree.focusElement(type, element);
				clickElement("input",type);
			}	
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
	}

	@Then("^user should navigate to \"([^\"]*)\" home page$")
	public void user_should_navigate_to_home_page(String element) {
		waitFor(2000);
		String title = tree.getDriver().getTitle();
		System.out.println(title);
		if (title.contains("Avatar"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("logo", "avatar"));
	}
	
	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" home button$")
	public void user_click_on_the_home_button(String element,String type) {
		waitFor(2000);
		if (element.contains("Home"))
		{
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		}
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
		}
	
	@When("^user mouse over on the \"([^\"]*)\" \"([^\"]*)\" movies button$")
	public void user_mouse_over_on_the_movies_button(String element, String type)  {
		if (element.contains("Movies")) 
		{
			waitFor(2000);
		mouseOverElement(type, element);
		waitFor(1000);
		log.info("mouseOver on the " + element + " " + type);
		}
	}

	@Then("^user should redirects to all the \"([^\"]*)\" \"([^\"]*)\" options$")
	public void user_should_redirects_to_all_the_options(String element, String type) {
		List<WebElement> optionsList;
		ArrayList<String> result = new ArrayList<>();
		optionsList = tree.findElements("list", sanitize(element));
		log.info("optionlist size = " + optionsList.size() + " element = " + element + "type =  " + type);
		for (int i = 0; i < optionsList.size(); i++) {
			log.info("Options " + optionsList.get(i).getText());
			href = optionsList.get(i).getAttribute("href");
			String optionText = optionsList.get(i).getAttribute("data-title");
			optionsList.get(i).click();
			waitFor(4000);// wait for page redirect
			String parentWindow = getWindowHandle();
			String parentWindowUrl = getCurrentURL();
			String url = "";
			for (String childWindowHandle : getWindowHandles()) {
				if (!parentWindow.equals(childWindowHandle)) {
					switchToWindow(childWindowHandle);
					url = getCurrentURL();
					waitFor(2000);// wait for page redirect
//					if (url.contains(href)) {
//						log.info("User redirected to the " + element + " '" + optionText + "' : " + href + " page");
//						result.add("tab: " + element + "\n" + (i + 1) + ":\n option: " + optionText + "\n URL: " + href);
//					}
					tree.closeActiveTab();
					switchToWindow(parentWindow);
				} else {
					url = getCurrentURL();
					if (url.contains(href))
						log.info("User redirected to the " + element + " '" + optionText + "' : " + href + " page");
					result.add("tab: " + element + "\n" + (i + 1) + ":\n option: " + optionText + "\n URL: " + href);
				}
			}
				mouseOverElement("tab", element);
			waitFor(2000);// Wait for dropdown
			optionsList = tree.findElements("list", sanitize(element));
			log.info("Mouse over on the " + element + " tab");
		}
		result.forEach(t -> WorldManager.scenario.write(t));
	}
	
	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" games button$")
	public void user_click_on_the_games_button(String element, String type) {
		waitFor(2000);// wait for scroll to bottom
		if (element.contains("Games"))
		{
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		}
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
	}

	@Then("^user should navigate to \"([^\"]*)\" games page$")
	public void user_should_navigate_to_games_page(String element) {
		waitFor(2000);
		String title = tree.getDriver().getTitle();
		System.out.println(title);
		if (title.contains("Games"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("logo", "avatar"));
	}
	
	@When("^user mouse over on the \"([^\"]*)\" \"([^\"]*)\" experiences button$")
	public void user_mouse_over_on_the_experiences_button(String element, String type) {
		if (element.contains("Experiences")) 
		{
		mouseOverElement(type, element);
		waitFor(1000);
		log.info("mouseover on the " + element + " " + type);
		}
	}

	@Then("^user should able to see \"([^\"]*)\" and \"([^\"]*)\" experiences options$")
	public void user_should_able_to_see_and_experiences_options(String element, String type) {
		waitFor(1000);
		if (element.contains("All Experiences")) {
			Assert.assertTrue("User found Movies element",
					element.contains(element) && isPresent("text", "All Experiences"));
		}
		else {
			if (element.contains("Pandora - The World of Avatar")) {
						Assert.assertTrue("User found Avatar(2009) element",
								element.contains(element) && isPresent("text", "Pandora"));
		}
	   }
	}
	
	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" community button$")
	public void user_click_on_the_community_button(String element, String type){
		waitFor(2000);
		if (element.contains("Community"))
		{
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		}
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
	}

	@Then("^user should navigate to \"([^\"]*)\" community page$")
	public void user_should_navigate_to_community_page(String element) {
		waitFor(2000);
		String title = tree.getDriver().getTitle();
		System.out.println(title);
		if (title.contains("Community"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("logo", "avatar"));
	}
	
	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" Publishing button$")
	public void user_click_on_the_Publishing_button(String element, String type) {
		waitFor(2000);
		if (element.contains("Publishing"))
		{
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		}
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
	}

	@Then("^user should navigate to \"([^\"]*)\" Publishing page$")
	public void user_should_navigate_to_Publishing_page(String element) {
		waitFor(2000);
		String title = tree.getDriver().getTitle();
		System.out.println(title);
		if (title.contains("Publishing"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("logo", "avatar"));
	}
	
	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" Partnerships button$")
	public void user_click_on_the_Partnerships_button(String element, String type){
		waitFor(2000);
		if (element.contains("Partnerships"))
		{
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		}
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
	}

	@Then("^user should navigate to \"([^\"]*)\" Partnerships page$")
	public void user_should_navigate_to_Partnerships_page(String element) {
		waitFor(2000);
		String title = tree.getDriver().getTitle();
		System.out.println(title);
		if (title.contains("Partnerships"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("logo", "avatar"));
	}
	

	@When("^user click on the \"([^\"]*)\" \"([^\"]*)\" Ourteam button$")
	public void user_click_on_the_Ourteam_button(String element, String type) {
		waitFor(2000);
		if (element.contains("Ourteam"))
		{
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		}
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
	}

	@Then("^user should navigate to \"([^\"]*)\" Ourteam page$")
	public void user_should_navigate_to_Ourteam_page(String element) {
		waitFor(2000);
		String title = tree.getDriver().getTitle();
		System.out.println(title);
		if (title.contains("Ourteam"))
			Assert.assertTrue("user is not redirected to a valid URL",
					title.contains(element) && isPresent("logo", "avatar"));
	}
	
	@Given("^user click on the avatar \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_click_on_the(String element, String type) {
		waitFor(2000);
			tree.focusElement(sanitize(type), sanitize(element));
			clickElement(type, element);
		waitFor(1000);
		log.info("clicked on the " + element + " " + type);
		
	}

	@Then("^user should navigates to the \"([^\"]*)\" avatar page$")
	public void user_should_navigates_to_the_page(String element) {
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

