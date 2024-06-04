package business;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.UI;

public class Main extends Application{

	public void start(Stage primaryStage) {
		
		UI gui = new UI();
		gui.start(primaryStage);
		
		Controller control = new Controller(gui);
		control.getControl();

	}

	public static void main(String[] args){
		launch(args);
		
	}

}
