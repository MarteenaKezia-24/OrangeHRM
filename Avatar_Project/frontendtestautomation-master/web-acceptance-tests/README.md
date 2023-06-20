#Web Acceptance Tests

This module is dedicated to web tests. It has an example that authenticates to https://the-internet.herokuapp.com.

##Layout
    .
    ├── java
    │   └── com
    │       └── disney
    │           ├── common              # Common step definitions for this project
    │           │   └── universal       # Universal step definitions usable accross any project (Copied in from Sequioia)
    │           ├── config              # Test suite configuration
    │           │   └── utilities       # Support for the config files
    │           ├── interactions        
    │           │   └── objects         # Pages in the Page Object model are stored here 
    │           └── steps               # Page specific step definitions
    └── resources
        └── com
            └── disney
                ├── drivers             # Driver binaries storage location
                ├── features            # Cucumber feature storage location
                └── properties          # Test suite property files storage location
        
##How to Use
This framework follows the [page object model](https://seleniumeasy.com/selenium-tutorials/page-object-model-framework-introduction).

* Pages are located in java/com/disney/interactions/objects
* Steps are located in java/com/disney/steps and java/com/disney/common/*
* Features are located in resources/com/disney/features

There is currently one example built into the framework that goes to [the internet](https://the-internet.herokuapp.com) and does the basic auth. 

TL;DR

* Create a new feature making up new Gherkin or using preexisting ones
* Create classes for each page of the website you want to test 
* Create steps for the new Gherkin that use the page class that was just created

##Questions, Suggestions, Comments?
Email [DIQualityEngineering@disney.com](mailto:DIQualityEngineering@disney.com)
        
##To Dos

* Fix maven dependencies
* Fix Environment Autowire
