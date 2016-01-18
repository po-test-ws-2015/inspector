package meilenstein3;

import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ClassificationTreeGenerator {
	
	/**
	 * Generates a Classification Tree for a given list of web elements.
	 * 
	 * @param elements
	 * @return
	 * @throws ParserConfigurationException
	 */
	public static Document generate(WebElementInformationList elements) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();
		
		Element testonaObject = doc.createElement("TestonaObject");
		doc.appendChild(testonaObject);
		
		Element tree = doc.createElement("Tree");
		testonaObject.appendChild(tree);
		
		Element viewComposition = doc.createElement("Composition");
		viewComposition.setAttribute("name", "View");
		
		tree.appendChild(viewComposition);
		
		Iterator<String> elementsIterator = elements.getStringList().iterator();
		Iterator<String> typesIterator = elements.getTypes().iterator();
		while(elementsIterator.hasNext() && typesIterator.hasNext()) {
			String element = elementsIterator.next();
			String type = typesIterator.next();
			
			Element classification = doc.createElement("Classification");
			classification.setAttribute("name", element);
			
			viewComposition.appendChild(classification);
			
			Element classValid = doc.createElement("Class");
			Element classInvalid = doc.createElement("Class");
			
			System.out.println("Type: " + type);
			
			/**
			 * Changes the classes, if element has specific type (e.g. Button can't be valid, but has states pushed or not)
			 */
			switch (type) {
				case "EditText":
					classValid.setAttribute("name", "Text Valid");
					classification.appendChild(classValid);
					
					classInvalid.setAttribute("name", "Text Invalid");
					classification.appendChild(classInvalid);
					break;
					
				case "ImageButton":
					classValid.setAttribute("name", "Button pushed");
					classification.appendChild(classValid);
					
					classInvalid.setAttribute("name", "Button not pushed");
					classification.appendChild(classInvalid);
					break;

				default:
					classValid.setAttribute("name", "Valid");
					classification.appendChild(classValid);
					
					classInvalid.setAttribute("name", "Invalid");					
					classification.appendChild(classInvalid);
					break;
			}
		}
		
		return doc;
	}
}
