/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package gcm.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import gcm.backend.model.UserRecord;

import static gcm.backend.utils.OfyService.ofy;

/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 * <p/>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p/>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "registration", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.gcm", ownerName = "backend.gcm", packagePath = ""))
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    /**
     * Register a device to the backend
     *
     * @param regId The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "register")
      public void registerDevice(@Named("regName") String regName, @Named("regId") String regId, @Named("regEmail") String regEmail, @Named("regPassword") String regPassword) {
        if (findRecordbyName(regName) != null) {
            log.info("Device " + regId + " already registered, skipping register");
            return;
        }

        UserRecord record = new UserRecord();

        record.setName(regName);
        record.setRegId(regId);
        record.setEmail(regEmail);
        record.setPassword(regPassword);
        record.setFriends("[]");
        ofy().save().entity(record).now();

    }

    /**
     * Unregister a device from the backend
     *
     * @param regId The Google Cloud Messaging registration Id to remove
     */
    @ApiMethod(name = "unregister")
    public void unregisterDevice(@Named("regId") String regId) {
        UserRecord record = findRecord(Long.parseLong(regId));
        if (record == null) {
            log.info("User " + regId + " not registered, skipping unregister");
            return;
        }
        ofy().delete().entity(record).now();
    }

    @ApiMethod(name = "findRecord")
    public UserRecord findRecord(@Named("id") Long id) {
        return ofy().load().type(UserRecord.class).filter("id", id).first().now();
    }

    @ApiMethod(name = "findRecordbyName")
    public UserRecord findRecordbyName(@Named("regName") String regName) {
        return ofy().load().type(UserRecord.class).filter("name", regName).first().now();
    }

    @ApiMethod(name = "login")
    public UserRecord login(@Named("regName") String name, @Named("regPassword") String password) {
        UserRecord user = findRecordbyName(name);
        if (user != null) {
            if (password.equals(user.getPassword()))
                return user;
            else
                return null;
        } else
            return null;

    }

    @ApiMethod(name = "addFriend")
    public void addFriend(@Named("addUserID") String addUserID, @Named("addFriendID") String addFriendID){

        UserRecord user = findRecord(Long.parseLong(addUserID));

        JSONParser parser=new JSONParser();
        String s = user.getFriends();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.add(addFriendID);
            s = array.toJSONString();
         } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        user.setFriends(s);

        if(user != null) {
            ofy().save().entity(user).now();
        }

    }

    @ApiMethod(name = "removeFriend")
    public void removeFriend(@Named("removeUserID") String userID, @Named("removeFriendID") String friendID){
        UserRecord user = findRecord(Long.parseLong(userID));
        UserRecord friend = findRecord(Long.parseLong(friendID));

        String[] aux = user.getFriends().split(" ");
        String new_friends = "";

        for (String s : aux){
            if(s != friend.getName())
                new_friends.concat(s).concat(" ");
        }

        UserRecord record = new UserRecord();
        record.setId(user.getId());
        record.setName(user.getName());
        record.setRegId(user.getRegId());
        record.setEmail(user.getEmail());
        record.setPassword(user.getPassword());
        record.setFriends(new_friends);

        ofy().save().entity(record).now();

    }

    @ApiMethod(name = "listFriends")
    public UserRecord listFriends(@Named("userID") String userID){
        UserRecord user = findRecord(Long.parseLong(userID));
        return user;
    }

    @ApiMethod(name = "listUsers")
    public CollectionResponse<UserRecord> showUsers(@Named("count") int count){
        List<UserRecord> users = ofy().load().type(UserRecord.class).limit(count).list();
        return CollectionResponse.<UserRecord>builder().setItems(users).build();
    }
}
