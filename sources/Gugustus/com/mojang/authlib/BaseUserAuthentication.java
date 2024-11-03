package com.mojang.authlib;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import net.augustus.utils.Logger;

public abstract class BaseUserAuthentication implements UserAuthentication {
   private static final Logger LOGGER = new Logger();
   protected static final String STORAGE_KEY_PROFILE_NAME = "displayName";
   protected static final String STORAGE_KEY_PROFILE_ID = "uuid";
   protected static final String STORAGE_KEY_PROFILE_PROPERTIES = "profileProperties";
   protected static final String STORAGE_KEY_USER_NAME = "username";
   protected static final String STORAGE_KEY_USER_ID = "userid";
   protected static final String STORAGE_KEY_USER_PROPERTIES = "userProperties";
   private final AuthenticationService authenticationService;
   private final PropertyMap userProperties = new PropertyMap();
   private String userid;
   private String username;
   private String password;
   private GameProfile selectedProfile;
   private UserType userType;

   protected BaseUserAuthentication(AuthenticationService authenticationService) {
      Validate.notNull(authenticationService);
      this.authenticationService = authenticationService;
   }

   @Override
   public boolean canLogIn() {
      return !this.canPlayOnline() && StringUtils.isNotBlank(this.getUsername()) && StringUtils.isNotBlank(this.getPassword());
   }

   @Override
   public void logOut() {
      this.password = null;
      this.userid = null;
      this.setSelectedProfile(null);
      this.getModifiableUserProperties().clear();
      this.setUserType(null);
   }

   @Override
   public boolean isLoggedIn() {
      return this.getSelectedProfile() != null;
   }

   @Override
   public void setUsername(String username) {
      if (this.isLoggedIn() && this.canPlayOnline()) {
         throw new IllegalStateException("Cannot change username whilst logged in & online");
      } else {
         this.username = username;
      }
   }

   @Override
   public void setPassword(String password) {
      if (this.isLoggedIn() && this.canPlayOnline() && StringUtils.isNotBlank(password)) {
         throw new IllegalStateException("Cannot set password whilst logged in & online");
      } else {
         this.password = password;
      }
   }

   protected String getUsername() {
      return this.username;
   }

   protected String getPassword() {
      return this.password;
   }

   @Override
   public void loadFromStorage(Map<String, Object> credentials) {
      this.logOut();
      this.setUsername(String.valueOf(credentials.get("username")));
      if (credentials.containsKey("userid")) {
         this.userid = String.valueOf(credentials.get("userid"));
      } else {
         this.userid = this.username;
      }

      if (credentials.containsKey("userProperties")) {
         try {
            for(Map<String, String> propertyMap : (List<Map<String, String>>)credentials.get("userProperties")) {
               String name = propertyMap.get("name");
               String value = propertyMap.get("value");
               String signature = propertyMap.get("signature");
               if (signature == null) {
                  this.getModifiableUserProperties().put(name, new Property(name, value));
               } else {
                  this.getModifiableUserProperties().put(name, new Property(name, value, signature));
               }
            }
         } catch (Throwable var10) {
            LOGGER.warn("Couldn't deserialize user properties", var10);
         }
      }

      if (credentials.containsKey("displayName") && credentials.containsKey("uuid")) {
         GameProfile profile = new GameProfile(
            UUIDTypeAdapter.fromString(String.valueOf(credentials.get("uuid"))), String.valueOf(credentials.get("displayName"))
         );
         if (credentials.containsKey("profileProperties")) {
            try {
               for(Map<String, String> propertyMap : (List<Map<String, String>>)credentials.get("profileProperties")) {
                  String name = propertyMap.get("name");
                  String value = propertyMap.get("value");
                  String signature = propertyMap.get("signature");
                  if (signature == null) {
                     profile.getProperties().put(name, new Property(name, value));
                  } else {
                     profile.getProperties().put(name, new Property(name, value, signature));
                  }
               }
            } catch (Throwable var9) {
               LOGGER.warn("Couldn't deserialize profile properties", var9);
            }
         }

         this.setSelectedProfile(profile);
      }
   }

   @Override
   public Map<String, Object> saveForStorage() {
      Map<String, Object> result = new HashMap<>();
      if (this.getUsername() != null) {
         result.put("username", this.getUsername());
      }

      if (this.getUserID() != null) {
         result.put("userid", this.getUserID());
      } else if (this.getUsername() != null) {
         result.put("username", this.getUsername());
      }

      if (!this.getUserProperties().isEmpty()) {
         List<Map<String, String>> properties = new ArrayList<>();

         for(Property userProperty : this.getUserProperties().values()) {
            Map<String, String> property = new HashMap<>();
            property.put("name", userProperty.getName());
            property.put("value", userProperty.getValue());
            property.put("signature", userProperty.getSignature());
            properties.add(property);
         }

         result.put("userProperties", properties);
      }

      GameProfile selectedProfile = this.getSelectedProfile();
      if (selectedProfile != null) {
         result.put("displayName", selectedProfile.getName());
         result.put("uuid", selectedProfile.getId());
         List<Map<String, String>> properties = new ArrayList<>();

         for(Property profileProperty : selectedProfile.getProperties().values()) {
            Map<String, String> property = new HashMap<>();
            property.put("name", profileProperty.getName());
            property.put("value", profileProperty.getValue());
            property.put("signature", profileProperty.getSignature());
            properties.add(property);
         }

         if (!properties.isEmpty()) {
            result.put("profileProperties", properties);
         }
      }

      return result;
   }

   protected void setSelectedProfile(GameProfile selectedProfile) {
      this.selectedProfile = selectedProfile;
   }

   @Override
   public GameProfile getSelectedProfile() {
      return this.selectedProfile;
   }

   @Override
   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(this.getClass().getSimpleName());
      result.append("{");
      if (this.isLoggedIn()) {
         result.append("Logged in as ");
         result.append(this.getUsername());
         if (this.getSelectedProfile() != null) {
            result.append(" / ");
            result.append(this.getSelectedProfile());
            result.append(" - ");
            if (this.canPlayOnline()) {
               result.append("Online");
            } else {
               result.append("Offline");
            }
         }
      } else {
         result.append("Not logged in");
      }

      result.append("}");
      return result.toString();
   }

   public AuthenticationService getAuthenticationService() {
      return this.authenticationService;
   }

   @Override
   public String getUserID() {
      return this.userid;
   }

   @Override
   public PropertyMap getUserProperties() {
      if (this.isLoggedIn()) {
         PropertyMap result = new PropertyMap();
         result.putAll(this.getModifiableUserProperties());
         return result;
      } else {
         return new PropertyMap();
      }
   }

   protected PropertyMap getModifiableUserProperties() {
      return this.userProperties;
   }

   @Override
   public UserType getUserType() {
      if (this.isLoggedIn()) {
         return this.userType == null ? UserType.LEGACY : this.userType;
      } else {
         return null;
      }
   }

   protected void setUserType(UserType userType) {
      this.userType = userType;
   }

   protected void setUserid(String userid) {
      this.userid = userid;
   }
}
