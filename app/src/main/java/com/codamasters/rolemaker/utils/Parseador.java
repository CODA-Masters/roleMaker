package com.codamasters.rolemaker.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Alex on 29/07/2015.
 */
public class Parseador {
    public static final String DATE = "date",MENSAJE="message",USER="username";

    public static ArrayList<MensajeChat> parsearListaMensajes(ArrayList<String> lista){
        ArrayList<MensajeChat> lista_mensajes = new ArrayList<>();
        for (String str : lista) {
            if (str != null){
                MensajeChat mc = parsearMensaje(str);
                lista_mensajes.add(mc);
            }
        }
        return lista_mensajes;
    }
    public static MensajeChat parsearMensaje(String str) {
        MensajeChat mc = new MensajeChat();
        try {
            JSONObject jsonob = new JSONObject(str);
            mc.setFecha(jsonob.getString(DATE));
            mc.setMensaje(jsonob.getString(MENSAJE));
            mc.setUser(jsonob.getString(USER));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mc;
    }
}
