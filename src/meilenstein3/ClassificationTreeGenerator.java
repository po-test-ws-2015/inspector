package meilenstein3;

import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ClassificationTreeGenerator {
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
