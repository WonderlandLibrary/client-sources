package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class da {
  private float H;
  
  private float E;
  
  private dx j;
  
  private static final long a = on.a(-4255216344434228818L, 1183444344556723709L, MethodHandles.lookup().lookupClass()).a(82148877621991L);
  
  public da(long paramLong) {
    this.j = new dp(l, 0, 0.0D, lp.BACKWARDS);
  }
  
  public void e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Float
    //   7: invokevirtual floatValue : ()F
    //   10: fstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore #4
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Integer
    //   28: invokevirtual intValue : ()I
    //   31: istore_3
    //   32: pop
    //   33: getstatic wtf/opal/da.a : J
    //   36: lload #4
    //   38: lxor
    //   39: lstore #4
    //   41: lload #4
    //   43: dup2
    //   44: ldc2_w 23065842935336
    //   47: lxor
    //   48: lstore #6
    //   50: dup2
    //   51: ldc2_w 127586806175006
    //   54: lxor
    //   55: lstore #8
    //   57: pop2
    //   58: aload_0
    //   59: aload_0
    //   60: getfield E : F
    //   63: aload_0
    //   64: getfield j : Lwtf/opal/dx;
    //   67: lload #8
    //   69: iconst_1
    //   70: anewarray java/lang/Object
    //   73: dup_x2
    //   74: dup_x2
    //   75: pop
    //   76: invokestatic valueOf : (J)Ljava/lang/Long;
    //   79: iconst_0
    //   80: swap
    //   81: aastore
    //   82: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   85: invokevirtual floatValue : ()F
    //   88: fsub
    //   89: putfield H : F
    //   92: invokestatic F : ()[Lwtf/opal/d;
    //   95: aload_0
    //   96: fload_2
    //   97: putfield E : F
    //   100: astore #10
    //   102: aload_0
    //   103: aload #10
    //   105: ifnonnull -> 136
    //   108: getfield H : F
    //   111: aload_0
    //   112: getfield E : F
    //   115: fload_2
    //   116: fsub
    //   117: fcmpl
    //   118: ifeq -> 162
    //   121: goto -> 128
    //   124: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   127: athrow
    //   128: aload_0
    //   129: goto -> 136
    //   132: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   135: athrow
    //   136: new wtf/opal/dp
    //   139: dup
    //   140: lload #6
    //   142: iload_3
    //   143: aload_0
    //   144: getfield E : F
    //   147: aload_0
    //   148: getfield H : F
    //   151: fsub
    //   152: f2d
    //   153: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   156: invokespecial <init> : (JIDLwtf/opal/lp;)V
    //   159: putfield j : Lwtf/opal/dx;
    //   162: aload #10
    //   164: lload #4
    //   166: lconst_0
    //   167: lcmp
    //   168: ifle -> 178
    //   171: ifnull -> 188
    //   174: iconst_4
    //   175: anewarray wtf/opal/d
    //   178: invokestatic p : ([Lwtf/opal/d;)V
    //   181: goto -> 188
    //   184: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   187: athrow
    //   188: return
    // Exception table:
    //   from	to	target	type
    //   102	121	124	wtf/opal/x5
    //   108	129	132	wtf/opal/x5
    //   162	181	184	wtf/opal/x5
  }
  
  public void R(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.E = f;
  }
  
  public boolean w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/da.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 110169065011374
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic F : ()[Lwtf/opal/d;
    //   30: astore #6
    //   32: aload_0
    //   33: getfield H : F
    //   36: aload_0
    //   37: getfield E : F
    //   40: fcmpl
    //   41: aload #6
    //   43: ifnonnull -> 101
    //   46: ifeq -> 100
    //   49: goto -> 56
    //   52: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   55: athrow
    //   56: aload_0
    //   57: getfield j : Lwtf/opal/dx;
    //   60: lload #4
    //   62: iconst_1
    //   63: anewarray java/lang/Object
    //   66: dup_x2
    //   67: dup_x2
    //   68: pop
    //   69: invokestatic valueOf : (J)Ljava/lang/Long;
    //   72: iconst_0
    //   73: swap
    //   74: aastore
    //   75: invokevirtual H : ([Ljava/lang/Object;)Z
    //   78: aload #6
    //   80: ifnonnull -> 101
    //   83: goto -> 90
    //   86: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: ifeq -> 104
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: iconst_1
    //   101: goto -> 105
    //   104: iconst_0
    //   105: ireturn
    // Exception table:
    //   from	to	target	type
    //   32	49	52	wtf/opal/x5
    //   46	83	86	wtf/opal/x5
    //   56	93	96	wtf/opal/x5
  }
  
  public float s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x461916397E65L;
    new Object[1];
    this.H = this.E - this.j.P(new Object[] { Long.valueOf(l2) }).floatValue();
    return this.H;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\da.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */