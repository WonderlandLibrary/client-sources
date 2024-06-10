package fr.litarvan.openauth.microsoft.model.request;

public class XSTSAuthorizationProperties {
  private final String SandboxId;
  
  private final String[] UserTokens;
  
  public XSTSAuthorizationProperties(String paramString, String[] paramArrayOfString) {
    this.SandboxId = paramString;
    this.UserTokens = paramArrayOfString;
  }
  
  public String getSandboxId() {
    return this.SandboxId;
  }
  
  public String[] getUserTokens() {
    return this.UserTokens;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\request\XSTSAuthorizationProperties.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */