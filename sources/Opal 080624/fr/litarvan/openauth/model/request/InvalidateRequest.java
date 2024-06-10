package fr.litarvan.openauth.model.request;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class InvalidateRequest {
  private String accessToken;
  
  private String clientToken;
  
  private static final long a = on.a(8249250046688966336L, 3958160295150238086L, MethodHandles.lookup().lookupClass()).a(136964070095635L);
  
  public InvalidateRequest(String paramString1, String paramString2) {
    this.accessToken = paramString1;
    int i = ValidateRequest.T();
    try {
      this.clientToken = paramString2;
      if (d.D() != null)
        ValidateRequest.I(++i); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void setAccessToken(String paramString) {
    this.accessToken = paramString;
  }
  
  public String getAccessToken() {
    return this.accessToken;
  }
  
  public void setClientToken(String paramString) {
    this.clientToken = paramString;
  }
  
  public String getClientToken() {
    return this.clientToken;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\request\InvalidateRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */