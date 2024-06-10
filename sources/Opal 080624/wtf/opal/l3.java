package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_243;

public final class l3 {
  private class_243 h;
  
  private class_243 j;
  
  private float n;
  
  private float S;
  
  private static final long a = on.a(-3322877357744335025L, 299678970956781119L, MethodHandles.lookup().lookupClass()).a(132598962563613L);
  
  public l3(class_243 paramclass_2431, long paramLong, float paramFloat1, float paramFloat2, class_243 paramclass_2432) {
    this.h = paramclass_2431;
    this.n = paramFloat1;
    int i = b6.r();
    try {
      this.S = paramFloat2;
      this.j = paramclass_2432;
      if (d.D() != null)
        b6.J(++i); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public class_243 T(Object[] paramArrayOfObject) {
    return this.h;
  }
  
  public class_243 M(Object[] paramArrayOfObject) {
    return this.j;
  }
  
  public float o(Object[] paramArrayOfObject) {
    return this.n;
  }
  
  public float Z(Object[] paramArrayOfObject) {
    return this.S;
  }
  
  public void Z(Object[] paramArrayOfObject) {
    class_243 class_2431 = (class_243)paramArrayOfObject[0];
    this.h = class_2431;
  }
  
  public void B(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.n = f;
  }
  
  public void v(Object[] paramArrayOfObject) {
    class_243 class_2431 = (class_243)paramArrayOfObject[0];
    this.j = class_2431;
  }
  
  public void a(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.S = f;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */