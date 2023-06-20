package com.disney.parameters;

import com.disney.common.CommonHelpers;
import static com.disney.steps.CommonStepDefs.song;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Disney on 10/24/17.
 */
@SuppressWarnings({ "WeakerAccess" })
public class TrackPageParameters extends CommonHelpers {
	private final static Logger logger = LoggerFactory.getLogger(TrackPageParameters.class);

	public static Map<String, String> provideCommonParameters() {
		Map<String, String> map = new HashMap<>();
		map.put("AQB", "0|1");
		map.put("AQE", "1");
		map.put("ndh", "1");
		map.put("cdp", "2");
		map.put("cc", "USD");
		map.put("v76", "not-detected|detected"); // ad block
		map.put("j", "1.6");
		map.put("k", "Y");
		map.put("v", "Y|N");
		map.put("c", "1|4|8|15|16|24|32|48"); // color depth
		map.put("g", "https://" + provideBaseUrl() + "/");
		return map;
	}

	public static Map<String, String> provideRadioCommonParameters() {
		Map<String, String> map = new HashMap<>();
		map.put("AQB", "0|1");
		map.put("AQE", "1");
		map.put("ndh", "1");
		map.put("cdp", "2");
		map.put("cc", "USD");
		map.put("v76", "not-detected|detected"); // ad block
		map.put("j", "1.6");
		map.put("k", "Y");
		map.put("v", "Y|N");
		map.put("c72", dolWAVer);
		map.put("c", "1|4|8|15|16|24|32|48"); // color depth
		map.put("g", "https://" + provideBaseUrl());
		return map;
	}

	public static Map<String, HashSet<String>> provideLandingEvents() {
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
		HashSet<String> tracktp = new HashSet<String>();
		HashSet<String> dolEvent = new HashSet<String>();
		HashSet<String> timeEvent = new HashSet<String>();
		HashSet<String> engmtEvent = new HashSet<String>();
		HashSet<String> kdpEvent = new HashSet<String>();
		HashSet<String> c69Event = new HashSet<String>();
		HashSet<String> c72Event = new HashSet<String>();
		HashSet<String> v8Event = new HashSet<String>();
		tracktp.add("trackevent");
		tracktp.add("trackpage");
		tracktp.add("trackvideo");
		map.put("trackTp:", tracktp);
		dolEvent.add(dolWAVer);
		map.put("dolWAVer:", dolEvent);
		timeEvent.add("5");
		timeEvent.add("10");
		timeEvent.add("15");
		map.put("timeOnPage:", timeEvent);
		engmtEvent.add("page_15_sec");
		engmtEvent.add("page_10_sec");
		engmtEvent.add("page_5_sec");
		engmtEvent.add("mtt_chrome_Parks%20%26%20Travel_hover_toplevel");
		map.put("engmtTp:", engmtEvent);
		c69Event.add("trackevent");
		c69Event.add("trackpage");
		c69Event.add("trackvideo");
		c69Event.add("tracklink");
		map.put("c69:", c69Event);
		c72Event.add(dolWAVer);
		map.put("c72:", c72Event);
		v8Event.add("page_15_sec");
		v8Event.add("page_10_sec");
		v8Event.add("page_5_sec");
		v8Event.add("mtt_chrome_parks%20%26%20travel_hover_toplevel");
		map.put("v8:", v8Event);
		kdpEvent.add("doPause");
		map.put("KDPEVNT:", kdpEvent);
		logger.info("Expected Data for Disney US: " + map);
		return map;
	}

	public static Map<String, HashSet<String>> provideRadioEvents() {
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
		HashSet<String> c9 = new HashSet<String>();
		c9.add(song);
		map.put("c9:", c9);
		logger.info("Expected Data for Radio Disney:" + map);
		return map;
	}

	public static Map<String, String> provideVideoCommonParameters() {
		Map<String, String> map = new HashMap<>();
		map.put("fullPgNm", "dcom|dvideo|homepage|homepage");
		map.put("adblck", "not-detected|detected");
		map.put("videoAdPattern", "AC|C");
		map.put("url", "https://" + provideBaseUrl() + "/");
		return map;
	}

	public static Map<String, HashSet<String>> provideVideoEvents() {
		Map<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
		HashSet<String> tracktp = new HashSet<String>();
		HashSet<String> dolEvent = new HashSet<String>();
		HashSet<String> volumeEvent = new HashSet<String>();
		HashSet<String> kdpEvent = new HashSet<String>();
		tracktp.add("trackevent");
		tracktp.add("trackpage");
		tracktp.add("trackvideo");
		map.put("trackTp:", tracktp);
		dolEvent.add(dolWAVer);
		map.put("dolWAVer:", dolEvent);
		volumeEvent.add("0");
		volumeEvent.add("100");
		map.put("changeVolume:", volumeEvent);
		kdpEvent.add("doPause");
		kdpEvent.add("percentReached");
		kdpEvent.add("scriptLoaded");
		kdpEvent.add("changeMedia");
		kdpEvent.add("playerSeekEnd");
		kdpEvent.add("playerSeekStart");
		kdpEvent.add("changeVolume");
		kdpEvent.add("openedFullScreen");
		kdpEvent.add("closedFullScreen");
		kdpEvent.add("doPlay");
		map.put("KDPEVNT:", kdpEvent);
		logger.info("expected Data from video Disney: " + map);
		return map;
	}

	public static Map<String, String> provideEvents(String page) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("AQB", "0|1");
		map.put("AQE", "1");
		map.put("ndh", "1");
		map.put("cc", "USD");
		map.put("v76", "not-detected|detected"); // ad block
		map.put("j", "1.6");
		map.put("k", "Y");
		map.put("v", "Y|N");
		map.put("c", "1|4|8|15|16|24|32|48"); // color depth
		map.put("g", getBaseUrl());
		map.put("c69", "trackpage");
		map.put("c28", getBaseUrl());
		switch (page) {
		case "disney_us":
			map.put("pageName", "dcom|dhome|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldhom");
			break;
		case "disney_lol":
			map.put("pageName", "dcom|lol|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldgam2");
			break;
		case "disney_lol_games":
			map.put("pageName", "dcom|lol|games|games");
			map.put("OmniParams", "wdgdsec,wdgdoldgam2");
			break;
		case "disney_video":
			map.put("pageName", "dcom|dvideo|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldvid2");
			break;
		case "disney_movies":
			map.put("pageName", "dcom|dmovies|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldmov");
			break;
		case "disney_movies_intheaters":
			map.put("pageName", "dcom|dmovies|in-theaters|in-theaters");
			map.put("OmniParams", "wdgdsec,wdgdoldmov");
			break;
		case "disney_channel":
			map.put("pageName", "dcom|dch|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldch");
			break;
		case "disney_xd":
			map.put("pageName", "dcom|dxd|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldxd");
			break;
		case "disney_junior":
			map.put("pageName", "dcom|dxd|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoldjr");
			break;
		case "disney_princess":
			map.put("pageName", "dcom|pri|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdolpri");
			break;
		case "disney_princess_rapunzel":
			map.put("pageName", "dcom|pri|rapunzel|rapunzel");
			map.put("OmniParams", "wdgdsec,wdgdolpri");
			break;
		case "disney_toystory":
			map.put("pageName", "dcom|fran_toy|homepage|homepage");
			map.put("OmniParams", "wdgdsec,wdgdoltoy,wdgdoldpic,wdgdolstusec");
			break;
		case "starwars_home":
			map.put("pageName", "luc|starwars|homepage|homepage");
			map.put("OmniParams", "wdgdollucas,wdgdolstarcom,wdgdsec");
			break;
		case "starwars_news":
			map.put("pageName", "(luc|disney|mwp-np:qa:starwars|main)|(luc|starwars|news|main)");
			map.put("OmniParams", "wdgdollucas,wdgdsec,wdgdolstarcom|wdgdollucas,wdgdolstarcom,wdgdsec");
			break;
		case "babble_home":
			map.put("pageName", "dfam|babble|homepage|main_1");
			map.put("OmniParams", "wdgdsec,wdgdolbabble,wdgdolfamsec");
			break;
		case "disney_ohmy":
			map.put("pageName", "dcom|dohmy|homepage|main_1");
			map.put("OmniParams", "wdgdsec,wdgdoldblog");
			break;
		case "disney_family":
			map.put("pageName", "(dcom|disney|mwp-np:qa:t1edm|main)|(dcom|family|homepage|main_1)");
			map.put("OmniParams", "wdgdsec,wdgdolfambiz|wdgdsec,wdgdolfamily");
			break;
		default:
			throw new IllegalArgumentException("Parameter: " + page + "name is invalid.");
		}
		return map;
	}
}
