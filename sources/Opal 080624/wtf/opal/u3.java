package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Deque;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class u3 extends u_<o> {
  private final Deque<class_2596<?>> E;
  
  private final Deque<class_2596<?>> Q;
  
  private int d;
  
  private final kr N;
  
  private final gm<lb> O;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] c;
  
  private static final Map e;
  
  public u3(long paramLong, o paramo) {
    // Byte code:
    //   0: getstatic wtf/opal/u3.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: aload_0
    //   7: aload_3
    //   8: invokespecial <init> : (Lwtf/opal/d;)V
    //   11: invokestatic d : ()Z
    //   14: aload_0
    //   15: new java/util/LinkedList
    //   18: dup
    //   19: invokespecial <init> : ()V
    //   22: putfield E : Ljava/util/Deque;
    //   25: aload_0
    //   26: new java/util/LinkedList
    //   29: dup
    //   30: invokespecial <init> : ()V
    //   33: putfield Q : Ljava/util/Deque;
    //   36: aload_0
    //   37: sipush #2925
    //   40: ldc2_w 7347934004950794653
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> l : (IJ)I
    //   50: putfield d : I
    //   53: aload_0
    //   54: new wtf/opal/kr
    //   57: dup
    //   58: invokespecial <init> : ()V
    //   61: putfield N : Lwtf/opal/kr;
    //   64: istore #4
    //   66: aload_0
    //   67: aload_0
    //   68: <illegal opcode> H : (Lwtf/opal/u3;)Lwtf/opal/gm;
    //   73: putfield O : Lwtf/opal/gm;
    //   76: iload #4
    //   78: ifne -> 95
    //   81: iconst_2
    //   82: anewarray wtf/opal/d
    //   85: invokestatic p : ([Lwtf/opal/d;)V
    //   88: goto -> 95
    //   91: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   94: athrow
    //   95: return
    // Exception table:
    //   from	to	target	type
    //   66	88	91	wtf/opal/x5
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return xk.CUBECRAFT;
  }
  
  private void lambda$new$0(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/u3.a : J
    //   3: ldc2_w 5764357397611
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 100431639195584
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic d : ()Z
    //   20: istore #6
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1724 : Lnet/minecraft/class_746;
    //   28: iload #6
    //   30: ifeq -> 56
    //   33: ifnull -> 88
    //   36: goto -> 43
    //   39: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   46: getfield field_1724 : Lnet/minecraft/class_746;
    //   49: goto -> 56
    //   52: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   55: athrow
    //   56: getfield field_6012 : I
    //   59: iload #6
    //   61: ifeq -> 161
    //   64: ifne -> 88
    //   67: goto -> 74
    //   70: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: aload_0
    //   75: getfield E : Ljava/util/Deque;
    //   78: invokeinterface clear : ()V
    //   83: return
    //   84: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   87: athrow
    //   88: aload_0
    //   89: getfield N : Lwtf/opal/kr;
    //   92: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   95: sipush #13473
    //   98: ldc2_w 4736241608669748797
    //   101: lload_2
    //   102: lxor
    //   103: <illegal opcode> l : (IJ)I
    //   108: sipush #26226
    //   111: ldc2_w 5395491369715928297
    //   114: lload_2
    //   115: lxor
    //   116: <illegal opcode> l : (IJ)I
    //   121: invokevirtual nextInt : (II)I
    //   124: i2l
    //   125: lload #4
    //   127: iconst_1
    //   128: iconst_3
    //   129: anewarray java/lang/Object
    //   132: dup_x1
    //   133: swap
    //   134: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   137: iconst_2
    //   138: swap
    //   139: aastore
    //   140: dup_x2
    //   141: dup_x2
    //   142: pop
    //   143: invokestatic valueOf : (J)Ljava/lang/Long;
    //   146: iconst_1
    //   147: swap
    //   148: aastore
    //   149: dup_x2
    //   150: dup_x2
    //   151: pop
    //   152: invokestatic valueOf : (J)Ljava/lang/Long;
    //   155: iconst_0
    //   156: swap
    //   157: aastore
    //   158: invokevirtual v : ([Ljava/lang/Object;)Z
    //   161: iload #6
    //   163: ifeq -> 249
    //   166: ifeq -> 238
    //   169: goto -> 176
    //   172: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   175: athrow
    //   176: aload_0
    //   177: getfield Q : Ljava/util/Deque;
    //   180: invokeinterface isEmpty : ()Z
    //   185: iload #6
    //   187: ifeq -> 249
    //   190: goto -> 197
    //   193: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   196: athrow
    //   197: ifne -> 238
    //   200: goto -> 207
    //   203: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   206: athrow
    //   207: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   210: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   213: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   216: aload_0
    //   217: getfield Q : Ljava/util/Deque;
    //   220: invokeinterface poll : ()Ljava/lang/Object;
    //   225: checkcast net/minecraft/class_2596
    //   228: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   231: goto -> 238
    //   234: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   237: athrow
    //   238: aload_1
    //   239: iconst_0
    //   240: anewarray java/lang/Object
    //   243: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   246: instanceof net/minecraft/class_6374
    //   249: iload #6
    //   251: ifeq -> 447
    //   254: ifeq -> 424
    //   257: goto -> 264
    //   260: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   263: athrow
    //   264: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   267: getfield field_1724 : Lnet/minecraft/class_746;
    //   270: getfield field_6012 : I
    //   273: aload_0
    //   274: getfield d : I
    //   277: irem
    //   278: iload #6
    //   280: ifeq -> 423
    //   283: goto -> 290
    //   286: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   289: athrow
    //   290: ifne -> 398
    //   293: goto -> 300
    //   296: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   299: athrow
    //   300: aload_0
    //   301: getfield E : Ljava/util/Deque;
    //   304: invokeinterface isEmpty : ()Z
    //   309: iload #6
    //   311: ifeq -> 423
    //   314: goto -> 321
    //   317: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   320: athrow
    //   321: ifne -> 398
    //   324: goto -> 331
    //   327: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   330: athrow
    //   331: aload_0
    //   332: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   335: sipush #6376
    //   338: ldc2_w 1741890985080517234
    //   341: lload_2
    //   342: lxor
    //   343: <illegal opcode> l : (IJ)I
    //   348: sipush #28123
    //   351: ldc2_w 3912183907275278147
    //   354: lload_2
    //   355: lxor
    //   356: <illegal opcode> l : (IJ)I
    //   361: invokevirtual nextInt : (II)I
    //   364: putfield d : I
    //   367: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   370: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   373: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   376: aload_0
    //   377: getfield E : Ljava/util/Deque;
    //   380: invokeinterface poll : ()Ljava/lang/Object;
    //   385: checkcast net/minecraft/class_2596
    //   388: invokevirtual method_10743 : (Lnet/minecraft/class_2596;)V
    //   391: goto -> 398
    //   394: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   397: athrow
    //   398: aload_1
    //   399: iconst_0
    //   400: anewarray java/lang/Object
    //   403: invokevirtual Z : ([Ljava/lang/Object;)V
    //   406: aload_0
    //   407: getfield E : Ljava/util/Deque;
    //   410: aload_1
    //   411: iconst_0
    //   412: anewarray java/lang/Object
    //   415: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   418: invokeinterface add : (Ljava/lang/Object;)Z
    //   423: pop
    //   424: aload_1
    //   425: iconst_0
    //   426: anewarray java/lang/Object
    //   429: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   432: iload #6
    //   434: ifeq -> 491
    //   437: instanceof net/minecraft/class_2827
    //   440: goto -> 447
    //   443: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   446: athrow
    //   447: ifeq -> 483
    //   450: aload_0
    //   451: getfield Q : Ljava/util/Deque;
    //   454: aload_1
    //   455: iconst_0
    //   456: anewarray java/lang/Object
    //   459: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   462: invokeinterface add : (Ljava/lang/Object;)Z
    //   467: pop
    //   468: aload_1
    //   469: iconst_0
    //   470: anewarray java/lang/Object
    //   473: invokevirtual Z : ([Ljava/lang/Object;)V
    //   476: goto -> 483
    //   479: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   482: athrow
    //   483: aload_1
    //   484: iconst_0
    //   485: anewarray java/lang/Object
    //   488: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   491: astore #8
    //   493: aload #8
    //   495: iload #6
    //   497: ifeq -> 522
    //   500: instanceof net/minecraft/class_2828
    //   503: ifeq -> 574
    //   506: goto -> 513
    //   509: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   512: athrow
    //   513: aload #8
    //   515: goto -> 522
    //   518: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   521: athrow
    //   522: checkcast net/minecraft/class_2828
    //   525: astore #7
    //   527: aload #7
    //   529: checkcast wtf/opal/mixin/PlayerMoveC2SPacketAccessor
    //   532: astore #8
    //   534: aload #8
    //   536: aload #7
    //   538: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   541: getfield field_1724 : Lnet/minecraft/class_746;
    //   544: invokevirtual method_23318 : ()D
    //   547: invokevirtual method_12268 : (D)D
    //   550: aload #7
    //   552: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   555: getfield field_1724 : Lnet/minecraft/class_746;
    //   558: invokevirtual method_23318 : ()D
    //   561: invokevirtual method_12268 : (D)D
    //   564: ldc2_w 0.015625
    //   567: drem
    //   568: dsub
    //   569: invokeinterface setY : (D)V
    //   574: return
    // Exception table:
    //   from	to	target	type
    //   22	36	39	wtf/opal/x5
    //   33	49	52	wtf/opal/x5
    //   56	67	70	wtf/opal/x5
    //   64	84	84	wtf/opal/x5
    //   161	169	172	wtf/opal/x5
    //   166	190	193	wtf/opal/x5
    //   176	200	203	wtf/opal/x5
    //   197	231	234	wtf/opal/x5
    //   249	257	260	wtf/opal/x5
    //   254	283	286	wtf/opal/x5
    //   264	293	296	wtf/opal/x5
    //   290	314	317	wtf/opal/x5
    //   300	324	327	wtf/opal/x5
    //   321	391	394	wtf/opal/x5
    //   424	440	443	wtf/opal/x5
    //   447	476	479	wtf/opal/x5
    //   493	506	509	wtf/opal/x5
    //   500	515	518	wtf/opal/x5
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 4558903733530328185
    //   3: ldc2_w -4060048187887586307
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 267783575391976
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/u3.a : J
    //   26: new java/util/HashMap
    //   29: dup
    //   30: bipush #13
    //   32: invokespecial <init> : (I)V
    //   35: putstatic wtf/opal/u3.e : Ljava/util/Map;
    //   38: getstatic wtf/opal/u3.a : J
    //   41: ldc2_w 33829737402960
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
    //   127: iconst_5
    //   128: newarray long
    //   130: astore #8
    //   132: iconst_0
    //   133: istore #5
    //   135: ldc_w 'nüÛàîðWæö§\\fGfigöäy/\\f¼'
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
    //   279: goto -> 460
    //   282: lastore
    //   283: iload #4
    //   285: iload #7
    //   287: if_icmplt -> 149
    //   290: ldc_w 'b;Yÿ{ßü½5¼Ûä­dð'
    //   293: dup
    //   294: astore #6
    //   296: invokevirtual length : ()I
    //   299: istore #7
    //   301: iconst_0
    //   302: istore #4
    //   304: aload #6
    //   306: iload #4
    //   308: iinc #4, 8
    //   311: iload #4
    //   313: invokevirtual substring : (II)Ljava/lang/String;
    //   316: ldc_w 'ISO-8859-1'
    //   319: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   322: astore #9
    //   324: aload #8
    //   326: iload #5
    //   328: iinc #5, 1
    //   331: aload #9
    //   333: iconst_0
    //   334: baload
    //   335: i2l
    //   336: ldc2_w 255
    //   339: land
    //   340: bipush #56
    //   342: lshl
    //   343: aload #9
    //   345: iconst_1
    //   346: baload
    //   347: i2l
    //   348: ldc2_w 255
    //   351: land
    //   352: bipush #48
    //   354: lshl
    //   355: lor
    //   356: aload #9
    //   358: iconst_2
    //   359: baload
    //   360: i2l
    //   361: ldc2_w 255
    //   364: land
    //   365: bipush #40
    //   367: lshl
    //   368: lor
    //   369: aload #9
    //   371: iconst_3
    //   372: baload
    //   373: i2l
    //   374: ldc2_w 255
    //   377: land
    //   378: bipush #32
    //   380: lshl
    //   381: lor
    //   382: aload #9
    //   384: iconst_4
    //   385: baload
    //   386: i2l
    //   387: ldc2_w 255
    //   390: land
    //   391: bipush #24
    //   393: lshl
    //   394: lor
    //   395: aload #9
    //   397: iconst_5
    //   398: baload
    //   399: i2l
    //   400: ldc2_w 255
    //   403: land
    //   404: bipush #16
    //   406: lshl
    //   407: lor
    //   408: aload #9
    //   410: bipush #6
    //   412: baload
    //   413: i2l
    //   414: ldc2_w 255
    //   417: land
    //   418: bipush #8
    //   420: lshl
    //   421: lor
    //   422: aload #9
    //   424: bipush #7
    //   426: baload
    //   427: i2l
    //   428: ldc2_w 255
    //   431: land
    //   432: lor
    //   433: iconst_0
    //   434: goto -> 460
    //   437: lastore
    //   438: iload #4
    //   440: iload #7
    //   442: if_icmplt -> 304
    //   445: aload #8
    //   447: putstatic wtf/opal/u3.b : [J
    //   450: iconst_5
    //   451: anewarray java/lang/Integer
    //   454: putstatic wtf/opal/u3.c : [Ljava/lang/Integer;
    //   457: goto -> 676
    //   460: dup_x2
    //   461: pop
    //   462: lstore #10
    //   464: bipush #8
    //   466: newarray byte
    //   468: dup
    //   469: iconst_0
    //   470: lload #10
    //   472: bipush #56
    //   474: lushr
    //   475: l2i
    //   476: i2b
    //   477: bastore
    //   478: dup
    //   479: iconst_1
    //   480: lload #10
    //   482: bipush #48
    //   484: lushr
    //   485: l2i
    //   486: i2b
    //   487: bastore
    //   488: dup
    //   489: iconst_2
    //   490: lload #10
    //   492: bipush #40
    //   494: lushr
    //   495: l2i
    //   496: i2b
    //   497: bastore
    //   498: dup
    //   499: iconst_3
    //   500: lload #10
    //   502: bipush #32
    //   504: lushr
    //   505: l2i
    //   506: i2b
    //   507: bastore
    //   508: dup
    //   509: iconst_4
    //   510: lload #10
    //   512: bipush #24
    //   514: lushr
    //   515: l2i
    //   516: i2b
    //   517: bastore
    //   518: dup
    //   519: iconst_5
    //   520: lload #10
    //   522: bipush #16
    //   524: lushr
    //   525: l2i
    //   526: i2b
    //   527: bastore
    //   528: dup
    //   529: bipush #6
    //   531: lload #10
    //   533: bipush #8
    //   535: lushr
    //   536: l2i
    //   537: i2b
    //   538: bastore
    //   539: dup
    //   540: bipush #7
    //   542: lload #10
    //   544: l2i
    //   545: i2b
    //   546: bastore
    //   547: aload_2
    //   548: swap
    //   549: invokevirtual doFinal : ([B)[B
    //   552: astore #12
    //   554: aload #12
    //   556: iconst_0
    //   557: baload
    //   558: i2l
    //   559: ldc2_w 255
    //   562: land
    //   563: bipush #56
    //   565: lshl
    //   566: aload #12
    //   568: iconst_1
    //   569: baload
    //   570: i2l
    //   571: ldc2_w 255
    //   574: land
    //   575: bipush #48
    //   577: lshl
    //   578: lor
    //   579: aload #12
    //   581: iconst_2
    //   582: baload
    //   583: i2l
    //   584: ldc2_w 255
    //   587: land
    //   588: bipush #40
    //   590: lshl
    //   591: lor
    //   592: aload #12
    //   594: iconst_3
    //   595: baload
    //   596: i2l
    //   597: ldc2_w 255
    //   600: land
    //   601: bipush #32
    //   603: lshl
    //   604: lor
    //   605: aload #12
    //   607: iconst_4
    //   608: baload
    //   609: i2l
    //   610: ldc2_w 255
    //   613: land
    //   614: bipush #24
    //   616: lshl
    //   617: lor
    //   618: aload #12
    //   620: iconst_5
    //   621: baload
    //   622: i2l
    //   623: ldc2_w 255
    //   626: land
    //   627: bipush #16
    //   629: lshl
    //   630: lor
    //   631: aload #12
    //   633: bipush #6
    //   635: baload
    //   636: i2l
    //   637: ldc2_w 255
    //   640: land
    //   641: bipush #8
    //   643: lshl
    //   644: lor
    //   645: aload #12
    //   647: bipush #7
    //   649: baload
    //   650: i2l
    //   651: ldc2_w 255
    //   654: land
    //   655: lor
    //   656: dup2_x1
    //   657: pop2
    //   658: tableswitch default -> 282, 0 -> 437
    //   676: return
  }
  
  private static x5 b(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1B87;
    if (c[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = b[i];
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
        throw new RuntimeException("wtf/opal/u3", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      c[i] = Integer.valueOf(j);
    } 
    return c[i].intValue();
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
    //   66: ldc_w 'wtf/opal/u3'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\u3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */