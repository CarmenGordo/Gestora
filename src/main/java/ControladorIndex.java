import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
//import modelos.Tiendas;
//import modelos.Almacenes;
//import modelos.HorarioAlmacen;
//import modelos.Productos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import static javafx.scene.paint.Color.rgb;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import javafx.scene.control.Control;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javax.imageio.ImageIO;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.GraphicValidationDecoration;
//import ConexionBD;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;

public class ControladorIndex implements Initializable {

    public Connection conexion;
    public Statement st;
    public ResultSet rs;
    
    private ResourceBundle rb;

    private Stage stage;
    
    ObservableList<Tiendas> listaTiendas = FXCollections.observableArrayList();
    ObservableList<Almacenes> listaAlmacenes = FXCollections.observableArrayList();
    ObservableList<HorarioAlmacen> horarioAlmacen = FXCollections.observableArrayList();
    ObservableList<Productos> listaProductos = FXCollections.observableArrayList();
    
    //? cambiar a libreria para conseguir paises y ciudade     
    //PARA MAPA VISUAL MIRAR: mapa control fx
    ObservableList<String> ciudadesEs = FXCollections.observableArrayList("","Sevilla", "Málaga", "Granada", "Córdoba", "Almería", "Cádiz","Jaén", "Huelva","Zaragoza", "Huesca", "Teruel","Oviedo", "Gijón", "Avilés","Santander","Toledo", "Albacete", "Ciudad Real","Guadalajara", "Cuenca","Barcelona");
    ObservableList<String> paisesEu = FXCollections.observableArrayList("","España", "Francia", "Alemania", "Italia", "Portugal", "Reino Unido", "Irlanda","Suecia");
    
    
    //general pane: cabeceras
    @FXML private AnchorPane anchorPaneContenedor;
    @FXML private Pane paneCabecera, paneBotonera;
    @FXML private ToggleButton btnModo;
    @FXML private StackPane stackpaneContenido;
    
    
    //Pane contenido:
    @FXML private Pane paneContenidoListaProductos, paneContenidoProductoSelec, paneAñadirProducto, paneContenidoInicio, paneContenidoListaTiendas, paneContenidoTiendaSelec, paneAñadirTienda, paneContenidoListaAlmacenes, paneContenidoAlmacenSelec, paneAñadirAlmacen;
    
    
    
    @FXML
    private void volverTiendas(){
        
    }
    private void visiblePaneCabecera(boolean b){
        //btnVolver.setVisible(b);
        //textoMigas.setVisible(b);
    }
    private void quitarPaneCabecera(boolean b){
        paneBotonera.setVisible(!b);
        paneBotonera.setManaged(!b);
    }
    
    
    
    //Botones iniciales menu:
    @FXML
    private VBox menuPrincipal;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnMenu;
    @FXML
    private void btnCambiarMenu(){
        if (menuPrincipal.getPrefWidth() == 150) {
            menuPrincipal.setPrefWidth(80);
            
            cambiarMenuPrincipal();
            
        } else {
            menuPrincipal.setPrefWidth(150);
        }
    }
    private void cambiarMenuPrincipal(){
        //?cambiar solo a icono
        btnTablero.setText("D");
        btnProductos.setText("P");
        btnTiendas.setText("T");
        btnAlmacenes.setText("A");
        btnAjustes.setText("H");
        
        btnTablero.setPrefWidth(80);
        btnProductos.setPrefWidth(80);
        btnTiendas.setPrefWidth(80);
        btnAlmacenes.setPrefWidth(80);
        btnAjustes.setPrefWidth(80);
        paneCabecera.setPrefWidth(720);
        paneBotonera.setPrefWidth(720);
        stackpaneContenido.setPrefWidth(720);
        
        AnchorPane.setRightAnchor(paneCabecera, 0.0);
        AnchorPane.setRightAnchor(paneBotonera, 0.0);
        AnchorPane.setRightAnchor(stackpaneContenido, 0.0);
        anchorPaneContenedor.getChildren().addAll(paneCabecera, paneBotonera, stackpaneContenido);
    }
    @FXML
    private Button btnTablero;
    @FXML
    private void cambiarPaneInicio(){
        quitarPaneCabecera(true);
        visiblePaneCabecera(false);
        mostrarPane(paneContenidoInicio);
        
    }
    @FXML
    private Button btnTiendas;
    @FXML
    private void cambiarPaneListaTiendas(){
        quitarPaneCabecera(false);
        visiblePaneCabecera(false);
        mostrarPane(paneContenidoListaTiendas);
        
        actualizar();
    }
    private void cambiarPaneTiendaSelec(){
        quitarPaneCabecera(false);
        mostrarPane(paneContenidoTiendaSelec);
    }
    @FXML
    private Button btnAlmacenes;
    @FXML
    private void cambiarPaneListaAlmacenes(){
        quitarPaneCabecera(false);
        visiblePaneCabecera(false);
        mostrarPane(paneContenidoListaAlmacenes);
        
        actualizar();
    }
    @FXML
    private Button btnProductos;
    @FXML
    private void cambiarPaneListaProductos(){
        quitarPaneCabecera(false);
        visiblePaneCabecera(false);
        mostrarPane(paneContenidoListaProductos);
        
        actualizar();
    }
    private void mostrarPane(Pane paneMostrar) {
        paneContenidoInicio.setVisible(false);
        
        paneContenidoListaProductos.setVisible(false);
        paneContenidoProductoSelec.setVisible(false);
        paneAñadirProducto.setVisible(false);
        
        paneContenidoListaTiendas.setVisible(false);
        paneContenidoTiendaSelec.setVisible(false);
        paneAñadirTienda.setVisible(false);
        
        paneContenidoListaAlmacenes.setVisible(false);
        paneContenidoAlmacenSelec.setVisible(false);
        paneAñadirAlmacen.setVisible(false);
        
        paneMostrar.setVisible(true);
    } 
    @FXML
    private Button btnAjustes;
    //recoger el stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private void abrirAjustes(){
        
        try {
          
            //cargar modal
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModalAjustes.fxml"));
            Parent root = fxmlLoader.load();
            
            // obtener el controlador ajuestes
            ControladorModalAjustes controladorAjustes = fxmlLoader.getController();
            controladorAjustes.recibirEstadoTema(temaOscuro);
            
            String idiomaSeleccionado = btnCambioIdioma.getValue().equals("Español") ? "es" : "en";
            controladorAjustes.recibirIdioma(idiomaSeleccionado);
            
            
            Stage modalStage = new Stage();
            //no se puede salir del modal
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(stage);
            Image icon = new Image("logo.png");
            modalStage.getIcons().add(icon);
            modalStage.setTitle("Ajustes");
            modalStage.setScene(new Scene(root));
            
            //mostrar el modal sin salir de inicio
            modalStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void abrirDocumentacion(){
        
        try {
            //cargar modal
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModalDocumentacion.fxml"));
            Parent root = fxmlLoader.load();
            
            // obtener el controlador ajuestes
            ControladorModalDocumentacion controladorDocu = fxmlLoader.getController();
            controladorDocu.recibirEstadoTema(temaOscuro);
            
            String idiomaSeleccionado = btnCambioIdioma.getValue().equals("Español") ? "es" : "en";
            controladorDocu.recibirIdioma(idiomaSeleccionado);
            
            
            Stage modalStage = new Stage();
            //no se puede salir del modal
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(stage);
            Image icon = new Image("logo.png");
            modalStage.getIcons().add(icon);
            modalStage.setTitle("Documentación");
            modalStage.setScene(new Scene(root));
            
            //mostrar el modal sin salir de inicio
            modalStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    //Tabla lista productos:
    @FXML
    private TableView<Productos> tablaListaProductos;
    @FXML
    private TableColumn<Productos, String> columnIdListaProductos, columnNombreListaProductos, columnImgListaProductos, columnSubTipoListaProductos, columnTallaListaProductos;
    @FXML
    private TableColumn<Productos, Productos.TipoProducto> columnTipoListaProductos;
    @FXML
    private TableColumn<Productos, Double> columnPrecioListaProductos;
    
    
    //pg producto selec:
    @FXML
    private Text textIdioma, textoDatosProcutos, textIdProductoSelec, textNombreProductoSelec, textTipoProductoSelec, textSubTipoProductoSelec, textTallaProductoSelec, textPrecioProductoSelec;
    @FXML
    private ImageView imgProductoSelec;
    
    //pg producto selec, tabla tiendas:
    @FXML
    private TableView<Tiendas> tablaTiendasProducto;
    @FXML
    private TableColumn<Tiendas, String> columnIdProductoSelecTiendas, columnNombreProductoSelecTiendas, columnTipoProductoSelecTiendas, columnDirProductoSelecTiendas, columnCiudadProductoSelecTiendas, columnPaisProductoSelecTiendas, columnHorarioProductoSelecTiendas;
    @FXML
    private TableColumn<Tiendas, Integer> columnTelProductoSelecTiendas;
   
    @FXML
    private TableView<Almacenes> tablaAlmacenesProductoSelec;
    @FXML
    private TableColumn<Almacenes, String> columnIdAlmacenesSelecProducto, columnNombreAlmacenesSelecProducto, columnDirAlmacenesSelecProducto, columnCiudadAlmacenesSelecProducto, columnPaisAlmacenesSelecProducto, columnTelAlmacenesSelecProducto, columnHoarioAlmacenesSelecProducto, columnCapOcupadaAlmacenesSelecProducto, columnCapTotalAlmacenesSelecProducto;
    
    
    
    //Tabla lista tiendas:
    @FXML
    private TableView<Tiendas> tablaListaTiendas;
    @FXML
    private TableColumn<Tiendas, String> columnIdListaTiendas, columnNombreListaTiendas, columnCiudadListaTiendas, columnDirListaTiendas, columnPaisListaTiendas;
    @FXML
    private TableColumn<Tiendas, Map> columnHorarioListaTiendas;
    @FXML
    private TableColumn<Tiendas, Integer> columnTelListaTiendas;
    @FXML
    private TableColumn<Tiendas, Tiendas.TipoTienda> columnTipoListaTiendas;
    
    
    
    //pg tienda selec, tabla tienda:
    @FXML
    private Label texIdTiendaSelec, textNombreTiendaSelec, textTelTiendaSelec, textTipoTiendaSelec, textDirTiendaSelec;
    @FXML
    private TableView<Tiendas> tablaHorarioTiendaSelec;
    @FXML
    private TableColumn<Tiendas, String> columnLHorarioTiendaSelec, columnMHorarioTiendaSelec, columnXHorarioTiendaSelec, columnJHorarioTiendaSelec, columnVHorarioTiendaSelec, columnSHorarioTiendaelec, columnDHorarioTiendaSelec;
    @FXML
    private Label textCiudadTiendaSelec, textPaisTiendaSelec;
    
    //pg tienda selec, tabla productos:
    @FXML
    private TableView<Productos> tablaProductosTienda;
    @FXML
    private TableColumn<Productos, String> columnIdTiendaSelecProd, columnNombreTiendaSelecProd, columnImgTiendaSelecProd, columnSubTipoTiendaSelecProd, columnTallaTiendaSelecProd;
    @FXML
    private TableColumn<Productos, Productos.TipoProducto>columnTipoTiendaSelecProd;
    @FXML
    private TableColumn<Productos, Double>columnPrecioTiendaSelecProd;
    @FXML
    private TableColumn<Productos, Integer>columnStockTiendaSelecProd;
    
    //pg tienda selec, tabla almacenes:
    @FXML
    private TableView<Almacenes> tablaAlmacenesTienda;
    @FXML
    private TableColumn<Almacenes, String> columnIdTiendaSelecAlmac, columnNombreTiendaSelecAlmac, columnDirTiendaSelecAlmac, columnCiudadTiendaSelecAlmac, columnPaisTiendaSelecAlmac, columnHorarioTiendaSelecAlmac;
    @FXML
    private TableColumn<Almacenes, Integer> columnTelTiendaSelecAlmac, columnCapOcupadaTiendaSelecAlmac, columnCapTotalTiendaSelecAlmac;
    
   
    
    //Tabla lista almacenes:
    @FXML
    private TableView<Almacenes> tablaListaAlmacenes;
    @FXML
    private TableColumn<Almacenes, String> columnIdListaAlmacenes, columnNombreListaAlmacenes, columnDirListaAlmacenes, columnCiudadListaAlmacenes, columnPaisListaAlmacenes;
    @FXML
    private TableColumn<Almacenes, Integer> columnTelListaAlmacenes, columnCapOcupadaListaAlmacenes, columnCapTotalListaAlmacenes;
    @FXML
    private TableColumn<Almacenes, Map> columnHorarioListaAlmacenes;    

    
    //pg almacen selec: elmentos
    @FXML
    private Label texIdAlmacenSelec, textNombreAlmacenSelec, textTelAlmacenSelec, textDirAlmacenSelec, textCapOcupadaAlmacenSelec, textCapTotalAlmacenSelec;
    @FXML
    private TableView<Almacenes> tablaHorarioAlmacenSelec;
    @FXML
    private TableColumn<Almacenes, String> columnLHorarioAlmacenSelec, columnMHorarioAlmacenSelec, columnXHorarioAlmacenSelec, columnJHorarioAlmacenSelec, columnVHorarioAlmacenSelec, columnSHorarioAlmacenSelec, columnDHorarioAlmacenSelec;
    @FXML
    private Label textCiudadAlmacenSelec, textPaisAlmacenSelec;
    @FXML
    private Canvas barraCapacidad;
    //? acabar barrcaCapacidad
    
    
    //pg almacen selec, tabla productos:
    @FXML
    private TableView<Productos> tablaProductosAlmacen;
    @FXML
    private TableColumn<Productos, String> columnIdProductosSelecAlmacen, columnNombreProductosSelecAlmacen, columnImgProductosSelecAlmacen, columnTipoProductosSelecAlmacen, columnSubTipoProductosSelecAlmacen,  columnTallaProductosSelecAlmacen;
    @FXML
    private TableColumn<Productos, Double> columnPrecioProductosSelecAlmacen;
    @FXML
    private TableColumn<Productos, Integer> columnStockProductosSelecAlmacen;
            
    //pg almacen selec, tabla tiendas:
    @FXML
    private TableView<Tiendas> tablaTiendasAlmacen;
    @FXML
    private TableColumn<Tiendas, String> columnIdTiendasSelecAlmacen, columnNombreTiendasSelecAlmacen, columnTipoTiendasSelecAlmacen, columnDirTiendasSelecAlmacen, columnCiudadTiendasSelecAlmacen, columnPaisTiendasSelecAlmacen;
    @FXML
    private TableColumn<Tiendas, Integer> columnTelTiendasSelecAlmacen;
    @FXML
    private TableColumn<Tiendas, Map> columnHorarioTiendasSelecAlmacen;
    
    
    //en productos al editar aparece:
    @FXML
    private HBox editarProductoElegirTienda, editarProductoElegirAlmacen;
    
    
    
    
    //Botones pane botonera:
    //elementos de añadir productos:
    @FXML
    private TextField textNombreAñProducto;
    @FXML
    private Label iconoValiNombreAñProducto, iconoValiTipoAñProducto, iconoValiSupTipoAñProducto, iconoValiTallaAñProducto, iconoValiPrecioAñProducto, iconoValiStockAñProducto, iconoValiIdTiendaAñProducto, iconoValiIdAlmacenAñProducto;
    @FXML
    private ImageView imgAñProducto;
    @FXML
    private ChoiceBox<Productos.TipoProducto> choiceBoxTipoAñProducto;
    @FXML
    private ChoiceBox choiceBoxSubTipoAñProducto;
    @FXML
    private ComboBox<Productos.TallaProducto> comboBoxTallasAñProducto;
    @FXML
    private TextField textPrecioAñProducto, textStockAñProducto;
    @FXML
    private ComboBox comboBoxElegirTiendaAñProd, comboBoxElegirAlmacenAñProd;

 
            
  

    //elementos de añadir tiendas:
    @FXML
    private TextField textNombreAñTienda, textDirAñTienda, textTelAñTienda;
    @FXML
    private Label iconoValiNombreAñTienda, iconoValiTipoAñTienda, iconoValiDirAñTienda, iconoValiPaisAñTienda, iconoValiCiudaadAñTienda, iconoValiTelAñTienda, iconoValiHorarioAñTienda;
    @FXML
    private ComboBox comboBoxTipoAñTienda;
    @FXML
    private ComboBox<String> comboBoxPaisAñTienda, comboBoxCiudadAñTienda;
    @FXML
    private HBox tablaHorarioAñTienda;
    @FXML
    private TextField textAperLHorarioAñTienda,  textCieLHorarioAñTienda,  textAperMHorarioAñTienda, textCieMHorarioAñTienda, textAperXHorarioAñTienda, textCieXHorarioAñTienda, textAperJHorarioAñTienda, textCieJHorarioAñTienda, textAperVHorarioAñTienda, textCieVHorarioAñTienda, textAperSHorarioAñTienda, textCieSHorarioAñTienda, textAperDHorarioAñTienda, textCieDHorarioAñTienda;

    //elementos de añadir almacenes:
    @FXML
    private TextField textNombreAñAlmacen, textDirAñAlmacen, textTelAñAlmacen, textCapTotalAñAlmacen;
    @FXML
    private Label iconoValiNombreAñAlmacen, iconoValiDirAñAlmacen, iconoValiPaisAñAlmacen, iconoValiCiudaadAñAlmacen, iconoValiTelAñAlmacen, iconoValiCapTotalAñAlmacen, iconoValiIdTiendaAñAlmacen, iconoValiHorarioAñAlmacen;
    @FXML
    private ComboBox<String> comboBoxPaisAñAlmacen;
    @FXML
    private ComboBox comboBoxCiudadAñAlmacen;
    @FXML
    private ComboBox textIdTiendaAñAlmacen;
    @FXML
    private HBox tablaHorarioAñAlmacen;
    @FXML
    private TextField textAperLHorarioAñAlmacen, textCieLHorarioAñAlmacen, textAperMHorarioAñAlmacen, textCieMHorarioAñAlmacen, textAperXHorarioAñAlmacen, textCieXHorarioAñAlmacen, textAperJHorarioAñAlmacen, textCieJHorarioAñAlmacen, textAperVHorarioAñAlmacen, textCieVHorarioAñAlmacen, textAperSHorarioAñAlmacen, textCieSHorarioAñAlmacen, textAperDHorarioAñAlmacen, textCieDHorarioAñAlmacen;
    @FXML
    private VBox barraProgresoAñAlmacen, barraProgresoVerAlmacen;
    @FXML private HBox footerBtnAñProducto, footerBtnEdProducto;
    
    private String imgBase64;
    private boolean imgArrastrada; 
    
    @FXML
    private void añadir(){
        limpiarPaneAñAlmacen();
        ObservableList<Tiendas> tiendasPro = darListaTiendas();

        if (paneContenidoListaProductos.isVisible() || paneContenidoProductoSelec.isVisible()) {
            tablaListaProductos.setPrefHeight(333);
            footerModificarProductoEdProducto.setVisible(false);
            footerModificarProductoEdProducto.setPrefHeight(0);
            //footerBtnAñProducto.setTranslateY(-20);
            footerBtnAñProducto.setVisible(true);
            footerBtnEdProducto.setVisible(false);
            mostrarPane(paneAñadirProducto);
           
            arrastrarImg();
            
            ObservableList<Productos.TipoProducto> opcTipo = FXCollections.observableArrayList(Productos.TipoProducto.values());
            ObservableList<Productos.TallaProducto> opcTalla = FXCollections.observableArrayList(Productos.TallaProducto.values());
            choiceBoxTipoAñProducto.setItems(opcTipo);
            
            choiceBoxTipoAñProducto.setOnAction((event) -> {
                if (choiceBoxTipoAñProducto.getValue() == Productos.TipoProducto.Ropa) {
                    ObservableList<Productos.SubTipoRopaProducto> ropaOpciones = FXCollections.observableArrayList(Productos.SubTipoRopaProducto.values());
                    choiceBoxSubTipoAñProducto.setItems(ropaOpciones);
                    choiceBoxSubTipoAñProducto.setDisable(false);
                    
                
                } else if (choiceBoxTipoAñProducto.getValue() == Productos.TipoProducto.Accesorios) {
                    ObservableList<Productos.SubTipoAccProducto> accesoriosOpciones = FXCollections.observableArrayList(Productos.SubTipoAccProducto.values());
                    choiceBoxSubTipoAñProducto.setItems(accesoriosOpciones);
                    choiceBoxSubTipoAñProducto.setDisable(false);
                   
                } else {
                    choiceBoxSubTipoAñProducto.setItems(FXCollections.observableArrayList());
                    choiceBoxSubTipoAñProducto.setDisable(true);
                }

            });
       
            comboBoxTallasAñProducto.setItems(opcTalla);
  
            comboBoxElegirTiendaAñProd.setItems(obtenerIdsTiendas());
            comboBoxElegirTiendaAñProd.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    footerModificarProductoEdProducto.setVisible(true);
                    footerBtnAñProducto.setStyle("-fx-margin-top: 5px;");
                    
                    String idTiendaSelec = (String) newValue;
                    String nombreTienda = obtenerIdNombreTiendas(idTiendaSelec);
                    textTiendaEdProd.setText(nombreTienda);
                    
                } else {
                    footerModificarProductoEdProducto.setVisible(false);
                    footerBtnAñProducto.setStyle("-fx-margin-top: 0px;");
                }
            });
            
            comboBoxElegirAlmacenAñProd.setItems(obtenerIdsAlmacenes());
            comboBoxElegirAlmacenAñProd.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    footerModificarProductoEdProducto.setVisible(true);
                    footerBtnAñProducto.setStyle("-fx-margin-top: 5px;");
                    
                    String idAlmaSelec = (String) newValue;
                    String nombreAlma = obtenerIdNombreAlmacenes(idAlmaSelec);
                    textAlmacenEdProd.setText(nombreAlma);
                    
                } else {
                    footerModificarProductoEdProducto.setVisible(false);
                    footerBtnAñProducto.setStyle("-fx-margin-top: 0px;");
                }
            });
            
            comprobarValidacionesAñProducto("añ");
            
        }else if (paneContenidoListaTiendas.isVisible() || paneContenidoTiendaSelec.isVisible()){
            mostrarPane(paneAñadirTienda);
            footerAñTienda.setVisible(true);
            footerEdTienda.setVisible(false);
            
            ObservableList<Tiendas.TipoTienda> opcTipo = FXCollections.observableArrayList(Tiendas.TipoTienda.values());
            comboBoxTipoAñTienda.setItems(opcTipo);
            comboBoxCiudadAñTienda.getItems().addAll(ciudadesEs);
            comboBoxCiudadAñTienda.getSelectionModel().selectFirst();
            comboBoxPaisAñTienda.getItems().addAll(paisesEu);
            comboBoxPaisAñTienda.getSelectionModel().selectFirst();
            
            comboBoxPaisAñTienda.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(comboBoxPaisAñTienda.getValue() == "España"){

                    comboBoxCiudadAñTienda.setDisable(false);
                    comboBoxCiudadAñTienda.setValue("");

                }else{
                    comboBoxCiudadAñTienda.setDisable(true);
                }
            });
            
            comprobarValidacionesAñTienda();
            
            
        }else if (paneContenidoListaAlmacenes.isVisible() || paneContenidoAlmacenSelec.isVisible()){
            mostrarPane(paneAñadirAlmacen);
            footerAñAlmacen.setVisible(true);
            footerEdAlmacen.setVisible(false);
            
            comboBoxCiudadAñAlmacen.getItems().addAll(ciudadesEs);
            comboBoxCiudadAñAlmacen.getSelectionModel().selectFirst();
            comboBoxPaisAñAlmacen.getItems().addAll(paisesEu);
            comboBoxPaisAñAlmacen.getSelectionModel().selectFirst();
    
            comboBoxPaisAñAlmacen.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(comboBoxPaisAñAlmacen.getValue() == "España"){

                    comboBoxCiudadAñAlmacen.setDisable(false);
                    comboBoxCiudadAñAlmacen.setValue("");

                }else{
                    comboBoxCiudadAñAlmacen.setDisable(true);
                }
            });
            
            textIdTiendaAñAlmacen.setItems(obtenerIdsTiendas());
            textCapTotalAñAlmacen.getText();
            System.out.println("textCapTotalAñAlmacen"+textCapTotalAñAlmacen.getText());
            

            comprobarValidacionesAñAlmacen(); 
        }
        
    }
    
    //soltar y agarrar img
    private void arrastrarImg(){
        //agarrar img 
            paneAñadirProducto.setOnDragOver(event -> {
                if (event.getGestureSource() != paneAñadirProducto && event.getDragboard().hasFiles()) {
                    //compruba q tiene imagenes para copiar
                    event.acceptTransferModes(TransferMode.COPY);
                }
                //consume() : hace qye lo cierre para que no siga 
                event.consume();
            });
            
            //soltar img
            paneAñadirProducto.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasFiles()) {
                    List<File> files = db.getFiles();
                    File selectedFile = files.get(0);

                    try {

                        //redimensionar la img
                        Image originalImage = new Image(selectedFile.toURI().toString());
                        Image resizedImage = new Image(originalImage.getUrl(), 222, 197, true, true);
                        imgAñProducto.setImage(resizedImage);

                        //conversiones para usar b64
                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(resizedImage, null);
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(bufferedImage, "png", baos);
                        
                        //convertimos a byte
                        byte[] imageBytes = baos.toByteArray();
                        imgBase64 = Base64.getEncoder().encodeToString(imageBytes);
                        imgArrastrada = true;

                        //mostar nombre b64
                        System.out.println("Imagen en base64: " + imgBase64);
                        success = true;
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Problemas con la imagen", "La imagen no se ha poodido cargar");
                    cargarImagen("sinFoto.png", imgAñProducto);
                }

                //indicar si ha salido bien
                event.setDropCompleted(success);
                event.consume();
            });
    }
    
    @FXML
    private void aceptarAñProducto(){
     
        String nombreProd = textNombreAñProducto.getText();
        String imgProd = imgBase64;
        Enum tipoProd = choiceBoxTipoAñProducto.getValue();
        Enum subProd = null;
        
        if (tipoProd == Productos.TipoProducto.Ropa) {
            subProd = (Productos.SubTipoRopaProducto) choiceBoxSubTipoAñProducto.getValue();
            
        } else if (tipoProd == Productos.TipoProducto.Accesorios) {
            subProd = (Productos.SubTipoAccProducto) choiceBoxSubTipoAñProducto.getValue();
            
        } else {
            subProd = null;
        }
        
        Enum tallaProd = comboBoxTallasAñProducto.getValue();
        double precioProd = Double.parseDouble(textPrecioAñProducto.getText());
        int stockProd = Integer.parseInt(textStockAñProducto.getText());
        String tiendaProd = comboBoxElegirTiendaAñProd.getSelectionModel().getSelectedItem().toString();
        String almacenProd = comboBoxElegirAlmacenAñProd.getSelectionModel().getSelectedItem().toString();

        comprobarValidacionesAñProducto("añ");
        if(comprobarDatosProducto()){
            System.out.println("Añadir porducto: "+nombreProd+imgProd+tipoProd+subProd+tallaProd+precioProd+stockProd+tiendaProd+almacenProd);
            añadirProducto(nombreProd, imgProd, tipoProd, subProd, tallaProd, precioProd, stockProd, tiendaProd, almacenProd);
            limpiarPaneAñProducto();
            actualizar();
            mostrarPane(paneContenidoListaProductos);
        }
    }
    @FXML
    private void cancelarAñProducto(){
        mostrarPane(paneContenidoListaProductos);
        limpiarPaneAñProducto();
    }
    private void limpiarPaneAñProducto() {
        tablaListaTiendas.getSelectionModel().clearSelection();
        
        textNombreAñProducto.clear();
        choiceBoxTipoAñProducto.getSelectionModel().clearSelection();
        choiceBoxSubTipoAñProducto.getSelectionModel().clearSelection();
        comboBoxTallasAñProducto.getSelectionModel().clearSelection();
        textPrecioAñProducto.clear();
        textStockAñProducto.clear();
        comboBoxElegirTiendaAñProd.getSelectionModel().clearSelection();
        comboBoxElegirAlmacenAñProd.getSelectionModel().clearSelection();
    }
    private String recogerHorario() {
        StringBuilder horarioJson = new StringBuilder();
        horarioJson.append("{");

        horarioJson.append("\"lunes\": \"").append(textAperLHorarioAñTienda.getText()).append("-").append(textCieLHorarioAñTienda.getText()).append("\", ");
        horarioJson.append("\"martes\": \"").append(textAperMHorarioAñTienda.getText()).append("-").append(textCieMHorarioAñTienda.getText()).append("\", ");
        horarioJson.append("\"miércoles\": \"").append(textAperXHorarioAñTienda.getText()).append("-").append(textCieXHorarioAñTienda.getText()).append("\", ");
        horarioJson.append("\"jueves\": \"").append(textAperJHorarioAñTienda.getText()).append("-").append(textCieJHorarioAñTienda.getText()).append("\", ");
        horarioJson.append("\"viernes\": \"").append(textAperVHorarioAñTienda.getText()).append("-").append(textCieVHorarioAñTienda.getText()).append("\", ");
        horarioJson.append("\"sábado\": \"").append(textAperSHorarioAñTienda.getText()).append("-").append(textCieSHorarioAñTienda.getText()).append("\", ");
        horarioJson.append("\"domingo\": \"").append(textAperDHorarioAñTienda.getText()).append("-").append(textCieDHorarioAñTienda.getText()).append("\"");

        horarioJson.append("}");

        return horarioJson.toString();
    }
    @FXML
    private void aceptarAñTienda(){
        
        String nombreTienda = textNombreAñTienda.getText();
        String tipoTienda = comboBoxTipoAñTienda.getValue().toString();
        String dirTienda = textDirAñTienda.getText();
        String ciudadTienda = comboBoxCiudadAñTienda.getValue().toString();
        String paisTienda = comboBoxPaisAñTienda.getValue().toString();
        String telefonoTiendaS = textTelAñTienda.getText();
        String horarioJson = recogerHorario();
        int telefonoTienda = Integer.parseInt(telefonoTiendaS);
        
        if(comprobarDatosTienda()){
            System.out.println("-- aceptarTienda");
            añadirTienda(nombreTienda, tipoTienda, dirTienda, ciudadTienda, paisTienda, telefonoTienda, horarioJson);
            limpiarPaneAñTienda();
            actualizar();
            mostrarPane(paneContenidoListaTiendas);
        }
    }
    @FXML
    private void cancelarAñTienda(){
        mostrarPane(paneContenidoListaTiendas);
        limpiarPaneAñTienda();
    }
    private void limpiarPaneAñTienda() {
        tablaListaTiendas.getSelectionModel().clearSelection();
        textNombreAñTienda.clear();
        comboBoxTipoAñTienda.getSelectionModel().clearSelection();
        textDirAñTienda.clear();
        comboBoxPaisAñTienda.getSelectionModel().clearSelection();
        comboBoxCiudadAñTienda.getSelectionModel().clearSelection();
        textTelAñTienda.clear();

        textAperLHorarioAñTienda.clear();
        textCieLHorarioAñTienda.clear();
        textAperMHorarioAñTienda.clear();
        textCieMHorarioAñTienda.clear();
        textAperXHorarioAñTienda.clear();
        textCieXHorarioAñTienda.clear();
        textAperJHorarioAñTienda.clear();
        textCieJHorarioAñTienda.clear();
        textAperVHorarioAñTienda.clear();
        textCieVHorarioAñTienda.clear();
        textAperSHorarioAñTienda.clear();
        textCieSHorarioAñTienda.clear();
        textAperDHorarioAñTienda.clear();
        textCieDHorarioAñTienda.clear();
    }
    
    @FXML
    private void aceptarAñAlmacen(){       
        String nombreAlmacen = textNombreAñAlmacen.getText();
        String dirAlmacen = textDirAñAlmacen.getText();
        String ciudadAlmacen = comboBoxCiudadAñAlmacen.getValue().toString();
        String paisAlmacen = comboBoxPaisAñAlmacen.getValue().toString();
        int telefonoAlmacen = Integer.parseInt(textTelAñAlmacen.getText());
        int capacidadTotalAlmacen = Integer.parseInt(textCapTotalAñAlmacen.getText());
        String idTiendaAlmacen = textIdTiendaAñAlmacen.getValue().toString();
        String horarioJson = recogerHorario();        
        
        if(comprobarDatosAlmacen()){
            System.out.println("-- aceptarAñAlmacen");
            añadirAlmacen(nombreAlmacen, dirAlmacen, ciudadAlmacen, paisAlmacen, telefonoAlmacen, horarioJson, capacidadTotalAlmacen, idTiendaAlmacen);
            limpiarPaneAñAlmacen();
            actualizar();
            mostrarPane(paneContenidoListaAlmacenes);
        }
    }
    @FXML
    private void cancelarAñAlmacen(){
        mostrarPane(paneContenidoListaAlmacenes);
        limpiarPaneAñAlmacen();
    }
    private void limpiarPaneAñAlmacen() {
        tablaListaAlmacenes.getSelectionModel().clearSelection();
        textNombreAñAlmacen.clear();
        textDirAñAlmacen.clear();
        comboBoxPaisAñAlmacen.getSelectionModel().clearSelection();
        comboBoxCiudadAñAlmacen.getSelectionModel().clearSelection();
        textTelAñAlmacen.clear();
        textCapTotalAñAlmacen.clear();
        textIdTiendaAñAlmacen.getSelectionModel().clearSelection();

        textAperLHorarioAñAlmacen.clear();
        textCieLHorarioAñAlmacen.clear();
        textAperMHorarioAñAlmacen.clear();
        textCieMHorarioAñAlmacen.clear();
        textAperXHorarioAñAlmacen.clear();
        textCieXHorarioAñAlmacen.clear();
        textAperJHorarioAñAlmacen.clear();
        textCieJHorarioAñAlmacen.clear();
        textAperVHorarioAñAlmacen.clear();
        textCieVHorarioAñAlmacen.clear();
        textAperSHorarioAñAlmacen.clear();
        textCieSHorarioAñAlmacen.clear();
        textAperDHorarioAñAlmacen.clear();
        textCieDHorarioAñAlmacen.clear();
    }

    
    
    
   
    @FXML private TabPane tabPaneProductos;
    @FXML private HBox footerModificarProductoSelect, footerAñTienda, footerEdTienda, footerAñAlmacen, footerEdAlmacen, footerModificarProductoEdProducto, footerModificarProductoAñProducto;
    @FXML private Text textTiendaEdProd, textAlmacenEdProd;
    @FXML Button btnAñadir,btnEditar, btnBorrar, btnVer, btnInformes;
    
   
    //activar botones:
    public void activarBtn(boolean b){
        btnAñadir.setDisable(b);
        btnEditar.setDisable(!b);
        btnBorrar.setDisable(!b);
        btnVer.setDisable(!b);
    }
    //comprobar si hay elemento selec en tabla
    public void comprobarFoco(){
        //activar botones:
        tablaListaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                activarBtn(tablaListaProductos.getSelectionModel().getSelectedItem() != null);
               
            }  else {
                activarBtn(false);
            }
        });
        tablaListaProductos.focusedProperty().addListener((obs, tenerFoco, perderFoco) -> {
            if (!perderFoco) {
                if (tablaListaProductos.getSelectionModel().getSelectedItem() == null) {
                    activarBtn(false);
                }
            }
        });
        
        //tiendas:
        tablaListaTiendas.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                activarBtn(tablaListaTiendas.getSelectionModel().getSelectedItem() != null);
               
            }  else {
                activarBtn(false);
            }
        });
        tablaListaTiendas.focusedProperty().addListener((obs, tenerFoco, perderFoco) -> {
            if (!perderFoco) {
                if (tablaListaTiendas.getSelectionModel().getSelectedItem() == null) {
                    activarBtn(false);
                }
            }
        });
        
        //almacenes:
        tablaListaAlmacenes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                activarBtn(tablaListaAlmacenes.getSelectionModel().getSelectedItem() != null);
               
            }  else {
                activarBtn(false);
            }
        });
        tablaListaAlmacenes.focusedProperty().addListener((obs, tenerFoco, perderFoco) -> {
            if (!perderFoco) {
                if (tablaListaAlmacenes.getSelectionModel().getSelectedItem() == null) {
                    activarBtn(false);
                }
            }
        });
    }
    
    
    private String idObjSelecEd;
    @FXML
    public void editar(){
        btnEditar.setDisable(true);
        
        ObservableList<Productos> productos = darListaProductos();
        ObservableList<Tiendas> tiendas = darListaTiendas();
        ObservableList<Almacenes> almacenes = darListaAlmacenes();
        
        if(tablaListaProductos.getSelectionModel().getSelectedItem() != null){
            Productos productoSelec = tablaListaProductos.getSelectionModel().getSelectedItem();

            mostrarPane(paneAñadirProducto);
            footerAñTienda.setVisible(false);
            footerAñTienda.setPrefHeight(0);
            footerEdTienda.setVisible(true);
            tabPaneProductos.setPrefHeight(378);
            
            footerModificarProductoAñProducto.setVisible(false);
            footerModificarProductoEdProducto.setVisible(true);
            footerModificarProductoEdProducto.setTranslateY(-40);
            footerBtnAñProducto.setVisible(false);
            footerBtnEdProducto.setVisible(true);
            footerBtnEdProducto.setTranslateY(-70);
 
            
            idObjSelecEd = productoSelec.getId_producto();
            System.out.println("productoSelec idAlam"+idObjSelecEd);
            
            textNombreAñProducto.setText(productoSelec.getNombre());
            cargarImagen(productoSelec.getImagen(), imgAñProducto);
            imgArrastrada = false;
            arrastrarImg();
            
            
            
            choiceBoxTipoAñProducto.setItems(FXCollections.observableArrayList(Productos.TipoProducto.values()));
            choiceBoxTipoAñProducto.setValue(productoSelec.getTipo());

            choiceBoxTipoAñProducto.setOnAction((event) -> {
                if (choiceBoxTipoAñProducto.getValue() == Productos.TipoProducto.Ropa) {
                    ObservableList<Productos.SubTipoRopaProducto> ropaOpciones = FXCollections.observableArrayList(Productos.SubTipoRopaProducto.values());
                    choiceBoxSubTipoAñProducto.setItems(ropaOpciones);
                    choiceBoxSubTipoAñProducto.setDisable(false);
                } else if (choiceBoxTipoAñProducto.getValue() == Productos.TipoProducto.Accesorios) {
                    ObservableList<Productos.SubTipoAccProducto> accesoriosOpciones = FXCollections.observableArrayList(Productos.SubTipoAccProducto.values());
                    choiceBoxSubTipoAñProducto.setItems(accesoriosOpciones);
                    choiceBoxSubTipoAñProducto.setDisable(false);
                } else {
                    choiceBoxSubTipoAñProducto.setItems(FXCollections.observableArrayList());
                    choiceBoxSubTipoAñProducto.setDisable(true);
                    choiceBoxSubTipoAñProducto.setValue(null);
                }
            });
            
            Productos.TallaProducto tallaSeleccionada = productoSelec.getTalla().iterator().next();
            comboBoxTallasAñProducto.setItems(FXCollections.observableArrayList(Productos.TallaProducto.values()));
            comboBoxTallasAñProducto.getSelectionModel().select(tallaSeleccionada);

            textPrecioAñProducto.setText(String.valueOf(productoSelec.getPrecio()));
            textStockAñProducto.setText(String.valueOf(productoSelec.getStock()));
            textTiendaEdProd.setText(obtenerIdNombreTiendas(productoSelec.getId_tienda()));
            textAlmacenEdProd.setText(obtenerIdNombreAlmacenes(productoSelec.getId_almacen()));
            
            comprobarValidacionesAñProducto("ed");
            
        } else if (tablaListaTiendas.getSelectionModel().getSelectedItem() != null){
            Tiendas tiendaSelec = tablaListaTiendas.getSelectionModel().getSelectedItem();
            ObservableList<Tiendas.TipoTienda> opcTipo = FXCollections.observableArrayList(Tiendas.TipoTienda.values());
            
            footerEdTienda.setTranslateY(-40);

            mostrarPane(paneAñadirTienda);
            footerAñTienda.setVisible(false);
            footerAñTienda.setPrefHeight(0);
            footerEdTienda.setVisible(true);
            
            idObjSelecEd = tiendaSelec.getId_tienda();
            System.out.println("almacenSelec idAlam"+idObjSelecEd);
            
            textNombreAñTienda.setText(tiendaSelec.getNombre());
            comboBoxTipoAñTienda.setValue(tiendaSelec.getTipo());
            textDirAñTienda.setText(tiendaSelec.getDireccion());
            comboBoxPaisAñTienda.setValue(tiendaSelec.getPais());
            
            comboBoxPaisAñTienda.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(comboBoxPaisAñTienda.getValue() == "España"){

                    comboBoxCiudadAñTienda.setDisable(false);
                    comboBoxCiudadAñTienda.setValue("");

                }else{
                    comboBoxCiudadAñTienda.setDisable(true);
                    comboBoxCiudadAñTienda.setValue(tiendaSelec.getCiudad());
                }
            });
            
            textTelAñTienda.setText(Integer.toString(tiendaSelec.getTelefono()));
            
         
            String horarioJsonOriginal = tiendaSelec.getHorarioString();
            System.out.println("Horario JSON original: " + horarioJsonOriginal);
            
            Map<String, String> horarioMap = new HashMap<>();
            String[] diasYhoras = horarioJsonOriginal.split(", ");
            for (String diaYhora : diasYhoras) {
                String[] partes = diaYhora.split(": ");
                if (partes.length == 2) {
                    String dia = partes[0].toLowerCase();
                    String horas = partes[1];
                    horarioMap.put(dia, horas);
                }
            }
        
            //String horarioJson = horarioJsonOriginal.replaceAll("(\\w+):", "\"$1\":").replaceAll("([^,]+)", "\"$1\"");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String horarioJson = objectMapper.writeValueAsString(horarioMap);
                System.out.println("Horario JSON ajustado: " + horarioJson);
                horarioMap = objectMapper.readValue(horarioJson, Map.class);

                textAperLHorarioAñTienda.setText(horarioMap.get("lunes").split("-")[0]);
                textCieLHorarioAñTienda.setText(horarioMap.get("lunes").split("-")[1]);

                textAperMHorarioAñTienda.setText(horarioMap.get("martes").split("-")[0]);
                textCieMHorarioAñTienda.setText(horarioMap.get("martes").split("-")[1]);

                textAperXHorarioAñTienda.setText(horarioMap.get("miércoles").split("-")[0]);
                textCieXHorarioAñTienda.setText(horarioMap.get("miércoles").split("-")[1]);

                textAperJHorarioAñTienda.setText(horarioMap.get("jueves").split("-")[0]);
                textCieJHorarioAñTienda.setText(horarioMap.get("jueves").split("-")[1]);

                textAperVHorarioAñTienda.setText(horarioMap.get("viernes").split("-")[0]);
                textCieVHorarioAñTienda.setText(horarioMap.get("viernes").split("-")[1]);

                textAperSHorarioAñTienda.setText(horarioMap.get("sábado").split("-")[0]);
                textCieSHorarioAñTienda.setText(horarioMap.get("sábado").split("-")[1]);

                textAperDHorarioAñTienda.setText(horarioMap.get("domingo").split("-")[0]);
                textCieDHorarioAñTienda.setText(horarioMap.get("domingo").split("-")[1]);
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al convertir JSON a mapa: " + e.getMessage());
            }
            
            //para q salgan las demas op
            comboBoxTipoAñTienda.setItems(opcTipo);
            comboBoxPaisAñTienda.getItems().addAll(paisesEu);
            comboBoxCiudadAñTienda.getItems().addAll(ciudadesEs);
            
            comprobarValidacionesAñTienda();
            
        }else if(tablaListaAlmacenes.getSelectionModel().getSelectedItem() != null){
            
            Almacenes almacenSelec = tablaListaAlmacenes.getSelectionModel().getSelectedItem();
            
            footerEdAlmacen.setTranslateY(-40);
            
            mostrarPane(paneAñadirAlmacen);
            footerAñAlmacen.setVisible(false);
            footerAñAlmacen.setPrefHeight(0);
            footerEdAlmacen.setVisible(true);
            
            idObjSelecEd = almacenSelec.getId_almacen();
            System.out.println("almacenSelec idAlam"+idObjSelecEd);
            
            textNombreAñAlmacen.setText(almacenSelec.getNombre());
            textDirAñAlmacen.setText(almacenSelec.getDireccion());
            comboBoxPaisAñAlmacen.setValue(almacenSelec.getPais());
            
            comboBoxPaisAñAlmacen.valueProperty().addListener((observable, oldValue, newValue) -> {
                if(comboBoxPaisAñAlmacen.getValue() == "España"){

                    comboBoxCiudadAñAlmacen.setDisable(false);
                    comboBoxCiudadAñAlmacen.setValue("");

                }else{
                    comboBoxCiudadAñAlmacen.setDisable(true);
                    comboBoxCiudadAñAlmacen.setValue(almacenSelec.getCiudad());
                }
            });

            textTelAñAlmacen.setText(Integer.toString(almacenSelec.getTelefono()));
            textCapTotalAñAlmacen.setText(Integer.toString(almacenSelec.getCapacidad_total()));
            textIdTiendaAñAlmacen.setValue(almacenSelec.getId_tienda());
            
            //? mostrar el horario real
            textAperLHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieLHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textAperMHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieMHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textAperXHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieXHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textAperJHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieJHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textAperVHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieVHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textAperSHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieSHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textAperDHorarioAñAlmacen.setText(almacenSelec.getNombre());
            textCieDHorarioAñAlmacen.setText(almacenSelec.getNombre());
            
            //para q salgan las demas op
            comboBoxPaisAñAlmacen.getItems().addAll(paisesEu);
            comboBoxCiudadAñAlmacen.getItems().addAll(ciudadesEs);
            
        }

    }
    @FXML
    private void aceptarEdProducto(){
        System.out.println("Editando producto: ");
        String nombreProd = textNombreAñProducto.getText().toString();
        String imgProd = imgBase64;
        Enum tipoProd = choiceBoxTipoAñProducto.getValue();
        String tipoProdS = tipoProd.toString();
        Enum subProd = null;
        Enum subProdRopa = null;
        Enum subProdAccesorios = null;
        
        if (tipoProd == Productos.TipoProducto.Ropa) {
            subProdRopa = (Productos.SubTipoRopaProducto) choiceBoxSubTipoAñProducto.getValue();
        } else if (tipoProd == Productos.TipoProducto.Accesorios) {
            subProdAccesorios = (Productos.SubTipoAccProducto) choiceBoxSubTipoAñProducto.getValue();
        } else {
            subProdRopa = null;
            subProdAccesorios = null;
        }
        Enum tallaProd = comboBoxTallasAñProducto.getValue();
        double precioProd = Double.parseDouble(textPrecioAñProducto.getText());
        int stockProd = Integer.parseInt(textStockAñProducto.getText());
        
        Productos productoSelec = obtenerProductoPorId(idObjSelecEd);
        String idTiendaProd = productoSelec.getId_tienda();
        String idAlmacenProd = productoSelec.getId_almacen();
        
        if (comprobarDatosProducto()) {
            System.out.println("Editando producto: " + nombreProd);
            editarProducto(idObjSelecEd, nombreProd, imgProd, tipoProd, subProdRopa, subProdAccesorios, tallaProd, precioProd, stockProd, idTiendaProd, idAlmacenProd);
        
            limpiarPaneAñProducto();
            actualizar();
            mostrarPane(paneContenidoListaProductos);
        }
    }
    @FXML
    private void cancelarEdProducto(){
        mostrarPane(paneContenidoListaProductos);
        limpiarPaneAñProducto();
    }
    @FXML
    private void aceptarEdTienda(){
        String idTiendaSelec = idObjSelecEd;
        String nombreTienda = textNombreAñTienda.getText();
        String tipoTienda = comboBoxTipoAñTienda.getValue().toString();
        String dirTienda = textDirAñTienda.getText();
        String paisTienda = comboBoxPaisAñTienda.getValue().toString();
        String ciudadTienda = comboBoxCiudadAñTienda.getValue().toString();
        int telefonoTienda = Integer.parseInt(textTelAñTienda.getText());
        String horarioJson = recogerHorario();
                
        if(comprobarDatosTienda()){
            System.out.println("-- editando tienda");
            editarTienda(idTiendaSelec,nombreTienda, tipoTienda, dirTienda, ciudadTienda, paisTienda, telefonoTienda, horarioJson);
            limpiarPaneAñTienda();
            actualizar();
            mostrarPane(paneContenidoListaTiendas);
        }
    }
    @FXML
    private void cancelarEdTienda(){
        mostrarPane(paneContenidoListaTiendas);
        limpiarPaneAñTienda();
    }
    @FXML
    private void aceptarEdAlmacen(){
        String idAlmacenSelec = idObjSelecEd;
        String nombreAlmacen = textNombreAñAlmacen.getText();
        String dirAlmacen = textDirAñAlmacen.getText();
        String ciudadAlmacen = comboBoxCiudadAñAlmacen.getValue().toString();
        String paisAlmacen = comboBoxPaisAñAlmacen.getValue().toString();
        int telefonoAlmacen = Integer.parseInt(textTelAñAlmacen.getText());
        int capacidadTotalAlmacen = Integer.parseInt(textCapTotalAñAlmacen.getText());
        String idTiendaAlmacen = textIdTiendaAñAlmacen.getValue().toString();
        String horarioJson = recogerHorario();
        
        if(comprobarDatosAlmacen()){
            System.out.println("-- aceptarAñAlmacen");
            editarAlmacen(idAlmacenSelec,nombreAlmacen, dirAlmacen, ciudadAlmacen, paisAlmacen, telefonoAlmacen, horarioJson, capacidadTotalAlmacen, idTiendaAlmacen);
            limpiarPaneAñAlmacen();
            actualizar();
            mostrarPane(paneContenidoListaAlmacenes);
        }
    }
    @FXML
    private void cancelarEdAlmacen(){
        mostrarPane(paneContenidoListaAlmacenes);
        limpiarPaneAñAlmacen();
    }
            
    private void actualizar() {
        ObservableList<Productos> productosActualizados = darListaProductos();
        if (productosActualizados != null && !productosActualizados.isEmpty()) {
            tablaListaProductos.setItems(productosActualizados);
            tablaListaProductos.refresh();
            System.out.println("Tabla productos actualizada");
        }

        ObservableList<Tiendas> tiendasActualizadas = darListaTiendas();
        if (tiendasActualizadas != null && !tiendasActualizadas.isEmpty()) {
            tablaListaTiendas.setItems(tiendasActualizadas);
            tablaListaTiendas.refresh();
            System.out.println("Tabla tiendas actualizada");
        }

        ObservableList<Almacenes> almacenesActualizados = darListaAlmacenes();
        if (almacenesActualizados != null && !almacenesActualizados.isEmpty()) {
            tablaListaAlmacenes.setItems(almacenesActualizados);
            tablaListaAlmacenes.refresh();
            System.out.println("Tabla almacenes actualizada");
        }
    }


            
    @FXML
    private void borrar(){
        ObservableList<Productos> productos = darListaProductos();
        ObservableList<Tiendas> tiendas = darListaTiendas();
        ObservableList<Almacenes> almacenes = darListaAlmacenes();
                
        if(tablaListaProductos.getSelectionModel().getSelectedItem() != null){
            Productos productoSelec = tablaListaProductos.getSelectionModel().getSelectedItem();
            String idProducto = productoSelec.getId_producto();
            String idTienda = productoSelec.getId_tienda();
            String idAlmacen = productoSelec.getId_almacen();
            
            if(!verificarTiendaExistente(idTienda)){
                System.out.println("La tienda con id o nombre "+idTienda+" , NO existe para el producto "+idProducto);
            }else if (!verificarAlmacenExistente(idAlmacen)){
                System.out.println("El almacen con id o nombre "+idAlmacen+" , NO existe para el producto "+idProducto);
            }

            boolean eliminado = borrarProducto(idProducto,idTienda,idAlmacen);


            if (!eliminado) {
                actualizar();
            } 
        
        } else if(tablaListaTiendas.getSelectionModel().getSelectedItem() != null){
            Tiendas tiendaSelec = tablaListaTiendas.getSelectionModel().getSelectedItem();
            String idTienda = tiendaSelec.getId_tienda();

            boolean eliminado = borrarTienda(idTienda);

            if (!eliminado) {
                actualizar();
            } 
            
        } else if(tablaListaAlmacenes.getSelectionModel().getSelectedItem() != null){
            Almacenes almacenSelec = tablaListaAlmacenes.getSelectionModel().getSelectedItem();
            String idAlmacen = almacenSelec.getId_almacen();

            boolean eliminado = borrarAlmacen(idAlmacen);

            if (!eliminado) {
                actualizar();
            } 
        }
    }
   
    
    
    @FXML
    private void verSeleccion() {
       
        if (tablaListaProductos.getSelectionModel().getSelectedItem() != null  || tablaProductosTienda.getSelectionModel().getSelectedItem() != null || tablaProductosAlmacen.getSelectionModel().getSelectedItem() != null){
            Productos productoSelec = tablaListaProductos.getSelectionModel().getSelectedItem();
            Productos tiendaSelecPro = tablaProductosTienda.getSelectionModel().getSelectedItem();
            Productos almacenSelecPro = tablaProductosAlmacen.getSelectionModel().getSelectedItem();
            
            mostrarPane(paneContenidoProductoSelec);
            visiblePaneCabecera(true);
            
            
            if(productoSelec != null){
                var almacenProductoSelec = productoSelec.getId_almacen();
                
                rellenarProductoSelec(productoSelec);
                rellenarTiendasProductoSelec(productoSelec.getId_producto());
                rellenarAlmacenesProductoSelec(almacenProductoSelec);
                
            }else if (tiendaSelecPro != null){
                var almacenProductoSelec = tiendaSelecPro.getId_almacen();
                
                rellenarProductoSelec(productoSelec);
                rellenarTiendasProductoSelec(productoSelec.getId_producto());
                rellenarAlmacenesProductoSelec(almacenProductoSelec);
                
            } else if(almacenSelecPro != null){
                var almacenProductoSelec = almacenSelecPro.getId_almacen();
                
                rellenarProductoSelec(almacenSelecPro);
                rellenarTiendasProductoSelec(almacenSelecPro.getId_producto());
                rellenarAlmacenesProductoSelec(almacenProductoSelec);
            }

            tablaListaProductos.getSelectionModel().clearSelection();
                    
        }else if (tablaListaTiendas.getSelectionModel().getSelectedItem() != null || tablaTiendasProducto.getSelectionModel().getSelectedItem() != null || tablaTiendasAlmacen.getSelectionModel().getSelectedItem() != null){
            Tiendas tiendaSelec = tablaListaTiendas.getSelectionModel().getSelectedItem();
            Tiendas tiendaSelecPro = tablaTiendasProducto.getSelectionModel().getSelectedItem();
            Tiendas almacenSelecPro = tablaTiendasAlmacen.getSelectionModel().getSelectedItem();
            
            mostrarPane(paneContenidoTiendaSelec);
            visiblePaneCabecera(true);
            
            if(tiendaSelec != null){
                rellenarTablaTiendaSelec(tiendaSelec);
                rellenarProductosTiendaSelec(tiendaSelec.getId_tienda());
                rellenarAlmacenesTiendaSelec(tiendaSelec.getId_tienda());
                
            }else if(tiendaSelecPro != null){
                rellenarTablaTiendaSelec(tiendaSelecPro);
                rellenarProductosTiendaSelec(tiendaSelecPro.getId_tienda());
                rellenarAlmacenesTiendaSelec(tiendaSelecPro.getId_tienda());
                
            }else if (almacenSelecPro != null){
                rellenarTablaTiendaSelec(almacenSelecPro);
                rellenarProductosTiendaSelec(almacenSelecPro.getId_tienda());
                rellenarAlmacenesTiendaSelec(almacenSelecPro.getId_tienda());
            }
            
            
            tablaListaTiendas.getSelectionModel().clearSelection();
            
        }else if (tablaListaAlmacenes.getSelectionModel().getSelectedItem() != null || tablaAlmacenesProductoSelec.getSelectionModel().getSelectedItem() != null || tablaAlmacenesTienda.getSelectionModel().getSelectedItem() != null){
            Almacenes almacenSelec = tablaListaAlmacenes.getSelectionModel().getSelectedItem();
            Almacenes prodSelecAlma = tablaAlmacenesProductoSelec.getSelectionModel().getSelectedItem();
            Almacenes tiendaSelecAlma = tablaAlmacenesTienda.getSelectionModel().getSelectedItem();
            
            mostrarPane(paneContenidoAlmacenSelec);
            visiblePaneCabecera(true);
            
            //mostrar valores del tile
            barraProgreso.setValue(almacenSelec.getCapacidad_ocupada());
            barraProgreso.setMaxValue(almacenSelec.getCapacidad_total());
            barraProgreso.setUnit(Integer.toString(almacenSelec.getCapacidad_total())+"aa");

            //barraProgreso.requestLayout();
            
            if(almacenSelec != null){
                var tiendaAlmacenSelec = almacenSelec.getId_tienda();
                
                rellenarAlmacenSelec(almacenSelec);
                rellenarProductosAlmacenSelec(almacenSelec.getId_almacen());
                rellenarTiendasAlmacenSelec(tiendaAlmacenSelec);
                
                //barraporgreso2
                barraProgreso2.setValue(almacenSelec.getCapacidad_ocupada());
                barraProgreso2.setMaxValue(almacenSelec.getCapacidad_total());
                barraProgreso2.setUnit(Integer.toString(almacenSelec.getCapacidad_total()) + " Un.");

                
            } else if(prodSelecAlma != null){
                var tiendaAlmacenSelec = prodSelecAlma.getId_tienda();
                
                rellenarAlmacenSelec(prodSelecAlma);
                rellenarProductosAlmacenSelec(prodSelecAlma.getId_almacen());
                rellenarTiendasAlmacenSelec(tiendaAlmacenSelec);
            }else if(tiendaSelecAlma != null){
                var tiendaAlmacenSelec = tiendaSelecAlma.getId_tienda();
                
                rellenarAlmacenSelec(tiendaSelecAlma);
                rellenarProductosAlmacenSelec(tiendaSelecAlma.getId_almacen());
                rellenarTiendasAlmacenSelec(tiendaAlmacenSelec);
            }
            
            tablaListaAlmacenes.getSelectionModel().clearSelection();
        }
       
    }  
    
    // VER INFORMES:
    @FXML 
    private void verInformes(){
        try {
            //cargar modal
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModalInformes.fxml"));
            Parent root = fxmlLoader.load();

            //observar controlador, para cambiar tema 
            ControladorModalInformes controladorInformes = fxmlLoader.getController();
            controladorInformes.recibirEstadoTema(temaOscuro);
            
            String idiomaSeleccionado = btnCambioIdioma.getValue().equals("Español") ? "es" : "en";
            controladorInformes.recibirIdioma(idiomaSeleccionado);
            
            Stage modalStage = new Stage();
            //no se puede salir del modal
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(stage);
            Image icon = new Image("logo.png");
            modalStage.getIcons().add(icon);
            modalStage.setTitle("Informes");
            modalStage.setScene(new Scene(root));
            
            //mostrar el modal sin salir de inicio
            modalStage.showAndWait();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //pg producto selec:
    private void rellenarProductoSelec(Productos productoSelec){
        textIdProductoSelec.setText(productoSelec.getId_producto());
        textNombreProductoSelec.setText(productoSelec.getNombre());
        textTipoProductoSelec.setText(productoSelec.getTipo().name());
        
        textSubTipoProductoSelec.setText(productoSelec.recogerSubTipo());
        textTallaProductoSelec.setText(productoSelec.recogerTallas());
        
        textPrecioProductoSelec.setText(String.format("%.2f €", productoSelec.getPrecio()));
        
        cargarImagen(productoSelec.getImagen(), imgProductoSelec);
    }
    
    public Object contenerProducto(String idProducto) {
        ObservableList<Productos> productos = darListaProductos();
        ObservableList<Tiendas> tiendas = darListaTiendas();
        ObservableList<Almacenes> almacenes = darListaAlmacenes();
        
        for(Productos producto : productos){
            if(producto.getId_producto().equals(idProducto)){
                //System.out.println("idProducto contenerr---"+ producto);
                
                for (Tiendas tienda : tiendas){
                    var idTienda = tienda.getId_tienda();
                    
                    if(producto.getId_tienda().equals(idTienda)){
                        return tienda;
                    }
                }
                
                
                for (Almacenes almacen : almacenes){
                    var idAlmacen = almacen.getId_tienda();
                    
                    if(producto.getId_tienda().equals(idAlmacen)){
                        return almacen;
                    }
                }
            }
        }
        
        return null;
               
    }
    private void rellenarTiendasProductoSelec(String idProducto){
        listaTiendas.clear();
        ObservableList<Tiendas> tiendas = darListaTiendas();
        
        for (int i = 0; i < tiendas.size(); i++) {
            Tiendas tienda = tiendas.get(i);
            
            contenerProducto(idProducto);
            listaTiendas.add(tienda);
            //System.err.println("contenerProducto----"+contenerProducto(idProducto));
           
        }   
        
        columnIdProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("id_tienda"));
        columnNombreProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnTipoProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnDirProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnCiudadProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnPaisProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("pais"));
        columnTelProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnHorarioProductoSelecTiendas.setCellValueFactory(new PropertyValueFactory<>("horarioString"));

        tablaTiendasProducto.setItems(listaTiendas);

    }
    private void rellenarAlmacenesProductoSelec(String idProducto){
        listaAlmacenes.clear();
        ObservableList<Almacenes> almacenes = darListaAlmacenes();

        for (int i = 0; i < almacenes.size(); i++) {
            Almacenes almacen = almacenes.get(i);
            
            contenerProducto(idProducto);
            listaAlmacenes.add(almacen); 
        }

        columnIdAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("id_almacen"));
        columnNombreAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDirAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnCiudadAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnPaisAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("pais"));
        columnTelAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnHoarioAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("horarioString"));
        columnCapOcupadaAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("capacidad_ocupada"));
        columnCapTotalAlmacenesSelecProducto.setCellValueFactory(new PropertyValueFactory<>("capacidad_total"));

        tablaAlmacenesProductoSelec.setItems(listaAlmacenes);
    }
    //para buscar la img
    private boolean esBase64(String string) {
        try {
            Base64.getDecoder().decode(string);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public void cargarImagen(String imagenNombre, ImageView imgProductoSelec) {
        
        if (imagenNombre != null && !imagenNombre.isEmpty()) {
            if (imagenNombre.startsWith("http://") || imagenNombre.startsWith("https://")) {
                try {
                    Image image = new Image(imagenNombre);
                    imgProductoSelec.setImage(image);
                    System.out.println("Imagen cargada desde URL: " + imagenNombre);
                } catch (Exception e) {
                    System.out.println("Error al cargar imagen desde URL: " + e.getMessage());
                    cargarImagenDesdeAssetsOPorDefecto(imgProductoSelec);
                }
            } else if (esBase64(imagenNombre)) {
                //si la imagen es un string base64, decodificarla
                try {
                    byte[] imageBytes = Base64.getDecoder().decode(imagenNombre);
                    ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                    Image image = new Image(bis);
                    imgProductoSelec.setImage(image);
                    System.out.println("Imagen cargada desde base64");
                } catch (Exception e) {
                    System.out.println("Error al cargar imagen desde base64: " + e.getMessage());
                    cargarImagenDesdeAssetsOPorDefecto(imgProductoSelec);
                }
            } else {

                try {
                    Image image = new Image(getClass().getResourceAsStream("/" + imagenNombre));
                    imgProductoSelec.setImage(image);
                    System.out.println("Imagen cargada desde archivo: " + imagenNombre);
                } catch (Exception e) {
                    System.out.println("Error al cargar imagen desde archivo: " + e.getMessage());
                    cargarImagenDesdeAssetsOPorDefecto(imgProductoSelec);
                }
            }
        } else {
            cargarImagenDesdeAssetsOPorDefecto(imgProductoSelec);
        }
    }

    private void cargarImagenDesdeAssetsOPorDefecto(ImageView imgProductoSelec) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/sinFoto.png"));
            imgProductoSelec.setImage(image);
            System.out.println("Imagen por defecto cargada");
        } catch (Exception e) {
            System.out.println("Error al cargar imagen por defecto: " + e.getMessage());
            e.printStackTrace();
        }
    }


    
    
    //pg tienda selec:
    private void rellenarTablaTiendaSelec(Tiendas tiendaSelec){
        texIdTiendaSelec.setText(tiendaSelec.getId_tienda());
        textNombreTiendaSelec.setText(tiendaSelec.getNombre());
        textTipoTiendaSelec.setText(tiendaSelec.getTipo().name());
        textDirTiendaSelec.setText(tiendaSelec.getDireccion());
        textTelTiendaSelec.setText(Integer.toString(tiendaSelec.getTelefono()));
        textCiudadTiendaSelec.setText(tiendaSelec.getCiudad());
        textPaisTiendaSelec.setText(tiendaSelec.getPais());
        
        //para el horario:
        ObservableList<Tiendas> listaTiendaSeleccionada = FXCollections.observableArrayList(tiendaSelec);
        Map<String, String> horario = tiendaSelec.getHorario();

        columnLHorarioTiendaSelec.setCellValueFactory(cellData -> {
            String horarioLunes = horario.get("lunes");
            if (horarioLunes == null || horarioLunes.isEmpty()) {
                horarioLunes = "No disponible";
            }
            return new SimpleStringProperty(horarioLunes);
        });

        columnMHorarioTiendaSelec.setCellValueFactory(cellData -> {
            String horarioMartes = horario.get("martes");
            if (horarioMartes == null || horarioMartes.isEmpty()) {
                horarioMartes = "No disponible";
            }
            return new SimpleStringProperty(horarioMartes);
        });

        columnXHorarioTiendaSelec.setCellValueFactory(cellData -> {
            String horarioMiercoles = horario.get("miércoles");
            if (horarioMiercoles == null || horarioMiercoles.isEmpty()) {
                horarioMiercoles = "No disponible";
            }
            return new SimpleStringProperty(horarioMiercoles);
        });

        columnJHorarioTiendaSelec.setCellValueFactory(cellData -> {
            String horarioJueves = horario.get("jueves");
            if (horarioJueves == null || horarioJueves.isEmpty()) {
                horarioJueves = "No disponible";
            }
            return new SimpleStringProperty(horarioJueves);
        });

        columnVHorarioTiendaSelec.setCellValueFactory(cellData -> {
            String horarioViernes = horario.get("viernes");
            if (horarioViernes == null || horarioViernes.isEmpty()) {
                horarioViernes = "No disponible";
            }
            return new SimpleStringProperty(horarioViernes);
        });

        columnSHorarioTiendaelec.setCellValueFactory(cellData -> {
            String horarioSabado = horario.get("sábado");
            if (horarioSabado == null || horarioSabado.isEmpty()) {
                horarioSabado = "No disponible";
            }
            return new SimpleStringProperty(horarioSabado);
        });

        columnDHorarioTiendaSelec.setCellValueFactory(cellData -> {
            String horarioDomingo = horario.get("domingo");
            if (horarioDomingo == null || horarioDomingo.isEmpty()) {
                horarioDomingo = "No disponible";
            }
            return new SimpleStringProperty(horarioDomingo);
        });

        tablaHorarioTiendaSelec.setItems(listaTiendaSeleccionada);
    }
    private void rellenarProductosTiendaSelec(String idTienda) {
        listaProductos.clear();
        ObservableList<Productos> productos = darListaProductos();
        
        for (int i = 0; i < productos.size(); i++) {
            Productos producto = productos.get(i);
            if (producto.getId_tienda().equals(idTienda)) {
                listaProductos.add(producto);
            }
        }
        
        columnIdTiendaSelecProd.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        columnNombreTiendaSelecProd.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnImgTiendaSelecProd.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        columnTipoTiendaSelecProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnSubTipoTiendaSelecProd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().recogerSubTipo()));
        columnTallaTiendaSelecProd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().recogerTallas()));
        columnPrecioTiendaSelecProd.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnStockTiendaSelecProd.setCellValueFactory(new PropertyValueFactory<>("stock"));

        tablaProductosTienda.setItems(listaProductos);
    }
    private void rellenarAlmacenesTiendaSelec(String idTienda) {
        listaAlmacenes.clear();
        ObservableList<Almacenes> almacenes = darListaAlmacenes();
        
        for (int i = 0; i < almacenes.size(); i++) {
            Almacenes almacen = almacenes.get(i);
            if (almacen.getId_tienda().equals(idTienda)) {
                listaAlmacenes.add(almacen);
            }
        }
        
        columnIdTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("id_almacen"));
        columnNombreTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnDirTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnCiudadTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnPaisTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("pais"));
        columnTelTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnHorarioTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("horarioString"));
        columnCapOcupadaTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("capacidad_ocupada"));
        columnCapTotalTiendaSelecAlmac.setCellValueFactory(new PropertyValueFactory<>("capacidad_total"));

        
        tablaAlmacenesTienda.setItems(listaAlmacenes);
    }
    
    
    //pg almacen selec:
    private void rellenarAlmacenSelec(Almacenes almacenSelec){
        texIdAlmacenSelec.setText(almacenSelec.getId_almacen());
        textNombreAlmacenSelec.setText(almacenSelec.getNombre());
        textDirAlmacenSelec.setText(almacenSelec.getDireccion());
        textTelAlmacenSelec.setText(Integer.toString(almacenSelec.getTelefono()));
        textCiudadAlmacenSelec.setText(almacenSelec.getCiudad());
        textPaisAlmacenSelec.setText(almacenSelec.getPais());
        textCapOcupadaAlmacenSelec.setText(Integer.toString(almacenSelec.getCapacidad_ocupada())+" Uni.");
        textCapTotalAlmacenSelec.setText(Integer.toString(almacenSelec.getCapacidad_total())+" Uni.");
        
        //para el horario:
        ObservableList<Almacenes> listaAlmacenSeleccionada = FXCollections.observableArrayList(almacenSelec);
        Map<String, String> horario = almacenSelec.getHorario();

        columnLHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioLunes = horario.get("lunes");
            if (horarioLunes == null || horarioLunes.isEmpty()) {
                horarioLunes = "No disponible";
            }
            return new SimpleStringProperty(horarioLunes);
        });

        columnMHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioMartes = horario.get("martes");
            if (horarioMartes == null || horarioMartes.isEmpty()) {
                horarioMartes = "No disponible";
            }
            return new SimpleStringProperty(horarioMartes);
        });

        columnXHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioMiercoles = horario.get("miércoles");
            if (horarioMiercoles == null || horarioMiercoles.isEmpty()) {
                horarioMiercoles = "No disponible";
            }
            return new SimpleStringProperty(horarioMiercoles);
        });

        columnJHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioJueves = horario.get("jueves");
            if (horarioJueves == null || horarioJueves.isEmpty()) {
                horarioJueves = "No disponible";
            }
            return new SimpleStringProperty(horarioJueves);
        });

        columnVHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioViernes = horario.get("viernes");
            if (horarioViernes == null || horarioViernes.isEmpty()) {
                horarioViernes = "No disponible";
            }
            return new SimpleStringProperty(horarioViernes);
        });

        columnSHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioSabado = horario.get("sábado");
            if (horarioSabado == null || horarioSabado.isEmpty()) {
                horarioSabado = "No disponible";
            }
            return new SimpleStringProperty(horarioSabado);
        });

        columnDHorarioAlmacenSelec.setCellValueFactory(cellData -> {
            String horarioDomingo = horario.get("domingo");
            if (horarioDomingo == null || horarioDomingo.isEmpty()) {
                horarioDomingo = "No disponible";
            }
            return new SimpleStringProperty(horarioDomingo);
        });

        tablaHorarioAlmacenSelec.setItems(listaAlmacenSeleccionada);
    }
    private void rellenarProductosAlmacenSelec(String idAlmacen){
        listaProductos.clear();
        ObservableList<Productos> productos = darListaProductos();

        for (int i = 0; i < productos.size(); i++) {
            Productos producto = productos.get(i);
            
            if (producto.getId_almacen().equals(idAlmacen)) {
                listaProductos.add(producto);
                //System.out.println("2productos de almacen "+producto.getId_producto());
            }
        }

        columnIdProductosSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        columnNombreProductosSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnImgProductosSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("imagen"));
        columnTipoProductosSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnSubTipoProductosSelecAlmacen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().recogerSubTipo()));
        columnTallaProductosSelecAlmacen.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().recogerTallas()));
        columnPrecioProductosSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("precio"));
        columnStockProductosSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("stock"));

        
        tablaProductosAlmacen.setItems(listaProductos);
    }
    private void rellenarTiendasAlmacenSelec(String idTienda){
        listaTiendas.clear();
        ObservableList<Tiendas> tiendas = darListaTiendas();
        System.out.println("Almacen selec, id TIENDA -- " + idTienda);

        for (int i = 0; i < tiendas.size(); i++) {
            Tiendas tienda = tiendas.get(i);
            if (tienda.getId_tienda().equals(idTienda)) {
                listaTiendas.add(tienda);
                //System.out.println("cc -- " + tienda.getId_tienda());
            }
        }
        //System.out.println("Num tiendas añadidas: " + listaTiendas.size());

        columnIdTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("id_tienda"));
        columnNombreTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnTipoTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        columnDirTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        columnCiudadTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        columnPaisTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("pais"));
        columnTelTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columnHorarioTiendasSelecAlmacen.setCellValueFactory(new PropertyValueFactory<>("horarioString"));
        

        tablaTiendasAlmacen.setItems(listaTiendas);tablaTiendasAlmacen.refresh();
    }

    @FXML private Tab tabInfoProducto, tabTiendasProducto, tabAlmacenesProducto, tabInfoProducto1,tabTiendas,tabInfoTiendaSelec,tabProductosTienda,tabAlmacenesTienda,tabAñTienda,tabAlmacenes;
    @FXML private Text labelIdProductoSelec,labelNombreProductoSelec,labelTipoProductoSelec,labelSubTipoProductoSelec,labelTallaProductoSelec,labelPrecioProductoSelec,labelNombreAñProducto,labelTipoAñProducto,labelSubTipoAñProducto,labelTallaAñProducto,labelPrecioAñProducto,labelStockAñProducto;
    @FXML private Label labelElegirTiendaAñProd,labelElegirAlmacenAñProd,labelElegirTiendaAñProd1,labelElegirAlmacenAñProd1, labelIdTiendaSelec,labelNombreTiendaSelec,labelTipoTiendaSelec,labelCiudadTiendaSelec,labelDirTiendaSelec,labelHorarioTiendaSelec,labelPaisTiendaSelec,labelTelTiendaSelec,labelNombreAñTienda,labelTipoAñTienda,labelDirAñTienda,labelPaisAñTienda,labelCiudadAñTienda,labelTelAñTienda,labelHorarioAñTienda,labelHorarioAAñTienda,labelHorarioCAñTienda,labelHorarioLAñTienda,labelHorarioMAñTienda,labelHorarioXAñTienda,labelHorarioJAñTienda,labelHorarioVAñTienda,labelHorarioSAñTienda,labelHorarioDAñTienda;
    @FXML private Button btnAceptarAñProducto,btnCancelarAñProducto,btnAceptarEdProducto,btnCancelarEdProducto;
    
    //recoger texto para cambio idioma
    @FXML private ComboBox<String> btnCambioIdioma;
    private void cargarIdiomas(String idioma){
        
        if (idioma.equals("en")) {
            rb = ResourceBundle.getBundle("messages", new java.util.Locale("en", "US"));
        } else {
            rb = ResourceBundle.getBundle("messages", new java.util.Locale("es", "ES"));
        }

        ponerTextos();
    }
    @FXML
    private void cambiarIdioma() {
        
        String idiomaSelec = btnCambioIdioma.getValue();
        
        if ("Español".equals(idiomaSelec)) {
            cargarIdiomas("es");
        } else if ("English".equals(idiomaSelec)) {
            cargarIdiomas("en");
        }
    }
    private void ponerTextos(){
        btnTablero.setText(rb.getString("btnTablero"));
        btnProductos.setText(rb.getString("btnProductos"));
        btnTiendas.setText(rb.getString("btnTiendas"));
        btnAlmacenes.setText(rb.getString("btnAlmacenes"));
        btnAjustes.setText(rb.getString("btnAjustes"));
        textIdioma.setText(rb.getString("textIdioma"));
        textoDatosProcutos.setText(rb.getString("textoDatosProcutos"));
        btnAñadir.setText(rb.getString("btnAñadir"));
        btnEditar.setText(rb.getString("btnEditar"));
        btnBorrar.setText(rb.getString("btnBorrar"));
        btnVer.setText(rb.getString("btnVer"));
        btnInformes.setText(rb.getString("btnInformes"));
        tabInfoProducto.setText(rb.getString("tabProductos"));
        columnIdListaProductos.setText(rb.getString("columnIdListaProductos"));
        columnNombreListaProductos.setText(rb.getString("columnNombreListaProductos"));
        columnImgListaProductos.setText(rb.getString("columnImgListaProductos"));
        columnTipoListaProductos.setText(rb.getString("columnTipoListaProductos"));
        columnSubTipoListaProductos.setText(rb.getString("columnSubTipoListaProductos"));
        columnTallaListaProductos.setText(rb.getString("columnTallaListaProductos"));
        columnPrecioListaProductos.setText(rb.getString("columnPrecioListaProductos"));
        labelIdProductoSelec.setText(rb.getString("labelIdProductoSelec"));
        labelNombreProductoSelec.setText(rb.getString("labelNombreProductoSelec"));
        labelTipoProductoSelec.setText(rb.getString("labelTipoProductoSelec"));
        labelSubTipoProductoSelec.setText(rb.getString("labelSubTipoProductoSelec"));
        labelTallaProductoSelec.setText(rb.getString("labelTallaProductoSelec"));
        labelPrecioProductoSelec.setText(rb.getString("labelPrecioProductoSelec"));
        columnIdProductoSelecTiendas.setText(rb.getString("columnIdProductoSelecTiendas"));
        columnNombreProductoSelecTiendas.setText(rb.getString("columnNombreProductoSelecTiendas"));
        columnTipoProductoSelecTiendas.setText(rb.getString("columnTipoProductoSelecTiendas"));
        columnDirProductoSelecTiendas.setText(rb.getString("columnDirProductoSelecTiendas"));
        columnCiudadProductoSelecTiendas.setText(rb.getString("columnCiudadProductoSelecTiendas"));
        columnPaisProductoSelecTiendas.setText(rb.getString("columnPaisProductoSelecTiendas"));
        columnTelProductoSelecTiendas.setText(rb.getString("columnTelProductoSelecTiendas"));
        columnHorarioProductoSelecTiendas.setText(rb.getString("columnHorarioProductoSelecTiendas"));
        tabTiendasProducto.setText(rb.getString("tabTiendasProducto"));
        tabAlmacenesProducto.setText(rb.getString("tabAlmacenesProducto"));
        columnNombreProductoSelecTiendas.setText(rb.getString("columnIdProductoSelecTiendas"));
        columnNombreProductoSelecTiendas.setText(rb.getString("columnNombreProductoSelecTiendas"));
        columnTipoProductoSelecTiendas.setText(rb.getString("columnTipoProductoSelecTiendas"));
        columnDirProductoSelecTiendas.setText(rb.getString("columnDirProductoSelecTiendas"));
        columnCiudadProductoSelecTiendas.setText(rb.getString("columnCiudadProductoSelecTiendas"));
        columnPaisProductoSelecTiendas.setText(rb.getString("columnPaisProductoSelecTiendas"));
        columnTelProductoSelecTiendas.setText(rb.getString("columnTelProductoSelecTiendas"));
        columnIdAlmacenesSelecProducto.setText(rb.getString("columnIdAlmacenesSelecProducto"));
        columnNombreAlmacenesSelecProducto.setText(rb.getString("columnNombreAlmacenesSelecProducto"));
        columnDirAlmacenesSelecProducto.setText(rb.getString("columnDirAlmacenesSelecProducto"));
        columnCiudadAlmacenesSelecProducto.setText(rb.getString("columnCiudadAlmacenesSelecProducto"));
        columnPaisAlmacenesSelecProducto.setText(rb.getString("columnPaisAlmacenesSelecProducto"));
        columnTelAlmacenesSelecProducto.setText(rb.getString("columnTelAlmacenesSelecProducto"));
        columnHoarioAlmacenesSelecProducto.setText(rb.getString("columnHoarioAlmacenesSelecProducto"));
        columnCapOcupadaAlmacenesSelecProducto.setText(rb.getString("columnCapOcupadaAlmacenesSelecProducto"));
        columnCapTotalAlmacenesSelecProducto.setText(rb.getString("columnCapTotalAlmacenesSelecProducto"));
        tabInfoProducto1.setText(rb.getString("tabInfoProducto1"));
        labelNombreAñProducto.setText(rb.getString("labelNombreAñProducto"));
        labelTipoAñProducto.setText(rb.getString("labelTipoAñProducto"));
        labelSubTipoAñProducto.setText(rb.getString("labelSubTipoAñProducto"));
        labelTallaAñProducto.setText(rb.getString("labelTallaAñProducto"));
        labelPrecioAñProducto.setText(rb.getString("labelPrecioAñProducto"));
        labelStockAñProducto.setText(rb.getString("labelStockAñProducto"));
        labelElegirTiendaAñProd.setText(rb.getString("labelElegirTiendaAñProd"));
        labelElegirAlmacenAñProd.setText(rb.getString("labelElegirAlmacenAñProd"));
        labelElegirTiendaAñProd1.setText(rb.getString("labelElegirTiendaAñProd1"));
        labelElegirAlmacenAñProd1.setText(rb.getString("labelElegirAlmacenAñProd1"));
        btnAceptarAñProducto.setText(rb.getString("btnAceptarAñProducto"));
        btnCancelarAñProducto.setText(rb.getString("btnCancelarAñProducto"));
        btnAceptarEdProducto.setText(rb.getString("btnAceptarEdProducto"));
        btnCancelarEdProducto.setText(rb.getString("btnCancelarEdProducto"));
        tabTiendas.setText(rb.getString("tabTiendas"));
        columnIdListaTiendas.setText(rb.getString("columnIdListaTiendas"));
        columnNombreListaTiendas.setText(rb.getString("columnNombreListaTiendas"));
        columnTipoListaTiendas.setText(rb.getString("columnTipoListaTiendas"));
        columnDirListaTiendas.setText(rb.getString("columnDirListaTiendas"));
        columnCiudadListaTiendas.setText(rb.getString("columnCiudadListaTiendas"));
        columnPaisListaTiendas.setText(rb.getString("columnPaisListaTiendas"));
        columnTelListaTiendas.setText(rb.getString("columnTelListaTiendas"));
        columnHorarioListaTiendas.setText(rb.getString("columnHorarioListaTiendas"));
        tabInfoTiendaSelec.setText(rb.getString("tabInfoTiendaSelec"));
        labelIdTiendaSelec.setText(rb.getString("labelIdTiendaSelec"));
        labelNombreTiendaSelec.setText(rb.getString("labelNombreTiendaSelec"));
        labelTipoTiendaSelec.setText(rb.getString("labelTipoTiendaSelec"));
        labelDirTiendaSelec.setText(rb.getString("labelDirTiendaSelec"));
        labelPaisTiendaSelec.setText(rb.getString("labelPaisTiendaSelec"));
        labelCiudadTiendaSelec.setText(rb.getString("labelCiudadTiendaSelec"));
        labelTelTiendaSelec.setText(rb.getString("labelTelTiendaSelec"));
        labelHorarioTiendaSelec.setText(rb.getString("labelHorarioTiendaSelec"));
        columnLHorarioTiendaSelec.setText(rb.getString("columnLHorarioTiendaSelec"));
        columnMHorarioTiendaSelec.setText(rb.getString("columnMHorarioTiendaSelec"));
        columnXHorarioTiendaSelec.setText(rb.getString("columnXHorarioTiendaSelec"));
        columnJHorarioTiendaSelec.setText(rb.getString("columnJHorarioTiendaSelec"));
        columnVHorarioTiendaSelec.setText(rb.getString("columnVHorarioTiendaSelec"));
        columnSHorarioTiendaelec.setText(rb.getString("columnSHorarioTiendaelec"));
        columnDHorarioTiendaSelec.setText(rb.getString("columnDHorarioTiendaSelec"));
        tabProductosTienda.setText(rb.getString("tabProductosTienda"));
        tabAlmacenesTienda.setText(rb.getString("tabAlmacenesTienda"));
        columnIdTiendaSelecProd.setText(rb.getString("columnIdTiendaSelecProd"));
        columnNombreTiendaSelecProd.setText(rb.getString("columnNombreTiendaSelecProd"));
        columnImgTiendaSelecProd.setText(rb.getString("columnImgTiendaSelecProd"));
        columnTipoTiendaSelecProd.setText(rb.getString("columnTipoTiendaSelecProd"));
        columnSubTipoTiendaSelecProd.setText(rb.getString("columnSubTipoTiendaSelecProd"));
        columnTallaTiendaSelecProd.setText(rb.getString("columnTallaTiendaSelecProd"));
        columnPrecioTiendaSelecProd.setText(rb.getString("columnPrecioTiendaSelecProd"));
        columnStockTiendaSelecProd.setText(rb.getString("columnStockTiendaSelecProd"));
        columnIdTiendaSelecAlmac.setText(rb.getString("columnIdTiendaSelecAlmac"));
        columnNombreTiendaSelecAlmac.setText(rb.getString("columnNombreTiendaSelecAlmac"));
        columnDirTiendaSelecAlmac.setText(rb.getString("columnDirTiendaSelecAlmac"));
        columnPaisTiendaSelecAlmac.setText(rb.getString("columnPaisTiendaSelecAlmac"));
        columnTelTiendaSelecAlmac.setText(rb.getString("columnTelTiendaSelecAlmac"));
        columnHorarioTiendaSelecAlmac.setText(rb.getString("columnHorarioTiendaSelecAlmac"));
        columnCapOcupadaTiendaSelecAlmac.setText(rb.getString("columnCapOcupadaTiendaSelecAlmac"));
        columnCapTotalTiendaSelecAlmac.setText(rb.getString("columnCapTotalTiendaSelecAlmac"));
        tabAñTienda.setText(rb.getString("tabAñTienda"));
        labelNombreAñTienda.setText(rb.getString("labelNombreAñTienda"));
        labelTipoAñTienda.setText(rb.getString("labelTipoAñTienda"));
        labelDirAñTienda.setText(rb.getString("labelDirAñTienda"));
        labelPaisAñTienda.setText(rb.getString("labelPaisAñTienda"));
        labelCiudadAñTienda.setText(rb.getString("labelCiudadAñTienda"));
        labelTelAñTienda.setText(rb.getString("labelTelAñTienda"));
        labelHorarioAñTienda.setText(rb.getString("labelHorarioAñTienda"));
        labelHorarioAAñTienda.setText(rb.getString("labelHorarioAAñTienda"));
        labelHorarioCAñTienda.setText(rb.getString("labelHorarioCAñTienda"));
        labelHorarioLAñTienda.setText(rb.getString("labelHorarioLAñTienda"));
        labelHorarioMAñTienda.setText(rb.getString("labelHorarioMAñTienda"));
        labelHorarioXAñTienda.setText(rb.getString("labelHorarioXAñTienda"));
        labelHorarioJAñTienda.setText(rb.getString("labelHorarioJAñTienda"));
        labelHorarioVAñTienda.setText(rb.getString("labelHorarioVAñTienda"));
        labelHorarioSAñTienda.setText(rb.getString("labelHorarioSAñTienda"));
        labelHorarioDAñTienda.setText(rb.getString("labelHorarioDAñTienda"));
        
        tabAlmacenes.setText(rb.getString("tabAlmacenes"));
        columnNombreListaAlmacenes.setText(rb.getString("columnNombreListaAlmacenes"));
    }
    
    
    //Tooltips:
    private void ponerTooltips(){
        textNombreAñProducto.setTooltip(new Tooltip("Nombre del producto"));
        choiceBoxTipoAñProducto.setTooltip(new Tooltip("Categoría del producto"));
        choiceBoxSubTipoAñProducto.setTooltip(new Tooltip("Sub categoría del producto"));
        comboBoxTallasAñProducto.setTooltip(new Tooltip("Talla del producto"));
        textPrecioAñProducto.setTooltip(new Tooltip("Precio del producto"));
        textStockAñProducto.setTooltip(new Tooltip("Stock del producto"));
        comboBoxElegirTiendaAñProd.setTooltip(new Tooltip("Tienda a la que estará asociada"));
        comboBoxElegirAlmacenAñProd.setTooltip(new Tooltip("Almacén a la que estará asociada"));
        textNombreAñTienda.setTooltip(new Tooltip("Nombre de la tienda"));
        comboBoxTipoAñTienda.setTooltip(new Tooltip("Tipo de la tienda"));    
        textDirAñTienda.setTooltip(new Tooltip("Dirección de la tienda"));
        comboBoxPaisAñTienda.setTooltip(new Tooltip("Dirección de la tienda"));
        comboBoxCiudadAñTienda.setTooltip(new Tooltip("Dirección de la tienda"));
        textTelAñTienda.setTooltip(new Tooltip("Telefono de la tienda"));
        textAperLHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del lunes"));
        textCieLHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del lunes"));
        textAperMHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del martes"));
        textCieMHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del martes"));
        textAperXHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del miercoles"));
        textCieXHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del miercoles"));
        textAperJHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del jueves"));
        textCieJHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del jueves"));
        textAperVHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del viernes"));
        textCieVHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del viernes"));
        textAperSHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del sábado"));
        textCieSHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del sábado"));
        textAperDHorarioAñTienda.setTooltip(new Tooltip("Horario de apertura del domingo"));
        textCieDHorarioAñTienda.setTooltip(new Tooltip("Horario de cierre del domingo"));
        textNombreAñAlmacen.setTooltip(new Tooltip("Nombre del almacén"));
        textDirAñAlmacen.setTooltip(new Tooltip("Dirección del almacén"));
        comboBoxPaisAñAlmacen.setTooltip(new Tooltip("País donde se encuentra el almacén"));
        comboBoxCiudadAñAlmacen.setTooltip(new Tooltip("País donde se encuentra el almacén"));
        textTelAñAlmacen.setTooltip(new Tooltip("Teléfono del almacén"));
        textCapTotalAñAlmacen.setTooltip(new Tooltip("Capacidad total del almacén"));
        textIdTiendaAñAlmacen.setTooltip(new Tooltip("Tienda a la que estará asociada"));
        textAperLHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del lunes"));
        textCieLHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del lunes"));
        textAperMHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del martes"));
        textCieMHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del martes"));
        textAperXHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del miercoles"));
        textCieXHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del miercoles"));
        textAperJHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del jueves"));
        textCieJHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del jueves"));
        textAperVHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del viernes"));
        textCieVHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del viernes"));
        textAperSHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del sábado"));
        textCieSHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del sábado"));
        textAperDHorarioAñAlmacen.setTooltip(new Tooltip("Horario de apertura del domingo"));
        textCieDHorarioAñAlmacen.setTooltip(new Tooltip("Horario de cierre del domingo"));
    }
    
    private void actualizarMaxValue(String newText) {
        int maxValueAñAlmacen = Integer.parseInt(newText);

        if (maxValueAñAlmacen > 0) { 
            barraProgreso.setValue(maxValueAñAlmacen);
            //requestLayout fuerza el cambio
            barraProgreso.requestLayout();

        } else {
            barraProgreso.setValue(1);
        }
    }
    
    //poner para que desde el incio salga paneContenidoInicio
    private void setearPaneInicio(){
        quitarPaneCabecera(true);
        mostrarPane(paneContenidoInicio);
    }
    @FXML
    private ImageView imgTablero, iconoMenu, iconoTablero, iconoProductos, iconoTiendas, iconoAlmacenes, iconoHerramientas, iconoAñadir, iconoEditar, iconoBorrar, iconoVer, iconoInforme;
    private void cargarIconos(){
        //imgTablero.setImage(new Image("fondo.png"));
        iconoMenu.setImage(new Image("menu.png"));
        iconoTablero.setImage(new Image("tablero.png"));
        iconoProductos.setImage(new Image("productos.png"));
        iconoTiendas.setImage(new Image("tiendas.png"));
        iconoAlmacenes.setImage(new Image("almacenes.png"));
        iconoHerramientas.setImage(new Image("ajustes.png"));
        iconoAñadir.setImage(new Image("anadir.png"));
        iconoEditar.setImage(new Image("editar.png"));
        iconoBorrar.setImage(new Image("borrar.png"));
        iconoVer.setImage(new Image("ver.png"));
        iconoInforme.setImage(new Image("informe.png"));
    }
    
    //para ver manual con F1
    private void configurarEventosDeTeclado() {
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getRoot().requestFocus();
                newScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F1) {
                    if (event.isMetaDown() || event.isControlDown() || !event.isShiftDown()) { 
                        //command + F1 para macOS (Meta), F1 para Windows/Linux
                        abrirDocumentacion();
                    }
                }
            });
            }
        });
    }



    GraphicValidationDecoration decorador;
    double maxValueAñAlmacen;
    Scene scene;
    boolean temaOscuro;
    Tile barraProgreso, barraProgreso2;
    @FXML private HBox root;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //poner hei tabla produc:
        //tablaListaProductos.setPrefHeight(342);
        
        //cambio de btnModo claros:
        temaOscuro = false;
        root.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                scene = newScene;

                btnModo.setOnAction(event -> {
                    if (btnModo.isSelected()) {
                        //tema oscuro
                        scene.getStylesheets().clear();
                        scene.getStylesheets().add(getClass().getResource("styleDark.css").toExternalForm());
                        temaOscuro = true;

                    } else {
                        //tema claro
                        scene.getStylesheets().clear();
                        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                        temaOscuro = true;
                    }
                });
                //por defecto es el claro
                btnModo.setSelected(false);
            }
        });
    
        
        //cargar iconos
        cargarIconos();
        
        //cargar los idiomas
        btnCambioIdioma.getItems().addAll("Español", "English");
        btnCambioIdioma.setValue("Español"); 
        btnCambioIdioma.setOnAction(e -> cambiarIdioma());
        
        //panel de inicio
        setearPaneInicio();
        
        //tooltips
        ponerTooltips();
                        
        //activar botones y comprobar su foco
        comprobarFoco();

        
        //barra de progreso
        barraProgreso = TileBuilder.create()
            .skinType(Tile.SkinType.CIRCULAR_PROGRESS)
            .unit("Un.")
            .value(0)
            .maxValue(0)
            .barColor(rgb(74, 213, 245))
            .unitColor(javafx.scene.paint.Color.rgb(138, 207, 116))
            .valueColor(javafx.scene.paint.Color.rgb(138, 207, 116))
            .build();

        barraProgreso.setBackgroundColor(javafx.scene.paint.Color.rgb(0,0, 0, 0)); 
        barraProgreso.setValueColor(javafx.scene.paint.Color.rgb(138, 207, 116)); 

        barraProgresoAñAlmacen.getChildren().add(barraProgreso);
        textCapTotalAñAlmacen.textProperty().addListener((obs, oldText, newText) -> {
            actualizarMaxValue(newText);
        });
        
        barraProgreso2 = TileBuilder.create()
            .skinType(Tile.SkinType.CIRCULAR_PROGRESS)
            .unit("Un.")
            .value(0)
            .maxValue(0)
            .barColor(rgb(74, 213, 245))
            .unitColor(javafx.scene.paint.Color.rgb(138, 207, 116))
            .valueColor(javafx.scene.paint.Color.rgb(138, 207, 116))
            .build();

        barraProgreso2.setBackgroundColor(javafx.scene.paint.Color.rgb(0,0, 0, 0)); 
        barraProgreso2.setValueColor(javafx.scene.paint.Color.rgb(138, 207, 116)); 
        barraProgresoVerAlmacen.getChildren().add(barraProgreso2);
        
    
        //para ver manual con F1 y con command+espacio
        Platform.runLater(() -> {
            String os = System.getProperty("os.name").toLowerCase();
        
            if (os.contains("mac")) {
                // Para macOS, escucha Control + Espacio
                if (stage != null) {
                    Scene scene = stage.getScene();
                    scene.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.SPACE && event.isControlDown()) {
                            System.out.println("Control + Espacio detectado en macOS.");
                            abrirDocumentacion();
                        }
                    });
                }
            } else if (os.contains("win") || os.contains("nix")) {
                // Para Windows y Linux (Ubuntu debería estar en esta categoría como un sistema 'nix'), escucha F1
                if (stage != null) {
                    Scene scene = stage.getScene();
                    scene.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.F1) {
                            System.out.println("F1 detectado en Windows o Ubuntu.");
                            abrirDocumentacion();
                        }
                    });
                }
            }
        });
        
       
        
        /*
        for (ValidationSupport validador : validadores){
            System.out.println("1validadores "+validadores);
            GraphicValidationDecoration decorador = new GraphicValidationDecoration() {
                @Override
                public void applyValidationDecoration(ValidationMessage message) {
                    super.applyValidationDecoration(message);
                    System.out.println("Mensaje:" + message);
                    if (message.getSeverity() == Severity.ERROR || message.getSeverity() == Severity.WARNING) {
                        
                        /*
                        if(validador.equals(nombreAl)){
                            iconoValiNombreAñAlmacen.setGraphic(usarIcono(false));
                        }
                        
                        
                        iconoValiDirAñAlmacen.setGraphic(usarIcono(false));

                        iconoValiPaisAñAlmacen.setGraphic(usarIcono(false));
                        iconoValiCiudaadAñAlmacen.setGraphic(usarIcono(false));
                        iconoValiTelAñAlmacen.setGraphic(usarIcono(false));
                        iconoValiCapTotalAñAlmacen.setGraphic(usarIcono(false));
                        iconoValiIdTiendaAñAlmacen.setGraphic(usarIcono(false));
                        iconoValiHorarioAñAlmacen.setGraphic(usarIcono(false));

                     

                    } else if (message.getSeverity() == Severity.INFO) {

                        // Añadir almacen
                        iconoValiNombreAñAlmacen.setGraphic(usarIcono(true));
                        /*
                        iconoValiDirAñAlmacen.setGraphic(usarIcono(true));
                        iconoValiPaisAñAlmacen.setGraphic(usarIcono(true));
                        iconoValiCiudaadAñAlmacen.setGraphic(usarIcono(true));
                        iconoValiTelAñAlmacen.setGraphic(usarIcono(true));
                        iconoValiCapTotalAñAlmacen.setGraphic(usarIcono(true));
                        iconoValiIdTiendaAñAlmacen.setGraphic(usarIcono(true));
                        iconoValiHorarioAñAlmacen.setGraphic(usarIcono(true));
                        

                    }
                }
                
            };
            
            decoradores.add(decorador);
            //}
            Platform.runLater(() -> {
                for (int i = 0; i < validadores.size(); i++) {
                    validadores.get(i).setValidationDecorator(decoradores.get(i));
                    validadores.get(i).initInitialDecoration();
                    System.out.println("validadores "+validadores.get(i));
                    System.out.println("decoradores validadores "+decoradores.get(i));
                }
            });
        */
                
        //decoradores.addAll(Arrays.asList(decoradorNombre, decoradorDir));
        decorador = new GraphicValidationDecoration() {
            @Override
            public void applyValidationDecoration(ValidationMessage message) {
                super.applyValidationDecoration(message);

                Control control = message.getTarget();

                ImageView icono = null;
                if (message.getSeverity() == Severity.ERROR) {
                    icono = usarIcono(false);
                } else if (message.getSeverity() == Severity.INFO) {
                    icono = usarIcono(true);
                }
            }
        };
       
       actualizar();
       
        try {
            conexion = ConexionBD.getConexion();  
            if (conexion != null) {
                st = conexion.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de conexión");
            alert.setContentText("No se pudo establecer la conexión con la base de datos");
            alert.showAndWait();
        }
        
        if (conexion != null) {
            try {
                ObservableList<Productos> listaP = darListaProductos();
                ObservableList<Tiendas> listaT = darListaTiendas();
                ObservableList<Almacenes> listaA = darListaAlmacenes();
                
                
                //mostrar solo los productos con el mismo id_Producto
                Set<String> mapeoListaP = new HashSet<>();
                ObservableList<Productos> nuevaListaP = FXCollections.observableArrayList();
                for(Productos producto : listaP){
                    
                    if(!mapeoListaP.contains(producto.getId_producto())){
                        mapeoListaP.add(producto.getId_producto());
                        nuevaListaP.add(producto);
                    }
                }
                
                
                columnIdListaProductos.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
                columnNombreListaProductos.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                columnImgListaProductos.setCellValueFactory(new PropertyValueFactory<>("imagen"));
                columnTipoListaProductos.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                columnSubTipoListaProductos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().recogerSubTipo()));
                columnTallaListaProductos.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().recogerTallas()));
                columnPrecioListaProductos.setCellValueFactory(new PropertyValueFactory<>("precio"));
                
                tablaListaProductos.setItems(listaP);
                
                columnIdListaTiendas.setCellValueFactory(new PropertyValueFactory<>("id_tienda"));
                columnNombreListaTiendas.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                columnTipoListaTiendas.setCellValueFactory(new PropertyValueFactory<>("tipo"));
                columnDirListaTiendas.setCellValueFactory(new PropertyValueFactory<>("direccion"));
                columnCiudadListaTiendas.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
                columnPaisListaTiendas.setCellValueFactory(new PropertyValueFactory<>("pais"));
                columnTelListaTiendas.setCellValueFactory(new PropertyValueFactory<>("telefono"));
                //al horario se le pasa horarioString que esta dentro de metodo recogerHorario
                columnHorarioListaTiendas.setCellValueFactory(new PropertyValueFactory<>("horarioString"));
                
                tablaListaTiendas.setItems(listaT); 
                
                columnIdListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("id_almacen"));
                columnNombreListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                columnDirListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("direccion"));
                columnCiudadListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
                columnPaisListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("pais"));
                columnTelListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("telefono"));
                columnHorarioListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("horarioString"));
                columnCapOcupadaListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("capacidad_ocupada"));
                columnCapTotalListaAlmacenes.setCellValueFactory(new PropertyValueFactory<>("capacidad_total"));
                
                tablaListaAlmacenes.setItems(listaA);
                
            } catch (ExceptionInInitializerError e) {
                e.printStackTrace();
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se han podido recoger los datos");
                alert.showAndWait();
            }
        }   
    }
    
   //llamada a las listas:
    private ObservableList<Productos> darListaProductos(){
        ObservableList<Productos> listaProductos = FXCollections.observableArrayList();
        
        if (conexion != null) {
            String query = "SELECT * FROM productos";
            try {
                rs = st.executeQuery(query);
                while (rs.next()) {
                    //convertir el enum a string
                    Productos.TipoProducto tipo = Productos.TipoProducto.valueOf(rs.getString("tipo"));
                    Productos.SubTipoRopaProducto subtipo_ropa = null;
                    Productos.SubTipoAccProducto subtipo_accesorios = null;
                    
                    if (tipo == Productos.TipoProducto.Ropa && rs.getString("subtipo_ropa") != null) {
                        String subTipoRopaString = rs.getString("subtipo_ropa").trim().replace(" ", "_");
                        subtipo_ropa = Productos.SubTipoRopaProducto.valueOf(subTipoRopaString);
                    }
                    
                    if (tipo == Productos.TipoProducto.Accesorios && rs.getString("subtipo_accesorios") != null) {
                        String subtipoAccString = rs.getString("subtipo_accesorios").trim().replace(" ", "_");
                        subtipo_accesorios = Productos.SubTipoAccProducto.valueOf(subtipoAccString);
                    }
                    
                    Set<Productos.TallaProducto> tallas = new HashSet<>();
                    String tallaString = rs.getString("talla");
                    if (tallaString != null && !tallaString.isEmpty()) {
                    //las tallas estan separadas por, 
                    String[] tallaArray = tallaString.split(",");
                    for (int i = 0; i < tallaArray.length; i++){
                        String t = tallaArray[i];
                        
                        try {
                            tallas.add(Productos.TallaProducto.fromString(t.trim()));
                        } catch (IllegalArgumentException e) {
                            
                           
                        }
                    }
                    
                    Productos producto = new Productos(
                        rs.getString("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("imagen"),
                        tipo,
                        subtipo_ropa,
                        subtipo_accesorios,
                        tallas,
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("id_tienda"),
                        rs.getString("id_almacen")
                    );

                    //System.out.println("id producto -- "+ producto.getId_producto());
                    listaProductos.add(producto);

                }}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return listaProductos;
    }
    
    private ObservableList<Tiendas> darListaTiendas(){
        ObservableList<Tiendas> listaTiendas = FXCollections.observableArrayList();
        
        if (conexion != null) {
            String query = "SELECT * FROM tiendas";
            try {
                rs = st.executeQuery(query);
                while (rs.next()) {
                    //convertir el enum a string
                    Tiendas.TipoTienda tipo = Tiendas.TipoTienda.valueOf(rs.getString("tipo"));

                    //convertir el json a map
                    String horarioJson = rs.getString("horario");
                    Map<String, String> horarioMap = null;
                    if (horarioJson != null && !horarioJson.isEmpty()) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            horarioMap = objectMapper.readValue(horarioJson, Map.class);
                        } catch (JsonProcessingException e) {
                            System.out.println("Error al procesar el JSON: " + e.getMessage());
                            continue;  
                        }
                    }

                    
                    Tiendas tienda = new Tiendas(
                        rs.getString("id_tienda"),
                        rs.getString("nombre"),
                        tipo,
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("pais"),
                        rs.getInt("telefono"),
                        horarioMap  
                    );
   
                    listaTiendas.add(tienda);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return listaTiendas;
    }
    
    private ObservableList<Almacenes> darListaAlmacenes(){
        ObservableList<Almacenes> listaAlmacenes = FXCollections.observableArrayList();
        
        if (conexion != null) {
            String query = "SELECT * FROM almacenes";
            try {
                rs = st.executeQuery(query);
                while (rs.next()) {
                    
                    //convertir el json a map
                    String horarioJson = rs.getString("horario");
                    Map<String, String> horarioMap = null;
                    if (horarioJson != null && !horarioJson.isEmpty()) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            horarioMap = objectMapper.readValue(horarioJson, Map.class);
                        } catch (JsonProcessingException e) {
                            System.out.println("Error al procesar el JSON: " + e.getMessage());
                            continue;  
                        }
                    }
    

                    Almacenes almacen = new Almacenes(
                        rs.getString("id_almacen"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("ciudad"),
                        rs.getString("pais"),
                        rs.getInt("telefono"),
                        horarioMap,
                        rs.getInt("capacidad_ocupada"),
                        rs.getInt("capacidad_total"),
                        rs.getString("id_tienda")
                    );

                    
                    listaAlmacenes.add(almacen);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return listaAlmacenes;
    }
    
    
    
    //CRUD en bd: añadir, editar/update y borrar
    //comporbar el ultimo id de las tabalas
    private String obtenerUltimoId(String tabla, String columnaId) {
        String ultimoId = null;

        if (conexion != null) {
            
            String query = "SELECT " + columnaId + " FROM " + tabla + " ORDER BY " + columnaId + " DESC LIMIT 1";
            try {
                rs = st.executeQuery(query);
                if (rs.next()) {
                    ultimoId = rs.getString(columnaId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String prefijo = "";
        
        if (ultimoId != null && !ultimoId.isEmpty()) {
            
            prefijo = ultimoId.substring(0, 1);

            String numeroStr = ultimoId.substring(1);
            int numero = Integer.parseInt(numeroStr);

            numero++;
            return String.format("%s%03d", prefijo, numero);
        }

        switch (tabla.toLowerCase()) {
            case "almacenes":
                prefijo = "A";
                break;
            case "productos":
                prefijo = "P";
                break;
            case "tiendas":
                prefijo = "T";
                break;
            default:
                throw new IllegalArgumentException("No se ha generado id");
        }

        return String.format("%s001", prefijo);
    }
    
    private void añadirProducto(String nombre, String img, Enum tipo, Enum subtipo, Enum talla, double precio, int stock, String idTienda, String idAlmacen) {
        
        if (conexion != null) {
            //valores por defecto
            String nuevoIdProducto = obtenerUltimoId("productos", "id_producto");
            
            // si se llama igual a otro pero con diferentes ids, se le agrega el mismo id_producto
            if(verificarProductoExistente(nombre)){
                
                String query = "SELECT id_producto FROM productos WHERE nombre = ?";
                try {
                    PreparedStatement ps = conexion.prepareStatement(query);
                    
                    ps.setString(1, nombre);
            
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                       
                    String idProductoExistente = rs.getString("id_producto");
                    System.out.println("El producto ya existe con ID: " + idProductoExistente);

                    String nuevoQuery = "INSERT INTO productos "
                            + "(id_producto, nombre, imagen, tipo, subtipo_ropa, subtipo_accesorios, talla, precio, stock, id_tienda, id_almacen) "
                            + "VALUES ( ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    try {
                        PreparedStatement psNuevo = conexion.prepareStatement(nuevoQuery);
                        
                        psNuevo.setString(1, nuevoIdProducto);
                        psNuevo.setString(2, nombre);
                        psNuevo.setString(3, img);
                        psNuevo.setString(4, tipo.name());

                        if (tipo == Productos.TipoProducto.Ropa) {
                            if (subtipo != null) {
                                psNuevo.setString(5, ((Productos.SubTipoRopaProducto) subtipo).name());
                            } else {
                                psNuevo.setString(5, null);
                            }
                            psNuevo.setString(6, null);

                        } else if (tipo == Productos.TipoProducto.Accesorios) {
                            psNuevo.setString(5, null);

                            if (subtipo != null) {
                                psNuevo.setString(6, ((Productos.SubTipoAccProducto) subtipo).name());
                            } else {
                                psNuevo.setString(6, null);
                            }

                        } else {
                            psNuevo.setString(5, null);
                            psNuevo.setString(6, null);
                        }

                        psNuevo.setString(7, talla.name());
                        psNuevo.setDouble(8, precio);
                        psNuevo.setInt(9, stock);
                        psNuevo.setString(10, idTienda);
                        psNuevo.setString(11, idAlmacen);
                        
                        int rowsInserted = psNuevo.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("Producto añadido con el mismo nombre");
                            mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Producto añadido! :)");
                        } else {
                            System.err.println("No se ha añadido el producto a la nueva ubicacion");
                            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir el producto :(");
                        }
                    } catch(SQLException e){
                        System.err.println("Error al verificar la existencia del producto: " + e.getMessage());
                    }
                    
                }       
            } catch (SQLException e) {
                System.err.println("Error al verificar la existencia del producto: " + e.getMessage());
            }
                    
           
                
            // si se llama igual a otro y con mismos ids, no se puede crear
            }else if(verificarProductoExistenteIgualIds(nombre, idTienda, idAlmacen)){
                    System.err.println("--error añadir producto: DATOS REPETIDOS de PRODUCTO");
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ya hay un producto con el mismo nombre y asignado a la misma tienda y almacen");
                
                
            // si no se llama igual a otro prod, se le pone un id_producto nuevo
            } else {
                
                String query = "INSERT INTO productos "
                        + "(id_producto, nombre, imagen, tipo, subtipo_ropa, subtipo_accesorios, talla, precio, stock, id_tienda, id_almacen) "
                        + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                try {

                    PreparedStatement ps = conexion.prepareStatement(query);

                    ps.setString(1, nuevoIdProducto);
                    ps.setString(2, nombre);
                    ps.setString(3, img);
                    // .name() : recoge lo selec devolviendolo como un string para q lo coja
                    ps.setString(4, tipo.name());
        
                    
                    if (tipo == Productos.TipoProducto.Ropa) {
                        if (subtipo != null) {
                            ps.setString(5, ((Productos.SubTipoRopaProducto) subtipo).name());
                        } else {
                            ps.setString(5, null);
                        }
                        ps.setString(6, null);

                    } else if (tipo == Productos.TipoProducto.Accesorios) {
                        ps.setString(5, null);
                        
                        if (subtipo != null) {
                            ps.setString(6, ((Productos.SubTipoAccProducto) subtipo).name());
                        } else {
                            ps.setString(6, null);
                        }

                    } else {
                        ps.setString(5, null);
                        ps.setString(6, null);
                    }
                    
                    if (talla != null) {
                        ps.setString(7, talla.name());
                    } else {
                        ps.setString(7, null);
                    }
                    ps.setDouble(8, precio);
                    ps.setInt(9, stock);
                    ps.setString(10, idTienda);
                    ps.setString(11, idAlmacen);

                    int rowsInserted = ps.executeUpdate();

                   
                    if (rowsInserted > 0) {
                        System.out.println("Producto añadido!");
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Producto añadido! :)");
                    } else {
                        System.err.println("No se ha añadido el producto");
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir el producto :(");
                    }

                } catch (SQLException e) {
                    System.err.println("--error añadir producto: " + e.getMessage());
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir el producto, revisa los campos");
                }
            }
            
        
        } else {
            System.err.println("No se pudo conectar a la base de datos");
        }
    }
    private void editarProducto(String idProducto, String nombre, String imagen, Enum tipo, Enum subtipoRopa, Enum subtipoAccesorios, Enum talla, 
                            double precio, int stock, String idTienda, String idAlmacen) {
        if (conexion != null) {
      
            String query = "UPDATE productos " +
                       "SET nombre = ?, imagen = ?, tipo = ?, subtipo_ropa = ?, subtipo_accesorios = ?, talla = ?, precio = ?, stock = ? " +
                       "WHERE id_producto = ? AND id_tienda = ? AND id_almacen = ?";

            
            try {

                PreparedStatement ps = conexion.prepareStatement(query);

                ps.setString(1, nombre);
                ps.setString(2, imagen);
                // .name() recoge lo selec devolviendolo como un string para q lo coja
                ps.setString(3, tipo.name());
                ps.setString(4, (subtipoRopa != null) ? subtipoRopa.name() : null);
                ps.setString(5, (subtipoAccesorios != null) ? subtipoAccesorios.name() : null);
                ps.setString(6, (talla != null) ? talla.name() : null);
                ps.setDouble(7, precio);
                ps.setInt(8, stock);
                ps.setString(9, idProducto);
                ps.setString(10, idTienda);
                ps.setString(11, idAlmacen);

System.out.println("Query: " + ps.toString());
            System.out.println("ID Producto: " + idProducto);
            System.out.println("ID Tienda: " + idTienda);
            System.out.println("ID Almacen: " + idAlmacen);
                int rowsInserted = ps.executeUpdate();

                
                if (rowsInserted > 0) {
                    System.out.println("Producto editado!");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Producto editado! :)");
                } else {
                    System.err.println("No se ha editar el producto");
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido editar el producto :(");
                }
                
            } catch (SQLException e) {
                System.err.println("--error editar producto: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido editar el producto, revisa los campos");
            }
            
        } else {
            System.err.println("No se pudo conectar a la base de datos");
        }
    }
    private boolean borrarProducto(String idProducto, String idTienda, String idAlmacen) {
        if (conexion != null) {
            String query = "DELETE FROM productos WHERE id_producto = ? AND id_tienda = ? AND id_almacen = ?";

            try {

                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, idProducto);
                ps.setString(2, idTienda);
                ps.setString(3, idAlmacen);

                int filaBorrada = ps.executeUpdate();

                if (filaBorrada > 0) {
                    System.out.println("Producto borrado");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Has eliminado el producto de esa tienda y almacen!");
                }
                
            } catch (SQLException e) {
                System.err.println("--error borrar producto: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido borrar el prodcuto asociado a esa tienda y almacen");
            }
        }
        return false;
    }

    private void añadirTienda(String nombre, String tipo, String direccion, String ciudad, String pais, int telefono, String horarioJson) {
        if (conexion != null) {
            //valores por defecto
            String nuevoIdTienda = obtenerUltimoId("tiendas", "id_tienda");
            
            String query = "INSERT INTO tiendas (id_tienda, nombre, tipo, direccion, ciudad, pais, telefono, horario) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            try {
                /*
                //convertir el mapa de horario a JSON
                String horarioJson = null;
                if (horario != null && !horario.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    horarioJson = objectMapper.writeValueAsString(horario);
                }*/

                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, nuevoIdTienda);
                ps.setString(2, nombre);
                ps.setString(3, tipo);
                ps.setString(4, direccion);
                ps.setString(5, ciudad);
                ps.setString(6, pais);
                ps.setInt(7, telefono);
                ps.setString(8, horarioJson);
             

                int rowsInserted = ps.executeUpdate();

                
                if (rowsInserted > 0) {
                    System.out.println("Tienda añadida!");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Tienda añadida! :)");
                } else {
                    System.err.println("No se ha añadida la tienda");
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir la tienda :(");
                }
                
            } catch (SQLException  e) {
                System.err.println("--error añadir tienda: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir la tienda, revisa los campos");
            }
            
        } else {
            System.err.println("No se pudo conectar a la base de datos");
        }
    }
    private void editarTienda(String idTienda, String nombre, String tipo, String direccion, String ciudad, String pais, int telefono, String horarioJson) {
        if (conexion != null) {
      
            String query = "UPDATE tiendas SET nombre = ?, direccion = ?, ciudad = ?, pais = ?, telefono = ?, horario = ? WHERE id_tienda = ?";
            
            try {
                /*
                //convertir el mapa de horario a JSON
                String horarioJson = null;
                if (horario != null && !horario.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    horarioJson = objectMapper.writeValueAsString(horario);
                }*/

                PreparedStatement ps = conexion.prepareStatement(query);

                ps.setString(1, nombre);
                ps.setString(2, direccion);
                ps.setString(3, ciudad);
                ps.setString(4, pais);
                ps.setInt(5, telefono);
                ps.setString(6, horarioJson);
                ps.setString(7, idTienda);

                int rowsInserted = ps.executeUpdate();

                
                if (rowsInserted > 0) {
                    System.out.println("Tienda editada!");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Tienda editada! :)");
                } else {
                    System.err.println("No se ha añadido la tienda");
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido editar la tienda :(");
                }
                
            } catch (SQLException e) {
                System.err.println("--error añadir tienda: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido editar la tienda, revisa los campos");
            }
            
        } else {
            System.err.println("No se pudo conectar a la base de datos");
        }
    }
    private boolean borrarTienda(String idTienda) {
        if (conexion != null) {
            String query = "DELETE FROM tiendas WHERE id_tienda = ?";

            try {

                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, idTienda);

                int filaBorrada = ps.executeUpdate();

                if (filaBorrada > 0) {
                    System.out.println("Tienda borrada");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Has eliminado la tienda!");
                }
                
            } catch (SQLException e) {
                System.err.println("--error borrar tienda: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido borrar la tienda");
            }
        }
        return false;
    }


    private void añadirAlmacen(String nombre, String direccion, String ciudad, String pais, int telefono, String horarioJson, int capacidadTotal, String idTienda) {
        if (conexion != null) {
            //valores por defecto
            String nuevoIdAlmacen = obtenerUltimoId("almacenes", "id_almacen");
            int capacidadOcupada = 0;
            
            String query = "INSERT INTO almacenes (id_almacen, nombre, direccion, ciudad, pais, telefono, horario, capacidad_ocupada, capacidad_total, id_tienda) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            try {
                /*
                //convertir el mapa de horario a JSON
                String horarioJson = null;
                if (horario != null && !horario.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    horarioJson = objectMapper.writeValueAsString(horario);
                }*/

                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, nuevoIdAlmacen);
                ps.setString(2, nombre);
                ps.setString(3, direccion);
                ps.setString(4, ciudad);
                ps.setString(5, pais);
                ps.setInt(6, telefono);
                ps.setString(7, horarioJson);
                ps.setInt(8, capacidadOcupada);
                ps.setInt(9, capacidadTotal);
                ps.setString(10, idTienda);

                int rowsInserted = ps.executeUpdate();

               
                if (rowsInserted > 0) {
                    System.out.println("Almacen añadido!");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Almacen añadido! :)");
                } else {
                    System.err.println("No se ha añadido el almacen");
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir el almacen :(");
                }
                
            } catch (SQLException e) {
                System.err.println("--error añadir almacen: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido añadir el almacen, revisa los campos");
            }
            
        } else {
            System.err.println("No se pudo conectar a la base de datos");
        }
    }
    private void editarAlmacen(String idAlmacen, String nombre, String direccion, String ciudad, String pais, int telefono, 
                           String horarioJson, int capacidadTotal, String idTienda) {
        if (conexion != null) {
      
            String query = "UPDATE almacenes SET nombre = ?, direccion = ?, ciudad = ?, pais = ?, telefono = ?, horario = ?, capacidad_total = ?, id_tienda = ? WHERE id_almacen = ? ";
            
            try {
                /*
                //convertir el mapa de horario a JSON
                String horarioJson = null;
                if (horario != null && !horario.isEmpty()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    horarioJson = objectMapper.writeValueAsString(horario);
                }*/

                PreparedStatement ps = conexion.prepareStatement(query);

                ps.setString(1, nombre);
                ps.setString(2, direccion);
                ps.setString(3, ciudad);
                ps.setString(4, pais);
                ps.setInt(5, telefono);
                ps.setString(6, horarioJson);
                ps.setInt(7, capacidadTotal);
                ps.setString(8, idTienda);
                ps.setString(9, idAlmacen);

                int rowsInserted = ps.executeUpdate();

              
                if (rowsInserted > 0) {
                    System.out.println("Almacen editado!");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Almacen editado! :)");
                } else {
                    System.err.println("No se ha añadido el almacen");
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido editar el almacen :(");
                }
                
            } catch (SQLException e) {
                System.err.println("--error añadir almacen: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido editar el almacen, revisa los campos");
            }
            
        } else {
            System.err.println("No se pudo conectar a la base de datos");
        }
    }
    private boolean borrarAlmacen(String idAlmacen) {
        if (conexion != null) {
            String query = "DELETE FROM almacenes WHERE id_almacen = ?";

            try {

                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, idAlmacen);

                int filaBorrada = ps.executeUpdate();

                if (filaBorrada > 0) {
                    System.out.println("Almacén eliminado correctamente.");
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Operacion exitosa", "Has eliminado el almacen!");
                }
                
            } catch (SQLException e) {
                System.err.println("--error borrar almacén: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se ha podido eliminar el almacen, porque tienes productos y tiendas enlazadas");
            }
        }
        return false;
    }


    //Alerta para informar al usuario
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(mensaje);
        alerta.showAndWait();
    }
    
    
    
    //Validaciones:
    public ImageView usarIcono(boolean valido){
        ImageView iconoOk = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("ok_icon.png")));
        ImageView iconoError = new ImageView(new Image(getClass().getClassLoader().getResourceAsStream("error_icon.png")));
        iconoOk.setFitHeight(16);
        iconoOk.setFitWidth(16);
        iconoError.setFitHeight(16);
        iconoError.setFitWidth(16);
        
        if (valido) {
            return iconoOk;
        } else {
            return iconoError;
        }
    }

    List<ValidationSupport> validadores;
    public void comprobarValidacionesAñProducto(String accion){
        ValidationSupport nombreAl = new ValidationSupport();
        Validator<String> nombreVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "El nombre no puede estar vacio");
                
            } else{
                String regex = "^[a-zA-Z0-9]*$";
                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El nombre NO puede contener numeros");
                } else {
                    return ValidationResult.fromInfo(c, "Nombre valido");
                }    
            }
        };
        nombreAl.registerValidator(textNombreAñProducto, false, nombreVal);
        
        
        ValidationSupport tipoAl = new ValidationSupport();
        Validator<Productos.TipoProducto> tipoVal = (Control c, Productos.TipoProducto tipo) -> {
            if (tipo == null) {
                return ValidationResult.fromError(c, "Hay que elegir un tipo");
            } else {
                return ValidationResult.fromInfo(c, "Tipo valido");
            }
        };
        tipoAl.registerValidator(choiceBoxTipoAñProducto, false, tipoVal);
        
        
        ValidationSupport subTipoAl = new ValidationSupport();
        Validator<Object> subTipoVal = (Control c, Object subtipo) -> {
            if (choiceBoxTipoAñProducto.getValue() == Productos.TipoProducto.Zapatillas) {
                return ValidationResult.fromInfo(c, "Tipo Zapatillas no requiere subtipo");
            }
            if (subtipo == null) {
                return ValidationResult.fromError(c, "Hay que elegir un subtipo");
            } else if (subtipo instanceof Productos.SubTipoRopaProducto || subtipo instanceof Productos.SubTipoAccProducto) {
                return ValidationResult.fromInfo(c, "Subtipo válido");
            } else {
                return ValidationResult.fromError(c, "Subtipo no válido");
            }
        };
        subTipoAl.registerValidator(choiceBoxSubTipoAñProducto, false, subTipoVal);
        //tiene que forzar si es Zapatillas
        choiceBoxTipoAñProducto.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            subTipoAl.revalidate();
            
        });
        
        
        ValidationSupport tallaAl = new ValidationSupport();
        Validator<Productos.TallaProducto> tallaVal = (Control c, Productos.TallaProducto talla) -> {
            if (talla == null) {
                return ValidationResult.fromError(c, "Hay que elegir una talla");
            } else {
                return ValidationResult.fromInfo(c, "Talla valida");
            }
        };
        tallaAl.registerValidator(comboBoxTallasAñProducto, false, tallaVal);
        
        
        ValidationSupport precioAl = new ValidationSupport();
        Validator<String> precioVal = (Control c, String texto) -> {
            if (texto.isEmpty()) {
                return ValidationResult.fromError(c, "El precio no puede estar vacio");
            } else {
                //puede ser de 1 a 4 num con '.' o ','
                String regex = "^[\\d.,]{1,4}$";

                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "Solo se permiten de 1 a 4 números, incluyendo '.' o ','");
                } else {
                    return ValidationResult.fromInfo(c, "Precio valido");
                }
            }
        };
        precioAl.registerValidator(textPrecioAñProducto, false, precioVal);
        
        
        ValidationSupport stockAl = new ValidationSupport();
        Validator<String> stockVal = (Control c, String texto) -> {
            if (texto.isEmpty()) {
                return ValidationResult.fromError(c, "El stock no puede estar vacio");
            } else {
                //solo 3 num
                String regex = "^[\\d.,]{1,4}$";

                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "Solo se permiten hasta 3 num");
                } else {
                    return ValidationResult.fromInfo(c, "Stock valido");
                }
            }
        };
        stockAl.registerValidator(textStockAñProducto, false, stockVal);
        
        
        List<ValidationSupport> validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(nombreAl, tipoAl, subTipoAl, tallaAl, stockAl, precioAl));

        if (!"ed".equals(accion)){
            ValidationSupport idTiendaAl = new ValidationSupport();
            Validator<String> idTiendaVal = (Control c, String comboBox) -> {
                if (comboBox == null) {
                    return ValidationResult.fromError(c, "Hay que elegir una tienda");
                } else {
                    return ValidationResult.fromInfo(c, "Tienda válida");
                }
            };
            idTiendaAl.registerValidator(comboBoxElegirTiendaAñProd, false, idTiendaVal);

            ValidationSupport idAlmacenAl = new ValidationSupport();
            Validator<String> idAlmacenVal = (Control c, String comboBox) -> {
                if (comboBox == null) {
                    return ValidationResult.fromError(c, "Hay que elegir un almacén");
                } else {
                    return ValidationResult.fromInfo(c, "Almacén válido");
                }
            };
            idAlmacenAl.registerValidator(comboBoxElegirAlmacenAñProd, false, idAlmacenVal);

            validadores.addAll(Arrays.asList(idTiendaAl, idAlmacenAl));
        }

        this.validadores = validadores;

        Platform.runLater(() -> {
            for (ValidationSupport validador : validadores) {
                validador.setValidationDecorator(decorador);
                validador.initInitialDecoration();  
            } 
        });
        
    }
    
    private boolean comprobarDatosProducto(){
        boolean todoOK = true;

        for (ValidationSupport validationSupport : validadores) {
            ValidationResult resultados = validationSupport.getValidationResult();
            if (resultados != null) {
                System.out.println("Validador Errores: " + resultados.getErrors());
                System.out.println("Validador Infos: " + resultados.getInfos());
                if (!resultados.getErrors().isEmpty()) {
                    todoOK = false;
                }
            }
        }

        return todoOK;
    }
    
    public void comprobarValidacionesAñTienda(){
        ValidationSupport nombreAl = new ValidationSupport();
        Validator<String> nombreVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "El nombre no puede estar vacio");
                
            } else{
                String regex = ".*\\d.*";
                if (texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El nombre NO puede contener numeros");
                } else {
                    return ValidationResult.fromInfo(c, "Nombre valido!");
                }    
            }
        };
        nombreAl.registerValidator(textNombreAñTienda, false, nombreVal);
   
        ValidationSupport tipoAl = new ValidationSupport();
        Validator<Tiendas.TipoTienda> tipoVal = (Control c, Tiendas.TipoTienda tipo) -> {
            if (tipo == null) {
                return ValidationResult.fromError(c, "Hay que elegir una tipo");
            } else {
                return ValidationResult.fromInfo(c, "Tipo valida");
            }
        };
        tipoAl.registerValidator(comboBoxTipoAñTienda, false, tipoVal);
        
        
        ValidationSupport dirAl = new ValidationSupport();
        Validator<String> dirVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "Direccion vacia");
            }else{
                //no permite \ , pero si /
                String regex = "^[A-Za-z0-9\\sºª,\\-\"/]*$";
                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "Direccion con caracteres no permitidos");
                }
                return ValidationResult.fromInfo(c, "Direccion valida!");
            }

        };
        dirAl.registerValidator(textDirAñTienda, false, dirVal);
        
  
        ValidationSupport paisAl = new ValidationSupport();
        Validator<String> paisVal = (Control c, String tipo) -> {
            if (tipo == null) {
                return ValidationResult.fromError(c, "Hay que elegir un pais");
            } else {
                return ValidationResult.fromInfo(c, "Pais valido");
            }
        };
        paisAl.registerValidator(comboBoxPaisAñTienda, false, paisVal);
        
        ValidationSupport ciudadAl = new ValidationSupport();
        Validator<String> ciudadVal = Validator.createPredicateValidator(
                texto -> !("".equals(texto)),
                "Hay que elegir una ciudad!",
                Severity.ERROR
        );
        ciudadAl.registerValidator(comboBoxCiudadAñTienda, false, ciudadVal);
        
        
        ValidationSupport telAl = new ValidationSupport();
        Validator<String> telVal = (Control c, String texto) -> {
            if (texto.isEmpty()) {
                return ValidationResult.fromError(c, "Telefono vacio");

            } else {
                //contien 9 num si o si
                String regex = "^\\d{9}$";

                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El telefono tiene que tener 9 numeros");
                } else {
                    return ValidationResult.fromInfo(c, "Telefono valido");
                }
            }
        };
        telAl.registerValidator(textTelAñTienda, false, telVal);
        

        ValidationSupport horarioAl = new ValidationSupport();
        Validator<String> horarioVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "El horario no puede estar vacío");
            } else {
                String regex = "^[a-zA-Z0-9:]+$";
                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El horario debe estar en el formato horas:minutos");
                } else {
                    return ValidationResult.fromInfo(c, "Horario válido");
                }
            }
        };
        horarioAl.registerValidator(textAperLHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieLHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textAperMHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieMHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textAperXHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieXHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textAperJHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieJHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textAperVHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieVHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textAperSHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieSHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textAperDHorarioAñTienda, false, horarioVal);
        horarioAl.registerValidator(textCieDHorarioAñTienda, false, horarioVal);

        
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(nombreAl, tipoAl, dirAl, paisAl, ciudadAl, telAl, horarioAl));
        
        
        Platform.runLater(() -> {
            for (ValidationSupport validador : validadores) {
                validador.setValidationDecorator(decorador);
                validador.initInitialDecoration();  
            }
        });
        
    }
    
    private boolean comprobarDatosTienda(){
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            ValidationResult resultados = validationSupport.getValidationResult();
            System.out.println("Validador Errores: " + resultados.getErrors());
            System.out.println("Validador Infos: " + resultados.getInfos());
            if (!resultados.getErrors().isEmpty()) {
                todoOK = false;
            }
        }
        return todoOK;
    }
    
    public void comprobarValidacionesAñAlmacen(){
    
        ValidationSupport nombreAl = new ValidationSupport();
        Validator<String> nombreVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "El nombre no puede estar vacio");
                
            } else{
                String regex = ".*\\d.*";
                if (texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El nombre NO puede contener numeros");
                } else {
                    return ValidationResult.fromInfo(c, "Nombre valido!");
                }    
            }
        };
        nombreAl.registerValidator(textNombreAñAlmacen, false, nombreVal);

        
        ValidationSupport dirAl = new ValidationSupport();
        Validator<String> dirVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "Direccion vacia");
            }else{
                //no permite \ , pero si /
                String regex = "^[A-Za-z0-9\\sºª,\\-\"/]*$";
                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "Direccion con caracteres no permitidos");
                }
                return ValidationResult.fromInfo(c, "Direccion valida!");
            }

        };
        dirAl.registerValidator(textDirAñAlmacen, false, dirVal);
        
        
        ValidationSupport paisAl = new ValidationSupport();
        Validator<String> paisVal = (Control c, String tipo) -> {
            if (tipo == null || tipo.isEmpty()) {
                return ValidationResult.fromError(c, "Hay que elegir un pais");
            } else {
                return ValidationResult.fromInfo(c, "Pais valido");
            }
        };
        paisAl.registerValidator(comboBoxPaisAñAlmacen, false, paisVal);
        
        
        ValidationSupport ciudadAl = new ValidationSupport();
        Validator<Object> ciudadVal = (Control c, Object ciudad) -> {
            if (!comboBoxPaisAñAlmacen.getValue().equals("España")) {
                return ValidationResult.fromInfo(c, "Este pais no requiere ciudad");
            }
            
            if (ciudad == null || ciudad.toString().isEmpty()) {
                return ValidationResult.fromError(c, "Hay que elegir una ciudad");
            } else {
                return ValidationResult.fromInfo(c, "Ciudad válida");
            }
        };
        ciudadAl.registerValidator(comboBoxCiudadAñAlmacen, false, ciudadVal);
        //tiene que forzar si es España
        comboBoxPaisAñAlmacen.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
            ciudadAl.revalidate();
            
        });
        
        
        ValidationSupport telAl = new ValidationSupport();
        Validator<String> telVal = (Control c, String texto) -> {
            if (texto.isEmpty()) {
                return ValidationResult.fromError(c, "Telefono vacio");

            } else {
                //contien 9 num si o si
                String regex = "^\\d{9}$";

                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El telefono tiene que tener 9 numeros");
                } else {
                    return ValidationResult.fromInfo(c, "Telefono valido");
                }
            }
        };
        telAl.registerValidator(textTelAñAlmacen, false, telVal);
        
        
        ValidationSupport capTotalAl = new ValidationSupport();
        Validator<String> capTotalVal = (Control c, String texto) -> {
             if (texto.isEmpty() || texto == "0") {
                return ValidationResult.fromError(c, "La capacidad no puede estar vacia o ser 0");

            } else {
               //ni caract especial ni nada, solo num
                String regex = "^\\d+$";

                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "La capacidad debe ser un numero valido");
                } else {
                    maxValueAñAlmacen = Integer.parseInt(textCapTotalAñAlmacen.getText().toString());
                    System.out.println("--maxValueAñAlmacen "+textCapTotalAñAlmacen.getText());
                    barraProgreso.setMaxValue(maxValueAñAlmacen);
                    
                    return ValidationResult.fromInfo(c, "Capacidad total valida!");
                }
            }
        };
        capTotalAl.registerValidator(textCapTotalAñAlmacen, false, capTotalVal);
        
        ValidationSupport horarioAl = new ValidationSupport();
        Validator<String> horarioVal = (Control c, String texto) -> {
            if (texto == null || texto.isEmpty()) {
                return ValidationResult.fromError(c, "El horario no puede estar vacío");
            } else {
                String regex = "^[a-zA-Z0-9:]+$";
                if (!texto.matches(regex)) {
                    return ValidationResult.fromError(c, "El horario debe estar en el formato horas:minutos");
                } else {
                    return ValidationResult.fromInfo(c, "Horario válido");
                }
            }
        };
        horarioAl.registerValidator(textAperLHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieLHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textAperMHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieMHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textAperXHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieXHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textAperJHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieJHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textAperVHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieVHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textAperSHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieSHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textAperDHorarioAñAlmacen, false, horarioVal);
        horarioAl.registerValidator(textCieDHorarioAñAlmacen, false, horarioVal);
        
        
        
        ValidationSupport idTiendaAl = new ValidationSupport();
        Validator<String> idTiendaVal = (Control c, String tipo) -> {
            if (tipo == null || tipo.isEmpty()) {
                return ValidationResult.fromError(c, "Hay que elegir una ciudad");
            } else {
                return ValidationResult.fromInfo(c, "La tienda es valida");
            }
        };
        idTiendaAl.registerValidator(textIdTiendaAñAlmacen, false, idTiendaVal);
        
       
        validadores = new ArrayList<>();
        validadores.addAll(Arrays.asList(nombreAl, dirAl, paisAl, ciudadAl, telAl, capTotalAl, idTiendaAl, horarioAl
        ));
        
        
        Platform.runLater(() -> {
            for (ValidationSupport validador : validadores) {
                validador.setValidationDecorator(decorador);
                //System.out.println("validador "+validationSupport);
                validador.initInitialDecoration();
                //validationSupport.setValidationDecorator(decoradores);
                //System.out.println("validador decorador   "+decoradores);
                
            }
            //nombreAl.setValidationDecorator(decoradorNombre);
          
        });
        

    }
    
    
    
    private boolean comprobarDatosAlmacen(){
        for (ValidationSupport validationSupport : validadores) {
            ValidationResult resultados = validationSupport.getValidationResult();
            System.out.println("Validador Errores: " + resultados.getErrors());
            System.out.println("Validador Infos: " + resultados.getInfos());
        }
        
        boolean todoOK = true;
        for (ValidationSupport validationSupport : validadores) {
            todoOK = (todoOK && validationSupport.getValidationResult().getErrors().isEmpty());
            return true;
        }
        return false;   
    }
    
    private boolean verificarProductoExistente(String nombreProducto) {
        if (conexion != null) {
            String query = "SELECT COUNT(*) AS count FROM productos WHERE nombre = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, nombreProducto);
                
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
                System.out.println("La tienda existe con el mismo nombre");

            } catch (SQLException e) {
                System.err.println("No existe la tienda que has indicado" + e.getMessage());
            }
        }
        return false;
    }
    
    private boolean verificarProductoExistenteIgualIds(String nombre, String idTienda, String idAlmacen) {
        if (conexion != null) {
            String query = "SELECT COUNT(*) AS count FROM productos WHERE nombre = ? AND id_tienda = ? AND id_almacen = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, nombre);
                ps.setString(2, idTienda);
                ps.setString(3, idAlmacen);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
                
                System.out.println("Ya existe la tienda con el mismo nombre y los mismos ids");

            } catch (SQLException e) {
                System.err.println("No existe la tienda que has indicado" + e.getMessage());
            }
        }
        return false;
    }
    
    private boolean verificarTiendaExistente(String tienda) {
        if (conexion != null) {
            String query = "SELECT COUNT(*) AS count FROM tiendas WHERE id_tienda = ? OR nombre = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, tienda);
                ps.setString(2, tienda);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            } catch (SQLException e) {
                System.err.println("No existe la tienda que has indicado" + e.getMessage());
            }
        }
        return false;
    }
    
    private boolean verificarAlmacenExistente(String almacen) {
        if (conexion != null) {
            String query = "SELECT COUNT(*) AS count FROM almacenes WHERE id_almacen = ? OR nombre = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, almacen);
                ps.setString(2, almacen);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            } catch (SQLException e) {
                System.err.println("No existe el almacen que has indicado" + e.getMessage());
            }
        }
        return false;
    }
    
    private Productos obtenerProductoPorId(String idProducto) {
        ObservableList<Productos> listaProductos = darListaProductos();
        for (Productos producto : listaProductos) {
            if (producto.getId_producto().equals(idProducto)) {
                return producto;
            }
        }
        return null;
    }
    
    private ObservableList<String> obtenerIdsTiendas() {
        ObservableList<String> idTiendas = FXCollections.observableArrayList();
        ObservableList<Tiendas> listaTiendas = darListaTiendas();
        
        for (Tiendas tienda : listaTiendas) {
            idTiendas.add(tienda.getId_tienda());
        }

        return idTiendas;
    }
    
    private String obtenerIdNombreTiendas(String idTienda) {
        ObservableList<Tiendas> listaTiendas = darListaTiendas();
        
        for (Tiendas tienda : listaTiendas) {
            if (tienda.getId_tienda().equals(idTienda)) {
                return tienda.getId_tienda() + " - " + tienda.getNombre();
            }
        }
        return "No se encuentra tienda con ID: " + idTienda;
    }
    private ObservableList<String> obtenerIdsAlmacenes() {
        ObservableList<String> idAlmacenes = FXCollections.observableArrayList();
        ObservableList<Almacenes> listaAlmacenes = darListaAlmacenes();

        for (Almacenes almacen : listaAlmacenes) {
        
            idAlmacenes.add(almacen.getId_almacen());
        }

        return idAlmacenes;
    }
    private String obtenerIdNombreAlmacenes(String idAlmacen) {
        ObservableList<Almacenes> listaAlmacenes = darListaAlmacenes();
        
        for (Almacenes almacen : listaAlmacenes) {
            if (almacen.getId_almacen().equals(idAlmacen)) {
                return almacen.getId_almacen()+ " - " + almacen.getNombre();
            }
        }
        return "No se encuentra almacén con ID: " + idAlmacen;
    }
    
    
}
