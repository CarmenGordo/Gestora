

//package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControladorModalAjustes implements Initializable{

    @FXML private Button btnCerrarAjustes, btnSalir, btnVerCreditos;
    @FXML
    private Pane modalAjustes, paneCreditos;
    @FXML
    private Label textCreditos;
    
    private boolean temaOscuro;
    private Scene scene; 
    private ResourceBundle rb;
    
    @FXML
    private void cerrarModalAjustes() {
        Stage stage = (Stage) btnCerrarAjustes.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void verCreditos() {
        
        paneCreditos.setVisible(true);
    }
    
    @FXML
    private void salir() {
        
        System.exit(0);
    }
    
    //cambio de tema:
    public void recibirEstadoTema(boolean temaOscuro) {
        this.temaOscuro = temaOscuro;
    }

    //idioma:
    public void recibirIdioma(String idioma) {
        cargarIdioma(idioma);
    }
    
    private void cargarIdioma(String idioma) {
        if ("en".equals(idioma)) {
            rb = ResourceBundle.getBundle("messages", new java.util.Locale("en", "US"));
        } else if ("es".equals(idioma)) {
            rb = ResourceBundle.getBundle("messages", new java.util.Locale("es", "ES"));
        }
        ponerTextos();
    }
    
    private void ponerTextos(){
        btnVerCreditos.setText(rb.getString("btnVerCreditos"));
        btnSalir.setText(rb.getString("btnSalir"));
        textCreditos.setText(rb.getString("textCreditos"));
    }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneCreditos.setVisible(false);
       
        modalAjustes.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                //guardar la escena
                scene = newScene; 
                    
                if (temaOscuro) {
                    scene.getStylesheets().add(getClass().getResource("styleDark.css").toExternalForm());
                } else {
                    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                }
            }
        });
    }

}
