@avatarallexperiences
Feature: Avatar Content - The Official Site for Avatar

  Background: 
   Given the user is on the "avatar_home" page
    When user mouse over on the "Experiences" "tab" experiences button
   And user click on the "All Experiences" "tab" experiences button
 
  Scenario: Cofirms that 'Images' displays correctly
    And user confirm that "allexperiences" "image1" displays correctly all experiences page
   Then user confirm that "allexperiences" "image2" displays correctly all experiences page
   
    Scenario: Cofirms that 'learn more Button' works correctly
    And user click on the "allexperiences" "pandoralearnmore" in all experiences page
   Then user confirm "pandora" "learnmore" navigates to experiences page

	Scenario: Cofirms that 'learn more Button' works correctly
    And user click on the "allexperiences" "exhibitionlearnmore" in all experiences page
    And user click on "continue" to navigate into learnmore from all experiences page
    Then user confirm "exhibition" "learnmore" navigates to experiences page

	Scenario: Cofirms that 'description' displays correctly
    Then user confirm that "allexperiences" "description1" displays correctly all experiences page
    Then user confirm that "allexperiences" "description2" displays correctly all experiences page
    
    Scenario: Navigates to 'Contact Us' Page through footer link
    And user click on the "Contact Us" "footer link" in avatar all experiences page
    Then user should navigates to the "Contact Us" in avatar all experiences page
   
    Scenario: Navigates to 'Terms of Use' Page through footer link
    And user click on the "Terms of Use" "footer link" in avatar all experiences page
    Then user should navigates to the "Terms of Use" in avatar all experiences page
    
    Scenario: Navigates to 'Privacy Policy' Page through footer link
    And user click on the "Privacy Policy" "footer link" in avatar all experiences page
    Then user should navigates to the "Privacy Policy" in avatar all experiences page
 
    Scenario: Navigates to 'Your California Privacy Rights' Page through footer link
    And user click on the "Your California Privacy Rights" "footer link" in avatar all experiences page
    Then user should navigates to the "Your California Privacy Rights" in avatar all experiences page
 
    Scenario: Navigates to 'Children’s Privacy Policy' Page through footer link
    And user click on the "Children’s Privacy Policy" "footer link" in avatar all experiences page
    Then user should navigates to the "Children’s Privacy Policy" in avatar all experiences page

    Scenario: Navigates to 'Interest-Based Ads' Page through footer link
    And user click on the "Interest-Based Ads" "footer link" in avatar all experiences page
    Then user should navigates to the "Interest-Based Ads" in avatar all experiences page
    
    Scenario: Navigates to 'Do Not Sell My Info' Page through footer link
    And user click on the "Do Not Sell My Info" "footer link" in avatar all experiences page
    Then user should navigates to the "Do Not Sell My Info" in avatar all experiences page
    