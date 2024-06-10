package fr.litarvan.openauth.microsoft.model.request;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class XboxLiveLoginProperties {
  private final String AuthMethod;
  
  private final String SiteName;
  
  private final String RpsTicket;
  
  private static final long a = on.a(7212678585585122401L, 3705817474190642423L, MethodHandles.lookup().lookupClass()).a(104523793242798L);
  
  public XboxLiveLoginProperties(String paramString1, String paramString2, String paramString3) {
    this.AuthMethod = paramString1;
    this.SiteName = paramString2;
    this.RpsTicket = paramString3;
    int[] arrayOfInt = XboxLoginRequest.A();
    try {
      if (d.D() != null)
        XboxLoginRequest.Y(new int[5]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public String getAuthMethod() {
    return this.AuthMethod;
  }
  
  public String getSiteName() {
    return this.SiteName;
  }
  
  public String getRpsTicket() {
    return this.RpsTicket;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\request\XboxLiveLoginProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */