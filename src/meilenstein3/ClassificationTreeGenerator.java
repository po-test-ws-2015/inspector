package meilenstein3;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ClassificationTreeGenerator {
	public static Document generate(List<String> elements) throws ParserConfigurationException {
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
		
		if(elements != null) {
			for(String element : elements) {
				Element classification = doc.createElement("Classification");
				classification.setAttribute("name", element);
				
				viewComposition.appendChild(classification);
				
				Element classValid = doc.createElement("Class");
				classValid.setAttribute("name", "Valid");
				
				classification.appendChild(classValid);
				
				Element classInvalid = doc.createElement("Class");
				classInvalid.setAttribute("name", "Invalid");
				
				classification.appendChild(classInvalid);
				
			}
		}
		
		return doc;
	}
}
