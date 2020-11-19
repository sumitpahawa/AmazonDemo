@Search
Feature: Search and purchase product

  Scenario: Login to amazon mobile application and search foran item and add to cart and purchase it”
    Given User is on home page
    When click on signIn button
    And Enter the valid credential and click on login button.
    Then User successfully logged in to the application
    When User enter the "65 inch TV" in search box
    And Select any of the product from search list and add it to cart and complete the purchase process

