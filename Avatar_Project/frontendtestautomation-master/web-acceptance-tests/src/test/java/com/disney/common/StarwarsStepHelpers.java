package com.disney.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.disney.steps.RunnerTest.tree;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by Disney on 4/4/17.
 */
public class StarwarsStepHelpers extends CommonStepHelpers {
	public final Logger logger = LoggerFactory.getLogger(StarwarsStepHelpers.class);
	public long waitTime = 60000;

	public boolean isVideoPlayerLoaded() {
		boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 5000);
		boolean playContainerFound = tree.isDisplayed("button", "container");
		String playContainer = tree.getAttribute("button", "player", "class");
		while (playContainerFound && !isPlay) {
			try {
				logger.info("Checking for play icon !!!");
				tree.click("button", "playicon", 2000);
			} catch (WebDriverException e) {
				playContainer = tree.getAttribute("button", "container", "class");
				if (!playContainer.contains("playkit-pre-playback"))
					logger.info("Time out for play icon !!!" + e.getMessage());
			}
			isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 2000);
		}
		isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
		if (isPlay) {
			playContainer = tree.getAttribute("button", "container", "class");
			try {
				while (playContainer.contains("playkit-ad-break")) {
					logger.info("Ad Identified!!");
					tree.isAttributeValueContained("button", "adtext", "class", "playkit-ad-notice", 3000);
					logger.info("Ad Text: " + getText("button", "adtext").toUpperCase());
					String adText = getText("button", "adtext").toUpperCase();
					if (adText != null && !adText.isEmpty()) {
						isPresent("button", "timer");
						tree.mouseOver("button", "timer");
						int adDuration = Integer
								.parseInt(getText("button", "timer").split("/")[1].split(":")[1].trim());
						tree.waitFor((adDuration * 1000) + 200);
						playContainer = tree.getAttribute("button", "container", "class");
					}
				}
			} catch (WebDriverException e) {
				logger.info("Ad stamp displayed but ad not performed: " + e.getMessage());
			}
		}
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			tree.waitFor(1000);// wait for the player to load
			boolean videoElementFound = isPresent("button", "playerelement");
			String isMute = tree.getAttribute("button", "mute", "class");
			while (videoElementFound && isMute.contains("playkit-is-muted")) {
				try {
					tree.mouseOver("button", "container");
					tree.click("button", "mute", 3000);
				} catch (WebDriverException e) {
					isMute = tree.getAttribute("button", "mute", "class");
				}
				isMute = tree.getAttribute("button", "mute", "class");
				logger.info("Video is in mute mode!!!");
			}
			return videoElementFound;
		}
		return playerFound;
	}

	public void pause() {
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			while (isPlay) {
				logger.info("..Video is playing....");
				try {
					tree.mouseOver("button", "container");
					tree.click("button", "pause", 3000);
					waitFor(1000);// wait for the pause action to perform
				} catch (WebDriverException e) {
					isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 3000);
				}
				isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing", 3000);
			}
		}
	}

	public boolean isPaused() {
		boolean isPause = false;
		isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused");
		if (isPause)
			log.info("..Clicked on pause button....");
		else
			log.info("..Unable to click on pause button....");
		return isPause;
	}

	public void play() {
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused");
			while (isPause) {
				logger.info("..Video is paused....");
				try {
					tree.mouseOver("button", "container");
					tree.click("button", "pause", 3000);
					waitFor(1000);// wait for the play action to perform
				} catch (WebDriverException e) {
					isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused",
							3000);
				}
				isPause = tree.isAttributeValueContained("button", "container", "class", "playkit-state-paused", 3000);
			}
		}
	}

	public boolean isPlaying() {
		boolean isPlay = false;
		isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
		if (isPlay)
			logger.info("..Clicked on play button....");
		else
			logger.info("..Unable to click on play button....");
		return isPlay;
	}

	public void fullscreen() {
		boolean isNormalscreen = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isNormalscreen = tree.getAttribute("button", "fullscreen", "class").equals("playkit-control-button");
				while (isNormalscreen) {
					logger.info("..Video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "fullscreen", 3000);
						waitFor(1000);// wait for the fullscreen to view the
										// video
					} catch (WebDriverException e) {
						isNormalscreen = tree.getAttribute("button", "fullscreen", "class")
								.equals("playkit-control-button");
					}
					isNormalscreen = tree.getAttribute("button", "fullscreen", "class")
							.equals("playkit-control-button");
				}
			}
		}
	}

	public boolean isFullscreen() {
		boolean isFullscreen = false;
		isFullscreen = tree.isAttributeValueContained("button", "container", "class", "playkit-fullscreen");
		if (isFullscreen)
			logger.info("..Clicked on fullscreen button....");
		else
			logger.info("..Unable to click on fullscreen button....");
		return isFullscreen;
	}

	public void exitFullscreen() {
		boolean isFullscreen = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isFullscreen = tree.isAttributeValueContained("button", "container", "class", "playkit-fullscreen");
				while (isFullscreen) {
					logger.info("..Video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "fullscreen", 3000);
						waitFor(1000);// wait for video to display in normal
										// screen
					} catch (WebDriverException e) {
						log.info("...retry for click on fullscreen button...");
						isFullscreen = tree.isAttributeValueContained("button", "container", "class",
								"playkit-fullscreen");
					}
					isFullscreen = tree.isAttributeValueContained("button", "container", "class", "playkit-fullscreen");
				}
			}
		}
	}

	public boolean isNormalscreen() {
		boolean isNormalscreen = false;
		isNormalscreen = tree.getAttribute("button", "fullscreen", "class").equals("playkit-control-button");
		if (isNormalscreen)
			logger.info("..Clicked on exit fullscreen button....");
		else
			logger.info("..Unable to click on exit fullscreen button....");
		return isNormalscreen;
	}

	public void mute() {
		boolean isMute = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			tree.mouseOver("button", "container");
			tree.mouseOver("button", "mute");
			isMute = tree.getAttribute("button", "mute", "class")
					.equals("playkit-control-button-container playkit-volume-control playkit-hover");
			while (isMute) {
				logger.info("..Video is playing....");
				try {
					tree.mouseOver("button", "container");
					tree.click("button", "mute", 3000);
					waitFor(1000);// wait for mute action to perform
				} catch (WebDriverException e) {
					log.info("Retry to click on mute button..." + e.getMessage());
					isMute = tree.getAttribute("button", "mute", "class")
							.equals("playkit-control-button-container playkit-volume-control playkit-hover");
				}
				isMute = tree.getAttribute("button", "mute", "class")
						.equals("playkit-control-button-container playkit-volume-control playkit-hover");
			}
		}
	}

	public boolean isMute() {
		boolean isMuted = false;
		isMuted = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted");
		if (isMuted)
			logger.info("..Clicked on mute button....");
		else
			logger.info("..Unable to click on mute button....");
		return isMuted;
	}

	public void unmute() {
		boolean isUnmute = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				isUnmute = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted");
				while (isUnmute) {
					logger.info("..Video is playing....");
					try {
						tree.mouseOver("button", "container");
						tree.click("button", "mute", 3000);
						waitFor(1000);// wait for unmute action to perform
					} catch (WebDriverException e) {
						isUnmute = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted", 3000);
					}
					isUnmute = tree.isAttributeValueContained("button", "mute", "class", "playkit-is-muted", 3000);
				}
			}
		}
	}

	public boolean isUnmute() {
		boolean isUnmuted = false;
		isUnmuted = tree.getAttribute("button", "mute", "class")
				.equals("playkit-control-button-container playkit-volume-control playkit-hover");
		if (isUnmuted)
			logger.info("..Clicked on unmute button....");
		else
			logger.info("..Unable to click on unmute button....");
		return isUnmuted;
	}

	public void captionsOn(String option) {
		boolean isMenuClicked = false;
		boolean playerFound = isPresent("button", "player");
		if (playerFound) {
			boolean isPlay = tree.isAttributeValueContained("button", "isPlay", "class", "playkit-is-playing");
			if (isPlay) {
				logger.info("..Video is playing....");
				tree.mouseOver("button", "container");
				tree.click("button", "ccbutton");
				boolean isButtonClicked = tree.isAttributeValueContained("button", "ccbutton", "class",
						"playkit-active");
				if (isButtonClicked) {
					tree.click("button", "ccmenu", 5000);
					isMenuClicked = tree.isAttributeValueContained("button", "ccmenu", "class", "playkit-active");
					if (isMenuClicked) {
						if (option.contains("ON")) {
							tree.click("button", "ccenglish", 5000);
							log.info("...Clicked on english cc...");
							waitFor(1000);// wait for english captions to be displayed
						} else if (option.contains("OFF")) {
							tree.click("button", "ccoff", 5000);
							log.info("...Clicked on captions OFF...");
							waitFor(1000);// wait for captions to be turn off
						}
					}
				}
			}
		}
	}

	public boolean isCaptionsON() {
		boolean isON = false;
		boolean isDisplay = false;
		boolean captionElementPresent = false;
		isDisplay = tree.isPresent("button", "ccsubtitles");
		captionElementPresent = tree.isPresent("button", "ccoff", 3000);
		if (isDisplay && captionElementPresent == false) {
			log.info("Captions are turn ON for video");
			String sampleText = tree.getText("button", "ccsubtitles");
			log.info("Part of the Subtitles text for the given video: " + sampleText);
			isON = true;
		}
		return isON;
	}

	public boolean isCaptionsOFF() {
		boolean isOFF = false;
		boolean isDisplay = false;
		boolean captionElementPresent = false;
		isDisplay = tree.isPresent("button", "ccsubtitles", 6000);
		captionElementPresent = tree.isPresent("button", "ccenglish", 3000);
		if (isDisplay == false && captionElementPresent == false) {
			log.info("Captions are turn OFF for video");
			isOFF = true;
		}
		return isOFF;
	}

	public int getScrubberWidth() {
		int scrubberWidth = 0;
		boolean playStateFound = isPresent("button", "player");
		if (playStateFound) {
			waitFor(150);// wait for to view the container
			tree.mouseOver("button", "container");
			logger.info("Mouse hover on Player");
			tree.isPresent("button", "scrubber");
			scrubberWidth = getScrubberWidth("button", "scrubber");
		}
		return scrubberWidth;
	}

	public String getScrubberTime() {
		String scrubberTime = null;
		boolean playStateFound = isPresent("button", "player");
		if (playStateFound) {
			waitFor(150);// wait for to view the scrubber
			tree.mouseOver("button", "scrubber");
			logger.info("Mouse hover on Player");
			tree.isPresent("button", "timer");
			scrubberTime = tree.getText("button", "timer").replaceAll("/.*", "");
		}
		return scrubberTime;
	}

	public void dragScrubberLeft() {
		boolean scrubberFound = tree.isPresent("button", "scrubber");
		if (scrubberFound) {
			tree.mouseOver("button", "container");
			waitFor(150);// wait for the scrubber to drag
			mouseOverElement("button", "scrubber", 250, 0);
			waitFor(150);// wait for the scrubber to release
		}
	}

	public int getScrubberWidth(String type, String name) {
		int width = tree.getElement(type, name).getSize().getWidth();
		return width;
	}

	public void scrollToViewElement(String type, String name) {
		WebElement element = tree.getElement(type, name);
		tree.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void mouseOverElement(String type, String name, int x, int y) {
		Actions actions = new Actions(tree.getDriver());
		WebElement element = tree.getElement(type, name);
		actions.clickAndHold(element).moveByOffset(x, y).release().build().perform();
	}
}
