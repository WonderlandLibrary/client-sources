package com.mojang.authlib;

import com.mojang.authlib.exceptions.*;
import java.util.*;
import com.mojang.authlib.properties.*;

public interface UserAuthentication
{
    boolean canLogIn();
    
    void logIn() throws AuthenticationException;
    
    void logOut();
    
    boolean isLoggedIn();
    
    boolean canPlayOnline();
    
    GameProfile[] getAvailableProfiles();
    
    GameProfile getSelectedProfile();
    
    void selectGameProfile(final GameProfile p0) throws AuthenticationException;
    
    void loadFromStorage(final Map<String, Object> p0);
    
    Map<String, Object> saveForStorage();
    
    void setUsername(final String p0);
    
    void setPassword(final String p0);
    
    String getAuthenticatedToken();
    
    String getUserID();
    
    PropertyMap getUserProperties();
    
    UserType getUserType();
}
