# AmazonDemo

To Run the code right click on project and run as mvn clean and then mvn test 
After succesfull execution of code test-output will be create which have the extent htl report with screenshot of all steps


src/test/resources/features - all the cucumber features files (files .feature ext) goes here.
src/test/java/StepDefinition - you can define step defintion under this package for your feature steps.
src/test/java/main/api/apps  - this package contains the Page objects of app
src/main/java/core - This pages contain the log  magage and all commomn in intreaction method that are required to drive the automation and also have many adb method required to drive the script


