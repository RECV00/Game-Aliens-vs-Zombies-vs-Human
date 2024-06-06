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
	
		System.out.println(fXML.searchXML("Descripcion de la Ciudad.xml"));
//		fXML.readXMLArrayList("Descripcion de la Ciudad.xml", "Cities")
		
		ui.setButtonMatrix(Integer.parseInt(fXML.searchXML("Descripcion de la Ciudad.xml")), 
						   Integer.parseInt(fXML.searchXML("Descripcion de la Ciudad.xml")));
		ui.setGPMatrix(ui.getButtonMatrix());
		ui.getPContainer().getChildren().add(ui.getGPMatrix());
		this.ui.getBMovement().setOnAction(e->{
			

            
		});
		
		
	}
	
}
