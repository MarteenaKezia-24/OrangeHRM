package com.disney.common;

import java.util.HashMap;
import java.util.Map;
import static com.disney.steps.RunnerTest.environment;
import static com.disney.steps.RunnerTest.tree;

/**
 * Created by Disney on 4/4/17.
 */
@SuppressWarnings({"WeakerAccess"})
public class SmartNav extends CommonHelpers
{
    public void navigateTo(String collectionURL)
    {
        if(!environment.equalsIgnoreCase("prod"))
        {        	
        	//logger.info("Navigating to the base URL: " + tree.getBaseUrl());
        	tree.navigateToUrl(tree.getBaseUrl());               
        }
        Map<String, Runnable> navigationMap = new HashMap<String, Runnable>();
        navigationMap.put(collectionURL, () -> navToHomePage());
                             
        navigationMap.get(collectionURL).run();
    }

	public void navToHomePage() {
		
		tree.navigateToUrl(tree.getBaseUrl());
	} 
}
