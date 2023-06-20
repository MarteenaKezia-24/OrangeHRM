package com.disney.common;

import static com.disney.steps.RunnerTest.tree;

import com.disney.config.WebConfig;

import cucumber.api.java.en.Given;

public class CommonAvatarStepDef extends SmartNav {
	String collectionURL=null;
	WebConfig config = new WebConfig();
	
	@Given("^user navigates to the \"([^\"]*)\" page$")
	public void userNavigatesToHomePage(String collection)
	{
		try {
			collectionURL = config.provideWorkingEnvironment(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tree.setBaseUrl(collectionURL);
		log.info("Navigating to the page for the collection type " + collection + " with the URL " + collectionURL);
		navigateTo(collectionURL);
		setPage("home");
		if (collection.contains("search")) {
			setPage("search");
		}
		maximize();
//		baseUrl=config.getTestUrls(content);
//		tree.setBaseUrl(baseUrl);
//		log.info("Navigating to the avatar home page with the URL :" + baseUrl);
//		tree.navigateToUrl(baseUrl);
//		tree.setPage("home");
//		maximize();
	}

}
