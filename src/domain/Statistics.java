package domain;

import data.FilesXML;
import javafx.scene.control.Button;

public class Statistics {

	private int zombies;
	private int human;
	private int aliens;
	private int potion;
	
	public Statistics() {
		
	}

	public Statistics(int zombies, int human, int aliens, int potion) {
		super();
		this.zombies = zombies;
		this.human = human;
		this.aliens = aliens;
		this.potion = potion;
	}

	public int getZombies() {
		return zombies;
	}

	public void setZombies(int zombies) {
		this.zombies = zombies;
	}

	public int getHuman() {
		return human;
	}

	public void setHuman(int human) {
		this.human = human;
	}

	public int getAliens() {
		return aliens;
	}

	public void setAliens(int aliens) {
		this.aliens = aliens;
	}

	public int getPotion() {
		return potion;
	}

	public void setPotion(int potion) {
		this.potion = potion;
	}

	
	public  String[] getDataName(){
		String[] dataName = {"zombies","human", "aliens", "potion"};
		
		return dataName;
	}
	
	
	
	

	    public void countAndReportLetters(Button[][] buttonMatrix) {

	        for (int i = 0; i < buttonMatrix.length; i++) {
	            for (int j = 0; j < buttonMatrix[0].length; j++) {
	                String text = buttonMatrix[i][j].getText();
	                for (char c : text.toCharArray()) {
	                    switch (c) {
	                        case 'A':
	                            aliens++;
	                            break;
	                        case 'H':
	                            human++;
	                            break;
	                        case 'Z':
	                            zombies++;
	                            break;
	                        case 'P':
	                            potion++;
	                            break;
	                        default:
	                            // Ignorar otros caracteres
	                            break;
	                    }
	                }
	            }
	        }

	        // Imprimir resultados
	        System.out.println("A: " + aliens);
	        System.out.println("H: " + human);
	        System.out.println("Z: " + zombies);
	        System.out.println("P: " + potion);

	       
	        
//	        Statistics estadistica = new Statistics(aliens,human,zombies,potion);
	       FilesXML.writeXML("Estadisticas del Juego.xml", "Statistic", getDataName(), getData());
	    }

	    public String[] getData() {
	        // Verificar si zombies o aliens0
	        if (zombies == 0) {
	            // Si es así, escribir "exterminio" en lugar del número
	            return new String[]{"EXTERMINIO", String.valueOf(human),String.valueOf(aliens) , String.valueOf(potion)};
	        } else if (aliens == 0) {
	            // Si es así, escribir "exterminio" en lugar del número
	            return new String[]{String.valueOf(zombies), String.valueOf(human), "EXTERMINIO", String.valueOf(potion)};
	        } else {
	          
	            return new String[]{String.valueOf(zombies), String.valueOf(human), String.valueOf(aliens), String.valueOf(potion)};
	        }
	    }
	@Override
	public String toString() {
		return zombies +"-"+ human +"-" + aliens +"-"+ potion;
	}
}
