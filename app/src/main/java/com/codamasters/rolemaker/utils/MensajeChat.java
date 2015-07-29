package com.codamasters.rolemaker.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 29/07/2015.
 */
public class MensajeChat {
    String mensaje;
    Date fecha;
    String user;

    public MensajeChat() {
    }

    public MensajeChat(String mensaje, Date fecha, String user) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        Log.d("problemo fec",fecha);
        String DATE_FORMAT_NOW = "HH:mm:ss dd/mm/yyy";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        try {
            date = sdf.parse(fecha);
        } catch(ParseException e) {
            //Exception handling
        }
        this.fecha=date;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
