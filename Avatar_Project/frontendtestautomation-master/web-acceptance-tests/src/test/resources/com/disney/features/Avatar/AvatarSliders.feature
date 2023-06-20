@avatar-slider
Feature: Avatar Content - The Official Site for Avatar

  Background: 
    Given the user is on the "avatar_home" page
    
    @avatar
    Scenario: Page content 'Image Slider' Left navigation
    And user click on the Latest news "Image Slider" "left arrow" button
    Then user should check "Image Slider" slider title
   @avatar 
    Scenario: Page content 'Image Slider' Right navigation
    And user click on the Latest news "Image Slider" "right arrow" button
    Then user should check "Image Slider" slider title
   @avatar
   Scenario: Verify that Title and Description is visible in Image slider
	Then user should view the Title and Description under "Image Slider" module  