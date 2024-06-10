package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_332;

public final class uj {
  private final class_332 n;
  
  private final float B;
  
  private static int m;
  
  private static final long a = on.a(-991231258643521093L, 1315457177075496993L, MethodHandles.lookup().lookupClass()).a(145242921844675L);
  
  public uj(class_332 paramclass_332, float paramFloat, long paramLong, int paramInt) {
    int i = N();
    try {
      this.n = paramclass_332;
      this.B = paramFloat;
      if (d.D() != null)
        n(++i); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public class_332 B(Object[] paramArrayOfObject) {
    return this.n;
  }
  
  public float N(Object[] paramArrayOfObject) {
    return this.B;
  }
  
  public static void n(int paramInt) {
    m = paramInt;
  }
  
  public static int U() {
    return m;
  }
  
  public static int N() {
    int i = U();
    try {
      if (i == 0)
        return 61; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    if (U() == 0)
      n(19); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uj.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */