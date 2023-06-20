package com.disney.commons;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Disney on 1/5/17.
 */
public final class SeleniumGridUtilities {
	private static final Logger logger = LoggerFactory.getLogger(SeleniumGridUtilities.class);

	/**
	 * Returns the IP of the selenium node executing the test.
	 *
	 * @param driver  The current automation driver
	 * @param hubIP   The IP Address of the selenium grid hub
	 * @param hubPort The port of the selenium grid hub
	 * @return
	 */
	public static String getIpOfCurrentNode(WebDriver driver, String hubIP, int hubPort) {
		JSONObject object = new JSONObject();
		HttpHost host = new HttpHost(hubIP, hubPort);
		DefaultHttpClient client = new DefaultHttpClient();
		URL testSessionApi = null;
		try {
			testSessionApi = new URL("http://" + hubIP + ":" + hubPort + "/grid/api/testsession?session="
					+ ((RemoteWebDriver) driver).getSessionId().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
				testSessionApi.toExternalForm());
		try {
			HttpResponse response = client.execute(host, r);
			object = new JSONObject(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String proxyID = object.get("proxyId").toString();

		return (proxyID.split("//")[1].split(":")[0]);
	}

	/**
	 * Returns the port of the selenium node executing the test.
	 *
	 * @param driver  The current automation driver
	 * @param hubIP   The IP Address of the selenium grid hub
	 * @param hubPort The port of the selenium grid hub
	 * @return
	 */
	public static String getPortOfCurrentNode(WebDriver driver, String hubIP, int hubPort) {
		JSONObject object = new JSONObject();
		HttpHost host = new HttpHost(hubIP, hubPort);
		DefaultHttpClient client = new DefaultHttpClient();
		URL testSessionApi = null;
		try {
			testSessionApi = new URL("http://" + hubIP + ":" + hubPort + "/grid/api/testsession?session="
					+ ((RemoteWebDriver) driver).getSessionId().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
				testSessionApi.toExternalForm());
		try {
			HttpResponse response = client.execute(host, r);
			object = new JSONObject(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String proxyID = object.get("proxyId").toString();

		return (proxyID.split("//")[1].split(":")[1]);
	}

	/**
	 * Sends a kill command for the currently running browser
	 *
	 * @param nodeIP  The IP Address of the selenium grid hub
	 * @param browser The port of the selenium grid hub
	 */
	public static void sendBrowserCloseRequest(String nodeIP, String browser) {
		Map<String, String> killCommand = new HashMap<String, String>();

		killCommand.put("chrome", "/kill_chrome");
		killCommand.put("firefox", "/kill_firefox");
		killCommand.put("safari", "/kill_safari");

		killCommand.put("ie", "/kill_ie");
		killCommand.put("explorer", "/kill_ie");
		killCommand.put("internet explorer", "/kill_ie");

		killCommand.put("edge", "/kill_all_by_name?name=MicrosoftEdge.exe");
		killCommand.put("microsoftedge", "/kill_all_by_name?name=MicrosoftEdge.exe");
		killCommand.put("microsoft edge", "/kill_all_by_name?name=MicrosoftEdge.exe");

		HttpHost host = new HttpHost(nodeIP, 3000);
		DefaultHttpClient client = new DefaultHttpClient();
		URL killCommandUrl = null;
		try {
			killCommandUrl = new URL("http://" + nodeIP + ":" + 3000 + killCommand.get(browser.toLowerCase()));
			logger.debug(killCommandUrl.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		BasicHttpEntityEnclosingRequest r = new BasicHttpEntityEnclosingRequest("POST",
				killCommandUrl.toExternalForm());
		try {
			client.execute(host, r);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
