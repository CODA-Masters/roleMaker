package gcm.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import gcm.backend.model.UserRecord;

import static gcm.backend.utils.OfyService.ofy;

/**
 * Created by Julio on 27/07/2015.
 */
@Api(name = "login", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.gcm", ownerName = "backend.gcm", packagePath = ""))
public class LoginEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    @ApiMethod(name = "login")
    public UserRecord login(@Named("name") String name, @Named("password") String password) {
        UserRecord user = findRecordbyName(name);
        if (user != null) {
            if (password == user.getPassword())
                return user;
            else
                return null;
        } else return null;

    }

    /**
     * Return a collection of registered devices
     *
     * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids
     */
    @ApiMethod(name = "listDevices")
    public CollectionResponse<UserRecord> listDevices(@Named("count") int count) {
        List<UserRecord> records = ofy().load().type(UserRecord.class).limit(count).list();
        return CollectionResponse.<UserRecord>builder().setItems(records).build();
    }

    private UserRecord findRecord(String regId) {
        return ofy().load().type(UserRecord.class).filter("regId", regId).first().now();
    }

    private UserRecord findRecordbyName(String regName) {
        return ofy().load().type(UserRecord.class).filter("name", regName).first().now();
    }
}