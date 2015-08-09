package gcm.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Alex on 28/07/2015.
 */
@Entity
public class GameRecord {

    @Index
    @Id
    Long id;

    @Index
    private String name; // Nombre de la partida

    @Index
    private String master; // ID del master
    private String players; // Array de IDs de jugadores
    private int numPlayers; // Número de ugadores actualmente en partida
    private int maxPlayers; // Número máximo de jugadores permitidos
    private String description; // Descripción de la partida
    private String style; // Estilo de partida (A elegir para personalizar skins o cualquier otra cosa):
                        // fantasía medieval, futurista, steampunk, realista)


    public GameRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
