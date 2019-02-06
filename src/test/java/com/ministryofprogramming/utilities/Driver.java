package com.ministryofprogramming.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Driver extends EventFiringWebDriver {

	private static final WebDriver REAL_DRIVER = DriverManager.getInstance().getDriver();
	protected WebDriverWait wait;
	protected JavascriptExecutor jsExecutor;
	protected WebElement target;

	private static final Thread CLOSE_THREAD = new Thread() {
		@Override
		public void run() {
			REAL_DRIVER.quit();
		}
	};

	static {
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
	}

	public Driver() {
		super(REAL_DRIVER);
		PageFactory.initElements(REAL_DRIVER, this);
	}

	@Override
	public void quit() {
		if (Thread.currentThread() != CLOSE_THREAD) {
			throw new UnsupportedOperationException("You shouldn't quit this WebDriver. It's shared and will quit when the JVM exits.");
		}
		super.quit();
	}

	public void waitForElement(WebElement element) {
		wait = new WebDriverWait(REAL_DRIVER, 15);
		target = wait.until(ExpectedConditions.visibilityOf(element));
		jsExecutor = ((JavascriptExecutor) REAL_DRIVER);
	}

	public void sendKeysToWebElement(WebElement element, String textToSend) throws Exception {
		this.waitForElement(element);
		element.clear();
		element.sendKeys(textToSend);
	}

}
