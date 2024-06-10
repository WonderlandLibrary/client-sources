package wtf.opal;

import java.lang.invoke.MethodHandles;

public abstract class b5<T extends k3<?>> implements kx {
  private final T A;
  
  protected float U;
  
  protected float t;
  
  protected float e;
  
  protected float m;
  
  protected float F;
  
  private static boolean S;
  
  private static final long a = on.a(-989812145178756226L, 8380751026402831365L, MethodHandles.lookup().lookupClass()).a(84551264216296L);
  
  public b5(k3 paramk3, long paramLong) {
    this.A = (T)paramk3;
    boolean bool = I();
    try {
      if (bool)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public final k3 h(Object[] paramArrayOfObject) {
    return (k3)this.A;
  }
  
  public final void n(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.U = f;
  }
  
  public final void Z(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.t = f;
  }
  
  public final void S(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.e = f;
  }
  
  public final void t(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.m = f;
  }
  
  public final void q(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.F = f;
  }
  
  public final float U(Object[] paramArrayOfObject) {
    return this.F;
  }
  
  public static void D(boolean paramBoolean) {
    S = paramBoolean;
  }
  
  public static boolean e() {
    return S;
  }
  
  public static boolean I() {
    boolean bool = e();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  static {
    if (I())
      D(true); 
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b5.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */