package fr.litarvan.openauth;

import fr.litarvan.openauth.model.AuthError;
import wtf.opal.x5;

public class AuthenticationException extends Exception {
  private AuthError model;
  
  private static boolean p;
  
  public AuthenticationException(AuthError paramAuthError) {
    super(paramAuthError.getErrorMessage());
    this.model = paramAuthError;
  }
  
  public AuthError getErrorModel() {
    return this.model;
  }
  
  public static void M(boolean paramBoolean) {
    p = paramBoolean;
  }
  
  public static boolean S() {
    return p;
  }
  
  public static boolean P() {
    boolean bool = S();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  static {
    if (S())
      M(true); 
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\AuthenticationException.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */