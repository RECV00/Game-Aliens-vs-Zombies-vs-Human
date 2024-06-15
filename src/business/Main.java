 package business;

import javafx.application.Application;
import javafx.stage.Stage;
import presentation.UI1;

public class Main extends Application{

	public void start(Stage primaryStage) {
		
		UI1 ui1 = new UI1();
		ui1.start(primaryStage);
		
		ControllerUI1 control = new ControllerUI1(ui1);
		control.getControl();

	}

	public static void main(String[] args){
		launch(args);
		
	}

}
