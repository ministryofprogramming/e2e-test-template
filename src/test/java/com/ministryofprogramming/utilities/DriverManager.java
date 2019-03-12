package com.ministryofprogramming.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class DriverManager {

	private static final ConfigManager ENV_CONFIG = ConfigManager.getInstance();
	private static volatile DriverManager instance;
	private WebDriver driver;

	public static DriverManager getInstance() {
		if (instance == null) {
			synchronized (DriverManager.class) {
				if (instance == null) {
					instance = new DriverManager();
					instance.loadDriver();
				}
			}
		}
		return instance;
	}

	private WebDriver loadDriver() {

		ChromeOptions chromeOptions = new ChromeOptions();
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		switch (ENV_CONFIG.getEnvConfig("browser")) {
			case "firefox":
				System.setProperty("webdriver.gecko.driver", ENV_CONFIG.getEnvConfig("firefoxDriver"));
				firefoxOptions.addArguments("--headless");
				driver = new FirefoxDriver(firefoxOptions);
				break;
			case "chrome":
				System.setProperty("webdriver.chrome.driver", ENV_CONFIG.getEnvConfig("chromeDriver"));
				chromeOptions.addArguments("--Start-maximized");
				chromeOptions.addArguments("--no-default-browser-check");
				chromeOptions.addArguments("--disable-infobars");
				driver = new ChromeDriver(chromeOptions);
				break;
			case "zalenium":
				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
				desiredCapabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
				desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				try {
					driver = new RemoteWebDriver(new URL(ENV_CONFIG.getEnvConfig("zaleniumRemoteURL")), desiredCapabilities);
					driver.manage().window().fullscreen();
				} catch (Exception e) {
					throw new IllegalStateException("The webdriver system property must be set");
				}
				break;
			default:
				throw new RuntimeException("Unsupported webdriver: " + ENV_CONFIG.getEnvConfig("browser"));
		}
		return driver;
	}

	/** Getters **/
	public WebDriver getDriver() {
		return driver;
	}
}
