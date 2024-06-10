package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_332;

public final class pr implements kx {
  private final d C;
  
  private final dk d;
  
  private final dk x;
  
  private final da X;
  
  private int m;
  
  private int q;
  
  private int O;
  
  private int A;
  
  private final List<b5<?>> a;
  
  private float N;
  
  private float L;
  
  private boolean v;
  
  private static final long b = on.a(4718702278274474183L, 1719509191632216897L, MethodHandles.lookup().lookupClass()).a(256088100265986L);
  
  private static final String c;
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public pr(long paramLong, d paramd) {
    // Byte code:
    //   0: getstatic wtf/opal/pr.b : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 28214940849542
    //   11: lxor
    //   12: lstore #4
    //   14: dup2
    //   15: ldc2_w 111415806203364
    //   18: lxor
    //   19: dup2
    //   20: bipush #32
    //   22: lushr
    //   23: l2i
    //   24: istore #6
    //   26: dup2
    //   27: bipush #32
    //   29: lshl
    //   30: bipush #48
    //   32: lushr
    //   33: l2i
    //   34: istore #7
    //   36: dup2
    //   37: bipush #48
    //   39: lshl
    //   40: bipush #48
    //   42: lushr
    //   43: l2i
    //   44: istore #8
    //   46: pop2
    //   47: pop2
    //   48: aload_0
    //   49: invokespecial <init> : ()V
    //   52: aload_0
    //   53: new wtf/opal/dk
    //   56: dup
    //   57: sipush #6903
    //   60: ldc2_w 6821387258666590424
    //   63: lload_1
    //   64: lxor
    //   65: <illegal opcode> g : (IJ)I
    //   70: iload #6
    //   72: dconst_1
    //   73: iload #7
    //   75: i2c
    //   76: iload #8
    //   78: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   81: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   84: putfield d : Lwtf/opal/dk;
    //   87: aload_0
    //   88: new wtf/opal/dk
    //   91: dup
    //   92: sipush #13061
    //   95: ldc2_w 4026279306909419822
    //   98: lload_1
    //   99: lxor
    //   100: <illegal opcode> g : (IJ)I
    //   105: iload #6
    //   107: dconst_1
    //   108: iload #7
    //   110: i2c
    //   111: iload #8
    //   113: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   116: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   119: putfield x : Lwtf/opal/dk;
    //   122: aload_0
    //   123: new wtf/opal/da
    //   126: dup
    //   127: lload #4
    //   129: invokespecial <init> : (J)V
    //   132: putfield X : Lwtf/opal/da;
    //   135: aload_0
    //   136: new java/util/ArrayList
    //   139: dup
    //   140: invokespecial <init> : ()V
    //   143: putfield a : Ljava/util/List;
    //   146: aload_0
    //   147: aload_3
    //   148: putfield C : Lwtf/opal/d;
    //   151: aload_3
    //   152: iconst_0
    //   153: anewarray java/lang/Object
    //   156: invokevirtual e : ([Ljava/lang/Object;)Ljava/util/List;
    //   159: aload_0
    //   160: <illegal opcode> accept : (Lwtf/opal/pr;)Ljava/util/function/Consumer;
    //   165: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   170: return
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
    //   18: istore #7
    //   20: dup
    //   21: iconst_2
    //   22: aaload
    //   23: checkcast java/lang/Integer
    //   26: invokevirtual intValue : ()I
    //   29: istore #4
    //   31: dup
    //   32: iconst_3
    //   33: aaload
    //   34: checkcast java/lang/Long
    //   37: invokevirtual longValue : ()J
    //   40: lstore_2
    //   41: dup
    //   42: iconst_4
    //   43: aaload
    //   44: checkcast java/lang/Float
    //   47: invokevirtual floatValue : ()F
    //   50: fstore #6
    //   52: pop
    //   53: lload_2
    //   54: dup2
    //   55: ldc2_w 135795707432174
    //   58: lxor
    //   59: lstore #8
    //   61: dup2
    //   62: ldc2_w 56374928813011
    //   65: lxor
    //   66: lstore #10
    //   68: dup2
    //   69: ldc2_w 69422246435098
    //   72: lxor
    //   73: lstore #12
    //   75: dup2
    //   76: ldc2_w 57303961947953
    //   79: lxor
    //   80: lstore #14
    //   82: dup2
    //   83: ldc2_w 135688353470910
    //   86: lxor
    //   87: lstore #16
    //   89: dup2
    //   90: ldc2_w 130132861335437
    //   93: lxor
    //   94: lstore #18
    //   96: dup2
    //   97: ldc2_w 64480015135216
    //   100: lxor
    //   101: lstore #20
    //   103: dup2
    //   104: ldc2_w 34202666976149
    //   107: lxor
    //   108: lstore #22
    //   110: dup2
    //   111: ldc2_w 101116344371865
    //   114: lxor
    //   115: lstore #24
    //   117: dup2
    //   118: ldc2_w 53439933111312
    //   121: lxor
    //   122: lstore #26
    //   124: dup2
    //   125: ldc2_w 19331518352804
    //   128: lxor
    //   129: lstore #28
    //   131: pop2
    //   132: invokestatic g : ()[Lwtf/opal/d;
    //   135: iconst_0
    //   136: anewarray java/lang/Object
    //   139: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   142: iconst_0
    //   143: anewarray java/lang/Object
    //   146: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   149: ldc wtf/opal/jt
    //   151: iconst_1
    //   152: anewarray java/lang/Object
    //   155: dup_x1
    //   156: swap
    //   157: iconst_0
    //   158: swap
    //   159: aastore
    //   160: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   163: checkcast wtf/opal/jt
    //   166: astore #35
    //   168: astore #34
    //   170: aload_0
    //   171: getfield x : Lwtf/opal/dk;
    //   174: aload_0
    //   175: getfield C : Lwtf/opal/d;
    //   178: iconst_0
    //   179: anewarray java/lang/Object
    //   182: invokevirtual D : ([Ljava/lang/Object;)Z
    //   185: ifeq -> 198
    //   188: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   191: goto -> 201
    //   194: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   197: athrow
    //   198: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   201: lload #12
    //   203: dup2_x1
    //   204: pop2
    //   205: iconst_2
    //   206: anewarray java/lang/Object
    //   209: dup_x1
    //   210: swap
    //   211: iconst_1
    //   212: swap
    //   213: aastore
    //   214: dup_x2
    //   215: dup_x2
    //   216: pop
    //   217: invokestatic valueOf : (J)Ljava/lang/Long;
    //   220: iconst_0
    //   221: swap
    //   222: aastore
    //   223: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   226: pop
    //   227: aload_0
    //   228: getfield v : Z
    //   231: aload #34
    //   233: lload_2
    //   234: lconst_0
    //   235: lcmp
    //   236: iflt -> 272
    //   239: ifnonnull -> 270
    //   242: ifeq -> 289
    //   245: goto -> 252
    //   248: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   251: athrow
    //   252: aload_0
    //   253: getfield C : Lwtf/opal/d;
    //   256: iconst_0
    //   257: anewarray java/lang/Object
    //   260: invokevirtual R : ([Ljava/lang/Object;)Z
    //   263: goto -> 270
    //   266: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   269: athrow
    //   270: aload #34
    //   272: ifnonnull -> 286
    //   275: ifne -> 289
    //   278: goto -> 285
    //   281: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   284: athrow
    //   285: iconst_1
    //   286: goto -> 290
    //   289: iconst_0
    //   290: istore #36
    //   292: aload_0
    //   293: getfield C : Lwtf/opal/d;
    //   296: iconst_0
    //   297: anewarray java/lang/Object
    //   300: invokevirtual D : ([Ljava/lang/Object;)Z
    //   303: lload_2
    //   304: lconst_0
    //   305: lcmp
    //   306: iflt -> 353
    //   309: aload #34
    //   311: ifnonnull -> 353
    //   314: ifne -> 528
    //   317: goto -> 324
    //   320: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   323: athrow
    //   324: aload_0
    //   325: getfield x : Lwtf/opal/dk;
    //   328: lload #26
    //   330: iconst_1
    //   331: anewarray java/lang/Object
    //   334: dup_x2
    //   335: dup_x2
    //   336: pop
    //   337: invokestatic valueOf : (J)Ljava/lang/Long;
    //   340: iconst_0
    //   341: swap
    //   342: aastore
    //   343: invokevirtual H : ([Ljava/lang/Object;)Z
    //   346: goto -> 353
    //   349: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   352: athrow
    //   353: ifne -> 528
    //   356: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   359: aload_0
    //   360: getfield m : I
    //   363: i2f
    //   364: aload_0
    //   365: getfield q : I
    //   368: i2f
    //   369: aload_0
    //   370: getfield O : I
    //   373: i2f
    //   374: aload_0
    //   375: getfield A : I
    //   378: i2f
    //   379: fconst_0
    //   380: fconst_0
    //   381: iload #36
    //   383: ifeq -> 402
    //   386: goto -> 393
    //   389: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   392: athrow
    //   393: ldc 4.0
    //   395: goto -> 403
    //   398: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   401: athrow
    //   402: fconst_0
    //   403: iload #36
    //   405: ifeq -> 417
    //   408: ldc 4.0
    //   410: goto -> 418
    //   413: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   416: athrow
    //   417: fconst_0
    //   418: sipush #16747
    //   421: ldc2_w 4454380287448706540
    //   424: lload_2
    //   425: lxor
    //   426: <illegal opcode> g : (IJ)I
    //   431: lload #20
    //   433: dup2_x2
    //   434: pop2
    //   435: bipush #10
    //   437: anewarray java/lang/Object
    //   440: dup_x1
    //   441: swap
    //   442: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   445: bipush #9
    //   447: swap
    //   448: aastore
    //   449: dup_x1
    //   450: swap
    //   451: invokestatic valueOf : (F)Ljava/lang/Float;
    //   454: bipush #8
    //   456: swap
    //   457: aastore
    //   458: dup_x2
    //   459: dup_x2
    //   460: pop
    //   461: invokestatic valueOf : (J)Ljava/lang/Long;
    //   464: bipush #7
    //   466: swap
    //   467: aastore
    //   468: dup_x1
    //   469: swap
    //   470: invokestatic valueOf : (F)Ljava/lang/Float;
    //   473: bipush #6
    //   475: swap
    //   476: aastore
    //   477: dup_x1
    //   478: swap
    //   479: invokestatic valueOf : (F)Ljava/lang/Float;
    //   482: iconst_5
    //   483: swap
    //   484: aastore
    //   485: dup_x1
    //   486: swap
    //   487: invokestatic valueOf : (F)Ljava/lang/Float;
    //   490: iconst_4
    //   491: swap
    //   492: aastore
    //   493: dup_x1
    //   494: swap
    //   495: invokestatic valueOf : (F)Ljava/lang/Float;
    //   498: iconst_3
    //   499: swap
    //   500: aastore
    //   501: dup_x1
    //   502: swap
    //   503: invokestatic valueOf : (F)Ljava/lang/Float;
    //   506: iconst_2
    //   507: swap
    //   508: aastore
    //   509: dup_x1
    //   510: swap
    //   511: invokestatic valueOf : (F)Ljava/lang/Float;
    //   514: iconst_1
    //   515: swap
    //   516: aastore
    //   517: dup_x1
    //   518: swap
    //   519: invokestatic valueOf : (F)Ljava/lang/Float;
    //   522: iconst_0
    //   523: swap
    //   524: aastore
    //   525: invokevirtual x : ([Ljava/lang/Object;)V
    //   528: new java/awt/Color
    //   531: dup
    //   532: aload #35
    //   534: iconst_0
    //   535: anewarray java/lang/Object
    //   538: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   541: invokevirtual z : ()Ljava/lang/Object;
    //   544: checkcast java/lang/Integer
    //   547: invokevirtual intValue : ()I
    //   550: invokespecial <init> : (I)V
    //   553: astore #37
    //   555: new java/awt/Color
    //   558: dup
    //   559: aload #35
    //   561: iconst_0
    //   562: anewarray java/lang/Object
    //   565: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   568: invokevirtual z : ()Ljava/lang/Object;
    //   571: checkcast java/lang/Integer
    //   574: invokevirtual intValue : ()I
    //   577: invokespecial <init> : (I)V
    //   580: astore #38
    //   582: aload_0
    //   583: getfield C : Lwtf/opal/d;
    //   586: iconst_0
    //   587: anewarray java/lang/Object
    //   590: invokevirtual D : ([Ljava/lang/Object;)Z
    //   593: lload_2
    //   594: lconst_0
    //   595: lcmp
    //   596: iflt -> 643
    //   599: aload #34
    //   601: ifnonnull -> 643
    //   604: ifne -> 646
    //   607: goto -> 614
    //   610: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   613: athrow
    //   614: aload_0
    //   615: getfield x : Lwtf/opal/dk;
    //   618: lload #26
    //   620: iconst_1
    //   621: anewarray java/lang/Object
    //   624: dup_x2
    //   625: dup_x2
    //   626: pop
    //   627: invokestatic valueOf : (J)Ljava/lang/Long;
    //   630: iconst_0
    //   631: swap
    //   632: aastore
    //   633: invokevirtual H : ([Ljava/lang/Object;)Z
    //   636: goto -> 643
    //   639: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   642: athrow
    //   643: ifne -> 991
    //   646: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   649: aload_0
    //   650: getfield m : I
    //   653: i2f
    //   654: aload_0
    //   655: getfield q : I
    //   658: i2f
    //   659: aload_0
    //   660: getfield O : I
    //   663: i2f
    //   664: aload_0
    //   665: getfield A : I
    //   668: i2f
    //   669: fconst_0
    //   670: fconst_0
    //   671: iload #36
    //   673: ifeq -> 692
    //   676: goto -> 683
    //   679: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   682: athrow
    //   683: ldc 4.0
    //   685: goto -> 693
    //   688: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   691: athrow
    //   692: fconst_0
    //   693: iload #36
    //   695: ifeq -> 707
    //   698: ldc 4.0
    //   700: goto -> 708
    //   703: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   706: athrow
    //   707: fconst_0
    //   708: new java/awt/Color
    //   711: dup
    //   712: aload #37
    //   714: invokevirtual getRed : ()I
    //   717: aload #37
    //   719: invokevirtual getGreen : ()I
    //   722: aload #37
    //   724: invokevirtual getBlue : ()I
    //   727: aload_0
    //   728: getfield x : Lwtf/opal/dk;
    //   731: lload #16
    //   733: iconst_1
    //   734: anewarray java/lang/Object
    //   737: dup_x2
    //   738: dup_x2
    //   739: pop
    //   740: invokestatic valueOf : (J)Ljava/lang/Long;
    //   743: iconst_0
    //   744: swap
    //   745: aastore
    //   746: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   749: invokevirtual doubleValue : ()D
    //   752: ldc2_w 255.0
    //   755: dmul
    //   756: d2i
    //   757: sipush #28562
    //   760: ldc2_w 6227975942262473495
    //   763: lload_2
    //   764: lxor
    //   765: <illegal opcode> g : (IJ)I
    //   770: invokestatic min : (II)I
    //   773: invokespecial <init> : (IIII)V
    //   776: invokevirtual getRGB : ()I
    //   779: lload #10
    //   781: dup2_x1
    //   782: pop2
    //   783: new java/awt/Color
    //   786: dup
    //   787: aload #38
    //   789: invokevirtual getRed : ()I
    //   792: aload #38
    //   794: invokevirtual getGreen : ()I
    //   797: aload #38
    //   799: invokevirtual getBlue : ()I
    //   802: aload_0
    //   803: getfield x : Lwtf/opal/dk;
    //   806: lload #16
    //   808: iconst_1
    //   809: anewarray java/lang/Object
    //   812: dup_x2
    //   813: dup_x2
    //   814: pop
    //   815: invokestatic valueOf : (J)Ljava/lang/Long;
    //   818: iconst_0
    //   819: swap
    //   820: aastore
    //   821: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   824: invokevirtual doubleValue : ()D
    //   827: ldc2_w 255.0
    //   830: dmul
    //   831: d2i
    //   832: sipush #32631
    //   835: ldc2_w 4306631717568074740
    //   838: lload_2
    //   839: lxor
    //   840: <illegal opcode> g : (IJ)I
    //   845: invokestatic min : (II)I
    //   848: invokespecial <init> : (IIII)V
    //   851: invokevirtual getRGB : ()I
    //   854: fconst_0
    //   855: bipush #12
    //   857: anewarray java/lang/Object
    //   860: dup_x1
    //   861: swap
    //   862: invokestatic valueOf : (F)Ljava/lang/Float;
    //   865: bipush #11
    //   867: swap
    //   868: aastore
    //   869: dup_x1
    //   870: swap
    //   871: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   874: bipush #10
    //   876: swap
    //   877: aastore
    //   878: dup_x1
    //   879: swap
    //   880: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   883: bipush #9
    //   885: swap
    //   886: aastore
    //   887: dup_x2
    //   888: dup_x2
    //   889: pop
    //   890: invokestatic valueOf : (J)Ljava/lang/Long;
    //   893: bipush #8
    //   895: swap
    //   896: aastore
    //   897: dup_x1
    //   898: swap
    //   899: invokestatic valueOf : (F)Ljava/lang/Float;
    //   902: bipush #7
    //   904: swap
    //   905: aastore
    //   906: dup_x1
    //   907: swap
    //   908: invokestatic valueOf : (F)Ljava/lang/Float;
    //   911: bipush #6
    //   913: swap
    //   914: aastore
    //   915: dup_x1
    //   916: swap
    //   917: invokestatic valueOf : (F)Ljava/lang/Float;
    //   920: iconst_5
    //   921: swap
    //   922: aastore
    //   923: dup_x1
    //   924: swap
    //   925: invokestatic valueOf : (F)Ljava/lang/Float;
    //   928: iconst_4
    //   929: swap
    //   930: aastore
    //   931: dup_x1
    //   932: swap
    //   933: invokestatic valueOf : (F)Ljava/lang/Float;
    //   936: iconst_3
    //   937: swap
    //   938: aastore
    //   939: dup_x1
    //   940: swap
    //   941: invokestatic valueOf : (F)Ljava/lang/Float;
    //   944: iconst_2
    //   945: swap
    //   946: aastore
    //   947: dup_x1
    //   948: swap
    //   949: invokestatic valueOf : (F)Ljava/lang/Float;
    //   952: iconst_1
    //   953: swap
    //   954: aastore
    //   955: dup_x1
    //   956: swap
    //   957: invokestatic valueOf : (F)Ljava/lang/Float;
    //   960: iconst_0
    //   961: swap
    //   962: aastore
    //   963: invokevirtual H : ([Ljava/lang/Object;)V
    //   966: lload_2
    //   967: lconst_0
    //   968: lcmp
    //   969: ifle -> 1156
    //   972: aload #34
    //   974: ifnull -> 1156
    //   977: iconst_5
    //   978: anewarray wtf/opal/d
    //   981: invokestatic p : ([Lwtf/opal/d;)V
    //   984: goto -> 991
    //   987: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   990: athrow
    //   991: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   994: aload_0
    //   995: getfield m : I
    //   998: i2f
    //   999: aload_0
    //   1000: getfield q : I
    //   1003: i2f
    //   1004: aload_0
    //   1005: getfield O : I
    //   1008: i2f
    //   1009: aload_0
    //   1010: getfield A : I
    //   1013: i2f
    //   1014: fconst_0
    //   1015: fconst_0
    //   1016: iload #36
    //   1018: ifeq -> 1030
    //   1021: ldc 4.0
    //   1023: goto -> 1031
    //   1026: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1029: athrow
    //   1030: fconst_0
    //   1031: iload #36
    //   1033: ifeq -> 1045
    //   1036: ldc 4.0
    //   1038: goto -> 1046
    //   1041: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1044: athrow
    //   1045: fconst_0
    //   1046: sipush #24553
    //   1049: ldc2_w 627454557393089384
    //   1052: lload_2
    //   1053: lxor
    //   1054: <illegal opcode> g : (IJ)I
    //   1059: lload #20
    //   1061: dup2_x2
    //   1062: pop2
    //   1063: bipush #10
    //   1065: anewarray java/lang/Object
    //   1068: dup_x1
    //   1069: swap
    //   1070: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1073: bipush #9
    //   1075: swap
    //   1076: aastore
    //   1077: dup_x1
    //   1078: swap
    //   1079: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1082: bipush #8
    //   1084: swap
    //   1085: aastore
    //   1086: dup_x2
    //   1087: dup_x2
    //   1088: pop
    //   1089: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1092: bipush #7
    //   1094: swap
    //   1095: aastore
    //   1096: dup_x1
    //   1097: swap
    //   1098: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1101: bipush #6
    //   1103: swap
    //   1104: aastore
    //   1105: dup_x1
    //   1106: swap
    //   1107: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1110: iconst_5
    //   1111: swap
    //   1112: aastore
    //   1113: dup_x1
    //   1114: swap
    //   1115: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1118: iconst_4
    //   1119: swap
    //   1120: aastore
    //   1121: dup_x1
    //   1122: swap
    //   1123: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1126: iconst_3
    //   1127: swap
    //   1128: aastore
    //   1129: dup_x1
    //   1130: swap
    //   1131: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1134: iconst_2
    //   1135: swap
    //   1136: aastore
    //   1137: dup_x1
    //   1138: swap
    //   1139: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1142: iconst_1
    //   1143: swap
    //   1144: aastore
    //   1145: dup_x1
    //   1146: swap
    //   1147: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1150: iconst_0
    //   1151: swap
    //   1152: aastore
    //   1153: invokevirtual x : ([Ljava/lang/Object;)V
    //   1156: aload #35
    //   1158: lload #8
    //   1160: iconst_1
    //   1161: anewarray java/lang/Object
    //   1164: dup_x2
    //   1165: dup_x2
    //   1166: pop
    //   1167: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1170: iconst_0
    //   1171: swap
    //   1172: aastore
    //   1173: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1176: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   1179: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1182: aload #34
    //   1184: ifnonnull -> 1224
    //   1187: ifeq -> 1220
    //   1190: goto -> 1197
    //   1193: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1196: athrow
    //   1197: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   1200: iconst_0
    //   1201: anewarray java/lang/Object
    //   1204: invokevirtual y : ([Ljava/lang/Object;)J
    //   1207: ldc_w -0.3
    //   1210: invokestatic nvgTextLetterSpacing : (JF)V
    //   1213: goto -> 1220
    //   1216: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1219: athrow
    //   1220: aload_0
    //   1221: getfield A : I
    //   1224: i2f
    //   1225: fconst_2
    //   1226: fdiv
    //   1227: getstatic wtf/opal/pr.Z : Lwtf/opal/bu;
    //   1230: aload_0
    //   1231: getfield C : Lwtf/opal/d;
    //   1234: iconst_0
    //   1235: anewarray java/lang/Object
    //   1238: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1241: lload #18
    //   1243: dup2_x1
    //   1244: pop2
    //   1245: ldc_w 8.5
    //   1248: iconst_3
    //   1249: anewarray java/lang/Object
    //   1252: dup_x1
    //   1253: swap
    //   1254: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1257: iconst_2
    //   1258: swap
    //   1259: aastore
    //   1260: dup_x1
    //   1261: swap
    //   1262: iconst_1
    //   1263: swap
    //   1264: aastore
    //   1265: dup_x2
    //   1266: dup_x2
    //   1267: pop
    //   1268: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1271: iconst_0
    //   1272: swap
    //   1273: aastore
    //   1274: invokevirtual A : ([Ljava/lang/Object;)F
    //   1277: fconst_2
    //   1278: fdiv
    //   1279: fsub
    //   1280: fstore #39
    //   1282: getstatic wtf/opal/pr.Z : Lwtf/opal/bu;
    //   1285: aload_0
    //   1286: getfield C : Lwtf/opal/d;
    //   1289: iconst_0
    //   1290: anewarray java/lang/Object
    //   1293: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1296: ifeq -> 1309
    //   1299: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   1302: goto -> 1312
    //   1305: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1308: athrow
    //   1309: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1312: aload #5
    //   1314: aload_0
    //   1315: getfield C : Lwtf/opal/d;
    //   1318: iconst_0
    //   1319: anewarray java/lang/Object
    //   1322: invokevirtual m : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1325: aload_0
    //   1326: getfield m : I
    //   1329: iconst_4
    //   1330: iadd
    //   1331: i2f
    //   1332: aload_0
    //   1333: getfield q : I
    //   1336: i2f
    //   1337: fload #39
    //   1339: fadd
    //   1340: ldc_w 8.5
    //   1343: iconst_m1
    //   1344: aload_0
    //   1345: getfield C : Lwtf/opal/d;
    //   1348: iconst_0
    //   1349: anewarray java/lang/Object
    //   1352: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1355: aload #34
    //   1357: lload_2
    //   1358: lconst_0
    //   1359: lcmp
    //   1360: iflt -> 1397
    //   1363: ifnonnull -> 1395
    //   1366: ifeq -> 1414
    //   1369: goto -> 1376
    //   1372: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1375: athrow
    //   1376: getstatic wtf/opal/j8.z : Lwtf/opal/ke;
    //   1379: invokevirtual z : ()Ljava/lang/Object;
    //   1382: checkcast java/lang/Boolean
    //   1385: invokevirtual booleanValue : ()Z
    //   1388: goto -> 1395
    //   1391: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1394: athrow
    //   1395: aload #34
    //   1397: ifnonnull -> 1411
    //   1400: ifeq -> 1414
    //   1403: goto -> 1410
    //   1406: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1409: athrow
    //   1410: iconst_1
    //   1411: goto -> 1415
    //   1414: iconst_0
    //   1415: istore #30
    //   1417: istore #31
    //   1419: fstore #32
    //   1421: fstore #33
    //   1423: lload #28
    //   1425: fload #33
    //   1427: fload #32
    //   1429: iload #31
    //   1431: iload #30
    //   1433: bipush #9
    //   1435: anewarray java/lang/Object
    //   1438: dup_x1
    //   1439: swap
    //   1440: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1443: bipush #8
    //   1445: swap
    //   1446: aastore
    //   1447: dup_x1
    //   1448: swap
    //   1449: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1452: bipush #7
    //   1454: swap
    //   1455: aastore
    //   1456: dup_x1
    //   1457: swap
    //   1458: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1461: bipush #6
    //   1463: swap
    //   1464: aastore
    //   1465: dup_x1
    //   1466: swap
    //   1467: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1470: iconst_5
    //   1471: swap
    //   1472: aastore
    //   1473: dup_x2
    //   1474: dup_x2
    //   1475: pop
    //   1476: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1479: iconst_4
    //   1480: swap
    //   1481: aastore
    //   1482: dup_x1
    //   1483: swap
    //   1484: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1487: iconst_3
    //   1488: swap
    //   1489: aastore
    //   1490: dup_x1
    //   1491: swap
    //   1492: iconst_2
    //   1493: swap
    //   1494: aastore
    //   1495: dup_x1
    //   1496: swap
    //   1497: iconst_1
    //   1498: swap
    //   1499: aastore
    //   1500: dup_x1
    //   1501: swap
    //   1502: iconst_0
    //   1503: swap
    //   1504: aastore
    //   1505: invokevirtual H : ([Ljava/lang/Object;)V
    //   1508: lload_2
    //   1509: lconst_0
    //   1510: lcmp
    //   1511: ifle -> 1564
    //   1514: aload #35
    //   1516: lload #8
    //   1518: iconst_1
    //   1519: anewarray java/lang/Object
    //   1522: dup_x2
    //   1523: dup_x2
    //   1524: pop
    //   1525: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1528: iconst_0
    //   1529: swap
    //   1530: aastore
    //   1531: invokevirtual S : ([Ljava/lang/Object;)Lwtf/opal/dv;
    //   1534: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   1537: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1540: ifeq -> 1564
    //   1543: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   1546: iconst_0
    //   1547: anewarray java/lang/Object
    //   1550: invokevirtual y : ([Ljava/lang/Object;)J
    //   1553: fconst_0
    //   1554: invokestatic nvgTextLetterSpacing : (JF)V
    //   1557: goto -> 1564
    //   1560: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1563: athrow
    //   1564: aload_0
    //   1565: getfield d : Lwtf/opal/dk;
    //   1568: aload_0
    //   1569: getfield C : Lwtf/opal/d;
    //   1572: iconst_0
    //   1573: anewarray java/lang/Object
    //   1576: invokevirtual R : ([Ljava/lang/Object;)Z
    //   1579: ifeq -> 1592
    //   1582: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   1585: goto -> 1595
    //   1588: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1591: athrow
    //   1592: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   1595: lload #12
    //   1597: dup2_x1
    //   1598: pop2
    //   1599: iconst_2
    //   1600: anewarray java/lang/Object
    //   1603: dup_x1
    //   1604: swap
    //   1605: iconst_1
    //   1606: swap
    //   1607: aastore
    //   1608: dup_x2
    //   1609: dup_x2
    //   1610: pop
    //   1611: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1614: iconst_0
    //   1615: swap
    //   1616: aastore
    //   1617: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   1620: pop
    //   1621: aload_0
    //   1622: getfield a : Ljava/util/List;
    //   1625: invokeinterface isEmpty : ()Z
    //   1630: lload_2
    //   1631: lconst_0
    //   1632: lcmp
    //   1633: iflt -> 1881
    //   1636: aload #34
    //   1638: ifnonnull -> 1881
    //   1641: ifne -> 1853
    //   1644: goto -> 1651
    //   1647: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1650: athrow
    //   1651: ldc_w ''
    //   1654: astore #40
    //   1656: getstatic wtf/opal/pr.M : Lwtf/opal/dc;
    //   1659: getstatic wtf/opal/pr.c : Ljava/lang/String;
    //   1662: lload #14
    //   1664: iconst_2
    //   1665: anewarray java/lang/Object
    //   1668: dup_x2
    //   1669: dup_x2
    //   1670: pop
    //   1671: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1674: iconst_1
    //   1675: swap
    //   1676: aastore
    //   1677: dup_x1
    //   1678: swap
    //   1679: iconst_0
    //   1680: swap
    //   1681: aastore
    //   1682: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   1685: astore #41
    //   1687: aload #41
    //   1689: lload #22
    //   1691: ldc_w ''
    //   1694: ldc_w 9.0
    //   1697: iconst_3
    //   1698: anewarray java/lang/Object
    //   1701: dup_x1
    //   1702: swap
    //   1703: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1706: iconst_2
    //   1707: swap
    //   1708: aastore
    //   1709: dup_x1
    //   1710: swap
    //   1711: iconst_1
    //   1712: swap
    //   1713: aastore
    //   1714: dup_x2
    //   1715: dup_x2
    //   1716: pop
    //   1717: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1720: iconst_0
    //   1721: swap
    //   1722: aastore
    //   1723: invokevirtual q : ([Ljava/lang/Object;)F
    //   1726: fstore #42
    //   1728: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   1731: aload_0
    //   1732: getfield d : Lwtf/opal/dk;
    //   1735: lload #16
    //   1737: iconst_1
    //   1738: anewarray java/lang/Object
    //   1741: dup_x2
    //   1742: dup_x2
    //   1743: pop
    //   1744: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1747: iconst_0
    //   1748: swap
    //   1749: aastore
    //   1750: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   1753: invokevirtual doubleValue : ()D
    //   1756: ldc2_w 180.0
    //   1759: dmul
    //   1760: aload_0
    //   1761: getfield m : I
    //   1764: aload_0
    //   1765: getfield O : I
    //   1768: iadd
    //   1769: i2f
    //   1770: fload #42
    //   1772: fconst_2
    //   1773: fdiv
    //   1774: fsub
    //   1775: ldc_w 8.0
    //   1778: fsub
    //   1779: aload_0
    //   1780: getfield q : I
    //   1783: i2f
    //   1784: fload #39
    //   1786: fadd
    //   1787: fload #42
    //   1789: ldc_w 10.0
    //   1792: aload #41
    //   1794: <illegal opcode> run : (Lwtf/opal/u2;)Ljava/lang/Runnable;
    //   1799: bipush #6
    //   1801: anewarray java/lang/Object
    //   1804: dup_x1
    //   1805: swap
    //   1806: iconst_5
    //   1807: swap
    //   1808: aastore
    //   1809: dup_x1
    //   1810: swap
    //   1811: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1814: iconst_4
    //   1815: swap
    //   1816: aastore
    //   1817: dup_x1
    //   1818: swap
    //   1819: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1822: iconst_3
    //   1823: swap
    //   1824: aastore
    //   1825: dup_x1
    //   1826: swap
    //   1827: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1830: iconst_2
    //   1831: swap
    //   1832: aastore
    //   1833: dup_x1
    //   1834: swap
    //   1835: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1838: iconst_1
    //   1839: swap
    //   1840: aastore
    //   1841: dup_x2
    //   1842: dup_x2
    //   1843: pop
    //   1844: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1847: iconst_0
    //   1848: swap
    //   1849: aastore
    //   1850: invokevirtual I : ([Ljava/lang/Object;)V
    //   1853: aload_0
    //   1854: fconst_0
    //   1855: putfield N : F
    //   1858: aload_0
    //   1859: aload #34
    //   1861: ifnonnull -> 1957
    //   1864: getfield C : Lwtf/opal/d;
    //   1867: iconst_0
    //   1868: anewarray java/lang/Object
    //   1871: invokevirtual R : ([Ljava/lang/Object;)Z
    //   1874: goto -> 1881
    //   1877: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1880: athrow
    //   1881: ifne -> 1934
    //   1884: aload_0
    //   1885: aload #34
    //   1887: lload_2
    //   1888: lconst_0
    //   1889: lcmp
    //   1890: ifle -> 1972
    //   1893: ifnonnull -> 1957
    //   1896: goto -> 1903
    //   1899: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1902: athrow
    //   1903: getfield d : Lwtf/opal/dk;
    //   1906: lload #26
    //   1908: iconst_1
    //   1909: anewarray java/lang/Object
    //   1912: dup_x2
    //   1913: dup_x2
    //   1914: pop
    //   1915: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1918: iconst_0
    //   1919: swap
    //   1920: aastore
    //   1921: invokevirtual H : ([Ljava/lang/Object;)Z
    //   1924: ifne -> 2054
    //   1927: goto -> 1934
    //   1930: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1933: athrow
    //   1934: aload_0
    //   1935: getfield a : Ljava/util/List;
    //   1938: aload_0
    //   1939: <illegal opcode> accept : (Lwtf/opal/pr;)Ljava/util/function/Consumer;
    //   1944: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   1949: aload_0
    //   1950: goto -> 1957
    //   1953: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1956: athrow
    //   1957: lload #24
    //   1959: iconst_1
    //   1960: anewarray java/lang/Object
    //   1963: dup_x2
    //   1964: dup_x2
    //   1965: pop
    //   1966: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1969: iconst_0
    //   1970: swap
    //   1971: aastore
    //   1972: invokevirtual S : ([Ljava/lang/Object;)[F
    //   1975: astore #40
    //   1977: getstatic wtf/opal/pr.h : Lwtf/opal/pa;
    //   1980: aload #40
    //   1982: iconst_0
    //   1983: faload
    //   1984: aload #40
    //   1986: iconst_1
    //   1987: faload
    //   1988: aload #40
    //   1990: iconst_2
    //   1991: faload
    //   1992: aload #40
    //   1994: iconst_3
    //   1995: faload
    //   1996: aload_0
    //   1997: aload #5
    //   1999: iload #7
    //   2001: iload #4
    //   2003: fload #6
    //   2005: <illegal opcode> run : (Lwtf/opal/pr;Lnet/minecraft/class_332;IIF)Ljava/lang/Runnable;
    //   2010: iconst_5
    //   2011: anewarray java/lang/Object
    //   2014: dup_x1
    //   2015: swap
    //   2016: iconst_4
    //   2017: swap
    //   2018: aastore
    //   2019: dup_x1
    //   2020: swap
    //   2021: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2024: iconst_3
    //   2025: swap
    //   2026: aastore
    //   2027: dup_x1
    //   2028: swap
    //   2029: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2032: iconst_2
    //   2033: swap
    //   2034: aastore
    //   2035: dup_x1
    //   2036: swap
    //   2037: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2040: iconst_1
    //   2041: swap
    //   2042: aastore
    //   2043: dup_x1
    //   2044: swap
    //   2045: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2048: iconst_0
    //   2049: swap
    //   2050: aastore
    //   2051: invokevirtual t : ([Ljava/lang/Object;)V
    //   2054: return
    // Exception table:
    //   from	to	target	type
    //   170	194	194	wtf/opal/x5
    //   201	245	248	wtf/opal/x5
    //   242	263	266	wtf/opal/x5
    //   270	278	281	wtf/opal/x5
    //   292	317	320	wtf/opal/x5
    //   314	346	349	wtf/opal/x5
    //   353	386	389	wtf/opal/x5
    //   356	398	398	wtf/opal/x5
    //   403	413	413	wtf/opal/x5
    //   582	607	610	wtf/opal/x5
    //   604	636	639	wtf/opal/x5
    //   643	676	679	wtf/opal/x5
    //   646	688	688	wtf/opal/x5
    //   693	703	703	wtf/opal/x5
    //   708	984	987	wtf/opal/x5
    //   991	1026	1026	wtf/opal/x5
    //   1031	1041	1041	wtf/opal/x5
    //   1156	1190	1193	wtf/opal/x5
    //   1187	1213	1216	wtf/opal/x5
    //   1282	1305	1305	wtf/opal/x5
    //   1312	1369	1372	wtf/opal/x5
    //   1366	1388	1391	wtf/opal/x5
    //   1395	1403	1406	wtf/opal/x5
    //   1423	1557	1560	wtf/opal/x5
    //   1564	1588	1588	wtf/opal/x5
    //   1595	1644	1647	wtf/opal/x5
    //   1853	1874	1877	wtf/opal/x5
    //   1881	1896	1899	wtf/opal/x5
    //   1884	1927	1930	wtf/opal/x5
    //   1903	1950	1953	wtf/opal/x5
  }
  
  public void e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #5
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Long
    //   28: invokevirtual longValue : ()J
    //   31: lstore #7
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore_2
    //   43: pop
    //   44: lload #7
    //   46: dup2
    //   47: ldc2_w 91173079021596
    //   50: lxor
    //   51: lstore #9
    //   53: dup2
    //   54: ldc2_w 14124922263942
    //   57: lxor
    //   58: dup2
    //   59: bipush #48
    //   61: lushr
    //   62: l2i
    //   63: istore #11
    //   65: dup2
    //   66: bipush #16
    //   68: lshl
    //   69: bipush #48
    //   71: lushr
    //   72: l2i
    //   73: istore #12
    //   75: dup2
    //   76: bipush #32
    //   78: lshl
    //   79: bipush #32
    //   81: lushr
    //   82: l2i
    //   83: istore #13
    //   85: pop2
    //   86: dup2
    //   87: ldc2_w 96325625196239
    //   90: lxor
    //   91: lstore #14
    //   93: pop2
    //   94: invokestatic g : ()[Lwtf/opal/d;
    //   97: astore #16
    //   99: aload_0
    //   100: getfield m : I
    //   103: i2f
    //   104: lload #9
    //   106: dup2_x1
    //   107: pop2
    //   108: aload_0
    //   109: getfield q : I
    //   112: i2f
    //   113: aload_0
    //   114: getfield O : I
    //   117: i2f
    //   118: aload_0
    //   119: getfield A : I
    //   122: i2f
    //   123: dload_3
    //   124: dload #5
    //   126: bipush #7
    //   128: anewarray java/lang/Object
    //   131: dup_x2
    //   132: dup_x2
    //   133: pop
    //   134: invokestatic valueOf : (D)Ljava/lang/Double;
    //   137: bipush #6
    //   139: swap
    //   140: aastore
    //   141: dup_x2
    //   142: dup_x2
    //   143: pop
    //   144: invokestatic valueOf : (D)Ljava/lang/Double;
    //   147: iconst_5
    //   148: swap
    //   149: aastore
    //   150: dup_x1
    //   151: swap
    //   152: invokestatic valueOf : (F)Ljava/lang/Float;
    //   155: iconst_4
    //   156: swap
    //   157: aastore
    //   158: dup_x1
    //   159: swap
    //   160: invokestatic valueOf : (F)Ljava/lang/Float;
    //   163: iconst_3
    //   164: swap
    //   165: aastore
    //   166: dup_x1
    //   167: swap
    //   168: invokestatic valueOf : (F)Ljava/lang/Float;
    //   171: iconst_2
    //   172: swap
    //   173: aastore
    //   174: dup_x1
    //   175: swap
    //   176: invokestatic valueOf : (F)Ljava/lang/Float;
    //   179: iconst_1
    //   180: swap
    //   181: aastore
    //   182: dup_x2
    //   183: dup_x2
    //   184: pop
    //   185: invokestatic valueOf : (J)Ljava/lang/Long;
    //   188: iconst_0
    //   189: swap
    //   190: aastore
    //   191: invokestatic Z : ([Ljava/lang/Object;)Z
    //   194: aload #16
    //   196: ifnonnull -> 415
    //   199: ifeq -> 392
    //   202: goto -> 209
    //   205: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   208: athrow
    //   209: iload_2
    //   210: aload #16
    //   212: lload #7
    //   214: lconst_0
    //   215: lcmp
    //   216: iflt -> 318
    //   219: ifnonnull -> 316
    //   222: goto -> 229
    //   225: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   228: athrow
    //   229: lload #7
    //   231: lconst_0
    //   232: lcmp
    //   233: iflt -> 309
    //   236: ifne -> 308
    //   239: goto -> 246
    //   242: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   245: athrow
    //   246: aload_0
    //   247: getfield C : Lwtf/opal/d;
    //   250: iload #11
    //   252: i2s
    //   253: iload #12
    //   255: i2s
    //   256: iload #13
    //   258: iconst_3
    //   259: anewarray java/lang/Object
    //   262: dup_x1
    //   263: swap
    //   264: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   267: iconst_2
    //   268: swap
    //   269: aastore
    //   270: dup_x1
    //   271: swap
    //   272: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   275: iconst_1
    //   276: swap
    //   277: aastore
    //   278: dup_x1
    //   279: swap
    //   280: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   283: iconst_0
    //   284: swap
    //   285: aastore
    //   286: invokevirtual D : ([Ljava/lang/Object;)V
    //   289: lload #7
    //   291: lconst_0
    //   292: lcmp
    //   293: iflt -> 392
    //   296: aload #16
    //   298: ifnull -> 392
    //   301: goto -> 308
    //   304: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   307: athrow
    //   308: iload_2
    //   309: goto -> 316
    //   312: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   315: athrow
    //   316: aload #16
    //   318: ifnonnull -> 415
    //   321: iconst_1
    //   322: if_icmpne -> 392
    //   325: goto -> 332
    //   328: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   331: athrow
    //   332: aload_0
    //   333: getfield a : Ljava/util/List;
    //   336: invokeinterface isEmpty : ()Z
    //   341: aload #16
    //   343: ifnonnull -> 415
    //   346: goto -> 353
    //   349: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   352: athrow
    //   353: ifne -> 392
    //   356: goto -> 363
    //   359: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   362: athrow
    //   363: aload_0
    //   364: getfield C : Lwtf/opal/d;
    //   367: lload #14
    //   369: iconst_1
    //   370: anewarray java/lang/Object
    //   373: dup_x2
    //   374: dup_x2
    //   375: pop
    //   376: invokestatic valueOf : (J)Ljava/lang/Long;
    //   379: iconst_0
    //   380: swap
    //   381: aastore
    //   382: invokevirtual I : ([Ljava/lang/Object;)V
    //   385: goto -> 392
    //   388: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   391: athrow
    //   392: aload_0
    //   393: aload #16
    //   395: ifnonnull -> 419
    //   398: getfield C : Lwtf/opal/d;
    //   401: iconst_0
    //   402: anewarray java/lang/Object
    //   405: invokevirtual R : ([Ljava/lang/Object;)Z
    //   408: goto -> 415
    //   411: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   414: athrow
    //   415: ifeq -> 436
    //   418: aload_0
    //   419: getfield a : Ljava/util/List;
    //   422: dload_3
    //   423: dload #5
    //   425: iload_2
    //   426: <illegal opcode> accept : (DDI)Ljava/util/function/Consumer;
    //   431: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   436: return
    // Exception table:
    //   from	to	target	type
    //   99	202	205	wtf/opal/x5
    //   199	222	225	wtf/opal/x5
    //   209	239	242	wtf/opal/x5
    //   229	301	304	wtf/opal/x5
    //   246	309	312	wtf/opal/x5
    //   316	325	328	wtf/opal/x5
    //   321	346	349	wtf/opal/x5
    //   332	356	359	wtf/opal/x5
    //   353	385	388	wtf/opal/x5
    //   392	408	411	wtf/opal/x5
  }
  
  public void c(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Double
    //   17: invokevirtual doubleValue : ()D
    //   20: dstore #5
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Double
    //   28: invokevirtual doubleValue : ()D
    //   31: dstore #7
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore_2
    //   43: pop
    //   44: invokestatic g : ()[Lwtf/opal/d;
    //   47: astore #9
    //   49: aload_0
    //   50: aload #9
    //   52: ifnonnull -> 83
    //   55: getfield C : Lwtf/opal/d;
    //   58: iconst_0
    //   59: anewarray java/lang/Object
    //   62: invokevirtual R : ([Ljava/lang/Object;)Z
    //   65: ifeq -> 101
    //   68: goto -> 75
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: aload_0
    //   76: goto -> 83
    //   79: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   82: athrow
    //   83: getfield a : Ljava/util/List;
    //   86: dload #5
    //   88: dload #7
    //   90: iload_2
    //   91: <illegal opcode> accept : (DDI)Ljava/util/function/Consumer;
    //   96: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   101: return
    // Exception table:
    //   from	to	target	type
    //   49	68	71	wtf/opal/x5
    //   55	76	79	wtf/opal/x5
  }
  
  public void d(Object[] paramArrayOfObject) {
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
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_3
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Long
    //   27: invokevirtual longValue : ()J
    //   30: lstore #4
    //   32: pop
    //   33: invokestatic g : ()[Lwtf/opal/d;
    //   36: astore #6
    //   38: aload_0
    //   39: aload #6
    //   41: ifnonnull -> 72
    //   44: getfield C : Lwtf/opal/d;
    //   47: iconst_0
    //   48: anewarray java/lang/Object
    //   51: invokevirtual R : ([Ljava/lang/Object;)Z
    //   54: ifeq -> 87
    //   57: goto -> 64
    //   60: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   63: athrow
    //   64: aload_0
    //   65: goto -> 72
    //   68: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   71: athrow
    //   72: getfield a : Ljava/util/List;
    //   75: iload_2
    //   76: iload_3
    //   77: <illegal opcode> accept : (CI)Ljava/util/function/Consumer;
    //   82: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   87: return
    // Exception table:
    //   from	to	target	type
    //   38	57	60	wtf/opal/x5
    //   44	65	68	wtf/opal/x5
  }
  
  public void o(Object[] paramArrayOfObject) {
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
    //   20: istore #5
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Integer
    //   28: invokevirtual intValue : ()I
    //   31: istore #4
    //   33: dup
    //   34: iconst_3
    //   35: aaload
    //   36: checkcast java/lang/Integer
    //   39: invokevirtual intValue : ()I
    //   42: istore #6
    //   44: pop
    //   45: invokestatic g : ()[Lwtf/opal/d;
    //   48: astore #7
    //   50: aload_0
    //   51: aload #7
    //   53: ifnonnull -> 84
    //   56: getfield C : Lwtf/opal/d;
    //   59: iconst_0
    //   60: anewarray java/lang/Object
    //   63: invokevirtual R : ([Ljava/lang/Object;)Z
    //   66: ifeq -> 103
    //   69: goto -> 76
    //   72: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   75: athrow
    //   76: aload_0
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: getfield a : Ljava/util/List;
    //   87: iload #5
    //   89: iload #4
    //   91: iload #6
    //   93: <illegal opcode> accept : (III)Ljava/util/function/Consumer;
    //   98: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   103: return
    // Exception table:
    //   from	to	target	type
    //   50	69	72	wtf/opal/x5
    //   56	77	80	wtf/opal/x5
  }
  
  public void w(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Double
    //   7: invokevirtual doubleValue : ()D
    //   10: dstore_2
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
    //   36: checkcast java/lang/Long
    //   39: invokevirtual longValue : ()J
    //   42: lstore #8
    //   44: dup
    //   45: iconst_4
    //   46: aaload
    //   47: checkcast java/lang/Double
    //   50: invokevirtual doubleValue : ()D
    //   53: dstore #10
    //   55: pop
    //   56: lload #8
    //   58: dup2
    //   59: ldc2_w 135758184867564
    //   62: lxor
    //   63: lstore #12
    //   65: dup2
    //   66: ldc2_w 86816486982881
    //   69: lxor
    //   70: lstore #14
    //   72: dup2
    //   73: ldc2_w 41339163857512
    //   76: lxor
    //   77: lstore #16
    //   79: pop2
    //   80: invokestatic g : ()[Lwtf/opal/d;
    //   83: astore #26
    //   85: aload_0
    //   86: aload #26
    //   88: ifnonnull -> 170
    //   91: getfield C : Lwtf/opal/d;
    //   94: iconst_0
    //   95: anewarray java/lang/Object
    //   98: invokevirtual R : ([Ljava/lang/Object;)Z
    //   101: ifne -> 162
    //   104: goto -> 111
    //   107: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   110: athrow
    //   111: aload_0
    //   112: aload #26
    //   114: lload #8
    //   116: lconst_0
    //   117: lcmp
    //   118: iflt -> 185
    //   121: ifnonnull -> 170
    //   124: goto -> 131
    //   127: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: getfield d : Lwtf/opal/dk;
    //   134: lload #16
    //   136: iconst_1
    //   137: anewarray java/lang/Object
    //   140: dup_x2
    //   141: dup_x2
    //   142: pop
    //   143: invokestatic valueOf : (J)Ljava/lang/Long;
    //   146: iconst_0
    //   147: swap
    //   148: aastore
    //   149: invokevirtual H : ([Ljava/lang/Object;)Z
    //   152: ifne -> 396
    //   155: goto -> 162
    //   158: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   161: athrow
    //   162: aload_0
    //   163: goto -> 170
    //   166: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   169: athrow
    //   170: lload #14
    //   172: iconst_1
    //   173: anewarray java/lang/Object
    //   176: dup_x2
    //   177: dup_x2
    //   178: pop
    //   179: invokestatic valueOf : (J)Ljava/lang/Long;
    //   182: iconst_0
    //   183: swap
    //   184: aastore
    //   185: invokevirtual S : ([Ljava/lang/Object;)[F
    //   188: astore #27
    //   190: aload #27
    //   192: iconst_0
    //   193: faload
    //   194: fstore #28
    //   196: aload #27
    //   198: iconst_1
    //   199: faload
    //   200: fstore #29
    //   202: aload #27
    //   204: iconst_2
    //   205: faload
    //   206: fstore #30
    //   208: aload #27
    //   210: iconst_3
    //   211: faload
    //   212: fstore #31
    //   214: fload #28
    //   216: lload #8
    //   218: lconst_0
    //   219: lcmp
    //   220: ifle -> 353
    //   223: fload #29
    //   225: aload #26
    //   227: ifnonnull -> 349
    //   230: fload #30
    //   232: fload #31
    //   234: dload_2
    //   235: dload #4
    //   237: dstore #18
    //   239: dstore #20
    //   241: fstore #22
    //   243: fstore #23
    //   245: fstore #24
    //   247: fstore #25
    //   249: lload #12
    //   251: fload #25
    //   253: fload #24
    //   255: fload #23
    //   257: fload #22
    //   259: dload #20
    //   261: dload #18
    //   263: bipush #7
    //   265: anewarray java/lang/Object
    //   268: dup_x2
    //   269: dup_x2
    //   270: pop
    //   271: invokestatic valueOf : (D)Ljava/lang/Double;
    //   274: bipush #6
    //   276: swap
    //   277: aastore
    //   278: dup_x2
    //   279: dup_x2
    //   280: pop
    //   281: invokestatic valueOf : (D)Ljava/lang/Double;
    //   284: iconst_5
    //   285: swap
    //   286: aastore
    //   287: dup_x1
    //   288: swap
    //   289: invokestatic valueOf : (F)Ljava/lang/Float;
    //   292: iconst_4
    //   293: swap
    //   294: aastore
    //   295: dup_x1
    //   296: swap
    //   297: invokestatic valueOf : (F)Ljava/lang/Float;
    //   300: iconst_3
    //   301: swap
    //   302: aastore
    //   303: dup_x1
    //   304: swap
    //   305: invokestatic valueOf : (F)Ljava/lang/Float;
    //   308: iconst_2
    //   309: swap
    //   310: aastore
    //   311: dup_x1
    //   312: swap
    //   313: invokestatic valueOf : (F)Ljava/lang/Float;
    //   316: iconst_1
    //   317: swap
    //   318: aastore
    //   319: dup_x2
    //   320: dup_x2
    //   321: pop
    //   322: invokestatic valueOf : (J)Ljava/lang/Long;
    //   325: iconst_0
    //   326: swap
    //   327: aastore
    //   328: invokestatic Z : ([Ljava/lang/Object;)Z
    //   331: ifeq -> 396
    //   334: aload_0
    //   335: getfield N : F
    //   338: fload #31
    //   340: fsub
    //   341: fconst_0
    //   342: goto -> 349
    //   345: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   348: athrow
    //   349: invokestatic max : (FF)F
    //   352: fneg
    //   353: fstore #32
    //   355: aload_0
    //   356: dup
    //   357: getfield L : F
    //   360: dload #10
    //   362: d2f
    //   363: ldc_w 50.0
    //   366: fmul
    //   367: fadd
    //   368: putfield L : F
    //   371: aload_0
    //   372: aload_0
    //   373: getfield L : F
    //   376: fload #32
    //   378: invokestatic max : (FF)F
    //   381: putfield L : F
    //   384: aload_0
    //   385: aload_0
    //   386: getfield L : F
    //   389: fconst_0
    //   390: invokestatic min : (FF)F
    //   393: putfield L : F
    //   396: return
    // Exception table:
    //   from	to	target	type
    //   85	104	107	wtf/opal/x5
    //   91	124	127	wtf/opal/x5
    //   111	155	158	wtf/opal/x5
    //   131	163	166	wtf/opal/x5
    //   249	342	345	wtf/opal/x5
  }
  
  public void u() {
    long l = b ^ 0x65027FFB2EEL;
    d[] arrayOfD = ld.g();
    try {
      if (arrayOfD == null)
        try {
          if (this.C.R(new Object[0])) {
          
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    this.a.forEach(kx::u);
  }
  
  private float[] S(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = b ^ l1;
    long l2 = l1 ^ 0x41856BC3E931L;
    (new float[4])[0] = this.m;
    (new float[4])[1] = (this.q + this.A);
    (new float[4])[2] = this.O;
    new Object[1];
    return new float[] { 0, 0, 0, this.N * this.d.P(new Object[] { Long.valueOf(l2) }).floatValue() };
  }
  
  public d D(Object[] paramArrayOfObject) {
    return this.C;
  }
  
  public float P(Object[] paramArrayOfObject) {
    return this.N;
  }
  
  public dk o(Object[] paramArrayOfObject) {
    return this.d;
  }
  
  public void B(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.m = i;
  }
  
  public void I(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.q = i;
  }
  
  public void U(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.O = i;
  }
  
  public void f(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.A = i;
  }
  
  public void k(Object[] paramArrayOfObject) {
    boolean bool = ((Boolean)paramArrayOfObject[0]).booleanValue();
    this.v = bool;
  }
  
  private static void lambda$keyPressed$8(int paramInt1, int paramInt2, int paramInt3, b5 paramb5) {
    long l1 = b ^ 0x4FB492614451L;
    long l2 = l1 ^ 0x7DD7341562A0L;
    (new Object[4])[3] = Integer.valueOf(paramInt3);
    (new Object[4])[2] = Integer.valueOf(paramInt2);
    (new Object[4])[1] = Integer.valueOf(paramInt1);
    new Object[4];
    paramb5.o(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$charTyped$7(char paramChar, int paramInt, b5 paramb5) {
    long l1 = b ^ 0x64A5DF1FF8A2L;
    long l2 = l1 ^ 0x2C45DC9C43CL;
    new Object[3];
    paramb5.d(new Object[] { null, null, Long.valueOf(l2), Integer.valueOf(paramInt), Integer.valueOf(paramChar) });
  }
  
  private static void lambda$mouseReleased$6(double paramDouble1, double paramDouble2, int paramInt, b5 paramb5) {
    long l1 = b ^ 0x1D80B22B7B6L;
    long l2 = l1 ^ 0x5121A5C91A87L;
    (new Object[4])[3] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[2] = Double.valueOf(paramDouble2);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble1);
    new Object[4];
    paramb5.c(new Object[] { Long.valueOf(l2) });
  }
  
  private static void lambda$mouseClicked$5(double paramDouble1, double paramDouble2, int paramInt, b5 paramb5) {
    long l1 = b ^ 0x52D6E03A3101L;
    long l2 = l1 ^ 0x7B40F3C5210CL;
    (new Object[4])[3] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[2] = Long.valueOf(l2);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble2);
    new Object[4];
    paramb5.e(new Object[] { Double.valueOf(paramDouble1) });
  }
  
  private void lambda$render$4(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: aload_0
    //   1: getfield a : Ljava/util/List;
    //   4: aload_1
    //   5: iload_2
    //   6: iload_3
    //   7: fload #4
    //   9: <illegal opcode> accept : (Lnet/minecraft/class_332;IIF)Ljava/util/function/Consumer;
    //   14: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   19: return
  }
  
  private static void lambda$render$3(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat, b5 paramb5) {
    long l1 = b ^ 0x2AB323E8FBC0L;
    long l2 = l1 ^ 0x24A5096AC585L;
    long l3 = l1 ^ 0x3FB50A9EFB45L;
    d[] arrayOfD = ld.g();
    try {
      if (arrayOfD == null)
        try {
          new Object[1];
          if (paramb5.h(new Object[0]).v(new Object[] { Long.valueOf(l2) }))
            return; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    (new Object[5])[4] = Float.valueOf(paramFloat);
    new Object[5];
    paramb5.g(new Object[] { null, null, null, Long.valueOf(l3), Integer.valueOf(paramInt2), Integer.valueOf(paramInt1), paramclass_332 });
  }
  
  private void lambda$render$2(b5 paramb5) {
    // Byte code:
    //   0: getstatic wtf/opal/pr.b : J
    //   3: ldc2_w 95527724403631
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 74672194851558
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 97821681677802
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 125322613619101
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic g : ()[Lwtf/opal/d;
    //   34: astore #10
    //   36: aload_1
    //   37: iconst_0
    //   38: anewarray java/lang/Object
    //   41: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/k3;
    //   44: lload #6
    //   46: iconst_1
    //   47: anewarray java/lang/Object
    //   50: dup_x2
    //   51: dup_x2
    //   52: pop
    //   53: invokestatic valueOf : (J)Ljava/lang/Long;
    //   56: iconst_0
    //   57: swap
    //   58: aastore
    //   59: invokevirtual v : ([Ljava/lang/Object;)Z
    //   62: aload #10
    //   64: ifnonnull -> 235
    //   67: ifeq -> 78
    //   70: goto -> 77
    //   73: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: return
    //   78: aload_0
    //   79: getfield X : Lwtf/opal/da;
    //   82: aload_0
    //   83: getfield L : F
    //   86: lload #4
    //   88: sipush #7799
    //   91: ldc2_w 5744375559460846047
    //   94: lload_2
    //   95: lxor
    //   96: <illegal opcode> g : (IJ)I
    //   101: iconst_3
    //   102: anewarray java/lang/Object
    //   105: dup_x1
    //   106: swap
    //   107: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   110: iconst_2
    //   111: swap
    //   112: aastore
    //   113: dup_x2
    //   114: dup_x2
    //   115: pop
    //   116: invokestatic valueOf : (J)Ljava/lang/Long;
    //   119: iconst_1
    //   120: swap
    //   121: aastore
    //   122: dup_x1
    //   123: swap
    //   124: invokestatic valueOf : (F)Ljava/lang/Float;
    //   127: iconst_0
    //   128: swap
    //   129: aastore
    //   130: invokevirtual e : ([Ljava/lang/Object;)V
    //   133: aload_1
    //   134: aload_0
    //   135: getfield m : I
    //   138: i2f
    //   139: iconst_1
    //   140: anewarray java/lang/Object
    //   143: dup_x1
    //   144: swap
    //   145: invokestatic valueOf : (F)Ljava/lang/Float;
    //   148: iconst_0
    //   149: swap
    //   150: aastore
    //   151: invokevirtual n : ([Ljava/lang/Object;)V
    //   154: aload_1
    //   155: aload_0
    //   156: getfield q : I
    //   159: aload_0
    //   160: getfield A : I
    //   163: iadd
    //   164: i2f
    //   165: aload_0
    //   166: getfield X : Lwtf/opal/da;
    //   169: lload #8
    //   171: iconst_1
    //   172: anewarray java/lang/Object
    //   175: dup_x2
    //   176: dup_x2
    //   177: pop
    //   178: invokestatic valueOf : (J)Ljava/lang/Long;
    //   181: iconst_0
    //   182: swap
    //   183: aastore
    //   184: invokevirtual s : ([Ljava/lang/Object;)F
    //   187: fadd
    //   188: aload_0
    //   189: getfield N : F
    //   192: fadd
    //   193: f2i
    //   194: i2f
    //   195: iconst_1
    //   196: anewarray java/lang/Object
    //   199: dup_x1
    //   200: swap
    //   201: invokestatic valueOf : (F)Ljava/lang/Float;
    //   204: iconst_0
    //   205: swap
    //   206: aastore
    //   207: invokevirtual Z : ([Ljava/lang/Object;)V
    //   210: aload_1
    //   211: aload_0
    //   212: getfield O : I
    //   215: i2f
    //   216: iconst_1
    //   217: anewarray java/lang/Object
    //   220: dup_x1
    //   221: swap
    //   222: invokestatic valueOf : (F)Ljava/lang/Float;
    //   225: iconst_0
    //   226: swap
    //   227: aastore
    //   228: invokevirtual S : ([Ljava/lang/Object;)V
    //   231: aload_1
    //   232: instanceof wtf/opal/be
    //   235: aload #10
    //   237: ifnonnull -> 342
    //   240: ifeq -> 319
    //   243: goto -> 250
    //   246: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: aload_0
    //   251: dup
    //   252: getfield N : F
    //   255: aload_0
    //   256: getfield A : I
    //   259: iconst_4
    //   260: isub
    //   261: i2f
    //   262: aload_1
    //   263: iconst_0
    //   264: anewarray java/lang/Object
    //   267: invokevirtual U : ([Ljava/lang/Object;)F
    //   270: fadd
    //   271: fadd
    //   272: putfield N : F
    //   275: aload_1
    //   276: aload_0
    //   277: getfield A : I
    //   280: iconst_4
    //   281: isub
    //   282: i2f
    //   283: aload_1
    //   284: iconst_0
    //   285: anewarray java/lang/Object
    //   288: invokevirtual U : ([Ljava/lang/Object;)F
    //   291: fadd
    //   292: iconst_1
    //   293: anewarray java/lang/Object
    //   296: dup_x1
    //   297: swap
    //   298: invokestatic valueOf : (F)Ljava/lang/Float;
    //   301: iconst_0
    //   302: swap
    //   303: aastore
    //   304: invokevirtual t : ([Ljava/lang/Object;)V
    //   307: aload #10
    //   309: ifnull -> 474
    //   312: goto -> 319
    //   315: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   318: athrow
    //   319: aload_1
    //   320: aload #10
    //   322: ifnonnull -> 445
    //   325: goto -> 332
    //   328: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   331: athrow
    //   332: instanceof wtf/opal/bq
    //   335: goto -> 342
    //   338: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   341: athrow
    //   342: ifeq -> 414
    //   345: aload_0
    //   346: dup
    //   347: getfield N : F
    //   350: aload_0
    //   351: getfield A : I
    //   354: iconst_2
    //   355: isub
    //   356: i2f
    //   357: aload_1
    //   358: iconst_0
    //   359: anewarray java/lang/Object
    //   362: invokevirtual U : ([Ljava/lang/Object;)F
    //   365: fadd
    //   366: fadd
    //   367: putfield N : F
    //   370: aload_1
    //   371: aload_0
    //   372: getfield A : I
    //   375: iconst_2
    //   376: isub
    //   377: i2f
    //   378: aload_1
    //   379: iconst_0
    //   380: anewarray java/lang/Object
    //   383: invokevirtual U : ([Ljava/lang/Object;)F
    //   386: fadd
    //   387: iconst_1
    //   388: anewarray java/lang/Object
    //   391: dup_x1
    //   392: swap
    //   393: invokestatic valueOf : (F)Ljava/lang/Float;
    //   396: iconst_0
    //   397: swap
    //   398: aastore
    //   399: invokevirtual t : ([Ljava/lang/Object;)V
    //   402: aload #10
    //   404: ifnull -> 474
    //   407: goto -> 414
    //   410: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   413: athrow
    //   414: aload_0
    //   415: dup
    //   416: getfield N : F
    //   419: aload_0
    //   420: getfield A : I
    //   423: i2f
    //   424: aload_1
    //   425: iconst_0
    //   426: anewarray java/lang/Object
    //   429: invokevirtual U : ([Ljava/lang/Object;)F
    //   432: fadd
    //   433: fadd
    //   434: putfield N : F
    //   437: aload_1
    //   438: goto -> 445
    //   441: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   444: athrow
    //   445: aload_0
    //   446: getfield A : I
    //   449: i2f
    //   450: aload_1
    //   451: iconst_0
    //   452: anewarray java/lang/Object
    //   455: invokevirtual U : ([Ljava/lang/Object;)F
    //   458: fadd
    //   459: iconst_1
    //   460: anewarray java/lang/Object
    //   463: dup_x1
    //   464: swap
    //   465: invokestatic valueOf : (F)Ljava/lang/Float;
    //   468: iconst_0
    //   469: swap
    //   470: aastore
    //   471: invokevirtual t : ([Ljava/lang/Object;)V
    //   474: return
    // Exception table:
    //   from	to	target	type
    //   36	70	73	wtf/opal/x5
    //   235	243	246	wtf/opal/x5
    //   240	312	315	wtf/opal/x5
    //   250	325	328	wtf/opal/x5
    //   319	335	338	wtf/opal/x5
    //   342	407	410	wtf/opal/x5
    //   345	438	441	wtf/opal/x5
  }
  
  private static void lambda$render$1(u2 paramu2) {
    // Byte code:
    //   0: getstatic wtf/opal/pr.b : J
    //   3: ldc2_w 109634717854218
    //   6: lxor
    //   7: lstore_1
    //   8: lload_1
    //   9: dup2
    //   10: ldc2_w 52882503177501
    //   13: lxor
    //   14: lstore_3
    //   15: pop2
    //   16: aload_0
    //   17: ldc_w ''
    //   20: fconst_0
    //   21: fconst_0
    //   22: ldc_w 9.0
    //   25: iconst_m1
    //   26: lload_3
    //   27: iconst_0
    //   28: sipush #21674
    //   31: ldc2_w 6972983879731827363
    //   34: lload_1
    //   35: lxor
    //   36: <illegal opcode> g : (IJ)I
    //   41: bipush #8
    //   43: anewarray java/lang/Object
    //   46: dup_x1
    //   47: swap
    //   48: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   51: bipush #7
    //   53: swap
    //   54: aastore
    //   55: dup_x1
    //   56: swap
    //   57: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   60: bipush #6
    //   62: swap
    //   63: aastore
    //   64: dup_x2
    //   65: dup_x2
    //   66: pop
    //   67: invokestatic valueOf : (J)Ljava/lang/Long;
    //   70: iconst_5
    //   71: swap
    //   72: aastore
    //   73: dup_x1
    //   74: swap
    //   75: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   78: iconst_4
    //   79: swap
    //   80: aastore
    //   81: dup_x1
    //   82: swap
    //   83: invokestatic valueOf : (F)Ljava/lang/Float;
    //   86: iconst_3
    //   87: swap
    //   88: aastore
    //   89: dup_x1
    //   90: swap
    //   91: invokestatic valueOf : (F)Ljava/lang/Float;
    //   94: iconst_2
    //   95: swap
    //   96: aastore
    //   97: dup_x1
    //   98: swap
    //   99: invokestatic valueOf : (F)Ljava/lang/Float;
    //   102: iconst_1
    //   103: swap
    //   104: aastore
    //   105: dup_x1
    //   106: swap
    //   107: iconst_0
    //   108: swap
    //   109: aastore
    //   110: invokevirtual e : ([Ljava/lang/Object;)V
    //   113: return
  }
  
  private void lambda$new$0(k3 paramk3) {
    // Byte code:
    //   0: getstatic wtf/opal/pr.b : J
    //   3: ldc2_w 129155377753458
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 87225442646131
    //   13: lxor
    //   14: dup2
    //   15: bipush #48
    //   17: lushr
    //   18: l2i
    //   19: istore #4
    //   21: dup2
    //   22: bipush #16
    //   24: lshl
    //   25: bipush #16
    //   27: lushr
    //   28: lstore #5
    //   30: pop2
    //   31: dup2
    //   32: ldc2_w 48920103343695
    //   35: lxor
    //   36: lstore #7
    //   38: dup2
    //   39: ldc2_w 90330224832133
    //   42: lxor
    //   43: lstore #9
    //   45: dup2
    //   46: ldc2_w 2479338698559
    //   49: lxor
    //   50: lstore #11
    //   52: dup2
    //   53: ldc2_w 34195394970645
    //   56: lxor
    //   57: lstore #13
    //   59: dup2
    //   60: ldc2_w 49145890369393
    //   63: lxor
    //   64: dup2
    //   65: bipush #32
    //   67: lushr
    //   68: l2i
    //   69: istore #15
    //   71: dup2
    //   72: bipush #32
    //   74: lshl
    //   75: bipush #48
    //   77: lushr
    //   78: l2i
    //   79: istore #16
    //   81: dup2
    //   82: bipush #48
    //   84: lshl
    //   85: bipush #48
    //   87: lushr
    //   88: l2i
    //   89: istore #17
    //   91: pop2
    //   92: pop2
    //   93: invokestatic g : ()[Lwtf/opal/d;
    //   96: astore #18
    //   98: aload_1
    //   99: instanceof wtf/opal/ke
    //   102: aload #18
    //   104: ifnonnull -> 151
    //   107: ifeq -> 147
    //   110: goto -> 117
    //   113: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   116: athrow
    //   117: aload_1
    //   118: checkcast wtf/opal/ke
    //   121: astore #19
    //   123: aload_0
    //   124: getfield a : Ljava/util/List;
    //   127: new wtf/opal/bq
    //   130: dup
    //   131: iload #4
    //   133: i2s
    //   134: lload #5
    //   136: aload #19
    //   138: invokespecial <init> : (SJLwtf/opal/ke;)V
    //   141: invokeinterface add : (Ljava/lang/Object;)Z
    //   146: pop
    //   147: aload_1
    //   148: instanceof wtf/opal/kt
    //   151: aload #18
    //   153: ifnonnull -> 202
    //   156: ifeq -> 198
    //   159: goto -> 166
    //   162: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: aload_1
    //   167: checkcast wtf/opal/kt
    //   170: astore #19
    //   172: aload_0
    //   173: getfield a : Ljava/util/List;
    //   176: new wtf/opal/be
    //   179: dup
    //   180: iload #15
    //   182: iload #16
    //   184: i2c
    //   185: iload #17
    //   187: aload #19
    //   189: invokespecial <init> : (ICILwtf/opal/kt;)V
    //   192: invokeinterface add : (Ljava/lang/Object;)Z
    //   197: pop
    //   198: aload_1
    //   199: instanceof wtf/opal/ky
    //   202: aload #18
    //   204: ifnonnull -> 248
    //   207: ifeq -> 244
    //   210: goto -> 217
    //   213: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   216: athrow
    //   217: aload_1
    //   218: checkcast wtf/opal/ky
    //   221: astore #19
    //   223: aload_0
    //   224: getfield a : Ljava/util/List;
    //   227: new wtf/opal/b_
    //   230: dup
    //   231: aload #19
    //   233: lload #9
    //   235: invokespecial <init> : (Lwtf/opal/ky;J)V
    //   238: invokeinterface add : (Ljava/lang/Object;)Z
    //   243: pop
    //   244: aload_1
    //   245: instanceof wtf/opal/kd
    //   248: aload #18
    //   250: ifnonnull -> 294
    //   253: ifeq -> 290
    //   256: goto -> 263
    //   259: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   262: athrow
    //   263: aload_1
    //   264: checkcast wtf/opal/kd
    //   267: astore #19
    //   269: aload_0
    //   270: getfield a : Ljava/util/List;
    //   273: new wtf/opal/b3
    //   276: dup
    //   277: aload #19
    //   279: lload #7
    //   281: invokespecial <init> : (Lwtf/opal/kd;J)V
    //   284: invokeinterface add : (Ljava/lang/Object;)Z
    //   289: pop
    //   290: aload_1
    //   291: instanceof wtf/opal/kg
    //   294: aload #18
    //   296: ifnonnull -> 352
    //   299: ifeq -> 336
    //   302: goto -> 309
    //   305: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   308: athrow
    //   309: aload_1
    //   310: checkcast wtf/opal/kg
    //   313: astore #19
    //   315: aload_0
    //   316: getfield a : Ljava/util/List;
    //   319: new wtf/opal/b4
    //   322: dup
    //   323: aload #19
    //   325: lload #11
    //   327: invokespecial <init> : (Lwtf/opal/kg;J)V
    //   330: invokeinterface add : (Ljava/lang/Object;)Z
    //   335: pop
    //   336: aload_1
    //   337: aload #18
    //   339: ifnonnull -> 356
    //   342: instanceof wtf/opal/kl
    //   345: goto -> 352
    //   348: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   351: athrow
    //   352: ifeq -> 382
    //   355: aload_1
    //   356: checkcast wtf/opal/kl
    //   359: astore #19
    //   361: aload_0
    //   362: getfield a : Ljava/util/List;
    //   365: new wtf/opal/bg
    //   368: dup
    //   369: lload #13
    //   371: aload #19
    //   373: invokespecial <init> : (JLwtf/opal/kl;)V
    //   376: invokeinterface add : (Ljava/lang/Object;)Z
    //   381: pop
    //   382: return
    // Exception table:
    //   from	to	target	type
    //   98	110	113	wtf/opal/x5
    //   151	159	162	wtf/opal/x5
    //   202	210	213	wtf/opal/x5
    //   248	256	259	wtf/opal/x5
    //   294	302	305	wtf/opal/x5
    //   336	345	348	wtf/opal/x5
  }
  
  static {
    long l = b ^ 0x5ECA88F6C6D5L;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1613;
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
        throw new RuntimeException("wtf/opal/pr", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   65: ldc_w 'wtf/opal/pr'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pr.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */