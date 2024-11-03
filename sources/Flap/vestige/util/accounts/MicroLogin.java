package vestige.util.accounts;

import com.google.gson.JsonObject;
import java.util.function.Consumer;

public class MicroLogin extends Account {
   private String refreshToken;

   public MicroLogin(String name, String uuid, String accessToken, String refreshToken) {
      super(AccountType.MICROSOFT, name, uuid, accessToken);
      this.refreshToken = refreshToken;
   }

   public static MicroLogin create() {
      MicroLogin account = new MicroLogin("", "", "", "");
      Consumer<String> consumer = (refreshToken) -> {
         account.setRefreshToken(refreshToken);
         account.login();
      };
      MicrosoftLogin.getRefreshToken(consumer);
      return account;
   }

   public boolean login() {
      if (this.refreshToken.isEmpty()) {
         return super.login();
      } else {
         MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(this.refreshToken);
         if (!loginData.isGood()) {
            return false;
         } else {
            this.setName(loginData.username);
            this.setUuid(loginData.uuid);
            this.setAccessToken(loginData.mcToken);
            this.setRefreshToken(loginData.newRefreshToken);
            return super.login();
         }
      }
   }

   public boolean isValid() {
      return super.isValid() && !this.refreshToken.isEmpty();
   }

   public JsonObject toJson() {
      JsonObject object = super.toJson();
      object.addProperty("refreshToken", this.refreshToken);
      return object;
   }

   public void parseJson(JsonObject object) {
      super.parseJson(object);
      if (object.has("refreshToken")) {
         this.refreshToken = object.get("refreshToken").getAsString();
      }

   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
   }
}
