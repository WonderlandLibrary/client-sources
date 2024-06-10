package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class p0 extends u_<xw> {
  private int m;
  
  private int H;
  
  private int O;
  
  private double w;
  
  private final gm<u0> o;
  
  private final gm<lb> c;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] d;
  
  private static final Map e;
  
  public p0(xw paramxw, long paramLong) {
    super(paramxw);
    boolean bool = pv.D();
    try {
      this.o = this::lambda$new$0;
      this.c = this::lambda$new$1;
      if (d.D() != null) {
        try {
        
        } catch (x5 x5) {
          throw b(null);
        } 
        pv.m(!bool);
      } 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.w = 1.0E-10D;
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l1.WATCHDOG_DYNAMIC;
  }
  
  private void lambda$new$1(lb paramlb) {
    long l1 = a ^ 0x54F45351F4B5L;
    long l2 = l1 ^ 0x2E7E35276859L;
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
    try {
      if (bool)
        try {
          if (((xw)y(new Object[0])).B.z().booleanValue()) {
            new Object[1];
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
    //   0: getstatic wtf/opal/p0.a : J
    //   3: ldc2_w 50070174562968
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 112631048931543
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 95673492006516
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 136900137242336
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic b : ()Z
    //   34: istore #10
    //   36: aload_1
    //   37: iconst_0
    //   38: anewarray java/lang/Object
    //   41: invokevirtual K : ([Ljava/lang/Object;)Z
    //   44: iload #10
    //   46: ifeq -> 89
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
    //   91: ifeq -> 123
    //   94: ifne -> 105
    //   97: goto -> 104
    //   100: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: return
    //   105: lload #6
    //   107: iconst_1
    //   108: anewarray java/lang/Object
    //   111: dup_x2
    //   112: dup_x2
    //   113: pop
    //   114: invokestatic valueOf : (J)Ljava/lang/Long;
    //   117: iconst_0
    //   118: swap
    //   119: aastore
    //   120: invokestatic I : ([Ljava/lang/Object;)Z
    //   123: iload #10
    //   125: ifeq -> 735
    //   128: ifeq -> 716
    //   131: goto -> 138
    //   134: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   141: getfield field_1690 : Lnet/minecraft/class_315;
    //   144: getfield field_1903 : Lnet/minecraft/class_304;
    //   147: invokevirtual method_1434 : ()Z
    //   150: iload #10
    //   152: ifeq -> 213
    //   155: goto -> 162
    //   158: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: ifeq -> 699
    //   165: goto -> 172
    //   168: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   171: athrow
    //   172: aload_0
    //   173: iconst_0
    //   174: putfield m : I
    //   177: aload_0
    //   178: dup
    //   179: getfield H : I
    //   182: iconst_1
    //   183: iadd
    //   184: putfield H : I
    //   187: aload_0
    //   188: dup
    //   189: getfield O : I
    //   192: iconst_1
    //   193: iadd
    //   194: putfield O : I
    //   197: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   200: getfield field_1724 : Lnet/minecraft/class_746;
    //   203: invokevirtual method_24828 : ()Z
    //   206: goto -> 213
    //   209: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   212: athrow
    //   213: iload #10
    //   215: ifeq -> 244
    //   218: ifeq -> 240
    //   221: goto -> 228
    //   224: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   227: athrow
    //   228: aload_0
    //   229: iconst_0
    //   230: putfield O : I
    //   233: goto -> 240
    //   236: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   239: athrow
    //   240: aload_0
    //   241: getfield O : I
    //   244: iload #10
    //   246: ifeq -> 334
    //   249: ifne -> 330
    //   252: goto -> 259
    //   255: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   258: athrow
    //   259: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   262: getfield field_1724 : Lnet/minecraft/class_746;
    //   265: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   268: getfield field_1724 : Lnet/minecraft/class_746;
    //   271: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   274: invokevirtual method_10216 : ()D
    //   277: ldc2_w 0.41999998688697815
    //   280: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   283: getfield field_1724 : Lnet/minecraft/class_746;
    //   286: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   289: invokevirtual method_10215 : ()D
    //   292: invokevirtual method_18800 : (DDD)V
    //   295: lload #8
    //   297: dconst_0
    //   298: iconst_2
    //   299: anewarray java/lang/Object
    //   302: dup_x2
    //   303: dup_x2
    //   304: pop
    //   305: invokestatic valueOf : (D)Ljava/lang/Double;
    //   308: iconst_1
    //   309: swap
    //   310: aastore
    //   311: dup_x2
    //   312: dup_x2
    //   313: pop
    //   314: invokestatic valueOf : (J)Ljava/lang/Long;
    //   317: iconst_0
    //   318: swap
    //   319: aastore
    //   320: invokestatic k : ([Ljava/lang/Object;)V
    //   323: goto -> 330
    //   326: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   329: athrow
    //   330: aload_0
    //   331: getfield O : I
    //   334: iconst_1
    //   335: iload #10
    //   337: ifeq -> 483
    //   340: if_icmpne -> 478
    //   343: goto -> 350
    //   346: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   349: athrow
    //   350: lload #6
    //   352: iconst_1
    //   353: anewarray java/lang/Object
    //   356: dup_x2
    //   357: dup_x2
    //   358: pop
    //   359: invokestatic valueOf : (J)Ljava/lang/Long;
    //   362: iconst_0
    //   363: swap
    //   364: aastore
    //   365: invokestatic I : ([Ljava/lang/Object;)Z
    //   368: iload #10
    //   370: ifeq -> 482
    //   373: goto -> 380
    //   376: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   379: athrow
    //   380: ifeq -> 478
    //   383: goto -> 390
    //   386: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   389: athrow
    //   390: lload #4
    //   392: iconst_1
    //   393: anewarray java/lang/Object
    //   396: dup_x2
    //   397: dup_x2
    //   398: pop
    //   399: invokestatic valueOf : (J)Ljava/lang/Long;
    //   402: iconst_0
    //   403: swap
    //   404: aastore
    //   405: invokestatic m : ([Ljava/lang/Object;)D
    //   408: aload_0
    //   409: getfield H : I
    //   412: sipush #32569
    //   415: ldc2_w 8453236614538466089
    //   418: lload_2
    //   419: lxor
    //   420: <illegal opcode> y : (IJ)I
    //   425: if_icmpge -> 445
    //   428: goto -> 435
    //   431: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   434: athrow
    //   435: ldc2_w 0.6
    //   438: goto -> 448
    //   441: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   444: athrow
    //   445: ldc2_w 0.7
    //   448: dmul
    //   449: lload #8
    //   451: dup2_x2
    //   452: pop2
    //   453: iconst_2
    //   454: anewarray java/lang/Object
    //   457: dup_x2
    //   458: dup_x2
    //   459: pop
    //   460: invokestatic valueOf : (D)Ljava/lang/Double;
    //   463: iconst_1
    //   464: swap
    //   465: aastore
    //   466: dup_x2
    //   467: dup_x2
    //   468: pop
    //   469: invokestatic valueOf : (J)Ljava/lang/Long;
    //   472: iconst_0
    //   473: swap
    //   474: aastore
    //   475: invokestatic k : ([Ljava/lang/Object;)V
    //   478: aload_0
    //   479: getfield O : I
    //   482: iconst_3
    //   483: iload #10
    //   485: ifeq -> 559
    //   488: if_icmpne -> 554
    //   491: goto -> 498
    //   494: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   497: athrow
    //   498: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   501: getfield field_1724 : Lnet/minecraft/class_746;
    //   504: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   507: getfield field_1724 : Lnet/minecraft/class_746;
    //   510: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   513: invokevirtual method_10216 : ()D
    //   516: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   519: getfield field_1724 : Lnet/minecraft/class_746;
    //   522: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   525: invokevirtual method_10214 : ()D
    //   528: ldc2_w 0.004999999888241291
    //   531: dsub
    //   532: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   535: getfield field_1724 : Lnet/minecraft/class_746;
    //   538: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   541: invokevirtual method_10215 : ()D
    //   544: invokevirtual method_18800 : (DDD)V
    //   547: goto -> 554
    //   550: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   553: athrow
    //   554: aload_0
    //   555: getfield O : I
    //   558: iconst_4
    //   559: iload #10
    //   561: ifeq -> 635
    //   564: if_icmpne -> 630
    //   567: goto -> 574
    //   570: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   573: athrow
    //   574: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   577: getfield field_1724 : Lnet/minecraft/class_746;
    //   580: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   583: getfield field_1724 : Lnet/minecraft/class_746;
    //   586: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   589: invokevirtual method_10216 : ()D
    //   592: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   595: getfield field_1724 : Lnet/minecraft/class_746;
    //   598: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   601: invokevirtual method_10214 : ()D
    //   604: ldc2_w 0.03500000014901161
    //   607: dsub
    //   608: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   611: getfield field_1724 : Lnet/minecraft/class_746;
    //   614: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   617: invokevirtual method_10215 : ()D
    //   620: invokevirtual method_18800 : (DDD)V
    //   623: goto -> 630
    //   626: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   629: athrow
    //   630: aload_0
    //   631: getfield O : I
    //   634: iconst_5
    //   635: if_icmpne -> 1211
    //   638: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   641: getfield field_1724 : Lnet/minecraft/class_746;
    //   644: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   647: getfield field_1724 : Lnet/minecraft/class_746;
    //   650: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   653: invokevirtual method_10216 : ()D
    //   656: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   659: getfield field_1724 : Lnet/minecraft/class_746;
    //   662: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   665: invokevirtual method_10214 : ()D
    //   668: ldc2_w 0.20000000298023224
    //   671: dsub
    //   672: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   675: getfield field_1724 : Lnet/minecraft/class_746;
    //   678: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   681: invokevirtual method_10215 : ()D
    //   684: invokevirtual method_18800 : (DDD)V
    //   687: iload #10
    //   689: ifne -> 1211
    //   692: goto -> 699
    //   695: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   698: athrow
    //   699: aload_0
    //   700: iconst_0
    //   701: putfield H : I
    //   704: iload #10
    //   706: ifne -> 1211
    //   709: goto -> 716
    //   712: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   715: athrow
    //   716: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   719: getfield field_1690 : Lnet/minecraft/class_315;
    //   722: getfield field_1903 : Lnet/minecraft/class_304;
    //   725: invokevirtual method_1434 : ()Z
    //   728: goto -> 735
    //   731: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   734: athrow
    //   735: iload #10
    //   737: ifeq -> 1084
    //   740: ifeq -> 1046
    //   743: goto -> 750
    //   746: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   749: athrow
    //   750: aload_0
    //   751: iconst_0
    //   752: putfield m : I
    //   755: aload_0
    //   756: dup
    //   757: getfield H : I
    //   760: iconst_1
    //   761: iadd
    //   762: putfield H : I
    //   765: aload_0
    //   766: dup
    //   767: getfield O : I
    //   770: iconst_1
    //   771: iadd
    //   772: putfield O : I
    //   775: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   778: getfield field_1724 : Lnet/minecraft/class_746;
    //   781: invokevirtual method_24828 : ()Z
    //   784: iload #10
    //   786: ifeq -> 822
    //   789: goto -> 796
    //   792: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   795: athrow
    //   796: ifeq -> 818
    //   799: goto -> 806
    //   802: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   805: athrow
    //   806: aload_0
    //   807: iconst_0
    //   808: putfield O : I
    //   811: goto -> 818
    //   814: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   817: athrow
    //   818: aload_0
    //   819: getfield O : I
    //   822: iload #10
    //   824: ifeq -> 884
    //   827: ifne -> 880
    //   830: goto -> 837
    //   833: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   836: athrow
    //   837: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   840: getfield field_1724 : Lnet/minecraft/class_746;
    //   843: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   846: getfield field_1724 : Lnet/minecraft/class_746;
    //   849: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   852: invokevirtual method_10216 : ()D
    //   855: ldc2_w 0.41999998688697815
    //   858: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   861: getfield field_1724 : Lnet/minecraft/class_746;
    //   864: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   867: invokevirtual method_10215 : ()D
    //   870: invokevirtual method_18800 : (DDD)V
    //   873: goto -> 880
    //   876: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   879: athrow
    //   880: aload_0
    //   881: getfield O : I
    //   884: iconst_1
    //   885: iload #10
    //   887: ifeq -> 948
    //   890: if_icmpne -> 943
    //   893: goto -> 900
    //   896: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   899: athrow
    //   900: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   903: getfield field_1724 : Lnet/minecraft/class_746;
    //   906: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   909: getfield field_1724 : Lnet/minecraft/class_746;
    //   912: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   915: invokevirtual method_10216 : ()D
    //   918: ldc2_w 0.33
    //   921: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   924: getfield field_1724 : Lnet/minecraft/class_746;
    //   927: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   930: invokevirtual method_10215 : ()D
    //   933: invokevirtual method_18800 : (DDD)V
    //   936: goto -> 943
    //   939: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   942: athrow
    //   943: aload_0
    //   944: getfield O : I
    //   947: iconst_2
    //   948: iload #10
    //   950: ifeq -> 1033
    //   953: if_icmpne -> 1016
    //   956: goto -> 963
    //   959: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   962: athrow
    //   963: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   966: getfield field_1724 : Lnet/minecraft/class_746;
    //   969: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   972: getfield field_1724 : Lnet/minecraft/class_746;
    //   975: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   978: invokevirtual method_10216 : ()D
    //   981: dconst_1
    //   982: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   985: getfield field_1724 : Lnet/minecraft/class_746;
    //   988: invokevirtual method_23318 : ()D
    //   991: dconst_1
    //   992: drem
    //   993: dsub
    //   994: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   997: getfield field_1724 : Lnet/minecraft/class_746;
    //   1000: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1003: invokevirtual method_10215 : ()D
    //   1006: invokevirtual method_18800 : (DDD)V
    //   1009: goto -> 1016
    //   1012: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1015: athrow
    //   1016: aload_0
    //   1017: iload #10
    //   1019: ifeq -> 1037
    //   1022: getfield O : I
    //   1025: iconst_2
    //   1026: goto -> 1033
    //   1029: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1032: athrow
    //   1033: if_icmplt -> 1211
    //   1036: aload_0
    //   1037: iconst_m1
    //   1038: putfield O : I
    //   1041: iload #10
    //   1043: ifne -> 1211
    //   1046: aload_0
    //   1047: sipush #16438
    //   1050: ldc2_w 8246372080027773989
    //   1053: lload_2
    //   1054: lxor
    //   1055: <illegal opcode> y : (IJ)I
    //   1060: putfield O : I
    //   1063: aload_0
    //   1064: dup
    //   1065: getfield m : I
    //   1068: iconst_1
    //   1069: iadd
    //   1070: putfield m : I
    //   1073: aload_0
    //   1074: getfield m : I
    //   1077: goto -> 1084
    //   1080: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1083: athrow
    //   1084: sipush #13286
    //   1087: ldc2_w 7694002133678055415
    //   1090: lload_2
    //   1091: lxor
    //   1092: <illegal opcode> y : (IJ)I
    //   1097: iload #10
    //   1099: ifeq -> 1203
    //   1102: if_icmpge -> 1174
    //   1105: goto -> 1112
    //   1108: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1111: athrow
    //   1112: aload_0
    //   1113: getfield H : I
    //   1116: iconst_5
    //   1117: iload #10
    //   1119: ifeq -> 1203
    //   1122: goto -> 1129
    //   1125: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1128: athrow
    //   1129: if_icmple -> 1174
    //   1132: goto -> 1139
    //   1135: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1138: athrow
    //   1139: lload #8
    //   1141: dconst_0
    //   1142: iconst_2
    //   1143: anewarray java/lang/Object
    //   1146: dup_x2
    //   1147: dup_x2
    //   1148: pop
    //   1149: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1152: iconst_1
    //   1153: swap
    //   1154: aastore
    //   1155: dup_x2
    //   1156: dup_x2
    //   1157: pop
    //   1158: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1161: iconst_0
    //   1162: swap
    //   1163: aastore
    //   1164: invokestatic k : ([Ljava/lang/Object;)V
    //   1167: goto -> 1174
    //   1170: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1173: athrow
    //   1174: aload_0
    //   1175: iload #10
    //   1177: ifeq -> 1207
    //   1180: getfield m : I
    //   1183: sipush #13286
    //   1186: ldc2_w 7694002133678055415
    //   1189: lload_2
    //   1190: lxor
    //   1191: <illegal opcode> y : (IJ)I
    //   1196: goto -> 1203
    //   1199: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1202: athrow
    //   1203: if_icmplt -> 1211
    //   1206: aload_0
    //   1207: iconst_0
    //   1208: putfield H : I
    //   1211: return
    // Exception table:
    //   from	to	target	type
    //   36	52	55	wtf/opal/x5
    //   49	82	85	wtf/opal/x5
    //   89	97	100	wtf/opal/x5
    //   123	131	134	wtf/opal/x5
    //   128	155	158	wtf/opal/x5
    //   138	165	168	wtf/opal/x5
    //   162	206	209	wtf/opal/x5
    //   213	221	224	wtf/opal/x5
    //   218	233	236	wtf/opal/x5
    //   244	252	255	wtf/opal/x5
    //   249	323	326	wtf/opal/x5
    //   334	343	346	wtf/opal/x5
    //   340	373	376	wtf/opal/x5
    //   350	383	386	wtf/opal/x5
    //   380	428	431	wtf/opal/x5
    //   390	441	441	wtf/opal/x5
    //   483	491	494	wtf/opal/x5
    //   488	547	550	wtf/opal/x5
    //   559	567	570	wtf/opal/x5
    //   564	623	626	wtf/opal/x5
    //   635	692	695	wtf/opal/x5
    //   638	709	712	wtf/opal/x5
    //   699	728	731	wtf/opal/x5
    //   735	743	746	wtf/opal/x5
    //   740	789	792	wtf/opal/x5
    //   750	799	802	wtf/opal/x5
    //   796	811	814	wtf/opal/x5
    //   822	830	833	wtf/opal/x5
    //   827	873	876	wtf/opal/x5
    //   884	893	896	wtf/opal/x5
    //   890	936	939	wtf/opal/x5
    //   948	956	959	wtf/opal/x5
    //   953	1009	1012	wtf/opal/x5
    //   1016	1026	1029	wtf/opal/x5
    //   1037	1077	1080	wtf/opal/x5
    //   1084	1105	1108	wtf/opal/x5
    //   1102	1122	1125	wtf/opal/x5
    //   1112	1132	1135	wtf/opal/x5
    //   1129	1167	1170	wtf/opal/x5
    //   1174	1196	1199	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -3445792730827980389
    //   3: ldc2_w -4378786279885537913
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 187967011900239
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/p0.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/p0.e : Ljava/util/Map;
    //   38: getstatic wtf/opal/p0.a : J
    //   41: ldc2_w 76069396429145
    //   44: lxor
    //   45: lstore_0
    //   46: ldc_w 'DES/CBC/NoPadding'
    //   49: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   52: dup
    //   53: astore_2
    //   54: iconst_2
    //   55: ldc_w 'DES'
    //   58: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   61: bipush #8
    //   63: newarray byte
    //   65: dup
    //   66: iconst_0
    //   67: lload_0
    //   68: bipush #56
    //   70: lushr
    //   71: l2i
    //   72: i2b
    //   73: bastore
    //   74: iconst_1
    //   75: istore_3
    //   76: iload_3
    //   77: bipush #8
    //   79: if_icmpge -> 102
    //   82: dup
    //   83: iload_3
    //   84: lload_0
    //   85: iload_3
    //   86: bipush #8
    //   88: imul
    //   89: lshl
    //   90: bipush #56
    //   92: lushr
    //   93: l2i
    //   94: i2b
    //   95: bastore
    //   96: iinc #3, 1
    //   99: goto -> 76
    //   102: new javax/crypto/spec/DESKeySpec
    //   105: dup_x1
    //   106: swap
    //   107: invokespecial <init> : ([B)V
    //   110: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   113: new javax/crypto/spec/IvParameterSpec
    //   116: dup
    //   117: bipush #8
    //   119: newarray byte
    //   121: invokespecial <init> : ([B)V
    //   124: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   127: iconst_3
    //   128: newarray long
    //   130: astore #8
    //   132: iconst_0
    //   133: istore #5
    //   135: ldc_w 'ÿMvtG¥zÁ9g.ðßí¥R\\f_ÏìÑi'
    //   138: dup
    //   139: astore #6
    //   141: invokevirtual length : ()I
    //   144: istore #7
    //   146: iconst_0
    //   147: istore #4
    //   149: aload #6
    //   151: iload #4
    //   153: iinc #4, 8
    //   156: iload #4
    //   158: invokevirtual substring : (II)Ljava/lang/String;
    //   161: ldc_w 'ISO-8859-1'
    //   164: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   167: astore #9
    //   169: aload #8
    //   171: iload #5
    //   173: iinc #5, 1
    //   176: aload #9
    //   178: iconst_0
    //   179: baload
    //   180: i2l
    //   181: ldc2_w 255
    //   184: land
    //   185: bipush #56
    //   187: lshl
    //   188: aload #9
    //   190: iconst_1
    //   191: baload
    //   192: i2l
    //   193: ldc2_w 255
    //   196: land
    //   197: bipush #48
    //   199: lshl
    //   200: lor
    //   201: aload #9
    //   203: iconst_2
    //   204: baload
    //   205: i2l
    //   206: ldc2_w 255
    //   209: land
    //   210: bipush #40
    //   212: lshl
    //   213: lor
    //   214: aload #9
    //   216: iconst_3
    //   217: baload
    //   218: i2l
    //   219: ldc2_w 255
    //   222: land
    //   223: bipush #32
    //   225: lshl
    //   226: lor
    //   227: aload #9
    //   229: iconst_4
    //   230: baload
    //   231: i2l
    //   232: ldc2_w 255
    //   235: land
    //   236: bipush #24
    //   238: lshl
    //   239: lor
    //   240: aload #9
    //   242: iconst_5
    //   243: baload
    //   244: i2l
    //   245: ldc2_w 255
    //   248: land
    //   249: bipush #16
    //   251: lshl
    //   252: lor
    //   253: aload #9
    //   255: bipush #6
    //   257: baload
    //   258: i2l
    //   259: ldc2_w 255
    //   262: land
    //   263: bipush #8
    //   265: lshl
    //   266: lor
    //   267: aload #9
    //   269: bipush #7
    //   271: baload
    //   272: i2l
    //   273: ldc2_w 255
    //   276: land
    //   277: lor
    //   278: iconst_m1
    //   279: goto -> 305
    //   282: lastore
    //   283: iload #4
    //   285: iload #7
    //   287: if_icmplt -> 149
    //   290: aload #8
    //   292: putstatic wtf/opal/p0.b : [J
    //   295: iconst_3
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/p0.d : [Ljava/lang/Integer;
    //   302: goto -> 507
    //   305: dup_x2
    //   306: pop
    //   307: lstore #10
    //   309: bipush #8
    //   311: newarray byte
    //   313: dup
    //   314: iconst_0
    //   315: lload #10
    //   317: bipush #56
    //   319: lushr
    //   320: l2i
    //   321: i2b
    //   322: bastore
    //   323: dup
    //   324: iconst_1
    //   325: lload #10
    //   327: bipush #48
    //   329: lushr
    //   330: l2i
    //   331: i2b
    //   332: bastore
    //   333: dup
    //   334: iconst_2
    //   335: lload #10
    //   337: bipush #40
    //   339: lushr
    //   340: l2i
    //   341: i2b
    //   342: bastore
    //   343: dup
    //   344: iconst_3
    //   345: lload #10
    //   347: bipush #32
    //   349: lushr
    //   350: l2i
    //   351: i2b
    //   352: bastore
    //   353: dup
    //   354: iconst_4
    //   355: lload #10
    //   357: bipush #24
    //   359: lushr
    //   360: l2i
    //   361: i2b
    //   362: bastore
    //   363: dup
    //   364: iconst_5
    //   365: lload #10
    //   367: bipush #16
    //   369: lushr
    //   370: l2i
    //   371: i2b
    //   372: bastore
    //   373: dup
    //   374: bipush #6
    //   376: lload #10
    //   378: bipush #8
    //   380: lushr
    //   381: l2i
    //   382: i2b
    //   383: bastore
    //   384: dup
    //   385: bipush #7
    //   387: lload #10
    //   389: l2i
    //   390: i2b
    //   391: bastore
    //   392: aload_2
    //   393: swap
    //   394: invokevirtual doFinal : ([B)[B
    //   397: astore #12
    //   399: aload #12
    //   401: iconst_0
    //   402: baload
    //   403: i2l
    //   404: ldc2_w 255
    //   407: land
    //   408: bipush #56
    //   410: lshl
    //   411: aload #12
    //   413: iconst_1
    //   414: baload
    //   415: i2l
    //   416: ldc2_w 255
    //   419: land
    //   420: bipush #48
    //   422: lshl
    //   423: lor
    //   424: aload #12
    //   426: iconst_2
    //   427: baload
    //   428: i2l
    //   429: ldc2_w 255
    //   432: land
    //   433: bipush #40
    //   435: lshl
    //   436: lor
    //   437: aload #12
    //   439: iconst_3
    //   440: baload
    //   441: i2l
    //   442: ldc2_w 255
    //   445: land
    //   446: bipush #32
    //   448: lshl
    //   449: lor
    //   450: aload #12
    //   452: iconst_4
    //   453: baload
    //   454: i2l
    //   455: ldc2_w 255
    //   458: land
    //   459: bipush #24
    //   461: lshl
    //   462: lor
    //   463: aload #12
    //   465: iconst_5
    //   466: baload
    //   467: i2l
    //   468: ldc2_w 255
    //   471: land
    //   472: bipush #16
    //   474: lshl
    //   475: lor
    //   476: aload #12
    //   478: bipush #6
    //   480: baload
    //   481: i2l
    //   482: ldc2_w 255
    //   485: land
    //   486: bipush #8
    //   488: lshl
    //   489: lor
    //   490: aload #12
    //   492: bipush #7
    //   494: baload
    //   495: i2l
    //   496: ldc2_w 255
    //   499: land
    //   500: lor
    //   501: dup2_x1
    //   502: pop2
    //   503: pop
    //   504: goto -> 282
    //   507: return
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x153E;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = b[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])e.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/p0", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      d[i] = Integer.valueOf(j);
    } 
    return d[i].intValue();
  }
  
  private static int a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc_w [Ljava/lang/Object;
    //   16: aload_2
    //   17: invokevirtual parameterCount : ()I
    //   20: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   23: iconst_0
    //   24: iconst_3
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_0
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload_1
    //   39: aastore
    //   40: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   43: aload_2
    //   44: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   47: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   50: goto -> 104
    //   53: astore #4
    //   55: new java/lang/RuntimeException
    //   58: dup
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'wtf/opal/p0'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w ' : '
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ' : '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: aload_3
    //   105: areturn
    // Exception table:
    //   from	to	target	type
    //   9	50	53	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\p0.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */