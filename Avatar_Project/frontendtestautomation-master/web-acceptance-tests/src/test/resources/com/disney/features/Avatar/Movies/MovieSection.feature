Feature: Avatar - Movie Section

  Background: 
    Given user navigates to the "Avatar_Home" page
    
    Scenario: Verify the 'Movies section' presents the option for All Movies
       When user mouse over on the "Movies section" "tab"
       Then user verifies that Movies section presents the "option" for "All Movies"
       
    Scenario: Verify the 'Movies section' presents the option for Avatar (2009)
       When user mouse over on the "Movies section" "tab"
       Then user verifies that Movies section presents the "option" for "Avatar (2009)"
       