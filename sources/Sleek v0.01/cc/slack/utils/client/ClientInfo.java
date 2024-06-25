package cc.slack.utils.client;

public class ClientInfo {
   public final String name;
   public final String version;
   public final ClientInfo.VersionType type;

   public String getType() {
      return this.type.toString().charAt(0) + this.type.toString().substring(1).toLowerCase();
   }

   public String getName() {
      return this.name;
   }

   public String getVersion() {
      return this.version;
   }

   public ClientInfo(String name, String version, ClientInfo.VersionType type) {
      this.name = name;
      this.version = version;
      this.type = type;
   }

   public static enum VersionType {
      RELEASE,
      BETA,
      DEVELOPER,
      ALPHA;
   }
}
