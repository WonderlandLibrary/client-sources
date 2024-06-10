package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_1297;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_241;
import net.minecraft.class_243;
import net.minecraft.class_3532;

public final class ln {
  private static final long a = on.a(-6633834228843153838L, -3008018592825638553L, MethodHandles.lookup().lookupClass()).a(40590354688372L);
  
  public static class_243 P(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    float f3 = f2 * 0.017453292F;
    float f4 = -f1 * 0.017453292F;
    float f5 = class_3532.method_15362(f4);
    float f6 = class_3532.method_15374(f4);
    float f7 = class_3532.method_15362(f3);
    float f8 = class_3532.method_15374(f3);
    return new class_243((f6 * f7), -f8, (f5 * f7));
  }
  
  public static class_241 v(Object[] paramArrayOfObject) {
    class_241 class_2411 = (class_241)paramArrayOfObject[0];
    class_241 class_2412 = (class_241)paramArrayOfObject[1];
    double d1 = ((Double)b9.c.field_1690.method_42495().method_41753()).doubleValue() * 0.6000000238418579D + 0.20000000298023224D;
    double d2 = d1 * d1 * d1;
    double d3 = d2 * 8.0D * 0.15D;
    float f1 = class_2412.field_1343 + (float)(Math.round((class_2411.field_1343 - class_2412.field_1343) / d3) * d3);
    float f2 = class_2412.field_1342 + (float)(Math.round((class_2411.field_1342 - class_2412.field_1342) / d3) * d3);
    return new class_241(f1, class_3532.method_15363(f2, -90.0F, 90.0F));
  }
  
  public static class_241 Q(Object[] paramArrayOfObject) {
    class_241 class_2411 = (class_241)paramArrayOfObject[0];
    class_241 class_2412 = (class_241)paramArrayOfObject[1];
    float f1 = ((Float)paramArrayOfObject[2]).floatValue();
    float f2 = ((Float)paramArrayOfObject[3]).floatValue();
    return new class_241(v(new Object[] { null, null, Float.valueOf(f1), Float.valueOf(class_2412.field_1343), Float.valueOf(class_2411.field_1343) }, ), M(new Object[] { null, null, Float.valueOf(f2), Float.valueOf(class_2412.field_1342), Float.valueOf(class_2411.field_1342) }));
  }
  
  private static float v(Object[] paramArrayOfObject) {
    float f2 = ((Float)paramArrayOfObject[0]).floatValue();
    float f3 = ((Float)paramArrayOfObject[1]).floatValue();
    float f1 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = f2 - f3;
    return f3 + f4 * f1;
  }
  
  private static float M(Object[] paramArrayOfObject) {
    float f3 = ((Float)paramArrayOfObject[0]).floatValue();
    float f2 = ((Float)paramArrayOfObject[1]).floatValue();
    float f1 = ((Float)paramArrayOfObject[2]).floatValue();
    float f4 = f3 - f2;
    return f2 + f4 * f1;
  }
  
  public static double[] V(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    double d = ((Double)paramArrayOfObject[1]).doubleValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x711CCFECB6BCL;
    try {
      new Object[1];
    } catch (x5 x5) {
      throw a(null);
    } 
    new Object[1];
    new Object[2];
    return P(new Object[] { null, Double.valueOf(d), Float.valueOf((l_.I(new Object[] { Long.valueOf(l2) }) ? l_.T(new Object[0]) : b9.c.field_1724.method_36454()) * d6.R(new Object[] { Double.valueOf(0.017453292519943295D) })) });
  }
  
  public static double[] P(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    double d = ((Double)paramArrayOfObject[1]).doubleValue();
    return new double[] { -class_3532.method_15374(f) * d, class_3532.method_15362(f) * d };
  }
  
  public static class_241 x(Object[] paramArrayOfObject) {
    class_1297 class_1297 = (class_1297)paramArrayOfObject[0];
    double d1 = class_1297.method_23317() - b9.c.field_1724.method_23317();
    double d2 = class_1297.method_23318() + class_1297.method_18381(class_1297.method_18376()) - b9.c.field_1724.method_23318() + b9.c.field_1724.method_18381(b9.c.field_1724.method_18376());
    double d3 = class_1297.method_23321() - b9.c.field_1724.method_23321();
    double d4 = Math.sqrt(d1 * d1 + d3 * d3);
    float f1 = (float)Math.toDegrees(-Math.atan2(d1, d3));
    float f2 = (float)-Math.toDegrees(Math.atan(d2 / d4));
    return new class_241(f1, f2);
  }
  
  public static class_241 p(Object[] paramArrayOfObject) {
    class_243 class_243 = (class_243)paramArrayOfObject[0];
    double d1 = class_243.method_10216() - b9.c.field_1724.method_23317();
    double d2 = class_243.method_10214() + b9.c.field_1724.method_18381(b9.c.field_1724.method_18376()) - b9.c.field_1724.method_23318() + b9.c.field_1724.method_18381(b9.c.field_1724.method_18376());
    double d3 = class_243.method_10215() - b9.c.field_1724.method_23321();
    double d4 = Math.sqrt(d1 * d1 + d3 * d3);
    float f1 = (float)Math.toDegrees(-Math.atan2(d1, d3));
    float f2 = (float)-Math.toDegrees(Math.atan(d2 / d4));
    return new class_241(f1, f2);
  }
  
  public static class_241 V(Object[] paramArrayOfObject) {
    class_2338 class_2338 = (class_2338)paramArrayOfObject[0];
    class_2350 class_2350 = (class_2350)paramArrayOfObject[1];
    float f1 = (float)(class_2338.method_10263() + 0.5D - b9.c.field_1724.method_23317() + class_2350.method_10148() * 0.25D);
    float f2 = (float)(b9.c.field_1724.method_23318() + b9.c.field_1724.method_18381(b9.c.field_1724.method_18376()) - class_2338.method_10264() - class_2350.method_10164() * 0.25D);
    float f3 = (float)(class_2338.method_10260() + 0.5D - b9.c.field_1724.method_23321() + class_2350.method_10165() * 0.25D);
    double d = class_3532.method_15355(f1 * f1 + f3 * f3);
    float f4 = (float)Math.toDegrees(-Math.atan2(f1, f3));
    float f5 = (float)Math.toDegrees(Math.atan(f2 / d));
    return new class_241(f4, f5);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ln.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */