package business;


import data.FilesXML;
import data.Logic;
import domain.City;
import javafx.stage.Stage;
import presentation.UI;

public class Controller {
	
	private UI ui;
	private City city;
	private FilesXML fXML;
	private Logic lo;
	private Stage secundaryStage;
	
	
	public Controller(UI ui) {
		secundaryStage = new Stage();
		this.ui = ui;
		ui.start(secundaryStage);
		city = new City();
		fXML = new FilesXML();
		lo = new Logic(ui);
//		fXML.creatXML("City", "Descripcion de la Ciudad.xml");
		getControl();
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
