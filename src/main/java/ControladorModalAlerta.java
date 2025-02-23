

//package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControladorModalAlerta implements Initializable{

    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnCerrarAlerta;
    @FXML
    private FlowPane modalAjustes;
    @FXML
    private Pane paneModalAlerta;
    @FXML
    private Label textPreguntaOperacion;
    
    private boolean operacionAceptada = false; 

    @FXML
    void aceptarOperacion() {
        operacionAceptada = true;
        cerrarModal();
    }

    @FXML
    void cancelarOperacion() {
        operacionAceptada = false; 
        cerrarModal();
    }

    @FXML
    void cerrarModalAlerta() {
        operacionAceptada = false; 
        cerrarModal();
    }    
    
    private void cerrarModal(){
        Stage stage = (Stage) btnCerrarAlerta.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

}
