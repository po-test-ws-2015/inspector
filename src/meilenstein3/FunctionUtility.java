package meilenstein3;

import io.appium.java_client.AppiumDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FunctionUtility {
	
	/**
	 * Gibt alle Webelemente im aktuellen Sichtbereich des Emulators in einer Liste zurueck
	 * @param hf 
	 * @return Liste mit WebElement-en
	 */
	public static List<WebElement> getAllElementsinView(AppiumDriver hf) {
		return hf.findElements(By.xpath("//*"));
	}
	
}
