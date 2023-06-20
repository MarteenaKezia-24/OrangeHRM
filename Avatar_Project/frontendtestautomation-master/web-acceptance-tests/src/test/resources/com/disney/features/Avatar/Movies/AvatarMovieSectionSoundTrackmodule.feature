 Feature: Avatar - Avatar Movie Section SoundTrack module 
 
 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option" 
    
    Scenario: Verify that 'Sound Track' iframe is loaded succesfully 
       When user moves to the "Sound Track" "module"
       When user can view the "Sound Track" "iframe"
 
    Scenario: User wants to play the 'Becoming one' 'Track' from player
      When user moves to the "Sound Track" "module"
       When user can view the "Sound Track" "iframe"
      Then user clicks on "track" "Becoming one"
      Then user wants to listen the "Becoming one" "track"
      
    Scenario: User wants to play the 'The destruction' 'Track' from player
    When user moves to the "Sound Track" "module"
      When user can view the "Sound Track" "iframe"
      Then user clicks on "track" "The destruction"
      Then user wants to listen the "The destruction" "track"