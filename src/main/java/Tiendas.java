

//package modelos;

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author carmen_gordo
 */
public class Tiendas {

    private String id_tienda;
    private String nombre;
    private TipoTienda tipo;
    private String direccion;
    private String ciudad;
    private String pais;
    private int telefono;
    private Map<String, String> horario;
    private StringProperty horarioString;
    
    private ObservableList<Productos> productos;
 

    public Tiendas() {
        this.horarioString = new SimpleStringProperty(recogerHorario());
    }

    public Tiendas( String id_tienda, String nombre, TipoTienda tipo, String direccion, String ciudad, String pais, int telefono, Map<String, String> horario) {
       
        this.id_tienda = id_tienda;
        this.nombre = nombre;
        this.tipo = tipo;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.telefono = telefono;
        this.horario = horario;
        
        this.horarioString = new SimpleStringProperty(recogerHorario());
    }

   

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoTienda getTipo() {
        return tipo;
    }

    public void setTipo(TipoTienda tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Map<String, String> getHorario() {
        return horario;
    }

    public void setHorario(Map<String, String> horario) {
        this.horario = horario;
        this.horarioString.set(recogerHorario());
    }

    
    //enum de tiendas:
    public enum TipoTienda {
        normal,
        outlet,
        premium
    }
    
    
    public String recogerHorario() {
        if (horario == null || horario.isEmpty()) {
            return "No disponible";
        }

        StringBuilder horarioStringBuilder = new StringBuilder();
        String[] diasSemana = {"lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo"};

        for (String diaSemana : diasSemana) {
            Object horasObj = horario.get(diaSemana);
            String horas = horasObj instanceof String ? (String) horasObj : null;

            horarioStringBuilder.append(ponerLetraMayus(diaSemana)).append(": ");

            if (horas != null && !horas.isEmpty()) {
                horarioStringBuilder.append(horas);
            } else {
                horarioStringBuilder.append("Cerrado");
            }
            horarioStringBuilder.append(", ");
        }

        if (horarioStringBuilder.length() > 2) {
            horarioStringBuilder.setLength(horarioStringBuilder.length() - 2);
        }

        return horarioStringBuilder.toString();
    }

    public String getHorarioString() {
        return horarioString.get();
    }

    public void setHorarioString(String horario) {
        this.horarioString.set(horario);
    }

    public StringProperty horarioStringProperty() {
        return horarioString;
    }
   
    private String ponerLetraMayus(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    
    //para saber sus productos
    public ObservableList<Productos> getProductos() {
        return productos;
    }
}
