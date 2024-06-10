package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.function.Predicate;
import net.minecraft.class_1297;
import net.minecraft.class_1675;
import net.minecraft.class_1922;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import net.minecraft.class_3726;
import net.minecraft.class_3959;
import net.minecraft.class_3965;

public final class up {
  private static final long a = on.a(7179220212249409185L, 3944124216454608100L, MethodHandles.lookup().lookupClass()).a(193548192120003L);
  
  public static class_239 Z(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    float f3 = ((Float)paramArrayOfObject[1]).floatValue();
    long l = ((Long)paramArrayOfObject[2]).longValue();
    boolean bool = ((Boolean)paramArrayOfObject[3]).booleanValue();
    float f1 = ((Float)paramArrayOfObject[4]).floatValue();
    float f2 = ((Float)paramArrayOfObject[5]).floatValue();
    l = a ^ l;
    class_243 class_2431 = J(new Object[] { null, b9.c.field_1724, Float.valueOf(f3) });
    class_243 class_2432 = ln.P(new Object[] { null, Float.valueOf(f1), Float.valueOf(f2) });
    class_243 class_2433 = class_2431.method_1031(class_2432.field_1352 * d, class_2432.field_1351 * d, class_2432.field_1350 * d);
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    return (class_239)b9.c.field_1687.method_17742(new class_3959(class_2431, class_2433, class_3959.class_3960.field_17559, bool ? class_3959.class_242.field_1347 : class_3959.class_242.field_1348, (class_1297)b9.c.field_1724));
  }
  
  public static class_3965 W(Object[] paramArrayOfObject) {
    double d = ((Double)paramArrayOfObject[0]).doubleValue();
    float f1 = ((Float)paramArrayOfObject[1]).floatValue();
    float f2 = ((Float)paramArrayOfObject[2]).floatValue();
    class_2338 class_2338 = (class_2338)paramArrayOfObject[3];
    class_2680 class_2680 = (class_2680)paramArrayOfObject[4];
    class_243 class_2431 = b9.c.field_1724.method_33571();
    class_243 class_2432 = ln.P(new Object[] { null, Float.valueOf(f1), Float.valueOf(f2) });
    class_243 class_2433 = class_2431.method_1031(class_2432.field_1352 * d, class_2432.field_1351 * d, class_2432.method_10215() * d);
    return b9.c.field_1687.method_17745(class_2431, class_2433, class_2338, class_2680.method_26172((class_1922)b9.c.field_1687, class_2338, class_3726.method_16195((class_1297)b9.c.field_1724)), class_2680);
  }
  
  public static class_239 t(Object[] paramArrayOfObject) {
    class_1297 class_1297 = (class_1297)paramArrayOfObject[0];
    double d = ((Double)paramArrayOfObject[1]).doubleValue();
    float f2 = ((Float)paramArrayOfObject[2]).floatValue();
    float f1 = ((Float)paramArrayOfObject[3]).floatValue();
    float f3 = ((Float)paramArrayOfObject[4]).floatValue();
    Predicate predicate = (Predicate)paramArrayOfObject[5];
    class_243 class_2431 = J(new Object[] { null, b9.c.field_1724, Float.valueOf(f2) });
    class_243 class_2432 = ln.P(new Object[] { null, Float.valueOf(f1), Float.valueOf(f3) });
    class_243 class_2433 = class_2431.method_1031(class_2432.field_1352 * d, class_2432.field_1351 * d, class_2432.field_1350 * d);
    class_238 class_238 = b9.c.field_1724.method_5829().method_18804(class_2432.method_1021(d)).method_1009(1.0D, 1.0D, 1.0D);
    return (class_239)class_1675.method_18075((class_1297)b9.c.field_1724, class_2431, class_2433, class_238, predicate, d * d);
  }
  
  public static class_243 J(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    class_1297 class_1297 = (class_1297)paramArrayOfObject[1];
    double d1 = class_3532.method_16436(f, class_1297.field_6014, class_1297.method_23317());
    double d2 = class_3532.method_16436(f, class_1297.field_6036, class_1297.method_23318()) + class_1297.method_5751();
    double d3 = class_3532.method_16436(f, class_1297.field_5969, class_1297.method_23321());
    return new class_243(d1, d2, d3);
  }
  
  public static boolean b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_2
    //   21: pop
    //   22: getstatic wtf/opal/up.a : J
    //   25: lload_2
    //   26: lxor
    //   27: lstore_2
    //   28: invokestatic B : ()[Ljava/lang/String;
    //   31: iconst_0
    //   32: istore #5
    //   34: astore #4
    //   36: iload #5
    //   38: iload_1
    //   39: if_icmpge -> 123
    //   42: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   45: getfield field_1687 : Lnet/minecraft/class_638;
    //   48: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   51: getfield field_1724 : Lnet/minecraft/class_746;
    //   54: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   57: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   60: getfield field_1724 : Lnet/minecraft/class_746;
    //   63: invokevirtual method_23318 : ()D
    //   66: iload #5
    //   68: i2d
    //   69: dsub
    //   70: d2i
    //   71: invokevirtual method_33096 : (I)Lnet/minecraft/class_2338;
    //   74: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   77: invokevirtual method_26215 : ()Z
    //   80: aload #4
    //   82: lload_2
    //   83: lconst_0
    //   84: lcmp
    //   85: ifle -> 93
    //   88: ifnull -> 130
    //   91: aload #4
    //   93: ifnull -> 114
    //   96: goto -> 103
    //   99: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: ifne -> 115
    //   106: goto -> 113
    //   109: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   112: athrow
    //   113: iconst_1
    //   114: ireturn
    //   115: iinc #5, 1
    //   118: aload #4
    //   120: ifnonnull -> 36
    //   123: lload_2
    //   124: lconst_0
    //   125: lcmp
    //   126: ifle -> 42
    //   129: iconst_0
    //   130: ireturn
    // Exception table:
    //   from	to	target	type
    //   42	96	99	wtf/opal/x5
    //   91	106	109	wtf/opal/x5
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\up.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */