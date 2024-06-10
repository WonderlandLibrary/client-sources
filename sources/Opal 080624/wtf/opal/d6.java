package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.class_243;

public final class d6 {
  private static final long a = on.a(2491207604650159091L, -5643983648110720730L, MethodHandles.lookup().lookupClass()).a(102848939317409L);
  
  public static double M(Object[] paramArrayOfObject) {
    null = ((Double)paramArrayOfObject[0]).doubleValue();
    double d1 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[2]).doubleValue();
    null = Math.max(d1, null);
    return Math.min(d2, null);
  }
  
  public static double g(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    class_243 class_2431 = (class_243)paramArrayOfObject[1];
    class_243 class_2432 = (class_243)paramArrayOfObject[2];
    l = a ^ l;
    double d1 = class_2431.method_10216() - class_2432.method_10216();
    double d2 = class_2431.method_10214() - class_2432.method_10214();
    String str = kr.F();
    double d3 = class_2431.method_10215() - class_2432.method_10215();
    try {
      if (d.D() != null)
        kr.R("TPKoz"); 
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    return d1 * d1 + d2 * d2 + d3 * d3;
  }
  
  public static Double Y(Object[] paramArrayOfObject) {
    double d1 = ((Double)paramArrayOfObject[0]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d3 = ((Double)paramArrayOfObject[2]).doubleValue();
    return Double.valueOf(d1 + (d2 - d1) * d3);
  }
  
  public static int L(Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    double d = ((Double)paramArrayOfObject[2]).doubleValue();
    new Object[3];
    (new Object[3])[2] = Double.valueOf((float)d);
    new Object[3];
    (new Object[3])[1] = Double.valueOf(i);
    new Object[3];
    return Y(new Object[] { Double.valueOf(j) }).intValue();
  }
  
  public static float R(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    return (float)(Math.round(d * 1.0E8D) / 1.0E8D);
  }
  
  public static int r(Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    return (int)(Math.random() * (i - j) + j);
  }
  
  public static double P(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    double d = ((Double)paramArrayOfObject[1]).doubleValue();
    int i = ((Integer)paramArrayOfObject[2]).intValue();
    l = a ^ l;
    try {
      if (i < 0)
        throw new IllegalArgumentException(); 
    } catch (IllegalArgumentException illegalArgumentException) {
      throw a(null);
    } 
    BigDecimal bigDecimal = new BigDecimal(d);
    bigDecimal = bigDecimal.setScale(i, RoundingMode.HALF_UP);
    return bigDecimal.doubleValue();
  }
  
  private static IllegalArgumentException a(IllegalArgumentException paramIllegalArgumentException) {
    return paramIllegalArgumentException;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d6.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */