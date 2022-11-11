package monaco;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class LectorDOMObjectes {
	
	public void llegeixEmpleats() throws IOException {
		try (FileInputStream reader = new FileInputStream("C:/Users/tonla/empleats_XML.xml")) {
			
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(reader);

			// document.getDocumentElement().getNodeName() �s "empleat"
			NodeList empleatsNodeList = document.getElementsByTagName("empleat");
			
			for (int i=0; i<empleatsNodeList.getLength(); i++) { // NodeList no implementa Iterable
				Node empleatNode = empleatsNodeList.item(i);

				if (empleatNode.getNodeType() == Node.ELEMENT_NODE) {
					Element empleatElement = (Element) empleatNode;
					
					empleats.add(new Employee(
							getNodeValue("nom", empleatElement),
							getNodeValue("cognom", empleatElement),
							Integer.parseInt(getNodeValue("edat", empleatElement)),
							Float.parseFloat(getNodeValue("mida", empleatElement)),
							getNodeValue("carrec", empleatElement)
					));
				}
			}	
		} catch (IOException | ParserConfigurationException | SAXException e) {
			throw new IOException(e);
		}
	}

	private static String getNodeValue(String etiqueta, Element element) {
		NodeList nodeList = element.getElementsByTagName(etiqueta).item(0).getChildNodes();
		Node node = nodeList.item(0);
		return node.getNodeValue();
	}
	
	public void mostraEmpleats() {
		for (Employee e : empleats)
			System.out.println(e);
	}
}

