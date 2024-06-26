package data;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import domain.Events;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class FilesXML {
	
	public FilesXML(){
	}
	public void creatXML(String objectName, String fileAddress) {
		File file = new File(fileAddress);
		if(!file.exists()) {
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.newDocument();
				Element rootElement = doc.createElement(objectName);
				doc.appendChild(rootElement);
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(file);
				transformer.transform(source, result);
				System.out.println("ArchivoCreado");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void deleteXML(String fileAddress) {
		File file = new File(fileAddress);
		if(file.exists()) {
			file.delete();
		}
	}
//	--------------------------------------------------------------------------------------------
	
	public void writeXML(String fileName, String elementType, String[] dataName,String[] data ) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(fileName));
			doc.getDocumentElement().normalize();
			Element rootElement = doc.getDocumentElement();
			Element ele = doc.createElement(elementType);
			rootElement.appendChild(ele);			
			for(int i = 0; i < data.length; i++) {
				Element dato = doc.createElement(dataName[i]);
				dato.appendChild(doc.createTextNode(data[i]));
				ele.appendChild(dato);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
			transformer.transform(source, result);
			System.out.println("Registro Guardado");
		}catch(ParserConfigurationException pce){
			pce.printStackTrace();
		}catch(SAXException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(TransformerConfigurationException e) {
			e.printStackTrace();
		}catch(TransformerException e) {
			e.printStackTrace();
		}
	}
//	--EXTRAE TAMAÑO MATRIZ--------------------------------------------------------------------------------------
	
	public String searchXMLSize(String address) {
	    String information = "";
	    try {
	        File file = new File(address);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(file);
	        doc.getDocumentElement().normalize();
	        NodeList nodeList = doc.getElementsByTagName("Cities");
	        for (int i = 0; i < nodeList.getLength(); i++) {
	            Element element = (Element) nodeList.item(i);
	            NodeList childNodes = element.getChildNodes();
	            for (int j = 0; j < childNodes.getLength(); j++) {
	                if (childNodes.item(j).getNodeName().equals("size")) {
	                    information = childNodes.item(j).getTextContent();
	                    break;
	                }
	            }
	            if (!information.isEmpty()) {
	                break;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return information;
	}
//----------------AGREGA LA INFO EN LA TABLA--------------------------------------------------
	public ArrayList<Events> readXMLArrayListEvents(String FileName, String elementType) {
		ArrayList<Events> arrayLEvent = new ArrayList<Events>();
		try {
			File inputFile = new File(FileName); //new 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Raíz de los Elementos:" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(elementType);
			for (int indice = 0; indice < nList.getLength(); indice++) {
				Node nNode = nList.item(indice);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Events e = new Events(Integer.parseInt(eElement.getElementsByTagName("avenue").item(0).getTextContent()),
							Integer.parseInt(eElement.getElementsByTagName("street").item(0).getTextContent()),
							eElement.getElementsByTagName("event").item(0).getTextContent(),
							eElement.getElementsByTagName("result").item(0).getTextContent());
				arrayLEvent.add(e);
				}
			}
		} catch (Exception e) {
		}
		return arrayLEvent;
	}
//---------------------LEER EVENTOS, AYUDA A COMPARAR XML Y TABLA----------------------------------------------------------------
	public void readXMLEvents(String address, String elementType) {
	    try {
	        File inputFile = new File(address);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        System.out.println("Raiz de los Elementos: " + doc.getDocumentElement().getNodeName());
	        NodeList nList = doc.getElementsByTagName(elementType);
	        System.out.println("--------------------------------------------------");
	        for (int indice = 0; indice < nList.getLength(); indice++) {
	            Node nNode = nList.item(indice);
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                Element eElement = (Element) nNode;
	                // Obtener y mostrar los datos específicos de cada elemento
	                System.out.println("\nDatos de las personas: " + eElement.getNodeName());
	                System.out.println("AVENIDA: " + eElement.getElementsByTagName("avenue").item(0).getTextContent());
	                System.out.println("CALLE: " + eElement.getElementsByTagName("street").item(0).getTextContent());
	                System.out.println("EVENTO: " + eElement.getElementsByTagName("event").item(0).getTextContent());
	                System.out.println("RESULTADO: " + eElement.getElementsByTagName("result").item(0).getTextContent());
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
//---------------------LEER ESTADISTICA----------------------------------------------------------------
	   public String readXMLStatistics(String address, String elementType) {
	        StringBuilder result = new StringBuilder();
	        try {
	            File inputFile = new File(address);
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(inputFile);
	            doc.getDocumentElement().normalize();

	            NodeList nList = doc.getElementsByTagName(elementType);
	            result.append(" ESTADISTICA DEL JUEGO ").append("\n");
	            result.append("--------------------------------------------------\n");
	            for (int index = 0; index < nList.getLength(); index++) {
	                Node nNode = nList.item(index);
	                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element eElement = (Element) nNode;
//	                    result.append("\nElement Data: ").append(eElement.getNodeName()).append("\n");
	                    result.append("Zombies: ").append(eElement.getElementsByTagName("zombies").item(0).getTextContent()).append("\n");
	                    result.append("Humanos: ").append(eElement.getElementsByTagName("human").item(0).getTextContent()).append("\n");
	                    result.append("Aliens: ").append(eElement.getElementsByTagName("aliens").item(0).getTextContent()).append("\n");
	                    result.append("Póciones: ").append(eElement.getElementsByTagName("potion").item(0).getTextContent()).append("\n");
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return result.toString();
	    }    
}
