package com.codamasters.rolemaker.utils;

/**
 * Created by Alex on 29/07/2015.
 */
public class MensajeChat {
    String mensaje;
    String fecha;
    String user;

    public MensajeChat() {
    }

    public MensajeChat(String mensaje, String fecha, String user) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.user = user;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha=fecha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
