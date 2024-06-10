package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.text.DecimalFormat;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class be extends b5<kt> {
  private boolean b;
  
  private Double v;
  
  private final da r;
  
  private final da T;
  
  private final DecimalFormat X;
  
  private static final long c = on.a(4564693036894374249L, -7833051056439650360L, MethodHandles.lookup().lookupClass()).a(97850630779568L);
  
  private static final String d;
  
  private static final long[] f;
  
  private static final Integer[] g;
  
  private static final Map i;
  
  public be(int paramInt1, char paramChar, int paramInt2, kt paramkt) {
    super(paramkt, l3);
    this.r = new da(l2);
    this.T = new da(l2);
    this.X = new DecimalFormat(d);
  }
  
  public void g(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_332
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Integer
    //   14: invokevirtual intValue : ()I
    //   17: istore #7
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Integer
    //   25: invokevirtual intValue : ()I
    //   28: istore_2
    //   29: dup
    //   30: iconst_3
    //   31: aaload
    //   32: checkcast java/lang/Long
    //   35: invokevirtual longValue : ()J
    //   38: lstore #5
    //   40: dup
    //   41: iconst_4
    //   42: aaload
    //   43: checkcast java/lang/Float
    //   46: invokevirtual floatValue : ()F
    //   49: fstore #4
    //   51: pop
    //   52: lload #5
    //   54: dup2
    //   55: ldc2_w 60545063372
    //   58: lxor
    //   59: lstore #8
    //   61: dup2
    //   62: ldc2_w 77055057199651
    //   65: lxor
    //   66: lstore #10
    //   68: dup2
    //   69: ldc2_w 10802718197547
    //   72: lxor
    //   73: lstore #12
    //   75: dup2
    //   76: ldc2_w 101002770853171
    //   79: lxor
    //   80: lstore #14
    //   82: dup2
    //   83: ldc2_w 94556900610908
    //   86: lxor
    //   87: lstore #16
    //   89: dup2
    //   90: ldc2_w 66468241307817
    //   93: lxor
    //   94: lstore #18
    //   96: dup2
    //   97: ldc2_w 130132861335437
    //   100: lxor
    //   101: lstore #20
    //   103: dup2
    //   104: ldc2_w 86228387855835
    //   107: lxor
    //   108: lstore #22
    //   110: dup2
    //   111: ldc2_w 55100416438967
    //   114: lxor
    //   115: lstore #24
    //   117: dup2
    //   118: ldc2_w 19331518352804
    //   121: lxor
    //   122: lstore #26
    //   124: pop2
    //   125: iconst_0
    //   126: anewarray java/lang/Object
    //   129: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   132: iconst_0
    //   133: anewarray java/lang/Object
    //   136: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   139: ldc wtf/opal/jt
    //   141: iconst_1
    //   142: anewarray java/lang/Object
    //   145: dup_x1
    //   146: swap
    //   147: iconst_0
    //   148: swap
    //   149: aastore
    //   150: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   153: checkcast wtf/opal/jt
    //   156: astore #29
    //   158: invokestatic S : ()[Lwtf/opal/d;
    //   161: aload_0
    //   162: aload_0
    //   163: iconst_0
    //   164: anewarray java/lang/Object
    //   167: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   170: checkcast wtf/opal/kt
    //   173: invokevirtual z : ()Ljava/lang/Object;
    //   176: checkcast java/lang/Double
    //   179: putfield v : Ljava/lang/Double;
    //   182: aload_0
    //   183: ldc 10.0
    //   185: iconst_1
    //   186: anewarray java/lang/Object
    //   189: dup_x1
    //   190: swap
    //   191: invokestatic valueOf : (F)Ljava/lang/Float;
    //   194: iconst_0
    //   195: swap
    //   196: aastore
    //   197: invokevirtual q : ([Ljava/lang/Object;)V
    //   200: aload_0
    //   201: getfield e : F
    //   204: ldc 10.0
    //   206: fsub
    //   207: fstore #30
    //   209: getstatic wtf/opal/be.h : Lwtf/opal/pa;
    //   212: aload_0
    //   213: getfield U : F
    //   216: aload_0
    //   217: getfield t : F
    //   220: aload_0
    //   221: getfield e : F
    //   224: aload_0
    //   225: getfield m : F
    //   228: sipush #5768
    //   231: ldc2_w 6459296656688259780
    //   234: lload #5
    //   236: lxor
    //   237: <illegal opcode> v : (IJ)I
    //   242: lload #18
    //   244: bipush #6
    //   246: anewarray java/lang/Object
    //   249: dup_x2
    //   250: dup_x2
    //   251: pop
    //   252: invokestatic valueOf : (J)Ljava/lang/Long;
    //   255: iconst_5
    //   256: swap
    //   257: aastore
    //   258: dup_x1
    //   259: swap
    //   260: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   263: iconst_4
    //   264: swap
    //   265: aastore
    //   266: dup_x1
    //   267: swap
    //   268: invokestatic valueOf : (F)Ljava/lang/Float;
    //   271: iconst_3
    //   272: swap
    //   273: aastore
    //   274: dup_x1
    //   275: swap
    //   276: invokestatic valueOf : (F)Ljava/lang/Float;
    //   279: iconst_2
    //   280: swap
    //   281: aastore
    //   282: dup_x1
    //   283: swap
    //   284: invokestatic valueOf : (F)Ljava/lang/Float;
    //   287: iconst_1
    //   288: swap
    //   289: aastore
    //   290: dup_x1
    //   291: swap
    //   292: invokestatic valueOf : (F)Ljava/lang/Float;
    //   295: iconst_0
    //   296: swap
    //   297: aastore
    //   298: invokevirtual k : ([Ljava/lang/Object;)V
    //   301: astore #28
    //   303: aload_0
    //   304: aload #28
    //   306: ifnull -> 568
    //   309: getfield b : Z
    //   312: ifeq -> 567
    //   315: goto -> 322
    //   318: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   321: athrow
    //   322: fconst_1
    //   323: fconst_0
    //   324: iload #7
    //   326: i2f
    //   327: aload_0
    //   328: getfield U : F
    //   331: ldc 5.0
    //   333: fadd
    //   334: fsub
    //   335: fload #30
    //   337: fdiv
    //   338: invokestatic max : (FF)F
    //   341: invokestatic min : (FF)F
    //   344: fstore #31
    //   346: aload_0
    //   347: aload_0
    //   348: iconst_0
    //   349: anewarray java/lang/Object
    //   352: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   355: checkcast wtf/opal/kt
    //   358: iconst_0
    //   359: anewarray java/lang/Object
    //   362: invokevirtual O : ([Ljava/lang/Object;)D
    //   365: aload_0
    //   366: iconst_0
    //   367: anewarray java/lang/Object
    //   370: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   373: checkcast wtf/opal/kt
    //   376: iconst_0
    //   377: anewarray java/lang/Object
    //   380: invokevirtual T : ([Ljava/lang/Object;)D
    //   383: fload #31
    //   385: f2d
    //   386: iconst_3
    //   387: anewarray java/lang/Object
    //   390: dup_x2
    //   391: dup_x2
    //   392: pop
    //   393: invokestatic valueOf : (D)Ljava/lang/Double;
    //   396: iconst_2
    //   397: swap
    //   398: aastore
    //   399: dup_x2
    //   400: dup_x2
    //   401: pop
    //   402: invokestatic valueOf : (D)Ljava/lang/Double;
    //   405: iconst_1
    //   406: swap
    //   407: aastore
    //   408: dup_x2
    //   409: dup_x2
    //   410: pop
    //   411: invokestatic valueOf : (D)Ljava/lang/Double;
    //   414: iconst_0
    //   415: swap
    //   416: aastore
    //   417: invokestatic Y : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   420: putfield v : Ljava/lang/Double;
    //   423: aload_0
    //   424: aload_0
    //   425: getfield v : Ljava/lang/Double;
    //   428: invokevirtual doubleValue : ()D
    //   431: aload_0
    //   432: iconst_0
    //   433: anewarray java/lang/Object
    //   436: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   439: checkcast wtf/opal/kt
    //   442: iconst_0
    //   443: anewarray java/lang/Object
    //   446: invokevirtual O : ([Ljava/lang/Object;)D
    //   449: aload_0
    //   450: iconst_0
    //   451: anewarray java/lang/Object
    //   454: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   457: checkcast wtf/opal/kt
    //   460: iconst_0
    //   461: anewarray java/lang/Object
    //   464: invokevirtual T : ([Ljava/lang/Object;)D
    //   467: iconst_3
    //   468: anewarray java/lang/Object
    //   471: dup_x2
    //   472: dup_x2
    //   473: pop
    //   474: invokestatic valueOf : (D)Ljava/lang/Double;
    //   477: iconst_2
    //   478: swap
    //   479: aastore
    //   480: dup_x2
    //   481: dup_x2
    //   482: pop
    //   483: invokestatic valueOf : (D)Ljava/lang/Double;
    //   486: iconst_1
    //   487: swap
    //   488: aastore
    //   489: dup_x2
    //   490: dup_x2
    //   491: pop
    //   492: invokestatic valueOf : (D)Ljava/lang/Double;
    //   495: iconst_0
    //   496: swap
    //   497: aastore
    //   498: invokestatic M : ([Ljava/lang/Object;)D
    //   501: invokestatic valueOf : (D)Ljava/lang/Double;
    //   504: putfield v : Ljava/lang/Double;
    //   507: aload_0
    //   508: aload_0
    //   509: getfield v : Ljava/lang/Double;
    //   512: invokevirtual doubleValue : ()D
    //   515: dconst_1
    //   516: aload_0
    //   517: iconst_0
    //   518: anewarray java/lang/Object
    //   521: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   524: checkcast wtf/opal/kt
    //   527: iconst_0
    //   528: anewarray java/lang/Object
    //   531: invokevirtual w : ([Ljava/lang/Object;)D
    //   534: ddiv
    //   535: dmul
    //   536: invokestatic round : (D)J
    //   539: l2d
    //   540: dconst_1
    //   541: aload_0
    //   542: iconst_0
    //   543: anewarray java/lang/Object
    //   546: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   549: checkcast wtf/opal/kt
    //   552: iconst_0
    //   553: anewarray java/lang/Object
    //   556: invokevirtual w : ([Ljava/lang/Object;)D
    //   559: ddiv
    //   560: ddiv
    //   561: invokestatic valueOf : (D)Ljava/lang/Double;
    //   564: putfield v : Ljava/lang/Double;
    //   567: aload_0
    //   568: getfield v : Ljava/lang/Double;
    //   571: invokevirtual doubleValue : ()D
    //   574: aload_0
    //   575: iconst_0
    //   576: anewarray java/lang/Object
    //   579: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   582: checkcast wtf/opal/kt
    //   585: iconst_0
    //   586: anewarray java/lang/Object
    //   589: invokevirtual O : ([Ljava/lang/Object;)D
    //   592: dsub
    //   593: aload_0
    //   594: iconst_0
    //   595: anewarray java/lang/Object
    //   598: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   601: checkcast wtf/opal/kt
    //   604: iconst_0
    //   605: anewarray java/lang/Object
    //   608: invokevirtual T : ([Ljava/lang/Object;)D
    //   611: aload_0
    //   612: iconst_0
    //   613: anewarray java/lang/Object
    //   616: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   619: checkcast wtf/opal/kt
    //   622: iconst_0
    //   623: anewarray java/lang/Object
    //   626: invokevirtual O : ([Ljava/lang/Object;)D
    //   629: dsub
    //   630: ddiv
    //   631: dstore #31
    //   633: aload_0
    //   634: getfield r : Lwtf/opal/da;
    //   637: fload #30
    //   639: f2d
    //   640: dload #31
    //   642: dmul
    //   643: d2f
    //   644: lload #8
    //   646: sipush #5273
    //   649: ldc2_w 6718386210052907220
    //   652: lload #5
    //   654: lxor
    //   655: <illegal opcode> v : (IJ)I
    //   660: iconst_3
    //   661: anewarray java/lang/Object
    //   664: dup_x1
    //   665: swap
    //   666: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   669: iconst_2
    //   670: swap
    //   671: aastore
    //   672: dup_x2
    //   673: dup_x2
    //   674: pop
    //   675: invokestatic valueOf : (J)Ljava/lang/Long;
    //   678: iconst_1
    //   679: swap
    //   680: aastore
    //   681: dup_x1
    //   682: swap
    //   683: invokestatic valueOf : (F)Ljava/lang/Float;
    //   686: iconst_0
    //   687: swap
    //   688: aastore
    //   689: invokevirtual e : ([Ljava/lang/Object;)V
    //   692: getstatic wtf/opal/be.Z : Lwtf/opal/bu;
    //   695: aload_0
    //   696: iconst_0
    //   697: anewarray java/lang/Object
    //   700: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   703: checkcast wtf/opal/kt
    //   706: iconst_0
    //   707: anewarray java/lang/Object
    //   710: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   713: lload #20
    //   715: dup2_x1
    //   716: pop2
    //   717: ldc 6.5
    //   719: iconst_3
    //   720: anewarray java/lang/Object
    //   723: dup_x1
    //   724: swap
    //   725: invokestatic valueOf : (F)Ljava/lang/Float;
    //   728: iconst_2
    //   729: swap
    //   730: aastore
    //   731: dup_x1
    //   732: swap
    //   733: iconst_1
    //   734: swap
    //   735: aastore
    //   736: dup_x2
    //   737: dup_x2
    //   738: pop
    //   739: invokestatic valueOf : (J)Ljava/lang/Long;
    //   742: iconst_0
    //   743: swap
    //   744: aastore
    //   745: invokevirtual A : ([Ljava/lang/Object;)F
    //   748: ldc 6.0
    //   750: fadd
    //   751: fstore #33
    //   753: getstatic wtf/opal/be.h : Lwtf/opal/pa;
    //   756: aload_0
    //   757: getfield U : F
    //   760: ldc 5.0
    //   762: fadd
    //   763: aload_0
    //   764: getfield t : F
    //   767: fload #33
    //   769: fadd
    //   770: fload #30
    //   772: ldc 4.0
    //   774: fconst_2
    //   775: sipush #6817
    //   778: ldc2_w 6959288845219835630
    //   781: lload #5
    //   783: lxor
    //   784: <illegal opcode> v : (IJ)I
    //   789: lload #16
    //   791: bipush #7
    //   793: anewarray java/lang/Object
    //   796: dup_x2
    //   797: dup_x2
    //   798: pop
    //   799: invokestatic valueOf : (J)Ljava/lang/Long;
    //   802: bipush #6
    //   804: swap
    //   805: aastore
    //   806: dup_x1
    //   807: swap
    //   808: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   811: iconst_5
    //   812: swap
    //   813: aastore
    //   814: dup_x1
    //   815: swap
    //   816: invokestatic valueOf : (F)Ljava/lang/Float;
    //   819: iconst_4
    //   820: swap
    //   821: aastore
    //   822: dup_x1
    //   823: swap
    //   824: invokestatic valueOf : (F)Ljava/lang/Float;
    //   827: iconst_3
    //   828: swap
    //   829: aastore
    //   830: dup_x1
    //   831: swap
    //   832: invokestatic valueOf : (F)Ljava/lang/Float;
    //   835: iconst_2
    //   836: swap
    //   837: aastore
    //   838: dup_x1
    //   839: swap
    //   840: invokestatic valueOf : (F)Ljava/lang/Float;
    //   843: iconst_1
    //   844: swap
    //   845: aastore
    //   846: dup_x1
    //   847: swap
    //   848: invokestatic valueOf : (F)Ljava/lang/Float;
    //   851: iconst_0
    //   852: swap
    //   853: aastore
    //   854: invokevirtual M : ([Ljava/lang/Object;)V
    //   857: getstatic wtf/opal/be.h : Lwtf/opal/pa;
    //   860: aload_0
    //   861: getfield U : F
    //   864: ldc 5.0
    //   866: fadd
    //   867: aload_0
    //   868: getfield t : F
    //   871: fload #33
    //   873: fadd
    //   874: aload_0
    //   875: getfield r : Lwtf/opal/da;
    //   878: lload #24
    //   880: iconst_1
    //   881: anewarray java/lang/Object
    //   884: dup_x2
    //   885: dup_x2
    //   886: pop
    //   887: invokestatic valueOf : (J)Ljava/lang/Long;
    //   890: iconst_0
    //   891: swap
    //   892: aastore
    //   893: invokevirtual s : ([Ljava/lang/Object;)F
    //   896: ldc 4.0
    //   898: fconst_2
    //   899: aload #29
    //   901: iconst_0
    //   902: anewarray java/lang/Object
    //   905: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   908: invokevirtual z : ()Ljava/lang/Object;
    //   911: checkcast java/lang/Integer
    //   914: invokevirtual intValue : ()I
    //   917: lload #16
    //   919: bipush #7
    //   921: anewarray java/lang/Object
    //   924: dup_x2
    //   925: dup_x2
    //   926: pop
    //   927: invokestatic valueOf : (J)Ljava/lang/Long;
    //   930: bipush #6
    //   932: swap
    //   933: aastore
    //   934: dup_x1
    //   935: swap
    //   936: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   939: iconst_5
    //   940: swap
    //   941: aastore
    //   942: dup_x1
    //   943: swap
    //   944: invokestatic valueOf : (F)Ljava/lang/Float;
    //   947: iconst_4
    //   948: swap
    //   949: aastore
    //   950: dup_x1
    //   951: swap
    //   952: invokestatic valueOf : (F)Ljava/lang/Float;
    //   955: iconst_3
    //   956: swap
    //   957: aastore
    //   958: dup_x1
    //   959: swap
    //   960: invokestatic valueOf : (F)Ljava/lang/Float;
    //   963: iconst_2
    //   964: swap
    //   965: aastore
    //   966: dup_x1
    //   967: swap
    //   968: invokestatic valueOf : (F)Ljava/lang/Float;
    //   971: iconst_1
    //   972: swap
    //   973: aastore
    //   974: dup_x1
    //   975: swap
    //   976: invokestatic valueOf : (F)Ljava/lang/Float;
    //   979: iconst_0
    //   980: swap
    //   981: aastore
    //   982: invokevirtual M : ([Ljava/lang/Object;)V
    //   985: ldc 4.0
    //   987: fstore #34
    //   989: getstatic wtf/opal/be.h : Lwtf/opal/pa;
    //   992: aload_0
    //   993: getfield U : F
    //   996: ldc 5.0
    //   998: fadd
    //   999: aload_0
    //   1000: getfield r : Lwtf/opal/da;
    //   1003: lload #24
    //   1005: iconst_1
    //   1006: anewarray java/lang/Object
    //   1009: dup_x2
    //   1010: dup_x2
    //   1011: pop
    //   1012: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1015: iconst_0
    //   1016: swap
    //   1017: aastore
    //   1018: invokevirtual s : ([Ljava/lang/Object;)F
    //   1021: fadd
    //   1022: fload #34
    //   1024: fconst_2
    //   1025: fdiv
    //   1026: fconst_2
    //   1027: fadd
    //   1028: fsub
    //   1029: aload_0
    //   1030: getfield t : F
    //   1033: fload #33
    //   1035: fadd
    //   1036: fload #34
    //   1038: fload #34
    //   1040: fload #34
    //   1042: fconst_2
    //   1043: fdiv
    //   1044: fconst_1
    //   1045: fsub
    //   1046: sipush #30352
    //   1049: ldc2_w 5588424266479518430
    //   1052: lload #5
    //   1054: lxor
    //   1055: <illegal opcode> v : (IJ)I
    //   1060: lload #16
    //   1062: bipush #7
    //   1064: anewarray java/lang/Object
    //   1067: dup_x2
    //   1068: dup_x2
    //   1069: pop
    //   1070: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1073: bipush #6
    //   1075: swap
    //   1076: aastore
    //   1077: dup_x1
    //   1078: swap
    //   1079: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1082: iconst_5
    //   1083: swap
    //   1084: aastore
    //   1085: dup_x1
    //   1086: swap
    //   1087: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1090: iconst_4
    //   1091: swap
    //   1092: aastore
    //   1093: dup_x1
    //   1094: swap
    //   1095: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1098: iconst_3
    //   1099: swap
    //   1100: aastore
    //   1101: dup_x1
    //   1102: swap
    //   1103: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1106: iconst_2
    //   1107: swap
    //   1108: aastore
    //   1109: dup_x1
    //   1110: swap
    //   1111: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1114: iconst_1
    //   1115: swap
    //   1116: aastore
    //   1117: dup_x1
    //   1118: swap
    //   1119: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1122: iconst_0
    //   1123: swap
    //   1124: aastore
    //   1125: invokevirtual M : ([Ljava/lang/Object;)V
    //   1128: ldc_w 5.5
    //   1131: fstore #34
    //   1133: getstatic wtf/opal/be.h : Lwtf/opal/pa;
    //   1136: aload_0
    //   1137: getfield U : F
    //   1140: ldc 5.0
    //   1142: fadd
    //   1143: aload_0
    //   1144: getfield r : Lwtf/opal/da;
    //   1147: lload #24
    //   1149: iconst_1
    //   1150: anewarray java/lang/Object
    //   1153: dup_x2
    //   1154: dup_x2
    //   1155: pop
    //   1156: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1159: iconst_0
    //   1160: swap
    //   1161: aastore
    //   1162: invokevirtual s : ([Ljava/lang/Object;)F
    //   1165: fadd
    //   1166: fload #34
    //   1168: fconst_2
    //   1169: fdiv
    //   1170: fsub
    //   1171: aload_0
    //   1172: getfield t : F
    //   1175: fload #33
    //   1177: fadd
    //   1178: ldc_w 0.75
    //   1181: fsub
    //   1182: fload #34
    //   1184: fload #34
    //   1186: fload #34
    //   1188: fconst_2
    //   1189: fdiv
    //   1190: iconst_m1
    //   1191: lload #16
    //   1193: bipush #7
    //   1195: anewarray java/lang/Object
    //   1198: dup_x2
    //   1199: dup_x2
    //   1200: pop
    //   1201: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1204: bipush #6
    //   1206: swap
    //   1207: aastore
    //   1208: dup_x1
    //   1209: swap
    //   1210: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1213: iconst_5
    //   1214: swap
    //   1215: aastore
    //   1216: dup_x1
    //   1217: swap
    //   1218: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1221: iconst_4
    //   1222: swap
    //   1223: aastore
    //   1224: dup_x1
    //   1225: swap
    //   1226: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1229: iconst_3
    //   1230: swap
    //   1231: aastore
    //   1232: dup_x1
    //   1233: swap
    //   1234: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1237: iconst_2
    //   1238: swap
    //   1239: aastore
    //   1240: dup_x1
    //   1241: swap
    //   1242: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1245: iconst_1
    //   1246: swap
    //   1247: aastore
    //   1248: dup_x1
    //   1249: swap
    //   1250: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1253: iconst_0
    //   1254: swap
    //   1255: aastore
    //   1256: invokevirtual M : ([Ljava/lang/Object;)V
    //   1259: aload_0
    //   1260: getfield X : Ljava/text/DecimalFormat;
    //   1263: aload_0
    //   1264: getfield v : Ljava/lang/Double;
    //   1267: invokevirtual format : (Ljava/lang/Object;)Ljava/lang/String;
    //   1270: astore #35
    //   1272: aload_0
    //   1273: getfield U : F
    //   1276: f2d
    //   1277: fload #30
    //   1279: f2d
    //   1280: dload #31
    //   1282: dmul
    //   1283: dadd
    //   1284: d2f
    //   1285: ldc_w 1.5
    //   1288: fsub
    //   1289: fstore #36
    //   1291: aload_0
    //   1292: getfield U : F
    //   1295: ldc 4.0
    //   1297: fadd
    //   1298: fload #36
    //   1300: invokestatic max : (FF)F
    //   1303: fstore #36
    //   1305: aload_0
    //   1306: getfield U : F
    //   1309: ldc 5.0
    //   1311: fadd
    //   1312: fload #30
    //   1314: fadd
    //   1315: getstatic wtf/opal/be.Z : Lwtf/opal/bu;
    //   1318: getstatic wtf/opal/lx.MEDIUM : Lwtf/opal/lx;
    //   1321: aload #35
    //   1323: ldc 6.5
    //   1325: lload #10
    //   1327: iconst_4
    //   1328: anewarray java/lang/Object
    //   1331: dup_x2
    //   1332: dup_x2
    //   1333: pop
    //   1334: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1337: iconst_3
    //   1338: swap
    //   1339: aastore
    //   1340: dup_x1
    //   1341: swap
    //   1342: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1345: iconst_2
    //   1346: swap
    //   1347: aastore
    //   1348: dup_x1
    //   1349: swap
    //   1350: iconst_1
    //   1351: swap
    //   1352: aastore
    //   1353: dup_x1
    //   1354: swap
    //   1355: iconst_0
    //   1356: swap
    //   1357: aastore
    //   1358: invokevirtual p : ([Ljava/lang/Object;)F
    //   1361: fsub
    //   1362: fload #36
    //   1364: invokestatic min : (FF)F
    //   1367: lload #5
    //   1369: lconst_0
    //   1370: lcmp
    //   1371: iflt -> 1640
    //   1374: fstore #36
    //   1376: aload #28
    //   1378: ifnull -> 1581
    //   1381: aload_0
    //   1382: iconst_0
    //   1383: anewarray java/lang/Object
    //   1386: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1389: checkcast wtf/opal/kt
    //   1392: iconst_0
    //   1393: anewarray java/lang/Object
    //   1396: invokevirtual f : ([Ljava/lang/Object;)Z
    //   1399: ifne -> 1463
    //   1402: goto -> 1409
    //   1405: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1408: athrow
    //   1409: aload_0
    //   1410: iconst_0
    //   1411: anewarray java/lang/Object
    //   1414: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1417: checkcast wtf/opal/kt
    //   1420: aload_0
    //   1421: getfield v : Ljava/lang/Double;
    //   1424: invokevirtual doubleValue : ()D
    //   1427: lload #14
    //   1429: dup2_x2
    //   1430: pop2
    //   1431: iconst_2
    //   1432: anewarray java/lang/Object
    //   1435: dup_x2
    //   1436: dup_x2
    //   1437: pop
    //   1438: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1441: iconst_1
    //   1442: swap
    //   1443: aastore
    //   1444: dup_x2
    //   1445: dup_x2
    //   1446: pop
    //   1447: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1450: iconst_0
    //   1451: swap
    //   1452: aastore
    //   1453: invokevirtual r : ([Ljava/lang/Object;)V
    //   1456: goto -> 1463
    //   1459: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1462: athrow
    //   1463: getstatic wtf/opal/be.Z : Lwtf/opal/bu;
    //   1466: aload_3
    //   1467: aload_0
    //   1468: iconst_0
    //   1469: anewarray java/lang/Object
    //   1472: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1475: checkcast wtf/opal/kt
    //   1478: iconst_0
    //   1479: anewarray java/lang/Object
    //   1482: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1485: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
    //   1490: aload_0
    //   1491: getfield U : F
    //   1494: ldc_w 3.0
    //   1497: fadd
    //   1498: aload_0
    //   1499: getfield t : F
    //   1502: ldc_w 3.0
    //   1505: fadd
    //   1506: ldc 6.5
    //   1508: iconst_m1
    //   1509: iconst_0
    //   1510: lload #22
    //   1512: bipush #8
    //   1514: anewarray java/lang/Object
    //   1517: dup_x2
    //   1518: dup_x2
    //   1519: pop
    //   1520: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1523: bipush #7
    //   1525: swap
    //   1526: aastore
    //   1527: dup_x1
    //   1528: swap
    //   1529: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1532: bipush #6
    //   1534: swap
    //   1535: aastore
    //   1536: dup_x1
    //   1537: swap
    //   1538: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1541: iconst_5
    //   1542: swap
    //   1543: aastore
    //   1544: dup_x1
    //   1545: swap
    //   1546: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1549: iconst_4
    //   1550: swap
    //   1551: aastore
    //   1552: dup_x1
    //   1553: swap
    //   1554: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1557: iconst_3
    //   1558: swap
    //   1559: aastore
    //   1560: dup_x1
    //   1561: swap
    //   1562: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1565: iconst_2
    //   1566: swap
    //   1567: aastore
    //   1568: dup_x1
    //   1569: swap
    //   1570: iconst_1
    //   1571: swap
    //   1572: aastore
    //   1573: dup_x1
    //   1574: swap
    //   1575: iconst_0
    //   1576: swap
    //   1577: aastore
    //   1578: invokevirtual B : ([Ljava/lang/Object;)V
    //   1581: getstatic wtf/opal/be.Z : Lwtf/opal/bu;
    //   1584: aload_0
    //   1585: iconst_0
    //   1586: anewarray java/lang/Object
    //   1589: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   1592: checkcast wtf/opal/kt
    //   1595: iconst_0
    //   1596: anewarray java/lang/Object
    //   1599: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1602: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;)Ljava/lang/String;
    //   1607: ldc 6.5
    //   1609: lload #12
    //   1611: iconst_3
    //   1612: anewarray java/lang/Object
    //   1615: dup_x2
    //   1616: dup_x2
    //   1617: pop
    //   1618: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1621: iconst_2
    //   1622: swap
    //   1623: aastore
    //   1624: dup_x1
    //   1625: swap
    //   1626: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1629: iconst_1
    //   1630: swap
    //   1631: aastore
    //   1632: dup_x1
    //   1633: swap
    //   1634: iconst_0
    //   1635: swap
    //   1636: aastore
    //   1637: invokevirtual s : ([Ljava/lang/Object;)F
    //   1640: fstore #37
    //   1642: getstatic wtf/opal/be.Z : Lwtf/opal/bu;
    //   1645: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   1648: aload_3
    //   1649: aload #35
    //   1651: aload_0
    //   1652: getfield U : F
    //   1655: ldc_w 3.0
    //   1658: fadd
    //   1659: fload #37
    //   1661: fadd
    //   1662: fconst_2
    //   1663: fadd
    //   1664: aload_0
    //   1665: getfield t : F
    //   1668: ldc_w 3.0
    //   1671: fadd
    //   1672: lload #26
    //   1674: dup2_x1
    //   1675: pop2
    //   1676: ldc 6.5
    //   1678: iconst_m1
    //   1679: iconst_0
    //   1680: bipush #9
    //   1682: anewarray java/lang/Object
    //   1685: dup_x1
    //   1686: swap
    //   1687: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1690: bipush #8
    //   1692: swap
    //   1693: aastore
    //   1694: dup_x1
    //   1695: swap
    //   1696: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1699: bipush #7
    //   1701: swap
    //   1702: aastore
    //   1703: dup_x1
    //   1704: swap
    //   1705: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1708: bipush #6
    //   1710: swap
    //   1711: aastore
    //   1712: dup_x1
    //   1713: swap
    //   1714: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1717: iconst_5
    //   1718: swap
    //   1719: aastore
    //   1720: dup_x2
    //   1721: dup_x2
    //   1722: pop
    //   1723: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1726: iconst_4
    //   1727: swap
    //   1728: aastore
    //   1729: dup_x1
    //   1730: swap
    //   1731: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1734: iconst_3
    //   1735: swap
    //   1736: aastore
    //   1737: dup_x1
    //   1738: swap
    //   1739: iconst_2
    //   1740: swap
    //   1741: aastore
    //   1742: dup_x1
    //   1743: swap
    //   1744: iconst_1
    //   1745: swap
    //   1746: aastore
    //   1747: dup_x1
    //   1748: swap
    //   1749: iconst_0
    //   1750: swap
    //   1751: aastore
    //   1752: invokevirtual H : ([Ljava/lang/Object;)V
    //   1755: invokestatic D : ()[Lwtf/opal/d;
    //   1758: lload #5
    //   1760: lconst_0
    //   1761: lcmp
    //   1762: iflt -> 1772
    //   1765: ifnull -> 1782
    //   1768: iconst_3
    //   1769: anewarray wtf/opal/d
    //   1772: invokestatic r : ([Lwtf/opal/d;)V
    //   1775: goto -> 1782
    //   1778: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1781: athrow
    //   1782: return
    // Exception table:
    //   from	to	target	type
    //   303	315	318	wtf/opal/x5
    //   1376	1402	1405	wtf/opal/x5
    //   1381	1456	1459	wtf/opal/x5
    //   1642	1775	1778	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    double d1 = ((Double)paramArrayOfObject[0]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[1]).doubleValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
    long l2 = l1 ^ 0x52EBE2FCF81CL;
    d[] arrayOfD = bg.S();
    try {
      new Object[7];
      (new Object[7])[6] = Double.valueOf(d2);
      new Object[7];
      (new Object[7])[5] = Double.valueOf(d1);
      (new Object[7])[4] = Float.valueOf(this.m);
      (new Object[7])[3] = Float.valueOf(this.e);
      (new Object[7])[2] = Float.valueOf(this.t);
      (new Object[7])[1] = Float.valueOf(this.U);
      new Object[7];
      if (arrayOfD != null)
        if (u1.Z(new Object[] { Long.valueOf(l2) })) {
        
        } else {
          return;
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (!u1.Z(new Object[] { Long.valueOf(l2) }))
        this.b = true; 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void c(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #4
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Double
    //   28: invokevirtual doubleValue : ()D
    //   31: dstore #6
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore #8
    //   44: pop
    //   45: lload_2
    //   46: dup2
    //   47: ldc2_w 33135905112199
    //   50: lxor
    //   51: lstore #9
    //   53: pop2
    //   54: invokestatic S : ()[Lwtf/opal/d;
    //   57: astore #11
    //   59: aload_0
    //   60: getfield b : Z
    //   63: aload #11
    //   65: ifnull -> 80
    //   68: ifeq -> 200
    //   71: goto -> 78
    //   74: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   77: athrow
    //   78: iload #8
    //   80: lload_2
    //   81: lconst_0
    //   82: lcmp
    //   83: iflt -> 138
    //   86: aload #11
    //   88: ifnull -> 138
    //   91: ifne -> 200
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: aload_0
    //   102: aload #11
    //   104: ifnull -> 196
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: iconst_0
    //   115: anewarray java/lang/Object
    //   118: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   121: checkcast wtf/opal/kt
    //   124: iconst_0
    //   125: anewarray java/lang/Object
    //   128: invokevirtual f : ([Ljava/lang/Object;)Z
    //   131: goto -> 138
    //   134: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   137: athrow
    //   138: ifeq -> 195
    //   141: aload_0
    //   142: iconst_0
    //   143: anewarray java/lang/Object
    //   146: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   149: checkcast wtf/opal/kt
    //   152: aload_0
    //   153: getfield v : Ljava/lang/Double;
    //   156: invokevirtual doubleValue : ()D
    //   159: lload #9
    //   161: dup2_x2
    //   162: pop2
    //   163: iconst_2
    //   164: anewarray java/lang/Object
    //   167: dup_x2
    //   168: dup_x2
    //   169: pop
    //   170: invokestatic valueOf : (D)Ljava/lang/Double;
    //   173: iconst_1
    //   174: swap
    //   175: aastore
    //   176: dup_x2
    //   177: dup_x2
    //   178: pop
    //   179: invokestatic valueOf : (J)Ljava/lang/Long;
    //   182: iconst_0
    //   183: swap
    //   184: aastore
    //   185: invokevirtual r : ([Ljava/lang/Object;)V
    //   188: goto -> 195
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: aload_0
    //   196: iconst_0
    //   197: putfield b : Z
    //   200: return
    // Exception table:
    //   from	to	target	type
    //   59	71	74	wtf/opal/x5
    //   80	94	97	wtf/opal/x5
    //   91	107	110	wtf/opal/x5
    //   101	131	134	wtf/opal/x5
    //   138	188	191	wtf/opal/x5
  }
  
  public void u() {}
  
  static {
    long l = c ^ 0x78E85DB5A465L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xEDF;
    if (g[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = f[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])i.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          i.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/be", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      g[i] = Integer.valueOf(j);
    } 
    return g[i].intValue();
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
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/be'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\be.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */