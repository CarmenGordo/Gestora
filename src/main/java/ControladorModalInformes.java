

//package controladores;

import java.awt.Choice;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.exit;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;



/**
 *
 * @author carmen_gordo
 */
public class ControladorModalInformes implements Initializable{
    
    Connection conexion;
    Statement st;
    ResultSet rs;
    
    //Param informe
    Map parametros = new HashMap();
    private KeyCodeCombination ctrlI;
    
    @FXML private CheckBox checkBox;
    @FXML private TextField filtro;
    @FXML private Button btnInforme, btnInformeCondicional;
    @FXML private ChoiceBox btnChoiceBox;
    @FXML private WebView webView;
    @FXML private StackPane root;
    
    private boolean temaOscuro;
    private Scene scene;
    private ResourceBundle rb;
    
    
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
        checkBox.setText(rb.getString("checkBox"));
        filtro.setText(rb.getString("filtro"));
        btnInforme.setText(rb.getString("btnInforme"));
        btnInformeCondicional.setText(rb.getString("btnInformeCondicional"));
    }
    
    //cambio de tema:
    public void recibirEstadoTema(boolean temaOscuro) {
        this.temaOscuro = temaOscuro;
    }
    
    
    @FXML
    private void btnInforme(){
        if(checkBox.isSelected()){
            lanzaInforme("InformeSimple.jasper", parametros, 0);
        }else{
            lanzaInforme("InformeSimple.jasper", parametros, 1);
        }
    }
    
    @FXML
    private void btnInformeCondicional(){
        if(checkBox.isSelected()){
            lanzaInforme("InformeCondicional.jasper", parametros, 0);
        }else{
            parametros.put("Parametro", "%" + filtro.getText() + "%");
            lanzaInforme("InformeCondicional.jasper", parametros, 1);
        }
    }

    
    public Connection getConnection() throws IOException {
       
        Properties properties = new Properties();
        String IP, PORT, BBDD, USER, PWD;
        //Se lee IP desde fuera del jar
        try {
            InputStream input_ip = new FileInputStream("ip.properties");//archivo debe estar junto al jar
            properties.load(input_ip);
            IP = (String) properties.get("IP");
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo encontrar el archivo de propiedades para IP, se establece localhost por defecto");
            IP = "localhost";
        }

        
        InputStream input = getClass().getClassLoader().getResourceAsStream("bbdd.properties");
        if (input == null) {
            System.out.println("No se pudo encontrar el archivo de propiedades");
            return null;
        } else {
            // Cargar las propiedades desde el archivo
            properties.load(input);
            // String IP = (String) properties.get("IP"); //Tiene sentido leerlo desde fuera del Jar por si cambiamos la IP, el resto no debería de cambiar
            //ni debería ser público
            
            //pasar variable de conexion 
            PORT = (String) properties.get("PORT");//En vez de crear con new, lo crea por asignación + casting
            BBDD = (String) properties.get("BBDD");
            USER = (String) properties.get("USER");//USER de MARIADB en LAMP 
            PWD = (String) properties.get("PWD");//PWD de MARIADB en LAMP 

            Connection conn;
            try {
                String cadconex = "jdbc:mariadb://" + IP + ":" + PORT + "/" + BBDD + " USER:" + USER + "PWD:" + PWD;
                System.out.println(cadconex);
                //Si usamos LAMP Funciona con ambos conectores
                conn = DriverManager.getConnection("jdbc:mariadb://" + IP + ":" + PORT + "/" + BBDD, USER, PWD);
                return conn;
            } catch (SQLException e) {
                System.out.println("Error SQL: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ha ocurrido un error de conexión");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                exit(0);
                return null;
            }
        }
    }
    
    private void lanzaInforme(String rutaInf, Map<String, Object> param, int tipo) {
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(rutaInf));
            try {
                // Llena el informe con los datos de la conexión
                System.out.println(this.conexion);
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, param, this.conexion);

                if (!jasperPrint.getPages().isEmpty()) {

                    //Exporta el informe a un archivo PDF (necesita librería)
                    String pdfOutputPath = "informe.pdf";
                    JasperExportManager.exportReportToPdfFile(jasperPrint, pdfOutputPath);

                    //Exporta el informe a un archivo HTML
                    String outputHtmlFile = "informeHTML.html";
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, outputHtmlFile);
                    
                    //Crea un WebView para mostrar la versión HTML del informe
                    
                    if (tipo == 0) {
                        webView.getEngine().load(new File(outputHtmlFile).toURI().toString());
                    } else { //tipo==1
                        WebView wvnuevo = new WebView();
                        wvnuevo.getEngine().load(new File(outputHtmlFile).toURI().toString());
                        StackPane stackPane = new StackPane(wvnuevo);
                        Scene scene = new Scene(stackPane, 600, 500);
                        Stage stage = new Stage();
                        stage.setTitle("Informe en HTML");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setResizable(true);
                        stage.setScene(scene);
                        stage.show();
                    }
                    
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Alerta de Informe");
                    alert.setContentText("La búsqueda " + filtro.getText() + " no generó páginas");
                    alert.showAndWait();
                }

            } catch (JRException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Error al generar el informe: " + e.getMessage());
            }
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
        }
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filtro.setPromptText("Filtrar por nombre");
        //ChoiceBox:
        btnChoiceBox.getItems().addAll(
            "InformeCompuesto.jasper",
            "InformeGrafica.jasper"
        );
        btnChoiceBox.getSelectionModel().selectFirst();
        
        try {
            conexion = this.getConnection();
            if (conexion != null) {
                this.st = conexion.createStatement();
            }
        } catch (IOException | SQLException e) {
        }
        
        

        ctrlI = new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN);

        checkBox.selectedProperty().addListener((observable, valorAnt, valorAct) -> {
            filtro.setDisable(valorAct);
        });
        
        
        //cambio de tema:
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
