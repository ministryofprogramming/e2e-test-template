package com.ministryofprogramming.utilities;

import cucumber.api.Scenario;
import org.openqa.selenium.Cookie;

public class ZaleniumManager {

    /** We are using this function for setup Zalenium message for each test step **/
    public static void setCookie(Driver driver, String newCookie) {
        Cookie cookie;
        cookie = new Cookie("zaleniumMessage", newCookie);
        driver.manage().addCookie(cookie);
    }

    public static void setZaleniumTestStatus(Driver driver, Scenario scenario) {
        if (!scenario.isFailed()) {
            Cookie cookie = new Cookie("zaleniumTestPassed", "true");
            driver.manage().addCookie(cookie);
        } else {
            Cookie cookie = new Cookie("zaleniumTestPassed", "false");
            driver.manage().addCookie(cookie);
        }
    }
}
