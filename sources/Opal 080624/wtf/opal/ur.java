package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2828;
import wtf.opal.mixin.PlayerMoveC2SPacketAccessor;

public final class ur extends u_<h> {
  private int w;
  
  private boolean e;
  
  private boolean o;
  
  private double s;
  
  private class_243 B = new class_243(0.0D, 0.0D, 0.0D);
  
  private final gm<b6> I = this::lambda$new$0;
  
  private final gm<p> D = this::lambda$new$1;
  
  private final gm<lu> L = this::lambda$new$2;
  
  private final gm<lb> V = this::lambda$new$3;
  
  private static final long a = on.a(-5013328589980309739L, -1825071236363230484L, MethodHandles.lookup().lookupClass()).a(112100019475664L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] f;
  
  private static final Integer[] g;
  
  private static final Map h;
  
  public ur(h paramh) {
    super(paramh);
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l0.FIREBALL;
  }
  
  public void X() {
    long l1 = a ^ 0x4BDEF08EBA2BL;
    long l2 = l1 ^ 0x9A83C2A3A86L;
    new Object[2];
    (new Object[2])[1] = Double.valueOf(0.0D);
    new Object[2];
    l_.k(new Object[] { Long.valueOf(l2) });
    super.X();
  }
  
  public void s(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.w = 0;
    this.e = false;
    this.o = false;
    this.B = new class_243(0.0D, 0.0D, 0.0D);
    this.s = 0.33000001311302185D;
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$new$3(lb paramlb) {
    long l = a ^ 0x903ADBDDAE1L;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    int i = uz.r();
    try {
      if (i == 0)
        try {
          if (class_2596 instanceof class_2828.class_2830) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    class_2828.class_2830 class_2830 = (class_2828.class_2830)class_2596;
    if (!this.e) {
      PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2830;
      playerMoveC2SPacketAccessor.setYaw(b9.c.field_1724.method_36454() + 180.0F);
      playerMoveC2SPacketAccessor.setPitch(55.0F);
    } 
  }
  
  private void lambda$new$2(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/ur.a : J
    //   3: ldc2_w 114801298698600
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic r : ()I
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: istore #4
    //   23: aload #6
    //   25: iload #4
    //   27: ifne -> 52
    //   30: instanceof net/minecraft/class_2743
    //   33: ifeq -> 117
    //   36: goto -> 43
    //   39: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #6
    //   45: goto -> 52
    //   48: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: checkcast net/minecraft/class_2743
    //   55: astore #5
    //   57: aload #5
    //   59: invokevirtual method_11818 : ()I
    //   62: iload #4
    //   64: ifne -> 109
    //   67: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   70: getfield field_1724 : Lnet/minecraft/class_746;
    //   73: invokevirtual method_5628 : ()I
    //   76: if_icmpne -> 117
    //   79: goto -> 86
    //   82: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: aload_0
    //   87: iload #4
    //   89: ifne -> 113
    //   92: goto -> 99
    //   95: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: getfield w : I
    //   102: goto -> 109
    //   105: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   108: athrow
    //   109: ifne -> 117
    //   112: aload_0
    //   113: iconst_1
    //   114: putfield e : Z
    //   117: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   30	45	48	wtf/opal/x5
    //   57	79	82	wtf/opal/x5
    //   67	92	95	wtf/opal/x5
    //   86	102	105	wtf/opal/x5
  }
  
  private void lambda$new$1(p paramp) {
    long l = a ^ 0x114084C8DB42L;
    int i = uz.M();
    try {
      if (i != 0)
        try {
          if (this.o) {
          
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
      if (!this.o)
        paramp.q(new Object[] { new class_243(0.0D, paramp.S(new Object[0]).method_10214(), 0.0D) }); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/ur.a : J
    //   3: ldc2_w 30305734038981
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 125790510005756
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 90695365415226
    //   20: lxor
    //   21: dup2
    //   22: bipush #48
    //   24: lushr
    //   25: l2i
    //   26: istore #6
    //   28: dup2
    //   29: bipush #16
    //   31: lshl
    //   32: bipush #48
    //   34: lushr
    //   35: l2i
    //   36: istore #7
    //   38: dup2
    //   39: bipush #32
    //   41: lshl
    //   42: bipush #32
    //   44: lushr
    //   45: l2i
    //   46: istore #8
    //   48: pop2
    //   49: dup2
    //   50: ldc2_w 98847970268520
    //   53: lxor
    //   54: lstore #9
    //   56: pop2
    //   57: invokestatic M : ()I
    //   60: istore #11
    //   62: aload_1
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokevirtual W : ([Ljava/lang/Object;)Z
    //   70: iload #11
    //   72: ifeq -> 90
    //   75: ifne -> 86
    //   78: goto -> 85
    //   81: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: return
    //   86: aload_0
    //   87: getfield e : Z
    //   90: iload #11
    //   92: ifeq -> 642
    //   95: ifeq -> 631
    //   98: goto -> 105
    //   101: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload_0
    //   106: dup
    //   107: getfield w : I
    //   110: iconst_1
    //   111: iadd
    //   112: putfield w : I
    //   115: aload_0
    //   116: getfield w : I
    //   119: sipush #20785
    //   122: ldc2_w 2523523660714900084
    //   125: lload_2
    //   126: lxor
    //   127: <illegal opcode> u : (IJ)I
    //   132: iload #11
    //   134: ifeq -> 374
    //   137: goto -> 144
    //   140: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: if_icmpge -> 338
    //   147: goto -> 154
    //   150: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: aload_0
    //   155: getfield w : I
    //   158: iconst_3
    //   159: irem
    //   160: iload #11
    //   162: ifeq -> 428
    //   165: goto -> 172
    //   168: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   171: athrow
    //   172: ifeq -> 410
    //   175: goto -> 182
    //   178: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   181: athrow
    //   182: aload_0
    //   183: getfield w : I
    //   186: iload #11
    //   188: ifeq -> 248
    //   191: goto -> 198
    //   194: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   197: athrow
    //   198: sipush #727
    //   201: ldc2_w 5291558095084464534
    //   204: lload_2
    //   205: lxor
    //   206: <illegal opcode> u : (IJ)I
    //   211: if_icmplt -> 263
    //   214: goto -> 221
    //   217: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   220: athrow
    //   221: aload_0
    //   222: iload #11
    //   224: ifeq -> 252
    //   227: goto -> 234
    //   230: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   233: athrow
    //   234: getfield s : D
    //   237: ldc2_w 0.2
    //   240: dcmpl
    //   241: goto -> 248
    //   244: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: ifle -> 263
    //   251: aload_0
    //   252: dup
    //   253: getfield s : D
    //   256: ldc2_w 0.02
    //   259: dsub
    //   260: putfield s : D
    //   263: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   266: getfield field_1724 : Lnet/minecraft/class_746;
    //   269: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   272: getfield field_1724 : Lnet/minecraft/class_746;
    //   275: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   278: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   281: aload_0
    //   282: iload #11
    //   284: ifeq -> 324
    //   287: getfield w : I
    //   290: sipush #3491
    //   293: ldc2_w 6710222491262101223
    //   296: lload_2
    //   297: lxor
    //   298: <illegal opcode> u : (IJ)I
    //   303: if_icmpge -> 323
    //   306: goto -> 313
    //   309: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   312: athrow
    //   313: ldc2_w 0.4300000071525574
    //   316: goto -> 327
    //   319: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   322: athrow
    //   323: aload_0
    //   324: getfield s : D
    //   327: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   330: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   333: iload #11
    //   335: ifne -> 410
    //   338: aload_0
    //   339: getfield w : I
    //   342: iload #11
    //   344: ifeq -> 428
    //   347: goto -> 354
    //   350: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   353: athrow
    //   354: sipush #31056
    //   357: ldc2_w 523456198681186838
    //   360: lload_2
    //   361: lxor
    //   362: <illegal opcode> u : (IJ)I
    //   367: goto -> 374
    //   370: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   373: athrow
    //   374: if_icmpge -> 410
    //   377: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   380: getfield field_1724 : Lnet/minecraft/class_746;
    //   383: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   386: getfield field_1724 : Lnet/minecraft/class_746;
    //   389: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   392: dconst_0
    //   393: ldc2_w 0.028
    //   396: dconst_0
    //   397: invokevirtual method_1031 : (DDD)Lnet/minecraft/class_243;
    //   400: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   403: goto -> 410
    //   406: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   409: athrow
    //   410: lload #4
    //   412: iconst_1
    //   413: anewarray java/lang/Object
    //   416: dup_x2
    //   417: dup_x2
    //   418: pop
    //   419: invokestatic valueOf : (J)Ljava/lang/Long;
    //   422: iconst_0
    //   423: swap
    //   424: aastore
    //   425: invokestatic I : ([Ljava/lang/Object;)Z
    //   428: iload #11
    //   430: ifeq -> 522
    //   433: ifeq -> 518
    //   436: goto -> 443
    //   439: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   442: athrow
    //   443: aload_0
    //   444: getfield w : I
    //   447: iconst_2
    //   448: iload #11
    //   450: ifeq -> 547
    //   453: goto -> 460
    //   456: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   459: athrow
    //   460: if_icmpne -> 518
    //   463: goto -> 470
    //   466: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   469: athrow
    //   470: iconst_0
    //   471: anewarray java/lang/Object
    //   474: invokestatic U : ([Ljava/lang/Object;)F
    //   477: f2d
    //   478: ldc2_w 2.89
    //   481: dmul
    //   482: lload #9
    //   484: dup2_x2
    //   485: pop2
    //   486: iconst_2
    //   487: anewarray java/lang/Object
    //   490: dup_x2
    //   491: dup_x2
    //   492: pop
    //   493: invokestatic valueOf : (D)Ljava/lang/Double;
    //   496: iconst_1
    //   497: swap
    //   498: aastore
    //   499: dup_x2
    //   500: dup_x2
    //   501: pop
    //   502: invokestatic valueOf : (J)Ljava/lang/Long;
    //   505: iconst_0
    //   506: swap
    //   507: aastore
    //   508: invokestatic k : ([Ljava/lang/Object;)V
    //   511: goto -> 518
    //   514: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   517: athrow
    //   518: aload_0
    //   519: getfield w : I
    //   522: iload #11
    //   524: ifeq -> 566
    //   527: sipush #23301
    //   530: ldc2_w 1351282209748447301
    //   533: lload_2
    //   534: lxor
    //   535: <illegal opcode> u : (IJ)I
    //   540: goto -> 547
    //   543: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   546: athrow
    //   547: if_icmple -> 1005
    //   550: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   553: getfield field_1724 : Lnet/minecraft/class_746;
    //   556: invokevirtual method_24828 : ()Z
    //   559: goto -> 566
    //   562: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   565: athrow
    //   566: ifeq -> 1005
    //   569: aload_0
    //   570: iconst_0
    //   571: anewarray java/lang/Object
    //   574: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   577: checkcast wtf/opal/h
    //   580: iload #6
    //   582: i2s
    //   583: iload #7
    //   585: i2s
    //   586: iload #8
    //   588: iconst_3
    //   589: anewarray java/lang/Object
    //   592: dup_x1
    //   593: swap
    //   594: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   597: iconst_2
    //   598: swap
    //   599: aastore
    //   600: dup_x1
    //   601: swap
    //   602: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   605: iconst_1
    //   606: swap
    //   607: aastore
    //   608: dup_x1
    //   609: swap
    //   610: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   613: iconst_0
    //   614: swap
    //   615: aastore
    //   616: invokevirtual D : ([Ljava/lang/Object;)V
    //   619: iload #11
    //   621: ifne -> 1005
    //   624: goto -> 631
    //   627: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   630: athrow
    //   631: aload_0
    //   632: getfield o : Z
    //   635: goto -> 642
    //   638: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   641: athrow
    //   642: iload #11
    //   644: ifeq -> 658
    //   647: ifne -> 1005
    //   650: goto -> 657
    //   653: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   656: athrow
    //   657: iconst_m1
    //   658: istore #12
    //   660: iconst_0
    //   661: istore #13
    //   663: iload #13
    //   665: sipush #5134
    //   668: ldc2_w 4297513011637927753
    //   671: lload_2
    //   672: lxor
    //   673: <illegal opcode> u : (IJ)I
    //   678: if_icmpge -> 762
    //   681: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   684: getfield field_1724 : Lnet/minecraft/class_746;
    //   687: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   690: getfield field_7547 : Lnet/minecraft/class_2371;
    //   693: iload #13
    //   695: invokevirtual get : (I)Ljava/lang/Object;
    //   698: checkcast net/minecraft/class_1799
    //   701: astore #14
    //   703: iload #11
    //   705: ifeq -> 757
    //   708: aload #14
    //   710: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   713: instanceof net/minecraft/class_1778
    //   716: iload #11
    //   718: ifeq -> 764
    //   721: goto -> 728
    //   724: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   727: athrow
    //   728: ifeq -> 747
    //   731: goto -> 738
    //   734: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   737: athrow
    //   738: iload #13
    //   740: istore #12
    //   742: iload #11
    //   744: ifne -> 762
    //   747: iinc #13, 1
    //   750: goto -> 757
    //   753: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   756: athrow
    //   757: iload #11
    //   759: ifne -> 663
    //   762: iload #12
    //   764: iconst_m1
    //   765: iload #11
    //   767: ifeq -> 904
    //   770: if_icmpne -> 886
    //   773: goto -> 780
    //   776: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   779: athrow
    //   780: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   783: invokevirtual method_1566 : ()Lnet/minecraft/class_374;
    //   786: new net/minecraft/class_370
    //   789: dup
    //   790: getstatic net/minecraft/class_370$class_9037.field_47588 : Lnet/minecraft/class_370$class_9037;
    //   793: sipush #13698
    //   796: ldc2_w 1761878226275478809
    //   799: lload_2
    //   800: lxor
    //   801: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   806: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   809: sipush #10150
    //   812: ldc2_w 8601760682665262908
    //   815: lload_2
    //   816: lxor
    //   817: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   822: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   825: invokespecial <init> : (Lnet/minecraft/class_370$class_9037;Lnet/minecraft/class_2561;Lnet/minecraft/class_2561;)V
    //   828: invokevirtual method_1999 : (Lnet/minecraft/class_368;)V
    //   831: aload_0
    //   832: iconst_0
    //   833: anewarray java/lang/Object
    //   836: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   839: checkcast wtf/opal/h
    //   842: iload #6
    //   844: i2s
    //   845: iload #7
    //   847: i2s
    //   848: iload #8
    //   850: iconst_3
    //   851: anewarray java/lang/Object
    //   854: dup_x1
    //   855: swap
    //   856: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   859: iconst_2
    //   860: swap
    //   861: aastore
    //   862: dup_x1
    //   863: swap
    //   864: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   867: iconst_1
    //   868: swap
    //   869: aastore
    //   870: dup_x1
    //   871: swap
    //   872: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   875: iconst_0
    //   876: swap
    //   877: aastore
    //   878: invokevirtual D : ([Ljava/lang/Object;)V
    //   881: return
    //   882: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   885: athrow
    //   886: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   889: getfield field_1724 : Lnet/minecraft/class_746;
    //   892: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   895: getfield field_7545 : I
    //   898: istore #13
    //   900: iload #13
    //   902: iload #12
    //   904: iload #11
    //   906: ifeq -> 979
    //   909: if_icmpeq -> 944
    //   912: goto -> 919
    //   915: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   918: athrow
    //   919: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   922: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   925: new net/minecraft/class_2868
    //   928: dup
    //   929: iload #12
    //   931: invokespecial <init> : (I)V
    //   934: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   937: goto -> 944
    //   940: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   943: athrow
    //   944: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   947: iload #11
    //   949: ifeq -> 985
    //   952: getfield field_1761 : Lnet/minecraft/class_636;
    //   955: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   958: getfield field_1724 : Lnet/minecraft/class_746;
    //   961: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   964: invokevirtual method_2919 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;
    //   967: pop
    //   968: iload #13
    //   970: iload #12
    //   972: goto -> 979
    //   975: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   978: athrow
    //   979: if_icmpeq -> 1000
    //   982: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   985: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   988: new net/minecraft/class_2868
    //   991: dup
    //   992: iload #13
    //   994: invokespecial <init> : (I)V
    //   997: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1000: aload_0
    //   1001: iconst_1
    //   1002: putfield o : Z
    //   1005: return
    // Exception table:
    //   from	to	target	type
    //   62	78	81	wtf/opal/x5
    //   90	98	101	wtf/opal/x5
    //   95	137	140	wtf/opal/x5
    //   105	147	150	wtf/opal/x5
    //   144	165	168	wtf/opal/x5
    //   154	175	178	wtf/opal/x5
    //   172	191	194	wtf/opal/x5
    //   182	214	217	wtf/opal/x5
    //   198	227	230	wtf/opal/x5
    //   221	241	244	wtf/opal/x5
    //   263	306	309	wtf/opal/x5
    //   287	319	319	wtf/opal/x5
    //   327	347	350	wtf/opal/x5
    //   338	367	370	wtf/opal/x5
    //   374	403	406	wtf/opal/x5
    //   428	436	439	wtf/opal/x5
    //   433	453	456	wtf/opal/x5
    //   443	463	466	wtf/opal/x5
    //   460	511	514	wtf/opal/x5
    //   522	540	543	wtf/opal/x5
    //   547	559	562	wtf/opal/x5
    //   566	624	627	wtf/opal/x5
    //   569	635	638	wtf/opal/x5
    //   642	650	653	wtf/opal/x5
    //   703	721	724	wtf/opal/x5
    //   708	731	734	wtf/opal/x5
    //   742	750	753	wtf/opal/x5
    //   764	773	776	wtf/opal/x5
    //   770	882	882	wtf/opal/x5
    //   904	912	915	wtf/opal/x5
    //   909	937	940	wtf/opal/x5
    //   944	972	975	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x79C1D9DD3649L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "ÓiR!;GÃu3i\030@ÝziýÃp\b +úÚ6¢B£Æ>à!þy\030\023\fDÿÁ\"í×ß\023«ëÉ@>\023ü\027!v").length();
    byte b2 = 40;
    byte b = -1;
    while (true);
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < i; b2++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3A3D;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/ur", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
  }
  
  private static Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(String.class, str);
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return str;
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
    //   66: ldc_w 'wtf/opal/ur'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x9E3;
    if (g[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = f[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])h.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          h.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/ur", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      g[i] = Integer.valueOf(j);
    } 
    return g[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   66: ldc_w 'wtf/opal/ur'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ur.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */