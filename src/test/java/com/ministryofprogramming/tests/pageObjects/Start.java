package com.ministryofprogramming.tests.pageObjects;

import com.ministryofprogramming.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.IOException;

public class Start extends Base {

    public Start(Driver driver) throws IOException{
        super(driver);
    }

    @FindBy(xpath = "//*[@id=\"navbar\"]/ul/li[3]/a") WebElement teamMenu;

    public String clickOnTeamMenu(Driver driver) throws Exception{
        teamMenu.click();
        String currentURL = driver.getCurrentUrl();
        return currentURL;
    }
}
