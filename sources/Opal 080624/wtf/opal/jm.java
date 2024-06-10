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

public final class jm extends d {
  private final kr l;
  
  public final ky<bn> u;
  
  private final ke x;
  
  private final ke f;
  
  private final kt M;
  
  private final gm<b6> d;
  
  private final gm<uj> a;
  
  private final gm<lu> b;
  
  private static String W;
  
  private static final long g = on.a(-6468370585781856438L, -9058806092681242800L, MethodHandles.lookup().lookupClass()).a(95071885452957L);
  
  private static final String[] k;
  
  private static final String[] m;
  
  private static final Map n = new HashMap<>(13);
  
  private static final long[] o;
  
  private static final Integer[] p;
  
  private static final Map q;
  
  public jm(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jm.g : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 133076133413132
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 53840531749212
    //   17: lxor
    //   18: dup2
    //   19: bipush #32
    //   21: lushr
    //   22: l2i
    //   23: istore #5
    //   25: dup2
    //   26: bipush #32
    //   28: lshl
    //   29: bipush #48
    //   31: lushr
    //   32: l2i
    //   33: istore #6
    //   35: dup2
    //   36: bipush #48
    //   38: lshl
    //   39: bipush #48
    //   41: lushr
    //   42: l2i
    //   43: istore #7
    //   45: pop2
    //   46: dup2
    //   47: ldc2_w 59652052015207
    //   50: lxor
    //   51: lstore #8
    //   53: dup2
    //   54: ldc2_w 26655659819314
    //   57: lxor
    //   58: lstore #10
    //   60: dup2
    //   61: ldc2_w 48188191644052
    //   64: lxor
    //   65: dup2
    //   66: bipush #48
    //   68: lushr
    //   69: l2i
    //   70: istore #12
    //   72: dup2
    //   73: bipush #16
    //   75: lshl
    //   76: bipush #48
    //   78: lushr
    //   79: l2i
    //   80: istore #13
    //   82: dup2
    //   83: bipush #32
    //   85: lshl
    //   86: bipush #32
    //   88: lushr
    //   89: l2i
    //   90: istore #14
    //   92: pop2
    //   93: pop2
    //   94: invokestatic P : ()Ljava/lang/String;
    //   97: aload_0
    //   98: sipush #22110
    //   101: ldc2_w 2147160090497459313
    //   104: lload_1
    //   105: lxor
    //   106: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   111: lload #10
    //   113: sipush #13347
    //   116: ldc2_w 1146722408313800201
    //   119: lload_1
    //   120: lxor
    //   121: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   126: getstatic wtf/opal/kn.MOVEMENT : Lwtf/opal/kn;
    //   129: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   132: astore #15
    //   134: aload_0
    //   135: new wtf/opal/kr
    //   138: dup
    //   139: invokespecial <init> : ()V
    //   142: putfield l : Lwtf/opal/kr;
    //   145: aload_0
    //   146: new wtf/opal/ky
    //   149: dup
    //   150: sipush #23706
    //   153: ldc2_w 615964505423785652
    //   156: lload_1
    //   157: lxor
    //   158: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   163: aload_0
    //   164: getstatic wtf/opal/bn.VANILLA : Lwtf/opal/bn;
    //   167: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   170: putfield u : Lwtf/opal/ky;
    //   173: aload_0
    //   174: new wtf/opal/ke
    //   177: dup
    //   178: sipush #29316
    //   181: ldc2_w 6134670361008603305
    //   184: lload_1
    //   185: lxor
    //   186: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   191: iconst_1
    //   192: invokespecial <init> : (Ljava/lang/String;Z)V
    //   195: putfield x : Lwtf/opal/ke;
    //   198: aload_0
    //   199: new wtf/opal/ke
    //   202: dup
    //   203: sipush #15690
    //   206: ldc2_w 6521001671265898337
    //   209: lload_1
    //   210: lxor
    //   211: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   216: iconst_1
    //   217: invokespecial <init> : (Ljava/lang/String;Z)V
    //   220: putfield f : Lwtf/opal/ke;
    //   223: aload_0
    //   224: new wtf/opal/kt
    //   227: dup
    //   228: sipush #9006
    //   231: ldc2_w 5907394822847058183
    //   234: lload_1
    //   235: lxor
    //   236: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   241: ldc2_w 3.0
    //   244: dconst_1
    //   245: ldc2_w 5.0
    //   248: ldc2_w 0.1
    //   251: invokespecial <init> : (Ljava/lang/String;DDDD)V
    //   254: putfield M : Lwtf/opal/kt;
    //   257: aload_0
    //   258: aload_0
    //   259: <illegal opcode> H : (Lwtf/opal/jm;)Lwtf/opal/gm;
    //   264: putfield d : Lwtf/opal/gm;
    //   267: aload_0
    //   268: aload_0
    //   269: <illegal opcode> H : (Lwtf/opal/jm;)Lwtf/opal/gm;
    //   274: putfield a : Lwtf/opal/gm;
    //   277: aload_0
    //   278: aload_0
    //   279: <illegal opcode> H : (Lwtf/opal/jm;)Lwtf/opal/gm;
    //   284: putfield b : Lwtf/opal/gm;
    //   287: aload_0
    //   288: iconst_4
    //   289: anewarray wtf/opal/k3
    //   292: dup
    //   293: iconst_0
    //   294: aload_0
    //   295: getfield u : Lwtf/opal/ky;
    //   298: aastore
    //   299: dup
    //   300: iconst_1
    //   301: aload_0
    //   302: getfield x : Lwtf/opal/ke;
    //   305: aastore
    //   306: dup
    //   307: iconst_2
    //   308: aload_0
    //   309: getfield M : Lwtf/opal/kt;
    //   312: aastore
    //   313: dup
    //   314: iconst_3
    //   315: aload_0
    //   316: getfield f : Lwtf/opal/ke;
    //   319: aastore
    //   320: lload #8
    //   322: dup2_x1
    //   323: pop2
    //   324: iconst_2
    //   325: anewarray java/lang/Object
    //   328: dup_x1
    //   329: swap
    //   330: iconst_1
    //   331: swap
    //   332: aastore
    //   333: dup_x2
    //   334: dup_x2
    //   335: pop
    //   336: invokestatic valueOf : (J)Ljava/lang/Long;
    //   339: iconst_0
    //   340: swap
    //   341: aastore
    //   342: invokevirtual o : ([Ljava/lang/Object;)V
    //   345: aload_0
    //   346: aload_0
    //   347: getfield u : Lwtf/opal/ky;
    //   350: iconst_4
    //   351: anewarray wtf/opal/u_
    //   354: dup
    //   355: iconst_0
    //   356: new wtf/opal/ua
    //   359: dup
    //   360: aload_0
    //   361: invokespecial <init> : (Lwtf/opal/jm;)V
    //   364: aastore
    //   365: dup
    //   366: iconst_1
    //   367: new wtf/opal/uu
    //   370: dup
    //   371: iload #5
    //   373: iload #6
    //   375: i2s
    //   376: iload #7
    //   378: aload_0
    //   379: invokespecial <init> : (ISILwtf/opal/jm;)V
    //   382: aastore
    //   383: dup
    //   384: iconst_2
    //   385: new wtf/opal/uo
    //   388: dup
    //   389: iload #12
    //   391: i2s
    //   392: iload #13
    //   394: i2c
    //   395: aload_0
    //   396: iload #14
    //   398: invokespecial <init> : (SCLwtf/opal/jm;I)V
    //   401: aastore
    //   402: dup
    //   403: iconst_3
    //   404: new wtf/opal/ub
    //   407: dup
    //   408: aload_0
    //   409: lload_3
    //   410: invokespecial <init> : (Lwtf/opal/jm;J)V
    //   413: aastore
    //   414: iconst_2
    //   415: anewarray java/lang/Object
    //   418: dup_x1
    //   419: swap
    //   420: iconst_1
    //   421: swap
    //   422: aastore
    //   423: dup_x1
    //   424: swap
    //   425: iconst_0
    //   426: swap
    //   427: aastore
    //   428: invokevirtual Y : ([Ljava/lang/Object;)V
    //   431: aload #15
    //   433: ifnull -> 450
    //   436: iconst_1
    //   437: anewarray wtf/opal/d
    //   440: invokestatic p : ([Lwtf/opal/d;)V
    //   443: goto -> 450
    //   446: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   449: athrow
    //   450: return
    // Exception table:
    //   from	to	target	type
    //   134	443	446	wtf/opal/x5
  }
  
  protected void z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x13EB4BECD7D4L;
    long l3 = l1 ^ 0x0L;
    String str = P();
    try {
      if (str == null)
        try {
          if (this.f.z().booleanValue()) {
            new Object[2];
            (new Object[2])[1] = Double.valueOf(0.0D);
            new Object[2];
            l_.k(new Object[] { Long.valueOf(l2) });
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    new Object[1];
    super.z(new Object[] { Long.valueOf(l3) });
  }
  
  public boolean e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jm.g : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic P : ()Ljava/lang/String;
    //   21: astore #4
    //   23: aload_0
    //   24: getfield u : Lwtf/opal/ky;
    //   27: invokevirtual z : ()Ljava/lang/Object;
    //   30: checkcast wtf/opal/bn
    //   33: getstatic wtf/opal/bn.WATCHDOG : Lwtf/opal/bn;
    //   36: invokevirtual equals : (Ljava/lang/Object;)Z
    //   39: aload #4
    //   41: ifnonnull -> 84
    //   44: ifeq -> 103
    //   47: goto -> 54
    //   50: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   53: athrow
    //   54: aload_0
    //   55: iconst_0
    //   56: anewarray java/lang/Object
    //   59: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/u_;
    //   62: checkcast wtf/opal/uo
    //   65: getfield i : Lwtf/opal/ke;
    //   68: invokevirtual z : ()Ljava/lang/Object;
    //   71: checkcast java/lang/Boolean
    //   74: invokevirtual booleanValue : ()Z
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: aload #4
    //   86: ifnonnull -> 100
    //   89: ifeq -> 103
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: iconst_1
    //   100: goto -> 104
    //   103: iconst_0
    //   104: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	47	50	wtf/opal/x5
    //   44	77	80	wtf/opal/x5
    //   84	92	95	wtf/opal/x5
  }
  
  public boolean W(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jm.g : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic P : ()Ljava/lang/String;
    //   21: astore #4
    //   23: aload_0
    //   24: getfield u : Lwtf/opal/ky;
    //   27: invokevirtual z : ()Ljava/lang/Object;
    //   30: checkcast wtf/opal/bn
    //   33: getstatic wtf/opal/bn.WATCHDOG : Lwtf/opal/bn;
    //   36: invokevirtual equals : (Ljava/lang/Object;)Z
    //   39: aload #4
    //   41: ifnonnull -> 84
    //   44: ifeq -> 103
    //   47: goto -> 54
    //   50: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   53: athrow
    //   54: aload_0
    //   55: iconst_0
    //   56: anewarray java/lang/Object
    //   59: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/u_;
    //   62: checkcast wtf/opal/uo
    //   65: getfield M : Lwtf/opal/ke;
    //   68: invokevirtual z : ()Ljava/lang/Object;
    //   71: checkcast java/lang/Boolean
    //   74: invokevirtual booleanValue : ()Z
    //   77: goto -> 84
    //   80: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   83: athrow
    //   84: aload #4
    //   86: ifnonnull -> 100
    //   89: ifeq -> 103
    //   92: goto -> 99
    //   95: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   98: athrow
    //   99: iconst_1
    //   100: goto -> 104
    //   103: iconst_0
    //   104: ireturn
    // Exception table:
    //   from	to	target	type
    //   23	47	50	wtf/opal/x5
    //   44	77	80	wtf/opal/x5
    //   84	92	95	wtf/opal/x5
  }
  
  public boolean g(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jm.g : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 98038479250612
    //   23: lxor
    //   24: lstore #4
    //   26: pop2
    //   27: invokestatic P : ()Ljava/lang/String;
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   37: iconst_0
    //   38: anewarray java/lang/Object
    //   41: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   44: ldc_w wtf/opal/xw
    //   47: iconst_1
    //   48: anewarray java/lang/Object
    //   51: dup_x1
    //   52: swap
    //   53: iconst_0
    //   54: swap
    //   55: aastore
    //   56: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   59: checkcast wtf/opal/xw
    //   62: astore #7
    //   64: astore #6
    //   66: aload #7
    //   68: iconst_0
    //   69: anewarray java/lang/Object
    //   72: invokevirtual D : ([Ljava/lang/Object;)Z
    //   75: aload #6
    //   77: ifnonnull -> 239
    //   80: ifeq -> 181
    //   83: goto -> 90
    //   86: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: aload #7
    //   92: getfield B : Lwtf/opal/ke;
    //   95: invokevirtual z : ()Ljava/lang/Object;
    //   98: checkcast java/lang/Boolean
    //   101: invokevirtual booleanValue : ()Z
    //   104: aload #6
    //   106: ifnonnull -> 239
    //   109: goto -> 116
    //   112: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   115: athrow
    //   116: ifeq -> 181
    //   119: goto -> 126
    //   122: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   125: athrow
    //   126: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   129: getfield field_1690 : Lnet/minecraft/class_315;
    //   132: getfield field_1903 : Lnet/minecraft/class_304;
    //   135: iconst_1
    //   136: anewarray java/lang/Object
    //   139: dup_x1
    //   140: swap
    //   141: iconst_0
    //   142: swap
    //   143: aastore
    //   144: invokestatic m : ([Ljava/lang/Object;)Z
    //   147: aload #6
    //   149: lload_2
    //   150: lconst_0
    //   151: lcmp
    //   152: ifle -> 241
    //   155: ifnonnull -> 239
    //   158: goto -> 165
    //   161: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   164: athrow
    //   165: ifeq -> 181
    //   168: goto -> 175
    //   171: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   174: athrow
    //   175: iconst_0
    //   176: ireturn
    //   177: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   180: athrow
    //   181: aload_0
    //   182: getfield l : Lwtf/opal/kr;
    //   185: aload_0
    //   186: getfield M : Lwtf/opal/kt;
    //   189: invokevirtual z : ()Ljava/lang/Object;
    //   192: checkcast java/lang/Double
    //   195: invokevirtual doubleValue : ()D
    //   198: ldc2_w 1000.0
    //   201: dmul
    //   202: d2l
    //   203: lload #4
    //   205: iconst_0
    //   206: iconst_3
    //   207: anewarray java/lang/Object
    //   210: dup_x1
    //   211: swap
    //   212: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   215: iconst_2
    //   216: swap
    //   217: aastore
    //   218: dup_x2
    //   219: dup_x2
    //   220: pop
    //   221: invokestatic valueOf : (J)Ljava/lang/Long;
    //   224: iconst_1
    //   225: swap
    //   226: aastore
    //   227: dup_x2
    //   228: dup_x2
    //   229: pop
    //   230: invokestatic valueOf : (J)Ljava/lang/Long;
    //   233: iconst_0
    //   234: swap
    //   235: aastore
    //   236: invokevirtual v : ([Ljava/lang/Object;)Z
    //   239: aload #6
    //   241: ifnonnull -> 290
    //   244: ifne -> 289
    //   247: goto -> 254
    //   250: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   253: athrow
    //   254: aload_0
    //   255: getfield x : Lwtf/opal/ke;
    //   258: invokevirtual z : ()Ljava/lang/Object;
    //   261: checkcast java/lang/Boolean
    //   264: invokevirtual booleanValue : ()Z
    //   267: aload #6
    //   269: ifnonnull -> 290
    //   272: goto -> 279
    //   275: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   278: athrow
    //   279: ifne -> 293
    //   282: goto -> 289
    //   285: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   288: athrow
    //   289: iconst_1
    //   290: goto -> 294
    //   293: iconst_0
    //   294: ireturn
    // Exception table:
    //   from	to	target	type
    //   66	83	86	wtf/opal/x5
    //   80	109	112	wtf/opal/x5
    //   90	119	122	wtf/opal/x5
    //   116	158	161	wtf/opal/x5
    //   126	168	171	wtf/opal/x5
    //   165	177	177	wtf/opal/x5
    //   239	247	250	wtf/opal/x5
    //   244	272	275	wtf/opal/x5
    //   254	282	285	wtf/opal/x5
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((bn)this.u.z()).toString();
  }
  
  private void lambda$new$2(lu paramlu) {
    long l = g ^ 0x3F43960AB140L;
    try {
      if (paramlu.g(new Object[0]) instanceof net.minecraft.class_2708)
        this.l.z(new Object[0]); 
    } catch (x5 x5) {
      throw a(null);
    } 
  }
  
  private void lambda$new$1(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/jm.g : J
    //   3: ldc2_w 123451479296375
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 37709720236828
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 100969810817139
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic P : ()Ljava/lang/String;
    //   27: astore #8
    //   29: aload_0
    //   30: lload #4
    //   32: iconst_1
    //   33: anewarray java/lang/Object
    //   36: dup_x2
    //   37: dup_x2
    //   38: pop
    //   39: invokestatic valueOf : (J)Ljava/lang/Long;
    //   42: iconst_0
    //   43: swap
    //   44: aastore
    //   45: invokevirtual g : ([Ljava/lang/Object;)Z
    //   48: aload #8
    //   50: ifnonnull -> 114
    //   53: ifne -> 117
    //   56: goto -> 63
    //   59: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   62: athrow
    //   63: aload_0
    //   64: getfield M : Lwtf/opal/kt;
    //   67: invokevirtual z : ()Ljava/lang/Object;
    //   70: checkcast java/lang/Double
    //   73: invokevirtual doubleValue : ()D
    //   76: aload_0
    //   77: getfield l : Lwtf/opal/kr;
    //   80: iconst_0
    //   81: anewarray java/lang/Object
    //   84: invokevirtual E : ([Ljava/lang/Object;)J
    //   87: l2d
    //   88: ldc2_w 1000.0
    //   91: ddiv
    //   92: dsub
    //   93: dconst_0
    //   94: aload #8
    //   96: ifnonnull -> 147
    //   99: goto -> 106
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: dcmpg
    //   107: goto -> 114
    //   110: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: ifgt -> 118
    //   117: return
    //   118: aload_0
    //   119: getfield M : Lwtf/opal/kt;
    //   122: invokevirtual z : ()Ljava/lang/Object;
    //   125: checkcast java/lang/Double
    //   128: invokevirtual doubleValue : ()D
    //   131: aload_0
    //   132: getfield l : Lwtf/opal/kr;
    //   135: iconst_0
    //   136: anewarray java/lang/Object
    //   139: invokevirtual E : ([Ljava/lang/Object;)J
    //   142: l2d
    //   143: ldc2_w 1000.0
    //   146: ddiv
    //   147: dsub
    //   148: lload #6
    //   150: dup2_x2
    //   151: pop2
    //   152: iconst_1
    //   153: iconst_3
    //   154: anewarray java/lang/Object
    //   157: dup_x1
    //   158: swap
    //   159: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   162: iconst_2
    //   163: swap
    //   164: aastore
    //   165: dup_x2
    //   166: dup_x2
    //   167: pop
    //   168: invokestatic valueOf : (D)Ljava/lang/Double;
    //   171: iconst_1
    //   172: swap
    //   173: aastore
    //   174: dup_x2
    //   175: dup_x2
    //   176: pop
    //   177: invokestatic valueOf : (J)Ljava/lang/Long;
    //   180: iconst_0
    //   181: swap
    //   182: aastore
    //   183: invokestatic P : ([Ljava/lang/Object;)D
    //   186: sipush #1288
    //   189: ldc2_w 2081777062439051354
    //   192: lload_2
    //   193: lxor
    //   194: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   199: dup_x2
    //   200: pop
    //   201: sipush #30840
    //   204: ldc2_w 239166098501836078
    //   207: lload_2
    //   208: lxor
    //   209: <illegal opcode> f : (IJ)Ljava/lang/String;
    //   214: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;DLjava/lang/String;)Ljava/lang/String;
    //   219: astore #9
    //   221: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   224: getfield field_1772 : Lnet/minecraft/class_327;
    //   227: aload #9
    //   229: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   232: invokevirtual method_30937 : ()Lnet/minecraft/class_5481;
    //   235: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   238: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   241: invokevirtual method_4486 : ()I
    //   244: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   247: getfield field_1772 : Lnet/minecraft/class_327;
    //   250: aload #9
    //   252: invokevirtual method_1727 : (Ljava/lang/String;)I
    //   255: isub
    //   256: i2f
    //   257: fconst_2
    //   258: fdiv
    //   259: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   262: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   265: invokevirtual method_4502 : ()I
    //   268: i2f
    //   269: fconst_2
    //   270: fdiv
    //   271: ldc_w 16.0
    //   274: fadd
    //   275: sipush #13076
    //   278: ldc2_w 3157303521718414651
    //   281: lload_2
    //   282: lxor
    //   283: <illegal opcode> i : (IJ)I
    //   288: sipush #7632
    //   291: ldc2_w 8894145033007629310
    //   294: lload_2
    //   295: lxor
    //   296: <illegal opcode> i : (IJ)I
    //   301: aload_1
    //   302: iconst_0
    //   303: anewarray java/lang/Object
    //   306: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   309: invokevirtual method_51448 : ()Lnet/minecraft/class_4587;
    //   312: invokevirtual method_23760 : ()Lnet/minecraft/class_4587$class_4665;
    //   315: invokevirtual method_23761 : ()Lorg/joml/Matrix4f;
    //   318: aload_1
    //   319: iconst_0
    //   320: anewarray java/lang/Object
    //   323: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   326: invokevirtual method_51450 : ()Lnet/minecraft/class_4597$class_4598;
    //   329: iconst_0
    //   330: invokevirtual method_37296 : (Lnet/minecraft/class_5481;FFIILorg/joml/Matrix4f;Lnet/minecraft/class_4597;I)V
    //   333: aload_1
    //   334: iconst_0
    //   335: anewarray java/lang/Object
    //   338: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   341: invokevirtual method_51452 : ()V
    //   344: return
    // Exception table:
    //   from	to	target	type
    //   29	56	59	wtf/opal/x5
    //   53	99	102	wtf/opal/x5
    //   63	107	110	wtf/opal/x5
  }
  
  private void lambda$new$0(b6 paramb6) {
    long l1 = g ^ 0x4BC2CD235C08L;
    long l2 = l1 ^ 0x19CE7ECBE663L;
    long l3 = l1 ^ 0x5E3CD8338605L;
    String str = P();
    try {
      if (str == null)
        try {
          if (paramb6.W(new Object[0])) {
            new Object[1];
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (str == null)
        try {
          if (paramb6.W(new Object[0])) {
            new Object[1];
          } else {
            return;
          } 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    if (paramb6.W(new Object[0])) {
      b9.c.field_1690.field_1903.method_23481(false);
      return;
    } 
  }
  
  public static void Y(String paramString) {
    W = paramString;
  }
  
  public static String P() {
    return W;
  }
  
  static {
    long l = g ^ 0x27DA6BD562A8L;
    Y(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[8];
    boolean bool = false;
    String str;
    int i = (str = "\035Ã$Ýë¹<üüSÛ¡ÕYiqíPN\005§mÇð©\021Ûh?v50µÂ¿K ¼ ù\rýñO\024T¡íÐïä\001Ï\001á¤4û¹M\007p\013ïl0}àI=ÏËÆêÁ8ºÌB,a\017\r\016¤Ñu«ê^?è\034%\002|´\027@2¼\004@À)\016t!\030´º¡@eY/çB0<¯*\0231õÅa¯*\n?½\020\"Ösß)uãg>nè_Ç¦(=Ôy\036E\tä\016\036MQ¤<·q³\"\036(­­dºy@ÊÕn|YÔÝÖÿyÌØÅ").length();
    byte b2 = 40;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2973;
    if (m[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])n.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          n.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jm", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = k[i].getBytes("ISO-8859-1");
      m[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return m[i];
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
    //   65: ldc_w 'wtf/opal/jm'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x520E;
    if (p[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = o[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])q.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          q.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/jm", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      p[i] = Integer.valueOf(j);
    } 
    return p[i].intValue();
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
    //   65: ldc_w 'wtf/opal/jm'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jm.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */