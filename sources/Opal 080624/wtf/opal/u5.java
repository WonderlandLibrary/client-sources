package wtf.opal;

import java.lang.invoke.MethodHandles;
import net.minecraft.class_241;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import net.minecraft.class_3545;

public final class u5 {
  private class_241 w = new class_241(0.0F, 0.0F);
  
  private class_241 d;
  
  private class_241 f;
  
  private class_241 p;
  
  private boolean F;
  
  private class_3545<Float, Float> z;
  
  private float u;
  
  private float b;
  
  private final gm<lb> D;
  
  private final gm<lz> m;
  
  private static int[] S;
  
  private static final long a = on.a(-5513109397900199601L, -7685453581463340275L, MethodHandles.lookup().lookupClass()).a(269455774332314L);
  
  public u5(long paramLong) {
    int[] arrayOfInt = d();
    try {
      this.p = new class_241(0.0F, 0.0F);
      this.z = new class_3545(Float.valueOf(0.0F), Float.valueOf(0.0F));
      this.u = 1.0F;
      this.b = 1.0F;
      this.D = this::lambda$new$0;
      this.m = this::lambda$new$1;
      if (arrayOfInt != null)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void p(Object[] paramArrayOfObject) {
    class_241 class_2411 = (class_241)paramArrayOfObject[0];
    this.f = class_2411;
    this.u = 1.5F;
    this.b = 1.5F;
  }
  
  public void g(Object[] paramArrayOfObject) {
    class_241 class_2411 = (class_241)paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    float f1 = ((Float)paramArrayOfObject[2]).floatValue();
    float f2 = ((Float)paramArrayOfObject[3]).floatValue();
    l = a ^ l;
    this.f = class_2411;
    this.u = f1;
    this.b = f2;
    int[] arrayOfInt = d();
    try {
      if (d.D() != null)
        i(new int[4]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public class_241 s(Object[] paramArrayOfObject) {
    return this.p;
  }
  
  public boolean U(Object[] paramArrayOfObject) {
    return this.F;
  }
  
  public class_241 v(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public class_241 k(Object[] paramArrayOfObject) {
    return this.w;
  }
  
  public class_3545 E(Object[] paramArrayOfObject) {
    return this.z;
  }
  
  public void E(Object[] paramArrayOfObject) {
    class_3545<Float, Float> class_35451 = (class_3545)paramArrayOfObject[0];
    this.z = class_35451;
  }
  
  private void lambda$new$1(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/u5.a : J
    //   3: ldc2_w 47549160954616
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic d : ()[I
    //   11: astore #4
    //   13: aload_0
    //   14: aload #4
    //   16: ifnonnull -> 321
    //   19: getfield f : Lnet/minecraft/class_241;
    //   22: ifnull -> 313
    //   25: goto -> 32
    //   28: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   31: athrow
    //   32: aload_0
    //   33: iconst_1
    //   34: putfield F : Z
    //   37: aload_0
    //   38: aload #4
    //   40: ifnonnull -> 101
    //   43: goto -> 50
    //   46: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   49: athrow
    //   50: getfield u : F
    //   53: fconst_1
    //   54: fcmpl
    //   55: ifne -> 93
    //   58: goto -> 65
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: aload_0
    //   66: aload #4
    //   68: ifnonnull -> 208
    //   71: goto -> 78
    //   74: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   77: athrow
    //   78: getfield b : F
    //   81: fconst_1
    //   82: fcmpl
    //   83: ifeq -> 200
    //   86: goto -> 93
    //   89: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   92: athrow
    //   93: aload_0
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: new net/minecraft/class_241
    //   104: dup
    //   105: aload_0
    //   106: getfield w : Lnet/minecraft/class_241;
    //   109: getfield field_1343 : F
    //   112: aload_0
    //   113: getfield f : Lnet/minecraft/class_241;
    //   116: getfield field_1343 : F
    //   119: aload_0
    //   120: getfield w : Lnet/minecraft/class_241;
    //   123: getfield field_1343 : F
    //   126: aload_0
    //   127: getfield f : Lnet/minecraft/class_241;
    //   130: getfield field_1343 : F
    //   133: invokestatic method_15381 : (FF)F
    //   136: aload_0
    //   137: getfield u : F
    //   140: fdiv
    //   141: invokestatic abs : (F)F
    //   144: invokestatic method_15388 : (FFF)F
    //   147: aload_0
    //   148: getfield w : Lnet/minecraft/class_241;
    //   151: getfield field_1342 : F
    //   154: aload_0
    //   155: getfield f : Lnet/minecraft/class_241;
    //   158: getfield field_1342 : F
    //   161: aload_0
    //   162: getfield w : Lnet/minecraft/class_241;
    //   165: getfield field_1342 : F
    //   168: aload_0
    //   169: getfield f : Lnet/minecraft/class_241;
    //   172: getfield field_1342 : F
    //   175: invokestatic method_15381 : (FF)F
    //   178: aload_0
    //   179: getfield b : F
    //   182: fdiv
    //   183: invokestatic abs : (F)F
    //   186: invokestatic method_15388 : (FFF)F
    //   189: invokespecial <init> : (FF)V
    //   192: putfield w : Lnet/minecraft/class_241;
    //   195: aload #4
    //   197: ifnull -> 215
    //   200: aload_0
    //   201: goto -> 208
    //   204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   207: athrow
    //   208: aload_0
    //   209: getfield f : Lnet/minecraft/class_241;
    //   212: putfield w : Lnet/minecraft/class_241;
    //   215: aload_0
    //   216: aload_0
    //   217: getfield w : Lnet/minecraft/class_241;
    //   220: aload_0
    //   221: aload #4
    //   223: ifnonnull -> 272
    //   226: getfield d : Lnet/minecraft/class_241;
    //   229: ifnonnull -> 271
    //   232: goto -> 239
    //   235: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   238: athrow
    //   239: new net/minecraft/class_241
    //   242: dup
    //   243: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   246: getfield field_1724 : Lnet/minecraft/class_746;
    //   249: invokevirtual method_36454 : ()F
    //   252: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   255: getfield field_1724 : Lnet/minecraft/class_746;
    //   258: invokevirtual method_36455 : ()F
    //   261: invokespecial <init> : (FF)V
    //   264: goto -> 275
    //   267: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   270: athrow
    //   271: aload_0
    //   272: getfield d : Lnet/minecraft/class_241;
    //   275: iconst_2
    //   276: anewarray java/lang/Object
    //   279: dup_x1
    //   280: swap
    //   281: iconst_1
    //   282: swap
    //   283: aastore
    //   284: dup_x1
    //   285: swap
    //   286: iconst_0
    //   287: swap
    //   288: aastore
    //   289: invokestatic v : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   292: putfield w : Lnet/minecraft/class_241;
    //   295: aload_0
    //   296: aload_0
    //   297: getfield w : Lnet/minecraft/class_241;
    //   300: putfield d : Lnet/minecraft/class_241;
    //   303: aload_0
    //   304: aconst_null
    //   305: putfield f : Lnet/minecraft/class_241;
    //   308: aload #4
    //   310: ifnull -> 735
    //   313: aload_0
    //   314: goto -> 321
    //   317: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   320: athrow
    //   321: getfield F : Z
    //   324: aload #4
    //   326: ifnonnull -> 739
    //   329: ifeq -> 735
    //   332: goto -> 339
    //   335: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   338: athrow
    //   339: aload_0
    //   340: aload #4
    //   342: ifnonnull -> 403
    //   345: goto -> 352
    //   348: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   351: athrow
    //   352: getfield u : F
    //   355: fconst_1
    //   356: fcmpl
    //   357: ifne -> 395
    //   360: goto -> 367
    //   363: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   366: athrow
    //   367: aload_0
    //   368: aload #4
    //   370: ifnonnull -> 518
    //   373: goto -> 380
    //   376: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   379: athrow
    //   380: getfield b : F
    //   383: fconst_1
    //   384: fcmpl
    //   385: ifeq -> 510
    //   388: goto -> 395
    //   391: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   394: athrow
    //   395: aload_0
    //   396: goto -> 403
    //   399: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   402: athrow
    //   403: new net/minecraft/class_241
    //   406: dup
    //   407: aload_0
    //   408: getfield w : Lnet/minecraft/class_241;
    //   411: getfield field_1343 : F
    //   414: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   417: getfield field_1724 : Lnet/minecraft/class_746;
    //   420: invokevirtual method_36454 : ()F
    //   423: aload_0
    //   424: getfield w : Lnet/minecraft/class_241;
    //   427: getfield field_1343 : F
    //   430: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   433: getfield field_1724 : Lnet/minecraft/class_746;
    //   436: invokevirtual method_36454 : ()F
    //   439: invokestatic method_15381 : (FF)F
    //   442: aload_0
    //   443: getfield u : F
    //   446: fdiv
    //   447: invokestatic abs : (F)F
    //   450: invokestatic method_15388 : (FFF)F
    //   453: aload_0
    //   454: getfield w : Lnet/minecraft/class_241;
    //   457: getfield field_1342 : F
    //   460: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   463: getfield field_1724 : Lnet/minecraft/class_746;
    //   466: invokevirtual method_36455 : ()F
    //   469: aload_0
    //   470: getfield w : Lnet/minecraft/class_241;
    //   473: getfield field_1342 : F
    //   476: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   479: getfield field_1724 : Lnet/minecraft/class_746;
    //   482: invokevirtual method_36455 : ()F
    //   485: invokestatic method_15381 : (FF)F
    //   488: aload_0
    //   489: getfield b : F
    //   492: fdiv
    //   493: invokestatic abs : (F)F
    //   496: invokestatic method_15388 : (FFF)F
    //   499: invokespecial <init> : (FF)V
    //   502: putfield w : Lnet/minecraft/class_241;
    //   505: aload #4
    //   507: ifnull -> 546
    //   510: aload_0
    //   511: goto -> 518
    //   514: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   517: athrow
    //   518: new net/minecraft/class_241
    //   521: dup
    //   522: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   525: getfield field_1724 : Lnet/minecraft/class_746;
    //   528: invokevirtual method_36454 : ()F
    //   531: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   534: getfield field_1724 : Lnet/minecraft/class_746;
    //   537: invokevirtual method_36455 : ()F
    //   540: invokespecial <init> : (FF)V
    //   543: putfield w : Lnet/minecraft/class_241;
    //   546: aload_0
    //   547: aload_0
    //   548: getfield w : Lnet/minecraft/class_241;
    //   551: aload_0
    //   552: aload #4
    //   554: ifnonnull -> 603
    //   557: getfield d : Lnet/minecraft/class_241;
    //   560: ifnonnull -> 602
    //   563: goto -> 570
    //   566: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   569: athrow
    //   570: new net/minecraft/class_241
    //   573: dup
    //   574: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   577: getfield field_1724 : Lnet/minecraft/class_746;
    //   580: invokevirtual method_36454 : ()F
    //   583: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   586: getfield field_1724 : Lnet/minecraft/class_746;
    //   589: invokevirtual method_36455 : ()F
    //   592: invokespecial <init> : (FF)V
    //   595: goto -> 606
    //   598: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   601: athrow
    //   602: aload_0
    //   603: getfield d : Lnet/minecraft/class_241;
    //   606: iconst_2
    //   607: anewarray java/lang/Object
    //   610: dup_x1
    //   611: swap
    //   612: iconst_1
    //   613: swap
    //   614: aastore
    //   615: dup_x1
    //   616: swap
    //   617: iconst_0
    //   618: swap
    //   619: aastore
    //   620: invokestatic v : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   623: putfield w : Lnet/minecraft/class_241;
    //   626: aload_0
    //   627: aload_0
    //   628: getfield w : Lnet/minecraft/class_241;
    //   631: putfield d : Lnet/minecraft/class_241;
    //   634: aload_0
    //   635: getfield w : Lnet/minecraft/class_241;
    //   638: getfield field_1343 : F
    //   641: invokestatic method_15393 : (F)F
    //   644: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   647: getfield field_1724 : Lnet/minecraft/class_746;
    //   650: invokevirtual method_36454 : ()F
    //   653: invokestatic method_15393 : (F)F
    //   656: fsub
    //   657: invokestatic abs : (F)F
    //   660: ldc 3.0
    //   662: fcmpg
    //   663: aload #4
    //   665: ifnonnull -> 739
    //   668: ifge -> 735
    //   671: goto -> 678
    //   674: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   677: athrow
    //   678: aload_0
    //   679: getfield w : Lnet/minecraft/class_241;
    //   682: getfield field_1342 : F
    //   685: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   688: getfield field_1724 : Lnet/minecraft/class_746;
    //   691: invokevirtual method_36455 : ()F
    //   694: fsub
    //   695: invokestatic abs : (F)F
    //   698: ldc 3.0
    //   700: fcmpg
    //   701: aload #4
    //   703: ifnonnull -> 739
    //   706: goto -> 713
    //   709: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   712: athrow
    //   713: ifge -> 735
    //   716: goto -> 723
    //   719: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   722: athrow
    //   723: aload_0
    //   724: iconst_0
    //   725: putfield F : Z
    //   728: goto -> 735
    //   731: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   734: athrow
    //   735: aload_0
    //   736: getfield F : Z
    //   739: aload #4
    //   741: ifnonnull -> 794
    //   744: ifne -> 790
    //   747: goto -> 754
    //   750: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   753: athrow
    //   754: aload_0
    //   755: new net/minecraft/class_241
    //   758: dup
    //   759: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   762: getfield field_1724 : Lnet/minecraft/class_746;
    //   765: invokevirtual method_36454 : ()F
    //   768: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   771: getfield field_1724 : Lnet/minecraft/class_746;
    //   774: invokevirtual method_36455 : ()F
    //   777: invokespecial <init> : (FF)V
    //   780: putfield w : Lnet/minecraft/class_241;
    //   783: goto -> 790
    //   786: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   789: athrow
    //   790: aload_0
    //   791: getfield F : Z
    //   794: ifeq -> 850
    //   797: aload_1
    //   798: aload_0
    //   799: getfield w : Lnet/minecraft/class_241;
    //   802: getfield field_1343 : F
    //   805: iconst_1
    //   806: anewarray java/lang/Object
    //   809: dup_x1
    //   810: swap
    //   811: invokestatic valueOf : (F)Ljava/lang/Float;
    //   814: iconst_0
    //   815: swap
    //   816: aastore
    //   817: invokevirtual T : ([Ljava/lang/Object;)V
    //   820: aload_1
    //   821: aload_0
    //   822: getfield w : Lnet/minecraft/class_241;
    //   825: getfield field_1342 : F
    //   828: iconst_1
    //   829: anewarray java/lang/Object
    //   832: dup_x1
    //   833: swap
    //   834: invokestatic valueOf : (F)Ljava/lang/Float;
    //   837: iconst_0
    //   838: swap
    //   839: aastore
    //   840: invokevirtual s : ([Ljava/lang/Object;)V
    //   843: goto -> 850
    //   846: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   849: athrow
    //   850: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	wtf/opal/x5
    //   19	43	46	wtf/opal/x5
    //   32	58	61	wtf/opal/x5
    //   50	71	74	wtf/opal/x5
    //   65	86	89	wtf/opal/x5
    //   78	94	97	wtf/opal/x5
    //   101	201	204	wtf/opal/x5
    //   215	232	235	wtf/opal/x5
    //   226	267	267	wtf/opal/x5
    //   275	314	317	wtf/opal/x5
    //   321	332	335	wtf/opal/x5
    //   329	345	348	wtf/opal/x5
    //   339	360	363	wtf/opal/x5
    //   352	373	376	wtf/opal/x5
    //   367	388	391	wtf/opal/x5
    //   380	396	399	wtf/opal/x5
    //   403	511	514	wtf/opal/x5
    //   546	563	566	wtf/opal/x5
    //   557	598	598	wtf/opal/x5
    //   606	671	674	wtf/opal/x5
    //   668	706	709	wtf/opal/x5
    //   678	716	719	wtf/opal/x5
    //   713	728	731	wtf/opal/x5
    //   739	747	750	wtf/opal/x5
    //   744	783	786	wtf/opal/x5
    //   794	843	846	wtf/opal/x5
  }
  
  private void lambda$new$0(lb paramlb) {
    long l = a ^ 0x21AD128E905L;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    int[] arrayOfInt = d();
    try {
      if (arrayOfInt == null)
        try {
          if (class_2596 instanceof class_2828) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_2828 class_2828 = (class_2828)class_2596;
    try {
      if (class_2828.method_36172())
        this.p = new class_241(class_2828.method_12271(b9.c.field_1724.method_36454()), class_2828.method_12270(b9.c.field_1724.method_36455())); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public static void i(int[] paramArrayOfint) {
    S = paramArrayOfint;
  }
  
  public static int[] d() {
    return S;
  }
  
  static {
    if (d() != null)
      i(new int[3]); 
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u5.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */