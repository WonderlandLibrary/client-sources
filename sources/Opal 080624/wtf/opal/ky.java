package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class ky<T extends Enum<T>> extends k3<T> {
  private final T[] n;
  
  private boolean g;
  
  private d W;
  
  private static final long b = on.a(-3616337090866823679L, -836171560098953465L, MethodHandles.lookup().lookupClass()).a(7176370238699L);
  
  public ky(String paramString, T paramT) {
    super(paramString);
    V(new Object[] { paramT });
    this.n = (T[])f(new Object[0]);
  }
  
  public ky(String paramString, long paramLong, u_ paramu_, Enum paramEnum) {
    super(l, paramString, paramu_);
    V(new Object[] { paramEnum });
    this.n = (T[])f(new Object[0]);
  }
  
  public ky(String paramString, d paramd, T paramT) {
    super(paramString);
    V(new Object[] { paramT });
    this.n = (T[])f(new Object[0]);
    this.W = paramd;
    paramd.a(new Object[] { this });
  }
  
  public void G(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.g = bool;
  }
  
  public boolean r(Object[] paramArrayOfObject) {
    return this.g;
  }
  
  private Enum[] f(Object[] paramArrayOfObject) {
    return (Enum[])((Enum)z()).getClass().getEnumConstants();
  }
  
  public Enum[] A(Object[] paramArrayOfObject) {
    return (Enum[])this.n;
  }
  
  public void U(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
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
    //   20: istore #4
    //   22: pop
    //   23: getstatic wtf/opal/ky.b : J
    //   26: lload_2
    //   27: lxor
    //   28: lstore_2
    //   29: lload_2
    //   30: dup2
    //   31: ldc2_w 116089217809202
    //   34: lxor
    //   35: lstore #5
    //   37: pop2
    //   38: invokestatic s : ()[Lwtf/opal/d;
    //   41: astore #7
    //   43: aload_0
    //   44: getfield W : Lwtf/opal/d;
    //   47: aload #7
    //   49: ifnonnull -> 144
    //   52: ifnull -> 120
    //   55: goto -> 62
    //   58: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   61: athrow
    //   62: lload_2
    //   63: lconst_0
    //   64: lcmp
    //   65: iflt -> 140
    //   68: iconst_0
    //   69: anewarray java/lang/Object
    //   72: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokevirtual L : ([Ljava/lang/Object;)Z
    //   82: ifne -> 120
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: aload_0
    //   93: getfield W : Lwtf/opal/d;
    //   96: iconst_0
    //   97: anewarray java/lang/Object
    //   100: invokevirtual Z : ([Ljava/lang/Object;)Ljava/util/List;
    //   103: <illegal opcode> accept : ()Ljava/util/function/Consumer;
    //   108: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   113: goto -> 120
    //   116: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   119: athrow
    //   120: aload_0
    //   121: aload_0
    //   122: getfield n : [Ljava/lang/Enum;
    //   125: iload #4
    //   127: aaload
    //   128: iconst_1
    //   129: anewarray java/lang/Object
    //   132: dup_x1
    //   133: swap
    //   134: iconst_0
    //   135: swap
    //   136: aastore
    //   137: invokevirtual V : ([Ljava/lang/Object;)V
    //   140: aload_0
    //   141: getfield W : Lwtf/opal/d;
    //   144: aload #7
    //   146: lload_2
    //   147: lconst_0
    //   148: lcmp
    //   149: iflt -> 180
    //   152: ifnonnull -> 176
    //   155: ifnull -> 318
    //   158: goto -> 165
    //   161: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   164: athrow
    //   165: aload_0
    //   166: getfield W : Lwtf/opal/d;
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: iconst_0
    //   177: anewarray java/lang/Object
    //   180: invokevirtual Z : ([Ljava/lang/Object;)Ljava/util/List;
    //   183: invokeinterface iterator : ()Ljava/util/Iterator;
    //   188: astore #8
    //   190: aload #8
    //   192: invokeinterface hasNext : ()Z
    //   197: ifeq -> 318
    //   200: aload #8
    //   202: invokeinterface next : ()Ljava/lang/Object;
    //   207: checkcast wtf/opal/u_
    //   210: astore #9
    //   212: aload #9
    //   214: iconst_0
    //   215: anewarray java/lang/Object
    //   218: invokevirtual V : ([Ljava/lang/Object;)Ljava/lang/Enum;
    //   221: invokevirtual ordinal : ()I
    //   224: lload_2
    //   225: lconst_0
    //   226: lcmp
    //   227: ifle -> 265
    //   230: aload #7
    //   232: ifnonnull -> 265
    //   235: iload #4
    //   237: if_icmpne -> 300
    //   240: goto -> 247
    //   243: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   246: athrow
    //   247: aload_0
    //   248: getfield W : Lwtf/opal/d;
    //   251: iconst_0
    //   252: anewarray java/lang/Object
    //   255: invokevirtual D : ([Ljava/lang/Object;)Z
    //   258: goto -> 265
    //   261: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   264: athrow
    //   265: ifeq -> 300
    //   268: aload #9
    //   270: lload #5
    //   272: iconst_1
    //   273: anewarray java/lang/Object
    //   276: dup_x2
    //   277: dup_x2
    //   278: pop
    //   279: invokestatic valueOf : (J)Ljava/lang/Long;
    //   282: iconst_0
    //   283: swap
    //   284: aastore
    //   285: invokevirtual s : ([Ljava/lang/Object;)V
    //   288: aload #7
    //   290: ifnull -> 318
    //   293: goto -> 300
    //   296: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   299: athrow
    //   300: aload #7
    //   302: ifnull -> 190
    //   305: lload_2
    //   306: lconst_0
    //   307: lcmp
    //   308: iflt -> 212
    //   311: goto -> 318
    //   314: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   317: athrow
    //   318: return
    // Exception table:
    //   from	to	target	type
    //   43	55	58	wtf/opal/x5
    //   52	85	88	wtf/opal/x5
    //   62	113	116	wtf/opal/x5
    //   144	158	161	wtf/opal/x5
    //   155	169	172	wtf/opal/x5
    //   212	240	243	wtf/opal/x5
    //   235	258	261	wtf/opal/x5
    //   265	293	296	wtf/opal/x5
    //   268	305	314	wtf/opal/x5
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ky.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */