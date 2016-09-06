import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Random;

public class CreateXmlFile {
	
	public static void main(String argv[]) {

		try {
			final int NO_OF_ELEMENT = 10000;
			Random random = new Random();
		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();

			// root element
			Element dataset = doc.createElement("dataset");
			doc.appendChild(dataset);
			
			Element size = doc.createElement("size");
			size.appendChild(doc.createTextNode(""+NO_OF_ELEMENT));
			dataset.appendChild(size);
			
			Element array = doc.createElement("array");
			dataset.appendChild(array);

			// large set of number element
			for (int i=0; i < NO_OF_ELEMENT ; ++i) {
	
				int num = random.nextInt(NO_OF_ELEMENT);
				Element number = doc.createElement("number");
				number.appendChild(doc.createTextNode(""+num));
				array.appendChild(number);
			}
		
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("numbers.xml"));
			transformer.transform(source, result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
