package fr.litarvan.openauth.microsoft;

public class AuthTokens {
  private final String accessToken;
  
  private final String refreshToken;
  
  public AuthTokens(String paramString1, String paramString2) {
    this.accessToken = paramString1;
    this.refreshToken = paramString2;
  }
  
  public String getAccessToken() {
    return this.accessToken;
  }
  
  public String getRefreshToken() {
    return this.refreshToken;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\AuthTokens.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */