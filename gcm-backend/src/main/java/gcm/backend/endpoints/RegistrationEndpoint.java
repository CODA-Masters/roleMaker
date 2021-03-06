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
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;

import gcm.backend.model.GameRecord;
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
        record.setFriendRequestsSent("[]");
        record.setFriendRequestsReceived("[]");
        record.setProfilePic("https://upload.wikimedia.org/wikipedia/commons/9/91/Default_User_Logo.jpg");
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

    @ApiMethod(name = "findGame")
    public GameRecord findGame(@Named("id") Long id) {
        return ofy().load().type(GameRecord.class).filter("id", id).first().now();
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

    @ApiMethod(name = "sendFriendRequest")
    public void sendFriendRequest(@Named("addUserID") String addUserID, @Named("addFriendID") String addFriendID){

        UserRecord user = findRecord(Long.parseLong(addUserID));
        UserRecord friend = findRecord(Long.parseLong(addFriendID));
        boolean mutualRequest = false;

        System.out.println("Envio de petición de amigo");

        JSONParser parser=new JSONParser();
        String s;
        if(user != null && friend != null) {
            s = user.getFriendRequestsReceived();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                if(array.contains(addFriendID)) {
                    System.out.println("Hay agregación mutua");
                    mutualRequest = true;
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            // Si no se produce una petición mutua se manda una petición y el otro la recibe
            if(!mutualRequest) {

                parser = new JSONParser();
                s = user.getFriendRequestsSent();
                try {
                    Object obj = parser.parse(s);
                    JSONArray array = (JSONArray) obj;
                    array.add(addFriendID);
                    s = array.toJSONString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }

                user.setFriendRequestsSent(s);

                parser = new JSONParser();
                s = friend.getFriendRequestsReceived();
                try {
                    Object obj = parser.parse(s);
                    JSONArray array = (JSONArray) obj;
                    array.add(addUserID);
                    s = array.toJSONString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
                friend.setFriendRequestsReceived(s);

            }
            // Si el otro ya había mandado una petición se debería borrar la petición  en ambas entidades y
            // añadirse como amigos mutuamente
            else{

                s = user.getFriends();
                try {
                    Object obj = parser.parse(s);
                    JSONArray array = (JSONArray) obj;
                    array.add(addFriendID);
                    s = array.toJSONString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }

                user.setFriends(s);

                parser=new JSONParser();

                s = friend.getFriendRequestsSent();
                try {
                    Object obj = parser.parse(s);
                    JSONArray array = (JSONArray) obj;
                    array.remove(addUserID);
                    s = array.toJSONString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
                friend.setFriendRequestsSent(s);

                s = user.getFriendRequestsReceived();
                try {
                    Object obj = parser.parse(s);
                    JSONArray array = (JSONArray) obj;
                    array.remove(addFriendID);
                    s = array.toJSONString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
                user.setFriendRequestsReceived(s);

                s = friend.getFriends();
                try {
                    Object obj = parser.parse(s);
                    JSONArray array = (JSONArray) obj;
                    array.add(addUserID);
                    s = array.toJSONString();
                } catch (org.json.simple.parser.ParseException e) {
                    e.printStackTrace();
                }
                friend.setFriends(s);

            }
            ofy().save().entity(friend).now();
            ofy().save().entity(user).now();
        }

    }

    @ApiMethod(name = "acceptFriendRequest")
    public void acceptFriendRequest(@Named("addUserID") String addUserID, @Named("addFriendID") String addFriendID){

        UserRecord user = findRecord(Long.parseLong(addUserID));
        UserRecord friend = findRecord(Long.parseLong(addFriendID));

        JSONParser parser=new JSONParser();
        String s;
        if(user != null && friend != null) {
            s = user.getFriendRequestsReceived();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.remove(addFriendID);
                s = array.toJSONString();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            user.setFriendRequestsReceived(s);

            s = user.getFriends();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.add(addFriendID);
                s = array.toJSONString();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            user.setFriends(s);

            ofy().save().entity(user).now();


            parser=new JSONParser();

            s = friend.getFriendRequestsSent();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.remove(addUserID);
                s = array.toJSONString();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            friend.setFriendRequestsSent(s);

            s = friend.getFriends();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.add(addUserID);
                s = array.toJSONString();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            friend.setFriends(s);

            ofy().save().entity(friend).now();
        }

    }

    @ApiMethod(name = "denyFriendRequest")
    public void denyFriendRequest(@Named("addUserID") String addUserID, @Named("addFriendID") String addFriendID){

        UserRecord user = findRecord(Long.parseLong(addUserID));
        UserRecord friend = findRecord(Long.parseLong(addFriendID));

        JSONParser parser=new JSONParser();
        String s;
        if(user != null && friend != null) {
            s = user.getFriendRequestsReceived();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.remove(addFriendID);
                s = array.toJSONString();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }

            user.setFriendRequestsReceived(s);
            ofy().save().entity(user).now();


            parser=new JSONParser();
            s = friend.getFriendRequestsSent();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                array.remove(addUserID);
                s = array.toJSONString();
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
            friend.setFriendRequestsSent(s);

            ofy().save().entity(friend).now();
        }

    }

    @ApiMethod(name = "removeFriend")
    public void removeFriend(@Named("removeUserID") String userID, @Named("removeFriendID") String friendID){
        UserRecord user = findRecord(Long.parseLong(userID));
        UserRecord friend = findRecord(Long.parseLong(friendID));

        JSONParser parser=new JSONParser();
        String s = user.getFriends();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.remove(friendID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        user.setFriends(s);

        if(user != null) {
            ofy().save().entity(user).now();
        }

        parser=new JSONParser();
        s = friend.getFriends();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.remove(userID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        friend.setFriends(s);

        if(user != null) {
            ofy().save().entity(friend).now();
        }

    }

    @ApiMethod(name = "listFriends")
    public UserRecord listFriends(@Named("userID") String userID){
        UserRecord user = findRecord(Long.parseLong(userID));
        return user;
    }

    @ApiMethod(name = "listUsers")
    public CollectionResponse<UserRecord> showUsers(@Named("count") int count, @Named("userID") String userID){
        List<UserRecord> users = ofy().load().type(UserRecord.class).limit(count).list();
        UserRecord user = findRecord(Long.parseLong(userID));
        users.remove(user);
        return CollectionResponse.<UserRecord>builder().setItems(users).build();
    }

    @ApiMethod(name="updateGCM")
    public void updateGCM(@Named("userID") String userID, @Named("regId") String regID){
        UserRecord user = findRecord(Long.parseLong(userID));
        user.setRegId(regID);
        ofy().save().entity(user).now();
    }

    @ApiMethod(name="createGame")
    public void createGame(@Named ("name") String name, @Named("master") String master, @Named("maxPlayers") int maxPlayers,
                           @Named("description") String description, @Named("style") String style){

        GameRecord game = new GameRecord();
        game.setName(name);
        game.setMaster(master);
        game.setMaxPlayers(maxPlayers);
        game.setDescription(description);
        game.setStyle(style);
        game.setPlayers("[]");
        game.setPendingPlayers("[]");

        ofy().save().entity(game).now();
    }

    @ApiMethod(name = "joinGames")
    public CollectionResponse<GameRecord> joinGames(@Named("userID") String userID){
        List<GameRecord> games = ofy().load().type(GameRecord.class).filter("master !=", userID).list();
        return CollectionResponse.<GameRecord>builder().setItems(games).build();
    }

    @ApiMethod(name = "joinGame")
    public void joinGame(@Named("userID") String userID, @Named("gameID") String gameID){
        GameRecord game = findGame(Long.parseLong(gameID));

        JSONParser parser=new JSONParser();
        String s = game.getPendingPlayers();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.add(userID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        game.setPendingPlayers(s);

        if(game != null) {
            ofy().save().entity(game).now();
        }
    }

    // Se listan los juegos en los que el usuario participa como master o jugador
    @ApiMethod(name = "listMyGames")
    public CollectionResponse<GameRecord> listMyGames(@Named("userID") String userID){
        List<GameRecord> masterGames = ofy().load().type(GameRecord.class).filter("master", userID).list();
        List<GameRecord> allGames = ofy().load().type(GameRecord.class).list();
        List<GameRecord> games = new ArrayList<>();

        for(GameRecord game : masterGames){
            games.add(game);
        }

        JSONParser parser=new JSONParser();
        String s = "";

        for(GameRecord game : allGames){

            s = game.getPlayers();
            try {
                Object obj = parser.parse(s);
                JSONArray array = (JSONArray) obj;
                for(int i = 0; i < array.size(); i++){
                    if(array.get(i).toString().equals(userID)){
                        games.add(game);
                        break;
                    }
                }
            } catch (org.json.simple.parser.ParseException e) {
                e.printStackTrace();
            }
        }

        return CollectionResponse.<GameRecord>builder().setItems(games).build();
    }

    @ApiMethod(name = "findPartida")
    public GameRecord findPartida(@Named("gameID") String gameID){
        GameRecord game = findGame(Long.parseLong(gameID));
        return game;
    }

    @ApiMethod(name = "acceptPlayer")
    public void acceptPlayer(@Named("gameID") String gameID, @Named("playerID") String playerID){
        GameRecord game = findGame(Long.parseLong(gameID));

        if(game.getNumPlayers() > game.getMaxPlayers()){
            return;
        }

        JSONParser parser=new JSONParser();
        String s = game.getPlayers();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.add(playerID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        game.setPlayers(s);
        game.setNumPlayers(game.getNumPlayers()+1);

        s = game.getPendingPlayers();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.remove(playerID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        game.setPendingPlayers(s);

        if(game != null) {
            ofy().save().entity(game).now();
        }

    }

    @ApiMethod(name = "denyPlayer")
    public void denyPlayer(@Named("gameID") String gameID, @Named("playerID") String playerID) {
        GameRecord game = findGame(Long.parseLong(gameID));

        JSONParser parser=new JSONParser();
        String s = game.getPendingPlayers();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.remove(playerID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        game.setPendingPlayers(s);

        if(game != null) {
            ofy().save().entity(game).now();
        }

    }

    @ApiMethod(name = "kickPlayer")
    public void kickPlayer(@Named("gameID") String gameID, @Named("playerID") String playerID) {
        GameRecord game = findGame(Long.parseLong(gameID));

        JSONParser parser=new JSONParser();
        String s = game.getPlayers();
        try {
            Object obj = parser.parse(s);
            JSONArray array = (JSONArray) obj;
            array.remove(playerID);
            s = array.toJSONString();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        game.setPlayers(s);
        game.setNumPlayers(game.getNumPlayers() - 1);

        if(game != null) {
            ofy().save().entity(game).now();
        }

    }

    @ApiMethod(name = "deleteGame")
    public void deleteGame(@Named("gameID") String gameID) {
        GameRecord game = findGame(Long.parseLong(gameID));
        ofy().delete().entity(game);
    }
}
