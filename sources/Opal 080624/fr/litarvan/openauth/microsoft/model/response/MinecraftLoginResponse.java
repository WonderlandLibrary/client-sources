package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftLoginResponse {
  private final String username;
  
  private final String access_token;
  
  private final String token_type;
  
  private final long expires_in;
  
  public MinecraftLoginResponse(String paramString1, String paramString2, String paramString3, long paramLong) {
    this.username = paramString1;
    this.access_token = paramString2;
    this.token_type = paramString3;
    this.expires_in = paramLong;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public String getAccessToken() {
    return this.access_token;
  }
  
  public String getTokenType() {
    return this.token_type;
  }
  
  public long getExpiresIn() {
    return this.expires_in;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\MinecraftLoginResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */