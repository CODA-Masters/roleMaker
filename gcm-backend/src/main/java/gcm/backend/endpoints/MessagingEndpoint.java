/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package gcm.backend.endpoints;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import gcm.backend.model.UserRecord;

import static gcm.backend.utils.OfyService.ofy;

/**
 * An endpoint to send messages to devices registered with the backend
 * <p/>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p/>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "messaging", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.gcm", ownerName = "backend.gcm", packagePath = ""))
public class MessagingEndpoint {
    private static final Logger log = Logger.getLogger(MessagingEndpoint.class.getName());

    /**
     * Api Keys can be obtained from the google cloud console
     */
    private static final String API_KEY = System.getProperty("gcm.api.key");

    /**
     * Send to the first 10 devices (You can modify this to send to any number of devices or a specific device)
     *
     * @param message The message to send
     */

    @ApiMethod(name = "sendText")
    public void sendText(@Named("message") String message) throws IOException, ParseException {
        if (message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }

        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", message).build();

        List<UserRecord> records = ofy().load().type(UserRecord.class).limit(10).list();

        for (UserRecord record : records) {

            // Función send --> Parámetros : mensaje, ID del usuario, número de intentos de envio

            Result result = sender.send(msg, record.getRegId(), 5);
        }
    }

    @ApiMethod(name = "sendTextToUsers")
    public void sendTextToUsers(@Named("message") String message, @Named("userIds") String userIds) throws IOException, ParseException {
        if (message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }

        Sender sender = new Sender(API_KEY);

        // Parseamos el String y obtenemos los IDS y con ello los usuarios

        ArrayList<UserRecord> users = new ArrayList<>();

        // Recorremos el array y obtenemos el objter USER de cada nombre de usuario

        JSONParser parser=new JSONParser();
        String s = userIds;
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            for(int i = 0; i < array.size(); i++){
                users.add(findRecord(Long.parseLong(array.get(i).toString())));
            }
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        // y así accedemos a su GCM_ID para enviarles el mensaje

        // Luego en la funcion GCMReceiveMessage ya insertamos cada mensaje en el usuario y partida correspondiente

        for (UserRecord user : users) {


            s = "[]";
            String messageSend = "";
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.add(user.getId());
                array.add(message);
                messageSend = array.toJSONString();;
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            
            Message msg = new Message.Builder().addData("message", messageSend).build();

            Result result = sender.send(msg, user.getRegId(), 5);
        }
    }

    private UserRecord findRecord(Long id) {
        return ofy().load().type(UserRecord.class).filter("id", id).first().now();
    }

    public void sendMessage(@Named("message") String message) throws IOException {
        if (message == null || message.trim().length() == 0) {
            log.warning("Not sending message because it is empty");
            return;
        }
        // crop longer messages
        if (message.length() > 1000) {
            message = message.substring(0, 1000) + "[...]";
        }

        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder().addData("message", message).build();


        List<UserRecord> records = ofy().load().type(UserRecord.class).limit(10).list();


        for (UserRecord record : records) {

            // Función send --> Parámetros : mensaje, ID del usuario, número de intentos de envio

            Result result = sender.send(msg, record.getRegId(), 5);

            if (result.getMessageId() != null) {
                        log.info("Message sent to " + record.getRegId());
                        String canonicalRegId = result.getCanonicalRegistrationId();
                        if (canonicalRegId != null) {
                            // if the regId changed, we have to update the datastore
                            log.info("Registration Id changed for " + record.getRegId() + " updating to " + canonicalRegId);
                            record.setRegId(canonicalRegId);
                            ofy().save().entity(record).now();
                }
            }
        }
    }

}
