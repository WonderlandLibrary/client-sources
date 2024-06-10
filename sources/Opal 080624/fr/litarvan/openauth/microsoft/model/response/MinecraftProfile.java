package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftProfile {
  private final String id;
  
  private final String name;
  
  private final MinecraftProfile$MinecraftSkin[] skins;
  
  public MinecraftProfile(String paramString1, String paramString2, MinecraftProfile$MinecraftSkin[] paramArrayOfMinecraftProfile$MinecraftSkin) {
    this.id = paramString1;
    this.name = paramString2;
    this.skins = paramArrayOfMinecraftProfile$MinecraftSkin;
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getName() {
    return this.name;
  }
  
  public MinecraftProfile$MinecraftSkin[] getSkins() {
    return this.skins;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\MinecraftProfile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */