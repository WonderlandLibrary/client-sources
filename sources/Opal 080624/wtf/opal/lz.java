package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_243;

public final class lz extends lr {
  private class_243 V;
  
  private float C;
  
  private float s;
  
  private boolean T;
  
  private static final long a = on.a(-5273580577216534568L, -8752080610542213882L, MethodHandles.lookup().lookupClass()).a(264118887484257L);
  
  public lz(class_243 paramclass_243, int paramInt1, float paramFloat1, int paramInt2, float paramFloat2, boolean paramBoolean, int paramInt3) {
    this.V = paramclass_243;
    this.C = paramFloat1;
    this.s = paramFloat2;
    int i = b6.k();
    try {
      this.T = paramBoolean;
      if (i == 0)
        d.p(new d[4]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public class_243 P(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  public double D(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    int i = b6.r();
    try {
      if (i == 0) {
        try {
          if (this.V != null);
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return this.V.method_10216();
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0.0D;
  }
  
  public double J(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    int i = b6.k();
    try {
      if (i != 0) {
        try {
          if (this.V != null);
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return this.V.method_10214();
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0.0D;
  }
  
  public double f(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = a ^ l;
    int i = b6.k();
    try {
      if (i != 0) {
        try {
          if (this.V != null);
        } catch (x5 x5) {
          throw a(null);
        } 
      } else {
        return this.V.method_10215();
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0.0D;
  }
  
  public void d(Object[] paramArrayOfObject) {
    class_243 class_2431 = (class_243)paramArrayOfObject[0];
    this.V = class_2431;
  }
  
  public float R(Object[] paramArrayOfObject) {
    return this.C;
  }
  
  public void T(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.C = f;
  }
  
  public float G(Object[] paramArrayOfObject) {
    return this.s;
  }
  
  public void s(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.s = f;
  }
  
  public boolean z(Object[] paramArrayOfObject) {
    return this.T;
  }
  
  public void w(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.T = bool;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\lz.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */