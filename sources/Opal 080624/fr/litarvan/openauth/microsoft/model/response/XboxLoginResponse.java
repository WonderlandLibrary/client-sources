package fr.litarvan.openauth.microsoft.model.response;

public class XboxLoginResponse {
  private final String IssueInstant;
  
  private final String NotAfter;
  
  private final String Token;
  
  private final XboxLoginResponse$XboxLiveLoginResponseClaims DisplayClaims;
  
  public XboxLoginResponse(String paramString1, String paramString2, String paramString3, XboxLoginResponse$XboxLiveLoginResponseClaims paramXboxLoginResponse$XboxLiveLoginResponseClaims) {
    this.IssueInstant = paramString1;
    this.NotAfter = paramString2;
    this.Token = paramString3;
    this.DisplayClaims = paramXboxLoginResponse$XboxLiveLoginResponseClaims;
  }
  
  public String getIssueInstant() {
    return this.IssueInstant;
  }
  
  public String getNotAfter() {
    return this.NotAfter;
  }
  
  public String getToken() {
    return this.Token;
  }
  
  public XboxLoginResponse$XboxLiveLoginResponseClaims getDisplayClaims() {
    return this.DisplayClaims;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\response\XboxLoginResponse.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */