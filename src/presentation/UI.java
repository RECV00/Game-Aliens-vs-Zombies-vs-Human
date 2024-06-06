package presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import domain.City;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UI {

	private City city;
	private Scene myScene;
	private Pane pContainer;
	private GridPane gPMatrix;
	
	private Button bMovement;
	private Button bExterminationZombies;
	private Button bExterminationAliens;
	private Button buttonMatrix[][];
   
	private ArrayList<City> cityList;

	public UI() {
		city = new City();
		setBMovement("Mover");
		setBExterminationZombies("Exterminio de Zombies");
		setBExterminationAliens("Exterminio de Aliens");
		setPContainer();
		setMyScene(pContainer);
		}
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Matriz de Botones");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(700);
		primaryStage.setScene(getMyScene());
		primaryStage.show();
	}
	
	public void setMyScene(Pane pContainer) {
		myScene = new Scene(pContainer);
		
	}
	public Scene getMyScene() {
		return myScene;
	}
	
	public void setPContainer() {
		pContainer = new Pane();
		pContainer.getChildren().add(bMovement);
		pContainer.getChildren().add(bExterminationZombies);
		pContainer.getChildren().add(bExterminationAliens);
//		pContainer.getChildren().add(gPMatrix);
	}
	
	public Pane getPContainer() {
		return pContainer;
	}
//--------------------------------------------------------------------------------
	
	public void setBMovement(String name) {
		bMovement = new Button(name);
		bMovement.setPrefSize(100, 20);
		bMovement.setTranslateX(500);
		bMovement.setTranslateY(60);
	}
	
	public Button getBMovement() {
		return bMovement;
	}
	
	public void setBExterminationZombies(String name) {
		bExterminationZombies = new Button(name);
		bExterminationZombies.setPrefSize(100, 20);
		bExterminationZombies.setTranslateX(100);
		bExterminationZombies.setTranslateY(60);
	}
	
	public Button getBExterminationZombies() {
		return bExterminationZombies;
	}
	
	public void setBExterminationAliens(String name) {
		bExterminationAliens = new Button(name);
		bExterminationAliens.setPrefSize(300, 20);
		bExterminationAliens.setTranslateX(500);
		bExterminationAliens.setTranslateY(60);
	}
	
	public Button getBExterminationAliens() {
		return bExterminationAliens;
	}
//----------------------------------------------------------------------------------	
//	public void setButtonMatrix(int row,int column,ArrayList<Person> personList) {
//		
//		buttonMatrix = new Button[row][column];
//		int k=0;
//		if (((row >=3) && (row <=5)) && ((column >=2)&&(column <=4))) {
//		for(int i=0; i<buttonMatrix.length;i++) {
//			for(int j=0; j<buttonMatrix[0].length;j++) {
//				int randomIndex = (int) (Math.random() * personList.size());
//				
//				buttonMatrix[i][j] = new Button(personList.get(k).getId());
//				buttonMatrix[i][j].setPrefSize(100, 20);
//				k++;
//			}
//			}
//		}else{
//			notify("Las Dimenciones del Edificio son Incorrectas");
//		}
//	}
	
	  public void setButtonMatrix(int row, int column, ArrayList<City> cityList) {
	        buttonMatrix = new Button[row][column];
	        
	    
	        if (((row >= 3) && (row <= 5)) && ((column >= 2) && (column <= 4))) {
	       
//	            Collections.shuffle(personList);
//	    
//	            int k = 0;
//	            for (int i = 0; i < buttonMatrix.length; i++) {
//	                for (int j = 0; j < buttonMatrix[0].length; j++) {
//	                    if (k < personList.size()) {
//	                
//	                        buttonMatrix[i][j] = new Button(personList.get(k).getId());
//	                        buttonMatrix[i][j].setPrefSize(100, 20);
//	                        k++;
//	                    } else {
//	                       
//	                        buttonMatrix[i][j] = new Button("Empty");
//	                        buttonMatrix[i][j].setPrefSize(100, 20);
//	                    }
//	                }
//	            }
//	        } else {
//	            notify("Las Dimensiones del Edificio son Incorrectas");
	        }
	    }
	  
	  public String getButtonId(int row, int column) {
		  
		    if (row >= 0 && row < buttonMatrix.length && column >= 0 && column < buttonMatrix[0].length) { 
		     return buttonMatrix[row][column].getText(); 
		      
		    } else {
		        return "Posición de botón inválida";
		    }
		}
    
	public Button[][] getButtonMatrix(){
		return buttonMatrix;
	}
	
	public void setGPMatrix( Button[][] buttonMatrix) {
		gPMatrix = new GridPane(buttonMatrix.length,buttonMatrix[0].length);
		gPMatrix.setTranslateX(120);
		gPMatrix.setTranslateY(200);
		
		for(int i=0; i<buttonMatrix.length;i++) {
			for(int j=0; j<buttonMatrix[0].length;j++) {
				
				gPMatrix.add(buttonMatrix[i][j], j, i);
			}
		}
	}
	
	public GridPane getGPMatrix() {
		return this.gPMatrix;
	}
	public void notify(String menssage) {
		JOptionPane.showMessageDialog(null,menssage);
	}
}