package business;


import java.awt.Button;

import data.FilesXML;
import data.Logic;
import domain.City;
import domain.Statistics;
import javafx.stage.Stage;
import presentation.UI;

public class Controller {
	
	private UI ui;
	private City city;
	private FilesXML fXML;
	private Logic lo;
	private Statistics sta;
	private Stage secundaryStage;

	
	public Controller(UI ui) {
		secundaryStage = new Stage();
		this.ui = ui;
		ui.start(secundaryStage);
		city = new City();
		fXML = new FilesXML();
		lo = new Logic(ui);
		sta = new Statistics();
//		fXML.creatXML("City", "Descripcion de la Ciudad.xml");
		fXML.deleteXML("Acontecimientos.xml" );
		fXML.deleteXML("Estadisticas del Juego.xml" );
		
		fXML.creatXML("Acontecimiento","Acontecimientos.xml" );
		fXML.creatXML("Statistics","Estadisticas del Juego.xml" );
		getControl();
	}
//--------------------------------------------------------------------------------------------------
	public void getControl() {
		
//city= new City(20,20,20,15,15,15,15);
//fXML.writeXML("Descripcion de la Ciudad.xml", "Cities",city.getDataName(), city.getData());
	
		System.out.println(fXML.searchXMLSize("Descripcion de la Ciudad.xml"));
		
//		fXML.readXMLArrayList("Descripcion de la Ciudad.xml", "Cities")
		
		ui.setButtonMatrix(Integer.parseInt(fXML.searchXMLSize("Descripcion de la Ciudad.xml")), 
						   Integer.parseInt(fXML.searchXMLSize("Descripcion de la Ciudad.xml")));
		ui.setGPMatrix(ui.getButtonMatrix());
		ui.getPContainer().getChildren().add(ui.getGPMatrix());
		lo.fillButtonMatrixWithEntitiesFromXML("Descripcion de la Ciudad.xml", Integer.parseInt(fXML.searchXMLSize("Descripcion de la Ciudad.xml")),
				Integer.parseInt(fXML.searchXMLSize("Descripcion de la Ciudad.xml")));
		
//----------------------------------------------------------------------------------------------------------------		
		
		this.ui.getBMovement().setOnAction(e->{
			
				
				lo.resolvePotionConflicts(ui.getButtonMatrix());
				lo.resolveConflicts(ui.getButtonMatrix());
				lo.moveEntities(ui.getButtonMatrix());
				ui.updateButtonMatrix(ui.getButtonMatrix());				
				ui.dataTableView(fXML.readXMLArrayListEvents("Acontecimientos.xml", "Acontecimiento"));
				
				if(lo.isGameOver(ui.getButtonMatrix()) == true){
					sta.countAndReportLetters(ui.getButtonMatrix());
					ui.notify(fXML.readXMLStatistics("Estadisticas del Juego.xml", "Statistics"));
					ui.closeWindow(secundaryStage);
				}
		});
	
		
		this.ui.getBExterminationZombies().setOnAction(e->{
			lo.eliminateEntities(ui.getButtonMatrix(), 'Z');
			sta.countAndReportLetters(ui.getButtonMatrix());
			ui.updateButtonMatrix(ui.getButtonMatrix());
			ui.notify(fXML.readXMLStatistics("Estadisticas del Juego.xml", "Statistics"));
//			ui.closeWindow(secundaryStage);
		});
		
		this.ui.getBExterminationAliens().setOnAction(e->{
			lo.eliminateEntities(ui.getButtonMatrix(), 'A');
			sta.countAndReportLetters(ui.getButtonMatrix());
			ui.notify(fXML.readXMLStatistics("Estadisticas del Juego.xml", "Statistics"));
//			ui.closeWindow(secundaryStage);
		});
		
		
	}
}