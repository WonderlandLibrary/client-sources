package fr.litarvan.openauth.model.response;

import fr.litarvan.openauth.model.AuthProfile;
import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class RefreshResponse {
  private String accessToken;
  
  private String clientToken;
  
  private AuthProfile selectedProfile;
  
  private static int[] S;
  
  private static final long a = on.a(-4312260334327194847L, -5589896353804222381L, MethodHandles.lookup().lookupClass()).a(235363051479459L);
  
  public RefreshResponse(String paramString1, String paramString2, AuthProfile paramAuthProfile) {
    this.accessToken = paramString1;
    this.clientToken = paramString2;
    this.selectedProfile = paramAuthProfile;
    int[] arrayOfInt = Q();
    try {
      if (d.D() != null)
        y(new int[4]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public String getAccessToken() {
    return this.accessToken;
  }
  
  public String getClientToken() {
    return this.clientToken;
  }
  
  public AuthProfile getSelectedProfile() {
    return this.selectedProfile;
  }
  
  public static void y(int[] paramArrayOfint) {
    S = paramArrayOfint;
  }
  
  public static int[] Q() {
    return S;
  }
  
  static {
    if (Q() != null)
      y(new int[5]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\response\RefreshResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */