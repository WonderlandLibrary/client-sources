package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class ps extends u_<jj> {
  private final gm<ux> p;
  
  private static boolean e;
  
  private static final long a = on.a(-6310581530892063909L, -5263307137491138135L, MethodHandles.lookup().lookupClass()).a(14327742311171L);
  
  public ps(jj paramjj, long paramLong) {
    super(paramjj);
    boolean bool = r();
    try {
      this.p = ps::lambda$new$0;
      if (!bool)
        d.p(new d[2]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return k.MOTION;
  }
  
  private static void lambda$new$0(ux paramux) {
    paramux.m(new Object[] { Float.valueOf(1.0F) });
  }
  
  public static void T(boolean paramBoolean) {
    e = paramBoolean;
  }
  
  public static boolean f() {
    return e;
  }
  
  public static boolean r() {
    boolean bool = f();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  static {
    if (f())
      T(true); 
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ps.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */