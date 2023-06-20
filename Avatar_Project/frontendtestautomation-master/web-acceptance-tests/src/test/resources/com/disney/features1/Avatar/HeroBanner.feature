Feature: Obsever the Hero Banner
Background:
   Given user navigates to the "Avatar_Home" page

   Scenario: Verify the Hero Banner images are displayed
       When user conforms that Hero banner is displeyed
       Then user checks the multiple banners by clicking left or right "arrow" navigation "button"
       
   Scenario: Verify the buttons on Hero Banner 
       When user clicks on Watch Game Triller "button"
       
##   Scenario: user conforms that "facebook" nagivation menu option works
#     When user clicks on "facebook" nagivation menu option in avatar page
#     Then user navigated to "Avatar Facebook" page 

   Scenario: verify that menu navigation present in 'All Movies' page
       Then user conforms that "menu" "navigation" is present in All Movies page
  
   Scenario: Navigates to 'Facebook' Page through Facebook link
        When user click on the "Facebook" "link"
        Then user should navigates to the "facebook" page
        
    Scenario: Navigates to 'Twitter' Page through Twitter link
        When user click on the "Twitter" "link"
        Then user should navigates to the "twitter" page
        
    Scenario: Navigates to 'Instagram' Page through Instagram link
        When user click on the "Instagram" "link"
        Then user should navigates to the "instagram" page
        
    Scenario: Navigates to 'Youtube' Page through Youtube link
        When user click on the "Youtube" "link"
        Then user should navigates to the "youtube" page