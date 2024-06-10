package fr.litarvan.openauth.model.request;

import fr.litarvan.openauth.model.AuthAgent;
import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class AuthRequest {
  private AuthAgent agent;
  
  private String username;
  
  private String password;
  
  private String clientToken;
  
  private static final long a = on.a(-1757384255835843913L, 480802516834035410L, MethodHandles.lookup().lookupClass()).a(248488413185908L);
  
  public AuthRequest(AuthAgent paramAuthAgent, String paramString1, String paramString2, String paramString3) {
    this.agent = paramAuthAgent;
    this.username = paramString1;
    this.password = paramString2;
    this.clientToken = paramString3;
    int i = ValidateRequest.O();
    try {
      if (i != 0)
        d.p(new d[5]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void setAgent(AuthAgent paramAuthAgent) {
    this.agent = paramAuthAgent;
  }
  
  public AuthAgent getAgent() {
    return this.agent;
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
  
  public void setClientToken(String paramString) {
    this.clientToken = paramString;
  }
  
  public String getClientToken() {
    return this.clientToken;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\request\AuthRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */