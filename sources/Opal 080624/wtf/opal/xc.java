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
import net.minecraft.class_2338;
import net.minecraft.class_241;

public final class xc extends d {
  private class_241 y;
  
  private boolean q;
  
  private class_2338 t;
  
  private lk o;
  
  private float Q;
  
  private int D;
  
  private int u;
  
  private int z;
  
  private boolean m;
  
  private boolean g;
  
  private boolean l;
  
  private final ky<l5> p;
  
  private final kd W;
  
  private final kt R;
  
  private final kt L;
  
  private final kt d;
  
  private final kt a;
  
  private final kt w;
  
  private final kt G;
  
  private final ke A;
  
  private final ke v;
  
  private final ke f;
  
  private final pa B;
  
  private final bu k;
  
  private final gm<u0> T;
  
  private final gm<lz> Z;
  
  private final gm<uj> U;
  
  private final gm<k7> E;
  
  private final gm<lb> b;
  
  private static final long n = on.a(8012395783201696370L, -7867217745313061019L, MethodHandles.lookup().lookupClass()).a(123722062770613L);
  
  private static final String[] r;
  
  private static final String[] s;
  
  private static final Map x = new HashMap<>(13);
  
  private static final long[] I;
  
  private static final Integer[] J;
  
  private static final Map M;
  
  public xc(int paramInt1, int paramInt2, char paramChar) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #48
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/xc.n : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 52395763633140
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 115157738240136
    //   42: lxor
    //   43: lstore #8
    //   45: dup2
    //   46: ldc2_w 77802567688669
    //   49: lxor
    //   50: lstore #10
    //   52: pop2
    //   53: aload_0
    //   54: sipush #26704
    //   57: ldc2_w 7536801135192597067
    //   60: lload #4
    //   62: lxor
    //   63: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   68: lload #10
    //   70: sipush #29275
    //   73: ldc2_w 2429780771477977160
    //   76: lload #4
    //   78: lxor
    //   79: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   84: getstatic wtf/opal/kn.WORLD : Lwtf/opal/kn;
    //   87: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   90: aload_0
    //   91: new wtf/opal/ky
    //   94: dup
    //   95: sipush #8261
    //   98: ldc2_w 1759670544793709136
    //   101: lload #4
    //   103: lxor
    //   104: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   109: getstatic wtf/opal/l5.SERVER : Lwtf/opal/l5;
    //   112: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   115: putfield p : Lwtf/opal/ky;
    //   118: aload_0
    //   119: new wtf/opal/kd
    //   122: dup
    //   123: sipush #16160
    //   126: ldc2_w 9099971885418755383
    //   129: lload #4
    //   131: lxor
    //   132: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   137: iconst_2
    //   138: anewarray wtf/opal/ke
    //   141: dup
    //   142: iconst_0
    //   143: new wtf/opal/ke
    //   146: dup
    //   147: sipush #14047
    //   150: ldc2_w 1309102275666885833
    //   153: lload #4
    //   155: lxor
    //   156: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   161: iconst_1
    //   162: invokespecial <init> : (Ljava/lang/String;Z)V
    //   165: aastore
    //   166: dup
    //   167: iconst_1
    //   168: new wtf/opal/ke
    //   171: dup
    //   172: sipush #21572
    //   175: ldc2_w 4983140294050012761
    //   178: lload #4
    //   180: lxor
    //   181: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   186: iconst_1
    //   187: invokespecial <init> : (Ljava/lang/String;Z)V
    //   190: aastore
    //   191: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   194: putfield W : Lwtf/opal/kd;
    //   197: aload_0
    //   198: new wtf/opal/kt
    //   201: dup
    //   202: sipush #20729
    //   205: ldc2_w 2393613634362235619
    //   208: lload #4
    //   210: lxor
    //   211: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   216: ldc2_w 4.0
    //   219: dconst_1
    //   220: ldc2_w 6.0
    //   223: dconst_1
    //   224: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   227: putfield R : Lwtf/opal/kt;
    //   230: aload_0
    //   231: new wtf/opal/kt
    //   234: dup
    //   235: sipush #17757
    //   238: ldc2_w 2743909232807857993
    //   241: lload #4
    //   243: lxor
    //   244: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   249: dconst_1
    //   250: ldc2_w 0.1
    //   253: dconst_1
    //   254: ldc2_w 0.05
    //   257: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   260: putfield L : Lwtf/opal/kt;
    //   263: aload_0
    //   264: new wtf/opal/kt
    //   267: dup
    //   268: sipush #18323
    //   271: ldc2_w 3495229277988375947
    //   274: lload #4
    //   276: lxor
    //   277: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   282: dconst_1
    //   283: ldc2_w 0.1
    //   286: dconst_1
    //   287: ldc2_w 0.05
    //   290: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   293: putfield d : Lwtf/opal/kt;
    //   296: aload_0
    //   297: new wtf/opal/kt
    //   300: dup
    //   301: sipush #30657
    //   304: ldc2_w 4623104402322423261
    //   307: lload #4
    //   309: lxor
    //   310: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   315: dconst_1
    //   316: dconst_1
    //   317: ldc2_w 10.0
    //   320: ldc2_w 0.1
    //   323: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   326: putfield a : Lwtf/opal/kt;
    //   329: aload_0
    //   330: new wtf/opal/kt
    //   333: dup
    //   334: sipush #19582
    //   337: ldc2_w 3741360504938317415
    //   340: lload #4
    //   342: lxor
    //   343: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   348: dconst_1
    //   349: dconst_1
    //   350: ldc2_w 10.0
    //   353: ldc2_w 0.1
    //   356: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   359: putfield w : Lwtf/opal/kt;
    //   362: aload_0
    //   363: new wtf/opal/kt
    //   366: dup
    //   367: sipush #14444
    //   370: ldc2_w 4681817165161443966
    //   373: lload #4
    //   375: lxor
    //   376: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   381: ldc2_w 5.0
    //   384: dconst_0
    //   385: ldc2_w 5.0
    //   388: dconst_1
    //   389: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   392: putfield G : Lwtf/opal/kt;
    //   395: aload_0
    //   396: new wtf/opal/ke
    //   399: dup
    //   400: sipush #24722
    //   403: ldc2_w 3314576017071499935
    //   406: lload #4
    //   408: lxor
    //   409: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   414: iconst_1
    //   415: invokespecial <init> : (Ljava/lang/String;Z)V
    //   418: putfield A : Lwtf/opal/ke;
    //   421: invokestatic k : ()[I
    //   424: aload_0
    //   425: new wtf/opal/ke
    //   428: dup
    //   429: sipush #27538
    //   432: ldc2_w 269572432833307020
    //   435: lload #4
    //   437: lxor
    //   438: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   443: iconst_0
    //   444: invokespecial <init> : (Ljava/lang/String;Z)V
    //   447: putfield v : Lwtf/opal/ke;
    //   450: aload_0
    //   451: new wtf/opal/ke
    //   454: dup
    //   455: sipush #5976
    //   458: ldc2_w 3833863581219134793
    //   461: lload #4
    //   463: lxor
    //   464: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   469: iconst_0
    //   470: invokespecial <init> : (Ljava/lang/String;Z)V
    //   473: putfield f : Lwtf/opal/ke;
    //   476: aload_0
    //   477: iconst_0
    //   478: anewarray java/lang/Object
    //   481: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   484: iconst_0
    //   485: anewarray java/lang/Object
    //   488: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   491: putfield B : Lwtf/opal/pa;
    //   494: aload_0
    //   495: iconst_0
    //   496: anewarray java/lang/Object
    //   499: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   502: iconst_0
    //   503: anewarray java/lang/Object
    //   506: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   509: putfield k : Lwtf/opal/bu;
    //   512: astore #12
    //   514: aload_0
    //   515: aload_0
    //   516: <illegal opcode> H : (Lwtf/opal/xc;)Lwtf/opal/gm;
    //   521: putfield T : Lwtf/opal/gm;
    //   524: aload_0
    //   525: aload_0
    //   526: <illegal opcode> H : (Lwtf/opal/xc;)Lwtf/opal/gm;
    //   531: putfield Z : Lwtf/opal/gm;
    //   534: aload_0
    //   535: aload_0
    //   536: <illegal opcode> H : (Lwtf/opal/xc;)Lwtf/opal/gm;
    //   541: putfield U : Lwtf/opal/gm;
    //   544: aload_0
    //   545: aload_0
    //   546: <illegal opcode> H : (Lwtf/opal/xc;)Lwtf/opal/gm;
    //   551: putfield E : Lwtf/opal/gm;
    //   554: aload_0
    //   555: aload_0
    //   556: <illegal opcode> H : (Lwtf/opal/xc;)Lwtf/opal/gm;
    //   561: putfield b : Lwtf/opal/gm;
    //   564: aload_0
    //   565: getfield f : Lwtf/opal/ke;
    //   568: aload_0
    //   569: getfield v : Lwtf/opal/ke;
    //   572: aload_0
    //   573: <illegal opcode> test : (Lwtf/opal/xc;)Ljava/util/function/Predicate;
    //   578: lload #6
    //   580: dup2_x1
    //   581: pop2
    //   582: iconst_3
    //   583: anewarray java/lang/Object
    //   586: dup_x1
    //   587: swap
    //   588: iconst_2
    //   589: swap
    //   590: aastore
    //   591: dup_x2
    //   592: dup_x2
    //   593: pop
    //   594: invokestatic valueOf : (J)Ljava/lang/Long;
    //   597: iconst_1
    //   598: swap
    //   599: aastore
    //   600: dup_x1
    //   601: swap
    //   602: iconst_0
    //   603: swap
    //   604: aastore
    //   605: invokevirtual C : ([Ljava/lang/Object;)V
    //   608: aload_0
    //   609: sipush #1934
    //   612: ldc2_w 1785411895039062082
    //   615: lload #4
    //   617: lxor
    //   618: <illegal opcode> e : (IJ)I
    //   623: anewarray wtf/opal/k3
    //   626: dup
    //   627: iconst_0
    //   628: aload_0
    //   629: getfield p : Lwtf/opal/ky;
    //   632: aastore
    //   633: dup
    //   634: iconst_1
    //   635: aload_0
    //   636: getfield W : Lwtf/opal/kd;
    //   639: aastore
    //   640: dup
    //   641: iconst_2
    //   642: aload_0
    //   643: getfield R : Lwtf/opal/kt;
    //   646: aastore
    //   647: dup
    //   648: iconst_3
    //   649: aload_0
    //   650: getfield L : Lwtf/opal/kt;
    //   653: aastore
    //   654: dup
    //   655: iconst_4
    //   656: aload_0
    //   657: getfield d : Lwtf/opal/kt;
    //   660: aastore
    //   661: dup
    //   662: iconst_5
    //   663: aload_0
    //   664: getfield a : Lwtf/opal/kt;
    //   667: aastore
    //   668: dup
    //   669: sipush #19477
    //   672: ldc2_w 4320783644328999898
    //   675: lload #4
    //   677: lxor
    //   678: <illegal opcode> e : (IJ)I
    //   683: aload_0
    //   684: getfield w : Lwtf/opal/kt;
    //   687: aastore
    //   688: dup
    //   689: sipush #32099
    //   692: ldc2_w 9042805591375448746
    //   695: lload #4
    //   697: lxor
    //   698: <illegal opcode> e : (IJ)I
    //   703: aload_0
    //   704: getfield G : Lwtf/opal/kt;
    //   707: aastore
    //   708: dup
    //   709: sipush #7728
    //   712: ldc2_w 8429031964898617840
    //   715: lload #4
    //   717: lxor
    //   718: <illegal opcode> e : (IJ)I
    //   723: aload_0
    //   724: getfield A : Lwtf/opal/ke;
    //   727: aastore
    //   728: dup
    //   729: sipush #11375
    //   732: ldc2_w 8932114926128290733
    //   735: lload #4
    //   737: lxor
    //   738: <illegal opcode> e : (IJ)I
    //   743: aload_0
    //   744: getfield v : Lwtf/opal/ke;
    //   747: aastore
    //   748: dup
    //   749: sipush #11834
    //   752: ldc2_w 3879916135110728183
    //   755: lload #4
    //   757: lxor
    //   758: <illegal opcode> e : (IJ)I
    //   763: aload_0
    //   764: getfield f : Lwtf/opal/ke;
    //   767: aastore
    //   768: lload #8
    //   770: dup2_x1
    //   771: pop2
    //   772: iconst_2
    //   773: anewarray java/lang/Object
    //   776: dup_x1
    //   777: swap
    //   778: iconst_1
    //   779: swap
    //   780: aastore
    //   781: dup_x2
    //   782: dup_x2
    //   783: pop
    //   784: invokestatic valueOf : (J)Ljava/lang/Long;
    //   787: iconst_0
    //   788: swap
    //   789: aastore
    //   790: invokevirtual o : ([Ljava/lang/Object;)V
    //   793: invokestatic D : ()[Lwtf/opal/d;
    //   796: ifnull -> 812
    //   799: iconst_3
    //   800: newarray int
    //   802: invokestatic X : ([I)V
    //   805: goto -> 812
    //   808: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   811: athrow
    //   812: return
    // Exception table:
    //   from	to	target	type
    //   514	805	808	wtf/opal/x5
  }
  
  public ke T(Object[] paramArrayOfObject) {
    return this.v;
  }
  
  public boolean F(Object[] paramArrayOfObject) {
    return this.g;
  }
  
  private void U(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/xc.n : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 10997759245587
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   30: getfield field_1724 : Lnet/minecraft/class_746;
    //   33: invokevirtual method_19538 : ()Lnet/minecraft/class_243;
    //   36: dconst_0
    //   37: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   40: getfield field_1724 : Lnet/minecraft/class_746;
    //   43: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   46: getfield field_1724 : Lnet/minecraft/class_746;
    //   49: invokevirtual method_18376 : ()Lnet/minecraft/class_4050;
    //   52: invokevirtual method_18381 : (Lnet/minecraft/class_4050;)F
    //   55: f2d
    //   56: dconst_0
    //   57: invokevirtual method_1031 : (DDD)Lnet/minecraft/class_243;
    //   60: astore #7
    //   62: aload #7
    //   64: invokestatic method_49638 : (Lnet/minecraft/class_2374;)Lnet/minecraft/class_2338;
    //   67: astore #8
    //   69: invokestatic k : ()[I
    //   72: aload_0
    //   73: getfield R : Lwtf/opal/kt;
    //   76: invokevirtual z : ()Ljava/lang/Object;
    //   79: checkcast java/lang/Double
    //   82: invokevirtual intValue : ()I
    //   85: istore #9
    //   87: aload #8
    //   89: new net/minecraft/class_2382
    //   92: dup
    //   93: iload #9
    //   95: iload #9
    //   97: iload #9
    //   99: invokespecial <init> : (III)V
    //   102: invokevirtual method_10059 : (Lnet/minecraft/class_2382;)Lnet/minecraft/class_2338;
    //   105: astore #10
    //   107: aload #8
    //   109: new net/minecraft/class_2382
    //   112: dup
    //   113: iload #9
    //   115: iload #9
    //   117: iload #9
    //   119: invokespecial <init> : (III)V
    //   122: invokevirtual method_10081 : (Lnet/minecraft/class_2382;)Lnet/minecraft/class_2338;
    //   125: astore #11
    //   127: astore #6
    //   129: new net/minecraft/class_2338
    //   132: dup
    //   133: aload #10
    //   135: invokevirtual method_10263 : ()I
    //   138: aload #11
    //   140: invokevirtual method_10263 : ()I
    //   143: invokestatic min : (II)I
    //   146: aload #10
    //   148: invokevirtual method_10264 : ()I
    //   151: aload #11
    //   153: invokevirtual method_10264 : ()I
    //   156: invokestatic min : (II)I
    //   159: aload #10
    //   161: invokevirtual method_10260 : ()I
    //   164: aload #11
    //   166: invokevirtual method_10260 : ()I
    //   169: invokestatic min : (II)I
    //   172: invokespecial <init> : (III)V
    //   175: astore #12
    //   177: new net/minecraft/class_2338
    //   180: dup
    //   181: aload #10
    //   183: invokevirtual method_10263 : ()I
    //   186: aload #11
    //   188: invokevirtual method_10263 : ()I
    //   191: invokestatic max : (II)I
    //   194: aload #10
    //   196: invokevirtual method_10264 : ()I
    //   199: aload #11
    //   201: invokevirtual method_10264 : ()I
    //   204: invokestatic max : (II)I
    //   207: aload #10
    //   209: invokevirtual method_10260 : ()I
    //   212: aload #11
    //   214: invokevirtual method_10260 : ()I
    //   217: invokestatic max : (II)I
    //   220: invokespecial <init> : (III)V
    //   223: astore #13
    //   225: aload #12
    //   227: invokevirtual method_10263 : ()I
    //   230: istore #14
    //   232: iload #14
    //   234: aload #13
    //   236: invokevirtual method_10263 : ()I
    //   239: if_icmpgt -> 495
    //   242: aload #12
    //   244: invokevirtual method_10264 : ()I
    //   247: istore #15
    //   249: iload #15
    //   251: aload #13
    //   253: invokevirtual method_10264 : ()I
    //   256: if_icmpgt -> 481
    //   259: aload #12
    //   261: invokevirtual method_10260 : ()I
    //   264: aload #6
    //   266: ifnull -> 234
    //   269: istore #16
    //   271: iload #16
    //   273: aload #13
    //   275: invokevirtual method_10260 : ()I
    //   278: if_icmpgt -> 467
    //   281: new net/minecraft/class_2338
    //   284: dup
    //   285: iload #14
    //   287: iload #15
    //   289: iload #16
    //   291: invokespecial <init> : (III)V
    //   294: astore #17
    //   296: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   299: getfield field_1687 : Lnet/minecraft/class_638;
    //   302: aload #17
    //   304: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   307: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   310: astore #19
    //   312: aload #6
    //   314: ifnull -> 462
    //   317: aload #19
    //   319: instanceof net/minecraft/class_2244
    //   322: aload #6
    //   324: ifnull -> 251
    //   327: lload_2
    //   328: lconst_0
    //   329: lcmp
    //   330: ifle -> 264
    //   333: goto -> 340
    //   336: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   339: athrow
    //   340: ifeq -> 459
    //   343: aload #19
    //   345: checkcast net/minecraft/class_2244
    //   348: astore #18
    //   350: aload #6
    //   352: lload_2
    //   353: lconst_0
    //   354: lcmp
    //   355: iflt -> 464
    //   358: ifnull -> 462
    //   361: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   364: getfield field_1687 : Lnet/minecraft/class_638;
    //   367: aload #17
    //   369: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   372: invokestatic method_24164 : (Lnet/minecraft/class_2680;)Lnet/minecraft/class_4732$class_4733;
    //   375: getstatic net/minecraft/class_4732$class_4733.field_21784 : Lnet/minecraft/class_4732$class_4733;
    //   378: invokevirtual equals : (Ljava/lang/Object;)Z
    //   381: ifeq -> 459
    //   384: goto -> 391
    //   387: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   390: athrow
    //   391: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   394: getfield field_1724 : Lnet/minecraft/class_746;
    //   397: invokevirtual method_22861 : ()I
    //   400: lload #4
    //   402: dup2_x1
    //   403: pop2
    //   404: iconst_2
    //   405: anewarray java/lang/Object
    //   408: dup_x1
    //   409: swap
    //   410: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   413: iconst_1
    //   414: swap
    //   415: aastore
    //   416: dup_x2
    //   417: dup_x2
    //   418: pop
    //   419: invokestatic valueOf : (J)Ljava/lang/Long;
    //   422: iconst_0
    //   423: swap
    //   424: aastore
    //   425: invokestatic r : ([Ljava/lang/Object;)I
    //   428: aload #18
    //   430: invokevirtual method_9487 : ()Lnet/minecraft/class_1767;
    //   433: invokevirtual method_16357 : ()I
    //   436: if_icmpeq -> 459
    //   439: goto -> 446
    //   442: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   445: athrow
    //   446: aload_0
    //   447: aload #17
    //   449: putfield t : Lnet/minecraft/class_2338;
    //   452: goto -> 459
    //   455: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   458: athrow
    //   459: iinc #16, 1
    //   462: aload #6
    //   464: ifnonnull -> 271
    //   467: iinc #15, 1
    //   470: aload #6
    //   472: lload_2
    //   473: lconst_0
    //   474: lcmp
    //   475: ifle -> 492
    //   478: ifnonnull -> 249
    //   481: iinc #14, 1
    //   484: lload_2
    //   485: lconst_0
    //   486: lcmp
    //   487: iflt -> 259
    //   490: aload #6
    //   492: ifnonnull -> 232
    //   495: lload_2
    //   496: lconst_0
    //   497: lcmp
    //   498: iflt -> 242
    //   501: return
    // Exception table:
    //   from	to	target	type
    //   312	327	336	wtf/opal/x5
    //   350	384	387	wtf/opal/x5
    //   361	439	442	wtf/opal/x5
    //   391	452	455	wtf/opal/x5
  }
  
  private boolean O(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_2338
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: pop
    //   19: getstatic wtf/opal/xc.n : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: invokestatic k : ()[I
    //   28: astore #5
    //   30: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   33: getfield field_1687 : Lnet/minecraft/class_638;
    //   36: aload_2
    //   37: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   40: invokevirtual method_26215 : ()Z
    //   43: aload #5
    //   45: ifnull -> 83
    //   48: ifeq -> 64
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: iconst_1
    //   59: ireturn
    //   60: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   63: athrow
    //   64: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   67: getfield field_1687 : Lnet/minecraft/class_638;
    //   70: aload_2
    //   71: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   74: invokestatic method_24164 : (Lnet/minecraft/class_2680;)Lnet/minecraft/class_4732$class_4733;
    //   77: getstatic net/minecraft/class_4732$class_4733.field_21784 : Lnet/minecraft/class_4732$class_4733;
    //   80: invokevirtual equals : (Ljava/lang/Object;)Z
    //   83: aload #5
    //   85: lload_3
    //   86: lconst_0
    //   87: lcmp
    //   88: ifle -> 128
    //   91: ifnull -> 126
    //   94: ifne -> 110
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: iconst_0
    //   105: ireturn
    //   106: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   113: getfield field_1687 : Lnet/minecraft/class_638;
    //   116: aload_2
    //   117: invokevirtual method_10084 : ()Lnet/minecraft/class_2338;
    //   120: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   123: invokevirtual method_26215 : ()Z
    //   126: aload #5
    //   128: ifnull -> 294
    //   131: ifne -> 293
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   144: getfield field_1687 : Lnet/minecraft/class_638;
    //   147: aload_2
    //   148: invokevirtual method_10067 : ()Lnet/minecraft/class_2338;
    //   151: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   154: invokevirtual method_26215 : ()Z
    //   157: aload #5
    //   159: ifnull -> 294
    //   162: goto -> 169
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: ifne -> 293
    //   172: goto -> 179
    //   175: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   178: athrow
    //   179: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   182: getfield field_1687 : Lnet/minecraft/class_638;
    //   185: aload_2
    //   186: invokevirtual method_10095 : ()Lnet/minecraft/class_2338;
    //   189: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   192: invokevirtual method_26215 : ()Z
    //   195: aload #5
    //   197: ifnull -> 294
    //   200: goto -> 207
    //   203: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   206: athrow
    //   207: ifne -> 293
    //   210: goto -> 217
    //   213: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   216: athrow
    //   217: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   220: getfield field_1687 : Lnet/minecraft/class_638;
    //   223: aload_2
    //   224: invokevirtual method_10078 : ()Lnet/minecraft/class_2338;
    //   227: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   230: invokevirtual method_26215 : ()Z
    //   233: aload #5
    //   235: ifnull -> 294
    //   238: goto -> 245
    //   241: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   244: athrow
    //   245: ifne -> 293
    //   248: goto -> 255
    //   251: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   254: athrow
    //   255: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   258: getfield field_1687 : Lnet/minecraft/class_638;
    //   261: aload_2
    //   262: invokevirtual method_10072 : ()Lnet/minecraft/class_2338;
    //   265: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   268: invokevirtual method_26215 : ()Z
    //   271: aload #5
    //   273: ifnull -> 296
    //   276: goto -> 283
    //   279: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: ifeq -> 295
    //   286: goto -> 293
    //   289: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   292: athrow
    //   293: iconst_1
    //   294: ireturn
    //   295: iconst_0
    //   296: ireturn
    // Exception table:
    //   from	to	target	type
    //   30	51	54	wtf/opal/x5
    //   48	60	60	wtf/opal/x5
    //   83	97	100	wtf/opal/x5
    //   94	106	106	wtf/opal/x5
    //   126	134	137	wtf/opal/x5
    //   131	162	165	wtf/opal/x5
    //   141	172	175	wtf/opal/x5
    //   169	200	203	wtf/opal/x5
    //   179	210	213	wtf/opal/x5
    //   207	238	241	wtf/opal/x5
    //   217	248	251	wtf/opal/x5
    //   245	276	279	wtf/opal/x5
    //   255	286	289	wtf/opal/x5
  }
  
  protected void z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.o = null;
    this.Q = 0.0F;
    this.q = false;
    this.D = this.G.z().intValue();
    this.z = -1;
    this.l = false;
    new Object[1];
    super.z(new Object[] { Long.valueOf(l2) });
  }
  
  public float p(Object[] paramArrayOfObject) {
    return this.Q;
  }
  
  public boolean Z(Object[] paramArrayOfObject) {
    return this.q;
  }
  
  public int z(Object[] paramArrayOfObject) {
    return this.z;
  }
  
  private void lambda$new$6(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/xc.n : J
    //   3: ldc2_w 45405361516480
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic k : ()[I
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: astore #4
    //   23: aload #6
    //   25: aload #4
    //   27: ifnull -> 52
    //   30: instanceof net/minecraft/class_2828
    //   33: ifeq -> 134
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: aload #6
    //   45: goto -> 52
    //   48: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: checkcast net/minecraft/class_2828
    //   55: astore #5
    //   57: aload_0
    //   58: getfield v : Lwtf/opal/ke;
    //   61: invokevirtual z : ()Ljava/lang/Object;
    //   64: checkcast java/lang/Boolean
    //   67: invokevirtual booleanValue : ()Z
    //   70: aload #4
    //   72: ifnull -> 117
    //   75: ifeq -> 134
    //   78: goto -> 85
    //   81: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: aload_0
    //   86: aload #4
    //   88: ifnull -> 121
    //   91: goto -> 98
    //   94: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: getfield f : Lwtf/opal/ke;
    //   101: invokevirtual z : ()Ljava/lang/Object;
    //   104: checkcast java/lang/Boolean
    //   107: invokevirtual booleanValue : ()Z
    //   110: goto -> 117
    //   113: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   116: athrow
    //   117: ifeq -> 134
    //   120: aload_0
    //   121: getfield o : Lwtf/opal/lk;
    //   124: ifnull -> 134
    //   127: aload #5
    //   129: checkcast wtf/opal/mixin/PlayerMoveC2SPacketAccessor
    //   132: astore #6
    //   134: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	wtf/opal/x5
    //   30	45	48	wtf/opal/x5
    //   57	78	81	wtf/opal/x5
    //   75	91	94	wtf/opal/x5
    //   85	110	113	wtf/opal/x5
  }
  
  private void lambda$new$5(k7 paramk7) {
    // Byte code:
    //   0: getstatic wtf/opal/xc.n : J
    //   3: ldc2_w 50196453239729
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 85056973172791
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 51744782545603
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 98257673165866
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic k : ()[I
    //   34: astore #10
    //   36: aload_0
    //   37: aload #10
    //   39: ifnull -> 63
    //   42: getfield q : Z
    //   45: ifeq -> 439
    //   48: goto -> 55
    //   51: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   54: athrow
    //   55: aload_0
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: getfield o : Lwtf/opal/lk;
    //   66: aload #10
    //   68: ifnull -> 120
    //   71: ifnull -> 439
    //   74: goto -> 81
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: aload_0
    //   82: getfield W : Lwtf/opal/kd;
    //   85: sipush #12860
    //   88: ldc2_w 5129127988402733855
    //   91: lload_2
    //   92: lxor
    //   93: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   98: iconst_1
    //   99: anewarray java/lang/Object
    //   102: dup_x1
    //   103: swap
    //   104: iconst_0
    //   105: swap
    //   106: aastore
    //   107: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   110: invokevirtual z : ()Ljava/lang/Object;
    //   113: goto -> 120
    //   116: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   119: athrow
    //   120: checkcast java/lang/Boolean
    //   123: invokevirtual booleanValue : ()Z
    //   126: aload #10
    //   128: ifnull -> 166
    //   131: ifeq -> 439
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: lload #4
    //   143: iconst_1
    //   144: anewarray java/lang/Object
    //   147: dup_x2
    //   148: dup_x2
    //   149: pop
    //   150: invokestatic valueOf : (J)Ljava/lang/Long;
    //   153: iconst_0
    //   154: swap
    //   155: aastore
    //   156: invokestatic h : ([Ljava/lang/Object;)Z
    //   159: goto -> 166
    //   162: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: ifne -> 439
    //   169: iconst_0
    //   170: anewarray java/lang/Object
    //   173: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   176: iconst_0
    //   177: anewarray java/lang/Object
    //   180: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   183: ldc_w wtf/opal/jt
    //   186: iconst_1
    //   187: anewarray java/lang/Object
    //   190: dup_x1
    //   191: swap
    //   192: iconst_0
    //   193: swap
    //   194: aastore
    //   195: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   198: checkcast wtf/opal/jt
    //   201: astore #11
    //   203: sipush #17580
    //   206: ldc2_w 8000186067060344919
    //   209: lload_2
    //   210: lxor
    //   211: <illegal opcode> e : (IJ)I
    //   216: iconst_0
    //   217: aload #11
    //   219: iconst_0
    //   220: anewarray java/lang/Object
    //   223: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   226: invokevirtual z : ()Ljava/lang/Object;
    //   229: checkcast java/lang/Integer
    //   232: invokevirtual intValue : ()I
    //   235: lload #6
    //   237: dup2_x1
    //   238: pop2
    //   239: aload #11
    //   241: iconst_0
    //   242: anewarray java/lang/Object
    //   245: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   248: invokevirtual z : ()Ljava/lang/Object;
    //   251: checkcast java/lang/Integer
    //   254: invokevirtual intValue : ()I
    //   257: iconst_5
    //   258: anewarray java/lang/Object
    //   261: dup_x1
    //   262: swap
    //   263: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   266: iconst_4
    //   267: swap
    //   268: aastore
    //   269: dup_x1
    //   270: swap
    //   271: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   274: iconst_3
    //   275: swap
    //   276: aastore
    //   277: dup_x2
    //   278: dup_x2
    //   279: pop
    //   280: invokestatic valueOf : (J)Ljava/lang/Long;
    //   283: iconst_2
    //   284: swap
    //   285: aastore
    //   286: dup_x1
    //   287: swap
    //   288: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   291: iconst_1
    //   292: swap
    //   293: aastore
    //   294: dup_x1
    //   295: swap
    //   296: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   299: iconst_0
    //   300: swap
    //   301: aastore
    //   302: invokestatic K : ([Ljava/lang/Object;)I
    //   305: istore #12
    //   307: iconst_1
    //   308: putstatic wtf/opal/bc.U : Z
    //   311: aload_1
    //   312: iconst_0
    //   313: anewarray java/lang/Object
    //   316: invokevirtual e : ([Ljava/lang/Object;)Lnet/minecraft/class_4587;
    //   319: iload #12
    //   321: ldc_w 0.4
    //   324: iconst_2
    //   325: anewarray java/lang/Object
    //   328: dup_x1
    //   329: swap
    //   330: invokestatic valueOf : (F)Ljava/lang/Float;
    //   333: iconst_1
    //   334: swap
    //   335: aastore
    //   336: dup_x1
    //   337: swap
    //   338: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   341: iconst_0
    //   342: swap
    //   343: aastore
    //   344: invokestatic X : ([Ljava/lang/Object;)I
    //   347: iload #12
    //   349: aload_0
    //   350: getfield o : Lwtf/opal/lk;
    //   353: iconst_0
    //   354: anewarray java/lang/Object
    //   357: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   360: invokevirtual method_46558 : ()Lnet/minecraft/class_243;
    //   363: ldc2_w 0.5
    //   366: ldc2_w 0.5
    //   369: ldc2_w 0.5
    //   372: invokevirtual method_1023 : (DDD)Lnet/minecraft/class_243;
    //   375: new net/minecraft/class_243
    //   378: dup
    //   379: dconst_1
    //   380: dconst_1
    //   381: dconst_1
    //   382: invokespecial <init> : (DDD)V
    //   385: lload #8
    //   387: bipush #6
    //   389: anewarray java/lang/Object
    //   392: dup_x2
    //   393: dup_x2
    //   394: pop
    //   395: invokestatic valueOf : (J)Ljava/lang/Long;
    //   398: iconst_5
    //   399: swap
    //   400: aastore
    //   401: dup_x1
    //   402: swap
    //   403: iconst_4
    //   404: swap
    //   405: aastore
    //   406: dup_x1
    //   407: swap
    //   408: iconst_3
    //   409: swap
    //   410: aastore
    //   411: dup_x1
    //   412: swap
    //   413: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   416: iconst_2
    //   417: swap
    //   418: aastore
    //   419: dup_x1
    //   420: swap
    //   421: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   424: iconst_1
    //   425: swap
    //   426: aastore
    //   427: dup_x1
    //   428: swap
    //   429: iconst_0
    //   430: swap
    //   431: aastore
    //   432: invokestatic T : ([Ljava/lang/Object;)V
    //   435: iconst_0
    //   436: putstatic wtf/opal/bc.U : Z
    //   439: return
    // Exception table:
    //   from	to	target	type
    //   36	48	51	wtf/opal/x5
    //   42	56	59	wtf/opal/x5
    //   63	74	77	wtf/opal/x5
    //   71	113	116	wtf/opal/x5
    //   120	134	137	wtf/opal/x5
    //   131	159	162	wtf/opal/x5
  }
  
  private void lambda$new$4(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/xc.n : J
    //   3: ldc2_w 52003665586127
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 87619873274953
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 80181797012101
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 38752073265989
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic k : ()[I
    //   34: astore #10
    //   36: aload_0
    //   37: getfield q : Z
    //   40: aload #10
    //   42: ifnull -> 100
    //   45: ifeq -> 143
    //   48: goto -> 55
    //   51: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   54: athrow
    //   55: aload_0
    //   56: getfield W : Lwtf/opal/kd;
    //   59: sipush #5354
    //   62: ldc2_w 4743831553448307128
    //   65: lload_2
    //   66: lxor
    //   67: <illegal opcode> e : (IJ)Ljava/lang/String;
    //   72: iconst_1
    //   73: anewarray java/lang/Object
    //   76: dup_x1
    //   77: swap
    //   78: iconst_0
    //   79: swap
    //   80: aastore
    //   81: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   84: invokevirtual z : ()Ljava/lang/Object;
    //   87: checkcast java/lang/Boolean
    //   90: invokevirtual booleanValue : ()Z
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: aload #10
    //   102: ifnull -> 140
    //   105: ifeq -> 143
    //   108: goto -> 115
    //   111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: lload #4
    //   117: iconst_1
    //   118: anewarray java/lang/Object
    //   121: dup_x2
    //   122: dup_x2
    //   123: pop
    //   124: invokestatic valueOf : (J)Ljava/lang/Long;
    //   127: iconst_0
    //   128: swap
    //   129: aastore
    //   130: invokestatic h : ([Ljava/lang/Object;)Z
    //   133: goto -> 140
    //   136: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   139: athrow
    //   140: ifeq -> 144
    //   143: return
    //   144: iconst_0
    //   145: anewarray java/lang/Object
    //   148: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   151: iconst_0
    //   152: anewarray java/lang/Object
    //   155: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   158: ldc_w wtf/opal/jt
    //   161: iconst_1
    //   162: anewarray java/lang/Object
    //   165: dup_x1
    //   166: swap
    //   167: iconst_0
    //   168: swap
    //   169: aastore
    //   170: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   173: checkcast wtf/opal/jt
    //   176: astore #11
    //   178: aload_0
    //   179: getfield Q : F
    //   182: ldc_w 100.0
    //   185: fmul
    //   186: invokestatic round : (F)I
    //   189: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
    //   194: astore #12
    //   196: iconst_3
    //   197: newarray int
    //   199: dup
    //   200: iconst_0
    //   201: sipush #25150
    //   204: ldc2_w 8371687430790537913
    //   207: lload_2
    //   208: lxor
    //   209: <illegal opcode> e : (IJ)I
    //   214: iastore
    //   215: dup
    //   216: iconst_1
    //   217: sipush #3897
    //   220: ldc2_w 7027370615256059834
    //   223: lload_2
    //   224: lxor
    //   225: <illegal opcode> e : (IJ)I
    //   230: iastore
    //   231: dup
    //   232: iconst_2
    //   233: sipush #29113
    //   236: ldc2_w 8965422997422600511
    //   239: lload_2
    //   240: lxor
    //   241: <illegal opcode> e : (IJ)I
    //   246: iastore
    //   247: aload_0
    //   248: getfield Q : F
    //   251: lload #6
    //   253: dup2_x1
    //   254: pop2
    //   255: iconst_3
    //   256: anewarray java/lang/Object
    //   259: dup_x1
    //   260: swap
    //   261: invokestatic valueOf : (F)Ljava/lang/Float;
    //   264: iconst_2
    //   265: swap
    //   266: aastore
    //   267: dup_x2
    //   268: dup_x2
    //   269: pop
    //   270: invokestatic valueOf : (J)Ljava/lang/Long;
    //   273: iconst_1
    //   274: swap
    //   275: aastore
    //   276: dup_x1
    //   277: swap
    //   278: iconst_0
    //   279: swap
    //   280: aastore
    //   281: invokestatic F : ([Ljava/lang/Object;)I
    //   284: istore #13
    //   286: aload_0
    //   287: getfield k : Lwtf/opal/bu;
    //   290: getstatic wtf/opal/lx.BOLD : Lwtf/opal/lx;
    //   293: lload #8
    //   295: aload #12
    //   297: ldc_w 8.0
    //   300: sipush #4282
    //   303: ldc2_w 7761346725727965236
    //   306: lload_2
    //   307: lxor
    //   308: <illegal opcode> e : (IJ)I
    //   313: iconst_5
    //   314: anewarray java/lang/Object
    //   317: dup_x1
    //   318: swap
    //   319: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   322: iconst_4
    //   323: swap
    //   324: aastore
    //   325: dup_x1
    //   326: swap
    //   327: invokestatic valueOf : (F)Ljava/lang/Float;
    //   330: iconst_3
    //   331: swap
    //   332: aastore
    //   333: dup_x1
    //   334: swap
    //   335: iconst_2
    //   336: swap
    //   337: aastore
    //   338: dup_x2
    //   339: dup_x2
    //   340: pop
    //   341: invokestatic valueOf : (J)Ljava/lang/Long;
    //   344: iconst_1
    //   345: swap
    //   346: aastore
    //   347: dup_x1
    //   348: swap
    //   349: iconst_0
    //   350: swap
    //   351: aastore
    //   352: invokevirtual h : ([Ljava/lang/Object;)F
    //   355: fstore #14
    //   357: aload_0
    //   358: getfield B : Lwtf/opal/pa;
    //   361: aload_0
    //   362: fload #14
    //   364: iload #13
    //   366: aload_1
    //   367: aload #12
    //   369: aload #11
    //   371: <illegal opcode> run : (Lwtf/opal/xc;FILwtf/opal/uj;Ljava/lang/String;Lwtf/opal/jt;)Ljava/lang/Runnable;
    //   376: iconst_1
    //   377: anewarray java/lang/Object
    //   380: dup_x1
    //   381: swap
    //   382: iconst_0
    //   383: swap
    //   384: aastore
    //   385: invokevirtual T : ([Ljava/lang/Object;)V
    //   388: return
    // Exception table:
    //   from	to	target	type
    //   36	48	51	wtf/opal/x5
    //   45	93	96	wtf/opal/x5
    //   100	108	111	wtf/opal/x5
    //   105	133	136	wtf/opal/x5
  }
  
  private void lambda$new$3(float paramFloat, int paramInt, uj paramuj, String paramString, jt paramjt) {
    long l1 = n ^ 0x380985A384F0L;
    long l2 = l1 ^ 0x6B3CBD5619BBL;
    long l3 = l1 ^ 0x2CCE0C582DFCL;
    long l4 = l1 ^ 0x2A8570E0F598L;
    long l5 = l1 ^ 0x12923F998F1L;
    (new Object[3])[2] = Float.valueOf(0.8F);
    (new Object[3])[1] = Integer.valueOf(paramInt);
    new Object[3];
    new Object[6];
    this.B.k(new Object[] { 
          null, null, null, null, null, Long.valueOf(l3), Integer.valueOf(pu.c(new Object[] { Long.valueOf(l4) })), Float.valueOf(2.0F), Float.valueOf(paramFloat + 4.0F), Float.valueOf(b9.c.method_22683().method_4502() / 2.0F + 40.0F), 
          Float.valueOf((b9.c.method_22683().method_4486() - paramFloat) / 2.0F - 1.0F) });
    int[] arrayOfInt = xw.k();
    try {
      new Object[6];
      this.B.k(new Object[] { 
            null, null, null, null, null, Long.valueOf(l3), Integer.valueOf(paramInt), Float.valueOf(1.0F), Float.valueOf((paramFloat + 4.0F) * this.Q), Float.valueOf(b9.c.method_22683().method_4502() / 2.0F + 40.0F), 
            Float.valueOf((b9.c.method_22683().method_4486() - paramFloat) / 2.0F - 2.0F) });
      if (arrayOfInt != null)
        try {
          new Object[1];
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    boolean bool = true;
    int i = paramInt;
    float f1 = paramjt.S(new Object[] { Long.valueOf(l2) }).equals(dv.MINECRAFT) ? 10.0F : 9.0F;
    float f2 = b9.c.method_22683().method_4502() / 2.0F + 28.0F;
    (new Object[9])[8] = Boolean.valueOf(bool);
    (new Object[9])[7] = Integer.valueOf(i);
    (new Object[9])[6] = Float.valueOf(f1);
    (new Object[9])[5] = Float.valueOf(f2);
    new Object[9];
    this.k.H(new Object[] { null, null, null, null, Long.valueOf(l5), Float.valueOf((b9.c.method_22683().method_4486() - paramFloat) / 2.0F), paramString, paramuj.B(new Object[0]), lx.REGULAR });
  }
  
  private void lambda$new$2(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/xc.n : J
    //   3: ldc2_w 61418793628140
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 95798004558442
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 132329522191484
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 119092857065572
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 87356382795261
    //   34: lxor
    //   35: lstore #10
    //   37: pop2
    //   38: invokestatic k : ()[I
    //   41: astore #12
    //   43: aload #12
    //   45: ifnull -> 100
    //   48: lload #4
    //   50: iconst_1
    //   51: anewarray java/lang/Object
    //   54: dup_x2
    //   55: dup_x2
    //   56: pop
    //   57: invokestatic valueOf : (J)Ljava/lang/Long;
    //   60: iconst_0
    //   61: swap
    //   62: aastore
    //   63: invokestatic h : ([Ljava/lang/Object;)Z
    //   66: ifeq -> 81
    //   69: goto -> 76
    //   72: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   75: athrow
    //   76: return
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: aload_0
    //   82: lload #6
    //   84: iconst_1
    //   85: anewarray java/lang/Object
    //   88: dup_x2
    //   89: dup_x2
    //   90: pop
    //   91: invokestatic valueOf : (J)Ljava/lang/Long;
    //   94: iconst_0
    //   95: swap
    //   96: aastore
    //   97: invokevirtual U : ([Ljava/lang/Object;)V
    //   100: aload_0
    //   101: aload #12
    //   103: ifnull -> 459
    //   106: getfield t : Lnet/minecraft/class_2338;
    //   109: ifnull -> 458
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   122: getfield field_1687 : Lnet/minecraft/class_638;
    //   125: aload_0
    //   126: getfield t : Lnet/minecraft/class_2338;
    //   129: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   132: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   135: instanceof net/minecraft/class_2244
    //   138: aload #12
    //   140: ifnull -> 474
    //   143: goto -> 150
    //   146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   149: athrow
    //   150: ifeq -> 458
    //   153: goto -> 160
    //   156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   159: athrow
    //   160: aload_0
    //   161: getfield t : Lnet/minecraft/class_2338;
    //   164: astore #13
    //   166: aload_0
    //   167: aload #13
    //   169: lload #8
    //   171: iconst_2
    //   172: anewarray java/lang/Object
    //   175: dup_x2
    //   176: dup_x2
    //   177: pop
    //   178: invokestatic valueOf : (J)Ljava/lang/Long;
    //   181: iconst_1
    //   182: swap
    //   183: aastore
    //   184: dup_x1
    //   185: swap
    //   186: iconst_0
    //   187: swap
    //   188: aastore
    //   189: invokevirtual O : ([Ljava/lang/Object;)Z
    //   192: aload #12
    //   194: ifnull -> 268
    //   197: ifne -> 254
    //   200: goto -> 207
    //   203: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   206: athrow
    //   207: aload_0
    //   208: getfield A : Lwtf/opal/ke;
    //   211: invokevirtual z : ()Ljava/lang/Object;
    //   214: checkcast java/lang/Boolean
    //   217: invokevirtual booleanValue : ()Z
    //   220: aload #12
    //   222: ifnull -> 268
    //   225: goto -> 232
    //   228: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   231: athrow
    //   232: ifeq -> 254
    //   235: goto -> 242
    //   238: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   241: athrow
    //   242: aload_0
    //   243: getfield t : Lnet/minecraft/class_2338;
    //   246: iconst_0
    //   247: iconst_1
    //   248: iconst_0
    //   249: invokevirtual method_10069 : (III)Lnet/minecraft/class_2338;
    //   252: astore #13
    //   254: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   257: getfield field_1687 : Lnet/minecraft/class_638;
    //   260: aload #13
    //   262: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   265: invokevirtual method_26215 : ()Z
    //   268: aload #12
    //   270: ifnull -> 474
    //   273: ifne -> 458
    //   276: goto -> 283
    //   279: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: aload #13
    //   285: getstatic net/minecraft/class_2350.field_11036 : Lnet/minecraft/class_2350;
    //   288: iconst_2
    //   289: anewarray java/lang/Object
    //   292: dup_x1
    //   293: swap
    //   294: iconst_1
    //   295: swap
    //   296: aastore
    //   297: dup_x1
    //   298: swap
    //   299: iconst_0
    //   300: swap
    //   301: aastore
    //   302: invokestatic V : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   305: astore #14
    //   307: ldc2_w 6.0
    //   310: aload #14
    //   312: getfield field_1343 : F
    //   315: aload #14
    //   317: getfield field_1342 : F
    //   320: aload #13
    //   322: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   325: getfield field_1687 : Lnet/minecraft/class_638;
    //   328: aload #13
    //   330: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   333: iconst_5
    //   334: anewarray java/lang/Object
    //   337: dup_x1
    //   338: swap
    //   339: iconst_4
    //   340: swap
    //   341: aastore
    //   342: dup_x1
    //   343: swap
    //   344: iconst_3
    //   345: swap
    //   346: aastore
    //   347: dup_x1
    //   348: swap
    //   349: invokestatic valueOf : (F)Ljava/lang/Float;
    //   352: iconst_2
    //   353: swap
    //   354: aastore
    //   355: dup_x1
    //   356: swap
    //   357: invokestatic valueOf : (F)Ljava/lang/Float;
    //   360: iconst_1
    //   361: swap
    //   362: aastore
    //   363: dup_x2
    //   364: dup_x2
    //   365: pop
    //   366: invokestatic valueOf : (D)Ljava/lang/Double;
    //   369: iconst_0
    //   370: swap
    //   371: aastore
    //   372: invokestatic W : ([Ljava/lang/Object;)Lnet/minecraft/class_3965;
    //   375: astore #15
    //   377: aload #15
    //   379: aload #12
    //   381: ifnull -> 396
    //   384: ifnull -> 458
    //   387: goto -> 394
    //   390: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   393: athrow
    //   394: aload #15
    //   396: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   399: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
    //   402: invokevirtual equals : (Ljava/lang/Object;)Z
    //   405: aload #12
    //   407: ifnull -> 474
    //   410: ifeq -> 458
    //   413: goto -> 420
    //   416: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   419: athrow
    //   420: aload_0
    //   421: aload #15
    //   423: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
    //   426: aload #15
    //   428: invokevirtual method_17780 : ()Lnet/minecraft/class_2350;
    //   431: iconst_2
    //   432: anewarray java/lang/Object
    //   435: dup_x1
    //   436: swap
    //   437: iconst_1
    //   438: swap
    //   439: aastore
    //   440: dup_x1
    //   441: swap
    //   442: iconst_0
    //   443: swap
    //   444: aastore
    //   445: invokestatic V : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   448: putfield y : Lnet/minecraft/class_241;
    //   451: goto -> 458
    //   454: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   457: athrow
    //   458: aload_0
    //   459: aload #12
    //   461: ifnull -> 799
    //   464: getfield q : Z
    //   467: goto -> 474
    //   470: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   473: athrow
    //   474: ifeq -> 791
    //   477: aload_0
    //   478: aload #12
    //   480: ifnull -> 799
    //   483: goto -> 490
    //   486: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   489: athrow
    //   490: getfield y : Lnet/minecraft/class_241;
    //   493: ifnull -> 791
    //   496: goto -> 503
    //   499: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   502: athrow
    //   503: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   506: getfield field_1687 : Lnet/minecraft/class_638;
    //   509: aload_0
    //   510: getfield o : Lwtf/opal/lk;
    //   513: iconst_0
    //   514: anewarray java/lang/Object
    //   517: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   520: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   523: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   526: getfield field_1724 : Lnet/minecraft/class_746;
    //   529: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   532: getfield field_1687 : Lnet/minecraft/class_638;
    //   535: aload_0
    //   536: getfield o : Lwtf/opal/lk;
    //   539: iconst_0
    //   540: anewarray java/lang/Object
    //   543: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   546: invokevirtual method_26165 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;)F
    //   549: fstore #13
    //   551: aload_0
    //   552: aload #12
    //   554: ifnull -> 695
    //   557: getfield q : Z
    //   560: ifeq -> 694
    //   563: goto -> 570
    //   566: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   569: athrow
    //   570: aload_0
    //   571: aload #12
    //   573: ifnull -> 695
    //   576: goto -> 583
    //   579: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   582: athrow
    //   583: getfield o : Lwtf/opal/lk;
    //   586: ifnull -> 694
    //   589: goto -> 596
    //   592: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   595: athrow
    //   596: aload_0
    //   597: aload #12
    //   599: ifnull -> 695
    //   602: goto -> 609
    //   605: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   608: athrow
    //   609: getfield z : I
    //   612: iconst_m1
    //   613: if_icmpeq -> 694
    //   616: goto -> 623
    //   619: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   622: athrow
    //   623: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   626: aload #12
    //   628: ifnull -> 681
    //   631: goto -> 638
    //   634: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   637: athrow
    //   638: getfield field_1687 : Lnet/minecraft/class_638;
    //   641: aload_0
    //   642: getfield o : Lwtf/opal/lk;
    //   645: iconst_0
    //   646: anewarray java/lang/Object
    //   649: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   652: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   655: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   658: instanceof net/minecraft/class_2244
    //   661: ifne -> 694
    //   664: goto -> 671
    //   667: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   670: athrow
    //   671: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   674: goto -> 681
    //   677: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   680: athrow
    //   681: getfield field_1724 : Lnet/minecraft/class_746;
    //   684: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   687: aload_0
    //   688: getfield z : I
    //   691: putfield field_7545 : I
    //   694: aload_0
    //   695: aload_0
    //   696: getfield Q : F
    //   699: fload #13
    //   701: fconst_2
    //   702: fmul
    //   703: fadd
    //   704: f2d
    //   705: aload_0
    //   706: getfield d : Lwtf/opal/kt;
    //   709: invokevirtual z : ()Ljava/lang/Object;
    //   712: checkcast java/lang/Double
    //   715: invokevirtual doubleValue : ()D
    //   718: dcmpl
    //   719: aload #12
    //   721: ifnull -> 763
    //   724: iflt -> 782
    //   727: goto -> 734
    //   730: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   733: athrow
    //   734: aload_0
    //   735: getfield Q : F
    //   738: fload #13
    //   740: fadd
    //   741: f2d
    //   742: aload_0
    //   743: getfield d : Lwtf/opal/kt;
    //   746: invokevirtual z : ()Ljava/lang/Object;
    //   749: checkcast java/lang/Double
    //   752: invokevirtual doubleValue : ()D
    //   755: dcmpg
    //   756: goto -> 763
    //   759: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   762: athrow
    //   763: aload #12
    //   765: ifnull -> 779
    //   768: ifge -> 782
    //   771: goto -> 778
    //   774: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   777: athrow
    //   778: iconst_1
    //   779: goto -> 783
    //   782: iconst_0
    //   783: putfield g : Z
    //   786: aload #12
    //   788: ifnonnull -> 803
    //   791: aload_0
    //   792: goto -> 799
    //   795: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   798: athrow
    //   799: iconst_0
    //   800: putfield g : Z
    //   803: aload_0
    //   804: getfield g : Z
    //   807: aload #12
    //   809: ifnull -> 927
    //   812: ifeq -> 989
    //   815: goto -> 822
    //   818: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   821: athrow
    //   822: iconst_0
    //   823: anewarray java/lang/Object
    //   826: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   829: iconst_0
    //   830: anewarray java/lang/Object
    //   833: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   836: aload_0
    //   837: getfield y : Lnet/minecraft/class_241;
    //   840: aload_0
    //   841: getfield a : Lwtf/opal/kt;
    //   844: invokevirtual z : ()Ljava/lang/Object;
    //   847: checkcast java/lang/Double
    //   850: invokevirtual floatValue : ()F
    //   853: lload #10
    //   855: dup2_x1
    //   856: pop2
    //   857: aload_0
    //   858: getfield w : Lwtf/opal/kt;
    //   861: invokevirtual z : ()Ljava/lang/Object;
    //   864: checkcast java/lang/Double
    //   867: invokevirtual floatValue : ()F
    //   870: iconst_4
    //   871: anewarray java/lang/Object
    //   874: dup_x1
    //   875: swap
    //   876: invokestatic valueOf : (F)Ljava/lang/Float;
    //   879: iconst_3
    //   880: swap
    //   881: aastore
    //   882: dup_x1
    //   883: swap
    //   884: invokestatic valueOf : (F)Ljava/lang/Float;
    //   887: iconst_2
    //   888: swap
    //   889: aastore
    //   890: dup_x2
    //   891: dup_x2
    //   892: pop
    //   893: invokestatic valueOf : (J)Ljava/lang/Long;
    //   896: iconst_1
    //   897: swap
    //   898: aastore
    //   899: dup_x1
    //   900: swap
    //   901: iconst_0
    //   902: swap
    //   903: aastore
    //   904: invokevirtual g : ([Ljava/lang/Object;)V
    //   907: aload_0
    //   908: getfield v : Lwtf/opal/ke;
    //   911: invokevirtual z : ()Ljava/lang/Object;
    //   914: checkcast java/lang/Boolean
    //   917: invokevirtual booleanValue : ()Z
    //   920: goto -> 927
    //   923: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   926: athrow
    //   927: aload #12
    //   929: ifnull -> 962
    //   932: ifeq -> 989
    //   935: goto -> 942
    //   938: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   941: athrow
    //   942: aload_0
    //   943: getfield f : Lwtf/opal/ke;
    //   946: invokevirtual z : ()Ljava/lang/Object;
    //   949: checkcast java/lang/Boolean
    //   952: invokevirtual booleanValue : ()Z
    //   955: goto -> 962
    //   958: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   961: athrow
    //   962: ifeq -> 989
    //   965: aload_1
    //   966: iconst_1
    //   967: iconst_1
    //   968: anewarray java/lang/Object
    //   971: dup_x1
    //   972: swap
    //   973: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   976: iconst_0
    //   977: swap
    //   978: aastore
    //   979: invokevirtual w : ([Ljava/lang/Object;)V
    //   982: goto -> 989
    //   985: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   988: athrow
    //   989: return
    // Exception table:
    //   from	to	target	type
    //   43	69	72	wtf/opal/x5
    //   48	77	77	wtf/opal/x5
    //   100	112	115	wtf/opal/x5
    //   106	143	146	wtf/opal/x5
    //   119	153	156	wtf/opal/x5
    //   166	200	203	wtf/opal/x5
    //   197	225	228	wtf/opal/x5
    //   207	235	238	wtf/opal/x5
    //   268	276	279	wtf/opal/x5
    //   377	387	390	wtf/opal/x5
    //   396	413	416	wtf/opal/x5
    //   410	451	454	wtf/opal/x5
    //   459	467	470	wtf/opal/x5
    //   474	483	486	wtf/opal/x5
    //   477	496	499	wtf/opal/x5
    //   551	563	566	wtf/opal/x5
    //   557	576	579	wtf/opal/x5
    //   570	589	592	wtf/opal/x5
    //   583	602	605	wtf/opal/x5
    //   596	616	619	wtf/opal/x5
    //   609	631	634	wtf/opal/x5
    //   623	664	667	wtf/opal/x5
    //   638	674	677	wtf/opal/x5
    //   695	727	730	wtf/opal/x5
    //   724	756	759	wtf/opal/x5
    //   763	771	774	wtf/opal/x5
    //   783	792	795	wtf/opal/x5
    //   803	815	818	wtf/opal/x5
    //   812	920	923	wtf/opal/x5
    //   927	935	938	wtf/opal/x5
    //   932	955	958	wtf/opal/x5
    //   962	982	985	wtf/opal/x5
  }
  
  private void lambda$new$1(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/xc.n : J
    //   3: ldc2_w 20166646939113
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 126082837374575
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 81205815090273
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 21304897267302
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic k : ()[I
    //   34: astore #10
    //   36: aload_1
    //   37: iconst_0
    //   38: anewarray java/lang/Object
    //   41: invokevirtual K : ([Ljava/lang/Object;)Z
    //   44: aload #10
    //   46: ifnull -> 84
    //   49: ifne -> 167
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: lload #4
    //   61: iconst_1
    //   62: anewarray java/lang/Object
    //   65: dup_x2
    //   66: dup_x2
    //   67: pop
    //   68: invokestatic valueOf : (J)Ljava/lang/Long;
    //   71: iconst_0
    //   72: swap
    //   73: aastore
    //   74: invokestatic h : ([Ljava/lang/Object;)Z
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: aload #10
    //   86: ifnull -> 118
    //   89: ifne -> 167
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   102: getfield field_1724 : Lnet/minecraft/class_746;
    //   105: invokevirtual method_31549 : ()Lnet/minecraft/class_1656;
    //   108: getfield field_7478 : Z
    //   111: goto -> 118
    //   114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: aload #10
    //   120: ifnull -> 152
    //   123: ifne -> 167
    //   126: goto -> 133
    //   129: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   136: getfield field_1724 : Lnet/minecraft/class_746;
    //   139: invokevirtual method_31549 : ()Lnet/minecraft/class_1656;
    //   142: getfield field_7479 : Z
    //   145: goto -> 152
    //   148: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   151: athrow
    //   152: aload #10
    //   154: ifnull -> 184
    //   157: ifeq -> 168
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: return
    //   168: aload_0
    //   169: aload #10
    //   171: ifnull -> 216
    //   174: getfield m : Z
    //   177: goto -> 184
    //   180: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   183: athrow
    //   184: ifne -> 215
    //   187: aload_0
    //   188: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   191: getfield field_1724 : Lnet/minecraft/class_746;
    //   194: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   197: getfield field_7545 : I
    //   200: putfield u : I
    //   203: aload_0
    //   204: iconst_1
    //   205: putfield m : Z
    //   208: goto -> 215
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: aload_0
    //   216: aload #10
    //   218: ifnull -> 867
    //   221: getfield t : Lnet/minecraft/class_2338;
    //   224: ifnull -> 823
    //   227: goto -> 234
    //   230: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   233: athrow
    //   234: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   237: aload #10
    //   239: ifnull -> 853
    //   242: goto -> 249
    //   245: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   248: athrow
    //   249: getfield field_1687 : Lnet/minecraft/class_638;
    //   252: aload_0
    //   253: getfield t : Lnet/minecraft/class_2338;
    //   256: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   259: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   262: instanceof net/minecraft/class_2244
    //   265: ifeq -> 823
    //   268: goto -> 275
    //   271: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   274: athrow
    //   275: aload_0
    //   276: getfield t : Lnet/minecraft/class_2338;
    //   279: astore #11
    //   281: aload_0
    //   282: aload #11
    //   284: lload #6
    //   286: iconst_2
    //   287: anewarray java/lang/Object
    //   290: dup_x2
    //   291: dup_x2
    //   292: pop
    //   293: invokestatic valueOf : (J)Ljava/lang/Long;
    //   296: iconst_1
    //   297: swap
    //   298: aastore
    //   299: dup_x1
    //   300: swap
    //   301: iconst_0
    //   302: swap
    //   303: aastore
    //   304: invokevirtual O : ([Ljava/lang/Object;)Z
    //   307: aload #10
    //   309: ifnull -> 354
    //   312: ifne -> 374
    //   315: goto -> 322
    //   318: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   321: athrow
    //   322: aload_0
    //   323: aload #10
    //   325: ifnull -> 358
    //   328: goto -> 335
    //   331: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   334: athrow
    //   335: getfield A : Lwtf/opal/ke;
    //   338: invokevirtual z : ()Ljava/lang/Object;
    //   341: checkcast java/lang/Boolean
    //   344: invokevirtual booleanValue : ()Z
    //   347: goto -> 354
    //   350: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   353: athrow
    //   354: ifeq -> 374
    //   357: aload_0
    //   358: getfield t : Lnet/minecraft/class_2338;
    //   361: iconst_0
    //   362: iconst_1
    //   363: iconst_0
    //   364: invokevirtual method_10069 : (III)Lnet/minecraft/class_2338;
    //   367: astore #11
    //   369: aload #10
    //   371: ifnonnull -> 397
    //   374: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   377: getfield field_1724 : Lnet/minecraft/class_746;
    //   380: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   383: aload_0
    //   384: getfield u : I
    //   387: putfield field_7545 : I
    //   390: goto -> 397
    //   393: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   396: athrow
    //   397: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   400: getfield field_1687 : Lnet/minecraft/class_638;
    //   403: aload #11
    //   405: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   408: lload #8
    //   410: iconst_2
    //   411: anewarray java/lang/Object
    //   414: dup_x2
    //   415: dup_x2
    //   416: pop
    //   417: invokestatic valueOf : (J)Ljava/lang/Long;
    //   420: iconst_1
    //   421: swap
    //   422: aastore
    //   423: dup_x1
    //   424: swap
    //   425: iconst_0
    //   426: swap
    //   427: aastore
    //   428: invokestatic E : ([Ljava/lang/Object;)I
    //   431: istore #12
    //   433: iload #12
    //   435: aload #10
    //   437: ifnull -> 490
    //   440: iconst_m1
    //   441: if_icmpeq -> 464
    //   444: goto -> 451
    //   447: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   450: athrow
    //   451: aload_0
    //   452: iload #12
    //   454: putfield z : I
    //   457: goto -> 464
    //   460: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   463: athrow
    //   464: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   467: aload #10
    //   469: ifnull -> 496
    //   472: getfield field_1687 : Lnet/minecraft/class_638;
    //   475: aload #11
    //   477: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   480: invokevirtual method_26215 : ()Z
    //   483: goto -> 490
    //   486: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   489: athrow
    //   490: ifeq -> 530
    //   493: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   496: getfield field_1724 : Lnet/minecraft/class_746;
    //   499: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   502: aload_0
    //   503: getfield u : I
    //   506: putfield field_7545 : I
    //   509: aload_0
    //   510: iconst_0
    //   511: putfield q : Z
    //   514: aload_0
    //   515: iconst_0
    //   516: putfield l : Z
    //   519: aload_0
    //   520: fconst_0
    //   521: putfield Q : F
    //   524: aload_0
    //   525: iconst_m1
    //   526: putfield z : I
    //   529: return
    //   530: aload #11
    //   532: getstatic net/minecraft/class_2350.field_11036 : Lnet/minecraft/class_2350;
    //   535: iconst_2
    //   536: anewarray java/lang/Object
    //   539: dup_x1
    //   540: swap
    //   541: iconst_1
    //   542: swap
    //   543: aastore
    //   544: dup_x1
    //   545: swap
    //   546: iconst_0
    //   547: swap
    //   548: aastore
    //   549: invokestatic V : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   552: astore #13
    //   554: ldc2_w 6.0
    //   557: aload #13
    //   559: getfield field_1343 : F
    //   562: aload #13
    //   564: getfield field_1342 : F
    //   567: aload #11
    //   569: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   572: getfield field_1687 : Lnet/minecraft/class_638;
    //   575: aload #11
    //   577: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   580: iconst_5
    //   581: anewarray java/lang/Object
    //   584: dup_x1
    //   585: swap
    //   586: iconst_4
    //   587: swap
    //   588: aastore
    //   589: dup_x1
    //   590: swap
    //   591: iconst_3
    //   592: swap
    //   593: aastore
    //   594: dup_x1
    //   595: swap
    //   596: invokestatic valueOf : (F)Ljava/lang/Float;
    //   599: iconst_2
    //   600: swap
    //   601: aastore
    //   602: dup_x1
    //   603: swap
    //   604: invokestatic valueOf : (F)Ljava/lang/Float;
    //   607: iconst_1
    //   608: swap
    //   609: aastore
    //   610: dup_x2
    //   611: dup_x2
    //   612: pop
    //   613: invokestatic valueOf : (D)Ljava/lang/Double;
    //   616: iconst_0
    //   617: swap
    //   618: aastore
    //   619: invokestatic W : ([Ljava/lang/Object;)Lnet/minecraft/class_3965;
    //   622: astore #14
    //   624: aload #14
    //   626: aload #10
    //   628: ifnull -> 643
    //   631: ifnull -> 818
    //   634: goto -> 641
    //   637: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   640: athrow
    //   641: aload #14
    //   643: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   646: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
    //   649: invokevirtual equals : (Ljava/lang/Object;)Z
    //   652: aload #10
    //   654: ifnull -> 678
    //   657: ifeq -> 818
    //   660: goto -> 667
    //   663: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   666: athrow
    //   667: aload_0
    //   668: getfield q : Z
    //   671: goto -> 678
    //   674: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   677: athrow
    //   678: aload #10
    //   680: ifnull -> 706
    //   683: ifne -> 818
    //   686: goto -> 693
    //   689: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   692: athrow
    //   693: aload_0
    //   694: getfield Q : F
    //   697: fconst_0
    //   698: fcmpl
    //   699: goto -> 706
    //   702: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   705: athrow
    //   706: aload #10
    //   708: ifnull -> 744
    //   711: ifne -> 818
    //   714: goto -> 721
    //   717: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   720: athrow
    //   721: aload_0
    //   722: aload #10
    //   724: ifnull -> 814
    //   727: goto -> 734
    //   730: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   733: athrow
    //   734: getfield D : I
    //   737: goto -> 744
    //   740: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   743: athrow
    //   744: ifle -> 762
    //   747: aload_0
    //   748: dup
    //   749: getfield D : I
    //   752: iconst_1
    //   753: isub
    //   754: putfield D : I
    //   757: return
    //   758: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   761: athrow
    //   762: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   765: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   768: new net/minecraft/class_2846
    //   771: dup
    //   772: getstatic net/minecraft/class_2846$class_2847.field_12968 : Lnet/minecraft/class_2846$class_2847;
    //   775: aload #14
    //   777: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
    //   780: aload #14
    //   782: invokevirtual method_17780 : ()Lnet/minecraft/class_2350;
    //   785: invokespecial <init> : (Lnet/minecraft/class_2846$class_2847;Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V
    //   788: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   791: aload_0
    //   792: new wtf/opal/lk
    //   795: dup
    //   796: aload_0
    //   797: aload #14
    //   799: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
    //   802: aload #14
    //   804: invokevirtual method_17780 : ()Lnet/minecraft/class_2350;
    //   807: invokespecial <init> : (Lwtf/opal/xc;Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V
    //   810: putfield o : Lwtf/opal/lk;
    //   813: aload_0
    //   814: iconst_1
    //   815: putfield q : Z
    //   818: aload #10
    //   820: ifnonnull -> 871
    //   823: aload_0
    //   824: iconst_0
    //   825: putfield q : Z
    //   828: aload_0
    //   829: iconst_0
    //   830: putfield l : Z
    //   833: aload_0
    //   834: iconst_m1
    //   835: putfield z : I
    //   838: aload_0
    //   839: fconst_0
    //   840: putfield Q : F
    //   843: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   846: goto -> 853
    //   849: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   852: athrow
    //   853: getfield field_1724 : Lnet/minecraft/class_746;
    //   856: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   859: aload_0
    //   860: getfield u : I
    //   863: putfield field_7545 : I
    //   866: aload_0
    //   867: iconst_0
    //   868: putfield m : Z
    //   871: aload_0
    //   872: aload #10
    //   874: ifnull -> 1306
    //   877: getfield q : Z
    //   880: ifeq -> 1305
    //   883: goto -> 890
    //   886: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   889: athrow
    //   890: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   893: getfield field_1687 : Lnet/minecraft/class_638;
    //   896: aload_0
    //   897: getfield o : Lwtf/opal/lk;
    //   900: iconst_0
    //   901: anewarray java/lang/Object
    //   904: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   907: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   910: invokevirtual method_26215 : ()Z
    //   913: aload #10
    //   915: ifnull -> 992
    //   918: goto -> 925
    //   921: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   924: athrow
    //   925: ifeq -> 976
    //   928: goto -> 935
    //   931: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   934: athrow
    //   935: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   938: getfield field_1724 : Lnet/minecraft/class_746;
    //   941: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   944: aload_0
    //   945: getfield u : I
    //   948: putfield field_7545 : I
    //   951: aload_0
    //   952: iconst_0
    //   953: putfield q : Z
    //   956: aload_0
    //   957: iconst_0
    //   958: putfield l : Z
    //   961: aload_0
    //   962: fconst_0
    //   963: putfield Q : F
    //   966: aload_0
    //   967: iconst_m1
    //   968: putfield z : I
    //   971: return
    //   972: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   975: athrow
    //   976: aload_0
    //   977: getfield p : Lwtf/opal/ky;
    //   980: invokevirtual z : ()Ljava/lang/Object;
    //   983: checkcast wtf/opal/l5
    //   986: getstatic wtf/opal/l5.CLIENT : Lwtf/opal/l5;
    //   989: invokevirtual equals : (Ljava/lang/Object;)Z
    //   992: ifeq -> 1019
    //   995: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   998: getfield field_1724 : Lnet/minecraft/class_746;
    //   1001: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   1004: invokevirtual method_6104 : (Lnet/minecraft/class_1268;)V
    //   1007: aload #10
    //   1009: ifnonnull -> 1045
    //   1012: goto -> 1019
    //   1015: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1018: athrow
    //   1019: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1022: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1025: new net/minecraft/class_2879
    //   1028: dup
    //   1029: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   1032: invokespecial <init> : (Lnet/minecraft/class_1268;)V
    //   1035: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1038: goto -> 1045
    //   1041: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1044: athrow
    //   1045: aload_0
    //   1046: dup
    //   1047: getfield Q : F
    //   1050: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1053: getfield field_1687 : Lnet/minecraft/class_638;
    //   1056: aload_0
    //   1057: getfield o : Lwtf/opal/lk;
    //   1060: iconst_0
    //   1061: anewarray java/lang/Object
    //   1064: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   1067: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   1070: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1073: getfield field_1724 : Lnet/minecraft/class_746;
    //   1076: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1079: getfield field_1687 : Lnet/minecraft/class_638;
    //   1082: aload_0
    //   1083: getfield o : Lwtf/opal/lk;
    //   1086: iconst_0
    //   1087: anewarray java/lang/Object
    //   1090: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   1093: invokevirtual method_26165 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;)F
    //   1096: fadd
    //   1097: putfield Q : F
    //   1100: aload_0
    //   1101: aload #10
    //   1103: ifnull -> 1215
    //   1106: getfield Q : F
    //   1109: f2d
    //   1110: aload_0
    //   1111: getfield L : Lwtf/opal/kt;
    //   1114: invokevirtual z : ()Ljava/lang/Object;
    //   1117: checkcast java/lang/Double
    //   1120: invokevirtual doubleValue : ()D
    //   1123: dcmpl
    //   1124: iflt -> 1235
    //   1127: goto -> 1134
    //   1130: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1133: athrow
    //   1134: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1137: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1140: new net/minecraft/class_2846
    //   1143: dup
    //   1144: getstatic net/minecraft/class_2846$class_2847.field_12973 : Lnet/minecraft/class_2846$class_2847;
    //   1147: aload_0
    //   1148: getfield o : Lwtf/opal/lk;
    //   1151: iconst_0
    //   1152: anewarray java/lang/Object
    //   1155: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   1158: aload_0
    //   1159: getfield o : Lwtf/opal/lk;
    //   1162: iconst_0
    //   1163: anewarray java/lang/Object
    //   1166: invokevirtual l : ([Ljava/lang/Object;)Lnet/minecraft/class_2350;
    //   1169: invokespecial <init> : (Lnet/minecraft/class_2846$class_2847;Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V
    //   1172: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1175: aload_0
    //   1176: fconst_0
    //   1177: putfield Q : F
    //   1180: aload_0
    //   1181: iconst_0
    //   1182: putfield q : Z
    //   1185: aload_0
    //   1186: aload_0
    //   1187: getfield G : Lwtf/opal/kt;
    //   1190: invokevirtual z : ()Ljava/lang/Object;
    //   1193: checkcast java/lang/Double
    //   1196: invokevirtual intValue : ()I
    //   1199: putfield D : I
    //   1202: aload_0
    //   1203: iconst_m1
    //   1204: putfield z : I
    //   1207: aload_0
    //   1208: goto -> 1215
    //   1211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1214: athrow
    //   1215: iconst_0
    //   1216: putfield l : Z
    //   1219: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1222: getfield field_1724 : Lnet/minecraft/class_746;
    //   1225: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   1228: aload_0
    //   1229: getfield u : I
    //   1232: putfield field_7545 : I
    //   1235: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1238: getfield field_1687 : Lnet/minecraft/class_638;
    //   1241: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1244: getfield field_1724 : Lnet/minecraft/class_746;
    //   1247: invokevirtual method_5628 : ()I
    //   1250: aload_0
    //   1251: getfield o : Lwtf/opal/lk;
    //   1254: iconst_0
    //   1255: anewarray java/lang/Object
    //   1258: invokevirtual x : ([Ljava/lang/Object;)Lnet/minecraft/class_2338;
    //   1261: aload_0
    //   1262: getfield Q : F
    //   1265: fconst_0
    //   1266: fcmpl
    //   1267: aload #10
    //   1269: ifnull -> 1298
    //   1272: ifle -> 1301
    //   1275: goto -> 1282
    //   1278: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1281: athrow
    //   1282: aload_0
    //   1283: getfield Q : F
    //   1286: ldc_w 10.0
    //   1289: fmul
    //   1290: f2i
    //   1291: goto -> 1298
    //   1294: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1297: athrow
    //   1298: goto -> 1302
    //   1301: iconst_m1
    //   1302: invokevirtual method_8517 : (ILnet/minecraft/class_2338;I)V
    //   1305: aload_0
    //   1306: aconst_null
    //   1307: putfield t : Lnet/minecraft/class_2338;
    //   1310: return
    // Exception table:
    //   from	to	target	type
    //   36	52	55	wtf/opal/x5
    //   49	77	80	wtf/opal/x5
    //   84	92	95	wtf/opal/x5
    //   89	111	114	wtf/opal/x5
    //   118	126	129	wtf/opal/x5
    //   123	145	148	wtf/opal/x5
    //   152	160	163	wtf/opal/x5
    //   168	177	180	wtf/opal/x5
    //   184	208	211	wtf/opal/x5
    //   216	227	230	wtf/opal/x5
    //   221	242	245	wtf/opal/x5
    //   234	268	271	wtf/opal/x5
    //   281	315	318	wtf/opal/x5
    //   312	328	331	wtf/opal/x5
    //   322	347	350	wtf/opal/x5
    //   369	390	393	wtf/opal/x5
    //   433	444	447	wtf/opal/x5
    //   440	457	460	wtf/opal/x5
    //   464	483	486	wtf/opal/x5
    //   624	634	637	wtf/opal/x5
    //   643	660	663	wtf/opal/x5
    //   657	671	674	wtf/opal/x5
    //   678	686	689	wtf/opal/x5
    //   683	699	702	wtf/opal/x5
    //   706	714	717	wtf/opal/x5
    //   711	727	730	wtf/opal/x5
    //   721	737	740	wtf/opal/x5
    //   744	758	758	wtf/opal/x5
    //   818	846	849	wtf/opal/x5
    //   871	883	886	wtf/opal/x5
    //   877	918	921	wtf/opal/x5
    //   890	928	931	wtf/opal/x5
    //   925	972	972	wtf/opal/x5
    //   992	1012	1015	wtf/opal/x5
    //   995	1038	1041	wtf/opal/x5
    //   1045	1127	1130	wtf/opal/x5
    //   1106	1208	1211	wtf/opal/x5
    //   1235	1275	1278	wtf/opal/x5
    //   1272	1291	1294	wtf/opal/x5
  }
  
  private boolean lambda$new$0(ke paramke) {
    return this.v.z().booleanValue();
  }
  
  static {
    long l = n ^ 0x39F2EB1986DFL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[17];
    boolean bool = false;
    String str;
    int i = (str = "F<ú\035_­\030\b\007´¦9ðÄ(+Öa\037\tMM6õªzþø¼ý\nuÍ§í]QnÊG{\rNjÝniå|Ú\020yw{¥£hÛ\027¼úÝ\007\n(Yo.àIM9ráÉªòmÖ¸ßþoîÁ \027ÜÊ§ð·ßI@Ý°8xÙÍë©\034AKJ\n;À<\005ÃH]F'o`\035\013SôC\0023§µÒ%Í-C}ä(Yè 7-¶Cû(I2\021B¢-?¹Òà©©sí²â¤'&Ôún\001\006SRàurJ|\030¦\032\037\t\020õ©Dñ¶õ~\002ÿüN6A¬¶\020yfÙÝu%£ðÞ¼ëvÜëQ \017\036cÊ½Ì¹ho\020\\\024nX[Ä¼A§\036j#g<±^â&»\030×\"¾«ÿA+£Èw¢`Çi¥\023\bg\bU\020_Zí%\\\017¼\b`4j\0332+Ô\024\020¢MuG0\030{=ö54Ói¯¹(ÕuH)fÆ¬®ü¯0P\"×X/\021î;u\001Çà\"½«!'Ûµnn\001n\020´u\007y-Ê¡ I8|Æèù8éNFíUo©¿íÛ´Ñ®{fvn\rÍSc_Ù6L¡Ë\004½\001¥=?ìÐ¯z\025;!\nÉ\026").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x31A9;
    if (s[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])x.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          x.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xc", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = r[i].getBytes("ISO-8859-1");
      s[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return s[i];
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
    //   66: ldc_w 'wtf/opal/xc'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4C7E;
    if (J[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = I[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])M.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          M.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xc", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      J[i] = Integer.valueOf(j);
    } 
    return J[i].intValue();
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
    //   66: ldc_w 'wtf/opal/xc'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xc.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */