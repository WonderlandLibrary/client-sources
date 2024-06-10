package wtf.opal;

import java.awt.Color;
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
import net.minecraft.class_1309;

public final class ph extends u_<jb> {
  private final pa O = d1.q(new Object[0]).z(new Object[0]);
  
  private final dc o = d1.q(new Object[0]).D(new Object[0]);
  
  private final gm<uj> R = this::lambda$new$2;
  
  private static final long a = on.a(-5015896680924338355L, -8781512957294353757L, MethodHandles.lookup().lookupClass()).a(46768612354344L);
  
  private static final String b;
  
  private static final long[] c;
  
  private static final Integer[] d;
  
  private static final Map e;
  
  public ph(jb paramjb) {
    super(paramjb);
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xg.ASTOLFO;
  }
  
  private void lambda$new$2(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/ph.a : J
    //   3: ldc2_w 87155003356727
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 58294800014393
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 69855712878793
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 44122178742743
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 90015296668955
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 120952230015138
    //   41: lxor
    //   42: lstore #12
    //   44: pop2
    //   45: aload_0
    //   46: getfield G : Lwtf/opal/d;
    //   49: checkcast wtf/opal/jb
    //   52: lload #4
    //   54: iconst_1
    //   55: anewarray java/lang/Object
    //   58: dup_x2
    //   59: dup_x2
    //   60: pop
    //   61: invokestatic valueOf : (J)Ljava/lang/Long;
    //   64: iconst_0
    //   65: swap
    //   66: aastore
    //   67: invokevirtual E : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   70: astore #14
    //   72: aload #14
    //   74: ifnonnull -> 82
    //   77: return
    //   78: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: iconst_0
    //   83: anewarray java/lang/Object
    //   86: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   89: iconst_0
    //   90: anewarray java/lang/Object
    //   93: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   96: ldc wtf/opal/jt
    //   98: iconst_1
    //   99: anewarray java/lang/Object
    //   102: dup_x1
    //   103: swap
    //   104: iconst_0
    //   105: swap
    //   106: aastore
    //   107: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   110: checkcast wtf/opal/jt
    //   113: astore #15
    //   115: sipush #2159
    //   118: aload_0
    //   119: getfield G : Lwtf/opal/d;
    //   122: checkcast wtf/opal/jb
    //   125: getfield z : Lwtf/opal/k6;
    //   128: lload #12
    //   130: iconst_1
    //   131: anewarray java/lang/Object
    //   134: dup_x2
    //   135: dup_x2
    //   136: pop
    //   137: invokestatic valueOf : (J)Ljava/lang/Long;
    //   140: iconst_0
    //   141: swap
    //   142: aastore
    //   143: invokevirtual t : ([Ljava/lang/Object;)F
    //   146: fstore #16
    //   148: ldc2_w 5329817421214751762
    //   151: lload_2
    //   152: lxor
    //   153: aload_0
    //   154: getfield G : Lwtf/opal/d;
    //   157: checkcast wtf/opal/jb
    //   160: getfield z : Lwtf/opal/k6;
    //   163: iconst_0
    //   164: anewarray java/lang/Object
    //   167: invokevirtual x : ([Ljava/lang/Object;)F
    //   170: fstore #17
    //   172: aload_0
    //   173: getfield G : Lwtf/opal/d;
    //   176: checkcast wtf/opal/jb
    //   179: iconst_0
    //   180: anewarray java/lang/Object
    //   183: invokevirtual k : ([Ljava/lang/Object;)F
    //   186: fstore #18
    //   188: aload #14
    //   190: invokevirtual method_6063 : ()F
    //   193: aload #14
    //   195: invokevirtual method_52541 : ()F
    //   198: fadd
    //   199: fstore #19
    //   201: aload_0
    //   202: getfield G : Lwtf/opal/d;
    //   205: checkcast wtf/opal/jb
    //   208: getfield Q : Lwtf/opal/d2;
    //   211: lload #8
    //   213: iconst_1
    //   214: anewarray java/lang/Object
    //   217: dup_x2
    //   218: dup_x2
    //   219: pop
    //   220: invokestatic valueOf : (J)Ljava/lang/Long;
    //   223: iconst_0
    //   224: swap
    //   225: aastore
    //   226: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   229: invokevirtual floatValue : ()F
    //   232: fstore #20
    //   234: <illegal opcode> n : (IJ)I
    //   239: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   242: getfield field_1772 : Lnet/minecraft/class_327;
    //   245: aload #14
    //   247: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   250: invokevirtual method_27525 : (Lnet/minecraft/class_5348;)I
    //   253: sipush #4448
    //   256: ldc2_w 6909303348672824606
    //   259: lload_2
    //   260: lxor
    //   261: <illegal opcode> n : (IJ)I
    //   266: iadd
    //   267: invokestatic max : (II)I
    //   270: i2f
    //   271: fstore #21
    //   273: ldc 47.0
    //   275: fstore #22
    //   277: aload_0
    //   278: getfield G : Lwtf/opal/d;
    //   281: checkcast wtf/opal/jb
    //   284: getfield z : Lwtf/opal/k6;
    //   287: fload #21
    //   289: ldc 47.0
    //   291: iconst_2
    //   292: anewarray java/lang/Object
    //   295: dup_x1
    //   296: swap
    //   297: invokestatic valueOf : (F)Ljava/lang/Float;
    //   300: iconst_1
    //   301: swap
    //   302: aastore
    //   303: dup_x1
    //   304: swap
    //   305: invokestatic valueOf : (F)Ljava/lang/Float;
    //   308: iconst_0
    //   309: swap
    //   310: aastore
    //   311: invokevirtual Y : ([Ljava/lang/Object;)V
    //   314: aload_0
    //   315: getfield G : Lwtf/opal/d;
    //   318: checkcast wtf/opal/jb
    //   321: getfield z : Lwtf/opal/k6;
    //   324: ldc 3.0
    //   326: iconst_1
    //   327: anewarray java/lang/Object
    //   330: dup_x1
    //   331: swap
    //   332: invokestatic valueOf : (F)Ljava/lang/Float;
    //   335: iconst_0
    //   336: swap
    //   337: aastore
    //   338: invokevirtual U : ([Ljava/lang/Object;)V
    //   341: new java/awt/Color
    //   344: dup
    //   345: aload #15
    //   347: iconst_0
    //   348: anewarray java/lang/Object
    //   351: invokevirtual L : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   354: invokevirtual z : ()Ljava/lang/Object;
    //   357: checkcast java/lang/Integer
    //   360: invokevirtual intValue : ()I
    //   363: invokespecial <init> : (I)V
    //   366: astore #23
    //   368: new java/awt/Color
    //   371: dup
    //   372: aload #15
    //   374: iconst_0
    //   375: anewarray java/lang/Object
    //   378: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/kg;
    //   381: invokevirtual z : ()Ljava/lang/Object;
    //   384: checkcast java/lang/Integer
    //   387: invokevirtual intValue : ()I
    //   390: invokespecial <init> : (I)V
    //   393: astore #24
    //   395: aload_0
    //   396: getfield O : Lwtf/opal/pa;
    //   399: aload_0
    //   400: fload #20
    //   402: fload #16
    //   404: fload #17
    //   406: fload #21
    //   408: fload #18
    //   410: fload #19
    //   412: aload #14
    //   414: aload #23
    //   416: aload #24
    //   418: <illegal opcode> run : (Lwtf/opal/ph;FFFFFFLnet/minecraft/class_1309;Ljava/awt/Color;Ljava/awt/Color;)Ljava/lang/Runnable;
    //   423: lload #6
    //   425: iconst_2
    //   426: anewarray java/lang/Object
    //   429: dup_x2
    //   430: dup_x2
    //   431: pop
    //   432: invokestatic valueOf : (J)Ljava/lang/Long;
    //   435: iconst_1
    //   436: swap
    //   437: aastore
    //   438: dup_x1
    //   439: swap
    //   440: iconst_0
    //   441: swap
    //   442: aastore
    //   443: invokevirtual F : ([Ljava/lang/Object;)V
    //   446: aload_1
    //   447: iconst_0
    //   448: anewarray java/lang/Object
    //   451: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   454: astore #25
    //   456: aload #25
    //   458: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   461: invokevirtual method_22903 : ()V
    //   464: aload #25
    //   466: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   469: fload #20
    //   471: fload #20
    //   473: fconst_1
    //   474: invokevirtual method_22905 : (FFF)V
    //   477: iconst_0
    //   478: putstatic wtf/opal/bc.L : Z
    //   481: aload #25
    //   483: fload #16
    //   485: ldc_w 8.0
    //   488: fadd
    //   489: fload #20
    //   491: fdiv
    //   492: f2i
    //   493: fload #17
    //   495: ldc_w 5.0
    //   498: fadd
    //   499: fload #20
    //   501: fdiv
    //   502: f2i
    //   503: fload #16
    //   505: ldc_w 26.0
    //   508: fadd
    //   509: fload #20
    //   511: fdiv
    //   512: f2i
    //   513: fload #17
    //   515: ldc_w 45.0
    //   518: fadd
    //   519: fload #20
    //   521: fdiv
    //   522: f2i
    //   523: ldc_w 18.0
    //   526: fload #20
    //   528: fdiv
    //   529: f2i
    //   530: fconst_0
    //   531: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   534: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   537: invokevirtual method_4480 : ()I
    //   540: i2f
    //   541: ldc_w 0.21
    //   544: fmul
    //   545: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   548: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   551: invokevirtual method_4507 : ()I
    //   554: i2f
    //   555: ldc_w 0.42
    //   558: fmul
    //   559: aload #14
    //   561: invokestatic method_2486 : (Lnet/minecraft/class_332;IIIIIFFFLnet/minecraft/class_1309;)V
    //   564: iconst_1
    //   565: putstatic wtf/opal/bc.L : Z
    //   568: aload #25
    //   570: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   573: getfield field_1772 : Lnet/minecraft/class_327;
    //   576: aload #14
    //   578: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   581: invokeinterface getString : ()Ljava/lang/String;
    //   586: fload #16
    //   588: ldc_w 34.0
    //   591: fadd
    //   592: fload #20
    //   594: fdiv
    //   595: f2i
    //   596: fload #17
    //   598: ldc_w 4.0
    //   601: fadd
    //   602: fload #20
    //   604: fdiv
    //   605: f2i
    //   606: iconst_m1
    //   607: invokevirtual method_25303 : (Lnet/minecraft/class_327;Ljava/lang/String;III)I
    //   610: pop
    //   611: aload #25
    //   613: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   616: invokevirtual method_22909 : ()V
    //   619: ldc_w 1.75
    //   622: fload #20
    //   624: fmul
    //   625: fstore #26
    //   627: aload #25
    //   629: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   632: fload #26
    //   634: fload #26
    //   636: fconst_1
    //   637: invokevirtual method_22905 : (FFF)V
    //   640: aload #25
    //   642: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   645: getfield field_1772 : Lnet/minecraft/class_327;
    //   648: getstatic wtf/opal/b9.n : Ljava/text/DecimalFormat;
    //   651: aload #14
    //   653: invokevirtual method_6032 : ()F
    //   656: aload #14
    //   658: invokevirtual method_6067 : ()F
    //   661: fadd
    //   662: f2d
    //   663: invokevirtual format : (D)Ljava/lang/String;
    //   666: getstatic wtf/opal/ph.b : Ljava/lang/String;
    //   669: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   674: fload #16
    //   676: ldc_w 34.0
    //   679: fadd
    //   680: fload #26
    //   682: fdiv
    //   683: f2i
    //   684: fload #17
    //   686: ldc_w 17.0
    //   689: fadd
    //   690: fload #26
    //   692: fdiv
    //   693: f2i
    //   694: aload #23
    //   696: invokevirtual getRGB : ()I
    //   699: invokevirtual method_25303 : (Lnet/minecraft/class_327;Ljava/lang/String;III)I
    //   702: pop
    //   703: aload #25
    //   705: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   708: fconst_1
    //   709: fload #26
    //   711: fdiv
    //   712: fconst_1
    //   713: fload #26
    //   715: fdiv
    //   716: fconst_1
    //   717: invokevirtual method_22905 : (FFF)V
    //   720: aload_0
    //   721: getfield G : Lwtf/opal/d;
    //   724: checkcast wtf/opal/jb
    //   727: lload #10
    //   729: iconst_1
    //   730: anewarray java/lang/Object
    //   733: dup_x2
    //   734: dup_x2
    //   735: pop
    //   736: invokestatic valueOf : (J)Ljava/lang/Long;
    //   739: iconst_0
    //   740: swap
    //   741: aastore
    //   742: invokevirtual x : ([Ljava/lang/Object;)V
    //   745: return
    // Exception table:
    //   from	to	target	type
    //   72	78	78	wtf/opal/x5
  }
  
  private void lambda$new$1(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, class_1309 paramclass_1309, Color paramColor1, Color paramColor2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield O : Lwtf/opal/pa;
    //   4: fload_1
    //   5: fload_2
    //   6: fload_3
    //   7: fload #4
    //   9: ldc 47.0
    //   11: aload_0
    //   12: fload_2
    //   13: fload_3
    //   14: fload #4
    //   16: fload #5
    //   18: fload #6
    //   20: aload #7
    //   22: aload #8
    //   24: aload #9
    //   26: <illegal opcode> run : (Lwtf/opal/ph;FFFFFLnet/minecraft/class_1309;Ljava/awt/Color;Ljava/awt/Color;)Ljava/lang/Runnable;
    //   31: bipush #6
    //   33: anewarray java/lang/Object
    //   36: dup_x1
    //   37: swap
    //   38: iconst_5
    //   39: swap
    //   40: aastore
    //   41: dup_x1
    //   42: swap
    //   43: invokestatic valueOf : (F)Ljava/lang/Float;
    //   46: iconst_4
    //   47: swap
    //   48: aastore
    //   49: dup_x1
    //   50: swap
    //   51: invokestatic valueOf : (F)Ljava/lang/Float;
    //   54: iconst_3
    //   55: swap
    //   56: aastore
    //   57: dup_x1
    //   58: swap
    //   59: invokestatic valueOf : (F)Ljava/lang/Float;
    //   62: iconst_2
    //   63: swap
    //   64: aastore
    //   65: dup_x1
    //   66: swap
    //   67: invokestatic valueOf : (F)Ljava/lang/Float;
    //   70: iconst_1
    //   71: swap
    //   72: aastore
    //   73: dup_x1
    //   74: swap
    //   75: invokestatic valueOf : (F)Ljava/lang/Float;
    //   78: iconst_0
    //   79: swap
    //   80: aastore
    //   81: invokevirtual r : ([Ljava/lang/Object;)V
    //   84: return
  }
  
  private void lambda$new$0(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, class_1309 paramclass_1309, Color paramColor1, Color paramColor2) {
    // Byte code:
    //   0: getstatic wtf/opal/ph.a : J
    //   3: ldc2_w 8138203012327
    //   6: lxor
    //   7: lstore #9
    //   9: lload #9
    //   11: dup2
    //   12: ldc2_w 43853195940114
    //   15: lxor
    //   16: lstore #11
    //   18: dup2
    //   19: ldc2_w 86404559899109
    //   22: lxor
    //   23: lstore #13
    //   25: pop2
    //   26: aload_0
    //   27: getfield O : Lwtf/opal/pa;
    //   30: fload_1
    //   31: fload_2
    //   32: fload_3
    //   33: ldc 47.0
    //   35: ldc 3.0
    //   37: sipush #212
    //   40: ldc2_w 8095769550737112699
    //   43: lload #9
    //   45: lxor
    //   46: <illegal opcode> n : (IJ)I
    //   51: ldc_w 0.6
    //   54: iconst_2
    //   55: anewarray java/lang/Object
    //   58: dup_x1
    //   59: swap
    //   60: invokestatic valueOf : (F)Ljava/lang/Float;
    //   63: iconst_1
    //   64: swap
    //   65: aastore
    //   66: dup_x1
    //   67: swap
    //   68: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   71: iconst_0
    //   72: swap
    //   73: aastore
    //   74: invokestatic X : ([Ljava/lang/Object;)I
    //   77: lload #13
    //   79: bipush #7
    //   81: anewarray java/lang/Object
    //   84: dup_x2
    //   85: dup_x2
    //   86: pop
    //   87: invokestatic valueOf : (J)Ljava/lang/Long;
    //   90: bipush #6
    //   92: swap
    //   93: aastore
    //   94: dup_x1
    //   95: swap
    //   96: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   99: iconst_5
    //   100: swap
    //   101: aastore
    //   102: dup_x1
    //   103: swap
    //   104: invokestatic valueOf : (F)Ljava/lang/Float;
    //   107: iconst_4
    //   108: swap
    //   109: aastore
    //   110: dup_x1
    //   111: swap
    //   112: invokestatic valueOf : (F)Ljava/lang/Float;
    //   115: iconst_3
    //   116: swap
    //   117: aastore
    //   118: dup_x1
    //   119: swap
    //   120: invokestatic valueOf : (F)Ljava/lang/Float;
    //   123: iconst_2
    //   124: swap
    //   125: aastore
    //   126: dup_x1
    //   127: swap
    //   128: invokestatic valueOf : (F)Ljava/lang/Float;
    //   131: iconst_1
    //   132: swap
    //   133: aastore
    //   134: dup_x1
    //   135: swap
    //   136: invokestatic valueOf : (F)Ljava/lang/Float;
    //   139: iconst_0
    //   140: swap
    //   141: aastore
    //   142: invokevirtual M : ([Ljava/lang/Object;)V
    //   145: fconst_0
    //   146: fload_3
    //   147: ldc_w 34.0
    //   150: fsub
    //   151: invokestatic max : (FF)F
    //   154: fstore #15
    //   156: fload #15
    //   158: fload #4
    //   160: fload #5
    //   162: fdiv
    //   163: fmul
    //   164: fstore #16
    //   166: fload #15
    //   168: aload #6
    //   170: invokevirtual method_6032 : ()F
    //   173: aload #6
    //   175: invokevirtual method_6067 : ()F
    //   178: fadd
    //   179: fload #5
    //   181: fdiv
    //   182: fmul
    //   183: fstore #17
    //   185: aload_0
    //   186: getfield O : Lwtf/opal/pa;
    //   189: fload_1
    //   190: ldc_w 32.0
    //   193: fadd
    //   194: fload_2
    //   195: ldc_w 34.0
    //   198: fadd
    //   199: fload #15
    //   201: ldc_w 4.0
    //   204: fsub
    //   205: ldc_w 7.0
    //   208: aload #7
    //   210: invokevirtual darker : ()Ljava/awt/Color;
    //   213: invokevirtual darker : ()Ljava/awt/Color;
    //   216: invokevirtual darker : ()Ljava/awt/Color;
    //   219: invokevirtual darker : ()Ljava/awt/Color;
    //   222: invokevirtual getRGB : ()I
    //   225: aload #8
    //   227: invokevirtual darker : ()Ljava/awt/Color;
    //   230: invokevirtual darker : ()Ljava/awt/Color;
    //   233: invokevirtual darker : ()Ljava/awt/Color;
    //   236: invokevirtual darker : ()Ljava/awt/Color;
    //   239: invokevirtual getRGB : ()I
    //   242: lload #11
    //   244: dup2_x1
    //   245: pop2
    //   246: ldc_w 180.0
    //   249: bipush #8
    //   251: anewarray java/lang/Object
    //   254: dup_x1
    //   255: swap
    //   256: invokestatic valueOf : (F)Ljava/lang/Float;
    //   259: bipush #7
    //   261: swap
    //   262: aastore
    //   263: dup_x1
    //   264: swap
    //   265: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   268: bipush #6
    //   270: swap
    //   271: aastore
    //   272: dup_x2
    //   273: dup_x2
    //   274: pop
    //   275: invokestatic valueOf : (J)Ljava/lang/Long;
    //   278: iconst_5
    //   279: swap
    //   280: aastore
    //   281: dup_x1
    //   282: swap
    //   283: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   286: iconst_4
    //   287: swap
    //   288: aastore
    //   289: dup_x1
    //   290: swap
    //   291: invokestatic valueOf : (F)Ljava/lang/Float;
    //   294: iconst_3
    //   295: swap
    //   296: aastore
    //   297: dup_x1
    //   298: swap
    //   299: invokestatic valueOf : (F)Ljava/lang/Float;
    //   302: iconst_2
    //   303: swap
    //   304: aastore
    //   305: dup_x1
    //   306: swap
    //   307: invokestatic valueOf : (F)Ljava/lang/Float;
    //   310: iconst_1
    //   311: swap
    //   312: aastore
    //   313: dup_x1
    //   314: swap
    //   315: invokestatic valueOf : (F)Ljava/lang/Float;
    //   318: iconst_0
    //   319: swap
    //   320: aastore
    //   321: invokevirtual Q : ([Ljava/lang/Object;)V
    //   324: aload_0
    //   325: getfield O : Lwtf/opal/pa;
    //   328: fload_1
    //   329: ldc_w 32.0
    //   332: fadd
    //   333: fload_2
    //   334: ldc_w 34.0
    //   337: fadd
    //   338: fload #16
    //   340: ldc_w 4.0
    //   343: fsub
    //   344: ldc_w 7.0
    //   347: aload #7
    //   349: invokevirtual darker : ()Ljava/awt/Color;
    //   352: invokevirtual darker : ()Ljava/awt/Color;
    //   355: invokevirtual getRGB : ()I
    //   358: aload #8
    //   360: invokevirtual darker : ()Ljava/awt/Color;
    //   363: invokevirtual darker : ()Ljava/awt/Color;
    //   366: invokevirtual getRGB : ()I
    //   369: lload #11
    //   371: dup2_x1
    //   372: pop2
    //   373: ldc_w 180.0
    //   376: bipush #8
    //   378: anewarray java/lang/Object
    //   381: dup_x1
    //   382: swap
    //   383: invokestatic valueOf : (F)Ljava/lang/Float;
    //   386: bipush #7
    //   388: swap
    //   389: aastore
    //   390: dup_x1
    //   391: swap
    //   392: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   395: bipush #6
    //   397: swap
    //   398: aastore
    //   399: dup_x2
    //   400: dup_x2
    //   401: pop
    //   402: invokestatic valueOf : (J)Ljava/lang/Long;
    //   405: iconst_5
    //   406: swap
    //   407: aastore
    //   408: dup_x1
    //   409: swap
    //   410: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   413: iconst_4
    //   414: swap
    //   415: aastore
    //   416: dup_x1
    //   417: swap
    //   418: invokestatic valueOf : (F)Ljava/lang/Float;
    //   421: iconst_3
    //   422: swap
    //   423: aastore
    //   424: dup_x1
    //   425: swap
    //   426: invokestatic valueOf : (F)Ljava/lang/Float;
    //   429: iconst_2
    //   430: swap
    //   431: aastore
    //   432: dup_x1
    //   433: swap
    //   434: invokestatic valueOf : (F)Ljava/lang/Float;
    //   437: iconst_1
    //   438: swap
    //   439: aastore
    //   440: dup_x1
    //   441: swap
    //   442: invokestatic valueOf : (F)Ljava/lang/Float;
    //   445: iconst_0
    //   446: swap
    //   447: aastore
    //   448: invokevirtual Q : ([Ljava/lang/Object;)V
    //   451: aload_0
    //   452: getfield O : Lwtf/opal/pa;
    //   455: fload_1
    //   456: ldc_w 32.0
    //   459: fadd
    //   460: fload_2
    //   461: ldc_w 34.0
    //   464: fadd
    //   465: fload #15
    //   467: fload #17
    //   469: invokestatic min : (FF)F
    //   472: ldc_w 4.0
    //   475: fsub
    //   476: ldc_w 7.0
    //   479: aload #7
    //   481: invokevirtual getRGB : ()I
    //   484: aload #8
    //   486: invokevirtual getRGB : ()I
    //   489: lload #11
    //   491: dup2_x1
    //   492: pop2
    //   493: ldc_w 180.0
    //   496: bipush #8
    //   498: anewarray java/lang/Object
    //   501: dup_x1
    //   502: swap
    //   503: invokestatic valueOf : (F)Ljava/lang/Float;
    //   506: bipush #7
    //   508: swap
    //   509: aastore
    //   510: dup_x1
    //   511: swap
    //   512: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   515: bipush #6
    //   517: swap
    //   518: aastore
    //   519: dup_x2
    //   520: dup_x2
    //   521: pop
    //   522: invokestatic valueOf : (J)Ljava/lang/Long;
    //   525: iconst_5
    //   526: swap
    //   527: aastore
    //   528: dup_x1
    //   529: swap
    //   530: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   533: iconst_4
    //   534: swap
    //   535: aastore
    //   536: dup_x1
    //   537: swap
    //   538: invokestatic valueOf : (F)Ljava/lang/Float;
    //   541: iconst_3
    //   542: swap
    //   543: aastore
    //   544: dup_x1
    //   545: swap
    //   546: invokestatic valueOf : (F)Ljava/lang/Float;
    //   549: iconst_2
    //   550: swap
    //   551: aastore
    //   552: dup_x1
    //   553: swap
    //   554: invokestatic valueOf : (F)Ljava/lang/Float;
    //   557: iconst_1
    //   558: swap
    //   559: aastore
    //   560: dup_x1
    //   561: swap
    //   562: invokestatic valueOf : (F)Ljava/lang/Float;
    //   565: iconst_0
    //   566: swap
    //   567: aastore
    //   568: invokevirtual Q : ([Ljava/lang/Object;)V
    //   571: return
  }
  
  static {
    long l = a ^ 0x49ABD1E3A040L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1E85;
    if (d[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])e.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/ph", exception);
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
    //   66: ldc_w 'wtf/opal/ph'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\ph.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */