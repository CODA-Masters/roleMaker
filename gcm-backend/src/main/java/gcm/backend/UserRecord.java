package gcm.backend;

/**
 * Created by Juan on 26/07/2015.
 */
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import javax.inject.Named;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class UserRecord {

    @Id
    Long id;

    @Index
    private String regId;
    // you can add more fields...
    private String name;
    private String email;
    private String password;

    public UserRecord() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
}