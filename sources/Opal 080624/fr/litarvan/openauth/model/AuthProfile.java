package fr.litarvan.openauth.model;

import java.lang.invoke.MethodHandles;
import wtf.opal.d;
import wtf.opal.on;
import wtf.opal.x5;

public class AuthProfile {
  private String name;
  
  private String id;
  
  private static final long a = on.a(-3037038233777562131L, 7030035086119479677L, MethodHandles.lookup().lookupClass()).a(220248105838806L);
  
  public AuthProfile() {
    this.name = "";
    this.id = "";
  }
  
  public AuthProfile(String paramString1, String paramString2) {
    int i = AuthError.G();
    try {
      this.name = paramString1;
      this.id = paramString2;
      if (d.D() != null)
        AuthError.P(++i); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getId() {
    return this.id;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\model\AuthProfile.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */