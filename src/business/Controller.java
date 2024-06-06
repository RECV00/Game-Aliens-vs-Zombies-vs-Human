package business;


import data.FilesXML;
import data.Logic;
import domain.City;
import presentation.UI;

public class Controller {
	
	private UI ui;
	private City city;
	private FilesXML fXML;
	private Logic lo;
	
	
	public Controller(UI ui) {
		this.ui = ui;
		city = new City();
		fXML = new FilesXML();
		lo = new Logic(ui);
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
		
		
		lo.fillButtonMatrixWithEntitiesFromXML("Descripcion de la Ciudad.xml", Integer.parseInt(fXML.searchXML("Descripcion de la Ciudad.xml")),
				Integer.parseInt(fXML.searchXML("Descripcion de la Ciudad.xml")));
		
		
		
		
		
		this.ui.getBMovement().setOnAction(e->{
			
//			lo.fillButtonMatrixWithEntitiesFromXML("Descripcion de la Ciudad.xml", Integer.parseInt(fXML.searchXML("Descripcion de la Ciudad.xml")),
//					Integer.parseInt(fXML.searchXML("Descripcion de la Ciudad.xml")));
//            
		});
		
		
	}
	
}
