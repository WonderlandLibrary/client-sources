package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_243;

public final class lv {
  private boolean t;
  
  private boolean V;
  
  private float w;
  
  private float J;
  
  private int S;
  
  private int a;
  
  private int B;
  
  private int c;
  
  private int g;
  
  private int f;
  
  private class_243 M;
  
  private static int[] q;
  
  private static final long b = on.a(2626478574501229234L, 2815493723292245784L, MethodHandles.lookup().lookupClass()).a(6654522160336L);
  
  public lv(short paramShort1, short paramShort2, int paramInt) {
    int[] arrayOfInt = U();
    try {
      this.M = new class_243(0.0D, 0.0D, 0.0D);
      if (arrayOfInt != null)
        d.p(new d[5]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void e(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.g = i;
  }
  
  public class_243 F(Object[] paramArrayOfObject) {
    return this.M;
  }
  
  public void z(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.f = i;
  }
  
  public int F(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public int V(Object[] paramArrayOfObject) {
    return this.g;
  }
  
  public void S(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.c = i;
  }
  
  public int b(Object[] paramArrayOfObject) {
    return this.c;
  }
  
  public void I(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.B = i;
  }
  
  public int T(Object[] paramArrayOfObject) {
    return this.B;
  }
  
  public void X(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.a = i;
  }
  
  public int B(Object[] paramArrayOfObject) {
    return this.a;
  }
  
  public void r(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.S = i;
  }
  
  public int a(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  public void R(Object[] paramArrayOfObject) {
    class_243 class_2431 = (class_243)paramArrayOfObject[0];
    this.M = class_2431;
  }
  
  public void w(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.t = bool;
  }
  
  public void n(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.V = bool;
  }
  
  public boolean C(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  public void h(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.w = f;
  }
  
  public float a(Object[] paramArrayOfObject) {
    return this.w;
  }
  
  public float U(Object[] paramArrayOfObject) {
    return this.J;
  }
  
  public void v(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.J = f;
  }
  
  public boolean u(Object[] paramArrayOfObject) {
    return this.t;
  }
  
  public static void H(int[] paramArrayOfint) {
    q = paramArrayOfint;
  }
  
  public static int[] U() {
    return q;
  }
  
  static {
    if (U() != null)
      H(new int[1]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */