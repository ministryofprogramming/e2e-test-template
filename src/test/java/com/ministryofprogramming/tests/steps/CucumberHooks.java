package com.ministryofprogramming.tests.steps;

import com.ministryofprogramming.utilities.*;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.io.IOException;

public class CucumberHooks {

    public static Driver driver = new Driver();

    @Before(order = 0)
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }

    @Before(order = 1)
    public void loadPageObjects() throws IOException {
        PageObjects.loadPageObjects(driver);
    }

    /**
     * We are using this function to mark test failed or success.
     **/
    @After(order = 0)
    public void zaleniumTestStatus(Scenario scenario) {
        ZaleniumManager.setZaleniumTestStatus(driver, scenario);
    }
}
