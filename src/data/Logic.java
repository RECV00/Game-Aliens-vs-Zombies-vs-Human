package data;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;

import javafx.scene.control.Button;
import presentation.UI;

public class Logic {
	
	private UI ui;
	
	public Logic(UI ui) {
		this.ui = ui;
	}


public void fillButtonMatrixWithEntitiesFromXML(String fileAddress, int row, int column) {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fileAddress);
        doc.getDocumentElement().normalize();

        Element rootElement = doc.getDocumentElement();
        int zombiesCount = Integer.parseInt(rootElement.getElementsByTagName("zombies").item(0).getTextContent().trim());
        int aliensCount = Integer.parseInt(rootElement.getElementsByTagName("aliens").item(0).getTextContent().trim());
//        int posimasCount = Integer.parseInt(rootElement.getElementsByTagName("potion").item(0).getTextContent().trim());
        int edificiosCount = Integer.parseInt(rootElement.getElementsByTagName("building").item(0).getTextContent().trim());
        int arbolesCount = Integer.parseInt(rootElement.getElementsByTagName("trees").item(0).getTextContent().trim());
        int humanosCount = Integer.parseInt(rootElement.getElementsByTagName("humans").item(0).getTextContent().trim());

        Random random = new Random();

        // Inicializar una matriz auxiliar para rastrear qué celdas ya están ocupadas por elementos
        boolean[][] occupiedCells = new boolean[row][column];

        // Llenar la matriz con todas las caracteristicas del xml
        fillEntities(ui.getButtonMatrix(), occupiedCells, zombiesCount, "Z", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, aliensCount, "A", random);
//        fillEntities(ui.getButtonMatrix(), occupiedCells, posimasCount, "P", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, edificiosCount, "E", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, arbolesCount, "T", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, humanosCount, "H", random);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void fillEntities(Button[][] buttonMatrix, boolean[][] occupiedCells, int count, String entity, Random random) {
    int row = buttonMatrix.length;
    int column = buttonMatrix[0].length;
    
    // Asegurar que la cantidad de entidades no sea mayor que el tamaño de la matriz
    count = Math.min(count, row * column);

    // Llenar
    for (int i = 0; i < count; i++) {
        int x, y;
        do {
            // revisa que no este ocupada
            x = random.nextInt(row);
            y = random.nextInt(column);
        } while (occupiedCells[x][y]);

            occupiedCells[x][y] = true; // si esta ocupada
        buttonMatrix[x][y].setText(entity);
    }
}


}

