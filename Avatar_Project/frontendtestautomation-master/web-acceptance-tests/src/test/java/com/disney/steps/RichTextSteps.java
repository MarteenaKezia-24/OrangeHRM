package com.disney.steps;

import static com.disney.steps.RunnerTest.tree;

import org.junit.Assert;

import org.openqa.selenium.WebElement;

import com.disney.common.SmartNav;

import cucumber.api.java.en.When;


public class RichTextSteps extends SmartNav{

	@When("^user should able to view the \"([^\"]*)\" Rich Text on avatar page$")
	public void user_checks_the_Rich_Text_on_avatar_page(String element) throws Throwable {
	   
		if(element.equalsIgnoreCase("More Sky")) {
			tree.mouseOver("text",sanitize(element));
			waitFor(2000);
			log.info(element + " rich text on Avatar page is:" + 
		             tree.getText("text",sanitize(element)));
			WebElement text=tree.getElement("text",sanitize(element));
			String style= text.getCssValue("font-family");
			System.out.println("font colour " +style);
			Assert.assertTrue(element + " rich text is not displayed",isPresent("text",sanitize(element)));
		}
	}
}
