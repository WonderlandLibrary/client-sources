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

public final class jl extends d {
  private final ky<lh> q;
  
  private final kd x;
  
  private final kd a;
  
  private final ke I;
  
  private final ke w;
  
  private final ke f;
  
  private final kt Z;
  
  private final kt E;
  
  private final kt L;
  
  private final kt X;
  
  private final kt k;
  
  public final k6 l;
  
  private final pa U;
  
  private final bu n;
  
  private final dc D;
  
  private List<d> g;
  
  private final gm<uj> T;
  
  private static final long b = on.a(5498313026241222786L, -771899406884381431L, MethodHandles.lookup().lookupClass()).a(90172198898465L);
  
  private static final String[] d;
  
  private static final String[] m;
  
  private static final Map o = new HashMap<>(13);
  
  private static final long[] p;
  
  private static final Integer[] r;
  
  private static final Map s;
  
  public jl(int paramInt1, int paramInt2, int paramInt3) {
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
    //   23: getstatic wtf/opal/jl.b : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 103790273669600
    //   35: lxor
    //   36: lstore #6
    //   38: dup2
    //   39: ldc2_w 106295401522418
    //   42: lxor
    //   43: lstore #8
    //   45: dup2
    //   46: ldc2_w 69932829342192
    //   49: lxor
    //   50: dup2
    //   51: bipush #56
    //   53: lushr
    //   54: l2i
    //   55: istore #10
    //   57: dup2
    //   58: bipush #8
    //   60: lshl
    //   61: bipush #8
    //   63: lushr
    //   64: lstore #11
    //   66: pop2
    //   67: dup2
    //   68: ldc2_w 28031516220060
    //   71: lxor
    //   72: lstore #13
    //   74: dup2
    //   75: ldc2_w 60473322849225
    //   78: lxor
    //   79: lstore #15
    //   81: pop2
    //   82: aload_0
    //   83: sipush #2509
    //   86: ldc2_w 2265723366025029666
    //   89: lload #4
    //   91: lxor
    //   92: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   97: lload #15
    //   99: sipush #16157
    //   102: ldc2_w 2593845751110491901
    //   105: lload #4
    //   107: lxor
    //   108: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   113: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   116: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   119: aload_0
    //   120: new wtf/opal/ky
    //   123: dup
    //   124: sipush #12876
    //   127: ldc2_w 6409239977657553835
    //   130: lload #4
    //   132: lxor
    //   133: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   138: getstatic wtf/opal/lh.RIGHT_BAR : Lwtf/opal/lh;
    //   141: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   144: putfield q : Lwtf/opal/ky;
    //   147: aload_0
    //   148: new wtf/opal/kd
    //   151: dup
    //   152: sipush #31351
    //   155: ldc2_w 5794618172045728665
    //   158: lload #4
    //   160: lxor
    //   161: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   166: iconst_4
    //   167: anewarray wtf/opal/ke
    //   170: dup
    //   171: iconst_0
    //   172: new wtf/opal/ke
    //   175: dup
    //   176: sipush #15847
    //   179: ldc2_w 8591238680331738113
    //   182: lload #4
    //   184: lxor
    //   185: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   190: iconst_1
    //   191: invokespecial <init> : (Ljava/lang/String;Z)V
    //   194: aastore
    //   195: dup
    //   196: iconst_1
    //   197: new wtf/opal/ke
    //   200: dup
    //   201: sipush #1361
    //   204: ldc2_w 5398391233477272762
    //   207: lload #4
    //   209: lxor
    //   210: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   215: iconst_1
    //   216: invokespecial <init> : (Ljava/lang/String;Z)V
    //   219: aastore
    //   220: dup
    //   221: iconst_2
    //   222: new wtf/opal/ke
    //   225: dup
    //   226: sipush #23057
    //   229: ldc2_w 3393561882017381360
    //   232: lload #4
    //   234: lxor
    //   235: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   240: iconst_1
    //   241: invokespecial <init> : (Ljava/lang/String;Z)V
    //   244: aastore
    //   245: dup
    //   246: iconst_3
    //   247: new wtf/opal/ke
    //   250: dup
    //   251: sipush #31638
    //   254: ldc2_w 661821558845774438
    //   257: lload #4
    //   259: lxor
    //   260: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   265: iconst_1
    //   266: invokespecial <init> : (Ljava/lang/String;Z)V
    //   269: aastore
    //   270: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   273: putfield x : Lwtf/opal/kd;
    //   276: invokestatic S : ()Ljava/lang/String;
    //   279: aload_0
    //   280: new wtf/opal/kd
    //   283: dup
    //   284: sipush #2648
    //   287: ldc2_w 446537010987035556
    //   290: lload #4
    //   292: lxor
    //   293: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   298: sipush #15930
    //   301: ldc2_w 7225682747086152554
    //   304: lload #4
    //   306: lxor
    //   307: <illegal opcode> f : (IJ)I
    //   312: anewarray wtf/opal/ke
    //   315: dup
    //   316: iconst_0
    //   317: new wtf/opal/ke
    //   320: dup
    //   321: sipush #26923
    //   324: ldc2_w 8934524985762154696
    //   327: lload #4
    //   329: lxor
    //   330: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   335: iconst_0
    //   336: invokespecial <init> : (Ljava/lang/String;Z)V
    //   339: aastore
    //   340: dup
    //   341: iconst_1
    //   342: new wtf/opal/ke
    //   345: dup
    //   346: sipush #4602
    //   349: ldc2_w 3446970652767645699
    //   352: lload #4
    //   354: lxor
    //   355: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   360: iconst_0
    //   361: invokespecial <init> : (Ljava/lang/String;Z)V
    //   364: aastore
    //   365: dup
    //   366: iconst_2
    //   367: new wtf/opal/ke
    //   370: dup
    //   371: sipush #20642
    //   374: ldc2_w 6793888099598807381
    //   377: lload #4
    //   379: lxor
    //   380: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   385: iconst_0
    //   386: invokespecial <init> : (Ljava/lang/String;Z)V
    //   389: aastore
    //   390: dup
    //   391: iconst_3
    //   392: new wtf/opal/ke
    //   395: dup
    //   396: sipush #11269
    //   399: ldc2_w 3118531587670461920
    //   402: lload #4
    //   404: lxor
    //   405: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   410: iconst_0
    //   411: invokespecial <init> : (Ljava/lang/String;Z)V
    //   414: aastore
    //   415: dup
    //   416: iconst_4
    //   417: new wtf/opal/ke
    //   420: dup
    //   421: sipush #19577
    //   424: ldc2_w 6306128371172191619
    //   427: lload #4
    //   429: lxor
    //   430: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   435: iconst_0
    //   436: invokespecial <init> : (Ljava/lang/String;Z)V
    //   439: aastore
    //   440: dup
    //   441: iconst_5
    //   442: new wtf/opal/ke
    //   445: dup
    //   446: sipush #31414
    //   449: ldc2_w 6260914289432370015
    //   452: lload #4
    //   454: lxor
    //   455: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   460: iconst_0
    //   461: invokespecial <init> : (Ljava/lang/String;Z)V
    //   464: aastore
    //   465: dup
    //   466: sipush #6184
    //   469: ldc2_w 4839058448039511420
    //   472: lload #4
    //   474: lxor
    //   475: <illegal opcode> f : (IJ)I
    //   480: new wtf/opal/ke
    //   483: dup
    //   484: sipush #32054
    //   487: ldc2_w 8823057977771760832
    //   490: lload #4
    //   492: lxor
    //   493: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   498: iconst_0
    //   499: invokespecial <init> : (Ljava/lang/String;Z)V
    //   502: aastore
    //   503: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   506: putfield a : Lwtf/opal/kd;
    //   509: aload_0
    //   510: new wtf/opal/ke
    //   513: dup
    //   514: sipush #31442
    //   517: ldc2_w 3430765096335514422
    //   520: lload #4
    //   522: lxor
    //   523: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   528: iconst_1
    //   529: invokespecial <init> : (Ljava/lang/String;Z)V
    //   532: putfield I : Lwtf/opal/ke;
    //   535: aload_0
    //   536: new wtf/opal/ke
    //   539: dup
    //   540: sipush #19202
    //   543: ldc2_w 5562626121220958972
    //   546: lload #4
    //   548: lxor
    //   549: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   554: iconst_1
    //   555: invokespecial <init> : (Ljava/lang/String;Z)V
    //   558: putfield w : Lwtf/opal/ke;
    //   561: astore #17
    //   563: aload_0
    //   564: new wtf/opal/ke
    //   567: dup
    //   568: sipush #13264
    //   571: ldc2_w 8223236702547464744
    //   574: lload #4
    //   576: lxor
    //   577: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   582: iconst_1
    //   583: invokespecial <init> : (Ljava/lang/String;Z)V
    //   586: putfield f : Lwtf/opal/ke;
    //   589: aload_0
    //   590: new wtf/opal/kt
    //   593: dup
    //   594: sipush #24387
    //   597: ldc2_w 8233573985480195762
    //   600: lload #4
    //   602: lxor
    //   603: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   608: ldc2_w 80.0
    //   611: dconst_0
    //   612: ldc2_w 255.0
    //   615: ldc2_w 5.0
    //   618: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   621: putfield Z : Lwtf/opal/kt;
    //   624: aload_0
    //   625: new wtf/opal/kt
    //   628: dup
    //   629: sipush #27114
    //   632: ldc2_w 3871978763166943257
    //   635: lload #4
    //   637: lxor
    //   638: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   643: ldc2_w 12.0
    //   646: ldc2_w 10.0
    //   649: ldc2_w 14.0
    //   652: ldc2_w 0.25
    //   655: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   658: putfield E : Lwtf/opal/kt;
    //   661: aload_0
    //   662: new wtf/opal/kt
    //   665: dup
    //   666: sipush #8427
    //   669: ldc2_w 9037668899140223233
    //   672: lload #4
    //   674: lxor
    //   675: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   680: ldc2_w 6.0
    //   683: dconst_1
    //   684: ldc2_w 20.0
    //   687: dconst_1
    //   688: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   691: putfield L : Lwtf/opal/kt;
    //   694: aload_0
    //   695: new wtf/opal/kt
    //   698: dup
    //   699: sipush #26410
    //   702: ldc2_w 5567003552675661527
    //   705: lload #4
    //   707: lxor
    //   708: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   713: ldc2_w 20.0
    //   716: dconst_1
    //   717: ldc2_w 100.0
    //   720: dconst_1
    //   721: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   724: putfield X : Lwtf/opal/kt;
    //   727: aload_0
    //   728: new wtf/opal/kt
    //   731: dup
    //   732: sipush #28321
    //   735: ldc2_w 6762859959965650765
    //   738: lload #4
    //   740: lxor
    //   741: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   746: dconst_1
    //   747: ldc2_w 0.5
    //   750: ldc2_w 1.5
    //   753: ldc2_w 0.01
    //   756: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   759: putfield k : Lwtf/opal/kt;
    //   762: aload_0
    //   763: new wtf/opal/k6
    //   766: dup
    //   767: iload #10
    //   769: i2b
    //   770: fconst_1
    //   771: lload #11
    //   773: fconst_0
    //   774: invokespecial <init> : (BFJF)V
    //   777: putfield l : Lwtf/opal/k6;
    //   780: aload_0
    //   781: iconst_0
    //   782: anewarray java/lang/Object
    //   785: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   788: iconst_0
    //   789: anewarray java/lang/Object
    //   792: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   795: putfield U : Lwtf/opal/pa;
    //   798: aload_0
    //   799: iconst_0
    //   800: anewarray java/lang/Object
    //   803: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   806: iconst_0
    //   807: anewarray java/lang/Object
    //   810: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   813: putfield n : Lwtf/opal/bu;
    //   816: aload_0
    //   817: iconst_0
    //   818: anewarray java/lang/Object
    //   821: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   824: iconst_0
    //   825: anewarray java/lang/Object
    //   828: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/dc;
    //   831: putfield D : Lwtf/opal/dc;
    //   834: aload_0
    //   835: aload_0
    //   836: <illegal opcode> H : (Lwtf/opal/jl;)Lwtf/opal/gm;
    //   841: putfield T : Lwtf/opal/gm;
    //   844: aload_0
    //   845: getfield Z : Lwtf/opal/kt;
    //   848: aload_0
    //   849: getfield w : Lwtf/opal/ke;
    //   852: aload_0
    //   853: <illegal opcode> test : (Lwtf/opal/jl;)Ljava/util/function/Predicate;
    //   858: lload #6
    //   860: dup2_x1
    //   861: pop2
    //   862: iconst_3
    //   863: anewarray java/lang/Object
    //   866: dup_x1
    //   867: swap
    //   868: iconst_2
    //   869: swap
    //   870: aastore
    //   871: dup_x2
    //   872: dup_x2
    //   873: pop
    //   874: invokestatic valueOf : (J)Ljava/lang/Long;
    //   877: iconst_1
    //   878: swap
    //   879: aastore
    //   880: dup_x1
    //   881: swap
    //   882: iconst_0
    //   883: swap
    //   884: aastore
    //   885: invokevirtual C : ([Ljava/lang/Object;)V
    //   888: aload_0
    //   889: sipush #23067
    //   892: ldc2_w 2998819846103058246
    //   895: lload #4
    //   897: lxor
    //   898: <illegal opcode> f : (IJ)I
    //   903: anewarray wtf/opal/k3
    //   906: dup
    //   907: iconst_0
    //   908: aload_0
    //   909: getfield l : Lwtf/opal/k6;
    //   912: aastore
    //   913: dup
    //   914: iconst_1
    //   915: aload_0
    //   916: getfield q : Lwtf/opal/ky;
    //   919: aastore
    //   920: dup
    //   921: iconst_2
    //   922: aload_0
    //   923: getfield x : Lwtf/opal/kd;
    //   926: aastore
    //   927: dup
    //   928: iconst_3
    //   929: aload_0
    //   930: getfield a : Lwtf/opal/kd;
    //   933: aastore
    //   934: dup
    //   935: iconst_4
    //   936: aload_0
    //   937: getfield I : Lwtf/opal/ke;
    //   940: aastore
    //   941: dup
    //   942: iconst_5
    //   943: aload_0
    //   944: getfield w : Lwtf/opal/ke;
    //   947: aastore
    //   948: dup
    //   949: sipush #4116
    //   952: ldc2_w 869127285296762182
    //   955: lload #4
    //   957: lxor
    //   958: <illegal opcode> f : (IJ)I
    //   963: aload_0
    //   964: getfield Z : Lwtf/opal/kt;
    //   967: aastore
    //   968: dup
    //   969: sipush #12061
    //   972: ldc2_w 8288906997113762376
    //   975: lload #4
    //   977: lxor
    //   978: <illegal opcode> f : (IJ)I
    //   983: aload_0
    //   984: getfield f : Lwtf/opal/ke;
    //   987: aastore
    //   988: dup
    //   989: sipush #25867
    //   992: ldc2_w 894972120605030493
    //   995: lload #4
    //   997: lxor
    //   998: <illegal opcode> f : (IJ)I
    //   1003: aload_0
    //   1004: getfield k : Lwtf/opal/kt;
    //   1007: aastore
    //   1008: dup
    //   1009: sipush #1562
    //   1012: ldc2_w 5457688649553976139
    //   1015: lload #4
    //   1017: lxor
    //   1018: <illegal opcode> f : (IJ)I
    //   1023: aload_0
    //   1024: getfield E : Lwtf/opal/kt;
    //   1027: aastore
    //   1028: dup
    //   1029: sipush #1658
    //   1032: ldc2_w 1596420691408362278
    //   1035: lload #4
    //   1037: lxor
    //   1038: <illegal opcode> f : (IJ)I
    //   1043: aload_0
    //   1044: getfield L : Lwtf/opal/kt;
    //   1047: aastore
    //   1048: dup
    //   1049: sipush #30650
    //   1052: ldc2_w 4762833467781868269
    //   1055: lload #4
    //   1057: lxor
    //   1058: <illegal opcode> f : (IJ)I
    //   1063: aload_0
    //   1064: getfield X : Lwtf/opal/kt;
    //   1067: aastore
    //   1068: lload #13
    //   1070: dup2_x1
    //   1071: pop2
    //   1072: iconst_2
    //   1073: anewarray java/lang/Object
    //   1076: dup_x1
    //   1077: swap
    //   1078: iconst_1
    //   1079: swap
    //   1080: aastore
    //   1081: dup_x2
    //   1082: dup_x2
    //   1083: pop
    //   1084: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1087: iconst_0
    //   1088: swap
    //   1089: aastore
    //   1090: invokevirtual o : ([Ljava/lang/Object;)V
    //   1093: aload_0
    //   1094: iconst_1
    //   1095: lload #8
    //   1097: iconst_2
    //   1098: anewarray java/lang/Object
    //   1101: dup_x2
    //   1102: dup_x2
    //   1103: pop
    //   1104: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1107: iconst_1
    //   1108: swap
    //   1109: aastore
    //   1110: dup_x1
    //   1111: swap
    //   1112: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1115: iconst_0
    //   1116: swap
    //   1117: aastore
    //   1118: invokevirtual Q : ([Ljava/lang/Object;)V
    //   1121: invokestatic D : ()[Lwtf/opal/d;
    //   1124: ifnull -> 1140
    //   1127: ldc_w 'zn4zeb'
    //   1130: invokestatic g : (Ljava/lang/String;)V
    //   1133: goto -> 1140
    //   1136: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1139: athrow
    //   1140: return
    // Exception table:
    //   from	to	target	type
    //   563	1133	1136	wtf/opal/x5
  }
  
  private void M(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: pop
    //   2: iconst_0
    //   3: anewarray java/lang/Object
    //   6: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   9: iconst_0
    //   10: anewarray java/lang/Object
    //   13: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   16: ldc wtf/opal/jt
    //   18: iconst_1
    //   19: anewarray java/lang/Object
    //   22: dup_x1
    //   23: swap
    //   24: iconst_0
    //   25: swap
    //   26: aastore
    //   27: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   30: checkcast wtf/opal/jt
    //   33: astore_2
    //   34: aload_0
    //   35: iconst_0
    //   36: anewarray java/lang/Object
    //   39: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   42: iconst_0
    //   43: anewarray java/lang/Object
    //   46: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   49: iconst_0
    //   50: anewarray java/lang/Object
    //   53: invokevirtual g : ([Ljava/lang/Object;)Ljava/util/Collection;
    //   56: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   61: aload_0
    //   62: <illegal opcode> test : (Lwtf/opal/jl;)Ljava/util/function/Predicate;
    //   67: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   72: aload_0
    //   73: aload_2
    //   74: <illegal opcode> applyAsDouble : (Lwtf/opal/jl;Lwtf/opal/jt;)Ljava/util/function/ToDoubleFunction;
    //   79: invokestatic comparingDouble : (Ljava/util/function/ToDoubleFunction;)Ljava/util/Comparator;
    //   82: invokeinterface reversed : ()Ljava/util/Comparator;
    //   87: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
    //   92: invokestatic toList : ()Ljava/util/stream/Collector;
    //   95: invokeinterface collect : (Ljava/util/stream/Collector;)Ljava/lang/Object;
    //   100: checkcast java/util/List
    //   103: putfield g : Ljava/util/List;
    //   106: return
  }
  
  public float z(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jl.b : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 40966109011052
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 41416983873852
    //   30: lxor
    //   31: lstore #6
    //   33: pop2
    //   34: iconst_0
    //   35: anewarray java/lang/Object
    //   38: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   41: iconst_0
    //   42: anewarray java/lang/Object
    //   45: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   48: iconst_0
    //   49: anewarray java/lang/Object
    //   52: invokevirtual g : ([Ljava/lang/Object;)Ljava/util/Collection;
    //   55: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   60: aload_0
    //   61: <illegal opcode> test : (Lwtf/opal/jl;)Ljava/util/function/Predicate;
    //   66: invokeinterface filter : (Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
    //   71: invokeinterface toList : ()Ljava/util/List;
    //   76: astore #9
    //   78: iconst_0
    //   79: anewarray java/lang/Object
    //   82: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   85: iconst_0
    //   86: anewarray java/lang/Object
    //   89: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   92: ldc wtf/opal/jt
    //   94: iconst_1
    //   95: anewarray java/lang/Object
    //   98: dup_x1
    //   99: swap
    //   100: iconst_0
    //   101: swap
    //   102: aastore
    //   103: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   106: checkcast wtf/opal/jt
    //   109: astore #10
    //   111: aload #10
    //   113: lload #4
    //   115: iconst_1
    //   116: anewarray java/lang/Object
    //   119: dup_x2
    //   120: dup_x2
    //   121: pop
    //   122: invokestatic valueOf : (J)Ljava/lang/Long;
    //   125: iconst_0
    //   126: swap
    //   127: aastore
    //   128: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   131: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   134: invokevirtual equals : (Ljava/lang/Object;)Z
    //   137: istore #11
    //   139: ldc_w 10.0
    //   142: fstore #12
    //   144: invokestatic S : ()Ljava/lang/String;
    //   147: aload #9
    //   149: invokeinterface iterator : ()Ljava/util/Iterator;
    //   154: astore #13
    //   156: astore #8
    //   158: aload #13
    //   160: invokeinterface hasNext : ()Z
    //   165: ifeq -> 270
    //   168: aload #13
    //   170: invokeinterface next : ()Ljava/lang/Object;
    //   175: checkcast wtf/opal/d
    //   178: astore #14
    //   180: fload #12
    //   182: aload #8
    //   184: ifnonnull -> 272
    //   187: aload #14
    //   189: iconst_0
    //   190: anewarray java/lang/Object
    //   193: invokevirtual t : ([Ljava/lang/Object;)Lwtf/opal/di;
    //   196: lload #6
    //   198: iconst_1
    //   199: anewarray java/lang/Object
    //   202: dup_x2
    //   203: dup_x2
    //   204: pop
    //   205: invokestatic valueOf : (J)Ljava/lang/Long;
    //   208: iconst_0
    //   209: swap
    //   210: aastore
    //   211: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   214: invokevirtual floatValue : ()F
    //   217: aload_0
    //   218: getfield E : Lwtf/opal/kt;
    //   221: invokevirtual z : ()Ljava/lang/Object;
    //   224: checkcast java/lang/Double
    //   227: invokevirtual floatValue : ()F
    //   230: iload #11
    //   232: aload #8
    //   234: ifnonnull -> 255
    //   237: goto -> 244
    //   240: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   243: athrow
    //   244: ifeq -> 258
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: iconst_1
    //   255: goto -> 259
    //   258: iconst_0
    //   259: i2f
    //   260: fsub
    //   261: fmul
    //   262: fadd
    //   263: fstore #12
    //   265: aload #8
    //   267: ifnull -> 158
    //   270: fload #12
    //   272: freturn
    // Exception table:
    //   from	to	target	type
    //   180	237	240	wtf/opal/x5
    //   187	247	250	wtf/opal/x5
  }
  
  private boolean lambda$getHeight$5(d paramd) {
    // Byte code:
    //   0: getstatic wtf/opal/jl.b : J
    //   3: ldc2_w 52890209886212
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 101474180086451
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: astore #6
    //   22: aload_1
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual P : ([Ljava/lang/Object;)Z
    //   30: aload #6
    //   32: ifnonnull -> 60
    //   35: ifne -> 189
    //   38: goto -> 45
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: aload_1
    //   46: iconst_0
    //   47: anewarray java/lang/Object
    //   50: invokevirtual D : ([Ljava/lang/Object;)Z
    //   53: goto -> 60
    //   56: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   59: athrow
    //   60: aload #6
    //   62: ifnonnull -> 170
    //   65: ifne -> 123
    //   68: goto -> 75
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: aload_1
    //   76: iconst_0
    //   77: anewarray java/lang/Object
    //   80: invokevirtual t : ([Ljava/lang/Object;)Lwtf/opal/di;
    //   83: lload #4
    //   85: iconst_1
    //   86: anewarray java/lang/Object
    //   89: dup_x2
    //   90: dup_x2
    //   91: pop
    //   92: invokestatic valueOf : (J)Ljava/lang/Long;
    //   95: iconst_0
    //   96: swap
    //   97: aastore
    //   98: invokevirtual H : ([Ljava/lang/Object;)Z
    //   101: aload #6
    //   103: ifnonnull -> 170
    //   106: goto -> 113
    //   109: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   112: athrow
    //   113: ifne -> 189
    //   116: goto -> 123
    //   119: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   122: athrow
    //   123: aload_0
    //   124: getfield a : Lwtf/opal/kd;
    //   127: aload_1
    //   128: iconst_0
    //   129: anewarray java/lang/Object
    //   132: invokevirtual X : ([Ljava/lang/Object;)Lwtf/opal/kn;
    //   135: iconst_0
    //   136: anewarray java/lang/Object
    //   139: invokevirtual S : ([Ljava/lang/Object;)Ljava/lang/String;
    //   142: iconst_1
    //   143: anewarray java/lang/Object
    //   146: dup_x1
    //   147: swap
    //   148: iconst_0
    //   149: swap
    //   150: aastore
    //   151: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   154: invokevirtual z : ()Ljava/lang/Object;
    //   157: checkcast java/lang/Boolean
    //   160: invokevirtual booleanValue : ()Z
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: aload #6
    //   172: ifnonnull -> 186
    //   175: ifne -> 189
    //   178: goto -> 185
    //   181: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: iconst_1
    //   186: goto -> 190
    //   189: iconst_0
    //   190: ireturn
    // Exception table:
    //   from	to	target	type
    //   22	38	41	wtf/opal/x5
    //   35	53	56	wtf/opal/x5
    //   60	68	71	wtf/opal/x5
    //   65	106	109	wtf/opal/x5
    //   75	116	119	wtf/opal/x5
    //   113	163	166	wtf/opal/x5
    //   170	178	181	wtf/opal/x5
  }
  
  private double lambda$sortModules$4(jt paramjt, d paramd) {
    // Byte code:
    //   0: getstatic wtf/opal/jl.b : J
    //   3: ldc2_w 5220756580325
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 33402034365281
    //   13: lxor
    //   14: lstore #5
    //   16: dup2
    //   17: ldc2_w 23908017519648
    //   20: lxor
    //   21: lstore #7
    //   23: pop2
    //   24: invokestatic S : ()Ljava/lang/String;
    //   27: astore #9
    //   29: aload_0
    //   30: getfield n : Lwtf/opal/bu;
    //   33: aload_1
    //   34: getfield f : Lwtf/opal/ke;
    //   37: invokevirtual z : ()Ljava/lang/Object;
    //   40: checkcast java/lang/Boolean
    //   43: invokevirtual booleanValue : ()Z
    //   46: ifeq -> 59
    //   49: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   52: goto -> 62
    //   55: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   58: athrow
    //   59: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   62: aload_2
    //   63: iconst_0
    //   64: anewarray java/lang/Object
    //   67: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   70: aload_0
    //   71: aload #9
    //   73: ifnonnull -> 106
    //   76: getfield f : Lwtf/opal/ke;
    //   79: invokevirtual z : ()Ljava/lang/Object;
    //   82: checkcast java/lang/Boolean
    //   85: invokevirtual booleanValue : ()Z
    //   88: ifeq -> 187
    //   91: goto -> 98
    //   94: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   97: athrow
    //   98: aload_2
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: lload #7
    //   108: iconst_1
    //   109: anewarray java/lang/Object
    //   112: dup_x2
    //   113: dup_x2
    //   114: pop
    //   115: invokestatic valueOf : (J)Ljava/lang/Long;
    //   118: iconst_0
    //   119: swap
    //   120: aastore
    //   121: invokevirtual o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   124: aload #9
    //   126: ifnonnull -> 184
    //   129: ifnull -> 187
    //   132: goto -> 139
    //   135: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   138: athrow
    //   139: aload_2
    //   140: lload #7
    //   142: iconst_1
    //   143: anewarray java/lang/Object
    //   146: dup_x2
    //   147: dup_x2
    //   148: pop
    //   149: invokestatic valueOf : (J)Ljava/lang/Long;
    //   152: iconst_0
    //   153: swap
    //   154: aastore
    //   155: invokevirtual o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   158: sipush #17032
    //   161: ldc2_w 6118847444634870036
    //   164: lload_3
    //   165: lxor
    //   166: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   171: swap
    //   172: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   177: goto -> 184
    //   180: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   183: athrow
    //   184: goto -> 190
    //   187: ldc_w ''
    //   190: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   195: ldc_w 10.0
    //   198: lload #5
    //   200: iconst_4
    //   201: anewarray java/lang/Object
    //   204: dup_x2
    //   205: dup_x2
    //   206: pop
    //   207: invokestatic valueOf : (J)Ljava/lang/Long;
    //   210: iconst_3
    //   211: swap
    //   212: aastore
    //   213: dup_x1
    //   214: swap
    //   215: invokestatic valueOf : (F)Ljava/lang/Float;
    //   218: iconst_2
    //   219: swap
    //   220: aastore
    //   221: dup_x1
    //   222: swap
    //   223: iconst_1
    //   224: swap
    //   225: aastore
    //   226: dup_x1
    //   227: swap
    //   228: iconst_0
    //   229: swap
    //   230: aastore
    //   231: invokevirtual p : ([Ljava/lang/Object;)F
    //   234: f2d
    //   235: dreturn
    // Exception table:
    //   from	to	target	type
    //   29	55	55	wtf/opal/x5
    //   62	91	94	wtf/opal/x5
    //   76	99	102	wtf/opal/x5
    //   106	132	135	wtf/opal/x5
    //   129	177	180	wtf/opal/x5
  }
  
  private boolean lambda$sortModules$3(d paramd) {
    // Byte code:
    //   0: getstatic wtf/opal/jl.b : J
    //   3: ldc2_w 36048183629376
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 73808763111933
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 84220642266359
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic S : ()Ljava/lang/String;
    //   27: astore #8
    //   29: aload_1
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokevirtual t : ([Ljava/lang/Object;)Lwtf/opal/di;
    //   37: aload_1
    //   38: iconst_0
    //   39: anewarray java/lang/Object
    //   42: invokevirtual D : ([Ljava/lang/Object;)Z
    //   45: ifeq -> 58
    //   48: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   51: goto -> 61
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   61: lload #4
    //   63: dup2_x1
    //   64: pop2
    //   65: iconst_2
    //   66: anewarray java/lang/Object
    //   69: dup_x1
    //   70: swap
    //   71: iconst_1
    //   72: swap
    //   73: aastore
    //   74: dup_x2
    //   75: dup_x2
    //   76: pop
    //   77: invokestatic valueOf : (J)Ljava/lang/Long;
    //   80: iconst_0
    //   81: swap
    //   82: aastore
    //   83: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   86: pop
    //   87: aload_1
    //   88: iconst_0
    //   89: anewarray java/lang/Object
    //   92: invokevirtual D : ([Ljava/lang/Object;)Z
    //   95: aload #8
    //   97: ifnonnull -> 233
    //   100: ifne -> 218
    //   103: goto -> 110
    //   106: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   109: athrow
    //   110: aload_1
    //   111: iconst_0
    //   112: anewarray java/lang/Object
    //   115: invokevirtual t : ([Ljava/lang/Object;)Lwtf/opal/di;
    //   118: lload #6
    //   120: iconst_1
    //   121: anewarray java/lang/Object
    //   124: dup_x2
    //   125: dup_x2
    //   126: pop
    //   127: invokestatic valueOf : (J)Ljava/lang/Long;
    //   130: iconst_0
    //   131: swap
    //   132: aastore
    //   133: invokevirtual H : ([Ljava/lang/Object;)Z
    //   136: aload #8
    //   138: ifnonnull -> 249
    //   141: goto -> 148
    //   144: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   147: athrow
    //   148: ifne -> 248
    //   151: goto -> 158
    //   154: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   157: athrow
    //   158: aload_0
    //   159: getfield x : Lwtf/opal/kd;
    //   162: sipush #15082
    //   165: ldc2_w 1506093558293506241
    //   168: lload_2
    //   169: lxor
    //   170: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   175: iconst_1
    //   176: anewarray java/lang/Object
    //   179: dup_x1
    //   180: swap
    //   181: iconst_0
    //   182: swap
    //   183: aastore
    //   184: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   187: invokevirtual z : ()Ljava/lang/Object;
    //   190: checkcast java/lang/Boolean
    //   193: invokevirtual booleanValue : ()Z
    //   196: aload #8
    //   198: ifnonnull -> 249
    //   201: goto -> 208
    //   204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   207: athrow
    //   208: ifeq -> 248
    //   211: goto -> 218
    //   214: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   217: athrow
    //   218: aload_1
    //   219: iconst_0
    //   220: anewarray java/lang/Object
    //   223: invokevirtual P : ([Ljava/lang/Object;)Z
    //   226: goto -> 233
    //   229: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   232: athrow
    //   233: aload #8
    //   235: ifnonnull -> 290
    //   238: ifeq -> 250
    //   241: goto -> 248
    //   244: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: iconst_0
    //   249: ireturn
    //   250: aload_0
    //   251: getfield a : Lwtf/opal/kd;
    //   254: aload_1
    //   255: iconst_0
    //   256: anewarray java/lang/Object
    //   259: invokevirtual X : ([Ljava/lang/Object;)Lwtf/opal/kn;
    //   262: iconst_0
    //   263: anewarray java/lang/Object
    //   266: invokevirtual S : ([Ljava/lang/Object;)Ljava/lang/String;
    //   269: iconst_1
    //   270: anewarray java/lang/Object
    //   273: dup_x1
    //   274: swap
    //   275: iconst_0
    //   276: swap
    //   277: aastore
    //   278: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   281: invokevirtual z : ()Ljava/lang/Object;
    //   284: checkcast java/lang/Boolean
    //   287: invokevirtual booleanValue : ()Z
    //   290: aload #8
    //   292: ifnonnull -> 312
    //   295: ifeq -> 311
    //   298: goto -> 305
    //   301: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   304: athrow
    //   305: iconst_0
    //   306: ireturn
    //   307: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: iconst_1
    //   312: ireturn
    // Exception table:
    //   from	to	target	type
    //   29	54	54	wtf/opal/x5
    //   61	103	106	wtf/opal/x5
    //   100	141	144	wtf/opal/x5
    //   110	151	154	wtf/opal/x5
    //   148	201	204	wtf/opal/x5
    //   158	211	214	wtf/opal/x5
    //   208	226	229	wtf/opal/x5
    //   233	241	244	wtf/opal/x5
    //   290	298	301	wtf/opal/x5
    //   295	307	307	wtf/opal/x5
  }
  
  private void lambda$new$2(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/jl.b : J
    //   3: ldc2_w 59233391852188
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 20428773077205
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 73709315749100
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 51684838768152
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 49225487187180
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 53757904834218
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 6412604697755
    //   48: lxor
    //   49: lstore #14
    //   51: dup2
    //   52: ldc2_w 92985825325573
    //   55: lxor
    //   56: lstore #16
    //   58: dup2
    //   59: ldc2_w 20330215089541
    //   62: lxor
    //   63: lstore #18
    //   65: dup2
    //   66: ldc2_w 34428422015926
    //   69: lxor
    //   70: lstore #20
    //   72: dup2
    //   73: ldc2_w 40537259565401
    //   76: lxor
    //   77: lstore #22
    //   79: dup2
    //   80: ldc2_w 92757926460656
    //   83: lxor
    //   84: lstore #24
    //   86: pop2
    //   87: invokestatic S : ()Ljava/lang/String;
    //   90: astore #26
    //   92: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   95: invokevirtual method_53526 : ()Lnet/minecraft/class_340;
    //   98: invokevirtual method_53536 : ()Z
    //   101: ifeq -> 109
    //   104: return
    //   105: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   108: athrow
    //   109: iconst_0
    //   110: anewarray java/lang/Object
    //   113: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   116: iconst_0
    //   117: anewarray java/lang/Object
    //   120: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   123: ldc wtf/opal/jt
    //   125: iconst_1
    //   126: anewarray java/lang/Object
    //   129: dup_x1
    //   130: swap
    //   131: iconst_0
    //   132: swap
    //   133: aastore
    //   134: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   137: checkcast wtf/opal/jt
    //   140: astore #27
    //   142: aload #27
    //   144: getfield f : Lwtf/opal/ke;
    //   147: invokevirtual z : ()Ljava/lang/Object;
    //   150: checkcast java/lang/Boolean
    //   153: invokevirtual booleanValue : ()Z
    //   156: ifeq -> 169
    //   159: getstatic wtf/opal/lx.BOLD : Lwtf/opal/lx;
    //   162: goto -> 172
    //   165: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   168: athrow
    //   169: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   172: astore #28
    //   174: aload_0
    //   175: iconst_0
    //   176: anewarray java/lang/Object
    //   179: invokevirtual M : ([Ljava/lang/Object;)V
    //   182: new java/util/concurrent/atomic/AtomicInteger
    //   185: dup
    //   186: invokespecial <init> : ()V
    //   189: astore #29
    //   191: fconst_0
    //   192: fstore #30
    //   194: aload_0
    //   195: getfield g : Ljava/util/List;
    //   198: invokeinterface size : ()I
    //   203: istore #31
    //   205: aload #27
    //   207: iconst_0
    //   208: anewarray java/lang/Object
    //   211: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   214: invokevirtual z : ()Ljava/lang/Object;
    //   217: checkcast java/lang/Integer
    //   220: invokevirtual intValue : ()I
    //   223: istore #32
    //   225: aload #27
    //   227: iconst_0
    //   228: anewarray java/lang/Object
    //   231: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   234: invokevirtual z : ()Ljava/lang/Object;
    //   237: checkcast java/lang/Integer
    //   240: invokevirtual intValue : ()I
    //   243: istore #33
    //   245: aload_0
    //   246: getfield g : Ljava/util/List;
    //   249: invokeinterface iterator : ()Ljava/util/Iterator;
    //   254: astore #34
    //   256: aload #34
    //   258: invokeinterface hasNext : ()Z
    //   263: ifeq -> 1882
    //   266: aload #34
    //   268: invokeinterface next : ()Ljava/lang/Object;
    //   273: checkcast wtf/opal/d
    //   276: astore #35
    //   278: aload #35
    //   280: iconst_0
    //   281: anewarray java/lang/Object
    //   284: invokevirtual t : ([Ljava/lang/Object;)Lwtf/opal/di;
    //   287: astore #36
    //   289: aload #36
    //   291: lload #18
    //   293: iconst_1
    //   294: anewarray java/lang/Object
    //   297: dup_x2
    //   298: dup_x2
    //   299: pop
    //   300: invokestatic valueOf : (J)Ljava/lang/Long;
    //   303: iconst_0
    //   304: swap
    //   305: aastore
    //   306: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   309: invokevirtual floatValue : ()F
    //   312: fstore #37
    //   314: aload_0
    //   315: getfield k : Lwtf/opal/kt;
    //   318: invokevirtual z : ()Ljava/lang/Object;
    //   321: checkcast java/lang/Double
    //   324: invokevirtual floatValue : ()F
    //   327: fstore #38
    //   329: aload #26
    //   331: ifnonnull -> 1903
    //   334: aload #35
    //   336: iconst_0
    //   337: anewarray java/lang/Object
    //   340: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   343: aload_0
    //   344: aload #26
    //   346: ifnonnull -> 387
    //   349: goto -> 356
    //   352: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   355: athrow
    //   356: getfield f : Lwtf/opal/ke;
    //   359: invokevirtual z : ()Ljava/lang/Object;
    //   362: checkcast java/lang/Boolean
    //   365: invokevirtual booleanValue : ()Z
    //   368: ifeq -> 469
    //   371: goto -> 378
    //   374: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: aload #35
    //   380: goto -> 387
    //   383: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   386: athrow
    //   387: lload #22
    //   389: iconst_1
    //   390: anewarray java/lang/Object
    //   393: dup_x2
    //   394: dup_x2
    //   395: pop
    //   396: invokestatic valueOf : (J)Ljava/lang/Long;
    //   399: iconst_0
    //   400: swap
    //   401: aastore
    //   402: invokevirtual o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   405: aload #26
    //   407: ifnonnull -> 466
    //   410: ifnull -> 469
    //   413: goto -> 420
    //   416: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   419: athrow
    //   420: aload #35
    //   422: lload #22
    //   424: iconst_1
    //   425: anewarray java/lang/Object
    //   428: dup_x2
    //   429: dup_x2
    //   430: pop
    //   431: invokestatic valueOf : (J)Ljava/lang/Long;
    //   434: iconst_0
    //   435: swap
    //   436: aastore
    //   437: invokevirtual o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   440: sipush #17032
    //   443: ldc2_w 6118898980686691437
    //   446: lload_2
    //   447: lxor
    //   448: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   453: swap
    //   454: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   459: goto -> 466
    //   462: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   465: athrow
    //   466: goto -> 472
    //   469: ldc_w ''
    //   472: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   477: astore #39
    //   479: aload_0
    //   480: getfield n : Lwtf/opal/bu;
    //   483: aload #28
    //   485: aload #39
    //   487: ldc_w 10.0
    //   490: lload #8
    //   492: iconst_4
    //   493: anewarray java/lang/Object
    //   496: dup_x2
    //   497: dup_x2
    //   498: pop
    //   499: invokestatic valueOf : (J)Ljava/lang/Long;
    //   502: iconst_3
    //   503: swap
    //   504: aastore
    //   505: dup_x1
    //   506: swap
    //   507: invokestatic valueOf : (F)Ljava/lang/Float;
    //   510: iconst_2
    //   511: swap
    //   512: aastore
    //   513: dup_x1
    //   514: swap
    //   515: iconst_1
    //   516: swap
    //   517: aastore
    //   518: dup_x1
    //   519: swap
    //   520: iconst_0
    //   521: swap
    //   522: aastore
    //   523: invokevirtual p : ([Ljava/lang/Object;)F
    //   526: fstore #40
    //   528: aload_0
    //   529: getfield n : Lwtf/opal/bu;
    //   532: lload #20
    //   534: aload #39
    //   536: ldc_w 10.0
    //   539: iconst_3
    //   540: anewarray java/lang/Object
    //   543: dup_x1
    //   544: swap
    //   545: invokestatic valueOf : (F)Ljava/lang/Float;
    //   548: iconst_2
    //   549: swap
    //   550: aastore
    //   551: dup_x1
    //   552: swap
    //   553: iconst_1
    //   554: swap
    //   555: aastore
    //   556: dup_x2
    //   557: dup_x2
    //   558: pop
    //   559: invokestatic valueOf : (J)Ljava/lang/Long;
    //   562: iconst_0
    //   563: swap
    //   564: aastore
    //   565: invokevirtual A : ([Ljava/lang/Object;)F
    //   568: fstore #41
    //   570: aload_0
    //   571: getfield l : Lwtf/opal/k6;
    //   574: iconst_0
    //   575: anewarray java/lang/Object
    //   578: invokevirtual g : ([Ljava/lang/Object;)F
    //   581: ldc_w 0.5
    //   584: fcmpl
    //   585: aload #26
    //   587: ifnonnull -> 601
    //   590: ifle -> 604
    //   593: goto -> 600
    //   596: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   599: athrow
    //   600: iconst_1
    //   601: goto -> 605
    //   604: iconst_0
    //   605: istore #42
    //   607: iload #42
    //   609: ifeq -> 636
    //   612: aload_0
    //   613: getfield l : Lwtf/opal/k6;
    //   616: iconst_0
    //   617: anewarray java/lang/Object
    //   620: invokevirtual K : ([Ljava/lang/Object;)F
    //   623: fload #40
    //   625: fload #38
    //   627: fmul
    //   628: fsub
    //   629: goto -> 637
    //   632: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   635: athrow
    //   636: fconst_0
    //   637: fstore #43
    //   639: aload_0
    //   640: getfield l : Lwtf/opal/k6;
    //   643: lload #24
    //   645: iconst_1
    //   646: anewarray java/lang/Object
    //   649: dup_x2
    //   650: dup_x2
    //   651: pop
    //   652: invokestatic valueOf : (J)Ljava/lang/Long;
    //   655: iconst_0
    //   656: swap
    //   657: aastore
    //   658: invokevirtual t : ([Ljava/lang/Object;)F
    //   661: fload #43
    //   663: aload #26
    //   665: ifnonnull -> 731
    //   668: fadd
    //   669: aload_0
    //   670: getfield x : Lwtf/opal/kd;
    //   673: sipush #24584
    //   676: ldc2_w 4567367227246010098
    //   679: lload_2
    //   680: lxor
    //   681: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   686: iconst_1
    //   687: anewarray java/lang/Object
    //   690: dup_x1
    //   691: swap
    //   692: iconst_0
    //   693: swap
    //   694: aastore
    //   695: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   698: invokevirtual z : ()Ljava/lang/Object;
    //   701: checkcast java/lang/Boolean
    //   704: invokevirtual booleanValue : ()Z
    //   707: ifeq -> 734
    //   710: goto -> 717
    //   713: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   716: athrow
    //   717: fload #37
    //   719: fconst_1
    //   720: fsub
    //   721: fload #40
    //   723: fmul
    //   724: goto -> 731
    //   727: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   730: athrow
    //   731: goto -> 735
    //   734: fconst_0
    //   735: invokestatic abs : (F)F
    //   738: iload #42
    //   740: aload #26
    //   742: ifnonnull -> 756
    //   745: ifeq -> 759
    //   748: goto -> 755
    //   751: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   754: athrow
    //   755: iconst_1
    //   756: goto -> 760
    //   759: iconst_m1
    //   760: i2f
    //   761: fmul
    //   762: fadd
    //   763: iload #42
    //   765: aload #26
    //   767: ifnonnull -> 803
    //   770: ifne -> 822
    //   773: goto -> 780
    //   776: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   779: athrow
    //   780: aload_0
    //   781: getfield q : Lwtf/opal/ky;
    //   784: invokevirtual z : ()Ljava/lang/Object;
    //   787: checkcast wtf/opal/lh
    //   790: getstatic wtf/opal/lh.LEFT_BAR : Lwtf/opal/lh;
    //   793: invokevirtual equals : (Ljava/lang/Object;)Z
    //   796: goto -> 803
    //   799: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   802: athrow
    //   803: aload #26
    //   805: ifnonnull -> 819
    //   808: ifeq -> 822
    //   811: goto -> 818
    //   814: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   817: athrow
    //   818: iconst_2
    //   819: goto -> 823
    //   822: iconst_0
    //   823: i2f
    //   824: fadd
    //   825: iload #42
    //   827: aload #26
    //   829: ifnonnull -> 865
    //   832: ifeq -> 922
    //   835: goto -> 842
    //   838: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   841: athrow
    //   842: aload_0
    //   843: getfield q : Lwtf/opal/ky;
    //   846: invokevirtual z : ()Ljava/lang/Object;
    //   849: checkcast wtf/opal/lh
    //   852: getstatic wtf/opal/lh.OUTLINE : Lwtf/opal/lh;
    //   855: invokevirtual equals : (Ljava/lang/Object;)Z
    //   858: goto -> 865
    //   861: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   864: athrow
    //   865: aload #26
    //   867: ifnonnull -> 919
    //   870: ifne -> 918
    //   873: goto -> 880
    //   876: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   879: athrow
    //   880: aload_0
    //   881: getfield q : Lwtf/opal/ky;
    //   884: invokevirtual z : ()Ljava/lang/Object;
    //   887: checkcast wtf/opal/lh
    //   890: getstatic wtf/opal/lh.RIGHT_BAR : Lwtf/opal/lh;
    //   893: invokevirtual equals : (Ljava/lang/Object;)Z
    //   896: aload #26
    //   898: ifnonnull -> 919
    //   901: goto -> 908
    //   904: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   907: athrow
    //   908: ifeq -> 922
    //   911: goto -> 918
    //   914: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   917: athrow
    //   918: iconst_2
    //   919: goto -> 923
    //   922: iconst_0
    //   923: i2f
    //   924: fsub
    //   925: fload #38
    //   927: fdiv
    //   928: fstore #44
    //   930: aload_0
    //   931: getfield l : Lwtf/opal/k6;
    //   934: iconst_0
    //   935: anewarray java/lang/Object
    //   938: invokevirtual x : ([Ljava/lang/Object;)F
    //   941: fload #30
    //   943: aload_0
    //   944: getfield x : Lwtf/opal/kd;
    //   947: sipush #29397
    //   950: ldc2_w 5428670664475298869
    //   953: lload_2
    //   954: lxor
    //   955: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   960: iconst_1
    //   961: anewarray java/lang/Object
    //   964: dup_x1
    //   965: swap
    //   966: iconst_0
    //   967: swap
    //   968: aastore
    //   969: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   972: invokevirtual z : ()Ljava/lang/Object;
    //   975: checkcast java/lang/Boolean
    //   978: invokevirtual booleanValue : ()Z
    //   981: ifeq -> 993
    //   984: fload #37
    //   986: goto -> 994
    //   989: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   992: athrow
    //   993: fconst_1
    //   994: fmul
    //   995: fadd
    //   996: aload_0
    //   997: getfield q : Lwtf/opal/ky;
    //   1000: invokevirtual z : ()Ljava/lang/Object;
    //   1003: checkcast wtf/opal/lh
    //   1006: getstatic wtf/opal/lh.OUTLINE : Lwtf/opal/lh;
    //   1009: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1012: aload #26
    //   1014: ifnonnull -> 1028
    //   1017: ifeq -> 1031
    //   1020: goto -> 1027
    //   1023: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1026: athrow
    //   1027: iconst_2
    //   1028: goto -> 1032
    //   1031: iconst_0
    //   1032: i2f
    //   1033: fadd
    //   1034: fload #38
    //   1036: fdiv
    //   1037: fstore #45
    //   1039: fload #40
    //   1041: ldc_w 4.0
    //   1044: fadd
    //   1045: fstore #46
    //   1047: fload #41
    //   1049: fconst_2
    //   1050: fadd
    //   1051: fstore #47
    //   1053: aload #29
    //   1055: invokevirtual getAndIncrement : ()I
    //   1058: istore #48
    //   1060: aload_0
    //   1061: getfield L : Lwtf/opal/kt;
    //   1064: invokevirtual z : ()Ljava/lang/Object;
    //   1067: checkcast java/lang/Double
    //   1070: invokevirtual intValue : ()I
    //   1073: iload #48
    //   1075: i2d
    //   1076: aload_0
    //   1077: getfield X : Lwtf/opal/kt;
    //   1080: invokevirtual z : ()Ljava/lang/Object;
    //   1083: checkcast java/lang/Double
    //   1086: invokevirtual doubleValue : ()D
    //   1089: dmul
    //   1090: d2i
    //   1091: lload #6
    //   1093: iload #32
    //   1095: iload #33
    //   1097: iconst_5
    //   1098: anewarray java/lang/Object
    //   1101: dup_x1
    //   1102: swap
    //   1103: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1106: iconst_4
    //   1107: swap
    //   1108: aastore
    //   1109: dup_x1
    //   1110: swap
    //   1111: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1114: iconst_3
    //   1115: swap
    //   1116: aastore
    //   1117: dup_x2
    //   1118: dup_x2
    //   1119: pop
    //   1120: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1123: iconst_2
    //   1124: swap
    //   1125: aastore
    //   1126: dup_x1
    //   1127: swap
    //   1128: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1131: iconst_1
    //   1132: swap
    //   1133: aastore
    //   1134: dup_x1
    //   1135: swap
    //   1136: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1139: iconst_0
    //   1140: swap
    //   1141: aastore
    //   1142: invokestatic K : ([Ljava/lang/Object;)I
    //   1145: lload #16
    //   1147: iconst_2
    //   1148: anewarray java/lang/Object
    //   1151: dup_x2
    //   1152: dup_x2
    //   1153: pop
    //   1154: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1157: iconst_1
    //   1158: swap
    //   1159: aastore
    //   1160: dup_x1
    //   1161: swap
    //   1162: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1165: iconst_0
    //   1166: swap
    //   1167: aastore
    //   1168: invokestatic S : ([Ljava/lang/Object;)I
    //   1171: aload_0
    //   1172: getfield x : Lwtf/opal/kd;
    //   1175: sipush #22990
    //   1178: ldc2_w 6253053781185816356
    //   1181: lload_2
    //   1182: lxor
    //   1183: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   1188: iconst_1
    //   1189: anewarray java/lang/Object
    //   1192: dup_x1
    //   1193: swap
    //   1194: iconst_0
    //   1195: swap
    //   1196: aastore
    //   1197: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   1200: invokevirtual z : ()Ljava/lang/Object;
    //   1203: checkcast java/lang/Boolean
    //   1206: invokevirtual booleanValue : ()Z
    //   1209: ifeq -> 1221
    //   1212: fload #37
    //   1214: goto -> 1222
    //   1217: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1220: athrow
    //   1221: fconst_1
    //   1222: iconst_2
    //   1223: anewarray java/lang/Object
    //   1226: dup_x1
    //   1227: swap
    //   1228: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1231: iconst_1
    //   1232: swap
    //   1233: aastore
    //   1234: dup_x1
    //   1235: swap
    //   1236: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1239: iconst_0
    //   1240: swap
    //   1241: aastore
    //   1242: invokestatic X : ([Ljava/lang/Object;)I
    //   1245: istore #49
    //   1247: iload #48
    //   1249: ifne -> 1282
    //   1252: aload_0
    //   1253: getfield l : Lwtf/opal/k6;
    //   1256: fload #46
    //   1258: fconst_2
    //   1259: fadd
    //   1260: iconst_1
    //   1261: anewarray java/lang/Object
    //   1264: dup_x1
    //   1265: swap
    //   1266: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1269: iconst_0
    //   1270: swap
    //   1271: aastore
    //   1272: invokevirtual r : ([Ljava/lang/Object;)V
    //   1275: goto -> 1282
    //   1278: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1281: athrow
    //   1282: aload_0
    //   1283: getfield U : Lwtf/opal/pa;
    //   1286: iconst_0
    //   1287: anewarray java/lang/Object
    //   1290: invokevirtual y : ([Ljava/lang/Object;)J
    //   1293: lstore #50
    //   1295: aload_0
    //   1296: getfield U : Lwtf/opal/pa;
    //   1299: aload_0
    //   1300: lload #50
    //   1302: fload #38
    //   1304: fload #44
    //   1306: iload #42
    //   1308: fload #45
    //   1310: fload #46
    //   1312: fload #47
    //   1314: fload #37
    //   1316: iload #48
    //   1318: iload #49
    //   1320: iload #31
    //   1322: aload #28
    //   1324: fload #40
    //   1326: <illegal opcode> run : (Lwtf/opal/jl;JFFZFFFFIIILwtf/opal/lx;F)Ljava/lang/Runnable;
    //   1331: lload #14
    //   1333: iconst_2
    //   1334: anewarray java/lang/Object
    //   1337: dup_x2
    //   1338: dup_x2
    //   1339: pop
    //   1340: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1343: iconst_1
    //   1344: swap
    //   1345: aastore
    //   1346: dup_x1
    //   1347: swap
    //   1348: iconst_0
    //   1349: swap
    //   1350: aastore
    //   1351: invokevirtual F : ([Ljava/lang/Object;)V
    //   1354: aload #27
    //   1356: lload #4
    //   1358: iconst_1
    //   1359: anewarray java/lang/Object
    //   1362: dup_x2
    //   1363: dup_x2
    //   1364: pop
    //   1365: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1368: iconst_0
    //   1369: swap
    //   1370: aastore
    //   1371: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1374: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   1377: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1380: istore #52
    //   1382: aload #36
    //   1384: iconst_0
    //   1385: anewarray java/lang/Object
    //   1388: invokevirtual H : ([Ljava/lang/Object;)Lwtf/opal/lp;
    //   1391: lload #10
    //   1393: iconst_1
    //   1394: anewarray java/lang/Object
    //   1397: dup_x2
    //   1398: dup_x2
    //   1399: pop
    //   1400: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1403: iconst_0
    //   1404: swap
    //   1405: aastore
    //   1406: invokevirtual b : ([Ljava/lang/Object;)Z
    //   1409: aload #26
    //   1411: ifnonnull -> 1475
    //   1414: ifne -> 1473
    //   1417: goto -> 1424
    //   1420: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1423: athrow
    //   1424: aload #36
    //   1426: lload #18
    //   1428: iconst_1
    //   1429: anewarray java/lang/Object
    //   1432: dup_x2
    //   1433: dup_x2
    //   1434: pop
    //   1435: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1438: iconst_0
    //   1439: swap
    //   1440: aastore
    //   1441: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1444: invokevirtual doubleValue : ()D
    //   1447: ldc2_w 0.3
    //   1450: dcmpl
    //   1451: aload #26
    //   1453: ifnonnull -> 1475
    //   1456: goto -> 1463
    //   1459: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1462: athrow
    //   1463: ifle -> 1780
    //   1466: goto -> 1473
    //   1469: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1472: athrow
    //   1473: iload #52
    //   1475: ifeq -> 1509
    //   1478: aload_1
    //   1479: iconst_0
    //   1480: anewarray java/lang/Object
    //   1483: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1486: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   1489: fload #38
    //   1491: fload #38
    //   1493: fconst_1
    //   1494: invokevirtual method_22905 : (FFF)V
    //   1497: aload #26
    //   1499: ifnull -> 1525
    //   1502: goto -> 1509
    //   1505: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1508: athrow
    //   1509: lload #50
    //   1511: fload #38
    //   1513: fload #38
    //   1515: invokestatic nvgScale : (JFF)V
    //   1518: goto -> 1525
    //   1521: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1524: athrow
    //   1525: aload_0
    //   1526: getfield n : Lwtf/opal/bu;
    //   1529: aload #28
    //   1531: aload_1
    //   1532: iconst_0
    //   1533: anewarray java/lang/Object
    //   1536: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1539: aload #39
    //   1541: fload #44
    //   1543: iload #42
    //   1545: ifeq -> 1558
    //   1548: ldc_w -2.0
    //   1551: goto -> 1561
    //   1554: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1557: athrow
    //   1558: ldc_w 2.5
    //   1561: fadd
    //   1562: fload #45
    //   1564: iload #52
    //   1566: ifeq -> 1579
    //   1569: ldc_w 0.5
    //   1572: goto -> 1580
    //   1575: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1578: athrow
    //   1579: fconst_1
    //   1580: fadd
    //   1581: ldc_w 10.0
    //   1584: iload #49
    //   1586: aload_0
    //   1587: getfield I : Lwtf/opal/ke;
    //   1590: invokevirtual z : ()Ljava/lang/Object;
    //   1593: checkcast java/lang/Boolean
    //   1596: invokevirtual booleanValue : ()Z
    //   1599: iconst_0
    //   1600: lload #12
    //   1602: sipush #7148
    //   1605: ldc2_w 7509717159969741239
    //   1608: lload_2
    //   1609: lxor
    //   1610: <illegal opcode> f : (IJ)I
    //   1615: bipush #11
    //   1617: anewarray java/lang/Object
    //   1620: dup_x1
    //   1621: swap
    //   1622: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1625: bipush #10
    //   1627: swap
    //   1628: aastore
    //   1629: dup_x2
    //   1630: dup_x2
    //   1631: pop
    //   1632: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1635: bipush #9
    //   1637: swap
    //   1638: aastore
    //   1639: dup_x1
    //   1640: swap
    //   1641: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1644: bipush #8
    //   1646: swap
    //   1647: aastore
    //   1648: dup_x1
    //   1649: swap
    //   1650: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1653: bipush #7
    //   1655: swap
    //   1656: aastore
    //   1657: dup_x1
    //   1658: swap
    //   1659: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1662: bipush #6
    //   1664: swap
    //   1665: aastore
    //   1666: dup_x1
    //   1667: swap
    //   1668: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1671: iconst_5
    //   1672: swap
    //   1673: aastore
    //   1674: dup_x1
    //   1675: swap
    //   1676: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1679: iconst_4
    //   1680: swap
    //   1681: aastore
    //   1682: dup_x1
    //   1683: swap
    //   1684: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1687: iconst_3
    //   1688: swap
    //   1689: aastore
    //   1690: dup_x1
    //   1691: swap
    //   1692: iconst_2
    //   1693: swap
    //   1694: aastore
    //   1695: dup_x1
    //   1696: swap
    //   1697: iconst_1
    //   1698: swap
    //   1699: aastore
    //   1700: dup_x1
    //   1701: swap
    //   1702: iconst_0
    //   1703: swap
    //   1704: aastore
    //   1705: invokevirtual E : ([Ljava/lang/Object;)V
    //   1708: aload #26
    //   1710: ifnonnull -> 1755
    //   1713: iload #52
    //   1715: ifeq -> 1760
    //   1718: goto -> 1725
    //   1721: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1724: athrow
    //   1725: aload_1
    //   1726: iconst_0
    //   1727: anewarray java/lang/Object
    //   1730: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1733: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   1736: fconst_1
    //   1737: fload #38
    //   1739: fdiv
    //   1740: fconst_1
    //   1741: fload #38
    //   1743: fdiv
    //   1744: fconst_1
    //   1745: invokevirtual method_22905 : (FFF)V
    //   1748: goto -> 1755
    //   1751: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1754: athrow
    //   1755: aload #26
    //   1757: ifnull -> 1780
    //   1760: lload #50
    //   1762: fconst_1
    //   1763: fload #38
    //   1765: fdiv
    //   1766: fconst_1
    //   1767: fload #38
    //   1769: fdiv
    //   1770: invokestatic nvgScale : (JFF)V
    //   1773: goto -> 1780
    //   1776: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1779: athrow
    //   1780: fload #30
    //   1782: aload_0
    //   1783: getfield x : Lwtf/opal/kd;
    //   1786: sipush #23057
    //   1789: ldc2_w 3393468626685912312
    //   1792: lload_2
    //   1793: lxor
    //   1794: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   1799: iconst_1
    //   1800: anewarray java/lang/Object
    //   1803: dup_x1
    //   1804: swap
    //   1805: iconst_0
    //   1806: swap
    //   1807: aastore
    //   1808: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   1811: invokevirtual z : ()Ljava/lang/Object;
    //   1814: checkcast java/lang/Boolean
    //   1817: invokevirtual booleanValue : ()Z
    //   1820: ifeq -> 1832
    //   1823: fload #37
    //   1825: goto -> 1833
    //   1828: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1831: athrow
    //   1832: fconst_1
    //   1833: aload_0
    //   1834: getfield E : Lwtf/opal/kt;
    //   1837: invokevirtual z : ()Ljava/lang/Object;
    //   1840: checkcast java/lang/Double
    //   1843: invokevirtual floatValue : ()F
    //   1846: iload #52
    //   1848: aload #26
    //   1850: ifnonnull -> 1864
    //   1853: ifeq -> 1867
    //   1856: goto -> 1863
    //   1859: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1862: athrow
    //   1863: iconst_1
    //   1864: goto -> 1868
    //   1867: iconst_0
    //   1868: i2f
    //   1869: fsub
    //   1870: fload #38
    //   1872: fmul
    //   1873: fmul
    //   1874: fadd
    //   1875: fstore #30
    //   1877: aload #26
    //   1879: ifnull -> 256
    //   1882: aload_0
    //   1883: getfield l : Lwtf/opal/k6;
    //   1886: fload #30
    //   1888: iconst_1
    //   1889: anewarray java/lang/Object
    //   1892: dup_x1
    //   1893: swap
    //   1894: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1897: iconst_0
    //   1898: swap
    //   1899: aastore
    //   1900: invokevirtual k : ([Ljava/lang/Object;)V
    //   1903: return
    // Exception table:
    //   from	to	target	type
    //   92	105	105	wtf/opal/x5
    //   142	165	165	wtf/opal/x5
    //   329	349	352	wtf/opal/x5
    //   334	371	374	wtf/opal/x5
    //   356	380	383	wtf/opal/x5
    //   387	413	416	wtf/opal/x5
    //   410	459	462	wtf/opal/x5
    //   570	593	596	wtf/opal/x5
    //   607	632	632	wtf/opal/x5
    //   639	710	713	wtf/opal/x5
    //   668	724	727	wtf/opal/x5
    //   735	748	751	wtf/opal/x5
    //   760	773	776	wtf/opal/x5
    //   770	796	799	wtf/opal/x5
    //   803	811	814	wtf/opal/x5
    //   823	835	838	wtf/opal/x5
    //   832	858	861	wtf/opal/x5
    //   865	873	876	wtf/opal/x5
    //   870	901	904	wtf/opal/x5
    //   880	911	914	wtf/opal/x5
    //   930	989	989	wtf/opal/x5
    //   994	1020	1023	wtf/opal/x5
    //   1060	1217	1217	wtf/opal/x5
    //   1247	1275	1278	wtf/opal/x5
    //   1382	1417	1420	wtf/opal/x5
    //   1414	1456	1459	wtf/opal/x5
    //   1424	1466	1469	wtf/opal/x5
    //   1475	1502	1505	wtf/opal/x5
    //   1478	1518	1521	wtf/opal/x5
    //   1525	1554	1554	wtf/opal/x5
    //   1561	1575	1575	wtf/opal/x5
    //   1580	1718	1721	wtf/opal/x5
    //   1713	1748	1751	wtf/opal/x5
    //   1755	1773	1776	wtf/opal/x5
    //   1780	1828	1828	wtf/opal/x5
    //   1833	1856	1859	wtf/opal/x5
  }
  
  private void lambda$new$1(long paramLong, float paramFloat1, float paramFloat2, boolean paramBoolean, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, int paramInt1, int paramInt2, int paramInt3, lx paramlx, float paramFloat7) {
    // Byte code:
    //   0: getstatic wtf/opal/jl.b : J
    //   3: ldc2_w 11083193000094
    //   6: lxor
    //   7: lstore #15
    //   9: lload #15
    //   11: dup2
    //   12: ldc2_w 18459920163866
    //   15: lxor
    //   16: lstore #17
    //   18: dup2
    //   19: ldc2_w 117291668905616
    //   22: lxor
    //   23: lstore #19
    //   25: dup2
    //   26: ldc2_w 29779298057051
    //   29: lxor
    //   30: lstore #21
    //   32: pop2
    //   33: invokestatic S : ()Ljava/lang/String;
    //   36: lload_1
    //   37: fload_3
    //   38: fload_3
    //   39: invokestatic nvgScale : (JFF)V
    //   42: astore #23
    //   44: aload_0
    //   45: getfield w : Lwtf/opal/ke;
    //   48: invokevirtual z : ()Ljava/lang/Object;
    //   51: checkcast java/lang/Boolean
    //   54: invokevirtual booleanValue : ()Z
    //   57: aload #23
    //   59: ifnonnull -> 218
    //   62: ifeq -> 205
    //   65: goto -> 72
    //   68: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   71: athrow
    //   72: aload_0
    //   73: getfield U : Lwtf/opal/pa;
    //   76: fload #4
    //   78: iload #5
    //   80: aload #23
    //   82: ifnonnull -> 103
    //   85: goto -> 92
    //   88: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: ifeq -> 106
    //   95: goto -> 102
    //   98: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   101: athrow
    //   102: iconst_4
    //   103: goto -> 107
    //   106: iconst_0
    //   107: i2f
    //   108: fsub
    //   109: fload #6
    //   111: fload #7
    //   113: fload #8
    //   115: new java/awt/Color
    //   118: dup
    //   119: iconst_0
    //   120: iconst_0
    //   121: iconst_0
    //   122: aload_0
    //   123: getfield Z : Lwtf/opal/kt;
    //   126: invokevirtual z : ()Ljava/lang/Object;
    //   129: checkcast java/lang/Double
    //   132: invokevirtual doubleValue : ()D
    //   135: fload #9
    //   137: f2d
    //   138: dmul
    //   139: d2i
    //   140: invokespecial <init> : (IIII)V
    //   143: invokevirtual getRGB : ()I
    //   146: lload #19
    //   148: bipush #6
    //   150: anewarray java/lang/Object
    //   153: dup_x2
    //   154: dup_x2
    //   155: pop
    //   156: invokestatic valueOf : (J)Ljava/lang/Long;
    //   159: iconst_5
    //   160: swap
    //   161: aastore
    //   162: dup_x1
    //   163: swap
    //   164: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   167: iconst_4
    //   168: swap
    //   169: aastore
    //   170: dup_x1
    //   171: swap
    //   172: invokestatic valueOf : (F)Ljava/lang/Float;
    //   175: iconst_3
    //   176: swap
    //   177: aastore
    //   178: dup_x1
    //   179: swap
    //   180: invokestatic valueOf : (F)Ljava/lang/Float;
    //   183: iconst_2
    //   184: swap
    //   185: aastore
    //   186: dup_x1
    //   187: swap
    //   188: invokestatic valueOf : (F)Ljava/lang/Float;
    //   191: iconst_1
    //   192: swap
    //   193: aastore
    //   194: dup_x1
    //   195: swap
    //   196: invokestatic valueOf : (F)Ljava/lang/Float;
    //   199: iconst_0
    //   200: swap
    //   201: aastore
    //   202: invokevirtual k : ([Ljava/lang/Object;)V
    //   205: aload_0
    //   206: getfield q : Lwtf/opal/ky;
    //   209: invokevirtual z : ()Ljava/lang/Object;
    //   212: checkcast wtf/opal/lh
    //   215: invokevirtual ordinal : ()I
    //   218: aload #23
    //   220: ifnonnull -> 254
    //   223: tableswitch default -> 1233, 1 -> 252, 2 -> 1017, 3 -> 1127
    //   248: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   251: athrow
    //   252: iload #10
    //   254: aload #23
    //   256: ifnonnull -> 378
    //   259: ifne -> 376
    //   262: goto -> 269
    //   265: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   268: athrow
    //   269: aload_0
    //   270: getfield U : Lwtf/opal/pa;
    //   273: fload #4
    //   275: iload #5
    //   277: aload #23
    //   279: ifnonnull -> 300
    //   282: goto -> 289
    //   285: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   288: athrow
    //   289: ifeq -> 303
    //   292: goto -> 299
    //   295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: iconst_5
    //   300: goto -> 304
    //   303: iconst_1
    //   304: i2f
    //   305: fsub
    //   306: fload #6
    //   308: fconst_1
    //   309: fsub
    //   310: fload #7
    //   312: fconst_2
    //   313: fadd
    //   314: fconst_1
    //   315: iload #11
    //   317: lload #19
    //   319: bipush #6
    //   321: anewarray java/lang/Object
    //   324: dup_x2
    //   325: dup_x2
    //   326: pop
    //   327: invokestatic valueOf : (J)Ljava/lang/Long;
    //   330: iconst_5
    //   331: swap
    //   332: aastore
    //   333: dup_x1
    //   334: swap
    //   335: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   338: iconst_4
    //   339: swap
    //   340: aastore
    //   341: dup_x1
    //   342: swap
    //   343: invokestatic valueOf : (F)Ljava/lang/Float;
    //   346: iconst_3
    //   347: swap
    //   348: aastore
    //   349: dup_x1
    //   350: swap
    //   351: invokestatic valueOf : (F)Ljava/lang/Float;
    //   354: iconst_2
    //   355: swap
    //   356: aastore
    //   357: dup_x1
    //   358: swap
    //   359: invokestatic valueOf : (F)Ljava/lang/Float;
    //   362: iconst_1
    //   363: swap
    //   364: aastore
    //   365: dup_x1
    //   366: swap
    //   367: invokestatic valueOf : (F)Ljava/lang/Float;
    //   370: iconst_0
    //   371: swap
    //   372: aastore
    //   373: invokevirtual k : ([Ljava/lang/Object;)V
    //   376: iload #10
    //   378: iload #12
    //   380: iconst_1
    //   381: isub
    //   382: if_icmpne -> 498
    //   385: aload_0
    //   386: getfield U : Lwtf/opal/pa;
    //   389: fload #4
    //   391: iload #5
    //   393: aload #23
    //   395: ifnonnull -> 416
    //   398: goto -> 405
    //   401: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   404: athrow
    //   405: ifeq -> 419
    //   408: goto -> 415
    //   411: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   414: athrow
    //   415: iconst_5
    //   416: goto -> 420
    //   419: iconst_1
    //   420: i2f
    //   421: fsub
    //   422: fload #6
    //   424: fload #8
    //   426: fadd
    //   427: fload #7
    //   429: fconst_2
    //   430: fadd
    //   431: fconst_1
    //   432: iload #11
    //   434: lload #19
    //   436: bipush #6
    //   438: anewarray java/lang/Object
    //   441: dup_x2
    //   442: dup_x2
    //   443: pop
    //   444: invokestatic valueOf : (J)Ljava/lang/Long;
    //   447: iconst_5
    //   448: swap
    //   449: aastore
    //   450: dup_x1
    //   451: swap
    //   452: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   455: iconst_4
    //   456: swap
    //   457: aastore
    //   458: dup_x1
    //   459: swap
    //   460: invokestatic valueOf : (F)Ljava/lang/Float;
    //   463: iconst_3
    //   464: swap
    //   465: aastore
    //   466: dup_x1
    //   467: swap
    //   468: invokestatic valueOf : (F)Ljava/lang/Float;
    //   471: iconst_2
    //   472: swap
    //   473: aastore
    //   474: dup_x1
    //   475: swap
    //   476: invokestatic valueOf : (F)Ljava/lang/Float;
    //   479: iconst_1
    //   480: swap
    //   481: aastore
    //   482: dup_x1
    //   483: swap
    //   484: invokestatic valueOf : (F)Ljava/lang/Float;
    //   487: iconst_0
    //   488: swap
    //   489: aastore
    //   490: invokevirtual k : ([Ljava/lang/Object;)V
    //   493: aload #23
    //   495: ifnull -> 815
    //   498: aload_0
    //   499: getfield g : Ljava/util/List;
    //   502: iload #10
    //   504: iconst_1
    //   505: iadd
    //   506: invokeinterface get : (I)Ljava/lang/Object;
    //   511: checkcast wtf/opal/d
    //   514: astore #24
    //   516: aload_0
    //   517: getfield n : Lwtf/opal/bu;
    //   520: aload #13
    //   522: aload #24
    //   524: iconst_0
    //   525: anewarray java/lang/Object
    //   528: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   531: aload_0
    //   532: aload #23
    //   534: ifnonnull -> 568
    //   537: getfield f : Lwtf/opal/ke;
    //   540: invokevirtual z : ()Ljava/lang/Object;
    //   543: checkcast java/lang/Boolean
    //   546: invokevirtual booleanValue : ()Z
    //   549: ifeq -> 651
    //   552: goto -> 559
    //   555: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   558: athrow
    //   559: aload #24
    //   561: goto -> 568
    //   564: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   567: athrow
    //   568: lload #21
    //   570: iconst_1
    //   571: anewarray java/lang/Object
    //   574: dup_x2
    //   575: dup_x2
    //   576: pop
    //   577: invokestatic valueOf : (J)Ljava/lang/Long;
    //   580: iconst_0
    //   581: swap
    //   582: aastore
    //   583: invokevirtual o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   586: aload #23
    //   588: ifnonnull -> 648
    //   591: ifnull -> 651
    //   594: goto -> 601
    //   597: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   600: athrow
    //   601: aload #24
    //   603: lload #21
    //   605: iconst_1
    //   606: anewarray java/lang/Object
    //   609: dup_x2
    //   610: dup_x2
    //   611: pop
    //   612: invokestatic valueOf : (J)Ljava/lang/Long;
    //   615: iconst_0
    //   616: swap
    //   617: aastore
    //   618: invokevirtual o : ([Ljava/lang/Object;)Ljava/lang/String;
    //   621: sipush #14726
    //   624: ldc2_w 8425441089949441399
    //   627: lload #15
    //   629: lxor
    //   630: <illegal opcode> u : (IJ)Ljava/lang/String;
    //   635: swap
    //   636: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   641: goto -> 648
    //   644: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   647: athrow
    //   648: goto -> 654
    //   651: ldc_w ''
    //   654: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   659: ldc_w 10.0
    //   662: lload #17
    //   664: iconst_4
    //   665: anewarray java/lang/Object
    //   668: dup_x2
    //   669: dup_x2
    //   670: pop
    //   671: invokestatic valueOf : (J)Ljava/lang/Long;
    //   674: iconst_3
    //   675: swap
    //   676: aastore
    //   677: dup_x1
    //   678: swap
    //   679: invokestatic valueOf : (F)Ljava/lang/Float;
    //   682: iconst_2
    //   683: swap
    //   684: aastore
    //   685: dup_x1
    //   686: swap
    //   687: iconst_1
    //   688: swap
    //   689: aastore
    //   690: dup_x1
    //   691: swap
    //   692: iconst_0
    //   693: swap
    //   694: aastore
    //   695: invokevirtual p : ([Ljava/lang/Object;)F
    //   698: fstore #25
    //   700: fload #7
    //   702: fload #25
    //   704: ldc_w 4.0
    //   707: fadd
    //   708: fsub
    //   709: fstore #26
    //   711: aload_0
    //   712: getfield U : Lwtf/opal/pa;
    //   715: fload #4
    //   717: iload #5
    //   719: ifeq -> 732
    //   722: ldc_w -5.0
    //   725: goto -> 741
    //   728: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   731: athrow
    //   732: fload #7
    //   734: fload #26
    //   736: fsub
    //   737: ldc_w 0.5
    //   740: fadd
    //   741: fadd
    //   742: fload #6
    //   744: fload #8
    //   746: fadd
    //   747: fload #26
    //   749: ldc_w 0.5
    //   752: fadd
    //   753: fconst_1
    //   754: iload #11
    //   756: lload #19
    //   758: bipush #6
    //   760: anewarray java/lang/Object
    //   763: dup_x2
    //   764: dup_x2
    //   765: pop
    //   766: invokestatic valueOf : (J)Ljava/lang/Long;
    //   769: iconst_5
    //   770: swap
    //   771: aastore
    //   772: dup_x1
    //   773: swap
    //   774: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   777: iconst_4
    //   778: swap
    //   779: aastore
    //   780: dup_x1
    //   781: swap
    //   782: invokestatic valueOf : (F)Ljava/lang/Float;
    //   785: iconst_3
    //   786: swap
    //   787: aastore
    //   788: dup_x1
    //   789: swap
    //   790: invokestatic valueOf : (F)Ljava/lang/Float;
    //   793: iconst_2
    //   794: swap
    //   795: aastore
    //   796: dup_x1
    //   797: swap
    //   798: invokestatic valueOf : (F)Ljava/lang/Float;
    //   801: iconst_1
    //   802: swap
    //   803: aastore
    //   804: dup_x1
    //   805: swap
    //   806: invokestatic valueOf : (F)Ljava/lang/Float;
    //   809: iconst_0
    //   810: swap
    //   811: aastore
    //   812: invokevirtual k : ([Ljava/lang/Object;)V
    //   815: aload_0
    //   816: getfield U : Lwtf/opal/pa;
    //   819: fload #4
    //   821: fload #7
    //   823: fadd
    //   824: iload #5
    //   826: aload #23
    //   828: ifnonnull -> 842
    //   831: ifeq -> 845
    //   834: goto -> 841
    //   837: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   840: athrow
    //   841: iconst_4
    //   842: goto -> 846
    //   845: iconst_0
    //   846: i2f
    //   847: fsub
    //   848: fload #6
    //   850: fconst_1
    //   851: fload #8
    //   853: iload #11
    //   855: lload #19
    //   857: bipush #6
    //   859: anewarray java/lang/Object
    //   862: dup_x2
    //   863: dup_x2
    //   864: pop
    //   865: invokestatic valueOf : (J)Ljava/lang/Long;
    //   868: iconst_5
    //   869: swap
    //   870: aastore
    //   871: dup_x1
    //   872: swap
    //   873: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   876: iconst_4
    //   877: swap
    //   878: aastore
    //   879: dup_x1
    //   880: swap
    //   881: invokestatic valueOf : (F)Ljava/lang/Float;
    //   884: iconst_3
    //   885: swap
    //   886: aastore
    //   887: dup_x1
    //   888: swap
    //   889: invokestatic valueOf : (F)Ljava/lang/Float;
    //   892: iconst_2
    //   893: swap
    //   894: aastore
    //   895: dup_x1
    //   896: swap
    //   897: invokestatic valueOf : (F)Ljava/lang/Float;
    //   900: iconst_1
    //   901: swap
    //   902: aastore
    //   903: dup_x1
    //   904: swap
    //   905: invokestatic valueOf : (F)Ljava/lang/Float;
    //   908: iconst_0
    //   909: swap
    //   910: aastore
    //   911: invokevirtual k : ([Ljava/lang/Object;)V
    //   914: aload_0
    //   915: getfield U : Lwtf/opal/pa;
    //   918: fload #4
    //   920: iload #5
    //   922: aload #23
    //   924: ifnonnull -> 938
    //   927: ifeq -> 941
    //   930: goto -> 937
    //   933: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   936: athrow
    //   937: iconst_4
    //   938: goto -> 942
    //   941: iconst_0
    //   942: i2f
    //   943: fsub
    //   944: fconst_1
    //   945: fsub
    //   946: fload #6
    //   948: fconst_1
    //   949: fload #8
    //   951: iload #11
    //   953: lload #19
    //   955: bipush #6
    //   957: anewarray java/lang/Object
    //   960: dup_x2
    //   961: dup_x2
    //   962: pop
    //   963: invokestatic valueOf : (J)Ljava/lang/Long;
    //   966: iconst_5
    //   967: swap
    //   968: aastore
    //   969: dup_x1
    //   970: swap
    //   971: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   974: iconst_4
    //   975: swap
    //   976: aastore
    //   977: dup_x1
    //   978: swap
    //   979: invokestatic valueOf : (F)Ljava/lang/Float;
    //   982: iconst_3
    //   983: swap
    //   984: aastore
    //   985: dup_x1
    //   986: swap
    //   987: invokestatic valueOf : (F)Ljava/lang/Float;
    //   990: iconst_2
    //   991: swap
    //   992: aastore
    //   993: dup_x1
    //   994: swap
    //   995: invokestatic valueOf : (F)Ljava/lang/Float;
    //   998: iconst_1
    //   999: swap
    //   1000: aastore
    //   1001: dup_x1
    //   1002: swap
    //   1003: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1006: iconst_0
    //   1007: swap
    //   1008: aastore
    //   1009: invokevirtual k : ([Ljava/lang/Object;)V
    //   1012: aload #23
    //   1014: ifnull -> 1233
    //   1017: aload_0
    //   1018: getfield U : Lwtf/opal/pa;
    //   1021: fload #4
    //   1023: iload #5
    //   1025: aload #23
    //   1027: ifnonnull -> 1048
    //   1030: goto -> 1037
    //   1033: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1036: athrow
    //   1037: ifeq -> 1051
    //   1040: goto -> 1047
    //   1043: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1046: athrow
    //   1047: iconst_4
    //   1048: goto -> 1052
    //   1051: iconst_0
    //   1052: i2f
    //   1053: fsub
    //   1054: fconst_1
    //   1055: fsub
    //   1056: fload #6
    //   1058: fconst_1
    //   1059: fload #8
    //   1061: iload #11
    //   1063: lload #19
    //   1065: bipush #6
    //   1067: anewarray java/lang/Object
    //   1070: dup_x2
    //   1071: dup_x2
    //   1072: pop
    //   1073: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1076: iconst_5
    //   1077: swap
    //   1078: aastore
    //   1079: dup_x1
    //   1080: swap
    //   1081: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1084: iconst_4
    //   1085: swap
    //   1086: aastore
    //   1087: dup_x1
    //   1088: swap
    //   1089: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1092: iconst_3
    //   1093: swap
    //   1094: aastore
    //   1095: dup_x1
    //   1096: swap
    //   1097: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1100: iconst_2
    //   1101: swap
    //   1102: aastore
    //   1103: dup_x1
    //   1104: swap
    //   1105: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1108: iconst_1
    //   1109: swap
    //   1110: aastore
    //   1111: dup_x1
    //   1112: swap
    //   1113: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1116: iconst_0
    //   1117: swap
    //   1118: aastore
    //   1119: invokevirtual k : ([Ljava/lang/Object;)V
    //   1122: aload #23
    //   1124: ifnull -> 1233
    //   1127: aload_0
    //   1128: getfield U : Lwtf/opal/pa;
    //   1131: fload #4
    //   1133: fload #14
    //   1135: fadd
    //   1136: iload #5
    //   1138: aload #23
    //   1140: ifnonnull -> 1161
    //   1143: goto -> 1150
    //   1146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1149: athrow
    //   1150: ifeq -> 1164
    //   1153: goto -> 1160
    //   1156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1159: athrow
    //   1160: iconst_0
    //   1161: goto -> 1165
    //   1164: iconst_4
    //   1165: i2f
    //   1166: fadd
    //   1167: fload #6
    //   1169: fconst_1
    //   1170: fload #8
    //   1172: iload #11
    //   1174: lload #19
    //   1176: bipush #6
    //   1178: anewarray java/lang/Object
    //   1181: dup_x2
    //   1182: dup_x2
    //   1183: pop
    //   1184: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1187: iconst_5
    //   1188: swap
    //   1189: aastore
    //   1190: dup_x1
    //   1191: swap
    //   1192: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1195: iconst_4
    //   1196: swap
    //   1197: aastore
    //   1198: dup_x1
    //   1199: swap
    //   1200: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1203: iconst_3
    //   1204: swap
    //   1205: aastore
    //   1206: dup_x1
    //   1207: swap
    //   1208: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1211: iconst_2
    //   1212: swap
    //   1213: aastore
    //   1214: dup_x1
    //   1215: swap
    //   1216: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1219: iconst_1
    //   1220: swap
    //   1221: aastore
    //   1222: dup_x1
    //   1223: swap
    //   1224: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1227: iconst_0
    //   1228: swap
    //   1229: aastore
    //   1230: invokevirtual k : ([Ljava/lang/Object;)V
    //   1233: lload_1
    //   1234: fconst_1
    //   1235: fload_3
    //   1236: fdiv
    //   1237: fconst_1
    //   1238: fload_3
    //   1239: fdiv
    //   1240: invokestatic nvgScale : (JFF)V
    //   1243: return
    // Exception table:
    //   from	to	target	type
    //   44	65	68	wtf/opal/x5
    //   62	85	88	wtf/opal/x5
    //   72	95	98	wtf/opal/x5
    //   218	248	248	wtf/opal/x5
    //   254	262	265	wtf/opal/x5
    //   259	282	285	wtf/opal/x5
    //   269	292	295	wtf/opal/x5
    //   378	398	401	wtf/opal/x5
    //   385	408	411	wtf/opal/x5
    //   516	552	555	wtf/opal/x5
    //   537	561	564	wtf/opal/x5
    //   568	594	597	wtf/opal/x5
    //   591	641	644	wtf/opal/x5
    //   711	728	728	wtf/opal/x5
    //   815	834	837	wtf/opal/x5
    //   846	930	933	wtf/opal/x5
    //   942	1030	1033	wtf/opal/x5
    //   1017	1040	1043	wtf/opal/x5
    //   1052	1143	1146	wtf/opal/x5
    //   1127	1153	1156	wtf/opal/x5
  }
  
  private boolean lambda$new$0(ke paramke) {
    return this.w.z().booleanValue();
  }
  
  static {
    long l = b ^ 0x131449897ABCL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[30];
    boolean bool = false;
    String str;
    int i = (str = "®VÂ¡MC¹¸_\020®&Ô9ÃÑ\n\020\024=ªw\032&\030¯-\034|Ý\013Õµí/þÅ³ïßÅ?\t \026ñ %\013)¬\004ëlåÉË\030\022\\\001¬qQ¼åª'\006æMz,\002\020½ñ§Þ×Õºr¨\002Äø$;Ê\030Èn¶©2é]x\037úÞ¹Gûøã\n\037c\030¼âQZòÉB$´\025§v\026Ýï\034FªÊíåF\030H_ËÓd\t¹{¤´`¤7ù\f+úV\023\006´+éY\020ÚÚeÆbVDgvfdÝ\020;d\036Ýí¡ø\034>&|ÿ·b\001\034\020gõÅ%nÂ³c^UX\026Ì)í8\037Ds;x¯jýuOk-A/`ª\020c\037\fÒw¦\013ºhÖ·K*W§Úm®©ÚÜ®¯ô\027oK¶2^\020'XkõÔ\030w$J<\n³ï¼ \\¬\000]zAy¦_9Â\rb\007³Ný\037|)\013Å?ùË8ç\021Û\020å¢¢í\t 2?8\025\025¸`ê¦ \000*öxl\020S\fÚ*e¼¢29¥Ï`k\007·ÕFùÒÓ\020\027)\034Ç*\021BvÆ7%;er\034\027\020NíC\032sn«æ±¸7ß\037\020®7¡»Gà\000ZzÒÓXY \017Ù¼«Ô£©dAX#áØ^cY'£(n¼ÆbYpê*ArfÍ G¾äéôûN\000!\005\020\"Üfõq\031$IoÓ£zÎè \03007å¯/@Èò·8\r¦\035·õýÐ¡ÎÅM\020s;¼\021¥¤°\024z\021\031Üì¢(Xåy2·ÕÉ÷7ëA\007Æa¹Q\bÿ3âbù¸gèèX>\002rMãÊ}\030'^{!\f:g ÇÎø\021\013Q\003qJÖ¿ò\030-s±ÞuÍÕ#{¿%LE@ç~b\013±\004B\020Xu\0068øôry<\032láÀÊP\020q=i7\t\004±2!7wÓ\002Å(_¯ó})íSöâeG¬d+³KÀ\022Q\\ò\036©DÏ¦°êL0íÙä²Á").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x784A;
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
        throw new RuntimeException("wtf/opal/jl", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = d[i].getBytes("ISO-8859-1");
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
    //   66: ldc_w 'wtf/opal/jl'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4F4;
    if (r[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = p[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])s.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          s.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jl", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      r[i] = Integer.valueOf(j);
    } 
    return r[i].intValue();
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
    //   66: ldc_w 'wtf/opal/jl'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */