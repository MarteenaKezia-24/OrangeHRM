package com.disney.common;

import static com.disney.steps.RunnerTest.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Reshma on 6/13/17.
 */
public class SmartNav extends CommonStepHelpers
{
    public void navigateTo(String collectionURL)
    {
        if(!environment.equalsIgnoreCase("prod") && !isThroughHub)
        {        	
        	log.info("Navigating to the base URL: " + tree.getBaseUrl());
        	
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
