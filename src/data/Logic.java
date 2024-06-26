package data;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javafx.scene.control.Button;
import presentation.UI;
import domain.Events;
public class Logic {
	private UI ui;
	private Random random;
	private FilesXML fXML;
	private int moveCounter;
	public int posimasCount;//cantidades P
	
	public Logic(UI ui) {
		this.ui = ui;
		fXML = new FilesXML();
		this.random = new Random();
		this.moveCounter=0;
	}
// ----------------EXTRAE DATOS--------------------------------------------------------------------------------------------------------------
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
        fillEntities(ui.getButtonMatrix(), occupiedCells, edificiosCount,"E", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, arbolesCount, "T", random);
        fillEntities(ui.getButtonMatrix(), occupiedCells, humanosCount, "H", random);
        
        this.posimasCount=posimasCount;
        
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//----------------LLENA MATRIZ CON AZHET Y LAS MUEVE--------------------------------------------------------------------------------------------------------------
private void fillEntities(Button[][] buttonMatrix, boolean[][] occupiedCells, int count, String entity, Random random) {
    int row = buttonMatrix.length ;
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
     // Aplicar color a los botones con E y T
        if (entity.equals("E")) {
            buttonMatrix[x][y].setStyle("-fx-background-color: #a69cac;-fx-border-color: black; -fx-border-width: 0.2px"); // Color verde para "E"
        } else if (entity.equals("T")) {
            buttonMatrix[x][y].setStyle("-fx-background-color: #95B18F;-fx-border-color: black; -fx-border-width: 0.2px"); // Color azul para "T"
        }else {
            buttonMatrix[x][y].setStyle("-fx-border-width: 0.2px"); 
        }
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
    /// Mover los elementos H, Z y A pero la P no
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            String texto = buttonMatrix[i][j].getText();
            if (texto.contains("H") || texto.contains("Z") || texto.contains("A")) {
                for (char elemento : texto.toCharArray()) {
                	if(elemento == 'P')continue;//no se mueve P
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
    }
    // Actualizar la matriz de botones con los movimientos realizados
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            buttonMatrix[i][j].setText(newMatrix[i][j].getText());
         // Aplicar color a los botones con E y T al crear la matriz
            String texto = newMatrix[i][j].getText();
            if (texto.contains("E")) {
            	 buttonMatrix[i][j].setStyle("-fx-background-color: #a69cac;-fx-border-color: black; -fx-border-width: 0.2px"); // Color gris para "E"
            } else if (texto.contains("T")) {
                buttonMatrix[i][j].setStyle("-fx-background-color: #95B18F;-fx-border-color: black; -fx-border-width: 0.2px"); // Color verde "T"
            }else if(texto.contains("P")) {
            	buttonMatrix[i][j].setStyle("-fx-background-color: skyBlue;-fx-border-color: black; -fx-border-width: 0.2px");// Color celeste "P"
            }else {
                buttonMatrix[i][j].setStyle("-fx-border-width: 0.2px"); 
            }
        }
    }
}

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
//----------------VALIDACIONES DE LOS SUCESOS----------------------------------------------------------------------------------------
public void eliminateEntities(Button[][] buttonMatrix, char entity) {
    for (int i = 0; i < buttonMatrix.length; i++) {
        for (int j = 0; j < buttonMatrix[0].length; j++) {
            String text = buttonMatrix[i][j].getText();
            text = text.replace(Character.toString(entity),"");
            buttonMatrix[i][j].setText(text);
        }
    }
}
public void resolvePotionConflicts(Button[][] buttonMatrix) {
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
                // Iterar sobre los vecinos para resolver conflictos      
                    String evento = null;
                    String result = "Sigue Jugando";
                    String newLetter = entity; // Por defecto, no cambia
                    int num = 0;
                    // Lógica para resolver conflictos con pociones
                    if (entity.contains("PZ")) {
                        evento = "Tomar";
                        result = "Z transf H";
                        newLetter = "H"; // 1
                        num = 1;
                    } else if (entity.contains("PA")) {
                        evento = "Tomar";
                        result = "A transf H";
                        newLetter = "H"; // 1
                        num = 1;
                    }      
                    // Si se resuelve un conflicto, actualizamos la matriz actualizada y guardamos el evento
                    if (evento != null) {	
                        Events event = new Events(i, j, evento, result);
                        fXML.writeXML("Acontecimientos.xml", "Acontecimiento", event.getDataName(), event.getData());
                         assignToAdjacentEmptyButtons(updatedMatrix, i, j, newLetter, num);
                         updatedMatrix[i][j].setText("");
                    }    
                }
            }
    // Actualizar la matriz original con la matriz actualizada
    for (int i = 0; i < row; i++) {
        for (int j = 0; j < column; j++) {
            buttonMatrix[i][j].setText(updatedMatrix[i][j].getText()); // Actualiza el texto en la matriz original    
        }
    }
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
                String evento = null;
                String result = "Sigue Jugando";
                String newLetter = entity; // Por defecto, no cambia
                int num = 0;    
               // Lógica para resolver conflictos según las reglas establecidas
                if (entity.contains("AZ")) {
                    evento = "Transformar";
                    result = "A transf Z";
                    newLetter = "Z"; //2
                    num = 2;
                } else if (entity.contains("ZZ")) {
                    evento = "Pelea";
                    result = "Continúan";
                    newLetter = "Z"; //2
                    num = 2;
                } else if (entity.contains("AH")) {
                    evento = "Asesinato";
                    result = "Muere H";
                    newLetter = "A"; //1
                    num = 1;
                }else if (entity.contains("AA")) {
                    evento = "Pelean";
                    result = "Continúan";
                    newLetter = "A"; //1
                    num = 2;
                } else if (entity.contains("ZH")) {
                  evento = "Transformar";
                  result = "H transf Z";
                  newLetter = "Z"; //2
                  num=2;
              } else if (entity.contains("ZZAA")) {
                  evento = "Transformar";
                  result = "As transf Zs";
                  newLetter = "Z"; //4
                  num = 4;
              } else if (entity.contains("ZZA")) {
                  evento = "Asesinato";
                  result = "Muere A";
                  newLetter = "Z"; //2
                  num = 2;
              } else if (entity.contains("AAZ")) {
                  evento = "Asesinato";
                  result = "Muere Z";
                  newLetter ="A"; //2
                  num = 2;
              } else if (entity.contains("AAAZ") ||  entity.equals("AAAH")||entity.equals("ZZZA") || entity.equals("ZZZH")|| entity.equals("HHHA")||(entity.contains("HHHZ"))) {
                    evento = "Pelea";
                    result = "Muere especie minoría";
                    newLetter = entity.substring(0, 1); // Ajusta esta letra según sea necesario
                    num = entity.length();
              }
                if (evento != null) {
                    Events event = new Events(i, j, evento, result);
                    fXML.writeXML("Acontecimientos.xml", "Acontecimiento", event.getDataName(), event.getData());
                    assignToAdjacentEmptyButtons(updatedMatrix, i, j, newLetter, num);
                    updatedMatrix[i][j].setText("");
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
}
private void assignToAdjacentEmptyButtons(Button[][] buttonMatrix, int row, int column, String letter, int count) {
    int rows = buttonMatrix.length;
    int cols = buttonMatrix[0].length;
    
    int assignedCount = 0;
    
    // Direcciones en orden específico: arriba, abajo, derecha, izquierda
//                            A        B       D        I
    int[][] directions = { {-1, 0}, {1, 0}, {0, 1}, {0, -1} };
    
    for (int[] dir : directions) {
        int newRow = row + dir[0];
        int newCol = column + dir[1];
        
        if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
            Button adjacentButton = buttonMatrix[newRow][newCol];
            
            if (adjacentButton.getText().isEmpty()) {
                adjacentButton.setText(letter);
                assignedCount++;
                
                if (assignedCount == count) {
                    return;
                }
            }
        }
    }
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
//------------------------------FIN DEL COMUNICADO---------------------------------------------------
public boolean isGameOver(Button[][] buttonMatrix) {
	int contA =0, contZ=0, contH=0;
    for (int i = 0; i < buttonMatrix.length; i++) {
        for (int j = 0; j < buttonMatrix[i].length; j++) {
            String text = buttonMatrix[i][j].getText();          
            if (text.contains("A")) {
            	contA++;	
	        }else if (text.contains("Z")) {
	        	contZ++;
	        }else if (text.contains("H")){
	        	contH++;
	        }
        }
    }  
    if(contA!=0 && contZ!=0 && contH!=0) {
    	return false;   	
    }else {
    	return true;
    }
}
}
