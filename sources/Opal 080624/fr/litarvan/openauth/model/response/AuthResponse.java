package fr.litarvan.openauth.model.response;

import fr.litarvan.openauth.model.AuthProfile;
import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class AuthResponse {
  private String accessToken;
  
  private String clientToken;
  
  private AuthProfile[] availableProfiles;
  
  private AuthProfile selectedProfile;
  
  private static final long a = on.a(-6558157919893762204L, 387250519126803768L, MethodHandles.lookup().lookupClass()).a(32597939531746L);
  
  public AuthResponse(String paramString1, String paramString2, AuthProfile[] paramArrayOfAuthProfile, AuthProfile paramAuthProfile) {
    int[] arrayOfInt = RefreshResponse.Q();
    try {
      this.accessToken = paramString1;
      this.clientToken = paramString2;
      this.availableProfiles = paramArrayOfAuthProfile;
      this.selectedProfile = paramAuthProfile;
      if (arrayOfInt != null)
        d.p(new d[1]); 
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
  
  public AuthProfile[] getAvailableProfiles() {
    return this.availableProfiles;
  }
  
  public AuthProfile getSelectedProfile() {
    return this.selectedProfile;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\response\AuthResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */