Feature: Avatar - Avatar Movie Section Image Gallery
 
 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option" 
 
    Scenario: Verify that 'Left' and 'Right' navigation button are displayed and work
        When user can view "Image Slider" "Left Arrow" button
        Then user clicks on "Image Slider" "Left Arrow" button
        When user can view "Image Slider" "Right Arrow" button
        Then user clicks on "Image Slider" "Right Arrow" button 
       
    Scenario: Verify that Images,Title and Image number is visible in image gallery
       Then user should view the images,Title and Image number under "Image Slider" module
      
    Scenario: Verify that user Clicking on image opens 'gallery modal'
       When user moves to the "Img Gallery" "Module"
       When user clicks on "Img" "Gallery" 
       Then user navigates to "Img Gallery" "Modal Slides"
    