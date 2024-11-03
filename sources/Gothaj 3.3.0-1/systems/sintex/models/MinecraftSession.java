package systems.sintex.models;

public class MinecraftSession {
   private String name;
   private String uuid;
   private String accessToken;
   private String refreshToken;

   @Override
   public String toString() {
      return "MinecraftSession{name='" + this.name + '\'' + ", uuid='" + this.uuid + '\'' + ", accessToken='" + this.accessToken + '\'' + '}';
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

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public MinecraftSession(String name, String uuid, String accessToken, String refreshToken) {
      this.name = name;
      this.uuid = uuid;
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
   }
}
