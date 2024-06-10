package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_310;
import net.minecraft.class_6367;

public final class xv {
  private static final long a = on.a(5022676824817190592L, -4154977772014803345L, MethodHandles.lookup().lookupClass()).a(58825563060212L);
  
  private static boolean x(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast net/minecraft/class_6367
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/xv.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: invokestatic u : ()Z
    //   28: istore #4
    //   30: aload_3
    //   31: iload #4
    //   33: ifne -> 47
    //   36: ifnull -> 115
    //   39: goto -> 46
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_3
    //   47: getfield field_1480 : I
    //   50: iload #4
    //   52: lload_1
    //   53: lconst_0
    //   54: lcmp
    //   55: ifle -> 70
    //   58: ifne -> 123
    //   61: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   64: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   67: invokevirtual method_4480 : ()I
    //   70: if_icmpne -> 115
    //   73: goto -> 80
    //   76: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   79: athrow
    //   80: aload_3
    //   81: getfield field_1477 : I
    //   84: iload #4
    //   86: ifne -> 123
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   99: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   102: invokevirtual method_4507 : ()I
    //   105: if_icmpeq -> 126
    //   108: goto -> 115
    //   111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: iconst_1
    //   116: goto -> 123
    //   119: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   122: athrow
    //   123: goto -> 127
    //   126: iconst_0
    //   127: ireturn
    // Exception table:
    //   from	to	target	type
    //   30	39	42	wtf/opal/x5
    //   47	73	76	wtf/opal/x5
    //   61	89	92	wtf/opal/x5
    //   80	108	111	wtf/opal/x5
    //   96	116	119	wtf/opal/x5
  }
  
  public static class_6367 t(Object[] paramArrayOfObject) {
    class_6367 class_6367 = (class_6367)paramArrayOfObject[0];
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    boolean bool1 = ((Boolean)paramArrayOfObject[2]).booleanValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x4F49C6BCF750L;
    boolean bool2 = lt.h();
    try {
      if (bool2)
        try {
          (new Object[2])[1] = class_6367;
          new Object[2];
          if (!x(new Object[] { Long.valueOf(l2) }))
            return class_6367; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (bool2)
        try {
          if (class_6367 != null)
            class_6367.method_1238(); 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return new class_6367(b9.c.method_22683().method_4480(), b9.c.method_22683().method_4507(), bool1, class_310.field_1703);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */