package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_241;
import net.minecraft.class_2596;
import net.minecraft.class_332;

public final class xw extends d {
  private final ky<xx> P;
  
  private final ky<pe> f;
  
  private final ky<ll> X;
  
  private final ky<l1> R;
  
  private final kt E;
  
  private final kt z;
  
  private final kt ms;
  
  private final kt J;
  
  public final ke Z;
  
  public final ke D;
  
  public final ke b;
  
  public final ke M;
  
  public final ke B;
  
  private final dx T;
  
  private dr v;
  
  private dr u;
  
  private class_241 Y;
  
  private int L;
  
  private int Q;
  
  private int G;
  
  private int p;
  
  private int d;
  
  private int o;
  
  private int r;
  
  private int t;
  
  private boolean U;
  
  private boolean W;
  
  private boolean n;
  
  private final List<class_2596<?>> m;
  
  private final List<dr> x;
  
  private final pa s;
  
  private final bu y;
  
  private final gm<u0> g;
  
  private final gm<b6> l;
  
  private final gm<lb> k;
  
  private final gm<lu> I;
  
  private final gm<uj> w;
  
  private final gm<p> A;
  
  private final gm<l8> a;
  
  private static int[] q;
  
  private static final long bb = on.a(5861556892034940589L, -7785307030410187506L, MethodHandles.lookup().lookupClass()).a(247933351341371L);
  
  private static final String[] cb;
  
  private static final String[] db;
  
  private static final Map eb = new HashMap<>(13);
  
  private static final long[] gb;
  
  private static final Integer[] hb;
  
  private static final Map ib;
  
  public xw(char paramChar, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #32
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/xw.bb : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 54247379454002
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 82217327205532
    //   42: lxor
    //   43: lstore #8
    //   45: dup2
    //   46: ldc2_w 130074783120206
    //   49: lxor
    //   50: lstore #10
    //   52: dup2
    //   53: ldc2_w 15646449708111
    //   56: lxor
    //   57: dup2
    //   58: bipush #48
    //   60: lushr
    //   61: l2i
    //   62: istore #12
    //   64: dup2
    //   65: bipush #16
    //   67: lshl
    //   68: bipush #48
    //   70: lushr
    //   71: l2i
    //   72: istore #13
    //   74: dup2
    //   75: bipush #32
    //   77: lshl
    //   78: bipush #32
    //   80: lushr
    //   81: l2i
    //   82: istore #14
    //   84: pop2
    //   85: dup2
    //   86: ldc2_w 96980214850075
    //   89: lxor
    //   90: lstore #15
    //   92: dup2
    //   93: ldc2_w 82358921503976
    //   96: lxor
    //   97: lstore #17
    //   99: pop2
    //   100: aload_0
    //   101: sipush #8148
    //   104: ldc2_w 2735934876754389209
    //   107: lload #4
    //   109: lxor
    //   110: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   115: lload #15
    //   117: sipush #27641
    //   120: ldc2_w 6533605639076109563
    //   123: lload #4
    //   125: lxor
    //   126: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   131: getstatic wtf/opal/kn.WORLD : Lwtf/opal/kn;
    //   134: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   137: aload_0
    //   138: new wtf/opal/ky
    //   141: dup
    //   142: sipush #22132
    //   145: ldc2_w 3182287521668456802
    //   148: lload #4
    //   150: lxor
    //   151: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   156: getstatic wtf/opal/xx.CLIENT : Lwtf/opal/xx;
    //   159: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   162: putfield P : Lwtf/opal/ky;
    //   165: aload_0
    //   166: new wtf/opal/ky
    //   169: dup
    //   170: sipush #23557
    //   173: ldc2_w 1273426983082912524
    //   176: lload #4
    //   178: lxor
    //   179: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   184: getstatic wtf/opal/pe.SERVER : Lwtf/opal/pe;
    //   187: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   190: putfield f : Lwtf/opal/ky;
    //   193: aload_0
    //   194: new wtf/opal/ky
    //   197: dup
    //   198: sipush #16618
    //   201: ldc2_w 2335050463477626853
    //   204: lload #4
    //   206: lxor
    //   207: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   212: getstatic wtf/opal/ll.VANILLA : Lwtf/opal/ll;
    //   215: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   218: putfield X : Lwtf/opal/ky;
    //   221: aload_0
    //   222: new wtf/opal/ky
    //   225: dup
    //   226: sipush #20771
    //   229: ldc2_w 6143646689166714405
    //   232: lload #4
    //   234: lxor
    //   235: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   240: aload_0
    //   241: getstatic wtf/opal/l1.VANILLA : Lwtf/opal/l1;
    //   244: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   247: putfield R : Lwtf/opal/ky;
    //   250: invokestatic k : ()[I
    //   253: aload_0
    //   254: new wtf/opal/kt
    //   257: dup
    //   258: sipush #19131
    //   261: ldc2_w 3480466823204601276
    //   264: lload #4
    //   266: lxor
    //   267: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   272: ldc2_w 3.0
    //   275: dconst_1
    //   276: ldc2_w 6.0
    //   279: dconst_1
    //   280: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   283: putfield E : Lwtf/opal/kt;
    //   286: aload_0
    //   287: new wtf/opal/kt
    //   290: dup
    //   291: sipush #5402
    //   294: ldc2_w 8606788320302342669
    //   297: lload #4
    //   299: lxor
    //   300: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   305: dconst_0
    //   306: dconst_0
    //   307: ldc2_w 10.0
    //   310: dconst_1
    //   311: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   314: putfield z : Lwtf/opal/kt;
    //   317: aload_0
    //   318: new wtf/opal/kt
    //   321: dup
    //   322: sipush #335
    //   325: ldc2_w 3839948783458629191
    //   328: lload #4
    //   330: lxor
    //   331: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   336: dconst_1
    //   337: dconst_1
    //   338: ldc2_w 10.0
    //   341: ldc2_w 0.1
    //   344: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   347: putfield ms : Lwtf/opal/kt;
    //   350: aload_0
    //   351: new wtf/opal/kt
    //   354: dup
    //   355: sipush #2292
    //   358: ldc2_w 8544575847078117374
    //   361: lload #4
    //   363: lxor
    //   364: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   369: dconst_1
    //   370: dconst_1
    //   371: ldc2_w 10.0
    //   374: ldc2_w 0.1
    //   377: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   380: putfield J : Lwtf/opal/kt;
    //   383: aload_0
    //   384: new wtf/opal/ke
    //   387: dup
    //   388: sipush #29601
    //   391: ldc2_w 9023325293924304045
    //   394: lload #4
    //   396: lxor
    //   397: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   402: iconst_1
    //   403: invokespecial <init> : (Ljava/lang/String;Z)V
    //   406: putfield Z : Lwtf/opal/ke;
    //   409: aload_0
    //   410: new wtf/opal/ke
    //   413: dup
    //   414: sipush #18702
    //   417: ldc2_w 985612465760173583
    //   420: lload #4
    //   422: lxor
    //   423: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   428: iconst_1
    //   429: invokespecial <init> : (Ljava/lang/String;Z)V
    //   432: putfield D : Lwtf/opal/ke;
    //   435: aload_0
    //   436: new wtf/opal/ke
    //   439: dup
    //   440: sipush #27803
    //   443: ldc2_w 8622349721861872536
    //   446: lload #4
    //   448: lxor
    //   449: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   454: iconst_0
    //   455: invokespecial <init> : (Ljava/lang/String;Z)V
    //   458: putfield b : Lwtf/opal/ke;
    //   461: astore #19
    //   463: aload_0
    //   464: new wtf/opal/ke
    //   467: dup
    //   468: sipush #7611
    //   471: ldc2_w 7165951997610928816
    //   474: lload #4
    //   476: lxor
    //   477: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   482: iconst_0
    //   483: invokespecial <init> : (Ljava/lang/String;Z)V
    //   486: putfield M : Lwtf/opal/ke;
    //   489: aload_0
    //   490: new wtf/opal/ke
    //   493: dup
    //   494: sipush #15333
    //   497: ldc2_w 3239487213425069285
    //   500: lload #4
    //   502: lxor
    //   503: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   508: iconst_0
    //   509: invokespecial <init> : (Ljava/lang/String;Z)V
    //   512: putfield B : Lwtf/opal/ke;
    //   515: aload_0
    //   516: new wtf/opal/d2
    //   519: dup
    //   520: iload #12
    //   522: i2s
    //   523: sipush #31356
    //   526: ldc2_w 6409016816358539552
    //   529: lload #4
    //   531: lxor
    //   532: <illegal opcode> t : (IJ)I
    //   537: dconst_1
    //   538: iload #13
    //   540: i2c
    //   541: iload #14
    //   543: fconst_1
    //   544: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   547: invokespecial <init> : (SIDCIFLwtf/opal/lp;)V
    //   550: putfield T : Lwtf/opal/dx;
    //   553: aload_0
    //   554: new java/util/concurrent/CopyOnWriteArrayList
    //   557: dup
    //   558: invokespecial <init> : ()V
    //   561: putfield m : Ljava/util/List;
    //   564: aload_0
    //   565: new java/util/ArrayList
    //   568: dup
    //   569: invokespecial <init> : ()V
    //   572: putfield x : Ljava/util/List;
    //   575: aload_0
    //   576: iconst_0
    //   577: anewarray java/lang/Object
    //   580: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   583: iconst_0
    //   584: anewarray java/lang/Object
    //   587: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   590: putfield s : Lwtf/opal/pa;
    //   593: aload_0
    //   594: iconst_0
    //   595: anewarray java/lang/Object
    //   598: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   601: iconst_0
    //   602: anewarray java/lang/Object
    //   605: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   608: putfield y : Lwtf/opal/bu;
    //   611: aload_0
    //   612: aload_0
    //   613: <illegal opcode> H : (Lwtf/opal/xw;)Lwtf/opal/gm;
    //   618: putfield g : Lwtf/opal/gm;
    //   621: aload_0
    //   622: aload_0
    //   623: <illegal opcode> H : (Lwtf/opal/xw;)Lwtf/opal/gm;
    //   628: putfield l : Lwtf/opal/gm;
    //   631: aload_0
    //   632: aload_0
    //   633: <illegal opcode> H : (Lwtf/opal/xw;)Lwtf/opal/gm;
    //   638: putfield k : Lwtf/opal/gm;
    //   641: aload_0
    //   642: <illegal opcode> H : ()Lwtf/opal/gm;
    //   647: putfield I : Lwtf/opal/gm;
    //   650: aload_0
    //   651: aload_0
    //   652: <illegal opcode> H : (Lwtf/opal/xw;)Lwtf/opal/gm;
    //   657: putfield w : Lwtf/opal/gm;
    //   660: aload_0
    //   661: <illegal opcode> H : ()Lwtf/opal/gm;
    //   666: putfield A : Lwtf/opal/gm;
    //   669: aload_0
    //   670: aload_0
    //   671: <illegal opcode> H : (Lwtf/opal/xw;)Lwtf/opal/gm;
    //   676: putfield a : Lwtf/opal/gm;
    //   679: aload_0
    //   680: getfield X : Lwtf/opal/ky;
    //   683: aload_0
    //   684: getfield Z : Lwtf/opal/ke;
    //   687: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   692: lload #6
    //   694: dup2_x1
    //   695: pop2
    //   696: iconst_3
    //   697: anewarray java/lang/Object
    //   700: dup_x1
    //   701: swap
    //   702: iconst_2
    //   703: swap
    //   704: aastore
    //   705: dup_x2
    //   706: dup_x2
    //   707: pop
    //   708: invokestatic valueOf : (J)Ljava/lang/Long;
    //   711: iconst_1
    //   712: swap
    //   713: aastore
    //   714: dup_x1
    //   715: swap
    //   716: iconst_0
    //   717: swap
    //   718: aastore
    //   719: invokevirtual C : ([Ljava/lang/Object;)V
    //   722: aload_0
    //   723: getfield R : Lwtf/opal/ky;
    //   726: aload_0
    //   727: getfield B : Lwtf/opal/ke;
    //   730: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   735: lload #6
    //   737: dup2_x1
    //   738: pop2
    //   739: iconst_3
    //   740: anewarray java/lang/Object
    //   743: dup_x1
    //   744: swap
    //   745: iconst_2
    //   746: swap
    //   747: aastore
    //   748: dup_x2
    //   749: dup_x2
    //   750: pop
    //   751: invokestatic valueOf : (J)Ljava/lang/Long;
    //   754: iconst_1
    //   755: swap
    //   756: aastore
    //   757: dup_x1
    //   758: swap
    //   759: iconst_0
    //   760: swap
    //   761: aastore
    //   762: invokevirtual C : ([Ljava/lang/Object;)V
    //   765: aload_0
    //   766: aload_0
    //   767: getfield R : Lwtf/opal/ky;
    //   770: sipush #2792
    //   773: ldc2_w 5300816485253951925
    //   776: lload #4
    //   778: lxor
    //   779: <illegal opcode> t : (IJ)I
    //   784: anewarray wtf/opal/u_
    //   787: dup
    //   788: iconst_0
    //   789: new wtf/opal/pp
    //   792: dup
    //   793: aload_0
    //   794: invokespecial <init> : (Lwtf/opal/xw;)V
    //   797: aastore
    //   798: dup
    //   799: iconst_1
    //   800: new wtf/opal/pv
    //   803: dup
    //   804: lload #17
    //   806: aload_0
    //   807: invokespecial <init> : (JLwtf/opal/xw;)V
    //   810: aastore
    //   811: dup
    //   812: iconst_2
    //   813: new wtf/opal/pi
    //   816: dup
    //   817: aload_0
    //   818: invokespecial <init> : (Lwtf/opal/xw;)V
    //   821: aastore
    //   822: dup
    //   823: iconst_3
    //   824: new wtf/opal/p0
    //   827: dup
    //   828: aload_0
    //   829: lload #8
    //   831: invokespecial <init> : (Lwtf/opal/xw;J)V
    //   834: aastore
    //   835: dup
    //   836: iconst_4
    //   837: new wtf/opal/p9
    //   840: dup
    //   841: aload_0
    //   842: invokespecial <init> : (Lwtf/opal/xw;)V
    //   845: aastore
    //   846: dup
    //   847: iconst_5
    //   848: new wtf/opal/p2
    //   851: dup
    //   852: aload_0
    //   853: invokespecial <init> : (Lwtf/opal/xw;)V
    //   856: aastore
    //   857: iconst_2
    //   858: anewarray java/lang/Object
    //   861: dup_x1
    //   862: swap
    //   863: iconst_1
    //   864: swap
    //   865: aastore
    //   866: dup_x1
    //   867: swap
    //   868: iconst_0
    //   869: swap
    //   870: aastore
    //   871: invokevirtual Y : ([Ljava/lang/Object;)V
    //   874: aload_0
    //   875: sipush #31397
    //   878: ldc2_w 7942334801210229245
    //   881: lload #4
    //   883: lxor
    //   884: <illegal opcode> t : (IJ)I
    //   889: anewarray wtf/opal/k3
    //   892: dup
    //   893: iconst_0
    //   894: aload_0
    //   895: getfield P : Lwtf/opal/ky;
    //   898: aastore
    //   899: dup
    //   900: iconst_1
    //   901: aload_0
    //   902: getfield f : Lwtf/opal/ky;
    //   905: aastore
    //   906: dup
    //   907: iconst_2
    //   908: aload_0
    //   909: getfield X : Lwtf/opal/ky;
    //   912: aastore
    //   913: dup
    //   914: iconst_3
    //   915: aload_0
    //   916: getfield R : Lwtf/opal/ky;
    //   919: aastore
    //   920: dup
    //   921: iconst_4
    //   922: aload_0
    //   923: getfield E : Lwtf/opal/kt;
    //   926: aastore
    //   927: dup
    //   928: iconst_5
    //   929: aload_0
    //   930: getfield z : Lwtf/opal/kt;
    //   933: aastore
    //   934: dup
    //   935: sipush #16606
    //   938: ldc2_w 4510806069051259800
    //   941: lload #4
    //   943: lxor
    //   944: <illegal opcode> t : (IJ)I
    //   949: aload_0
    //   950: getfield ms : Lwtf/opal/kt;
    //   953: aastore
    //   954: dup
    //   955: sipush #2941
    //   958: ldc2_w 6525621164022771773
    //   961: lload #4
    //   963: lxor
    //   964: <illegal opcode> t : (IJ)I
    //   969: aload_0
    //   970: getfield J : Lwtf/opal/kt;
    //   973: aastore
    //   974: dup
    //   975: sipush #16218
    //   978: ldc2_w 7696408674664678430
    //   981: lload #4
    //   983: lxor
    //   984: <illegal opcode> t : (IJ)I
    //   989: aload_0
    //   990: getfield Z : Lwtf/opal/ke;
    //   993: aastore
    //   994: dup
    //   995: sipush #27330
    //   998: ldc2_w 3052788211738857861
    //   1001: lload #4
    //   1003: lxor
    //   1004: <illegal opcode> t : (IJ)I
    //   1009: aload_0
    //   1010: getfield D : Lwtf/opal/ke;
    //   1013: aastore
    //   1014: dup
    //   1015: sipush #25604
    //   1018: ldc2_w 7635017545589874509
    //   1021: lload #4
    //   1023: lxor
    //   1024: <illegal opcode> t : (IJ)I
    //   1029: aload_0
    //   1030: getfield b : Lwtf/opal/ke;
    //   1033: aastore
    //   1034: dup
    //   1035: sipush #19505
    //   1038: ldc2_w 4695207773765072764
    //   1041: lload #4
    //   1043: lxor
    //   1044: <illegal opcode> t : (IJ)I
    //   1049: aload_0
    //   1050: getfield M : Lwtf/opal/ke;
    //   1053: aastore
    //   1054: dup
    //   1055: sipush #4058
    //   1058: ldc2_w 6670539249920291992
    //   1061: lload #4
    //   1063: lxor
    //   1064: <illegal opcode> t : (IJ)I
    //   1069: aload_0
    //   1070: getfield B : Lwtf/opal/ke;
    //   1073: aastore
    //   1074: lload #10
    //   1076: dup2_x1
    //   1077: pop2
    //   1078: iconst_2
    //   1079: anewarray java/lang/Object
    //   1082: dup_x1
    //   1083: swap
    //   1084: iconst_1
    //   1085: swap
    //   1086: aastore
    //   1087: dup_x2
    //   1088: dup_x2
    //   1089: pop
    //   1090: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1093: iconst_0
    //   1094: swap
    //   1095: aastore
    //   1096: invokevirtual o : ([Ljava/lang/Object;)V
    //   1099: aload #19
    //   1101: ifnonnull -> 1118
    //   1104: iconst_4
    //   1105: anewarray wtf/opal/d
    //   1108: invokestatic p : ([Lwtf/opal/d;)V
    //   1111: goto -> 1118
    //   1114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1117: athrow
    //   1118: return
    // Exception table:
    //   from	to	target	type
    //   463	1111	1114	wtf/opal/x5
  }
  
  private void y(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/xw.bb : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 137024671396904
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 57019465304977
    //   30: lxor
    //   31: lstore #6
    //   33: pop2
    //   34: invokestatic k : ()[I
    //   37: aload_0
    //   38: lload #6
    //   40: iconst_1
    //   41: anewarray java/lang/Object
    //   44: dup_x2
    //   45: dup_x2
    //   46: pop
    //   47: invokestatic valueOf : (J)Ljava/lang/Long;
    //   50: iconst_0
    //   51: swap
    //   52: aastore
    //   53: invokevirtual w : ([Ljava/lang/Object;)V
    //   56: astore #8
    //   58: aload_0
    //   59: getfield u : Lwtf/opal/dr;
    //   62: aload #8
    //   64: ifnull -> 307
    //   67: ifnull -> 285
    //   70: goto -> 77
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: aload_0
    //   78: getfield v : Lwtf/opal/dr;
    //   81: aload #8
    //   83: ifnull -> 307
    //   86: goto -> 93
    //   89: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   92: athrow
    //   93: ifnull -> 285
    //   96: goto -> 103
    //   99: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: aload_0
    //   104: aload #8
    //   106: ifnull -> 281
    //   109: goto -> 116
    //   112: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   115: athrow
    //   116: lload_2
    //   117: lconst_0
    //   118: lcmp
    //   119: ifle -> 274
    //   122: getfield u : Lwtf/opal/dr;
    //   125: getfield i : Lnet/minecraft/class_2350;
    //   128: getstatic net/minecraft/class_2350.field_11036 : Lnet/minecraft/class_2350;
    //   131: if_acmpeq -> 273
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: aload_0
    //   142: aload #8
    //   144: ifnull -> 281
    //   147: goto -> 154
    //   150: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: lload_2
    //   155: lconst_0
    //   156: lcmp
    //   157: iflt -> 274
    //   160: getfield u : Lwtf/opal/dr;
    //   163: getfield i : Lnet/minecraft/class_2350;
    //   166: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
    //   169: if_acmpeq -> 273
    //   172: goto -> 179
    //   175: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   178: athrow
    //   179: aload_0
    //   180: lload_2
    //   181: lconst_0
    //   182: lcmp
    //   183: ifle -> 258
    //   186: aload #8
    //   188: ifnull -> 258
    //   191: goto -> 198
    //   194: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   197: athrow
    //   198: lload_2
    //   199: lconst_0
    //   200: lcmp
    //   201: ifle -> 251
    //   204: getfield u : Lwtf/opal/dr;
    //   207: getfield i : Lnet/minecraft/class_2350;
    //   210: aload_0
    //   211: getfield v : Lwtf/opal/dr;
    //   214: getfield i : Lnet/minecraft/class_2350;
    //   217: if_acmpne -> 250
    //   220: goto -> 227
    //   223: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   226: athrow
    //   227: aload_0
    //   228: iconst_0
    //   229: putfield n : Z
    //   232: lload_2
    //   233: lconst_0
    //   234: lcmp
    //   235: ifle -> 285
    //   238: aload #8
    //   240: ifnonnull -> 285
    //   243: goto -> 250
    //   246: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: aload_0
    //   251: goto -> 258
    //   254: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   257: athrow
    //   258: iconst_1
    //   259: putfield n : Z
    //   262: lload_2
    //   263: lconst_0
    //   264: lcmp
    //   265: iflt -> 285
    //   268: aload #8
    //   270: ifnonnull -> 285
    //   273: aload_0
    //   274: goto -> 281
    //   277: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   280: athrow
    //   281: iconst_0
    //   282: putfield n : Z
    //   285: aload_0
    //   286: lload_2
    //   287: lconst_0
    //   288: lcmp
    //   289: iflt -> 311
    //   292: aload #8
    //   294: ifnull -> 311
    //   297: getfield v : Lwtf/opal/dr;
    //   300: goto -> 307
    //   303: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   306: athrow
    //   307: ifnull -> 388
    //   310: aload_0
    //   311: getfield Q : I
    //   314: aload #8
    //   316: lload_2
    //   317: lconst_0
    //   318: lcmp
    //   319: ifle -> 454
    //   322: ifnull -> 446
    //   325: iconst_0
    //   326: anewarray java/lang/Object
    //   329: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   332: iconst_0
    //   333: anewarray java/lang/Object
    //   336: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   339: lload #4
    //   341: sipush #23700
    //   344: ldc2_w 1660822388573556060
    //   347: lload_2
    //   348: lxor
    //   349: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   354: iconst_2
    //   355: anewarray java/lang/Object
    //   358: dup_x1
    //   359: swap
    //   360: iconst_1
    //   361: swap
    //   362: aastore
    //   363: dup_x2
    //   364: dup_x2
    //   365: pop
    //   366: invokestatic valueOf : (J)Ljava/lang/Long;
    //   369: iconst_0
    //   370: swap
    //   371: aastore
    //   372: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   375: invokestatic parseInt : (Ljava/lang/String;)I
    //   378: if_icmpne -> 393
    //   381: goto -> 388
    //   384: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   387: athrow
    //   388: return
    //   389: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   392: athrow
    //   393: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   396: getfield field_1761 : Lnet/minecraft/class_636;
    //   399: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   402: getfield field_1724 : Lnet/minecraft/class_746;
    //   405: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   408: new net/minecraft/class_3965
    //   411: dup
    //   412: aload_0
    //   413: getfield v : Lwtf/opal/dr;
    //   416: getfield c : Lnet/minecraft/class_2338;
    //   419: invokevirtual method_46558 : ()Lnet/minecraft/class_243;
    //   422: aload_0
    //   423: getfield v : Lwtf/opal/dr;
    //   426: getfield i : Lnet/minecraft/class_2350;
    //   429: aload_0
    //   430: getfield v : Lwtf/opal/dr;
    //   433: getfield c : Lnet/minecraft/class_2338;
    //   436: iconst_0
    //   437: invokespecial <init> : (Lnet/minecraft/class_243;Lnet/minecraft/class_2350;Lnet/minecraft/class_2338;Z)V
    //   440: invokevirtual method_2896 : (Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;
    //   443: invokevirtual method_23665 : ()Z
    //   446: lload_2
    //   447: lconst_0
    //   448: lcmp
    //   449: ifle -> 490
    //   452: aload #8
    //   454: ifnull -> 490
    //   457: ifeq -> 553
    //   460: goto -> 467
    //   463: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   466: athrow
    //   467: aload_0
    //   468: getfield f : Lwtf/opal/ky;
    //   471: invokevirtual z : ()Ljava/lang/Object;
    //   474: checkcast wtf/opal/pe
    //   477: getstatic wtf/opal/pe.CLIENT : Lwtf/opal/pe;
    //   480: invokevirtual equals : (Ljava/lang/Object;)Z
    //   483: goto -> 490
    //   486: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   489: athrow
    //   490: ifeq -> 517
    //   493: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   496: getfield field_1724 : Lnet/minecraft/class_746;
    //   499: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   502: invokevirtual method_6104 : (Lnet/minecraft/class_1268;)V
    //   505: aload #8
    //   507: ifnonnull -> 543
    //   510: goto -> 517
    //   513: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   516: athrow
    //   517: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   520: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   523: new net/minecraft/class_2879
    //   526: dup
    //   527: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   530: invokespecial <init> : (Lnet/minecraft/class_1268;)V
    //   533: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   536: goto -> 543
    //   539: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   542: athrow
    //   543: aload_0
    //   544: dup
    //   545: getfield t : I
    //   548: iconst_1
    //   549: iadd
    //   550: putfield t : I
    //   553: aload_0
    //   554: aload_0
    //   555: getfield v : Lwtf/opal/dr;
    //   558: putfield u : Lwtf/opal/dr;
    //   561: aload_0
    //   562: aconst_null
    //   563: putfield v : Lwtf/opal/dr;
    //   566: return
    // Exception table:
    //   from	to	target	type
    //   58	70	73	wtf/opal/x5
    //   67	86	89	wtf/opal/x5
    //   77	96	99	wtf/opal/x5
    //   93	109	112	wtf/opal/x5
    //   103	134	137	wtf/opal/x5
    //   116	147	150	wtf/opal/x5
    //   141	172	175	wtf/opal/x5
    //   154	191	194	wtf/opal/x5
    //   179	220	223	wtf/opal/x5
    //   198	243	246	wtf/opal/x5
    //   227	251	254	wtf/opal/x5
    //   258	274	277	wtf/opal/x5
    //   285	300	303	wtf/opal/x5
    //   311	381	384	wtf/opal/x5
    //   325	389	389	wtf/opal/x5
    //   446	460	463	wtf/opal/x5
    //   457	483	486	wtf/opal/x5
    //   490	510	513	wtf/opal/x5
    //   493	536	539	wtf/opal/x5
  }
  
  public void P(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_332
    //   17: astore #4
    //   19: pop
    //   20: getstatic wtf/opal/xw.bb : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 134053463214985
    //   31: lxor
    //   32: lstore #5
    //   34: dup2
    //   35: ldc2_w 12072875475494
    //   38: lxor
    //   39: lstore #7
    //   41: dup2
    //   42: ldc2_w 14773569116008
    //   45: lxor
    //   46: lstore #9
    //   48: dup2
    //   49: ldc2_w 42521761137224
    //   52: lxor
    //   53: lstore #11
    //   55: dup2
    //   56: ldc2_w 133052985695961
    //   59: lxor
    //   60: lstore #13
    //   62: dup2
    //   63: ldc2_w 56009927416695
    //   66: lxor
    //   67: lstore #15
    //   69: pop2
    //   70: invokestatic k : ()[I
    //   73: astore #17
    //   75: aload_0
    //   76: iconst_0
    //   77: anewarray java/lang/Object
    //   80: invokevirtual D : ([Ljava/lang/Object;)Z
    //   83: aload #17
    //   85: ifnull -> 168
    //   88: ifne -> 148
    //   91: goto -> 98
    //   94: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: aload_0
    //   99: getfield T : Lwtf/opal/dx;
    //   102: lload #15
    //   104: iconst_1
    //   105: anewarray java/lang/Object
    //   108: dup_x2
    //   109: dup_x2
    //   110: pop
    //   111: invokestatic valueOf : (J)Ljava/lang/Long;
    //   114: iconst_0
    //   115: swap
    //   116: aastore
    //   117: invokevirtual H : ([Ljava/lang/Object;)Z
    //   120: lload_2
    //   121: lconst_0
    //   122: lcmp
    //   123: iflt -> 168
    //   126: aload #17
    //   128: ifnull -> 168
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: ifne -> 754
    //   141: goto -> 148
    //   144: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   147: athrow
    //   148: aload_0
    //   149: getfield D : Lwtf/opal/ke;
    //   152: invokevirtual z : ()Ljava/lang/Object;
    //   155: checkcast java/lang/Boolean
    //   158: invokevirtual booleanValue : ()Z
    //   161: goto -> 168
    //   164: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   167: athrow
    //   168: ifeq -> 754
    //   171: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   174: getfield field_1755 : Lnet/minecraft/class_437;
    //   177: ifnonnull -> 754
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: lload #7
    //   189: iconst_1
    //   190: anewarray java/lang/Object
    //   193: dup_x2
    //   194: dup_x2
    //   195: pop
    //   196: invokestatic valueOf : (J)Ljava/lang/Long;
    //   199: iconst_0
    //   200: swap
    //   201: aastore
    //   202: invokestatic e : ([Ljava/lang/Object;)I
    //   205: istore #18
    //   207: iload #18
    //   209: invokestatic valueOf : (I)Ljava/lang/String;
    //   212: astore #19
    //   214: aload_0
    //   215: getfield y : Lwtf/opal/bu;
    //   218: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   221: lload #11
    //   223: aload #19
    //   225: ldc_w 8.0
    //   228: sipush #10185
    //   231: ldc2_w 3860983784880756995
    //   234: lload_2
    //   235: lxor
    //   236: <illegal opcode> t : (IJ)I
    //   241: iconst_5
    //   242: anewarray java/lang/Object
    //   245: dup_x1
    //   246: swap
    //   247: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   250: iconst_4
    //   251: swap
    //   252: aastore
    //   253: dup_x1
    //   254: swap
    //   255: invokestatic valueOf : (F)Ljava/lang/Float;
    //   258: iconst_3
    //   259: swap
    //   260: aastore
    //   261: dup_x1
    //   262: swap
    //   263: iconst_2
    //   264: swap
    //   265: aastore
    //   266: dup_x2
    //   267: dup_x2
    //   268: pop
    //   269: invokestatic valueOf : (J)Ljava/lang/Long;
    //   272: iconst_1
    //   273: swap
    //   274: aastore
    //   275: dup_x1
    //   276: swap
    //   277: iconst_0
    //   278: swap
    //   279: aastore
    //   280: invokevirtual h : ([Ljava/lang/Object;)F
    //   283: fstore #20
    //   285: aload_0
    //   286: getfield T : Lwtf/opal/dx;
    //   289: lload #13
    //   291: iconst_1
    //   292: anewarray java/lang/Object
    //   295: dup_x2
    //   296: dup_x2
    //   297: pop
    //   298: invokestatic valueOf : (J)Ljava/lang/Long;
    //   301: iconst_0
    //   302: swap
    //   303: aastore
    //   304: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   307: invokevirtual floatValue : ()F
    //   310: fstore #21
    //   312: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   315: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   318: invokevirtual method_4486 : ()I
    //   321: i2f
    //   322: fload #20
    //   324: fsub
    //   325: iconst_0
    //   326: anewarray java/lang/Object
    //   329: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   332: iconst_0
    //   333: anewarray java/lang/Object
    //   336: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   339: lload #9
    //   341: ldc_w 'L'
    //   344: iconst_2
    //   345: anewarray java/lang/Object
    //   348: dup_x1
    //   349: swap
    //   350: iconst_1
    //   351: swap
    //   352: aastore
    //   353: dup_x2
    //   354: dup_x2
    //   355: pop
    //   356: invokestatic valueOf : (J)Ljava/lang/Long;
    //   359: iconst_0
    //   360: swap
    //   361: aastore
    //   362: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   365: invokestatic parseFloat : (Ljava/lang/String;)F
    //   368: fdiv
    //   369: fstore #22
    //   371: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   374: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   377: invokevirtual method_4502 : ()I
    //   380: i2f
    //   381: iconst_0
    //   382: anewarray java/lang/Object
    //   385: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   388: iconst_0
    //   389: anewarray java/lang/Object
    //   392: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   395: lload #9
    //   397: ldc_w 'L'
    //   400: iconst_2
    //   401: anewarray java/lang/Object
    //   404: dup_x1
    //   405: swap
    //   406: iconst_1
    //   407: swap
    //   408: aastore
    //   409: dup_x2
    //   410: dup_x2
    //   411: pop
    //   412: invokestatic valueOf : (J)Ljava/lang/Long;
    //   415: iconst_0
    //   416: swap
    //   417: aastore
    //   418: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   421: invokestatic parseFloat : (Ljava/lang/String;)F
    //   424: fdiv
    //   425: fstore #23
    //   427: aload_0
    //   428: getfield Q : I
    //   431: iconst_m1
    //   432: if_icmpne -> 443
    //   435: aconst_null
    //   436: goto -> 459
    //   439: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   442: athrow
    //   443: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   446: getfield field_1724 : Lnet/minecraft/class_746;
    //   449: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   452: aload_0
    //   453: getfield Q : I
    //   456: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   459: astore #24
    //   461: aload #24
    //   463: aload #17
    //   465: ifnull -> 519
    //   468: ifnull -> 517
    //   471: goto -> 478
    //   474: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   477: athrow
    //   478: aload #24
    //   480: lload_2
    //   481: lconst_0
    //   482: lcmp
    //   483: iflt -> 519
    //   486: aload #17
    //   488: ifnull -> 519
    //   491: goto -> 498
    //   494: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   497: athrow
    //   498: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   501: instanceof net/minecraft/class_1747
    //   504: ifne -> 517
    //   507: goto -> 514
    //   510: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   513: athrow
    //   514: aconst_null
    //   515: astore #24
    //   517: aload #24
    //   519: ifnull -> 530
    //   522: iconst_1
    //   523: goto -> 531
    //   526: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   529: athrow
    //   530: iconst_0
    //   531: istore #25
    //   533: iload #25
    //   535: aload #17
    //   537: lload_2
    //   538: lconst_0
    //   539: lcmp
    //   540: ifle -> 634
    //   543: ifnull -> 632
    //   546: ifeq -> 630
    //   549: goto -> 556
    //   552: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   555: athrow
    //   556: aload #4
    //   558: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   561: invokevirtual method_22903 : ()V
    //   564: aload #4
    //   566: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   569: fload #22
    //   571: fload #23
    //   573: ldc_w 8.0
    //   576: fadd
    //   577: fconst_0
    //   578: invokevirtual method_46416 : (FFF)V
    //   581: aload #4
    //   583: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   586: fload #21
    //   588: fload #21
    //   590: fconst_1
    //   591: invokevirtual method_22905 : (FFF)V
    //   594: aload #4
    //   596: aload #24
    //   598: sipush #30112
    //   601: ldc2_w 6774826649049369448
    //   604: lload_2
    //   605: lxor
    //   606: <illegal opcode> t : (IJ)I
    //   611: iconst_0
    //   612: invokevirtual method_51427 : (Lnet/minecraft/class_1799;II)V
    //   615: aload #4
    //   617: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   620: invokevirtual method_22909 : ()V
    //   623: goto -> 630
    //   626: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   629: athrow
    //   630: iload #25
    //   632: aload #17
    //   634: ifnull -> 649
    //   637: ifne -> 652
    //   640: goto -> 647
    //   643: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   646: athrow
    //   647: iload #18
    //   649: ifne -> 754
    //   652: iconst_0
    //   653: anewarray java/lang/Object
    //   656: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   659: iconst_0
    //   660: anewarray java/lang/Object
    //   663: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   666: ldc_w wtf/opal/jt
    //   669: iconst_1
    //   670: anewarray java/lang/Object
    //   673: dup_x1
    //   674: swap
    //   675: iconst_0
    //   676: swap
    //   677: aastore
    //   678: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   681: checkcast wtf/opal/jt
    //   684: astore #26
    //   686: aload #26
    //   688: lload #5
    //   690: iconst_1
    //   691: anewarray java/lang/Object
    //   694: dup_x2
    //   695: dup_x2
    //   696: pop
    //   697: invokestatic valueOf : (J)Ljava/lang/Long;
    //   700: iconst_0
    //   701: swap
    //   702: aastore
    //   703: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   706: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   709: invokevirtual equals : (Ljava/lang/Object;)Z
    //   712: istore #27
    //   714: aload_0
    //   715: getfield s : Lwtf/opal/pa;
    //   718: aload_0
    //   719: fload #21
    //   721: fload #22
    //   723: fload #23
    //   725: fload #20
    //   727: iload #18
    //   729: iload #27
    //   731: aload #4
    //   733: aload #19
    //   735: iload #25
    //   737: <illegal opcode> run : (Lwtf/opal/xw;FFFFIZLnet/minecraft/class_332;Ljava/lang/String;Z)Ljava/lang/Runnable;
    //   742: iconst_1
    //   743: anewarray java/lang/Object
    //   746: dup_x1
    //   747: swap
    //   748: iconst_0
    //   749: swap
    //   750: aastore
    //   751: invokevirtual T : ([Ljava/lang/Object;)V
    //   754: return
    // Exception table:
    //   from	to	target	type
    //   75	91	94	wtf/opal/x5
    //   88	131	134	wtf/opal/x5
    //   98	141	144	wtf/opal/x5
    //   138	161	164	wtf/opal/x5
    //   168	180	183	wtf/opal/x5
    //   427	439	439	wtf/opal/x5
    //   461	471	474	wtf/opal/x5
    //   468	491	494	wtf/opal/x5
    //   478	507	510	wtf/opal/x5
    //   519	526	526	wtf/opal/x5
    //   533	549	552	wtf/opal/x5
    //   546	623	626	wtf/opal/x5
    //   632	640	643	wtf/opal/x5
  }
  
  protected void K(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: lload_2
    //   13: dup2
    //   14: ldc2_w 0
    //   17: lxor
    //   18: lstore #4
    //   20: dup2
    //   21: ldc2_w 29584409000981
    //   24: lxor
    //   25: lstore #6
    //   27: dup2
    //   28: ldc2_w 47066715337984
    //   31: lxor
    //   32: lstore #8
    //   34: pop2
    //   35: invokestatic k : ()[I
    //   38: astore #10
    //   40: aload #10
    //   42: ifnull -> 311
    //   45: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   48: getfield field_1724 : Lnet/minecraft/class_746;
    //   51: ifnonnull -> 66
    //   54: goto -> 61
    //   57: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: return
    //   62: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   65: athrow
    //   66: aload_0
    //   67: aload_0
    //   68: aload_0
    //   69: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   72: getfield field_1724 : Lnet/minecraft/class_746;
    //   75: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   78: getfield field_7545 : I
    //   81: dup_x1
    //   82: putfield L : I
    //   85: dup_x1
    //   86: putfield G : I
    //   89: putfield Q : I
    //   92: aload_0
    //   93: aload_0
    //   94: aconst_null
    //   95: dup_x1
    //   96: putfield u : Lwtf/opal/dr;
    //   99: putfield v : Lwtf/opal/dr;
    //   102: aload_0
    //   103: new net/minecraft/class_241
    //   106: dup
    //   107: iconst_0
    //   108: anewarray java/lang/Object
    //   111: invokestatic T : ([Ljava/lang/Object;)F
    //   114: iconst_0
    //   115: anewarray java/lang/Object
    //   118: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   121: iconst_0
    //   122: anewarray java/lang/Object
    //   125: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   128: lload #6
    //   130: sipush #5212
    //   133: ldc2_w 5251765446199791010
    //   136: lload_2
    //   137: lxor
    //   138: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   143: iconst_2
    //   144: anewarray java/lang/Object
    //   147: dup_x1
    //   148: swap
    //   149: iconst_1
    //   150: swap
    //   151: aastore
    //   152: dup_x2
    //   153: dup_x2
    //   154: pop
    //   155: invokestatic valueOf : (J)Ljava/lang/Long;
    //   158: iconst_0
    //   159: swap
    //   160: aastore
    //   161: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   164: invokestatic parseFloat : (Ljava/lang/String;)F
    //   167: ldc_w 400.0
    //   170: fdiv
    //   171: fsub
    //   172: ldc_w 78.0
    //   175: invokespecial <init> : (FF)V
    //   178: putfield Y : Lnet/minecraft/class_241;
    //   181: aload_0
    //   182: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   185: getfield field_1724 : Lnet/minecraft/class_746;
    //   188: invokevirtual method_23318 : ()D
    //   191: invokestatic floor : (D)D
    //   194: d2i
    //   195: putfield o : I
    //   198: aload_0
    //   199: getfield m : Ljava/util/List;
    //   202: invokeinterface clear : ()V
    //   207: aload_0
    //   208: sipush #13772
    //   211: ldc2_w 2459980322927730793
    //   214: lload_2
    //   215: lxor
    //   216: <illegal opcode> t : (IJ)I
    //   221: putfield r : I
    //   224: aload_0
    //   225: iconst_0
    //   226: putfield t : I
    //   229: aload_0
    //   230: iconst_0
    //   231: putfield W : Z
    //   234: aload_0
    //   235: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   238: getfield field_1724 : Lnet/minecraft/class_746;
    //   241: invokevirtual method_24828 : ()Z
    //   244: putfield U : Z
    //   247: aload_0
    //   248: getfield x : Ljava/util/List;
    //   251: invokeinterface clear : ()V
    //   256: aload_0
    //   257: iconst_0
    //   258: putfield n : Z
    //   261: aload_0
    //   262: getfield T : Lwtf/opal/dx;
    //   265: lload #8
    //   267: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   270: iconst_2
    //   271: anewarray java/lang/Object
    //   274: dup_x1
    //   275: swap
    //   276: iconst_1
    //   277: swap
    //   278: aastore
    //   279: dup_x2
    //   280: dup_x2
    //   281: pop
    //   282: invokestatic valueOf : (J)Ljava/lang/Long;
    //   285: iconst_0
    //   286: swap
    //   287: aastore
    //   288: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   291: pop
    //   292: aload_0
    //   293: lload #4
    //   295: iconst_1
    //   296: anewarray java/lang/Object
    //   299: dup_x2
    //   300: dup_x2
    //   301: pop
    //   302: invokestatic valueOf : (J)Ljava/lang/Long;
    //   305: iconst_0
    //   306: swap
    //   307: aastore
    //   308: invokespecial K : ([Ljava/lang/Object;)V
    //   311: return
    // Exception table:
    //   from	to	target	type
    //   40	54	57	wtf/opal/x5
    //   45	62	62	wtf/opal/x5
  }
  
  protected void z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: lload_2
    //   13: dup2
    //   14: ldc2_w 25400854201234
    //   17: lxor
    //   18: lstore #4
    //   20: dup2
    //   21: ldc2_w 0
    //   24: lxor
    //   25: lstore #6
    //   27: pop2
    //   28: invokestatic k : ()[I
    //   31: aload_0
    //   32: getfield m : Ljava/util/List;
    //   35: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   38: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   41: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   44: dup
    //   45: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   48: pop
    //   49: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   54: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   59: aload_0
    //   60: getfield m : Ljava/util/List;
    //   63: invokeinterface clear : ()V
    //   68: astore #8
    //   70: aload_0
    //   71: aload #8
    //   73: ifnull -> 342
    //   76: getfield P : Lwtf/opal/ky;
    //   79: invokevirtual z : ()Ljava/lang/Object;
    //   82: checkcast wtf/opal/xx
    //   85: invokevirtual ordinal : ()I
    //   88: lload_2
    //   89: lconst_0
    //   90: lcmp
    //   91: iflt -> 281
    //   94: lookupswitch default -> 280, 0 -> 124, 1 -> 208
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   127: getfield field_1724 : Lnet/minecraft/class_746;
    //   130: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   133: lload_2
    //   134: lconst_0
    //   135: lcmp
    //   136: ifle -> 190
    //   139: aload #8
    //   141: ifnull -> 190
    //   144: goto -> 151
    //   147: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: getfield field_7545 : I
    //   154: lload_2
    //   155: lconst_0
    //   156: lcmp
    //   157: ifle -> 281
    //   160: aload_0
    //   161: getfield L : I
    //   164: if_icmpeq -> 280
    //   167: goto -> 174
    //   170: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   173: athrow
    //   174: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   177: getfield field_1724 : Lnet/minecraft/class_746;
    //   180: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   183: goto -> 190
    //   186: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   189: athrow
    //   190: aload_0
    //   191: getfield L : I
    //   194: putfield field_7545 : I
    //   197: lload_2
    //   198: lconst_0
    //   199: lcmp
    //   200: ifle -> 341
    //   203: aload #8
    //   205: ifnonnull -> 280
    //   208: lload_2
    //   209: lconst_0
    //   210: lcmp
    //   211: ifle -> 360
    //   214: aload_0
    //   215: aload #8
    //   217: ifnull -> 342
    //   220: goto -> 227
    //   223: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   226: athrow
    //   227: getfield G : I
    //   230: lload_2
    //   231: lconst_0
    //   232: lcmp
    //   233: iflt -> 281
    //   236: aload_0
    //   237: getfield L : I
    //   240: if_icmpeq -> 280
    //   243: goto -> 250
    //   246: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   253: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   256: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   259: new net/minecraft/class_2868
    //   262: dup
    //   263: aload_0
    //   264: getfield L : I
    //   267: invokespecial <init> : (I)V
    //   270: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   273: goto -> 280
    //   276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   279: athrow
    //   280: iconst_0
    //   281: anewarray java/lang/Object
    //   284: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   287: iconst_0
    //   288: anewarray java/lang/Object
    //   291: invokevirtual l : ([Ljava/lang/Object;)Lwtf/opal/un;
    //   294: fconst_1
    //   295: iconst_1
    //   296: anewarray java/lang/Object
    //   299: dup_x1
    //   300: swap
    //   301: invokestatic valueOf : (F)Ljava/lang/Float;
    //   304: iconst_0
    //   305: swap
    //   306: aastore
    //   307: invokevirtual U : ([Ljava/lang/Object;)V
    //   310: aload_0
    //   311: getfield T : Lwtf/opal/dx;
    //   314: lload #4
    //   316: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   319: iconst_2
    //   320: anewarray java/lang/Object
    //   323: dup_x1
    //   324: swap
    //   325: iconst_1
    //   326: swap
    //   327: aastore
    //   328: dup_x2
    //   329: dup_x2
    //   330: pop
    //   331: invokestatic valueOf : (J)Ljava/lang/Long;
    //   334: iconst_0
    //   335: swap
    //   336: aastore
    //   337: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   340: pop
    //   341: aload_0
    //   342: lload #6
    //   344: iconst_1
    //   345: anewarray java/lang/Object
    //   348: dup_x2
    //   349: dup_x2
    //   350: pop
    //   351: invokestatic valueOf : (J)Ljava/lang/Long;
    //   354: iconst_0
    //   355: swap
    //   356: aastore
    //   357: invokespecial z : ([Ljava/lang/Object;)V
    //   360: return
    // Exception table:
    //   from	to	target	type
    //   70	120	120	wtf/opal/x5
    //   76	144	147	wtf/opal/x5
    //   124	167	170	wtf/opal/x5
    //   151	183	186	wtf/opal/x5
    //   190	220	223	wtf/opal/x5
    //   208	243	246	wtf/opal/x5
    //   227	273	276	wtf/opal/x5
  }
  
  private void w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/xw.bb : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 41087560879906
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 63808921205477
    //   30: lxor
    //   31: lstore #6
    //   33: dup2
    //   34: ldc2_w 18712660699172
    //   37: lxor
    //   38: lstore #8
    //   40: pop2
    //   41: invokestatic k : ()[I
    //   44: iconst_0
    //   45: anewarray java/lang/Object
    //   48: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   51: iconst_0
    //   52: anewarray java/lang/Object
    //   55: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   58: ldc_w wtf/opal/jm
    //   61: iconst_1
    //   62: anewarray java/lang/Object
    //   65: dup_x1
    //   66: swap
    //   67: iconst_0
    //   68: swap
    //   69: aastore
    //   70: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   73: checkcast wtf/opal/jm
    //   76: astore #11
    //   78: astore #10
    //   80: aload_0
    //   81: getfield B : Lwtf/opal/ke;
    //   84: invokevirtual z : ()Ljava/lang/Object;
    //   87: checkcast java/lang/Boolean
    //   90: invokevirtual booleanValue : ()Z
    //   93: aload #10
    //   95: ifnull -> 131
    //   98: ifeq -> 409
    //   101: goto -> 108
    //   104: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: aload_0
    //   109: getfield R : Lwtf/opal/ky;
    //   112: invokevirtual z : ()Ljava/lang/Object;
    //   115: checkcast wtf/opal/l1
    //   118: getstatic wtf/opal/l1.WATCHDOG : Lwtf/opal/l1;
    //   121: invokevirtual equals : (Ljava/lang/Object;)Z
    //   124: goto -> 131
    //   127: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: aload #10
    //   133: ifnull -> 253
    //   136: ifne -> 234
    //   139: goto -> 146
    //   142: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   145: athrow
    //   146: aload_0
    //   147: getfield R : Lwtf/opal/ky;
    //   150: invokevirtual z : ()Ljava/lang/Object;
    //   153: checkcast wtf/opal/l1
    //   156: getstatic wtf/opal/l1.WATCHDOG_LOWHOP : Lwtf/opal/l1;
    //   159: invokevirtual equals : (Ljava/lang/Object;)Z
    //   162: aload #10
    //   164: ifnull -> 253
    //   167: goto -> 174
    //   170: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   173: athrow
    //   174: lload_2
    //   175: lconst_0
    //   176: lcmp
    //   177: iflt -> 246
    //   180: ifne -> 234
    //   183: goto -> 190
    //   186: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   189: athrow
    //   190: aload_0
    //   191: getfield R : Lwtf/opal/ky;
    //   194: invokevirtual z : ()Ljava/lang/Object;
    //   197: checkcast wtf/opal/l1
    //   200: getstatic wtf/opal/l1.WATCHDOG_DYNAMIC : Lwtf/opal/l1;
    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
    //   206: aload #10
    //   208: lload_2
    //   209: lconst_0
    //   210: lcmp
    //   211: iflt -> 255
    //   214: ifnull -> 253
    //   217: goto -> 224
    //   220: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   223: athrow
    //   224: ifeq -> 409
    //   227: goto -> 234
    //   230: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   233: athrow
    //   234: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   237: getfield field_1690 : Lnet/minecraft/class_315;
    //   240: getfield field_1903 : Lnet/minecraft/class_304;
    //   243: invokevirtual method_1434 : ()Z
    //   246: goto -> 253
    //   249: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   252: athrow
    //   253: aload #10
    //   255: lload_2
    //   256: lconst_0
    //   257: lcmp
    //   258: ifle -> 289
    //   261: ifnull -> 287
    //   264: ifeq -> 409
    //   267: goto -> 274
    //   270: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   273: athrow
    //   274: aload_0
    //   275: getfield t : I
    //   278: iconst_2
    //   279: irem
    //   280: goto -> 287
    //   283: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   286: athrow
    //   287: aload #10
    //   289: lload_2
    //   290: lconst_0
    //   291: lcmp
    //   292: iflt -> 335
    //   295: ifnull -> 333
    //   298: ifne -> 409
    //   301: goto -> 308
    //   304: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   307: athrow
    //   308: lload #4
    //   310: iconst_1
    //   311: anewarray java/lang/Object
    //   314: dup_x2
    //   315: dup_x2
    //   316: pop
    //   317: invokestatic valueOf : (J)Ljava/lang/Long;
    //   320: iconst_0
    //   321: swap
    //   322: aastore
    //   323: invokestatic I : ([Ljava/lang/Object;)Z
    //   326: goto -> 333
    //   329: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   332: athrow
    //   333: aload #10
    //   335: lload_2
    //   336: lconst_0
    //   337: lcmp
    //   338: iflt -> 392
    //   341: ifnull -> 390
    //   344: ifne -> 409
    //   347: goto -> 354
    //   350: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   353: athrow
    //   354: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   357: getfield field_1724 : Lnet/minecraft/class_746;
    //   360: invokevirtual method_23321 : ()D
    //   363: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   366: getfield field_1724 : Lnet/minecraft/class_746;
    //   369: invokevirtual method_23321 : ()D
    //   372: invokestatic floor : (D)D
    //   375: dsub
    //   376: invokestatic abs : (D)D
    //   379: ldc2_w 0.5
    //   382: dcmpg
    //   383: goto -> 390
    //   386: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   389: athrow
    //   390: aload #10
    //   392: ifnull -> 406
    //   395: ifgt -> 409
    //   398: goto -> 405
    //   401: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   404: athrow
    //   405: iconst_1
    //   406: goto -> 410
    //   409: iconst_0
    //   410: istore #12
    //   412: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   415: getfield field_1690 : Lnet/minecraft/class_315;
    //   418: getfield field_1903 : Lnet/minecraft/class_304;
    //   421: invokevirtual method_1434 : ()Z
    //   424: aload #10
    //   426: ifnull -> 532
    //   429: ifeq -> 512
    //   432: goto -> 439
    //   435: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   438: athrow
    //   439: aload_0
    //   440: getfield M : Lwtf/opal/ke;
    //   443: invokevirtual z : ()Ljava/lang/Object;
    //   446: checkcast java/lang/Boolean
    //   449: invokevirtual booleanValue : ()Z
    //   452: aload #10
    //   454: lload_2
    //   455: lconst_0
    //   456: lcmp
    //   457: iflt -> 534
    //   460: ifnull -> 532
    //   463: goto -> 470
    //   466: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   469: athrow
    //   470: ifeq -> 512
    //   473: goto -> 480
    //   476: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   479: athrow
    //   480: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   483: getfield field_1724 : Lnet/minecraft/class_746;
    //   486: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   489: aload_0
    //   490: getfield o : I
    //   493: invokevirtual method_33096 : (I)Lnet/minecraft/class_2338;
    //   496: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   499: astore #13
    //   501: aload #10
    //   503: lload_2
    //   504: lconst_0
    //   505: lcmp
    //   506: ifle -> 976
    //   509: ifnonnull -> 969
    //   512: aload_0
    //   513: getfield M : Lwtf/opal/ke;
    //   516: invokevirtual z : ()Ljava/lang/Object;
    //   519: checkcast java/lang/Boolean
    //   522: invokevirtual booleanValue : ()Z
    //   525: goto -> 532
    //   528: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   531: athrow
    //   532: aload #10
    //   534: lload_2
    //   535: lconst_0
    //   536: lcmp
    //   537: ifle -> 578
    //   540: ifnull -> 576
    //   543: ifeq -> 955
    //   546: goto -> 553
    //   549: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   552: athrow
    //   553: aload_0
    //   554: getfield X : Lwtf/opal/ky;
    //   557: invokevirtual z : ()Ljava/lang/Object;
    //   560: checkcast wtf/opal/ll
    //   563: getstatic wtf/opal/ll.WATCHDOGJUMP : Lwtf/opal/ll;
    //   566: invokevirtual equals : (Ljava/lang/Object;)Z
    //   569: goto -> 576
    //   572: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   575: athrow
    //   576: aload #10
    //   578: lload_2
    //   579: lconst_0
    //   580: lcmp
    //   581: iflt -> 615
    //   584: ifnull -> 613
    //   587: ifeq -> 923
    //   590: goto -> 597
    //   593: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   596: athrow
    //   597: aload #11
    //   599: iconst_0
    //   600: anewarray java/lang/Object
    //   603: invokevirtual D : ([Ljava/lang/Object;)Z
    //   606: goto -> 613
    //   609: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   612: athrow
    //   613: aload #10
    //   615: lload_2
    //   616: lconst_0
    //   617: lcmp
    //   618: ifle -> 663
    //   621: ifnull -> 661
    //   624: ifeq -> 680
    //   627: goto -> 634
    //   630: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   633: athrow
    //   634: aload #11
    //   636: lload #8
    //   638: iconst_1
    //   639: anewarray java/lang/Object
    //   642: dup_x2
    //   643: dup_x2
    //   644: pop
    //   645: invokestatic valueOf : (J)Ljava/lang/Long;
    //   648: iconst_0
    //   649: swap
    //   650: aastore
    //   651: invokevirtual e : ([Ljava/lang/Object;)Z
    //   654: goto -> 661
    //   657: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   660: athrow
    //   661: aload #10
    //   663: ifnull -> 677
    //   666: ifeq -> 680
    //   669: goto -> 676
    //   672: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   675: athrow
    //   676: iconst_1
    //   677: goto -> 681
    //   680: iconst_0
    //   681: istore #14
    //   683: aload_0
    //   684: getfield p : I
    //   687: iload #14
    //   689: aload #10
    //   691: ifnull -> 717
    //   694: ifeq -> 720
    //   697: goto -> 704
    //   700: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   703: athrow
    //   704: sipush #16606
    //   707: ldc2_w 4510907131949618067
    //   710: lload_2
    //   711: lxor
    //   712: <illegal opcode> t : (IJ)I
    //   717: goto -> 733
    //   720: sipush #26564
    //   723: ldc2_w 6168801101579629716
    //   726: lload_2
    //   727: lxor
    //   728: <illegal opcode> t : (IJ)I
    //   733: lload_2
    //   734: lconst_0
    //   735: lcmp
    //   736: iflt -> 748
    //   739: if_icmpeq -> 802
    //   742: aload_0
    //   743: getfield p : I
    //   746: iload #14
    //   748: aload #10
    //   750: ifnull -> 783
    //   753: goto -> 760
    //   756: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   759: athrow
    //   760: ifeq -> 786
    //   763: goto -> 770
    //   766: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   769: athrow
    //   770: sipush #26564
    //   773: ldc2_w 6168801101579629716
    //   776: lload_2
    //   777: lxor
    //   778: <illegal opcode> t : (IJ)I
    //   783: goto -> 799
    //   786: sipush #18756
    //   789: ldc2_w 1261576428568615440
    //   792: lload_2
    //   793: lxor
    //   794: <illegal opcode> t : (IJ)I
    //   799: if_icmpne -> 884
    //   802: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   805: getfield field_1724 : Lnet/minecraft/class_746;
    //   808: aload #10
    //   810: ifnull -> 897
    //   813: goto -> 820
    //   816: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   819: athrow
    //   820: dconst_0
    //   821: ldc2_w -1.5
    //   824: dconst_0
    //   825: invokevirtual method_5654 : (DDD)Z
    //   828: ifne -> 884
    //   831: goto -> 838
    //   834: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   837: athrow
    //   838: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   841: getfield field_1724 : Lnet/minecraft/class_746;
    //   844: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   847: aload_0
    //   848: getfield o : I
    //   851: invokevirtual method_33096 : (I)Lnet/minecraft/class_2338;
    //   854: astore #13
    //   856: aload_0
    //   857: sipush #24735
    //   860: ldc2_w 3918903092262384604
    //   863: lload_2
    //   864: lxor
    //   865: <illegal opcode> t : (IJ)I
    //   870: putfield p : I
    //   873: aload #10
    //   875: lload_2
    //   876: lconst_0
    //   877: lcmp
    //   878: iflt -> 914
    //   881: ifnonnull -> 912
    //   884: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   887: getfield field_1724 : Lnet/minecraft/class_746;
    //   890: goto -> 897
    //   893: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   896: athrow
    //   897: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   900: aload_0
    //   901: getfield o : I
    //   904: invokevirtual method_33096 : (I)Lnet/minecraft/class_2338;
    //   907: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   910: astore #13
    //   912: aload #10
    //   914: lload_2
    //   915: lconst_0
    //   916: lcmp
    //   917: iflt -> 976
    //   920: ifnonnull -> 969
    //   923: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   926: getfield field_1724 : Lnet/minecraft/class_746;
    //   929: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   932: aload_0
    //   933: getfield o : I
    //   936: invokevirtual method_33096 : (I)Lnet/minecraft/class_2338;
    //   939: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   942: astore #13
    //   944: aload #10
    //   946: lload_2
    //   947: lconst_0
    //   948: lcmp
    //   949: ifle -> 976
    //   952: ifnonnull -> 969
    //   955: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   958: getfield field_1724 : Lnet/minecraft/class_746;
    //   961: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   964: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   967: astore #13
    //   969: aload_0
    //   970: getfield M : Lwtf/opal/ke;
    //   973: invokevirtual z : ()Ljava/lang/Object;
    //   976: checkcast java/lang/Boolean
    //   979: invokevirtual booleanValue : ()Z
    //   982: lload_2
    //   983: lconst_0
    //   984: lcmp
    //   985: ifle -> 1045
    //   988: aload #10
    //   990: ifnull -> 1045
    //   993: ifeq -> 1043
    //   996: goto -> 1003
    //   999: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1002: athrow
    //   1003: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1006: getfield field_1690 : Lnet/minecraft/class_315;
    //   1009: getfield field_1903 : Lnet/minecraft/class_304;
    //   1012: invokevirtual method_1434 : ()Z
    //   1015: aload #10
    //   1017: lload_2
    //   1018: lconst_0
    //   1019: lcmp
    //   1020: ifle -> 1164
    //   1023: ifnull -> 1162
    //   1026: goto -> 1033
    //   1029: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1032: athrow
    //   1033: ifeq -> 1139
    //   1036: goto -> 1043
    //   1039: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1042: athrow
    //   1043: iload #12
    //   1045: ifeq -> 1125
    //   1048: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1051: getfield field_1724 : Lnet/minecraft/class_746;
    //   1054: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   1057: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   1060: iconst_0
    //   1061: iconst_0
    //   1062: iconst_0
    //   1063: anewarray java/lang/Object
    //   1066: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1069: iconst_0
    //   1070: anewarray java/lang/Object
    //   1073: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   1076: lload #6
    //   1078: sipush #1431
    //   1081: ldc2_w 84868407865399960
    //   1084: lload_2
    //   1085: lxor
    //   1086: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   1091: iconst_2
    //   1092: anewarray java/lang/Object
    //   1095: dup_x1
    //   1096: swap
    //   1097: iconst_1
    //   1098: swap
    //   1099: aastore
    //   1100: dup_x2
    //   1101: dup_x2
    //   1102: pop
    //   1103: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1106: iconst_0
    //   1107: swap
    //   1108: aastore
    //   1109: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1112: invokestatic parseInt : (Ljava/lang/String;)I
    //   1115: invokevirtual method_10069 : (III)Lnet/minecraft/class_2338;
    //   1118: goto -> 1137
    //   1121: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1124: athrow
    //   1125: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1128: getfield field_1724 : Lnet/minecraft/class_746;
    //   1131: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
    //   1134: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
    //   1137: astore #13
    //   1139: aload_0
    //   1140: getfield x : Ljava/util/List;
    //   1143: invokeinterface clear : ()V
    //   1148: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1151: getfield field_1687 : Lnet/minecraft/class_638;
    //   1154: aload #13
    //   1156: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   1159: invokevirtual method_26215 : ()Z
    //   1162: aload #10
    //   1164: ifnull -> 1576
    //   1167: ifeq -> 1526
    //   1170: goto -> 1177
    //   1173: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1176: athrow
    //   1177: iconst_0
    //   1178: istore #14
    //   1180: iload #14
    //   1182: i2d
    //   1183: aload_0
    //   1184: getfield E : Lwtf/opal/kt;
    //   1187: invokevirtual z : ()Ljava/lang/Object;
    //   1190: checkcast java/lang/Double
    //   1193: invokevirtual doubleValue : ()D
    //   1196: dcmpg
    //   1197: ifge -> 1526
    //   1200: iconst_0
    //   1201: aload #10
    //   1203: ifnull -> 1576
    //   1206: istore #15
    //   1208: iload #15
    //   1210: i2d
    //   1211: aload_0
    //   1212: getfield E : Lwtf/opal/kt;
    //   1215: invokevirtual z : ()Ljava/lang/Object;
    //   1218: checkcast java/lang/Double
    //   1221: invokevirtual doubleValue : ()D
    //   1224: dcmpg
    //   1225: ifge -> 1512
    //   1228: iconst_1
    //   1229: aload #10
    //   1231: ifnull -> 1197
    //   1234: istore #16
    //   1236: iload #16
    //   1238: sipush #5641
    //   1241: ldc2_w 9067394464708379995
    //   1244: lload_2
    //   1245: lxor
    //   1246: <illegal opcode> t : (IJ)I
    //   1251: if_icmple -> 1498
    //   1254: aload #13
    //   1256: iload #14
    //   1258: iload #16
    //   1260: imul
    //   1261: iconst_0
    //   1262: iload #15
    //   1264: iload #16
    //   1266: imul
    //   1267: invokevirtual method_10069 : (III)Lnet/minecraft/class_2338;
    //   1270: astore #17
    //   1272: aload #10
    //   1274: lload_2
    //   1275: lconst_0
    //   1276: lcmp
    //   1277: iflt -> 1495
    //   1280: ifnull -> 1493
    //   1283: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1286: getfield field_1687 : Lnet/minecraft/class_638;
    //   1289: aload #17
    //   1291: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   1294: invokevirtual method_26215 : ()Z
    //   1297: aload #10
    //   1299: ifnull -> 1225
    //   1302: lload_2
    //   1303: lconst_0
    //   1304: lcmp
    //   1305: ifle -> 1229
    //   1308: goto -> 1315
    //   1311: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1314: athrow
    //   1315: ifeq -> 1484
    //   1318: invokestatic values : ()[Lnet/minecraft/class_2350;
    //   1321: astore #18
    //   1323: aload #18
    //   1325: arraylength
    //   1326: istore #19
    //   1328: iconst_0
    //   1329: istore #20
    //   1331: iload #20
    //   1333: iload #19
    //   1335: if_icmpge -> 1484
    //   1338: aload #18
    //   1340: iload #20
    //   1342: aaload
    //   1343: astore #21
    //   1345: aload #17
    //   1347: aload #21
    //   1349: invokevirtual method_10093 : (Lnet/minecraft/class_2350;)Lnet/minecraft/class_2338;
    //   1352: astore #22
    //   1354: aload #10
    //   1356: lload_2
    //   1357: lconst_0
    //   1358: lcmp
    //   1359: iflt -> 1481
    //   1362: ifnull -> 1479
    //   1365: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1368: getfield field_1687 : Lnet/minecraft/class_638;
    //   1371: aload #22
    //   1373: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   1376: invokevirtual method_26215 : ()Z
    //   1379: aload #10
    //   1381: ifnull -> 1238
    //   1384: lload_2
    //   1385: lconst_0
    //   1386: lcmp
    //   1387: iflt -> 1238
    //   1390: goto -> 1397
    //   1393: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1396: athrow
    //   1397: lload_2
    //   1398: lconst_0
    //   1399: lcmp
    //   1400: ifle -> 1423
    //   1403: ifne -> 1476
    //   1406: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1409: getfield field_1687 : Lnet/minecraft/class_638;
    //   1412: aload #22
    //   1414: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
    //   1417: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
    //   1420: instanceof net/minecraft/class_2404
    //   1423: aload #10
    //   1425: ifnull -> 1475
    //   1428: goto -> 1435
    //   1431: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1434: athrow
    //   1435: ifne -> 1476
    //   1438: goto -> 1445
    //   1441: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1444: athrow
    //   1445: aload_0
    //   1446: getfield x : Ljava/util/List;
    //   1449: new wtf/opal/dr
    //   1452: dup
    //   1453: aload #22
    //   1455: aload #21
    //   1457: invokevirtual method_10153 : ()Lnet/minecraft/class_2350;
    //   1460: invokespecial <init> : (Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V
    //   1463: invokeinterface add : (Ljava/lang/Object;)Z
    //   1468: goto -> 1475
    //   1471: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1474: athrow
    //   1475: pop
    //   1476: iinc #20, 1
    //   1479: aload #10
    //   1481: ifnonnull -> 1331
    //   1484: lload_2
    //   1485: lconst_0
    //   1486: lcmp
    //   1487: iflt -> 1501
    //   1490: iinc #16, -2
    //   1493: aload #10
    //   1495: ifnonnull -> 1236
    //   1498: iinc #15, 1
    //   1501: aload #10
    //   1503: lload_2
    //   1504: lconst_0
    //   1505: lcmp
    //   1506: ifle -> 1523
    //   1509: ifnonnull -> 1208
    //   1512: iinc #14, 1
    //   1515: lload_2
    //   1516: lconst_0
    //   1517: lcmp
    //   1518: iflt -> 1228
    //   1521: aload #10
    //   1523: ifnonnull -> 1180
    //   1526: aload_0
    //   1527: getfield x : Ljava/util/List;
    //   1530: <illegal opcode> applyAsDouble : ()Ljava/util/function/ToDoubleFunction;
    //   1535: invokestatic comparingDouble : (Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
    //   1538: invokeinterface sort : (Ljava/util/Comparator;)V
    //   1543: aload_0
    //   1544: aload #10
    //   1546: lload_2
    //   1547: lconst_0
    //   1548: lcmp
    //   1549: iflt -> 1590
    //   1552: lload_2
    //   1553: lconst_0
    //   1554: lcmp
    //   1555: iflt -> 1590
    //   1558: ifnull -> 1580
    //   1561: getfield x : Ljava/util/List;
    //   1564: invokeinterface isEmpty : ()Z
    //   1569: goto -> 1576
    //   1572: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1575: athrow
    //   1576: ifne -> 1596
    //   1579: aload_0
    //   1580: aload_0
    //   1581: getfield x : Ljava/util/List;
    //   1584: iconst_0
    //   1585: invokeinterface get : (I)Ljava/lang/Object;
    //   1590: checkcast wtf/opal/dr
    //   1593: putfield v : Lwtf/opal/dr;
    //   1596: return
    // Exception table:
    //   from	to	target	type
    //   80	101	104	wtf/opal/x5
    //   98	124	127	wtf/opal/x5
    //   131	139	142	wtf/opal/x5
    //   136	167	170	wtf/opal/x5
    //   146	183	186	wtf/opal/x5
    //   174	217	220	wtf/opal/x5
    //   190	227	230	wtf/opal/x5
    //   224	246	249	wtf/opal/x5
    //   253	267	270	wtf/opal/x5
    //   264	280	283	wtf/opal/x5
    //   287	301	304	wtf/opal/x5
    //   298	326	329	wtf/opal/x5
    //   333	347	350	wtf/opal/x5
    //   344	383	386	wtf/opal/x5
    //   390	398	401	wtf/opal/x5
    //   412	432	435	wtf/opal/x5
    //   429	463	466	wtf/opal/x5
    //   439	473	476	wtf/opal/x5
    //   501	525	528	wtf/opal/x5
    //   532	546	549	wtf/opal/x5
    //   543	569	572	wtf/opal/x5
    //   576	590	593	wtf/opal/x5
    //   587	606	609	wtf/opal/x5
    //   613	627	630	wtf/opal/x5
    //   624	654	657	wtf/opal/x5
    //   661	669	672	wtf/opal/x5
    //   683	697	700	wtf/opal/x5
    //   733	753	756	wtf/opal/x5
    //   742	763	766	wtf/opal/x5
    //   799	813	816	wtf/opal/x5
    //   802	831	834	wtf/opal/x5
    //   856	890	893	wtf/opal/x5
    //   969	996	999	wtf/opal/x5
    //   993	1026	1029	wtf/opal/x5
    //   1003	1036	1039	wtf/opal/x5
    //   1045	1121	1121	wtf/opal/x5
    //   1162	1170	1173	wtf/opal/x5
    //   1272	1302	1311	wtf/opal/x5
    //   1354	1384	1393	wtf/opal/x5
    //   1397	1428	1431	wtf/opal/x5
    //   1406	1438	1441	wtf/opal/x5
    //   1435	1468	1471	wtf/opal/x5
    //   1526	1569	1572	wtf/opal/x5
  }
  
  private void c(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/xw.bb : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 42149273307499
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic k : ()[I
    //   30: astore #6
    //   32: aload_0
    //   33: aload #6
    //   35: ifnull -> 59
    //   38: getfield u : Lwtf/opal/dr;
    //   41: ifnull -> 77
    //   44: goto -> 51
    //   47: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   50: athrow
    //   51: aload_0
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: getfield Y : Lnet/minecraft/class_241;
    //   62: aload #6
    //   64: ifnull -> 85
    //   67: ifnonnull -> 78
    //   70: goto -> 77
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: return
    //   78: aload_0
    //   79: getfield E : Lwtf/opal/kt;
    //   82: invokevirtual z : ()Ljava/lang/Object;
    //   85: checkcast java/lang/Double
    //   88: invokevirtual doubleValue : ()D
    //   91: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   94: invokevirtual method_1488 : ()F
    //   97: lload #4
    //   99: iconst_0
    //   100: aload_0
    //   101: getfield Y : Lnet/minecraft/class_241;
    //   104: getfield field_1343 : F
    //   107: aload_0
    //   108: getfield Y : Lnet/minecraft/class_241;
    //   111: getfield field_1342 : F
    //   114: bipush #6
    //   116: anewarray java/lang/Object
    //   119: dup_x1
    //   120: swap
    //   121: invokestatic valueOf : (F)Ljava/lang/Float;
    //   124: iconst_5
    //   125: swap
    //   126: aastore
    //   127: dup_x1
    //   128: swap
    //   129: invokestatic valueOf : (F)Ljava/lang/Float;
    //   132: iconst_4
    //   133: swap
    //   134: aastore
    //   135: dup_x1
    //   136: swap
    //   137: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   140: iconst_3
    //   141: swap
    //   142: aastore
    //   143: dup_x2
    //   144: dup_x2
    //   145: pop
    //   146: invokestatic valueOf : (J)Ljava/lang/Long;
    //   149: iconst_2
    //   150: swap
    //   151: aastore
    //   152: dup_x1
    //   153: swap
    //   154: invokestatic valueOf : (F)Ljava/lang/Float;
    //   157: iconst_1
    //   158: swap
    //   159: aastore
    //   160: dup_x2
    //   161: dup_x2
    //   162: pop
    //   163: invokestatic valueOf : (D)Ljava/lang/Double;
    //   166: iconst_0
    //   167: swap
    //   168: aastore
    //   169: invokestatic Z : ([Ljava/lang/Object;)Lnet/minecraft/class_239;
    //   172: astore #7
    //   174: aload #7
    //   176: lload_2
    //   177: lconst_0
    //   178: lcmp
    //   179: iflt -> 199
    //   182: aload #6
    //   184: ifnull -> 199
    //   187: ifnull -> 211
    //   190: goto -> 197
    //   193: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   196: athrow
    //   197: aload #7
    //   199: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   202: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
    //   205: invokevirtual equals : (Ljava/lang/Object;)Z
    //   208: ifne -> 253
    //   211: aload_0
    //   212: aload_0
    //   213: getfield u : Lwtf/opal/dr;
    //   216: getfield c : Lnet/minecraft/class_2338;
    //   219: aload_0
    //   220: getfield u : Lwtf/opal/dr;
    //   223: getfield i : Lnet/minecraft/class_2350;
    //   226: iconst_2
    //   227: anewarray java/lang/Object
    //   230: dup_x1
    //   231: swap
    //   232: iconst_1
    //   233: swap
    //   234: aastore
    //   235: dup_x1
    //   236: swap
    //   237: iconst_0
    //   238: swap
    //   239: aastore
    //   240: invokestatic V : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   243: putfield Y : Lnet/minecraft/class_241;
    //   246: goto -> 253
    //   249: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   252: athrow
    //   253: return
    // Exception table:
    //   from	to	target	type
    //   32	44	47	wtf/opal/x5
    //   38	52	55	wtf/opal/x5
    //   59	70	73	wtf/opal/x5
    //   174	190	193	wtf/opal/x5
    //   199	246	249	wtf/opal/x5
  }
  
  public boolean F(Object[] paramArrayOfObject) {
    return this.Z.z().booleanValue();
  }
  
  public boolean G(Object[] paramArrayOfObject) {
    return ((xx)this.P.z()).equals(xx.SERVER);
  }
  
  public boolean s(Object[] paramArrayOfObject) {
    return this.M.z().booleanValue();
  }
  
  public int H(Object[] paramArrayOfObject) {
    return this.Q;
  }
  
  private static double lambda$getBlockCache$9(dr paramdr) {
    long l1 = bb ^ 0xB3E45034C66L;
    long l2 = l1 ^ 0x4063C069BEFL;
    (new Object[3])[2] = paramdr.c.method_46558();
    (new Object[3])[1] = b9.c.field_1724.method_19538().method_1031(0.0D, -1.0D, 0.0D);
    new Object[3];
    return d6.g(new Object[] { Long.valueOf(l2) });
  }
  
  private void lambda$renderCounter$8(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean1, class_332 paramclass_332, String paramString, boolean paramBoolean2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield s : Lwtf/opal/pa;
    //   4: fload_1
    //   5: fload_2
    //   6: fload_3
    //   7: ldc_w 8.0
    //   10: fadd
    //   11: fload #4
    //   13: ldc_w 4.0
    //   16: aload_0
    //   17: iload #5
    //   19: iload #6
    //   21: aload #7
    //   23: aload #8
    //   25: fload_2
    //   26: iload #9
    //   28: fload_3
    //   29: fload_1
    //   30: <illegal opcode> run : (Lwtf/opal/xw;IZLnet/minecraft/class_332;Ljava/lang/String;FZFF)Ljava/lang/Runnable;
    //   35: bipush #6
    //   37: anewarray java/lang/Object
    //   40: dup_x1
    //   41: swap
    //   42: iconst_5
    //   43: swap
    //   44: aastore
    //   45: dup_x1
    //   46: swap
    //   47: invokestatic valueOf : (F)Ljava/lang/Float;
    //   50: iconst_4
    //   51: swap
    //   52: aastore
    //   53: dup_x1
    //   54: swap
    //   55: invokestatic valueOf : (F)Ljava/lang/Float;
    //   58: iconst_3
    //   59: swap
    //   60: aastore
    //   61: dup_x1
    //   62: swap
    //   63: invokestatic valueOf : (F)Ljava/lang/Float;
    //   66: iconst_2
    //   67: swap
    //   68: aastore
    //   69: dup_x1
    //   70: swap
    //   71: invokestatic valueOf : (F)Ljava/lang/Float;
    //   74: iconst_1
    //   75: swap
    //   76: aastore
    //   77: dup_x1
    //   78: swap
    //   79: invokestatic valueOf : (F)Ljava/lang/Float;
    //   82: iconst_0
    //   83: swap
    //   84: aastore
    //   85: invokevirtual r : ([Ljava/lang/Object;)V
    //   88: return
  }
  
  private void lambda$renderCounter$7(int paramInt, boolean paramBoolean1, class_332 paramclass_332, String paramString, float paramFloat1, boolean paramBoolean2, float paramFloat2, float paramFloat3) {
    // Byte code:
    //   0: getstatic wtf/opal/xw.bb : J
    //   3: ldc2_w 113701757331138
    //   6: lxor
    //   7: lstore #9
    //   9: lload #9
    //   11: dup2
    //   12: ldc2_w 131583566943401
    //   15: lxor
    //   16: lstore #11
    //   18: pop2
    //   19: invokestatic k : ()[I
    //   22: sipush #13897
    //   25: ldc2_w 4778677818731796198
    //   28: lload #9
    //   30: lxor
    //   31: <illegal opcode> t : (IJ)I
    //   36: istore #18
    //   38: astore #17
    //   40: iload_1
    //   41: sipush #874
    //   44: ldc2_w 8829237589525603276
    //   47: lload #9
    //   49: lxor
    //   50: <illegal opcode> t : (IJ)I
    //   55: aload #17
    //   57: ifnull -> 125
    //   60: if_icmple -> 91
    //   63: goto -> 70
    //   66: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   69: athrow
    //   70: sipush #11521
    //   73: ldc2_w 6352602351911538088
    //   76: lload #9
    //   78: lxor
    //   79: <illegal opcode> t : (IJ)I
    //   84: istore #18
    //   86: aload #17
    //   88: ifnonnull -> 144
    //   91: iload_1
    //   92: aload #17
    //   94: ifnull -> 142
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: sipush #894
    //   107: ldc2_w 1301301768270881736
    //   110: lload #9
    //   112: lxor
    //   113: <illegal opcode> t : (IJ)I
    //   118: goto -> 125
    //   121: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   124: athrow
    //   125: if_icmple -> 144
    //   128: sipush #28947
    //   131: ldc2_w 8799740092698350014
    //   134: lload #9
    //   136: lxor
    //   137: <illegal opcode> t : (IJ)I
    //   142: istore #18
    //   144: aload_0
    //   145: getfield y : Lwtf/opal/bu;
    //   148: iload_2
    //   149: ifeq -> 162
    //   152: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   155: goto -> 165
    //   158: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   165: aload_3
    //   166: aload #4
    //   168: fload #5
    //   170: iload #6
    //   172: ifeq -> 185
    //   175: ldc_w 8.5
    //   178: goto -> 188
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: ldc_w -0.5
    //   188: fadd
    //   189: fload #7
    //   191: ldc_w 12.0
    //   194: aload #17
    //   196: ifnull -> 224
    //   199: fadd
    //   200: iload_2
    //   201: ifeq -> 227
    //   204: goto -> 211
    //   207: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   210: athrow
    //   211: ldc_w 10.0
    //   214: fload #8
    //   216: fmul
    //   217: goto -> 224
    //   220: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   223: athrow
    //   224: goto -> 230
    //   227: ldc_w 9.0
    //   230: iload #18
    //   232: iconst_1
    //   233: istore #13
    //   235: istore #14
    //   237: fstore #15
    //   239: fstore #16
    //   241: lload #11
    //   243: fload #16
    //   245: fload #15
    //   247: iload #14
    //   249: iload #13
    //   251: bipush #9
    //   253: anewarray java/lang/Object
    //   256: dup_x1
    //   257: swap
    //   258: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   261: bipush #8
    //   263: swap
    //   264: aastore
    //   265: dup_x1
    //   266: swap
    //   267: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   270: bipush #7
    //   272: swap
    //   273: aastore
    //   274: dup_x1
    //   275: swap
    //   276: invokestatic valueOf : (F)Ljava/lang/Float;
    //   279: bipush #6
    //   281: swap
    //   282: aastore
    //   283: dup_x1
    //   284: swap
    //   285: invokestatic valueOf : (F)Ljava/lang/Float;
    //   288: iconst_5
    //   289: swap
    //   290: aastore
    //   291: dup_x2
    //   292: dup_x2
    //   293: pop
    //   294: invokestatic valueOf : (J)Ljava/lang/Long;
    //   297: iconst_4
    //   298: swap
    //   299: aastore
    //   300: dup_x1
    //   301: swap
    //   302: invokestatic valueOf : (F)Ljava/lang/Float;
    //   305: iconst_3
    //   306: swap
    //   307: aastore
    //   308: dup_x1
    //   309: swap
    //   310: iconst_2
    //   311: swap
    //   312: aastore
    //   313: dup_x1
    //   314: swap
    //   315: iconst_1
    //   316: swap
    //   317: aastore
    //   318: dup_x1
    //   319: swap
    //   320: iconst_0
    //   321: swap
    //   322: aastore
    //   323: invokevirtual H : ([Ljava/lang/Object;)V
    //   326: return
    // Exception table:
    //   from	to	target	type
    //   40	63	66	wtf/opal/x5
    //   86	97	100	wtf/opal/x5
    //   91	118	121	wtf/opal/x5
    //   144	158	158	wtf/opal/x5
    //   165	181	181	wtf/opal/x5
    //   188	204	207	wtf/opal/x5
    //   199	217	220	wtf/opal/x5
  }
  
  private void lambda$new$6(l8 paraml8) {
    long l = bb ^ 0x171182E0FA5AL;
    try {
      if (this.b.z().booleanValue())
        paraml8.n(new Object[] { Boolean.valueOf(true) }); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private static void lambda$new$5(p paramp) {}
  
  private void lambda$new$4(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/xw.bb : J
    //   3: ldc2_w 71174203587814
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 89856514156257
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 134382766968437
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic k : ()[I
    //   27: astore #8
    //   29: aload_0
    //   30: getfield B : Lwtf/opal/ke;
    //   33: invokevirtual z : ()Ljava/lang/Object;
    //   36: checkcast java/lang/Boolean
    //   39: invokevirtual booleanValue : ()Z
    //   42: aload #8
    //   44: ifnull -> 118
    //   47: ifeq -> 95
    //   50: goto -> 57
    //   53: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   56: athrow
    //   57: aload_0
    //   58: getfield R : Lwtf/opal/ky;
    //   61: invokevirtual z : ()Ljava/lang/Object;
    //   64: checkcast wtf/opal/l1
    //   67: getstatic wtf/opal/l1.WATCHDOG : Lwtf/opal/l1;
    //   70: invokevirtual equals : (Ljava/lang/Object;)Z
    //   73: aload #8
    //   75: ifnull -> 190
    //   78: goto -> 85
    //   81: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   84: athrow
    //   85: ifne -> 171
    //   88: goto -> 95
    //   91: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   94: athrow
    //   95: aload_0
    //   96: getfield R : Lwtf/opal/ky;
    //   99: invokevirtual z : ()Ljava/lang/Object;
    //   102: checkcast wtf/opal/l1
    //   105: getstatic wtf/opal/l1.WATCHDOG_LOWHOP : Lwtf/opal/l1;
    //   108: invokevirtual equals : (Ljava/lang/Object;)Z
    //   111: goto -> 118
    //   114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: aload #8
    //   120: ifnull -> 190
    //   123: ifne -> 171
    //   126: goto -> 133
    //   129: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: aload_0
    //   134: getfield R : Lwtf/opal/ky;
    //   137: invokevirtual z : ()Ljava/lang/Object;
    //   140: checkcast wtf/opal/l1
    //   143: getstatic wtf/opal/l1.WATCHDOG_DYNAMIC : Lwtf/opal/l1;
    //   146: invokevirtual equals : (Ljava/lang/Object;)Z
    //   149: aload #8
    //   151: ifnull -> 190
    //   154: goto -> 161
    //   157: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   160: athrow
    //   161: ifeq -> 356
    //   164: goto -> 171
    //   167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   170: athrow
    //   171: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   174: getfield field_1690 : Lnet/minecraft/class_315;
    //   177: getfield field_1903 : Lnet/minecraft/class_304;
    //   180: invokevirtual method_1434 : ()Z
    //   183: goto -> 190
    //   186: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   189: athrow
    //   190: aload #8
    //   192: ifnull -> 230
    //   195: ifeq -> 356
    //   198: goto -> 205
    //   201: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   204: athrow
    //   205: lload #4
    //   207: iconst_1
    //   208: anewarray java/lang/Object
    //   211: dup_x2
    //   212: dup_x2
    //   213: pop
    //   214: invokestatic valueOf : (J)Ljava/lang/Long;
    //   217: iconst_0
    //   218: swap
    //   219: aastore
    //   220: invokestatic I : ([Ljava/lang/Object;)Z
    //   223: goto -> 230
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: ifne -> 356
    //   233: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   236: getfield field_1724 : Lnet/minecraft/class_746;
    //   239: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   242: getfield field_1724 : Lnet/minecraft/class_746;
    //   245: invokevirtual method_23317 : ()D
    //   248: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   251: getfield field_1724 : Lnet/minecraft/class_746;
    //   254: invokevirtual method_23318 : ()D
    //   257: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   260: getfield field_1724 : Lnet/minecraft/class_746;
    //   263: invokevirtual method_23321 : ()D
    //   266: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   269: getfield field_1724 : Lnet/minecraft/class_746;
    //   272: invokevirtual method_23321 : ()D
    //   275: invokestatic floor : (D)D
    //   278: ldc2_w 0.009999999776482582
    //   281: iconst_3
    //   282: anewarray java/lang/Object
    //   285: dup_x2
    //   286: dup_x2
    //   287: pop
    //   288: invokestatic valueOf : (D)Ljava/lang/Double;
    //   291: iconst_2
    //   292: swap
    //   293: aastore
    //   294: dup_x2
    //   295: dup_x2
    //   296: pop
    //   297: invokestatic valueOf : (D)Ljava/lang/Double;
    //   300: iconst_1
    //   301: swap
    //   302: aastore
    //   303: dup_x2
    //   304: dup_x2
    //   305: pop
    //   306: invokestatic valueOf : (D)Ljava/lang/Double;
    //   309: iconst_0
    //   310: swap
    //   311: aastore
    //   312: invokestatic Y : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   315: invokevirtual doubleValue : ()D
    //   318: invokevirtual method_5814 : (DDD)V
    //   321: lload #6
    //   323: dconst_0
    //   324: iconst_2
    //   325: anewarray java/lang/Object
    //   328: dup_x2
    //   329: dup_x2
    //   330: pop
    //   331: invokestatic valueOf : (D)Ljava/lang/Double;
    //   334: iconst_1
    //   335: swap
    //   336: aastore
    //   337: dup_x2
    //   338: dup_x2
    //   339: pop
    //   340: invokestatic valueOf : (J)Ljava/lang/Long;
    //   343: iconst_0
    //   344: swap
    //   345: aastore
    //   346: invokestatic k : ([Ljava/lang/Object;)V
    //   349: goto -> 356
    //   352: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   355: athrow
    //   356: return
    // Exception table:
    //   from	to	target	type
    //   29	50	53	wtf/opal/x5
    //   47	78	81	wtf/opal/x5
    //   57	88	91	wtf/opal/x5
    //   85	111	114	wtf/opal/x5
    //   118	126	129	wtf/opal/x5
    //   123	154	157	wtf/opal/x5
    //   133	164	167	wtf/opal/x5
    //   161	183	186	wtf/opal/x5
    //   190	198	201	wtf/opal/x5
    //   195	223	226	wtf/opal/x5
    //   230	349	352	wtf/opal/x5
  }
  
  private static void lambda$new$3(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/xw.bb : J
    //   3: ldc2_w 34501398369626
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic k : ()[I
    //   11: aload_0
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #5
    //   21: astore_3
    //   22: aload #5
    //   24: aload_3
    //   25: ifnull -> 50
    //   28: instanceof net/minecraft/class_2653
    //   31: ifeq -> 340
    //   34: goto -> 41
    //   37: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   40: athrow
    //   41: aload #5
    //   43: goto -> 50
    //   46: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   49: athrow
    //   50: checkcast net/minecraft/class_2653
    //   53: astore #4
    //   55: aload #4
    //   57: aload_3
    //   58: ifnull -> 102
    //   61: invokevirtual method_11449 : ()Lnet/minecraft/class_1799;
    //   64: ifnonnull -> 93
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: aload_0
    //   75: iconst_0
    //   76: anewarray java/lang/Object
    //   79: invokevirtual Z : ([Ljava/lang/Object;)V
    //   82: aload_3
    //   83: ifnonnull -> 340
    //   86: goto -> 93
    //   89: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   92: athrow
    //   93: aload #4
    //   95: goto -> 102
    //   98: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: invokevirtual method_11450 : ()I
    //   105: sipush #22240
    //   108: ldc2_w 3899563148281628123
    //   111: lload_1
    //   112: lxor
    //   113: <illegal opcode> t : (IJ)I
    //   118: isub
    //   119: istore #5
    //   121: iload #5
    //   123: ifge -> 131
    //   126: return
    //   127: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   134: getfield field_1724 : Lnet/minecraft/class_746;
    //   137: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   140: iload #5
    //   142: invokevirtual method_5438 : (I)Lnet/minecraft/class_1799;
    //   145: astore #6
    //   147: aload #4
    //   149: invokevirtual method_11449 : ()Lnet/minecraft/class_1799;
    //   152: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   155: astore #7
    //   157: aload #6
    //   159: aload_3
    //   160: ifnull -> 239
    //   163: ifnonnull -> 230
    //   166: goto -> 173
    //   169: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   172: athrow
    //   173: aload #4
    //   175: invokevirtual method_11449 : ()Lnet/minecraft/class_1799;
    //   178: aload_3
    //   179: ifnull -> 239
    //   182: goto -> 189
    //   185: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   188: athrow
    //   189: invokevirtual method_7947 : ()I
    //   192: sipush #5675
    //   195: ldc2_w 1058505131309532436
    //   198: lload_1
    //   199: lxor
    //   200: <illegal opcode> t : (IJ)I
    //   205: if_icmpgt -> 230
    //   208: goto -> 215
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: aload #7
    //   217: instanceof net/minecraft/class_1747
    //   220: ifne -> 325
    //   223: goto -> 230
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: aload #6
    //   232: goto -> 239
    //   235: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   238: athrow
    //   239: aload_3
    //   240: ifnull -> 322
    //   243: ifnull -> 310
    //   246: goto -> 253
    //   249: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   252: athrow
    //   253: aload #6
    //   255: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   258: checkcast net/minecraft/class_1799
    //   261: aload_3
    //   262: ifnull -> 322
    //   265: goto -> 272
    //   268: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   271: athrow
    //   272: invokevirtual method_7947 : ()I
    //   275: aload #4
    //   277: invokevirtual method_11449 : ()Lnet/minecraft/class_1799;
    //   280: invokevirtual method_7947 : ()I
    //   283: isub
    //   284: invokestatic abs : (I)I
    //   287: sipush #5675
    //   290: ldc2_w 1058505131309532436
    //   293: lload_1
    //   294: lxor
    //   295: <illegal opcode> t : (IJ)I
    //   300: if_icmple -> 325
    //   303: goto -> 310
    //   306: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   309: athrow
    //   310: aload #4
    //   312: invokevirtual method_11449 : ()Lnet/minecraft/class_1799;
    //   315: goto -> 322
    //   318: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   321: athrow
    //   322: ifnonnull -> 340
    //   325: aload_0
    //   326: iconst_0
    //   327: anewarray java/lang/Object
    //   330: invokevirtual Z : ([Ljava/lang/Object;)V
    //   333: goto -> 340
    //   336: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   339: athrow
    //   340: return
    // Exception table:
    //   from	to	target	type
    //   22	34	37	wtf/opal/x5
    //   28	43	46	wtf/opal/x5
    //   55	67	70	wtf/opal/x5
    //   61	86	89	wtf/opal/x5
    //   74	95	98	wtf/opal/x5
    //   121	127	127	wtf/opal/x5
    //   157	166	169	wtf/opal/x5
    //   163	182	185	wtf/opal/x5
    //   173	208	211	wtf/opal/x5
    //   189	223	226	wtf/opal/x5
    //   215	232	235	wtf/opal/x5
    //   239	246	249	wtf/opal/x5
    //   243	265	268	wtf/opal/x5
    //   253	303	306	wtf/opal/x5
    //   272	315	318	wtf/opal/x5
    //   322	333	336	wtf/opal/x5
  }
  
  private void lambda$new$2(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/xw.bb : J
    //   3: ldc2_w 8836100619315
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 27537185383988
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 6998066942963
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 36170255919381
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: iconst_0
    //   32: anewarray java/lang/Object
    //   35: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   38: iconst_0
    //   39: anewarray java/lang/Object
    //   42: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   45: ldc_w wtf/opal/jm
    //   48: iconst_1
    //   49: anewarray java/lang/Object
    //   52: dup_x1
    //   53: swap
    //   54: iconst_0
    //   55: swap
    //   56: aastore
    //   57: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   60: checkcast wtf/opal/jm
    //   63: astore #11
    //   65: invokestatic k : ()[I
    //   68: aload_1
    //   69: iconst_0
    //   70: anewarray java/lang/Object
    //   73: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   76: astore #13
    //   78: astore #10
    //   80: aload #13
    //   82: instanceof net/minecraft/class_2828
    //   85: aload #10
    //   87: ifnull -> 988
    //   90: ifeq -> 975
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: aload #13
    //   102: checkcast net/minecraft/class_2828
    //   105: astore #12
    //   107: aload #12
    //   109: checkcast wtf/opal/mixin/PlayerMoveC2SPacketAccessor
    //   112: astore #13
    //   114: aload_0
    //   115: getfield Z : Lwtf/opal/ke;
    //   118: invokevirtual z : ()Ljava/lang/Object;
    //   121: checkcast java/lang/Boolean
    //   124: invokevirtual booleanValue : ()Z
    //   127: aload #10
    //   129: ifnull -> 702
    //   132: ifeq -> 689
    //   135: goto -> 142
    //   138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: aload_0
    //   143: getfield X : Lwtf/opal/ky;
    //   146: invokevirtual z : ()Ljava/lang/Object;
    //   149: checkcast wtf/opal/ll
    //   152: getstatic wtf/opal/ll.WATCHDOG : Lwtf/opal/ll;
    //   155: invokevirtual equals : (Ljava/lang/Object;)Z
    //   158: aload #10
    //   160: ifnull -> 702
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: ifeq -> 689
    //   173: goto -> 180
    //   176: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   179: athrow
    //   180: lload #4
    //   182: iconst_1
    //   183: anewarray java/lang/Object
    //   186: dup_x2
    //   187: dup_x2
    //   188: pop
    //   189: invokestatic valueOf : (J)Ljava/lang/Long;
    //   192: iconst_0
    //   193: swap
    //   194: aastore
    //   195: invokestatic I : ([Ljava/lang/Object;)Z
    //   198: aload #10
    //   200: ifnull -> 702
    //   203: goto -> 210
    //   206: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   209: athrow
    //   210: ifeq -> 689
    //   213: goto -> 220
    //   216: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   219: athrow
    //   220: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   223: getfield field_1724 : Lnet/minecraft/class_746;
    //   226: invokevirtual method_24828 : ()Z
    //   229: aload #10
    //   231: ifnull -> 539
    //   234: goto -> 241
    //   237: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: ifeq -> 486
    //   244: goto -> 251
    //   247: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   250: athrow
    //   251: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   254: getfield field_1690 : Lnet/minecraft/class_315;
    //   257: getfield field_1903 : Lnet/minecraft/class_304;
    //   260: invokevirtual method_1434 : ()Z
    //   263: aload #10
    //   265: ifnull -> 539
    //   268: goto -> 275
    //   271: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   274: athrow
    //   275: ifne -> 486
    //   278: goto -> 285
    //   281: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   284: athrow
    //   285: aload #11
    //   287: iconst_0
    //   288: anewarray java/lang/Object
    //   291: invokevirtual D : ([Ljava/lang/Object;)Z
    //   294: aload #10
    //   296: ifnull -> 539
    //   299: goto -> 306
    //   302: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   305: athrow
    //   306: ifne -> 486
    //   309: goto -> 316
    //   312: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   315: athrow
    //   316: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   319: getfield field_1724 : Lnet/minecraft/class_746;
    //   322: getfield field_6012 : I
    //   325: iconst_0
    //   326: anewarray java/lang/Object
    //   329: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   332: iconst_0
    //   333: anewarray java/lang/Object
    //   336: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   339: lload #6
    //   341: ldc_w 'L'
    //   344: iconst_2
    //   345: anewarray java/lang/Object
    //   348: dup_x1
    //   349: swap
    //   350: iconst_1
    //   351: swap
    //   352: aastore
    //   353: dup_x2
    //   354: dup_x2
    //   355: pop
    //   356: invokestatic valueOf : (J)Ljava/lang/Long;
    //   359: iconst_0
    //   360: swap
    //   361: aastore
    //   362: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   365: invokestatic parseInt : (Ljava/lang/String;)I
    //   368: irem
    //   369: aload #10
    //   371: ifnull -> 539
    //   374: goto -> 381
    //   377: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   380: athrow
    //   381: ifne -> 486
    //   384: goto -> 391
    //   387: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   390: athrow
    //   391: iconst_0
    //   392: anewarray java/lang/Object
    //   395: invokestatic U : ([Ljava/lang/Object;)F
    //   398: f2d
    //   399: ldc2_w 0.8
    //   402: dmul
    //   403: lload #8
    //   405: dup2_x2
    //   406: pop2
    //   407: iconst_2
    //   408: anewarray java/lang/Object
    //   411: dup_x2
    //   412: dup_x2
    //   413: pop
    //   414: invokestatic valueOf : (D)Ljava/lang/Double;
    //   417: iconst_1
    //   418: swap
    //   419: aastore
    //   420: dup_x2
    //   421: dup_x2
    //   422: pop
    //   423: invokestatic valueOf : (J)Ljava/lang/Long;
    //   426: iconst_0
    //   427: swap
    //   428: aastore
    //   429: invokestatic V : ([Ljava/lang/Object;)[D
    //   432: astore #14
    //   434: aload #13
    //   436: aload #12
    //   438: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   441: getfield field_1724 : Lnet/minecraft/class_746;
    //   444: invokevirtual method_23317 : ()D
    //   447: invokevirtual method_12269 : (D)D
    //   450: aload #14
    //   452: iconst_0
    //   453: daload
    //   454: dsub
    //   455: invokeinterface setX : (D)V
    //   460: aload #13
    //   462: aload #12
    //   464: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   467: getfield field_1724 : Lnet/minecraft/class_746;
    //   470: invokevirtual method_23321 : ()D
    //   473: invokevirtual method_12274 : (D)D
    //   476: aload #14
    //   478: iconst_1
    //   479: daload
    //   480: dsub
    //   481: invokeinterface setZ : (D)V
    //   486: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   489: getfield field_1724 : Lnet/minecraft/class_746;
    //   492: getfield field_6012 : I
    //   495: iconst_0
    //   496: anewarray java/lang/Object
    //   499: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   502: iconst_0
    //   503: anewarray java/lang/Object
    //   506: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   509: lload #6
    //   511: ldc_w 'L'
    //   514: iconst_2
    //   515: anewarray java/lang/Object
    //   518: dup_x1
    //   519: swap
    //   520: iconst_1
    //   521: swap
    //   522: aastore
    //   523: dup_x2
    //   524: dup_x2
    //   525: pop
    //   526: invokestatic valueOf : (J)Ljava/lang/Long;
    //   529: iconst_0
    //   530: swap
    //   531: aastore
    //   532: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   535: invokestatic parseInt : (Ljava/lang/String;)I
    //   538: irem
    //   539: aload #10
    //   541: ifnull -> 573
    //   544: ifne -> 645
    //   547: goto -> 554
    //   550: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   553: athrow
    //   554: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   557: getfield field_1690 : Lnet/minecraft/class_315;
    //   560: getfield field_1903 : Lnet/minecraft/class_304;
    //   563: invokevirtual method_1434 : ()Z
    //   566: goto -> 573
    //   569: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   572: athrow
    //   573: aload #10
    //   575: ifnull -> 604
    //   578: ifne -> 645
    //   581: goto -> 588
    //   584: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   587: athrow
    //   588: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   591: getfield field_1724 : Lnet/minecraft/class_746;
    //   594: invokevirtual method_24828 : ()Z
    //   597: goto -> 604
    //   600: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   603: athrow
    //   604: aload #10
    //   606: ifnull -> 630
    //   609: ifeq -> 645
    //   612: goto -> 619
    //   615: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   618: athrow
    //   619: aload_0
    //   620: getfield W : Z
    //   623: goto -> 630
    //   626: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   629: athrow
    //   630: aload #10
    //   632: ifnull -> 702
    //   635: ifeq -> 689
    //   638: goto -> 645
    //   641: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   644: athrow
    //   645: aload_0
    //   646: getfield m : Ljava/util/List;
    //   649: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   652: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   655: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   658: dup
    //   659: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   662: pop
    //   663: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   668: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   673: aload_0
    //   674: getfield m : Ljava/util/List;
    //   677: invokeinterface clear : ()V
    //   682: goto -> 689
    //   685: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   688: athrow
    //   689: aload_0
    //   690: getfield Z : Lwtf/opal/ke;
    //   693: invokevirtual z : ()Ljava/lang/Object;
    //   696: checkcast java/lang/Boolean
    //   699: invokevirtual booleanValue : ()Z
    //   702: aload #10
    //   704: ifnull -> 988
    //   707: ifeq -> 975
    //   710: goto -> 717
    //   713: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   716: athrow
    //   717: aload_0
    //   718: getfield X : Lwtf/opal/ky;
    //   721: invokevirtual z : ()Ljava/lang/Object;
    //   724: checkcast wtf/opal/ll
    //   727: getstatic wtf/opal/ll.WATCHDOG2 : Lwtf/opal/ll;
    //   730: invokevirtual equals : (Ljava/lang/Object;)Z
    //   733: aload #10
    //   735: ifnull -> 988
    //   738: goto -> 745
    //   741: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   744: athrow
    //   745: ifeq -> 975
    //   748: goto -> 755
    //   751: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   754: athrow
    //   755: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   758: getfield field_1690 : Lnet/minecraft/class_315;
    //   761: getfield field_1903 : Lnet/minecraft/class_304;
    //   764: invokevirtual method_1434 : ()Z
    //   767: aload #10
    //   769: ifnull -> 988
    //   772: goto -> 779
    //   775: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   778: athrow
    //   779: ifne -> 975
    //   782: goto -> 789
    //   785: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   788: athrow
    //   789: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   792: getfield field_1724 : Lnet/minecraft/class_746;
    //   795: getfield field_6012 : I
    //   798: iconst_0
    //   799: anewarray java/lang/Object
    //   802: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   805: iconst_0
    //   806: anewarray java/lang/Object
    //   809: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   812: lload #6
    //   814: ldc_w 'L'
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
    //   835: invokevirtual n : ([Ljava/lang/Object;)Ljava/lang/String;
    //   838: invokestatic parseInt : (Ljava/lang/String;)I
    //   841: irem
    //   842: aload #10
    //   844: ifnull -> 988
    //   847: goto -> 854
    //   850: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   853: athrow
    //   854: ifne -> 975
    //   857: goto -> 864
    //   860: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   863: athrow
    //   864: lload #4
    //   866: iconst_1
    //   867: anewarray java/lang/Object
    //   870: dup_x2
    //   871: dup_x2
    //   872: pop
    //   873: invokestatic valueOf : (J)Ljava/lang/Long;
    //   876: iconst_0
    //   877: swap
    //   878: aastore
    //   879: invokestatic I : ([Ljava/lang/Object;)Z
    //   882: aload #10
    //   884: ifnull -> 988
    //   887: goto -> 894
    //   890: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   893: athrow
    //   894: ifeq -> 975
    //   897: goto -> 904
    //   900: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   903: athrow
    //   904: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   907: getfield field_1724 : Lnet/minecraft/class_746;
    //   910: invokevirtual method_24828 : ()Z
    //   913: aload #10
    //   915: ifnull -> 988
    //   918: goto -> 925
    //   921: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   924: athrow
    //   925: ifeq -> 975
    //   928: goto -> 935
    //   931: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   934: athrow
    //   935: aload #13
    //   937: aload #12
    //   939: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   942: getfield field_1724 : Lnet/minecraft/class_746;
    //   945: invokevirtual method_23318 : ()D
    //   948: invokevirtual method_12268 : (D)D
    //   951: ldc2_w 9.9E-4
    //   954: dadd
    //   955: invokeinterface setY : (D)V
    //   960: aload #13
    //   962: iconst_0
    //   963: invokeinterface setOnGround : (Z)V
    //   968: goto -> 975
    //   971: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   974: athrow
    //   975: aload_0
    //   976: getfield Z : Lwtf/opal/ke;
    //   979: invokevirtual z : ()Ljava/lang/Object;
    //   982: checkcast java/lang/Boolean
    //   985: invokevirtual booleanValue : ()Z
    //   988: aload #10
    //   990: ifnull -> 1026
    //   993: ifeq -> 1205
    //   996: goto -> 1003
    //   999: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1002: athrow
    //   1003: aload_0
    //   1004: getfield X : Lwtf/opal/ky;
    //   1007: invokevirtual z : ()Ljava/lang/Object;
    //   1010: checkcast wtf/opal/ll
    //   1013: getstatic wtf/opal/ll.WATCHDOG : Lwtf/opal/ll;
    //   1016: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1019: goto -> 1026
    //   1022: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1025: athrow
    //   1026: aload #10
    //   1028: ifnull -> 1059
    //   1031: ifeq -> 1205
    //   1034: goto -> 1041
    //   1037: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1040: athrow
    //   1041: aload_1
    //   1042: iconst_0
    //   1043: anewarray java/lang/Object
    //   1046: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   1049: instanceof net/minecraft/class_2885
    //   1052: goto -> 1059
    //   1055: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1058: athrow
    //   1059: aload #10
    //   1061: ifnull -> 1123
    //   1064: ifne -> 1107
    //   1067: goto -> 1074
    //   1070: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1073: athrow
    //   1074: aload_1
    //   1075: iconst_0
    //   1076: anewarray java/lang/Object
    //   1079: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   1082: aload #10
    //   1084: ifnull -> 1213
    //   1087: goto -> 1094
    //   1090: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1093: athrow
    //   1094: instanceof net/minecraft/class_2879
    //   1097: ifeq -> 1205
    //   1100: goto -> 1107
    //   1103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1106: athrow
    //   1107: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1110: getfield field_1724 : Lnet/minecraft/class_746;
    //   1113: invokevirtual method_24828 : ()Z
    //   1116: goto -> 1123
    //   1119: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1122: athrow
    //   1123: aload #10
    //   1125: ifnull -> 1157
    //   1128: ifeq -> 1205
    //   1131: goto -> 1138
    //   1134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1137: athrow
    //   1138: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1141: getfield field_1690 : Lnet/minecraft/class_315;
    //   1144: getfield field_1903 : Lnet/minecraft/class_304;
    //   1147: invokevirtual method_1434 : ()Z
    //   1150: goto -> 1157
    //   1153: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1156: athrow
    //   1157: aload #10
    //   1159: ifnull -> 1204
    //   1162: ifne -> 1205
    //   1165: goto -> 1172
    //   1168: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1171: athrow
    //   1172: aload_1
    //   1173: iconst_0
    //   1174: anewarray java/lang/Object
    //   1177: invokevirtual Z : ([Ljava/lang/Object;)V
    //   1180: aload_0
    //   1181: getfield m : Ljava/util/List;
    //   1184: aload_1
    //   1185: iconst_0
    //   1186: anewarray java/lang/Object
    //   1189: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   1192: invokeinterface add : (Ljava/lang/Object;)Z
    //   1197: goto -> 1204
    //   1200: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1203: athrow
    //   1204: pop
    //   1205: aload_1
    //   1206: iconst_0
    //   1207: anewarray java/lang/Object
    //   1210: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   1213: astore #13
    //   1215: aload #13
    //   1217: aload #10
    //   1219: ifnull -> 1244
    //   1222: instanceof net/minecraft/class_2868
    //   1225: ifeq -> 1304
    //   1228: goto -> 1235
    //   1231: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1234: athrow
    //   1235: aload #13
    //   1237: goto -> 1244
    //   1240: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1243: athrow
    //   1244: checkcast net/minecraft/class_2868
    //   1247: astore #12
    //   1249: aload_0
    //   1250: aload #10
    //   1252: ifnull -> 1288
    //   1255: getfield P : Lwtf/opal/ky;
    //   1258: invokevirtual z : ()Ljava/lang/Object;
    //   1261: checkcast wtf/opal/xx
    //   1264: getstatic wtf/opal/xx.SERVER : Lwtf/opal/xx;
    //   1267: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1270: ifeq -> 1304
    //   1273: goto -> 1280
    //   1276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1279: athrow
    //   1280: aload_0
    //   1281: goto -> 1288
    //   1284: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1287: athrow
    //   1288: aload #12
    //   1290: invokevirtual method_12442 : ()I
    //   1293: putfield L : I
    //   1296: aload_1
    //   1297: iconst_0
    //   1298: anewarray java/lang/Object
    //   1301: invokevirtual Z : ([Ljava/lang/Object;)V
    //   1304: return
    // Exception table:
    //   from	to	target	type
    //   80	93	96	wtf/opal/x5
    //   114	135	138	wtf/opal/x5
    //   132	163	166	wtf/opal/x5
    //   142	173	176	wtf/opal/x5
    //   170	203	206	wtf/opal/x5
    //   180	213	216	wtf/opal/x5
    //   210	234	237	wtf/opal/x5
    //   220	244	247	wtf/opal/x5
    //   241	268	271	wtf/opal/x5
    //   251	278	281	wtf/opal/x5
    //   275	299	302	wtf/opal/x5
    //   285	309	312	wtf/opal/x5
    //   306	374	377	wtf/opal/x5
    //   316	384	387	wtf/opal/x5
    //   539	547	550	wtf/opal/x5
    //   544	566	569	wtf/opal/x5
    //   573	581	584	wtf/opal/x5
    //   578	597	600	wtf/opal/x5
    //   604	612	615	wtf/opal/x5
    //   609	623	626	wtf/opal/x5
    //   630	638	641	wtf/opal/x5
    //   635	682	685	wtf/opal/x5
    //   702	710	713	wtf/opal/x5
    //   707	738	741	wtf/opal/x5
    //   717	748	751	wtf/opal/x5
    //   745	772	775	wtf/opal/x5
    //   755	782	785	wtf/opal/x5
    //   779	847	850	wtf/opal/x5
    //   789	857	860	wtf/opal/x5
    //   854	887	890	wtf/opal/x5
    //   864	897	900	wtf/opal/x5
    //   894	918	921	wtf/opal/x5
    //   904	928	931	wtf/opal/x5
    //   925	968	971	wtf/opal/x5
    //   988	996	999	wtf/opal/x5
    //   993	1019	1022	wtf/opal/x5
    //   1026	1034	1037	wtf/opal/x5
    //   1031	1052	1055	wtf/opal/x5
    //   1059	1067	1070	wtf/opal/x5
    //   1064	1087	1090	wtf/opal/x5
    //   1074	1100	1103	wtf/opal/x5
    //   1094	1116	1119	wtf/opal/x5
    //   1123	1131	1134	wtf/opal/x5
    //   1128	1150	1153	wtf/opal/x5
    //   1157	1165	1168	wtf/opal/x5
    //   1162	1197	1200	wtf/opal/x5
    //   1215	1228	1231	wtf/opal/x5
    //   1222	1237	1240	wtf/opal/x5
    //   1249	1273	1276	wtf/opal/x5
    //   1255	1281	1284	wtf/opal/x5
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/xw.bb : J
    //   3: ldc2_w 121132949208744
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 136943559884999
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 8094103199066
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 69686589355219
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic k : ()[I
    //   34: aload_0
    //   35: lload #4
    //   37: iconst_1
    //   38: anewarray java/lang/Object
    //   41: dup_x2
    //   42: dup_x2
    //   43: pop
    //   44: invokestatic valueOf : (J)Ljava/lang/Long;
    //   47: iconst_0
    //   48: swap
    //   49: aastore
    //   50: invokestatic d : ([Ljava/lang/Object;)I
    //   53: putfield Q : I
    //   56: astore #10
    //   58: aload_0
    //   59: getfield Q : I
    //   62: aload #10
    //   64: ifnull -> 382
    //   67: iconst_m1
    //   68: if_icmpeq -> 266
    //   71: goto -> 78
    //   74: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   77: athrow
    //   78: aload_0
    //   79: getfield P : Lwtf/opal/ky;
    //   82: invokevirtual z : ()Ljava/lang/Object;
    //   85: checkcast wtf/opal/xx
    //   88: invokevirtual ordinal : ()I
    //   91: aload #10
    //   93: ifnull -> 382
    //   96: goto -> 103
    //   99: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: lookupswitch default -> 266, 0 -> 132, 1 -> 198
    //   128: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   135: getfield field_1724 : Lnet/minecraft/class_746;
    //   138: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   141: getfield field_7545 : I
    //   144: aload #10
    //   146: ifnull -> 382
    //   149: goto -> 156
    //   152: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: aload_0
    //   157: getfield Q : I
    //   160: if_icmpeq -> 266
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   173: getfield field_1724 : Lnet/minecraft/class_746;
    //   176: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   179: aload_0
    //   180: getfield Q : I
    //   183: putfield field_7545 : I
    //   186: aload #10
    //   188: ifnonnull -> 266
    //   191: goto -> 198
    //   194: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   197: athrow
    //   198: aload_0
    //   199: getfield G : I
    //   202: aload #10
    //   204: ifnull -> 382
    //   207: goto -> 214
    //   210: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   213: athrow
    //   214: aload_0
    //   215: getfield Q : I
    //   218: if_icmpeq -> 266
    //   221: goto -> 228
    //   224: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   227: athrow
    //   228: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   231: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   234: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   237: new net/minecraft/class_2868
    //   240: dup
    //   241: aload_0
    //   242: getfield Q : I
    //   245: invokespecial <init> : (I)V
    //   248: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   251: aload_0
    //   252: aload_0
    //   253: getfield Q : I
    //   256: putfield G : I
    //   259: goto -> 266
    //   262: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   265: athrow
    //   266: aload_0
    //   267: lload #6
    //   269: iconst_1
    //   270: anewarray java/lang/Object
    //   273: dup_x2
    //   274: dup_x2
    //   275: pop
    //   276: invokestatic valueOf : (J)Ljava/lang/Long;
    //   279: iconst_0
    //   280: swap
    //   281: aastore
    //   282: invokevirtual c : ([Ljava/lang/Object;)V
    //   285: iconst_0
    //   286: anewarray java/lang/Object
    //   289: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   292: iconst_0
    //   293: anewarray java/lang/Object
    //   296: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   299: aload_0
    //   300: getfield Y : Lnet/minecraft/class_241;
    //   303: aload_0
    //   304: getfield ms : Lwtf/opal/kt;
    //   307: invokevirtual z : ()Ljava/lang/Object;
    //   310: checkcast java/lang/Double
    //   313: invokevirtual floatValue : ()F
    //   316: lload #8
    //   318: dup2_x1
    //   319: pop2
    //   320: aload_0
    //   321: getfield J : Lwtf/opal/kt;
    //   324: invokevirtual z : ()Ljava/lang/Object;
    //   327: checkcast java/lang/Double
    //   330: invokevirtual floatValue : ()F
    //   333: iconst_4
    //   334: anewarray java/lang/Object
    //   337: dup_x1
    //   338: swap
    //   339: invokestatic valueOf : (F)Ljava/lang/Float;
    //   342: iconst_3
    //   343: swap
    //   344: aastore
    //   345: dup_x1
    //   346: swap
    //   347: invokestatic valueOf : (F)Ljava/lang/Float;
    //   350: iconst_2
    //   351: swap
    //   352: aastore
    //   353: dup_x2
    //   354: dup_x2
    //   355: pop
    //   356: invokestatic valueOf : (J)Ljava/lang/Long;
    //   359: iconst_1
    //   360: swap
    //   361: aastore
    //   362: dup_x1
    //   363: swap
    //   364: iconst_0
    //   365: swap
    //   366: aastore
    //   367: invokevirtual g : ([Ljava/lang/Object;)V
    //   370: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   373: getfield field_1690 : Lnet/minecraft/class_315;
    //   376: getfield field_1903 : Lnet/minecraft/class_304;
    //   379: invokevirtual method_1434 : ()Z
    //   382: aload #10
    //   384: ifnull -> 455
    //   387: ifeq -> 423
    //   390: goto -> 397
    //   393: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   396: athrow
    //   397: aload_0
    //   398: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   401: getfield field_1724 : Lnet/minecraft/class_746;
    //   404: invokevirtual method_23318 : ()D
    //   407: d2i
    //   408: putfield o : I
    //   411: aload #10
    //   413: ifnonnull -> 482
    //   416: goto -> 423
    //   419: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   422: athrow
    //   423: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   426: getfield field_1724 : Lnet/minecraft/class_746;
    //   429: invokevirtual method_23318 : ()D
    //   432: aload_0
    //   433: getfield o : I
    //   436: i2d
    //   437: dsub
    //   438: invokestatic floor : (D)D
    //   441: invokestatic abs : (D)D
    //   444: ldc2_w 2.0
    //   447: dcmpl
    //   448: goto -> 455
    //   451: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   454: athrow
    //   455: ifle -> 482
    //   458: aload_0
    //   459: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   462: getfield field_1724 : Lnet/minecraft/class_746;
    //   465: invokevirtual method_23318 : ()D
    //   468: invokestatic floor : (D)D
    //   471: d2i
    //   472: putfield o : I
    //   475: goto -> 482
    //   478: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   481: athrow
    //   482: return
    // Exception table:
    //   from	to	target	type
    //   58	71	74	wtf/opal/x5
    //   67	96	99	wtf/opal/x5
    //   78	128	128	wtf/opal/x5
    //   103	149	152	wtf/opal/x5
    //   132	163	166	wtf/opal/x5
    //   156	191	194	wtf/opal/x5
    //   170	207	210	wtf/opal/x5
    //   198	221	224	wtf/opal/x5
    //   214	259	262	wtf/opal/x5
    //   382	390	393	wtf/opal/x5
    //   387	416	419	wtf/opal/x5
    //   397	448	451	wtf/opal/x5
    //   455	475	478	wtf/opal/x5
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/xw.bb : J
    //   3: ldc2_w 122178397260253
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 87343568223609
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 138658983416794
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 115009005983593
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 94133804743502
    //   34: lxor
    //   35: lstore #10
    //   37: pop2
    //   38: invokestatic k : ()[I
    //   41: astore #12
    //   43: iconst_0
    //   44: anewarray java/lang/Object
    //   47: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   50: iconst_0
    //   51: anewarray java/lang/Object
    //   54: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/po;
    //   64: ifnonnull -> 78
    //   67: iconst_0
    //   68: invokestatic exit : (I)V
    //   71: goto -> 78
    //   74: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   77: athrow
    //   78: aload_1
    //   79: iconst_0
    //   80: anewarray java/lang/Object
    //   83: invokevirtual K : ([Ljava/lang/Object;)Z
    //   86: aload #12
    //   88: ifnull -> 111
    //   91: ifeq -> 102
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: return
    //   102: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   105: getfield field_1724 : Lnet/minecraft/class_746;
    //   108: invokevirtual method_24828 : ()Z
    //   111: aload #12
    //   113: ifnull -> 182
    //   116: aload_0
    //   117: getfield U : Z
    //   120: if_icmpeq -> 173
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   133: getfield field_1724 : Lnet/minecraft/class_746;
    //   136: invokevirtual method_24828 : ()Z
    //   139: aload #12
    //   141: ifnull -> 182
    //   144: goto -> 151
    //   147: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: ifeq -> 173
    //   154: goto -> 161
    //   157: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   160: athrow
    //   161: aload_0
    //   162: iconst_1
    //   163: putfield W : Z
    //   166: goto -> 173
    //   169: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   172: athrow
    //   173: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   176: getfield field_1724 : Lnet/minecraft/class_746;
    //   179: invokevirtual method_24828 : ()Z
    //   182: ifeq -> 212
    //   185: aload_0
    //   186: dup
    //   187: getfield d : I
    //   190: iconst_1
    //   191: iadd
    //   192: putfield d : I
    //   195: aload_0
    //   196: iconst_0
    //   197: putfield p : I
    //   200: aload #12
    //   202: ifnonnull -> 234
    //   205: goto -> 212
    //   208: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: aload_0
    //   213: iconst_0
    //   214: putfield d : I
    //   217: aload_0
    //   218: dup
    //   219: getfield p : I
    //   222: iconst_1
    //   223: iadd
    //   224: putfield p : I
    //   227: goto -> 234
    //   230: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   233: athrow
    //   234: aload_0
    //   235: getfield d : I
    //   238: aload #12
    //   240: ifnull -> 411
    //   243: iconst_1
    //   244: if_icmple -> 398
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload_0
    //   255: getfield d : I
    //   258: aload #12
    //   260: ifnull -> 411
    //   263: goto -> 270
    //   266: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   269: athrow
    //   270: sipush #5675
    //   273: ldc2_w 1058592963997587859
    //   276: lload_2
    //   277: lxor
    //   278: <illegal opcode> t : (IJ)I
    //   283: if_icmpgt -> 398
    //   286: goto -> 293
    //   289: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   292: athrow
    //   293: aload_0
    //   294: getfield W : Z
    //   297: aload #12
    //   299: ifnull -> 411
    //   302: goto -> 309
    //   305: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   308: athrow
    //   309: ifeq -> 398
    //   312: goto -> 319
    //   315: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   318: athrow
    //   319: lload #10
    //   321: dconst_0
    //   322: iconst_2
    //   323: anewarray java/lang/Object
    //   326: dup_x2
    //   327: dup_x2
    //   328: pop
    //   329: invokestatic valueOf : (D)Ljava/lang/Double;
    //   332: iconst_1
    //   333: swap
    //   334: aastore
    //   335: dup_x2
    //   336: dup_x2
    //   337: pop
    //   338: invokestatic valueOf : (J)Ljava/lang/Long;
    //   341: iconst_0
    //   342: swap
    //   343: aastore
    //   344: invokestatic k : ([Ljava/lang/Object;)V
    //   347: aload_0
    //   348: getfield d : I
    //   351: aload #12
    //   353: ifnull -> 411
    //   356: goto -> 363
    //   359: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   362: athrow
    //   363: sipush #5675
    //   366: ldc2_w 1058592963997587859
    //   369: lload_2
    //   370: lxor
    //   371: <illegal opcode> t : (IJ)I
    //   376: if_icmpne -> 398
    //   379: goto -> 386
    //   382: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   385: athrow
    //   386: aload_0
    //   387: iconst_0
    //   388: putfield W : Z
    //   391: goto -> 398
    //   394: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   397: athrow
    //   398: aload_0
    //   399: getfield Z : Lwtf/opal/ke;
    //   402: invokevirtual z : ()Ljava/lang/Object;
    //   405: checkcast java/lang/Boolean
    //   408: invokevirtual booleanValue : ()Z
    //   411: aload #12
    //   413: ifnull -> 1553
    //   416: ifeq -> 1537
    //   419: goto -> 426
    //   422: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   425: athrow
    //   426: aload_0
    //   427: getfield X : Lwtf/opal/ky;
    //   430: invokevirtual z : ()Ljava/lang/Object;
    //   433: checkcast wtf/opal/ll
    //   436: invokevirtual ordinal : ()I
    //   439: aload #12
    //   441: ifnull -> 503
    //   444: goto -> 451
    //   447: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   450: athrow
    //   451: tableswitch default -> 1532, 1 -> 484, 2 -> 806, 3 -> 988, 4 -> 1410
    //   480: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   483: athrow
    //   484: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   487: getfield field_1690 : Lnet/minecraft/class_315;
    //   490: getfield field_1903 : Lnet/minecraft/class_304;
    //   493: invokevirtual method_1434 : ()Z
    //   496: goto -> 503
    //   499: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   502: athrow
    //   503: aload #12
    //   505: ifnull -> 534
    //   508: ifne -> 1532
    //   511: goto -> 518
    //   514: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   517: athrow
    //   518: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   521: getfield field_1724 : Lnet/minecraft/class_746;
    //   524: invokevirtual method_24828 : ()Z
    //   527: goto -> 534
    //   530: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   533: athrow
    //   534: aload #12
    //   536: ifnull -> 678
    //   539: ifne -> 669
    //   542: goto -> 549
    //   545: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   548: athrow
    //   549: iconst_0
    //   550: anewarray java/lang/Object
    //   553: invokestatic U : ([Ljava/lang/Object;)F
    //   556: f2d
    //   557: ldc2_w 0.3
    //   560: dcmpl
    //   561: aload #12
    //   563: ifnull -> 678
    //   566: goto -> 573
    //   569: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   572: athrow
    //   573: ifle -> 669
    //   576: goto -> 583
    //   579: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   582: athrow
    //   583: aload_0
    //   584: getfield n : Z
    //   587: aload #12
    //   589: ifnull -> 678
    //   592: goto -> 599
    //   595: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   598: athrow
    //   599: ifeq -> 669
    //   602: goto -> 609
    //   605: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   608: athrow
    //   609: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   612: getfield field_1724 : Lnet/minecraft/class_746;
    //   615: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   618: getfield field_1724 : Lnet/minecraft/class_746;
    //   621: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   624: invokevirtual method_10216 : ()D
    //   627: ldc2_w 0.9
    //   630: dmul
    //   631: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   634: getfield field_1724 : Lnet/minecraft/class_746;
    //   637: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   640: invokevirtual method_10214 : ()D
    //   643: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   646: getfield field_1724 : Lnet/minecraft/class_746;
    //   649: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   652: invokevirtual method_10215 : ()D
    //   655: ldc2_w 0.9
    //   658: dmul
    //   659: invokevirtual method_18800 : (DDD)V
    //   662: goto -> 669
    //   665: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   668: athrow
    //   669: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   672: getfield field_1724 : Lnet/minecraft/class_746;
    //   675: invokevirtual method_24828 : ()Z
    //   678: aload #12
    //   680: ifnull -> 712
    //   683: ifeq -> 1532
    //   686: goto -> 693
    //   689: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   692: athrow
    //   693: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   696: getfield field_1724 : Lnet/minecraft/class_746;
    //   699: getstatic net/minecraft/class_1294.field_5904 : Lnet/minecraft/class_6880;
    //   702: invokevirtual method_6059 : (Lnet/minecraft/class_6880;)Z
    //   705: goto -> 712
    //   708: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   711: athrow
    //   712: aload #12
    //   714: ifnull -> 738
    //   717: ifeq -> 1532
    //   720: goto -> 727
    //   723: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   726: athrow
    //   727: aload_0
    //   728: getfield n : Z
    //   731: goto -> 738
    //   734: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   737: athrow
    //   738: ifne -> 1532
    //   741: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   744: getfield field_1724 : Lnet/minecraft/class_746;
    //   747: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   750: getfield field_1724 : Lnet/minecraft/class_746;
    //   753: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   756: invokevirtual method_10216 : ()D
    //   759: ldc2_w 0.74
    //   762: dmul
    //   763: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   766: getfield field_1724 : Lnet/minecraft/class_746;
    //   769: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   772: invokevirtual method_10214 : ()D
    //   775: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   778: getfield field_1724 : Lnet/minecraft/class_746;
    //   781: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   784: invokevirtual method_10215 : ()D
    //   787: ldc2_w 0.74
    //   790: dmul
    //   791: invokevirtual method_18800 : (DDD)V
    //   794: aload #12
    //   796: ifnonnull -> 1532
    //   799: goto -> 806
    //   802: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   805: athrow
    //   806: aload_0
    //   807: getfield r : I
    //   810: aload #12
    //   812: ifnull -> 864
    //   815: goto -> 822
    //   818: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   821: athrow
    //   822: sipush #5675
    //   825: ldc2_w 1058592963997587859
    //   828: lload_2
    //   829: lxor
    //   830: <illegal opcode> t : (IJ)I
    //   835: if_icmplt -> 1532
    //   838: goto -> 845
    //   841: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   844: athrow
    //   845: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   848: getfield field_1690 : Lnet/minecraft/class_315;
    //   851: getfield field_1903 : Lnet/minecraft/class_304;
    //   854: invokevirtual method_1434 : ()Z
    //   857: goto -> 864
    //   860: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   863: athrow
    //   864: aload #12
    //   866: ifnull -> 904
    //   869: ifne -> 1532
    //   872: goto -> 879
    //   875: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   878: athrow
    //   879: lload #6
    //   881: iconst_1
    //   882: anewarray java/lang/Object
    //   885: dup_x2
    //   886: dup_x2
    //   887: pop
    //   888: invokestatic valueOf : (J)Ljava/lang/Long;
    //   891: iconst_0
    //   892: swap
    //   893: aastore
    //   894: invokestatic I : ([Ljava/lang/Object;)Z
    //   897: goto -> 904
    //   900: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   903: athrow
    //   904: aload #12
    //   906: ifnull -> 938
    //   909: ifeq -> 1532
    //   912: goto -> 919
    //   915: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   918: athrow
    //   919: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   922: getfield field_1724 : Lnet/minecraft/class_746;
    //   925: getstatic net/minecraft/class_1294.field_5904 : Lnet/minecraft/class_6880;
    //   928: invokevirtual method_6059 : (Lnet/minecraft/class_6880;)Z
    //   931: goto -> 938
    //   934: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   937: athrow
    //   938: ifeq -> 951
    //   941: ldc2_w 0.125
    //   944: goto -> 954
    //   947: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   950: athrow
    //   951: ldc2_w 0.145
    //   954: lload #10
    //   956: dup2_x2
    //   957: pop2
    //   958: iconst_2
    //   959: anewarray java/lang/Object
    //   962: dup_x2
    //   963: dup_x2
    //   964: pop
    //   965: invokestatic valueOf : (D)Ljava/lang/Double;
    //   968: iconst_1
    //   969: swap
    //   970: aastore
    //   971: dup_x2
    //   972: dup_x2
    //   973: pop
    //   974: invokestatic valueOf : (J)Ljava/lang/Long;
    //   977: iconst_0
    //   978: swap
    //   979: aastore
    //   980: invokestatic k : ([Ljava/lang/Object;)V
    //   983: aload #12
    //   985: ifnonnull -> 1532
    //   988: iconst_0
    //   989: anewarray java/lang/Object
    //   992: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   995: iconst_0
    //   996: anewarray java/lang/Object
    //   999: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   1002: ldc_w wtf/opal/jm
    //   1005: iconst_1
    //   1006: anewarray java/lang/Object
    //   1009: dup_x1
    //   1010: swap
    //   1011: iconst_0
    //   1012: swap
    //   1013: aastore
    //   1014: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   1017: checkcast wtf/opal/jm
    //   1020: astore #13
    //   1022: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1025: getfield field_1724 : Lnet/minecraft/class_746;
    //   1028: invokevirtual method_24828 : ()Z
    //   1031: aload #12
    //   1033: ifnull -> 1328
    //   1036: ifeq -> 1305
    //   1039: goto -> 1046
    //   1042: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1045: athrow
    //   1046: aload #13
    //   1048: iconst_0
    //   1049: anewarray java/lang/Object
    //   1052: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1055: aload #12
    //   1057: ifnull -> 1328
    //   1060: goto -> 1067
    //   1063: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1066: athrow
    //   1067: ifne -> 1305
    //   1070: goto -> 1077
    //   1073: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1076: athrow
    //   1077: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1080: getfield field_1690 : Lnet/minecraft/class_315;
    //   1083: getfield field_1903 : Lnet/minecraft/class_304;
    //   1086: invokevirtual method_1434 : ()Z
    //   1089: aload #12
    //   1091: ifnull -> 1328
    //   1094: goto -> 1101
    //   1097: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1100: athrow
    //   1101: ifne -> 1305
    //   1104: goto -> 1111
    //   1107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1110: athrow
    //   1111: aload_0
    //   1112: getfield t : I
    //   1115: aload #12
    //   1117: ifnull -> 1328
    //   1120: goto -> 1127
    //   1123: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1126: athrow
    //   1127: ifle -> 1305
    //   1130: goto -> 1137
    //   1133: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1136: athrow
    //   1137: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1140: getfield field_1724 : Lnet/minecraft/class_746;
    //   1143: invokevirtual method_6043 : ()V
    //   1146: lload #6
    //   1148: iconst_1
    //   1149: anewarray java/lang/Object
    //   1152: dup_x2
    //   1153: dup_x2
    //   1154: pop
    //   1155: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1158: iconst_0
    //   1159: swap
    //   1160: aastore
    //   1161: invokestatic I : ([Ljava/lang/Object;)Z
    //   1164: aload #12
    //   1166: ifnull -> 1328
    //   1169: goto -> 1176
    //   1172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1175: athrow
    //   1176: ifeq -> 1305
    //   1179: goto -> 1186
    //   1182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1185: athrow
    //   1186: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1189: getfield field_1724 : Lnet/minecraft/class_746;
    //   1192: invokevirtual method_23318 : ()D
    //   1195: aload_0
    //   1196: getfield o : I
    //   1199: i2d
    //   1200: dsub
    //   1201: invokestatic floor : (D)D
    //   1204: invokestatic abs : (D)D
    //   1207: dconst_0
    //   1208: dcmpl
    //   1209: aload #12
    //   1211: ifnull -> 1328
    //   1214: goto -> 1221
    //   1217: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1220: athrow
    //   1221: ifle -> 1305
    //   1224: goto -> 1231
    //   1227: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1230: athrow
    //   1231: lload #4
    //   1233: iconst_1
    //   1234: anewarray java/lang/Object
    //   1237: dup_x2
    //   1238: dup_x2
    //   1239: pop
    //   1240: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1243: iconst_0
    //   1244: swap
    //   1245: aastore
    //   1246: invokestatic m : ([Ljava/lang/Object;)D
    //   1249: aload_0
    //   1250: getfield t : I
    //   1253: iconst_5
    //   1254: if_icmpge -> 1274
    //   1257: goto -> 1264
    //   1260: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1263: athrow
    //   1264: ldc2_w 0.95
    //   1267: goto -> 1275
    //   1270: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1273: athrow
    //   1274: dconst_1
    //   1275: dmul
    //   1276: lload #10
    //   1278: dup2_x2
    //   1279: pop2
    //   1280: iconst_2
    //   1281: anewarray java/lang/Object
    //   1284: dup_x2
    //   1285: dup_x2
    //   1286: pop
    //   1287: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1290: iconst_1
    //   1291: swap
    //   1292: aastore
    //   1293: dup_x2
    //   1294: dup_x2
    //   1295: pop
    //   1296: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1299: iconst_0
    //   1300: swap
    //   1301: aastore
    //   1302: invokestatic k : ([Ljava/lang/Object;)V
    //   1305: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1308: getfield field_1724 : Lnet/minecraft/class_746;
    //   1311: invokevirtual method_23318 : ()D
    //   1314: aload_0
    //   1315: getfield o : I
    //   1318: i2d
    //   1319: dsub
    //   1320: invokestatic floor : (D)D
    //   1323: invokestatic abs : (D)D
    //   1326: dconst_0
    //   1327: dcmpl
    //   1328: aload #12
    //   1330: ifnull -> 1374
    //   1333: ifne -> 1405
    //   1336: goto -> 1343
    //   1339: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1342: athrow
    //   1343: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1346: aload #12
    //   1348: ifnull -> 1380
    //   1351: goto -> 1358
    //   1354: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1357: athrow
    //   1358: getfield field_1690 : Lnet/minecraft/class_315;
    //   1361: getfield field_1903 : Lnet/minecraft/class_304;
    //   1364: invokevirtual method_1434 : ()Z
    //   1367: goto -> 1374
    //   1370: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1373: athrow
    //   1374: ifne -> 1405
    //   1377: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1380: getfield field_1724 : Lnet/minecraft/class_746;
    //   1383: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1386: getfield field_1724 : Lnet/minecraft/class_746;
    //   1389: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1392: ldc2_w 0.9
    //   1395: dconst_1
    //   1396: ldc2_w 0.9
    //   1399: invokevirtual method_18805 : (DDD)Lnet/minecraft/class_243;
    //   1402: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   1405: aload #12
    //   1407: ifnonnull -> 1532
    //   1410: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1413: getfield field_1724 : Lnet/minecraft/class_746;
    //   1416: invokevirtual method_24828 : ()Z
    //   1419: aload #12
    //   1421: ifnull -> 1488
    //   1424: goto -> 1431
    //   1427: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1430: athrow
    //   1431: ifeq -> 1484
    //   1434: goto -> 1441
    //   1437: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1440: athrow
    //   1441: aload_0
    //   1442: getfield t : I
    //   1445: iconst_2
    //   1446: aload #12
    //   1448: ifnull -> 1501
    //   1451: goto -> 1458
    //   1454: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1457: athrow
    //   1458: if_icmple -> 1484
    //   1461: goto -> 1468
    //   1464: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1467: athrow
    //   1468: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1471: getfield field_1724 : Lnet/minecraft/class_746;
    //   1474: invokevirtual method_6043 : ()V
    //   1477: goto -> 1484
    //   1480: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1483: athrow
    //   1484: aload_0
    //   1485: getfield p : I
    //   1488: sipush #16606
    //   1491: ldc2_w 4510827131775291243
    //   1494: lload_2
    //   1495: lxor
    //   1496: <illegal opcode> t : (IJ)I
    //   1501: aload #12
    //   1503: ifnull -> 1528
    //   1506: if_icmpgt -> 1532
    //   1509: goto -> 1516
    //   1512: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1515: athrow
    //   1516: aload_0
    //   1517: getfield t : I
    //   1520: iconst_2
    //   1521: goto -> 1528
    //   1524: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1527: athrow
    //   1528: if_icmple -> 1532
    //   1531: return
    //   1532: aload #12
    //   1534: ifnonnull -> 1628
    //   1537: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1540: getfield field_1724 : Lnet/minecraft/class_746;
    //   1543: invokevirtual method_24828 : ()Z
    //   1546: goto -> 1553
    //   1549: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1552: athrow
    //   1553: aload #12
    //   1555: ifnull -> 1646
    //   1558: ifeq -> 1628
    //   1561: goto -> 1568
    //   1564: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1567: athrow
    //   1568: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1571: getfield field_1724 : Lnet/minecraft/class_746;
    //   1574: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1577: getfield field_1724 : Lnet/minecraft/class_746;
    //   1580: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1583: invokevirtual method_10216 : ()D
    //   1586: ldc2_w 0.95
    //   1589: dmul
    //   1590: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1593: getfield field_1724 : Lnet/minecraft/class_746;
    //   1596: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1599: invokevirtual method_10214 : ()D
    //   1602: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1605: getfield field_1724 : Lnet/minecraft/class_746;
    //   1608: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1611: invokevirtual method_10215 : ()D
    //   1614: ldc2_w 0.95
    //   1617: dmul
    //   1618: invokevirtual method_18800 : (DDD)V
    //   1621: goto -> 1628
    //   1624: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1627: athrow
    //   1628: lload #6
    //   1630: iconst_1
    //   1631: anewarray java/lang/Object
    //   1634: dup_x2
    //   1635: dup_x2
    //   1636: pop
    //   1637: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1640: iconst_0
    //   1641: swap
    //   1642: aastore
    //   1643: invokestatic I : ([Ljava/lang/Object;)Z
    //   1646: aload #12
    //   1648: ifnull -> 1662
    //   1651: ifeq -> 1730
    //   1654: goto -> 1661
    //   1657: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1660: athrow
    //   1661: iconst_0
    //   1662: istore #13
    //   1664: iload #13
    //   1666: i2d
    //   1667: aload_0
    //   1668: getfield z : Lwtf/opal/kt;
    //   1671: invokevirtual z : ()Ljava/lang/Object;
    //   1674: checkcast java/lang/Double
    //   1677: invokevirtual doubleValue : ()D
    //   1680: dconst_1
    //   1681: dadd
    //   1682: dcmpg
    //   1683: ifge -> 1725
    //   1686: aload_0
    //   1687: lload #8
    //   1689: iconst_1
    //   1690: anewarray java/lang/Object
    //   1693: dup_x2
    //   1694: dup_x2
    //   1695: pop
    //   1696: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1699: iconst_0
    //   1700: swap
    //   1701: aastore
    //   1702: invokevirtual y : ([Ljava/lang/Object;)V
    //   1705: iinc #13, 1
    //   1708: aload #12
    //   1710: ifnull -> 1756
    //   1713: aload #12
    //   1715: ifnonnull -> 1664
    //   1718: goto -> 1725
    //   1721: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1724: athrow
    //   1725: aload #12
    //   1727: ifnonnull -> 1756
    //   1730: aload_0
    //   1731: lload #8
    //   1733: iconst_1
    //   1734: anewarray java/lang/Object
    //   1737: dup_x2
    //   1738: dup_x2
    //   1739: pop
    //   1740: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1743: iconst_0
    //   1744: swap
    //   1745: aastore
    //   1746: invokevirtual y : ([Ljava/lang/Object;)V
    //   1749: goto -> 1756
    //   1752: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1755: athrow
    //   1756: aload_0
    //   1757: aload #12
    //   1759: ifnull -> 1815
    //   1762: getfield Z : Lwtf/opal/ke;
    //   1765: invokevirtual z : ()Ljava/lang/Object;
    //   1768: checkcast java/lang/Boolean
    //   1771: invokevirtual booleanValue : ()Z
    //   1774: ifne -> 1814
    //   1777: goto -> 1784
    //   1780: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1783: athrow
    //   1784: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1787: getfield field_1690 : Lnet/minecraft/class_315;
    //   1790: getfield field_1867 : Lnet/minecraft/class_304;
    //   1793: iconst_0
    //   1794: invokevirtual method_23481 : (Z)V
    //   1797: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1800: getfield field_1724 : Lnet/minecraft/class_746;
    //   1803: iconst_0
    //   1804: invokevirtual method_5728 : (Z)V
    //   1807: goto -> 1814
    //   1810: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1813: athrow
    //   1814: aload_0
    //   1815: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1818: getfield field_1724 : Lnet/minecraft/class_746;
    //   1821: invokevirtual method_24828 : ()Z
    //   1824: putfield U : Z
    //   1827: return
    // Exception table:
    //   from	to	target	type
    //   43	71	74	wtf/opal/x5
    //   78	94	97	wtf/opal/x5
    //   111	123	126	wtf/opal/x5
    //   116	144	147	wtf/opal/x5
    //   130	154	157	wtf/opal/x5
    //   151	166	169	wtf/opal/x5
    //   182	205	208	wtf/opal/x5
    //   185	227	230	wtf/opal/x5
    //   234	247	250	wtf/opal/x5
    //   243	263	266	wtf/opal/x5
    //   254	286	289	wtf/opal/x5
    //   270	302	305	wtf/opal/x5
    //   293	312	315	wtf/opal/x5
    //   309	356	359	wtf/opal/x5
    //   319	379	382	wtf/opal/x5
    //   363	391	394	wtf/opal/x5
    //   411	419	422	wtf/opal/x5
    //   416	444	447	wtf/opal/x5
    //   426	480	480	wtf/opal/x5
    //   451	496	499	wtf/opal/x5
    //   503	511	514	wtf/opal/x5
    //   508	527	530	wtf/opal/x5
    //   534	542	545	wtf/opal/x5
    //   539	566	569	wtf/opal/x5
    //   549	576	579	wtf/opal/x5
    //   573	592	595	wtf/opal/x5
    //   583	602	605	wtf/opal/x5
    //   599	662	665	wtf/opal/x5
    //   678	686	689	wtf/opal/x5
    //   683	705	708	wtf/opal/x5
    //   712	720	723	wtf/opal/x5
    //   717	731	734	wtf/opal/x5
    //   738	799	802	wtf/opal/x5
    //   741	815	818	wtf/opal/x5
    //   806	838	841	wtf/opal/x5
    //   822	857	860	wtf/opal/x5
    //   864	872	875	wtf/opal/x5
    //   869	897	900	wtf/opal/x5
    //   904	912	915	wtf/opal/x5
    //   909	931	934	wtf/opal/x5
    //   938	947	947	wtf/opal/x5
    //   1022	1039	1042	wtf/opal/x5
    //   1036	1060	1063	wtf/opal/x5
    //   1046	1070	1073	wtf/opal/x5
    //   1067	1094	1097	wtf/opal/x5
    //   1077	1104	1107	wtf/opal/x5
    //   1101	1120	1123	wtf/opal/x5
    //   1111	1130	1133	wtf/opal/x5
    //   1127	1169	1172	wtf/opal/x5
    //   1137	1179	1182	wtf/opal/x5
    //   1176	1214	1217	wtf/opal/x5
    //   1186	1224	1227	wtf/opal/x5
    //   1221	1257	1260	wtf/opal/x5
    //   1231	1270	1270	wtf/opal/x5
    //   1328	1336	1339	wtf/opal/x5
    //   1333	1351	1354	wtf/opal/x5
    //   1343	1367	1370	wtf/opal/x5
    //   1405	1424	1427	wtf/opal/x5
    //   1410	1434	1437	wtf/opal/x5
    //   1431	1451	1454	wtf/opal/x5
    //   1441	1461	1464	wtf/opal/x5
    //   1458	1477	1480	wtf/opal/x5
    //   1501	1509	1512	wtf/opal/x5
    //   1506	1521	1524	wtf/opal/x5
    //   1532	1546	1549	wtf/opal/x5
    //   1553	1561	1564	wtf/opal/x5
    //   1558	1621	1624	wtf/opal/x5
    //   1646	1654	1657	wtf/opal/x5
    //   1686	1718	1721	wtf/opal/x5
    //   1725	1749	1752	wtf/opal/x5
    //   1756	1777	1780	wtf/opal/x5
    //   1762	1807	1810	wtf/opal/x5
  }
  
  public static void X(int[] paramArrayOfint) {
    q = paramArrayOfint;
  }
  
  public static int[] k() {
    return q;
  }
  
  static {
    long l = bb ^ 0x6EAB07064042L;
    X(new int[2]);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[18];
    boolean bool = false;
    String str;
    int i = (str = "¸æ¡GY%31êå¨µÕî7¼(dibb£®\nLµ\003%ë\030º[§>§ó³æî\013tåYÐºåî±\020¡ /ÄÃåµ\036\016éY02T\020\003¯×­WÐö!î=ÏM\030B¾0çV\036}Å£L\0208õÛÁ×ß«âVI@ÈLwQmQèÒ\000\024ÙèX\004´UJ-?µÞ¨tb\004#O`ÆÁò°G\000¦ãÌæ\004:\006\006Þ ¾K\005è0H\fiamn\030îsÊ)?¬b:»À/;N\025KLIÊ\020/÷o,°/lQt x^dßp-sKO\031'_Êé 1YìêS)WÅõñ'4@Ñ\020Úê`söéù³\005J¨ïsê~ á-´ÀgØ¬ÒÏ(æªpo\036¡§JÇ\036SÏ³9±ÌÀSÄ\030X2ÂÝYjÓv+¡¸ÇD¤çM¹ú \020\035àÅvqeÅÀ¶jßmD±0ùÞ*â~ê÷Yóâ\brA\030F43\027Í&RY¯cvÍP÷CÃ\027Ôç¸X@í\030Ú1\005\026¯ï\006Î Qsá1ÉV`àÅ\034¯(ATkI%EÀ³Üãäâ,òm·ç?¬ÏÍöO5Ö$³Ã)ä\003åÇñ#\004").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4F75;
    if (db[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])eb.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          eb.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xw", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = cb[i].getBytes("ISO-8859-1");
      db[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return db[i];
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
    //   66: ldc_w 'wtf/opal/xw'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x433C;
    if (hb[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = gb[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])ib.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          ib.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xw", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      hb[i] = Integer.valueOf(j);
    } 
    return hb[i].intValue();
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
    //   66: ldc_w 'wtf/opal/xw'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */