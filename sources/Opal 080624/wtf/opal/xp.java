package wtf.opal;

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

public final class xp {
  private static final pa z;
  
  private static final dc X;
  
  private final dk E;
  
  private String e;
  
  private String R;
  
  private float q;
  
  private float f;
  
  private float G;
  
  private float D;
  
  private float a;
  
  private float J;
  
  private int m;
  
  private int i;
  
  private int d;
  
  private int N;
  
  private static int u;
  
  private static final long b;
  
  private static final long[] c;
  
  private static final Integer[] g;
  
  private static final Map h;
  
  public xp(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, char paramChar1, float paramFloat4, int paramInt1, int paramInt2, char paramChar2) {
    // Byte code:
    //   0: iload #5
    //   2: i2l
    //   3: bipush #48
    //   5: lshl
    //   6: iload #8
    //   8: i2l
    //   9: bipush #32
    //   11: lshl
    //   12: bipush #16
    //   14: lushr
    //   15: lor
    //   16: iload #9
    //   18: i2l
    //   19: bipush #48
    //   21: lshl
    //   22: bipush #48
    //   24: lushr
    //   25: lor
    //   26: getstatic wtf/opal/xp.b : J
    //   29: lxor
    //   30: lstore #10
    //   32: lload #10
    //   34: dup2
    //   35: ldc2_w 75145279848811
    //   38: lxor
    //   39: dup2
    //   40: bipush #32
    //   42: lushr
    //   43: l2i
    //   44: istore #12
    //   46: dup2
    //   47: bipush #32
    //   49: lshl
    //   50: bipush #48
    //   52: lushr
    //   53: l2i
    //   54: istore #13
    //   56: dup2
    //   57: bipush #48
    //   59: lshl
    //   60: bipush #48
    //   62: lushr
    //   63: l2i
    //   64: istore #14
    //   66: pop2
    //   67: pop2
    //   68: aload_0
    //   69: invokespecial <init> : ()V
    //   72: aload_0
    //   73: new wtf/opal/dk
    //   76: dup
    //   77: sipush #3580
    //   80: ldc2_w 8692832519706124546
    //   83: lload #10
    //   85: lxor
    //   86: <illegal opcode> d : (IJ)I
    //   91: iload #12
    //   93: dconst_1
    //   94: iload #13
    //   96: i2c
    //   97: iload #14
    //   99: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   102: invokespecial <init> : (IIDCILwtf/opal/lp;)V
    //   105: putfield E : Lwtf/opal/dk;
    //   108: aload_0
    //   109: aload_1
    //   110: putfield e : Ljava/lang/String;
    //   113: aload_0
    //   114: fload_2
    //   115: putfield q : F
    //   118: aload_0
    //   119: fload_3
    //   120: putfield f : F
    //   123: aload_0
    //   124: fload #4
    //   126: putfield G : F
    //   129: aload_0
    //   130: fload #6
    //   132: putfield D : F
    //   135: invokestatic r : ()I
    //   138: aload_0
    //   139: iload #7
    //   141: putfield i : I
    //   144: istore #15
    //   146: invokestatic D : ()[Lwtf/opal/d;
    //   149: ifnull -> 167
    //   152: iinc #15, 1
    //   155: iload #15
    //   157: invokestatic P : (I)V
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: return
    // Exception table:
    //   from	to	target	type
    //   146	160	163	wtf/opal/x5
  }
  
  public void Q(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Integer
    //   7: invokevirtual intValue : ()I
    //   10: istore_3
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/Integer
    //   17: invokevirtual intValue : ()I
    //   20: istore_2
    //   21: dup
    //   22: iconst_2
    //   23: aaload
    //   24: checkcast java/lang/Long
    //   27: invokevirtual longValue : ()J
    //   30: lstore #4
    //   32: pop
    //   33: getstatic wtf/opal/xp.b : J
    //   36: lload #4
    //   38: lxor
    //   39: lstore #4
    //   41: lload #4
    //   43: dup2
    //   44: ldc2_w 14549160866290
    //   47: lxor
    //   48: lstore #6
    //   50: dup2
    //   51: ldc2_w 96064387323479
    //   54: lxor
    //   55: lstore #8
    //   57: dup2
    //   58: ldc2_w 101580367183996
    //   61: lxor
    //   62: lstore #10
    //   64: pop2
    //   65: invokestatic z : ()I
    //   68: getstatic wtf/opal/xp.X : Lwtf/opal/dc;
    //   71: aload_0
    //   72: getfield R : Ljava/lang/String;
    //   75: lload #8
    //   77: iconst_2
    //   78: anewarray java/lang/Object
    //   81: dup_x2
    //   82: dup_x2
    //   83: pop
    //   84: invokestatic valueOf : (J)Ljava/lang/Long;
    //   87: iconst_1
    //   88: swap
    //   89: aastore
    //   90: dup_x1
    //   91: swap
    //   92: iconst_0
    //   93: swap
    //   94: aastore
    //   95: invokevirtual W : ([Ljava/lang/Object;)Lwtf/opal/u2;
    //   98: astore #13
    //   100: aload_0
    //   101: getfield q : F
    //   104: lload #6
    //   106: dup2_x1
    //   107: pop2
    //   108: aload_0
    //   109: getfield f : F
    //   112: aload_0
    //   113: getfield G : F
    //   116: aload_0
    //   117: getfield D : F
    //   120: iload_3
    //   121: i2d
    //   122: iload_2
    //   123: i2d
    //   124: bipush #7
    //   126: anewarray java/lang/Object
    //   129: dup_x2
    //   130: dup_x2
    //   131: pop
    //   132: invokestatic valueOf : (D)Ljava/lang/Double;
    //   135: bipush #6
    //   137: swap
    //   138: aastore
    //   139: dup_x2
    //   140: dup_x2
    //   141: pop
    //   142: invokestatic valueOf : (D)Ljava/lang/Double;
    //   145: iconst_5
    //   146: swap
    //   147: aastore
    //   148: dup_x1
    //   149: swap
    //   150: invokestatic valueOf : (F)Ljava/lang/Float;
    //   153: iconst_4
    //   154: swap
    //   155: aastore
    //   156: dup_x1
    //   157: swap
    //   158: invokestatic valueOf : (F)Ljava/lang/Float;
    //   161: iconst_3
    //   162: swap
    //   163: aastore
    //   164: dup_x1
    //   165: swap
    //   166: invokestatic valueOf : (F)Ljava/lang/Float;
    //   169: iconst_2
    //   170: swap
    //   171: aastore
    //   172: dup_x1
    //   173: swap
    //   174: invokestatic valueOf : (F)Ljava/lang/Float;
    //   177: iconst_1
    //   178: swap
    //   179: aastore
    //   180: dup_x2
    //   181: dup_x2
    //   182: pop
    //   183: invokestatic valueOf : (J)Ljava/lang/Long;
    //   186: iconst_0
    //   187: swap
    //   188: aastore
    //   189: invokestatic Z : ([Ljava/lang/Object;)Z
    //   192: istore #14
    //   194: istore #12
    //   196: aload_0
    //   197: getfield E : Lwtf/opal/dk;
    //   200: iload #14
    //   202: ifeq -> 215
    //   205: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   208: goto -> 218
    //   211: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   214: athrow
    //   215: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   218: lload #10
    //   220: dup2_x1
    //   221: pop2
    //   222: iconst_2
    //   223: anewarray java/lang/Object
    //   226: dup_x1
    //   227: swap
    //   228: iconst_1
    //   229: swap
    //   230: aastore
    //   231: dup_x2
    //   232: dup_x2
    //   233: pop
    //   234: invokestatic valueOf : (J)Ljava/lang/Long;
    //   237: iconst_0
    //   238: swap
    //   239: aastore
    //   240: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   243: pop
    //   244: getstatic wtf/opal/xp.z : Lwtf/opal/pa;
    //   247: aload_0
    //   248: aload #13
    //   250: <illegal opcode> run : (Lwtf/opal/xp;Lwtf/opal/u2;)Ljava/lang/Runnable;
    //   255: iconst_1
    //   256: anewarray java/lang/Object
    //   259: dup_x1
    //   260: swap
    //   261: iconst_0
    //   262: swap
    //   263: aastore
    //   264: invokevirtual T : ([Ljava/lang/Object;)V
    //   267: iload #12
    //   269: lload #4
    //   271: lconst_0
    //   272: lcmp
    //   273: ifle -> 280
    //   276: ifeq -> 293
    //   279: iconst_4
    //   280: anewarray wtf/opal/d
    //   283: invokestatic p : ([Lwtf/opal/d;)V
    //   286: goto -> 293
    //   289: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   292: athrow
    //   293: return
    // Exception table:
    //   from	to	target	type
    //   196	211	211	wtf/opal/x5
    //   218	286	289	wtf/opal/x5
  }
  
  public int Q(Object[] paramArrayOfObject) {
    return this.N;
  }
  
  public void y(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.N = i;
  }
  
  public void D(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.J = f;
  }
  
  public void F(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.d = i;
  }
  
  public void w(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    this.e = str;
  }
  
  public void i(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    this.R = str;
  }
  
  public String w(Object[] paramArrayOfObject) {
    return this.e;
  }
  
  public void V(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.i = i;
  }
  
  public int L(Object[] paramArrayOfObject) {
    return this.i;
  }
  
  public float K(Object[] paramArrayOfObject) {
    return this.q;
  }
  
  public void H(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.q = f;
  }
  
  public float N(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public void G(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.f = f;
  }
  
  public float H(Object[] paramArrayOfObject) {
    return this.G;
  }
  
  public void Z(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.G = f;
  }
  
  public float t(Object[] paramArrayOfObject) {
    return this.D;
  }
  
  public void T(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.D = f;
  }
  
  public float n(Object[] paramArrayOfObject) {
    return this.a;
  }
  
  public void e(Object[] paramArrayOfObject) {
    float f = ((Float)paramArrayOfObject[0]).floatValue();
    this.a = f;
  }
  
  public int R(Object[] paramArrayOfObject) {
    return this.m;
  }
  
  public void v(Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    this.m = i;
  }
  
  private void lambda$render$0(u2 paramu2) {
    // Byte code:
    //   0: getstatic wtf/opal/xp.b : J
    //   3: ldc2_w 38386525122205
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 86716111088339
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 131557726741326
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 105870886985777
    //   27: lxor
    //   28: lstore #8
    //   30: dup2
    //   31: ldc2_w 4651337873946
    //   34: lxor
    //   35: lstore #10
    //   37: dup2
    //   38: ldc2_w 68061879532649
    //   41: lxor
    //   42: lstore #12
    //   44: dup2
    //   45: ldc2_w 69869700637758
    //   48: lxor
    //   49: lstore #14
    //   51: pop2
    //   52: getstatic wtf/opal/xp.z : Lwtf/opal/pa;
    //   55: aload_0
    //   56: getfield q : F
    //   59: aload_0
    //   60: getfield f : F
    //   63: aload_0
    //   64: getfield G : F
    //   67: aload_0
    //   68: getfield D : F
    //   71: aload_0
    //   72: getfield a : F
    //   75: aload_0
    //   76: getfield N : I
    //   79: ldc 0.1
    //   81: iconst_2
    //   82: anewarray java/lang/Object
    //   85: dup_x1
    //   86: swap
    //   87: invokestatic valueOf : (F)Ljava/lang/Float;
    //   90: iconst_1
    //   91: swap
    //   92: aastore
    //   93: dup_x1
    //   94: swap
    //   95: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   98: iconst_0
    //   99: swap
    //   100: aastore
    //   101: invokestatic X : ([Ljava/lang/Object;)I
    //   104: aload_0
    //   105: getfield N : I
    //   108: ldc 0.17
    //   110: iconst_2
    //   111: anewarray java/lang/Object
    //   114: dup_x1
    //   115: swap
    //   116: invokestatic valueOf : (F)Ljava/lang/Float;
    //   119: iconst_1
    //   120: swap
    //   121: aastore
    //   122: dup_x1
    //   123: swap
    //   124: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   127: iconst_0
    //   128: swap
    //   129: aastore
    //   130: invokestatic X : ([Ljava/lang/Object;)I
    //   133: aload_0
    //   134: getfield E : Lwtf/opal/dk;
    //   137: lload #8
    //   139: iconst_1
    //   140: anewarray java/lang/Object
    //   143: dup_x2
    //   144: dup_x2
    //   145: pop
    //   146: invokestatic valueOf : (J)Ljava/lang/Long;
    //   149: iconst_0
    //   150: swap
    //   151: aastore
    //   152: invokevirtual P : ([Ljava/lang/Object;)Ljava/lang/Double;
    //   155: invokevirtual floatValue : ()F
    //   158: iconst_3
    //   159: anewarray java/lang/Object
    //   162: dup_x1
    //   163: swap
    //   164: invokestatic valueOf : (F)Ljava/lang/Float;
    //   167: iconst_2
    //   168: swap
    //   169: aastore
    //   170: dup_x1
    //   171: swap
    //   172: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   175: iconst_1
    //   176: swap
    //   177: aastore
    //   178: dup_x1
    //   179: swap
    //   180: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   183: iconst_0
    //   184: swap
    //   185: aastore
    //   186: invokestatic P : ([Ljava/lang/Object;)I
    //   189: lload #4
    //   191: bipush #7
    //   193: anewarray java/lang/Object
    //   196: dup_x2
    //   197: dup_x2
    //   198: pop
    //   199: invokestatic valueOf : (J)Ljava/lang/Long;
    //   202: bipush #6
    //   204: swap
    //   205: aastore
    //   206: dup_x1
    //   207: swap
    //   208: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   211: iconst_5
    //   212: swap
    //   213: aastore
    //   214: dup_x1
    //   215: swap
    //   216: invokestatic valueOf : (F)Ljava/lang/Float;
    //   219: iconst_4
    //   220: swap
    //   221: aastore
    //   222: dup_x1
    //   223: swap
    //   224: invokestatic valueOf : (F)Ljava/lang/Float;
    //   227: iconst_3
    //   228: swap
    //   229: aastore
    //   230: dup_x1
    //   231: swap
    //   232: invokestatic valueOf : (F)Ljava/lang/Float;
    //   235: iconst_2
    //   236: swap
    //   237: aastore
    //   238: dup_x1
    //   239: swap
    //   240: invokestatic valueOf : (F)Ljava/lang/Float;
    //   243: iconst_1
    //   244: swap
    //   245: aastore
    //   246: dup_x1
    //   247: swap
    //   248: invokestatic valueOf : (F)Ljava/lang/Float;
    //   251: iconst_0
    //   252: swap
    //   253: aastore
    //   254: invokevirtual M : ([Ljava/lang/Object;)V
    //   257: getstatic wtf/opal/xp.z : Lwtf/opal/pa;
    //   260: aload_0
    //   261: getfield q : F
    //   264: aload_0
    //   265: getfield f : F
    //   268: aload_0
    //   269: getfield G : F
    //   272: aload_0
    //   273: getfield D : F
    //   276: aload_0
    //   277: getfield a : F
    //   280: lload #14
    //   282: dup2_x1
    //   283: pop2
    //   284: aload_0
    //   285: getfield m : I
    //   288: i2f
    //   289: aload_0
    //   290: getfield N : I
    //   293: bipush #8
    //   295: anewarray java/lang/Object
    //   298: dup_x1
    //   299: swap
    //   300: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   303: bipush #7
    //   305: swap
    //   306: aastore
    //   307: dup_x1
    //   308: swap
    //   309: invokestatic valueOf : (F)Ljava/lang/Float;
    //   312: bipush #6
    //   314: swap
    //   315: aastore
    //   316: dup_x1
    //   317: swap
    //   318: invokestatic valueOf : (F)Ljava/lang/Float;
    //   321: iconst_5
    //   322: swap
    //   323: aastore
    //   324: dup_x2
    //   325: dup_x2
    //   326: pop
    //   327: invokestatic valueOf : (J)Ljava/lang/Long;
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
    //   365: invokevirtual G : ([Ljava/lang/Object;)V
    //   368: aload_0
    //   369: getfield e : Ljava/lang/String;
    //   372: invokevirtual isEmpty : ()Z
    //   375: ifne -> 596
    //   378: aload_1
    //   379: aload_0
    //   380: getfield e : Ljava/lang/String;
    //   383: lload #10
    //   385: dup2_x1
    //   386: pop2
    //   387: aload_0
    //   388: getfield J : F
    //   391: iconst_3
    //   392: anewarray java/lang/Object
    //   395: dup_x1
    //   396: swap
    //   397: invokestatic valueOf : (F)Ljava/lang/Float;
    //   400: iconst_2
    //   401: swap
    //   402: aastore
    //   403: dup_x1
    //   404: swap
    //   405: iconst_1
    //   406: swap
    //   407: aastore
    //   408: dup_x2
    //   409: dup_x2
    //   410: pop
    //   411: invokestatic valueOf : (J)Ljava/lang/Long;
    //   414: iconst_0
    //   415: swap
    //   416: aastore
    //   417: invokevirtual q : ([Ljava/lang/Object;)F
    //   420: fstore #16
    //   422: aload_1
    //   423: aload_0
    //   424: getfield e : Ljava/lang/String;
    //   427: aload_0
    //   428: getfield J : F
    //   431: lload #12
    //   433: sipush #15340
    //   436: ldc2_w 493740446650985656
    //   439: lload_2
    //   440: lxor
    //   441: <illegal opcode> d : (IJ)I
    //   446: iconst_4
    //   447: anewarray java/lang/Object
    //   450: dup_x1
    //   451: swap
    //   452: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   455: iconst_3
    //   456: swap
    //   457: aastore
    //   458: dup_x2
    //   459: dup_x2
    //   460: pop
    //   461: invokestatic valueOf : (J)Ljava/lang/Long;
    //   464: iconst_2
    //   465: swap
    //   466: aastore
    //   467: dup_x1
    //   468: swap
    //   469: invokestatic valueOf : (F)Ljava/lang/Float;
    //   472: iconst_1
    //   473: swap
    //   474: aastore
    //   475: dup_x1
    //   476: swap
    //   477: iconst_0
    //   478: swap
    //   479: aastore
    //   480: invokevirtual w : ([Ljava/lang/Object;)F
    //   483: fstore #17
    //   485: aload_1
    //   486: aload_0
    //   487: getfield e : Ljava/lang/String;
    //   490: aload_0
    //   491: getfield q : F
    //   494: aload_0
    //   495: getfield G : F
    //   498: fconst_2
    //   499: fdiv
    //   500: fadd
    //   501: fload #16
    //   503: fconst_2
    //   504: fdiv
    //   505: fsub
    //   506: aload_0
    //   507: getfield f : F
    //   510: aload_0
    //   511: getfield D : F
    //   514: fconst_2
    //   515: fdiv
    //   516: fadd
    //   517: fload #17
    //   519: fconst_2
    //   520: fdiv
    //   521: fsub
    //   522: aload_0
    //   523: getfield J : F
    //   526: aload_0
    //   527: getfield d : I
    //   530: iconst_1
    //   531: lload #6
    //   533: bipush #7
    //   535: anewarray java/lang/Object
    //   538: dup_x2
    //   539: dup_x2
    //   540: pop
    //   541: invokestatic valueOf : (J)Ljava/lang/Long;
    //   544: bipush #6
    //   546: swap
    //   547: aastore
    //   548: dup_x1
    //   549: swap
    //   550: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   553: iconst_5
    //   554: swap
    //   555: aastore
    //   556: dup_x1
    //   557: swap
    //   558: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   561: iconst_4
    //   562: swap
    //   563: aastore
    //   564: dup_x1
    //   565: swap
    //   566: invokestatic valueOf : (F)Ljava/lang/Float;
    //   569: iconst_3
    //   570: swap
    //   571: aastore
    //   572: dup_x1
    //   573: swap
    //   574: invokestatic valueOf : (F)Ljava/lang/Float;
    //   577: iconst_2
    //   578: swap
    //   579: aastore
    //   580: dup_x1
    //   581: swap
    //   582: invokestatic valueOf : (F)Ljava/lang/Float;
    //   585: iconst_1
    //   586: swap
    //   587: aastore
    //   588: dup_x1
    //   589: swap
    //   590: iconst_0
    //   591: swap
    //   592: aastore
    //   593: invokevirtual b : ([Ljava/lang/Object;)V
    //   596: return
  }
  
  static {
    // Byte code:
    //   0: ldc2_w 5092360465505653666
    //   3: ldc2_w 5869743314639487452
    //   6: invokestatic lookup : ()Ljava/lang/invoke/MethodHandles$Lookup;
    //   9: invokevirtual lookupClass : ()Ljava/lang/Class;
    //   12: invokestatic a : (JJLjava/lang/Object;)Lwtf/opal/t5;
    //   15: ldc2_w 183182753902098
    //   18: invokeinterface a : (J)J
    //   23: putstatic wtf/opal/xp.b : J
    //   26: iconst_0
    //   27: new java/util/HashMap
    //   30: dup
    //   31: bipush #13
    //   33: invokespecial <init> : (I)V
    //   36: putstatic wtf/opal/xp.h : Ljava/util/Map;
    //   39: invokestatic P : (I)V
    //   42: getstatic wtf/opal/xp.b : J
    //   45: ldc2_w 104494139189555
    //   48: lxor
    //   49: lstore_0
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
    //   131: iconst_2
    //   132: newarray long
    //   134: astore #8
    //   136: iconst_0
    //   137: istore #5
    //   139: ldc_w 'M¦Ð?õoµN\sò¨¤¹'
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
    //   296: putstatic wtf/opal/xp.c : [J
    //   299: iconst_2
    //   300: anewarray java/lang/Integer
    //   303: putstatic wtf/opal/xp.g : [Ljava/lang/Integer;
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
    //   511: iconst_0
    //   512: anewarray java/lang/Object
    //   515: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   518: iconst_0
    //   519: anewarray java/lang/Object
    //   522: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   525: putstatic wtf/opal/xp.z : Lwtf/opal/pa;
    //   528: iconst_0
    //   529: anewarray java/lang/Object
    //   532: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   535: iconst_0
    //   536: anewarray java/lang/Object
    //   539: invokevirtual D : ([Ljava/lang/Object;)Lwtf/opal/dc;
    //   542: putstatic wtf/opal/xp.X : Lwtf/opal/dc;
    //   545: return
  }
  
  public static void P(int paramInt) {
    u = paramInt;
  }
  
  public static int z() {
    return u;
  }
  
  public static int r() {
    int i = z();
    try {
      if (i == 0)
        return 39; 
    } catch (x5 x5) {
      throw a(null);
    } 
    return 0;
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
  }
  
  private static int a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5449;
    if (g[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = c[i];
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
        throw new RuntimeException("wtf/opal/xp", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      g[i] = Integer.valueOf(j);
    } 
    return g[i].intValue();
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
    //   65: ldc_w 'wtf/opal/xp'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\xp.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */