@trackpage-frontend
Feature: TrackPage - capturing the disney frontend trackPage events

  @trackpage-fe
  Scenario: Disney Home trackpage
    scenario_label=disney_home
    When the user navigates to the "disney_home" page
    Then capture all the "disney_us" track events

  @trackpage-fe
  Scenario: Disney Lol trackpage
    scenario_label=disney_lol
    When the user navigates to the "disney_lol" page
    Then capture all the "disney_lol" track events

  @trackpage-fe
  Scenario: Disney Lol Games trackpage
    scenario_label=disney_lol_games
    When the user navigates to the "disney_lol_games" page
    Then capture all the "disney_lol_games" track events

  @trackpage-fe
  Scenario: Disney Video trackpage
    scenario_label=disney_video
    When the user navigates to the "disney_video" page
    Then capture all the "disney_video" track events

  @trackpage-fe
  Scenario: Disney Movies trackpage
    scenario_label=disney_movies
    When the user navigates to the "disney_movies" page
    Then capture all the "disney_movies" track events

  @trackpage-fe
  Scenario: Disney Movies in theaters trackpage
    scenario_label=disney_movies_theaters
    When the user navigates to the "disney_movies_intheaters" page
    Then capture all the "disney_movies_intheaters" track events

  @trackpage-fe
  Scenario: Disney Princess trackpage
    scenario_label=disney_princess
    When the user navigates to the "disney_princess" page
    Then capture all the "disney_princess" track events

  @trackpage-fe
  Scenario: Disney Princess rapunzel trackpage
    scenario_label=disney_princess_rapunzel
    When the user navigates to the "disney_princess_rapunzel" page
    Then capture all the "disney_princess_rapunzel" track events

  @trackpage-fe
  Scenario: Disney Toy Story trackpage
    scenario_label=disney_toy_story
    When the user navigates to the "disney_toystory" page
    Then capture all the "disney_toystory" track events

  Scenario: Starwars Home trackpage
    scenario_label=starwars_home
    When the user navigates to the "starwars_home" page
    Then capture all the "starwars_home" track events

  Scenario: Starwars News trackpage
    scenario_label=starwars_news
    When the user navigates to the "starwars_news" page
    Then capture all the "starwars_news" track events

  Scenario: Babble Home trackpage
    scenario_label=babble_home
    When the user navigates to the "babble_home" page
    Then capture all the "babble_home" track events

  @trackpage-fe
  Scenario: Disney Oh My trackpage
    scenario_label=disney_oh_my
    When the user navigates to the "disney_ohmy" page
    Then capture all the "disney_ohmy" track events

  @trackpage-fe
  Scenario: Disney Family trackpage
    scenario_label=disney_family
    When the user navigates to the "disney_family" page
    Then capture all the "disney_family" track events
