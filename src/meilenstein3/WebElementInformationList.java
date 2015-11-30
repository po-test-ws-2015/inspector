package meilenstein3;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

/**
 * Multi-Listen Datenstruktur von Webelementen.
 * Speichert dessen Namen, Index, Koordinaten und Dimensionen zur einfachen und schnellen Abfrage
 * @author Jakob Andersen
 *
 */
public class WebElementInformationList {

	protected static String[] tagNameBlacklist = {"ImageView", "TextView", "ViewFlipper", "ProgressBar", "WebView", "Layout"};

	private List<Dimension> lDimensions;
	private List<Point> lPoints;
	private List<String> lStrings;
	private int size;

	public WebElementInformationList(List<WebElement> listW) {
		this.lStrings = new ArrayList<String>();
		this.lPoints = new ArrayList<Point>();
		this.lDimensions = new ArrayList<Dimension>();
		this.size = 0;
		listW.forEach(this::insert);
	}

	/**
	 * Fuegt ein neues Webelement hinzu.
	 * Indizierung der Webelemente wird vorgenommen 
	 * @param we
	 */
	private void insert(WebElement we) {
		try {
			String resourceId = we.getAttribute("resourceId");
			String tagName = we.getTagName();

			if(checkElement(tagName, resourceId))
			{
				this.size++;
				this.lStrings.add(format(resourceId));

				lDimensions.add(we.getSize());
				lPoints.add(we.getLocation());
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private boolean checkElement(String tagName, String resourceId) {

		System.out.println("resourceId: " + resourceId);

		if(resourceId.length() == 0) {
			return false;
		}

		if(resourceId.startsWith("android:id")) {
			return false;
		}

		System.out.println("Tag: " + tagName);

		for(String blacklist : tagNameBlacklist) {
			if(tagName.contains(blacklist)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Konkatenation des Namens und Indexes eines Strings
	 * @param el
	 * @return
	 */
	private String format(String el) {
		return String.format("%s", el.replace("de.akquinet.campusapp.haw_hamburg:id/", ""));
	}

	public String getString(int i) {
		return this.lStrings.get(i);
	}

	public Point getPoint(int i) {
		return this.lPoints.get(i);
	}

	public Dimension getDimension(int i) {
		return this.lDimensions.get(i);
	}

	public List<String> getStringList() {
		return this.lStrings;
	}

	/**
	 * Erstellt ein Rectangle zu einem Listeneintrag. 
	 * @param s
	 * @param c
	 * @return
	 */
	public Rectangle getRactangel(String s, Color c) {
		for (int i = 0; i < size; i++) {
			if(s.equals(lStrings.get(i))) {
				Rectangle r = new Rectangle(lPoints.get(i).x, lPoints.get(i).y, lDimensions.get(i).width, lDimensions.get(i).height);
				r.setFill(Color.TRANSPARENT);
				r.setStroke(c);
				r.setStrokeWidth(10);
				return r;
			}
		}
		return new Rectangle(0,0,0,0); 
	}

}
