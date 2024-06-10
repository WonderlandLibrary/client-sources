package fr.litarvan.openauth.model;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class AuthError {
  private String error;
  
  private String errorMessage;
  
  private String cause;
  
  private static int h;
  
  private static final long a = on.a(5108843052163978032L, -5750610908494233666L, MethodHandles.lookup().lookupClass()).a(2117431370754L);
  
  public AuthError(String paramString1, String paramString2, String paramString3) {
    this.error = paramString1;
    this.errorMessage = paramString2;
    this.cause = paramString3;
    int i = c();
    try {
      if (i == 0)
        d.p(new d[2]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public String getError() {
    return this.error;
  }
  
  public String getErrorMessage() {
    return this.errorMessage;
  }
  
  public String getCause() {
    return this.cause;
  }
  
  public static void P(int paramInt) {
    h = paramInt;
  }
  
  public static int G() {
    return h;
  }
  
  public static int c() {
    int i = G();
    try {
      if (i == 0)
        return 43; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    if (c() == 0)
      P(82); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\AuthError.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */