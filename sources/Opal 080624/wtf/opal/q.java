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
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_241;
import net.minecraft.class_2596;
import net.minecraft.class_2886;

public final class q extends d {
  private final ky<b7> u;
  
  private final ky<p4> X;
  
  private final kd A;
  
  private final kd r;
  
  private final kd s;
  
  private final kt R;
  
  private final kt Y;
  
  private final kt p;
  
  private final kt D;
  
  private final kt Q;
  
  private final kt J;
  
  private final kt Z;
  
  private final kt f;
  
  private final kt b;
  
  private final kt t;
  
  private final kt M;
  
  private final ke W;
  
  private final ke n;
  
  private class_1309 v;
  
  private class_1309 g;
  
  private List<class_1309> y;
  
  private boolean L;
  
  private boolean d;
  
  private boolean E;
  
  private int U;
  
  private int I;
  
  private int z;
  
  private final List<class_2596<?>> P;
  
  private class_241 x;
  
  private final kr G;
  
  private final dx a;
  
  private double T;
  
  private long B;
  
  private final gm<u0> l;
  
  private final gm<lz> w;
  
  private final gm<l3> q;
  
  private final gm<lb> k;
  
  private final gm<k7> o;
  
  private static int m;
  
  private static final long bb = on.a(8890084196423647103L, -7891659487497861676L, MethodHandles.lookup().lookupClass()).a(14576395347169L);
  
  private static final String[] cb;
  
  private static final String[] db;
  
  private static final Map eb = new HashMap<>(13);
  
  private static final long[] gb;
  
  private static final Integer[] hb;
  
  private static final Map ib;
  
  public q(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 68270429693687
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 30909615796075
    //   17: lxor
    //   18: dup2
    //   19: bipush #32
    //   21: lushr
    //   22: l2i
    //   23: istore #5
    //   25: dup2
    //   26: bipush #32
    //   28: lshl
    //   29: bipush #48
    //   31: lushr
    //   32: l2i
    //   33: istore #6
    //   35: dup2
    //   36: bipush #48
    //   38: lshl
    //   39: bipush #48
    //   41: lushr
    //   42: l2i
    //   43: istore #7
    //   45: pop2
    //   46: dup2
    //   47: ldc2_w 133094128506251
    //   50: lxor
    //   51: lstore #8
    //   53: dup2
    //   54: ldc2_w 96151269018846
    //   57: lxor
    //   58: lstore #10
    //   60: pop2
    //   61: aload_0
    //   62: sipush #13302
    //   65: ldc2_w 4752103414135857337
    //   68: lload_1
    //   69: lxor
    //   70: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   75: lload #10
    //   77: sipush #18113
    //   80: ldc2_w 7547772007062001027
    //   83: lload_1
    //   84: lxor
    //   85: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   90: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   93: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   96: invokestatic y : ()I
    //   99: aload_0
    //   100: new wtf/opal/ky
    //   103: dup
    //   104: sipush #18810
    //   107: ldc2_w 3795986386620013089
    //   110: lload_1
    //   111: lxor
    //   112: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   117: getstatic wtf/opal/b7.RANGE : Lwtf/opal/b7;
    //   120: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   123: putfield u : Lwtf/opal/ky;
    //   126: aload_0
    //   127: new wtf/opal/ky
    //   130: dup
    //   131: sipush #18450
    //   134: ldc2_w 8763541076654480202
    //   137: lload_1
    //   138: lxor
    //   139: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   144: getstatic wtf/opal/p4.FAKE : Lwtf/opal/p4;
    //   147: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   150: putfield X : Lwtf/opal/ky;
    //   153: aload_0
    //   154: new wtf/opal/kd
    //   157: dup
    //   158: sipush #17791
    //   161: ldc2_w 5376037586922591807
    //   164: lload_1
    //   165: lxor
    //   166: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   171: iconst_3
    //   172: anewarray wtf/opal/ke
    //   175: dup
    //   176: iconst_0
    //   177: new wtf/opal/ke
    //   180: dup
    //   181: sipush #14936
    //   184: ldc2_w 765026555937466642
    //   187: lload_1
    //   188: lxor
    //   189: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   194: iconst_1
    //   195: invokespecial <init> : (Ljava/lang/String;Z)V
    //   198: aastore
    //   199: dup
    //   200: iconst_1
    //   201: new wtf/opal/ke
    //   204: dup
    //   205: sipush #10549
    //   208: ldc2_w 5286909990328973940
    //   211: lload_1
    //   212: lxor
    //   213: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   218: iconst_0
    //   219: invokespecial <init> : (Ljava/lang/String;Z)V
    //   222: aastore
    //   223: dup
    //   224: iconst_2
    //   225: new wtf/opal/ke
    //   228: dup
    //   229: sipush #29194
    //   232: ldc2_w 8553397453571088723
    //   235: lload_1
    //   236: lxor
    //   237: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   242: iconst_0
    //   243: invokespecial <init> : (Ljava/lang/String;Z)V
    //   246: aastore
    //   247: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   250: putfield A : Lwtf/opal/kd;
    //   253: istore #12
    //   255: aload_0
    //   256: new wtf/opal/kd
    //   259: dup
    //   260: sipush #23008
    //   263: ldc2_w 2998413941749893796
    //   266: lload_1
    //   267: lxor
    //   268: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   273: iconst_2
    //   274: anewarray wtf/opal/ke
    //   277: dup
    //   278: iconst_0
    //   279: new wtf/opal/ke
    //   282: dup
    //   283: sipush #32263
    //   286: ldc2_w 4493739327516241227
    //   289: lload_1
    //   290: lxor
    //   291: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   296: iconst_0
    //   297: invokespecial <init> : (Ljava/lang/String;Z)V
    //   300: aastore
    //   301: dup
    //   302: iconst_1
    //   303: new wtf/opal/ke
    //   306: dup
    //   307: sipush #10953
    //   310: ldc2_w 8883455316119468438
    //   313: lload_1
    //   314: lxor
    //   315: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   320: iconst_0
    //   321: invokespecial <init> : (Ljava/lang/String;Z)V
    //   324: aastore
    //   325: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   328: putfield r : Lwtf/opal/kd;
    //   331: aload_0
    //   332: new wtf/opal/kd
    //   335: dup
    //   336: sipush #7898
    //   339: ldc2_w 5442015254292860300
    //   342: lload_1
    //   343: lxor
    //   344: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   349: iconst_1
    //   350: anewarray wtf/opal/ke
    //   353: dup
    //   354: iconst_0
    //   355: new wtf/opal/ke
    //   358: dup
    //   359: sipush #8216
    //   362: ldc2_w 2787924312374555462
    //   365: lload_1
    //   366: lxor
    //   367: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   372: iconst_1
    //   373: invokespecial <init> : (Ljava/lang/String;Z)V
    //   376: aastore
    //   377: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   380: putfield s : Lwtf/opal/kd;
    //   383: aload_0
    //   384: new wtf/opal/kt
    //   387: dup
    //   388: sipush #4403
    //   391: ldc2_w 5611449008512139876
    //   394: lload_1
    //   395: lxor
    //   396: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   401: ldc2_w 3.0
    //   404: ldc2_w 3.0
    //   407: ldc2_w 8.0
    //   410: ldc2_w 0.1
    //   413: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   416: putfield R : Lwtf/opal/kt;
    //   419: aload_0
    //   420: new wtf/opal/kt
    //   423: dup
    //   424: sipush #10789
    //   427: ldc2_w 7265846453648996717
    //   430: lload_1
    //   431: lxor
    //   432: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   437: ldc2_w 3.0
    //   440: ldc2_w 3.0
    //   443: ldc2_w 8.0
    //   446: ldc2_w 0.1
    //   449: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   452: putfield Y : Lwtf/opal/kt;
    //   455: aload_0
    //   456: new wtf/opal/kt
    //   459: dup
    //   460: sipush #17259
    //   463: ldc2_w 8339704455040946235
    //   466: lload_1
    //   467: lxor
    //   468: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   473: ldc2_w 3.0
    //   476: ldc2_w 3.0
    //   479: ldc2_w 8.0
    //   482: ldc2_w 0.1
    //   485: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   488: putfield p : Lwtf/opal/kt;
    //   491: aload_0
    //   492: new wtf/opal/kt
    //   495: dup
    //   496: sipush #8520
    //   499: ldc2_w 1587677544988520988
    //   502: lload_1
    //   503: lxor
    //   504: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   509: ldc2_w 3.0
    //   512: ldc2_w 3.0
    //   515: ldc2_w 8.0
    //   518: ldc2_w 0.1
    //   521: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   524: putfield D : Lwtf/opal/kt;
    //   527: aload_0
    //   528: new wtf/opal/kt
    //   531: dup
    //   532: sipush #31722
    //   535: ldc2_w 3292589163852327075
    //   538: lload_1
    //   539: lxor
    //   540: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   545: ldc2_w 12.0
    //   548: dconst_1
    //   549: ldc2_w 20.0
    //   552: dconst_1
    //   553: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   556: putfield Q : Lwtf/opal/kt;
    //   559: aload_0
    //   560: new wtf/opal/kt
    //   563: dup
    //   564: sipush #14669
    //   567: ldc2_w 903676960998861343
    //   570: lload_1
    //   571: lxor
    //   572: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   577: ldc2_w 15.0
    //   580: dconst_1
    //   581: ldc2_w 20.0
    //   584: dconst_1
    //   585: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   588: putfield J : Lwtf/opal/kt;
    //   591: aload_0
    //   592: new wtf/opal/kt
    //   595: dup
    //   596: sipush #13078
    //   599: ldc2_w 8571036629881430109
    //   602: lload_1
    //   603: lxor
    //   604: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   609: ldc2_w 10.0
    //   612: dconst_0
    //   613: ldc2_w 100.0
    //   616: dconst_1
    //   617: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   620: putfield Z : Lwtf/opal/kt;
    //   623: aload_0
    //   624: new wtf/opal/kt
    //   627: dup
    //   628: sipush #12237
    //   631: ldc2_w 6455004201436560529
    //   634: lload_1
    //   635: lxor
    //   636: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   641: dconst_0
    //   642: dconst_0
    //   643: ldc2_w 10.0
    //   646: dconst_1
    //   647: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   650: putfield f : Lwtf/opal/kt;
    //   653: aload_0
    //   654: new wtf/opal/kt
    //   657: dup
    //   658: sipush #454
    //   661: ldc2_w 6339712267666592405
    //   664: lload_1
    //   665: lxor
    //   666: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   671: dconst_1
    //   672: dconst_0
    //   673: ldc2_w 10.0
    //   676: dconst_1
    //   677: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   680: putfield b : Lwtf/opal/kt;
    //   683: aload_0
    //   684: new wtf/opal/kt
    //   687: dup
    //   688: sipush #32154
    //   691: ldc2_w 8978400003743968991
    //   694: lload_1
    //   695: lxor
    //   696: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   701: dconst_1
    //   702: dconst_1
    //   703: ldc2_w 10.0
    //   706: ldc2_w 0.1
    //   709: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   712: putfield t : Lwtf/opal/kt;
    //   715: aload_0
    //   716: new wtf/opal/kt
    //   719: dup
    //   720: sipush #5991
    //   723: ldc2_w 2095642204135621674
    //   726: lload_1
    //   727: lxor
    //   728: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   733: dconst_1
    //   734: dconst_1
    //   735: ldc2_w 10.0
    //   738: ldc2_w 0.1
    //   741: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   744: putfield M : Lwtf/opal/kt;
    //   747: aload_0
    //   748: new wtf/opal/ke
    //   751: dup
    //   752: sipush #16151
    //   755: ldc2_w 4789561731565956176
    //   758: lload_1
    //   759: lxor
    //   760: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   765: iconst_1
    //   766: invokespecial <init> : (Ljava/lang/String;Z)V
    //   769: putfield W : Lwtf/opal/ke;
    //   772: aload_0
    //   773: new wtf/opal/ke
    //   776: dup
    //   777: sipush #7376
    //   780: ldc2_w 2199834607605944202
    //   783: lload_1
    //   784: lxor
    //   785: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   790: iconst_0
    //   791: invokespecial <init> : (Ljava/lang/String;Z)V
    //   794: putfield n : Lwtf/opal/ke;
    //   797: aload_0
    //   798: new java/util/ArrayList
    //   801: dup
    //   802: invokespecial <init> : ()V
    //   805: putfield y : Ljava/util/List;
    //   808: aload_0
    //   809: new java/util/ArrayList
    //   812: dup
    //   813: invokespecial <init> : ()V
    //   816: putfield P : Ljava/util/List;
    //   819: aload_0
    //   820: new net/minecraft/class_241
    //   823: dup
    //   824: fconst_0
    //   825: fconst_0
    //   826: invokespecial <init> : (FF)V
    //   829: putfield x : Lnet/minecraft/class_241;
    //   832: aload_0
    //   833: new wtf/opal/kr
    //   836: dup
    //   837: invokespecial <init> : ()V
    //   840: putfield G : Lwtf/opal/kr;
    //   843: aload_0
    //   844: new wtf/opal/dk
    //   847: dup
    //   848: sipush #27030
    //   851: ldc2_w 5371090683644326644
    //   854: lload_1
    //   855: lxor
    //   856: <illegal opcode> g : (IJ)I
    //   861: iload #5
    //   863: dconst_1
    //   864: iload #6
    //   866: i2c
    //   867: iload #7
    //   869: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   872: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   875: putfield a : Lwtf/opal/dx;
    //   878: aload_0
    //   879: aload_0
    //   880: <illegal opcode> H : (Lwtf/opal/q;)Lwtf/opal/gm;
    //   885: putfield l : Lwtf/opal/gm;
    //   888: aload_0
    //   889: aload_0
    //   890: <illegal opcode> H : (Lwtf/opal/q;)Lwtf/opal/gm;
    //   895: putfield w : Lwtf/opal/gm;
    //   898: aload_0
    //   899: aload_0
    //   900: <illegal opcode> H : (Lwtf/opal/q;)Lwtf/opal/gm;
    //   905: putfield q : Lwtf/opal/gm;
    //   908: aload_0
    //   909: aload_0
    //   910: <illegal opcode> H : (Lwtf/opal/q;)Lwtf/opal/gm;
    //   915: putfield k : Lwtf/opal/gm;
    //   918: aload_0
    //   919: aload_0
    //   920: <illegal opcode> H : (Lwtf/opal/q;)Lwtf/opal/gm;
    //   925: putfield o : Lwtf/opal/gm;
    //   928: aload_0
    //   929: getfield D : Lwtf/opal/kt;
    //   932: aload_0
    //   933: getfield n : Lwtf/opal/ke;
    //   936: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   941: lload_3
    //   942: dup2_x1
    //   943: pop2
    //   944: iconst_3
    //   945: anewarray java/lang/Object
    //   948: dup_x1
    //   949: swap
    //   950: iconst_2
    //   951: swap
    //   952: aastore
    //   953: dup_x2
    //   954: dup_x2
    //   955: pop
    //   956: invokestatic valueOf : (J)Ljava/lang/Long;
    //   959: iconst_1
    //   960: swap
    //   961: aastore
    //   962: dup_x1
    //   963: swap
    //   964: iconst_0
    //   965: swap
    //   966: aastore
    //   967: invokevirtual C : ([Ljava/lang/Object;)V
    //   970: aload_0
    //   971: getfield X : Lwtf/opal/ky;
    //   974: aload_0
    //   975: getfield n : Lwtf/opal/ke;
    //   978: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   983: lload_3
    //   984: dup2_x1
    //   985: pop2
    //   986: iconst_3
    //   987: anewarray java/lang/Object
    //   990: dup_x1
    //   991: swap
    //   992: iconst_2
    //   993: swap
    //   994: aastore
    //   995: dup_x2
    //   996: dup_x2
    //   997: pop
    //   998: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1001: iconst_1
    //   1002: swap
    //   1003: aastore
    //   1004: dup_x1
    //   1005: swap
    //   1006: iconst_0
    //   1007: swap
    //   1008: aastore
    //   1009: invokevirtual C : ([Ljava/lang/Object;)V
    //   1012: aload_0
    //   1013: sipush #14369
    //   1016: ldc2_w 1311029723078452044
    //   1019: lload_1
    //   1020: lxor
    //   1021: <illegal opcode> g : (IJ)I
    //   1026: anewarray wtf/opal/k3
    //   1029: dup
    //   1030: iconst_0
    //   1031: aload_0
    //   1032: getfield u : Lwtf/opal/ky;
    //   1035: aastore
    //   1036: dup
    //   1037: iconst_1
    //   1038: aload_0
    //   1039: getfield X : Lwtf/opal/ky;
    //   1042: aastore
    //   1043: dup
    //   1044: iconst_2
    //   1045: aload_0
    //   1046: getfield A : Lwtf/opal/kd;
    //   1049: aastore
    //   1050: dup
    //   1051: iconst_3
    //   1052: aload_0
    //   1053: getfield r : Lwtf/opal/kd;
    //   1056: aastore
    //   1057: dup
    //   1058: iconst_4
    //   1059: aload_0
    //   1060: getfield s : Lwtf/opal/kd;
    //   1063: aastore
    //   1064: dup
    //   1065: iconst_5
    //   1066: aload_0
    //   1067: getfield R : Lwtf/opal/kt;
    //   1070: aastore
    //   1071: dup
    //   1072: sipush #23851
    //   1075: ldc2_w 2003647258116392524
    //   1078: lload_1
    //   1079: lxor
    //   1080: <illegal opcode> g : (IJ)I
    //   1085: aload_0
    //   1086: getfield Y : Lwtf/opal/kt;
    //   1089: aastore
    //   1090: dup
    //   1091: sipush #19146
    //   1094: ldc2_w 2459056576938906030
    //   1097: lload_1
    //   1098: lxor
    //   1099: <illegal opcode> g : (IJ)I
    //   1104: aload_0
    //   1105: getfield p : Lwtf/opal/kt;
    //   1108: aastore
    //   1109: dup
    //   1110: sipush #21648
    //   1113: ldc2_w 4566549808211693560
    //   1116: lload_1
    //   1117: lxor
    //   1118: <illegal opcode> g : (IJ)I
    //   1123: aload_0
    //   1124: getfield D : Lwtf/opal/kt;
    //   1127: aastore
    //   1128: dup
    //   1129: sipush #29484
    //   1132: ldc2_w 7119303772941035591
    //   1135: lload_1
    //   1136: lxor
    //   1137: <illegal opcode> g : (IJ)I
    //   1142: aload_0
    //   1143: getfield Q : Lwtf/opal/kt;
    //   1146: aastore
    //   1147: dup
    //   1148: sipush #21520
    //   1151: ldc2_w 2562087834031230847
    //   1154: lload_1
    //   1155: lxor
    //   1156: <illegal opcode> g : (IJ)I
    //   1161: aload_0
    //   1162: getfield J : Lwtf/opal/kt;
    //   1165: aastore
    //   1166: dup
    //   1167: sipush #26922
    //   1170: ldc2_w 472151940270731855
    //   1173: lload_1
    //   1174: lxor
    //   1175: <illegal opcode> g : (IJ)I
    //   1180: aload_0
    //   1181: getfield Z : Lwtf/opal/kt;
    //   1184: aastore
    //   1185: dup
    //   1186: sipush #12673
    //   1189: ldc2_w 5900105145986509547
    //   1192: lload_1
    //   1193: lxor
    //   1194: <illegal opcode> g : (IJ)I
    //   1199: aload_0
    //   1200: getfield f : Lwtf/opal/kt;
    //   1203: aastore
    //   1204: dup
    //   1205: sipush #9059
    //   1208: ldc2_w 1948438015488522253
    //   1211: lload_1
    //   1212: lxor
    //   1213: <illegal opcode> g : (IJ)I
    //   1218: aload_0
    //   1219: getfield b : Lwtf/opal/kt;
    //   1222: aastore
    //   1223: dup
    //   1224: sipush #17854
    //   1227: ldc2_w 727144553336970968
    //   1230: lload_1
    //   1231: lxor
    //   1232: <illegal opcode> g : (IJ)I
    //   1237: aload_0
    //   1238: getfield t : Lwtf/opal/kt;
    //   1241: aastore
    //   1242: dup
    //   1243: sipush #16489
    //   1246: ldc2_w 7728242795871247109
    //   1249: lload_1
    //   1250: lxor
    //   1251: <illegal opcode> g : (IJ)I
    //   1256: aload_0
    //   1257: getfield M : Lwtf/opal/kt;
    //   1260: aastore
    //   1261: dup
    //   1262: sipush #29943
    //   1265: ldc2_w 795879161554710422
    //   1268: lload_1
    //   1269: lxor
    //   1270: <illegal opcode> g : (IJ)I
    //   1275: aload_0
    //   1276: getfield W : Lwtf/opal/ke;
    //   1279: aastore
    //   1280: dup
    //   1281: sipush #9298
    //   1284: ldc2_w 1463498113663008571
    //   1287: lload_1
    //   1288: lxor
    //   1289: <illegal opcode> g : (IJ)I
    //   1294: aload_0
    //   1295: getfield n : Lwtf/opal/ke;
    //   1298: aastore
    //   1299: lload #8
    //   1301: dup2_x1
    //   1302: pop2
    //   1303: iconst_2
    //   1304: anewarray java/lang/Object
    //   1307: dup_x1
    //   1308: swap
    //   1309: iconst_1
    //   1310: swap
    //   1311: aastore
    //   1312: dup_x2
    //   1313: dup_x2
    //   1314: pop
    //   1315: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1318: iconst_0
    //   1319: swap
    //   1320: aastore
    //   1321: invokevirtual o : ([Ljava/lang/Object;)V
    //   1324: iload #12
    //   1326: ifne -> 1343
    //   1329: iconst_5
    //   1330: anewarray wtf/opal/d
    //   1333: invokestatic p : ([Lwtf/opal/d;)V
    //   1336: goto -> 1343
    //   1339: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1342: athrow
    //   1343: return
    // Exception table:
    //   from	to	target	type
    //   255	1336	1339	wtf/opal/x5
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    int i = y();
    try {
      if (i != 0) {
        try {
          if (b9.c.field_1724 == null)
            return; 
        } catch (x5 x5) {
          throw a(null);
        } 
        this.z = 0;
        this.E = false;
        new Object[1];
        super.K(new Object[] { Long.valueOf(l2) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
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
    //   14: ldc2_w 0
    //   17: lxor
    //   18: lstore #4
    //   20: pop2
    //   21: invokestatic F : ()I
    //   24: istore #6
    //   26: aload_0
    //   27: iload #6
    //   29: ifne -> 279
    //   32: getfield n : Lwtf/opal/ke;
    //   35: invokevirtual z : ()Ljava/lang/Object;
    //   38: checkcast java/lang/Boolean
    //   41: invokevirtual booleanValue : ()Z
    //   44: ifeq -> 240
    //   47: goto -> 54
    //   50: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   53: athrow
    //   54: aload_0
    //   55: iload #6
    //   57: ifne -> 279
    //   60: goto -> 67
    //   63: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   66: athrow
    //   67: lload_2
    //   68: lconst_0
    //   69: lcmp
    //   70: ifle -> 269
    //   73: getfield X : Lwtf/opal/ky;
    //   76: invokevirtual z : ()Ljava/lang/Object;
    //   79: checkcast wtf/opal/p4
    //   82: getstatic wtf/opal/p4.WATCHDOG : Lwtf/opal/p4;
    //   85: invokevirtual equals : (Ljava/lang/Object;)Z
    //   88: ifeq -> 240
    //   91: goto -> 98
    //   94: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   101: getfield field_1724 : Lnet/minecraft/class_746;
    //   104: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   107: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   110: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   113: instanceof net/minecraft/class_1829
    //   116: iload #6
    //   118: ifne -> 167
    //   121: goto -> 128
    //   124: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   127: athrow
    //   128: ifeq -> 240
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: lload_2
    //   139: lconst_0
    //   140: lcmp
    //   141: ifle -> 297
    //   144: aload_0
    //   145: iload #6
    //   147: ifne -> 279
    //   150: goto -> 157
    //   153: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: getfield d : Z
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: ifeq -> 240
    //   170: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   173: getfield field_1724 : Lnet/minecraft/class_746;
    //   176: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   179: getfield field_7545 : I
    //   182: istore #7
    //   184: iload #7
    //   186: iconst_1
    //   187: iadd
    //   188: sipush #17856
    //   191: ldc2_w 8216054959322343436
    //   194: lload_2
    //   195: lxor
    //   196: <illegal opcode> g : (IJ)I
    //   201: irem
    //   202: istore #8
    //   204: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   207: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   210: new net/minecraft/class_2868
    //   213: dup
    //   214: iload #8
    //   216: invokespecial <init> : (I)V
    //   219: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   222: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   225: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   228: new net/minecraft/class_2868
    //   231: dup
    //   232: iload #7
    //   234: invokespecial <init> : (I)V
    //   237: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   240: aload_0
    //   241: iconst_0
    //   242: putfield d : Z
    //   245: aload_0
    //   246: iconst_0
    //   247: putfield L : Z
    //   250: aload_0
    //   251: getfield P : Ljava/util/List;
    //   254: invokeinterface clear : ()V
    //   259: aload_0
    //   260: getfield y : Ljava/util/List;
    //   263: invokeinterface clear : ()V
    //   268: aload_0
    //   269: aload_0
    //   270: aconst_null
    //   271: dup_x1
    //   272: putfield g : Lnet/minecraft/class_1309;
    //   275: putfield v : Lnet/minecraft/class_1309;
    //   278: aload_0
    //   279: lload #4
    //   281: iconst_1
    //   282: anewarray java/lang/Object
    //   285: dup_x2
    //   286: dup_x2
    //   287: pop
    //   288: invokestatic valueOf : (J)Ljava/lang/Long;
    //   291: iconst_0
    //   292: swap
    //   293: aastore
    //   294: invokespecial z : ([Ljava/lang/Object;)V
    //   297: return
    // Exception table:
    //   from	to	target	type
    //   26	47	50	wtf/opal/x5
    //   32	60	63	wtf/opal/x5
    //   54	91	94	wtf/opal/x5
    //   67	121	124	wtf/opal/x5
    //   98	131	134	wtf/opal/x5
    //   128	150	153	wtf/opal/x5
    //   138	160	163	wtf/opal/x5
  }
  
  private boolean C(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/q.bb : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 16811203469696
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic y : ()I
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   37: iconst_0
    //   38: anewarray java/lang/Object
    //   41: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   44: ldc_w wtf/opal/xw
    //   47: iconst_1
    //   48: anewarray java/lang/Object
    //   51: dup_x1
    //   52: swap
    //   53: iconst_0
    //   54: swap
    //   55: aastore
    //   56: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   59: checkcast wtf/opal/xw
    //   62: astore #7
    //   64: istore #6
    //   66: aload #7
    //   68: iload #6
    //   70: ifeq -> 131
    //   73: iconst_0
    //   74: anewarray java/lang/Object
    //   77: invokevirtual D : ([Ljava/lang/Object;)Z
    //   80: lload_2
    //   81: lconst_0
    //   82: lcmp
    //   83: ifle -> 103
    //   86: ifeq -> 102
    //   89: goto -> 96
    //   92: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   95: athrow
    //   96: iconst_0
    //   97: ireturn
    //   98: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: iconst_0
    //   103: anewarray java/lang/Object
    //   106: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   109: iconst_0
    //   110: anewarray java/lang/Object
    //   113: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   116: ldc_w wtf/opal/xc
    //   119: iconst_1
    //   120: anewarray java/lang/Object
    //   123: dup_x1
    //   124: swap
    //   125: iconst_0
    //   126: swap
    //   127: aastore
    //   128: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   131: checkcast wtf/opal/xc
    //   134: astore #8
    //   136: aload #8
    //   138: iconst_0
    //   139: anewarray java/lang/Object
    //   142: invokevirtual D : ([Ljava/lang/Object;)Z
    //   145: iload #6
    //   147: ifeq -> 216
    //   150: ifeq -> 203
    //   153: goto -> 160
    //   156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   159: athrow
    //   160: aload #8
    //   162: iconst_0
    //   163: anewarray java/lang/Object
    //   166: invokevirtual F : ([Ljava/lang/Object;)Z
    //   169: iload #6
    //   171: lload_2
    //   172: lconst_0
    //   173: lcmp
    //   174: iflt -> 218
    //   177: ifeq -> 216
    //   180: goto -> 187
    //   183: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: ifeq -> 203
    //   190: goto -> 197
    //   193: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   196: athrow
    //   197: iconst_0
    //   198: ireturn
    //   199: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   202: athrow
    //   203: aload_0
    //   204: getfield W : Lwtf/opal/ke;
    //   207: invokevirtual z : ()Ljava/lang/Object;
    //   210: checkcast java/lang/Boolean
    //   213: invokevirtual booleanValue : ()Z
    //   216: iload #6
    //   218: ifeq -> 278
    //   221: ifeq -> 277
    //   224: goto -> 231
    //   227: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   230: athrow
    //   231: lload #4
    //   233: iconst_1
    //   234: anewarray java/lang/Object
    //   237: dup_x2
    //   238: dup_x2
    //   239: pop
    //   240: invokestatic valueOf : (J)Ljava/lang/Long;
    //   243: iconst_0
    //   244: swap
    //   245: aastore
    //   246: invokestatic J : ([Ljava/lang/Object;)Z
    //   249: iload #6
    //   251: ifeq -> 278
    //   254: goto -> 261
    //   257: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   260: athrow
    //   261: ifne -> 277
    //   264: goto -> 271
    //   267: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   270: athrow
    //   271: iconst_0
    //   272: ireturn
    //   273: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   276: athrow
    //   277: iconst_1
    //   278: ireturn
    // Exception table:
    //   from	to	target	type
    //   66	89	92	wtf/opal/x5
    //   73	98	98	wtf/opal/x5
    //   136	153	156	wtf/opal/x5
    //   150	180	183	wtf/opal/x5
    //   160	190	193	wtf/opal/x5
    //   187	199	199	wtf/opal/x5
    //   216	224	227	wtf/opal/x5
    //   221	254	257	wtf/opal/x5
    //   231	264	267	wtf/opal/x5
    //   261	273	273	wtf/opal/x5
  }
  
  private void E(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/q.bb : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic F : ()I
    //   21: aload_0
    //   22: getfield y : Ljava/util/List;
    //   25: invokeinterface clear : ()V
    //   30: istore #4
    //   32: aload_0
    //   33: getfield W : Lwtf/opal/ke;
    //   36: invokevirtual z : ()Ljava/lang/Object;
    //   39: checkcast java/lang/Boolean
    //   42: invokevirtual booleanValue : ()Z
    //   45: iload #4
    //   47: ifne -> 238
    //   50: ifeq -> 152
    //   53: goto -> 60
    //   56: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   63: getfield field_1724 : Lnet/minecraft/class_746;
    //   66: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   69: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   72: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   75: astore #5
    //   77: aload #5
    //   79: instanceof net/minecraft/class_1829
    //   82: iload #4
    //   84: ifne -> 238
    //   87: ifne -> 152
    //   90: goto -> 97
    //   93: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: aload #5
    //   99: instanceof net/minecraft/class_1743
    //   102: iload #4
    //   104: ifne -> 238
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: ifne -> 152
    //   117: goto -> 124
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: aload #5
    //   126: instanceof net/minecraft/class_1835
    //   129: iload #4
    //   131: ifne -> 238
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: ifne -> 152
    //   144: goto -> 151
    //   147: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: return
    //   152: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   155: getfield field_1687 : Lnet/minecraft/class_638;
    //   158: invokevirtual method_18112 : ()Ljava/lang/Iterable;
    //   161: aload_0
    //   162: <illegal opcode> accept : (Lwtf/opal/q;)Ljava/util/function/Consumer;
    //   167: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   172: aload_0
    //   173: aload_0
    //   174: getfield y : Ljava/util/List;
    //   177: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   182: aload_0
    //   183: <illegal opcode> test : (Lwtf/opal/q;)Ljava/util/function/Predicate;
    //   188: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   193: invokestatic toList : ()Ljava/util/stream/Collector;
    //   196: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
    //   201: checkcast java/util/List
    //   204: putfield y : Ljava/util/List;
    //   207: aload_0
    //   208: lload_2
    //   209: lconst_0
    //   210: lcmp
    //   211: iflt -> 277
    //   214: iload #4
    //   216: ifne -> 277
    //   219: getfield u : Lwtf/opal/ky;
    //   222: invokevirtual z : ()Ljava/lang/Object;
    //   225: checkcast wtf/opal/b7
    //   228: invokevirtual ordinal : ()I
    //   231: goto -> 238
    //   234: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   237: athrow
    //   238: tableswitch default -> 459, 0 -> 276, 1 -> 315, 2 -> 344, 3 -> 378, 4 -> 407, 5 -> 437
    //   276: aload_0
    //   277: getfield y : Ljava/util/List;
    //   280: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   283: getfield field_1724 : Lnet/minecraft/class_746;
    //   286: dup
    //   287: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   290: pop
    //   291: <illegal opcode> applyAsDouble : (Lnet/minecraft/class_746;)Ljava/util/function/ToDoubleFunction;
    //   296: invokestatic comparingDouble : (Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
    //   299: invokeinterface sort : (Ljava/util/Comparator;)V
    //   304: iload #4
    //   306: lload_2
    //   307: lconst_0
    //   308: lcmp
    //   309: ifle -> 334
    //   312: ifeq -> 459
    //   315: aload_0
    //   316: getfield y : Ljava/util/List;
    //   319: <illegal opcode> applyAsDouble : ()Ljava/util/function/ToDoubleFunction;
    //   324: invokestatic comparingDouble : (Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
    //   327: invokeinterface sort : (Ljava/util/Comparator;)V
    //   332: iload #4
    //   334: ifeq -> 459
    //   337: goto -> 344
    //   340: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   343: athrow
    //   344: aload_0
    //   345: getfield y : Ljava/util/List;
    //   348: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
    //   353: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
    //   356: invokeinterface reversed : ()Ljava/util/Comparator;
    //   361: invokeinterface sort : (Ljava/util/Comparator;)V
    //   366: iload #4
    //   368: ifeq -> 459
    //   371: goto -> 378
    //   374: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: aload_0
    //   379: getfield y : Ljava/util/List;
    //   382: <illegal opcode> applyAsInt : ()Ljava/util/function/ToIntFunction;
    //   387: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
    //   390: invokeinterface sort : (Ljava/util/Comparator;)V
    //   395: iload #4
    //   397: ifeq -> 459
    //   400: goto -> 407
    //   403: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   406: athrow
    //   407: aload_0
    //   408: getfield y : Ljava/util/List;
    //   411: aload_0
    //   412: <illegal opcode> applyAsInt : (Lwtf/opal/q;)Ljava/util/function/ToIntFunction;
    //   417: invokestatic comparingInt : (Ljava/util/function/ToIntFunction;)Ljava/util/Comparator;
    //   420: invokeinterface sort : (Ljava/util/Comparator;)V
    //   425: iload #4
    //   427: ifeq -> 459
    //   430: goto -> 437
    //   433: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   436: athrow
    //   437: aload_0
    //   438: getfield y : Ljava/util/List;
    //   441: aload_0
    //   442: <illegal opcode> compare : (Lwtf/opal/q;)Ljava/util/Comparator;
    //   447: invokeinterface sort : (Ljava/util/Comparator;)V
    //   452: goto -> 459
    //   455: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   458: athrow
    //   459: return
    // Exception table:
    //   from	to	target	type
    //   32	53	56	wtf/opal/x5
    //   77	90	93	wtf/opal/x5
    //   87	107	110	wtf/opal/x5
    //   97	117	120	wtf/opal/x5
    //   114	134	137	wtf/opal/x5
    //   124	144	147	wtf/opal/x5
    //   152	231	234	wtf/opal/x5
    //   277	337	340	wtf/opal/x5
    //   315	371	374	wtf/opal/x5
    //   344	400	403	wtf/opal/x5
    //   378	430	433	wtf/opal/x5
    //   407	452	455	wtf/opal/x5
  }
  
  private int N(class_1309 paramclass_1309) {
    return paramclass_1309.field_6235;
  }
  
  public boolean Y(Object[] paramArrayOfObject) {
    return this.L;
  }
  
  public boolean K(Object[] paramArrayOfObject) {
    return this.n.z().booleanValue();
  }
  
  private void w(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    l = bb ^ l;
    int i = y();
    try {
      if (i != 0)
        try {
          if (this.v == null)
            return; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    this.x = ln.x(new Object[] { this.v });
  }
  
  public class_1309 U(Object[] paramArrayOfObject) {
    return this.v;
  }
  
  public class_1309 d(Object[] paramArrayOfObject) {
    return this.g;
  }
  
  private int lambda$handleTargets$8(class_1309 paramclass_13091, class_1309 paramclass_13092) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 36504636498502
    //   6: lxor
    //   7: lstore_3
    //   8: aload_1
    //   9: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   12: getfield field_1724 : Lnet/minecraft/class_746;
    //   15: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   18: f2d
    //   19: dstore #6
    //   21: invokestatic F : ()I
    //   24: aload_2
    //   25: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   28: getfield field_1724 : Lnet/minecraft/class_746;
    //   31: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   34: f2d
    //   35: dstore #8
    //   37: istore #5
    //   39: dload #6
    //   41: aload_0
    //   42: getfield Y : Lwtf/opal/kt;
    //   45: invokevirtual z : ()Ljava/lang/Object;
    //   48: checkcast java/lang/Double
    //   51: invokevirtual doubleValue : ()D
    //   54: dcmpg
    //   55: iload #5
    //   57: ifne -> 131
    //   60: ifgt -> 124
    //   63: goto -> 70
    //   66: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   69: athrow
    //   70: dload #8
    //   72: aload_0
    //   73: getfield Y : Lwtf/opal/kt;
    //   76: invokevirtual z : ()Ljava/lang/Object;
    //   79: checkcast java/lang/Double
    //   82: invokevirtual doubleValue : ()D
    //   85: dcmpg
    //   86: iload #5
    //   88: ifne -> 131
    //   91: goto -> 98
    //   94: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: ifgt -> 124
    //   101: goto -> 108
    //   104: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   107: athrow
    //   108: aload_1
    //   109: getfield field_6235 : I
    //   112: aload_2
    //   113: getfield field_6235 : I
    //   116: invokestatic compare : (II)I
    //   119: ireturn
    //   120: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   123: athrow
    //   124: dload #6
    //   126: dload #8
    //   128: invokestatic compare : (DD)I
    //   131: istore #10
    //   133: iload #10
    //   135: iload #5
    //   137: ifne -> 168
    //   140: ifeq -> 157
    //   143: goto -> 150
    //   146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   149: athrow
    //   150: iload #10
    //   152: ireturn
    //   153: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: aload_1
    //   158: getfield field_6235 : I
    //   161: aload_2
    //   162: getfield field_6235 : I
    //   165: invokestatic compare : (II)I
    //   168: ireturn
    // Exception table:
    //   from	to	target	type
    //   39	63	66	wtf/opal/x5
    //   60	91	94	wtf/opal/x5
    //   70	101	104	wtf/opal/x5
    //   98	120	120	wtf/opal/x5
    //   133	143	146	wtf/opal/x5
    //   140	153	153	wtf/opal/x5
  }
  
  private boolean lambda$handleTargets$7(class_1309 paramclass_1309) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 22352230675556
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 14438153388771
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic y : ()I
    //   20: istore #6
    //   22: aload_1
    //   23: instanceof net/minecraft/class_1657
    //   26: iload #6
    //   28: ifeq -> 111
    //   31: ifeq -> 107
    //   34: goto -> 41
    //   37: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   40: athrow
    //   41: aload_0
    //   42: getfield A : Lwtf/opal/kd;
    //   45: sipush #19761
    //   48: ldc2_w 3708850310129249810
    //   51: lload_2
    //   52: lxor
    //   53: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   58: iconst_1
    //   59: anewarray java/lang/Object
    //   62: dup_x1
    //   63: swap
    //   64: iconst_0
    //   65: swap
    //   66: aastore
    //   67: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   70: invokevirtual z : ()Ljava/lang/Object;
    //   73: checkcast java/lang/Boolean
    //   76: invokevirtual booleanValue : ()Z
    //   79: iload #6
    //   81: ifeq -> 111
    //   84: goto -> 91
    //   87: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   90: athrow
    //   91: ifne -> 107
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: iconst_0
    //   102: ireturn
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: aload_1
    //   108: instanceof net/minecraft/class_1657
    //   111: iload #6
    //   113: ifeq -> 229
    //   116: ifne -> 192
    //   119: goto -> 126
    //   122: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   125: athrow
    //   126: aload_0
    //   127: getfield A : Lwtf/opal/kd;
    //   130: sipush #29858
    //   133: ldc2_w 7648673098197587849
    //   136: lload_2
    //   137: lxor
    //   138: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   143: iconst_1
    //   144: anewarray java/lang/Object
    //   147: dup_x1
    //   148: swap
    //   149: iconst_0
    //   150: swap
    //   151: aastore
    //   152: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   155: invokevirtual z : ()Ljava/lang/Object;
    //   158: checkcast java/lang/Boolean
    //   161: invokevirtual booleanValue : ()Z
    //   164: iload #6
    //   166: ifeq -> 229
    //   169: goto -> 176
    //   172: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: ifne -> 192
    //   179: goto -> 186
    //   182: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: iconst_0
    //   187: ireturn
    //   188: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   191: athrow
    //   192: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   195: getfield field_1724 : Lnet/minecraft/class_746;
    //   198: lload #4
    //   200: dup2_x1
    //   201: pop2
    //   202: aload_1
    //   203: iconst_3
    //   204: anewarray java/lang/Object
    //   207: dup_x1
    //   208: swap
    //   209: iconst_2
    //   210: swap
    //   211: aastore
    //   212: dup_x1
    //   213: swap
    //   214: iconst_1
    //   215: swap
    //   216: aastore
    //   217: dup_x2
    //   218: dup_x2
    //   219: pop
    //   220: invokestatic valueOf : (J)Ljava/lang/Long;
    //   223: iconst_0
    //   224: swap
    //   225: aastore
    //   226: invokestatic W : ([Ljava/lang/Object;)Z
    //   229: iload #6
    //   231: ifeq -> 311
    //   234: ifeq -> 310
    //   237: goto -> 244
    //   240: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   243: athrow
    //   244: aload_0
    //   245: getfield A : Lwtf/opal/kd;
    //   248: sipush #28736
    //   251: ldc2_w 8503142347485139829
    //   254: lload_2
    //   255: lxor
    //   256: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   261: iconst_1
    //   262: anewarray java/lang/Object
    //   265: dup_x1
    //   266: swap
    //   267: iconst_0
    //   268: swap
    //   269: aastore
    //   270: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   273: invokevirtual z : ()Ljava/lang/Object;
    //   276: checkcast java/lang/Boolean
    //   279: invokevirtual booleanValue : ()Z
    //   282: iload #6
    //   284: ifeq -> 311
    //   287: goto -> 294
    //   290: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   293: athrow
    //   294: ifne -> 310
    //   297: goto -> 304
    //   300: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   303: athrow
    //   304: iconst_0
    //   305: ireturn
    //   306: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   309: athrow
    //   310: iconst_1
    //   311: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	34	37	wtf/opal/x5
    //   31	84	87	wtf/opal/x5
    //   41	94	97	wtf/opal/x5
    //   91	103	103	wtf/opal/x5
    //   111	119	122	wtf/opal/x5
    //   116	169	172	wtf/opal/x5
    //   126	179	182	wtf/opal/x5
    //   176	188	188	wtf/opal/x5
    //   229	237	240	wtf/opal/x5
    //   234	287	290	wtf/opal/x5
    //   244	297	300	wtf/opal/x5
    //   294	306	306	wtf/opal/x5
  }
  
  private void lambda$handleTargets$6(class_1297 paramclass_1297) {
    long l1 = bb ^ 0x704572588287L;
    long l2 = l1 ^ 0x32F369BC9A9DL;
    int i = y();
    try {
      if (i != 0)
        try {
          if (paramclass_1297 instanceof class_1309) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    class_1309 class_13091 = (class_1309)paramclass_1297;
    try {
      if (i != 0) {
        try {
          if (i != 0)
            try {
              if (b9.c.field_1724.method_5739((class_1297)class_13091) <= this.R.z().doubleValue()) {
              
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
          if (i != 0)
            try {
              if (b9.c.field_1724.method_5739((class_1297)class_13091) == this.R.z().doubleValue()) {
                new Object[2];
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
          if (i != 0)
            try {
              if (b9.c.field_1724.method_5739((class_1297)class_13091) == this.R.z().doubleValue()) {
              
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
          if (i != 0)
            if (b9.c.field_1724.method_5739((class_1297)class_13091) != this.R.z().doubleValue())
              return;  
        } catch (x5 x5) {
          throw a(null);
        } 
        this.y.add(class_13091);
        return;
      } 
      return;
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private void lambda$new$5(k7 paramk7) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 2294302714114
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 48013582522506
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic F : ()I
    //   20: istore #6
    //   22: aload_0
    //   23: getfield v : Lnet/minecraft/class_1309;
    //   26: iload #6
    //   28: ifne -> 86
    //   31: ifnonnull -> 42
    //   34: goto -> 41
    //   37: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   40: athrow
    //   41: return
    //   42: aload_0
    //   43: iload #6
    //   45: ifne -> 103
    //   48: getfield s : Lwtf/opal/kd;
    //   51: sipush #17183
    //   54: ldc2_w 8527247407679454558
    //   57: lload_2
    //   58: lxor
    //   59: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   64: iconst_1
    //   65: anewarray java/lang/Object
    //   68: dup_x1
    //   69: swap
    //   70: iconst_0
    //   71: swap
    //   72: aastore
    //   73: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   76: invokevirtual z : ()Ljava/lang/Object;
    //   79: goto -> 86
    //   82: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: checkcast java/lang/Boolean
    //   89: invokevirtual booleanValue : ()Z
    //   92: ifeq -> 657
    //   95: aload_0
    //   96: goto -> 103
    //   99: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: getfield v : Lnet/minecraft/class_1309;
    //   106: aload_1
    //   107: iconst_0
    //   108: anewarray java/lang/Object
    //   111: invokevirtual W : ([Ljava/lang/Object;)F
    //   114: iconst_2
    //   115: anewarray java/lang/Object
    //   118: dup_x1
    //   119: swap
    //   120: invokestatic valueOf : (F)Ljava/lang/Float;
    //   123: iconst_1
    //   124: swap
    //   125: aastore
    //   126: dup_x1
    //   127: swap
    //   128: iconst_0
    //   129: swap
    //   130: aastore
    //   131: invokestatic F : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   134: astore #7
    //   136: aload_0
    //   137: getfield v : Lnet/minecraft/class_1309;
    //   140: invokevirtual method_17682 : ()F
    //   143: aload_0
    //   144: getfield v : Lnet/minecraft/class_1309;
    //   147: invokevirtual method_5715 : ()Z
    //   150: ifeq -> 163
    //   153: ldc_w 0.1
    //   156: goto -> 166
    //   159: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   162: athrow
    //   163: ldc_w 0.2
    //   166: fadd
    //   167: fstore #8
    //   169: aload_0
    //   170: dup
    //   171: getfield T : D
    //   174: invokestatic currentTimeMillis : ()J
    //   177: aload_0
    //   178: getfield B : J
    //   181: lsub
    //   182: l2d
    //   183: ldc2_w 0.004
    //   186: dmul
    //   187: dadd
    //   188: putfield T : D
    //   191: aload_0
    //   192: invokestatic currentTimeMillis : ()J
    //   195: putfield B : J
    //   198: aload #7
    //   200: getfield field_1352 : D
    //   203: dstore #9
    //   205: aload #7
    //   207: getfield field_1351 : D
    //   210: aload_0
    //   211: getfield T : D
    //   214: invokestatic sin : (D)D
    //   217: dadd
    //   218: dconst_1
    //   219: dadd
    //   220: dstore #11
    //   222: aload #7
    //   224: getfield field_1350 : D
    //   227: dstore #13
    //   229: invokestatic method_1348 : ()Lnet/minecraft/class_289;
    //   232: astore #15
    //   234: aload #15
    //   236: invokevirtual method_1349 : ()Lnet/minecraft/class_287;
    //   239: astore #16
    //   241: aload_1
    //   242: iconst_0
    //   243: anewarray java/lang/Object
    //   246: invokevirtual e : ([Ljava/lang/Object;)Lnet/minecraft/class_4587;
    //   249: invokevirtual method_22903 : ()V
    //   252: invokestatic enableBlend : ()V
    //   255: invokestatic defaultBlendFunc : ()V
    //   258: invokestatic disableCull : ()V
    //   261: invokestatic disableDepthTest : ()V
    //   264: <illegal opcode> get : ()Ljava/util/function/Supplier;
    //   269: invokestatic setShader : (Ljava/util/function/Supplier;)V
    //   272: aload #16
    //   274: getstatic net/minecraft/class_293$class_5596.field_27380 : Lnet/minecraft/class_293$class_5596;
    //   277: getstatic net/minecraft/class_290.field_1576 : Lnet/minecraft/class_293;
    //   280: invokevirtual method_1328 : (Lnet/minecraft/class_293$class_5596;Lnet/minecraft/class_293;)V
    //   283: aload_1
    //   284: iconst_0
    //   285: anewarray java/lang/Object
    //   288: invokevirtual e : ([Ljava/lang/Object;)Lnet/minecraft/class_4587;
    //   291: invokevirtual method_23760 : ()Lnet/minecraft/class_4587$class_4665;
    //   294: invokevirtual method_23761 : ()Lorg/joml/Matrix4f;
    //   297: astore #17
    //   299: iconst_0
    //   300: anewarray java/lang/Object
    //   303: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   306: iconst_0
    //   307: anewarray java/lang/Object
    //   310: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   313: ldc_w wtf/opal/jt
    //   316: iconst_1
    //   317: anewarray java/lang/Object
    //   320: dup_x1
    //   321: swap
    //   322: iconst_0
    //   323: swap
    //   324: aastore
    //   325: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   328: checkcast wtf/opal/jt
    //   331: astore #18
    //   333: aload_0
    //   334: getfield T : D
    //   337: ldc2_w 0.1
    //   340: dadd
    //   341: invokestatic sin : (D)D
    //   344: fload #8
    //   346: f2d
    //   347: ddiv
    //   348: dstore #19
    //   350: aload #18
    //   352: iconst_0
    //   353: anewarray java/lang/Object
    //   356: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   359: invokevirtual z : ()Ljava/lang/Object;
    //   362: checkcast java/lang/Integer
    //   365: invokevirtual intValue : ()I
    //   368: aload_0
    //   369: getfield a : Lwtf/opal/dx;
    //   372: lload #4
    //   374: iconst_1
    //   375: anewarray java/lang/Object
    //   378: dup_x2
    //   379: dup_x2
    //   380: pop
    //   381: invokestatic valueOf : (J)Ljava/lang/Long;
    //   384: iconst_0
    //   385: swap
    //   386: aastore
    //   387: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   390: invokevirtual floatValue : ()F
    //   393: ldc_w 0.6
    //   396: fmul
    //   397: iconst_2
    //   398: anewarray java/lang/Object
    //   401: dup_x1
    //   402: swap
    //   403: invokestatic valueOf : (F)Ljava/lang/Float;
    //   406: iconst_1
    //   407: swap
    //   408: aastore
    //   409: dup_x1
    //   410: swap
    //   411: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   414: iconst_0
    //   415: swap
    //   416: aastore
    //   417: invokestatic X : ([Ljava/lang/Object;)I
    //   420: istore #21
    //   422: aload #18
    //   424: iconst_0
    //   425: anewarray java/lang/Object
    //   428: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   431: invokevirtual z : ()Ljava/lang/Object;
    //   434: checkcast java/lang/Integer
    //   437: invokevirtual intValue : ()I
    //   440: aload_0
    //   441: getfield a : Lwtf/opal/dx;
    //   444: lload #4
    //   446: iconst_1
    //   447: anewarray java/lang/Object
    //   450: dup_x2
    //   451: dup_x2
    //   452: pop
    //   453: invokestatic valueOf : (J)Ljava/lang/Long;
    //   456: iconst_0
    //   457: swap
    //   458: aastore
    //   459: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   462: invokevirtual floatValue : ()F
    //   465: ldc_w 0.3
    //   468: fmul
    //   469: iconst_2
    //   470: anewarray java/lang/Object
    //   473: dup_x1
    //   474: swap
    //   475: invokestatic valueOf : (F)Ljava/lang/Float;
    //   478: iconst_1
    //   479: swap
    //   480: aastore
    //   481: dup_x1
    //   482: swap
    //   483: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   486: iconst_0
    //   487: swap
    //   488: aastore
    //   489: invokestatic X : ([Ljava/lang/Object;)I
    //   492: istore #22
    //   494: ldc_w 6.2831855
    //   497: fstore #23
    //   499: fconst_0
    //   500: fstore #24
    //   502: fload #24
    //   504: ldc_w 6.479535
    //   507: fcmpg
    //   508: ifge -> 632
    //   511: dload #9
    //   513: ldc2_w 0.75
    //   516: fload #24
    //   518: f2d
    //   519: invokestatic cos : (D)D
    //   522: dmul
    //   523: dadd
    //   524: dstore #25
    //   526: dload #13
    //   528: ldc2_w 0.75
    //   531: fload #24
    //   533: f2d
    //   534: invokestatic sin : (D)D
    //   537: dmul
    //   538: dadd
    //   539: dstore #27
    //   541: aload #16
    //   543: aload #17
    //   545: dload #25
    //   547: d2f
    //   548: dload #11
    //   550: dload #19
    //   552: dsub
    //   553: d2f
    //   554: dload #27
    //   556: d2f
    //   557: invokevirtual method_22918 : (Lorg/joml/Matrix4f;FFF)Lnet/minecraft/class_4588;
    //   560: iload #21
    //   562: invokeinterface method_39415 : (I)Lnet/minecraft/class_4588;
    //   567: invokeinterface method_1344 : ()V
    //   572: aload #16
    //   574: aload #17
    //   576: dload #25
    //   578: d2f
    //   579: dload #11
    //   581: dload #19
    //   583: dsub
    //   584: ldc2_w 0.09817477315664291
    //   587: dadd
    //   588: d2f
    //   589: dload #27
    //   591: d2f
    //   592: invokevirtual method_22918 : (Lorg/joml/Matrix4f;FFF)Lnet/minecraft/class_4588;
    //   595: iload #22
    //   597: invokeinterface method_39415 : (I)Lnet/minecraft/class_4588;
    //   602: invokeinterface method_1344 : ()V
    //   607: fload #24
    //   609: ldc_w 0.19634955
    //   612: fadd
    //   613: fstore #24
    //   615: iload #6
    //   617: ifne -> 646
    //   620: iload #6
    //   622: ifeq -> 502
    //   625: goto -> 632
    //   628: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   631: athrow
    //   632: aload #15
    //   634: invokevirtual method_1350 : ()V
    //   637: invokestatic enableCull : ()V
    //   640: invokestatic disableBlend : ()V
    //   643: invokestatic enableDepthTest : ()V
    //   646: aload_1
    //   647: iconst_0
    //   648: anewarray java/lang/Object
    //   651: invokevirtual e : ([Ljava/lang/Object;)Lnet/minecraft/class_4587;
    //   654: invokevirtual method_22909 : ()V
    //   657: return
    // Exception table:
    //   from	to	target	type
    //   22	34	37	wtf/opal/x5
    //   42	79	82	wtf/opal/x5
    //   86	96	99	wtf/opal/x5
    //   136	159	159	wtf/opal/x5
    //   615	625	628	wtf/opal/x5
  }
  
  private void lambda$new$4(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 13214450040564
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic F : ()I
    //   11: istore #4
    //   13: aload_0
    //   14: getfield n : Lwtf/opal/ke;
    //   17: invokevirtual z : ()Ljava/lang/Object;
    //   20: checkcast java/lang/Boolean
    //   23: invokevirtual booleanValue : ()Z
    //   26: iload #4
    //   28: ifne -> 66
    //   31: ifeq -> 361
    //   34: goto -> 41
    //   37: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   40: athrow
    //   41: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   44: getfield field_1724 : Lnet/minecraft/class_746;
    //   47: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   50: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   53: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   56: instanceof net/minecraft/class_1829
    //   59: goto -> 66
    //   62: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   65: athrow
    //   66: iload #4
    //   68: ifne -> 104
    //   71: ifeq -> 361
    //   74: goto -> 81
    //   77: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   80: athrow
    //   81: aload_0
    //   82: getfield X : Lwtf/opal/ky;
    //   85: invokevirtual z : ()Ljava/lang/Object;
    //   88: checkcast wtf/opal/p4
    //   91: getstatic wtf/opal/p4.WATCHDOG : Lwtf/opal/p4;
    //   94: invokevirtual equals : (Ljava/lang/Object;)Z
    //   97: goto -> 104
    //   100: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   103: athrow
    //   104: iload #4
    //   106: ifne -> 259
    //   109: ifeq -> 236
    //   112: goto -> 119
    //   115: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   118: athrow
    //   119: aload_1
    //   120: iconst_0
    //   121: anewarray java/lang/Object
    //   124: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   127: astore #6
    //   129: aload #6
    //   131: iload #4
    //   133: ifne -> 158
    //   136: instanceof net/minecraft/class_2846
    //   139: ifeq -> 231
    //   142: goto -> 149
    //   145: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: aload #6
    //   151: goto -> 158
    //   154: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   157: athrow
    //   158: checkcast net/minecraft/class_2846
    //   161: astore #5
    //   163: aload_0
    //   164: getfield z : I
    //   167: iload #4
    //   169: ifne -> 213
    //   172: sipush #10325
    //   175: ldc2_w 3092597903208122820
    //   178: lload_2
    //   179: lxor
    //   180: <illegal opcode> g : (IJ)I
    //   185: if_icmpge -> 231
    //   188: goto -> 195
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: aload #5
    //   197: invokevirtual method_12363 : ()Lnet/minecraft/class_2846$class_2847;
    //   200: getstatic net/minecraft/class_2846$class_2847.field_12974 : Lnet/minecraft/class_2846$class_2847;
    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
    //   206: goto -> 213
    //   209: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   212: athrow
    //   213: ifeq -> 231
    //   216: aload_1
    //   217: iconst_0
    //   218: anewarray java/lang/Object
    //   221: invokevirtual Z : ([Ljava/lang/Object;)V
    //   224: goto -> 231
    //   227: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   230: athrow
    //   231: iload #4
    //   233: ifeq -> 361
    //   236: aload_0
    //   237: getfield X : Lwtf/opal/ky;
    //   240: invokevirtual z : ()Ljava/lang/Object;
    //   243: checkcast wtf/opal/p4
    //   246: getstatic wtf/opal/p4.VANILLA : Lwtf/opal/p4;
    //   249: invokevirtual equals : (Ljava/lang/Object;)Z
    //   252: goto -> 259
    //   255: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   258: athrow
    //   259: ifeq -> 361
    //   262: aload_1
    //   263: iconst_0
    //   264: anewarray java/lang/Object
    //   267: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   270: astore #6
    //   272: aload #6
    //   274: iload #4
    //   276: ifne -> 301
    //   279: instanceof net/minecraft/class_2846
    //   282: ifeq -> 361
    //   285: goto -> 292
    //   288: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   291: athrow
    //   292: aload #6
    //   294: goto -> 301
    //   297: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   300: athrow
    //   301: checkcast net/minecraft/class_2846
    //   304: astore #5
    //   306: aload_0
    //   307: getfield d : Z
    //   310: iload #4
    //   312: ifne -> 343
    //   315: ifeq -> 361
    //   318: goto -> 325
    //   321: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   324: athrow
    //   325: aload #5
    //   327: invokevirtual method_12363 : ()Lnet/minecraft/class_2846$class_2847;
    //   330: getstatic net/minecraft/class_2846$class_2847.field_12974 : Lnet/minecraft/class_2846$class_2847;
    //   333: invokevirtual equals : (Ljava/lang/Object;)Z
    //   336: goto -> 343
    //   339: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   342: athrow
    //   343: ifeq -> 361
    //   346: aload_1
    //   347: iconst_0
    //   348: anewarray java/lang/Object
    //   351: invokevirtual Z : ([Ljava/lang/Object;)V
    //   354: goto -> 361
    //   357: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   360: athrow
    //   361: return
    // Exception table:
    //   from	to	target	type
    //   13	34	37	wtf/opal/x5
    //   31	59	62	wtf/opal/x5
    //   66	74	77	wtf/opal/x5
    //   71	97	100	wtf/opal/x5
    //   104	112	115	wtf/opal/x5
    //   129	142	145	wtf/opal/x5
    //   136	151	154	wtf/opal/x5
    //   163	188	191	wtf/opal/x5
    //   172	206	209	wtf/opal/x5
    //   213	224	227	wtf/opal/x5
    //   231	252	255	wtf/opal/x5
    //   272	285	288	wtf/opal/x5
    //   279	294	297	wtf/opal/x5
    //   306	318	321	wtf/opal/x5
    //   315	336	339	wtf/opal/x5
    //   343	354	357	wtf/opal/x5
  }
  
  private void lambda$new$3(l3 paraml3) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 52703235661078
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic y : ()I
    //   11: istore #4
    //   13: aload_0
    //   14: getfield v : Lnet/minecraft/class_1309;
    //   17: iload #4
    //   19: ifeq -> 71
    //   22: ifnull -> 92
    //   25: goto -> 32
    //   28: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   31: athrow
    //   32: aload_0
    //   33: getfield r : Lwtf/opal/kd;
    //   36: sipush #31155
    //   39: ldc2_w 4582552369956090873
    //   42: lload_2
    //   43: lxor
    //   44: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   49: iconst_1
    //   50: anewarray java/lang/Object
    //   53: dup_x1
    //   54: swap
    //   55: iconst_0
    //   56: swap
    //   57: aastore
    //   58: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   61: invokevirtual z : ()Ljava/lang/Object;
    //   64: goto -> 71
    //   67: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   70: athrow
    //   71: checkcast java/lang/Boolean
    //   74: invokevirtual booleanValue : ()Z
    //   77: iload #4
    //   79: ifeq -> 118
    //   82: ifne -> 93
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: return
    //   93: aload_1
    //   94: iconst_0
    //   95: anewarray java/lang/Object
    //   98: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   101: iload #4
    //   103: ifeq -> 159
    //   106: invokevirtual method_1027 : ()D
    //   109: dconst_1
    //   110: dcmpl
    //   111: goto -> 118
    //   114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: ifle -> 151
    //   121: aload_1
    //   122: iconst_0
    //   123: anewarray java/lang/Object
    //   126: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   129: invokevirtual method_1029 : ()Lnet/minecraft/class_243;
    //   132: aload_1
    //   133: iconst_0
    //   134: anewarray java/lang/Object
    //   137: invokevirtual o : ([Ljava/lang/Object;)F
    //   140: f2d
    //   141: invokevirtual method_1021 : (D)Lnet/minecraft/class_243;
    //   144: goto -> 171
    //   147: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: aload_1
    //   152: iconst_0
    //   153: anewarray java/lang/Object
    //   156: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_243;
    //   159: aload_1
    //   160: iconst_0
    //   161: anewarray java/lang/Object
    //   164: invokevirtual o : ([Ljava/lang/Object;)F
    //   167: f2d
    //   168: invokevirtual method_1021 : (D)Lnet/minecraft/class_243;
    //   171: astore #5
    //   173: aload_0
    //   174: getfield x : Lnet/minecraft/class_241;
    //   177: getfield field_1343 : F
    //   180: f2d
    //   181: ldc2_w 0.017453292519943295
    //   184: dmul
    //   185: d2f
    //   186: invokestatic method_15374 : (F)F
    //   189: fstore #6
    //   191: aload_0
    //   192: getfield x : Lnet/minecraft/class_241;
    //   195: getfield field_1343 : F
    //   198: f2d
    //   199: ldc2_w 0.017453292519943295
    //   202: dmul
    //   203: d2f
    //   204: invokestatic method_15362 : (F)F
    //   207: fstore #7
    //   209: aload_1
    //   210: new net/minecraft/class_243
    //   213: dup
    //   214: aload #5
    //   216: getfield field_1352 : D
    //   219: fload #7
    //   221: f2d
    //   222: dmul
    //   223: aload #5
    //   225: getfield field_1350 : D
    //   228: fload #6
    //   230: f2d
    //   231: dmul
    //   232: dsub
    //   233: aload #5
    //   235: getfield field_1351 : D
    //   238: aload #5
    //   240: getfield field_1350 : D
    //   243: fload #7
    //   245: f2d
    //   246: dmul
    //   247: aload #5
    //   249: getfield field_1352 : D
    //   252: fload #6
    //   254: f2d
    //   255: dmul
    //   256: dadd
    //   257: invokespecial <init> : (DDD)V
    //   260: iconst_1
    //   261: anewarray java/lang/Object
    //   264: dup_x1
    //   265: swap
    //   266: iconst_0
    //   267: swap
    //   268: aastore
    //   269: invokevirtual v : ([Ljava/lang/Object;)V
    //   272: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	wtf/opal/x5
    //   22	64	67	wtf/opal/x5
    //   71	85	88	wtf/opal/x5
    //   93	111	114	wtf/opal/x5
    //   118	147	147	wtf/opal/x5
  }
  
  private void lambda$new$2(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 90281744622600
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 100148412899597
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 128421290503096
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 92692371984233
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 88864375749002
    //   34: lxor
    //   35: lstore #10
    //   37: pop2
    //   38: invokestatic y : ()I
    //   41: istore #12
    //   43: aload_0
    //   44: getfield Q : Lwtf/opal/kt;
    //   47: invokevirtual z : ()Ljava/lang/Object;
    //   50: checkcast java/lang/Double
    //   53: invokevirtual intValue : ()I
    //   56: iload #12
    //   58: ifeq -> 162
    //   61: aload_0
    //   62: getfield J : Lwtf/opal/kt;
    //   65: invokevirtual z : ()Ljava/lang/Object;
    //   68: checkcast java/lang/Double
    //   71: invokevirtual intValue : ()I
    //   74: if_icmple -> 137
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: aload_0
    //   85: getfield J : Lwtf/opal/kt;
    //   88: aload_0
    //   89: getfield Q : Lwtf/opal/kt;
    //   92: invokevirtual z : ()Ljava/lang/Object;
    //   95: checkcast java/lang/Double
    //   98: invokevirtual doubleValue : ()D
    //   101: lload #4
    //   103: dup2_x2
    //   104: pop2
    //   105: iconst_2
    //   106: anewarray java/lang/Object
    //   109: dup_x2
    //   110: dup_x2
    //   111: pop
    //   112: invokestatic valueOf : (D)Ljava/lang/Double;
    //   115: iconst_1
    //   116: swap
    //   117: aastore
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (J)Ljava/lang/Long;
    //   124: iconst_0
    //   125: swap
    //   126: aastore
    //   127: invokevirtual r : ([Ljava/lang/Object;)V
    //   130: goto -> 137
    //   133: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   136: athrow
    //   137: aload_0
    //   138: iload #12
    //   140: ifeq -> 221
    //   143: getfield n : Lwtf/opal/ke;
    //   146: invokevirtual z : ()Ljava/lang/Object;
    //   149: checkcast java/lang/Boolean
    //   152: invokevirtual booleanValue : ()Z
    //   155: goto -> 162
    //   158: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: ifeq -> 220
    //   165: aload_0
    //   166: iload #12
    //   168: ifeq -> 221
    //   171: goto -> 178
    //   174: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   177: athrow
    //   178: getfield X : Lwtf/opal/ky;
    //   181: invokevirtual z : ()Ljava/lang/Object;
    //   184: checkcast wtf/opal/p4
    //   187: getstatic wtf/opal/p4.WATCHDOG : Lwtf/opal/p4;
    //   190: invokevirtual equals : (Ljava/lang/Object;)Z
    //   193: ifeq -> 220
    //   196: goto -> 203
    //   199: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   202: athrow
    //   203: aload_0
    //   204: dup
    //   205: getfield z : I
    //   208: iconst_1
    //   209: iadd
    //   210: putfield z : I
    //   213: goto -> 220
    //   216: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   219: athrow
    //   220: aload_0
    //   221: iload #12
    //   223: ifeq -> 247
    //   226: getfield v : Lnet/minecraft/class_1309;
    //   229: ifnull -> 280
    //   232: goto -> 239
    //   235: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   238: athrow
    //   239: aload_0
    //   240: goto -> 247
    //   243: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   246: athrow
    //   247: lload #6
    //   249: iconst_1
    //   250: anewarray java/lang/Object
    //   253: dup_x2
    //   254: dup_x2
    //   255: pop
    //   256: invokestatic valueOf : (J)Ljava/lang/Long;
    //   259: iconst_0
    //   260: swap
    //   261: aastore
    //   262: invokevirtual C : ([Ljava/lang/Object;)Z
    //   265: iload #12
    //   267: ifeq -> 309
    //   270: ifne -> 281
    //   273: goto -> 280
    //   276: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   279: athrow
    //   280: return
    //   281: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   284: getfield field_1724 : Lnet/minecraft/class_746;
    //   287: aload_0
    //   288: getfield v : Lnet/minecraft/class_1309;
    //   291: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   294: f2d
    //   295: aload_0
    //   296: getfield p : Lwtf/opal/kt;
    //   299: invokevirtual z : ()Ljava/lang/Object;
    //   302: checkcast java/lang/Double
    //   305: invokevirtual doubleValue : ()D
    //   308: dcmpg
    //   309: ifgt -> 423
    //   312: aload_0
    //   313: lload #8
    //   315: iconst_1
    //   316: anewarray java/lang/Object
    //   319: dup_x2
    //   320: dup_x2
    //   321: pop
    //   322: invokestatic valueOf : (J)Ljava/lang/Long;
    //   325: iconst_0
    //   326: swap
    //   327: aastore
    //   328: invokevirtual w : ([Ljava/lang/Object;)V
    //   331: iconst_0
    //   332: anewarray java/lang/Object
    //   335: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   338: iconst_0
    //   339: anewarray java/lang/Object
    //   342: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   345: aload_0
    //   346: getfield x : Lnet/minecraft/class_241;
    //   349: aload_0
    //   350: getfield t : Lwtf/opal/kt;
    //   353: invokevirtual z : ()Ljava/lang/Object;
    //   356: checkcast java/lang/Double
    //   359: invokevirtual floatValue : ()F
    //   362: lload #10
    //   364: dup2_x1
    //   365: pop2
    //   366: aload_0
    //   367: getfield M : Lwtf/opal/kt;
    //   370: invokevirtual z : ()Ljava/lang/Object;
    //   373: checkcast java/lang/Double
    //   376: invokevirtual floatValue : ()F
    //   379: iconst_4
    //   380: anewarray java/lang/Object
    //   383: dup_x1
    //   384: swap
    //   385: invokestatic valueOf : (F)Ljava/lang/Float;
    //   388: iconst_3
    //   389: swap
    //   390: aastore
    //   391: dup_x1
    //   392: swap
    //   393: invokestatic valueOf : (F)Ljava/lang/Float;
    //   396: iconst_2
    //   397: swap
    //   398: aastore
    //   399: dup_x2
    //   400: dup_x2
    //   401: pop
    //   402: invokestatic valueOf : (J)Ljava/lang/Long;
    //   405: iconst_1
    //   406: swap
    //   407: aastore
    //   408: dup_x1
    //   409: swap
    //   410: iconst_0
    //   411: swap
    //   412: aastore
    //   413: invokevirtual g : ([Ljava/lang/Object;)V
    //   416: goto -> 423
    //   419: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   422: athrow
    //   423: return
    // Exception table:
    //   from	to	target	type
    //   43	77	80	wtf/opal/x5
    //   61	130	133	wtf/opal/x5
    //   137	155	158	wtf/opal/x5
    //   162	171	174	wtf/opal/x5
    //   165	196	199	wtf/opal/x5
    //   178	213	216	wtf/opal/x5
    //   221	232	235	wtf/opal/x5
    //   226	240	243	wtf/opal/x5
    //   247	273	276	wtf/opal/x5
    //   309	416	419	wtf/opal/x5
  }
  
  private void lambda$new$1(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/q.bb : J
    //   3: ldc2_w 9726256863100
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 109067443802674
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 40917677710086
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 111250353055312
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 50614581221580
    //   34: lxor
    //   35: lstore #10
    //   37: pop2
    //   38: invokestatic F : ()I
    //   41: istore #12
    //   43: iload #12
    //   45: ifne -> 116
    //   48: iconst_0
    //   49: anewarray java/lang/Object
    //   52: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   55: iconst_0
    //   56: anewarray java/lang/Object
    //   59: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/po;
    //   69: iconst_0
    //   70: anewarray java/lang/Object
    //   73: invokevirtual l : ([Ljava/lang/Object;)Ljava/lang/String;
    //   76: ifnonnull -> 97
    //   79: goto -> 86
    //   82: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: iconst_0
    //   87: invokestatic exit : (I)V
    //   90: goto -> 97
    //   93: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: aload_0
    //   98: lload #4
    //   100: iconst_1
    //   101: anewarray java/lang/Object
    //   104: dup_x2
    //   105: dup_x2
    //   106: pop
    //   107: invokestatic valueOf : (J)Ljava/lang/Long;
    //   110: iconst_0
    //   111: swap
    //   112: aastore
    //   113: invokevirtual E : ([Ljava/lang/Object;)V
    //   116: aload_0
    //   117: aload_0
    //   118: getfield y : Ljava/util/List;
    //   121: iload #12
    //   123: ifne -> 157
    //   126: invokeinterface isEmpty : ()Z
    //   131: ifne -> 163
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: aload_0
    //   142: getfield y : Ljava/util/List;
    //   145: invokeinterface getFirst : ()Ljava/lang/Object;
    //   150: goto -> 157
    //   153: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: checkcast net/minecraft/class_1309
    //   160: goto -> 164
    //   163: aconst_null
    //   164: putfield v : Lnet/minecraft/class_1309;
    //   167: aload_0
    //   168: getfield a : Lwtf/opal/dx;
    //   171: aload_0
    //   172: getfield v : Lnet/minecraft/class_1309;
    //   175: ifnull -> 188
    //   178: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   181: goto -> 191
    //   184: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   187: athrow
    //   188: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   191: lload #8
    //   193: dup2_x1
    //   194: pop2
    //   195: iconst_2
    //   196: anewarray java/lang/Object
    //   199: dup_x1
    //   200: swap
    //   201: iconst_1
    //   202: swap
    //   203: aastore
    //   204: dup_x2
    //   205: dup_x2
    //   206: pop
    //   207: invokestatic valueOf : (J)Ljava/lang/Long;
    //   210: iconst_0
    //   211: swap
    //   212: aastore
    //   213: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   216: pop
    //   217: aload_1
    //   218: iconst_0
    //   219: anewarray java/lang/Object
    //   222: invokevirtual K : ([Ljava/lang/Object;)Z
    //   225: ifne -> 1311
    //   228: aload_0
    //   229: iload #12
    //   231: ifne -> 318
    //   234: goto -> 241
    //   237: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: getfield v : Lnet/minecraft/class_1309;
    //   244: ifnull -> 295
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload_0
    //   255: iload #12
    //   257: ifne -> 324
    //   260: goto -> 267
    //   263: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   266: athrow
    //   267: lload #10
    //   269: iconst_1
    //   270: anewarray java/lang/Object
    //   273: dup_x2
    //   274: dup_x2
    //   275: pop
    //   276: invokestatic valueOf : (J)Ljava/lang/Long;
    //   279: iconst_0
    //   280: swap
    //   281: aastore
    //   282: invokevirtual C : ([Ljava/lang/Object;)Z
    //   285: ifne -> 323
    //   288: goto -> 295
    //   291: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   294: athrow
    //   295: aload_0
    //   296: iconst_0
    //   297: putfield L : Z
    //   300: aload_0
    //   301: aconst_null
    //   302: putfield g : Lnet/minecraft/class_1309;
    //   305: aload_0
    //   306: iconst_0
    //   307: putfield U : I
    //   310: aload_0
    //   311: goto -> 318
    //   314: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   317: athrow
    //   318: iconst_0
    //   319: putfield I : I
    //   322: return
    //   323: aload_0
    //   324: iload #12
    //   326: ifne -> 371
    //   329: getfield v : Lnet/minecraft/class_1309;
    //   332: aload_0
    //   333: getfield g : Lnet/minecraft/class_1309;
    //   336: if_acmpeq -> 363
    //   339: goto -> 346
    //   342: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   345: athrow
    //   346: aload_0
    //   347: iconst_0
    //   348: putfield I : I
    //   351: iload #12
    //   353: ifeq -> 380
    //   356: goto -> 363
    //   359: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   362: athrow
    //   363: aload_0
    //   364: goto -> 371
    //   367: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   370: athrow
    //   371: dup
    //   372: getfield I : I
    //   375: iconst_1
    //   376: iadd
    //   377: putfield I : I
    //   380: aload_0
    //   381: dup
    //   382: getfield U : I
    //   385: iconst_1
    //   386: iadd
    //   387: putfield U : I
    //   390: aload_0
    //   391: iload #12
    //   393: ifne -> 476
    //   396: getfield U : I
    //   399: aload_0
    //   400: getfield f : Lwtf/opal/kt;
    //   403: invokevirtual z : ()Ljava/lang/Object;
    //   406: checkcast java/lang/Double
    //   409: invokevirtual intValue : ()I
    //   412: if_icmpgt -> 468
    //   415: goto -> 422
    //   418: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   421: athrow
    //   422: aload_0
    //   423: iload #12
    //   425: ifne -> 456
    //   428: goto -> 435
    //   431: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   434: athrow
    //   435: getfield g : Lnet/minecraft/class_1309;
    //   438: ifnull -> 480
    //   441: goto -> 448
    //   444: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   447: athrow
    //   448: aload_0
    //   449: goto -> 456
    //   452: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   455: athrow
    //   456: aload_0
    //   457: getfield g : Lnet/minecraft/class_1309;
    //   460: putfield v : Lnet/minecraft/class_1309;
    //   463: iload #12
    //   465: ifeq -> 480
    //   468: aload_0
    //   469: goto -> 476
    //   472: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   475: athrow
    //   476: iconst_0
    //   477: putfield U : I
    //   480: iconst_0
    //   481: istore #13
    //   483: aload_0
    //   484: getfield G : Lwtf/opal/kr;
    //   487: iload #12
    //   489: ifne -> 871
    //   492: ldc_w 1000.0
    //   495: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   498: aload_0
    //   499: getfield Q : Lwtf/opal/kt;
    //   502: invokevirtual z : ()Ljava/lang/Object;
    //   505: checkcast java/lang/Double
    //   508: invokevirtual intValue : ()I
    //   511: iconst_1
    //   512: isub
    //   513: i2f
    //   514: aload_0
    //   515: getfield J : Lwtf/opal/kt;
    //   518: invokevirtual z : ()Ljava/lang/Object;
    //   521: checkcast java/lang/Double
    //   524: invokevirtual intValue : ()I
    //   527: i2f
    //   528: invokevirtual nextFloat : (FF)F
    //   531: fdiv
    //   532: f2l
    //   533: lload #6
    //   535: iconst_0
    //   536: iconst_3
    //   537: anewarray java/lang/Object
    //   540: dup_x1
    //   541: swap
    //   542: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   545: iconst_2
    //   546: swap
    //   547: aastore
    //   548: dup_x2
    //   549: dup_x2
    //   550: pop
    //   551: invokestatic valueOf : (J)Ljava/lang/Long;
    //   554: iconst_1
    //   555: swap
    //   556: aastore
    //   557: dup_x2
    //   558: dup_x2
    //   559: pop
    //   560: invokestatic valueOf : (J)Ljava/lang/Long;
    //   563: iconst_0
    //   564: swap
    //   565: aastore
    //   566: invokevirtual v : ([Ljava/lang/Object;)Z
    //   569: ifeq -> 864
    //   572: goto -> 579
    //   575: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   578: athrow
    //   579: aload_0
    //   580: getfield r : Lwtf/opal/kd;
    //   583: sipush #15934
    //   586: ldc2_w 2756305422748663318
    //   589: lload_2
    //   590: lxor
    //   591: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   596: iconst_1
    //   597: anewarray java/lang/Object
    //   600: dup_x1
    //   601: swap
    //   602: iconst_0
    //   603: swap
    //   604: aastore
    //   605: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   608: invokevirtual z : ()Ljava/lang/Object;
    //   611: checkcast java/lang/Boolean
    //   614: invokevirtual booleanValue : ()Z
    //   617: iload #12
    //   619: ifne -> 847
    //   622: goto -> 629
    //   625: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   628: athrow
    //   629: ifeq -> 800
    //   632: goto -> 639
    //   635: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   638: athrow
    //   639: aload_0
    //   640: getfield v : Lnet/minecraft/class_1309;
    //   643: ldc2_w 20.0
    //   646: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   649: invokevirtual method_1488 : ()F
    //   652: iconst_0
    //   653: anewarray java/lang/Object
    //   656: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   659: iconst_0
    //   660: anewarray java/lang/Object
    //   663: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   666: iconst_0
    //   667: anewarray java/lang/Object
    //   670: invokevirtual k : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   673: getfield field_1343 : F
    //   676: iconst_0
    //   677: anewarray java/lang/Object
    //   680: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   683: iconst_0
    //   684: anewarray java/lang/Object
    //   687: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   690: iconst_0
    //   691: anewarray java/lang/Object
    //   694: invokevirtual k : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   697: getfield field_1342 : F
    //   700: invokestatic alwaysTrue : ()Lcom/google/common/base/Predicate;
    //   703: bipush #6
    //   705: anewarray java/lang/Object
    //   708: dup_x1
    //   709: swap
    //   710: iconst_5
    //   711: swap
    //   712: aastore
    //   713: dup_x1
    //   714: swap
    //   715: invokestatic valueOf : (F)Ljava/lang/Float;
    //   718: iconst_4
    //   719: swap
    //   720: aastore
    //   721: dup_x1
    //   722: swap
    //   723: invokestatic valueOf : (F)Ljava/lang/Float;
    //   726: iconst_3
    //   727: swap
    //   728: aastore
    //   729: dup_x1
    //   730: swap
    //   731: invokestatic valueOf : (F)Ljava/lang/Float;
    //   734: iconst_2
    //   735: swap
    //   736: aastore
    //   737: dup_x2
    //   738: dup_x2
    //   739: pop
    //   740: invokestatic valueOf : (D)Ljava/lang/Double;
    //   743: iconst_1
    //   744: swap
    //   745: aastore
    //   746: dup_x1
    //   747: swap
    //   748: iconst_0
    //   749: swap
    //   750: aastore
    //   751: invokestatic t : ([Ljava/lang/Object;)Lnet/minecraft/class_239;
    //   754: astore #14
    //   756: aload #14
    //   758: iload #12
    //   760: ifne -> 775
    //   763: ifnull -> 799
    //   766: goto -> 773
    //   769: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   772: athrow
    //   773: aload #14
    //   775: invokevirtual method_17783 : ()Lnet/minecraft/class_239$class_240;
    //   778: getstatic net/minecraft/class_239$class_240.field_1331 : Lnet/minecraft/class_239$class_240;
    //   781: invokevirtual equals : (Ljava/lang/Object;)Z
    //   784: iload #12
    //   786: ifne -> 847
    //   789: ifne -> 800
    //   792: goto -> 799
    //   795: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   798: athrow
    //   799: return
    //   800: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   803: iconst_0
    //   804: sipush #5520
    //   807: ldc2_w 1143434786219679133
    //   810: lload_2
    //   811: lxor
    //   812: <illegal opcode> g : (IJ)I
    //   817: invokevirtual nextInt : (II)I
    //   820: i2d
    //   821: iload #12
    //   823: ifne -> 877
    //   826: aload_0
    //   827: getfield Z : Lwtf/opal/kt;
    //   830: invokevirtual z : ()Ljava/lang/Object;
    //   833: checkcast java/lang/Double
    //   836: invokevirtual doubleValue : ()D
    //   839: dcmpl
    //   840: goto -> 847
    //   843: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   846: athrow
    //   847: iflt -> 864
    //   850: iconst_1
    //   851: istore #13
    //   853: aload_0
    //   854: getfield G : Lwtf/opal/kr;
    //   857: iconst_0
    //   858: anewarray java/lang/Object
    //   861: invokevirtual z : ([Ljava/lang/Object;)V
    //   864: aload_0
    //   865: getfield Y : Lwtf/opal/kt;
    //   868: invokevirtual z : ()Ljava/lang/Object;
    //   871: checkcast java/lang/Double
    //   874: invokevirtual doubleValue : ()D
    //   877: dstore #14
    //   879: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   882: getfield field_1724 : Lnet/minecraft/class_746;
    //   885: aload_0
    //   886: getfield v : Lnet/minecraft/class_1309;
    //   889: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   892: f2d
    //   893: dload #14
    //   895: dcmpg
    //   896: iload #12
    //   898: ifne -> 1037
    //   901: ifgt -> 1012
    //   904: goto -> 911
    //   907: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   910: athrow
    //   911: iload #13
    //   913: iload #12
    //   915: ifne -> 1037
    //   918: goto -> 925
    //   921: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   924: athrow
    //   925: ifeq -> 1012
    //   928: goto -> 935
    //   931: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   934: athrow
    //   935: aload_0
    //   936: getfield I : I
    //   939: iload #12
    //   941: ifne -> 1037
    //   944: goto -> 951
    //   947: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   950: athrow
    //   951: aload_0
    //   952: getfield b : Lwtf/opal/kt;
    //   955: invokevirtual z : ()Ljava/lang/Object;
    //   958: checkcast java/lang/Double
    //   961: invokevirtual intValue : ()I
    //   964: if_icmplt -> 1012
    //   967: goto -> 974
    //   970: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   973: athrow
    //   974: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   977: getfield field_1761 : Lnet/minecraft/class_636;
    //   980: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   983: getfield field_1724 : Lnet/minecraft/class_746;
    //   986: aload_0
    //   987: getfield v : Lnet/minecraft/class_1309;
    //   990: invokevirtual method_2918 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;)V
    //   993: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   996: getfield field_1724 : Lnet/minecraft/class_746;
    //   999: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   1002: invokevirtual method_6104 : (Lnet/minecraft/class_1268;)V
    //   1005: goto -> 1012
    //   1008: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1011: athrow
    //   1012: aload_0
    //   1013: iload #12
    //   1015: ifne -> 1299
    //   1018: getfield n : Lwtf/opal/ke;
    //   1021: invokevirtual z : ()Ljava/lang/Object;
    //   1024: checkcast java/lang/Boolean
    //   1027: invokevirtual booleanValue : ()Z
    //   1030: goto -> 1037
    //   1033: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1036: athrow
    //   1037: ifeq -> 1298
    //   1040: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1043: getfield field_1724 : Lnet/minecraft/class_746;
    //   1046: aload_0
    //   1047: getfield v : Lnet/minecraft/class_1309;
    //   1050: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   1053: f2d
    //   1054: aload_0
    //   1055: getfield D : Lwtf/opal/kt;
    //   1058: invokevirtual z : ()Ljava/lang/Object;
    //   1061: checkcast java/lang/Double
    //   1064: invokevirtual doubleValue : ()D
    //   1067: dcmpg
    //   1068: iload #12
    //   1070: ifne -> 1115
    //   1073: goto -> 1080
    //   1076: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1079: athrow
    //   1080: ifgt -> 1298
    //   1083: goto -> 1090
    //   1086: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1089: athrow
    //   1090: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1093: getfield field_1724 : Lnet/minecraft/class_746;
    //   1096: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   1099: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   1102: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1105: instanceof net/minecraft/class_1829
    //   1108: goto -> 1115
    //   1111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1114: athrow
    //   1115: iload #12
    //   1117: ifne -> 1153
    //   1120: ifeq -> 1298
    //   1123: goto -> 1130
    //   1126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1129: athrow
    //   1130: aload_0
    //   1131: getfield X : Lwtf/opal/ky;
    //   1134: invokevirtual z : ()Ljava/lang/Object;
    //   1137: checkcast wtf/opal/p4
    //   1140: getstatic wtf/opal/p4.WATCHDOG : Lwtf/opal/p4;
    //   1143: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1146: goto -> 1153
    //   1149: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1152: athrow
    //   1153: iload #12
    //   1155: ifne -> 1233
    //   1158: ifeq -> 1205
    //   1161: goto -> 1168
    //   1164: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1167: athrow
    //   1168: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1171: getfield field_1761 : Lnet/minecraft/class_636;
    //   1174: checkcast wtf/opal/mixin/ClientPlayerInteractionManagerAccessor
    //   1177: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1180: getfield field_1687 : Lnet/minecraft/class_638;
    //   1183: <illegal opcode> predict : ()Lnet/minecraft/class_7204;
    //   1188: invokeinterface callSendSequencedPacket : (Lnet/minecraft/class_638;Lnet/minecraft/class_7204;)V
    //   1193: aload_0
    //   1194: iconst_1
    //   1195: putfield d : Z
    //   1198: goto -> 1205
    //   1201: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1204: athrow
    //   1205: aload_0
    //   1206: iload #12
    //   1208: ifne -> 1294
    //   1211: getfield X : Lwtf/opal/ky;
    //   1214: invokevirtual z : ()Ljava/lang/Object;
    //   1217: checkcast wtf/opal/p4
    //   1220: getstatic wtf/opal/p4.VANILLA : Lwtf/opal/p4;
    //   1223: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1226: goto -> 1233
    //   1229: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1232: athrow
    //   1233: ifeq -> 1293
    //   1236: aload_0
    //   1237: iload #12
    //   1239: ifne -> 1294
    //   1242: goto -> 1249
    //   1245: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1248: athrow
    //   1249: getfield d : Z
    //   1252: ifne -> 1293
    //   1255: goto -> 1262
    //   1258: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1261: athrow
    //   1262: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1265: getfield field_1761 : Lnet/minecraft/class_636;
    //   1268: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1271: getfield field_1724 : Lnet/minecraft/class_746;
    //   1274: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   1277: invokevirtual method_2919 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1268;)Lnet/minecraft/class_1269;
    //   1280: pop
    //   1281: aload_0
    //   1282: iconst_1
    //   1283: putfield d : Z
    //   1286: goto -> 1293
    //   1289: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1292: athrow
    //   1293: aload_0
    //   1294: iconst_1
    //   1295: putfield L : Z
    //   1298: aload_0
    //   1299: aload_0
    //   1300: getfield v : Lnet/minecraft/class_1309;
    //   1303: putfield g : Lnet/minecraft/class_1309;
    //   1306: iload #12
    //   1308: ifeq -> 1914
    //   1311: aload_0
    //   1312: getfield v : Lnet/minecraft/class_1309;
    //   1315: iload #12
    //   1317: ifne -> 1392
    //   1320: goto -> 1327
    //   1323: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1326: athrow
    //   1327: ifnull -> 1378
    //   1330: goto -> 1337
    //   1333: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1336: athrow
    //   1337: aload_0
    //   1338: lload #10
    //   1340: iconst_1
    //   1341: anewarray java/lang/Object
    //   1344: dup_x2
    //   1345: dup_x2
    //   1346: pop
    //   1347: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1350: iconst_0
    //   1351: swap
    //   1352: aastore
    //   1353: invokevirtual C : ([Ljava/lang/Object;)Z
    //   1356: iload #12
    //   1358: ifne -> 1689
    //   1361: goto -> 1368
    //   1364: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1367: athrow
    //   1368: ifne -> 1676
    //   1371: goto -> 1378
    //   1374: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1377: athrow
    //   1378: aload_0
    //   1379: getfield n : Lwtf/opal/ke;
    //   1382: invokevirtual z : ()Ljava/lang/Object;
    //   1385: goto -> 1392
    //   1388: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1391: athrow
    //   1392: checkcast java/lang/Boolean
    //   1395: invokevirtual booleanValue : ()Z
    //   1398: iload #12
    //   1400: ifne -> 1438
    //   1403: ifeq -> 1675
    //   1406: goto -> 1413
    //   1409: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1412: athrow
    //   1413: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1416: getfield field_1724 : Lnet/minecraft/class_746;
    //   1419: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   1422: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   1425: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1428: instanceof net/minecraft/class_1829
    //   1431: goto -> 1438
    //   1434: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1437: athrow
    //   1438: iload #12
    //   1440: ifne -> 1464
    //   1443: ifeq -> 1675
    //   1446: goto -> 1453
    //   1449: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1452: athrow
    //   1453: aload_0
    //   1454: getfield d : Z
    //   1457: goto -> 1464
    //   1460: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1463: athrow
    //   1464: iload #12
    //   1466: ifne -> 1502
    //   1469: ifeq -> 1675
    //   1472: goto -> 1479
    //   1475: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1478: athrow
    //   1479: aload_0
    //   1480: getfield X : Lwtf/opal/ky;
    //   1483: invokevirtual z : ()Ljava/lang/Object;
    //   1486: checkcast wtf/opal/p4
    //   1489: getstatic wtf/opal/p4.WATCHDOG : Lwtf/opal/p4;
    //   1492: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1495: goto -> 1502
    //   1498: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1501: athrow
    //   1502: iload #12
    //   1504: ifne -> 1627
    //   1507: ifeq -> 1592
    //   1510: goto -> 1517
    //   1513: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1516: athrow
    //   1517: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1520: getfield field_1724 : Lnet/minecraft/class_746;
    //   1523: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   1526: getfield field_7545 : I
    //   1529: istore #13
    //   1531: iload #13
    //   1533: iconst_1
    //   1534: iadd
    //   1535: sipush #17856
    //   1538: ldc2_w 8216034943212228046
    //   1541: lload_2
    //   1542: lxor
    //   1543: <illegal opcode> g : (IJ)I
    //   1548: irem
    //   1549: istore #14
    //   1551: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1554: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1557: new net/minecraft/class_2868
    //   1560: dup
    //   1561: iload #14
    //   1563: invokespecial <init> : (I)V
    //   1566: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1569: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1572: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1575: new net/minecraft/class_2868
    //   1578: dup
    //   1579: iload #13
    //   1581: invokespecial <init> : (I)V
    //   1584: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1587: iload #12
    //   1589: ifeq -> 1665
    //   1592: aload_0
    //   1593: iload #12
    //   1595: ifne -> 1671
    //   1598: goto -> 1605
    //   1601: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1604: athrow
    //   1605: getfield X : Lwtf/opal/ky;
    //   1608: invokevirtual z : ()Ljava/lang/Object;
    //   1611: checkcast wtf/opal/p4
    //   1614: getstatic wtf/opal/p4.VANILLA : Lwtf/opal/p4;
    //   1617: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1620: goto -> 1627
    //   1623: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1626: athrow
    //   1627: ifeq -> 1665
    //   1630: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1633: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1636: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   1639: new net/minecraft/class_2846
    //   1642: dup
    //   1643: getstatic net/minecraft/class_2846$class_2847.field_12974 : Lnet/minecraft/class_2846$class_2847;
    //   1646: getstatic net/minecraft/class_2338.field_10980 : Lnet/minecraft/class_2338;
    //   1649: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
    //   1652: invokespecial <init> : (Lnet/minecraft/class_2846$class_2847;Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)V
    //   1655: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   1658: goto -> 1665
    //   1661: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1664: athrow
    //   1665: aload_0
    //   1666: iconst_0
    //   1667: putfield d : Z
    //   1670: aload_0
    //   1671: iconst_0
    //   1672: putfield z : I
    //   1675: return
    //   1676: aload_0
    //   1677: getfield n : Lwtf/opal/ke;
    //   1680: invokevirtual z : ()Ljava/lang/Object;
    //   1683: checkcast java/lang/Boolean
    //   1686: invokevirtual booleanValue : ()Z
    //   1689: iload #12
    //   1691: ifne -> 1739
    //   1694: ifeq -> 1914
    //   1697: goto -> 1704
    //   1700: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1703: athrow
    //   1704: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1707: getfield field_1724 : Lnet/minecraft/class_746;
    //   1710: aload_0
    //   1711: getfield v : Lnet/minecraft/class_1309;
    //   1714: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   1717: f2d
    //   1718: aload_0
    //   1719: getfield D : Lwtf/opal/kt;
    //   1722: invokevirtual z : ()Ljava/lang/Object;
    //   1725: checkcast java/lang/Double
    //   1728: invokevirtual doubleValue : ()D
    //   1731: dcmpg
    //   1732: goto -> 1739
    //   1735: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1738: athrow
    //   1739: iload #12
    //   1741: ifne -> 1779
    //   1744: ifgt -> 1914
    //   1747: goto -> 1754
    //   1750: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1753: athrow
    //   1754: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1757: getfield field_1724 : Lnet/minecraft/class_746;
    //   1760: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   1763: invokevirtual method_7391 : ()Lnet/minecraft/class_1799;
    //   1766: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1769: instanceof net/minecraft/class_1829
    //   1772: goto -> 1779
    //   1775: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1778: athrow
    //   1779: iload #12
    //   1781: ifne -> 1817
    //   1784: ifeq -> 1914
    //   1787: goto -> 1794
    //   1790: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1793: athrow
    //   1794: aload_0
    //   1795: getfield X : Lwtf/opal/ky;
    //   1798: invokevirtual z : ()Ljava/lang/Object;
    //   1801: checkcast wtf/opal/p4
    //   1804: getstatic wtf/opal/p4.WATCHDOG : Lwtf/opal/p4;
    //   1807: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1810: goto -> 1817
    //   1813: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1816: athrow
    //   1817: iload #12
    //   1819: ifne -> 1851
    //   1822: ifeq -> 1914
    //   1825: goto -> 1832
    //   1828: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1831: athrow
    //   1832: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1835: getfield field_1724 : Lnet/minecraft/class_746;
    //   1838: invokevirtual method_31548 : ()Lnet/minecraft/class_1661;
    //   1841: getfield field_7545 : I
    //   1844: goto -> 1851
    //   1847: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1850: athrow
    //   1851: istore #13
    //   1853: iload #13
    //   1855: iconst_1
    //   1856: iadd
    //   1857: sipush #17856
    //   1860: ldc2_w 8216034943212228046
    //   1863: lload_2
    //   1864: lxor
    //   1865: <illegal opcode> g : (IJ)I
    //   1870: irem
    //   1871: istore #14
    //   1873: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1876: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1879: new net/minecraft/class_2868
    //   1882: dup
    //   1883: iload #14
    //   1885: invokespecial <init> : (I)V
    //   1888: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1891: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1894: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   1897: new net/minecraft/class_2868
    //   1900: dup
    //   1901: iload #13
    //   1903: invokespecial <init> : (I)V
    //   1906: invokevirtual method_52787 : (Lnet/minecraft/class_2596;)V
    //   1909: aload_0
    //   1910: iconst_0
    //   1911: putfield z : I
    //   1914: return
    // Exception table:
    //   from	to	target	type
    //   43	79	82	wtf/opal/x5
    //   48	90	93	wtf/opal/x5
    //   116	134	137	wtf/opal/x5
    //   126	150	153	wtf/opal/x5
    //   164	184	184	wtf/opal/x5
    //   191	234	237	wtf/opal/x5
    //   228	247	250	wtf/opal/x5
    //   241	260	263	wtf/opal/x5
    //   254	288	291	wtf/opal/x5
    //   267	311	314	wtf/opal/x5
    //   324	339	342	wtf/opal/x5
    //   329	356	359	wtf/opal/x5
    //   346	364	367	wtf/opal/x5
    //   380	415	418	wtf/opal/x5
    //   396	428	431	wtf/opal/x5
    //   422	441	444	wtf/opal/x5
    //   435	449	452	wtf/opal/x5
    //   456	469	472	wtf/opal/x5
    //   483	572	575	wtf/opal/x5
    //   492	622	625	wtf/opal/x5
    //   579	632	635	wtf/opal/x5
    //   756	766	769	wtf/opal/x5
    //   775	792	795	wtf/opal/x5
    //   800	840	843	wtf/opal/x5
    //   879	904	907	wtf/opal/x5
    //   901	918	921	wtf/opal/x5
    //   911	928	931	wtf/opal/x5
    //   925	944	947	wtf/opal/x5
    //   935	967	970	wtf/opal/x5
    //   951	1005	1008	wtf/opal/x5
    //   1012	1030	1033	wtf/opal/x5
    //   1037	1073	1076	wtf/opal/x5
    //   1040	1083	1086	wtf/opal/x5
    //   1080	1108	1111	wtf/opal/x5
    //   1115	1123	1126	wtf/opal/x5
    //   1120	1146	1149	wtf/opal/x5
    //   1153	1161	1164	wtf/opal/x5
    //   1158	1198	1201	wtf/opal/x5
    //   1205	1226	1229	wtf/opal/x5
    //   1233	1242	1245	wtf/opal/x5
    //   1236	1255	1258	wtf/opal/x5
    //   1249	1286	1289	wtf/opal/x5
    //   1299	1320	1323	wtf/opal/x5
    //   1311	1330	1333	wtf/opal/x5
    //   1327	1361	1364	wtf/opal/x5
    //   1337	1371	1374	wtf/opal/x5
    //   1368	1385	1388	wtf/opal/x5
    //   1392	1406	1409	wtf/opal/x5
    //   1403	1431	1434	wtf/opal/x5
    //   1438	1446	1449	wtf/opal/x5
    //   1443	1457	1460	wtf/opal/x5
    //   1464	1472	1475	wtf/opal/x5
    //   1469	1495	1498	wtf/opal/x5
    //   1502	1510	1513	wtf/opal/x5
    //   1551	1598	1601	wtf/opal/x5
    //   1592	1620	1623	wtf/opal/x5
    //   1627	1658	1661	wtf/opal/x5
    //   1689	1697	1700	wtf/opal/x5
    //   1694	1732	1735	wtf/opal/x5
    //   1739	1747	1750	wtf/opal/x5
    //   1744	1772	1775	wtf/opal/x5
    //   1779	1787	1790	wtf/opal/x5
    //   1784	1810	1813	wtf/opal/x5
    //   1817	1825	1828	wtf/opal/x5
    //   1822	1844	1847	wtf/opal/x5
  }
  
  private static class_2596 lambda$new$0(int paramInt) {
    return (class_2596)new class_2886(class_1268.field_5808, paramInt);
  }
  
  public static void H(int paramInt) {
    m = paramInt;
  }
  
  public static int y() {
    return m;
  }
  
  public static int F() {
    int i = y();
    try {
      if (i == 0)
        return 26; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  static {
    H(121);
    long l = bb ^ 0x5AF2858CCCEDL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[32];
    boolean bool = false;
    String str;
    int i = (str = "è{Þbã«\007_!\024O3û3S{æô[v©àó£^ã\030â¤\037qiî;oÄq\004\001,hÿ>[Ð>Ùá0àNÓPÀ÷à½Êþ3J Iº{ñT?ñç¯+3¤×n@\026\\;²\r\030BZ$Ú\007 Pbj·\n_\026ËbQ\000R¦\017ìÊå\013È/KÑýß\004ãñ Â­àel|;ß1lº\022Ëï¯~Ïµû\027TBZ\024V4]\020\001@Ñ¤NÓÃ¼Èyæd¯lT:\0302\"Í¤ÃÛíìvéêÒðÏt¦\027çxn( ÞdQ\005WË!>\024ÌtÍJD+*;Êw×\036\tçCsyzí È\000\032«çw\034nÔà\t¦\032'E\"Ù j\023P\0209Î{S\004$Ñ(«} ÝDå?\026-=\0045¹3Éfà¾\003(u/d}Î,,öl(Ë y\032B;á\026öF\017\033þÃtcüÏ2-¼xb\nñ\006ÍDÊ¡¯1M¤i\005\020¹I Þ±áÚ\bg8Î¸ôëª\020n\033\b?M¶\002ñQÞ£\026.Ð`½¥âiÚÕ\022p<iÀßX4\034H\004\t\\\025ÌÛ\t?#9sU}\0242(«\n>É\032ðÚN\026pjês¾2¸ýDÑocEc{à\003l\r\021\025¤ÆVd\026QË\031ò\021Ý]Ù%EÐ:3sxÆ/¯\030²1@µ¹i\tý-?\003/\007á$~i2P¹æ{\020î;¦üä¬ê\007\016+xU\030º¿È{ìP~üº vBÓy\022Æåíì)! \020J<Ü.a¤Lm¿Ý=¨å>û ôzsBõ)c§ÊË®\004ef\033\\÷Õ@s è mÉ\031Ç}±m\001\033Åþd¿Z±rfNæ\003\013Ñcªbèé¯ O¯ë¿(q«»A÷:øï&\037Às¹«ÚîÉôWbls¯´\030ïS5\036Êys¦z$ý­¨ýdÂ\002S­Ì\020Å3ÝJ!_ÀZ9ò S\030\016\033@çÎÜgj,¸ÁVoxÇi³1 qf\006úy>Õ¹e\002!'­×\003;ßK/ùâÚV\037@\020ËúÈp.Ð\021\004×oÞîÕÙ\002\020mæiºRÙà\034fÄJ¡I) l:Ò\016\004\f ß#lðÿµ00ÀZ\n\0068¸ÁQ&¬LU(,ËÙe0\0176\000×0ÌÏçzÃ&\035\003S bjX\031!)ØÏK8Íâ6ñâ y\021\bEe³Ä³r½;`Î\016NÜsª#ê1â{¦wîmä").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x45F8;
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
        throw new RuntimeException("wtf/opal/q", exception);
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
    //   66: ldc_w 'wtf/opal/q'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xDD0;
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
        throw new RuntimeException("wtf/opal/q", exception);
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
    //   66: ldc_w 'wtf/opal/q'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\q.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */