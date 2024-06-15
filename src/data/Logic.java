package data;

import java.util.ArrayList;
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
import domain.City;
import domain.Events;
public class Logic {
	
	private UI ui;
	private Random random;
	private Events events;
	private FilesXML fXML;
	private int moveCounter;
	private int posimasCount;//cantidades P
	
	public Logic(UI ui) {
		this.ui = ui;
		events = new Events();
		fXML = new FilesXML();
		this.random = new Random();
		this.moveCounter=0;
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
        int posimasCount = Integer.parseInt(rootElement.getElementsByTagName("potion").item(0).getTextContent().trim());
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
        fillEntities(ui.getButtonMatrix(), occupiedCells, edificiosCount,"E", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, arbolesCount, "T", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, humanosCount, "H", random);

        this.posimasCount=posimasCount;
        
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

    Button[][] newMatrix = new Button[row][column];
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            newMatrix[i][j] = new Button(buttonMatrix[i][j].getText());
        }
    }


    /// Mover los elementos H, Z y A
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            String texto = buttonMatrix[i][j].getText();
            if (texto.contains("H") || texto.contains("Z") || texto.contains("A")) {
                for (char elemento : texto.toCharArray()) {
                    moverElemento(i, j, newMatrix, String.valueOf(elemento));
                }
            }
        }
    }
    moveCounter++;
  //Agrega P cada 5 movimientos si hay disponibles
    if(moveCounter % 5 == 0 && posimasCount > 0) {
    	addPotion(newMatrix);
    	posimasCount--;// reduce la cantidad restante de P
    	fXML.updateXML(posimasCount,"Descripcion de la Ciudad.xml"); //Actualiza el xml
    	
    }
    // Actualizar la matriz de botones con los movimientos realizados
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            buttonMatrix[i][j].setText(newMatrix[i][j].getText());
        }
    }
}
//--------------------------------------------------------------------------------------------------------
// validaciones
//private void moveEntity(String[][] newMatrix, String entity, int x, int y, Button[][] buttonMatrix) {
//    int row = newMatrix.length;
//    int column = newMatrix[0].length;
//
//    // Lista de direcciones posibles: arriba, abajo, izquierda, derecha
//    int[] directions = { -1, 1, -1, 1 };
//    int[] randomOrder = { 0, 1, 2, 3 };
//    shuffleArray(randomOrder);
//
//    for (int index : randomOrder) {
//        int dir = directions[index];
//        if (dir == -1 || dir == 1) {
//            int newX = x + dir;
//            int newY = y;
//            if (isValidMove(newMatrix, newX, newY, row, column, buttonMatrix)) {
//                newMatrix[newX][newY] += entity;
//                newMatrix[x][y] = newMatrix[x][y].replace(entity, "");
//                return;
//            }
//
//            newX = x;
//            newY = y + dir;
//            if (isValidMove(newMatrix, newX, newY, row, column, buttonMatrix)) {
//                newMatrix[newX][newY] += entity;
//                newMatrix[x][y] = newMatrix[x][y].replace(entity, "");
//                return;
//            }
//        }
//    }
//}

private  void moverElemento(int x, int y, Button[][] nuevaMatriz, String elemento) {
    Random random = new Random();
    boolean movimientoValido = false;
    
   
    
if(nuevaMatriz.length >= 4){
	while (!movimientoValido) {
        int direccion = random.nextInt(4) + 1;
        int nuevoX = x, nuevoY = y;
        switch (direccion) {
            case 1: // Arriba
                nuevoX = x - 1;
                break;
            case 2: // Abajo
                nuevoX = x + 1;
                break;
            case 3: // Izquierda
                nuevoY = y - 1;
                break;
            case 4: // Derecha
                nuevoY = y + 1;
                break;
        }
        if (isValidMove(nuevoX, nuevoY, nuevaMatriz)) {
            String currentText = nuevaMatriz[nuevoX][nuevoY].getText();
            nuevaMatriz[nuevoX][nuevoY].setText(currentText + elemento);
            String originalText = nuevaMatriz[x][y].getText();
            nuevaMatriz[x][y].setText(originalText.replace(elemento, ""));
            movimientoValido = true;
        }
	}
      
    }
}
//-----------------------------------------------------------------------------------------------------------
private  boolean isValidMove(int x, int y, Button[][] buttonMatrix) {
	int row = buttonMatrix.length;
    int column = buttonMatrix[0].length;
    if (x < 0 || x >= row || y < 0 || y >= column) {
        return false;
    }

    String text = buttonMatrix[x][y].getText();
    return !(text.contains("E") || text.contains("T"));
}
private void addPotion(Button[][] buttonMatrix) {
    int row = buttonMatrix.length;
    int column = buttonMatrix[0].length;
    boolean added = false;

    while (!added) {
        int x = random.nextInt(row);
        int y = random.nextInt(column);

        if (buttonMatrix[x][y].getText().isEmpty()) {
            buttonMatrix[x][y].setText("P");
            added = true;
        }
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
private void removeLetters(Button[][] buttonMatrix, int row, int column, String letters) {
    for (char letter : letters.toCharArray()) {
        buttonMatrix[row][column].setText(buttonMatrix[row][column].getText().replace(String.valueOf(letter), ""));
    }
}

private List<String> getNeighbors(Button[][] buttonMatrix, int i, int j) {
    List<String> neighbors = new ArrayList<>();
    int rows = buttonMatrix.length;
    int columns = buttonMatrix[0].length;

    if (i > 0) neighbors.add(buttonMatrix[i - 1][j].getText());
    if (i < rows - 1) neighbors.add(buttonMatrix[i + 1][j].getText());
    if (j > 0) neighbors.add(buttonMatrix[i][j - 1].getText());
    if (j < columns - 1) neighbors.add(buttonMatrix[i][j + 1].getText());

    return neighbors;
}

public void resolveConflicts(Button[][] buttonMatrix) {
    int row = buttonMatrix.length;
    int column = buttonMatrix[0].length;
    Button[][] updatedMatrix = new Button[row][column];

    // Copiar la matriz original en la matriz actualizada
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            updatedMatrix[i][j] = new Button(buttonMatrix[i][j].getText()); // Copia el texto del botón original
        }
    }

    // Iterar sobre la matriz para resolver conflictos
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            String entity = updatedMatrix[i][j].getText();

            // Si el botón tiene más de una entidad, resolver conflictos
            if (entity.length() > 1) {
                List<String> neighbors = getNeighbors(buttonMatrix, i, j);

                // Iterar sobre los vecinos para resolver conflictos
                for (String neighbor : neighbors) {
                    String evento = null;
                    String result = "Sigue Jugando";
                   
                    String newLetter = entity; // Por defecto, no cambia
                    
                    int num = 0;

                    // Lógica para resolver conflictos según las reglas establecidas
                    if (entity.contains("A") && neighbor.contains("Z")) {
                        evento = "Transformar";
                        result = "A transf Z";
                        newLetter = "Z"; //2
                        num = 2;
                    } else if (entity.contains("Z") && neighbor.contains("Z")) {
                        evento = "Pelea";
                        result = "Continúan";
                        newLetter = "Z"; //2
                        num = 2;
                    } else if (entity.contains("A") && neighbor.contains("H")) {
                        evento = "Asesinato";
                        result = "Muere H";
                        newLetter = "A"; //1
                        num = 1;
                    } else if (entity.contains("A") && neighbor.contains("A")) {
                        evento = "Pelea";
                        result = "Continúan";
                        newLetter = "A"; //2
                    } else if (entity.contains("Z") && neighbor.contains("H")) {
                        evento = "Transformar";
                        result = "H transf Z";
                        newLetter = "Z"; //2
                        num = 2;
                    } else if (entity.contains("Z") && neighbor.contains("Z") && neighbor.contains("A") && neighbor.contains("A")) {
                        evento = "Transformar";
                        result = "A transf Z";
                        newLetter = "Z"; //4
                        num = 4;
                    } else if (entity.contains("Z") && neighbor.contains("Z") && neighbor.contains("A")) {
                        evento = "Asesinato";
                        result = "Muere A";
                        newLetter = "Z"; //2
                        num = 2;
                    } else if (entity.contains("A") && neighbor.contains("A") && neighbor.contains("Z")) {
                        evento = "Asesinato";
                        result = "Muere Z";
                        newLetter ="Z"; //2
                        num = 2;
                    } else if ((entity.contains("AAA") || entity.contains("ZZZ") || entity.contains("HHH")) && !entity.contains(neighbor)) {
                        evento = "Pelea";
                        result = "Muere especie minoría";
                        newLetter = entity; // Ajusta esta letra según sea necesario
                        num = 3;
                    } else if (entity.contains("Z") && neighbor.contains("P")) {
                        evento = "Tomar";
                        result = "Z transf H";
                        newLetter = "Z"; //1
                        num = 1;
                    } else if (entity.contains("A") && neighbor.contains("P")) {
                        evento = "Tomar";
                        result = "A transf H";
                        newLetter = "Z"; //1
                        num = 1;
                    } else if (entity.contains("P") && neighbor.contains("Z")) {
                        evento = "Tomar";
                        result = "Z transf H";
                        newLetter = "H"; //1
                        num = 1;
                        removeLetters(updatedMatrix,i,j,"PZ");
                    }else if (entity.contains("P") && neighbor.contains("A")) {
                        evento = "Tomar";
                        result = "A transf H";
                        newLetter = "H"; //1
                        num = 1;
                        removeLetters(updatedMatrix,i,j,"PA");
                    }else if (entity.contains("P") && neighbor.contains("H")) {
                        evento = "No pasa nada";
                        result = "Continúa";
                        newLetter = "PH"; //1
                        num = 1;
                    }             else {
                        // Si no cumple ninguna condición, separamos las letras
                        separateAndAssignToAdjacentButtons(updatedMatrix, i, j, entity);
                    }

                    // Si se resuelve un conflicto, actualizamos la matriz actualizada y guardamos el evento
                    if (evento != null) {
                        Events event = new Events(i, j, evento, result);
                        fXML.writeXML("Acontecimientos.xml", "Acontecimiento", event.getDataName(), event.getData());
                        fXML.readXML("Acontecimientos.xml", "Acontecimiento");
                        assignToAdjacentEmptyButtons(updatedMatrix, i, j, newLetter, num);
                        updatedMatrix[i][j].setText(String.valueOf(newLetter)); // Actualiza el texto en la matriz actualizada
                    }
                }
            }
        }
    }

    // Actualizar la matriz original con la matriz actualizada
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            buttonMatrix[i][j].setText(updatedMatrix[i][j].getText()); // Actualiza el texto en la matriz original
        }
    }

    ui.updateButtonMatrix(buttonMatrix); // Actualiza la interfaz de usuario con la matriz original actualizada
}

private void separateAndAssignToAdjacentButtons(Button[][] updatedMatrix, int row, int column, String entities) {
    int rows = updatedMatrix.length;
    int cols = updatedMatrix[0].length;

    int count = entities.length();
    int assignedCount = 0;
    // Buscar en las 4 direcciones adyacentes
    int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

    for (int[] dir : directions) {
        int newRow = row + dir[0];
        int newCol = column + dir[1];

        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
            Button adjacentButton = updatedMatrix[newRow][newCol];
            if (adjacentButton.getText().isEmpty()) {
                adjacentButton.setText(Character.toString(entities.charAt(assignedCount)));
                assignedCount++;
                
                if (assignedCount == count) {
                    break;
                }
            }
        }
    }
}



private void assignToAdjacentEmptyButtons(Button[][] buttonMatrix, int row, int column, String letter, int count) {
    int rows = buttonMatrix.length;
    int cols = buttonMatrix[0].length;

    int assignedCount = 0;
    // Buscar en las 4 direcciones adyacentes
    int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

    for (int[] dir : directions) {
        int newRow = row + dir[0];
        int newCol = column + dir[1];

        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
            Button adjacentButton = buttonMatrix[newRow][newCol];
            if (adjacentButton.getText().isEmpty()) {
                adjacentButton.setText(letter);
                assignedCount++;
                
                if (assignedCount == count) {
                    break;
                }
            }
        }
    }
}




}
