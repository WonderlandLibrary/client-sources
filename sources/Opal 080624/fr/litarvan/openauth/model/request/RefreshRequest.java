package fr.litarvan.openauth.model.request;

public class RefreshRequest {
  private String accessToken;
  
  private String clientToken;
  
  public RefreshRequest(String paramString1, String paramString2) {
    this.accessToken = paramString1;
    this.clientToken = paramString2;
  }
  
  public void setAccessToken(String paramString) {
    this.accessToken = paramString;
  }
  
  public String getAccessToken() {
    return this.accessToken;
  }
  
  public void setClientToken(String paramString) {
    this.clientToken = paramString;
  }
  
  public String getClientToken() {
    return this.clientToken;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\request\RefreshRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */