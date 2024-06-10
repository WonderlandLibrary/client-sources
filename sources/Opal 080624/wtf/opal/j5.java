package wtf.opal;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1735;
import net.minecraft.class_3545;

public final class j5 extends d {
  private final kr L;
  
  private final kt W;
  
  private final kt R;
  
  private final kt A;
  
  private final kt k;
  
  private final kt B;
  
  private final ke n;
  
  private final ke D;
  
  private final gm<lz> Z;
  
  private static final long a = on.a(-6600255557584608378L, -2304326817406102119L, MethodHandles.lookup().lookupClass()).a(186743440602072L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] g;
  
  private static final Integer[] l;
  
  private static final Map m;
  
  public j5(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j5.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 81678929002593
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 14647138267933
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 38675627207240
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #21330
    //   32: ldc2_w 1503418366352597168
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #19724
    //   47: ldc2_w 8338733162310123243
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.PLAYER : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/kr
    //   67: dup
    //   68: invokespecial <init> : ()V
    //   71: putfield L : Lwtf/opal/kr;
    //   74: invokestatic n : ()Z
    //   77: aload_0
    //   78: new wtf/opal/kt
    //   81: dup
    //   82: sipush #31641
    //   85: ldc2_w 4141304848055306364
    //   88: lload_1
    //   89: lxor
    //   90: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   95: ldc2_w 50.0
    //   98: dconst_0
    //   99: ldc2_w 250.0
    //   102: dconst_1
    //   103: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   106: putfield W : Lwtf/opal/kt;
    //   109: aload_0
    //   110: new wtf/opal/kt
    //   113: dup
    //   114: sipush #4591
    //   117: ldc2_w 4278613142329123339
    //   120: lload_1
    //   121: lxor
    //   122: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   127: dconst_1
    //   128: dconst_1
    //   129: ldc2_w 9.0
    //   132: dconst_1
    //   133: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   136: putfield R : Lwtf/opal/kt;
    //   139: aload_0
    //   140: new wtf/opal/kt
    //   143: dup
    //   144: sipush #27623
    //   147: ldc2_w 6360557937446721537
    //   150: lload_1
    //   151: lxor
    //   152: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   157: ldc2_w 2.0
    //   160: dconst_1
    //   161: ldc2_w 9.0
    //   164: dconst_1
    //   165: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   168: putfield A : Lwtf/opal/kt;
    //   171: aload_0
    //   172: new wtf/opal/kt
    //   175: dup
    //   176: sipush #28778
    //   179: ldc2_w 6601594135229399946
    //   182: lload_1
    //   183: lxor
    //   184: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   189: ldc2_w 4.0
    //   192: dconst_1
    //   193: ldc2_w 9.0
    //   196: dconst_1
    //   197: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   200: putfield k : Lwtf/opal/kt;
    //   203: aload_0
    //   204: new wtf/opal/kt
    //   207: dup
    //   208: sipush #16800
    //   211: ldc2_w 7322841785410269775
    //   214: lload_1
    //   215: lxor
    //   216: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   221: ldc2_w 3.0
    //   224: dconst_1
    //   225: ldc2_w 9.0
    //   228: dconst_1
    //   229: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   232: putfield B : Lwtf/opal/kt;
    //   235: istore #9
    //   237: aload_0
    //   238: new wtf/opal/ke
    //   241: dup
    //   242: sipush #25257
    //   245: ldc2_w 5092342638244172103
    //   248: lload_1
    //   249: lxor
    //   250: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   255: iconst_1
    //   256: invokespecial <init> : (Ljava/lang/String;Z)V
    //   259: putfield n : Lwtf/opal/ke;
    //   262: aload_0
    //   263: new wtf/opal/ke
    //   266: dup
    //   267: sipush #6639
    //   270: ldc2_w 3535968417871325708
    //   273: lload_1
    //   274: lxor
    //   275: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   280: iconst_1
    //   281: invokespecial <init> : (Ljava/lang/String;Z)V
    //   284: putfield D : Lwtf/opal/ke;
    //   287: aload_0
    //   288: aload_0
    //   289: <illegal opcode> H : (Lwtf/opal/j5;)Lwtf/opal/gm;
    //   294: putfield Z : Lwtf/opal/gm;
    //   297: aload_0
    //   298: getfield A : Lwtf/opal/kt;
    //   301: aload_0
    //   302: getfield n : Lwtf/opal/ke;
    //   305: aload_0
    //   306: <illegal opcode> test : (Lwtf/opal/j5;)Ljava/util/function/Predicate;
    //   311: lload_3
    //   312: dup2_x1
    //   313: pop2
    //   314: iconst_3
    //   315: anewarray java/lang/Object
    //   318: dup_x1
    //   319: swap
    //   320: iconst_2
    //   321: swap
    //   322: aastore
    //   323: dup_x2
    //   324: dup_x2
    //   325: pop
    //   326: invokestatic valueOf : (J)Ljava/lang/Long;
    //   329: iconst_1
    //   330: swap
    //   331: aastore
    //   332: dup_x1
    //   333: swap
    //   334: iconst_0
    //   335: swap
    //   336: aastore
    //   337: invokevirtual C : ([Ljava/lang/Object;)V
    //   340: aload_0
    //   341: getfield B : Lwtf/opal/kt;
    //   344: aload_0
    //   345: getfield n : Lwtf/opal/ke;
    //   348: aload_0
    //   349: <illegal opcode> test : (Lwtf/opal/j5;)Ljava/util/function/Predicate;
    //   354: lload_3
    //   355: dup2_x1
    //   356: pop2
    //   357: iconst_3
    //   358: anewarray java/lang/Object
    //   361: dup_x1
    //   362: swap
    //   363: iconst_2
    //   364: swap
    //   365: aastore
    //   366: dup_x2
    //   367: dup_x2
    //   368: pop
    //   369: invokestatic valueOf : (J)Ljava/lang/Long;
    //   372: iconst_1
    //   373: swap
    //   374: aastore
    //   375: dup_x1
    //   376: swap
    //   377: iconst_0
    //   378: swap
    //   379: aastore
    //   380: invokevirtual C : ([Ljava/lang/Object;)V
    //   383: aload_0
    //   384: sipush #30523
    //   387: ldc2_w 231656931003067446
    //   390: lload_1
    //   391: lxor
    //   392: <illegal opcode> z : (IJ)I
    //   397: anewarray wtf/opal/k3
    //   400: dup
    //   401: iconst_0
    //   402: aload_0
    //   403: getfield W : Lwtf/opal/kt;
    //   406: aastore
    //   407: dup
    //   408: iconst_1
    //   409: aload_0
    //   410: getfield R : Lwtf/opal/kt;
    //   413: aastore
    //   414: dup
    //   415: iconst_2
    //   416: aload_0
    //   417: getfield A : Lwtf/opal/kt;
    //   420: aastore
    //   421: dup
    //   422: iconst_3
    //   423: aload_0
    //   424: getfield k : Lwtf/opal/kt;
    //   427: aastore
    //   428: dup
    //   429: iconst_4
    //   430: aload_0
    //   431: getfield B : Lwtf/opal/kt;
    //   434: aastore
    //   435: dup
    //   436: iconst_5
    //   437: aload_0
    //   438: getfield n : Lwtf/opal/ke;
    //   441: aastore
    //   442: dup
    //   443: sipush #10491
    //   446: ldc2_w 5403285296566612981
    //   449: lload_1
    //   450: lxor
    //   451: <illegal opcode> z : (IJ)I
    //   456: aload_0
    //   457: getfield D : Lwtf/opal/ke;
    //   460: aastore
    //   461: lload #5
    //   463: dup2_x1
    //   464: pop2
    //   465: iconst_2
    //   466: anewarray java/lang/Object
    //   469: dup_x1
    //   470: swap
    //   471: iconst_1
    //   472: swap
    //   473: aastore
    //   474: dup_x2
    //   475: dup_x2
    //   476: pop
    //   477: invokestatic valueOf : (J)Ljava/lang/Long;
    //   480: iconst_0
    //   481: swap
    //   482: aastore
    //   483: invokevirtual o : ([Ljava/lang/Object;)V
    //   486: iload #9
    //   488: ifeq -> 505
    //   491: iconst_2
    //   492: anewarray wtf/opal/d
    //   495: invokestatic p : ([Lwtf/opal/d;)V
    //   498: goto -> 505
    //   501: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   504: athrow
    //   505: return
    // Exception table:
    //   from	to	target	type
    //   237	498	501	wtf/opal/x5
  }
  
  private class_1735 h(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j5.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 72391920099635
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: new java/util/ArrayList
    //   30: dup
    //   31: invokespecial <init> : ()V
    //   34: astore #7
    //   36: invokestatic k : ()Z
    //   39: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   42: getfield field_1724 : Lnet/minecraft/class_746;
    //   45: getfield field_7498 : Lnet/minecraft/class_1723;
    //   48: getfield field_7761 : Lnet/minecraft/class_2371;
    //   51: invokevirtual iterator : ()Ljava/util/Iterator;
    //   54: astore #8
    //   56: istore #6
    //   58: aload #8
    //   60: invokeinterface hasNext : ()Z
    //   65: ifeq -> 128
    //   68: aload #8
    //   70: invokeinterface next : ()Ljava/lang/Object;
    //   75: checkcast net/minecraft/class_1735
    //   78: astore #9
    //   80: aload #9
    //   82: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   85: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   88: instanceof net/minecraft/class_1829
    //   91: iload #6
    //   93: ifeq -> 122
    //   96: ifeq -> 123
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: aload #7
    //   108: aload #9
    //   110: invokeinterface add : (Ljava/lang/Object;)Z
    //   115: goto -> 122
    //   118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   121: athrow
    //   122: pop
    //   123: iload #6
    //   125: ifne -> 58
    //   128: new java/util/ArrayList
    //   131: dup
    //   132: invokespecial <init> : ()V
    //   135: lload_2
    //   136: lconst_0
    //   137: lcmp
    //   138: ifle -> 75
    //   141: astore #8
    //   143: aload #7
    //   145: invokeinterface iterator : ()Ljava/util/Iterator;
    //   150: astore #9
    //   152: aload #9
    //   154: invokeinterface hasNext : ()Z
    //   159: ifeq -> 254
    //   162: aload #9
    //   164: invokeinterface next : ()Ljava/lang/Object;
    //   169: checkcast net/minecraft/class_1735
    //   172: astore #10
    //   174: aload #8
    //   176: new net/minecraft/class_3545
    //   179: dup
    //   180: aload #10
    //   182: aload_0
    //   183: aload #10
    //   185: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   188: lload #4
    //   190: dup2_x1
    //   191: pop2
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup_x1
    //   197: swap
    //   198: iconst_1
    //   199: swap
    //   200: aastore
    //   201: dup_x2
    //   202: dup_x2
    //   203: pop
    //   204: invokestatic valueOf : (J)Ljava/lang/Long;
    //   207: iconst_0
    //   208: swap
    //   209: aastore
    //   210: invokevirtual n : ([Ljava/lang/Object;)F
    //   213: invokestatic valueOf : (F)Ljava/lang/Float;
    //   216: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   219: invokeinterface add : (Ljava/lang/Object;)Z
    //   224: pop
    //   225: lload_2
    //   226: lconst_0
    //   227: lcmp
    //   228: iflt -> 274
    //   231: iload #6
    //   233: ifeq -> 274
    //   236: iload #6
    //   238: ifne -> 152
    //   241: lload_2
    //   242: lconst_0
    //   243: lcmp
    //   244: iflt -> 225
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload #8
    //   256: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   261: invokestatic comparing : (Ljava/util/function/Function;)Ljava/util/Comparator;
    //   264: invokeinterface reversed : ()Ljava/util/Comparator;
    //   269: invokeinterface sort : (Ljava/util/Comparator;)V
    //   274: aload #8
    //   276: iload #6
    //   278: ifeq -> 316
    //   281: invokeinterface isEmpty : ()Z
    //   286: ifeq -> 302
    //   289: goto -> 296
    //   292: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   295: athrow
    //   296: aconst_null
    //   297: areturn
    //   298: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   301: athrow
    //   302: aload #8
    //   304: iconst_0
    //   305: invokeinterface get : (I)Ljava/lang/Object;
    //   310: checkcast net/minecraft/class_3545
    //   313: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   316: checkcast net/minecraft/class_1735
    //   319: areturn
    // Exception table:
    //   from	to	target	type
    //   80	99	102	wtf/opal/x5
    //   96	115	118	wtf/opal/x5
    //   174	241	250	wtf/opal/x5
    //   274	289	292	wtf/opal/x5
    //   281	298	298	wtf/opal/x5
  }
  
  private float n(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_1799
    //   17: astore_2
    //   18: pop
    //   19: getstatic wtf/opal/j5.a : J
    //   22: lload_3
    //   23: lxor
    //   24: lstore_3
    //   25: aload_2
    //   26: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   29: astore #6
    //   31: invokestatic n : ()Z
    //   34: new java/util/concurrent/atomic/AtomicInteger
    //   37: dup
    //   38: aload #6
    //   40: checkcast net/minecraft/class_1829
    //   43: invokevirtual method_8022 : ()Lnet/minecraft/class_1832;
    //   46: invokeinterface method_8028 : ()F
    //   51: aload_2
    //   52: getstatic net/minecraft/class_1299.field_6097 : Lnet/minecraft/class_1299;
    //   55: invokestatic method_8218 : (Lnet/minecraft/class_1799;Lnet/minecraft/class_1299;)F
    //   58: fadd
    //   59: ldc_w 100.0
    //   62: fmul
    //   63: f2i
    //   64: invokespecial <init> : (I)V
    //   67: astore #7
    //   69: aload_2
    //   70: invokestatic method_57532 : (Lnet/minecraft/class_1799;)Lnet/minecraft/class_9304;
    //   73: invokevirtual method_57539 : ()Ljava/util/Set;
    //   76: astore #8
    //   78: aload #8
    //   80: aload #7
    //   82: <illegal opcode> accept : (Ljava/util/concurrent/atomic/AtomicInteger;)Ljava/util/function/Consumer;
    //   87: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   92: istore #5
    //   94: aload_2
    //   95: invokevirtual method_7919 : ()I
    //   98: i2f
    //   99: aload_2
    //   100: invokevirtual method_7936 : ()I
    //   103: i2f
    //   104: fdiv
    //   105: f2d
    //   106: ldc2_w 0.75
    //   109: dcmpl
    //   110: iload #5
    //   112: ifne -> 193
    //   115: ifle -> 151
    //   118: goto -> 125
    //   121: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   124: athrow
    //   125: aload #7
    //   127: sipush #15828
    //   130: ldc2_w 3102264625964684184
    //   133: lload_3
    //   134: lxor
    //   135: <illegal opcode> z : (IJ)I
    //   140: invokevirtual addAndGet : (I)I
    //   143: pop
    //   144: goto -> 151
    //   147: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   150: athrow
    //   151: aload #7
    //   153: aload_2
    //   154: invokevirtual method_7936 : ()I
    //   157: sipush #7369
    //   160: ldc2_w 3304087943853716110
    //   163: lload_3
    //   164: lxor
    //   165: <illegal opcode> z : (IJ)I
    //   170: idiv
    //   171: aload_2
    //   172: invokevirtual method_7919 : ()I
    //   175: sipush #16280
    //   178: ldc2_w 6492363535243252177
    //   181: lload_3
    //   182: lxor
    //   183: <illegal opcode> z : (IJ)I
    //   188: idiv
    //   189: isub
    //   190: invokevirtual addAndGet : (I)I
    //   193: i2f
    //   194: freturn
    // Exception table:
    //   from	to	target	type
    //   94	118	121	wtf/opal/x5
    //   115	144	147	wtf/opal/x5
  }
  
  private class_1735 Q(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j5.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 42841298371381
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic k : ()Z
    //   30: new java/util/ArrayList
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: astore #7
    //   39: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   42: getfield field_1724 : Lnet/minecraft/class_746;
    //   45: getfield field_7498 : Lnet/minecraft/class_1723;
    //   48: getfield field_7761 : Lnet/minecraft/class_2371;
    //   51: invokevirtual iterator : ()Ljava/util/Iterator;
    //   54: astore #8
    //   56: istore #6
    //   58: aload #8
    //   60: invokeinterface hasNext : ()Z
    //   65: ifeq -> 128
    //   68: aload #8
    //   70: invokeinterface next : ()Ljava/lang/Object;
    //   75: checkcast net/minecraft/class_1735
    //   78: astore #9
    //   80: aload #9
    //   82: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   85: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   88: instanceof net/minecraft/class_1810
    //   91: iload #6
    //   93: ifeq -> 122
    //   96: ifeq -> 123
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: aload #7
    //   108: aload #9
    //   110: invokeinterface add : (Ljava/lang/Object;)Z
    //   115: goto -> 122
    //   118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   121: athrow
    //   122: pop
    //   123: iload #6
    //   125: ifne -> 58
    //   128: new java/util/ArrayList
    //   131: dup
    //   132: invokespecial <init> : ()V
    //   135: lload_2
    //   136: lconst_0
    //   137: lcmp
    //   138: ifle -> 75
    //   141: astore #8
    //   143: aload #7
    //   145: invokeinterface iterator : ()Ljava/util/Iterator;
    //   150: astore #9
    //   152: aload #9
    //   154: invokeinterface hasNext : ()Z
    //   159: ifeq -> 254
    //   162: aload #9
    //   164: invokeinterface next : ()Ljava/lang/Object;
    //   169: checkcast net/minecraft/class_1735
    //   172: astore #10
    //   174: aload #8
    //   176: new net/minecraft/class_3545
    //   179: dup
    //   180: aload #10
    //   182: aload_0
    //   183: aload #10
    //   185: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   188: lload #4
    //   190: dup2_x1
    //   191: pop2
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup_x1
    //   197: swap
    //   198: iconst_1
    //   199: swap
    //   200: aastore
    //   201: dup_x2
    //   202: dup_x2
    //   203: pop
    //   204: invokestatic valueOf : (J)Ljava/lang/Long;
    //   207: iconst_0
    //   208: swap
    //   209: aastore
    //   210: invokevirtual x : ([Ljava/lang/Object;)F
    //   213: invokestatic valueOf : (F)Ljava/lang/Float;
    //   216: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   219: invokeinterface add : (Ljava/lang/Object;)Z
    //   224: pop
    //   225: lload_2
    //   226: lconst_0
    //   227: lcmp
    //   228: iflt -> 274
    //   231: iload #6
    //   233: ifeq -> 274
    //   236: iload #6
    //   238: ifne -> 152
    //   241: lload_2
    //   242: lconst_0
    //   243: lcmp
    //   244: ifle -> 225
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload #8
    //   256: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   261: invokestatic comparing : (Ljava/util/function/Function;)Ljava/util/Comparator;
    //   264: invokeinterface reversed : ()Ljava/util/Comparator;
    //   269: invokeinterface sort : (Ljava/util/Comparator;)V
    //   274: aload #8
    //   276: iload #6
    //   278: ifeq -> 316
    //   281: invokeinterface isEmpty : ()Z
    //   286: ifeq -> 302
    //   289: goto -> 296
    //   292: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   295: athrow
    //   296: aconst_null
    //   297: areturn
    //   298: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   301: athrow
    //   302: aload #8
    //   304: iconst_0
    //   305: invokeinterface get : (I)Ljava/lang/Object;
    //   310: checkcast net/minecraft/class_3545
    //   313: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   316: checkcast net/minecraft/class_1735
    //   319: areturn
    // Exception table:
    //   from	to	target	type
    //   80	99	102	wtf/opal/x5
    //   96	115	118	wtf/opal/x5
    //   174	241	250	wtf/opal/x5
    //   274	289	292	wtf/opal/x5
    //   281	298	298	wtf/opal/x5
  }
  
  private class_1735 Y(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j5.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 6086863318542
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic n : ()Z
    //   30: new java/util/ArrayList
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: astore #7
    //   39: istore #6
    //   41: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   44: getfield field_1724 : Lnet/minecraft/class_746;
    //   47: getfield field_7498 : Lnet/minecraft/class_1723;
    //   50: getfield field_7761 : Lnet/minecraft/class_2371;
    //   53: invokevirtual iterator : ()Ljava/util/Iterator;
    //   56: astore #8
    //   58: aload #8
    //   60: invokeinterface hasNext : ()Z
    //   65: ifeq -> 128
    //   68: aload #8
    //   70: invokeinterface next : ()Ljava/lang/Object;
    //   75: checkcast net/minecraft/class_1735
    //   78: astore #9
    //   80: aload #9
    //   82: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   85: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   88: instanceof net/minecraft/class_1743
    //   91: iload #6
    //   93: ifne -> 122
    //   96: ifeq -> 123
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: aload #7
    //   108: aload #9
    //   110: invokeinterface add : (Ljava/lang/Object;)Z
    //   115: goto -> 122
    //   118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   121: athrow
    //   122: pop
    //   123: iload #6
    //   125: ifeq -> 58
    //   128: new java/util/ArrayList
    //   131: dup
    //   132: invokespecial <init> : ()V
    //   135: lload_2
    //   136: lconst_0
    //   137: lcmp
    //   138: ifle -> 75
    //   141: astore #8
    //   143: aload #7
    //   145: invokeinterface iterator : ()Ljava/util/Iterator;
    //   150: astore #9
    //   152: aload #9
    //   154: invokeinterface hasNext : ()Z
    //   159: ifeq -> 254
    //   162: aload #9
    //   164: invokeinterface next : ()Ljava/lang/Object;
    //   169: checkcast net/minecraft/class_1735
    //   172: astore #10
    //   174: aload #8
    //   176: new net/minecraft/class_3545
    //   179: dup
    //   180: aload #10
    //   182: aload_0
    //   183: aload #10
    //   185: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   188: lload #4
    //   190: dup2_x1
    //   191: pop2
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup_x1
    //   197: swap
    //   198: iconst_1
    //   199: swap
    //   200: aastore
    //   201: dup_x2
    //   202: dup_x2
    //   203: pop
    //   204: invokestatic valueOf : (J)Ljava/lang/Long;
    //   207: iconst_0
    //   208: swap
    //   209: aastore
    //   210: invokevirtual x : ([Ljava/lang/Object;)F
    //   213: invokestatic valueOf : (F)Ljava/lang/Float;
    //   216: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   219: invokeinterface add : (Ljava/lang/Object;)Z
    //   224: pop
    //   225: lload_2
    //   226: lconst_0
    //   227: lcmp
    //   228: iflt -> 274
    //   231: iload #6
    //   233: ifne -> 274
    //   236: iload #6
    //   238: ifeq -> 152
    //   241: lload_2
    //   242: lconst_0
    //   243: lcmp
    //   244: ifle -> 225
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload #8
    //   256: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   261: invokestatic comparing : (Ljava/util/function/Function;)Ljava/util/Comparator;
    //   264: invokeinterface reversed : ()Ljava/util/Comparator;
    //   269: invokeinterface sort : (Ljava/util/Comparator;)V
    //   274: aload #8
    //   276: iload #6
    //   278: ifne -> 316
    //   281: invokeinterface isEmpty : ()Z
    //   286: ifeq -> 302
    //   289: goto -> 296
    //   292: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   295: athrow
    //   296: aconst_null
    //   297: areturn
    //   298: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   301: athrow
    //   302: aload #8
    //   304: iconst_0
    //   305: invokeinterface get : (I)Ljava/lang/Object;
    //   310: checkcast net/minecraft/class_3545
    //   313: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   316: checkcast net/minecraft/class_1735
    //   319: areturn
    // Exception table:
    //   from	to	target	type
    //   80	99	102	wtf/opal/x5
    //   96	115	118	wtf/opal/x5
    //   174	241	250	wtf/opal/x5
    //   274	289	292	wtf/opal/x5
    //   281	298	298	wtf/opal/x5
  }
  
  private class_1735 M(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j5.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 69427528474904
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic n : ()Z
    //   30: new java/util/ArrayList
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: astore #7
    //   39: istore #6
    //   41: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   44: getfield field_1724 : Lnet/minecraft/class_746;
    //   47: getfield field_7498 : Lnet/minecraft/class_1723;
    //   50: getfield field_7761 : Lnet/minecraft/class_2371;
    //   53: invokevirtual iterator : ()Ljava/util/Iterator;
    //   56: astore #8
    //   58: aload #8
    //   60: invokeinterface hasNext : ()Z
    //   65: ifeq -> 254
    //   68: aload #8
    //   70: invokeinterface next : ()Ljava/lang/Object;
    //   75: checkcast net/minecraft/class_1735
    //   78: astore #9
    //   80: aload #9
    //   82: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   85: astore #10
    //   87: aload #10
    //   89: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   92: astore #12
    //   94: aload #12
    //   96: iload #6
    //   98: ifne -> 134
    //   101: instanceof net/minecraft/class_1747
    //   104: lload_2
    //   105: lconst_0
    //   106: lcmp
    //   107: ifle -> 299
    //   110: iload #6
    //   112: ifne -> 299
    //   115: goto -> 122
    //   118: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   121: athrow
    //   122: ifeq -> 249
    //   125: goto -> 132
    //   128: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: aload #12
    //   134: checkcast net/minecraft/class_1747
    //   137: astore #11
    //   139: aload #10
    //   141: invokevirtual method_7947 : ()I
    //   144: iload #6
    //   146: lload_2
    //   147: lconst_0
    //   148: lcmp
    //   149: iflt -> 204
    //   152: ifne -> 202
    //   155: ifle -> 249
    //   158: goto -> 165
    //   161: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   164: athrow
    //   165: aload #11
    //   167: invokevirtual method_7711 : ()Lnet/minecraft/class_2248;
    //   170: lload #4
    //   172: dup2_x1
    //   173: pop2
    //   174: iconst_2
    //   175: anewarray java/lang/Object
    //   178: dup_x1
    //   179: swap
    //   180: iconst_1
    //   181: swap
    //   182: aastore
    //   183: dup_x2
    //   184: dup_x2
    //   185: pop
    //   186: invokestatic valueOf : (J)Ljava/lang/Long;
    //   189: iconst_0
    //   190: swap
    //   191: aastore
    //   192: invokestatic o : ([Ljava/lang/Object;)Z
    //   195: goto -> 202
    //   198: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   201: athrow
    //   202: iload #6
    //   204: ifne -> 248
    //   207: ifeq -> 249
    //   210: goto -> 217
    //   213: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   216: athrow
    //   217: aload #7
    //   219: new net/minecraft/class_3545
    //   222: dup
    //   223: aload #9
    //   225: aload #10
    //   227: invokevirtual method_7947 : ()I
    //   230: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   233: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/Object;)V
    //   236: invokeinterface add : (Ljava/lang/Object;)Z
    //   241: goto -> 248
    //   244: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   247: athrow
    //   248: pop
    //   249: iload #6
    //   251: ifeq -> 58
    //   254: aload #7
    //   256: <illegal opcode> apply : ()Ljava/util/function/Function;
    //   261: invokestatic comparing : (Ljava/util/function/Function;)Ljava/util/Comparator;
    //   264: invokeinterface reversed : ()Ljava/util/Comparator;
    //   269: invokeinterface sort : (Ljava/util/Comparator;)V
    //   274: aload #7
    //   276: iload #6
    //   278: lload_2
    //   279: lconst_0
    //   280: lcmp
    //   281: ifle -> 311
    //   284: ifne -> 322
    //   287: invokeinterface isEmpty : ()Z
    //   292: goto -> 299
    //   295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: ifeq -> 308
    //   302: aconst_null
    //   303: areturn
    //   304: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   307: athrow
    //   308: aload #7
    //   310: iconst_0
    //   311: invokeinterface get : (I)Ljava/lang/Object;
    //   316: checkcast net/minecraft/class_3545
    //   319: invokevirtual method_15442 : ()Ljava/lang/Object;
    //   322: checkcast net/minecraft/class_1735
    //   325: areturn
    // Exception table:
    //   from	to	target	type
    //   94	115	118	wtf/opal/x5
    //   101	125	128	wtf/opal/x5
    //   139	158	161	wtf/opal/x5
    //   155	195	198	wtf/opal/x5
    //   202	210	213	wtf/opal/x5
    //   207	241	244	wtf/opal/x5
    //   254	292	295	wtf/opal/x5
    //   299	304	304	wtf/opal/x5
  }
  
  private float x(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_1799
    //   17: astore #4
    //   19: pop
    //   20: getstatic wtf/opal/j5.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: invokestatic k : ()Z
    //   29: aload #4
    //   31: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   34: astore #6
    //   36: istore #5
    //   38: aload #6
    //   40: iload #5
    //   42: ifeq -> 67
    //   45: instanceof net/minecraft/class_1831
    //   48: ifeq -> 77
    //   51: goto -> 58
    //   54: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   57: athrow
    //   58: aload #6
    //   60: goto -> 67
    //   63: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   66: athrow
    //   67: checkcast net/minecraft/class_1831
    //   70: astore #7
    //   72: iload #5
    //   74: ifne -> 83
    //   77: fconst_0
    //   78: freturn
    //   79: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   82: athrow
    //   83: new java/util/concurrent/atomic/AtomicInteger
    //   86: dup
    //   87: aload #7
    //   89: invokevirtual method_8022 : ()Lnet/minecraft/class_1832;
    //   92: invokeinterface method_8027 : ()F
    //   97: ldc_w 100.0
    //   100: fmul
    //   101: f2i
    //   102: invokespecial <init> : (I)V
    //   105: astore #8
    //   107: aload #4
    //   109: invokestatic method_57532 : (Lnet/minecraft/class_1799;)Lnet/minecraft/class_9304;
    //   112: invokevirtual method_57539 : ()Ljava/util/Set;
    //   115: astore #9
    //   117: aload #9
    //   119: aload #8
    //   121: <illegal opcode> accept : (Ljava/util/concurrent/atomic/AtomicInteger;)Ljava/util/function/Consumer;
    //   126: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   131: aload #6
    //   133: invokevirtual method_7876 : ()Ljava/lang/String;
    //   136: sipush #5934
    //   139: ldc2_w 1274590933930042684
    //   142: lload_2
    //   143: lxor
    //   144: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   149: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   152: iload #5
    //   154: ifeq -> 237
    //   157: ifeq -> 193
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: aload #8
    //   169: sipush #32221
    //   172: ldc2_w 7326428375367608109
    //   175: lload_2
    //   176: lxor
    //   177: <illegal opcode> z : (IJ)I
    //   182: invokevirtual addAndGet : (I)I
    //   185: pop
    //   186: goto -> 193
    //   189: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   192: athrow
    //   193: aload #8
    //   195: aload #4
    //   197: invokevirtual method_7936 : ()I
    //   200: sipush #28683
    //   203: ldc2_w 8849944605868092159
    //   206: lload_2
    //   207: lxor
    //   208: <illegal opcode> z : (IJ)I
    //   213: idiv
    //   214: aload #4
    //   216: invokevirtual method_7919 : ()I
    //   219: sipush #6891
    //   222: ldc2_w 2899723471479668762
    //   225: lload_2
    //   226: lxor
    //   227: <illegal opcode> z : (IJ)I
    //   232: idiv
    //   233: isub
    //   234: invokevirtual addAndGet : (I)I
    //   237: i2f
    //   238: freturn
    // Exception table:
    //   from	to	target	type
    //   38	51	54	wtf/opal/x5
    //   45	60	63	wtf/opal/x5
    //   72	79	79	wtf/opal/x5
    //   117	160	163	wtf/opal/x5
    //   157	186	189	wtf/opal/x5
  }
  
  private boolean q(Object[] paramArrayOfObject) {
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
    //   14: checkcast net/minecraft/class_1799
    //   17: astore #4
    //   19: pop
    //   20: getstatic wtf/opal/j5.a : J
    //   23: lload_2
    //   24: lxor
    //   25: lstore_2
    //   26: lload_2
    //   27: dup2
    //   28: ldc2_w 48143705745505
    //   31: lxor
    //   32: lstore #5
    //   34: dup2
    //   35: ldc2_w 77224936100191
    //   38: lxor
    //   39: lstore #7
    //   41: pop2
    //   42: invokestatic n : ()Z
    //   45: aload #4
    //   47: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   50: astore #10
    //   52: istore #9
    //   54: aload #10
    //   56: instanceof net/minecraft/class_1747
    //   59: iload #9
    //   61: ifne -> 117
    //   64: ifeq -> 112
    //   67: goto -> 74
    //   70: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   73: athrow
    //   74: aload #10
    //   76: checkcast net/minecraft/class_1747
    //   79: astore #11
    //   81: aload #11
    //   83: invokevirtual method_7711 : ()Lnet/minecraft/class_2248;
    //   86: lload #5
    //   88: dup2_x1
    //   89: pop2
    //   90: iconst_2
    //   91: anewarray java/lang/Object
    //   94: dup_x1
    //   95: swap
    //   96: iconst_1
    //   97: swap
    //   98: aastore
    //   99: dup_x2
    //   100: dup_x2
    //   101: pop
    //   102: invokestatic valueOf : (J)Ljava/lang/Long;
    //   105: iconst_0
    //   106: swap
    //   107: aastore
    //   108: invokestatic o : ([Ljava/lang/Object;)Z
    //   111: ireturn
    //   112: aload #10
    //   114: instanceof net/minecraft/class_1738
    //   117: iload #9
    //   119: ifne -> 389
    //   122: ifne -> 388
    //   125: goto -> 132
    //   128: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   131: athrow
    //   132: aload #10
    //   134: instanceof net/minecraft/class_1812
    //   137: iload #9
    //   139: lload_2
    //   140: lconst_0
    //   141: lcmp
    //   142: ifle -> 232
    //   145: ifne -> 230
    //   148: goto -> 155
    //   151: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   154: athrow
    //   155: lload_2
    //   156: lconst_0
    //   157: lcmp
    //   158: iflt -> 223
    //   161: ifeq -> 218
    //   164: goto -> 171
    //   167: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   170: athrow
    //   171: lload #7
    //   173: aload #4
    //   175: iconst_2
    //   176: anewarray java/lang/Object
    //   179: dup_x1
    //   180: swap
    //   181: iconst_1
    //   182: swap
    //   183: aastore
    //   184: dup_x2
    //   185: dup_x2
    //   186: pop
    //   187: invokestatic valueOf : (J)Ljava/lang/Long;
    //   190: iconst_0
    //   191: swap
    //   192: aastore
    //   193: invokestatic L : ([Ljava/lang/Object;)Z
    //   196: iload #9
    //   198: ifne -> 389
    //   201: goto -> 208
    //   204: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   207: athrow
    //   208: ifne -> 388
    //   211: goto -> 218
    //   214: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   217: athrow
    //   218: aload #10
    //   220: instanceof net/minecraft/class_1753
    //   223: goto -> 230
    //   226: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   229: athrow
    //   230: iload #9
    //   232: ifne -> 389
    //   235: ifne -> 388
    //   238: goto -> 245
    //   241: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   244: athrow
    //   245: aload #10
    //   247: instanceof net/minecraft/class_1776
    //   250: iload #9
    //   252: ifne -> 389
    //   255: goto -> 262
    //   258: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   261: athrow
    //   262: ifne -> 388
    //   265: goto -> 272
    //   268: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   271: athrow
    //   272: aload #10
    //   274: instanceof net/minecraft/class_1747
    //   277: iload #9
    //   279: ifne -> 389
    //   282: goto -> 289
    //   285: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   288: athrow
    //   289: ifne -> 388
    //   292: goto -> 299
    //   295: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: aload #10
    //   301: instanceof net/minecraft/class_1744
    //   304: iload #9
    //   306: ifne -> 389
    //   309: goto -> 316
    //   312: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   315: athrow
    //   316: ifne -> 388
    //   319: goto -> 326
    //   322: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   325: athrow
    //   326: aload #10
    //   328: instanceof net/minecraft/class_1819
    //   331: iload #9
    //   333: ifne -> 389
    //   336: goto -> 343
    //   339: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   342: athrow
    //   343: ifne -> 388
    //   346: goto -> 353
    //   349: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   352: athrow
    //   353: aload #10
    //   355: invokevirtual method_57347 : ()Lnet/minecraft/class_9323;
    //   358: getstatic net/minecraft/class_9334.field_50075 : Lnet/minecraft/class_9331;
    //   361: invokeinterface method_57832 : (Lnet/minecraft/class_9331;)Z
    //   366: iload #9
    //   368: ifne -> 389
    //   371: goto -> 378
    //   374: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: ifeq -> 392
    //   381: goto -> 388
    //   384: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   387: athrow
    //   388: iconst_1
    //   389: goto -> 393
    //   392: iconst_0
    //   393: ireturn
    // Exception table:
    //   from	to	target	type
    //   54	67	70	wtf/opal/x5
    //   117	125	128	wtf/opal/x5
    //   122	148	151	wtf/opal/x5
    //   132	164	167	wtf/opal/x5
    //   155	201	204	wtf/opal/x5
    //   171	211	214	wtf/opal/x5
    //   208	223	226	wtf/opal/x5
    //   230	238	241	wtf/opal/x5
    //   235	255	258	wtf/opal/x5
    //   245	265	268	wtf/opal/x5
    //   262	282	285	wtf/opal/x5
    //   272	292	295	wtf/opal/x5
    //   289	309	312	wtf/opal/x5
    //   299	319	322	wtf/opal/x5
    //   316	336	339	wtf/opal/x5
    //   326	346	349	wtf/opal/x5
    //   343	371	374	wtf/opal/x5
    //   353	381	384	wtf/opal/x5
  }
  
  private static void lambda$getToolScore$8(AtomicInteger paramAtomicInteger, Object2IntMap.Entry paramEntry) {
    // Byte code:
    //   0: getstatic wtf/opal/j5.a : J
    //   3: ldc2_w 17752423101090
    //   6: lxor
    //   7: lstore_2
    //   8: aload_1
    //   9: invokeinterface getKey : ()Ljava/lang/Object;
    //   14: checkcast net/minecraft/class_6880
    //   17: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   22: checkcast net/minecraft/class_1887
    //   25: astore #5
    //   27: aload_1
    //   28: invokeinterface getIntValue : ()I
    //   33: istore #6
    //   35: invokestatic n : ()Z
    //   38: iconst_0
    //   39: istore #7
    //   41: istore #4
    //   43: aload #5
    //   45: getstatic net/minecraft/class_1893.field_9131 : Lnet/minecraft/class_1887;
    //   48: iload #4
    //   50: ifne -> 101
    //   53: if_acmpne -> 89
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: iload #7
    //   65: iload #6
    //   67: sipush #23635
    //   70: ldc2_w 2318788799396983376
    //   73: lload_2
    //   74: lxor
    //   75: <illegal opcode> z : (IJ)I
    //   80: imul
    //   81: iadd
    //   82: istore #7
    //   84: iload #4
    //   86: ifeq -> 125
    //   89: aload #5
    //   91: getstatic net/minecraft/class_1893.field_9119 : Lnet/minecraft/class_1887;
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: if_acmpne -> 125
    //   104: iload #7
    //   106: iload #6
    //   108: sipush #4670
    //   111: ldc2_w 7450347326343937076
    //   114: lload_2
    //   115: lxor
    //   116: <illegal opcode> z : (IJ)I
    //   121: imul
    //   122: iadd
    //   123: istore #7
    //   125: aload_0
    //   126: iload #7
    //   128: invokevirtual addAndGet : (I)I
    //   131: pop
    //   132: return
    // Exception table:
    //   from	to	target	type
    //   43	56	59	wtf/opal/x5
    //   84	94	97	wtf/opal/x5
  }
  
  private static Integer lambda$getMostBlocks$7(class_3545 paramclass_3545) {
    return (Integer)paramclass_3545.method_15441();
  }
  
  private static Float lambda$getBestAxe$6(class_3545 paramclass_3545) {
    return (Float)paramclass_3545.method_15441();
  }
  
  private static Float lambda$getBestPickaxe$5(class_3545 paramclass_3545) {
    return (Float)paramclass_3545.method_15441();
  }
  
  private static void lambda$getSwordValue$4(AtomicInteger paramAtomicInteger, Object2IntMap.Entry paramEntry) {
    // Byte code:
    //   0: getstatic wtf/opal/j5.a : J
    //   3: ldc2_w 60183026973065
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic k : ()Z
    //   11: aload_1
    //   12: invokeinterface getKey : ()Ljava/lang/Object;
    //   17: checkcast net/minecraft/class_6880
    //   20: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   25: checkcast net/minecraft/class_1887
    //   28: astore #5
    //   30: istore #4
    //   32: aload_1
    //   33: invokeinterface getIntValue : ()I
    //   38: istore #6
    //   40: iconst_0
    //   41: istore #7
    //   43: aload #5
    //   45: getstatic net/minecraft/class_1893.field_9118 : Lnet/minecraft/class_1887;
    //   48: iload #4
    //   50: ifeq -> 101
    //   53: if_acmpne -> 89
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: iload #7
    //   65: iload #6
    //   67: sipush #13018
    //   70: ldc2_w 205511355360843764
    //   73: lload_2
    //   74: lxor
    //   75: <illegal opcode> z : (IJ)I
    //   80: imul
    //   81: iadd
    //   82: istore #7
    //   84: iload #4
    //   86: ifne -> 178
    //   89: aload #5
    //   91: getstatic net/minecraft/class_1893.field_9124 : Lnet/minecraft/class_1887;
    //   94: goto -> 101
    //   97: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   100: athrow
    //   101: iload #4
    //   103: ifeq -> 154
    //   106: if_acmpne -> 142
    //   109: goto -> 116
    //   112: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   115: athrow
    //   116: iload #7
    //   118: iload #6
    //   120: sipush #4190
    //   123: ldc2_w 2528629129120593277
    //   126: lload_2
    //   127: lxor
    //   128: <illegal opcode> z : (IJ)I
    //   133: imul
    //   134: iadd
    //   135: istore #7
    //   137: iload #4
    //   139: ifne -> 178
    //   142: aload #5
    //   144: getstatic net/minecraft/class_1893.field_9119 : Lnet/minecraft/class_1887;
    //   147: goto -> 154
    //   150: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   153: athrow
    //   154: if_acmpne -> 178
    //   157: iload #7
    //   159: iload #6
    //   161: sipush #26117
    //   164: ldc2_w 820490318531052341
    //   167: lload_2
    //   168: lxor
    //   169: <illegal opcode> z : (IJ)I
    //   174: imul
    //   175: iadd
    //   176: istore #7
    //   178: aload_0
    //   179: iload #7
    //   181: invokevirtual addAndGet : (I)I
    //   184: pop
    //   185: return
    // Exception table:
    //   from	to	target	type
    //   43	56	59	wtf/opal/x5
    //   84	94	97	wtf/opal/x5
    //   101	109	112	wtf/opal/x5
    //   137	147	150	wtf/opal/x5
  }
  
  private static Float lambda$getBestSword$3(class_3545 paramclass_3545) {
    return (Float)paramclass_3545.method_15441();
  }
  
  private void lambda$new$2(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/j5.a : J
    //   3: ldc2_w 77840988752421
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 48570261719772
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 703963046778
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 74784270137640
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 14451915575106
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 88964317687889
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 50969052652153
    //   48: lxor
    //   49: lstore #14
    //   51: dup2
    //   52: ldc2_w 51490543262196
    //   55: lxor
    //   56: lstore #16
    //   58: pop2
    //   59: invokestatic k : ()Z
    //   62: iconst_0
    //   63: anewarray java/lang/Object
    //   66: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   69: iconst_0
    //   70: anewarray java/lang/Object
    //   73: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   76: ldc_w wtf/opal/q
    //   79: iconst_1
    //   80: anewarray java/lang/Object
    //   83: dup_x1
    //   84: swap
    //   85: iconst_0
    //   86: swap
    //   87: aastore
    //   88: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   91: checkcast wtf/opal/q
    //   94: astore #19
    //   96: istore #18
    //   98: iconst_0
    //   99: anewarray java/lang/Object
    //   102: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   105: iconst_0
    //   106: anewarray java/lang/Object
    //   109: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   112: ldc_w wtf/opal/xw
    //   115: iconst_1
    //   116: anewarray java/lang/Object
    //   119: dup_x1
    //   120: swap
    //   121: iconst_0
    //   122: swap
    //   123: aastore
    //   124: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   127: checkcast wtf/opal/xw
    //   130: astore #20
    //   132: iconst_0
    //   133: anewarray java/lang/Object
    //   136: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   139: iconst_0
    //   140: anewarray java/lang/Object
    //   143: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   146: ldc_w wtf/opal/o
    //   149: iconst_1
    //   150: anewarray java/lang/Object
    //   153: dup_x1
    //   154: swap
    //   155: iconst_0
    //   156: swap
    //   157: aastore
    //   158: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   161: checkcast wtf/opal/o
    //   164: astore #21
    //   166: aload_0
    //   167: getfield D : Lwtf/opal/ke;
    //   170: invokevirtual z : ()Ljava/lang/Object;
    //   173: checkcast java/lang/Boolean
    //   176: invokevirtual booleanValue : ()Z
    //   179: iload #18
    //   181: ifeq -> 241
    //   184: ifne -> 225
    //   187: goto -> 194
    //   190: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   193: athrow
    //   194: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   197: getfield field_1755 : Lnet/minecraft/class_437;
    //   200: instanceof net/minecraft/class_490
    //   203: iload #18
    //   205: ifeq -> 241
    //   208: goto -> 215
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: ifeq -> 306
    //   218: goto -> 225
    //   221: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   224: athrow
    //   225: aload #19
    //   227: iconst_0
    //   228: anewarray java/lang/Object
    //   231: invokevirtual D : ([Ljava/lang/Object;)Z
    //   234: goto -> 241
    //   237: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: iload #18
    //   243: ifeq -> 291
    //   246: ifeq -> 275
    //   249: goto -> 256
    //   252: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   255: athrow
    //   256: aload #19
    //   258: iconst_0
    //   259: anewarray java/lang/Object
    //   262: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   265: ifnonnull -> 306
    //   268: goto -> 275
    //   271: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   274: athrow
    //   275: aload #20
    //   277: iconst_0
    //   278: anewarray java/lang/Object
    //   281: invokevirtual D : ([Ljava/lang/Object;)Z
    //   284: goto -> 291
    //   287: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   290: athrow
    //   291: iload #18
    //   293: ifeq -> 327
    //   296: ifeq -> 307
    //   299: goto -> 306
    //   302: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   305: athrow
    //   306: return
    //   307: aload #21
    //   309: lload #4
    //   311: iconst_1
    //   312: anewarray java/lang/Object
    //   315: dup_x2
    //   316: dup_x2
    //   317: pop
    //   318: invokestatic valueOf : (J)Ljava/lang/Long;
    //   321: iconst_0
    //   322: swap
    //   323: aastore
    //   324: invokevirtual A : ([Ljava/lang/Object;)Z
    //   327: iload #18
    //   329: ifeq -> 383
    //   332: ifne -> 374
    //   335: goto -> 342
    //   338: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   341: athrow
    //   342: aload #21
    //   344: iconst_0
    //   345: anewarray java/lang/Object
    //   348: invokevirtual D : ([Ljava/lang/Object;)Z
    //   351: iload #18
    //   353: ifeq -> 383
    //   356: goto -> 363
    //   359: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   362: athrow
    //   363: ifeq -> 374
    //   366: goto -> 373
    //   369: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   372: athrow
    //   373: return
    //   374: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   377: getfield field_1755 : Lnet/minecraft/class_437;
    //   380: instanceof net/minecraft/class_476
    //   383: iload #18
    //   385: ifeq -> 414
    //   388: ifeq -> 399
    //   391: goto -> 398
    //   394: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   397: athrow
    //   398: return
    //   399: aload_0
    //   400: getfield W : Lwtf/opal/kt;
    //   403: invokevirtual z : ()Ljava/lang/Object;
    //   406: checkcast java/lang/Double
    //   409: invokevirtual doubleValue : ()D
    //   412: dconst_0
    //   413: dcmpl
    //   414: iload #18
    //   416: ifeq -> 489
    //   419: ifeq -> 492
    //   422: goto -> 429
    //   425: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   428: athrow
    //   429: aload_0
    //   430: getfield L : Lwtf/opal/kr;
    //   433: aload_0
    //   434: getfield W : Lwtf/opal/kt;
    //   437: invokevirtual z : ()Ljava/lang/Object;
    //   440: checkcast java/lang/Double
    //   443: invokevirtual longValue : ()J
    //   446: lload #6
    //   448: iconst_0
    //   449: iconst_3
    //   450: anewarray java/lang/Object
    //   453: dup_x1
    //   454: swap
    //   455: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   458: iconst_2
    //   459: swap
    //   460: aastore
    //   461: dup_x2
    //   462: dup_x2
    //   463: pop
    //   464: invokestatic valueOf : (J)Ljava/lang/Long;
    //   467: iconst_1
    //   468: swap
    //   469: aastore
    //   470: dup_x2
    //   471: dup_x2
    //   472: pop
    //   473: invokestatic valueOf : (J)Ljava/lang/Long;
    //   476: iconst_0
    //   477: swap
    //   478: aastore
    //   479: invokevirtual v : ([Ljava/lang/Object;)Z
    //   482: goto -> 489
    //   485: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   488: athrow
    //   489: ifeq -> 2050
    //   492: new java/util/ArrayList
    //   495: dup
    //   496: invokespecial <init> : ()V
    //   499: astore #22
    //   501: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   504: getfield field_1724 : Lnet/minecraft/class_746;
    //   507: getfield field_7498 : Lnet/minecraft/class_1723;
    //   510: getfield field_7761 : Lnet/minecraft/class_2371;
    //   513: invokevirtual iterator : ()Ljava/util/Iterator;
    //   516: astore #23
    //   518: aload #23
    //   520: invokeinterface hasNext : ()Z
    //   525: ifeq -> 635
    //   528: aload #23
    //   530: invokeinterface next : ()Ljava/lang/Object;
    //   535: checkcast net/minecraft/class_1735
    //   538: astore #24
    //   540: aload #24
    //   542: iload #18
    //   544: ifeq -> 654
    //   547: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   550: invokevirtual method_7960 : ()Z
    //   553: iload #18
    //   555: ifeq -> 598
    //   558: goto -> 565
    //   561: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   564: athrow
    //   565: ifne -> 630
    //   568: goto -> 575
    //   571: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   574: athrow
    //   575: aload #24
    //   577: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   580: invokevirtual method_7964 : ()Lnet/minecraft/class_2561;
    //   583: invokeinterface method_10866 : ()Lnet/minecraft/class_2583;
    //   588: invokevirtual method_10967 : ()Z
    //   591: goto -> 598
    //   594: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   597: athrow
    //   598: iload #18
    //   600: ifeq -> 629
    //   603: ifeq -> 630
    //   606: goto -> 613
    //   609: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   612: athrow
    //   613: aload #22
    //   615: aload #24
    //   617: invokeinterface add : (Ljava/lang/Object;)Z
    //   622: goto -> 629
    //   625: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   628: athrow
    //   629: pop
    //   630: iload #18
    //   632: ifne -> 518
    //   635: aload_0
    //   636: lload #16
    //   638: iconst_1
    //   639: anewarray java/lang/Object
    //   642: dup_x2
    //   643: dup_x2
    //   644: pop
    //   645: invokestatic valueOf : (J)Ljava/lang/Long;
    //   648: iconst_0
    //   649: swap
    //   650: aastore
    //   651: invokevirtual h : ([Ljava/lang/Object;)Lnet/minecraft/class_1735;
    //   654: astore #23
    //   656: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   659: getfield field_1724 : Lnet/minecraft/class_746;
    //   662: getfield field_7498 : Lnet/minecraft/class_1723;
    //   665: aload_0
    //   666: getfield R : Lwtf/opal/kt;
    //   669: invokevirtual z : ()Ljava/lang/Object;
    //   672: checkcast java/lang/Double
    //   675: invokevirtual intValue : ()I
    //   678: sipush #10365
    //   681: ldc2_w 1696797161639378673
    //   684: lload_2
    //   685: lxor
    //   686: <illegal opcode> z : (IJ)I
    //   691: iadd
    //   692: invokevirtual method_7611 : (I)Lnet/minecraft/class_1735;
    //   695: astore #24
    //   697: aload #23
    //   699: iload #18
    //   701: ifeq -> 940
    //   704: ifnull -> 921
    //   707: goto -> 714
    //   710: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   713: athrow
    //   714: aload #23
    //   716: iload #18
    //   718: ifeq -> 940
    //   721: goto -> 728
    //   724: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   727: athrow
    //   728: invokevirtual method_34266 : ()I
    //   731: aload #24
    //   733: invokevirtual method_34266 : ()I
    //   736: if_icmpeq -> 921
    //   739: goto -> 746
    //   742: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   745: athrow
    //   746: aload #24
    //   748: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   751: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   754: instanceof net/minecraft/class_1829
    //   757: iload #18
    //   759: ifeq -> 867
    //   762: goto -> 769
    //   765: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   768: athrow
    //   769: ifeq -> 855
    //   772: goto -> 779
    //   775: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   778: athrow
    //   779: aload #23
    //   781: iload #18
    //   783: ifeq -> 864
    //   786: goto -> 793
    //   789: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   792: athrow
    //   793: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   796: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   799: aload #24
    //   801: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   804: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   807: if_acmpne -> 855
    //   810: goto -> 817
    //   813: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   816: athrow
    //   817: aload #23
    //   819: iload #18
    //   821: ifeq -> 940
    //   824: goto -> 831
    //   827: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   830: athrow
    //   831: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   834: invokevirtual method_7919 : ()I
    //   837: aload #24
    //   839: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   842: invokevirtual method_7919 : ()I
    //   845: if_icmpge -> 921
    //   848: goto -> 855
    //   851: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   854: athrow
    //   855: aload #23
    //   857: goto -> 864
    //   860: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   863: athrow
    //   864: getfield field_7874 : I
    //   867: aload #24
    //   869: getfield field_7874 : I
    //   872: sipush #28931
    //   875: ldc2_w 2166225626360162182
    //   878: lload_2
    //   879: lxor
    //   880: <illegal opcode> z : (IJ)I
    //   885: isub
    //   886: iconst_2
    //   887: anewarray java/lang/Object
    //   890: dup_x1
    //   891: swap
    //   892: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   895: iconst_1
    //   896: swap
    //   897: aastore
    //   898: dup_x1
    //   899: swap
    //   900: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   903: iconst_0
    //   904: swap
    //   905: aastore
    //   906: invokestatic R : ([Ljava/lang/Object;)V
    //   909: aload_0
    //   910: getfield L : Lwtf/opal/kr;
    //   913: iconst_0
    //   914: anewarray java/lang/Object
    //   917: invokevirtual z : ([Ljava/lang/Object;)V
    //   920: return
    //   921: aload_0
    //   922: lload #10
    //   924: iconst_1
    //   925: anewarray java/lang/Object
    //   928: dup_x2
    //   929: dup_x2
    //   930: pop
    //   931: invokestatic valueOf : (J)Ljava/lang/Long;
    //   934: iconst_0
    //   935: swap
    //   936: aastore
    //   937: invokevirtual Q : ([Ljava/lang/Object;)Lnet/minecraft/class_1735;
    //   940: astore #25
    //   942: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   945: getfield field_1724 : Lnet/minecraft/class_746;
    //   948: getfield field_7498 : Lnet/minecraft/class_1723;
    //   951: aload_0
    //   952: getfield A : Lwtf/opal/kt;
    //   955: invokevirtual z : ()Ljava/lang/Object;
    //   958: checkcast java/lang/Double
    //   961: invokevirtual intValue : ()I
    //   964: sipush #5317
    //   967: ldc2_w 2627752932425646669
    //   970: lload_2
    //   971: lxor
    //   972: <illegal opcode> z : (IJ)I
    //   977: iadd
    //   978: invokevirtual method_7611 : (I)Lnet/minecraft/class_1735;
    //   981: astore #26
    //   983: aload #25
    //   985: iload #18
    //   987: ifeq -> 1226
    //   990: ifnull -> 1207
    //   993: goto -> 1000
    //   996: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   999: athrow
    //   1000: aload #25
    //   1002: iload #18
    //   1004: ifeq -> 1226
    //   1007: goto -> 1014
    //   1010: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1013: athrow
    //   1014: invokevirtual method_34266 : ()I
    //   1017: aload #26
    //   1019: invokevirtual method_34266 : ()I
    //   1022: if_icmpeq -> 1207
    //   1025: goto -> 1032
    //   1028: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1031: athrow
    //   1032: aload #26
    //   1034: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1037: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1040: instanceof net/minecraft/class_1810
    //   1043: iload #18
    //   1045: ifeq -> 1153
    //   1048: goto -> 1055
    //   1051: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1054: athrow
    //   1055: ifeq -> 1141
    //   1058: goto -> 1065
    //   1061: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1064: athrow
    //   1065: aload #25
    //   1067: iload #18
    //   1069: ifeq -> 1150
    //   1072: goto -> 1079
    //   1075: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1078: athrow
    //   1079: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1082: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1085: aload #26
    //   1087: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1090: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1093: if_acmpne -> 1141
    //   1096: goto -> 1103
    //   1099: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1102: athrow
    //   1103: aload #25
    //   1105: iload #18
    //   1107: ifeq -> 1226
    //   1110: goto -> 1117
    //   1113: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1116: athrow
    //   1117: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1120: invokevirtual method_7919 : ()I
    //   1123: aload #26
    //   1125: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1128: invokevirtual method_7919 : ()I
    //   1131: if_icmpge -> 1207
    //   1134: goto -> 1141
    //   1137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1140: athrow
    //   1141: aload #25
    //   1143: goto -> 1150
    //   1146: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1149: athrow
    //   1150: getfield field_7874 : I
    //   1153: aload #26
    //   1155: getfield field_7874 : I
    //   1158: sipush #31875
    //   1161: ldc2_w 6487900243384674818
    //   1164: lload_2
    //   1165: lxor
    //   1166: <illegal opcode> z : (IJ)I
    //   1171: isub
    //   1172: iconst_2
    //   1173: anewarray java/lang/Object
    //   1176: dup_x1
    //   1177: swap
    //   1178: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1181: iconst_1
    //   1182: swap
    //   1183: aastore
    //   1184: dup_x1
    //   1185: swap
    //   1186: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1189: iconst_0
    //   1190: swap
    //   1191: aastore
    //   1192: invokestatic R : ([Ljava/lang/Object;)V
    //   1195: aload_0
    //   1196: getfield L : Lwtf/opal/kr;
    //   1199: iconst_0
    //   1200: anewarray java/lang/Object
    //   1203: invokevirtual z : ([Ljava/lang/Object;)V
    //   1206: return
    //   1207: aload_0
    //   1208: lload #14
    //   1210: iconst_1
    //   1211: anewarray java/lang/Object
    //   1214: dup_x2
    //   1215: dup_x2
    //   1216: pop
    //   1217: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1220: iconst_0
    //   1221: swap
    //   1222: aastore
    //   1223: invokevirtual Y : ([Ljava/lang/Object;)Lnet/minecraft/class_1735;
    //   1226: astore #27
    //   1228: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1231: getfield field_1724 : Lnet/minecraft/class_746;
    //   1234: getfield field_7498 : Lnet/minecraft/class_1723;
    //   1237: aload_0
    //   1238: getfield B : Lwtf/opal/kt;
    //   1241: invokevirtual z : ()Ljava/lang/Object;
    //   1244: checkcast java/lang/Double
    //   1247: invokevirtual intValue : ()I
    //   1250: sipush #5317
    //   1253: ldc2_w 2627752932425646669
    //   1256: lload_2
    //   1257: lxor
    //   1258: <illegal opcode> z : (IJ)I
    //   1263: iadd
    //   1264: invokevirtual method_7611 : (I)Lnet/minecraft/class_1735;
    //   1267: astore #28
    //   1269: aload #27
    //   1271: iload #18
    //   1273: ifeq -> 1512
    //   1276: ifnull -> 1493
    //   1279: goto -> 1286
    //   1282: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1285: athrow
    //   1286: aload #27
    //   1288: iload #18
    //   1290: ifeq -> 1512
    //   1293: goto -> 1300
    //   1296: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1299: athrow
    //   1300: invokevirtual method_34266 : ()I
    //   1303: aload #28
    //   1305: invokevirtual method_34266 : ()I
    //   1308: if_icmpeq -> 1493
    //   1311: goto -> 1318
    //   1314: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1317: athrow
    //   1318: aload #28
    //   1320: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1323: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1326: instanceof net/minecraft/class_1743
    //   1329: iload #18
    //   1331: ifeq -> 1439
    //   1334: goto -> 1341
    //   1337: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1340: athrow
    //   1341: ifeq -> 1427
    //   1344: goto -> 1351
    //   1347: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1350: athrow
    //   1351: aload #27
    //   1353: iload #18
    //   1355: ifeq -> 1436
    //   1358: goto -> 1365
    //   1361: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1364: athrow
    //   1365: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1368: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1371: aload #28
    //   1373: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1376: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1379: if_acmpne -> 1427
    //   1382: goto -> 1389
    //   1385: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1388: athrow
    //   1389: aload #27
    //   1391: iload #18
    //   1393: ifeq -> 1512
    //   1396: goto -> 1403
    //   1399: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1402: athrow
    //   1403: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1406: invokevirtual method_7919 : ()I
    //   1409: aload #28
    //   1411: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1414: invokevirtual method_7919 : ()I
    //   1417: if_icmpge -> 1493
    //   1420: goto -> 1427
    //   1423: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1426: athrow
    //   1427: aload #27
    //   1429: goto -> 1436
    //   1432: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1435: athrow
    //   1436: getfield field_7874 : I
    //   1439: aload #28
    //   1441: getfield field_7874 : I
    //   1444: sipush #31875
    //   1447: ldc2_w 6487900243384674818
    //   1450: lload_2
    //   1451: lxor
    //   1452: <illegal opcode> z : (IJ)I
    //   1457: isub
    //   1458: iconst_2
    //   1459: anewarray java/lang/Object
    //   1462: dup_x1
    //   1463: swap
    //   1464: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1467: iconst_1
    //   1468: swap
    //   1469: aastore
    //   1470: dup_x1
    //   1471: swap
    //   1472: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1475: iconst_0
    //   1476: swap
    //   1477: aastore
    //   1478: invokestatic R : ([Ljava/lang/Object;)V
    //   1481: aload_0
    //   1482: getfield L : Lwtf/opal/kr;
    //   1485: iconst_0
    //   1486: anewarray java/lang/Object
    //   1489: invokevirtual z : ([Ljava/lang/Object;)V
    //   1492: return
    //   1493: aload_0
    //   1494: lload #8
    //   1496: iconst_1
    //   1497: anewarray java/lang/Object
    //   1500: dup_x2
    //   1501: dup_x2
    //   1502: pop
    //   1503: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1506: iconst_0
    //   1507: swap
    //   1508: aastore
    //   1509: invokevirtual M : ([Ljava/lang/Object;)Lnet/minecraft/class_1735;
    //   1512: astore #29
    //   1514: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1517: getfield field_1724 : Lnet/minecraft/class_746;
    //   1520: getfield field_7498 : Lnet/minecraft/class_1723;
    //   1523: aload_0
    //   1524: getfield k : Lwtf/opal/kt;
    //   1527: invokevirtual z : ()Ljava/lang/Object;
    //   1530: checkcast java/lang/Double
    //   1533: invokevirtual intValue : ()I
    //   1536: sipush #5317
    //   1539: ldc2_w 2627752932425646669
    //   1542: lload_2
    //   1543: lxor
    //   1544: <illegal opcode> z : (IJ)I
    //   1549: iadd
    //   1550: invokevirtual method_7611 : (I)Lnet/minecraft/class_1735;
    //   1553: astore #30
    //   1555: aload #29
    //   1557: iload #18
    //   1559: ifeq -> 1574
    //   1562: ifnull -> 1734
    //   1565: goto -> 1572
    //   1568: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1571: athrow
    //   1572: aload #29
    //   1574: invokevirtual method_34266 : ()I
    //   1577: iload #18
    //   1579: ifeq -> 1615
    //   1582: aload #30
    //   1584: invokevirtual method_34266 : ()I
    //   1587: if_icmpeq -> 1734
    //   1590: goto -> 1597
    //   1593: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1596: athrow
    //   1597: aload #30
    //   1599: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1602: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
    //   1605: instanceof net/minecraft/class_1747
    //   1608: goto -> 1615
    //   1611: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1614: athrow
    //   1615: iload #18
    //   1617: ifeq -> 1680
    //   1620: ifeq -> 1668
    //   1623: goto -> 1630
    //   1626: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1629: athrow
    //   1630: aload #29
    //   1632: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1635: invokevirtual method_7947 : ()I
    //   1638: aload #30
    //   1640: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1643: invokevirtual method_7947 : ()I
    //   1646: iload #18
    //   1648: ifeq -> 1699
    //   1651: goto -> 1658
    //   1654: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1657: athrow
    //   1658: if_icmple -> 1734
    //   1661: goto -> 1668
    //   1664: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1667: athrow
    //   1668: aload #29
    //   1670: getfield field_7874 : I
    //   1673: goto -> 1680
    //   1676: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1679: athrow
    //   1680: aload #30
    //   1682: getfield field_7874 : I
    //   1685: sipush #31875
    //   1688: ldc2_w 6487900243384674818
    //   1691: lload_2
    //   1692: lxor
    //   1693: <illegal opcode> z : (IJ)I
    //   1698: isub
    //   1699: iconst_2
    //   1700: anewarray java/lang/Object
    //   1703: dup_x1
    //   1704: swap
    //   1705: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1708: iconst_1
    //   1709: swap
    //   1710: aastore
    //   1711: dup_x1
    //   1712: swap
    //   1713: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1716: iconst_0
    //   1717: swap
    //   1718: aastore
    //   1719: invokestatic R : ([Ljava/lang/Object;)V
    //   1722: aload_0
    //   1723: getfield L : Lwtf/opal/kr;
    //   1726: iconst_0
    //   1727: anewarray java/lang/Object
    //   1730: invokevirtual z : ([Ljava/lang/Object;)V
    //   1733: return
    //   1734: aload #22
    //   1736: invokeinterface iterator : ()Ljava/util/Iterator;
    //   1741: astore #31
    //   1743: aload #31
    //   1745: invokeinterface hasNext : ()Z
    //   1750: ifeq -> 2050
    //   1753: aload #31
    //   1755: invokeinterface next : ()Ljava/lang/Object;
    //   1760: checkcast net/minecraft/class_1735
    //   1763: astore #32
    //   1765: aload_0
    //   1766: aload #32
    //   1768: invokevirtual method_7677 : ()Lnet/minecraft/class_1799;
    //   1771: lload #12
    //   1773: dup2_x1
    //   1774: pop2
    //   1775: iconst_2
    //   1776: anewarray java/lang/Object
    //   1779: dup_x1
    //   1780: swap
    //   1781: iconst_1
    //   1782: swap
    //   1783: aastore
    //   1784: dup_x2
    //   1785: dup_x2
    //   1786: pop
    //   1787: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1790: iconst_0
    //   1791: swap
    //   1792: aastore
    //   1793: invokevirtual q : ([Ljava/lang/Object;)Z
    //   1796: ifeq -> 1811
    //   1799: iload #18
    //   1801: ifne -> 1743
    //   1804: goto -> 1811
    //   1807: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1810: athrow
    //   1811: aload #23
    //   1813: iload #18
    //   1815: ifeq -> 1879
    //   1818: ifnull -> 1872
    //   1821: goto -> 1828
    //   1824: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1827: athrow
    //   1828: aload #32
    //   1830: invokevirtual method_34266 : ()I
    //   1833: iload #18
    //   1835: ifeq -> 1885
    //   1838: goto -> 1845
    //   1841: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1844: athrow
    //   1845: aload #23
    //   1847: invokevirtual method_34266 : ()I
    //   1850: if_icmpne -> 1872
    //   1853: goto -> 1860
    //   1856: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1859: athrow
    //   1860: iload #18
    //   1862: ifne -> 1743
    //   1865: goto -> 1872
    //   1868: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1871: athrow
    //   1872: aload_0
    //   1873: getfield n : Lwtf/opal/ke;
    //   1876: invokevirtual z : ()Ljava/lang/Object;
    //   1879: checkcast java/lang/Boolean
    //   1882: invokevirtual booleanValue : ()Z
    //   1885: iload #18
    //   1887: ifeq -> 2034
    //   1890: ifeq -> 2029
    //   1893: goto -> 1900
    //   1896: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1899: athrow
    //   1900: aload #25
    //   1902: iload #18
    //   1904: ifeq -> 1970
    //   1907: goto -> 1914
    //   1910: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1913: athrow
    //   1914: ifnull -> 1968
    //   1917: goto -> 1924
    //   1920: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1923: athrow
    //   1924: aload #32
    //   1926: iload #18
    //   1928: ifeq -> 1970
    //   1931: goto -> 1938
    //   1934: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1937: athrow
    //   1938: invokevirtual method_34266 : ()I
    //   1941: aload #25
    //   1943: invokevirtual method_34266 : ()I
    //   1946: if_icmpne -> 1968
    //   1949: goto -> 1956
    //   1952: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1955: athrow
    //   1956: iload #18
    //   1958: ifne -> 1743
    //   1961: goto -> 1968
    //   1964: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1967: athrow
    //   1968: aload #27
    //   1970: iload #18
    //   1972: ifeq -> 2031
    //   1975: ifnull -> 2029
    //   1978: goto -> 1985
    //   1981: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1984: athrow
    //   1985: aload #32
    //   1987: invokevirtual method_34266 : ()I
    //   1990: iload #18
    //   1992: ifeq -> 2034
    //   1995: goto -> 2002
    //   1998: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2001: athrow
    //   2002: aload #27
    //   2004: invokevirtual method_34266 : ()I
    //   2007: if_icmpne -> 2029
    //   2010: goto -> 2017
    //   2013: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2016: athrow
    //   2017: iload #18
    //   2019: ifne -> 1743
    //   2022: goto -> 2029
    //   2025: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2028: athrow
    //   2029: aload #32
    //   2031: getfield field_7874 : I
    //   2034: iconst_1
    //   2035: anewarray java/lang/Object
    //   2038: dup_x1
    //   2039: swap
    //   2040: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2043: iconst_0
    //   2044: swap
    //   2045: aastore
    //   2046: invokestatic J : ([Ljava/lang/Object;)V
    //   2049: return
    //   2050: return
    // Exception table:
    //   from	to	target	type
    //   166	187	190	wtf/opal/x5
    //   184	208	211	wtf/opal/x5
    //   194	218	221	wtf/opal/x5
    //   215	234	237	wtf/opal/x5
    //   241	249	252	wtf/opal/x5
    //   246	268	271	wtf/opal/x5
    //   256	284	287	wtf/opal/x5
    //   291	299	302	wtf/opal/x5
    //   327	335	338	wtf/opal/x5
    //   332	356	359	wtf/opal/x5
    //   342	366	369	wtf/opal/x5
    //   383	391	394	wtf/opal/x5
    //   414	422	425	wtf/opal/x5
    //   419	482	485	wtf/opal/x5
    //   540	558	561	wtf/opal/x5
    //   547	568	571	wtf/opal/x5
    //   565	591	594	wtf/opal/x5
    //   598	606	609	wtf/opal/x5
    //   603	622	625	wtf/opal/x5
    //   697	707	710	wtf/opal/x5
    //   704	721	724	wtf/opal/x5
    //   714	739	742	wtf/opal/x5
    //   728	762	765	wtf/opal/x5
    //   746	772	775	wtf/opal/x5
    //   769	786	789	wtf/opal/x5
    //   779	810	813	wtf/opal/x5
    //   793	824	827	wtf/opal/x5
    //   817	848	851	wtf/opal/x5
    //   831	857	860	wtf/opal/x5
    //   983	993	996	wtf/opal/x5
    //   990	1007	1010	wtf/opal/x5
    //   1000	1025	1028	wtf/opal/x5
    //   1014	1048	1051	wtf/opal/x5
    //   1032	1058	1061	wtf/opal/x5
    //   1055	1072	1075	wtf/opal/x5
    //   1065	1096	1099	wtf/opal/x5
    //   1079	1110	1113	wtf/opal/x5
    //   1103	1134	1137	wtf/opal/x5
    //   1117	1143	1146	wtf/opal/x5
    //   1269	1279	1282	wtf/opal/x5
    //   1276	1293	1296	wtf/opal/x5
    //   1286	1311	1314	wtf/opal/x5
    //   1300	1334	1337	wtf/opal/x5
    //   1318	1344	1347	wtf/opal/x5
    //   1341	1358	1361	wtf/opal/x5
    //   1351	1382	1385	wtf/opal/x5
    //   1365	1396	1399	wtf/opal/x5
    //   1389	1420	1423	wtf/opal/x5
    //   1403	1429	1432	wtf/opal/x5
    //   1555	1565	1568	wtf/opal/x5
    //   1574	1590	1593	wtf/opal/x5
    //   1582	1608	1611	wtf/opal/x5
    //   1615	1623	1626	wtf/opal/x5
    //   1620	1651	1654	wtf/opal/x5
    //   1630	1661	1664	wtf/opal/x5
    //   1658	1673	1676	wtf/opal/x5
    //   1765	1804	1807	wtf/opal/x5
    //   1811	1821	1824	wtf/opal/x5
    //   1818	1838	1841	wtf/opal/x5
    //   1828	1853	1856	wtf/opal/x5
    //   1845	1865	1868	wtf/opal/x5
    //   1885	1893	1896	wtf/opal/x5
    //   1890	1907	1910	wtf/opal/x5
    //   1900	1917	1920	wtf/opal/x5
    //   1914	1931	1934	wtf/opal/x5
    //   1924	1949	1952	wtf/opal/x5
    //   1938	1961	1964	wtf/opal/x5
    //   1970	1978	1981	wtf/opal/x5
    //   1975	1995	1998	wtf/opal/x5
    //   1985	2010	2013	wtf/opal/x5
    //   2002	2022	2025	wtf/opal/x5
  }
  
  private boolean lambda$new$1(ke paramke) {
    return this.n.z().booleanValue();
  }
  
  private boolean lambda$new$0(ke paramke) {
    return this.n.z().booleanValue();
  }
  
  static {
    long l = a ^ 0x2AB0FA2BC37FL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[10];
    boolean bool = false;
    String str;
    int i = (str = "ò\030ZIÓT\013àjXA\rÆ³\016RÁ¤õ{Ú0d\004!\013£\002XBµ~ÓÎ¿l'8\022Ç\016/ÖÔ\003\013)ê²\027³ø·Å²,i\004Ç 1}æã?Þ\036y3eôç\006u Ý\0051JG­ç¡Äö/¯3\002Ë\020.°\003¢\037¨¨\013q\032] X¤\"(Ã\024®é\030Sa\0244E×­(Ñþ_k!\006$Gb½7M_85ÚÙ0eáAV\020±øÐÎHÝ]úøö¦m·Ñ 5Ü|àÂg=\021I}¼\006hÊ`;\t\016TÖ\030¬§át*\f\020åYÃS\002vãiú©ti+°").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x43C7;
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
        throw new RuntimeException("wtf/opal/j5", exception);
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
    //   65: ldc_w 'wtf/opal/j5'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2B29;
    if (l[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = g[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])m.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          m.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/j5", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      l[i] = Integer.valueOf(j);
    } 
    return l[i].intValue();
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
    //   65: ldc_w 'wtf/opal/j5'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j5.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */