@SmokeTest @RegressionTest @LoginTest
Feature: Test login functionalities

  @positive_test
 Scenario: Check login is successful with valid credentials
   Given a user is on the login page
   When user enters username "Nizam" and password "12345"
   And click on the login button
   Then user is navigated to home page

  @negative_test
  Scenario: Check login is unsuccessful with invalid credentials
    Given a user is on the login page
    When user enters username "Nizam" and password "44444"
    And click on the login button
    Then user is failed to login

    @dataDriven_test
  Scenario Outline: Check login is successful with valid credentials for multiple users
    Given a user is on the login page
    When user enters username "<username>" and password "<password>"
    And click on the login button
    Then user is navigated to home page

    Examples:
    |username|password|
    |Asma    |12345   |
    |Robert  |12345   |
    |Edward  |12345   |

      @dataTable_test
    Scenario: Check login is successful using data table
      Given a user is on the login page
      When user click on the login button upon entering credentials
      |username|password|
      |Asma    |12345   |
      Then user is navigated to home page

