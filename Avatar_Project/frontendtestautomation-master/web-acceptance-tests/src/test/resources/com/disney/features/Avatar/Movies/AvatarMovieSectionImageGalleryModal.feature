 Feature: Avatar - Avatar Movie Section Image Gallery Module
 
 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option" 
    When user clicks on "img" "gallery"  
     
     Scenario: Verify that 'Modal Close' button is visible
        When user can view "modal close" "button"
      
     Scenario: User verify that 'left and right' navigation buttions are visible and work
        When user can view "right nav" "button"
        Then user clicks on "right nav" "button"
        When user can view "left nav" "button"
        Then user clicks on "left nav" "button"
     
     Scenario: Verify that 'Share' button is visible and work
        When user clicks on "share" "button"
        Then user can view "share" "window"
       
#     Scenario: Verify that 'Print' buttion is visible and work
#        When user clicks on "print" "button"
#        Then user can view "print" "window"

     Scenario: Verify that 'Image Number' is visible
        Then user can view "Image Number" "text"
