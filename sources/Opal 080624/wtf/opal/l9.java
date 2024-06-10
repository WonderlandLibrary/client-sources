package wtf.opal;

import java.lang.invoke.MethodHandles;

public class l9 {
  public final int D;
  
  public final int s;
  
  public final int l;
  
  private static final long a = on.a(4800723278388002744L, -7771758018032246467L, MethodHandles.lookup().lookupClass()).a(213009461481225L);
  
  l9(int paramInt1, int paramInt2, int paramInt3) {
    this.D = paramInt1;
    this.l = paramInt3;
    this.s = paramInt2;
  }
  
  public String toString() {
    return "" + this.s + ":" + this.s;
  }
  
  public int hashCode() {
    return this.D;
  }
  
  public boolean equals(Object paramObject) {
    // Byte code:
    //   0: getstatic wtf/opal/l9.a : J
    //   3: ldc2_w 65676679196979
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic O : ()I
    //   11: istore #4
    //   13: aload_0
    //   14: iload #4
    //   16: ifeq -> 37
    //   19: aload_1
    //   20: if_acmpne -> 36
    //   23: goto -> 30
    //   26: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   29: athrow
    //   30: iconst_1
    //   31: ireturn
    //   32: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   35: athrow
    //   36: aload_1
    //   37: ifnonnull -> 46
    //   40: iconst_0
    //   41: ireturn
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_0
    //   47: invokevirtual getClass : ()Ljava/lang/Class;
    //   50: iload #4
    //   52: ifeq -> 76
    //   55: aload_1
    //   56: invokevirtual getClass : ()Ljava/lang/Class;
    //   59: if_acmpeq -> 75
    //   62: goto -> 69
    //   65: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: iconst_0
    //   70: ireturn
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: aload_1
    //   76: checkcast wtf/opal/l9
    //   79: astore #5
    //   81: aload_0
    //   82: getfield D : I
    //   85: aload #5
    //   87: getfield D : I
    //   90: iload #4
    //   92: ifeq -> 121
    //   95: if_icmpne -> 171
    //   98: goto -> 105
    //   101: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload_0
    //   106: getfield l : I
    //   109: aload #5
    //   111: getfield l : I
    //   114: goto -> 121
    //   117: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   120: athrow
    //   121: iload #4
    //   123: ifeq -> 164
    //   126: if_icmpne -> 171
    //   129: goto -> 136
    //   132: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   135: athrow
    //   136: aload_0
    //   137: getfield s : I
    //   140: iload #4
    //   142: ifeq -> 168
    //   145: goto -> 152
    //   148: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   151: athrow
    //   152: aload #5
    //   154: getfield s : I
    //   157: goto -> 164
    //   160: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   163: athrow
    //   164: if_icmpne -> 171
    //   167: iconst_1
    //   168: goto -> 172
    //   171: iconst_0
    //   172: ireturn
    // Exception table:
    //   from	to	target	type
    //   13	23	26	wtf/opal/x5
    //   19	32	32	wtf/opal/x5
    //   37	42	42	wtf/opal/x5
    //   46	62	65	wtf/opal/x5
    //   55	71	71	wtf/opal/x5
    //   81	98	101	wtf/opal/x5
    //   95	114	117	wtf/opal/x5
    //   121	129	132	wtf/opal/x5
    //   126	145	148	wtf/opal/x5
    //   136	157	160	wtf/opal/x5
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\l9.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */