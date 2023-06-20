@trackevents-frontend
Feature: TrackEvents - FrontEnd - Analytic Events

  #Scenario: DisneyUS analytic events
  #Scenario_label=FE_Analytics_DisneyUS
  #When the user navigates to the "disney_us" page
  #And click on the "play" "button" on the video
  #And click on the "pause" "button"
  #And navigate to "Header" "menu" and mouse hover on the options
  #And selects the "parks" "menu" option and do ctrl click
  #Then capture the all disney_us tracking events
 
  @trackevents-fe
  Scenario: RadioDisney analytic events
    Scenario_label=FE_Analytics_RadioDisney
    When the user navigates to the "radio_disney" page
    #And click on the Request a Song "link" to "play" the song
    And click on the Heart symbol "link" to "like" the song
    Then capture the radio_disney tracking events

  @trackevents-fe
  Scenario: VideoDisney analytic events
    Scenario_label=FE_Analytics_VideoDisney
    When the user navigates to the "disney_video" page
    And the video player is loaded
    And the user clicks on the Video pause button
    And the user clicks on the Video play button
    And the user clicks on the Video fullscreen button
    And the user clicks on the Video collapse fullscreen button
    And the user clicks on the Video mute button
    And the user clicks on the Video unmute button
    And the user drags the Video player progress slider to the left
    And the video clip ends
    Then capture all the disney_video tracking events