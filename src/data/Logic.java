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
	private Random random;
	
	public Logic(UI ui) {
		this.ui = ui;
		this.random = new Random();
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
    
    // Cantidad de entidades no sea mayor que el tamaño de la matriz
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
public void moveEntities(Button[][] buttonMatrix) {
    int row = buttonMatrix.length;
    int column = buttonMatrix[0].length;

    String[][] newMatrix = new String[row][column];
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            newMatrix[i][j] = "";
        }
    }

    // Copiar matriz actual a newMatrix
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            newMatrix[i][j] = buttonMatrix[i][j].getText();
        }
    }

    // Mover todas las letras A, Z y H simultáneamente
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            String text = buttonMatrix[i][j].getText();
            if (text.contains("A") || text.contains("Z") || text.contains("H")) {
                moveEntity(newMatrix, text, i, j, buttonMatrix);
            }
        }
    }

    // Actualizar la matriz de botones con los movimientos realizados
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            buttonMatrix[i][j].setText(newMatrix[i][j]);
        }
    }
}
//--------------------------------------------------------------------------------------------------------
// validaciones
private void moveEntity(String[][] newMatrix, String entity, int x, int y, Button[][] buttonMatrix) {
    int row = newMatrix.length;
    int column = newMatrix[0].length;

    // Lista de direcciones posibles: arriba, abajo, izquierda, derecha
    int[] directions = { -1, 1, -1, 1 };
    int[] randomOrder = { 0, 1, 2, 3 };
    shuffleArray(randomOrder);

    for (int index : randomOrder) {
        int dir = directions[index];
        if (dir == -1 || dir == 1) {
            int newX = x + dir;
            int newY = y;
            if (isValidMove(newMatrix, newX, newY, row, column, buttonMatrix)) {
                newMatrix[newX][newY] += entity;
                newMatrix[x][y] = newMatrix[x][y].replace(entity, "");
                return;
            }

            newX = x;
            newY = y + dir;
            if (isValidMove(newMatrix, newX, newY, row, column, buttonMatrix)) {
                newMatrix[newX][newY] += entity;
                newMatrix[x][y] = newMatrix[x][y].replace(entity, "");
                return;
            }
        }
    }
}
//-----------------------------------------------------------------------------------------------------------
private boolean isValidMove(String[][] matrix, int x, int y, int row, int column, Button[][] buttonMatrix) {
    return x >= 0 && x < row && y >= 0 && y < column &&
            !matrix[x][y].contains("E") && !matrix[x][y].contains("T");
}

private void shuffleArray(int[] array) {
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--) {
        int index = random.nextInt(i + 1);
        // Swap
        int temp = array[index];
        array[index] = array[i];
        array[i] = temp;
    }
}
//-------------------------------------------------------------------------------------------------------------
public void eliminateEntities(Button[][] buttonMatrix, String... entities) {
    for (int i = 0; i < buttonMatrix.length; i++) {
        for (int j = 0; j < buttonMatrix[0].length; j++) {
            for (String entity : entities) {
                buttonMatrix[i][j].setText(buttonMatrix[i][j].getText().replace(entity, ""));
            }
        }
    }
}


}

