package wtf.opal;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1887;
import net.minecraft.class_332;
import net.minecraft.class_4604;
import org.joml.Matrix4f;

public final class jy extends d {
  private final pa D;
  
  private final bu x;
  
  private final ke r;
  
  private final ke J;
  
  private final ke t;
  
  private final ke L;
  
  private final ky<kh> G;
  
  private final kd k;
  
  private final kt X;
  
  private final kt I;
  
  private int y;
  
  private int q;
  
  private int P;
  
  private int U;
  
  private final gm<uj> b;
  
  private static final Map<class_1887, String> d;
  
  private static final long a = on.a(-7361200213727407840L, -1075934492529153641L, MethodHandles.lookup().lookupClass()).a(137839881213331L);
  
  private static final String[] f;
  
  private static final String[] g;
  
  private static final Map l = new HashMap<>(13);
  
  private static final long[] m;
  
  private static final Integer[] n;
  
  private static final Map o;
  
  public jy(int paramInt, long paramLong) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: lload_2
    //   6: bipush #32
    //   8: lshl
    //   9: bipush #32
    //   11: lushr
    //   12: lor
    //   13: getstatic wtf/opal/jy.a : J
    //   16: lxor
    //   17: lstore #4
    //   19: lload #4
    //   21: dup2
    //   22: ldc2_w 18571602905348
    //   25: lxor
    //   26: lstore #6
    //   28: dup2
    //   29: ldc2_w 96753252498040
    //   32: lxor
    //   33: lstore #8
    //   35: dup2
    //   36: ldc2_w 133597327696685
    //   39: lxor
    //   40: lstore #10
    //   42: pop2
    //   43: aload_0
    //   44: sipush #18364
    //   47: ldc2_w 6280590591126980900
    //   50: lload #4
    //   52: lxor
    //   53: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   58: lload #10
    //   60: sipush #21936
    //   63: ldc2_w 7063979004763986730
    //   66: lload #4
    //   68: lxor
    //   69: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   74: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   77: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   80: aload_0
    //   81: iconst_0
    //   82: anewarray java/lang/Object
    //   85: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   88: iconst_0
    //   89: anewarray java/lang/Object
    //   92: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   95: putfield D : Lwtf/opal/pa;
    //   98: aload_0
    //   99: iconst_0
    //   100: anewarray java/lang/Object
    //   103: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   106: iconst_0
    //   107: anewarray java/lang/Object
    //   110: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   113: putfield x : Lwtf/opal/bu;
    //   116: aload_0
    //   117: new wtf/opal/ke
    //   120: dup
    //   121: sipush #32081
    //   124: ldc2_w 2991186567624291270
    //   127: lload #4
    //   129: lxor
    //   130: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   135: iconst_1
    //   136: invokespecial <init> : (Ljava/lang/String;Z)V
    //   139: putfield r : Lwtf/opal/ke;
    //   142: aload_0
    //   143: new wtf/opal/ke
    //   146: dup
    //   147: sipush #836
    //   150: ldc2_w 2487469458830200262
    //   153: lload #4
    //   155: lxor
    //   156: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   161: iconst_1
    //   162: invokespecial <init> : (Ljava/lang/String;Z)V
    //   165: putfield J : Lwtf/opal/ke;
    //   168: aload_0
    //   169: new wtf/opal/ke
    //   172: dup
    //   173: sipush #16183
    //   176: ldc2_w 8860138726714519979
    //   179: lload #4
    //   181: lxor
    //   182: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   187: iconst_1
    //   188: invokespecial <init> : (Ljava/lang/String;Z)V
    //   191: putfield t : Lwtf/opal/ke;
    //   194: aload_0
    //   195: new wtf/opal/ke
    //   198: dup
    //   199: sipush #12364
    //   202: ldc2_w 4678275505579638483
    //   205: lload #4
    //   207: lxor
    //   208: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   213: iconst_1
    //   214: invokespecial <init> : (Ljava/lang/String;Z)V
    //   217: putfield L : Lwtf/opal/ke;
    //   220: aload_0
    //   221: new wtf/opal/ky
    //   224: dup
    //   225: sipush #28079
    //   228: ldc2_w 8951065560457831228
    //   231: lload #4
    //   233: lxor
    //   234: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   239: getstatic wtf/opal/kh.GRADIENT : Lwtf/opal/kh;
    //   242: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   245: putfield G : Lwtf/opal/ky;
    //   248: aload_0
    //   249: new wtf/opal/kd
    //   252: dup
    //   253: sipush #20685
    //   256: ldc2_w 972383504533610061
    //   259: lload #4
    //   261: lxor
    //   262: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   267: iconst_5
    //   268: anewarray wtf/opal/ke
    //   271: dup
    //   272: iconst_0
    //   273: new wtf/opal/ke
    //   276: dup
    //   277: sipush #14108
    //   280: ldc2_w 1056622089689577876
    //   283: lload #4
    //   285: lxor
    //   286: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   291: iconst_1
    //   292: invokespecial <init> : (Ljava/lang/String;Z)V
    //   295: aastore
    //   296: dup
    //   297: iconst_1
    //   298: new wtf/opal/ke
    //   301: dup
    //   302: sipush #12739
    //   305: ldc2_w 6653266972884549440
    //   308: lload #4
    //   310: lxor
    //   311: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   316: iconst_0
    //   317: invokespecial <init> : (Ljava/lang/String;Z)V
    //   320: aastore
    //   321: dup
    //   322: iconst_2
    //   323: new wtf/opal/ke
    //   326: dup
    //   327: sipush #28233
    //   330: ldc2_w 8152208492450308319
    //   333: lload #4
    //   335: lxor
    //   336: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   341: iconst_1
    //   342: invokespecial <init> : (Ljava/lang/String;Z)V
    //   345: aastore
    //   346: dup
    //   347: iconst_3
    //   348: new wtf/opal/ke
    //   351: dup
    //   352: sipush #12848
    //   355: ldc2_w 2938438804722269344
    //   358: lload #4
    //   360: lxor
    //   361: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   366: iconst_1
    //   367: invokespecial <init> : (Ljava/lang/String;Z)V
    //   370: aastore
    //   371: dup
    //   372: iconst_4
    //   373: new wtf/opal/ke
    //   376: dup
    //   377: sipush #29352
    //   380: ldc2_w 4468380262327647278
    //   383: lload #4
    //   385: lxor
    //   386: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   391: iconst_1
    //   392: invokespecial <init> : (Ljava/lang/String;Z)V
    //   395: aastore
    //   396: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   399: putfield k : Lwtf/opal/kd;
    //   402: aload_0
    //   403: new wtf/opal/kt
    //   406: dup
    //   407: sipush #26625
    //   410: ldc2_w 3767802615389513364
    //   413: lload #4
    //   415: lxor
    //   416: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   421: ldc2_w 6.0
    //   424: dconst_1
    //   425: ldc2_w 15.0
    //   428: dconst_1
    //   429: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   432: putfield X : Lwtf/opal/kt;
    //   435: aload_0
    //   436: new wtf/opal/kt
    //   439: dup
    //   440: sipush #12908
    //   443: ldc2_w 1507676600038767861
    //   446: lload #4
    //   448: lxor
    //   449: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   454: ldc2_w 40.0
    //   457: dconst_0
    //   458: ldc2_w 100.0
    //   461: dconst_1
    //   462: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   465: putfield I : Lwtf/opal/kt;
    //   468: aload_0
    //   469: iconst_0
    //   470: putfield y : I
    //   473: aload_0
    //   474: iconst_0
    //   475: putfield q : I
    //   478: aload_0
    //   479: iconst_0
    //   480: putfield P : I
    //   483: aload_0
    //   484: iconst_0
    //   485: putfield U : I
    //   488: aload_0
    //   489: aload_0
    //   490: <illegal opcode> H : (Lwtf/opal/jy;)Lwtf/opal/gm;
    //   495: putfield b : Lwtf/opal/gm;
    //   498: aload_0
    //   499: getfield t : Lwtf/opal/ke;
    //   502: aload_0
    //   503: getfield J : Lwtf/opal/ke;
    //   506: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   511: lload #6
    //   513: dup2_x1
    //   514: pop2
    //   515: iconst_3
    //   516: anewarray java/lang/Object
    //   519: dup_x1
    //   520: swap
    //   521: iconst_2
    //   522: swap
    //   523: aastore
    //   524: dup_x2
    //   525: dup_x2
    //   526: pop
    //   527: invokestatic valueOf : (J)Ljava/lang/Long;
    //   530: iconst_1
    //   531: swap
    //   532: aastore
    //   533: dup_x1
    //   534: swap
    //   535: iconst_0
    //   536: swap
    //   537: aastore
    //   538: invokevirtual C : ([Ljava/lang/Object;)V
    //   541: aload_0
    //   542: getfield G : Lwtf/opal/ky;
    //   545: aload_0
    //   546: getfield J : Lwtf/opal/ke;
    //   549: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   554: lload #6
    //   556: dup2_x1
    //   557: pop2
    //   558: iconst_3
    //   559: anewarray java/lang/Object
    //   562: dup_x1
    //   563: swap
    //   564: iconst_2
    //   565: swap
    //   566: aastore
    //   567: dup_x2
    //   568: dup_x2
    //   569: pop
    //   570: invokestatic valueOf : (J)Ljava/lang/Long;
    //   573: iconst_1
    //   574: swap
    //   575: aastore
    //   576: dup_x1
    //   577: swap
    //   578: iconst_0
    //   579: swap
    //   580: aastore
    //   581: invokevirtual C : ([Ljava/lang/Object;)V
    //   584: aload_0
    //   585: getfield k : Lwtf/opal/kd;
    //   588: aload_0
    //   589: getfield L : Lwtf/opal/ke;
    //   592: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   597: lload #6
    //   599: dup2_x1
    //   600: pop2
    //   601: iconst_3
    //   602: anewarray java/lang/Object
    //   605: dup_x1
    //   606: swap
    //   607: iconst_2
    //   608: swap
    //   609: aastore
    //   610: dup_x2
    //   611: dup_x2
    //   612: pop
    //   613: invokestatic valueOf : (J)Ljava/lang/Long;
    //   616: iconst_1
    //   617: swap
    //   618: aastore
    //   619: dup_x1
    //   620: swap
    //   621: iconst_0
    //   622: swap
    //   623: aastore
    //   624: invokevirtual C : ([Ljava/lang/Object;)V
    //   627: aload_0
    //   628: getfield X : Lwtf/opal/kt;
    //   631: aload_0
    //   632: getfield L : Lwtf/opal/ke;
    //   635: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   640: lload #6
    //   642: dup2_x1
    //   643: pop2
    //   644: iconst_3
    //   645: anewarray java/lang/Object
    //   648: dup_x1
    //   649: swap
    //   650: iconst_2
    //   651: swap
    //   652: aastore
    //   653: dup_x2
    //   654: dup_x2
    //   655: pop
    //   656: invokestatic valueOf : (J)Ljava/lang/Long;
    //   659: iconst_1
    //   660: swap
    //   661: aastore
    //   662: dup_x1
    //   663: swap
    //   664: iconst_0
    //   665: swap
    //   666: aastore
    //   667: invokevirtual C : ([Ljava/lang/Object;)V
    //   670: aload_0
    //   671: getfield I : Lwtf/opal/kt;
    //   674: aload_0
    //   675: getfield k : Lwtf/opal/kd;
    //   678: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   683: lload #6
    //   685: dup2_x1
    //   686: pop2
    //   687: iconst_3
    //   688: anewarray java/lang/Object
    //   691: dup_x1
    //   692: swap
    //   693: iconst_2
    //   694: swap
    //   695: aastore
    //   696: dup_x2
    //   697: dup_x2
    //   698: pop
    //   699: invokestatic valueOf : (J)Ljava/lang/Long;
    //   702: iconst_1
    //   703: swap
    //   704: aastore
    //   705: dup_x1
    //   706: swap
    //   707: iconst_0
    //   708: swap
    //   709: aastore
    //   710: invokevirtual C : ([Ljava/lang/Object;)V
    //   713: aload_0
    //   714: sipush #3060
    //   717: ldc2_w 5536872029169764815
    //   720: lload #4
    //   722: lxor
    //   723: <illegal opcode> t : (IJ)I
    //   728: anewarray wtf/opal/k3
    //   731: dup
    //   732: iconst_0
    //   733: aload_0
    //   734: getfield r : Lwtf/opal/ke;
    //   737: aastore
    //   738: dup
    //   739: iconst_1
    //   740: aload_0
    //   741: getfield J : Lwtf/opal/ke;
    //   744: aastore
    //   745: dup
    //   746: iconst_2
    //   747: aload_0
    //   748: getfield G : Lwtf/opal/ky;
    //   751: aastore
    //   752: dup
    //   753: iconst_3
    //   754: aload_0
    //   755: getfield t : Lwtf/opal/ke;
    //   758: aastore
    //   759: dup
    //   760: iconst_4
    //   761: aload_0
    //   762: getfield L : Lwtf/opal/ke;
    //   765: aastore
    //   766: dup
    //   767: iconst_5
    //   768: aload_0
    //   769: getfield k : Lwtf/opal/kd;
    //   772: aastore
    //   773: dup
    //   774: sipush #26398
    //   777: ldc2_w 2478425161003869473
    //   780: lload #4
    //   782: lxor
    //   783: <illegal opcode> t : (IJ)I
    //   788: aload_0
    //   789: getfield X : Lwtf/opal/kt;
    //   792: aastore
    //   793: dup
    //   794: sipush #27308
    //   797: ldc2_w 31483222812764306
    //   800: lload #4
    //   802: lxor
    //   803: <illegal opcode> t : (IJ)I
    //   808: aload_0
    //   809: getfield I : Lwtf/opal/kt;
    //   812: aastore
    //   813: lload #8
    //   815: dup2_x1
    //   816: pop2
    //   817: iconst_2
    //   818: anewarray java/lang/Object
    //   821: dup_x1
    //   822: swap
    //   823: iconst_1
    //   824: swap
    //   825: aastore
    //   826: dup_x2
    //   827: dup_x2
    //   828: pop
    //   829: invokestatic valueOf : (J)Ljava/lang/Long;
    //   832: iconst_0
    //   833: swap
    //   834: aastore
    //   835: invokevirtual o : ([Ljava/lang/Object;)V
    //   838: return
  }
  
  private void S(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore #5
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast net/minecraft/class_1309
    //   15: astore #8
    //   17: dup
    //   18: iconst_2
    //   19: aaload
    //   20: checkcast [I
    //   23: astore #4
    //   25: dup
    //   26: iconst_3
    //   27: aaload
    //   28: checkcast org/joml/Matrix4f
    //   31: astore #7
    //   33: dup
    //   34: iconst_4
    //   35: aaload
    //   36: checkcast java/lang/Float
    //   39: invokevirtual floatValue : ()F
    //   42: fstore #6
    //   44: dup
    //   45: iconst_5
    //   46: aaload
    //   47: checkcast java/lang/Long
    //   50: invokevirtual longValue : ()J
    //   53: lstore_2
    //   54: pop
    //   55: getstatic wtf/opal/jy.a : J
    //   58: lload_2
    //   59: lxor
    //   60: lstore_2
    //   61: lload_2
    //   62: dup2
    //   63: ldc2_w 15007664565845
    //   66: lxor
    //   67: lstore #9
    //   69: dup2
    //   70: ldc2_w 140687765595536
    //   73: lxor
    //   74: lstore #11
    //   76: dup2
    //   77: ldc2_w 35666929645651
    //   80: lxor
    //   81: lstore #13
    //   83: dup2
    //   84: ldc2_w 82115902269712
    //   87: lxor
    //   88: lstore #15
    //   90: dup2
    //   91: ldc2_w 29580551696923
    //   94: lxor
    //   95: lstore #17
    //   97: dup2
    //   98: ldc2_w 70227262267618
    //   101: lxor
    //   102: lstore #19
    //   104: dup2
    //   105: ldc2_w 72549050719979
    //   108: lxor
    //   109: lstore #21
    //   111: dup2
    //   112: ldc2_w 114021184073503
    //   115: lxor
    //   116: lstore #23
    //   118: pop2
    //   119: invokestatic S : ()Ljava/lang/String;
    //   122: aload #8
    //   124: fload #6
    //   126: iconst_2
    //   127: anewarray java/lang/Object
    //   130: dup_x1
    //   131: swap
    //   132: invokestatic valueOf : (F)Ljava/lang/Float;
    //   135: iconst_1
    //   136: swap
    //   137: aastore
    //   138: dup_x1
    //   139: swap
    //   140: iconst_0
    //   141: swap
    //   142: aastore
    //   143: invokestatic F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   146: astore #26
    //   148: astore #25
    //   150: aload #8
    //   152: invokevirtual method_17681 : ()F
    //   155: fconst_2
    //   156: fdiv
    //   157: fstore #27
    //   159: aload #8
    //   161: invokevirtual method_17682 : ()F
    //   164: aload #8
    //   166: invokevirtual method_5715 : ()Z
    //   169: ifeq -> 182
    //   172: ldc_w 0.1
    //   175: goto -> 185
    //   178: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   181: athrow
    //   182: ldc_w 0.2
    //   185: fadd
    //   186: fstore #28
    //   188: new net/minecraft/class_238
    //   191: dup
    //   192: aload #26
    //   194: getfield field_1352 : D
    //   197: fload #27
    //   199: f2d
    //   200: dsub
    //   201: aload #26
    //   203: getfield field_1351 : D
    //   206: aload #26
    //   208: getfield field_1350 : D
    //   211: fload #27
    //   213: f2d
    //   214: dsub
    //   215: aload #26
    //   217: getfield field_1352 : D
    //   220: fload #27
    //   222: f2d
    //   223: dadd
    //   224: aload #26
    //   226: getfield field_1351 : D
    //   229: fload #28
    //   231: f2d
    //   232: dadd
    //   233: aload #26
    //   235: getfield field_1350 : D
    //   238: fload #27
    //   240: f2d
    //   241: dadd
    //   242: invokespecial <init> : (DDDDDD)V
    //   245: astore #29
    //   247: aload #4
    //   249: aload #7
    //   251: aload #29
    //   253: lload #21
    //   255: iconst_4
    //   256: anewarray java/lang/Object
    //   259: dup_x2
    //   260: dup_x2
    //   261: pop
    //   262: invokestatic valueOf : (J)Ljava/lang/Long;
    //   265: iconst_3
    //   266: swap
    //   267: aastore
    //   268: dup_x1
    //   269: swap
    //   270: iconst_2
    //   271: swap
    //   272: aastore
    //   273: dup_x1
    //   274: swap
    //   275: iconst_1
    //   276: swap
    //   277: aastore
    //   278: dup_x1
    //   279: swap
    //   280: iconst_0
    //   281: swap
    //   282: aastore
    //   283: invokestatic S : ([Ljava/lang/Object;)Lorg/joml/Vector4d;
    //   286: astore #30
    //   288: aload #30
    //   290: aload #25
    //   292: ifnonnull -> 353
    //   295: ifnonnull -> 306
    //   298: goto -> 305
    //   301: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   304: athrow
    //   305: return
    //   306: aload #30
    //   308: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   311: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   314: invokevirtual method_4495 : ()D
    //   317: invokevirtual div : (D)Lorg/joml/Vector4d;
    //   320: pop
    //   321: aload #30
    //   323: dup
    //   324: getfield z : D
    //   327: aload #30
    //   329: getfield x : D
    //   332: dsub
    //   333: putfield z : D
    //   336: aload #30
    //   338: dup
    //   339: getfield w : D
    //   342: aload #30
    //   344: getfield y : D
    //   347: dsub
    //   348: putfield w : D
    //   351: aload #30
    //   353: getfield x : D
    //   356: d2f
    //   357: fstore #31
    //   359: aload #30
    //   361: getfield y : D
    //   364: d2f
    //   365: fstore #32
    //   367: aload #30
    //   369: getfield z : D
    //   372: d2f
    //   373: fstore #33
    //   375: aload #30
    //   377: getfield w : D
    //   380: d2f
    //   381: fstore #34
    //   383: aload_0
    //   384: getfield J : Lwtf/opal/ke;
    //   387: invokevirtual z : ()Ljava/lang/Object;
    //   390: checkcast java/lang/Boolean
    //   393: invokevirtual booleanValue : ()Z
    //   396: aload #25
    //   398: ifnonnull -> 1422
    //   401: ifeq -> 1409
    //   404: goto -> 411
    //   407: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   410: athrow
    //   411: aload_0
    //   412: aload #25
    //   414: ifnonnull -> 584
    //   417: goto -> 424
    //   420: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   423: athrow
    //   424: getfield t : Lwtf/opal/ke;
    //   427: invokevirtual z : ()Ljava/lang/Object;
    //   430: checkcast java/lang/Boolean
    //   433: invokevirtual booleanValue : ()Z
    //   436: lload_2
    //   437: lconst_0
    //   438: lcmp
    //   439: ifle -> 556
    //   442: ifeq -> 555
    //   445: goto -> 452
    //   448: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   451: athrow
    //   452: aload_0
    //   453: getfield D : Lwtf/opal/pa;
    //   456: fload #31
    //   458: fload #32
    //   460: fload #33
    //   462: lload #19
    //   464: fload #34
    //   466: ldc_w 1.5
    //   469: sipush #4016
    //   472: ldc2_w 7187044352133213409
    //   475: lload_2
    //   476: lxor
    //   477: <illegal opcode> t : (IJ)I
    //   482: bipush #7
    //   484: anewarray java/lang/Object
    //   487: dup_x1
    //   488: swap
    //   489: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   492: bipush #6
    //   494: swap
    //   495: aastore
    //   496: dup_x1
    //   497: swap
    //   498: invokestatic valueOf : (F)Ljava/lang/Float;
    //   501: iconst_5
    //   502: swap
    //   503: aastore
    //   504: dup_x1
    //   505: swap
    //   506: invokestatic valueOf : (F)Ljava/lang/Float;
    //   509: iconst_4
    //   510: swap
    //   511: aastore
    //   512: dup_x2
    //   513: dup_x2
    //   514: pop
    //   515: invokestatic valueOf : (J)Ljava/lang/Long;
    //   518: iconst_3
    //   519: swap
    //   520: aastore
    //   521: dup_x1
    //   522: swap
    //   523: invokestatic valueOf : (F)Ljava/lang/Float;
    //   526: iconst_2
    //   527: swap
    //   528: aastore
    //   529: dup_x1
    //   530: swap
    //   531: invokestatic valueOf : (F)Ljava/lang/Float;
    //   534: iconst_1
    //   535: swap
    //   536: aastore
    //   537: dup_x1
    //   538: swap
    //   539: invokestatic valueOf : (F)Ljava/lang/Float;
    //   542: iconst_0
    //   543: swap
    //   544: aastore
    //   545: invokevirtual P : ([Ljava/lang/Object;)V
    //   548: goto -> 555
    //   551: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   554: athrow
    //   555: iconst_0
    //   556: anewarray java/lang/Object
    //   559: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   562: iconst_0
    //   563: anewarray java/lang/Object
    //   566: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   569: ldc_w wtf/opal/jt
    //   572: iconst_1
    //   573: anewarray java/lang/Object
    //   576: dup_x1
    //   577: swap
    //   578: iconst_0
    //   579: swap
    //   580: aastore
    //   581: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   584: checkcast wtf/opal/jt
    //   587: astore #35
    //   589: aload_0
    //   590: getfield G : Lwtf/opal/ky;
    //   593: invokevirtual z : ()Ljava/lang/Object;
    //   596: checkcast wtf/opal/kh
    //   599: getstatic wtf/opal/kh.GRADIENT : Lwtf/opal/kh;
    //   602: invokevirtual equals : (Ljava/lang/Object;)Z
    //   605: lload_2
    //   606: lconst_0
    //   607: lcmp
    //   608: iflt -> 1203
    //   611: aload #25
    //   613: ifnonnull -> 1203
    //   616: ifeq -> 1171
    //   619: goto -> 626
    //   622: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   625: athrow
    //   626: aload_0
    //   627: aload #35
    //   629: iconst_0
    //   630: anewarray java/lang/Object
    //   633: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   636: invokevirtual z : ()Ljava/lang/Object;
    //   639: checkcast java/lang/Integer
    //   642: invokevirtual intValue : ()I
    //   645: aload #35
    //   647: iconst_0
    //   648: anewarray java/lang/Object
    //   651: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   654: invokevirtual z : ()Ljava/lang/Object;
    //   657: checkcast java/lang/Integer
    //   660: invokevirtual intValue : ()I
    //   663: lload #13
    //   665: dup2_x1
    //   666: pop2
    //   667: iconst_3
    //   668: anewarray java/lang/Object
    //   671: dup_x1
    //   672: swap
    //   673: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   676: iconst_2
    //   677: swap
    //   678: aastore
    //   679: dup_x2
    //   680: dup_x2
    //   681: pop
    //   682: invokestatic valueOf : (J)Ljava/lang/Long;
    //   685: iconst_1
    //   686: swap
    //   687: aastore
    //   688: dup_x1
    //   689: swap
    //   690: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   693: iconst_0
    //   694: swap
    //   695: aastore
    //   696: invokevirtual f : ([Ljava/lang/Object;)V
    //   699: aload_0
    //   700: getfield D : Lwtf/opal/pa;
    //   703: fload #31
    //   705: ldc_w 0.25
    //   708: fsub
    //   709: fload #32
    //   711: ldc_w 0.25
    //   714: fsub
    //   715: fload #33
    //   717: ldc_w 0.5
    //   720: fadd
    //   721: ldc_w 0.5
    //   724: aload_0
    //   725: getfield y : I
    //   728: aload_0
    //   729: getfield q : I
    //   732: lload #15
    //   734: dup2_x1
    //   735: pop2
    //   736: fconst_0
    //   737: bipush #8
    //   739: anewarray java/lang/Object
    //   742: dup_x1
    //   743: swap
    //   744: invokestatic valueOf : (F)Ljava/lang/Float;
    //   747: bipush #7
    //   749: swap
    //   750: aastore
    //   751: dup_x1
    //   752: swap
    //   753: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   756: bipush #6
    //   758: swap
    //   759: aastore
    //   760: dup_x2
    //   761: dup_x2
    //   762: pop
    //   763: invokestatic valueOf : (J)Ljava/lang/Long;
    //   766: iconst_5
    //   767: swap
    //   768: aastore
    //   769: dup_x1
    //   770: swap
    //   771: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   774: iconst_4
    //   775: swap
    //   776: aastore
    //   777: dup_x1
    //   778: swap
    //   779: invokestatic valueOf : (F)Ljava/lang/Float;
    //   782: iconst_3
    //   783: swap
    //   784: aastore
    //   785: dup_x1
    //   786: swap
    //   787: invokestatic valueOf : (F)Ljava/lang/Float;
    //   790: iconst_2
    //   791: swap
    //   792: aastore
    //   793: dup_x1
    //   794: swap
    //   795: invokestatic valueOf : (F)Ljava/lang/Float;
    //   798: iconst_1
    //   799: swap
    //   800: aastore
    //   801: dup_x1
    //   802: swap
    //   803: invokestatic valueOf : (F)Ljava/lang/Float;
    //   806: iconst_0
    //   807: swap
    //   808: aastore
    //   809: invokevirtual Q : ([Ljava/lang/Object;)V
    //   812: aload_0
    //   813: getfield D : Lwtf/opal/pa;
    //   816: fload #31
    //   818: ldc_w 0.25
    //   821: fsub
    //   822: fload #32
    //   824: ldc_w 0.25
    //   827: fsub
    //   828: ldc_w 0.5
    //   831: fload #34
    //   833: ldc_w 0.5
    //   836: fadd
    //   837: aload_0
    //   838: getfield y : I
    //   841: aload_0
    //   842: getfield U : I
    //   845: lload #15
    //   847: dup2_x1
    //   848: pop2
    //   849: ldc_w 90.0
    //   852: bipush #8
    //   854: anewarray java/lang/Object
    //   857: dup_x1
    //   858: swap
    //   859: invokestatic valueOf : (F)Ljava/lang/Float;
    //   862: bipush #7
    //   864: swap
    //   865: aastore
    //   866: dup_x1
    //   867: swap
    //   868: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   871: bipush #6
    //   873: swap
    //   874: aastore
    //   875: dup_x2
    //   876: dup_x2
    //   877: pop
    //   878: invokestatic valueOf : (J)Ljava/lang/Long;
    //   881: iconst_5
    //   882: swap
    //   883: aastore
    //   884: dup_x1
    //   885: swap
    //   886: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   889: iconst_4
    //   890: swap
    //   891: aastore
    //   892: dup_x1
    //   893: swap
    //   894: invokestatic valueOf : (F)Ljava/lang/Float;
    //   897: iconst_3
    //   898: swap
    //   899: aastore
    //   900: dup_x1
    //   901: swap
    //   902: invokestatic valueOf : (F)Ljava/lang/Float;
    //   905: iconst_2
    //   906: swap
    //   907: aastore
    //   908: dup_x1
    //   909: swap
    //   910: invokestatic valueOf : (F)Ljava/lang/Float;
    //   913: iconst_1
    //   914: swap
    //   915: aastore
    //   916: dup_x1
    //   917: swap
    //   918: invokestatic valueOf : (F)Ljava/lang/Float;
    //   921: iconst_0
    //   922: swap
    //   923: aastore
    //   924: invokevirtual Q : ([Ljava/lang/Object;)V
    //   927: aload_0
    //   928: getfield D : Lwtf/opal/pa;
    //   931: fload #31
    //   933: fload #32
    //   935: fload #34
    //   937: fadd
    //   938: ldc_w 0.25
    //   941: fsub
    //   942: fload #33
    //   944: ldc_w 0.5
    //   947: aload_0
    //   948: getfield U : I
    //   951: aload_0
    //   952: getfield P : I
    //   955: lload #15
    //   957: dup2_x1
    //   958: pop2
    //   959: fconst_0
    //   960: bipush #8
    //   962: anewarray java/lang/Object
    //   965: dup_x1
    //   966: swap
    //   967: invokestatic valueOf : (F)Ljava/lang/Float;
    //   970: bipush #7
    //   972: swap
    //   973: aastore
    //   974: dup_x1
    //   975: swap
    //   976: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   979: bipush #6
    //   981: swap
    //   982: aastore
    //   983: dup_x2
    //   984: dup_x2
    //   985: pop
    //   986: invokestatic valueOf : (J)Ljava/lang/Long;
    //   989: iconst_5
    //   990: swap
    //   991: aastore
    //   992: dup_x1
    //   993: swap
    //   994: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   997: iconst_4
    //   998: swap
    //   999: aastore
    //   1000: dup_x1
    //   1001: swap
    //   1002: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1005: iconst_3
    //   1006: swap
    //   1007: aastore
    //   1008: dup_x1
    //   1009: swap
    //   1010: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1013: iconst_2
    //   1014: swap
    //   1015: aastore
    //   1016: dup_x1
    //   1017: swap
    //   1018: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1021: iconst_1
    //   1022: swap
    //   1023: aastore
    //   1024: dup_x1
    //   1025: swap
    //   1026: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1029: iconst_0
    //   1030: swap
    //   1031: aastore
    //   1032: invokevirtual Q : ([Ljava/lang/Object;)V
    //   1035: aload_0
    //   1036: getfield D : Lwtf/opal/pa;
    //   1039: fload #31
    //   1041: fload #33
    //   1043: fadd
    //   1044: ldc_w 0.25
    //   1047: fsub
    //   1048: fload #32
    //   1050: ldc_w 0.25
    //   1053: fsub
    //   1054: ldc_w 0.5
    //   1057: fload #34
    //   1059: ldc_w 0.5
    //   1062: fadd
    //   1063: aload_0
    //   1064: getfield q : I
    //   1067: aload_0
    //   1068: getfield P : I
    //   1071: lload #15
    //   1073: dup2_x1
    //   1074: pop2
    //   1075: ldc_w 90.0
    //   1078: bipush #8
    //   1080: anewarray java/lang/Object
    //   1083: dup_x1
    //   1084: swap
    //   1085: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1088: bipush #7
    //   1090: swap
    //   1091: aastore
    //   1092: dup_x1
    //   1093: swap
    //   1094: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1097: bipush #6
    //   1099: swap
    //   1100: aastore
    //   1101: dup_x2
    //   1102: dup_x2
    //   1103: pop
    //   1104: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1107: iconst_5
    //   1108: swap
    //   1109: aastore
    //   1110: dup_x1
    //   1111: swap
    //   1112: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1115: iconst_4
    //   1116: swap
    //   1117: aastore
    //   1118: dup_x1
    //   1119: swap
    //   1120: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1123: iconst_3
    //   1124: swap
    //   1125: aastore
    //   1126: dup_x1
    //   1127: swap
    //   1128: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1131: iconst_2
    //   1132: swap
    //   1133: aastore
    //   1134: dup_x1
    //   1135: swap
    //   1136: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1139: iconst_1
    //   1140: swap
    //   1141: aastore
    //   1142: dup_x1
    //   1143: swap
    //   1144: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1147: iconst_0
    //   1148: swap
    //   1149: aastore
    //   1150: invokevirtual Q : ([Ljava/lang/Object;)V
    //   1153: aload #25
    //   1155: lload_2
    //   1156: lconst_0
    //   1157: lcmp
    //   1158: ifle -> 1416
    //   1161: ifnull -> 1409
    //   1164: goto -> 1171
    //   1167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1170: athrow
    //   1171: aload_0
    //   1172: getfield G : Lwtf/opal/ky;
    //   1175: invokevirtual z : ()Ljava/lang/Object;
    //   1178: checkcast wtf/opal/kh
    //   1181: aload #25
    //   1183: ifnonnull -> 1316
    //   1186: goto -> 1193
    //   1189: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1192: athrow
    //   1193: invokevirtual ordinal : ()I
    //   1196: goto -> 1203
    //   1199: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1202: athrow
    //   1203: lload_2
    //   1204: lconst_0
    //   1205: lcmp
    //   1206: iflt -> 1258
    //   1209: tableswitch default -> 1304, 0 -> 1240, 1 -> 1265, 2 -> 1304, 3 -> 1286
    //   1240: aload #35
    //   1242: iconst_0
    //   1243: anewarray java/lang/Object
    //   1246: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   1249: invokevirtual z : ()Ljava/lang/Object;
    //   1252: checkcast java/lang/Integer
    //   1255: invokevirtual intValue : ()I
    //   1258: goto -> 1322
    //   1261: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1264: athrow
    //   1265: aload #35
    //   1267: iconst_0
    //   1268: anewarray java/lang/Object
    //   1271: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   1274: invokevirtual z : ()Ljava/lang/Object;
    //   1277: checkcast java/lang/Integer
    //   1280: invokevirtual intValue : ()I
    //   1283: goto -> 1322
    //   1286: new java/awt/Color
    //   1289: dup
    //   1290: aload #8
    //   1292: invokevirtual method_22861 : ()I
    //   1295: invokespecial <init> : (I)V
    //   1298: invokevirtual getRGB : ()I
    //   1301: goto -> 1322
    //   1304: aload #35
    //   1306: iconst_0
    //   1307: anewarray java/lang/Object
    //   1310: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   1313: invokevirtual z : ()Ljava/lang/Object;
    //   1316: checkcast java/lang/Integer
    //   1319: invokevirtual intValue : ()I
    //   1322: istore #36
    //   1324: aload_0
    //   1325: getfield D : Lwtf/opal/pa;
    //   1328: fload #31
    //   1330: fload #32
    //   1332: fload #33
    //   1334: lload #19
    //   1336: fload #34
    //   1338: ldc_w 0.5
    //   1341: iload #36
    //   1343: bipush #7
    //   1345: anewarray java/lang/Object
    //   1348: dup_x1
    //   1349: swap
    //   1350: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1353: bipush #6
    //   1355: swap
    //   1356: aastore
    //   1357: dup_x1
    //   1358: swap
    //   1359: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1362: iconst_5
    //   1363: swap
    //   1364: aastore
    //   1365: dup_x1
    //   1366: swap
    //   1367: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1370: iconst_4
    //   1371: swap
    //   1372: aastore
    //   1373: dup_x2
    //   1374: dup_x2
    //   1375: pop
    //   1376: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1379: iconst_3
    //   1380: swap
    //   1381: aastore
    //   1382: dup_x1
    //   1383: swap
    //   1384: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1387: iconst_2
    //   1388: swap
    //   1389: aastore
    //   1390: dup_x1
    //   1391: swap
    //   1392: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1395: iconst_1
    //   1396: swap
    //   1397: aastore
    //   1398: dup_x1
    //   1399: swap
    //   1400: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1403: iconst_0
    //   1404: swap
    //   1405: aastore
    //   1406: invokevirtual P : ([Ljava/lang/Object;)V
    //   1409: aload_0
    //   1410: getfield L : Lwtf/opal/ke;
    //   1413: invokevirtual z : ()Ljava/lang/Object;
    //   1416: checkcast java/lang/Boolean
    //   1419: invokevirtual booleanValue : ()Z
    //   1422: ifeq -> 2832
    //   1425: aload #8
    //   1427: invokevirtual method_6032 : ()F
    //   1430: aload #8
    //   1432: invokevirtual method_6067 : ()F
    //   1435: fadd
    //   1436: fstore #35
    //   1438: iconst_0
    //   1439: anewarray java/lang/Object
    //   1442: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1445: iconst_0
    //   1446: anewarray java/lang/Object
    //   1449: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   1452: ldc_w wtf/opal/jt
    //   1455: iconst_1
    //   1456: anewarray java/lang/Object
    //   1459: dup_x1
    //   1460: swap
    //   1461: iconst_0
    //   1462: swap
    //   1463: aastore
    //   1464: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   1467: checkcast wtf/opal/jt
    //   1470: astore #36
    //   1472: aload #36
    //   1474: lload #9
    //   1476: iconst_1
    //   1477: anewarray java/lang/Object
    //   1480: dup_x2
    //   1481: dup_x2
    //   1482: pop
    //   1483: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1486: iconst_0
    //   1487: swap
    //   1488: aastore
    //   1489: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1492: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   1495: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1498: lload_2
    //   1499: lconst_0
    //   1500: lcmp
    //   1501: iflt -> 1520
    //   1504: ifeq -> 1517
    //   1507: ldc_w '❤'
    //   1510: goto -> 1530
    //   1513: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1516: athrow
    //   1517: sipush #624
    //   1520: ldc2_w 8338904557277277575
    //   1523: lload_2
    //   1524: lxor
    //   1525: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1530: astore #37
    //   1532: aload #8
    //   1534: invokevirtual method_5476 : ()Lnet/minecraft/class_2561;
    //   1537: aload #25
    //   1539: ifnonnull -> 1613
    //   1542: ifnull -> 1601
    //   1545: goto -> 1552
    //   1548: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1551: athrow
    //   1552: new wtf/opal/d5
    //   1555: dup
    //   1556: invokespecial <init> : ()V
    //   1559: astore #39
    //   1561: aload #8
    //   1563: invokevirtual method_5476 : ()Lnet/minecraft/class_2561;
    //   1566: invokeinterface method_30937 : ()Lnet/minecraft/class_5481;
    //   1571: aload #39
    //   1573: invokeinterface accept : (Lnet/minecraft/class_5224;)Z
    //   1578: pop
    //   1579: aload #39
    //   1581: iconst_0
    //   1582: anewarray java/lang/Object
    //   1585: invokevirtual z : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1588: astore #38
    //   1590: aload #25
    //   1592: lload_2
    //   1593: lconst_0
    //   1594: lcmp
    //   1595: iflt -> 1623
    //   1598: ifnull -> 1620
    //   1601: aload #8
    //   1603: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   1606: goto -> 1613
    //   1609: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1612: athrow
    //   1613: invokeinterface getString : ()Ljava/lang/String;
    //   1618: astore #38
    //   1620: ldc_w ''
    //   1623: astore #39
    //   1625: iconst_0
    //   1626: anewarray java/lang/Object
    //   1629: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1632: iconst_0
    //   1633: anewarray java/lang/Object
    //   1636: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/l2;
    //   1639: iconst_0
    //   1640: anewarray java/lang/Object
    //   1643: invokevirtual m : ([Ljava/lang/Object;)Ljava/util/List;
    //   1646: aload #8
    //   1648: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   1651: invokeinterface getString : ()Ljava/lang/String;
    //   1656: invokevirtual toUpperCase : ()Ljava/lang/String;
    //   1659: invokeinterface contains : (Ljava/lang/Object;)Z
    //   1664: aload #25
    //   1666: lload_2
    //   1667: lconst_0
    //   1668: lcmp
    //   1669: ifle -> 1776
    //   1672: ifnonnull -> 1774
    //   1675: ifeq -> 1769
    //   1678: goto -> 1685
    //   1681: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1684: athrow
    //   1685: aload #39
    //   1687: aload #36
    //   1689: lload #9
    //   1691: iconst_1
    //   1692: anewarray java/lang/Object
    //   1695: dup_x2
    //   1696: dup_x2
    //   1697: pop
    //   1698: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1701: iconst_0
    //   1702: swap
    //   1703: aastore
    //   1704: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1707: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   1710: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1713: lload_2
    //   1714: lconst_0
    //   1715: lcmp
    //   1716: iflt -> 1752
    //   1719: ifeq -> 1749
    //   1722: goto -> 1729
    //   1725: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1728: athrow
    //   1729: sipush #1095
    //   1732: ldc2_w 4409720674897882018
    //   1735: lload_2
    //   1736: lxor
    //   1737: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1742: goto -> 1762
    //   1745: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1748: athrow
    //   1749: sipush #29437
    //   1752: ldc2_w 6661688675866811668
    //   1755: lload_2
    //   1756: lxor
    //   1757: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1762: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1767: astore #39
    //   1769: aload #8
    //   1771: invokevirtual method_5715 : ()Z
    //   1774: aload #25
    //   1776: lload_2
    //   1777: lconst_0
    //   1778: lcmp
    //   1779: iflt -> 1919
    //   1782: ifnonnull -> 1917
    //   1785: ifeq -> 1879
    //   1788: goto -> 1795
    //   1791: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1794: athrow
    //   1795: aload #39
    //   1797: aload #36
    //   1799: lload #9
    //   1801: iconst_1
    //   1802: anewarray java/lang/Object
    //   1805: dup_x2
    //   1806: dup_x2
    //   1807: pop
    //   1808: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1811: iconst_0
    //   1812: swap
    //   1813: aastore
    //   1814: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1817: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   1820: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1823: lload_2
    //   1824: lconst_0
    //   1825: lcmp
    //   1826: iflt -> 1862
    //   1829: ifeq -> 1859
    //   1832: goto -> 1839
    //   1835: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1838: athrow
    //   1839: sipush #17633
    //   1842: ldc2_w 5536873176040319751
    //   1845: lload_2
    //   1846: lxor
    //   1847: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1852: goto -> 1872
    //   1855: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1858: athrow
    //   1859: sipush #12366
    //   1862: ldc2_w 9215291966351531951
    //   1865: lload_2
    //   1866: lxor
    //   1867: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1872: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1877: astore #39
    //   1879: aload_0
    //   1880: getfield k : Lwtf/opal/kd;
    //   1883: sipush #18521
    //   1886: ldc2_w 5388252665562343329
    //   1889: lload_2
    //   1890: lxor
    //   1891: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1896: iconst_1
    //   1897: anewarray java/lang/Object
    //   1900: dup_x1
    //   1901: swap
    //   1902: iconst_0
    //   1903: swap
    //   1904: aastore
    //   1905: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   1908: invokevirtual z : ()Ljava/lang/Object;
    //   1911: checkcast java/lang/Boolean
    //   1914: invokevirtual booleanValue : ()Z
    //   1917: aload #25
    //   1919: ifnonnull -> 2049
    //   1922: ifeq -> 1982
    //   1925: goto -> 1932
    //   1928: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1931: athrow
    //   1932: aload #39
    //   1934: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1937: getfield field_1724 : Lnet/minecraft/class_746;
    //   1940: aload #8
    //   1942: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   1945: invokestatic round : (F)I
    //   1948: sipush #9901
    //   1951: ldc2_w 3816423341726189907
    //   1954: lload_2
    //   1955: lxor
    //   1956: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1961: swap
    //   1962: sipush #23714
    //   1965: ldc2_w 5069052464528503631
    //   1968: lload_2
    //   1969: lxor
    //   1970: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   1975: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;
    //   1980: astore #39
    //   1982: aload #39
    //   1984: aload #38
    //   1986: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1991: astore #39
    //   1993: aload_0
    //   1994: lload_2
    //   1995: lconst_0
    //   1996: lcmp
    //   1997: iflt -> 2036
    //   2000: aload #25
    //   2002: ifnonnull -> 2111
    //   2005: getfield k : Lwtf/opal/kd;
    //   2008: sipush #1493
    //   2011: ldc2_w 6269279912083352104
    //   2014: lload_2
    //   2015: lxor
    //   2016: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   2021: iconst_1
    //   2022: anewarray java/lang/Object
    //   2025: dup_x1
    //   2026: swap
    //   2027: iconst_0
    //   2028: swap
    //   2029: aastore
    //   2030: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   2033: invokevirtual z : ()Ljava/lang/Object;
    //   2036: checkcast java/lang/Boolean
    //   2039: invokevirtual booleanValue : ()Z
    //   2042: goto -> 2049
    //   2045: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2048: athrow
    //   2049: ifeq -> 2110
    //   2052: aload #39
    //   2054: fload #35
    //   2056: invokestatic round : (F)I
    //   2059: aload #37
    //   2061: sipush #13513
    //   2064: ldc2_w 3899466204610157358
    //   2067: lload_2
    //   2068: lxor
    //   2069: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   2074: dup_x2
    //   2075: pop
    //   2076: sipush #8023
    //   2079: ldc2_w 4237300696204406967
    //   2082: lload_2
    //   2083: lxor
    //   2084: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   2089: swap
    //   2090: sipush #11982
    //   2093: ldc2_w 796805430239412518
    //   2096: lload_2
    //   2097: lxor
    //   2098: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   2103: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   2108: astore #39
    //   2110: aload_0
    //   2111: getfield x : Lwtf/opal/bu;
    //   2114: aload #39
    //   2116: aload_0
    //   2117: getfield X : Lwtf/opal/kt;
    //   2120: invokevirtual z : ()Ljava/lang/Object;
    //   2123: checkcast java/lang/Double
    //   2126: invokevirtual floatValue : ()F
    //   2129: lload #11
    //   2131: iconst_3
    //   2132: anewarray java/lang/Object
    //   2135: dup_x2
    //   2136: dup_x2
    //   2137: pop
    //   2138: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2141: iconst_2
    //   2142: swap
    //   2143: aastore
    //   2144: dup_x1
    //   2145: swap
    //   2146: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2149: iconst_1
    //   2150: swap
    //   2151: aastore
    //   2152: dup_x1
    //   2153: swap
    //   2154: iconst_0
    //   2155: swap
    //   2156: aastore
    //   2157: invokevirtual s : ([Ljava/lang/Object;)F
    //   2160: fstore #40
    //   2162: fload #31
    //   2164: fload #33
    //   2166: fconst_2
    //   2167: fdiv
    //   2168: fadd
    //   2169: fstore #41
    //   2171: fload #41
    //   2173: fload #40
    //   2175: fconst_2
    //   2176: fdiv
    //   2177: fsub
    //   2178: fstore #42
    //   2180: fload #32
    //   2182: ldc_w 10.0
    //   2185: fsub
    //   2186: fstore #43
    //   2188: new java/awt/Color
    //   2191: dup
    //   2192: iconst_0
    //   2193: iconst_0
    //   2194: iconst_0
    //   2195: ldc_w 255.0
    //   2198: aload_0
    //   2199: getfield I : Lwtf/opal/kt;
    //   2202: invokevirtual z : ()Ljava/lang/Object;
    //   2205: checkcast java/lang/Double
    //   2208: invokevirtual intValue : ()I
    //   2211: i2f
    //   2212: ldc_w 100.0
    //   2215: fdiv
    //   2216: fmul
    //   2217: fconst_0
    //   2218: ldc_w 255.0
    //   2221: invokestatic clamp : (FFF)F
    //   2224: f2i
    //   2225: invokespecial <init> : (IIII)V
    //   2228: invokevirtual getRGB : ()I
    //   2231: istore #44
    //   2233: aload_0
    //   2234: getfield k : Lwtf/opal/kd;
    //   2237: sipush #6228
    //   2240: ldc2_w 6475722806544627621
    //   2243: lload_2
    //   2244: lxor
    //   2245: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   2250: iconst_1
    //   2251: anewarray java/lang/Object
    //   2254: dup_x1
    //   2255: swap
    //   2256: iconst_0
    //   2257: swap
    //   2258: aastore
    //   2259: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   2262: invokevirtual z : ()Ljava/lang/Object;
    //   2265: checkcast java/lang/Boolean
    //   2268: invokevirtual booleanValue : ()Z
    //   2271: aload #25
    //   2273: ifnonnull -> 2511
    //   2276: ifeq -> 2334
    //   2279: goto -> 2286
    //   2282: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2285: athrow
    //   2286: aload_0
    //   2287: getfield D : Lwtf/opal/pa;
    //   2290: aload_0
    //   2291: fload #42
    //   2293: fload #43
    //   2295: fload #40
    //   2297: iload #44
    //   2299: <illegal opcode> run : (Lwtf/opal/jy;FFFI)Ljava/lang/Runnable;
    //   2304: lload #17
    //   2306: iconst_2
    //   2307: anewarray java/lang/Object
    //   2310: dup_x2
    //   2311: dup_x2
    //   2312: pop
    //   2313: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2316: iconst_1
    //   2317: swap
    //   2318: aastore
    //   2319: dup_x1
    //   2320: swap
    //   2321: iconst_0
    //   2322: swap
    //   2323: aastore
    //   2324: invokevirtual F : ([Ljava/lang/Object;)V
    //   2327: goto -> 2334
    //   2330: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2333: athrow
    //   2334: aload_0
    //   2335: getfield x : Lwtf/opal/bu;
    //   2338: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   2341: aload #5
    //   2343: aload #39
    //   2345: fload #42
    //   2347: fload #43
    //   2349: aload #36
    //   2351: lload #9
    //   2353: iconst_1
    //   2354: anewarray java/lang/Object
    //   2357: dup_x2
    //   2358: dup_x2
    //   2359: pop
    //   2360: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2363: iconst_0
    //   2364: swap
    //   2365: aastore
    //   2366: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   2369: iconst_0
    //   2370: anewarray java/lang/Object
    //   2373: invokevirtual b : ([Ljava/lang/Object;)F
    //   2376: fconst_2
    //   2377: fdiv
    //   2378: fsub
    //   2379: lload #23
    //   2381: dup2_x1
    //   2382: pop2
    //   2383: aload_0
    //   2384: getfield X : Lwtf/opal/kt;
    //   2387: invokevirtual z : ()Ljava/lang/Object;
    //   2390: checkcast java/lang/Double
    //   2393: invokevirtual floatValue : ()F
    //   2396: iconst_m1
    //   2397: iconst_1
    //   2398: bipush #9
    //   2400: anewarray java/lang/Object
    //   2403: dup_x1
    //   2404: swap
    //   2405: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   2408: bipush #8
    //   2410: swap
    //   2411: aastore
    //   2412: dup_x1
    //   2413: swap
    //   2414: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2417: bipush #7
    //   2419: swap
    //   2420: aastore
    //   2421: dup_x1
    //   2422: swap
    //   2423: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2426: bipush #6
    //   2428: swap
    //   2429: aastore
    //   2430: dup_x1
    //   2431: swap
    //   2432: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2435: iconst_5
    //   2436: swap
    //   2437: aastore
    //   2438: dup_x2
    //   2439: dup_x2
    //   2440: pop
    //   2441: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2444: iconst_4
    //   2445: swap
    //   2446: aastore
    //   2447: dup_x1
    //   2448: swap
    //   2449: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2452: iconst_3
    //   2453: swap
    //   2454: aastore
    //   2455: dup_x1
    //   2456: swap
    //   2457: iconst_2
    //   2458: swap
    //   2459: aastore
    //   2460: dup_x1
    //   2461: swap
    //   2462: iconst_1
    //   2463: swap
    //   2464: aastore
    //   2465: dup_x1
    //   2466: swap
    //   2467: iconst_0
    //   2468: swap
    //   2469: aastore
    //   2470: invokevirtual H : ([Ljava/lang/Object;)V
    //   2473: aload_0
    //   2474: getfield k : Lwtf/opal/kd;
    //   2477: sipush #15318
    //   2480: ldc2_w 5846827016753861693
    //   2483: lload_2
    //   2484: lxor
    //   2485: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   2490: iconst_1
    //   2491: anewarray java/lang/Object
    //   2494: dup_x1
    //   2495: swap
    //   2496: iconst_0
    //   2497: swap
    //   2498: aastore
    //   2499: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   2502: invokevirtual z : ()Ljava/lang/Object;
    //   2505: checkcast java/lang/Boolean
    //   2508: invokevirtual booleanValue : ()Z
    //   2511: ifeq -> 2832
    //   2514: new java/util/ArrayList
    //   2517: dup
    //   2518: invokespecial <init> : ()V
    //   2521: astore #45
    //   2523: aload #8
    //   2525: invokevirtual method_5661 : ()Ljava/lang/Iterable;
    //   2528: aload #45
    //   2530: <illegal opcode> accept : (Ljava/util/List;)Ljava/util/function/Consumer;
    //   2535: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   2540: aload #8
    //   2542: invokevirtual method_6047 : ()Lnet/minecraft/class_1799;
    //   2545: astore #46
    //   2547: aload #46
    //   2549: invokevirtual method_7960 : ()Z
    //   2552: aload #25
    //   2554: ifnonnull -> 2583
    //   2557: ifne -> 2584
    //   2560: goto -> 2567
    //   2563: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2566: athrow
    //   2567: aload #45
    //   2569: aload #46
    //   2571: invokeinterface add : (Ljava/lang/Object;)Z
    //   2576: goto -> 2583
    //   2579: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2582: athrow
    //   2583: pop
    //   2584: ldc_w 0.65
    //   2587: fstore #47
    //   2589: ldc_w 0.75
    //   2592: fstore #48
    //   2594: iconst_0
    //   2595: istore #49
    //   2597: iload #49
    //   2599: aload #45
    //   2601: invokeinterface size : ()I
    //   2606: if_icmpge -> 2832
    //   2609: aload #45
    //   2611: iload #49
    //   2613: invokeinterface get : (I)Ljava/lang/Object;
    //   2618: checkcast net/minecraft/class_1799
    //   2621: astore #50
    //   2623: fload #41
    //   2625: aload #45
    //   2627: invokeinterface size : ()I
    //   2632: i2f
    //   2633: ldc_w 0.65
    //   2636: fmul
    //   2637: ldc_w 8.0
    //   2640: fmul
    //   2641: fsub
    //   2642: aload #45
    //   2644: invokeinterface size : ()I
    //   2649: iload #49
    //   2651: isub
    //   2652: iconst_1
    //   2653: isub
    //   2654: i2f
    //   2655: ldc_w 0.65
    //   2658: fmul
    //   2659: ldc_w 16.0
    //   2662: fmul
    //   2663: fadd
    //   2664: fstore #51
    //   2666: aload #5
    //   2668: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2671: invokevirtual method_22903 : ()V
    //   2674: aload #5
    //   2676: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2679: fload #51
    //   2681: fload #32
    //   2683: ldc_w 24.0
    //   2686: fsub
    //   2687: fconst_0
    //   2688: invokevirtual method_46416 : (FFF)V
    //   2691: aload #5
    //   2693: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2696: ldc_w 0.65
    //   2699: ldc_w 0.65
    //   2702: fconst_1
    //   2703: invokevirtual method_22905 : (FFF)V
    //   2706: aload #5
    //   2708: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2711: ldc_w 0.75
    //   2714: ldc_w 0.75
    //   2717: fconst_1
    //   2718: invokevirtual method_22905 : (FFF)V
    //   2721: aload #5
    //   2723: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2726: ldc_w 6.0
    //   2729: ldc_w 12.0
    //   2732: ldc_w 200.0
    //   2735: invokevirtual method_46416 : (FFF)V
    //   2738: new java/util/concurrent/atomic/AtomicInteger
    //   2741: dup
    //   2742: invokespecial <init> : ()V
    //   2745: astore #52
    //   2747: aload #39
    //   2749: astore #53
    //   2751: aload #50
    //   2753: invokestatic method_57532 : (Lnet/minecraft/class_1799;)Lnet/minecraft/class_9304;
    //   2756: invokevirtual method_57539 : ()Ljava/util/Set;
    //   2759: aload #52
    //   2761: aload #53
    //   2763: aload #5
    //   2765: <illegal opcode> accept : (Ljava/util/concurrent/atomic/AtomicInteger;Ljava/lang/String;Lnet/minecraft/class_332;)Ljava/util/function/Consumer;
    //   2770: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   2775: aload #5
    //   2777: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2780: ldc_w -6.0
    //   2783: ldc_w -12.0
    //   2786: ldc_w -200.0
    //   2789: invokevirtual method_46416 : (FFF)V
    //   2792: aload #5
    //   2794: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2797: ldc_w 1.3333334
    //   2800: ldc_w 1.3333334
    //   2803: fconst_1
    //   2804: invokevirtual method_22905 : (FFF)V
    //   2807: aload #5
    //   2809: aload #50
    //   2811: iconst_0
    //   2812: iconst_0
    //   2813: invokevirtual method_51427 : (Lnet/minecraft/class_1799;II)V
    //   2816: aload #5
    //   2818: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   2821: invokevirtual method_22909 : ()V
    //   2824: iinc #49, 1
    //   2827: aload #25
    //   2829: ifnull -> 2597
    //   2832: return
    // Exception table:
    //   from	to	target	type
    //   159	178	178	wtf/opal/x5
    //   288	298	301	wtf/opal/x5
    //   383	404	407	wtf/opal/x5
    //   401	417	420	wtf/opal/x5
    //   411	445	448	wtf/opal/x5
    //   424	548	551	wtf/opal/x5
    //   589	619	622	wtf/opal/x5
    //   616	1164	1167	wtf/opal/x5
    //   626	1186	1189	wtf/opal/x5
    //   1171	1196	1199	wtf/opal/x5
    //   1203	1261	1261	wtf/opal/x5
    //   1472	1513	1513	wtf/opal/x5
    //   1532	1545	1548	wtf/opal/x5
    //   1590	1606	1609	wtf/opal/x5
    //   1625	1678	1681	wtf/opal/x5
    //   1675	1722	1725	wtf/opal/x5
    //   1685	1745	1745	wtf/opal/x5
    //   1774	1788	1791	wtf/opal/x5
    //   1785	1832	1835	wtf/opal/x5
    //   1795	1855	1855	wtf/opal/x5
    //   1917	1925	1928	wtf/opal/x5
    //   1993	2042	2045	wtf/opal/x5
    //   2233	2279	2282	wtf/opal/x5
    //   2276	2327	2330	wtf/opal/x5
    //   2547	2560	2563	wtf/opal/x5
    //   2557	2576	2579	wtf/opal/x5
  }
  
  private void f(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_3
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Integer
    //   27: invokevirtual intValue : ()I
    //   30: istore #5
    //   32: pop
    //   33: getstatic wtf/opal/jy.a : J
    //   36: lload_3
    //   37: lxor
    //   38: lstore_3
    //   39: lload_3
    //   40: dup2
    //   41: ldc2_w 88636321010004
    //   44: lxor
    //   45: lstore #6
    //   47: pop2
    //   48: aload_0
    //   49: sipush #28897
    //   52: ldc2_w 8194204110073456775
    //   55: lload_3
    //   56: lxor
    //   57: <illegal opcode> t : (IJ)I
    //   62: iconst_0
    //   63: lload #6
    //   65: iload_2
    //   66: iload #5
    //   68: iconst_5
    //   69: anewarray java/lang/Object
    //   72: dup_x1
    //   73: swap
    //   74: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   77: iconst_4
    //   78: swap
    //   79: aastore
    //   80: dup_x1
    //   81: swap
    //   82: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   85: iconst_3
    //   86: swap
    //   87: aastore
    //   88: dup_x2
    //   89: dup_x2
    //   90: pop
    //   91: invokestatic valueOf : (J)Ljava/lang/Long;
    //   94: iconst_2
    //   95: swap
    //   96: aastore
    //   97: dup_x1
    //   98: swap
    //   99: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   102: iconst_1
    //   103: swap
    //   104: aastore
    //   105: dup_x1
    //   106: swap
    //   107: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   110: iconst_0
    //   111: swap
    //   112: aastore
    //   113: invokestatic K : ([Ljava/lang/Object;)I
    //   116: putfield y : I
    //   119: aload_0
    //   120: sipush #2581
    //   123: ldc2_w 6343735341439160955
    //   126: lload_3
    //   127: lxor
    //   128: <illegal opcode> t : (IJ)I
    //   133: sipush #27810
    //   136: ldc2_w 6838113499281585351
    //   139: lload_3
    //   140: lxor
    //   141: <illegal opcode> t : (IJ)I
    //   146: lload #6
    //   148: iload_2
    //   149: iload #5
    //   151: iconst_5
    //   152: anewarray java/lang/Object
    //   155: dup_x1
    //   156: swap
    //   157: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   160: iconst_4
    //   161: swap
    //   162: aastore
    //   163: dup_x1
    //   164: swap
    //   165: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   168: iconst_3
    //   169: swap
    //   170: aastore
    //   171: dup_x2
    //   172: dup_x2
    //   173: pop
    //   174: invokestatic valueOf : (J)Ljava/lang/Long;
    //   177: iconst_2
    //   178: swap
    //   179: aastore
    //   180: dup_x1
    //   181: swap
    //   182: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   185: iconst_1
    //   186: swap
    //   187: aastore
    //   188: dup_x1
    //   189: swap
    //   190: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   193: iconst_0
    //   194: swap
    //   195: aastore
    //   196: invokestatic K : ([Ljava/lang/Object;)I
    //   199: putfield q : I
    //   202: aload_0
    //   203: sipush #2581
    //   206: ldc2_w 6343735341439160955
    //   209: lload_3
    //   210: lxor
    //   211: <illegal opcode> t : (IJ)I
    //   216: sipush #17791
    //   219: ldc2_w 5385695253313576215
    //   222: lload_3
    //   223: lxor
    //   224: <illegal opcode> t : (IJ)I
    //   229: lload #6
    //   231: iload_2
    //   232: iload #5
    //   234: iconst_5
    //   235: anewarray java/lang/Object
    //   238: dup_x1
    //   239: swap
    //   240: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   243: iconst_4
    //   244: swap
    //   245: aastore
    //   246: dup_x1
    //   247: swap
    //   248: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   251: iconst_3
    //   252: swap
    //   253: aastore
    //   254: dup_x2
    //   255: dup_x2
    //   256: pop
    //   257: invokestatic valueOf : (J)Ljava/lang/Long;
    //   260: iconst_2
    //   261: swap
    //   262: aastore
    //   263: dup_x1
    //   264: swap
    //   265: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   268: iconst_1
    //   269: swap
    //   270: aastore
    //   271: dup_x1
    //   272: swap
    //   273: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   276: iconst_0
    //   277: swap
    //   278: aastore
    //   279: invokestatic K : ([Ljava/lang/Object;)I
    //   282: putfield P : I
    //   285: aload_0
    //   286: sipush #2581
    //   289: ldc2_w 6343735341439160955
    //   292: lload_3
    //   293: lxor
    //   294: <illegal opcode> t : (IJ)I
    //   299: sipush #16249
    //   302: ldc2_w 3486703201969645342
    //   305: lload_3
    //   306: lxor
    //   307: <illegal opcode> t : (IJ)I
    //   312: lload #6
    //   314: iload_2
    //   315: iload #5
    //   317: iconst_5
    //   318: anewarray java/lang/Object
    //   321: dup_x1
    //   322: swap
    //   323: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   326: iconst_4
    //   327: swap
    //   328: aastore
    //   329: dup_x1
    //   330: swap
    //   331: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   334: iconst_3
    //   335: swap
    //   336: aastore
    //   337: dup_x2
    //   338: dup_x2
    //   339: pop
    //   340: invokestatic valueOf : (J)Ljava/lang/Long;
    //   343: iconst_2
    //   344: swap
    //   345: aastore
    //   346: dup_x1
    //   347: swap
    //   348: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   351: iconst_1
    //   352: swap
    //   353: aastore
    //   354: dup_x1
    //   355: swap
    //   356: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   359: iconst_0
    //   360: swap
    //   361: aastore
    //   362: invokestatic K : ([Ljava/lang/Object;)I
    //   365: putfield U : I
    //   368: return
  }
  
  public boolean N(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jy.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic S : ()Ljava/lang/String;
    //   21: astore #4
    //   23: aload_0
    //   24: iconst_0
    //   25: anewarray java/lang/Object
    //   28: invokevirtual D : ([Ljava/lang/Object;)Z
    //   31: aload #4
    //   33: ifnonnull -> 66
    //   36: ifeq -> 85
    //   39: goto -> 46
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_0
    //   47: getfield r : Lwtf/opal/ke;
    //   50: invokevirtual z : ()Ljava/lang/Object;
    //   53: checkcast java/lang/Boolean
    //   56: invokevirtual booleanValue : ()Z
    //   59: goto -> 66
    //   62: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   65: athrow
    //   66: aload #4
    //   68: ifnonnull -> 82
    //   71: ifeq -> 85
    //   74: goto -> 81
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: iconst_1
    //   82: goto -> 86
    //   85: iconst_0
    //   86: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	39	42	wtf/opal/x5
    //   36	59	62	wtf/opal/x5
    //   66	74	77	wtf/opal/x5
  }
  
  public boolean V(Object[] paramArrayOfObject) {
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
    //   23: lload_2
    //   24: bipush #16
    //   26: lshl
    //   27: iload #4
    //   29: i2l
    //   30: bipush #48
    //   32: lshl
    //   33: bipush #48
    //   35: lushr
    //   36: lor
    //   37: getstatic wtf/opal/jy.a : J
    //   40: lxor
    //   41: lstore #5
    //   43: invokestatic S : ()Ljava/lang/String;
    //   46: astore #7
    //   48: aload_0
    //   49: iconst_0
    //   50: anewarray java/lang/Object
    //   53: invokevirtual D : ([Ljava/lang/Object;)Z
    //   56: aload #7
    //   58: ifnonnull -> 91
    //   61: ifeq -> 110
    //   64: goto -> 71
    //   67: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   70: athrow
    //   71: aload_0
    //   72: getfield L : Lwtf/opal/ke;
    //   75: invokevirtual z : ()Ljava/lang/Object;
    //   78: checkcast java/lang/Boolean
    //   81: invokevirtual booleanValue : ()Z
    //   84: goto -> 91
    //   87: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   90: athrow
    //   91: aload #7
    //   93: ifnonnull -> 107
    //   96: ifeq -> 110
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: iconst_1
    //   107: goto -> 111
    //   110: iconst_0
    //   111: ireturn
    // Exception table:
    //   from	to	target	type
    //   48	64	67	wtf/opal/x5
    //   61	84	87	wtf/opal/x5
    //   91	99	102	wtf/opal/x5
  }
  
  private static void lambda$render2D$6(AtomicInteger paramAtomicInteger, String paramString, class_332 paramclass_332, Object2IntMap.Entry paramEntry) {
    // Byte code:
    //   0: getstatic wtf/opal/jy.a : J
    //   3: ldc2_w 107303601621667
    //   6: lxor
    //   7: lstore #4
    //   9: getstatic wtf/opal/jy.d : Ljava/util/Map;
    //   12: aload_3
    //   13: invokeinterface getKey : ()Ljava/lang/Object;
    //   18: checkcast net/minecraft/class_6880
    //   21: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   26: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   31: checkcast java/lang/String
    //   34: astore #6
    //   36: aload #6
    //   38: ifnonnull -> 46
    //   41: return
    //   42: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   45: athrow
    //   46: aload_0
    //   47: invokevirtual getAndIncrement : ()I
    //   50: istore #7
    //   52: aload #6
    //   54: aload_3
    //   55: invokeinterface getIntValue : ()I
    //   60: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;I)Ljava/lang/String;
    //   65: astore #8
    //   67: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   70: getfield field_1772 : Lnet/minecraft/class_327;
    //   73: aload_1
    //   74: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   77: i2f
    //   78: ldc_w 0.75
    //   81: fmul
    //   82: fstore #9
    //   84: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   87: getfield field_1772 : Lnet/minecraft/class_327;
    //   90: aload #8
    //   92: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   95: invokevirtual method_30937 : ()Lnet/minecraft/class_5481;
    //   98: fconst_0
    //   99: iload #7
    //   101: sipush #5283
    //   104: ldc2_w 6386737996160149014
    //   107: lload #4
    //   109: lxor
    //   110: <illegal opcode> t : (IJ)I
    //   115: imul
    //   116: i2f
    //   117: iconst_m1
    //   118: iconst_0
    //   119: aload_2
    //   120: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   123: invokevirtual method_23760 : ()Lnet/minecraft/class_4587$class_4665;
    //   126: invokevirtual method_23761 : ()Lorg/joml/Matrix4f;
    //   129: aload_2
    //   130: invokevirtual method_51450 : ()Lnet/minecraft/class_4597$class_4598;
    //   133: sipush #8160
    //   136: ldc2_w 1887074479849385300
    //   139: lload #4
    //   141: lxor
    //   142: <illegal opcode> t : (IJ)I
    //   147: invokevirtual method_37296 : (Lnet/minecraft/class_5481;FFIILorg/joml/Matrix4f;Lnet/minecraft/class_4597;I)V
    //   150: return
    // Exception table:
    //   from	to	target	type
    //   36	42	42	wtf/opal/x5
  }
  
  private static void lambda$render2D$5(List paramList, class_1799 paramclass_1799) {
    long l = a ^ 0x22863AEC43D4L;
    String str = jt.S();
    try {
      if (str == null)
        try {
          if (!paramclass_1799.method_7960()) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    paramclass_1799.method_7960();
  }
  
  private void lambda$render2D$4(float paramFloat1, float paramFloat2, float paramFloat3, int paramInt) {
    // Byte code:
    //   0: getstatic wtf/opal/jy.a : J
    //   3: ldc2_w 119537392516414
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 107215350159258
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 8862351359087
    //   22: lxor
    //   23: lstore #9
    //   25: pop2
    //   26: invokestatic S : ()Ljava/lang/String;
    //   29: astore #11
    //   31: aload_0
    //   32: aload #11
    //   34: ifnonnull -> 224
    //   37: getfield k : Lwtf/opal/kd;
    //   40: sipush #32653
    //   43: ldc2_w 8109860783331030530
    //   46: lload #5
    //   48: lxor
    //   49: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   54: iconst_1
    //   55: anewarray java/lang/Object
    //   58: dup_x1
    //   59: swap
    //   60: iconst_0
    //   61: swap
    //   62: aastore
    //   63: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   66: invokevirtual z : ()Ljava/lang/Object;
    //   69: checkcast java/lang/Boolean
    //   72: invokevirtual booleanValue : ()Z
    //   75: ifeq -> 216
    //   78: goto -> 85
    //   81: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: aload_0
    //   86: getfield D : Lwtf/opal/pa;
    //   89: fload_1
    //   90: fconst_2
    //   91: fsub
    //   92: fload_2
    //   93: fconst_2
    //   94: fsub
    //   95: fload_3
    //   96: ldc_w 4.0
    //   99: fadd
    //   100: aload_0
    //   101: getfield X : Lwtf/opal/kt;
    //   104: invokevirtual z : ()Ljava/lang/Object;
    //   107: checkcast java/lang/Double
    //   110: invokevirtual floatValue : ()F
    //   113: ldc_w 4.0
    //   116: fadd
    //   117: aload_0
    //   118: getfield X : Lwtf/opal/kt;
    //   121: invokevirtual z : ()Ljava/lang/Object;
    //   124: checkcast java/lang/Double
    //   127: invokevirtual floatValue : ()F
    //   130: ldc_w 4.0
    //   133: fdiv
    //   134: iload #4
    //   136: lload #7
    //   138: bipush #7
    //   140: anewarray java/lang/Object
    //   143: dup_x2
    //   144: dup_x2
    //   145: pop
    //   146: invokestatic valueOf : (J)Ljava/lang/Long;
    //   149: bipush #6
    //   151: swap
    //   152: aastore
    //   153: dup_x1
    //   154: swap
    //   155: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   158: iconst_5
    //   159: swap
    //   160: aastore
    //   161: dup_x1
    //   162: swap
    //   163: invokestatic valueOf : (F)Ljava/lang/Float;
    //   166: iconst_4
    //   167: swap
    //   168: aastore
    //   169: dup_x1
    //   170: swap
    //   171: invokestatic valueOf : (F)Ljava/lang/Float;
    //   174: iconst_3
    //   175: swap
    //   176: aastore
    //   177: dup_x1
    //   178: swap
    //   179: invokestatic valueOf : (F)Ljava/lang/Float;
    //   182: iconst_2
    //   183: swap
    //   184: aastore
    //   185: dup_x1
    //   186: swap
    //   187: invokestatic valueOf : (F)Ljava/lang/Float;
    //   190: iconst_1
    //   191: swap
    //   192: aastore
    //   193: dup_x1
    //   194: swap
    //   195: invokestatic valueOf : (F)Ljava/lang/Float;
    //   198: iconst_0
    //   199: swap
    //   200: aastore
    //   201: invokevirtual M : ([Ljava/lang/Object;)V
    //   204: aload #11
    //   206: ifnull -> 316
    //   209: goto -> 216
    //   212: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   215: athrow
    //   216: aload_0
    //   217: goto -> 224
    //   220: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   223: athrow
    //   224: getfield D : Lwtf/opal/pa;
    //   227: fload_1
    //   228: fconst_2
    //   229: fsub
    //   230: fload_2
    //   231: fconst_2
    //   232: fsub
    //   233: fload_3
    //   234: ldc_w 4.0
    //   237: fadd
    //   238: aload_0
    //   239: getfield X : Lwtf/opal/kt;
    //   242: invokevirtual z : ()Ljava/lang/Object;
    //   245: checkcast java/lang/Double
    //   248: invokevirtual floatValue : ()F
    //   251: ldc_w 4.0
    //   254: fadd
    //   255: iload #4
    //   257: lload #9
    //   259: bipush #6
    //   261: anewarray java/lang/Object
    //   264: dup_x2
    //   265: dup_x2
    //   266: pop
    //   267: invokestatic valueOf : (J)Ljava/lang/Long;
    //   270: iconst_5
    //   271: swap
    //   272: aastore
    //   273: dup_x1
    //   274: swap
    //   275: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   278: iconst_4
    //   279: swap
    //   280: aastore
    //   281: dup_x1
    //   282: swap
    //   283: invokestatic valueOf : (F)Ljava/lang/Float;
    //   286: iconst_3
    //   287: swap
    //   288: aastore
    //   289: dup_x1
    //   290: swap
    //   291: invokestatic valueOf : (F)Ljava/lang/Float;
    //   294: iconst_2
    //   295: swap
    //   296: aastore
    //   297: dup_x1
    //   298: swap
    //   299: invokestatic valueOf : (F)Ljava/lang/Float;
    //   302: iconst_1
    //   303: swap
    //   304: aastore
    //   305: dup_x1
    //   306: swap
    //   307: invokestatic valueOf : (F)Ljava/lang/Float;
    //   310: iconst_0
    //   311: swap
    //   312: aastore
    //   313: invokevirtual k : ([Ljava/lang/Object;)V
    //   316: return
    // Exception table:
    //   from	to	target	type
    //   31	78	81	wtf/opal/x5
    //   37	209	212	wtf/opal/x5
    //   85	217	220	wtf/opal/x5
  }
  
  private void lambda$new$3(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/jy.a : J
    //   3: ldc2_w 6856400694311
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 112369415952262
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: astore #6
    //   22: aload_0
    //   23: getfield J : Lwtf/opal/ke;
    //   26: invokevirtual z : ()Ljava/lang/Object;
    //   29: checkcast java/lang/Boolean
    //   32: invokevirtual booleanValue : ()Z
    //   35: aload #6
    //   37: ifnonnull -> 87
    //   40: ifne -> 86
    //   43: goto -> 50
    //   46: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   49: athrow
    //   50: aload_0
    //   51: getfield L : Lwtf/opal/ke;
    //   54: invokevirtual z : ()Ljava/lang/Object;
    //   57: checkcast java/lang/Boolean
    //   60: invokevirtual booleanValue : ()Z
    //   63: aload #6
    //   65: ifnonnull -> 87
    //   68: goto -> 75
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: ifne -> 86
    //   78: goto -> 85
    //   81: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: return
    //   86: iconst_4
    //   87: newarray int
    //   89: dup
    //   90: iconst_0
    //   91: iconst_0
    //   92: iastore
    //   93: dup
    //   94: iconst_1
    //   95: iconst_0
    //   96: iastore
    //   97: dup
    //   98: iconst_2
    //   99: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   102: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   105: invokevirtual method_4489 : ()I
    //   108: iastore
    //   109: dup
    //   110: iconst_3
    //   111: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   114: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   117: invokevirtual method_4506 : ()I
    //   120: iastore
    //   121: astore #7
    //   123: aload_1
    //   124: iconst_0
    //   125: anewarray java/lang/Object
    //   128: invokevirtual N : ([Ljava/lang/Object;)F
    //   131: lload #4
    //   133: dup2_x1
    //   134: pop2
    //   135: iconst_2
    //   136: anewarray java/lang/Object
    //   139: dup_x1
    //   140: swap
    //   141: invokestatic valueOf : (F)Ljava/lang/Float;
    //   144: iconst_1
    //   145: swap
    //   146: aastore
    //   147: dup_x2
    //   148: dup_x2
    //   149: pop
    //   150: invokestatic valueOf : (J)Ljava/lang/Long;
    //   153: iconst_0
    //   154: swap
    //   155: aastore
    //   156: invokestatic X : ([Ljava/lang/Object;)Lnet/minecraft/class_4587;
    //   159: astore #8
    //   161: aload #8
    //   163: invokevirtual method_23760 : ()Lnet/minecraft/class_4587$class_4665;
    //   166: invokevirtual method_23761 : ()Lorg/joml/Matrix4f;
    //   169: astore #9
    //   171: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   174: getfield field_1769 : Lnet/minecraft/class_761;
    //   177: checkcast wtf/opal/mixin/WorldRendererAccessor
    //   180: invokeinterface getFrustum : ()Lnet/minecraft/class_4604;
    //   185: astore #10
    //   187: aload_0
    //   188: getfield D : Lwtf/opal/pa;
    //   191: aload_0
    //   192: aload #10
    //   194: aload_1
    //   195: aload #7
    //   197: aload #9
    //   199: <illegal opcode> run : (Lwtf/opal/jy;Lnet/minecraft/class_4604;Lwtf/opal/uj;[ILorg/joml/Matrix4f;)Ljava/lang/Runnable;
    //   204: iconst_1
    //   205: anewarray java/lang/Object
    //   208: dup_x1
    //   209: swap
    //   210: iconst_0
    //   211: swap
    //   212: aastore
    //   213: invokevirtual T : ([Ljava/lang/Object;)V
    //   216: return
    // Exception table:
    //   from	to	target	type
    //   22	43	46	wtf/opal/x5
    //   40	68	71	wtf/opal/x5
    //   50	78	81	wtf/opal/x5
  }
  
  private void lambda$new$2(class_4604 paramclass_4604, uj paramuj, int[] paramArrayOfint, Matrix4f paramMatrix4f) {
    // Byte code:
    //   0: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   3: getfield field_1687 : Lnet/minecraft/class_638;
    //   6: invokevirtual method_18112 : ()Ljava/lang/Iterable;
    //   9: aload_0
    //   10: aload_1
    //   11: aload_2
    //   12: aload_3
    //   13: aload #4
    //   15: <illegal opcode> accept : (Lwtf/opal/jy;Lnet/minecraft/class_4604;Lwtf/opal/uj;[ILorg/joml/Matrix4f;)Ljava/util/function/Consumer;
    //   20: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   25: return
  }
  
  private void lambda$new$1(class_4604 paramclass_4604, uj paramuj, int[] paramArrayOfint, Matrix4f paramMatrix4f, class_1297 paramclass_1297) {
    long l1 = a ^ 0x1D632B1DFE0DL;
    long l2 = l1 ^ 0x1F59A21EE625L;
    long l3 = l1 ^ 0x55CB320937D9L;
    String str = jt.S();
    try {
      if (str == null)
        try {
          if (paramclass_1297 instanceof class_1657) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_1657 class_1657 = (class_1657)paramclass_1297;
    try {
      new Object[2];
      if (str == null)
        try {
          if (!l.K(new Object[] { null, Long.valueOf(l3), paramclass_1297 })) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (l.K(new Object[] { null, Long.valueOf(l3), paramclass_1297 })) {
        new Object[6];
        S(new Object[] { 
              null, null, null, null, null, Long.valueOf(l2), Float.valueOf(paramuj.N(new Object[0])), paramMatrix4f, paramArrayOfint, class_1657, 
              paramuj.B(new Object[0]) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static boolean lambda$new$0(kd paramkd) {
    // Byte code:
    //   0: getstatic wtf/opal/jy.a : J
    //   3: ldc2_w 51015533186496
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: sipush #12848
    //   12: ldc2_w 2938423444012550991
    //   15: lload_1
    //   16: lxor
    //   17: <illegal opcode> r : (IJ)Ljava/lang/String;
    //   22: iconst_1
    //   23: anewarray java/lang/Object
    //   26: dup_x1
    //   27: swap
    //   28: iconst_0
    //   29: swap
    //   30: aastore
    //   31: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   34: invokevirtual z : ()Ljava/lang/Object;
    //   37: checkcast java/lang/Boolean
    //   40: invokevirtual booleanValue : ()Z
    //   43: ireturn
  }
  
  static {
    (new byte[8])[0] = (byte)(int)(l1 >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l1 << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[30];
    boolean bool = false;
    String str;
    int i = (str = "b.E\031Â6£AÈ\000\031 ü'@üs®zÙ\025é´B\021\023\034Lòäce$ÃË/g±ò±¦©\030ÒÍ\005mÚ\b2Xø#yÒaÃasµ\021G}dF\020»\fy¹§³²ÊÊ5,³§S{\030ËhoáÎ¿\026ø,êûÊÙk6®çäÏâG« ì}V&ë5LÙÎî·R®ò+±¯ÎÊ+(g­¶bFX\033ö\020Ê¡\"(\025qÎµARN\035¶@<\030®\005¥Ë\001æ¨xpÕuè\r(*1d\034´^(uÓ\006éÅS?r*uÊ\007u\034¨kN$©J96ý¸#ÃYÊþ\0226}ëú\020,]ÝªüHcÇ·/\tEÄa\020Ò^ÅH:Ë½¯)\005ê9»¥Ê8É­¬AÀ]rïí\\ ºeí5&c«>¢\000^R\\Å6T<\025\fÌ°ç³ò\b9ÌÛ\036Íé*ðéÔG\030<,\002BY\037\030K«{àë-h3ºSãºn\027äw <5\\9\023LK®+/S'ó¹ÚìNRÕÛ_EÜ£0G \034:»¼ætWµdüjPÊûJ\"o3(J£z_\031$¤\033ý\016E=\025 _\000áFô\032²+#¢Ùpõq6m\031\013Ã­^\022÷|\025Px\021 ñô2\033%î\024cß^\017yãb\031z&µä\tWó¹?Úäï'(]í\025ÏT9cVn\"Pû\037¶\007÷u]¢t¾º\026ü+kÿ[<\004åÁ¡+\006ãÖ\030A ¦ÌÆn%êm\035¼gZôÖ¡\f8x\000Î\020g ùa`¡­\003Ë- \000ñ¿\030ü,þªmÅÈ{±Xo*k\037suBb0ÒzÏ:\020h¡\004þ\r\fu¾\003Lû\030¸ô÷^DMê!õ|g\016JÑüÃYtr(òK¾ W}£\r@ÿ7F\025ØÇ¡LæfÐþeáã\034ý`ê?$ì TI\n\rí \005©\036Bðý½\005ãß=ÌÑãEËºù\f\001º\020~\034µ;\027Mµpc1ù\026Dl¶\030Ù3~¯/u¡éÚe$Ð0¬Ç1Ã7¥g\0309Ùzqb\017NêQ\004\003g0\021}óùmÅp\016-").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x77D5;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])l.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          l.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jy", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = f[i].getBytes("ISO-8859-1");
      g[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return g[i];
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
    //   66: ldc_w 'wtf/opal/jy'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x3B7F;
    if (n[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = m[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])o.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          o.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jy", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      n[i] = Integer.valueOf(j);
    } 
    return n[i].intValue();
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
    //   66: ldc_w 'wtf/opal/jy'
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
  
  static {
    long l1 = a ^ 0x2076A793747DL;
    long l2 = l1 ^ 0x3160F6B3D16FL;
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jy.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */