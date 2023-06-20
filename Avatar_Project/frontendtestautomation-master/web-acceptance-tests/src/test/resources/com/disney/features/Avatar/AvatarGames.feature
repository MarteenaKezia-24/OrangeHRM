@avatargames
Feature: Avatar Content - The Official Site for Avatar

  Background: 
   Given the user is on the "avatar_home" page
   When user click on the "Avatar Games" "tab" games button
   
    Scenario: Cofirms that 'Images' displays correctly
    And user confirm that "games" "image1" displays correctly in avatar games page
    Then user confirm that "games" "image2" displays correctly in avatar games page
   
    Scenario: Cofirms that 'Watch game trailer Button' works correctly
    And user click on the "button" "watchgametrailer" in games page
    Then user confirm "watchgametrailer" navigates to correct page
    
    Scenario: Cofirms that 'learn more Button' works correctly
    And user click on the "games" "frontierslearnmore" in games page
    And user click on "continue" to navigate into learnmore page
    Then user confirm "frontierslearnmore" navigates to correct page
    
    Scenario: Cofirms that 'learn more Button' works correctly
    And user click on the "games" "risinglearnmore" in games page
    And user click on "continue" to navigate into learnmore page
    Then user confirm "risinglearnmore" navigates to correct page

	Scenario: Cofirms that 'description' displays correctly
    Then user confirm that "games" "description1" displays correctly in avatar games page
    Then user confirm that "games" "description2" displays correctly in avatar games page
    
    Scenario: Navigates to 'Contact Us' Page through footer link
    And user click on the "Contact Us" "footer link" in avatar games page
    Then user should navigates to the "Contact Us" in avatar games page
    
    Scenario: Navigates to 'Terms of Use' Page through footer link
    And user click on the "Terms of Use" "footer link" in avatar games page
    Then user should navigates to the "Terms of Use" in avatar games page
    
    Scenario: Navigates to 'Privacy Policy' Page through footer link
    And user click on the "Privacy Policy" "footer link" in avatar games page
    Then user should navigates to the "Privacy Policy" in avatar games page
    
    Scenario: Navigates to 'Your California Privacy Rights' Page through footer link
    And user click on the "Your California Privacy Rights" "footer link" in avatar games page
    Then user should navigates to the "Your California Privacy Rights" in avatar games page
    
    Scenario: Navigates to 'Children’s Privacy Policy' Page through footer link
    And user click on the "Children’s Privacy Policy" "footer link" in avatar games page
    Then user should navigates to the "Children’s Privacy Policy" in avatar games page
    
    Scenario: Navigates to 'Interest-Based Ads' Page through footer link
    And user click on the "Interest-Based Ads" "footer link" in avatar games page
    Then user should navigates to the "Interest-Based Ads" in avatar games page
    
    Scenario: Navigates to 'Do Not Sell My Info' Page through footer link
    And user click on the "Do Not Sell My Info" "footer link" in avatar games page
    Then user should navigates to the "Do Not Sell My Info" in avatar games page