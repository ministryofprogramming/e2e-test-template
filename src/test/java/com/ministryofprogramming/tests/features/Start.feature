@local @dev @prod @start
Feature: Start Feature
  This is your first feature

  Scenario Outline: User access to website
    Given User open <URL> website
    When Click on Team menu
    Then User should see <TeamPage>

    Examples:
      |URL|TeamPage|
      |"https://ministryofprogramming.com"|"https://ministryofprogramming.com/team/"|
