package presentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JOptionPane;

import domain.Events;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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

	private Events event;
	private Scene myScene;
	private Pane pContainer;
	private GridPane gPMatrix;
	
	private TableView<Events> tableView;
	private ObservableList<Events> eventList1;
	
	private Button bMovement;
	private Button bExterminationZombies;
	private Button bExterminationAliens;
	private Button buttonMatrix[][];
   
	private ArrayList<Events> eventList;

	public UI() {
		event = new Events();
		setBMovement("Mover");
		setBExterminationZombies("Exterminar Zombies");
		setBExterminationAliens("Exterminar Aliens");
		setTableView();
		setPContainer();
		setMyScene(pContainer);
		}
	
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Alien vs Zombie vs Human");
		primaryStage.setMinWidth(1200);
		primaryStage.setMinHeight(700);
		primaryStage.setScene(getMyScene());
		getMyScene().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
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
		pContainer.getChildren().add(tableView);
//		pContainer.getChildren().add(gPMatrix);
	}
	
	public Pane getPContainer() {
		return pContainer;
	}
//--------------------------------------------------------------------------------
	
	public void setBMovement(String name) {
		bMovement = new Button(name);
		bMovement.setPrefSize(100, 20);
		bMovement.setTranslateX(1060);
		bMovement.setTranslateY(550);
	}
	
	public Button getBMovement() {
		return bMovement;
	}
	
	public void setBExterminationZombies(String name) {
		bExterminationZombies = new Button(name);
		bExterminationZombies.setPrefSize(150, 20);
		bExterminationZombies.setTranslateX(700);
		bExterminationZombies.setTranslateY(550);
	}
	
	public Button getBExterminationZombies() {
		return bExterminationZombies;
	}
	
	public void setBExterminationAliens(String name) {
		bExterminationAliens = new Button(name);
		bExterminationAliens.setPrefSize(150, 20);
		bExterminationAliens.setTranslateX(900);
		bExterminationAliens.setTranslateY(550);
	}
	
	public Button getBExterminationAliens() {
		return bExterminationAliens;
	}
//----------------------------------------------------------------------------------	
	
	public void setButtonMatrix(int row,int column) {
		buttonMatrix = new Button[row][column];
		for(int i=0; i<buttonMatrix.length;i++) {
			for(int j=0; j<buttonMatrix[0].length;j++) {
				
				buttonMatrix[i][j] = new Button(" ");
				buttonMatrix[i][j].setPrefSize(10,10);
			}
		}
	}
    
	public Button[][] getButtonMatrix(){
		return buttonMatrix;
	}
	
	public void setGPMatrix(Button[][] buttonMatrix) {
	    gPMatrix = new GridPane();
	    gPMatrix.setTranslateX(2);
	    gPMatrix.setTranslateY(2);
	    gPMatrix.setHgap(0); // Sin espacio horizontal entre las celdas
	    gPMatrix.setVgap(0); // Sin espacio vertical entre las celdas
	    gPMatrix.setPadding(new Insets(0)); // Sin margen

	    for (int i = 0; i < buttonMatrix.length; i++) {
	        for (int j = 0; j < buttonMatrix[0].length; j++) {
	            Button button = buttonMatrix[i][j];
	            button.setPrefSize(64, 20); // Ajusta el tamaño de los botones como desees
	            gPMatrix.add(button, j, i);
	        }
	    }
	}
	public GridPane getGPMatrix() {
		return this.gPMatrix;
	}
	 // Método para actualizar la matriz de botones en el GridPane
    public void updateButtonMatrix(Button[][] buttonMatrix) {
        if (gPMatrix != null) {
            gPMatrix.getChildren().clear(); // Borra todos los botones actuales del GridPane

            // Vuelve a agregar los botones actualizados
            for (int i = 0; i < buttonMatrix.length; i++) {
                for (int j = 0; j < buttonMatrix[0].length; j++) {
                    Button button = buttonMatrix[i][j];
                    gPMatrix.add(button, j, i);
                }
            }
        }
    }
//----------------------------TABLEVIEW-----------------------------------------------------------------
	public void setTableView() {
        tableView = new TableView<>();
        tableView.setPrefSize(600, 140);
        tableView.setTranslateX(20);
        tableView.setTranslateY(510);
        
        TableColumn<Events, Integer> column1 = new TableColumn<>("Avenida");
       column1.setCellValueFactory(new PropertyValueFactory<>("avenue"));
       column1.setPrefWidth(100);

        TableColumn<Events, Integer> column2 = new TableColumn<>("Calle");
        column2.setCellValueFactory(new PropertyValueFactory<>("street"));
        column2.setPrefWidth(100);
        
        TableColumn<Events, String> column3 = new TableColumn<>("Evento");
        column3.setCellValueFactory(new PropertyValueFactory<>("event"));
        column3.setPrefWidth(150);
        
        TableColumn<Events, String> column4 = new TableColumn<>("Resultado");
        column4.setCellValueFactory(new PropertyValueFactory<>("result"));
        column4.setPrefWidth(250);
        
        tableView.getColumns().addAll(column1, column2, column3,column4);
        
        eventList1 = FXCollections.observableArrayList();
        System.out.println(eventList1);
        tableView.setItems(eventList1);
    }
    
    public TableView<Events> getTVCity(){
    	
    	return this.tableView;
    }

    public void dataTableView(ArrayList<Events> Acontecimiento) {
    	eventList1.setAll(Acontecimiento);
    }
	
//-----------------------------------------------------------------------------------------------------
	public void notify(String menssage) {
		JOptionPane.showMessageDialog(null,menssage);
	}
}