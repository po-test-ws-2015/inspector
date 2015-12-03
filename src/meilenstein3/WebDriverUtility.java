package meilenstein3;

import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;

/**
 * Hilfsklasse mit Methoden zur einfachen Steuerung des Drivers
 * @author Jakob Andersen
 *
 */
public class WebDriverUtility {

	public static final String XPATH_SELECTOR = "//*";

	public static List<WebElement> getAllWebElements(AppiumDriver driver) {
		return driver.findElements(By.xpath(XPATH_SELECTOR));
	}

	/**
	 * Scrollt zu einen gewissen Webelement
	 * @param driver
	 * @param webElement
	 */
	public static void scrollTo(AppiumDriver driver, WebElement webElement) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		HashMap<String, String> scrollObject = new HashMap<String, String>();
		scrollObject.put("direction", "down");
		scrollObject.put("element", ((RemoteWebElement) webElement).getId());
		js.executeScript("mobile: scroll", scrollObject);
	}

	/**
	 * Erzeugt ein Image Objekt des Bildschirmes, des Emulators
	 * Wartet bis alle Elemente geladen sind.
	 * @param webDriver
	 * @return Image
	 */
	public static Image takeScreenshot(AppiumDriver webDriver) {
		waitForPageLoad(webDriver);
		return takeScreenshotInst(webDriver);
	}

	/**
	 * Erzeugt ein Image Objekt des Bildschirmes, des Emulators
	 * @param webDriver
	 * @return Image
	 */
	public static Image takeScreenshotInst(AppiumDriver webDriver) {
		return new Image(webDriver.getScreenshotAs(OutputType.FILE).toURI().toString());
	}

	/**
	 * Wartet bis alle Elemente geladen sind
	 * @param webDriver
	 */
	public static void waitForPageLoad(AppiumDriver webDriver) {
		new WebDriverWait(webDriver, 100).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(XPATH_SELECTOR)));
	}

	/**
	 * Setzt die Applikation auf den Ausgangspunkt zurueck
	 * @param webDriver
	 */
	public static void resetApp(AppiumDriver webDriver) {
		webDriver.resetApp();
	}

	/**
	 * Fuehrt eine "Klick"-Aktion auf einem Element aus
	 * @param Name des Elementes + Index
	 * @param driver
	 * @return
	 */
	public static String clickOn(String s, AppiumDriver driver) {
		driver.findElement(By.id(s)).click();
		return "driver.findElement(By.id(" + s + ").click();";
	}

	/**
	 * Schickt einem Webelement einen Text
	 * @param s
	 * @param driver
	 * @param text
	 * @return
	 */
	public static String sendTo(String s, AppiumDriver driver, String text) {
		driver.findElement(By.id(s)).sendKeys(text);
		return "driver.findElement(By.id(" + s + ").sendKeys(" + text + ");";
	}


}
