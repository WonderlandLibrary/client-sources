package fr.litarvan.openauth.microsoft.model.response;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class MicrosoftRefreshResponse {
  private final String token_type;
  
  private final long expires_in;
  
  private final String scope;
  
  private final String access_token;
  
  private final String refresh_token;
  
  private final String user_id;
  
  private static final long a = on.a(-5619214147960557895L, -2911609817236411077L, MethodHandles.lookup().lookupClass()).a(249007005692572L);
  
  public MicrosoftRefreshResponse(String paramString1, long paramLong, String paramString2, String paramString3, String paramString4, String paramString5) {
    this.token_type = paramString1;
    this.expires_in = paramLong;
    String[] arrayOfString = XboxLoginResponse$XboxLiveUserInfo.h();
    try {
      this.scope = paramString2;
      this.access_token = paramString3;
      this.refresh_token = paramString4;
      this.user_id = paramString5;
      if (arrayOfString == null)
        d.p(new d[5]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public String getTokenType() {
    return this.token_type;
  }
  
  public long getExpiresIn() {
    return this.expires_in;
  }
  
  public String getScope() {
    return this.scope;
  }
  
  public String getAccessToken() {
    return this.access_token;
  }
  
  public String getRefreshToken() {
    return this.refresh_token;
  }
  
  public String getUserId() {
    return this.user_id;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\MicrosoftRefreshResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */