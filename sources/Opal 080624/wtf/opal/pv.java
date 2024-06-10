package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class pv extends u_<xw> {
  private int Z;
  
  private int Y;
  
  private int c;
  
  private final List<class_2596<?>> j = new CopyOnWriteArrayList<>();
  
  private final gm<u0> b;
  
  private static boolean T;
  
  private static final long a;
  
  private static final long[] d;
  
  private static final Integer[] e;
  
  private static final Map f;
  
  public pv(long paramLong, xw paramxw) {
    super(paramxw);
    boolean bool = D();
    try {
      this.b = this::lambda$new$0;
      if (bool)
        d.p(new d[3]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l1.WATCHDOG;
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/pv.a : J
    //   3: ldc2_w 1776331269200
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 49627529298227
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic D : ()Z
    //   20: istore #6
    //   22: aload_1
    //   23: iconst_0
    //   24: anewarray java/lang/Object
    //   27: invokevirtual K : ([Ljava/lang/Object;)Z
    //   30: iload #6
    //   32: ifne -> 75
    //   35: ifne -> 90
    //   38: goto -> 45
    //   41: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: aload_0
    //   46: iconst_0
    //   47: anewarray java/lang/Object
    //   50: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   53: checkcast wtf/opal/xw
    //   56: getfield B : Lwtf/opal/ke;
    //   59: invokevirtual z : ()Ljava/lang/Object;
    //   62: checkcast java/lang/Boolean
    //   65: invokevirtual booleanValue : ()Z
    //   68: goto -> 75
    //   71: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: iload #6
    //   77: ifne -> 103
    //   80: ifne -> 91
    //   83: goto -> 90
    //   86: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: return
    //   91: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   94: getfield field_1690 : Lnet/minecraft/class_315;
    //   97: getfield field_1903 : Lnet/minecraft/class_304;
    //   100: invokevirtual method_1434 : ()Z
    //   103: iload #6
    //   105: ifne -> 452
    //   108: ifeq -> 414
    //   111: goto -> 118
    //   114: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: aload_0
    //   119: iconst_0
    //   120: putfield Z : I
    //   123: aload_0
    //   124: dup
    //   125: getfield Y : I
    //   128: iconst_1
    //   129: iadd
    //   130: putfield Y : I
    //   133: aload_0
    //   134: dup
    //   135: getfield c : I
    //   138: iconst_1
    //   139: iadd
    //   140: putfield c : I
    //   143: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   146: getfield field_1724 : Lnet/minecraft/class_746;
    //   149: invokevirtual method_24828 : ()Z
    //   152: iload #6
    //   154: ifne -> 190
    //   157: goto -> 164
    //   160: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   163: athrow
    //   164: ifeq -> 186
    //   167: goto -> 174
    //   170: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   173: athrow
    //   174: aload_0
    //   175: iconst_0
    //   176: putfield c : I
    //   179: goto -> 186
    //   182: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   185: athrow
    //   186: aload_0
    //   187: getfield c : I
    //   190: iload #6
    //   192: ifne -> 252
    //   195: ifne -> 248
    //   198: goto -> 205
    //   201: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   204: athrow
    //   205: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   208: getfield field_1724 : Lnet/minecraft/class_746;
    //   211: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   214: getfield field_1724 : Lnet/minecraft/class_746;
    //   217: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   220: invokevirtual method_10216 : ()D
    //   223: ldc2_w 0.41999998688697815
    //   226: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   229: getfield field_1724 : Lnet/minecraft/class_746;
    //   232: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   235: invokevirtual method_10215 : ()D
    //   238: invokevirtual method_18800 : (DDD)V
    //   241: goto -> 248
    //   244: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: aload_0
    //   249: getfield c : I
    //   252: iconst_1
    //   253: iload #6
    //   255: ifne -> 316
    //   258: if_icmpne -> 311
    //   261: goto -> 268
    //   264: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   267: athrow
    //   268: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   271: getfield field_1724 : Lnet/minecraft/class_746;
    //   274: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   277: getfield field_1724 : Lnet/minecraft/class_746;
    //   280: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   283: invokevirtual method_10216 : ()D
    //   286: ldc2_w 0.33
    //   289: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   292: getfield field_1724 : Lnet/minecraft/class_746;
    //   295: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   298: invokevirtual method_10215 : ()D
    //   301: invokevirtual method_18800 : (DDD)V
    //   304: goto -> 311
    //   307: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: aload_0
    //   312: getfield c : I
    //   315: iconst_2
    //   316: iload #6
    //   318: ifne -> 401
    //   321: if_icmpne -> 384
    //   324: goto -> 331
    //   327: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   330: athrow
    //   331: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   334: getfield field_1724 : Lnet/minecraft/class_746;
    //   337: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   340: getfield field_1724 : Lnet/minecraft/class_746;
    //   343: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   346: invokevirtual method_10216 : ()D
    //   349: dconst_1
    //   350: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   353: getfield field_1724 : Lnet/minecraft/class_746;
    //   356: invokevirtual method_23318 : ()D
    //   359: dconst_1
    //   360: drem
    //   361: dsub
    //   362: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   365: getfield field_1724 : Lnet/minecraft/class_746;
    //   368: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   371: invokevirtual method_10215 : ()D
    //   374: invokevirtual method_18800 : (DDD)V
    //   377: goto -> 384
    //   380: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   383: athrow
    //   384: aload_0
    //   385: iload #6
    //   387: ifne -> 405
    //   390: getfield c : I
    //   393: iconst_2
    //   394: goto -> 401
    //   397: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   400: athrow
    //   401: if_icmplt -> 579
    //   404: aload_0
    //   405: iconst_m1
    //   406: putfield c : I
    //   409: iload #6
    //   411: ifeq -> 579
    //   414: aload_0
    //   415: sipush #27937
    //   418: ldc2_w 9056993041461742661
    //   421: lload_2
    //   422: lxor
    //   423: <illegal opcode> x : (IJ)I
    //   428: putfield c : I
    //   431: aload_0
    //   432: dup
    //   433: getfield Z : I
    //   436: iconst_1
    //   437: iadd
    //   438: putfield Z : I
    //   441: aload_0
    //   442: getfield Z : I
    //   445: goto -> 452
    //   448: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   451: athrow
    //   452: sipush #2069
    //   455: ldc2_w 107155188877093235
    //   458: lload_2
    //   459: lxor
    //   460: <illegal opcode> x : (IJ)I
    //   465: iload #6
    //   467: ifne -> 571
    //   470: if_icmpge -> 542
    //   473: goto -> 480
    //   476: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   479: athrow
    //   480: aload_0
    //   481: getfield Y : I
    //   484: iconst_5
    //   485: iload #6
    //   487: ifne -> 571
    //   490: goto -> 497
    //   493: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   496: athrow
    //   497: if_icmple -> 542
    //   500: goto -> 507
    //   503: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   506: athrow
    //   507: lload #4
    //   509: dconst_0
    //   510: iconst_2
    //   511: anewarray java/lang/Object
    //   514: dup_x2
    //   515: dup_x2
    //   516: pop
    //   517: invokestatic valueOf : (D)Ljava/lang/Double;
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
    //   532: invokestatic k : ([Ljava/lang/Object;)V
    //   535: goto -> 542
    //   538: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   541: athrow
    //   542: aload_0
    //   543: iload #6
    //   545: ifne -> 575
    //   548: getfield Z : I
    //   551: sipush #21030
    //   554: ldc2_w 8049700429888795457
    //   557: lload_2
    //   558: lxor
    //   559: <illegal opcode> x : (IJ)I
    //   564: goto -> 571
    //   567: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   570: athrow
    //   571: if_icmplt -> 579
    //   574: aload_0
    //   575: iconst_0
    //   576: putfield Y : I
    //   579: return
    // Exception table:
    //   from	to	target	type
    //   22	38	41	wtf/opal/x5
    //   35	68	71	wtf/opal/x5
    //   75	83	86	wtf/opal/x5
    //   103	111	114	wtf/opal/x5
    //   108	157	160	wtf/opal/x5
    //   118	167	170	wtf/opal/x5
    //   164	179	182	wtf/opal/x5
    //   190	198	201	wtf/opal/x5
    //   195	241	244	wtf/opal/x5
    //   252	261	264	wtf/opal/x5
    //   258	304	307	wtf/opal/x5
    //   316	324	327	wtf/opal/x5
    //   321	377	380	wtf/opal/x5
    //   384	394	397	wtf/opal/x5
    //   405	445	448	wtf/opal/x5
    //   452	473	476	wtf/opal/x5
    //   470	490	493	wtf/opal/x5
    //   480	500	503	wtf/opal/x5
    //   497	535	538	wtf/opal/x5
    //   542	564	567	wtf/opal/x5
  }
  
  public static void m(boolean paramBoolean) {
    T = paramBoolean;
  }
  
  public static boolean b() {
    return T;
  }
  
  public static boolean D() {
    boolean bool = b();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 4668981862416261715
    //   3: ldc2_w -8710089525276532156
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 211425099564910
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/pv.a : J
    //   26: iconst_1
    //   27: new java/util/HashMap
    //   30: dup
    //   31: bipush #13
    //   33: invokespecial <init> : (I)V
    //   36: putstatic wtf/opal/pv.f : Ljava/util/Map;
    //   39: getstatic wtf/opal/pv.a : J
    //   42: ldc2_w 76368130992302
    //   45: lxor
    //   46: lstore_0
    //   47: invokestatic m : (Z)V
    //   50: ldc_w 'DES/CBC/NoPadding'
    //   53: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   56: dup
    //   57: astore_2
    //   58: iconst_2
    //   59: ldc_w 'DES'
    //   62: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   65: bipush #8
    //   67: newarray byte
    //   69: dup
    //   70: iconst_0
    //   71: lload_0
    //   72: bipush #56
    //   74: lushr
    //   75: l2i
    //   76: i2b
    //   77: bastore
    //   78: iconst_1
    //   79: istore_3
    //   80: iload_3
    //   81: bipush #8
    //   83: if_icmpge -> 106
    //   86: dup
    //   87: iload_3
    //   88: lload_0
    //   89: iload_3
    //   90: bipush #8
    //   92: imul
    //   93: lshl
    //   94: bipush #56
    //   96: lushr
    //   97: l2i
    //   98: i2b
    //   99: bastore
    //   100: iinc #3, 1
    //   103: goto -> 80
    //   106: new javax/crypto/spec/DESKeySpec
    //   109: dup_x1
    //   110: swap
    //   111: invokespecial <init> : ([B)V
    //   114: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   117: new javax/crypto/spec/IvParameterSpec
    //   120: dup
    //   121: bipush #8
    //   123: newarray byte
    //   125: invokespecial <init> : ([B)V
    //   128: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   131: iconst_3
    //   132: newarray long
    //   134: astore #8
    //   136: iconst_0
    //   137: istore #5
    //   139: ldc_w '«Ïn]À¼0¢-Õ½'Tp2D¾AÍ'
    //   142: dup
    //   143: astore #6
    //   145: invokevirtual length : ()I
    //   148: istore #7
    //   150: iconst_0
    //   151: istore #4
    //   153: aload #6
    //   155: iload #4
    //   157: iinc #4, 8
    //   160: iload #4
    //   162: invokevirtual substring : (II)Ljava/lang/String;
    //   165: ldc_w 'ISO-8859-1'
    //   168: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   171: astore #9
    //   173: aload #8
    //   175: iload #5
    //   177: iinc #5, 1
    //   180: aload #9
    //   182: iconst_0
    //   183: baload
    //   184: i2l
    //   185: ldc2_w 255
    //   188: land
    //   189: bipush #56
    //   191: lshl
    //   192: aload #9
    //   194: iconst_1
    //   195: baload
    //   196: i2l
    //   197: ldc2_w 255
    //   200: land
    //   201: bipush #48
    //   203: lshl
    //   204: lor
    //   205: aload #9
    //   207: iconst_2
    //   208: baload
    //   209: i2l
    //   210: ldc2_w 255
    //   213: land
    //   214: bipush #40
    //   216: lshl
    //   217: lor
    //   218: aload #9
    //   220: iconst_3
    //   221: baload
    //   222: i2l
    //   223: ldc2_w 255
    //   226: land
    //   227: bipush #32
    //   229: lshl
    //   230: lor
    //   231: aload #9
    //   233: iconst_4
    //   234: baload
    //   235: i2l
    //   236: ldc2_w 255
    //   239: land
    //   240: bipush #24
    //   242: lshl
    //   243: lor
    //   244: aload #9
    //   246: iconst_5
    //   247: baload
    //   248: i2l
    //   249: ldc2_w 255
    //   252: land
    //   253: bipush #16
    //   255: lshl
    //   256: lor
    //   257: aload #9
    //   259: bipush #6
    //   261: baload
    //   262: i2l
    //   263: ldc2_w 255
    //   266: land
    //   267: bipush #8
    //   269: lshl
    //   270: lor
    //   271: aload #9
    //   273: bipush #7
    //   275: baload
    //   276: i2l
    //   277: ldc2_w 255
    //   280: land
    //   281: lor
    //   282: iconst_m1
    //   283: goto -> 309
    //   286: lastore
    //   287: iload #4
    //   289: iload #7
    //   291: if_icmplt -> 153
    //   294: aload #8
    //   296: putstatic wtf/opal/pv.d : [J
    //   299: iconst_3
    //   300: anewarray java/lang/Integer
    //   303: putstatic wtf/opal/pv.e : [Ljava/lang/Integer;
    //   306: goto -> 511
    //   309: dup_x2
    //   310: pop
    //   311: lstore #10
    //   313: bipush #8
    //   315: newarray byte
    //   317: dup
    //   318: iconst_0
    //   319: lload #10
    //   321: bipush #56
    //   323: lushr
    //   324: l2i
    //   325: i2b
    //   326: bastore
    //   327: dup
    //   328: iconst_1
    //   329: lload #10
    //   331: bipush #48
    //   333: lushr
    //   334: l2i
    //   335: i2b
    //   336: bastore
    //   337: dup
    //   338: iconst_2
    //   339: lload #10
    //   341: bipush #40
    //   343: lushr
    //   344: l2i
    //   345: i2b
    //   346: bastore
    //   347: dup
    //   348: iconst_3
    //   349: lload #10
    //   351: bipush #32
    //   353: lushr
    //   354: l2i
    //   355: i2b
    //   356: bastore
    //   357: dup
    //   358: iconst_4
    //   359: lload #10
    //   361: bipush #24
    //   363: lushr
    //   364: l2i
    //   365: i2b
    //   366: bastore
    //   367: dup
    //   368: iconst_5
    //   369: lload #10
    //   371: bipush #16
    //   373: lushr
    //   374: l2i
    //   375: i2b
    //   376: bastore
    //   377: dup
    //   378: bipush #6
    //   380: lload #10
    //   382: bipush #8
    //   384: lushr
    //   385: l2i
    //   386: i2b
    //   387: bastore
    //   388: dup
    //   389: bipush #7
    //   391: lload #10
    //   393: l2i
    //   394: i2b
    //   395: bastore
    //   396: aload_2
    //   397: swap
    //   398: invokevirtual doFinal : ([B)[B
    //   401: astore #12
    //   403: aload #12
    //   405: iconst_0
    //   406: baload
    //   407: i2l
    //   408: ldc2_w 255
    //   411: land
    //   412: bipush #56
    //   414: lshl
    //   415: aload #12
    //   417: iconst_1
    //   418: baload
    //   419: i2l
    //   420: ldc2_w 255
    //   423: land
    //   424: bipush #48
    //   426: lshl
    //   427: lor
    //   428: aload #12
    //   430: iconst_2
    //   431: baload
    //   432: i2l
    //   433: ldc2_w 255
    //   436: land
    //   437: bipush #40
    //   439: lshl
    //   440: lor
    //   441: aload #12
    //   443: iconst_3
    //   444: baload
    //   445: i2l
    //   446: ldc2_w 255
    //   449: land
    //   450: bipush #32
    //   452: lshl
    //   453: lor
    //   454: aload #12
    //   456: iconst_4
    //   457: baload
    //   458: i2l
    //   459: ldc2_w 255
    //   462: land
    //   463: bipush #24
    //   465: lshl
    //   466: lor
    //   467: aload #12
    //   469: iconst_5
    //   470: baload
    //   471: i2l
    //   472: ldc2_w 255
    //   475: land
    //   476: bipush #16
    //   478: lshl
    //   479: lor
    //   480: aload #12
    //   482: bipush #6
    //   484: baload
    //   485: i2l
    //   486: ldc2_w 255
    //   489: land
    //   490: bipush #8
    //   492: lshl
    //   493: lor
    //   494: aload #12
    //   496: bipush #7
    //   498: baload
    //   499: i2l
    //   500: ldc2_w 255
    //   503: land
    //   504: lor
    //   505: dup2_x1
    //   506: pop2
    //   507: pop
    //   508: goto -> 286
    //   511: return
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x179A;
    if (e[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = d[i];
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
        throw new RuntimeException("wtf/opal/pv", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      e[i] = Integer.valueOf(j);
    } 
    return e[i].intValue();
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
    //   66: ldc_w 'wtf/opal/pv'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pv.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */