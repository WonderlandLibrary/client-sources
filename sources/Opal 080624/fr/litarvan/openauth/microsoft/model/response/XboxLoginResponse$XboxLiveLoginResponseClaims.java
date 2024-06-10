package fr.litarvan.openauth.microsoft.model.response;

public class XboxLoginResponse$XboxLiveLoginResponseClaims {
  private final XboxLoginResponse$XboxLiveUserInfo[] xui;
  
  public XboxLoginResponse$XboxLiveLoginResponseClaims(XboxLoginResponse$XboxLiveUserInfo[] paramArrayOfXboxLoginResponse$XboxLiveUserInfo) {
    this.xui = paramArrayOfXboxLoginResponse$XboxLiveUserInfo;
  }
  
  public XboxLoginResponse$XboxLiveUserInfo[] getUsers() {
    return this.xui;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\XboxLoginResponse$XboxLiveLoginResponseClaims.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */