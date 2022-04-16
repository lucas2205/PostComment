

package com.project2.project2.dto;

import java.util.Date;


public class ErrorDetalles {
    
    private Date marcaDeTiempo;
    private String mensaje;
    private String Detalles;

    public ErrorDetalles(Date marcaDeTiempo, String mensaje, String Detalles) {
        this.marcaDeTiempo = marcaDeTiempo;
        this.mensaje = mensaje;
        this.Detalles = Detalles;
    }

    public Date getMarcaDeTiempo() {
        return marcaDeTiempo;
    }

    public void setMarcaDeTiempo(Date marcaDeTiempo) {
        this.marcaDeTiempo = marcaDeTiempo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getDetalles() {
        return Detalles;
    }

    public void setDetalles(String Detalles) {
        this.Detalles = Detalles;
    }

}
