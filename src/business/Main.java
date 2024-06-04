package business;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.UI;

public class Main extends Application{

	public void start(Stage primaryStage) {
		
		UI ui = new UI();
		ui.start(primaryStage);
		
		Controller control = new Controller(ui);
		control.getControl();

	}

	public static void main(String[] args){
		launch(args);
		
	}

}
