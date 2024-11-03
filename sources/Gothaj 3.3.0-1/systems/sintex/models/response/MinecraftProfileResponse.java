package systems.sintex.models.response;

public class MinecraftProfileResponse {
   private String uuid;
   private String name;

   public String getUuid() {
      return this.uuid;
   }

   public String getName() {
      return this.name;
   }

   public MinecraftProfileResponse(String uuid, String name) {
      this.uuid = uuid;
      this.name = name;
   }
}
