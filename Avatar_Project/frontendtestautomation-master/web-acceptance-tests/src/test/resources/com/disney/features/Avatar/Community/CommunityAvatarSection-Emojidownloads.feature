Feature: Avatar - Community Avatar Section - Emoji Download

Background: 
    Given user navigates to the "Avatar_Home" page
    When user clicks on "Tab" "Community"
    When user moves to the "Emoji Download" "module"
   
    Scenario: Vefify that 'Emoji Images' are diaplayed 
       When user can view the "Male Emoji" "img"
       When user can view the "Female Emoji" "img"
         
    Scenario: Verify that 'Title' and 'Description' is visible
       Then user can view "Emoji Download" "Title" 
       Then user can view "Emoji Download" "Description" 
      
    Scenario: Verify that 'download' button is visible
       When user can view "Female Emoji Download" "button"
       When user can view "Male Emoji Download" "button"
     
    Scenario: Navigated to the separate window through 'Female Download' button 
       When user clicks on "Female Emoji Download" "button"
       Then user can view the "Female Emoji" "img" in separate window
      
    Scenario: Navigated to the separate window through 'Male Download' button 
       When user clicks on "Male Emoji Download" "button"
       Then user can view the "Male Emoji" "img" in separate window
       
       