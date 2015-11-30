package meilenstein3;

import javafx.scene.control.TextArea;

/**
 * Klasse repräsentiert ein Textfeld, welches den Verlauf der getaetigten Aktionen darstellt.
 * @author Jakob Andersen
 *
 */

public class CodeCreator {

	private final String SEND = "Tippe Text %s in Element %s ein.\n";
	private final String CLICK =  "Tippe Element %s an.\n";

	private TextArea tArea;

	public CodeCreator(TextArea tArea) {
		this.tArea = tArea;
	}

	/**
	 * Fuegt dem Verlauf eine "Klick"-Aktion hinzu
	 * @param s Name des Elementes mit Index
	 */
	public void addClick(String s) {
		System.out.println(s);
		this.tArea.appendText(String.format(CLICK, s));
	}

	/**
	 * Fuegt dem Verlauf eine "Send"-Aktion hinzu
	 * @param s
	 * @param text
	 */
	public void addSend(String s, String text) {
		this.tArea.appendText(String.format(SEND, text, s));
	}

	/**
	 * Erstellt einen neuen Verlauf
	 */
	public void clear() {
		this.tArea = new TextArea();
	}

}
