/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-07-16 18:28:29 UTC)
 * on 2015-07-27 at 13:47:58 UTC 
 * Modify at your own risk.
 */

package gcm.backend.registration;

/**
 * Service definition for Registration (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link RegistrationRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class Registration extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the registration library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "registration/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public Registration(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  Registration(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "addFriend".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link AddFriend#execute()} method to invoke the remote operation.
   *
   * @param userID
   * @param friendID
   * @return the request
   */
  public AddFriend addFriend(java.lang.String userID, java.lang.String friendID) throws java.io.IOException {
    AddFriend result = new AddFriend(userID, friendID);
    initialize(result);
    return result;
  }

  public class AddFriend extends RegistrationRequest<Void> {

    private static final String REST_PATH = "addFriend/{userID}/{friendID}";

    /**
     * Create a request for the method "addFriend".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link AddFriend#execute()} method to invoke the remote
     * operation. <p> {@link
     * AddFriend#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param userID
     * @param friendID
     * @since 1.13
     */
    protected AddFriend(java.lang.String userID, java.lang.String friendID) {
      super(Registration.this, "POST", REST_PATH, null, Void.class);
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
      this.friendID = com.google.api.client.util.Preconditions.checkNotNull(friendID, "Required parameter friendID must be specified.");
    }

    @Override
    public AddFriend setAlt(java.lang.String alt) {
      return (AddFriend) super.setAlt(alt);
    }

    @Override
    public AddFriend setFields(java.lang.String fields) {
      return (AddFriend) super.setFields(fields);
    }

    @Override
    public AddFriend setKey(java.lang.String key) {
      return (AddFriend) super.setKey(key);
    }

    @Override
    public AddFriend setOauthToken(java.lang.String oauthToken) {
      return (AddFriend) super.setOauthToken(oauthToken);
    }

    @Override
    public AddFriend setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (AddFriend) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public AddFriend setQuotaUser(java.lang.String quotaUser) {
      return (AddFriend) super.setQuotaUser(quotaUser);
    }

    @Override
    public AddFriend setUserIp(java.lang.String userIp) {
      return (AddFriend) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public AddFriend setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String friendID;

    /**

     */
    public java.lang.String getFriendID() {
      return friendID;
    }

    public AddFriend setFriendID(java.lang.String friendID) {
      this.friendID = friendID;
      return this;
    }

    @Override
    public AddFriend set(String parameterName, Object value) {
      return (AddFriend) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listFriends".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link ListFriends#execute()} method to invoke the remote operation.
   *
   * @param userID
   * @return the request
   */
  public ListFriends listFriends(java.lang.String userID) throws java.io.IOException {
    ListFriends result = new ListFriends(userID);
    initialize(result);
    return result;
  }

  public class ListFriends extends RegistrationRequest<gcm.backend.registration.model.CollectionResponseUserRecord> {

    private static final String REST_PATH = "userrecord/{userID}";

    /**
     * Create a request for the method "listFriends".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link ListFriends#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListFriends#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param userID
     * @since 1.13
     */
    protected ListFriends(java.lang.String userID) {
      super(Registration.this, "GET", REST_PATH, null, gcm.backend.registration.model.CollectionResponseUserRecord.class);
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ListFriends setAlt(java.lang.String alt) {
      return (ListFriends) super.setAlt(alt);
    }

    @Override
    public ListFriends setFields(java.lang.String fields) {
      return (ListFriends) super.setFields(fields);
    }

    @Override
    public ListFriends setKey(java.lang.String key) {
      return (ListFriends) super.setKey(key);
    }

    @Override
    public ListFriends setOauthToken(java.lang.String oauthToken) {
      return (ListFriends) super.setOauthToken(oauthToken);
    }

    @Override
    public ListFriends setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListFriends) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListFriends setQuotaUser(java.lang.String quotaUser) {
      return (ListFriends) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListFriends setUserIp(java.lang.String userIp) {
      return (ListFriends) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public ListFriends setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @Override
    public ListFriends set(String parameterName, Object value) {
      return (ListFriends) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listUsers".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link ListUsers#execute()} method to invoke the remote operation.
   *
   * @param count
   * @return the request
   */
  public ListUsers listUsers(java.lang.Integer count) throws java.io.IOException {
    ListUsers result = new ListUsers(count);
    initialize(result);
    return result;
  }

  public class ListUsers extends RegistrationRequest<gcm.backend.registration.model.CollectionResponseUserRecord> {

    private static final String REST_PATH = "showUsers/{count}";

    /**
     * Create a request for the method "listUsers".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link ListUsers#execute()} method to invoke the remote
     * operation. <p> {@link
     * ListUsers#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param count
     * @since 1.13
     */
    protected ListUsers(java.lang.Integer count) {
      super(Registration.this, "POST", REST_PATH, null, gcm.backend.registration.model.CollectionResponseUserRecord.class);
      this.count = com.google.api.client.util.Preconditions.checkNotNull(count, "Required parameter count must be specified.");
    }

    @Override
    public ListUsers setAlt(java.lang.String alt) {
      return (ListUsers) super.setAlt(alt);
    }

    @Override
    public ListUsers setFields(java.lang.String fields) {
      return (ListUsers) super.setFields(fields);
    }

    @Override
    public ListUsers setKey(java.lang.String key) {
      return (ListUsers) super.setKey(key);
    }

    @Override
    public ListUsers setOauthToken(java.lang.String oauthToken) {
      return (ListUsers) super.setOauthToken(oauthToken);
    }

    @Override
    public ListUsers setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListUsers) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListUsers setQuotaUser(java.lang.String quotaUser) {
      return (ListUsers) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListUsers setUserIp(java.lang.String userIp) {
      return (ListUsers) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer count;

    /**

     */
    public java.lang.Integer getCount() {
      return count;
    }

    public ListUsers setCount(java.lang.Integer count) {
      this.count = count;
      return this;
    }

    @Override
    public ListUsers set(String parameterName, Object value) {
      return (ListUsers) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "login".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link Login#execute()} method to invoke the remote operation.
   *
   * @param regName
   * @param regPassword
   * @return the request
   */
  public Login login(java.lang.String regName, java.lang.String regPassword) throws java.io.IOException {
    Login result = new Login(regName, regPassword);
    initialize(result);
    return result;
  }

  public class Login extends RegistrationRequest<gcm.backend.registration.model.UserRecord> {

    private static final String REST_PATH = "login/{regName}/{regPassword}";

    /**
     * Create a request for the method "login".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link Login#execute()} method to invoke the remote operation.
     * <p> {@link
     * Login#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
     * be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param regName
     * @param regPassword
     * @since 1.13
     */
    protected Login(java.lang.String regName, java.lang.String regPassword) {
      super(Registration.this, "POST", REST_PATH, null, gcm.backend.registration.model.UserRecord.class);
      this.regName = com.google.api.client.util.Preconditions.checkNotNull(regName, "Required parameter regName must be specified.");
      this.regPassword = com.google.api.client.util.Preconditions.checkNotNull(regPassword, "Required parameter regPassword must be specified.");
    }

    @Override
    public Login setAlt(java.lang.String alt) {
      return (Login) super.setAlt(alt);
    }

    @Override
    public Login setFields(java.lang.String fields) {
      return (Login) super.setFields(fields);
    }

    @Override
    public Login setKey(java.lang.String key) {
      return (Login) super.setKey(key);
    }

    @Override
    public Login setOauthToken(java.lang.String oauthToken) {
      return (Login) super.setOauthToken(oauthToken);
    }

    @Override
    public Login setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Login) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Login setQuotaUser(java.lang.String quotaUser) {
      return (Login) super.setQuotaUser(quotaUser);
    }

    @Override
    public Login setUserIp(java.lang.String userIp) {
      return (Login) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String regName;

    /**

     */
    public java.lang.String getRegName() {
      return regName;
    }

    public Login setRegName(java.lang.String regName) {
      this.regName = regName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String regPassword;

    /**

     */
    public java.lang.String getRegPassword() {
      return regPassword;
    }

    public Login setRegPassword(java.lang.String regPassword) {
      this.regPassword = regPassword;
      return this;
    }

    @Override
    public Login set(String parameterName, Object value) {
      return (Login) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "register".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link Register#execute()} method to invoke the remote operation.
   *
   * @param regName
   * @param regId
   * @param regEmail
   * @param regPassword
   * @return the request
   */
  public Register register(java.lang.String regName, java.lang.String regId, java.lang.String regEmail, java.lang.String regPassword) throws java.io.IOException {
    Register result = new Register(regName, regId, regEmail, regPassword);
    initialize(result);
    return result;
  }

  public class Register extends RegistrationRequest<Void> {

    private static final String REST_PATH = "registerDevice/{regName}/{regId}/{regEmail}/{regPassword}";

    /**
     * Create a request for the method "register".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link Register#execute()} method to invoke the remote operation.
     * <p> {@link
     * Register#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param regName
     * @param regId
     * @param regEmail
     * @param regPassword
     * @since 1.13
     */
    protected Register(java.lang.String regName, java.lang.String regId, java.lang.String regEmail, java.lang.String regPassword) {
      super(Registration.this, "POST", REST_PATH, null, Void.class);
      this.regName = com.google.api.client.util.Preconditions.checkNotNull(regName, "Required parameter regName must be specified.");
      this.regId = com.google.api.client.util.Preconditions.checkNotNull(regId, "Required parameter regId must be specified.");
      this.regEmail = com.google.api.client.util.Preconditions.checkNotNull(regEmail, "Required parameter regEmail must be specified.");
      this.regPassword = com.google.api.client.util.Preconditions.checkNotNull(regPassword, "Required parameter regPassword must be specified.");
    }

    @Override
    public Register setAlt(java.lang.String alt) {
      return (Register) super.setAlt(alt);
    }

    @Override
    public Register setFields(java.lang.String fields) {
      return (Register) super.setFields(fields);
    }

    @Override
    public Register setKey(java.lang.String key) {
      return (Register) super.setKey(key);
    }

    @Override
    public Register setOauthToken(java.lang.String oauthToken) {
      return (Register) super.setOauthToken(oauthToken);
    }

    @Override
    public Register setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Register) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Register setQuotaUser(java.lang.String quotaUser) {
      return (Register) super.setQuotaUser(quotaUser);
    }

    @Override
    public Register setUserIp(java.lang.String userIp) {
      return (Register) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String regName;

    /**

     */
    public java.lang.String getRegName() {
      return regName;
    }

    public Register setRegName(java.lang.String regName) {
      this.regName = regName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String regId;

    /**

     */
    public java.lang.String getRegId() {
      return regId;
    }

    public Register setRegId(java.lang.String regId) {
      this.regId = regId;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String regEmail;

    /**

     */
    public java.lang.String getRegEmail() {
      return regEmail;
    }

    public Register setRegEmail(java.lang.String regEmail) {
      this.regEmail = regEmail;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String regPassword;

    /**

     */
    public java.lang.String getRegPassword() {
      return regPassword;
    }

    public Register setRegPassword(java.lang.String regPassword) {
      this.regPassword = regPassword;
      return this;
    }

    @Override
    public Register set(String parameterName, Object value) {
      return (Register) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "removeFriend".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link RemoveFriend#execute()} method to invoke the remote operation.
   *
   * @param userID
   * @param friendID
   * @return the request
   */
  public RemoveFriend removeFriend(java.lang.String userID, java.lang.String friendID) throws java.io.IOException {
    RemoveFriend result = new RemoveFriend(userID, friendID);
    initialize(result);
    return result;
  }

  public class RemoveFriend extends RegistrationRequest<Void> {

    private static final String REST_PATH = "friend/{userID}/{friendID}";

    /**
     * Create a request for the method "removeFriend".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link RemoveFriend#execute()} method to invoke the remote
     * operation. <p> {@link
     * RemoveFriend#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param userID
     * @param friendID
     * @since 1.13
     */
    protected RemoveFriend(java.lang.String userID, java.lang.String friendID) {
      super(Registration.this, "DELETE", REST_PATH, null, Void.class);
      this.userID = com.google.api.client.util.Preconditions.checkNotNull(userID, "Required parameter userID must be specified.");
      this.friendID = com.google.api.client.util.Preconditions.checkNotNull(friendID, "Required parameter friendID must be specified.");
    }

    @Override
    public RemoveFriend setAlt(java.lang.String alt) {
      return (RemoveFriend) super.setAlt(alt);
    }

    @Override
    public RemoveFriend setFields(java.lang.String fields) {
      return (RemoveFriend) super.setFields(fields);
    }

    @Override
    public RemoveFriend setKey(java.lang.String key) {
      return (RemoveFriend) super.setKey(key);
    }

    @Override
    public RemoveFriend setOauthToken(java.lang.String oauthToken) {
      return (RemoveFriend) super.setOauthToken(oauthToken);
    }

    @Override
    public RemoveFriend setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (RemoveFriend) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public RemoveFriend setQuotaUser(java.lang.String quotaUser) {
      return (RemoveFriend) super.setQuotaUser(quotaUser);
    }

    @Override
    public RemoveFriend setUserIp(java.lang.String userIp) {
      return (RemoveFriend) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String userID;

    /**

     */
    public java.lang.String getUserID() {
      return userID;
    }

    public RemoveFriend setUserID(java.lang.String userID) {
      this.userID = userID;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String friendID;

    /**

     */
    public java.lang.String getFriendID() {
      return friendID;
    }

    public RemoveFriend setFriendID(java.lang.String friendID) {
      this.friendID = friendID;
      return this;
    }

    @Override
    public RemoveFriend set(String parameterName, Object value) {
      return (RemoveFriend) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "unregister".
   *
   * This request holds the parameters needed by the registration server.  After setting any optional
   * parameters, call the {@link Unregister#execute()} method to invoke the remote operation.
   *
   * @param regId
   * @return the request
   */
  public Unregister unregister(java.lang.String regId) throws java.io.IOException {
    Unregister result = new Unregister(regId);
    initialize(result);
    return result;
  }

  public class Unregister extends RegistrationRequest<Void> {

    private static final String REST_PATH = "unregisterDevice/{regId}";

    /**
     * Create a request for the method "unregister".
     *
     * This request holds the parameters needed by the the registration server.  After setting any
     * optional parameters, call the {@link Unregister#execute()} method to invoke the remote
     * operation. <p> {@link
     * Unregister#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param regId
     * @since 1.13
     */
    protected Unregister(java.lang.String regId) {
      super(Registration.this, "POST", REST_PATH, null, Void.class);
      this.regId = com.google.api.client.util.Preconditions.checkNotNull(regId, "Required parameter regId must be specified.");
    }

    @Override
    public Unregister setAlt(java.lang.String alt) {
      return (Unregister) super.setAlt(alt);
    }

    @Override
    public Unregister setFields(java.lang.String fields) {
      return (Unregister) super.setFields(fields);
    }

    @Override
    public Unregister setKey(java.lang.String key) {
      return (Unregister) super.setKey(key);
    }

    @Override
    public Unregister setOauthToken(java.lang.String oauthToken) {
      return (Unregister) super.setOauthToken(oauthToken);
    }

    @Override
    public Unregister setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Unregister) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Unregister setQuotaUser(java.lang.String quotaUser) {
      return (Unregister) super.setQuotaUser(quotaUser);
    }

    @Override
    public Unregister setUserIp(java.lang.String userIp) {
      return (Unregister) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String regId;

    /**

     */
    public java.lang.String getRegId() {
      return regId;
    }

    public Unregister setRegId(java.lang.String regId) {
      this.regId = regId;
      return this;
    }

    @Override
    public Unregister set(String parameterName, Object value) {
      return (Unregister) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link Registration}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link Registration}. */
    @Override
    public Registration build() {
      return new Registration(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link RegistrationRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setRegistrationRequestInitializer(
        RegistrationRequestInitializer registrationRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(registrationRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
