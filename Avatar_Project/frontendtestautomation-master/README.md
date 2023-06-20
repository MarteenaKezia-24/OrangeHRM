# qe-automation-quickstart
qe-automation-quickstart

Overview
----------
Quick Start Framework is a high level design template to develop the automated tests for restful api’s, mobile native app’s, performance and for web applications.

The purpose of this framework is to do a Rapid development of automated tests that are built on structured & well-designed framework that provides a comfort usage of readily available reusable methods in a Page Object Model Pattern.

QE Quick Start Framework is a multi-module maven project that includes different maven projects for Backend APIs, Mobile, Performance and Web tests. Each of the maven projects has its own design to automate the tests.
 
Lets see some details on the web automation framework.

Web Automation Framework
-------------------------
The Web Automation framework template is a maven java project developed with the below specifications in Page Object Model pattern:
* Java 1.8
* Springs 4.2.4
* Cucumber BDD framework
* Junit testing framework 4.12

We can find all the dependencies on the above mentioned technicalities in pom.xml under the main project qe-automation-quickstart.
Let’s see the framework elaborating in a bit technically on what it has.
 
WHAT IT HAS:
___________

Well, What does it have?! The framework contains the classes, methods and properties well-defined in different packages under the ‘test’ and ‘resources’ source folders, properly organized in a structured way of POM.

Lets see how it is being designed and what classes does it have and with what purpose.
Importantly, we see two source folders in any test automation project - src/test/java and src/test/resources.
 
test/resources source folder

^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The source folder ‘resources’ contains different packages or folders to maintain the driver executables, feature files and properties files.
* com.disney.assets: This package contains driver executables necessary for the browsers to open and run the automated tests.
* com.disney.features: This package contains all the features, containing the test scenarios of the project.
* com.disney.properties: This package contains all the necessary properties files determining the web environment, selenium grid entities, maven run-time properties or values, authentication details (if any), locator values for the web-elements and the desired test data.
 
test/java source folder

^^^^^^^^^^^^^^^^^^^^^^^

The source folder ‘test’ contains different packages with a purpose and mentioned them below, having the beauty of code:

* com.disney.config.utilities.maven
* com.disney.config.utilities.managers
* com.disney.interaction.objects
* com.disney.config
* com.disney.common
* com.disney.steps
 
com.disney.config.utilities.maven:
***************************
This package contains a class ‘MavenPropertyExtractor’. Purpose is to set the web environment via maven run-time parameters using the command-line execution or using the configured Jenkins job.

The runtime parameters are:
* working_environment (dev/ qa/ int/ demo, stag, prod), defaulted to dev
* browser (chrome/ IE/ firefox/ safari etc), defaulted to chrome
* system (Windows, Linux, MAC, Any etc), defaulted to ‘any’
* use_grid (true/ false), defaulted to false
* use_vault (true/ false), defaulted to false
* Selenium Grid information

com.disney.config.utilities.managers: 
******************************
This package contains an abstract class named ‘AbstractBaseSpringPropertiesManager’ that has the environment object binding all the dependent parameters.

It also contain other classes like EnvironmentPropertiesManager, ExternalPropertiesManager, SeleniumGridPropertiesManager, WebElementPropertiesManager, DisneyHubPropertiesManager and a sample class ‘Sample_DisneyLifeAuthPropertiesManager’ to show an example to pass on the test data from the properties file.

All these classes inherit from the class ‘AbstractBaseSpringPropertiesManager’ and the each inherited class gets the related property values.
 
com.disney.interaction.objects:
*******************************
This package has all the classes based on the web pages in the application. Each class would have all the page objects and the respective functions for test on the page.

Essentially, this package contains one classes: BasePage and classes inherited off of it.

This class 'BasePage' can be used to facilitate more methods on driver object in the any of our automation projects.
