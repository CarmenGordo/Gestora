

import java.util.Map;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author carmen_gordo
 */
public class Almacenes {

    public String id_almacen;
    public String nombre;
    public String direccion;
    public String ciudad;
    public String pais;
    public int telefono;
    public Map<String, String> horario;
    public int capacidad_ocupada;
    public int capacidad_total;
    public String id_tienda;
    private StringProperty horarioString;
    
    private ObservableList<Productos> productos;

    public Almacenes() {
        this.horarioString = new SimpleStringProperty(recogerHorario());
    }

    public Almacenes(String id_almacen, String nombre, String direccion, String ciudad, String pais, int telefono, Map<String, String> horario, int capacidad_ocupada, int capacidad_total, String id_tienda) {
        this.id_almacen = id_almacen;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.pais = pais;
        this.telefono = telefono;
        this.horario = horario;
        this.capacidad_ocupada = capacidad_ocupada;
        this.capacidad_total = capacidad_total;
        this.id_tienda = id_tienda;
        
        this.horarioString = new SimpleStringProperty(recogerHorario());
    }

    public String getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(String id_almacen) {
        this.id_almacen = id_almacen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    }

    public int getCapacidad_ocupada() {
        return capacidad_ocupada;
    }

    public void setCapacidad_ocupada(int capacidad_ocupada) {
        this.capacidad_ocupada = capacidad_ocupada;
    }

    public int getCapacidad_total() {
        return capacidad_total;
    }

    public void setCapacidad_total(int capacidad_total) {
        this.capacidad_total = capacidad_total;
    }

    public String getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(String id_tienda) {
        this.id_tienda = id_tienda;
    }
    
    public String recogerHorario() {
        if (horario == null || horario.isEmpty()) {
            System.out.println("El horario es nulo o está vacío.");
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
            horarioStringBuilder.setLength(horarioStringBuilder.length() - 2); // Eliminar la última coma y espacio
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
    
    //para saber sus producots
    public ObservableList<Productos> getProductos() {
        return productos;
    }

}
