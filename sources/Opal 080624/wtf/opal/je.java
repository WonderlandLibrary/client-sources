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
import net.minecraft.class_1306;
import net.minecraft.class_4587;
import net.minecraft.class_742;
import net.minecraft.class_7833;

public final class je extends d {
  private final ke n;
  
  private final ke r;
  
  private final ke D;
  
  private final ke q;
  
  private final ke M;
  
  private final ke J;
  
  private final kt A;
  
  private final ky<bd> g;
  
  private final ky<x0> a;
  
  private final kt s;
  
  private final kt w;
  
  private final kt B;
  
  private final kt v;
  
  private final kt f;
  
  private final kt u;
  
  private final kt P;
  
  private final kt x;
  
  private final kt T;
  
  private final kt p;
  
  private final kt Z;
  
  private final kt W;
  
  private boolean d;
  
  private final gm<b6> E;
  
  private final gm<d4> k;
  
  private static final long b = on.a(-8422253278184081437L, 561220159843957682L, MethodHandles.lookup().lookupClass()).a(36688047681160L);
  
  private static final String[] l;
  
  private static final String[] m;
  
  private static final Map o = new HashMap<>(13);
  
  private static final long[] t;
  
  private static final Integer[] y;
  
  private static final Map z;
  
  public je(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/je.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 40025628983370
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 109384890872630
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 84674037426787
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #15751
    //   32: ldc2_w 5485923990192446997
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #13420
    //   47: ldc2_w 4125549736144689128
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/ke
    //   67: dup
    //   68: sipush #19509
    //   71: ldc2_w 3708025544932339624
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   81: iconst_1
    //   82: invokespecial <init> : (Ljava/lang/String;Z)V
    //   85: putfield n : Lwtf/opal/ke;
    //   88: aload_0
    //   89: new wtf/opal/ke
    //   92: dup
    //   93: sipush #25189
    //   96: ldc2_w 1985130849479197154
    //   99: lload_1
    //   100: lxor
    //   101: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   106: iconst_1
    //   107: invokespecial <init> : (Ljava/lang/String;Z)V
    //   110: putfield r : Lwtf/opal/ke;
    //   113: aload_0
    //   114: new wtf/opal/ke
    //   117: dup
    //   118: sipush #177
    //   121: ldc2_w 6539769969520108326
    //   124: lload_1
    //   125: lxor
    //   126: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   131: iconst_1
    //   132: invokespecial <init> : (Ljava/lang/String;Z)V
    //   135: putfield D : Lwtf/opal/ke;
    //   138: aload_0
    //   139: new wtf/opal/ke
    //   142: dup
    //   143: sipush #9940
    //   146: ldc2_w 3436699247378187584
    //   149: lload_1
    //   150: lxor
    //   151: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   156: iconst_1
    //   157: invokespecial <init> : (Ljava/lang/String;Z)V
    //   160: putfield q : Lwtf/opal/ke;
    //   163: aload_0
    //   164: new wtf/opal/ke
    //   167: dup
    //   168: sipush #423
    //   171: ldc2_w 7531418345744851495
    //   174: lload_1
    //   175: lxor
    //   176: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   181: iconst_0
    //   182: invokespecial <init> : (Ljava/lang/String;Z)V
    //   185: putfield M : Lwtf/opal/ke;
    //   188: aload_0
    //   189: new wtf/opal/ke
    //   192: dup
    //   193: sipush #28195
    //   196: ldc2_w 5601570362059427254
    //   199: lload_1
    //   200: lxor
    //   201: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   206: iconst_0
    //   207: invokespecial <init> : (Ljava/lang/String;Z)V
    //   210: putfield J : Lwtf/opal/ke;
    //   213: aload_0
    //   214: new wtf/opal/kt
    //   217: dup
    //   218: sipush #18732
    //   221: ldc2_w 9079109394321189564
    //   224: lload_1
    //   225: lxor
    //   226: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   231: dconst_1
    //   232: dconst_1
    //   233: ldc2_w 10.0
    //   236: ldc2_w 0.5
    //   239: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   242: putfield A : Lwtf/opal/kt;
    //   245: aload_0
    //   246: new wtf/opal/ky
    //   249: dup
    //   250: sipush #11286
    //   253: ldc2_w 1604637575014436751
    //   256: lload_1
    //   257: lxor
    //   258: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   263: getstatic wtf/opal/bd.MAIN : Lwtf/opal/bd;
    //   266: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   269: putfield g : Lwtf/opal/ky;
    //   272: aload_0
    //   273: new wtf/opal/ky
    //   276: dup
    //   277: sipush #18104
    //   280: ldc2_w 1024196232369218875
    //   283: lload_1
    //   284: lxor
    //   285: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   290: getstatic wtf/opal/x0.SWANK : Lwtf/opal/x0;
    //   293: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   296: putfield a : Lwtf/opal/ky;
    //   299: aload_0
    //   300: new wtf/opal/kt
    //   303: dup
    //   304: sipush #27292
    //   307: ldc2_w 5983046078783785223
    //   310: lload_1
    //   311: lxor
    //   312: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   317: dconst_0
    //   318: ldc2_w -5.0
    //   321: ldc2_w 5.0
    //   324: ldc2_w 0.1
    //   327: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   330: putfield s : Lwtf/opal/kt;
    //   333: aload_0
    //   334: new wtf/opal/kt
    //   337: dup
    //   338: sipush #13878
    //   341: ldc2_w 3087398899889853866
    //   344: lload_1
    //   345: lxor
    //   346: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   351: dconst_0
    //   352: ldc2_w -5.0
    //   355: ldc2_w 5.0
    //   358: ldc2_w 0.1
    //   361: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   364: putfield w : Lwtf/opal/kt;
    //   367: aload_0
    //   368: new wtf/opal/kt
    //   371: dup
    //   372: sipush #32488
    //   375: ldc2_w 1132187921545665904
    //   378: lload_1
    //   379: lxor
    //   380: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   385: dconst_0
    //   386: ldc2_w -5.0
    //   389: ldc2_w 5.0
    //   392: ldc2_w 0.1
    //   395: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   398: putfield B : Lwtf/opal/kt;
    //   401: aload_0
    //   402: new wtf/opal/kt
    //   405: dup
    //   406: sipush #14892
    //   409: ldc2_w 3829025024861231533
    //   412: lload_1
    //   413: lxor
    //   414: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   419: dconst_0
    //   420: ldc2_w -50.0
    //   423: ldc2_w 50.0
    //   426: dconst_1
    //   427: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   430: putfield v : Lwtf/opal/kt;
    //   433: aload_0
    //   434: new wtf/opal/kt
    //   437: dup
    //   438: sipush #21773
    //   441: ldc2_w 4672986707558086302
    //   444: lload_1
    //   445: lxor
    //   446: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   451: dconst_0
    //   452: ldc2_w -50.0
    //   455: ldc2_w 50.0
    //   458: dconst_1
    //   459: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   462: putfield f : Lwtf/opal/kt;
    //   465: aload_0
    //   466: new wtf/opal/kt
    //   469: dup
    //   470: sipush #18558
    //   473: ldc2_w 4076757101599632380
    //   476: lload_1
    //   477: lxor
    //   478: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   483: dconst_0
    //   484: ldc2_w -50.0
    //   487: ldc2_w 50.0
    //   490: dconst_1
    //   491: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   494: putfield u : Lwtf/opal/kt;
    //   497: aload_0
    //   498: new wtf/opal/kt
    //   501: dup
    //   502: sipush #31707
    //   505: ldc2_w 4486675326527248478
    //   508: lload_1
    //   509: lxor
    //   510: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   515: dconst_0
    //   516: ldc2_w -5.0
    //   519: ldc2_w 5.0
    //   522: ldc2_w 0.1
    //   525: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   528: putfield P : Lwtf/opal/kt;
    //   531: aload_0
    //   532: new wtf/opal/kt
    //   535: dup
    //   536: sipush #9163
    //   539: ldc2_w 7740974450921226321
    //   542: lload_1
    //   543: lxor
    //   544: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   549: dconst_0
    //   550: ldc2_w -5.0
    //   553: ldc2_w 5.0
    //   556: ldc2_w 0.1
    //   559: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   562: putfield x : Lwtf/opal/kt;
    //   565: aload_0
    //   566: new wtf/opal/kt
    //   569: dup
    //   570: sipush #31918
    //   573: ldc2_w 1226576346445619007
    //   576: lload_1
    //   577: lxor
    //   578: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   583: dconst_0
    //   584: ldc2_w -5.0
    //   587: ldc2_w 5.0
    //   590: ldc2_w 0.1
    //   593: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   596: putfield T : Lwtf/opal/kt;
    //   599: aload_0
    //   600: new wtf/opal/kt
    //   603: dup
    //   604: sipush #16802
    //   607: ldc2_w 6336961983054029372
    //   610: lload_1
    //   611: lxor
    //   612: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   617: dconst_0
    //   618: ldc2_w -50.0
    //   621: ldc2_w 50.0
    //   624: dconst_1
    //   625: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   628: putfield p : Lwtf/opal/kt;
    //   631: aload_0
    //   632: new wtf/opal/kt
    //   635: dup
    //   636: sipush #9523
    //   639: ldc2_w 4171158400085182117
    //   642: lload_1
    //   643: lxor
    //   644: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   649: dconst_0
    //   650: ldc2_w -50.0
    //   653: ldc2_w 50.0
    //   656: dconst_1
    //   657: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   660: putfield Z : Lwtf/opal/kt;
    //   663: aload_0
    //   664: new wtf/opal/kt
    //   667: dup
    //   668: sipush #1126
    //   671: ldc2_w 5008681839262099449
    //   674: lload_1
    //   675: lxor
    //   676: <illegal opcode> a : (IJ)Ljava/lang/String;
    //   681: dconst_0
    //   682: ldc2_w -50.0
    //   685: ldc2_w 50.0
    //   688: dconst_1
    //   689: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   692: putfield W : Lwtf/opal/kt;
    //   695: aload_0
    //   696: aload_0
    //   697: <illegal opcode> H : (Lwtf/opal/je;)Lwtf/opal/gm;
    //   702: putfield E : Lwtf/opal/gm;
    //   705: aload_0
    //   706: aload_0
    //   707: <illegal opcode> H : (Lwtf/opal/je;)Lwtf/opal/gm;
    //   712: putfield k : Lwtf/opal/gm;
    //   715: aload_0
    //   716: getfield s : Lwtf/opal/kt;
    //   719: aload_0
    //   720: getfield g : Lwtf/opal/ky;
    //   723: aload_0
    //   724: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   729: lload_3
    //   730: dup2_x1
    //   731: pop2
    //   732: iconst_3
    //   733: anewarray java/lang/Object
    //   736: dup_x1
    //   737: swap
    //   738: iconst_2
    //   739: swap
    //   740: aastore
    //   741: dup_x2
    //   742: dup_x2
    //   743: pop
    //   744: invokestatic valueOf : (J)Ljava/lang/Long;
    //   747: iconst_1
    //   748: swap
    //   749: aastore
    //   750: dup_x1
    //   751: swap
    //   752: iconst_0
    //   753: swap
    //   754: aastore
    //   755: invokevirtual C : ([Ljava/lang/Object;)V
    //   758: aload_0
    //   759: getfield w : Lwtf/opal/kt;
    //   762: aload_0
    //   763: getfield g : Lwtf/opal/ky;
    //   766: aload_0
    //   767: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   772: lload_3
    //   773: dup2_x1
    //   774: pop2
    //   775: iconst_3
    //   776: anewarray java/lang/Object
    //   779: dup_x1
    //   780: swap
    //   781: iconst_2
    //   782: swap
    //   783: aastore
    //   784: dup_x2
    //   785: dup_x2
    //   786: pop
    //   787: invokestatic valueOf : (J)Ljava/lang/Long;
    //   790: iconst_1
    //   791: swap
    //   792: aastore
    //   793: dup_x1
    //   794: swap
    //   795: iconst_0
    //   796: swap
    //   797: aastore
    //   798: invokevirtual C : ([Ljava/lang/Object;)V
    //   801: aload_0
    //   802: getfield B : Lwtf/opal/kt;
    //   805: aload_0
    //   806: getfield g : Lwtf/opal/ky;
    //   809: aload_0
    //   810: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   815: lload_3
    //   816: dup2_x1
    //   817: pop2
    //   818: iconst_3
    //   819: anewarray java/lang/Object
    //   822: dup_x1
    //   823: swap
    //   824: iconst_2
    //   825: swap
    //   826: aastore
    //   827: dup_x2
    //   828: dup_x2
    //   829: pop
    //   830: invokestatic valueOf : (J)Ljava/lang/Long;
    //   833: iconst_1
    //   834: swap
    //   835: aastore
    //   836: dup_x1
    //   837: swap
    //   838: iconst_0
    //   839: swap
    //   840: aastore
    //   841: invokevirtual C : ([Ljava/lang/Object;)V
    //   844: aload_0
    //   845: getfield v : Lwtf/opal/kt;
    //   848: aload_0
    //   849: getfield g : Lwtf/opal/ky;
    //   852: aload_0
    //   853: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   858: lload_3
    //   859: dup2_x1
    //   860: pop2
    //   861: iconst_3
    //   862: anewarray java/lang/Object
    //   865: dup_x1
    //   866: swap
    //   867: iconst_2
    //   868: swap
    //   869: aastore
    //   870: dup_x2
    //   871: dup_x2
    //   872: pop
    //   873: invokestatic valueOf : (J)Ljava/lang/Long;
    //   876: iconst_1
    //   877: swap
    //   878: aastore
    //   879: dup_x1
    //   880: swap
    //   881: iconst_0
    //   882: swap
    //   883: aastore
    //   884: invokevirtual C : ([Ljava/lang/Object;)V
    //   887: aload_0
    //   888: getfield f : Lwtf/opal/kt;
    //   891: aload_0
    //   892: getfield g : Lwtf/opal/ky;
    //   895: aload_0
    //   896: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   901: lload_3
    //   902: dup2_x1
    //   903: pop2
    //   904: iconst_3
    //   905: anewarray java/lang/Object
    //   908: dup_x1
    //   909: swap
    //   910: iconst_2
    //   911: swap
    //   912: aastore
    //   913: dup_x2
    //   914: dup_x2
    //   915: pop
    //   916: invokestatic valueOf : (J)Ljava/lang/Long;
    //   919: iconst_1
    //   920: swap
    //   921: aastore
    //   922: dup_x1
    //   923: swap
    //   924: iconst_0
    //   925: swap
    //   926: aastore
    //   927: invokevirtual C : ([Ljava/lang/Object;)V
    //   930: aload_0
    //   931: getfield u : Lwtf/opal/kt;
    //   934: aload_0
    //   935: getfield g : Lwtf/opal/ky;
    //   938: aload_0
    //   939: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   944: lload_3
    //   945: dup2_x1
    //   946: pop2
    //   947: iconst_3
    //   948: anewarray java/lang/Object
    //   951: dup_x1
    //   952: swap
    //   953: iconst_2
    //   954: swap
    //   955: aastore
    //   956: dup_x2
    //   957: dup_x2
    //   958: pop
    //   959: invokestatic valueOf : (J)Ljava/lang/Long;
    //   962: iconst_1
    //   963: swap
    //   964: aastore
    //   965: dup_x1
    //   966: swap
    //   967: iconst_0
    //   968: swap
    //   969: aastore
    //   970: invokevirtual C : ([Ljava/lang/Object;)V
    //   973: invokestatic S : ()Ljava/lang/String;
    //   976: aload_0
    //   977: getfield P : Lwtf/opal/kt;
    //   980: aload_0
    //   981: getfield g : Lwtf/opal/ky;
    //   984: aload_0
    //   985: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   990: lload_3
    //   991: dup2_x1
    //   992: pop2
    //   993: iconst_3
    //   994: anewarray java/lang/Object
    //   997: dup_x1
    //   998: swap
    //   999: iconst_2
    //   1000: swap
    //   1001: aastore
    //   1002: dup_x2
    //   1003: dup_x2
    //   1004: pop
    //   1005: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1008: iconst_1
    //   1009: swap
    //   1010: aastore
    //   1011: dup_x1
    //   1012: swap
    //   1013: iconst_0
    //   1014: swap
    //   1015: aastore
    //   1016: invokevirtual C : ([Ljava/lang/Object;)V
    //   1019: aload_0
    //   1020: getfield x : Lwtf/opal/kt;
    //   1023: aload_0
    //   1024: getfield g : Lwtf/opal/ky;
    //   1027: aload_0
    //   1028: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   1033: lload_3
    //   1034: dup2_x1
    //   1035: pop2
    //   1036: iconst_3
    //   1037: anewarray java/lang/Object
    //   1040: dup_x1
    //   1041: swap
    //   1042: iconst_2
    //   1043: swap
    //   1044: aastore
    //   1045: dup_x2
    //   1046: dup_x2
    //   1047: pop
    //   1048: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1051: iconst_1
    //   1052: swap
    //   1053: aastore
    //   1054: dup_x1
    //   1055: swap
    //   1056: iconst_0
    //   1057: swap
    //   1058: aastore
    //   1059: invokevirtual C : ([Ljava/lang/Object;)V
    //   1062: aload_0
    //   1063: getfield T : Lwtf/opal/kt;
    //   1066: aload_0
    //   1067: getfield g : Lwtf/opal/ky;
    //   1070: aload_0
    //   1071: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   1076: lload_3
    //   1077: dup2_x1
    //   1078: pop2
    //   1079: iconst_3
    //   1080: anewarray java/lang/Object
    //   1083: dup_x1
    //   1084: swap
    //   1085: iconst_2
    //   1086: swap
    //   1087: aastore
    //   1088: dup_x2
    //   1089: dup_x2
    //   1090: pop
    //   1091: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1094: iconst_1
    //   1095: swap
    //   1096: aastore
    //   1097: dup_x1
    //   1098: swap
    //   1099: iconst_0
    //   1100: swap
    //   1101: aastore
    //   1102: invokevirtual C : ([Ljava/lang/Object;)V
    //   1105: aload_0
    //   1106: getfield p : Lwtf/opal/kt;
    //   1109: aload_0
    //   1110: getfield g : Lwtf/opal/ky;
    //   1113: aload_0
    //   1114: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   1119: lload_3
    //   1120: dup2_x1
    //   1121: pop2
    //   1122: iconst_3
    //   1123: anewarray java/lang/Object
    //   1126: dup_x1
    //   1127: swap
    //   1128: iconst_2
    //   1129: swap
    //   1130: aastore
    //   1131: dup_x2
    //   1132: dup_x2
    //   1133: pop
    //   1134: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1137: iconst_1
    //   1138: swap
    //   1139: aastore
    //   1140: dup_x1
    //   1141: swap
    //   1142: iconst_0
    //   1143: swap
    //   1144: aastore
    //   1145: invokevirtual C : ([Ljava/lang/Object;)V
    //   1148: aload_0
    //   1149: getfield Z : Lwtf/opal/kt;
    //   1152: aload_0
    //   1153: getfield g : Lwtf/opal/ky;
    //   1156: aload_0
    //   1157: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   1162: lload_3
    //   1163: dup2_x1
    //   1164: pop2
    //   1165: iconst_3
    //   1166: anewarray java/lang/Object
    //   1169: dup_x1
    //   1170: swap
    //   1171: iconst_2
    //   1172: swap
    //   1173: aastore
    //   1174: dup_x2
    //   1175: dup_x2
    //   1176: pop
    //   1177: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1180: iconst_1
    //   1181: swap
    //   1182: aastore
    //   1183: dup_x1
    //   1184: swap
    //   1185: iconst_0
    //   1186: swap
    //   1187: aastore
    //   1188: invokevirtual C : ([Ljava/lang/Object;)V
    //   1191: astore #9
    //   1193: aload_0
    //   1194: getfield W : Lwtf/opal/kt;
    //   1197: aload_0
    //   1198: getfield g : Lwtf/opal/ky;
    //   1201: aload_0
    //   1202: <illegal opcode> test : (Lwtf/opal/je;)Ljava/util/function/Predicate;
    //   1207: lload_3
    //   1208: dup2_x1
    //   1209: pop2
    //   1210: iconst_3
    //   1211: anewarray java/lang/Object
    //   1214: dup_x1
    //   1215: swap
    //   1216: iconst_2
    //   1217: swap
    //   1218: aastore
    //   1219: dup_x2
    //   1220: dup_x2
    //   1221: pop
    //   1222: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1225: iconst_1
    //   1226: swap
    //   1227: aastore
    //   1228: dup_x1
    //   1229: swap
    //   1230: iconst_0
    //   1231: swap
    //   1232: aastore
    //   1233: invokevirtual C : ([Ljava/lang/Object;)V
    //   1236: aload_0
    //   1237: sipush #13570
    //   1240: ldc2_w 6058578445365483013
    //   1243: lload_1
    //   1244: lxor
    //   1245: <illegal opcode> y : (IJ)I
    //   1250: anewarray wtf/opal/k3
    //   1253: dup
    //   1254: iconst_0
    //   1255: aload_0
    //   1256: getfield n : Lwtf/opal/ke;
    //   1259: aastore
    //   1260: dup
    //   1261: iconst_1
    //   1262: aload_0
    //   1263: getfield r : Lwtf/opal/ke;
    //   1266: aastore
    //   1267: dup
    //   1268: iconst_2
    //   1269: aload_0
    //   1270: getfield D : Lwtf/opal/ke;
    //   1273: aastore
    //   1274: dup
    //   1275: iconst_3
    //   1276: aload_0
    //   1277: getfield q : Lwtf/opal/ke;
    //   1280: aastore
    //   1281: dup
    //   1282: iconst_4
    //   1283: aload_0
    //   1284: getfield M : Lwtf/opal/ke;
    //   1287: aastore
    //   1288: dup
    //   1289: iconst_5
    //   1290: aload_0
    //   1291: getfield J : Lwtf/opal/ke;
    //   1294: aastore
    //   1295: dup
    //   1296: sipush #4728
    //   1299: ldc2_w 557769944995246457
    //   1302: lload_1
    //   1303: lxor
    //   1304: <illegal opcode> y : (IJ)I
    //   1309: aload_0
    //   1310: getfield A : Lwtf/opal/kt;
    //   1313: aastore
    //   1314: dup
    //   1315: sipush #24309
    //   1318: ldc2_w 5601399215899540983
    //   1321: lload_1
    //   1322: lxor
    //   1323: <illegal opcode> y : (IJ)I
    //   1328: aload_0
    //   1329: getfield a : Lwtf/opal/ky;
    //   1332: aastore
    //   1333: dup
    //   1334: sipush #6680
    //   1337: ldc2_w 8851912437222324496
    //   1340: lload_1
    //   1341: lxor
    //   1342: <illegal opcode> y : (IJ)I
    //   1347: aload_0
    //   1348: getfield g : Lwtf/opal/ky;
    //   1351: aastore
    //   1352: dup
    //   1353: sipush #18163
    //   1356: ldc2_w 2632861647814878718
    //   1359: lload_1
    //   1360: lxor
    //   1361: <illegal opcode> y : (IJ)I
    //   1366: aload_0
    //   1367: getfield s : Lwtf/opal/kt;
    //   1370: aastore
    //   1371: dup
    //   1372: sipush #2158
    //   1375: ldc2_w 5814961711338597224
    //   1378: lload_1
    //   1379: lxor
    //   1380: <illegal opcode> y : (IJ)I
    //   1385: aload_0
    //   1386: getfield w : Lwtf/opal/kt;
    //   1389: aastore
    //   1390: dup
    //   1391: sipush #30477
    //   1394: ldc2_w 2757954534449009668
    //   1397: lload_1
    //   1398: lxor
    //   1399: <illegal opcode> y : (IJ)I
    //   1404: aload_0
    //   1405: getfield B : Lwtf/opal/kt;
    //   1408: aastore
    //   1409: dup
    //   1410: sipush #3966
    //   1413: ldc2_w 2135891768270367860
    //   1416: lload_1
    //   1417: lxor
    //   1418: <illegal opcode> y : (IJ)I
    //   1423: aload_0
    //   1424: getfield v : Lwtf/opal/kt;
    //   1427: aastore
    //   1428: dup
    //   1429: sipush #25508
    //   1432: ldc2_w 3302638057579167915
    //   1435: lload_1
    //   1436: lxor
    //   1437: <illegal opcode> y : (IJ)I
    //   1442: aload_0
    //   1443: getfield f : Lwtf/opal/kt;
    //   1446: aastore
    //   1447: dup
    //   1448: sipush #6617
    //   1451: ldc2_w 7270149214591302357
    //   1454: lload_1
    //   1455: lxor
    //   1456: <illegal opcode> y : (IJ)I
    //   1461: aload_0
    //   1462: getfield u : Lwtf/opal/kt;
    //   1465: aastore
    //   1466: dup
    //   1467: sipush #28960
    //   1470: ldc2_w 7855727026607936043
    //   1473: lload_1
    //   1474: lxor
    //   1475: <illegal opcode> y : (IJ)I
    //   1480: aload_0
    //   1481: getfield P : Lwtf/opal/kt;
    //   1484: aastore
    //   1485: dup
    //   1486: sipush #7081
    //   1489: ldc2_w 2939020369982797993
    //   1492: lload_1
    //   1493: lxor
    //   1494: <illegal opcode> y : (IJ)I
    //   1499: aload_0
    //   1500: getfield x : Lwtf/opal/kt;
    //   1503: aastore
    //   1504: dup
    //   1505: sipush #22624
    //   1508: ldc2_w 1600978213137939299
    //   1511: lload_1
    //   1512: lxor
    //   1513: <illegal opcode> y : (IJ)I
    //   1518: aload_0
    //   1519: getfield T : Lwtf/opal/kt;
    //   1522: aastore
    //   1523: dup
    //   1524: sipush #2950
    //   1527: ldc2_w 9172633346796824707
    //   1530: lload_1
    //   1531: lxor
    //   1532: <illegal opcode> y : (IJ)I
    //   1537: aload_0
    //   1538: getfield p : Lwtf/opal/kt;
    //   1541: aastore
    //   1542: dup
    //   1543: sipush #22685
    //   1546: ldc2_w 5289701406018468741
    //   1549: lload_1
    //   1550: lxor
    //   1551: <illegal opcode> y : (IJ)I
    //   1556: aload_0
    //   1557: getfield Z : Lwtf/opal/kt;
    //   1560: aastore
    //   1561: dup
    //   1562: sipush #27893
    //   1565: ldc2_w 354888221717417969
    //   1568: lload_1
    //   1569: lxor
    //   1570: <illegal opcode> y : (IJ)I
    //   1575: aload_0
    //   1576: getfield W : Lwtf/opal/kt;
    //   1579: aastore
    //   1580: lload #5
    //   1582: dup2_x1
    //   1583: pop2
    //   1584: iconst_2
    //   1585: anewarray java/lang/Object
    //   1588: dup_x1
    //   1589: swap
    //   1590: iconst_1
    //   1591: swap
    //   1592: aastore
    //   1593: dup_x2
    //   1594: dup_x2
    //   1595: pop
    //   1596: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1599: iconst_0
    //   1600: swap
    //   1601: aastore
    //   1602: invokevirtual o : ([Ljava/lang/Object;)V
    //   1605: aload #9
    //   1607: ifnull -> 1624
    //   1610: iconst_3
    //   1611: anewarray wtf/opal/d
    //   1614: invokestatic p : ([Lwtf/opal/d;)V
    //   1617: goto -> 1624
    //   1620: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1623: athrow
    //   1624: return
    // Exception table:
    //   from	to	target	type
    //   1193	1617	1620	wtf/opal/x5
  }
  
  private int A(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/je.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic S : ()Ljava/lang/String;
    //   21: astore #4
    //   23: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   26: getfield field_1724 : Lnet/minecraft/class_746;
    //   29: invokestatic method_5576 : (Lnet/minecraft/class_1309;)Z
    //   32: aload #4
    //   34: ifnonnull -> 89
    //   37: ifeq -> 77
    //   40: goto -> 47
    //   43: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   46: athrow
    //   47: sipush #1121
    //   50: ldc2_w 2583897793189705234
    //   53: lload_2
    //   54: lxor
    //   55: <illegal opcode> y : (IJ)I
    //   60: iconst_1
    //   61: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   64: getfield field_1724 : Lnet/minecraft/class_746;
    //   67: invokestatic method_5575 : (Lnet/minecraft/class_1309;)I
    //   70: iadd
    //   71: isub
    //   72: ireturn
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   80: getfield field_1724 : Lnet/minecraft/class_746;
    //   83: getstatic net/minecraft/class_1294.field_5901 : Lnet/minecraft/class_6880;
    //   86: invokevirtual method_6059 : (Lnet/minecraft/class_6880;)Z
    //   89: aload #4
    //   91: ifnonnull -> 155
    //   94: ifeq -> 142
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: sipush #1121
    //   107: ldc2_w 2583897793189705234
    //   110: lload_2
    //   111: lxor
    //   112: <illegal opcode> y : (IJ)I
    //   117: iconst_1
    //   118: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   121: getfield field_1724 : Lnet/minecraft/class_746;
    //   124: getstatic net/minecraft/class_1294.field_5901 : Lnet/minecraft/class_6880;
    //   127: invokevirtual method_6112 : (Lnet/minecraft/class_6880;)Lnet/minecraft/class_1293;
    //   130: invokevirtual method_5578 : ()I
    //   133: iadd
    //   134: iconst_2
    //   135: imul
    //   136: iadd
    //   137: ireturn
    //   138: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   141: athrow
    //   142: sipush #1121
    //   145: ldc2_w 2583897793189705234
    //   148: lload_2
    //   149: lxor
    //   150: <illegal opcode> y : (IJ)I
    //   155: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	40	43	wtf/opal/x5
    //   37	73	73	wtf/opal/x5
    //   89	97	100	wtf/opal/x5
    //   94	138	138	wtf/opal/x5
  }
  
  public void g(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    class_742 class_742 = (class_742)paramArrayOfObject[1];
    class_4587 class_4587 = (class_4587)paramArrayOfObject[2];
    l = b ^ l;
    class_1306 class_1306 = class_742.method_6068();
    try {
    
    } catch (x5 x5) {
      throw a(null);
    } 
    boolean bool = (class_1306 == class_1306.field_6183) ? true : true;
    class_4587.method_46416(-0.04F, 0.05F, 0.04142136F);
    class_4587.method_22907(class_7833.field_40714.rotationDegrees(-275.5F));
    class_4587.method_22907(class_7833.field_40716.rotationDegrees(bool * -51.635F));
    class_4587.method_22907(class_7833.field_40718.rotationDegrees(bool * 106.55F));
  }
  
  public void w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_4587
    //   7: astore #6
    //   9: dup
    //   10: iconst_1
    //   11: aaload
    //   12: checkcast java/lang/Long
    //   15: invokevirtual longValue : ()J
    //   18: lstore_3
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast net/minecraft/class_1306
    //   25: astore #5
    //   27: dup
    //   28: iconst_3
    //   29: aaload
    //   30: checkcast java/lang/Float
    //   33: invokevirtual floatValue : ()F
    //   36: fstore_2
    //   37: pop
    //   38: getstatic wtf/opal/je.b : J
    //   41: lload_3
    //   42: lxor
    //   43: lstore_3
    //   44: invokestatic S : ()Ljava/lang/String;
    //   47: astore #7
    //   49: aload #5
    //   51: getstatic net/minecraft/class_1306.field_6183 : Lnet/minecraft/class_1306;
    //   54: if_acmpne -> 65
    //   57: iconst_1
    //   58: goto -> 66
    //   61: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   64: athrow
    //   65: iconst_m1
    //   66: istore #8
    //   68: fload_2
    //   69: fload_2
    //   70: fmul
    //   71: ldc_w 3.1415927
    //   74: fmul
    //   75: invokestatic method_15374 : (F)F
    //   78: fstore #9
    //   80: fload_2
    //   81: invokestatic method_15355 : (F)F
    //   84: ldc_w 3.1415927
    //   87: fmul
    //   88: invokestatic method_15374 : (F)F
    //   91: fstore #10
    //   93: aload #7
    //   95: lload_3
    //   96: lconst_0
    //   97: lcmp
    //   98: ifle -> 250
    //   101: ifnonnull -> 248
    //   104: aload_0
    //   105: getfield a : Lwtf/opal/ky;
    //   108: invokevirtual z : ()Ljava/lang/Object;
    //   111: checkcast wtf/opal/x0
    //   114: invokevirtual ordinal : ()I
    //   117: tableswitch default -> 539, 0 -> 152, 1 -> 539, 2 -> 259, 3 -> 394
    //   148: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   151: athrow
    //   152: aload #6
    //   154: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   157: iload #8
    //   159: i2f
    //   160: ldc_w 45.0
    //   163: fload #9
    //   165: ldc_w 20.0
    //   168: fmul
    //   169: fadd
    //   170: fmul
    //   171: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   176: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   179: aload #6
    //   181: getstatic net/minecraft/class_7833.field_40718 : Lnet/minecraft/class_7833;
    //   184: iload #8
    //   186: i2f
    //   187: fload #10
    //   189: fmul
    //   190: ldc_w 75.0
    //   193: fmul
    //   194: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   199: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   202: aload #6
    //   204: getstatic net/minecraft/class_7833.field_40714 : Lnet/minecraft/class_7833;
    //   207: fload #10
    //   209: ldc_w -20.0
    //   212: fmul
    //   213: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   218: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   221: aload #6
    //   223: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   226: iload #8
    //   228: i2f
    //   229: ldc_w -45.0
    //   232: fmul
    //   233: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   238: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   241: goto -> 248
    //   244: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: aload #7
    //   250: lload_3
    //   251: lconst_0
    //   252: lcmp
    //   253: ifle -> 384
    //   256: ifnull -> 539
    //   259: aload #6
    //   261: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   264: fload #10
    //   266: ldc_w 32.0
    //   269: fmul
    //   270: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   275: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   278: aload #6
    //   280: getstatic net/minecraft/class_7833.field_40714 : Lnet/minecraft/class_7833;
    //   283: fload #10
    //   285: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   290: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   293: aload #6
    //   295: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   298: iload #8
    //   300: i2f
    //   301: ldc_w 45.0
    //   304: fload #9
    //   306: ldc_w 20.0
    //   309: fmul
    //   310: fadd
    //   311: fmul
    //   312: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   317: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   320: aload #6
    //   322: getstatic net/minecraft/class_7833.field_40718 : Lnet/minecraft/class_7833;
    //   325: iload #8
    //   327: i2f
    //   328: fload #10
    //   330: fmul
    //   331: ldc_w 75.0
    //   334: fmul
    //   335: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   340: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   343: aload #6
    //   345: getstatic net/minecraft/class_7833.field_40714 : Lnet/minecraft/class_7833;
    //   348: fload #10
    //   350: ldc_w -20.0
    //   353: fmul
    //   354: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   359: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   362: aload #6
    //   364: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   367: iload #8
    //   369: i2f
    //   370: ldc_w -45.0
    //   373: fmul
    //   374: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   379: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   382: aload #7
    //   384: ifnull -> 539
    //   387: goto -> 394
    //   390: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   393: athrow
    //   394: aload #6
    //   396: getstatic net/minecraft/class_7833.field_40713 : Lnet/minecraft/class_7833;
    //   399: fload #10
    //   401: ldc_w 30.0
    //   404: fmul
    //   405: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   410: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   413: aload #6
    //   415: getstatic net/minecraft/class_7833.field_40718 : Lnet/minecraft/class_7833;
    //   418: fload #10
    //   420: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   425: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   428: aload #6
    //   430: getstatic net/minecraft/class_7833.field_40715 : Lnet/minecraft/class_7833;
    //   433: fload #10
    //   435: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   440: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   443: aload #6
    //   445: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   448: iload #8
    //   450: i2f
    //   451: ldc_w 45.0
    //   454: fload #9
    //   456: ldc_w 20.0
    //   459: fmul
    //   460: fadd
    //   461: fmul
    //   462: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   467: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   470: aload #6
    //   472: getstatic net/minecraft/class_7833.field_40718 : Lnet/minecraft/class_7833;
    //   475: iload #8
    //   477: i2f
    //   478: fload #10
    //   480: fmul
    //   481: ldc_w 75.0
    //   484: fmul
    //   485: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   490: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   493: aload #6
    //   495: getstatic net/minecraft/class_7833.field_40714 : Lnet/minecraft/class_7833;
    //   498: fload #10
    //   500: ldc_w -20.0
    //   503: fmul
    //   504: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   509: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   512: aload #6
    //   514: getstatic net/minecraft/class_7833.field_40716 : Lnet/minecraft/class_7833;
    //   517: iload #8
    //   519: i2f
    //   520: ldc_w -45.0
    //   523: fmul
    //   524: invokeinterface rotationDegrees : (F)Lorg/joml/Quaternionf;
    //   529: invokevirtual method_22907 : (Lorg/joml/Quaternionf;)V
    //   532: goto -> 539
    //   535: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   538: athrow
    //   539: return
    // Exception table:
    //   from	to	target	type
    //   49	61	61	wtf/opal/x5
    //   93	148	148	wtf/opal/x5
    //   104	241	244	wtf/opal/x5
    //   248	387	390	wtf/opal/x5
    //   259	532	535	wtf/opal/x5
  }
  
  public boolean A(Object[] paramArrayOfObject) {
    return this.d;
  }
  
  public ke w(Object[] paramArrayOfObject) {
    return this.n;
  }
  
  public ke c(Object[] paramArrayOfObject) {
    return this.D;
  }
  
  public ke W(Object[] paramArrayOfObject) {
    return this.q;
  }
  
  public ke m(Object[] paramArrayOfObject) {
    return this.M;
  }
  
  public kt j(Object[] paramArrayOfObject) {
    return this.A;
  }
  
  public kt K(Object[] paramArrayOfObject) {
    return this.s;
  }
  
  public kt t(Object[] paramArrayOfObject) {
    return this.w;
  }
  
  public kt S(Object[] paramArrayOfObject) {
    return this.B;
  }
  
  public kt d(Object[] paramArrayOfObject) {
    return this.v;
  }
  
  public kt O(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public kt L(Object[] paramArrayOfObject) {
    return this.u;
  }
  
  public kt E(Object[] paramArrayOfObject) {
    return this.P;
  }
  
  public kt b(Object[] paramArrayOfObject) {
    return this.x;
  }
  
  public kt Z(Object[] paramArrayOfObject) {
    return this.T;
  }
  
  public kt P(Object[] paramArrayOfObject) {
    return this.p;
  }
  
  public kt r(Object[] paramArrayOfObject) {
    return this.Z;
  }
  
  public kt h(Object[] paramArrayOfObject) {
    return this.W;
  }
  
  public ke z(Object[] paramArrayOfObject) {
    return this.J;
  }
  
  public boolean o(Object[] paramArrayOfObject) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((x0)this.a.z()).toString();
  }
  
  private void lambda$new$13(d4 paramd4) {
    this.d = false;
  }
  
  private void lambda$new$12(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/je.b : J
    //   3: ldc2_w 100276876558930
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 57401681339173
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: astore #6
    //   22: aload_1
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual W : ([Ljava/lang/Object;)Z
    //   30: aload #6
    //   32: ifnonnull -> 149
    //   35: ifeq -> 129
    //   38: goto -> 45
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: aload_0
    //   46: getfield d : Z
    //   49: ifne -> 803
    //   52: goto -> 59
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   62: getfield field_1724 : Lnet/minecraft/class_746;
    //   65: aload #6
    //   67: ifnonnull -> 100
    //   70: goto -> 77
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: ifnull -> 803
    //   80: goto -> 87
    //   83: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   86: athrow
    //   87: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   90: getfield field_1724 : Lnet/minecraft/class_746;
    //   93: goto -> 100
    //   96: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: invokevirtual method_6079 : ()Lnet/minecraft/class_1799;
    //   103: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   106: instanceof net/minecraft/class_1819
    //   109: ifeq -> 803
    //   112: aload_0
    //   113: iconst_1
    //   114: putfield d : Z
    //   117: aload #6
    //   119: ifnull -> 803
    //   122: goto -> 129
    //   125: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   128: athrow
    //   129: aload_0
    //   130: getfield r : Lwtf/opal/ke;
    //   133: invokevirtual z : ()Ljava/lang/Object;
    //   136: checkcast java/lang/Boolean
    //   139: invokevirtual booleanValue : ()Z
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: ifeq -> 803
    //   152: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   155: aload #6
    //   157: ifnonnull -> 190
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: getfield field_1724 : Lnet/minecraft/class_746;
    //   170: ifnull -> 236
    //   173: goto -> 180
    //   176: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   179: athrow
    //   180: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   183: goto -> 190
    //   186: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   189: athrow
    //   190: aload #6
    //   192: ifnonnull -> 218
    //   195: getfield field_1687 : Lnet/minecraft/class_638;
    //   198: ifnull -> 236
    //   201: goto -> 208
    //   204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   207: athrow
    //   208: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   211: goto -> 218
    //   214: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   217: athrow
    //   218: aload #6
    //   220: ifnonnull -> 244
    //   223: getfield field_1761 : Lnet/minecraft/class_636;
    //   226: ifnonnull -> 241
    //   229: goto -> 236
    //   232: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   235: athrow
    //   236: return
    //   237: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   244: getfield field_1690 : Lnet/minecraft/class_315;
    //   247: getfield field_1886 : Lnet/minecraft/class_304;
    //   250: invokevirtual method_1434 : ()Z
    //   253: aload #6
    //   255: ifnonnull -> 299
    //   258: ifeq -> 803
    //   261: goto -> 268
    //   264: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   267: athrow
    //   268: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   271: aload #6
    //   273: ifnonnull -> 305
    //   276: goto -> 283
    //   279: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: getfield field_1690 : Lnet/minecraft/class_315;
    //   286: getfield field_1904 : Lnet/minecraft/class_304;
    //   289: invokevirtual method_1434 : ()Z
    //   292: goto -> 299
    //   295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: ifeq -> 803
    //   302: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   305: getfield field_1765 : Lnet/minecraft/class_239;
    //   308: aload #6
    //   310: ifnonnull -> 336
    //   313: ifnull -> 803
    //   316: goto -> 323
    //   319: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   322: athrow
    //   323: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   326: getfield field_1765 : Lnet/minecraft/class_239;
    //   329: goto -> 336
    //   332: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   335: athrow
    //   336: aload #6
    //   338: ifnonnull -> 370
    //   341: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   344: getstatic net/minecraft/class_239$class_240.field_1332 : Lnet/minecraft/class_239$class_240;
    //   347: if_acmpne -> 803
    //   350: goto -> 357
    //   353: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   356: athrow
    //   357: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   360: getfield field_1765 : Lnet/minecraft/class_239;
    //   363: goto -> 370
    //   366: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   369: athrow
    //   370: checkcast net/minecraft/class_3965
    //   373: astore #8
    //   375: aload #8
    //   377: invokevirtual method_17777 : ()Lnet/minecraft/class_2338;
    //   380: astore #9
    //   382: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   385: getfield field_1724 : Lnet/minecraft/class_746;
    //   388: invokevirtual method_6030 : ()Lnet/minecraft/class_1799;
    //   391: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   394: astore #10
    //   396: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   399: getfield field_1724 : Lnet/minecraft/class_746;
    //   402: invokevirtual method_6030 : ()Lnet/minecraft/class_1799;
    //   405: getstatic net/minecraft/class_9334.field_50075 : Lnet/minecraft/class_9331;
    //   408: invokevirtual method_57824 : (Lnet/minecraft/class_9331;)Ljava/lang/Object;
    //   411: checkcast net/minecraft/class_4174
    //   414: astore #11
    //   416: aload #10
    //   418: instanceof net/minecraft/class_1819
    //   421: istore #12
    //   423: aload #11
    //   425: ifnonnull -> 525
    //   428: aload #10
    //   430: instanceof net/minecraft/class_1819
    //   433: aload #6
    //   435: ifnonnull -> 467
    //   438: goto -> 445
    //   441: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   444: athrow
    //   445: ifne -> 525
    //   448: goto -> 455
    //   451: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   454: athrow
    //   455: aload #10
    //   457: instanceof net/minecraft/class_1829
    //   460: goto -> 467
    //   463: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   466: athrow
    //   467: aload #6
    //   469: ifnonnull -> 494
    //   472: ifne -> 525
    //   475: goto -> 482
    //   478: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   481: athrow
    //   482: aload #10
    //   484: instanceof net/minecraft/class_1753
    //   487: goto -> 494
    //   490: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   493: athrow
    //   494: aload #6
    //   496: ifnonnull -> 521
    //   499: ifne -> 525
    //   502: goto -> 509
    //   505: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   508: athrow
    //   509: aload #10
    //   511: instanceof net/minecraft/class_1812
    //   514: goto -> 521
    //   517: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   520: athrow
    //   521: ifne -> 525
    //   524: return
    //   525: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   528: getfield field_1724 : Lnet/minecraft/class_746;
    //   531: invokevirtual method_6058 : ()Lnet/minecraft/class_1268;
    //   534: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   537: if_acmpne -> 565
    //   540: iload #12
    //   542: aload #6
    //   544: ifnonnull -> 567
    //   547: goto -> 554
    //   550: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   553: athrow
    //   554: ifeq -> 565
    //   557: goto -> 564
    //   560: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   563: athrow
    //   564: return
    //   565: iload #12
    //   567: aload #6
    //   569: ifnonnull -> 641
    //   572: ifeq -> 620
    //   575: goto -> 582
    //   578: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   581: athrow
    //   582: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   585: getfield field_1724 : Lnet/minecraft/class_746;
    //   588: invokevirtual method_6047 : ()Lnet/minecraft/class_1799;
    //   591: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   594: instanceof net/minecraft/class_1829
    //   597: aload #6
    //   599: ifnonnull -> 641
    //   602: goto -> 609
    //   605: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   608: athrow
    //   609: ifne -> 620
    //   612: goto -> 619
    //   615: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   618: athrow
    //   619: return
    //   620: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   623: getfield field_1724 : Lnet/minecraft/class_746;
    //   626: aload #6
    //   628: ifnonnull -> 760
    //   631: getfield field_6252 : Z
    //   634: goto -> 641
    //   637: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   640: athrow
    //   641: ifeq -> 727
    //   644: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   647: getfield field_1724 : Lnet/minecraft/class_746;
    //   650: aload #6
    //   652: ifnonnull -> 760
    //   655: goto -> 662
    //   658: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   661: athrow
    //   662: getfield field_6279 : I
    //   665: aload_0
    //   666: lload #4
    //   668: iconst_1
    //   669: anewarray java/lang/Object
    //   672: dup_x2
    //   673: dup_x2
    //   674: pop
    //   675: invokestatic valueOf : (J)Ljava/lang/Long;
    //   678: iconst_0
    //   679: swap
    //   680: aastore
    //   681: invokevirtual A : ([Ljava/lang/Object;)I
    //   684: iconst_2
    //   685: idiv
    //   686: if_icmpge -> 727
    //   689: goto -> 696
    //   692: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   695: athrow
    //   696: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   699: aload #6
    //   701: ifnonnull -> 790
    //   704: goto -> 711
    //   707: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   710: athrow
    //   711: getfield field_1724 : Lnet/minecraft/class_746;
    //   714: getfield field_6279 : I
    //   717: ifge -> 787
    //   720: goto -> 727
    //   723: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   726: athrow
    //   727: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   730: getfield field_1724 : Lnet/minecraft/class_746;
    //   733: iconst_m1
    //   734: putfield field_6279 : I
    //   737: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   740: getfield field_1724 : Lnet/minecraft/class_746;
    //   743: iconst_1
    //   744: putfield field_6252 : Z
    //   747: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   750: getfield field_1724 : Lnet/minecraft/class_746;
    //   753: goto -> 760
    //   756: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   759: athrow
    //   760: iload #12
    //   762: ifeq -> 775
    //   765: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   768: goto -> 784
    //   771: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   774: athrow
    //   775: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   778: getfield field_1724 : Lnet/minecraft/class_746;
    //   781: invokevirtual method_6058 : ()Lnet/minecraft/class_1268;
    //   784: putfield field_6266 : Lnet/minecraft/class_1268;
    //   787: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   790: getfield field_1713 : Lnet/minecraft/class_702;
    //   793: aload #9
    //   795: aload #8
    //   797: invokevirtual method_17780 : ()Lnet/minecraft/class_2350;
    //   800: invokevirtual method_3054 : (Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V
    //   803: return
    // Exception table:
    //   from	to	target	type
    //   22	38	41	wtf/opal/x5
    //   35	52	55	wtf/opal/x5
    //   45	70	73	wtf/opal/x5
    //   59	80	83	wtf/opal/x5
    //   77	93	96	wtf/opal/x5
    //   100	122	125	wtf/opal/x5
    //   112	142	145	wtf/opal/x5
    //   149	160	163	wtf/opal/x5
    //   152	173	176	wtf/opal/x5
    //   167	183	186	wtf/opal/x5
    //   190	201	204	wtf/opal/x5
    //   195	211	214	wtf/opal/x5
    //   218	229	232	wtf/opal/x5
    //   223	237	237	wtf/opal/x5
    //   244	261	264	wtf/opal/x5
    //   258	276	279	wtf/opal/x5
    //   268	292	295	wtf/opal/x5
    //   305	316	319	wtf/opal/x5
    //   313	329	332	wtf/opal/x5
    //   336	350	353	wtf/opal/x5
    //   341	363	366	wtf/opal/x5
    //   423	438	441	wtf/opal/x5
    //   428	448	451	wtf/opal/x5
    //   445	460	463	wtf/opal/x5
    //   467	475	478	wtf/opal/x5
    //   472	487	490	wtf/opal/x5
    //   494	502	505	wtf/opal/x5
    //   499	514	517	wtf/opal/x5
    //   525	547	550	wtf/opal/x5
    //   540	557	560	wtf/opal/x5
    //   567	575	578	wtf/opal/x5
    //   572	602	605	wtf/opal/x5
    //   582	612	615	wtf/opal/x5
    //   620	634	637	wtf/opal/x5
    //   641	655	658	wtf/opal/x5
    //   644	689	692	wtf/opal/x5
    //   662	704	707	wtf/opal/x5
    //   696	720	723	wtf/opal/x5
    //   711	753	756	wtf/opal/x5
    //   760	771	771	wtf/opal/x5
  }
  
  private boolean lambda$new$11(ky paramky) {
    return ((bd)this.g.z()).equals(bd.OFF);
  }
  
  private boolean lambda$new$10(ky paramky) {
    return ((bd)this.g.z()).equals(bd.OFF);
  }
  
  private boolean lambda$new$9(ky paramky) {
    return ((bd)this.g.z()).equals(bd.OFF);
  }
  
  private boolean lambda$new$8(ky paramky) {
    return ((bd)this.g.z()).equals(bd.OFF);
  }
  
  private boolean lambda$new$7(ky paramky) {
    return ((bd)this.g.z()).equals(bd.OFF);
  }
  
  private boolean lambda$new$6(ky paramky) {
    return ((bd)this.g.z()).equals(bd.OFF);
  }
  
  private boolean lambda$new$5(ky paramky) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  private boolean lambda$new$4(ky paramky) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  private boolean lambda$new$3(ky paramky) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  private boolean lambda$new$2(ky paramky) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  private boolean lambda$new$1(ky paramky) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  private boolean lambda$new$0(ky paramky) {
    return ((bd)this.g.z()).equals(bd.MAIN);
  }
  
  static {
    long l = b ^ 0x39F1FCBEA246L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[23];
    boolean bool = false;
    String str;
    int i = (str = "¶Û \005nîBÌ(s> ÝWM-RÈ¾Xx¾¾¬ÕCc\002ë.J\0345\007ôo\030Ã©{ò´h3amrõ¬iå\033®É\005Ón+ \013\024C\034<ôÜ\024JÍê\017DC­\026Í\005\"m\002ÉçR¤¿\001I Nº\034­Q½,Trú·7WrBaøÕÓ\004È\000÷û»S] \022Vx`º~¾ó²eÛ._ÄÙ¼êÖ¿\006ÞÐÒµÁõ/WoeÛ0\030#÷0Í§\032F$áÈ\0262\013ý\nØ\033ö@:B\030ä\020£TÆ38êÁí5¾ëÂ×²%[û=\035\020ÁÉ«\034=«½ç\017\004ïVsÁ\020¨ÓË®är:NÈN4¸\024Act c%ì%Èa±{8c\0326¯\025\003yÊ*ÃY)®¿DüÃx£\020À¡ÿ]cÞ÷\022eï?5j\rÈ\030!jÌH\007@³JBr9\022ùa4:0¬\037º\020\0057¨å. 2^Úsû`Â` \003%\rÃ\004Gs`8ÓþÃ)\023éNð\033\006×pv*B×Êãçâ:¦ \022\fÍú\024]î\031i\036öª\000N¥`õÅF\027è82g Ù 8ô\002\007Ã\bsEÅ~õ\031\003q\016ïäµ¼®\017$!\013Plè\0310Õ¿5<\033¶`m\031ïiLØÓ\025\t^5º%ügÈµTgw+¾N+¸\\xu[¶\0063÷%`_1\030¨ÈÏ+à¢ ~ÉU±Z\tÃÌo¥bðg m\003;]¬ßZ¾X®¬f6°ç\022õ|J¦c¥Í¡G¢\023\030ÔGÑÅþÚÉZ\033Û® b@\027Ð3\013`~x").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x139B;
    if (m[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])o.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          o.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/je", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = l[i].getBytes("ISO-8859-1");
      m[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return m[i];
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
    //   66: ldc_w 'wtf/opal/je'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5302;
    if (y[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = t[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])z.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          z.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/je", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      y[i] = Integer.valueOf(j);
    } 
    return y[i].intValue();
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
    //   66: ldc_w 'wtf/opal/je'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\je.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */