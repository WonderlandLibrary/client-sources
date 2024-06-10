package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class pj extends u_<j4> {
  private boolean n;
  
  private final gm<lz> b = this::lambda$new$0;
  
  private final gm<bw> J = this::lambda$new$1;
  
  private static final long a = on.a(4490509238853763342L, -2168356756946662210L, MethodHandles.lookup().lookupClass()).a(246302514618238L);
  
  public pj(j4 paramj4) {
    super(paramj4);
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return km.COLLISION;
  }
  
  private void lambda$new$1(bw parambw) {
    // Byte code:
    //   0: getstatic wtf/opal/pj.a : J
    //   3: ldc2_w 2720289760931
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic y : ()Z
    //   11: istore #4
    //   13: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   16: getfield field_1724 : Lnet/minecraft/class_746;
    //   19: getfield field_6017 : F
    //   22: ldc 3.0
    //   24: fcmpl
    //   25: iload #4
    //   27: ifne -> 81
    //   30: iflt -> 105
    //   33: goto -> 40
    //   36: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   39: athrow
    //   40: aload_1
    //   41: iload #4
    //   43: ifne -> 85
    //   46: goto -> 53
    //   49: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   52: athrow
    //   53: iconst_0
    //   54: anewarray java/lang/Object
    //   57: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   60: invokevirtual method_10264 : ()I
    //   63: i2d
    //   64: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   67: getfield field_1724 : Lnet/minecraft/class_746;
    //   70: invokevirtual method_23318 : ()D
    //   73: dcmpg
    //   74: goto -> 81
    //   77: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: ifge -> 105
    //   84: aload_1
    //   85: invokestatic method_1077 : ()Lnet/minecraft/class_265;
    //   88: iconst_1
    //   89: anewarray java/lang/Object
    //   92: dup_x1
    //   93: swap
    //   94: iconst_0
    //   95: swap
    //   96: aastore
    //   97: invokevirtual I : ([Ljava/lang/Object;)V
    //   100: aload_0
    //   101: iconst_1
    //   102: putfield n : Z
    //   105: return
    // Exception table:
    //   from	to	target	type
    //   13	33	36	wtf/opal/x5
    //   30	46	49	wtf/opal/x5
    //   40	74	77	wtf/opal/x5
  }
  
  private void lambda$new$0(lz paramlz) {
    long l = a ^ 0x2BFEF29FCE65L;
    boolean bool = pw.F();
    try {
      if (bool)
        try {
          if (this.n) {
            paramlz.w(new Object[] { Boolean.valueOf(true) });
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    this.n = false;
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pj.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */