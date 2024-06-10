package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class pp extends u_<xw> {
  private int c;
  
  private int a;
  
  private int s;
  
  private double T;
  
  private final gm<u0> h = this::lambda$new$0;
  
  private final gm<lb> q = this::lambda$new$1;
  
  private static final long b = on.a(-2991403265446369874L, -5748874816573386801L, MethodHandles.lookup().lookupClass()).a(181679534974952L);
  
  private static final long d;
  
  public pp(xw paramxw) {
    super(paramxw);
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.T = 1.0E-10D;
    this.a = 0;
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l1.WATCHDOG_LOWHOP;
  }
  
  private void lambda$new$1(lb paramlb) {
    long l = b ^ 0x3536B439ECC2L;
    boolean bool = pv.b();
    try {
      if (bool)
        try {
          if (((xw)y(new Object[0])).B.z().booleanValue()) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    if (!((xw)y(new Object[0])).B.z().booleanValue())
      return; 
    class_2596 class_2596 = paramlb.J(new Object[0]);
    try {
      if (bool)
        try {
          if (class_2596 instanceof class_2828) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2828 class_2828 = (class_2828)class_2596;
    PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2828;
    try {
      if (b9.c.field_1724.method_24828())
        playerMoveC2SPacketAccessor.setY(class_2828.method_12268(b9.c.field_1724.method_23318()) + 1.0E-7D); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/pp.b : J
    //   3: ldc2_w 79388193054345
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 7537913040054
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 61260251652629
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 31020250581633
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic D : ()Z
    //   34: istore #10
    //   36: aload_1
    //   37: iconst_0
    //   38: anewarray java/lang/Object
    //   41: invokevirtual K : ([Ljava/lang/Object;)Z
    //   44: iload #10
    //   46: ifne -> 89
    //   49: ifne -> 104
    //   52: goto -> 59
    //   55: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: aload_0
    //   60: iconst_0
    //   61: anewarray java/lang/Object
    //   64: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   67: checkcast wtf/opal/xw
    //   70: getfield B : Lwtf/opal/ke;
    //   73: invokevirtual z : ()Ljava/lang/Object;
    //   76: checkcast java/lang/Boolean
    //   79: invokevirtual booleanValue : ()Z
    //   82: goto -> 89
    //   85: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   88: athrow
    //   89: iload #10
    //   91: ifne -> 117
    //   94: ifne -> 105
    //   97: goto -> 104
    //   100: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: return
    //   105: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   108: getfield field_1690 : Lnet/minecraft/class_315;
    //   111: getfield field_1903 : Lnet/minecraft/class_304;
    //   114: invokevirtual method_1434 : ()Z
    //   117: iload #10
    //   119: ifne -> 173
    //   122: ifeq -> 765
    //   125: goto -> 132
    //   128: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: aload_0
    //   133: iconst_0
    //   134: putfield c : I
    //   137: aload_0
    //   138: dup
    //   139: getfield a : I
    //   142: iconst_1
    //   143: iadd
    //   144: putfield a : I
    //   147: aload_0
    //   148: dup
    //   149: getfield s : I
    //   152: iconst_1
    //   153: iadd
    //   154: putfield s : I
    //   157: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   160: getfield field_1724 : Lnet/minecraft/class_746;
    //   163: invokevirtual method_24828 : ()Z
    //   166: goto -> 173
    //   169: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   172: athrow
    //   173: iload #10
    //   175: ifne -> 204
    //   178: ifeq -> 200
    //   181: goto -> 188
    //   184: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   187: athrow
    //   188: aload_0
    //   189: iconst_0
    //   190: putfield s : I
    //   193: goto -> 200
    //   196: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   199: athrow
    //   200: aload_0
    //   201: getfield s : I
    //   204: iload #10
    //   206: ifne -> 296
    //   209: ifne -> 292
    //   212: goto -> 219
    //   215: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   218: athrow
    //   219: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   222: getfield field_1724 : Lnet/minecraft/class_746;
    //   225: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   228: getfield field_1724 : Lnet/minecraft/class_746;
    //   231: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   234: invokevirtual method_10216 : ()D
    //   237: ldc2_w 0.41999998688697815
    //   240: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   243: getfield field_1724 : Lnet/minecraft/class_746;
    //   246: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   249: invokevirtual method_10215 : ()D
    //   252: invokevirtual method_18800 : (DDD)V
    //   255: lload #8
    //   257: ldc2_w 0.1
    //   260: iconst_2
    //   261: anewarray java/lang/Object
    //   264: dup_x2
    //   265: dup_x2
    //   266: pop
    //   267: invokestatic valueOf : (D)Ljava/lang/Double;
    //   270: iconst_1
    //   271: swap
    //   272: aastore
    //   273: dup_x2
    //   274: dup_x2
    //   275: pop
    //   276: invokestatic valueOf : (J)Ljava/lang/Long;
    //   279: iconst_0
    //   280: swap
    //   281: aastore
    //   282: invokestatic k : ([Ljava/lang/Object;)V
    //   285: goto -> 292
    //   288: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   291: athrow
    //   292: aload_0
    //   293: getfield s : I
    //   296: iconst_1
    //   297: iload #10
    //   299: ifne -> 549
    //   302: if_icmpne -> 544
    //   305: goto -> 312
    //   308: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   311: athrow
    //   312: lload #6
    //   314: iconst_1
    //   315: anewarray java/lang/Object
    //   318: dup_x2
    //   319: dup_x2
    //   320: pop
    //   321: invokestatic valueOf : (J)Ljava/lang/Long;
    //   324: iconst_0
    //   325: swap
    //   326: aastore
    //   327: invokestatic I : ([Ljava/lang/Object;)Z
    //   330: iload #10
    //   332: ifne -> 548
    //   335: goto -> 342
    //   338: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   341: athrow
    //   342: ifeq -> 544
    //   345: goto -> 352
    //   348: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   351: athrow
    //   352: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   355: getfield field_1724 : Lnet/minecraft/class_746;
    //   358: getstatic net/minecraft/class_1294.field_5904 : Lnet/minecraft/class_6880;
    //   361: invokevirtual method_6059 : (Lnet/minecraft/class_6880;)Z
    //   364: iload #10
    //   366: ifne -> 408
    //   369: goto -> 376
    //   372: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   375: athrow
    //   376: ifeq -> 435
    //   379: goto -> 386
    //   382: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   385: athrow
    //   386: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   389: getfield field_1724 : Lnet/minecraft/class_746;
    //   392: getstatic net/minecraft/class_1294.field_5904 : Lnet/minecraft/class_6880;
    //   395: invokevirtual method_6112 : (Lnet/minecraft/class_6880;)Lnet/minecraft/class_1293;
    //   398: invokevirtual method_5578 : ()I
    //   401: goto -> 408
    //   404: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   407: athrow
    //   408: iload #10
    //   410: ifne -> 432
    //   413: iconst_1
    //   414: if_icmplt -> 435
    //   417: goto -> 424
    //   420: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   423: athrow
    //   424: iconst_1
    //   425: goto -> 432
    //   428: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   431: athrow
    //   432: goto -> 436
    //   435: iconst_0
    //   436: istore #11
    //   438: lload #4
    //   440: iconst_1
    //   441: anewarray java/lang/Object
    //   444: dup_x2
    //   445: dup_x2
    //   446: pop
    //   447: invokestatic valueOf : (J)Ljava/lang/Long;
    //   450: iconst_0
    //   451: swap
    //   452: aastore
    //   453: invokestatic m : ([Ljava/lang/Object;)D
    //   456: ldc2_w 0.7
    //   459: iload #10
    //   461: ifne -> 493
    //   464: dmul
    //   465: aload_0
    //   466: getfield a : I
    //   469: getstatic wtf/opal/pp.d : J
    //   472: l2i
    //   473: if_icmpge -> 496
    //   476: goto -> 483
    //   479: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   482: athrow
    //   483: ldc2_w 0.1
    //   486: goto -> 493
    //   489: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   492: athrow
    //   493: goto -> 497
    //   496: dconst_0
    //   497: dsub
    //   498: iload #11
    //   500: ifeq -> 513
    //   503: ldc2_w 0.05
    //   506: goto -> 514
    //   509: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   512: athrow
    //   513: dconst_0
    //   514: dsub
    //   515: lload #8
    //   517: dup2_x2
    //   518: pop2
    //   519: iconst_2
    //   520: anewarray java/lang/Object
    //   523: dup_x2
    //   524: dup_x2
    //   525: pop
    //   526: invokestatic valueOf : (D)Ljava/lang/Double;
    //   529: iconst_1
    //   530: swap
    //   531: aastore
    //   532: dup_x2
    //   533: dup_x2
    //   534: pop
    //   535: invokestatic valueOf : (J)Ljava/lang/Long;
    //   538: iconst_0
    //   539: swap
    //   540: aastore
    //   541: invokestatic k : ([Ljava/lang/Object;)V
    //   544: aload_0
    //   545: getfield s : I
    //   548: iconst_3
    //   549: iload #10
    //   551: ifne -> 625
    //   554: if_icmpne -> 620
    //   557: goto -> 564
    //   560: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   563: athrow
    //   564: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   567: getfield field_1724 : Lnet/minecraft/class_746;
    //   570: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   573: getfield field_1724 : Lnet/minecraft/class_746;
    //   576: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   579: invokevirtual method_10216 : ()D
    //   582: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   585: getfield field_1724 : Lnet/minecraft/class_746;
    //   588: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   591: invokevirtual method_10214 : ()D
    //   594: ldc2_w 0.004999999888241291
    //   597: dsub
    //   598: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   601: getfield field_1724 : Lnet/minecraft/class_746;
    //   604: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   607: invokevirtual method_10215 : ()D
    //   610: invokevirtual method_18800 : (DDD)V
    //   613: goto -> 620
    //   616: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   619: athrow
    //   620: aload_0
    //   621: getfield s : I
    //   624: iconst_4
    //   625: iload #10
    //   627: ifne -> 701
    //   630: if_icmpne -> 696
    //   633: goto -> 640
    //   636: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   639: athrow
    //   640: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   643: getfield field_1724 : Lnet/minecraft/class_746;
    //   646: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   649: getfield field_1724 : Lnet/minecraft/class_746;
    //   652: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   655: invokevirtual method_10216 : ()D
    //   658: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   661: getfield field_1724 : Lnet/minecraft/class_746;
    //   664: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   667: invokevirtual method_10214 : ()D
    //   670: ldc2_w 0.03500000014901161
    //   673: dsub
    //   674: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   677: getfield field_1724 : Lnet/minecraft/class_746;
    //   680: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   683: invokevirtual method_10215 : ()D
    //   686: invokevirtual method_18800 : (DDD)V
    //   689: goto -> 696
    //   692: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   695: athrow
    //   696: aload_0
    //   697: getfield s : I
    //   700: iconst_5
    //   701: if_icmpne -> 777
    //   704: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   707: getfield field_1724 : Lnet/minecraft/class_746;
    //   710: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   713: getfield field_1724 : Lnet/minecraft/class_746;
    //   716: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   719: invokevirtual method_10216 : ()D
    //   722: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   725: getfield field_1724 : Lnet/minecraft/class_746;
    //   728: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   731: invokevirtual method_10214 : ()D
    //   734: ldc2_w 0.20000000298023224
    //   737: dsub
    //   738: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   741: getfield field_1724 : Lnet/minecraft/class_746;
    //   744: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   747: invokevirtual method_10215 : ()D
    //   750: invokevirtual method_18800 : (DDD)V
    //   753: iload #10
    //   755: ifeq -> 777
    //   758: goto -> 765
    //   761: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   764: athrow
    //   765: aload_0
    //   766: iconst_0
    //   767: putfield a : I
    //   770: goto -> 777
    //   773: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   776: athrow
    //   777: return
    // Exception table:
    //   from	to	target	type
    //   36	52	55	wtf/opal/x5
    //   49	82	85	wtf/opal/x5
    //   89	97	100	wtf/opal/x5
    //   117	125	128	wtf/opal/x5
    //   122	166	169	wtf/opal/x5
    //   173	181	184	wtf/opal/x5
    //   178	193	196	wtf/opal/x5
    //   204	212	215	wtf/opal/x5
    //   209	285	288	wtf/opal/x5
    //   296	305	308	wtf/opal/x5
    //   302	335	338	wtf/opal/x5
    //   312	345	348	wtf/opal/x5
    //   342	369	372	wtf/opal/x5
    //   352	379	382	wtf/opal/x5
    //   376	401	404	wtf/opal/x5
    //   408	417	420	wtf/opal/x5
    //   413	425	428	wtf/opal/x5
    //   438	476	479	wtf/opal/x5
    //   464	486	489	wtf/opal/x5
    //   497	509	509	wtf/opal/x5
    //   549	557	560	wtf/opal/x5
    //   554	613	616	wtf/opal/x5
    //   625	633	636	wtf/opal/x5
    //   630	689	692	wtf/opal/x5
    //   701	758	761	wtf/opal/x5
    //   704	770	773	wtf/opal/x5
  }
  
  static {
    long l = b ^ 0x70CE1D9745F0L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/NoPadding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */