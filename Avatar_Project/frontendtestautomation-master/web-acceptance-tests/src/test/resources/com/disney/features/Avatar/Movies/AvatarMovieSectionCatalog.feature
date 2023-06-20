Feature: Avatar - Avatar (2009) Section Catalog

 Background: 
    Given user navigates to the "Avatar_Home" page
    When user mouse over on the "Movies section" "tab"
    When user clicks on "Avatar (2009)" "option"
    When user moves to the "catalog" "module"
    
    Scenario: Verify that 'Catalog image' displays in catalog module
       When user conforms that "Catalog image" is displayed
  
    Scenario: verify that 'Title' and 'Description' of catalog is visible 
       When user can view the "Title" of "Catalog" 
       When user can view the "Description" of "Catalog" 
   
    Scenario: verify that 'Rating' is present in catalog module
       When user can view the "Rating" of "Catalog"
       
    Scenario: verify that 'Logos' are present in catalog module
        When user can view the "PG13 Logo" of "Catalog"
        When user can view the "LightStrome Logo" of "Catalog"
        When user can view the "20thCen Logo" of "Catalog"
      
    Scenario: verify that 'Credits' are displayed correctly in catalog module
        When user can view the "Credits" of "Catalog"
      
    Scenario: redirects to 'motionpictures' page through motionpictures link
        When user clicks on "motionpictures" "link"
        Then user redirects to the "motionpictures" page
      
    Scenario: redirects to 'filmratings' page through filmratings link
        When user clicks on "filmratings" "link"
        Then user redirects to the "filmratings" page