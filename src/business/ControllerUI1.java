package business;

import presentation.UI;
import presentation.UI1;
public class ControllerUI1 {
	private UI1 ui1;
	private UI ui;
	public ControllerUI1(UI1 ui1) {
		this.ui1=ui1;
	}
//-----------LLAMADO DE LA SEGUNDA VENTANA Y SEGUNDA CONTROLLER------------------------------------------
	public void getControl() {
		this.ui1.getBPlay().setOnAction(e->{		
				ui= new UI();
				new Controller(ui);
				});
			}
}
