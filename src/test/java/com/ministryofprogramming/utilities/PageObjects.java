package com.ministryofprogramming.utilities;


import com.ministryofprogramming.tests.pageObjects.*;

import java.io.IOException;

public class PageObjects extends Driver {

    /** Page Objects **/
    public static Start startPage = null;


    public static void loadPageObjects(Driver driver) throws IOException {
        startPage = new Start(driver);
    }

}
