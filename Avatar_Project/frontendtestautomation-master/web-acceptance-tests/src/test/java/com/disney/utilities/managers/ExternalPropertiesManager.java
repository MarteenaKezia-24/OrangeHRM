package com.disney.utilities.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Disney on 3/31/17.
 */
public class ExternalPropertiesManager extends AbstractBaseSpringPropertiesManager {
	public int count;

	public String getExternalEnvironmentProperty() {
		return System.getProperty("working_environment", "qa");
	}

	public List<String> getExternalCollectionProperty() {
		List<String> collectiontypes = new ArrayList<String>();

		if (System.getProperty("collection").equals(null)) {
			collectiontypes.add("disney_US".toLowerCase());
		} else {
			String collections;
			collections = System.getProperty("collection");
			List<String> myList = new ArrayList<String>(Arrays.asList(collections.split(",")));
			for (int i = 0; i < myList.size(); i++) {
				collectiontypes.add(myList.get(i).toLowerCase());
				count = i;
			}
		}
		return collectiontypes;
	}

	public String getExternalBrowserProperty() {
		return System.getProperty("browser", "chrome");
	}

	public String getJobName() {
		return System.getProperty("job_name", "");
	}

	public String getExternalUseGridProperty() {
		return System.getProperty("use_grid", "false");
	}

	public String getExternalUseSauceLabsProperty() {
		return System.getProperty("use_sauce_labs", "false");
	}

	public String getExternalSauceLabsUsernameProperty() {
		return System.getProperty("sl_username", "");
	}

	public String getExternalSauceLabsTokenProperty() {
		return System.getProperty("sl_token", "");
	}

	public String getExternalBrowserVersionProperty() {
		return System.getProperty("browser_version", "latest");
	}

	public String getSauceLabsTunnelId() {
		return System.getProperty("sauce_tunnel_id", "");
	}

	public String getExternalWebsite() {
		return System.getProperty("website", "https://www.google.com/");
	}

	public String getExternalVaultProperty() {
		return System.getProperty("use_vault", "false");
	}

	public String getExternalPlatformProperty() {
		return System.getProperty("platform", "win");
	}

	public String getExternalAccountNumberProperty() {
		return System.getProperty("account_number", "");
	}

	public String getExternalSeleniumVersion() {
		return System.getProperty("selenium_version", "");
	}

	public boolean getExternalRunContinuouslyProperty() {
		return Boolean.valueOf(System.getProperty("run_continuously", "true").toLowerCase());
	}

	public boolean getExternalExtendedDebuggingProperty() {
		return Boolean.valueOf(System.getProperty("extended_debugging", "false").toLowerCase());
	}

	public String getExternalSauceLabsBuildProperty() {
		return System.getProperty("sauce_labs_build", "MhFE_" + getExternalBrowserProperty() + "_"
				+ getExternalEnvironmentProperty() + ":" + System.getProperty("timestamp"));
	}
}
