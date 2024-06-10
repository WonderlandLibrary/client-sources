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

public final class pu {
  private static boolean c;
  
  private static final long a;
  
  private static final long[] b;
  
  private static final Integer[] d;
  
  private static final Map e;
  
  private static final long f;
  
  public static int[] J(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_2
    //   21: pop
    //   22: getstatic wtf/opal/pu.a : J
    //   25: lload_2
    //   26: lxor
    //   27: lstore_2
    //   28: iload_1
    //   29: sipush #20623
    //   32: ldc2_w 2961181653357391808
    //   35: lload_2
    //   36: lxor
    //   37: <illegal opcode> u : (IJ)I
    //   42: ishr
    //   43: sipush #3985
    //   46: ldc2_w 6975378557531959514
    //   49: lload_2
    //   50: lxor
    //   51: <illegal opcode> u : (IJ)I
    //   56: iand
    //   57: istore #4
    //   59: iload_1
    //   60: sipush #13939
    //   63: ldc2_w 7614639094433417530
    //   66: lload_2
    //   67: lxor
    //   68: <illegal opcode> u : (IJ)I
    //   73: ishr
    //   74: sipush #3985
    //   77: ldc2_w 6975378557531959514
    //   80: lload_2
    //   81: lxor
    //   82: <illegal opcode> u : (IJ)I
    //   87: iand
    //   88: istore #5
    //   90: iload_1
    //   91: sipush #3985
    //   94: ldc2_w 6975378557531959514
    //   97: lload_2
    //   98: lxor
    //   99: <illegal opcode> u : (IJ)I
    //   104: iand
    //   105: istore #6
    //   107: iload_1
    //   108: sipush #4021
    //   111: ldc2_w 8168512699387949309
    //   114: lload_2
    //   115: lxor
    //   116: <illegal opcode> u : (IJ)I
    //   121: ishr
    //   122: sipush #3985
    //   125: ldc2_w 6975378557531959514
    //   128: lload_2
    //   129: lxor
    //   130: <illegal opcode> u : (IJ)I
    //   135: iand
    //   136: istore #7
    //   138: iconst_4
    //   139: newarray int
    //   141: dup
    //   142: iconst_0
    //   143: iload #4
    //   145: iastore
    //   146: dup
    //   147: iconst_1
    //   148: iload #5
    //   150: iastore
    //   151: dup
    //   152: iconst_2
    //   153: iload #6
    //   155: iastore
    //   156: dup
    //   157: iconst_3
    //   158: iload #7
    //   160: iastore
    //   161: areturn
  }
  
  public static Color Y(Object[] paramArrayOfObject) {
    Color color = (Color)paramArrayOfObject[0];
    float f = ((Float)paramArrayOfObject[1]).floatValue();
    f = Math.min(1.0F, Math.max(0.0F, f));
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * f));
  }
  
  public static int X(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    float f = ((Float)paramArrayOfObject[1]).floatValue();
    f = Math.min(1.0F, Math.max(0.0F, f));
    Color color = new Color(i);
    return (new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * f))).getRGB();
  }
  
  public static float[] M(Object[] paramArrayOfObject) {
    Color color = (Color)paramArrayOfObject[0];
    return new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F };
  }
  
  public static int P(Object[] paramArrayOfObject) {
    int j = ((Integer)paramArrayOfObject[0]).intValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    float f = ((Float)paramArrayOfObject[2]).floatValue();
    f = Math.min(1.0F, Math.max(0.0F, f));
    Color color1 = new Color(j, true);
    Color color2 = new Color(i, true);
    new Object[3];
    int k = d6.L(new Object[] { null, null, Double.valueOf(f), Integer.valueOf(color2.getRed()), Integer.valueOf(color1.getRed()) });
    new Object[3];
    int m = d6.L(new Object[] { null, null, Double.valueOf(f), Integer.valueOf(color2.getGreen()), Integer.valueOf(color1.getGreen()) });
    new Object[3];
    int n = d6.L(new Object[] { null, null, Double.valueOf(f), Integer.valueOf(color2.getBlue()), Integer.valueOf(color1.getBlue()) });
    new Object[3];
    int i1 = d6.L(new Object[] { null, null, Double.valueOf(f), Integer.valueOf(color2.getAlpha()), Integer.valueOf(color1.getAlpha()) });
    return (new Color(k, m, n, i1)).getRGB();
  }
  
  public static int F(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[0];
    long l = ((Long)paramArrayOfObject[1]).longValue();
    float f = ((Float)paramArrayOfObject[2]).floatValue();
    l = a ^ l;
    int i = arrayOfInt.length;
    boolean bool = P();
    try {
      if (bool)
        try {
          if (f == 1.0F)
            return arrayOfInt[0]; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    try {
      if (bool) {
        try {
          if (f == 0.0F)
            return arrayOfInt[i - 1]; 
        } catch (x5 x5) {
          throw a(null);
        } 
        f = Math.max(0.0F, (1.0F - f) * (i - 1));
      } 
    } catch (x5 x5) {
      throw a(null);
    } 
    int j = (int)f;
    return P(new Object[] { null, null, Float.valueOf(f - j), Integer.valueOf(arrayOfInt[j + 1]), Integer.valueOf(arrayOfInt[j]) });
  }
  
  public static int K(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore #6
    //   22: dup
    //   23: iconst_2
    //   24: aaload
    //   25: checkcast java/lang/Long
    //   28: invokevirtual longValue : ()J
    //   31: lstore_2
    //   32: dup
    //   33: iconst_3
    //   34: aaload
    //   35: checkcast java/lang/Integer
    //   38: invokevirtual intValue : ()I
    //   41: istore #4
    //   43: dup
    //   44: iconst_4
    //   45: aaload
    //   46: checkcast java/lang/Integer
    //   49: invokevirtual intValue : ()I
    //   52: istore #5
    //   54: pop
    //   55: getstatic wtf/opal/pu.a : J
    //   58: lload_2
    //   59: lxor
    //   60: lstore_2
    //   61: invokestatic P : ()Z
    //   64: invokestatic currentTimeMillis : ()J
    //   67: iload_1
    //   68: i2l
    //   69: ldiv
    //   70: iload #6
    //   72: i2l
    //   73: lsub
    //   74: getstatic wtf/opal/pu.f : J
    //   77: lrem
    //   78: l2i
    //   79: istore #8
    //   81: istore #7
    //   83: iload #8
    //   85: sipush #15759
    //   88: ldc2_w 6253048436153706488
    //   91: lload_2
    //   92: lxor
    //   93: <illegal opcode> u : (IJ)I
    //   98: iload #7
    //   100: ifeq -> 135
    //   103: if_icmplt -> 139
    //   106: goto -> 113
    //   109: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   112: athrow
    //   113: sipush #13495
    //   116: ldc2_w 5492286585096203975
    //   119: lload_2
    //   120: lxor
    //   121: <illegal opcode> u : (IJ)I
    //   126: iload #8
    //   128: goto -> 135
    //   131: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   134: athrow
    //   135: isub
    //   136: goto -> 141
    //   139: iload #8
    //   141: iconst_2
    //   142: imul
    //   143: istore #8
    //   145: iload #4
    //   147: iload #5
    //   149: iload #8
    //   151: i2f
    //   152: ldc 360.0
    //   154: fdiv
    //   155: iconst_3
    //   156: anewarray java/lang/Object
    //   159: dup_x1
    //   160: swap
    //   161: invokestatic valueOf : (F)Ljava/lang/Float;
    //   164: iconst_2
    //   165: swap
    //   166: aastore
    //   167: dup_x1
    //   168: swap
    //   169: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   172: iconst_1
    //   173: swap
    //   174: aastore
    //   175: dup_x1
    //   176: swap
    //   177: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   180: iconst_0
    //   181: swap
    //   182: aastore
    //   183: invokestatic P : ([Ljava/lang/Object;)I
    //   186: ireturn
    // Exception table:
    //   from	to	target	type
    //   83	106	109	wtf/opal/x5
    //   103	128	131	wtf/opal/x5
  }
  
  public static int c(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_3
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Float
    //   27: invokevirtual floatValue : ()F
    //   30: fstore #4
    //   32: pop
    //   33: getstatic wtf/opal/pu.a : J
    //   36: lload_1
    //   37: lxor
    //   38: lstore_1
    //   39: fconst_1
    //   40: fload #4
    //   42: fsub
    //   43: fstore #5
    //   45: iload_3
    //   46: sipush #20623
    //   49: ldc2_w 2961249019946509024
    //   52: lload_1
    //   53: lxor
    //   54: <illegal opcode> u : (IJ)I
    //   59: ishr
    //   60: sipush #3985
    //   63: ldc2_w 6975449763688119802
    //   66: lload_1
    //   67: lxor
    //   68: <illegal opcode> u : (IJ)I
    //   73: iand
    //   74: i2f
    //   75: fload #5
    //   77: fmul
    //   78: f2i
    //   79: istore #6
    //   81: iload_3
    //   82: sipush #13939
    //   85: ldc2_w 7614710266296943642
    //   88: lload_1
    //   89: lxor
    //   90: <illegal opcode> u : (IJ)I
    //   95: ishr
    //   96: sipush #3985
    //   99: ldc2_w 6975449763688119802
    //   102: lload_1
    //   103: lxor
    //   104: <illegal opcode> u : (IJ)I
    //   109: iand
    //   110: i2f
    //   111: fload #5
    //   113: fmul
    //   114: f2i
    //   115: istore #7
    //   117: iload_3
    //   118: sipush #3985
    //   121: ldc2_w 6975449763688119802
    //   124: lload_1
    //   125: lxor
    //   126: <illegal opcode> u : (IJ)I
    //   131: iand
    //   132: i2f
    //   133: fload #5
    //   135: fmul
    //   136: f2i
    //   137: istore #8
    //   139: iload_3
    //   140: sipush #4021
    //   143: ldc2_w 8168439319781899741
    //   146: lload_1
    //   147: lxor
    //   148: <illegal opcode> u : (IJ)I
    //   153: ishr
    //   154: sipush #3985
    //   157: ldc2_w 6975449763688119802
    //   160: lload_1
    //   161: lxor
    //   162: <illegal opcode> u : (IJ)I
    //   167: iand
    //   168: istore #9
    //   170: iload #6
    //   172: sipush #3985
    //   175: ldc2_w 6975449763688119802
    //   178: lload_1
    //   179: lxor
    //   180: <illegal opcode> u : (IJ)I
    //   185: iand
    //   186: sipush #20623
    //   189: ldc2_w 2961249019946509024
    //   192: lload_1
    //   193: lxor
    //   194: <illegal opcode> u : (IJ)I
    //   199: ishl
    //   200: iload #7
    //   202: sipush #3985
    //   205: ldc2_w 6975449763688119802
    //   208: lload_1
    //   209: lxor
    //   210: <illegal opcode> u : (IJ)I
    //   215: iand
    //   216: sipush #13939
    //   219: ldc2_w 7614710266296943642
    //   222: lload_1
    //   223: lxor
    //   224: <illegal opcode> u : (IJ)I
    //   229: ishl
    //   230: ior
    //   231: iload #8
    //   233: sipush #3985
    //   236: ldc2_w 6975449763688119802
    //   239: lload_1
    //   240: lxor
    //   241: <illegal opcode> u : (IJ)I
    //   246: iand
    //   247: ior
    //   248: iload #9
    //   250: sipush #3985
    //   253: ldc2_w 6975449763688119802
    //   256: lload_1
    //   257: lxor
    //   258: <illegal opcode> u : (IJ)I
    //   263: iand
    //   264: sipush #4021
    //   267: ldc2_w 8168439319781899741
    //   270: lload_1
    //   271: lxor
    //   272: <illegal opcode> u : (IJ)I
    //   277: ishl
    //   278: ior
    //   279: ireturn
  }
  
  public static int Z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    int i = ((Integer)paramArrayOfObject[1]).intValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x18C235E98A4L;
    (new Object[3])[2] = Float.valueOf(0.3F);
    (new Object[3])[1] = Integer.valueOf(i);
    new Object[3];
    return c(new Object[] { Long.valueOf(l2) });
  }
  
  public static int V(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Long
    //   17: invokevirtual longValue : ()J
    //   20: lstore_1
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Float
    //   27: invokevirtual floatValue : ()F
    //   30: fstore #4
    //   32: pop
    //   33: getstatic wtf/opal/pu.a : J
    //   36: lload_1
    //   37: lxor
    //   38: lstore_1
    //   39: invokestatic P : ()Z
    //   42: iload_3
    //   43: sipush #28087
    //   46: ldc2_w 8295161948500120335
    //   49: lload_1
    //   50: lxor
    //   51: <illegal opcode> u : (IJ)I
    //   56: ishr
    //   57: sipush #27259
    //   60: ldc2_w 2316218563969963212
    //   63: lload_1
    //   64: lxor
    //   65: <illegal opcode> u : (IJ)I
    //   70: iand
    //   71: istore #6
    //   73: iload_3
    //   74: sipush #10102
    //   77: ldc2_w 96027176920095168
    //   80: lload_1
    //   81: lxor
    //   82: <illegal opcode> u : (IJ)I
    //   87: ishr
    //   88: sipush #3985
    //   91: ldc2_w 6975408669715706158
    //   94: lload_1
    //   95: lxor
    //   96: <illegal opcode> u : (IJ)I
    //   101: iand
    //   102: istore #7
    //   104: istore #5
    //   106: iload_3
    //   107: sipush #3985
    //   110: ldc2_w 6975408669715706158
    //   113: lload_1
    //   114: lxor
    //   115: <illegal opcode> u : (IJ)I
    //   120: iand
    //   121: istore #8
    //   123: iload_3
    //   124: sipush #31719
    //   127: ldc2_w 6938989134606550365
    //   130: lload_1
    //   131: lxor
    //   132: <illegal opcode> u : (IJ)I
    //   137: ishr
    //   138: sipush #3985
    //   141: ldc2_w 6975408669715706158
    //   144: lload_1
    //   145: lxor
    //   146: <illegal opcode> u : (IJ)I
    //   151: iand
    //   152: istore #9
    //   154: dconst_1
    //   155: fconst_1
    //   156: fload #4
    //   158: fsub
    //   159: f2d
    //   160: ddiv
    //   161: d2i
    //   162: istore #10
    //   164: iload #6
    //   166: iload #5
    //   168: ifeq -> 260
    //   171: ifne -> 258
    //   174: goto -> 181
    //   177: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   180: athrow
    //   181: iload #7
    //   183: iload #5
    //   185: ifeq -> 260
    //   188: goto -> 195
    //   191: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   194: athrow
    //   195: ifne -> 258
    //   198: goto -> 205
    //   201: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   204: athrow
    //   205: iload #8
    //   207: iload #5
    //   209: lload_1
    //   210: lconst_0
    //   211: lcmp
    //   212: iflt -> 262
    //   215: ifeq -> 260
    //   218: goto -> 225
    //   221: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   224: athrow
    //   225: ifne -> 258
    //   228: goto -> 235
    //   231: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   234: athrow
    //   235: new java/awt/Color
    //   238: dup
    //   239: iload #10
    //   241: iload #10
    //   243: iload #10
    //   245: iload #9
    //   247: invokespecial <init> : (IIII)V
    //   250: invokevirtual getRGB : ()I
    //   253: ireturn
    //   254: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   257: athrow
    //   258: iload #6
    //   260: iload #5
    //   262: ifeq -> 313
    //   265: ifle -> 311
    //   268: goto -> 275
    //   271: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   274: athrow
    //   275: iload #6
    //   277: iload #5
    //   279: lload_1
    //   280: lconst_0
    //   281: lcmp
    //   282: iflt -> 315
    //   285: ifeq -> 313
    //   288: goto -> 295
    //   291: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   294: athrow
    //   295: iload #10
    //   297: if_icmpge -> 311
    //   300: goto -> 307
    //   303: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   306: athrow
    //   307: iload #10
    //   309: istore #6
    //   311: iload #7
    //   313: iload #5
    //   315: ifeq -> 366
    //   318: ifle -> 364
    //   321: goto -> 328
    //   324: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   327: athrow
    //   328: iload #7
    //   330: iload #5
    //   332: lload_1
    //   333: lconst_0
    //   334: lcmp
    //   335: iflt -> 368
    //   338: ifeq -> 366
    //   341: goto -> 348
    //   344: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   347: athrow
    //   348: iload #10
    //   350: if_icmpge -> 364
    //   353: goto -> 360
    //   356: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   359: athrow
    //   360: iload #10
    //   362: istore #7
    //   364: iload #8
    //   366: iload #5
    //   368: ifeq -> 492
    //   371: ifle -> 411
    //   374: goto -> 381
    //   377: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   380: athrow
    //   381: iload #8
    //   383: iload #5
    //   385: ifeq -> 492
    //   388: goto -> 395
    //   391: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   394: athrow
    //   395: iload #10
    //   397: if_icmpge -> 411
    //   400: goto -> 407
    //   403: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   406: athrow
    //   407: iload #10
    //   409: istore #8
    //   411: new java/awt/Color
    //   414: dup
    //   415: iload #6
    //   417: i2f
    //   418: fload #4
    //   420: fdiv
    //   421: f2i
    //   422: sipush #3985
    //   425: ldc2_w 6975408669715706158
    //   428: lload_1
    //   429: lxor
    //   430: <illegal opcode> u : (IJ)I
    //   435: invokestatic min : (II)I
    //   438: iload #7
    //   440: i2f
    //   441: fload #4
    //   443: fdiv
    //   444: f2i
    //   445: sipush #3985
    //   448: ldc2_w 6975408669715706158
    //   451: lload_1
    //   452: lxor
    //   453: <illegal opcode> u : (IJ)I
    //   458: invokestatic min : (II)I
    //   461: iload #8
    //   463: i2f
    //   464: fload #4
    //   466: fdiv
    //   467: f2i
    //   468: sipush #3985
    //   471: ldc2_w 6975408669715706158
    //   474: lload_1
    //   475: lxor
    //   476: <illegal opcode> u : (IJ)I
    //   481: invokestatic min : (II)I
    //   484: iload #9
    //   486: invokespecial <init> : (IIII)V
    //   489: invokevirtual getRGB : ()I
    //   492: ireturn
    // Exception table:
    //   from	to	target	type
    //   164	174	177	wtf/opal/x5
    //   171	188	191	wtf/opal/x5
    //   181	198	201	wtf/opal/x5
    //   195	218	221	wtf/opal/x5
    //   205	228	231	wtf/opal/x5
    //   225	254	254	wtf/opal/x5
    //   260	268	271	wtf/opal/x5
    //   265	288	291	wtf/opal/x5
    //   275	300	303	wtf/opal/x5
    //   313	321	324	wtf/opal/x5
    //   318	341	344	wtf/opal/x5
    //   328	353	356	wtf/opal/x5
    //   366	374	377	wtf/opal/x5
    //   371	388	391	wtf/opal/x5
    //   381	400	403	wtf/opal/x5
  }
  
  public static int S(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x56A2BEB447E2L;
    (new Object[3])[2] = Float.valueOf(0.7F);
    new Object[3];
    return V(new Object[] { null, Long.valueOf(l2), Integer.valueOf(i) });
  }
  
  public static void v(boolean paramBoolean) {
    c = paramBoolean;
  }
  
  public static boolean Y() {
    return c;
  }
  
  public static boolean P() {
    boolean bool = Y();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return false;
  }
  
  static {
    // Byte code:
    //   0: ldc2_w -2222789948065171803
    //   3: ldc2_w -4396942185815264448
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 34592498021144
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/pu.a : J
    //   26: iconst_0
    //   27: new java/util/HashMap
    //   30: dup
    //   31: bipush #13
    //   33: invokespecial <init> : (I)V
    //   36: putstatic wtf/opal/pu.e : Ljava/util/Map;
    //   39: invokestatic v : (Z)V
    //   42: getstatic wtf/opal/pu.a : J
    //   45: ldc2_w 137659963346721
    //   48: lxor
    //   49: lstore #5
    //   51: ldc 'DES/CBC/NoPadding'
    //   53: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   56: dup
    //   57: astore #7
    //   59: iconst_2
    //   60: ldc 'DES'
    //   62: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   65: bipush #8
    //   67: newarray byte
    //   69: dup
    //   70: iconst_0
    //   71: lload #5
    //   73: bipush #56
    //   75: lushr
    //   76: l2i
    //   77: i2b
    //   78: bastore
    //   79: iconst_1
    //   80: istore #8
    //   82: iload #8
    //   84: bipush #8
    //   86: if_icmpge -> 112
    //   89: dup
    //   90: iload #8
    //   92: lload #5
    //   94: iload #8
    //   96: bipush #8
    //   98: imul
    //   99: lshl
    //   100: bipush #56
    //   102: lushr
    //   103: l2i
    //   104: i2b
    //   105: bastore
    //   106: iinc #8, 1
    //   109: goto -> 82
    //   112: new javax/crypto/spec/DESKeySpec
    //   115: dup_x1
    //   116: swap
    //   117: invokespecial <init> : ([B)V
    //   120: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   123: new javax/crypto/spec/IvParameterSpec
    //   126: dup
    //   127: bipush #8
    //   129: newarray byte
    //   131: invokespecial <init> : ([B)V
    //   134: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   137: bipush #10
    //   139: newarray long
    //   141: astore #13
    //   143: iconst_0
    //   144: istore #10
    //   146: ldc_w 'GøFòíä[¼:eJÎÀ#Jü³F6Ð^&TÏûpQÌ-ßîa+r¤ãðëb&(jÇ6Ä½Ä'
    //   149: dup
    //   150: astore #11
    //   152: invokevirtual length : ()I
    //   155: istore #12
    //   157: iconst_0
    //   158: istore #9
    //   160: aload #11
    //   162: iload #9
    //   164: iinc #9, 8
    //   167: iload #9
    //   169: invokevirtual substring : (II)Ljava/lang/String;
    //   172: ldc_w 'ISO-8859-1'
    //   175: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   178: astore #14
    //   180: aload #13
    //   182: iload #10
    //   184: iinc #10, 1
    //   187: aload #14
    //   189: iconst_0
    //   190: baload
    //   191: i2l
    //   192: ldc2_w 255
    //   195: land
    //   196: bipush #56
    //   198: lshl
    //   199: aload #14
    //   201: iconst_1
    //   202: baload
    //   203: i2l
    //   204: ldc2_w 255
    //   207: land
    //   208: bipush #48
    //   210: lshl
    //   211: lor
    //   212: aload #14
    //   214: iconst_2
    //   215: baload
    //   216: i2l
    //   217: ldc2_w 255
    //   220: land
    //   221: bipush #40
    //   223: lshl
    //   224: lor
    //   225: aload #14
    //   227: iconst_3
    //   228: baload
    //   229: i2l
    //   230: ldc2_w 255
    //   233: land
    //   234: bipush #32
    //   236: lshl
    //   237: lor
    //   238: aload #14
    //   240: iconst_4
    //   241: baload
    //   242: i2l
    //   243: ldc2_w 255
    //   246: land
    //   247: bipush #24
    //   249: lshl
    //   250: lor
    //   251: aload #14
    //   253: iconst_5
    //   254: baload
    //   255: i2l
    //   256: ldc2_w 255
    //   259: land
    //   260: bipush #16
    //   262: lshl
    //   263: lor
    //   264: aload #14
    //   266: bipush #6
    //   268: baload
    //   269: i2l
    //   270: ldc2_w 255
    //   273: land
    //   274: bipush #8
    //   276: lshl
    //   277: lor
    //   278: aload #14
    //   280: bipush #7
    //   282: baload
    //   283: i2l
    //   284: ldc2_w 255
    //   287: land
    //   288: lor
    //   289: iconst_m1
    //   290: goto -> 472
    //   293: lastore
    //   294: iload #9
    //   296: iload #12
    //   298: if_icmplt -> 160
    //   301: ldc_w '¸°,ãMñäÕpq»'
    //   304: dup
    //   305: astore #11
    //   307: invokevirtual length : ()I
    //   310: istore #12
    //   312: iconst_0
    //   313: istore #9
    //   315: aload #11
    //   317: iload #9
    //   319: iinc #9, 8
    //   322: iload #9
    //   324: invokevirtual substring : (II)Ljava/lang/String;
    //   327: ldc_w 'ISO-8859-1'
    //   330: invokevirtual getBytes : (Ljava/lang/String;)[B
    //   333: astore #14
    //   335: aload #13
    //   337: iload #10
    //   339: iinc #10, 1
    //   342: aload #14
    //   344: iconst_0
    //   345: baload
    //   346: i2l
    //   347: ldc2_w 255
    //   350: land
    //   351: bipush #56
    //   353: lshl
    //   354: aload #14
    //   356: iconst_1
    //   357: baload
    //   358: i2l
    //   359: ldc2_w 255
    //   362: land
    //   363: bipush #48
    //   365: lshl
    //   366: lor
    //   367: aload #14
    //   369: iconst_2
    //   370: baload
    //   371: i2l
    //   372: ldc2_w 255
    //   375: land
    //   376: bipush #40
    //   378: lshl
    //   379: lor
    //   380: aload #14
    //   382: iconst_3
    //   383: baload
    //   384: i2l
    //   385: ldc2_w 255
    //   388: land
    //   389: bipush #32
    //   391: lshl
    //   392: lor
    //   393: aload #14
    //   395: iconst_4
    //   396: baload
    //   397: i2l
    //   398: ldc2_w 255
    //   401: land
    //   402: bipush #24
    //   404: lshl
    //   405: lor
    //   406: aload #14
    //   408: iconst_5
    //   409: baload
    //   410: i2l
    //   411: ldc2_w 255
    //   414: land
    //   415: bipush #16
    //   417: lshl
    //   418: lor
    //   419: aload #14
    //   421: bipush #6
    //   423: baload
    //   424: i2l
    //   425: ldc2_w 255
    //   428: land
    //   429: bipush #8
    //   431: lshl
    //   432: lor
    //   433: aload #14
    //   435: bipush #7
    //   437: baload
    //   438: i2l
    //   439: ldc2_w 255
    //   442: land
    //   443: lor
    //   444: iconst_0
    //   445: goto -> 472
    //   448: lastore
    //   449: iload #9
    //   451: iload #12
    //   453: if_icmplt -> 315
    //   456: aload #13
    //   458: putstatic wtf/opal/pu.b : [J
    //   461: bipush #10
    //   463: anewarray java/lang/Integer
    //   466: putstatic wtf/opal/pu.d : [Ljava/lang/Integer;
    //   469: goto -> 688
    //   472: dup_x2
    //   473: pop
    //   474: lstore #15
    //   476: bipush #8
    //   478: newarray byte
    //   480: dup
    //   481: iconst_0
    //   482: lload #15
    //   484: bipush #56
    //   486: lushr
    //   487: l2i
    //   488: i2b
    //   489: bastore
    //   490: dup
    //   491: iconst_1
    //   492: lload #15
    //   494: bipush #48
    //   496: lushr
    //   497: l2i
    //   498: i2b
    //   499: bastore
    //   500: dup
    //   501: iconst_2
    //   502: lload #15
    //   504: bipush #40
    //   506: lushr
    //   507: l2i
    //   508: i2b
    //   509: bastore
    //   510: dup
    //   511: iconst_3
    //   512: lload #15
    //   514: bipush #32
    //   516: lushr
    //   517: l2i
    //   518: i2b
    //   519: bastore
    //   520: dup
    //   521: iconst_4
    //   522: lload #15
    //   524: bipush #24
    //   526: lushr
    //   527: l2i
    //   528: i2b
    //   529: bastore
    //   530: dup
    //   531: iconst_5
    //   532: lload #15
    //   534: bipush #16
    //   536: lushr
    //   537: l2i
    //   538: i2b
    //   539: bastore
    //   540: dup
    //   541: bipush #6
    //   543: lload #15
    //   545: bipush #8
    //   547: lushr
    //   548: l2i
    //   549: i2b
    //   550: bastore
    //   551: dup
    //   552: bipush #7
    //   554: lload #15
    //   556: l2i
    //   557: i2b
    //   558: bastore
    //   559: aload #7
    //   561: swap
    //   562: invokevirtual doFinal : ([B)[B
    //   565: astore #17
    //   567: aload #17
    //   569: iconst_0
    //   570: baload
    //   571: i2l
    //   572: ldc2_w 255
    //   575: land
    //   576: bipush #56
    //   578: lshl
    //   579: aload #17
    //   581: iconst_1
    //   582: baload
    //   583: i2l
    //   584: ldc2_w 255
    //   587: land
    //   588: bipush #48
    //   590: lshl
    //   591: lor
    //   592: aload #17
    //   594: iconst_2
    //   595: baload
    //   596: i2l
    //   597: ldc2_w 255
    //   600: land
    //   601: bipush #40
    //   603: lshl
    //   604: lor
    //   605: aload #17
    //   607: iconst_3
    //   608: baload
    //   609: i2l
    //   610: ldc2_w 255
    //   613: land
    //   614: bipush #32
    //   616: lshl
    //   617: lor
    //   618: aload #17
    //   620: iconst_4
    //   621: baload
    //   622: i2l
    //   623: ldc2_w 255
    //   626: land
    //   627: bipush #24
    //   629: lshl
    //   630: lor
    //   631: aload #17
    //   633: iconst_5
    //   634: baload
    //   635: i2l
    //   636: ldc2_w 255
    //   639: land
    //   640: bipush #16
    //   642: lshl
    //   643: lor
    //   644: aload #17
    //   646: bipush #6
    //   648: baload
    //   649: i2l
    //   650: ldc2_w 255
    //   653: land
    //   654: bipush #8
    //   656: lshl
    //   657: lor
    //   658: aload #17
    //   660: bipush #7
    //   662: baload
    //   663: i2l
    //   664: ldc2_w 255
    //   667: land
    //   668: lor
    //   669: dup2_x1
    //   670: pop2
    //   671: tableswitch default -> 293, 0 -> 448
    //   688: ldc 'DES/CBC/NoPadding'
    //   690: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/Cipher;
    //   693: dup
    //   694: astore_0
    //   695: iconst_2
    //   696: ldc 'DES'
    //   698: invokestatic getInstance : (Ljava/lang/String;)Ljavax/crypto/SecretKeyFactory;
    //   701: bipush #8
    //   703: newarray byte
    //   705: dup
    //   706: iconst_0
    //   707: lload #5
    //   709: bipush #56
    //   711: lushr
    //   712: l2i
    //   713: i2b
    //   714: bastore
    //   715: iconst_1
    //   716: istore_1
    //   717: iload_1
    //   718: bipush #8
    //   720: if_icmpge -> 744
    //   723: dup
    //   724: iload_1
    //   725: lload #5
    //   727: iload_1
    //   728: bipush #8
    //   730: imul
    //   731: lshl
    //   732: bipush #56
    //   734: lushr
    //   735: l2i
    //   736: i2b
    //   737: bastore
    //   738: iinc #1, 1
    //   741: goto -> 717
    //   744: new javax/crypto/spec/DESKeySpec
    //   747: dup_x1
    //   748: swap
    //   749: invokespecial <init> : ([B)V
    //   752: invokevirtual generateSecret : (Ljava/security/spec/KeySpec;)Ljavax/crypto/SecretKey;
    //   755: new javax/crypto/spec/IvParameterSpec
    //   758: dup
    //   759: bipush #8
    //   761: newarray byte
    //   763: invokespecial <init> : ([B)V
    //   766: invokevirtual init : (ILjava/security/Key;Ljava/security/spec/AlgorithmParameterSpec;)V
    //   769: ldc2_w 5122591704495232843
    //   772: iconst_m1
    //   773: goto -> 782
    //   776: putstatic wtf/opal/pu.f : J
    //   779: goto -> 975
    //   782: dup_x2
    //   783: pop
    //   784: lstore_2
    //   785: bipush #8
    //   787: newarray byte
    //   789: dup
    //   790: iconst_0
    //   791: lload_2
    //   792: bipush #56
    //   794: lushr
    //   795: l2i
    //   796: i2b
    //   797: bastore
    //   798: dup
    //   799: iconst_1
    //   800: lload_2
    //   801: bipush #48
    //   803: lushr
    //   804: l2i
    //   805: i2b
    //   806: bastore
    //   807: dup
    //   808: iconst_2
    //   809: lload_2
    //   810: bipush #40
    //   812: lushr
    //   813: l2i
    //   814: i2b
    //   815: bastore
    //   816: dup
    //   817: iconst_3
    //   818: lload_2
    //   819: bipush #32
    //   821: lushr
    //   822: l2i
    //   823: i2b
    //   824: bastore
    //   825: dup
    //   826: iconst_4
    //   827: lload_2
    //   828: bipush #24
    //   830: lushr
    //   831: l2i
    //   832: i2b
    //   833: bastore
    //   834: dup
    //   835: iconst_5
    //   836: lload_2
    //   837: bipush #16
    //   839: lushr
    //   840: l2i
    //   841: i2b
    //   842: bastore
    //   843: dup
    //   844: bipush #6
    //   846: lload_2
    //   847: bipush #8
    //   849: lushr
    //   850: l2i
    //   851: i2b
    //   852: bastore
    //   853: dup
    //   854: bipush #7
    //   856: lload_2
    //   857: l2i
    //   858: i2b
    //   859: bastore
    //   860: aload_0
    //   861: swap
    //   862: invokevirtual doFinal : ([B)[B
    //   865: astore #4
    //   867: aload #4
    //   869: iconst_0
    //   870: baload
    //   871: i2l
    //   872: ldc2_w 255
    //   875: land
    //   876: bipush #56
    //   878: lshl
    //   879: aload #4
    //   881: iconst_1
    //   882: baload
    //   883: i2l
    //   884: ldc2_w 255
    //   887: land
    //   888: bipush #48
    //   890: lshl
    //   891: lor
    //   892: aload #4
    //   894: iconst_2
    //   895: baload
    //   896: i2l
    //   897: ldc2_w 255
    //   900: land
    //   901: bipush #40
    //   903: lshl
    //   904: lor
    //   905: aload #4
    //   907: iconst_3
    //   908: baload
    //   909: i2l
    //   910: ldc2_w 255
    //   913: land
    //   914: bipush #32
    //   916: lshl
    //   917: lor
    //   918: aload #4
    //   920: iconst_4
    //   921: baload
    //   922: i2l
    //   923: ldc2_w 255
    //   926: land
    //   927: bipush #24
    //   929: lshl
    //   930: lor
    //   931: aload #4
    //   933: iconst_5
    //   934: baload
    //   935: i2l
    //   936: ldc2_w 255
    //   939: land
    //   940: bipush #16
    //   942: lshl
    //   943: lor
    //   944: aload #4
    //   946: bipush #6
    //   948: baload
    //   949: i2l
    //   950: ldc2_w 255
    //   953: land
    //   954: bipush #8
    //   956: lshl
    //   957: lor
    //   958: aload #4
    //   960: bipush #7
    //   962: baload
    //   963: i2l
    //   964: ldc2_w 255
    //   967: land
    //   968: lor
    //   969: dup2_x1
    //   970: pop2
    //   971: pop
    //   972: goto -> 776
    //   975: return
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4DF0;
    if (d[i] == null) {
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
        throw new RuntimeException("wtf/opal/pu", exception);
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
    //   65: ldc_w 'wtf/opal/pu'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */