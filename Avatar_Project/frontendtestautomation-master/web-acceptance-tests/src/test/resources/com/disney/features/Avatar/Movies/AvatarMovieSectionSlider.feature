Feature: Avatar - Avatar Section Slider 

 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option"
    When user moves to the "slider page" "module"
  
    Scenario: Verify that 'Left and Right' slider is visible and work
        When user can view the "Left Arrow" "Slider"
        Then user clicked on the "Slider" "Left Arrow" button
        When user can view the "Right Arrow" "Slider"
        Then user clicked on the "Slider" "Right Arrow" button
    
    Scenario: Verify that Images,Title and Description is visible in slider
       Then user should view the images,Title and Description under "Slider" module