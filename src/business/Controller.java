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
		city = new City();
		fXML = new FilesXML();
//		fXML.creatXML("City", "Descripcion de la Ciudad.xml");
		
	}
//--------------------------------------------------------------------------------------------------
	public void getControl() {

//city= new City(20,20,20,15,15,15,15);
//fXML.writeXML("Descripcion de la Ciudad.xml", "Cities",city.getDataName(), city.getData());
	
		
		
		this.ui.getBMovement().setOnAction(e->{
			

            
		});
		
		
	}
	
}
