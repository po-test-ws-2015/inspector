package meilenstein3;

import org.openqa.selenium.WebElement;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Klasse repraesentiert Webelemente dargestellt durch grafische Flaechen 
 * @author Jakob Andersen
 *
 */
public class ElementArea extends Rectangle {

	private WebElement web;
	
	public double x;
	public double y;
	
	public double w;
	public double h;
	
	/**
	 * Erzeugt eine ElementArea durch Uebergabe eines Webelementes und Farbe der Flaeche
	 * @param w Das Webelement
	 * @param c Das Color Objekt für die Farbe
	 */
	public ElementArea(WebElement w, Color c) {
		super(w.getLocation().x, w.getLocation().y, w.getSize().width, w.getSize().height);
		this.web = w;
		this.setFill(Color.TRANSPARENT);
	    this.setStroke(c);
	    this.setStrokeWidth(10);
	    
	    this.x = w.getLocation().x;
	    this.y = w.getLocation().y;
	    
	    this.w = w.getSize().width;
	    this.h = w.getSize().height;
	    
	}
	
	public WebElement getWebelement() {
		return web;
	}
	
}
