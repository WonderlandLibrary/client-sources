package fr.litarvan.openauth.model.request;

public class SignoutRequest {
  private String username;
  
  private String password;
  
  public SignoutRequest(String paramString1, String paramString2) {
    this.username = paramString1;
    this.password = paramString2;
  }
  
  public void setUsername(String paramString) {
    this.username = paramString;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public void setPassword(String paramString) {
    this.password = paramString;
  }
  
  public String getPassword() {
    return this.password;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\request\SignoutRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */