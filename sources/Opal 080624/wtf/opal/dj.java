package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_304;
import net.minecraft.class_3675;
import org.lwjgl.glfw.GLFW;

public final class dj {
  private static final long a = on.a(-7784365280637778064L, -3062855392307954630L, MethodHandles.lookup().lookupClass()).a(281080993752217L);
  
  public static boolean W(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast net/minecraft/class_1309
    //   17: astore_1
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast net/minecraft/class_1309
    //   24: astore #4
    //   26: pop
    //   27: getstatic wtf/opal/dj.a : J
    //   30: lload_2
    //   31: lxor
    //   32: lstore_2
    //   33: invokestatic B : ()[Ljava/lang/String;
    //   36: astore #5
    //   38: aload_1
    //   39: invokevirtual method_5476 : ()Lnet/minecraft/class_2561;
    //   42: aload #5
    //   44: ifnull -> 81
    //   47: ifnull -> 84
    //   50: goto -> 57
    //   53: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   56: athrow
    //   57: aload #4
    //   59: aload #5
    //   61: ifnull -> 91
    //   64: goto -> 71
    //   67: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   70: athrow
    //   71: invokevirtual method_5476 : ()Lnet/minecraft/class_2561;
    //   74: goto -> 81
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: ifnonnull -> 90
    //   84: iconst_0
    //   85: ireturn
    //   86: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: aload_1
    //   91: invokevirtual method_22861 : ()I
    //   94: istore #6
    //   96: aload #4
    //   98: invokevirtual method_22861 : ()I
    //   101: istore #7
    //   103: iload #6
    //   105: aload #5
    //   107: ifnull -> 130
    //   110: iload #7
    //   112: if_icmpne -> 133
    //   115: goto -> 122
    //   118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   121: athrow
    //   122: iconst_1
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: goto -> 134
    //   133: iconst_0
    //   134: ireturn
    // Exception table:
    //   from	to	target	type
    //   38	50	53	wtf/opal/x5
    //   47	64	67	wtf/opal/x5
    //   57	74	77	wtf/opal/x5
    //   81	86	86	wtf/opal/x5
    //   103	115	118	wtf/opal/x5
    //   110	123	126	wtf/opal/x5
  }
  
  public static int r(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_1
    //   21: pop
    //   22: getstatic wtf/opal/dj.a : J
    //   25: lload_2
    //   26: lxor
    //   27: lstore_2
    //   28: invokestatic values : ()[Lwtf/opal/kk;
    //   31: astore #5
    //   33: aload #5
    //   35: arraylength
    //   36: istore #6
    //   38: invokestatic B : ()[Ljava/lang/String;
    //   41: iconst_0
    //   42: istore #7
    //   44: astore #4
    //   46: iload #7
    //   48: iload #6
    //   50: if_icmpge -> 117
    //   53: aload #5
    //   55: iload #7
    //   57: aaload
    //   58: astore #8
    //   60: aload #4
    //   62: lload_2
    //   63: lconst_0
    //   64: lcmp
    //   65: ifle -> 114
    //   68: ifnull -> 112
    //   71: aload #8
    //   73: getfield w : I
    //   76: aload #4
    //   78: ifnull -> 118
    //   81: goto -> 88
    //   84: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: iload_1
    //   89: if_icmpne -> 109
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: aload #8
    //   101: getfield H : I
    //   104: ireturn
    //   105: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   108: athrow
    //   109: iinc #7, 1
    //   112: aload #4
    //   114: ifnonnull -> 46
    //   117: iconst_0
    //   118: ireturn
    // Exception table:
    //   from	to	target	type
    //   60	81	84	wtf/opal/x5
    //   71	92	95	wtf/opal/x5
    //   88	105	105	wtf/opal/x5
  }
  
  public static boolean D(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    return class_3675.method_15987(b9.c.method_22683().method_4490(), i);
  }
  
  public static boolean m(Object[] paramArrayOfObject) {
    class_304 class_304 = (class_304)paramArrayOfObject[0];
    return D(new Object[] { Integer.valueOf(class_304.method_1429().method_1444()) });
  }
  
  public static boolean g(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    l = a ^ l;
    String[] arrayOfString = l_.B();
    try {
      if (arrayOfString != null)
        try {
        
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return (GLFW.glfwGetMouseButton(b9.c.method_22683().method_4490(), i) == 1);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dj.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */