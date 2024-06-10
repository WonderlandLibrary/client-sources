package wtf.opal;

import java.lang.invoke.MethodHandles;

public final class u1 {
  private static final long a = on.a(-4580866016682690436L, -7781216001590567896L, MethodHandles.lookup().lookupClass()).a(263924478162342L);
  
  public static boolean Z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore #8
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/Float
    //   18: invokevirtual floatValue : ()F
    //   21: fstore #10
    //   23: dup
    //   24: iconst_2
    //   25: aaload
    //   26: checkcast java/lang/Float
    //   29: invokevirtual floatValue : ()F
    //   32: fstore #5
    //   34: dup
    //   35: iconst_3
    //   36: aaload
    //   37: checkcast java/lang/Float
    //   40: invokevirtual floatValue : ()F
    //   43: fstore #6
    //   45: dup
    //   46: iconst_4
    //   47: aaload
    //   48: checkcast java/lang/Float
    //   51: invokevirtual floatValue : ()F
    //   54: fstore #7
    //   56: dup
    //   57: iconst_5
    //   58: aaload
    //   59: checkcast java/lang/Double
    //   62: invokevirtual doubleValue : ()D
    //   65: dstore_1
    //   66: dup
    //   67: bipush #6
    //   69: aaload
    //   70: checkcast java/lang/Double
    //   73: invokevirtual doubleValue : ()D
    //   76: dstore_3
    //   77: pop
    //   78: getstatic wtf/opal/u1.a : J
    //   81: lload #8
    //   83: lxor
    //   84: lstore #8
    //   86: invokestatic F : ()Ljava/lang/String;
    //   89: dload_1
    //   90: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   93: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   96: invokevirtual method_4480 : ()I
    //   99: i2d
    //   100: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   103: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   106: invokevirtual method_4489 : ()I
    //   109: i2d
    //   110: ddiv
    //   111: dmul
    //   112: dstore_1
    //   113: dload_3
    //   114: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   117: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   120: invokevirtual method_4507 : ()I
    //   123: i2d
    //   124: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   127: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   130: invokevirtual method_4506 : ()I
    //   133: i2d
    //   134: ddiv
    //   135: dmul
    //   136: dstore_3
    //   137: astore #11
    //   139: dload_1
    //   140: fload #10
    //   142: f2d
    //   143: dcmpl
    //   144: aload #11
    //   146: ifnonnull -> 171
    //   149: iflt -> 264
    //   152: goto -> 159
    //   155: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: dload_3
    //   160: fload #5
    //   162: f2d
    //   163: dcmpl
    //   164: goto -> 171
    //   167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   170: athrow
    //   171: aload #11
    //   173: lload #8
    //   175: lconst_0
    //   176: lcmp
    //   177: ifle -> 210
    //   180: ifnonnull -> 208
    //   183: iflt -> 264
    //   186: goto -> 193
    //   189: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   192: athrow
    //   193: dload_1
    //   194: fload #10
    //   196: fload #6
    //   198: fadd
    //   199: f2d
    //   200: dcmpg
    //   201: goto -> 208
    //   204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   207: athrow
    //   208: aload #11
    //   210: lload #8
    //   212: lconst_0
    //   213: lcmp
    //   214: ifle -> 247
    //   217: ifnonnull -> 245
    //   220: ifge -> 264
    //   223: goto -> 230
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: dload_3
    //   231: fload #5
    //   233: fload #7
    //   235: fadd
    //   236: f2d
    //   237: dcmpg
    //   238: goto -> 245
    //   241: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   244: athrow
    //   245: aload #11
    //   247: ifnonnull -> 261
    //   250: ifge -> 264
    //   253: goto -> 260
    //   256: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   259: athrow
    //   260: iconst_1
    //   261: goto -> 265
    //   264: iconst_0
    //   265: lload #8
    //   267: lconst_0
    //   268: lcmp
    //   269: iflt -> 284
    //   272: aload #11
    //   274: ifnull -> 291
    //   277: iconst_1
    //   278: anewarray wtf/opal/d
    //   281: invokestatic p : ([Lwtf/opal/d;)V
    //   284: goto -> 291
    //   287: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   290: athrow
    //   291: ireturn
    // Exception table:
    //   from	to	target	type
    //   139	152	155	wtf/opal/x5
    //   149	164	167	wtf/opal/x5
    //   171	186	189	wtf/opal/x5
    //   183	201	204	wtf/opal/x5
    //   208	223	226	wtf/opal/x5
    //   220	238	241	wtf/opal/x5
    //   245	253	256	wtf/opal/x5
    //   265	284	287	wtf/opal/x5
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */