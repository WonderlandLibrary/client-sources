package fr.litarvan.openauth.model.request;

import wtf.opal.x5;

public class ValidateRequest {
  private String accessToken;
  
  private static int d;
  
  public ValidateRequest(String paramString) {
    this.accessToken = paramString;
  }
  
  public void setAccessToken(String paramString) {
    this.accessToken = paramString;
  }
  
  public String getAccessToken() {
    return this.accessToken;
  }
  
  public static void I(int paramInt) {
    d = paramInt;
  }
  
  public static int T() {
    return d;
  }
  
  public static int O() {
    int i = T();
    try {
      if (i == 0)
        return 123; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  static {
    if (O() != 0)
      I(123); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\request\ValidateRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */