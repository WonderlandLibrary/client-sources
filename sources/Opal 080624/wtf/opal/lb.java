package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_2596;

public final class lb extends lr {
  private final class_2596<?> o;
  
  private static int g;
  
  private static final long a = on.a(-7662350082673595551L, 3922651960768868580L, MethodHandles.lookup().lookupClass()).a(55705883696983L);
  
  public lb(class_2596<?> paramclass_2596, long paramLong) {
    this.o = paramclass_2596;
    int i = I();
    try {
      if (d.D() != null)
        L(++i); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public class_2596 J(Object[] paramArrayOfObject) {
    return this.o;
  }
  
  public static void L(int paramInt) {
    g = paramInt;
  }
  
  public static int I() {
    return g;
  }
  
  public static int J() {
    int i = I();
    try {
      if (i == 0)
        return 65; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    if (J() != 0)
      L(70); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */