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
import net.minecraft.class_1309;
import net.minecraft.class_1799;

public final class px extends u_<jb> {
  private final pa A;
  
  private final dc Q;
  
  private final int M;
  
  private final int c;
  
  private final int J;
  
  private final int C;
  
  private final gm<uj> w;
  
  private static final long a = on.a(-7762943011069010382L, 1328727169822989135L, MethodHandles.lookup().lookupClass()).a(52543264469605L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map e = new HashMap<>(13);
  
  private static final long[] f;
  
  private static final Integer[] g;
  
  private static final Map h;
  
  public px(int paramInt1, jb paramjb, int paramInt2, byte paramByte) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #32
    //   4: lshl
    //   5: iload_3
    //   6: i2l
    //   7: bipush #40
    //   9: lshl
    //   10: bipush #32
    //   12: lushr
    //   13: lor
    //   14: iload #4
    //   16: i2l
    //   17: bipush #56
    //   19: lshl
    //   20: bipush #56
    //   22: lushr
    //   23: lor
    //   24: getstatic wtf/opal/px.a : J
    //   27: lxor
    //   28: lstore #5
    //   30: invokestatic s : ()Ljava/lang/String;
    //   33: aload_0
    //   34: aload_2
    //   35: invokespecial <init> : (Lwtf/opal/d;)V
    //   38: aload_0
    //   39: iconst_0
    //   40: anewarray java/lang/Object
    //   43: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   46: iconst_0
    //   47: anewarray java/lang/Object
    //   50: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   53: putfield A : Lwtf/opal/pa;
    //   56: astore #7
    //   58: aload_0
    //   59: iconst_0
    //   60: anewarray java/lang/Object
    //   63: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   66: iconst_0
    //   67: anewarray java/lang/Object
    //   70: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/dc;
    //   73: putfield Q : Lwtf/opal/dc;
    //   76: aload_0
    //   77: new java/awt/Color
    //   80: dup
    //   81: sipush #3340
    //   84: ldc2_w 2719095914545778825
    //   87: lload #5
    //   89: lxor
    //   90: <illegal opcode> u : (IJ)I
    //   95: sipush #3340
    //   98: ldc2_w 2719095914545778825
    //   101: lload #5
    //   103: lxor
    //   104: <illegal opcode> u : (IJ)I
    //   109: sipush #3340
    //   112: ldc2_w 2719095914545778825
    //   115: lload #5
    //   117: lxor
    //   118: <illegal opcode> u : (IJ)I
    //   123: invokespecial <init> : (III)V
    //   126: invokevirtual getRGB : ()I
    //   129: putfield M : I
    //   132: aload_0
    //   133: new java/awt/Color
    //   136: dup
    //   137: sipush #32556
    //   140: ldc2_w 3289556373428724386
    //   143: lload #5
    //   145: lxor
    //   146: <illegal opcode> u : (IJ)I
    //   151: sipush #3636
    //   154: ldc2_w 4114613373749793717
    //   157: lload #5
    //   159: lxor
    //   160: <illegal opcode> u : (IJ)I
    //   165: sipush #3636
    //   168: ldc2_w 4114613373749793717
    //   171: lload #5
    //   173: lxor
    //   174: <illegal opcode> u : (IJ)I
    //   179: invokespecial <init> : (III)V
    //   182: invokevirtual getRGB : ()I
    //   185: putfield c : I
    //   188: aload_0
    //   189: new java/awt/Color
    //   192: dup
    //   193: sipush #22599
    //   196: ldc2_w 3725168262072878539
    //   199: lload #5
    //   201: lxor
    //   202: <illegal opcode> u : (IJ)I
    //   207: sipush #16039
    //   210: ldc2_w 7682526629123236648
    //   213: lload #5
    //   215: lxor
    //   216: <illegal opcode> u : (IJ)I
    //   221: sipush #16039
    //   224: ldc2_w 7682526629123236648
    //   227: lload #5
    //   229: lxor
    //   230: <illegal opcode> u : (IJ)I
    //   235: invokespecial <init> : (III)V
    //   238: invokevirtual getRGB : ()I
    //   241: putfield J : I
    //   244: aload_0
    //   245: new java/awt/Color
    //   248: dup
    //   249: sipush #17486
    //   252: ldc2_w 8492667134501935565
    //   255: lload #5
    //   257: lxor
    //   258: <illegal opcode> u : (IJ)I
    //   263: sipush #14423
    //   266: ldc2_w 3018251708699157978
    //   269: lload #5
    //   271: lxor
    //   272: <illegal opcode> u : (IJ)I
    //   277: sipush #14423
    //   280: ldc2_w 3018251708699157978
    //   283: lload #5
    //   285: lxor
    //   286: <illegal opcode> u : (IJ)I
    //   291: invokespecial <init> : (III)V
    //   294: invokevirtual getRGB : ()I
    //   297: putfield C : I
    //   300: aload_0
    //   301: aload_0
    //   302: <illegal opcode> H : (Lwtf/opal/px;)Lwtf/opal/gm;
    //   307: putfield w : Lwtf/opal/gm;
    //   310: aload #7
    //   312: ifnonnull -> 329
    //   315: iconst_1
    //   316: anewarray wtf/opal/d
    //   319: invokestatic p : ([Lwtf/opal/d;)V
    //   322: goto -> 329
    //   325: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   328: athrow
    //   329: return
    // Exception table:
    //   from	to	target	type
    //   58	322	325	wtf/opal/x5
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xg.EXHIBITION;
  }
  
  private void lambda$new$3(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/px.a : J
    //   3: ldc2_w 53017259060617
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 84305905772844
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 34079086103117
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 77105999877596
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 89648072383682
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 44489393598478
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 59355714635497
    //   48: lxor
    //   49: lstore #14
    //   51: dup2
    //   52: ldc2_w 26015925290423
    //   55: lxor
    //   56: lstore #16
    //   58: pop2
    //   59: invokestatic s : ()Ljava/lang/String;
    //   62: aload_0
    //   63: getfield G : Lwtf/opal/d;
    //   66: checkcast wtf/opal/jb
    //   69: lload #4
    //   71: iconst_1
    //   72: anewarray java/lang/Object
    //   75: dup_x2
    //   76: dup_x2
    //   77: pop
    //   78: invokestatic valueOf : (J)Ljava/lang/Long;
    //   81: iconst_0
    //   82: swap
    //   83: aastore
    //   84: invokevirtual E : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   87: astore #19
    //   89: astore #18
    //   91: aload #19
    //   93: ifnonnull -> 101
    //   96: return
    //   97: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: aload_0
    //   102: getfield Q : Lwtf/opal/dc;
    //   105: sipush #27023
    //   108: ldc2_w 3802445597107983660
    //   111: lload_2
    //   112: lxor
    //   113: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   118: lload #6
    //   120: iconst_2
    //   121: anewarray java/lang/Object
    //   124: dup_x2
    //   125: dup_x2
    //   126: pop
    //   127: invokestatic valueOf : (J)Ljava/lang/Long;
    //   130: iconst_1
    //   131: swap
    //   132: aastore
    //   133: dup_x1
    //   134: swap
    //   135: iconst_0
    //   136: swap
    //   137: aastore
    //   138: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   141: astore #20
    //   143: aload_0
    //   144: getfield Q : Lwtf/opal/dc;
    //   147: sipush #21661
    //   150: ldc2_w 2465283376866175036
    //   153: lload_2
    //   154: lxor
    //   155: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   160: lload #6
    //   162: iconst_2
    //   163: anewarray java/lang/Object
    //   166: dup_x2
    //   167: dup_x2
    //   168: pop
    //   169: invokestatic valueOf : (J)Ljava/lang/Long;
    //   172: iconst_1
    //   173: swap
    //   174: aastore
    //   175: dup_x1
    //   176: swap
    //   177: iconst_0
    //   178: swap
    //   179: aastore
    //   180: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   183: astore #21
    //   185: aload_0
    //   186: getfield G : Lwtf/opal/d;
    //   189: checkcast wtf/opal/jb
    //   192: getfield z : Lwtf/opal/k6;
    //   195: ldc 135.0
    //   197: aload #21
    //   199: aload #19
    //   201: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   204: invokeinterface getString : ()Ljava/lang/String;
    //   209: lload #14
    //   211: dup2_x1
    //   212: pop2
    //   213: ldc 8.0
    //   215: iconst_3
    //   216: anewarray java/lang/Object
    //   219: dup_x1
    //   220: swap
    //   221: invokestatic valueOf : (F)Ljava/lang/Float;
    //   224: iconst_2
    //   225: swap
    //   226: aastore
    //   227: dup_x1
    //   228: swap
    //   229: iconst_1
    //   230: swap
    //   231: aastore
    //   232: dup_x2
    //   233: dup_x2
    //   234: pop
    //   235: invokestatic valueOf : (J)Ljava/lang/Long;
    //   238: iconst_0
    //   239: swap
    //   240: aastore
    //   241: invokevirtual q : ([Ljava/lang/Object;)F
    //   244: ldc 60.0
    //   246: fadd
    //   247: invokestatic max : (FF)F
    //   250: ldc 46.0
    //   252: iconst_2
    //   253: anewarray java/lang/Object
    //   256: dup_x1
    //   257: swap
    //   258: invokestatic valueOf : (F)Ljava/lang/Float;
    //   261: iconst_1
    //   262: swap
    //   263: aastore
    //   264: dup_x1
    //   265: swap
    //   266: invokestatic valueOf : (F)Ljava/lang/Float;
    //   269: iconst_0
    //   270: swap
    //   271: aastore
    //   272: invokevirtual Y : ([Ljava/lang/Object;)V
    //   275: aload_0
    //   276: getfield G : Lwtf/opal/d;
    //   279: checkcast wtf/opal/jb
    //   282: getfield z : Lwtf/opal/k6;
    //   285: fconst_0
    //   286: iconst_1
    //   287: anewarray java/lang/Object
    //   290: dup_x1
    //   291: swap
    //   292: invokestatic valueOf : (F)Ljava/lang/Float;
    //   295: iconst_0
    //   296: swap
    //   297: aastore
    //   298: invokevirtual U : ([Ljava/lang/Object;)V
    //   301: aload_0
    //   302: getfield G : Lwtf/opal/d;
    //   305: checkcast wtf/opal/jb
    //   308: getfield z : Lwtf/opal/k6;
    //   311: lload #16
    //   313: iconst_1
    //   314: anewarray java/lang/Object
    //   317: dup_x2
    //   318: dup_x2
    //   319: pop
    //   320: invokestatic valueOf : (J)Ljava/lang/Long;
    //   323: iconst_0
    //   324: swap
    //   325: aastore
    //   326: invokevirtual t : ([Ljava/lang/Object;)F
    //   329: fstore #22
    //   331: aload_0
    //   332: getfield G : Lwtf/opal/d;
    //   335: checkcast wtf/opal/jb
    //   338: getfield z : Lwtf/opal/k6;
    //   341: iconst_0
    //   342: anewarray java/lang/Object
    //   345: invokevirtual x : ([Ljava/lang/Object;)F
    //   348: fstore #23
    //   350: aload_0
    //   351: getfield G : Lwtf/opal/d;
    //   354: checkcast wtf/opal/jb
    //   357: getfield z : Lwtf/opal/k6;
    //   360: iconst_0
    //   361: anewarray java/lang/Object
    //   364: invokevirtual K : ([Ljava/lang/Object;)F
    //   367: fstore #24
    //   369: aload_0
    //   370: getfield G : Lwtf/opal/d;
    //   373: checkcast wtf/opal/jb
    //   376: getfield z : Lwtf/opal/k6;
    //   379: iconst_0
    //   380: anewarray java/lang/Object
    //   383: invokevirtual p : ([Ljava/lang/Object;)F
    //   386: fstore #25
    //   388: aload_0
    //   389: getfield G : Lwtf/opal/d;
    //   392: checkcast wtf/opal/jb
    //   395: iconst_0
    //   396: anewarray java/lang/Object
    //   399: invokevirtual k : ([Ljava/lang/Object;)F
    //   402: fstore #26
    //   404: aload #19
    //   406: invokevirtual method_6063 : ()F
    //   409: aload #19
    //   411: invokevirtual method_52541 : ()F
    //   414: fadd
    //   415: fstore #27
    //   417: aload_0
    //   418: getfield G : Lwtf/opal/d;
    //   421: checkcast wtf/opal/jb
    //   424: getfield Q : Lwtf/opal/d2;
    //   427: lload #10
    //   429: iconst_1
    //   430: anewarray java/lang/Object
    //   433: dup_x2
    //   434: dup_x2
    //   435: pop
    //   436: invokestatic valueOf : (J)Ljava/lang/Long;
    //   439: iconst_0
    //   440: swap
    //   441: aastore
    //   442: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   445: invokevirtual floatValue : ()F
    //   448: fstore #28
    //   450: fload #26
    //   452: ldc_w 10.0
    //   455: fcmpl
    //   456: aload #18
    //   458: ifnull -> 666
    //   461: ifle -> 617
    //   464: goto -> 471
    //   467: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   470: athrow
    //   471: new java/awt/Color
    //   474: dup
    //   475: sipush #15961
    //   478: ldc2_w 2535729704058537628
    //   481: lload_2
    //   482: lxor
    //   483: <illegal opcode> u : (IJ)I
    //   488: sipush #13919
    //   491: ldc2_w 8447756173893291679
    //   494: lload_2
    //   495: lxor
    //   496: <illegal opcode> u : (IJ)I
    //   501: sipush #19299
    //   504: ldc2_w 6091449136815125410
    //   507: lload_2
    //   508: lxor
    //   509: <illegal opcode> u : (IJ)I
    //   514: invokespecial <init> : (III)V
    //   517: invokevirtual getRGB : ()I
    //   520: new java/awt/Color
    //   523: dup
    //   524: sipush #3340
    //   527: ldc2_w 2719125111660672462
    //   530: lload_2
    //   531: lxor
    //   532: <illegal opcode> u : (IJ)I
    //   537: sipush #13919
    //   540: ldc2_w 8447756173893291679
    //   543: lload_2
    //   544: lxor
    //   545: <illegal opcode> u : (IJ)I
    //   550: sipush #3340
    //   553: ldc2_w 2719125111660672462
    //   556: lload_2
    //   557: lxor
    //   558: <illegal opcode> u : (IJ)I
    //   563: invokespecial <init> : (III)V
    //   566: invokevirtual getRGB : ()I
    //   569: fload #26
    //   571: ldc_w 0.5
    //   574: fsub
    //   575: ldc_w 0.5
    //   578: fdiv
    //   579: iconst_3
    //   580: anewarray java/lang/Object
    //   583: dup_x1
    //   584: swap
    //   585: invokestatic valueOf : (F)Ljava/lang/Float;
    //   588: iconst_2
    //   589: swap
    //   590: aastore
    //   591: dup_x1
    //   592: swap
    //   593: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   596: iconst_1
    //   597: swap
    //   598: aastore
    //   599: dup_x1
    //   600: swap
    //   601: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   604: iconst_0
    //   605: swap
    //   606: aastore
    //   607: invokestatic P : ([Ljava/lang/Object;)I
    //   610: goto -> 750
    //   613: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   616: athrow
    //   617: new java/awt/Color
    //   620: dup
    //   621: sipush #13919
    //   624: ldc2_w 8447756173893291679
    //   627: lload_2
    //   628: lxor
    //   629: <illegal opcode> u : (IJ)I
    //   634: sipush #3340
    //   637: ldc2_w 2719125111660672462
    //   640: lload_2
    //   641: lxor
    //   642: <illegal opcode> u : (IJ)I
    //   647: sipush #3340
    //   650: ldc2_w 2719125111660672462
    //   653: lload_2
    //   654: lxor
    //   655: <illegal opcode> u : (IJ)I
    //   660: invokespecial <init> : (III)V
    //   663: invokevirtual getRGB : ()I
    //   666: new java/awt/Color
    //   669: dup
    //   670: sipush #13919
    //   673: ldc2_w 8447756173893291679
    //   676: lload_2
    //   677: lxor
    //   678: <illegal opcode> u : (IJ)I
    //   683: sipush #13919
    //   686: ldc2_w 8447756173893291679
    //   689: lload_2
    //   690: lxor
    //   691: <illegal opcode> u : (IJ)I
    //   696: sipush #3340
    //   699: ldc2_w 2719125111660672462
    //   702: lload_2
    //   703: lxor
    //   704: <illegal opcode> u : (IJ)I
    //   709: invokespecial <init> : (III)V
    //   712: invokevirtual getRGB : ()I
    //   715: fload #26
    //   717: fconst_2
    //   718: fmul
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
    //   733: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   736: iconst_1
    //   737: swap
    //   738: aastore
    //   739: dup_x1
    //   740: swap
    //   741: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   744: iconst_0
    //   745: swap
    //   746: aastore
    //   747: invokestatic P : ([Ljava/lang/Object;)I
    //   750: istore #29
    //   752: aload_0
    //   753: getfield A : Lwtf/opal/pa;
    //   756: aload_0
    //   757: fload #28
    //   759: fload #22
    //   761: fload #23
    //   763: fload #24
    //   765: fload #25
    //   767: aload #21
    //   769: aload #19
    //   771: iload #29
    //   773: fload #26
    //   775: fload #27
    //   777: aload #20
    //   779: <illegal opcode> run : (Lwtf/opal/px;FFFFFLwtf/opal/u2;Lnet/minecraft/class_1309;IFFLwtf/opal/u2;)Ljava/lang/Runnable;
    //   784: lload #8
    //   786: iconst_2
    //   787: anewarray java/lang/Object
    //   790: dup_x2
    //   791: dup_x2
    //   792: pop
    //   793: invokestatic valueOf : (J)Ljava/lang/Long;
    //   796: iconst_1
    //   797: swap
    //   798: aastore
    //   799: dup_x1
    //   800: swap
    //   801: iconst_0
    //   802: swap
    //   803: aastore
    //   804: invokevirtual F : ([Ljava/lang/Object;)V
    //   807: new java/util/ArrayList
    //   810: dup
    //   811: invokespecial <init> : ()V
    //   814: astore #30
    //   816: aload #19
    //   818: invokevirtual method_5661 : ()Ljava/lang/Iterable;
    //   821: aload #30
    //   823: <illegal opcode> accept : (Ljava/util/List;)Ljava/util/function/Consumer;
    //   828: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   833: aload #30
    //   835: invokestatic reverse : (Ljava/util/List;)V
    //   838: aload #19
    //   840: invokevirtual method_6047 : ()Lnet/minecraft/class_1799;
    //   843: astore #31
    //   845: aload #31
    //   847: invokevirtual method_7960 : ()Z
    //   850: aload #18
    //   852: ifnull -> 881
    //   855: ifne -> 882
    //   858: goto -> 865
    //   861: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   864: athrow
    //   865: aload #30
    //   867: aload #31
    //   869: invokeinterface add : (Ljava/lang/Object;)Z
    //   874: goto -> 881
    //   877: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   880: athrow
    //   881: pop
    //   882: aload_1
    //   883: iconst_0
    //   884: anewarray java/lang/Object
    //   887: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   890: astore #32
    //   892: aload #32
    //   894: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   897: invokevirtual method_22903 : ()V
    //   900: aload #32
    //   902: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   905: fload #22
    //   907: ldc_w 45.0
    //   910: fadd
    //   911: ldc_w 16.0
    //   914: fadd
    //   915: fload #23
    //   917: ldc_w 29.0
    //   920: fadd
    //   921: fconst_0
    //   922: invokevirtual method_46416 : (FFF)V
    //   925: aload #32
    //   927: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   930: fload #28
    //   932: fload #28
    //   934: fconst_1
    //   935: invokevirtual method_22905 : (FFF)V
    //   938: iconst_0
    //   939: istore #33
    //   941: iload #33
    //   943: aload #30
    //   945: invokeinterface size : ()I
    //   950: if_icmpge -> 1025
    //   953: aload #30
    //   955: iload #33
    //   957: invokeinterface get : (I)Ljava/lang/Object;
    //   962: checkcast net/minecraft/class_1799
    //   965: astore #34
    //   967: aload #32
    //   969: aload #34
    //   971: iload #33
    //   973: sipush #4098
    //   976: ldc2_w 2030783512322173121
    //   979: lload_2
    //   980: lxor
    //   981: <illegal opcode> u : (IJ)I
    //   986: imul
    //   987: sipush #19325
    //   990: ldc2_w 2487578869386294193
    //   993: lload_2
    //   994: lxor
    //   995: <illegal opcode> u : (IJ)I
    //   1000: isub
    //   1001: iconst_0
    //   1002: invokevirtual method_51445 : (Lnet/minecraft/class_1799;II)V
    //   1005: iinc #33, 1
    //   1008: aload #18
    //   1010: ifnull -> 1178
    //   1013: aload #18
    //   1015: ifnonnull -> 941
    //   1018: goto -> 1025
    //   1021: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1024: athrow
    //   1025: aload #32
    //   1027: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   1030: invokevirtual method_22909 : ()V
    //   1033: aload #32
    //   1035: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   1038: invokevirtual method_22903 : ()V
    //   1041: aload #32
    //   1043: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   1046: fload #28
    //   1048: fload #28
    //   1050: fconst_1
    //   1051: invokevirtual method_22905 : (FFF)V
    //   1054: iconst_0
    //   1055: putstatic wtf/opal/bc.L : Z
    //   1058: aload #32
    //   1060: fload #22
    //   1062: ldc_w 3.0
    //   1065: fadd
    //   1066: fload #28
    //   1068: fdiv
    //   1069: f2i
    //   1070: fload #23
    //   1072: ldc_w 3.0
    //   1075: fadd
    //   1076: fload #28
    //   1078: fdiv
    //   1079: f2i
    //   1080: fload #22
    //   1082: ldc_w 43.0
    //   1085: fadd
    //   1086: fload #28
    //   1088: fdiv
    //   1089: f2i
    //   1090: fload #23
    //   1092: ldc_w 44.0
    //   1095: fadd
    //   1096: fload #28
    //   1098: fdiv
    //   1099: f2i
    //   1100: ldc_w 18.0
    //   1103: fload #28
    //   1105: fdiv
    //   1106: f2i
    //   1107: fconst_0
    //   1108: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1111: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   1114: invokevirtual method_4480 : ()I
    //   1117: i2f
    //   1118: ldc_w 0.21
    //   1121: fmul
    //   1122: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1125: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   1128: invokevirtual method_4507 : ()I
    //   1131: i2f
    //   1132: ldc_w 0.42
    //   1135: fmul
    //   1136: aload #19
    //   1138: invokestatic method_2486 : (Lnet/minecraft/class_332;IIIIIFFFLnet/minecraft/class_1309;)V
    //   1141: iconst_1
    //   1142: putstatic wtf/opal/bc.L : Z
    //   1145: aload #32
    //   1147: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   1150: invokevirtual method_22909 : ()V
    //   1153: aload_0
    //   1154: getfield G : Lwtf/opal/d;
    //   1157: checkcast wtf/opal/jb
    //   1160: lload #12
    //   1162: iconst_1
    //   1163: anewarray java/lang/Object
    //   1166: dup_x2
    //   1167: dup_x2
    //   1168: pop
    //   1169: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1172: iconst_0
    //   1173: swap
    //   1174: aastore
    //   1175: invokevirtual x : ([Ljava/lang/Object;)V
    //   1178: return
    // Exception table:
    //   from	to	target	type
    //   91	97	97	wtf/opal/x5
    //   450	464	467	wtf/opal/x5
    //   461	613	613	wtf/opal/x5
    //   845	858	861	wtf/opal/x5
    //   855	874	877	wtf/opal/x5
    //   967	1018	1021	wtf/opal/x5
  }
  
  private static void lambda$new$2(List paramList, class_1799 paramclass_1799) {
    long l = a ^ 0x7D02C2FF90E3L;
    String str = pk.s();
    try {
      if (str != null)
        try {
          if (!paramclass_1799.method_7960()) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw b(null);
        }  
    } catch (x5 x5) {
      throw b(null);
    } 
    paramclass_1799.method_7960();
  }
  
  private void lambda$new$1(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, u2 paramu21, class_1309 paramclass_1309, int paramInt, float paramFloat6, float paramFloat7, u2 paramu22) {
    // Byte code:
    //   0: aload_0
    //   1: getfield A : Lwtf/opal/pa;
    //   4: fload_1
    //   5: fload_2
    //   6: fload_3
    //   7: fload #4
    //   9: fload #5
    //   11: aload_0
    //   12: fload_2
    //   13: fload_3
    //   14: fload #4
    //   16: fload #5
    //   18: aload #6
    //   20: aload #7
    //   22: iload #8
    //   24: fload #9
    //   26: fload #10
    //   28: aload #11
    //   30: <illegal opcode> run : (Lwtf/opal/px;FFFFLwtf/opal/u2;Lnet/minecraft/class_1309;IFFLwtf/opal/u2;)Ljava/lang/Runnable;
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
  
  private void lambda$new$0(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, u2 paramu21, class_1309 paramclass_1309, int paramInt, float paramFloat5, float paramFloat6, u2 paramu22) {
    // Byte code:
    //   0: getstatic wtf/opal/px.a : J
    //   3: ldc2_w 127476911302988
    //   6: lxor
    //   7: lstore #11
    //   9: lload #11
    //   11: dup2
    //   12: ldc2_w 36079539258336
    //   15: lxor
    //   16: lstore #13
    //   18: dup2
    //   19: ldc2_w 93853299751184
    //   22: lxor
    //   23: lstore #15
    //   25: dup2
    //   26: ldc2_w 78161922180984
    //   29: lxor
    //   30: lstore #17
    //   32: dup2
    //   33: ldc2_w 117933487673047
    //   36: lxor
    //   37: lstore #19
    //   39: pop2
    //   40: aload_0
    //   41: getfield A : Lwtf/opal/pa;
    //   44: fload_1
    //   45: ldc_w 3.5
    //   48: fsub
    //   49: fload_2
    //   50: ldc_w 3.5
    //   53: fsub
    //   54: fload_3
    //   55: ldc_w 7.0
    //   58: fadd
    //   59: fload #4
    //   61: ldc_w 7.0
    //   64: fadd
    //   65: aload_0
    //   66: getfield M : I
    //   69: lload #15
    //   71: bipush #6
    //   73: anewarray java/lang/Object
    //   76: dup_x2
    //   77: dup_x2
    //   78: pop
    //   79: invokestatic valueOf : (J)Ljava/lang/Long;
    //   82: iconst_5
    //   83: swap
    //   84: aastore
    //   85: dup_x1
    //   86: swap
    //   87: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   90: iconst_4
    //   91: swap
    //   92: aastore
    //   93: dup_x1
    //   94: swap
    //   95: invokestatic valueOf : (F)Ljava/lang/Float;
    //   98: iconst_3
    //   99: swap
    //   100: aastore
    //   101: dup_x1
    //   102: swap
    //   103: invokestatic valueOf : (F)Ljava/lang/Float;
    //   106: iconst_2
    //   107: swap
    //   108: aastore
    //   109: dup_x1
    //   110: swap
    //   111: invokestatic valueOf : (F)Ljava/lang/Float;
    //   114: iconst_1
    //   115: swap
    //   116: aastore
    //   117: dup_x1
    //   118: swap
    //   119: invokestatic valueOf : (F)Ljava/lang/Float;
    //   122: iconst_0
    //   123: swap
    //   124: aastore
    //   125: invokevirtual k : ([Ljava/lang/Object;)V
    //   128: aload_0
    //   129: getfield A : Lwtf/opal/pa;
    //   132: fload_1
    //   133: ldc_w 3.0
    //   136: fsub
    //   137: fload_2
    //   138: ldc_w 3.0
    //   141: fsub
    //   142: fload_3
    //   143: ldc_w 6.0
    //   146: fadd
    //   147: fload #4
    //   149: ldc_w 6.0
    //   152: fadd
    //   153: aload_0
    //   154: getfield C : I
    //   157: lload #15
    //   159: bipush #6
    //   161: anewarray java/lang/Object
    //   164: dup_x2
    //   165: dup_x2
    //   166: pop
    //   167: invokestatic valueOf : (J)Ljava/lang/Long;
    //   170: iconst_5
    //   171: swap
    //   172: aastore
    //   173: dup_x1
    //   174: swap
    //   175: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   178: iconst_4
    //   179: swap
    //   180: aastore
    //   181: dup_x1
    //   182: swap
    //   183: invokestatic valueOf : (F)Ljava/lang/Float;
    //   186: iconst_3
    //   187: swap
    //   188: aastore
    //   189: dup_x1
    //   190: swap
    //   191: invokestatic valueOf : (F)Ljava/lang/Float;
    //   194: iconst_2
    //   195: swap
    //   196: aastore
    //   197: dup_x1
    //   198: swap
    //   199: invokestatic valueOf : (F)Ljava/lang/Float;
    //   202: iconst_1
    //   203: swap
    //   204: aastore
    //   205: dup_x1
    //   206: swap
    //   207: invokestatic valueOf : (F)Ljava/lang/Float;
    //   210: iconst_0
    //   211: swap
    //   212: aastore
    //   213: invokevirtual k : ([Ljava/lang/Object;)V
    //   216: aload_0
    //   217: getfield A : Lwtf/opal/pa;
    //   220: fload_1
    //   221: fconst_1
    //   222: fsub
    //   223: fload_2
    //   224: fconst_1
    //   225: fsub
    //   226: fload_3
    //   227: fconst_2
    //   228: fadd
    //   229: fload #4
    //   231: fconst_2
    //   232: fadd
    //   233: aload_0
    //   234: getfield J : I
    //   237: lload #15
    //   239: bipush #6
    //   241: anewarray java/lang/Object
    //   244: dup_x2
    //   245: dup_x2
    //   246: pop
    //   247: invokestatic valueOf : (J)Ljava/lang/Long;
    //   250: iconst_5
    //   251: swap
    //   252: aastore
    //   253: dup_x1
    //   254: swap
    //   255: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   258: iconst_4
    //   259: swap
    //   260: aastore
    //   261: dup_x1
    //   262: swap
    //   263: invokestatic valueOf : (F)Ljava/lang/Float;
    //   266: iconst_3
    //   267: swap
    //   268: aastore
    //   269: dup_x1
    //   270: swap
    //   271: invokestatic valueOf : (F)Ljava/lang/Float;
    //   274: iconst_2
    //   275: swap
    //   276: aastore
    //   277: dup_x1
    //   278: swap
    //   279: invokestatic valueOf : (F)Ljava/lang/Float;
    //   282: iconst_1
    //   283: swap
    //   284: aastore
    //   285: dup_x1
    //   286: swap
    //   287: invokestatic valueOf : (F)Ljava/lang/Float;
    //   290: iconst_0
    //   291: swap
    //   292: aastore
    //   293: invokevirtual k : ([Ljava/lang/Object;)V
    //   296: aload_0
    //   297: getfield A : Lwtf/opal/pa;
    //   300: fload_1
    //   301: fload_2
    //   302: fload_3
    //   303: fload #4
    //   305: aload_0
    //   306: getfield c : I
    //   309: lload #15
    //   311: bipush #6
    //   313: anewarray java/lang/Object
    //   316: dup_x2
    //   317: dup_x2
    //   318: pop
    //   319: invokestatic valueOf : (J)Ljava/lang/Long;
    //   322: iconst_5
    //   323: swap
    //   324: aastore
    //   325: dup_x1
    //   326: swap
    //   327: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   330: iconst_4
    //   331: swap
    //   332: aastore
    //   333: dup_x1
    //   334: swap
    //   335: invokestatic valueOf : (F)Ljava/lang/Float;
    //   338: iconst_3
    //   339: swap
    //   340: aastore
    //   341: dup_x1
    //   342: swap
    //   343: invokestatic valueOf : (F)Ljava/lang/Float;
    //   346: iconst_2
    //   347: swap
    //   348: aastore
    //   349: dup_x1
    //   350: swap
    //   351: invokestatic valueOf : (F)Ljava/lang/Float;
    //   354: iconst_1
    //   355: swap
    //   356: aastore
    //   357: dup_x1
    //   358: swap
    //   359: invokestatic valueOf : (F)Ljava/lang/Float;
    //   362: iconst_0
    //   363: swap
    //   364: aastore
    //   365: invokevirtual k : ([Ljava/lang/Object;)V
    //   368: aload_0
    //   369: getfield A : Lwtf/opal/pa;
    //   372: fload_1
    //   373: ldc_w 3.0
    //   376: fadd
    //   377: fload_2
    //   378: ldc_w 3.0
    //   381: fadd
    //   382: ldc_w 40.0
    //   385: lload #13
    //   387: ldc_w 40.0
    //   390: ldc_w 0.5
    //   393: aload_0
    //   394: getfield J : I
    //   397: bipush #7
    //   399: anewarray java/lang/Object
    //   402: dup_x1
    //   403: swap
    //   404: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   407: bipush #6
    //   409: swap
    //   410: aastore
    //   411: dup_x1
    //   412: swap
    //   413: invokestatic valueOf : (F)Ljava/lang/Float;
    //   416: iconst_5
    //   417: swap
    //   418: aastore
    //   419: dup_x1
    //   420: swap
    //   421: invokestatic valueOf : (F)Ljava/lang/Float;
    //   424: iconst_4
    //   425: swap
    //   426: aastore
    //   427: dup_x2
    //   428: dup_x2
    //   429: pop
    //   430: invokestatic valueOf : (J)Ljava/lang/Long;
    //   433: iconst_3
    //   434: swap
    //   435: aastore
    //   436: dup_x1
    //   437: swap
    //   438: invokestatic valueOf : (F)Ljava/lang/Float;
    //   441: iconst_2
    //   442: swap
    //   443: aastore
    //   444: dup_x1
    //   445: swap
    //   446: invokestatic valueOf : (F)Ljava/lang/Float;
    //   449: iconst_1
    //   450: swap
    //   451: aastore
    //   452: dup_x1
    //   453: swap
    //   454: invokestatic valueOf : (F)Ljava/lang/Float;
    //   457: iconst_0
    //   458: swap
    //   459: aastore
    //   460: invokevirtual P : ([Ljava/lang/Object;)V
    //   463: invokestatic s : ()Ljava/lang/String;
    //   466: aload #5
    //   468: aload #6
    //   470: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   473: invokeinterface getString : ()Ljava/lang/String;
    //   478: fload_1
    //   479: ldc 8.0
    //   481: fadd
    //   482: ldc_w 40.0
    //   485: fadd
    //   486: fload_2
    //   487: ldc_w 4.0
    //   490: fadd
    //   491: lload #19
    //   493: ldc 8.0
    //   495: iconst_m1
    //   496: bipush #6
    //   498: anewarray java/lang/Object
    //   501: dup_x1
    //   502: swap
    //   503: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   506: iconst_5
    //   507: swap
    //   508: aastore
    //   509: dup_x1
    //   510: swap
    //   511: invokestatic valueOf : (F)Ljava/lang/Float;
    //   514: iconst_4
    //   515: swap
    //   516: aastore
    //   517: dup_x2
    //   518: dup_x2
    //   519: pop
    //   520: invokestatic valueOf : (J)Ljava/lang/Long;
    //   523: iconst_3
    //   524: swap
    //   525: aastore
    //   526: dup_x1
    //   527: swap
    //   528: invokestatic valueOf : (F)Ljava/lang/Float;
    //   531: iconst_2
    //   532: swap
    //   533: aastore
    //   534: dup_x1
    //   535: swap
    //   536: invokestatic valueOf : (F)Ljava/lang/Float;
    //   539: iconst_1
    //   540: swap
    //   541: aastore
    //   542: dup_x1
    //   543: swap
    //   544: iconst_0
    //   545: swap
    //   546: aastore
    //   547: invokevirtual C : ([Ljava/lang/Object;)V
    //   550: fload_3
    //   551: ldc_w 52.0
    //   554: fsub
    //   555: fstore #22
    //   557: aload_0
    //   558: getfield A : Lwtf/opal/pa;
    //   561: fload_1
    //   562: ldc 8.0
    //   564: fadd
    //   565: ldc_w 40.0
    //   568: fadd
    //   569: fload_2
    //   570: ldc_w 13.5
    //   573: fadd
    //   574: fload #22
    //   576: ldc_w 5.0
    //   579: aload_0
    //   580: getfield M : I
    //   583: lload #15
    //   585: bipush #6
    //   587: anewarray java/lang/Object
    //   590: dup_x2
    //   591: dup_x2
    //   592: pop
    //   593: invokestatic valueOf : (J)Ljava/lang/Long;
    //   596: iconst_5
    //   597: swap
    //   598: aastore
    //   599: dup_x1
    //   600: swap
    //   601: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   604: iconst_4
    //   605: swap
    //   606: aastore
    //   607: dup_x1
    //   608: swap
    //   609: invokestatic valueOf : (F)Ljava/lang/Float;
    //   612: iconst_3
    //   613: swap
    //   614: aastore
    //   615: dup_x1
    //   616: swap
    //   617: invokestatic valueOf : (F)Ljava/lang/Float;
    //   620: iconst_2
    //   621: swap
    //   622: aastore
    //   623: dup_x1
    //   624: swap
    //   625: invokestatic valueOf : (F)Ljava/lang/Float;
    //   628: iconst_1
    //   629: swap
    //   630: aastore
    //   631: dup_x1
    //   632: swap
    //   633: invokestatic valueOf : (F)Ljava/lang/Float;
    //   636: iconst_0
    //   637: swap
    //   638: aastore
    //   639: invokevirtual k : ([Ljava/lang/Object;)V
    //   642: aload_0
    //   643: getfield A : Lwtf/opal/pa;
    //   646: fload_1
    //   647: ldc 8.0
    //   649: fadd
    //   650: ldc_w 40.0
    //   653: fadd
    //   654: ldc_w 0.5
    //   657: fadd
    //   658: fload_2
    //   659: ldc_w 14.0
    //   662: fadd
    //   663: fload #22
    //   665: fconst_1
    //   666: fsub
    //   667: ldc_w 4.0
    //   670: aload_0
    //   671: getfield M : I
    //   674: iload #7
    //   676: ldc_w 0.2
    //   679: iconst_3
    //   680: anewarray java/lang/Object
    //   683: dup_x1
    //   684: swap
    //   685: invokestatic valueOf : (F)Ljava/lang/Float;
    //   688: iconst_2
    //   689: swap
    //   690: aastore
    //   691: dup_x1
    //   692: swap
    //   693: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   696: iconst_1
    //   697: swap
    //   698: aastore
    //   699: dup_x1
    //   700: swap
    //   701: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   704: iconst_0
    //   705: swap
    //   706: aastore
    //   707: invokestatic P : ([Ljava/lang/Object;)I
    //   710: lload #15
    //   712: bipush #6
    //   714: anewarray java/lang/Object
    //   717: dup_x2
    //   718: dup_x2
    //   719: pop
    //   720: invokestatic valueOf : (J)Ljava/lang/Long;
    //   723: iconst_5
    //   724: swap
    //   725: aastore
    //   726: dup_x1
    //   727: swap
    //   728: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   731: iconst_4
    //   732: swap
    //   733: aastore
    //   734: dup_x1
    //   735: swap
    //   736: invokestatic valueOf : (F)Ljava/lang/Float;
    //   739: iconst_3
    //   740: swap
    //   741: aastore
    //   742: dup_x1
    //   743: swap
    //   744: invokestatic valueOf : (F)Ljava/lang/Float;
    //   747: iconst_2
    //   748: swap
    //   749: aastore
    //   750: dup_x1
    //   751: swap
    //   752: invokestatic valueOf : (F)Ljava/lang/Float;
    //   755: iconst_1
    //   756: swap
    //   757: aastore
    //   758: dup_x1
    //   759: swap
    //   760: invokestatic valueOf : (F)Ljava/lang/Float;
    //   763: iconst_0
    //   764: swap
    //   765: aastore
    //   766: invokevirtual k : ([Ljava/lang/Object;)V
    //   769: fload #22
    //   771: fconst_1
    //   772: fsub
    //   773: fstore #23
    //   775: aload_0
    //   776: getfield A : Lwtf/opal/pa;
    //   779: fload_1
    //   780: ldc 8.0
    //   782: fadd
    //   783: ldc_w 40.0
    //   786: fadd
    //   787: ldc_w 0.5
    //   790: fadd
    //   791: fload_2
    //   792: ldc_w 14.5
    //   795: fadd
    //   796: fload #23
    //   798: fload #8
    //   800: fload #9
    //   802: fdiv
    //   803: fmul
    //   804: ldc_w 4.0
    //   807: iload #7
    //   809: lload #15
    //   811: bipush #6
    //   813: anewarray java/lang/Object
    //   816: dup_x2
    //   817: dup_x2
    //   818: pop
    //   819: invokestatic valueOf : (J)Ljava/lang/Long;
    //   822: iconst_5
    //   823: swap
    //   824: aastore
    //   825: dup_x1
    //   826: swap
    //   827: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   830: iconst_4
    //   831: swap
    //   832: aastore
    //   833: dup_x1
    //   834: swap
    //   835: invokestatic valueOf : (F)Ljava/lang/Float;
    //   838: iconst_3
    //   839: swap
    //   840: aastore
    //   841: dup_x1
    //   842: swap
    //   843: invokestatic valueOf : (F)Ljava/lang/Float;
    //   846: iconst_2
    //   847: swap
    //   848: aastore
    //   849: dup_x1
    //   850: swap
    //   851: invokestatic valueOf : (F)Ljava/lang/Float;
    //   854: iconst_1
    //   855: swap
    //   856: aastore
    //   857: dup_x1
    //   858: swap
    //   859: invokestatic valueOf : (F)Ljava/lang/Float;
    //   862: iconst_0
    //   863: swap
    //   864: aastore
    //   865: invokevirtual k : ([Ljava/lang/Object;)V
    //   868: astore #21
    //   870: fload #23
    //   872: ldc_w 11.0
    //   875: fdiv
    //   876: fstore #24
    //   878: iconst_1
    //   879: istore #25
    //   881: iload #25
    //   883: sipush #31534
    //   886: ldc2_w 547053103722972972
    //   889: lload #11
    //   891: lxor
    //   892: <illegal opcode> u : (IJ)I
    //   897: if_icmpge -> 1013
    //   900: aload_0
    //   901: getfield A : Lwtf/opal/pa;
    //   904: fload_1
    //   905: ldc 8.0
    //   907: fadd
    //   908: ldc_w 40.0
    //   911: fadd
    //   912: fload #24
    //   914: iload #25
    //   916: i2f
    //   917: fmul
    //   918: fadd
    //   919: fload_2
    //   920: ldc_w 14.5
    //   923: fadd
    //   924: ldc_w 0.5
    //   927: ldc_w 4.0
    //   930: aload_0
    //   931: getfield M : I
    //   934: lload #15
    //   936: bipush #6
    //   938: anewarray java/lang/Object
    //   941: dup_x2
    //   942: dup_x2
    //   943: pop
    //   944: invokestatic valueOf : (J)Ljava/lang/Long;
    //   947: iconst_5
    //   948: swap
    //   949: aastore
    //   950: dup_x1
    //   951: swap
    //   952: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   955: iconst_4
    //   956: swap
    //   957: aastore
    //   958: dup_x1
    //   959: swap
    //   960: invokestatic valueOf : (F)Ljava/lang/Float;
    //   963: iconst_3
    //   964: swap
    //   965: aastore
    //   966: dup_x1
    //   967: swap
    //   968: invokestatic valueOf : (F)Ljava/lang/Float;
    //   971: iconst_2
    //   972: swap
    //   973: aastore
    //   974: dup_x1
    //   975: swap
    //   976: invokestatic valueOf : (F)Ljava/lang/Float;
    //   979: iconst_1
    //   980: swap
    //   981: aastore
    //   982: dup_x1
    //   983: swap
    //   984: invokestatic valueOf : (F)Ljava/lang/Float;
    //   987: iconst_0
    //   988: swap
    //   989: aastore
    //   990: invokevirtual k : ([Ljava/lang/Object;)V
    //   993: iinc #25, 1
    //   996: aload #21
    //   998: ifnull -> 1220
    //   1001: aload #21
    //   1003: ifnonnull -> 881
    //   1006: goto -> 1013
    //   1009: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1012: athrow
    //   1013: aload #10
    //   1015: fload #8
    //   1017: f2d
    //   1018: lload #17
    //   1020: dup2_x2
    //   1021: pop2
    //   1022: iconst_1
    //   1023: iconst_3
    //   1024: anewarray java/lang/Object
    //   1027: dup_x1
    //   1028: swap
    //   1029: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1032: iconst_2
    //   1033: swap
    //   1034: aastore
    //   1035: dup_x2
    //   1036: dup_x2
    //   1037: pop
    //   1038: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1041: iconst_1
    //   1042: swap
    //   1043: aastore
    //   1044: dup_x2
    //   1045: dup_x2
    //   1046: pop
    //   1047: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1050: iconst_0
    //   1051: swap
    //   1052: aastore
    //   1053: invokestatic P : ([Ljava/lang/Object;)D
    //   1056: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1059: getfield field_1724 : Lnet/minecraft/class_746;
    //   1062: aload #6
    //   1064: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   1067: f2d
    //   1068: lload #17
    //   1070: dup2_x2
    //   1071: pop2
    //   1072: iconst_1
    //   1073: iconst_3
    //   1074: anewarray java/lang/Object
    //   1077: dup_x1
    //   1078: swap
    //   1079: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1082: iconst_2
    //   1083: swap
    //   1084: aastore
    //   1085: dup_x2
    //   1086: dup_x2
    //   1087: pop
    //   1088: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1091: iconst_1
    //   1092: swap
    //   1093: aastore
    //   1094: dup_x2
    //   1095: dup_x2
    //   1096: pop
    //   1097: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1100: iconst_0
    //   1101: swap
    //   1102: aastore
    //   1103: invokestatic P : ([Ljava/lang/Object;)D
    //   1106: dstore #26
    //   1108: dstore #28
    //   1110: sipush #6589
    //   1113: ldc2_w 4827772858210722266
    //   1116: lload #11
    //   1118: lxor
    //   1119: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   1124: dload #28
    //   1126: sipush #23531
    //   1129: ldc2_w 5717674718058668942
    //   1132: lload #11
    //   1134: lxor
    //   1135: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   1140: dload #26
    //   1142: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;DLjava/lang/String;D)Ljava/lang/String;
    //   1147: fload_1
    //   1148: ldc 8.0
    //   1150: fadd
    //   1151: ldc_w 40.0
    //   1154: fadd
    //   1155: fload_2
    //   1156: ldc_w 22.5
    //   1159: fadd
    //   1160: lload #19
    //   1162: ldc_w 6.0
    //   1165: iconst_m1
    //   1166: bipush #6
    //   1168: anewarray java/lang/Object
    //   1171: dup_x1
    //   1172: swap
    //   1173: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1176: iconst_5
    //   1177: swap
    //   1178: aastore
    //   1179: dup_x1
    //   1180: swap
    //   1181: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1184: iconst_4
    //   1185: swap
    //   1186: aastore
    //   1187: dup_x2
    //   1188: dup_x2
    //   1189: pop
    //   1190: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1193: iconst_3
    //   1194: swap
    //   1195: aastore
    //   1196: dup_x1
    //   1197: swap
    //   1198: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1201: iconst_2
    //   1202: swap
    //   1203: aastore
    //   1204: dup_x1
    //   1205: swap
    //   1206: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1209: iconst_1
    //   1210: swap
    //   1211: aastore
    //   1212: dup_x1
    //   1213: swap
    //   1214: iconst_0
    //   1215: swap
    //   1216: aastore
    //   1217: invokevirtual C : ([Ljava/lang/Object;)V
    //   1220: return
    // Exception table:
    //   from	to	target	type
    //   900	1006	1009	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x5CB7A88F272EL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "2;IÎYË®<¡zÌµoHznÖXåcû\bÚuG\f¬\0205s»Ý\002ÜùwFµS}/%").length();
    byte b2 = 32;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x434C;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])e.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/px", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      d[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[i];
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
    //   66: ldc_w 'wtf/opal/px'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x472F;
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
        throw new RuntimeException("wtf/opal/px", exception);
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
    //   66: ldc_w 'wtf/opal/px'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\px.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */