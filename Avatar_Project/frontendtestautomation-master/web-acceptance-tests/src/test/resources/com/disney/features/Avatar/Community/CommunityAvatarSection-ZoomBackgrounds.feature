Feature: Avatar - Community Avatar Section - Zoom Backgrounds

Background: 
    Given user navigates to the "Avatar_Home" page
    When user clicks on "Tab" "Community"
    When user moves to the "Zoom Backgrounds" "module"
    
    Scenario: Verify that 'Text' and 'Description' is visible
      When user can view "Zoom Backgrounds" "Text"
      When user can view "Zoom Backgrounds" "Description"
    
#    Scenario: User Clicking 'backgrouns button' initiates a zip download
#      When user can view "Download All Backgrounds" "Button"
#      Then user clicks on "Download All Backgrounds" "Button"
    
