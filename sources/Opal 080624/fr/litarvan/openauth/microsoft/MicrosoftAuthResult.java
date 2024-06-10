package fr.litarvan.openauth.microsoft;

import fr.litarvan.openauth.microsoft.model.response.MinecraftProfile;

public class MicrosoftAuthResult {
  private final MinecraftProfile profile;
  
  private final String accessToken;
  
  private final String refreshToken;
  
  public MicrosoftAuthResult(MinecraftProfile paramMinecraftProfile, String paramString1, String paramString2) {
    this.profile = paramMinecraftProfile;
    this.accessToken = paramString1;
    this.refreshToken = paramString2;
  }
  
  public MicrosoftAuthResult(MinecraftProfile paramMinecraftProfile, String paramString) {
    this.profile = paramMinecraftProfile;
    this.accessToken = paramString;
    this.refreshToken = null;
  }
  
  public MinecraftProfile getProfile() {
    return this.profile;
  }
  
  public String getAccessToken() {
    return this.accessToken;
  }
  
  public String getRefreshToken() {
    return this.refreshToken;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\MicrosoftAuthResult.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */