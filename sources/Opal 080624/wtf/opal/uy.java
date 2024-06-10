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

public final class uy extends u_<s> {
  private int J;
  
  private boolean T;
  
  private boolean A;
  
  private double w;
  
  private final gm<p> q = this::lambda$new$0;
  
  private final gm<b6> t = this::lambda$new$1;
  
  private final gm<lu> z = this::lambda$new$2;
  
  private final gm<lb> r = this::lambda$new$3;
  
  private static final long a = on.a(7544669148107943215L, 7304889403103687187L, MethodHandles.lookup().lookupClass()).a(54133955346784L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public uy(long paramLong, s params) {
    super(params);
    String str = ut.R();
    try {
      if (d.D() != null)
        ut.B("GAXTs"); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return kz.FIREBALL;
  }
  
  public void X() {
    long l1 = a ^ 0x7B07D1F3AC56L;
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
    this.J = 0;
    this.T = false;
    this.A = false;
    new Object[1];
    super.s(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$new$3(lb paramlb) {
    long l = a ^ 0x54FAD4918ACDL;
    class_2596 class_2596 = paramlb.J(new Object[0]);
    String str = ut.R();
    try {
      if (str != null)
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
    if (!this.T) {
      PlayerMoveC2SPacketAccessor playerMoveC2SPacketAccessor = (PlayerMoveC2SPacketAccessor)class_2830;
      playerMoveC2SPacketAccessor.setYaw(b9.c.field_1724.method_36454() + 180.0F);
      playerMoveC2SPacketAccessor.setPitch(55.0F);
    } 
  }
  
  private void lambda$new$2(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/uy.a : J
    //   3: ldc2_w 37791074239345
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic R : ()Ljava/lang/String;
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: astore #4
    //   23: aload #6
    //   25: instanceof net/minecraft/class_2743
    //   28: aload #4
    //   30: ifnull -> 144
    //   33: ifeq -> 117
    //   36: goto -> 43
    //   39: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #6
    //   45: checkcast net/minecraft/class_2743
    //   48: astore #5
    //   50: aload #5
    //   52: invokevirtual method_11818 : ()I
    //   55: aload #4
    //   57: ifnull -> 144
    //   60: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   63: getfield field_1724 : Lnet/minecraft/class_746;
    //   66: invokevirtual method_5628 : ()I
    //   69: if_icmpne -> 117
    //   72: goto -> 79
    //   75: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   78: athrow
    //   79: aload_0
    //   80: getfield J : I
    //   83: aload #4
    //   85: ifnull -> 144
    //   88: goto -> 95
    //   91: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   94: athrow
    //   95: ifne -> 117
    //   98: goto -> 105
    //   101: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload_0
    //   106: iconst_1
    //   107: putfield T : Z
    //   110: goto -> 117
    //   113: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   116: athrow
    //   117: aload_1
    //   118: iconst_0
    //   119: anewarray java/lang/Object
    //   122: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   125: astore #6
    //   127: aload #6
    //   129: aload #4
    //   131: ifnull -> 149
    //   134: instanceof net/minecraft/class_2664
    //   137: goto -> 144
    //   140: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   143: athrow
    //   144: ifeq -> 154
    //   147: aload #6
    //   149: checkcast net/minecraft/class_2664
    //   152: astore #5
    //   154: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   50	72	75	wtf/opal/x5
    //   60	88	91	wtf/opal/x5
    //   79	98	101	wtf/opal/x5
    //   95	110	113	wtf/opal/x5
    //   127	137	140	wtf/opal/x5
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/uy.a : J
    //   3: ldc2_w 55995608894065
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 118488068043829
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 83395054360819
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
    //   50: ldc2_w 70654690401441
    //   53: lxor
    //   54: lstore #9
    //   56: pop2
    //   57: invokestatic R : ()Ljava/lang/String;
    //   60: astore #11
    //   62: aload_1
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokevirtual W : ([Ljava/lang/Object;)Z
    //   70: aload #11
    //   72: ifnull -> 90
    //   75: ifne -> 86
    //   78: goto -> 85
    //   81: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: return
    //   86: aload_0
    //   87: getfield T : Z
    //   90: aload #11
    //   92: ifnull -> 466
    //   95: ifeq -> 455
    //   98: goto -> 105
    //   101: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   104: athrow
    //   105: aload_0
    //   106: dup
    //   107: getfield J : I
    //   110: iconst_1
    //   111: iadd
    //   112: putfield J : I
    //   115: aload_0
    //   116: getfield J : I
    //   119: aload #11
    //   121: ifnull -> 252
    //   124: goto -> 131
    //   127: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: sipush #3431
    //   134: ldc2_w 2816006094047974777
    //   137: lload_2
    //   138: lxor
    //   139: <illegal opcode> a : (IJ)I
    //   144: if_icmpge -> 234
    //   147: goto -> 154
    //   150: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: aload_0
    //   155: getfield J : I
    //   158: iconst_3
    //   159: irem
    //   160: aload #11
    //   162: ifnull -> 252
    //   165: goto -> 172
    //   168: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   171: athrow
    //   172: ifeq -> 234
    //   175: goto -> 182
    //   178: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   181: athrow
    //   182: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   185: getfield field_1724 : Lnet/minecraft/class_746;
    //   188: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   191: getfield field_1724 : Lnet/minecraft/class_746;
    //   194: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   197: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   200: aload_0
    //   201: getfield J : I
    //   204: iconst_1
    //   205: if_icmpne -> 225
    //   208: goto -> 215
    //   211: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: ldc2_w 0.4300000071525574
    //   218: goto -> 228
    //   221: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   224: athrow
    //   225: ldc2_w 0.029999999329447746
    //   228: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   231: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   234: lload #4
    //   236: iconst_1
    //   237: anewarray java/lang/Object
    //   240: dup_x2
    //   241: dup_x2
    //   242: pop
    //   243: invokestatic valueOf : (J)Ljava/lang/Long;
    //   246: iconst_0
    //   247: swap
    //   248: aastore
    //   249: invokestatic I : ([Ljava/lang/Object;)Z
    //   252: aload #11
    //   254: ifnull -> 346
    //   257: ifeq -> 342
    //   260: goto -> 267
    //   263: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   266: athrow
    //   267: aload_0
    //   268: getfield J : I
    //   271: iconst_2
    //   272: aload #11
    //   274: ifnull -> 371
    //   277: goto -> 284
    //   280: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   283: athrow
    //   284: if_icmpne -> 342
    //   287: goto -> 294
    //   290: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   293: athrow
    //   294: iconst_0
    //   295: anewarray java/lang/Object
    //   298: invokestatic U : ([Ljava/lang/Object;)F
    //   301: f2d
    //   302: ldc2_w 2.89
    //   305: dmul
    //   306: lload #9
    //   308: dup2_x2
    //   309: pop2
    //   310: iconst_2
    //   311: anewarray java/lang/Object
    //   314: dup_x2
    //   315: dup_x2
    //   316: pop
    //   317: invokestatic valueOf : (D)Ljava/lang/Double;
    //   320: iconst_1
    //   321: swap
    //   322: aastore
    //   323: dup_x2
    //   324: dup_x2
    //   325: pop
    //   326: invokestatic valueOf : (J)Ljava/lang/Long;
    //   329: iconst_0
    //   330: swap
    //   331: aastore
    //   332: invokestatic k : ([Ljava/lang/Object;)V
    //   335: goto -> 342
    //   338: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   341: athrow
    //   342: aload_0
    //   343: getfield J : I
    //   346: aload #11
    //   348: ifnull -> 390
    //   351: sipush #8220
    //   354: ldc2_w 9074226876818798593
    //   357: lload_2
    //   358: lxor
    //   359: <illegal opcode> a : (IJ)I
    //   364: goto -> 371
    //   367: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   370: athrow
    //   371: if_icmple -> 829
    //   374: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   377: getfield field_1724 : Lnet/minecraft/class_746;
    //   380: invokevirtual method_24828 : ()Z
    //   383: goto -> 390
    //   386: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   389: athrow
    //   390: ifeq -> 829
    //   393: aload_0
    //   394: iconst_0
    //   395: anewarray java/lang/Object
    //   398: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   401: checkcast wtf/opal/s
    //   404: iload #6
    //   406: i2s
    //   407: iload #7
    //   409: i2s
    //   410: iload #8
    //   412: iconst_3
    //   413: anewarray java/lang/Object
    //   416: dup_x1
    //   417: swap
    //   418: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   421: iconst_2
    //   422: swap
    //   423: aastore
    //   424: dup_x1
    //   425: swap
    //   426: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   429: iconst_1
    //   430: swap
    //   431: aastore
    //   432: dup_x1
    //   433: swap
    //   434: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   437: iconst_0
    //   438: swap
    //   439: aastore
    //   440: invokevirtual D : ([Ljava/lang/Object;)V
    //   443: aload #11
    //   445: ifnonnull -> 829
    //   448: goto -> 455
    //   451: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   454: athrow
    //   455: aload_0
    //   456: getfield A : Z
    //   459: goto -> 466
    //   462: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   465: athrow
    //   466: aload #11
    //   468: ifnull -> 482
    //   471: ifne -> 829
    //   474: goto -> 481
    //   477: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   480: athrow
    //   481: iconst_m1
    //   482: istore #12
    //   484: iconst_0
    //   485: istore #13
    //   487: iload #13
    //   489: sipush #24858
    //   492: ldc2_w 731316351095470342
    //   495: lload_2
    //   496: lxor
    //   497: <illegal opcode> a : (IJ)I
    //   502: if_icmpge -> 586
    //   505: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   508: getfield field_1724 : Lnet/minecraft/class_746;
    //   511: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   514: getfield field_7547 : Lnet/minecraft/class_2371;
    //   517: iload #13
    //   519: invokevirtual get : (I)Ljava/lang/Object;
    //   522: checkcast net/minecraft/class_1799
    //   525: astore #14
    //   527: aload #11
    //   529: ifnull -> 581
    //   532: aload #14
    //   534: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   537: instanceof net/minecraft/class_1778
    //   540: aload #11
    //   542: ifnull -> 588
    //   545: goto -> 552
    //   548: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   551: athrow
    //   552: ifeq -> 571
    //   555: goto -> 562
    //   558: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   561: athrow
    //   562: iload #13
    //   564: istore #12
    //   566: aload #11
    //   568: ifnonnull -> 586
    //   571: iinc #13, 1
    //   574: goto -> 581
    //   577: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   580: athrow
    //   581: aload #11
    //   583: ifnonnull -> 487
    //   586: iload #12
    //   588: iconst_m1
    //   589: aload #11
    //   591: ifnull -> 728
    //   594: if_icmpne -> 710
    //   597: goto -> 604
    //   600: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   603: athrow
    //   604: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   607: invokevirtual method_1566 : ()Lnet/minecraft/class_374;
    //   610: new net/minecraft/class_370
    //   613: dup
    //   614: getstatic net/minecraft/class_370$class_9037.field_47588 : Lnet/minecraft/class_370$class_9037;
    //   617: sipush #5124
    //   620: ldc2_w 7044124699127155401
    //   623: lload_2
    //   624: lxor
    //   625: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   630: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   633: sipush #1404
    //   636: ldc2_w 1785312836139424688
    //   639: lload_2
    //   640: lxor
    //   641: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   646: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   649: invokespecial <init> : (Lnet/minecraft/class_370$class_9037;Lnet/minecraft/class_2561;Lnet/minecraft/class_2561;)V
    //   652: invokevirtual method_1999 : (Lnet/minecraft/class_368;)V
    //   655: aload_0
    //   656: iconst_0
    //   657: anewarray java/lang/Object
    //   660: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   663: checkcast wtf/opal/s
    //   666: iload #6
    //   668: i2s
    //   669: iload #7
    //   671: i2s
    //   672: iload #8
    //   674: iconst_3
    //   675: anewarray java/lang/Object
    //   678: dup_x1
    //   679: swap
    //   680: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   683: iconst_2
    //   684: swap
    //   685: aastore
    //   686: dup_x1
    //   687: swap
    //   688: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   691: iconst_1
    //   692: swap
    //   693: aastore
    //   694: dup_x1
    //   695: swap
    //   696: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   699: iconst_0
    //   700: swap
    //   701: aastore
    //   702: invokevirtual D : ([Ljava/lang/Object;)V
    //   705: return
    //   706: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   709: athrow
    //   710: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   713: getfield field_1724 : Lnet/minecraft/class_746;
    //   716: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   719: getfield field_7545 : I
    //   722: istore #13
    //   724: iload #13
    //   726: iload #12
    //   728: aload #11
    //   730: ifnull -> 803
    //   733: if_icmpeq -> 768
    //   736: goto -> 743
    //   739: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   742: athrow
    //   743: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   746: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   749: new net/minecraft/class_2868
    //   752: dup
    //   753: iload #12
    //   755: invokespecial <init> : (I)V
    //   758: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   761: goto -> 768
    //   764: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   767: athrow
    //   768: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   771: aload #11
    //   773: ifnull -> 809
    //   776: getfield field_1761 : Lnet/minecraft/class_636;
    //   779: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   782: getfield field_1724 : Lnet/minecraft/class_746;
    //   785: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   788: invokevirtual method_2919 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;
    //   791: pop
    //   792: iload #13
    //   794: iload #12
    //   796: goto -> 803
    //   799: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   802: athrow
    //   803: if_icmpeq -> 824
    //   806: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   809: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   812: new net/minecraft/class_2868
    //   815: dup
    //   816: iload #13
    //   818: invokespecial <init> : (I)V
    //   821: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   824: aload_0
    //   825: iconst_1
    //   826: putfield A : Z
    //   829: return
    // Exception table:
    //   from	to	target	type
    //   62	78	81	wtf/opal/x5
    //   90	98	101	wtf/opal/x5
    //   95	124	127	wtf/opal/x5
    //   105	147	150	wtf/opal/x5
    //   131	165	168	wtf/opal/x5
    //   154	175	178	wtf/opal/x5
    //   172	208	211	wtf/opal/x5
    //   182	221	221	wtf/opal/x5
    //   252	260	263	wtf/opal/x5
    //   257	277	280	wtf/opal/x5
    //   267	287	290	wtf/opal/x5
    //   284	335	338	wtf/opal/x5
    //   346	364	367	wtf/opal/x5
    //   371	383	386	wtf/opal/x5
    //   390	448	451	wtf/opal/x5
    //   393	459	462	wtf/opal/x5
    //   466	474	477	wtf/opal/x5
    //   527	545	548	wtf/opal/x5
    //   532	555	558	wtf/opal/x5
    //   566	574	577	wtf/opal/x5
    //   588	597	600	wtf/opal/x5
    //   594	706	706	wtf/opal/x5
    //   728	736	739	wtf/opal/x5
    //   733	761	764	wtf/opal/x5
    //   768	796	799	wtf/opal/x5
  }
  
  private void lambda$new$0(p paramp) {
    long l = a ^ 0x113336F552F7L;
    String str = ut.R();
    try {
      if (str != null)
        try {
          if (this.A) {
          
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
      if (!this.A)
        paramp.q(new Object[] { new class_243(0.0D, paramp.S(new Object[0]).method_10214(), 0.0D) }); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  static {
    long l = a ^ 0x123534B26BC6L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "îîù¶öó)óoý[Ûv(¹ÕVbÞUäÐÞ+|~sTÑùâ\026iêd`\017Öø\bê|3ìXTä9ÑÁa").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x35A3;
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
        throw new RuntimeException("wtf/opal/uy", exception);
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
    //   66: ldc_w 'wtf/opal/uy'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3372;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/uy", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   66: ldc_w 'wtf/opal/uy'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uy.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */