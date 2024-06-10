package wtf.opal;

import java.lang.invoke.MethodHandles;

public abstract class u_<T extends d> {
  protected T G;
  
  private static boolean l;
  
  private static final long v = on.a(1471161702344155110L, 6293277085128256875L, MethodHandles.lookup().lookupClass()).a(213506350533152L);
  
  protected u_(T paramT) {
    this.G = paramT;
  }
  
  public d y(Object[] paramArrayOfObject) {
    return (d)this.G;
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x204EF9D31E48L;
    try {
      if (this.G.V(new Object[0]) == this) {
        (new Object[2])[1] = this;
        new Object[2];
        d1.q(new Object[0]).q(new Object[0]).P(new Object[] { Long.valueOf(l2) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void X() {
    long l = v ^ 0x6DA66374E188L;
    try {
      if (this.G.V(new Object[0]) == this)
        d1.q(new Object[0]).q(new Object[0]).z(new Object[] { this }); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public abstract Enum V(Object[] paramArrayOfObject);
  
  public static void K(boolean paramBoolean) {
    l = paramBoolean;
  }
  
  public static boolean Q() {
    return l;
  }
  
  public static boolean w() {
    boolean bool = Q();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    if (w())
      K(true); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u_.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */