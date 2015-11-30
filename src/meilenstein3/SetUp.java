package meilenstein3;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;

/**
 * Klasse zum Aufbau einer Verbindung zum Server.
 * - Zur Auslagerung / Wiederverwaendbarkeit
 * @author Jakob Andersen
 *
 */
public final class SetUp {

	private SetUp() {
		throw new IllegalAccessError();
	}
	
	public static AppiumDriver create() {
		File appDir = new File("Application");
		File app = new File(appDir, "de.akquinet.campusapp.haw_hamburg.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", "Sample");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("app", app.getAbsolutePath());
		
		AppiumDriver driver = null;
		try {
			driver = new AppiumDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return driver;
	}
	
	
}
