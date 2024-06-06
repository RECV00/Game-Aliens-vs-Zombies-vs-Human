package business;


import data.FilesXML;
import domain.City;
import presentation.UI;

public class Controller {
	
	private UI ui;
	private City city;
	private FilesXML fXML;
	
	
	public Controller(UI ui) {
		this.ui = ui;
		fXML = new FilesXML();
//		fXML.creatXML("People", "Document Person.xml");
		
	}
//--------------------------------------------------------------------------------------------------
	public void getControl() {
	
		
		this.ui.getBMovement().setOnAction(e->{
			

            
		});
		
		
	}
	
}
