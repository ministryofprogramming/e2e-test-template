package com.ministryofprogramming.tests.steps;

import com.ministryofprogramming.utilities.ZaleniumManager;
import static com.ministryofprogramming.utilities.PageObjects.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class Start {

    String URL;

    @Given("^User open \"([^\"]*)\" website$")
    public void user_open_website(String url) throws Throwable {
        CucumberHooks.driver.get(url);
    }

    @When("^Click on Team menu$")
    public void click_on_Team_menu() throws Throwable {
        ZaleniumManager.setCookie(CucumberHooks.driver, "User clicks on Team menu");
        URL = startPage.clickOnTeamMenu(CucumberHooks.driver);
    }


    @Then("^User should see \"([^\"]*)\"$")
    public void user_should_see_Team_page(String teamPage) throws Throwable {
        ZaleniumManager.setCookie(CucumberHooks.driver, "User should see Team page");
        Assert.assertEquals(teamPage, URL);
    }
}
