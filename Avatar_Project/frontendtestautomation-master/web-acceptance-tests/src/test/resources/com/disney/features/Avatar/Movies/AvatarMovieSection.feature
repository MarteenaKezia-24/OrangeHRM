Feature: Avatar - Avatar (2009) Section

 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option"
  
    Scenario: Redirecting to 'Avatar (2009)' page through Avatar (2009) option
       Then user redirects to the "Avatar (2009)" page
       
    Scenario: Verify the "Hero Banner" image is displayed in Avatar (2009) page 
       When user conforms that "Hero banner4" is displayed
       
   Scenario: navigates to 'Disney+' page through StreamNow button
       When user clicks on "Stream Anytime" "button"
       Then user navigates to "Disney+" page