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

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
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
			
//			Attr attr = doc.createAttribute(dataName[0]);
//			attr.setValue(data[0]);
//			ele.setAttributeNode(attr);
			
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
	
//	----------------------------------------------------------------------------------------
	
	public void readXML(String address, String elementType) {
		try {
			File inputFile =  new File(address);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			
			System.out.println("Raiz de los Elementos:" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName(elementType);
			
			System.out.println("--------------------------------------------------");
			Node nNode = null;
			for(int indice =0; indice <nList.getLength(); indice++) {
				nNode = nList.item(indice);
				System.out.println("\nDatos de las personas: " + nNode.getNodeName());
				
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println("Cedula: " + eElement.getAttribute("id"));
				System.out.println("Nombre: " + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("Edad: " + eElement.getElementsByTagName("age").item(0).getTextContent());
				
			}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	public ArrayList<Person> readXMLArrayList(String FileName, String elementType) {
//		ArrayList<Person> arrayLPeople = new ArrayList<Person>();
//		
//		try {
//			File inputFile = new File(FileName); //new 
//			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//			Document doc = dBuilder.parse(inputFile);
//			doc.getDocumentElement().normalize();
//
//			System.out.println("Raíz de los Elementos:" + doc.getDocumentElement().getNodeName());
//			NodeList nList = doc.getElementsByTagName(elementType);
//			System.out.println("----------------------------");
//
//			for (int indice = 0; indice < nList.getLength(); indice++) {
//				Node nNode = nList.item(indice);
//				System.out.println("\nDatos de las Personas: " + nNode.getNodeName());
//
//				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//					Element eElement = (Element) nNode;
//					
//					Person p =new Person(
//							eElement.getAttribute("id"),
//							eElement.getElementsByTagName("name").item(0).getTextContent(),
//							Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent()));
//					
//					arrayLPeople.add(p);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return arrayLPeople;
//	}
}
