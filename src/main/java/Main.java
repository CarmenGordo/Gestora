//package crud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author carmen_gordo
 */
public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Index.fxml"));
        
        Parent root = loader.load();
        
        //pasar el stage para usar el modal:
        ControladorIndex controlador = loader.getController();
        controlador.setStage(stage);
        Image icon = new Image("logo.png");
        stage.getIcons().add(icon);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toString());
        stage.setScene(scene);
        stage.setTitle("Gestora");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
