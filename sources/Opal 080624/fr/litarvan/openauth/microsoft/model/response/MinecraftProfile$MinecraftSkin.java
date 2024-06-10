package fr.litarvan.openauth.microsoft.model.response;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class MinecraftProfile$MinecraftSkin {
  private final String id;
  
  private final String state;
  
  private final String url;
  
  private final String variant;
  
  private final String alias;
  
  private static final long a = on.a(68064613543552999L, 7332943431314036526L, MethodHandles.lookup().lookupClass()).a(206154024311528L);
  
  public MinecraftProfile$MinecraftSkin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
    this.id = paramString1;
    this.state = paramString2;
    this.url = paramString3;
    this.variant = paramString4;
    String[] arrayOfString = XboxLoginResponse$XboxLiveUserInfo.h();
    try {
      this.alias = paramString5;
      if (d.D() != null)
        XboxLoginResponse$XboxLiveUserInfo.b(new String[2]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public String getId() {
    return this.id;
  }
  
  public String getState() {
    return this.state;
  }
  
  public String getUrl() {
    return this.url;
  }
  
  public String getVariant() {
    return this.variant;
  }
  
  public String getAlias() {
    return this.alias;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\MinecraftProfile$MinecraftSkin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */