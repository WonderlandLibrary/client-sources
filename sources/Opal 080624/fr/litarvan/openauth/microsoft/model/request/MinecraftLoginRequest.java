package fr.litarvan.openauth.microsoft.model.request;

public class MinecraftLoginRequest {
  private final String identityToken;
  
  public MinecraftLoginRequest(String paramString) {
    this.identityToken = paramString;
  }
  
  public String getIdentityToken() {
    return this.identityToken;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\request\MinecraftLoginRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */