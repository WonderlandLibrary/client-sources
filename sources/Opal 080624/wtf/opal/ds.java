package wtf.opal;

import java.lang.invoke.MethodHandles;
import java.util.Random;

public final class ds {
  private static final j6 J;
  
  private static final Random O;
  
  private static boolean b;
  
  private static final long a = on.a(-8061193920023061288L, -6891116707078984691L, MethodHandles.lookup().lookupClass()).a(136464040851866L);
  
  public static boolean C(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: pop
    //   12: getstatic wtf/opal/ds.a : J
    //   15: lload_1
    //   16: lxor
    //   17: lstore_1
    //   18: invokestatic P : ()Z
    //   21: istore_3
    //   22: getstatic wtf/opal/ds.J : Lwtf/opal/j6;
    //   25: iconst_0
    //   26: anewarray java/lang/Object
    //   29: invokevirtual D : ([Ljava/lang/Object;)Z
    //   32: iload_3
    //   33: ifeq -> 90
    //   36: ifeq -> 89
    //   39: goto -> 46
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: getstatic wtf/opal/ds.J : Lwtf/opal/j6;
    //   49: iconst_0
    //   50: anewarray java/lang/Object
    //   53: invokevirtual r : ([Ljava/lang/Object;)Lwtf/opal/bm;
    //   56: getstatic wtf/opal/bm.LIGHTNING : Lwtf/opal/bm;
    //   59: invokevirtual equals : (Ljava/lang/Object;)Z
    //   62: iload_3
    //   63: lload_1
    //   64: lconst_0
    //   65: lcmp
    //   66: ifle -> 95
    //   69: ifeq -> 94
    //   72: goto -> 79
    //   75: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: ifne -> 91
    //   82: goto -> 89
    //   85: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   88: athrow
    //   89: iconst_1
    //   90: ireturn
    //   91: getstatic wtf/opal/ds.b : Z
    //   94: iload_3
    //   95: ifeq -> 109
    //   98: ifne -> 112
    //   101: goto -> 108
    //   104: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: iconst_1
    //   109: goto -> 113
    //   112: iconst_0
    //   113: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	39	42	wtf/opal/x5
    //   36	72	75	wtf/opal/x5
    //   46	82	85	wtf/opal/x5
    //   94	101	104	wtf/opal/x5
  }
  
  static {
    J = (j6)d1.q(new Object[0]).x(new Object[0]).V(new Object[] { j6.class });
    O = new Random();
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ds.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */