Feature: Avatar - Avatar Movie Section Trailer

 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option"
    When user moves to the "Triller" "module"
    
   Scenario: Verify that 'Triller' image is displayed 
       When user can view the "Triller" "img"
     
   Scenario: Verify that 'Video Play' button is displayed 
       When user can view the "Video Play" "button"
    
   Scenario: Verify that 'Video Tite' is displayed 
       When user can view the "Video" "title"
    
   Scenario: User wnats to play the video 
       When user clicks on "Video Play" "button"
       Then user navigates to "Video Module" and start to watch the video
     