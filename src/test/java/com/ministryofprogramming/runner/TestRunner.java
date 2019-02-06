package com.ministryofprogramming.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/com/ministryofprogramming/tests/features",
        glue = "com.ministryofprogramming.tests.steps",
        monochrome = true,
        tags = {"@dev"},
        plugin = {"pretty", "io.qameta.allure.cucumberjvm.AllureCucumberJvm"})

public class TestRunner {
}