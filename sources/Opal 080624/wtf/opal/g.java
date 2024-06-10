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
import net.minecraft.class_1674;

public final class g extends d {
  private final kt E;
  
  private final kt U;
  
  private final kr x;
  
  private class_1674 v;
  
  private float B;
  
  private final gm<u0> g;
  
  private final gm<b6> Z;
  
  private static final long a = on.a(-9117968140388133351L, 7464691908577079569L, MethodHandles.lookup().lookupClass()).a(161585244935687L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  public g(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/g.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 54520580581177
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 35083938102892
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #25237
    //   25: ldc2_w 1825370739068680976
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #20304
    //   40: ldc2_w 8395287312645396180
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.COMBAT : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new wtf/opal/kt
    //   60: dup
    //   61: sipush #15502
    //   64: ldc2_w 6010608191912305928
    //   67: lload_1
    //   68: lxor
    //   69: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   74: ldc2_w 4.0
    //   77: ldc2_w 3.0
    //   80: ldc2_w 10.0
    //   83: ldc2_w 0.1
    //   86: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   89: putfield E : Lwtf/opal/kt;
    //   92: aload_0
    //   93: new wtf/opal/kt
    //   96: dup
    //   97: sipush #22247
    //   100: ldc2_w 3276637745194078048
    //   103: lload_1
    //   104: lxor
    //   105: <illegal opcode> y : (IJ)Ljava/lang/String;
    //   110: ldc2_w 6.0
    //   113: ldc2_w 3.0
    //   116: ldc2_w 10.0
    //   119: ldc2_w 0.1
    //   122: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   125: putfield U : Lwtf/opal/kt;
    //   128: aload_0
    //   129: new wtf/opal/kr
    //   132: dup
    //   133: invokespecial <init> : ()V
    //   136: putfield x : Lwtf/opal/kr;
    //   139: aload_0
    //   140: aload_0
    //   141: <illegal opcode> H : (Lwtf/opal/g;)Lwtf/opal/gm;
    //   146: putfield g : Lwtf/opal/gm;
    //   149: aload_0
    //   150: aload_0
    //   151: <illegal opcode> H : (Lwtf/opal/g;)Lwtf/opal/gm;
    //   156: putfield Z : Lwtf/opal/gm;
    //   159: aload_0
    //   160: iconst_2
    //   161: anewarray wtf/opal/k3
    //   164: dup
    //   165: iconst_0
    //   166: aload_0
    //   167: getfield E : Lwtf/opal/kt;
    //   170: aastore
    //   171: dup
    //   172: iconst_1
    //   173: aload_0
    //   174: getfield U : Lwtf/opal/kt;
    //   177: aastore
    //   178: lload_3
    //   179: dup2_x1
    //   180: pop2
    //   181: iconst_2
    //   182: anewarray java/lang/Object
    //   185: dup_x1
    //   186: swap
    //   187: iconst_1
    //   188: swap
    //   189: aastore
    //   190: dup_x2
    //   191: dup_x2
    //   192: pop
    //   193: invokestatic valueOf : (J)Ljava/lang/Long;
    //   196: iconst_0
    //   197: swap
    //   198: aastore
    //   199: invokevirtual o : ([Ljava/lang/Object;)V
    //   202: return
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/g.a : J
    //   3: ldc2_w 105543227747252
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 36428387200524
    //   13: lxor
    //   14: dup2
    //   15: bipush #48
    //   17: lushr
    //   18: l2i
    //   19: istore #4
    //   21: dup2
    //   22: bipush #16
    //   24: lshl
    //   25: bipush #32
    //   27: lushr
    //   28: l2i
    //   29: istore #5
    //   31: dup2
    //   32: bipush #48
    //   34: lshl
    //   35: bipush #48
    //   37: lushr
    //   38: l2i
    //   39: istore #6
    //   41: pop2
    //   42: dup2
    //   43: ldc2_w 98385507636500
    //   46: lxor
    //   47: lstore #7
    //   49: pop2
    //   50: invokestatic F : ()I
    //   53: istore #9
    //   55: aload_0
    //   56: iload #9
    //   58: ifne -> 158
    //   61: getfield U : Lwtf/opal/kt;
    //   64: invokevirtual z : ()Ljava/lang/Object;
    //   67: checkcast java/lang/Double
    //   70: invokevirtual doubleValue : ()D
    //   73: aload_0
    //   74: getfield E : Lwtf/opal/kt;
    //   77: invokevirtual z : ()Ljava/lang/Object;
    //   80: checkcast java/lang/Double
    //   83: invokevirtual doubleValue : ()D
    //   86: dcmpg
    //   87: ifge -> 130
    //   90: goto -> 97
    //   93: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: aload_0
    //   98: getfield U : Lwtf/opal/kt;
    //   101: aload_0
    //   102: getfield E : Lwtf/opal/kt;
    //   105: invokevirtual z : ()Ljava/lang/Object;
    //   108: checkcast java/lang/Double
    //   111: iconst_1
    //   112: anewarray java/lang/Object
    //   115: dup_x1
    //   116: swap
    //   117: iconst_0
    //   118: swap
    //   119: aastore
    //   120: invokevirtual V : ([Ljava/lang/Object;)V
    //   123: goto -> 130
    //   126: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   129: athrow
    //   130: iconst_0
    //   131: anewarray java/lang/Object
    //   134: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   137: iconst_0
    //   138: anewarray java/lang/Object
    //   141: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   144: ldc wtf/opal/xw
    //   146: iconst_1
    //   147: anewarray java/lang/Object
    //   150: dup_x1
    //   151: swap
    //   152: iconst_0
    //   153: swap
    //   154: aastore
    //   155: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   158: checkcast wtf/opal/xw
    //   161: astore #10
    //   163: iconst_0
    //   164: anewarray java/lang/Object
    //   167: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   170: iconst_0
    //   171: anewarray java/lang/Object
    //   174: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   177: ldc wtf/opal/xc
    //   179: iconst_1
    //   180: anewarray java/lang/Object
    //   183: dup_x1
    //   184: swap
    //   185: iconst_0
    //   186: swap
    //   187: aastore
    //   188: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   191: checkcast wtf/opal/xc
    //   194: astore #11
    //   196: aload_0
    //   197: getfield v : Lnet/minecraft/class_1674;
    //   200: ifnull -> 398
    //   203: aload #10
    //   205: iconst_0
    //   206: anewarray java/lang/Object
    //   209: invokevirtual D : ([Ljava/lang/Object;)Z
    //   212: iload #9
    //   214: ifne -> 250
    //   217: goto -> 224
    //   220: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   223: athrow
    //   224: ifne -> 398
    //   227: goto -> 234
    //   230: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   233: athrow
    //   234: aload #11
    //   236: iconst_0
    //   237: anewarray java/lang/Object
    //   240: invokevirtual D : ([Ljava/lang/Object;)Z
    //   243: goto -> 250
    //   246: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   249: athrow
    //   250: iload #9
    //   252: ifne -> 383
    //   255: ifeq -> 327
    //   258: goto -> 265
    //   261: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   264: athrow
    //   265: aload #11
    //   267: iconst_0
    //   268: anewarray java/lang/Object
    //   271: invokevirtual Z : ([Ljava/lang/Object;)Z
    //   274: iload #9
    //   276: ifne -> 383
    //   279: goto -> 286
    //   282: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   285: athrow
    //   286: ifeq -> 327
    //   289: goto -> 296
    //   292: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   295: athrow
    //   296: aload #11
    //   298: iconst_0
    //   299: anewarray java/lang/Object
    //   302: invokevirtual F : ([Ljava/lang/Object;)Z
    //   305: iload #9
    //   307: ifne -> 383
    //   310: goto -> 317
    //   313: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   316: athrow
    //   317: ifne -> 398
    //   320: goto -> 327
    //   323: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   326: athrow
    //   327: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   330: getfield field_1724 : Lnet/minecraft/class_746;
    //   333: iload #4
    //   335: i2s
    //   336: iload #5
    //   338: iload #6
    //   340: iconst_4
    //   341: anewarray java/lang/Object
    //   344: dup_x1
    //   345: swap
    //   346: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   349: iconst_3
    //   350: swap
    //   351: aastore
    //   352: dup_x1
    //   353: swap
    //   354: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   357: iconst_2
    //   358: swap
    //   359: aastore
    //   360: dup_x1
    //   361: swap
    //   362: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   365: iconst_1
    //   366: swap
    //   367: aastore
    //   368: dup_x1
    //   369: swap
    //   370: iconst_0
    //   371: swap
    //   372: aastore
    //   373: invokestatic J : ([Ljava/lang/Object;)Z
    //   376: goto -> 383
    //   379: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   382: athrow
    //   383: iload #9
    //   385: ifne -> 406
    //   388: ifeq -> 399
    //   391: goto -> 398
    //   394: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   397: athrow
    //   398: return
    //   399: aload_0
    //   400: getfield v : Lnet/minecraft/class_1674;
    //   403: invokevirtual method_31481 : ()Z
    //   406: iload #9
    //   408: ifne -> 459
    //   411: ifeq -> 431
    //   414: goto -> 421
    //   417: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   420: athrow
    //   421: aload_0
    //   422: aconst_null
    //   423: putfield v : Lnet/minecraft/class_1674;
    //   426: return
    //   427: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   430: athrow
    //   431: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   434: getfield field_1724 : Lnet/minecraft/class_746;
    //   437: aload_0
    //   438: getfield v : Lnet/minecraft/class_1674;
    //   441: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   444: f2d
    //   445: aload_0
    //   446: getfield U : Lwtf/opal/kt;
    //   449: invokevirtual z : ()Ljava/lang/Object;
    //   452: checkcast java/lang/Double
    //   455: invokevirtual doubleValue : ()D
    //   458: dcmpg
    //   459: ifgt -> 540
    //   462: iconst_0
    //   463: anewarray java/lang/Object
    //   466: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   469: iconst_0
    //   470: anewarray java/lang/Object
    //   473: invokevirtual i : ([Ljava/lang/Object;)Lwtf/opal/u5;
    //   476: aload_0
    //   477: getfield v : Lnet/minecraft/class_1674;
    //   480: iconst_1
    //   481: anewarray java/lang/Object
    //   484: dup_x1
    //   485: swap
    //   486: iconst_0
    //   487: swap
    //   488: aastore
    //   489: invokestatic x : ([Ljava/lang/Object;)Lnet/minecraft/class_241;
    //   492: lload #7
    //   494: fconst_1
    //   495: fconst_1
    //   496: iconst_4
    //   497: anewarray java/lang/Object
    //   500: dup_x1
    //   501: swap
    //   502: invokestatic valueOf : (F)Ljava/lang/Float;
    //   505: iconst_3
    //   506: swap
    //   507: aastore
    //   508: dup_x1
    //   509: swap
    //   510: invokestatic valueOf : (F)Ljava/lang/Float;
    //   513: iconst_2
    //   514: swap
    //   515: aastore
    //   516: dup_x2
    //   517: dup_x2
    //   518: pop
    //   519: invokestatic valueOf : (J)Ljava/lang/Long;
    //   522: iconst_1
    //   523: swap
    //   524: aastore
    //   525: dup_x1
    //   526: swap
    //   527: iconst_0
    //   528: swap
    //   529: aastore
    //   530: invokevirtual g : ([Ljava/lang/Object;)V
    //   533: goto -> 540
    //   536: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   539: athrow
    //   540: return
    // Exception table:
    //   from	to	target	type
    //   55	90	93	wtf/opal/x5
    //   61	123	126	wtf/opal/x5
    //   196	217	220	wtf/opal/x5
    //   203	227	230	wtf/opal/x5
    //   224	243	246	wtf/opal/x5
    //   250	258	261	wtf/opal/x5
    //   255	279	282	wtf/opal/x5
    //   265	289	292	wtf/opal/x5
    //   286	310	313	wtf/opal/x5
    //   296	320	323	wtf/opal/x5
    //   317	376	379	wtf/opal/x5
    //   383	391	394	wtf/opal/x5
    //   406	414	417	wtf/opal/x5
    //   411	427	427	wtf/opal/x5
    //   459	533	536	wtf/opal/x5
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/g.a : J
    //   3: ldc2_w 23429156006853
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 66975422907549
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic F : ()I
    //   20: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   23: getfield field_1687 : Lnet/minecraft/class_638;
    //   26: invokevirtual method_18112 : ()Ljava/lang/Iterable;
    //   29: invokeinterface iterator : ()Ljava/util/Iterator;
    //   34: astore #7
    //   36: istore #6
    //   38: aload #7
    //   40: invokeinterface hasNext : ()Z
    //   45: ifeq -> 310
    //   48: aload #7
    //   50: invokeinterface next : ()Ljava/lang/Object;
    //   55: checkcast net/minecraft/class_1297
    //   58: astore #8
    //   60: aload #8
    //   62: instanceof net/minecraft/class_1674
    //   65: ifne -> 75
    //   68: goto -> 38
    //   71: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   74: athrow
    //   75: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   78: getfield field_1724 : Lnet/minecraft/class_746;
    //   81: aload #8
    //   83: invokevirtual method_5739 : (Lnet/minecraft/class_1297;)F
    //   86: fstore #9
    //   88: aload_0
    //   89: iload #6
    //   91: ifne -> 115
    //   94: getfield v : Lnet/minecraft/class_1674;
    //   97: ifnull -> 136
    //   100: goto -> 107
    //   103: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   106: athrow
    //   107: aload_0
    //   108: goto -> 115
    //   111: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: getfield B : F
    //   118: fload #9
    //   120: fcmpl
    //   121: iload #6
    //   123: ifne -> 160
    //   126: ifle -> 305
    //   129: goto -> 136
    //   132: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   135: athrow
    //   136: fload #9
    //   138: f2d
    //   139: aload_0
    //   140: getfield E : Lwtf/opal/kt;
    //   143: invokevirtual z : ()Ljava/lang/Object;
    //   146: checkcast java/lang/Double
    //   149: invokevirtual doubleValue : ()D
    //   152: dcmpg
    //   153: goto -> 160
    //   156: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   159: athrow
    //   160: iload #6
    //   162: ifne -> 251
    //   165: ifgt -> 305
    //   168: goto -> 175
    //   171: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   174: athrow
    //   175: aload_0
    //   176: iload #6
    //   178: ifne -> 271
    //   181: goto -> 188
    //   184: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   187: athrow
    //   188: getfield x : Lwtf/opal/kr;
    //   191: ldc_w 1000.0
    //   194: invokestatic current : ()Ljava/util/concurrent/ThreadLocalRandom;
    //   197: ldc_w 12.0
    //   200: ldc_w 15.0
    //   203: invokevirtual nextFloat : (FF)F
    //   206: fdiv
    //   207: f2l
    //   208: lload #4
    //   210: iconst_1
    //   211: iconst_3
    //   212: anewarray java/lang/Object
    //   215: dup_x1
    //   216: swap
    //   217: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   220: iconst_2
    //   221: swap
    //   222: aastore
    //   223: dup_x2
    //   224: dup_x2
    //   225: pop
    //   226: invokestatic valueOf : (J)Ljava/lang/Long;
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
    //   241: invokevirtual v : ([Ljava/lang/Object;)Z
    //   244: goto -> 251
    //   247: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   250: athrow
    //   251: ifeq -> 305
    //   254: aload_0
    //   255: aload #8
    //   257: checkcast net/minecraft/class_1674
    //   260: putfield v : Lnet/minecraft/class_1674;
    //   263: aload_0
    //   264: goto -> 271
    //   267: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   270: athrow
    //   271: fload #9
    //   273: putfield B : F
    //   276: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   279: getfield field_1761 : Lnet/minecraft/class_636;
    //   282: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   285: getfield field_1724 : Lnet/minecraft/class_746;
    //   288: aload #8
    //   290: invokevirtual method_2918 : (Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;)V
    //   293: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   296: getfield field_1724 : Lnet/minecraft/class_746;
    //   299: getstatic net/minecraft/class_1268.field_5808 : Lnet/minecraft/class_1268;
    //   302: invokevirtual method_6104 : (Lnet/minecraft/class_1268;)V
    //   305: iload #6
    //   307: ifeq -> 38
    //   310: return
    // Exception table:
    //   from	to	target	type
    //   60	71	71	wtf/opal/x5
    //   88	100	103	wtf/opal/x5
    //   94	108	111	wtf/opal/x5
    //   115	129	132	wtf/opal/x5
    //   126	153	156	wtf/opal/x5
    //   160	168	171	wtf/opal/x5
    //   165	181	184	wtf/opal/x5
    //   175	244	247	wtf/opal/x5
    //   251	264	267	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0xCA0275215BL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[4];
    boolean bool = false;
    String str;
    int i = (str = "ÁwY1aT¸zÔÉAz \007ï\031¤#Üq\032 Mì3|»\036/À.q¯4 3\\I\000Ô¶:b´[K0º").length();
    byte b2 = 24;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7D82;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])f.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          f.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/g", exception);
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
    //   66: ldc_w 'wtf/opal/g'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\g.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */