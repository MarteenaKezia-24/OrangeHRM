Feature: Avatar Content - The Official Site for Avatar

  Background: 
   Given the user is on the "avatar_home" page
    When user mouse over on the "Experiences" "tab" experiences button
   And user click on the "experiencesworldofavatar" "tab" experiences button
   @demo
   Scenario: Cofirms that 'Images' displays in world avatar 
    And user confirm that "1" "worldavatarimage" in avatar world avatar page
    Then user confirm that "1worldavatartitle" displays title on image
    Then user confirm that "1worldavatardescription" displays description on image