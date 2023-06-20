Feature: Avatar - Community Avatar Section - generic Modules

Background: 
    Given user navigates to the "Avatar_Home" page
    When user clicks on "Tab" "Community"
 
    Scenario: Redirecting to 'Community' page through community tab
       Then user redirects to the "Community" page 
          
    Scenario: Verify that 'Hero Banner' load succesfully in Community page
       When user can view "Hero Banner5" "img"
      
    Scenario: Navigates to 'Facebook' Page through 'Follow Us on Facebook' button
       And user click on the "Follow Us on Facebook" "button"
       Then user should navigates to the "facebook" page
    
    Scenario: Navigates to 'Instagram' Page through 'Follow Us on Instagram' button
       And user click on the "Follow Us on Instagram" "footer links"
       Then user should navigates to the "instagram" page
