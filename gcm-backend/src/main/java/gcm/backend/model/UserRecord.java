package gcm.backend.model;

/**
 * Created by Juan on 26/07/2015.
 */

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Objectify object model for device registrations we are persisting
 */
@Entity
public class UserRecord {

    @Index
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

    private String friends;

    private String friendRequestsReceived;
    private String friendRequestsSent;

    public UserRecord() {
    }

    public String getFriendRequestsSent() {
        return friendRequestsSent;
    }

    public void setFriendRequestsSent(String friendRequestsSent) {
        this.friendRequestsSent = friendRequestsSent;
    }

    public String getFriendRequestsReceived() {
        return friendRequestsReceived;
    }

    public void setFriendRequestsReceived(String friendRequestsReceived) {
        this.friendRequestsReceived = friendRequestsReceived;
    }

    public Long getId() {
        return id;
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

    public String getFriends() {
        return friends;
    }

    public void setFriends(String friends) {
        this.friends = friends;
    }

    public void setId(Long id) {
        this.id = id;
    }


}