/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.yggdrasil;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.Environment;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.HttpUserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.request.AuthenticationRequest;
import com.mojang.authlib.yggdrasil.request.RefreshRequest;
import com.mojang.authlib.yggdrasil.request.ValidateRequest;
import com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
import com.mojang.authlib.yggdrasil.response.RefreshResponse;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.authlib.yggdrasil.response.User;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class YggdrasilUserAuthentication
extends HttpUserAuthentication {
    private static final Logger LOGGER = LogManager.getLogger();
    private final URL routeAuthenticate;
    private final URL routeRefresh;
    private final URL routeValidate;
    private final URL routeInvalidate;
    private final URL routeSignout;
    private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";
    private final Agent agent;
    private GameProfile[] profiles;
    private final String clientToken;
    private String accessToken;
    private boolean isOnline;

    public YggdrasilUserAuthentication(YggdrasilAuthenticationService yggdrasilAuthenticationService, String string, Agent agent) {
        this(yggdrasilAuthenticationService, string, agent, YggdrasilEnvironment.PROD);
    }

    public YggdrasilUserAuthentication(YggdrasilAuthenticationService yggdrasilAuthenticationService, String string, Agent agent, Environment environment) {
        super(yggdrasilAuthenticationService);
        this.clientToken = string;
        this.agent = agent;
        LOGGER.info("Environment: " + environment.getName(), new Object[]{". AuthHost: " + environment.getAuthHost()});
        this.routeAuthenticate = HttpAuthenticationService.constantURL(environment.getAuthHost() + "/authenticate");
        this.routeRefresh = HttpAuthenticationService.constantURL(environment.getAuthHost() + "/refresh");
        this.routeValidate = HttpAuthenticationService.constantURL(environment.getAuthHost() + "/validate");
        this.routeInvalidate = HttpAuthenticationService.constantURL(environment.getAuthHost() + "/invalidate");
        this.routeSignout = HttpAuthenticationService.constantURL(environment.getAuthHost() + "/signout");
    }

    @Override
    public boolean canLogIn() {
        return !this.canPlayOnline() && StringUtils.isNotBlank(this.getUsername()) && (StringUtils.isNotBlank(this.getPassword()) || StringUtils.isNotBlank(this.getAuthenticatedToken()));
    }

    @Override
    public void logIn() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isNotBlank(this.getAuthenticatedToken())) {
            this.logInWithToken();
        } else if (StringUtils.isNotBlank(this.getPassword())) {
            this.logInWithPassword();
        } else {
            throw new InvalidCredentialsException("Invalid password");
        }
    }

    protected void logInWithPassword() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isBlank(this.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        LOGGER.info("Logging in with username & password");
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(this.getAgent(), this.getUsername(), this.getPassword(), this.clientToken);
        AuthenticationResponse authenticationResponse = this.getAuthenticationService().makeRequest(this.routeAuthenticate, authenticationRequest, AuthenticationResponse.class);
        if (!authenticationResponse.getClientToken().equals(this.clientToken)) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        if (authenticationResponse.getSelectedProfile() != null) {
            this.setUserType(authenticationResponse.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        } else if (ArrayUtils.isNotEmpty(authenticationResponse.getAvailableProfiles())) {
            this.setUserType(authenticationResponse.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        User user = authenticationResponse.getUser();
        if (user != null && user.getId() != null) {
            this.setUserid(user.getId());
        } else {
            this.setUserid(this.getUsername());
        }
        this.isOnline = true;
        this.accessToken = authenticationResponse.getAccessToken();
        this.profiles = authenticationResponse.getAvailableProfiles();
        this.setSelectedProfile(authenticationResponse.getSelectedProfile());
        this.getModifiableUserProperties().clear();
        this.updateUserProperties(user);
    }

    protected void updateUserProperties(User user) {
        if (user == null) {
            return;
        }
        if (user.getProperties() != null) {
            this.getModifiableUserProperties().putAll(user.getProperties());
        }
    }

    protected void logInWithToken() throws AuthenticationException {
        if (StringUtils.isBlank(this.getUserID())) {
            if (StringUtils.isBlank(this.getUsername())) {
                this.setUserid(this.getUsername());
            } else {
                throw new InvalidCredentialsException("Invalid uuid & username");
            }
        }
        if (StringUtils.isBlank(this.getAuthenticatedToken())) {
            throw new InvalidCredentialsException("Invalid access token");
        }
        LOGGER.info("Logging in with access token");
        if (this.checkTokenValidity()) {
            LOGGER.debug("Skipping refresh call as we're safely logged in.");
            this.isOnline = true;
            return;
        }
        RefreshRequest refreshRequest = new RefreshRequest(this.getAuthenticatedToken(), this.clientToken);
        RefreshResponse refreshResponse = this.getAuthenticationService().makeRequest(this.routeRefresh, refreshRequest, RefreshResponse.class);
        if (!refreshResponse.getClientToken().equals(this.clientToken)) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        if (refreshResponse.getSelectedProfile() != null) {
            this.setUserType(refreshResponse.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        } else if (ArrayUtils.isNotEmpty(refreshResponse.getAvailableProfiles())) {
            this.setUserType(refreshResponse.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        if (refreshResponse.getUser() != null && refreshResponse.getUser().getId() != null) {
            this.setUserid(refreshResponse.getUser().getId());
        } else {
            this.setUserid(this.getUsername());
        }
        this.isOnline = true;
        this.accessToken = refreshResponse.getAccessToken();
        this.profiles = refreshResponse.getAvailableProfiles();
        this.setSelectedProfile(refreshResponse.getSelectedProfile());
        this.getModifiableUserProperties().clear();
        this.updateUserProperties(refreshResponse.getUser());
    }

    protected boolean checkTokenValidity() throws AuthenticationException {
        ValidateRequest validateRequest = new ValidateRequest(this.getAuthenticatedToken(), this.clientToken);
        try {
            this.getAuthenticationService().makeRequest(this.routeValidate, validateRequest, Response.class);
            return true;
        } catch (AuthenticationException authenticationException) {
            return true;
        }
    }

    @Override
    public void logOut() {
        super.logOut();
        this.accessToken = null;
        this.profiles = null;
        this.isOnline = false;
    }

    @Override
    public GameProfile[] getAvailableProfiles() {
        return this.profiles;
    }

    @Override
    public boolean isLoggedIn() {
        return StringUtils.isNotBlank(this.accessToken);
    }

    @Override
    public boolean canPlayOnline() {
        return this.isLoggedIn() && this.getSelectedProfile() != null && this.isOnline;
    }

    @Override
    public void selectGameProfile(GameProfile gameProfile) throws AuthenticationException {
        if (!this.isLoggedIn()) {
            throw new AuthenticationException("Cannot change game profile whilst not logged in");
        }
        if (this.getSelectedProfile() != null) {
            throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
        }
        if (gameProfile == null || !ArrayUtils.contains(this.profiles, gameProfile)) {
            throw new IllegalArgumentException("Invalid profile '" + gameProfile + "'");
        }
        RefreshRequest refreshRequest = new RefreshRequest(this.getAuthenticatedToken(), this.clientToken, gameProfile);
        RefreshResponse refreshResponse = this.getAuthenticationService().makeRequest(this.routeRefresh, refreshRequest, RefreshResponse.class);
        if (!refreshResponse.getClientToken().equals(this.clientToken)) {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        this.isOnline = true;
        this.accessToken = refreshResponse.getAccessToken();
        this.setSelectedProfile(refreshResponse.getSelectedProfile());
    }

    @Override
    public void loadFromStorage(Map<String, Object> map) {
        super.loadFromStorage(map);
        this.accessToken = String.valueOf(map.get(STORAGE_KEY_ACCESS_TOKEN));
    }

    @Override
    public Map<String, Object> saveForStorage() {
        Map<String, Object> map = super.saveForStorage();
        if (StringUtils.isNotBlank(this.getAuthenticatedToken())) {
            map.put(STORAGE_KEY_ACCESS_TOKEN, this.getAuthenticatedToken());
        }
        return map;
    }

    @Deprecated
    public String getSessionToken() {
        if (this.isLoggedIn() && this.getSelectedProfile() != null && this.canPlayOnline()) {
            return String.format("token:%s:%s", this.getAuthenticatedToken(), this.getSelectedProfile().getId());
        }
        return null;
    }

    @Override
    public String getAuthenticatedToken() {
        return this.accessToken;
    }

    public Agent getAgent() {
        return this.agent;
    }

    @Override
    public String toString() {
        return "YggdrasilAuthenticationService{agent=" + this.agent + ", profiles=" + Arrays.toString(this.profiles) + ", selectedProfile=" + this.getSelectedProfile() + ", username='" + this.getUsername() + '\'' + ", isLoggedIn=" + this.isLoggedIn() + ", userType=" + (Object)((Object)this.getUserType()) + ", canPlayOnline=" + this.canPlayOnline() + ", accessToken='" + this.accessToken + '\'' + ", clientToken='" + this.clientToken + '\'' + '}';
    }

    @Override
    public YggdrasilAuthenticationService getAuthenticationService() {
        return (YggdrasilAuthenticationService)super.getAuthenticationService();
    }

    @Override
    public HttpAuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return this.getAuthenticationService();
    }
}

