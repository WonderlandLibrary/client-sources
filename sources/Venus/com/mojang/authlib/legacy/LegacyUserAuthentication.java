/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.legacy;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.HttpUserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.legacy.LegacyAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public class LegacyUserAuthentication
extends HttpUserAuthentication {
    private static final URL AUTHENTICATION_URL = HttpAuthenticationService.constantURL("https://login.minecraft.net");
    private static final int AUTHENTICATION_VERSION = 14;
    private static final int RESPONSE_PART_PROFILE_NAME = 2;
    private static final int RESPONSE_PART_SESSION_TOKEN = 3;
    private static final int RESPONSE_PART_PROFILE_ID = 4;
    private String sessionToken;

    protected LegacyUserAuthentication(LegacyAuthenticationService legacyAuthenticationService) {
        super(legacyAuthenticationService);
    }

    @Override
    public void logIn() throws AuthenticationException {
        String string;
        String string2;
        String string3;
        String string4;
        if (StringUtils.isBlank(this.getUsername())) {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isBlank(this.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("user", this.getUsername());
        hashMap.put("password", this.getPassword());
        hashMap.put("version", 14);
        try {
            string4 = this.getAuthenticationService().performPostRequest(AUTHENTICATION_URL, HttpAuthenticationService.buildQuery(hashMap), "application/x-www-form-urlencoded").trim();
        } catch (IOException iOException) {
            throw new AuthenticationException("Authentication server is not responding", iOException);
        }
        String[] stringArray = string4.split(":");
        if (stringArray.length == 5) {
            string3 = stringArray[0];
            string2 = stringArray[5];
            string = stringArray[7];
            if (StringUtils.isBlank(string3) || StringUtils.isBlank(string2) || StringUtils.isBlank(string)) {
                throw new AuthenticationException("Unknown response from authentication server: " + string4);
            }
        } else {
            throw new InvalidCredentialsException(string4);
        }
        this.setSelectedProfile(new GameProfile(UUIDTypeAdapter.fromString(string3), string2));
        this.sessionToken = string;
        this.setUserType(UserType.LEGACY);
    }

    @Override
    public void logOut() {
        super.logOut();
        this.sessionToken = null;
    }

    @Override
    public boolean canPlayOnline() {
        return this.isLoggedIn() && this.getSelectedProfile() != null && this.getAuthenticatedToken() != null;
    }

    @Override
    public GameProfile[] getAvailableProfiles() {
        if (this.getSelectedProfile() != null) {
            return new GameProfile[]{this.getSelectedProfile()};
        }
        return new GameProfile[0];
    }

    @Override
    public void selectGameProfile(GameProfile gameProfile) throws AuthenticationException {
        throw new UnsupportedOperationException("Game profiles cannot be changed in the legacy authentication service");
    }

    @Override
    public String getAuthenticatedToken() {
        return this.sessionToken;
    }

    @Override
    public String getUserID() {
        return this.getUsername();
    }

    @Override
    public LegacyAuthenticationService getAuthenticationService() {
        return (LegacyAuthenticationService)super.getAuthenticationService();
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

