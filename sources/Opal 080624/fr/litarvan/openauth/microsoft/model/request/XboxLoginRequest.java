package fr.litarvan.openauth.microsoft.model.request;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class XboxLoginRequest<T> {
  private final T Properties;
  
  private final String RelyingParty;
  
  private final String TokenType;
  
  private static int[] Y;
  
  private static final long a = on.a(-6914305523420322849L, -1093478676033152483L, MethodHandles.lookup().lookupClass()).a(143602917745297L);
  
  public XboxLoginRequest(T paramT, String paramString1, String paramString2) {
    int[] arrayOfInt = A();
    try {
      this.Properties = paramT;
      this.RelyingParty = paramString1;
      this.TokenType = paramString2;
      if (arrayOfInt != null)
        d.p(new d[5]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public T getProperties() {
    return this.Properties;
  }
  
  public String getSiteName() {
    return this.RelyingParty;
  }
  
  public String getTokenType() {
    return this.TokenType;
  }
  
  public static void Y(int[] paramArrayOfint) {
    Y = paramArrayOfint;
  }
  
  public static int[] A() {
    return Y;
  }
  
  static {
    if (A() != null)
      Y(new int[1]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\model\request\XboxLoginRequest.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */