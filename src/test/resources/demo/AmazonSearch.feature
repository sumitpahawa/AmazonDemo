@Search
Feature: Search and purchase product
  Scenario: Select the product from search list and add it to cart and purchase it.
    Given User is on home page
    When Search the product with "65 inch TV"
    And Select any of the random product from search list and add it to cart and complete the purchase process

