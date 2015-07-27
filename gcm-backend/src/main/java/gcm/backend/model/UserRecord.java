package gcm.backend.model;

/**
 * Created by Juan on 26/07/2015.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

import javax.inject.Named;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class UserRecord {

    @Id
    Long id;

    @Index
    private String name;

    @Index
    private String regId;
    // you can add more fields...

    @Index
    private String email;
    private String password;

    private List<String> friends;

    public UserRecord() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRegId() {
        return regId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void addFriend(String friend) {
        this.friends.add(friend);
    }

    public void removeFriend(String friend) {
        this.friends.remove(friend);
    }
}