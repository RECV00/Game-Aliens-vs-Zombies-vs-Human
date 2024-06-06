package presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import domain.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UI {

	private City city;
	private Scene myScene;
	private Pane pContainer;
	private GridPane gPMatrix;
	
	private TableView<City> tableView;
	private ObservableList<City> cityList1;
	
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
		setTableView();
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
		this.pContainer.getChildren().add(tableView);
//		pContainer.getChildren().add(gPMatrix);
	}
	
	public Pane getPContainer() {
		return pContainer;
	}
//--------------------------------------------------------------------------------
	
	public void setBMovement(String name) {
		bMovement = new Button(name);
		bMovement.setPrefSize(100, 20);
		bMovement.setTranslateX(60);
		bMovement.setTranslateY(400);
	}
	
	public Button getBMovement() {
		return bMovement;
	}
	
	public void setBExterminationZombies(String name) {
		bExterminationZombies = new Button(name);
		bExterminationZombies.setPrefSize(150, 20);
		bExterminationZombies.setTranslateX(420);
		bExterminationZombies.setTranslateY(400);
	}
	
	public Button getBExterminationZombies() {
		return bExterminationZombies;
	}
	
	public void setBExterminationAliens(String name) {
		bExterminationAliens = new Button(name);
		bExterminationAliens.setPrefSize(150, 20);
		bExterminationAliens.setTranslateX(600);
		bExterminationAliens.setTranslateY(400);
	}
	
	public Button getBExterminationAliens() {
		return bExterminationAliens;
	}
//----------------------------------------------------------------------------------	
	
	  public void setButtonMatrix(int row, int column, ArrayList<City> cityList) {
	        buttonMatrix = new Button[row][column];
	        
	    
	        if (((row >= 3) && (row <= 5)) && ((column >= 2) && (column <= 4))) {
	       
	            Collections.shuffle(cityList);
	    
	            int k = 0;
	            for (int i = 0; i < buttonMatrix.length; i++) {
	                for (int j = 0; j < buttonMatrix[0].length; j++) {
	                    if (k < cityList.size()) {
	                
                        buttonMatrix[i][j] = new Button();
	                        buttonMatrix[i][j].setPrefSize(100, 20);
	                        k++;
	                        } else {
	                       
	                        buttonMatrix[i][j] = new Button("Empty");
	                        buttonMatrix[i][j].setPrefSize(100, 20);
	                    }
	                }
	            }
	        } else {
	            notify("Las Dimensiones del Edificio son Incorrectas");
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
	
//----------------------------TABLEVIEW-----------------------------------------------------------------
	public void setTableView() {
        tableView = new TableView<>();
        tableView.setPrefSize(500, 200);
        tableView.setTranslateX(200);
        tableView.setTranslateY(450);
        
        TableColumn<City, Integer> column1 = new TableColumn<>("Avenida");
       column1.setCellValueFactory(new PropertyValueFactory<>("avenue"));

        TableColumn<City, Integer> column2 = new TableColumn<>("Calle");
        column2.setCellValueFactory(new PropertyValueFactory<>("street"));

        TableColumn<City, String> column3 = new TableColumn<>("Evento");
        column3.setCellValueFactory(new PropertyValueFactory<>("event"));
        
        TableColumn<City, String> column4 = new TableColumn<>("Resultado");
        column3.setCellValueFactory(new PropertyValueFactory<>("result"));

        tableView.getColumns().addAll(column1, column2, column3,column4);
        
        cityList1 = FXCollections.observableArrayList();
        System.out.println(cityList1);
        tableView.setItems(cityList1);
    }
    
    public TableView<City> getTVCity(){
    	
    	return this.tableView;
    }

    public void dataTableView(ArrayList<City> citys) {
        cityList1.setAll(citys);
    }
	
//-----------------------------------------------------------------------------------------------------
	public void notify(String menssage) {
		JOptionPane.showMessageDialog(null,menssage);
	}
}