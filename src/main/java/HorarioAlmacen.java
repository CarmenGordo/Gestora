

//package modelos;

import java.util.Map;

/**
 *
 * @author carmen_gordo
 */
public class HorarioAlmacen {
    private String lunes;
    private String martes;
    private String miercoles;
    private String jueves;
    private String viernes;
    private String sabado;
    private String domingo;

    public HorarioAlmacen(Map<String, Map<String, String>> horarios) {
        this.lunes = formatearHorario(horarios.get("lunes"));
        this.martes = formatearHorario(horarios.get("martes"));
        this.miercoles = formatearHorario(horarios.get("miércoles"));
        this.jueves = formatearHorario(horarios.get("jueves"));
        this.viernes = formatearHorario(horarios.get("viernes"));
        this.sabado = horarios.containsKey("sabado") ? formatearHorario(horarios.get("sabado")) : "Cerrado";
        this.domingo = horarios.containsKey("domingo") ? formatearHorario(horarios.get("domingo")) : "Cerrado";
    }

    public String getLunes() {
        return lunes;
    }

    public String getMartes() {
        return martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public String getDomingo() {
        return domingo;
    }


    private String formatearHorario(Map<String, String> diaHorario) {
        if (diaHorario == null || diaHorario.isEmpty()) {
            return "Cerrado";
        }
        String apertura = diaHorario.getOrDefault("apertura", "Cerrado");
        String cierre = diaHorario.getOrDefault("cierre", "Cerrado");
        return apertura + " - \n" + cierre;
    }
}
