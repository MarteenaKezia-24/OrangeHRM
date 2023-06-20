Feature: Avatar - All Movie Section

 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "All Movies" "option"
    
    Scenario: Redirecting to 'All Movies' page through all movies option
       Then user redirects to the "All Movies" page
    
    Scenario: Verify that All images loads correctly in 'All Movies' page
       When user conforms that "img" "Movie avatar" is present in All Movies page
       When user conforms that "img" "Movie sequals" is present in All Movies page
   
    Scenario: navigates to 'Disney+' page through StreamNow button
       When user clicks on "Stream Now" "button"
       Then user navigates to "Disney+" page
           
    Scenario: redirecting to "Movies Avatar" page through LearnMore button
        When user clicks on "Learn More3" "button"
        Then user should navigate to the avatar "movies" page
       
    Scenario: Verify that all footer links are visible in the 'All Movies' page 
        When user conforms that "Terms of Use" "footer link" is visible
        When user conforms that "Privacy Policy" "footer link" is visible
        When user conforms that "Childrens online Privacy Policy" "footer link" is visible
        When user conforms that "Your California Privacy Rights" "footer link" is visible
        When user conforms that "Avatar at shopDisney" "footer link" is visible
        When user conforms that "Interest-Based Ads" "footer link" is visible
        When user conforms that "Do Not Sell My Personal info" "footer link" is visible
        When user conforms that "Facebook" "footer link" is visible
        When user conforms that "Twitter" "footer link" is visible
        When user conforms that "Instagram" "footer link" is visible
        When user conforms that "Youtube" "footer link" is visible