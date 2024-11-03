package vestige.util.accounts;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Account {
   private AccountType type;
   private String name;
   private String uuid;
   private String accessToken;
   private long lastUsed;

   public Account(AccountType type, String name, String uuid, String accessToken) {
      this.type = type;
      this.name = name;
      this.uuid = uuid;
      this.accessToken = accessToken;
   }

   public boolean login() {
      Minecraft mc = Minecraft.getMinecraft();
      mc.setSession(new Session(this.name, this.uuid, this.accessToken, "mojang"));
      this.lastUsed = System.currentTimeMillis();
      return true;
   }

   public boolean isValid() {
      return this.name != null && this.uuid != null && this.accessToken != null && !this.name.isEmpty() && !this.uuid.isEmpty() && !this.accessToken.isEmpty();
   }

   public JsonObject toJson() {
      JsonObject object = new JsonObject();
      object.addProperty("type", this.type.getDeclaringClass().getName());
      object.addProperty("name", this.name);
      object.addProperty("uuid", this.uuid);
      object.addProperty("accessToken", this.accessToken);
      object.addProperty("lastUsed", this.lastUsed);
      return object;
   }

   public void parseJson(JsonObject object) {
      if (object.has("type")) {
         this.type = AccountType.getByName(object.get("type").getAsString());
      } else {
         this.type = AccountType.CRACKED;
      }

      if (object.has("name")) {
         this.name = object.get("name").getAsString();
      }

      if (object.has("uuid")) {
         this.uuid = object.get("uuid").getAsString();
      }

      if (object.has("accessToken")) {
         this.accessToken = object.get("accessToken").getAsString();
      }

      if (object.has("lastUsed")) {
         this.lastUsed = object.get("lastUsed").getAsLong();
      }

   }

   public AccountType getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public String getUuid() {
      return this.uuid;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public long getLastUsed() {
      return this.lastUsed;
   }

   public void setType(AccountType type) {
      this.type = type;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }

   public void setLastUsed(long lastUsed) {
      this.lastUsed = lastUsed;
   }
}
