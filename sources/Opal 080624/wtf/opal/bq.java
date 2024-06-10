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

public final class bq extends b5<ke> {
  private final dk D;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map f;
  
  public bq(short paramShort, long paramLong, ke paramke) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: lload_2
    //   6: bipush #16
    //   8: lshl
    //   9: bipush #16
    //   11: lushr
    //   12: lor
    //   13: getstatic wtf/opal/bq.b : J
    //   16: lxor
    //   17: lstore #5
    //   19: lload #5
    //   21: dup2
    //   22: ldc2_w 24498074065242
    //   25: lxor
    //   26: dup2
    //   27: bipush #32
    //   29: lushr
    //   30: l2i
    //   31: istore #7
    //   33: dup2
    //   34: bipush #32
    //   36: lshl
    //   37: bipush #48
    //   39: lushr
    //   40: l2i
    //   41: istore #8
    //   43: dup2
    //   44: bipush #48
    //   46: lshl
    //   47: bipush #48
    //   49: lushr
    //   50: l2i
    //   51: istore #9
    //   53: pop2
    //   54: dup2
    //   55: ldc2_w 125043234714056
    //   58: lxor
    //   59: lstore #10
    //   61: pop2
    //   62: aload_0
    //   63: aload #4
    //   65: lload #10
    //   67: invokespecial <init> : (Lwtf/opal/k3;J)V
    //   70: aload_0
    //   71: new wtf/opal/dk
    //   74: dup
    //   75: sipush #1045
    //   78: ldc2_w 6748495083746217552
    //   81: lload #5
    //   83: lxor
    //   84: <illegal opcode> x : (IJ)I
    //   89: iload #7
    //   91: dconst_1
    //   92: iload #8
    //   94: i2c
    //   95: iload #9
    //   97: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   100: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   103: putfield D : Lwtf/opal/dk;
    //   106: return
  }
  
  public void g(Object[] paramArrayOfObject) {
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
    //   12: checkcast java/lang/Integer
    //   15: invokevirtual intValue : ()I
    //   18: istore_2
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/Integer
    //   25: invokevirtual intValue : ()I
    //   28: istore #4
    //   30: dup
    //   31: iconst_3
    //   32: aaload
    //   33: checkcast java/lang/Long
    //   36: invokevirtual longValue : ()J
    //   39: lstore #6
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Float
    //   47: invokevirtual floatValue : ()F
    //   50: fstore_3
    //   51: pop
    //   52: lload #6
    //   54: dup2
    //   55: ldc2_w 28729273170977
    //   58: lxor
    //   59: lstore #8
    //   61: dup2
    //   62: ldc2_w 94556900610908
    //   65: lxor
    //   66: lstore #10
    //   68: dup2
    //   69: ldc2_w 69422246435098
    //   72: lxor
    //   73: lstore #12
    //   75: dup2
    //   76: ldc2_w 66468241307817
    //   79: lxor
    //   80: lstore #14
    //   82: dup2
    //   83: ldc2_w 130132861335437
    //   86: lxor
    //   87: lstore #16
    //   89: dup2
    //   90: ldc2_w 135688353470910
    //   93: lxor
    //   94: lstore #18
    //   96: dup2
    //   97: ldc2_w 53439933111312
    //   100: lxor
    //   101: lstore #20
    //   103: pop2
    //   104: invokestatic S : ()[Lwtf/opal/d;
    //   107: iconst_0
    //   108: anewarray java/lang/Object
    //   111: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   114: iconst_0
    //   115: anewarray java/lang/Object
    //   118: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   121: ldc wtf/opal/jt
    //   123: iconst_1
    //   124: anewarray java/lang/Object
    //   127: dup_x1
    //   128: swap
    //   129: iconst_0
    //   130: swap
    //   131: aastore
    //   132: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   135: checkcast wtf/opal/jt
    //   138: astore #23
    //   140: astore #22
    //   142: getstatic wtf/opal/bq.h : Lwtf/opal/pa;
    //   145: aload_0
    //   146: getfield U : F
    //   149: aload_0
    //   150: getfield t : F
    //   153: aload_0
    //   154: getfield e : F
    //   157: aload_0
    //   158: getfield m : F
    //   161: sipush #4533
    //   164: ldc2_w 3785826331924349415
    //   167: lload #6
    //   169: lxor
    //   170: <illegal opcode> x : (IJ)I
    //   175: lload #14
    //   177: bipush #6
    //   179: anewarray java/lang/Object
    //   182: dup_x2
    //   183: dup_x2
    //   184: pop
    //   185: invokestatic valueOf : (J)Ljava/lang/Long;
    //   188: iconst_5
    //   189: swap
    //   190: aastore
    //   191: dup_x1
    //   192: swap
    //   193: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   196: iconst_4
    //   197: swap
    //   198: aastore
    //   199: dup_x1
    //   200: swap
    //   201: invokestatic valueOf : (F)Ljava/lang/Float;
    //   204: iconst_3
    //   205: swap
    //   206: aastore
    //   207: dup_x1
    //   208: swap
    //   209: invokestatic valueOf : (F)Ljava/lang/Float;
    //   212: iconst_2
    //   213: swap
    //   214: aastore
    //   215: dup_x1
    //   216: swap
    //   217: invokestatic valueOf : (F)Ljava/lang/Float;
    //   220: iconst_1
    //   221: swap
    //   222: aastore
    //   223: dup_x1
    //   224: swap
    //   225: invokestatic valueOf : (F)Ljava/lang/Float;
    //   228: iconst_0
    //   229: swap
    //   230: aastore
    //   231: invokevirtual k : ([Ljava/lang/Object;)V
    //   234: aload_0
    //   235: getfield m : F
    //   238: fconst_2
    //   239: fdiv
    //   240: getstatic wtf/opal/bq.Z : Lwtf/opal/bu;
    //   243: aload_0
    //   244: iconst_0
    //   245: anewarray java/lang/Object
    //   248: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   251: checkcast wtf/opal/ke
    //   254: iconst_0
    //   255: anewarray java/lang/Object
    //   258: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   261: lload #16
    //   263: dup2_x1
    //   264: pop2
    //   265: ldc 6.5
    //   267: iconst_3
    //   268: anewarray java/lang/Object
    //   271: dup_x1
    //   272: swap
    //   273: invokestatic valueOf : (F)Ljava/lang/Float;
    //   276: iconst_2
    //   277: swap
    //   278: aastore
    //   279: dup_x1
    //   280: swap
    //   281: iconst_1
    //   282: swap
    //   283: aastore
    //   284: dup_x2
    //   285: dup_x2
    //   286: pop
    //   287: invokestatic valueOf : (J)Ljava/lang/Long;
    //   290: iconst_0
    //   291: swap
    //   292: aastore
    //   293: invokevirtual A : ([Ljava/lang/Object;)F
    //   296: fconst_2
    //   297: fdiv
    //   298: fsub
    //   299: fstore #24
    //   301: getstatic wtf/opal/bq.Z : Lwtf/opal/bu;
    //   304: aload #5
    //   306: aload_0
    //   307: iconst_0
    //   308: anewarray java/lang/Object
    //   311: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   314: checkcast wtf/opal/ke
    //   317: iconst_0
    //   318: anewarray java/lang/Object
    //   321: invokevirtual r : ([Ljava/lang/Object;)Ljava/lang/String;
    //   324: aload_0
    //   325: getfield U : F
    //   328: ldc 3.0
    //   330: fadd
    //   331: lload #8
    //   333: dup2_x1
    //   334: pop2
    //   335: aload_0
    //   336: getfield t : F
    //   339: fload #24
    //   341: fadd
    //   342: ldc 6.5
    //   344: iconst_m1
    //   345: bipush #7
    //   347: anewarray java/lang/Object
    //   350: dup_x1
    //   351: swap
    //   352: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   355: bipush #6
    //   357: swap
    //   358: aastore
    //   359: dup_x1
    //   360: swap
    //   361: invokestatic valueOf : (F)Ljava/lang/Float;
    //   364: iconst_5
    //   365: swap
    //   366: aastore
    //   367: dup_x1
    //   368: swap
    //   369: invokestatic valueOf : (F)Ljava/lang/Float;
    //   372: iconst_4
    //   373: swap
    //   374: aastore
    //   375: dup_x1
    //   376: swap
    //   377: invokestatic valueOf : (F)Ljava/lang/Float;
    //   380: iconst_3
    //   381: swap
    //   382: aastore
    //   383: dup_x2
    //   384: dup_x2
    //   385: pop
    //   386: invokestatic valueOf : (J)Ljava/lang/Long;
    //   389: iconst_2
    //   390: swap
    //   391: aastore
    //   392: dup_x1
    //   393: swap
    //   394: iconst_1
    //   395: swap
    //   396: aastore
    //   397: dup_x1
    //   398: swap
    //   399: iconst_0
    //   400: swap
    //   401: aastore
    //   402: invokevirtual R : ([Ljava/lang/Object;)V
    //   405: aload_0
    //   406: getfield D : Lwtf/opal/dk;
    //   409: aload_0
    //   410: iconst_0
    //   411: anewarray java/lang/Object
    //   414: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   417: checkcast wtf/opal/ke
    //   420: invokevirtual z : ()Ljava/lang/Object;
    //   423: checkcast java/lang/Boolean
    //   426: invokevirtual booleanValue : ()Z
    //   429: ifeq -> 442
    //   432: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   435: goto -> 445
    //   438: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   441: athrow
    //   442: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   445: lload #12
    //   447: dup2_x1
    //   448: pop2
    //   449: iconst_2
    //   450: anewarray java/lang/Object
    //   453: dup_x1
    //   454: swap
    //   455: iconst_1
    //   456: swap
    //   457: aastore
    //   458: dup_x2
    //   459: dup_x2
    //   460: pop
    //   461: invokestatic valueOf : (J)Ljava/lang/Long;
    //   464: iconst_0
    //   465: swap
    //   466: aastore
    //   467: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   470: pop
    //   471: ldc 8.0
    //   473: fstore #25
    //   475: ldc 18.0
    //   477: fstore #26
    //   479: aload_0
    //   480: getfield m : F
    //   483: fconst_2
    //   484: fdiv
    //   485: fload #25
    //   487: fconst_2
    //   488: fdiv
    //   489: fsub
    //   490: fstore #27
    //   492: aload_0
    //   493: iconst_0
    //   494: anewarray java/lang/Object
    //   497: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   500: checkcast wtf/opal/ke
    //   503: invokevirtual z : ()Ljava/lang/Object;
    //   506: checkcast java/lang/Boolean
    //   509: invokevirtual booleanValue : ()Z
    //   512: aload #22
    //   514: ifnull -> 556
    //   517: ifne -> 559
    //   520: goto -> 527
    //   523: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   526: athrow
    //   527: aload_0
    //   528: getfield D : Lwtf/opal/dk;
    //   531: lload #20
    //   533: iconst_1
    //   534: anewarray java/lang/Object
    //   537: dup_x2
    //   538: dup_x2
    //   539: pop
    //   540: invokestatic valueOf : (J)Ljava/lang/Long;
    //   543: iconst_0
    //   544: swap
    //   545: aastore
    //   546: invokevirtual H : ([Ljava/lang/Object;)Z
    //   549: goto -> 556
    //   552: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   555: athrow
    //   556: ifne -> 746
    //   559: new java/awt/Color
    //   562: dup
    //   563: aload #23
    //   565: iconst_0
    //   566: anewarray java/lang/Object
    //   569: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   572: invokevirtual z : ()Ljava/lang/Object;
    //   575: checkcast java/lang/Integer
    //   578: invokevirtual intValue : ()I
    //   581: invokespecial <init> : (I)V
    //   584: astore #28
    //   586: getstatic wtf/opal/bq.h : Lwtf/opal/pa;
    //   589: aload_0
    //   590: getfield U : F
    //   593: aload_0
    //   594: getfield e : F
    //   597: fadd
    //   598: ldc 23.0
    //   600: fsub
    //   601: aload_0
    //   602: getfield t : F
    //   605: fload #27
    //   607: fadd
    //   608: fload #26
    //   610: fload #25
    //   612: fload #25
    //   614: fconst_2
    //   615: fdiv
    //   616: aload #28
    //   618: aload_0
    //   619: getfield D : Lwtf/opal/dk;
    //   622: lload #18
    //   624: iconst_1
    //   625: anewarray java/lang/Object
    //   628: dup_x2
    //   629: dup_x2
    //   630: pop
    //   631: invokestatic valueOf : (J)Ljava/lang/Long;
    //   634: iconst_0
    //   635: swap
    //   636: aastore
    //   637: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   640: invokevirtual floatValue : ()F
    //   643: iconst_2
    //   644: anewarray java/lang/Object
    //   647: dup_x1
    //   648: swap
    //   649: invokestatic valueOf : (F)Ljava/lang/Float;
    //   652: iconst_1
    //   653: swap
    //   654: aastore
    //   655: dup_x1
    //   656: swap
    //   657: iconst_0
    //   658: swap
    //   659: aastore
    //   660: invokestatic Y : ([Ljava/lang/Object;)Ljava/awt/Color;
    //   663: invokevirtual getRGB : ()I
    //   666: lload #10
    //   668: bipush #7
    //   670: anewarray java/lang/Object
    //   673: dup_x2
    //   674: dup_x2
    //   675: pop
    //   676: invokestatic valueOf : (J)Ljava/lang/Long;
    //   679: bipush #6
    //   681: swap
    //   682: aastore
    //   683: dup_x1
    //   684: swap
    //   685: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   688: iconst_5
    //   689: swap
    //   690: aastore
    //   691: dup_x1
    //   692: swap
    //   693: invokestatic valueOf : (F)Ljava/lang/Float;
    //   696: iconst_4
    //   697: swap
    //   698: aastore
    //   699: dup_x1
    //   700: swap
    //   701: invokestatic valueOf : (F)Ljava/lang/Float;
    //   704: iconst_3
    //   705: swap
    //   706: aastore
    //   707: dup_x1
    //   708: swap
    //   709: invokestatic valueOf : (F)Ljava/lang/Float;
    //   712: iconst_2
    //   713: swap
    //   714: aastore
    //   715: dup_x1
    //   716: swap
    //   717: invokestatic valueOf : (F)Ljava/lang/Float;
    //   720: iconst_1
    //   721: swap
    //   722: aastore
    //   723: dup_x1
    //   724: swap
    //   725: invokestatic valueOf : (F)Ljava/lang/Float;
    //   728: iconst_0
    //   729: swap
    //   730: aastore
    //   731: invokevirtual M : ([Ljava/lang/Object;)V
    //   734: lload #6
    //   736: lconst_0
    //   737: lcmp
    //   738: ifle -> 858
    //   741: aload #22
    //   743: ifnonnull -> 865
    //   746: getstatic wtf/opal/bq.h : Lwtf/opal/pa;
    //   749: aload_0
    //   750: getfield U : F
    //   753: aload_0
    //   754: getfield e : F
    //   757: fadd
    //   758: ldc 23.0
    //   760: fsub
    //   761: aload_0
    //   762: getfield t : F
    //   765: fload #27
    //   767: fadd
    //   768: fload #26
    //   770: fload #25
    //   772: fload #25
    //   774: fconst_2
    //   775: fdiv
    //   776: sipush #24662
    //   779: ldc2_w 1038764518500216837
    //   782: lload #6
    //   784: lxor
    //   785: <illegal opcode> x : (IJ)I
    //   790: lload #10
    //   792: bipush #7
    //   794: anewarray java/lang/Object
    //   797: dup_x2
    //   798: dup_x2
    //   799: pop
    //   800: invokestatic valueOf : (J)Ljava/lang/Long;
    //   803: bipush #6
    //   805: swap
    //   806: aastore
    //   807: dup_x1
    //   808: swap
    //   809: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   812: iconst_5
    //   813: swap
    //   814: aastore
    //   815: dup_x1
    //   816: swap
    //   817: invokestatic valueOf : (F)Ljava/lang/Float;
    //   820: iconst_4
    //   821: swap
    //   822: aastore
    //   823: dup_x1
    //   824: swap
    //   825: invokestatic valueOf : (F)Ljava/lang/Float;
    //   828: iconst_3
    //   829: swap
    //   830: aastore
    //   831: dup_x1
    //   832: swap
    //   833: invokestatic valueOf : (F)Ljava/lang/Float;
    //   836: iconst_2
    //   837: swap
    //   838: aastore
    //   839: dup_x1
    //   840: swap
    //   841: invokestatic valueOf : (F)Ljava/lang/Float;
    //   844: iconst_1
    //   845: swap
    //   846: aastore
    //   847: dup_x1
    //   848: swap
    //   849: invokestatic valueOf : (F)Ljava/lang/Float;
    //   852: iconst_0
    //   853: swap
    //   854: aastore
    //   855: invokevirtual M : ([Ljava/lang/Object;)V
    //   858: goto -> 865
    //   861: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   864: athrow
    //   865: ldc 6.0
    //   867: fstore #28
    //   869: aload_0
    //   870: getfield m : F
    //   873: fconst_2
    //   874: fdiv
    //   875: fload #28
    //   877: fconst_2
    //   878: fdiv
    //   879: fsub
    //   880: fstore #29
    //   882: getstatic wtf/opal/bq.h : Lwtf/opal/pa;
    //   885: aload_0
    //   886: getfield U : F
    //   889: aload_0
    //   890: getfield e : F
    //   893: fadd
    //   894: ldc 22.0
    //   896: fsub
    //   897: aload_0
    //   898: getfield D : Lwtf/opal/dk;
    //   901: lload #18
    //   903: iconst_1
    //   904: anewarray java/lang/Object
    //   907: dup_x2
    //   908: dup_x2
    //   909: pop
    //   910: invokestatic valueOf : (J)Ljava/lang/Long;
    //   913: iconst_0
    //   914: swap
    //   915: aastore
    //   916: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   919: invokevirtual floatValue : ()F
    //   922: fload #26
    //   924: fload #28
    //   926: fconst_2
    //   927: fadd
    //   928: fsub
    //   929: fmul
    //   930: fadd
    //   931: aload_0
    //   932: getfield t : F
    //   935: fload #29
    //   937: fadd
    //   938: fload #28
    //   940: fload #28
    //   942: fload #28
    //   944: fconst_2
    //   945: fdiv
    //   946: iconst_m1
    //   947: lload #10
    //   949: bipush #7
    //   951: anewarray java/lang/Object
    //   954: dup_x2
    //   955: dup_x2
    //   956: pop
    //   957: invokestatic valueOf : (J)Ljava/lang/Long;
    //   960: bipush #6
    //   962: swap
    //   963: aastore
    //   964: dup_x1
    //   965: swap
    //   966: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   969: iconst_5
    //   970: swap
    //   971: aastore
    //   972: dup_x1
    //   973: swap
    //   974: invokestatic valueOf : (F)Ljava/lang/Float;
    //   977: iconst_4
    //   978: swap
    //   979: aastore
    //   980: dup_x1
    //   981: swap
    //   982: invokestatic valueOf : (F)Ljava/lang/Float;
    //   985: iconst_3
    //   986: swap
    //   987: aastore
    //   988: dup_x1
    //   989: swap
    //   990: invokestatic valueOf : (F)Ljava/lang/Float;
    //   993: iconst_2
    //   994: swap
    //   995: aastore
    //   996: dup_x1
    //   997: swap
    //   998: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1001: iconst_1
    //   1002: swap
    //   1003: aastore
    //   1004: dup_x1
    //   1005: swap
    //   1006: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1009: iconst_0
    //   1010: swap
    //   1011: aastore
    //   1012: invokevirtual M : ([Ljava/lang/Object;)V
    //   1015: return
    // Exception table:
    //   from	to	target	type
    //   301	438	438	wtf/opal/x5
    //   492	520	523	wtf/opal/x5
    //   517	549	552	wtf/opal/x5
    //   586	858	861	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    double d1 = ((Double)paramArrayOfObject[0]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[1]).doubleValue();
    long l1 = ((Long)paramArrayOfObject[2]).longValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
    long l2 = l1 ^ 0x52EBE2FCF81CL;
    long l3 = l1 ^ 0x7EE33A4079CL;
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
      if (!u1.Z(new Object[] { Long.valueOf(l2) })) {
        new Object[1];
        ((ke)h(new Object[0])).x(new Object[] { Long.valueOf(l3) });
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  public void c(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    double d1 = ((Double)paramArrayOfObject[1]).doubleValue();
    double d2 = ((Double)paramArrayOfObject[2]).doubleValue();
    int i = ((Integer)paramArrayOfObject[3]).intValue();
  }
  
  public void u() {}
  
  static {
    // Byte code:
    //   0: ldc2_w -8874376730029293467
    //   3: ldc2_w -7505239147910769475
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 182524294998575
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/bq.b : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/bq.f : Ljava/util/Map;
    //   38: getstatic wtf/opal/bq.b : J
    //   41: ldc2_w 24999882130493
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
    //   135: ldc_w 'u0 ¯%ÿÜÙ`C(oÐeÚ ·*'
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
    //   292: putstatic wtf/opal/bq.c : [J
    //   295: iconst_3
    //   296: anewarray java/lang/Integer
    //   299: putstatic wtf/opal/bq.d : [Ljava/lang/Integer;
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
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6AC1;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])f.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/bq", exception);
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
    //   65: ldc_w 'wtf/opal/bq'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bq.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */