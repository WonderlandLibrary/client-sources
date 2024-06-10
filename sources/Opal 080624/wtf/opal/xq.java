package wtf.opal;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
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
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;

public final class xq extends class_437 {
  private final pa q;
  
  private final dc W;
  
  private boolean r;
  
  private final dk o;
  
  private final List<xp> L;
  
  public static String Y;
  
  private static boolean k;
  
  private static final long a = on.a(366207066117271906L, 3514668451631880989L, MethodHandles.lookup().lookupClass()).a(128435425484133L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public xq(int paramInt1, int paramInt2, char paramChar) {
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
    //   23: getstatic wtf/opal/xq.a : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 12544462671674
    //   35: lxor
    //   36: dup2
    //   37: bipush #32
    //   39: lushr
    //   40: l2i
    //   41: istore #6
    //   43: dup2
    //   44: bipush #32
    //   46: lshl
    //   47: bipush #48
    //   49: lushr
    //   50: l2i
    //   51: istore #7
    //   53: dup2
    //   54: bipush #48
    //   56: lshl
    //   57: bipush #48
    //   59: lushr
    //   60: l2i
    //   61: istore #8
    //   63: pop2
    //   64: pop2
    //   65: invokestatic l : ()Z
    //   68: aload_0
    //   69: ldc ''
    //   71: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   74: invokespecial <init> : (Lnet/minecraft/class_2561;)V
    //   77: istore #9
    //   79: aload_0
    //   80: iconst_0
    //   81: anewarray java/lang/Object
    //   84: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   87: iconst_0
    //   88: anewarray java/lang/Object
    //   91: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   94: putfield q : Lwtf/opal/pa;
    //   97: aload_0
    //   98: iconst_0
    //   99: anewarray java/lang/Object
    //   102: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   105: iconst_0
    //   106: anewarray java/lang/Object
    //   109: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/dc;
    //   112: putfield W : Lwtf/opal/dc;
    //   115: aload_0
    //   116: new wtf/opal/dk
    //   119: dup
    //   120: sipush #31414
    //   123: ldc2_w 5970461581168146745
    //   126: lload #4
    //   128: lxor
    //   129: <illegal opcode> t : (IJ)I
    //   134: iload #6
    //   136: dconst_1
    //   137: iload #7
    //   139: i2c
    //   140: iload #8
    //   142: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   145: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   148: putfield o : Lwtf/opal/dk;
    //   151: aload_0
    //   152: new java/util/ArrayList
    //   155: dup
    //   156: invokespecial <init> : ()V
    //   159: putfield L : Ljava/util/List;
    //   162: invokestatic D : ()[Lwtf/opal/d;
    //   165: ifnull -> 192
    //   168: iload #9
    //   170: ifeq -> 188
    //   173: goto -> 180
    //   176: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   179: athrow
    //   180: iconst_0
    //   181: goto -> 189
    //   184: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   187: athrow
    //   188: iconst_1
    //   189: invokestatic u : (Z)V
    //   192: return
    // Exception table:
    //   from	to	target	type
    //   79	173	176	java/lang/RuntimeException
    //   168	184	184	java/lang/RuntimeException
  }
  
  public void method_25410(class_310 paramclass_310, int paramInt1, int paramInt2) {
    this.L.clear();
    super.method_25410(paramclass_310, paramInt1, paramInt2);
  }
  
  public void method_25394(class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    // Byte code:
    //   0: getstatic wtf/opal/xq.a : J
    //   3: ldc2_w 40084299243574
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 76307513428674
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 60546191880734
    //   22: lxor
    //   23: lstore #9
    //   25: dup2
    //   26: ldc2_w 83519914443937
    //   29: lxor
    //   30: dup2
    //   31: bipush #48
    //   33: lushr
    //   34: l2i
    //   35: istore #11
    //   37: dup2
    //   38: bipush #16
    //   40: lshl
    //   41: bipush #32
    //   43: lushr
    //   44: l2i
    //   45: istore #12
    //   47: dup2
    //   48: bipush #48
    //   50: lshl
    //   51: bipush #48
    //   53: lushr
    //   54: l2i
    //   55: istore #13
    //   57: pop2
    //   58: dup2
    //   59: ldc2_w 62297799014164
    //   62: lxor
    //   63: lstore #14
    //   65: pop2
    //   66: invokestatic l : ()Z
    //   69: aload_0
    //   70: aload_1
    //   71: iload_2
    //   72: iload_3
    //   73: fload #4
    //   75: invokespecial method_25394 : (Lnet/minecraft/class_332;IIF)V
    //   78: istore #16
    //   80: aload_0
    //   81: getfield o : Lwtf/opal/dk;
    //   84: iload #16
    //   86: ifne -> 156
    //   89: lload #14
    //   91: iconst_1
    //   92: anewarray java/lang/Object
    //   95: dup_x2
    //   96: dup_x2
    //   97: pop
    //   98: invokestatic valueOf : (J)Ljava/lang/Long;
    //   101: iconst_0
    //   102: swap
    //   103: aastore
    //   104: invokevirtual H : ([Ljava/lang/Object;)Z
    //   107: ifeq -> 152
    //   110: goto -> 117
    //   113: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   116: athrow
    //   117: aload_0
    //   118: aload_0
    //   119: getfield r : Z
    //   122: iload #16
    //   124: ifne -> 145
    //   127: goto -> 134
    //   130: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   133: athrow
    //   134: ifne -> 148
    //   137: goto -> 144
    //   140: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   143: athrow
    //   144: iconst_1
    //   145: goto -> 149
    //   148: iconst_0
    //   149: putfield r : Z
    //   152: aload_0
    //   153: getfield o : Lwtf/opal/dk;
    //   156: aload_0
    //   157: getfield r : Z
    //   160: ifeq -> 173
    //   163: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   166: goto -> 176
    //   169: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   172: athrow
    //   173: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   176: lload #9
    //   178: dup2_x1
    //   179: pop2
    //   180: iconst_2
    //   181: anewarray java/lang/Object
    //   184: dup_x1
    //   185: swap
    //   186: iconst_1
    //   187: swap
    //   188: aastore
    //   189: dup_x2
    //   190: dup_x2
    //   191: pop
    //   192: invokestatic valueOf : (J)Ljava/lang/Long;
    //   195: iconst_0
    //   196: swap
    //   197: aastore
    //   198: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   201: pop
    //   202: ldc 150.0
    //   204: fstore #17
    //   206: ldc 55.0
    //   208: fstore #18
    //   210: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   213: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   216: invokevirtual method_4486 : ()I
    //   219: i2f
    //   220: ldc 300.0
    //   222: fsub
    //   223: fstore #19
    //   225: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   228: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   231: invokevirtual method_4502 : ()I
    //   234: i2f
    //   235: ldc 110.0
    //   237: fsub
    //   238: fstore #20
    //   240: aload_0
    //   241: getfield L : Ljava/util/List;
    //   244: iload #16
    //   246: ifne -> 453
    //   249: invokeinterface isEmpty : ()Z
    //   254: ifeq -> 423
    //   257: goto -> 264
    //   260: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   263: athrow
    //   264: iconst_3
    //   265: anewarray java/lang/String
    //   268: dup
    //   269: iconst_0
    //   270: sipush #14409
    //   273: ldc2_w 4297002570185264923
    //   276: lload #5
    //   278: lxor
    //   279: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   284: aastore
    //   285: dup
    //   286: iconst_1
    //   287: sipush #28983
    //   290: ldc2_w 6285428341357872751
    //   293: lload #5
    //   295: lxor
    //   296: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   301: aastore
    //   302: dup
    //   303: iconst_2
    //   304: sipush #10043
    //   307: ldc2_w 2934353291822977126
    //   310: lload #5
    //   312: lxor
    //   313: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   318: aastore
    //   319: astore #21
    //   321: iconst_0
    //   322: istore #22
    //   324: iconst_0
    //   325: istore #23
    //   327: iload #23
    //   329: aload #21
    //   331: arraylength
    //   332: if_icmpge -> 423
    //   335: aload_0
    //   336: getfield L : Ljava/util/List;
    //   339: new wtf/opal/xp
    //   342: dup
    //   343: aload #21
    //   345: iload #23
    //   347: aaload
    //   348: ldc 25.0
    //   350: ldc 55.0
    //   352: iload #22
    //   354: sipush #25137
    //   357: ldc2_w 9015760383197267660
    //   360: lload #5
    //   362: lxor
    //   363: <illegal opcode> t : (IJ)I
    //   368: imul
    //   369: i2f
    //   370: fadd
    //   371: ldc 100.0
    //   373: iload #11
    //   375: i2c
    //   376: ldc 20.0
    //   378: iconst_m1
    //   379: iload #12
    //   381: iload #13
    //   383: i2c
    //   384: invokespecial <init> : (Ljava/lang/String;FFFCFIIC)V
    //   387: invokeinterface add : (Ljava/lang/Object;)Z
    //   392: pop
    //   393: iinc #22, 1
    //   396: iinc #23, 1
    //   399: iload #16
    //   401: ifne -> 487
    //   404: iload #16
    //   406: ifeq -> 327
    //   409: goto -> 416
    //   412: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   415: athrow
    //   416: iconst_3
    //   417: anewarray wtf/opal/d
    //   420: invokestatic p : ([Lwtf/opal/d;)V
    //   423: aload_0
    //   424: getfield q : Lwtf/opal/pa;
    //   427: aload_0
    //   428: fload #19
    //   430: fload #20
    //   432: <illegal opcode> run : (Lwtf/opal/xq;FF)Ljava/lang/Runnable;
    //   437: iconst_1
    //   438: anewarray java/lang/Object
    //   441: dup_x1
    //   442: swap
    //   443: iconst_0
    //   444: swap
    //   445: aastore
    //   446: invokevirtual T : ([Ljava/lang/Object;)V
    //   449: aload_0
    //   450: getfield L : Ljava/util/List;
    //   453: iload_2
    //   454: iload_3
    //   455: <illegal opcode> accept : (II)Ljava/util/function/Consumer;
    //   460: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   465: aload_0
    //   466: getfield q : Lwtf/opal/pa;
    //   469: lload #7
    //   471: iconst_1
    //   472: anewarray java/lang/Object
    //   475: dup_x2
    //   476: dup_x2
    //   477: pop
    //   478: invokestatic valueOf : (J)Ljava/lang/Long;
    //   481: iconst_0
    //   482: swap
    //   483: aastore
    //   484: invokevirtual J : ([Ljava/lang/Object;)V
    //   487: return
    // Exception table:
    //   from	to	target	type
    //   80	110	113	java/lang/RuntimeException
    //   89	127	130	java/lang/RuntimeException
    //   117	137	140	java/lang/RuntimeException
    //   156	169	169	java/lang/RuntimeException
    //   240	257	260	java/lang/RuntimeException
    //   335	409	412	java/lang/RuntimeException
  }
  
  public boolean method_25402(double paramDouble1, double paramDouble2, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield L : Ljava/util/List;
    //   4: dload_1
    //   5: dload_3
    //   6: <illegal opcode> accept : (DD)Ljava/util/function/Consumer;
    //   11: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   16: aload_0
    //   17: dload_1
    //   18: dload_3
    //   19: iload #5
    //   21: invokespecial method_25402 : (DDI)Z
    //   24: ireturn
  }
  
  public void method_25419() {
    Y = "";
    super.method_25419();
  }
  
  private static void lambda$mouseClicked$4(double paramDouble1, double paramDouble2, xp paramxp) {
    // Byte code:
    //   0: getstatic wtf/opal/xq.a : J
    //   3: ldc2_w 46013485320730
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 119350116174453
    //   15: lxor
    //   16: lstore #7
    //   18: dup2
    //   19: ldc2_w 118631182199228
    //   22: lxor
    //   23: lstore #9
    //   25: dup2
    //   26: ldc2_w 18158808623542
    //   29: lxor
    //   30: lstore #11
    //   32: dup2
    //   33: ldc2_w 124024493191673
    //   36: lxor
    //   37: lstore #13
    //   39: dup2
    //   40: ldc2_w 84000164881964
    //   43: lxor
    //   44: lstore #15
    //   46: dup2
    //   47: ldc2_w 9151941349496
    //   50: lxor
    //   51: lstore #17
    //   53: dup2
    //   54: ldc2_w 14602223711396
    //   57: lxor
    //   58: lstore #19
    //   60: pop2
    //   61: invokestatic l : ()Z
    //   64: istore #21
    //   66: aload #4
    //   68: iload #21
    //   70: ifne -> 200
    //   73: iconst_0
    //   74: anewarray java/lang/Object
    //   77: invokevirtual K : ([Ljava/lang/Object;)F
    //   80: lload #9
    //   82: dup2_x1
    //   83: pop2
    //   84: aload #4
    //   86: iconst_0
    //   87: anewarray java/lang/Object
    //   90: invokevirtual N : ([Ljava/lang/Object;)F
    //   93: aload #4
    //   95: iconst_0
    //   96: anewarray java/lang/Object
    //   99: invokevirtual H : ([Ljava/lang/Object;)F
    //   102: aload #4
    //   104: iconst_0
    //   105: anewarray java/lang/Object
    //   108: invokevirtual t : ([Ljava/lang/Object;)F
    //   111: dload_0
    //   112: dload_2
    //   113: bipush #7
    //   115: anewarray java/lang/Object
    //   118: dup_x2
    //   119: dup_x2
    //   120: pop
    //   121: invokestatic valueOf : (D)Ljava/lang/Double;
    //   124: bipush #6
    //   126: swap
    //   127: aastore
    //   128: dup_x2
    //   129: dup_x2
    //   130: pop
    //   131: invokestatic valueOf : (D)Ljava/lang/Double;
    //   134: iconst_5
    //   135: swap
    //   136: aastore
    //   137: dup_x1
    //   138: swap
    //   139: invokestatic valueOf : (F)Ljava/lang/Float;
    //   142: iconst_4
    //   143: swap
    //   144: aastore
    //   145: dup_x1
    //   146: swap
    //   147: invokestatic valueOf : (F)Ljava/lang/Float;
    //   150: iconst_3
    //   151: swap
    //   152: aastore
    //   153: dup_x1
    //   154: swap
    //   155: invokestatic valueOf : (F)Ljava/lang/Float;
    //   158: iconst_2
    //   159: swap
    //   160: aastore
    //   161: dup_x1
    //   162: swap
    //   163: invokestatic valueOf : (F)Ljava/lang/Float;
    //   166: iconst_1
    //   167: swap
    //   168: aastore
    //   169: dup_x2
    //   170: dup_x2
    //   171: pop
    //   172: invokestatic valueOf : (J)Ljava/lang/Long;
    //   175: iconst_0
    //   176: swap
    //   177: aastore
    //   178: invokestatic Z : ([Ljava/lang/Object;)Z
    //   181: ifeq -> 760
    //   184: goto -> 191
    //   187: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   190: athrow
    //   191: aload #4
    //   193: goto -> 200
    //   196: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   199: athrow
    //   200: iconst_0
    //   201: anewarray java/lang/Object
    //   204: invokevirtual w : ([Ljava/lang/Object;)Ljava/lang/String;
    //   207: astore #22
    //   209: iconst_m1
    //   210: istore #23
    //   212: aload #22
    //   214: invokevirtual hashCode : ()I
    //   217: iload #21
    //   219: ifne -> 403
    //   222: lookupswitch default -> 401, -903505627 -> 308, -692135369 -> 357, 990334700 -> 260
    //   256: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   259: athrow
    //   260: aload #22
    //   262: bipush #35
    //   264: ldc2_w 1008760185971645788
    //   267: lload #5
    //   269: lxor
    //   270: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   275: invokevirtual equals : (Ljava/lang/Object;)Z
    //   278: iload #21
    //   280: ifne -> 403
    //   283: goto -> 290
    //   286: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   289: athrow
    //   290: ifeq -> 401
    //   293: goto -> 300
    //   296: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   299: athrow
    //   300: iconst_0
    //   301: istore #23
    //   303: iload #21
    //   305: ifeq -> 401
    //   308: aload #22
    //   310: sipush #15252
    //   313: ldc2_w 6178722676445783778
    //   316: lload #5
    //   318: lxor
    //   319: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   324: invokevirtual equals : (Ljava/lang/Object;)Z
    //   327: iload #21
    //   329: ifne -> 403
    //   332: goto -> 339
    //   335: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   338: athrow
    //   339: ifeq -> 401
    //   342: goto -> 349
    //   345: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   348: athrow
    //   349: iconst_1
    //   350: istore #23
    //   352: iload #21
    //   354: ifeq -> 401
    //   357: aload #22
    //   359: sipush #23790
    //   362: ldc2_w 8440975992620401042
    //   365: lload #5
    //   367: lxor
    //   368: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   373: invokevirtual equals : (Ljava/lang/Object;)Z
    //   376: iload #21
    //   378: ifne -> 403
    //   381: goto -> 388
    //   384: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   387: athrow
    //   388: ifeq -> 401
    //   391: goto -> 398
    //   394: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   397: athrow
    //   398: iconst_2
    //   399: istore #23
    //   401: iload #23
    //   403: tableswitch default -> 760, 0 -> 428, 1 -> 455, 2 -> 501
    //   428: new java/lang/Thread
    //   431: dup
    //   432: <illegal opcode> run : ()Ljava/lang/Runnable;
    //   437: invokespecial <init> : (Ljava/lang/Runnable;)V
    //   440: invokevirtual start : ()V
    //   443: iload #21
    //   445: ifeq -> 760
    //   448: goto -> 455
    //   451: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   454: athrow
    //   455: <illegal opcode> accept : ()Ljava/util/function/Consumer;
    //   460: lload #11
    //   462: dup2_x1
    //   463: pop2
    //   464: iconst_2
    //   465: anewarray java/lang/Object
    //   468: dup_x1
    //   469: swap
    //   470: iconst_1
    //   471: swap
    //   472: aastore
    //   473: dup_x2
    //   474: dup_x2
    //   475: pop
    //   476: invokestatic valueOf : (J)Ljava/lang/Long;
    //   479: iconst_0
    //   480: swap
    //   481: aastore
    //   482: invokestatic l : ([Ljava/lang/Object;)Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   485: pop
    //   486: goto -> 760
    //   489: astore #24
    //   491: new java/lang/RuntimeException
    //   494: dup
    //   495: aload #24
    //   497: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   500: athrow
    //   501: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   504: getfield field_1774 : Lnet/minecraft/class_309;
    //   507: invokevirtual method_1460 : ()Ljava/lang/String;
    //   510: lload #13
    //   512: dup2_x1
    //   513: pop2
    //   514: getstatic wtf/opal/ks.r : Lwtf/opal/ks;
    //   517: iconst_3
    //   518: anewarray java/lang/Object
    //   521: dup_x1
    //   522: swap
    //   523: iconst_2
    //   524: swap
    //   525: aastore
    //   526: dup_x1
    //   527: swap
    //   528: iconst_1
    //   529: swap
    //   530: aastore
    //   531: dup_x2
    //   532: dup_x2
    //   533: pop
    //   534: invokestatic valueOf : (J)Ljava/lang/Long;
    //   537: iconst_0
    //   538: swap
    //   539: aastore
    //   540: invokestatic t : ([Ljava/lang/Object;)Lwtf/opal/p3;
    //   543: astore #24
    //   545: lload #17
    //   547: aload #24
    //   549: iconst_2
    //   550: anewarray java/lang/Object
    //   553: dup_x1
    //   554: swap
    //   555: iconst_1
    //   556: swap
    //   557: aastore
    //   558: dup_x2
    //   559: dup_x2
    //   560: pop
    //   561: invokestatic valueOf : (J)Ljava/lang/Long;
    //   564: iconst_0
    //   565: swap
    //   566: aastore
    //   567: invokestatic e : ([Ljava/lang/Object;)Lwtf/opal/bl;
    //   570: astore #25
    //   572: lload #19
    //   574: aload #25
    //   576: iconst_2
    //   577: anewarray java/lang/Object
    //   580: dup_x1
    //   581: swap
    //   582: iconst_1
    //   583: swap
    //   584: aastore
    //   585: dup_x2
    //   586: dup_x2
    //   587: pop
    //   588: invokestatic valueOf : (J)Ljava/lang/Long;
    //   591: iconst_0
    //   592: swap
    //   593: aastore
    //   594: invokestatic u : ([Ljava/lang/Object;)Lwtf/opal/bt;
    //   597: astore #26
    //   599: lload #15
    //   601: aload #26
    //   603: iconst_2
    //   604: anewarray java/lang/Object
    //   607: dup_x1
    //   608: swap
    //   609: iconst_1
    //   610: swap
    //   611: aastore
    //   612: dup_x2
    //   613: dup_x2
    //   614: pop
    //   615: invokestatic valueOf : (J)Ljava/lang/Long;
    //   618: iconst_0
    //   619: swap
    //   620: aastore
    //   621: invokestatic l : ([Ljava/lang/Object;)Lwtf/opal/dg;
    //   624: astore #27
    //   626: aload #27
    //   628: lload #7
    //   630: iconst_2
    //   631: anewarray java/lang/Object
    //   634: dup_x2
    //   635: dup_x2
    //   636: pop
    //   637: invokestatic valueOf : (J)Ljava/lang/Long;
    //   640: iconst_1
    //   641: swap
    //   642: aastore
    //   643: dup_x1
    //   644: swap
    //   645: iconst_0
    //   646: swap
    //   647: aastore
    //   648: invokestatic C : ([Ljava/lang/Object;)Lwtf/opal/ka;
    //   651: astore #28
    //   653: new net/minecraft/class_320
    //   656: dup
    //   657: aload #28
    //   659: iconst_0
    //   660: anewarray java/lang/Object
    //   663: invokevirtual j : ([Ljava/lang/Object;)Ljava/lang/String;
    //   666: aload #28
    //   668: iconst_0
    //   669: anewarray java/lang/Object
    //   672: invokevirtual Z : ([Ljava/lang/Object;)Ljava/util/UUID;
    //   675: aload #27
    //   677: iconst_0
    //   678: anewarray java/lang/Object
    //   681: invokevirtual c : ([Ljava/lang/Object;)Ljava/lang/String;
    //   684: invokestatic empty : ()Ljava/util/Optional;
    //   687: invokestatic empty : ()Ljava/util/Optional;
    //   690: getstatic net/minecraft/class_320$class_321.field_34962 : Lnet/minecraft/class_320$class_321;
    //   693: invokespecial <init> : (Ljava/lang/String;Ljava/util/UUID;Ljava/lang/String;Ljava/util/Optional;Ljava/util/Optional;Lnet/minecraft/class_320$class_321;)V
    //   696: astore #29
    //   698: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   701: checkcast wtf/opal/mixin/MinecraftClientAccessor
    //   704: astore #30
    //   706: aload #30
    //   708: aload #29
    //   710: invokeinterface setSession : (Lnet/minecraft/class_320;)V
    //   715: aload #28
    //   717: iconst_0
    //   718: anewarray java/lang/Object
    //   721: invokevirtual j : ([Ljava/lang/Object;)Ljava/lang/String;
    //   724: sipush #2521
    //   727: ldc2_w 1483239745343558827
    //   730: lload #5
    //   732: lxor
    //   733: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   738: swap
    //   739: ldc_w '!'
    //   742: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   747: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   750: goto -> 760
    //   753: astore #24
    //   755: aload #24
    //   757: invokevirtual printStackTrace : ()V
    //   760: return
    // Exception table:
    //   from	to	target	type
    //   66	184	187	java/lang/Exception
    //   73	193	196	java/lang/Exception
    //   212	256	256	java/lang/Exception
    //   222	283	286	java/lang/Exception
    //   260	293	296	java/lang/Exception
    //   303	332	335	java/lang/Exception
    //   308	342	345	java/lang/Exception
    //   352	381	384	java/lang/Exception
    //   357	391	394	java/lang/Exception
    //   403	448	451	java/lang/Exception
    //   455	486	489	java/lang/Exception
    //   501	750	753	java/io/IOException
  }
  
  private static void lambda$mouseClicked$3(MicrosoftAuthResult paramMicrosoftAuthResult) {}
  
  private static void lambda$mouseClicked$2() {
    // Byte code:
    //   0: getstatic wtf/opal/xq.a : J
    //   3: ldc2_w 123368526154515
    //   6: lxor
    //   7: lstore_0
    //   8: lload_0
    //   9: dup2
    //   10: ldc2_w 128978049366982
    //   13: lxor
    //   14: lstore_2
    //   15: pop2
    //   16: invokestatic l : ()Z
    //   19: invokestatic stackPush : ()Lorg/lwjgl/system/MemoryStack;
    //   22: astore #7
    //   24: istore #4
    //   26: aload #7
    //   28: iconst_1
    //   29: invokevirtual mallocPointer : (I)Lorg/lwjgl/PointerBuffer;
    //   32: astore #5
    //   34: aload #5
    //   36: aload #7
    //   38: sipush #6154
    //   41: ldc2_w 8533749382173623416
    //   44: lload_0
    //   45: lxor
    //   46: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   51: invokevirtual UTF8 : (Ljava/lang/CharSequence;)Ljava/nio/ByteBuffer;
    //   54: invokevirtual put : (Ljava/nio/ByteBuffer;)Lorg/lwjgl/PointerBuffer;
    //   57: pop
    //   58: aload #5
    //   60: invokevirtual flip : ()Lorg/lwjgl/system/CustomBuffer;
    //   63: pop
    //   64: sipush #18819
    //   67: ldc2_w 2446243910119478755
    //   70: lload_0
    //   71: lxor
    //   72: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   77: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   80: sipush #8697
    //   83: ldc2_w 1301293452939967881
    //   86: lload_0
    //   87: lxor
    //   88: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   93: ldc ''
    //   95: aload #5
    //   97: sipush #8278
    //   100: ldc2_w 1320318688626981927
    //   103: lload_0
    //   104: lxor
    //   105: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   110: iconst_0
    //   111: invokestatic tinyfd_openFileDialog : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;Lorg/lwjgl/PointerBuffer;Ljava/lang/CharSequence;Z)Ljava/lang/String;
    //   114: astore #6
    //   116: aload #7
    //   118: iload #4
    //   120: ifne -> 135
    //   123: ifnull -> 180
    //   126: goto -> 133
    //   129: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   132: athrow
    //   133: aload #7
    //   135: invokevirtual close : ()V
    //   138: goto -> 180
    //   141: astore #8
    //   143: aload #7
    //   145: iload #4
    //   147: ifne -> 162
    //   150: ifnull -> 177
    //   153: goto -> 160
    //   156: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   159: athrow
    //   160: aload #7
    //   162: invokevirtual close : ()V
    //   165: goto -> 177
    //   168: astore #9
    //   170: aload #8
    //   172: aload #9
    //   174: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   177: aload #8
    //   179: athrow
    //   180: aload #6
    //   182: iload #4
    //   184: ifne -> 233
    //   187: ifnull -> 224
    //   190: goto -> 197
    //   193: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   196: athrow
    //   197: aload #6
    //   199: iload #4
    //   201: ifne -> 233
    //   204: goto -> 211
    //   207: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   210: athrow
    //   211: invokevirtual isEmpty : ()Z
    //   214: ifeq -> 237
    //   217: goto -> 224
    //   220: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   223: athrow
    //   224: ldc ''
    //   226: goto -> 233
    //   229: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   232: athrow
    //   233: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   236: return
    //   237: new java/lang/StringBuilder
    //   240: dup
    //   241: invokespecial <init> : ()V
    //   244: astore #7
    //   246: new java/util/Scanner
    //   249: dup
    //   250: new java/io/FileReader
    //   253: dup
    //   254: aload #6
    //   256: invokespecial <init> : (Ljava/lang/String;)V
    //   259: invokespecial <init> : (Ljava/lang/Readable;)V
    //   262: astore #8
    //   264: aload #8
    //   266: invokevirtual hasNextLine : ()Z
    //   269: ifeq -> 306
    //   272: aload #7
    //   274: aload #8
    //   276: invokevirtual nextLine : ()Ljava/lang/String;
    //   279: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   282: ldc_w '\\n'
    //   285: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: pop
    //   289: iload #4
    //   291: ifne -> 360
    //   294: iload #4
    //   296: ifeq -> 264
    //   299: goto -> 306
    //   302: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   305: athrow
    //   306: aload #8
    //   308: invokevirtual close : ()V
    //   311: sipush #22626
    //   314: ldc2_w 4448107426244838424
    //   317: lload_0
    //   318: lxor
    //   319: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   324: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   327: aload #7
    //   329: invokevirtual toString : ()Ljava/lang/String;
    //   332: ldc_w '\\n'
    //   335: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   338: lload_2
    //   339: iconst_2
    //   340: anewarray java/lang/Object
    //   343: dup_x2
    //   344: dup_x2
    //   345: pop
    //   346: invokestatic valueOf : (J)Ljava/lang/Long;
    //   349: iconst_1
    //   350: swap
    //   351: aastore
    //   352: dup_x1
    //   353: swap
    //   354: iconst_0
    //   355: swap
    //   356: aastore
    //   357: invokestatic k : ([Ljava/lang/Object;)V
    //   360: goto -> 370
    //   363: astore #7
    //   365: aload #7
    //   367: invokevirtual printStackTrace : ()V
    //   370: return
    // Exception table:
    //   from	to	target	type
    //   26	116	141	java/lang/Throwable
    //   116	126	129	java/lang/Throwable
    //   143	153	156	java/lang/Throwable
    //   160	165	168	java/lang/Throwable
    //   180	190	193	java/lang/Throwable
    //   187	204	207	java/lang/Throwable
    //   197	217	220	java/lang/Throwable
    //   211	226	229	java/lang/Throwable
    //   237	360	363	java/lang/Exception
    //   272	299	302	java/lang/Throwable
  }
  
  private static void lambda$render$1(int paramInt1, int paramInt2, xp paramxp) {
    // Byte code:
    //   0: getstatic wtf/opal/xq.a : J
    //   3: ldc2_w 113475524070417
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 112045010154436
    //   13: lxor
    //   14: lstore #5
    //   16: pop2
    //   17: aload_2
    //   18: sipush #31662
    //   21: ldc2_w 4978098127333613784
    //   24: lload_3
    //   25: lxor
    //   26: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   31: iconst_1
    //   32: anewarray java/lang/Object
    //   35: dup_x1
    //   36: swap
    //   37: iconst_0
    //   38: swap
    //   39: aastore
    //   40: invokevirtual i : ([Ljava/lang/Object;)V
    //   43: aload_2
    //   44: iconst_0
    //   45: iconst_1
    //   46: anewarray java/lang/Object
    //   49: dup_x1
    //   50: swap
    //   51: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   54: iconst_0
    //   55: swap
    //   56: aastore
    //   57: invokevirtual V : ([Ljava/lang/Object;)V
    //   60: aload_2
    //   61: ldc_w 9.0
    //   64: iconst_1
    //   65: anewarray java/lang/Object
    //   68: dup_x1
    //   69: swap
    //   70: invokestatic valueOf : (F)Ljava/lang/Float;
    //   73: iconst_0
    //   74: swap
    //   75: aastore
    //   76: invokevirtual D : ([Ljava/lang/Object;)V
    //   79: aload_2
    //   80: iconst_m1
    //   81: iconst_1
    //   82: anewarray java/lang/Object
    //   85: dup_x1
    //   86: swap
    //   87: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   90: iconst_0
    //   91: swap
    //   92: aastore
    //   93: invokevirtual F : ([Ljava/lang/Object;)V
    //   96: aload_2
    //   97: iconst_1
    //   98: iconst_1
    //   99: anewarray java/lang/Object
    //   102: dup_x1
    //   103: swap
    //   104: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   107: iconst_0
    //   108: swap
    //   109: aastore
    //   110: invokevirtual v : ([Ljava/lang/Object;)V
    //   113: aload_2
    //   114: ldc_w 5.0
    //   117: iconst_1
    //   118: anewarray java/lang/Object
    //   121: dup_x1
    //   122: swap
    //   123: invokestatic valueOf : (F)Ljava/lang/Float;
    //   126: iconst_0
    //   127: swap
    //   128: aastore
    //   129: invokevirtual e : ([Ljava/lang/Object;)V
    //   132: aload_2
    //   133: sipush #29578
    //   136: ldc2_w 5061582955762957137
    //   139: lload_3
    //   140: lxor
    //   141: <illegal opcode> t : (IJ)I
    //   146: iconst_1
    //   147: anewarray java/lang/Object
    //   150: dup_x1
    //   151: swap
    //   152: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   155: iconst_0
    //   156: swap
    //   157: aastore
    //   158: invokevirtual y : ([Ljava/lang/Object;)V
    //   161: aload_2
    //   162: iload_0
    //   163: iload_1
    //   164: lload #5
    //   166: iconst_3
    //   167: anewarray java/lang/Object
    //   170: dup_x2
    //   171: dup_x2
    //   172: pop
    //   173: invokestatic valueOf : (J)Ljava/lang/Long;
    //   176: iconst_2
    //   177: swap
    //   178: aastore
    //   179: dup_x1
    //   180: swap
    //   181: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   184: iconst_1
    //   185: swap
    //   186: aastore
    //   187: dup_x1
    //   188: swap
    //   189: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   192: iconst_0
    //   193: swap
    //   194: aastore
    //   195: invokevirtual Q : ([Ljava/lang/Object;)V
    //   198: return
  }
  
  private void lambda$render$0(float paramFloat1, float paramFloat2) {
    // Byte code:
    //   0: getstatic wtf/opal/xq.a : J
    //   3: ldc2_w 81182931133212
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 89669151778591
    //   13: lxor
    //   14: lstore #5
    //   16: dup2
    //   17: ldc2_w 48842113968759
    //   20: lxor
    //   21: lstore #7
    //   23: dup2
    //   24: ldc2_w 114319604229952
    //   27: lxor
    //   28: lstore #9
    //   30: dup2
    //   31: ldc2_w 134727930178491
    //   34: lxor
    //   35: lstore #11
    //   37: pop2
    //   38: invokestatic l : ()Z
    //   41: aload_0
    //   42: getfield W : Lwtf/opal/dc;
    //   45: sipush #19401
    //   48: ldc2_w 8614533248562364341
    //   51: lload_3
    //   52: lxor
    //   53: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   58: lload #5
    //   60: iconst_2
    //   61: anewarray java/lang/Object
    //   64: dup_x2
    //   65: dup_x2
    //   66: pop
    //   67: invokestatic valueOf : (J)Ljava/lang/Long;
    //   70: iconst_1
    //   71: swap
    //   72: aastore
    //   73: dup_x1
    //   74: swap
    //   75: iconst_0
    //   76: swap
    //   77: aastore
    //   78: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   81: lload #11
    //   83: sipush #26134
    //   86: ldc2_w 515123112432849511
    //   89: lload_3
    //   90: lxor
    //   91: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   96: ldc_w 30.0
    //   99: iconst_3
    //   100: anewarray java/lang/Object
    //   103: dup_x1
    //   104: swap
    //   105: invokestatic valueOf : (F)Ljava/lang/Float;
    //   108: iconst_2
    //   109: swap
    //   110: aastore
    //   111: dup_x1
    //   112: swap
    //   113: iconst_1
    //   114: swap
    //   115: aastore
    //   116: dup_x2
    //   117: dup_x2
    //   118: pop
    //   119: invokestatic valueOf : (J)Ljava/lang/Long;
    //   122: iconst_0
    //   123: swap
    //   124: aastore
    //   125: invokevirtual q : ([Ljava/lang/Object;)F
    //   128: fstore #14
    //   130: aload_0
    //   131: getfield W : Lwtf/opal/dc;
    //   134: sipush #9365
    //   137: ldc2_w 4471528183024587000
    //   140: lload_3
    //   141: lxor
    //   142: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   147: lload #5
    //   149: iconst_2
    //   150: anewarray java/lang/Object
    //   153: dup_x2
    //   154: dup_x2
    //   155: pop
    //   156: invokestatic valueOf : (J)Ljava/lang/Long;
    //   159: iconst_1
    //   160: swap
    //   161: aastore
    //   162: dup_x1
    //   163: swap
    //   164: iconst_0
    //   165: swap
    //   166: aastore
    //   167: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   170: sipush #4468
    //   173: ldc2_w 1759785452941419800
    //   176: lload_3
    //   177: lxor
    //   178: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   183: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   186: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   189: invokevirtual method_4486 : ()I
    //   192: i2f
    //   193: fconst_2
    //   194: fdiv
    //   195: fload #14
    //   197: fconst_2
    //   198: fdiv
    //   199: fsub
    //   200: ldc_w 15.0
    //   203: lload #9
    //   205: ldc_w 30.0
    //   208: iconst_m1
    //   209: bipush #6
    //   211: anewarray java/lang/Object
    //   214: dup_x1
    //   215: swap
    //   216: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   219: iconst_5
    //   220: swap
    //   221: aastore
    //   222: dup_x1
    //   223: swap
    //   224: invokestatic valueOf : (F)Ljava/lang/Float;
    //   227: iconst_4
    //   228: swap
    //   229: aastore
    //   230: dup_x2
    //   231: dup_x2
    //   232: pop
    //   233: invokestatic valueOf : (J)Ljava/lang/Long;
    //   236: iconst_3
    //   237: swap
    //   238: aastore
    //   239: dup_x1
    //   240: swap
    //   241: invokestatic valueOf : (F)Ljava/lang/Float;
    //   244: iconst_2
    //   245: swap
    //   246: aastore
    //   247: dup_x1
    //   248: swap
    //   249: invokestatic valueOf : (F)Ljava/lang/Float;
    //   252: iconst_1
    //   253: swap
    //   254: aastore
    //   255: dup_x1
    //   256: swap
    //   257: iconst_0
    //   258: swap
    //   259: aastore
    //   260: invokevirtual C : ([Ljava/lang/Object;)V
    //   263: istore #13
    //   265: aload_0
    //   266: iload #13
    //   268: ifne -> 386
    //   271: getfield q : Lwtf/opal/pa;
    //   274: ldc 150.0
    //   276: ldc 55.0
    //   278: fload_1
    //   279: lload #7
    //   281: fload_2
    //   282: fconst_1
    //   283: sipush #19032
    //   286: ldc2_w 6460137115413687693
    //   289: lload_3
    //   290: lxor
    //   291: <illegal opcode> t : (IJ)I
    //   296: bipush #7
    //   298: anewarray java/lang/Object
    //   301: dup_x1
    //   302: swap
    //   303: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   306: bipush #6
    //   308: swap
    //   309: aastore
    //   310: dup_x1
    //   311: swap
    //   312: invokestatic valueOf : (F)Ljava/lang/Float;
    //   315: iconst_5
    //   316: swap
    //   317: aastore
    //   318: dup_x1
    //   319: swap
    //   320: invokestatic valueOf : (F)Ljava/lang/Float;
    //   323: iconst_4
    //   324: swap
    //   325: aastore
    //   326: dup_x2
    //   327: dup_x2
    //   328: pop
    //   329: invokestatic valueOf : (J)Ljava/lang/Long;
    //   332: iconst_3
    //   333: swap
    //   334: aastore
    //   335: dup_x1
    //   336: swap
    //   337: invokestatic valueOf : (F)Ljava/lang/Float;
    //   340: iconst_2
    //   341: swap
    //   342: aastore
    //   343: dup_x1
    //   344: swap
    //   345: invokestatic valueOf : (F)Ljava/lang/Float;
    //   348: iconst_1
    //   349: swap
    //   350: aastore
    //   351: dup_x1
    //   352: swap
    //   353: invokestatic valueOf : (F)Ljava/lang/Float;
    //   356: iconst_0
    //   357: swap
    //   358: aastore
    //   359: invokevirtual P : ([Ljava/lang/Object;)V
    //   362: getstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   365: invokevirtual isEmpty : ()Z
    //   368: ifne -> 591
    //   371: goto -> 378
    //   374: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   377: athrow
    //   378: aload_0
    //   379: goto -> 386
    //   382: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   385: athrow
    //   386: getfield W : Lwtf/opal/dc;
    //   389: sipush #14956
    //   392: ldc2_w 9099875581586017823
    //   395: lload_3
    //   396: lxor
    //   397: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   402: lload #5
    //   404: iconst_2
    //   405: anewarray java/lang/Object
    //   408: dup_x2
    //   409: dup_x2
    //   410: pop
    //   411: invokestatic valueOf : (J)Ljava/lang/Long;
    //   414: iconst_1
    //   415: swap
    //   416: aastore
    //   417: dup_x1
    //   418: swap
    //   419: iconst_0
    //   420: swap
    //   421: aastore
    //   422: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   425: lload #11
    //   427: getstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   430: ldc_w 15.0
    //   433: iconst_3
    //   434: anewarray java/lang/Object
    //   437: dup_x1
    //   438: swap
    //   439: invokestatic valueOf : (F)Ljava/lang/Float;
    //   442: iconst_2
    //   443: swap
    //   444: aastore
    //   445: dup_x1
    //   446: swap
    //   447: iconst_1
    //   448: swap
    //   449: aastore
    //   450: dup_x2
    //   451: dup_x2
    //   452: pop
    //   453: invokestatic valueOf : (J)Ljava/lang/Long;
    //   456: iconst_0
    //   457: swap
    //   458: aastore
    //   459: invokevirtual q : ([Ljava/lang/Object;)F
    //   462: fstore #15
    //   464: aload_0
    //   465: getfield W : Lwtf/opal/dc;
    //   468: sipush #18096
    //   471: ldc2_w 1409474653175182022
    //   474: lload_3
    //   475: lxor
    //   476: <illegal opcode> o : (IJ)Ljava/lang/String;
    //   481: lload #5
    //   483: iconst_2
    //   484: anewarray java/lang/Object
    //   487: dup_x2
    //   488: dup_x2
    //   489: pop
    //   490: invokestatic valueOf : (J)Ljava/lang/Long;
    //   493: iconst_1
    //   494: swap
    //   495: aastore
    //   496: dup_x1
    //   497: swap
    //   498: iconst_0
    //   499: swap
    //   500: aastore
    //   501: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   504: getstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   507: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   510: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   513: invokevirtual method_4486 : ()I
    //   516: i2f
    //   517: fconst_2
    //   518: fdiv
    //   519: fload #15
    //   521: fconst_2
    //   522: fdiv
    //   523: fsub
    //   524: ldc 55.0
    //   526: fload_2
    //   527: fadd
    //   528: ldc 20.0
    //   530: fadd
    //   531: lload #9
    //   533: ldc_w 15.0
    //   536: iconst_m1
    //   537: bipush #6
    //   539: anewarray java/lang/Object
    //   542: dup_x1
    //   543: swap
    //   544: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   547: iconst_5
    //   548: swap
    //   549: aastore
    //   550: dup_x1
    //   551: swap
    //   552: invokestatic valueOf : (F)Ljava/lang/Float;
    //   555: iconst_4
    //   556: swap
    //   557: aastore
    //   558: dup_x2
    //   559: dup_x2
    //   560: pop
    //   561: invokestatic valueOf : (J)Ljava/lang/Long;
    //   564: iconst_3
    //   565: swap
    //   566: aastore
    //   567: dup_x1
    //   568: swap
    //   569: invokestatic valueOf : (F)Ljava/lang/Float;
    //   572: iconst_2
    //   573: swap
    //   574: aastore
    //   575: dup_x1
    //   576: swap
    //   577: invokestatic valueOf : (F)Ljava/lang/Float;
    //   580: iconst_1
    //   581: swap
    //   582: aastore
    //   583: dup_x1
    //   584: swap
    //   585: iconst_0
    //   586: swap
    //   587: aastore
    //   588: invokevirtual C : ([Ljava/lang/Object;)V
    //   591: return
    // Exception table:
    //   from	to	target	type
    //   265	371	374	java/lang/RuntimeException
    //   271	379	382	java/lang/RuntimeException
  }
  
  static {
    u(true);
    long l = a ^ 0x68E6149377D6L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[19];
    boolean bool = false;
    String str;
    int i = (str = "cÐ\\­~Í²G$ÇÇ¸7ýì((¸ |\022WVNhë:mx\\e÷½º< {`<G\005\\­ÌÓQÖ\036\\\b\fß(«=gíï~Å23Ë}ÝKý!Û²×VÅø\037¼k\000'\026 ú\024Üáaé¤/\023A ,Y-Ô]zKë\007+þs'}QRo5retHþXføc°çÂâ\030\032Ú<e82ädù}Í\rÙ\037»Ô!40w \025tÛP¾.\n+$Ü\006=\032Jº¤ezu+\f_\000í»g?¯ZÀ(A°Íð\021l³ÍÞ*téÑß@¹B«Â)|òB0¢£µjûøðåLH²5i(^ÿsÍõíËÔBòG\025*¶CÏ\021ï­øÀ­ÜB~Sý!\022;ô²ï'F\030bü£{im\030\004Ë×èïåÉ×§ì*\030Å£]¼\023½ÇÕ2\nÓ\025ÐÊ;\027(Ì\f1Ãp(ª³²\031\035É²#\024\035Rt²-\034u8¹^iµÀRE\030 lR\006ó=(AMlgÇEÛ\020\0259OQT½Fª\rØ#%ÆÌÞ\006\003¼Ie\001ã\001\022è@ ¶YÐ´Ó@dòf{Í\rlÚQÜ£;\031Áyú%¥a\030O\036/Å1(ÆÿùËO\025Dy\022\034Ô;Û\035©%W]\b+:cîÙé¹½)Â¹S«³¹ \003ÏJh xm\017$¦ÜN\033\0036\000o¾G¿9Ô/«·(\004_ãhJíþ\004Ä\020S>|\"°×iü¾Ø\016 Ö©RßªNÆ­ç<(ò§òÛ¥b<éÆUTÆ\fzümà¯r\fxÞ\023Qs\004ú·ûySf,u\021").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  public static void u(boolean paramBoolean) {
    k = paramBoolean;
  }
  
  public static boolean W() {
    return k;
  }
  
  public static boolean l() {
    boolean bool = W();
    try {
      if (!bool)
        return true; 
    } catch (RuntimeException runtimeException) {
      throw a(null);
    } 
    return false;
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x26C0;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/xq", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
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
    //   66: ldc_w 'wtf/opal/xq'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x756B;
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
        throw new RuntimeException("wtf/opal/xq", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   66: ldc_w 'wtf/opal/xq'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xq.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */