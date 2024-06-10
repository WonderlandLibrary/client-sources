package wtf.opal;

import com.google.common.util.concurrent.AtomicDouble;
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
import net.minecraft.class_332;

public final class jt extends d {
  private final kd l;
  
  private final ky<dv> k;
  
  public final ke f;
  
  private final kg r;
  
  private final kg J;
  
  private final pa G;
  
  private final bu b;
  
  private final gm<uj> a;
  
  private static String D;
  
  private static final long d = on.a(-637701065943800967L, 1566350319325440380L, MethodHandles.lookup().lookupClass()).a(165359687859459L);
  
  private static final String[] g;
  
  private static final String[] m;
  
  private static final Map n = new HashMap<>(13);
  
  private static final long[] o;
  
  private static final Integer[] p;
  
  private static final Map q;
  
  public jt(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jt.d : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 80694385105776
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 53560039433502
    //   17: lxor
    //   18: lstore #5
    //   20: dup2
    //   21: ldc2_w 33848526355531
    //   24: lxor
    //   25: lstore #7
    //   27: pop2
    //   28: aload_0
    //   29: sipush #24520
    //   32: ldc2_w 495299331434222953
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   42: lload #7
    //   44: sipush #22454
    //   47: ldc2_w 3339725269540019478
    //   50: lload_1
    //   51: lxor
    //   52: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   57: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   60: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   63: aload_0
    //   64: new wtf/opal/kd
    //   67: dup
    //   68: sipush #18579
    //   71: ldc2_w 4594298339406447163
    //   74: lload_1
    //   75: lxor
    //   76: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   81: iconst_5
    //   82: anewarray wtf/opal/ke
    //   85: dup
    //   86: iconst_0
    //   87: new wtf/opal/ke
    //   90: dup
    //   91: sipush #4499
    //   94: ldc2_w 5483419662425185068
    //   97: lload_1
    //   98: lxor
    //   99: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   104: iconst_1
    //   105: invokespecial <init> : (Ljava/lang/String;Z)V
    //   108: aastore
    //   109: dup
    //   110: iconst_1
    //   111: new wtf/opal/ke
    //   114: dup
    //   115: sipush #10447
    //   118: ldc2_w 7032341331747463798
    //   121: lload_1
    //   122: lxor
    //   123: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   128: iconst_1
    //   129: invokespecial <init> : (Ljava/lang/String;Z)V
    //   132: aastore
    //   133: dup
    //   134: iconst_2
    //   135: new wtf/opal/ke
    //   138: dup
    //   139: sipush #26322
    //   142: ldc2_w 5020020496084741237
    //   145: lload_1
    //   146: lxor
    //   147: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   152: iconst_0
    //   153: invokespecial <init> : (Ljava/lang/String;Z)V
    //   156: aastore
    //   157: dup
    //   158: iconst_3
    //   159: new wtf/opal/ke
    //   162: dup
    //   163: sipush #19112
    //   166: ldc2_w 5357738021643911184
    //   169: lload_1
    //   170: lxor
    //   171: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   176: iconst_1
    //   177: invokespecial <init> : (Ljava/lang/String;Z)V
    //   180: aastore
    //   181: dup
    //   182: iconst_4
    //   183: new wtf/opal/ke
    //   186: dup
    //   187: sipush #15042
    //   190: ldc2_w 9181127945558978684
    //   193: lload_1
    //   194: lxor
    //   195: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   200: iconst_1
    //   201: invokespecial <init> : (Ljava/lang/String;Z)V
    //   204: aastore
    //   205: invokespecial <init> : (Ljava/lang/String;[Lwtf/opal/ke;)V
    //   208: putfield l : Lwtf/opal/kd;
    //   211: aload_0
    //   212: new wtf/opal/ky
    //   215: dup
    //   216: sipush #17474
    //   219: ldc2_w 6405741755356122867
    //   222: lload_1
    //   223: lxor
    //   224: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   229: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   232: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   235: putfield k : Lwtf/opal/ky;
    //   238: aload_0
    //   239: new wtf/opal/ke
    //   242: dup
    //   243: sipush #23455
    //   246: ldc2_w 1139670263610904885
    //   249: lload_1
    //   250: lxor
    //   251: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   256: iconst_0
    //   257: invokespecial <init> : (Ljava/lang/String;Z)V
    //   260: putfield f : Lwtf/opal/ke;
    //   263: aload_0
    //   264: new wtf/opal/kg
    //   267: dup
    //   268: sipush #7221
    //   271: ldc2_w 5407357831620941467
    //   274: lload_1
    //   275: lxor
    //   276: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   281: sipush #3042
    //   284: ldc2_w 6346213204709847237
    //   287: lload_1
    //   288: lxor
    //   289: <illegal opcode> w : (IJ)I
    //   294: invokespecial <init> : (Ljava/lang/String;I)V
    //   297: putfield r : Lwtf/opal/kg;
    //   300: aload_0
    //   301: new wtf/opal/kg
    //   304: dup
    //   305: sipush #17048
    //   308: ldc2_w 7926626217559787556
    //   311: lload_1
    //   312: lxor
    //   313: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   318: sipush #3350
    //   321: ldc2_w 6531651194676459056
    //   324: lload_1
    //   325: lxor
    //   326: <illegal opcode> w : (IJ)I
    //   331: invokespecial <init> : (Ljava/lang/String;I)V
    //   334: putfield J : Lwtf/opal/kg;
    //   337: aload_0
    //   338: iconst_0
    //   339: anewarray java/lang/Object
    //   342: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   345: iconst_0
    //   346: anewarray java/lang/Object
    //   349: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   352: putfield G : Lwtf/opal/pa;
    //   355: aload_0
    //   356: iconst_0
    //   357: anewarray java/lang/Object
    //   360: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   363: iconst_0
    //   364: anewarray java/lang/Object
    //   367: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   370: putfield b : Lwtf/opal/bu;
    //   373: aload_0
    //   374: aload_0
    //   375: <illegal opcode> H : (Lwtf/opal/jt;)Lwtf/opal/gm;
    //   380: putfield a : Lwtf/opal/gm;
    //   383: aload_0
    //   384: iconst_5
    //   385: anewarray wtf/opal/k3
    //   388: dup
    //   389: iconst_0
    //   390: aload_0
    //   391: getfield l : Lwtf/opal/kd;
    //   394: aastore
    //   395: dup
    //   396: iconst_1
    //   397: aload_0
    //   398: getfield k : Lwtf/opal/ky;
    //   401: aastore
    //   402: dup
    //   403: iconst_2
    //   404: aload_0
    //   405: getfield f : Lwtf/opal/ke;
    //   408: aastore
    //   409: dup
    //   410: iconst_3
    //   411: aload_0
    //   412: getfield r : Lwtf/opal/kg;
    //   415: aastore
    //   416: dup
    //   417: iconst_4
    //   418: aload_0
    //   419: getfield J : Lwtf/opal/kg;
    //   422: aastore
    //   423: lload #5
    //   425: dup2_x1
    //   426: pop2
    //   427: iconst_2
    //   428: anewarray java/lang/Object
    //   431: dup_x1
    //   432: swap
    //   433: iconst_1
    //   434: swap
    //   435: aastore
    //   436: dup_x2
    //   437: dup_x2
    //   438: pop
    //   439: invokestatic valueOf : (J)Ljava/lang/Long;
    //   442: iconst_0
    //   443: swap
    //   444: aastore
    //   445: invokevirtual o : ([Ljava/lang/Object;)V
    //   448: aload_0
    //   449: iconst_1
    //   450: lload_3
    //   451: iconst_2
    //   452: anewarray java/lang/Object
    //   455: dup_x2
    //   456: dup_x2
    //   457: pop
    //   458: invokestatic valueOf : (J)Ljava/lang/Long;
    //   461: iconst_1
    //   462: swap
    //   463: aastore
    //   464: dup_x1
    //   465: swap
    //   466: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   469: iconst_0
    //   470: swap
    //   471: aastore
    //   472: invokevirtual Q : ([Ljava/lang/Object;)V
    //   475: return
  }
  
  public kg L(Object[] paramArrayOfObject) {
    return this.r;
  }
  
  public kg W(Object[] paramArrayOfObject) {
    return this.J;
  }
  
  public dv S(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jt.d : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic S : ()Ljava/lang/String;
    //   21: astore #4
    //   23: aload_0
    //   24: getfield b : Lwtf/opal/bu;
    //   27: getfield R : Z
    //   30: aload #4
    //   32: ifnonnull -> 72
    //   35: ifeq -> 53
    //   38: goto -> 45
    //   41: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   44: athrow
    //   45: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   48: areturn
    //   49: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   52: athrow
    //   53: aload_0
    //   54: getfield b : Lwtf/opal/bu;
    //   57: aload #4
    //   59: ifnonnull -> 128
    //   62: getfield t : Z
    //   65: goto -> 72
    //   68: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   71: athrow
    //   72: ifne -> 121
    //   75: aload_0
    //   76: getfield k : Lwtf/opal/ky;
    //   79: invokevirtual z : ()Ljava/lang/Object;
    //   82: checkcast wtf/opal/dv
    //   85: aload #4
    //   87: ifnonnull -> 131
    //   90: goto -> 97
    //   93: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: getstatic wtf/opal/dv.MINECRAFT : Lwtf/opal/dv;
    //   100: invokevirtual equals : (Ljava/lang/Object;)Z
    //   103: ifeq -> 121
    //   106: goto -> 113
    //   109: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   112: athrow
    //   113: getstatic wtf/opal/dv.GEIST_SANS : Lwtf/opal/dv;
    //   116: areturn
    //   117: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   120: athrow
    //   121: aload_0
    //   122: getfield k : Lwtf/opal/ky;
    //   125: invokevirtual z : ()Ljava/lang/Object;
    //   128: checkcast wtf/opal/dv
    //   131: areturn
    // Exception table:
    //   from	to	target	type
    //   23	38	41	wtf/opal/x5
    //   35	49	49	wtf/opal/x5
    //   53	65	68	wtf/opal/x5
    //   72	90	93	wtf/opal/x5
    //   75	106	109	wtf/opal/x5
    //   97	117	117	wtf/opal/x5
  }
  
  private void lambda$new$3(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/jt.d : J
    //   3: ldc2_w 131547956054597
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 59839692248052
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: astore #6
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: aload #6
    //   27: ifnonnull -> 53
    //   30: getfield field_1724 : Lnet/minecraft/class_746;
    //   33: ifnull -> 62
    //   36: goto -> 43
    //   39: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   42: athrow
    //   43: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   46: goto -> 53
    //   49: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   52: athrow
    //   53: getfield field_1755 : Lnet/minecraft/class_437;
    //   56: instanceof net/minecraft/class_408
    //   59: ifeq -> 67
    //   62: return
    //   63: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   66: athrow
    //   67: aload_1
    //   68: iconst_0
    //   69: anewarray java/lang/Object
    //   72: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   75: astore #7
    //   77: aload_0
    //   78: getfield f : Lwtf/opal/ke;
    //   81: invokevirtual z : ()Ljava/lang/Object;
    //   84: checkcast java/lang/Boolean
    //   87: invokevirtual booleanValue : ()Z
    //   90: ifeq -> 103
    //   93: getstatic wtf/opal/lx.BOLD : Lwtf/opal/lx;
    //   96: goto -> 106
    //   99: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   102: athrow
    //   103: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   106: astore #8
    //   108: aload_0
    //   109: getfield b : Lwtf/opal/bu;
    //   112: ldc_w 10.0
    //   115: lload #4
    //   117: iconst_2
    //   118: anewarray java/lang/Object
    //   121: dup_x2
    //   122: dup_x2
    //   123: pop
    //   124: invokestatic valueOf : (J)Ljava/lang/Long;
    //   127: iconst_1
    //   128: swap
    //   129: aastore
    //   130: dup_x1
    //   131: swap
    //   132: invokestatic valueOf : (F)Ljava/lang/Float;
    //   135: iconst_0
    //   136: swap
    //   137: aastore
    //   138: invokevirtual j : ([Ljava/lang/Object;)F
    //   141: fstore #9
    //   143: aload_0
    //   144: getfield G : Lwtf/opal/pa;
    //   147: aload_0
    //   148: fload #9
    //   150: aload #8
    //   152: aload #7
    //   154: <illegal opcode> run : (Lwtf/opal/jt;FLwtf/opal/lx;Lnet/minecraft/class_332;)Ljava/lang/Runnable;
    //   159: iconst_1
    //   160: anewarray java/lang/Object
    //   163: dup_x1
    //   164: swap
    //   165: iconst_0
    //   166: swap
    //   167: aastore
    //   168: invokevirtual T : ([Ljava/lang/Object;)V
    //   171: return
    // Exception table:
    //   from	to	target	type
    //   22	36	39	wtf/opal/x5
    //   30	46	49	wtf/opal/x5
    //   53	63	63	wtf/opal/x5
    //   77	99	99	wtf/opal/x5
  }
  
  private void lambda$new$2(float paramFloat, lx paramlx, class_332 paramclass_332) {
    // Byte code:
    //   0: getstatic wtf/opal/jt.d : J
    //   3: ldc2_w 41065625245860
    //   6: lxor
    //   7: lstore #4
    //   9: lload #4
    //   11: dup2
    //   12: ldc2_w 131844510923413
    //   15: lxor
    //   16: lstore #6
    //   18: dup2
    //   19: ldc2_w 30654940950625
    //   22: lxor
    //   23: lstore #8
    //   25: dup2
    //   26: ldc2_w 4418522808531
    //   29: lxor
    //   30: lstore #10
    //   32: dup2
    //   33: ldc2_w 25742308607813
    //   36: lxor
    //   37: lstore #12
    //   39: dup2
    //   40: ldc2_w 106039718427772
    //   43: lxor
    //   44: lstore #14
    //   46: dup2
    //   47: ldc2_w 83980968099814
    //   50: lxor
    //   51: lstore #16
    //   53: pop2
    //   54: invokestatic S : ()Ljava/lang/String;
    //   57: ldc_w 4.0
    //   60: fstore #19
    //   62: astore #18
    //   64: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   67: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   70: invokevirtual method_4502 : ()I
    //   73: i2f
    //   74: aload_0
    //   75: getfield k : Lwtf/opal/ky;
    //   78: invokevirtual z : ()Ljava/lang/Object;
    //   81: checkcast wtf/opal/dv
    //   84: iconst_0
    //   85: anewarray java/lang/Object
    //   88: invokevirtual b : ([Ljava/lang/Object;)F
    //   91: fconst_2
    //   92: fdiv
    //   93: fadd
    //   94: fload_1
    //   95: fsub
    //   96: ldc_w 4.0
    //   99: fsub
    //   100: fstore #20
    //   102: aload_0
    //   103: getfield l : Lwtf/opal/kd;
    //   106: sipush #11732
    //   109: ldc2_w 6286112372657785244
    //   112: lload #4
    //   114: lxor
    //   115: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   120: iconst_1
    //   121: anewarray java/lang/Object
    //   124: dup_x1
    //   125: swap
    //   126: iconst_0
    //   127: swap
    //   128: aastore
    //   129: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   132: invokevirtual z : ()Ljava/lang/Object;
    //   135: checkcast java/lang/Boolean
    //   138: invokevirtual booleanValue : ()Z
    //   141: aload #18
    //   143: ifnonnull -> 572
    //   146: ifeq -> 533
    //   149: goto -> 156
    //   152: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   155: athrow
    //   156: aload_0
    //   157: getfield b : Lwtf/opal/bu;
    //   160: aload_2
    //   161: aload_3
    //   162: sipush #12294
    //   165: ldc2_w 5504002732328817745
    //   168: lload #4
    //   170: lxor
    //   171: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   176: ldc_w 4.0
    //   179: fload #20
    //   181: ldc_w 10.0
    //   184: aload_0
    //   185: getfield r : Lwtf/opal/kg;
    //   188: invokevirtual z : ()Ljava/lang/Object;
    //   191: checkcast java/lang/Integer
    //   194: invokevirtual intValue : ()I
    //   197: aload_0
    //   198: getfield J : Lwtf/opal/kg;
    //   201: invokevirtual z : ()Ljava/lang/Object;
    //   204: checkcast java/lang/Integer
    //   207: invokevirtual intValue : ()I
    //   210: iconst_1
    //   211: lload #12
    //   213: iconst_0
    //   214: sipush #17804
    //   217: ldc2_w 4128935720835354715
    //   220: lload #4
    //   222: lxor
    //   223: <illegal opcode> w : (IJ)I
    //   228: bipush #12
    //   230: anewarray java/lang/Object
    //   233: dup_x1
    //   234: swap
    //   235: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   238: bipush #11
    //   240: swap
    //   241: aastore
    //   242: dup_x1
    //   243: swap
    //   244: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   247: bipush #10
    //   249: swap
    //   250: aastore
    //   251: dup_x2
    //   252: dup_x2
    //   253: pop
    //   254: invokestatic valueOf : (J)Ljava/lang/Long;
    //   257: bipush #9
    //   259: swap
    //   260: aastore
    //   261: dup_x1
    //   262: swap
    //   263: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   266: bipush #8
    //   268: swap
    //   269: aastore
    //   270: dup_x1
    //   271: swap
    //   272: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   275: bipush #7
    //   277: swap
    //   278: aastore
    //   279: dup_x1
    //   280: swap
    //   281: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   284: bipush #6
    //   286: swap
    //   287: aastore
    //   288: dup_x1
    //   289: swap
    //   290: invokestatic valueOf : (F)Ljava/lang/Float;
    //   293: iconst_5
    //   294: swap
    //   295: aastore
    //   296: dup_x1
    //   297: swap
    //   298: invokestatic valueOf : (F)Ljava/lang/Float;
    //   301: iconst_4
    //   302: swap
    //   303: aastore
    //   304: dup_x1
    //   305: swap
    //   306: invokestatic valueOf : (F)Ljava/lang/Float;
    //   309: iconst_3
    //   310: swap
    //   311: aastore
    //   312: dup_x1
    //   313: swap
    //   314: iconst_2
    //   315: swap
    //   316: aastore
    //   317: dup_x1
    //   318: swap
    //   319: iconst_1
    //   320: swap
    //   321: aastore
    //   322: dup_x1
    //   323: swap
    //   324: iconst_0
    //   325: swap
    //   326: aastore
    //   327: invokevirtual n : ([Ljava/lang/Object;)V
    //   330: aload_0
    //   331: getfield b : Lwtf/opal/bu;
    //   334: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   337: aload_3
    //   338: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   341: getfield field_1724 : Lnet/minecraft/class_746;
    //   344: invokevirtual method_23317 : ()D
    //   347: invokestatic round : (D)J
    //   350: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   353: getfield field_1724 : Lnet/minecraft/class_746;
    //   356: invokevirtual method_23318 : ()D
    //   359: invokestatic round : (D)J
    //   362: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   365: getfield field_1724 : Lnet/minecraft/class_746;
    //   368: invokevirtual method_23321 : ()D
    //   371: invokestatic round : (D)J
    //   374: <illegal opcode> makeConcatWithConstants : (JJJ)Ljava/lang/String;
    //   379: aload_0
    //   380: getfield b : Lwtf/opal/bu;
    //   383: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   386: sipush #19289
    //   389: ldc2_w 4660490763963297560
    //   392: lload #4
    //   394: lxor
    //   395: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   400: ldc_w 10.0
    //   403: lload #8
    //   405: iconst_4
    //   406: anewarray java/lang/Object
    //   409: dup_x2
    //   410: dup_x2
    //   411: pop
    //   412: invokestatic valueOf : (J)Ljava/lang/Long;
    //   415: iconst_3
    //   416: swap
    //   417: aastore
    //   418: dup_x1
    //   419: swap
    //   420: invokestatic valueOf : (F)Ljava/lang/Float;
    //   423: iconst_2
    //   424: swap
    //   425: aastore
    //   426: dup_x1
    //   427: swap
    //   428: iconst_1
    //   429: swap
    //   430: aastore
    //   431: dup_x1
    //   432: swap
    //   433: iconst_0
    //   434: swap
    //   435: aastore
    //   436: invokevirtual p : ([Ljava/lang/Object;)F
    //   439: ldc_w 5.0
    //   442: fadd
    //   443: lload #16
    //   445: fload #20
    //   447: ldc_w 10.0
    //   450: iconst_m1
    //   451: iconst_1
    //   452: bipush #9
    //   454: anewarray java/lang/Object
    //   457: dup_x1
    //   458: swap
    //   459: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   462: bipush #8
    //   464: swap
    //   465: aastore
    //   466: dup_x1
    //   467: swap
    //   468: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   471: bipush #7
    //   473: swap
    //   474: aastore
    //   475: dup_x1
    //   476: swap
    //   477: invokestatic valueOf : (F)Ljava/lang/Float;
    //   480: bipush #6
    //   482: swap
    //   483: aastore
    //   484: dup_x1
    //   485: swap
    //   486: invokestatic valueOf : (F)Ljava/lang/Float;
    //   489: iconst_5
    //   490: swap
    //   491: aastore
    //   492: dup_x2
    //   493: dup_x2
    //   494: pop
    //   495: invokestatic valueOf : (J)Ljava/lang/Long;
    //   498: iconst_4
    //   499: swap
    //   500: aastore
    //   501: dup_x1
    //   502: swap
    //   503: invokestatic valueOf : (F)Ljava/lang/Float;
    //   506: iconst_3
    //   507: swap
    //   508: aastore
    //   509: dup_x1
    //   510: swap
    //   511: iconst_2
    //   512: swap
    //   513: aastore
    //   514: dup_x1
    //   515: swap
    //   516: iconst_1
    //   517: swap
    //   518: aastore
    //   519: dup_x1
    //   520: swap
    //   521: iconst_0
    //   522: swap
    //   523: aastore
    //   524: invokevirtual H : ([Ljava/lang/Object;)V
    //   527: fload #20
    //   529: fload_1
    //   530: fsub
    //   531: fstore #20
    //   533: aload_0
    //   534: getfield l : Lwtf/opal/kd;
    //   537: sipush #17578
    //   540: ldc2_w 1778602576413548790
    //   543: lload #4
    //   545: lxor
    //   546: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   551: iconst_1
    //   552: anewarray java/lang/Object
    //   555: dup_x1
    //   556: swap
    //   557: iconst_0
    //   558: swap
    //   559: aastore
    //   560: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   563: invokevirtual z : ()Ljava/lang/Object;
    //   566: checkcast java/lang/Boolean
    //   569: invokevirtual booleanValue : ()Z
    //   572: aload #18
    //   574: ifnonnull -> 972
    //   577: ifeq -> 933
    //   580: goto -> 587
    //   583: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   586: athrow
    //   587: aload_0
    //   588: getfield b : Lwtf/opal/bu;
    //   591: aload_2
    //   592: aload_3
    //   593: sipush #15138
    //   596: ldc2_w 3462011580396248932
    //   599: lload #4
    //   601: lxor
    //   602: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   607: ldc_w 4.0
    //   610: fload #20
    //   612: ldc_w 10.0
    //   615: aload_0
    //   616: getfield r : Lwtf/opal/kg;
    //   619: invokevirtual z : ()Ljava/lang/Object;
    //   622: checkcast java/lang/Integer
    //   625: invokevirtual intValue : ()I
    //   628: aload_0
    //   629: getfield J : Lwtf/opal/kg;
    //   632: invokevirtual z : ()Ljava/lang/Object;
    //   635: checkcast java/lang/Integer
    //   638: invokevirtual intValue : ()I
    //   641: iconst_1
    //   642: lload #12
    //   644: iconst_0
    //   645: sipush #17087
    //   648: ldc2_w 4295021547176859497
    //   651: lload #4
    //   653: lxor
    //   654: <illegal opcode> w : (IJ)I
    //   659: bipush #12
    //   661: anewarray java/lang/Object
    //   664: dup_x1
    //   665: swap
    //   666: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   669: bipush #11
    //   671: swap
    //   672: aastore
    //   673: dup_x1
    //   674: swap
    //   675: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   678: bipush #10
    //   680: swap
    //   681: aastore
    //   682: dup_x2
    //   683: dup_x2
    //   684: pop
    //   685: invokestatic valueOf : (J)Ljava/lang/Long;
    //   688: bipush #9
    //   690: swap
    //   691: aastore
    //   692: dup_x1
    //   693: swap
    //   694: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   697: bipush #8
    //   699: swap
    //   700: aastore
    //   701: dup_x1
    //   702: swap
    //   703: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   706: bipush #7
    //   708: swap
    //   709: aastore
    //   710: dup_x1
    //   711: swap
    //   712: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   715: bipush #6
    //   717: swap
    //   718: aastore
    //   719: dup_x1
    //   720: swap
    //   721: invokestatic valueOf : (F)Ljava/lang/Float;
    //   724: iconst_5
    //   725: swap
    //   726: aastore
    //   727: dup_x1
    //   728: swap
    //   729: invokestatic valueOf : (F)Ljava/lang/Float;
    //   732: iconst_4
    //   733: swap
    //   734: aastore
    //   735: dup_x1
    //   736: swap
    //   737: invokestatic valueOf : (F)Ljava/lang/Float;
    //   740: iconst_3
    //   741: swap
    //   742: aastore
    //   743: dup_x1
    //   744: swap
    //   745: iconst_2
    //   746: swap
    //   747: aastore
    //   748: dup_x1
    //   749: swap
    //   750: iconst_1
    //   751: swap
    //   752: aastore
    //   753: dup_x1
    //   754: swap
    //   755: iconst_0
    //   756: swap
    //   757: aastore
    //   758: invokevirtual n : ([Ljava/lang/Object;)V
    //   761: aload_0
    //   762: getfield b : Lwtf/opal/bu;
    //   765: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   768: aload_3
    //   769: iconst_0
    //   770: anewarray java/lang/Object
    //   773: invokestatic H : ([Ljava/lang/Object;)D
    //   776: invokestatic valueOf : (D)Ljava/lang/String;
    //   779: aload_0
    //   780: getfield b : Lwtf/opal/bu;
    //   783: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   786: sipush #24188
    //   789: ldc2_w 2288723746098477628
    //   792: lload #4
    //   794: lxor
    //   795: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   800: ldc_w 10.0
    //   803: lload #8
    //   805: iconst_4
    //   806: anewarray java/lang/Object
    //   809: dup_x2
    //   810: dup_x2
    //   811: pop
    //   812: invokestatic valueOf : (J)Ljava/lang/Long;
    //   815: iconst_3
    //   816: swap
    //   817: aastore
    //   818: dup_x1
    //   819: swap
    //   820: invokestatic valueOf : (F)Ljava/lang/Float;
    //   823: iconst_2
    //   824: swap
    //   825: aastore
    //   826: dup_x1
    //   827: swap
    //   828: iconst_1
    //   829: swap
    //   830: aastore
    //   831: dup_x1
    //   832: swap
    //   833: iconst_0
    //   834: swap
    //   835: aastore
    //   836: invokevirtual p : ([Ljava/lang/Object;)F
    //   839: ldc_w 5.0
    //   842: fadd
    //   843: lload #16
    //   845: fload #20
    //   847: ldc_w 10.0
    //   850: iconst_m1
    //   851: iconst_1
    //   852: bipush #9
    //   854: anewarray java/lang/Object
    //   857: dup_x1
    //   858: swap
    //   859: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   862: bipush #8
    //   864: swap
    //   865: aastore
    //   866: dup_x1
    //   867: swap
    //   868: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   871: bipush #7
    //   873: swap
    //   874: aastore
    //   875: dup_x1
    //   876: swap
    //   877: invokestatic valueOf : (F)Ljava/lang/Float;
    //   880: bipush #6
    //   882: swap
    //   883: aastore
    //   884: dup_x1
    //   885: swap
    //   886: invokestatic valueOf : (F)Ljava/lang/Float;
    //   889: iconst_5
    //   890: swap
    //   891: aastore
    //   892: dup_x2
    //   893: dup_x2
    //   894: pop
    //   895: invokestatic valueOf : (J)Ljava/lang/Long;
    //   898: iconst_4
    //   899: swap
    //   900: aastore
    //   901: dup_x1
    //   902: swap
    //   903: invokestatic valueOf : (F)Ljava/lang/Float;
    //   906: iconst_3
    //   907: swap
    //   908: aastore
    //   909: dup_x1
    //   910: swap
    //   911: iconst_2
    //   912: swap
    //   913: aastore
    //   914: dup_x1
    //   915: swap
    //   916: iconst_1
    //   917: swap
    //   918: aastore
    //   919: dup_x1
    //   920: swap
    //   921: iconst_0
    //   922: swap
    //   923: aastore
    //   924: invokevirtual H : ([Ljava/lang/Object;)V
    //   927: fload #20
    //   929: fload_1
    //   930: fsub
    //   931: fstore #20
    //   933: aload_0
    //   934: getfield l : Lwtf/opal/kd;
    //   937: sipush #16826
    //   940: ldc2_w 6223432690823685620
    //   943: lload #4
    //   945: lxor
    //   946: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   951: iconst_1
    //   952: anewarray java/lang/Object
    //   955: dup_x1
    //   956: swap
    //   957: iconst_0
    //   958: swap
    //   959: aastore
    //   960: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   963: invokevirtual z : ()Ljava/lang/Object;
    //   966: checkcast java/lang/Boolean
    //   969: invokevirtual booleanValue : ()Z
    //   972: aload #18
    //   974: ifnonnull -> 1344
    //   977: ifeq -> 1333
    //   980: goto -> 987
    //   983: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   986: athrow
    //   987: aload_0
    //   988: getfield b : Lwtf/opal/bu;
    //   991: aload_2
    //   992: aload_3
    //   993: sipush #24684
    //   996: ldc2_w 4267589804633408569
    //   999: lload #4
    //   1001: lxor
    //   1002: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   1007: ldc_w 4.0
    //   1010: fload #20
    //   1012: ldc_w 10.0
    //   1015: aload_0
    //   1016: getfield r : Lwtf/opal/kg;
    //   1019: invokevirtual z : ()Ljava/lang/Object;
    //   1022: checkcast java/lang/Integer
    //   1025: invokevirtual intValue : ()I
    //   1028: aload_0
    //   1029: getfield J : Lwtf/opal/kg;
    //   1032: invokevirtual z : ()Ljava/lang/Object;
    //   1035: checkcast java/lang/Integer
    //   1038: invokevirtual intValue : ()I
    //   1041: iconst_1
    //   1042: lload #12
    //   1044: iconst_0
    //   1045: sipush #17087
    //   1048: ldc2_w 4295021547176859497
    //   1051: lload #4
    //   1053: lxor
    //   1054: <illegal opcode> w : (IJ)I
    //   1059: bipush #12
    //   1061: anewarray java/lang/Object
    //   1064: dup_x1
    //   1065: swap
    //   1066: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1069: bipush #11
    //   1071: swap
    //   1072: aastore
    //   1073: dup_x1
    //   1074: swap
    //   1075: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1078: bipush #10
    //   1080: swap
    //   1081: aastore
    //   1082: dup_x2
    //   1083: dup_x2
    //   1084: pop
    //   1085: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1088: bipush #9
    //   1090: swap
    //   1091: aastore
    //   1092: dup_x1
    //   1093: swap
    //   1094: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1097: bipush #8
    //   1099: swap
    //   1100: aastore
    //   1101: dup_x1
    //   1102: swap
    //   1103: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1106: bipush #7
    //   1108: swap
    //   1109: aastore
    //   1110: dup_x1
    //   1111: swap
    //   1112: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1115: bipush #6
    //   1117: swap
    //   1118: aastore
    //   1119: dup_x1
    //   1120: swap
    //   1121: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1124: iconst_5
    //   1125: swap
    //   1126: aastore
    //   1127: dup_x1
    //   1128: swap
    //   1129: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1132: iconst_4
    //   1133: swap
    //   1134: aastore
    //   1135: dup_x1
    //   1136: swap
    //   1137: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1140: iconst_3
    //   1141: swap
    //   1142: aastore
    //   1143: dup_x1
    //   1144: swap
    //   1145: iconst_2
    //   1146: swap
    //   1147: aastore
    //   1148: dup_x1
    //   1149: swap
    //   1150: iconst_1
    //   1151: swap
    //   1152: aastore
    //   1153: dup_x1
    //   1154: swap
    //   1155: iconst_0
    //   1156: swap
    //   1157: aastore
    //   1158: invokevirtual n : ([Ljava/lang/Object;)V
    //   1161: aload_0
    //   1162: getfield b : Lwtf/opal/bu;
    //   1165: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1168: aload_3
    //   1169: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1172: invokevirtual method_47599 : ()I
    //   1175: invokestatic valueOf : (I)Ljava/lang/String;
    //   1178: aload_0
    //   1179: getfield b : Lwtf/opal/bu;
    //   1182: getstatic wtf/opal/lx.SEMI_BOLD : Lwtf/opal/lx;
    //   1185: sipush #22273
    //   1188: ldc2_w 8445680090721088345
    //   1191: lload #4
    //   1193: lxor
    //   1194: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   1199: ldc_w 10.0
    //   1202: lload #8
    //   1204: iconst_4
    //   1205: anewarray java/lang/Object
    //   1208: dup_x2
    //   1209: dup_x2
    //   1210: pop
    //   1211: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1214: iconst_3
    //   1215: swap
    //   1216: aastore
    //   1217: dup_x1
    //   1218: swap
    //   1219: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1222: iconst_2
    //   1223: swap
    //   1224: aastore
    //   1225: dup_x1
    //   1226: swap
    //   1227: iconst_1
    //   1228: swap
    //   1229: aastore
    //   1230: dup_x1
    //   1231: swap
    //   1232: iconst_0
    //   1233: swap
    //   1234: aastore
    //   1235: invokevirtual p : ([Ljava/lang/Object;)F
    //   1238: ldc_w 5.0
    //   1241: fadd
    //   1242: lload #16
    //   1244: fload #20
    //   1246: ldc_w 10.0
    //   1249: iconst_m1
    //   1250: iconst_1
    //   1251: bipush #9
    //   1253: anewarray java/lang/Object
    //   1256: dup_x1
    //   1257: swap
    //   1258: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1261: bipush #8
    //   1263: swap
    //   1264: aastore
    //   1265: dup_x1
    //   1266: swap
    //   1267: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1270: bipush #7
    //   1272: swap
    //   1273: aastore
    //   1274: dup_x1
    //   1275: swap
    //   1276: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1279: bipush #6
    //   1281: swap
    //   1282: aastore
    //   1283: dup_x1
    //   1284: swap
    //   1285: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1288: iconst_5
    //   1289: swap
    //   1290: aastore
    //   1291: dup_x2
    //   1292: dup_x2
    //   1293: pop
    //   1294: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1297: iconst_4
    //   1298: swap
    //   1299: aastore
    //   1300: dup_x1
    //   1301: swap
    //   1302: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1305: iconst_3
    //   1306: swap
    //   1307: aastore
    //   1308: dup_x1
    //   1309: swap
    //   1310: iconst_2
    //   1311: swap
    //   1312: aastore
    //   1313: dup_x1
    //   1314: swap
    //   1315: iconst_1
    //   1316: swap
    //   1317: aastore
    //   1318: dup_x1
    //   1319: swap
    //   1320: iconst_0
    //   1321: swap
    //   1322: aastore
    //   1323: invokevirtual H : ([Ljava/lang/Object;)V
    //   1326: goto -> 1333
    //   1329: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1332: athrow
    //   1333: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1336: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   1339: invokevirtual method_4486 : ()I
    //   1342: iconst_4
    //   1343: isub
    //   1344: i2f
    //   1345: fstore #21
    //   1347: new com/google/common/util/concurrent/AtomicDouble
    //   1350: dup
    //   1351: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1354: invokevirtual method_22683 : ()Lnet/minecraft/class_1041;
    //   1357: invokevirtual method_4502 : ()I
    //   1360: i2f
    //   1361: aload_0
    //   1362: getfield k : Lwtf/opal/ky;
    //   1365: invokevirtual z : ()Ljava/lang/Object;
    //   1368: checkcast wtf/opal/dv
    //   1371: iconst_0
    //   1372: anewarray java/lang/Object
    //   1375: invokevirtual b : ([Ljava/lang/Object;)F
    //   1378: fconst_2
    //   1379: fdiv
    //   1380: fadd
    //   1381: fload_1
    //   1382: fsub
    //   1383: ldc_w 4.0
    //   1386: fsub
    //   1387: f2d
    //   1388: invokespecial <init> : (D)V
    //   1391: astore #22
    //   1393: aload_0
    //   1394: getfield l : Lwtf/opal/kd;
    //   1397: sipush #32289
    //   1400: ldc2_w 8125718295197014654
    //   1403: lload #4
    //   1405: lxor
    //   1406: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   1411: iconst_1
    //   1412: anewarray java/lang/Object
    //   1415: dup_x1
    //   1416: swap
    //   1417: iconst_0
    //   1418: swap
    //   1419: aastore
    //   1420: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   1423: invokevirtual z : ()Ljava/lang/Object;
    //   1426: checkcast java/lang/Boolean
    //   1429: invokevirtual booleanValue : ()Z
    //   1432: aload #18
    //   1434: ifnonnull -> 1877
    //   1437: ifeq -> 1838
    //   1440: goto -> 1447
    //   1443: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1446: athrow
    //   1447: iconst_0
    //   1448: anewarray java/lang/Object
    //   1451: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1454: iconst_0
    //   1455: anewarray java/lang/Object
    //   1458: invokevirtual C : ([Ljava/lang/Object;)Lwtf/opal/lf;
    //   1461: iconst_0
    //   1462: anewarray java/lang/Object
    //   1465: invokevirtual c : ([Ljava/lang/Object;)Lwtf/opal/po;
    //   1468: iconst_0
    //   1469: anewarray java/lang/Object
    //   1472: invokevirtual l : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1475: astore #23
    //   1477: sipush #7816
    //   1480: aload #23
    //   1482: getstatic wtf/opal/p7.Y : Lwtf/opal/xf;
    //   1485: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
    //   1488: sipush #22692
    //   1491: ldc2_w 2180973913985678567
    //   1494: lload #4
    //   1496: lxor
    //   1497: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   1502: swap
    //   1503: sipush #11388
    //   1506: ldc2_w 3746843614250960941
    //   1509: lload #4
    //   1511: lxor
    //   1512: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   1517: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1522: astore #24
    //   1524: ldc2_w 3170026128763997016
    //   1527: lload #4
    //   1529: lxor
    //   1530: aload_0
    //   1531: getfield b : Lwtf/opal/bu;
    //   1534: aload_2
    //   1535: aload #24
    //   1537: ldc_w 10.0
    //   1540: lload #8
    //   1542: iconst_4
    //   1543: anewarray java/lang/Object
    //   1546: dup_x2
    //   1547: dup_x2
    //   1548: pop
    //   1549: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1552: iconst_3
    //   1553: swap
    //   1554: aastore
    //   1555: dup_x1
    //   1556: swap
    //   1557: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1560: iconst_2
    //   1561: swap
    //   1562: aastore
    //   1563: dup_x1
    //   1564: swap
    //   1565: iconst_1
    //   1566: swap
    //   1567: aastore
    //   1568: dup_x1
    //   1569: swap
    //   1570: iconst_0
    //   1571: swap
    //   1572: aastore
    //   1573: invokevirtual p : ([Ljava/lang/Object;)F
    //   1576: fstore #25
    //   1578: <illegal opcode> w : (IJ)I
    //   1583: iconst_0
    //   1584: aload_0
    //   1585: getfield r : Lwtf/opal/kg;
    //   1588: invokevirtual z : ()Ljava/lang/Object;
    //   1591: checkcast java/lang/Integer
    //   1594: invokevirtual intValue : ()I
    //   1597: lload #6
    //   1599: dup2_x1
    //   1600: pop2
    //   1601: aload_0
    //   1602: getfield J : Lwtf/opal/kg;
    //   1605: invokevirtual z : ()Ljava/lang/Object;
    //   1608: checkcast java/lang/Integer
    //   1611: invokevirtual intValue : ()I
    //   1614: lload #14
    //   1616: iconst_2
    //   1617: anewarray java/lang/Object
    //   1620: dup_x2
    //   1621: dup_x2
    //   1622: pop
    //   1623: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1626: iconst_1
    //   1627: swap
    //   1628: aastore
    //   1629: dup_x1
    //   1630: swap
    //   1631: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1634: iconst_0
    //   1635: swap
    //   1636: aastore
    //   1637: invokestatic S : ([Ljava/lang/Object;)I
    //   1640: iconst_5
    //   1641: anewarray java/lang/Object
    //   1644: dup_x1
    //   1645: swap
    //   1646: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1649: iconst_4
    //   1650: swap
    //   1651: aastore
    //   1652: dup_x1
    //   1653: swap
    //   1654: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1657: iconst_3
    //   1658: swap
    //   1659: aastore
    //   1660: dup_x2
    //   1661: dup_x2
    //   1662: pop
    //   1663: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1666: iconst_2
    //   1667: swap
    //   1668: aastore
    //   1669: dup_x1
    //   1670: swap
    //   1671: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1674: iconst_1
    //   1675: swap
    //   1676: aastore
    //   1677: dup_x1
    //   1678: swap
    //   1679: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1682: iconst_0
    //   1683: swap
    //   1684: aastore
    //   1685: invokestatic K : ([Ljava/lang/Object;)I
    //   1688: istore #26
    //   1690: aload #22
    //   1692: fload_1
    //   1693: ldc_w 0.5
    //   1696: fadd
    //   1697: fneg
    //   1698: f2d
    //   1699: invokevirtual getAndAdd : (D)D
    //   1702: d2f
    //   1703: fstore #27
    //   1705: aload_0
    //   1706: getfield b : Lwtf/opal/bu;
    //   1709: aload_2
    //   1710: aload_3
    //   1711: aload #24
    //   1713: fload #21
    //   1715: fload #25
    //   1717: fsub
    //   1718: fconst_1
    //   1719: fadd
    //   1720: fload #27
    //   1722: ldc_w 10.0
    //   1725: iload #26
    //   1727: iconst_1
    //   1728: iconst_0
    //   1729: lload #10
    //   1731: sipush #17087
    //   1734: ldc2_w 4295021547176859497
    //   1737: lload #4
    //   1739: lxor
    //   1740: <illegal opcode> w : (IJ)I
    //   1745: bipush #11
    //   1747: anewarray java/lang/Object
    //   1750: dup_x1
    //   1751: swap
    //   1752: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1755: bipush #10
    //   1757: swap
    //   1758: aastore
    //   1759: dup_x2
    //   1760: dup_x2
    //   1761: pop
    //   1762: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1765: bipush #9
    //   1767: swap
    //   1768: aastore
    //   1769: dup_x1
    //   1770: swap
    //   1771: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1774: bipush #8
    //   1776: swap
    //   1777: aastore
    //   1778: dup_x1
    //   1779: swap
    //   1780: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1783: bipush #7
    //   1785: swap
    //   1786: aastore
    //   1787: dup_x1
    //   1788: swap
    //   1789: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1792: bipush #6
    //   1794: swap
    //   1795: aastore
    //   1796: dup_x1
    //   1797: swap
    //   1798: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1801: iconst_5
    //   1802: swap
    //   1803: aastore
    //   1804: dup_x1
    //   1805: swap
    //   1806: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1809: iconst_4
    //   1810: swap
    //   1811: aastore
    //   1812: dup_x1
    //   1813: swap
    //   1814: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1817: iconst_3
    //   1818: swap
    //   1819: aastore
    //   1820: dup_x1
    //   1821: swap
    //   1822: iconst_2
    //   1823: swap
    //   1824: aastore
    //   1825: dup_x1
    //   1826: swap
    //   1827: iconst_1
    //   1828: swap
    //   1829: aastore
    //   1830: dup_x1
    //   1831: swap
    //   1832: iconst_0
    //   1833: swap
    //   1834: aastore
    //   1835: invokevirtual E : ([Ljava/lang/Object;)V
    //   1838: aload_0
    //   1839: getfield l : Lwtf/opal/kd;
    //   1842: sipush #1167
    //   1845: ldc2_w 4828493293696229593
    //   1848: lload #4
    //   1850: lxor
    //   1851: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   1856: iconst_1
    //   1857: anewarray java/lang/Object
    //   1860: dup_x1
    //   1861: swap
    //   1862: iconst_0
    //   1863: swap
    //   1864: aastore
    //   1865: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/ke;
    //   1868: invokevirtual z : ()Ljava/lang/Object;
    //   1871: checkcast java/lang/Boolean
    //   1874: invokevirtual booleanValue : ()Z
    //   1877: ifeq -> 1934
    //   1880: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1883: getfield field_1724 : Lnet/minecraft/class_746;
    //   1886: invokevirtual method_6088 : ()Ljava/util/Map;
    //   1889: invokeinterface entrySet : ()Ljava/util/Set;
    //   1894: invokeinterface stream : ()Ljava/util/stream/Stream;
    //   1899: aload_0
    //   1900: <illegal opcode> compare : (Lwtf/opal/jt;)Ljava/util/Comparator;
    //   1905: invokeinterface sorted : (Ljava/util/Comparator;)Ljava/util/stream/Stream;
    //   1910: aload_0
    //   1911: aload #22
    //   1913: fload_1
    //   1914: aload_3
    //   1915: fload #21
    //   1917: <illegal opcode> accept : (Lwtf/opal/jt;Lcom/google/common/util/concurrent/AtomicDouble;FLnet/minecraft/class_332;F)Ljava/util/function/Consumer;
    //   1922: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   1927: goto -> 1934
    //   1930: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1933: athrow
    //   1934: return
    // Exception table:
    //   from	to	target	type
    //   102	149	152	wtf/opal/x5
    //   572	580	583	wtf/opal/x5
    //   972	980	983	wtf/opal/x5
    //   977	1326	1329	wtf/opal/x5
    //   1393	1440	1443	wtf/opal/x5
    //   1877	1927	1930	wtf/opal/x5
  }
  
  private void lambda$new$1(AtomicDouble paramAtomicDouble, float paramFloat1, class_332 paramclass_332, float paramFloat2, Map.Entry paramEntry) {
    // Byte code:
    //   0: getstatic wtf/opal/jt.d : J
    //   3: ldc2_w 41082381751539
    //   6: lxor
    //   7: lstore #6
    //   9: lload #6
    //   11: dup2
    //   12: ldc2_w 30670396701750
    //   15: lxor
    //   16: lstore #8
    //   18: dup2
    //   19: ldc2_w 4400978300036
    //   22: lxor
    //   23: lstore #10
    //   25: pop2
    //   26: invokestatic S : ()Ljava/lang/String;
    //   29: aload #5
    //   31: invokeinterface getKey : ()Ljava/lang/Object;
    //   36: checkcast net/minecraft/class_6880
    //   39: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   44: checkcast net/minecraft/class_1291
    //   47: astore #13
    //   49: aload #5
    //   51: invokeinterface getValue : ()Ljava/lang/Object;
    //   56: checkcast net/minecraft/class_1293
    //   59: astore #14
    //   61: astore #12
    //   63: aload #14
    //   65: invokevirtual method_5586 : ()Ljava/lang/String;
    //   68: iconst_0
    //   69: anewarray java/lang/Object
    //   72: invokestatic method_4662 : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   75: astore #15
    //   77: aload #14
    //   79: invokevirtual method_48559 : ()Z
    //   82: aload #12
    //   84: ifnonnull -> 123
    //   87: ifeq -> 118
    //   90: goto -> 97
    //   93: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: sipush #31139
    //   100: ldc2_w 1993352881906962862
    //   103: lload #6
    //   105: lxor
    //   106: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   111: goto -> 129
    //   114: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   117: athrow
    //   118: aload #14
    //   120: invokevirtual method_5584 : ()I
    //   123: ldc_w 20.0
    //   126: invokestatic method_15439 : (IF)Ljava/lang/String;
    //   129: astore #16
    //   131: aload #15
    //   133: aload #14
    //   135: invokevirtual method_5578 : ()I
    //   138: aload #12
    //   140: ifnonnull -> 167
    //   143: ifle -> 175
    //   146: goto -> 153
    //   149: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   152: athrow
    //   153: aload #14
    //   155: invokevirtual method_5578 : ()I
    //   158: iconst_1
    //   159: iadd
    //   160: goto -> 167
    //   163: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   166: athrow
    //   167: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
    //   172: goto -> 178
    //   175: ldc_w ''
    //   178: aload #16
    //   180: sipush #11582
    //   183: ldc2_w 3843301355726243104
    //   186: lload #6
    //   188: lxor
    //   189: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   194: swap
    //   195: ldc_w ')'
    //   198: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   203: astore #17
    //   205: aload_0
    //   206: getfield b : Lwtf/opal/bu;
    //   209: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   212: aload #17
    //   214: ldc_w 10.0
    //   217: lload #8
    //   219: iconst_4
    //   220: anewarray java/lang/Object
    //   223: dup_x2
    //   224: dup_x2
    //   225: pop
    //   226: invokestatic valueOf : (J)Ljava/lang/Long;
    //   229: iconst_3
    //   230: swap
    //   231: aastore
    //   232: dup_x1
    //   233: swap
    //   234: invokestatic valueOf : (F)Ljava/lang/Float;
    //   237: iconst_2
    //   238: swap
    //   239: aastore
    //   240: dup_x1
    //   241: swap
    //   242: iconst_1
    //   243: swap
    //   244: aastore
    //   245: dup_x1
    //   246: swap
    //   247: iconst_0
    //   248: swap
    //   249: aastore
    //   250: invokevirtual p : ([Ljava/lang/Object;)F
    //   253: fstore #18
    //   255: aload #13
    //   257: invokevirtual method_5556 : ()I
    //   260: ldc_w 255.0
    //   263: iconst_2
    //   264: anewarray java/lang/Object
    //   267: dup_x1
    //   268: swap
    //   269: invokestatic valueOf : (F)Ljava/lang/Float;
    //   272: iconst_1
    //   273: swap
    //   274: aastore
    //   275: dup_x1
    //   276: swap
    //   277: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   280: iconst_0
    //   281: swap
    //   282: aastore
    //   283: invokestatic X : ([Ljava/lang/Object;)I
    //   286: istore #19
    //   288: aload_1
    //   289: fload_2
    //   290: ldc_w 0.5
    //   293: fadd
    //   294: fneg
    //   295: f2d
    //   296: invokevirtual getAndAdd : (D)D
    //   299: d2f
    //   300: fstore #20
    //   302: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   305: invokevirtual method_18505 : ()Lnet/minecraft/class_4074;
    //   308: aload #5
    //   310: invokeinterface getKey : ()Ljava/lang/Object;
    //   315: checkcast net/minecraft/class_6880
    //   318: invokevirtual method_18663 : (Lnet/minecraft/class_6880;)Lnet/minecraft/class_1058;
    //   321: astore #21
    //   323: invokestatic enableBlend : ()V
    //   326: aload_3
    //   327: fload #4
    //   329: fload #18
    //   331: fsub
    //   332: ldc_w 11.0
    //   335: fsub
    //   336: f2i
    //   337: fload #20
    //   339: f2i
    //   340: iconst_0
    //   341: sipush #17087
    //   344: ldc2_w 4295021529573628734
    //   347: lload #6
    //   349: lxor
    //   350: <illegal opcode> w : (IJ)I
    //   355: sipush #17087
    //   358: ldc2_w 4295021529573628734
    //   361: lload #6
    //   363: lxor
    //   364: <illegal opcode> w : (IJ)I
    //   369: aload #21
    //   371: invokevirtual method_25298 : (IIIIILnet/minecraft/class_1058;)V
    //   374: invokestatic disableBlend : ()V
    //   377: aload_0
    //   378: getfield b : Lwtf/opal/bu;
    //   381: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   384: aload_3
    //   385: aload #17
    //   387: fload #4
    //   389: fload #18
    //   391: fsub
    //   392: fconst_1
    //   393: fadd
    //   394: fload #20
    //   396: ldc_w 10.0
    //   399: iload #19
    //   401: iconst_1
    //   402: iconst_0
    //   403: lload #10
    //   405: sipush #17087
    //   408: ldc2_w 4295021529573628734
    //   411: lload #6
    //   413: lxor
    //   414: <illegal opcode> w : (IJ)I
    //   419: bipush #11
    //   421: anewarray java/lang/Object
    //   424: dup_x1
    //   425: swap
    //   426: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   429: bipush #10
    //   431: swap
    //   432: aastore
    //   433: dup_x2
    //   434: dup_x2
    //   435: pop
    //   436: invokestatic valueOf : (J)Ljava/lang/Long;
    //   439: bipush #9
    //   441: swap
    //   442: aastore
    //   443: dup_x1
    //   444: swap
    //   445: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   448: bipush #8
    //   450: swap
    //   451: aastore
    //   452: dup_x1
    //   453: swap
    //   454: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   457: bipush #7
    //   459: swap
    //   460: aastore
    //   461: dup_x1
    //   462: swap
    //   463: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   466: bipush #6
    //   468: swap
    //   469: aastore
    //   470: dup_x1
    //   471: swap
    //   472: invokestatic valueOf : (F)Ljava/lang/Float;
    //   475: iconst_5
    //   476: swap
    //   477: aastore
    //   478: dup_x1
    //   479: swap
    //   480: invokestatic valueOf : (F)Ljava/lang/Float;
    //   483: iconst_4
    //   484: swap
    //   485: aastore
    //   486: dup_x1
    //   487: swap
    //   488: invokestatic valueOf : (F)Ljava/lang/Float;
    //   491: iconst_3
    //   492: swap
    //   493: aastore
    //   494: dup_x1
    //   495: swap
    //   496: iconst_2
    //   497: swap
    //   498: aastore
    //   499: dup_x1
    //   500: swap
    //   501: iconst_1
    //   502: swap
    //   503: aastore
    //   504: dup_x1
    //   505: swap
    //   506: iconst_0
    //   507: swap
    //   508: aastore
    //   509: invokevirtual E : ([Ljava/lang/Object;)V
    //   512: return
    // Exception table:
    //   from	to	target	type
    //   77	90	93	wtf/opal/x5
    //   87	114	114	wtf/opal/x5
    //   131	146	149	wtf/opal/x5
    //   143	160	163	wtf/opal/x5
  }
  
  private int lambda$new$0(Map.Entry paramEntry1, Map.Entry paramEntry2) {
    // Byte code:
    //   0: getstatic wtf/opal/jt.d : J
    //   3: ldc2_w 125727947424982
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 3464361388315
    //   13: lxor
    //   14: lstore #5
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: aload_1
    //   21: invokeinterface getKey : ()Ljava/lang/Object;
    //   26: checkcast net/minecraft/class_6880
    //   29: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   34: checkcast net/minecraft/class_1291
    //   37: astore #9
    //   39: aload_1
    //   40: invokeinterface getValue : ()Ljava/lang/Object;
    //   45: checkcast net/minecraft/class_1293
    //   48: astore #10
    //   50: astore #7
    //   52: aload #10
    //   54: invokevirtual method_5586 : ()Ljava/lang/String;
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokestatic method_4662 : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   64: astore #11
    //   66: aload #10
    //   68: invokevirtual method_48559 : ()Z
    //   71: aload #7
    //   73: ifnonnull -> 111
    //   76: ifeq -> 106
    //   79: goto -> 86
    //   82: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   85: athrow
    //   86: sipush #31210
    //   89: ldc2_w 6884866038341927368
    //   92: lload_3
    //   93: lxor
    //   94: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   99: goto -> 117
    //   102: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   105: athrow
    //   106: aload #10
    //   108: invokevirtual method_5584 : ()I
    //   111: ldc_w 20.0
    //   114: invokestatic method_15439 : (IF)Ljava/lang/String;
    //   117: astore #12
    //   119: aload #11
    //   121: aload #10
    //   123: invokevirtual method_5578 : ()I
    //   126: aload #7
    //   128: ifnonnull -> 155
    //   131: ifle -> 163
    //   134: goto -> 141
    //   137: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: aload #10
    //   143: invokevirtual method_5578 : ()I
    //   146: iconst_1
    //   147: iadd
    //   148: goto -> 155
    //   151: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   154: athrow
    //   155: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
    //   160: goto -> 166
    //   163: ldc_w ''
    //   166: aload #12
    //   168: sipush #11744
    //   171: ldc2_w 1717559947475342796
    //   174: lload_3
    //   175: lxor
    //   176: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   181: swap
    //   182: ldc_w ')'
    //   185: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   190: astore #8
    //   192: aload_2
    //   193: invokeinterface getKey : ()Ljava/lang/Object;
    //   198: checkcast net/minecraft/class_6880
    //   201: invokeinterface comp_349 : ()Ljava/lang/Object;
    //   206: checkcast net/minecraft/class_1291
    //   209: astore #10
    //   211: aload_2
    //   212: invokeinterface getValue : ()Ljava/lang/Object;
    //   217: checkcast net/minecraft/class_1293
    //   220: astore #11
    //   222: aload #11
    //   224: invokevirtual method_5586 : ()Ljava/lang/String;
    //   227: iconst_0
    //   228: anewarray java/lang/Object
    //   231: invokestatic method_4662 : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   234: astore #12
    //   236: aload #11
    //   238: invokevirtual method_48559 : ()Z
    //   241: aload #7
    //   243: ifnonnull -> 281
    //   246: ifeq -> 276
    //   249: goto -> 256
    //   252: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   255: athrow
    //   256: sipush #31139
    //   259: ldc2_w 1993292427648074123
    //   262: lload_3
    //   263: lxor
    //   264: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   269: goto -> 287
    //   272: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   275: athrow
    //   276: aload #11
    //   278: invokevirtual method_5584 : ()I
    //   281: ldc_w 20.0
    //   284: invokestatic method_15439 : (IF)Ljava/lang/String;
    //   287: astore #13
    //   289: aload #12
    //   291: aload #11
    //   293: invokevirtual method_5578 : ()I
    //   296: aload #7
    //   298: ifnonnull -> 325
    //   301: ifle -> 333
    //   304: goto -> 311
    //   307: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   310: athrow
    //   311: aload #11
    //   313: invokevirtual method_5578 : ()I
    //   316: iconst_1
    //   317: iadd
    //   318: goto -> 325
    //   321: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   324: athrow
    //   325: <illegal opcode> makeConcatWithConstants : (I)Ljava/lang/String;
    //   330: goto -> 336
    //   333: ldc_w ''
    //   336: aload #13
    //   338: sipush #11582
    //   341: ldc2_w 3843383834615174405
    //   344: lload_3
    //   345: lxor
    //   346: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   351: swap
    //   352: ldc_w ')'
    //   355: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   360: astore #9
    //   362: aload_0
    //   363: getfield b : Lwtf/opal/bu;
    //   366: aload #8
    //   368: ldc_w 10.0
    //   371: lload #5
    //   373: iconst_3
    //   374: anewarray java/lang/Object
    //   377: dup_x2
    //   378: dup_x2
    //   379: pop
    //   380: invokestatic valueOf : (J)Ljava/lang/Long;
    //   383: iconst_2
    //   384: swap
    //   385: aastore
    //   386: dup_x1
    //   387: swap
    //   388: invokestatic valueOf : (F)Ljava/lang/Float;
    //   391: iconst_1
    //   392: swap
    //   393: aastore
    //   394: dup_x1
    //   395: swap
    //   396: iconst_0
    //   397: swap
    //   398: aastore
    //   399: invokevirtual s : ([Ljava/lang/Object;)F
    //   402: fneg
    //   403: aload_0
    //   404: getfield b : Lwtf/opal/bu;
    //   407: aload #9
    //   409: ldc_w 10.0
    //   412: lload #5
    //   414: iconst_3
    //   415: anewarray java/lang/Object
    //   418: dup_x2
    //   419: dup_x2
    //   420: pop
    //   421: invokestatic valueOf : (J)Ljava/lang/Long;
    //   424: iconst_2
    //   425: swap
    //   426: aastore
    //   427: dup_x1
    //   428: swap
    //   429: invokestatic valueOf : (F)Ljava/lang/Float;
    //   432: iconst_1
    //   433: swap
    //   434: aastore
    //   435: dup_x1
    //   436: swap
    //   437: iconst_0
    //   438: swap
    //   439: aastore
    //   440: invokevirtual s : ([Ljava/lang/Object;)F
    //   443: fneg
    //   444: invokestatic compare : (FF)I
    //   447: ireturn
    // Exception table:
    //   from	to	target	type
    //   66	79	82	wtf/opal/x5
    //   76	102	102	wtf/opal/x5
    //   119	134	137	wtf/opal/x5
    //   131	148	151	wtf/opal/x5
    //   236	249	252	wtf/opal/x5
    //   246	272	272	wtf/opal/x5
    //   289	304	307	wtf/opal/x5
    //   301	318	321	wtf/opal/x5
  }
  
  public static void g(String paramString) {
    D = paramString;
  }
  
  public static String S() {
    return D;
  }
  
  static {
    long l = d ^ 0x6F22AFAFE0BEL;
    g(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[29];
    boolean bool = false;
    String str;
    int i = (str = "éúrà¼*Ä\\´ÂlhHÌ\0307\030÷6³¬Äqo-QÌW\020¨ÔT\rmI\020\017\037¹\031½bf\025Ï3æ5öOM@ \013Ï\023¦s\r\bw GÂÐ\027\\%ÞªÐÝMçt®Ä¦Shþþ\020©\032ÐTL2âÆai ¢\002x\030»çÉ%9Aó¾+O¯-\021p\036¤uì´d\037\020O/N\0015àfÈI rë¾æ 2­AìPz1\026þw½{k³­Bz®y×üýNSTÜ¦\022u£v\020AÏ¢\034ºãPKÐWCj£Í(\fYÉVÚhG´·\007Gçá\001t\02022{\"\016Hìmûn;·\034Ö}`ý\000\033\020ó½öËN¯\031°ñÁÛ@Æ´\030Fß\004B_y@MÃá( 6ÛüÁñ¼Õâè· È+)Ç\003¤k¶ñÊZ\000\b±\037ó&\023\r?Í%¢iú¶s\020ÿ¯Ì )Â\034ÈËÏ\003Ó\020Ì°l5Ò\002Råes}.gç\020f×Â\036´ZOåjH®:²è\020ûØ\036ôåW.äGe¡\023X \nTx~Rwè\001UN7eãP;\0254ÜÛí¡Ã;Ã5ö\027\0201\036]ÍÌÑÄ\033c\bTD©­\"K\020ÝÖ6ßFcQ\005-\006:ióE\020¤\021\021Öµý>0A\021¹«\017 kîm3ÑÐÅþu{#\033p\006hd\006~çðìO#\nùÜï\007\020\006^óèÊÜ½´²±@¦¤\031 SÊÚû¸Òé¥·rÁ'{Á\035\026¾a\022rHuov÷¬ð\016\020ÿ+¢Ê]P$\bæÁ\t\nJ\t Ý´\016&-øyítÓ\036¦\005yÄG4¦øÓ\006È\021b\006ìZE\020\034\025UÚ=ì{\rp\031µï+Á^").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x2C8B;
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
        throw new RuntimeException("wtf/opal/jt", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = g[i].getBytes("ISO-8859-1");
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
    //   65: ldc_w 'wtf/opal/jt'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5D05;
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
        throw new RuntimeException("wtf/opal/jt", exception);
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
    //   65: ldc_w 'wtf/opal/jt'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */