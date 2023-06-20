Feature: Avatar - Avatar Movie Section Video Player pop up Modal

 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option"
    When user clicks on "Video Play" "button"
    
     Scenario: User navigated to vido module and start to watch the video
       Then user navigates to "Video Modal" and start to watch the video
      
     Scenario: Verify that 'close button' is available
        When user can view "close" "button"
       
     Scenario: Verify that video modal 'title' is available
       When user can verify the "video modal" "title"
    
     Scenario: Verify that 'Watch Later' option is available 
        When user can verify the "Watch Later" "option"
     
     Scenario: Verify that 'Share' option is available 
        When user can verify the "Share" "option"
        
     Scenario: User performs the 'play and pause' operations
        When user can verify the "play" "button"
        Then user clicks on "pause" "button"
        Then user clicks on "play" "button"