Feature: Hero Banner - Avatar

Background:
   Given user navigates to the "Avatar_Home" page

   Scenario: Verify the Hero Banner images are displayed
       When user conforms that "Hero banner1" is displayed
       Then user checks the multiple banners by clicking left or "right arrow" navigation "button"
     
   Scenario: Verify the Watch Game Triller button on Hero Banner 
       When user clicks on "Watch Game Triller" "button"
       Then user navigates to the avatar Watch game triller window
          
   Scenario: Verify the 'Learn More' button to navigate Experiences page
       When user clicks on "Avatar" "Exhibition"
       When user clicks on "Learn More1" "button"
       Then user should navigate to the avatar "Experiences" page
 
   Scenario: Verify the 'Learn More' button to navigate Partnerships page
       When user clicks on "Avatar" "Mercedes Benz"
       When user clicks on "Learn More2" "button"
       Then user should navigate to the avatar "Partnerships" page
       
