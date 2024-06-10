package presentation;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UI1 {
	private Scene myScene;
    private Pane pContainer;
    private Button bPlay;
    
    public UI1() {
    	
        setPContainer();
        setMyScene(pContainer);
        
    }
//------------------------------------------------------------ 
    public void start(Stage secondaryStage) {
        secondaryStage.setTitle("Alien vs Zombie vs Human");
        secondaryStage.setMinWidth(500);
        secondaryStage.setMinHeight(300);
        secondaryStage.setScene(getMyScene());
        secondaryStage.show();
    }
    public void setMyScene(Pane pContainer) {
        myScene = new Scene(pContainer);
    }

    public Scene getMyScene() {
        return myScene;
    }
//--------------------------------------------------------------------------------------
    public void setPContainer() {
        pContainer = new Pane();

        // Cargar y agregar la imagen al contenedor
        Image image = new Image(getClass().getResourceAsStream("/resources/Game.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500); // Ajusta el ancho de la imagen según tus necesidades
        imageView.setFitHeight(300); // Ajusta el alto de la imagen según tus necesidades
        imageView.setTranslateX(0); // Ajusta la posición X de la imagen
        imageView.setTranslateY(10); // Ajusta la posición Y de la imagen
        pContainer.getChildren().add(imageView);

        // Configurar y agregar el botón "Jugar"
        setBPlay("Jugar");
        pContainer.getChildren().add(bPlay); // Asegúrate de que el botón se agregue después de la imagen
    }

    public Pane getPContainer() {
        return pContainer;
    }
//-------------------------------------------------------------------------------------
    public void setBPlay(String name) {
        bPlay = new Button(name);
        bPlay.setPrefSize(99, 30);
        bPlay.setTranslateX(207); // Ajusta la posición X del botón "Jugar"
        bPlay.setTranslateY(211); // Ajusta la posición Y del botón "Jugar"
        
    }

    public Button getBPlay() {
        return bPlay;
    }
    
     
}
