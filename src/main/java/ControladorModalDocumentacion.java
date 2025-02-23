

//package controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

public class ControladorModalDocumentacion implements Initializable{

    @FXML private StackPane root;
    @FXML private WebView webView;
    
    private boolean temaOscuro;
    private Scene scene; 
    private ResourceBundle rb;
    
    String documentacionHtmlEs = "ManualGestora.html";
    String documentacionHtmlEn = "DocumentationGestora.html";

    
    //cambio de tema:
    public void recibirEstadoTema(boolean temaOscuro) {
        this.temaOscuro = temaOscuro;
    }

    //idioma:
    public void recibirIdioma(String idioma) {
        cargarIdioma(idioma);
        cargarDocumentacion(idioma);
    }
    
    private void cargarIdioma(String idioma) {
        if ("en".equals(idioma)) {
            rb = ResourceBundle.getBundle("messages", new java.util.Locale("en", "US"));
        } else if ("es".equals(idioma)) {
            rb = ResourceBundle.getBundle("messages", new java.util.Locale("es", "ES"));
        }
    
    }
    
    
    private void cargarDocumentacion(String idioma) {
        String archivoHtml = "es".equals(idioma) ? documentacionHtmlEs : documentacionHtmlEn;
        URL fileUrl = getClass().getResource("/" + archivoHtml);

        if (fileUrl != null) {
            webView.getEngine().load(fileUrl.toExternalForm());
        } else {
            System.err.println("El archivo " + archivoHtml + " no existe. Cargando el archivo alternativo.");
            archivoHtml = "es".equals(idioma) ? documentacionHtmlEn : documentacionHtmlEs;
            fileUrl = getClass().getResource("/" + archivoHtml);
            if (fileUrl != null) {
                webView.getEngine().load(fileUrl.toExternalForm());
            } else {
                System.err.println("El archivo alternativo " + archivoHtml + " tampoco existe.");
            }
        }
    }

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
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
