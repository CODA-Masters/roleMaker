package gcm.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Alex on 28/07/2015.
 */
@Entity
public class Partida {

    @Index
    @Id
    Long id;

    @Index
    private String nombre;


    @Index
    private String idMaster;
    private String idJugadores;//Array de idesssss

    private String atributosjugadores;//Hashmap de atributos algo como Hashmap<String,Atributos>

    public Partida() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdMaster() {
        return idMaster;
    }

    public void setIdMaster(String idMaster) {
        this.idMaster = idMaster;
    }

    public String getIdJugadores() {
        return idJugadores;
    }

    public void setIdJugadores(String idJugadores) {
        this.idJugadores = idJugadores;
    }

    public String getAtributosjugadores() {
        return atributosjugadores;
    }

    public void setAtributosjugadores(String atributosjugadores) {
        this.atributosjugadores = atributosjugadores;
    }
}
