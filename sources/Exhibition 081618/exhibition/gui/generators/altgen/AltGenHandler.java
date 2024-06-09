package exhibition.gui.generators.altgen;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exhibition.util.security.Connection;
import exhibition.util.security.Connector;

public class AltGenHandler {
   private String baseURL = "https://api.alt-gen.com/";
   private AltGenHandler.AltGenUser user;
   private String apiKey = null;

   public void setApiKey(String apiKey) {
      this.apiKey = apiKey;
   }

   public String getApiKey() {
      return this.apiKey;
   }

   public Connection connectApi(String dir) {
      Connection connection = new Connection(this.baseURL + dir);
      connection.setHeaders("X-Authorization", this.apiKey);
      return connection;
   }

   public boolean setupUser() {
      String response = Connector.get(this.connectApi("user"));
      if (response.contains("error")) {
         return false;
      } else {
         JsonObject jo = (new JsonParser()).parse(response).getAsJsonObject();
         this.user = new AltGenHandler.AltGenUser(jo.get("username").getAsString(), jo.get("avatar").getAsString(), jo.get("active_plan").getAsString());
         return true;
      }
   }

   public String getAlt() {
      String response = Connector.get(this.connectApi("generator"));
      if (response.contains("error")) {
         return "Error while generating alt!";
      } else if (response.contains("email")) {
         JsonObject jo = (new JsonParser()).parse(response).getAsJsonObject();
         return jo.get("email").getAsString() + ":" + jo.get("password").getAsString();
      } else {
         return "Could not generate alt!";
      }
   }

   public AltGenHandler.AltGenUser getUser() {
      return this.user;
   }

   public class AltGenUser {
      private String username;
      private String avatarURL;
      private String activePlan;
      private boolean softLimit;

      public AltGenUser(String username, String avatarURL, String activePlan) {
         this.username = username;
         this.avatarURL = avatarURL;
         this.activePlan = activePlan;
      }

      public String getUsername() {
         return this.username;
      }

      public String getAvatarURL() {
         return this.avatarURL;
      }

      public String getActivePlan() {
         return this.activePlan;
      }

      public boolean isSoftLimit() {
         return this.softLimit;
      }

      public void setSoftLimit(boolean softLimit) {
         this.softLimit = softLimit;
      }
   }
}
