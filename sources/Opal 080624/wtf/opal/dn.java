package wtf.opal;

import java.lang.invoke.MethodHandles;

public abstract class dn {
  private final String W;
  
  private final dt e;
  
  private static d[] y;
  
  private static final long a = on.a(-9104795405399989493L, 3726961514116392424L, MethodHandles.lookup().lookupClass()).a(125948228831435L);
  
  public dn(String paramString, long paramLong, dt paramdt) {
    this.W = paramString;
    d[] arrayOfD = i();
    try {
      this.e = paramdt;
      if (d.D() != null)
        X(new d[3]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public dt d(Object[] paramArrayOfObject) {
    return this.e;
  }
  
  public String Y(Object[] paramArrayOfObject) {
    return this.W;
  }
  
  public abstract boolean b(Object[] paramArrayOfObject);
  
  public static void X(d[] paramArrayOfd) {
    y = paramArrayOfd;
  }
  
  public static d[] i() {
    return y;
  }
  
  static {
    if (i() != null)
      X(new d[3]); 
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */