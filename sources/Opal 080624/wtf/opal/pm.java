package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_2596;

public final class pm extends u_<jw> {
  private long A = System.currentTimeMillis();
  
  private long F = System.currentTimeMillis();
  
  private long e = System.currentTimeMillis();
  
  private final List<class_2596<?>> i = new ArrayList<>();
  
  private boolean C;
  
  private double Y;
  
  private final gm<lu> p = this::lambda$new$0;
  
  private final gm<b6> g;
  
  private final gm<lb> h;
  
  private static boolean a;
  
  private static final long b = on.a(6472146225741570206L, 3853946714293942839L, MethodHandles.lookup().lookupClass()).a(9246301300149L);
  
  private static final String[] c;
  
  private static final String[] d;
  
  private static final Map f = new HashMap<>(13);
  
  private static final long[] j;
  
  private static final Integer[] k;
  
  private static final Map m;
  
  private static final long[] n;
  
  private static final Long[] o;
  
  private static final Map q;
  
  public pm(jw paramjw, short paramShort, int paramInt1, int paramInt2) {
    super(paramjw);
    boolean bool = A();
    try {
      this.g = this::lambda$new$1;
      this.h = this::lambda$new$2;
      if (!bool)
        d.p(new d[2]); 
    } catch (x5 x5) {
      throw b(null);
    } 
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return x4.VANILLA;
  }
  
  private void lambda$new$2(lb paramlb) {
    // Byte code:
    //   0: getstatic wtf/opal/pm.b : J
    //   3: ldc2_w 68094904865773
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic D : ()Z
    //   11: istore #4
    //   13: aload_0
    //   14: getfield G : Lwtf/opal/d;
    //   17: checkcast wtf/opal/jw
    //   20: getfield R : Lwtf/opal/ke;
    //   23: invokevirtual z : ()Ljava/lang/Object;
    //   26: checkcast java/lang/Boolean
    //   29: invokevirtual booleanValue : ()Z
    //   32: iload #4
    //   34: ifne -> 52
    //   37: ifne -> 48
    //   40: goto -> 47
    //   43: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   46: athrow
    //   47: return
    //   48: aload_0
    //   49: getfield C : Z
    //   52: iload #4
    //   54: ifne -> 199
    //   57: ifeq -> 171
    //   60: goto -> 67
    //   63: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   66: athrow
    //   67: aload_1
    //   68: iconst_0
    //   69: anewarray java/lang/Object
    //   72: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   75: instanceof net/minecraft/class_6374
    //   78: iload #4
    //   80: ifne -> 165
    //   83: goto -> 90
    //   86: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   89: athrow
    //   90: ifne -> 133
    //   93: goto -> 100
    //   96: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   99: athrow
    //   100: aload_1
    //   101: iconst_0
    //   102: anewarray java/lang/Object
    //   105: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   108: instanceof net/minecraft/class_2827
    //   111: iload #4
    //   113: ifne -> 165
    //   116: goto -> 123
    //   119: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   122: athrow
    //   123: ifeq -> 246
    //   126: goto -> 133
    //   129: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: aload_1
    //   134: iconst_0
    //   135: anewarray java/lang/Object
    //   138: invokevirtual Z : ([Ljava/lang/Object;)V
    //   141: aload_0
    //   142: getfield i : Ljava/util/List;
    //   145: aload_1
    //   146: iconst_0
    //   147: anewarray java/lang/Object
    //   150: invokevirtual J : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   153: invokeinterface add : (Ljava/lang/Object;)Z
    //   158: goto -> 165
    //   161: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   164: athrow
    //   165: pop
    //   166: iload #4
    //   168: ifeq -> 246
    //   171: aload_0
    //   172: getfield i : Ljava/util/List;
    //   175: iload #4
    //   177: ifne -> 241
    //   180: goto -> 187
    //   183: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   186: athrow
    //   187: invokeinterface isEmpty : ()Z
    //   192: goto -> 199
    //   195: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   198: athrow
    //   199: ifne -> 246
    //   202: aload_0
    //   203: getfield i : Ljava/util/List;
    //   206: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   209: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   212: invokevirtual method_48296 : ()Lnet/minecraft/class_2535;
    //   215: dup
    //   216: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
    //   219: pop
    //   220: <illegal opcode> accept : (Lnet/minecraft/class_2535;)Ljava/util/function/Consumer;
    //   225: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   230: aload_0
    //   231: getfield i : Ljava/util/List;
    //   234: goto -> 241
    //   237: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   240: athrow
    //   241: invokeinterface clear : ()V
    //   246: return
    // Exception table:
    //   from	to	target	type
    //   13	40	43	wtf/opal/x5
    //   52	60	63	wtf/opal/x5
    //   57	83	86	wtf/opal/x5
    //   67	93	96	wtf/opal/x5
    //   90	116	119	wtf/opal/x5
    //   100	126	129	wtf/opal/x5
    //   123	158	161	wtf/opal/x5
    //   165	180	183	wtf/opal/x5
    //   171	192	195	wtf/opal/x5
    //   199	234	237	wtf/opal/x5
  }
  
  private void lambda$new$1(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/pm.b : J
    //   3: ldc2_w 122063583198044
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic A : ()Z
    //   11: istore #4
    //   13: aload_0
    //   14: getfield G : Lwtf/opal/d;
    //   17: checkcast wtf/opal/jw
    //   20: getfield R : Lwtf/opal/ke;
    //   23: invokevirtual z : ()Ljava/lang/Object;
    //   26: checkcast java/lang/Boolean
    //   29: invokevirtual booleanValue : ()Z
    //   32: iload #4
    //   34: ifeq -> 62
    //   37: ifeq -> 77
    //   40: goto -> 47
    //   43: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   46: athrow
    //   47: aload_1
    //   48: iconst_0
    //   49: anewarray java/lang/Object
    //   52: invokevirtual W : ([Ljava/lang/Object;)Z
    //   55: goto -> 62
    //   58: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   61: athrow
    //   62: iload #4
    //   64: ifeq -> 82
    //   67: ifne -> 78
    //   70: goto -> 77
    //   73: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: return
    //   78: aload_0
    //   79: getfield C : Z
    //   82: iload #4
    //   84: ifeq -> 219
    //   87: ifeq -> 198
    //   90: goto -> 97
    //   93: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   100: getfield field_1724 : Lnet/minecraft/class_746;
    //   103: iload #4
    //   105: ifeq -> 172
    //   108: goto -> 115
    //   111: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   114: athrow
    //   115: invokevirtual method_24828 : ()Z
    //   118: ifne -> 159
    //   121: goto -> 128
    //   124: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   127: athrow
    //   128: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   131: getfield field_1724 : Lnet/minecraft/class_746;
    //   134: invokevirtual method_52535 : ()Z
    //   137: iload #4
    //   139: ifeq -> 219
    //   142: goto -> 149
    //   145: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   148: athrow
    //   149: ifeq -> 198
    //   152: goto -> 159
    //   155: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   158: athrow
    //   159: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   162: getfield field_1724 : Lnet/minecraft/class_746;
    //   165: goto -> 172
    //   168: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   171: athrow
    //   172: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   175: getfield field_1724 : Lnet/minecraft/class_746;
    //   178: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   181: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   184: ldc2_w 0.41999998688697815
    //   187: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   190: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   193: aload_0
    //   194: iconst_0
    //   195: putfield C : Z
    //   198: aload_0
    //   199: iload #4
    //   201: ifeq -> 243
    //   204: getfield i : Ljava/util/List;
    //   207: invokeinterface size : ()I
    //   212: goto -> 219
    //   215: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   218: athrow
    //   219: sipush #18555
    //   222: ldc2_w 3540036670241793487
    //   225: lload_2
    //   226: lxor
    //   227: <illegal opcode> t : (IJ)I
    //   232: if_icmple -> 247
    //   235: aload_0
    //   236: goto -> 243
    //   239: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   242: athrow
    //   243: iconst_0
    //   244: putfield C : Z
    //   247: return
    // Exception table:
    //   from	to	target	type
    //   13	40	43	wtf/opal/x5
    //   37	55	58	wtf/opal/x5
    //   62	70	73	wtf/opal/x5
    //   82	90	93	wtf/opal/x5
    //   87	108	111	wtf/opal/x5
    //   97	121	124	wtf/opal/x5
    //   115	142	145	wtf/opal/x5
    //   128	152	155	wtf/opal/x5
    //   149	165	168	wtf/opal/x5
    //   198	212	215	wtf/opal/x5
    //   219	236	239	wtf/opal/x5
  }
  
  private void lambda$new$0(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/pm.b : J
    //   3: ldc2_w 82096340207440
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 88771634119225
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: invokestatic D : ()Z
    //   20: istore #6
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: getfield field_1724 : Lnet/minecraft/class_746;
    //   28: ifnull -> 623
    //   31: aload_1
    //   32: iconst_0
    //   33: anewarray java/lang/Object
    //   36: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   39: astore #10
    //   41: aload #10
    //   43: instanceof net/minecraft/class_2743
    //   46: iload #6
    //   48: ifne -> 504
    //   51: ifeq -> 489
    //   54: goto -> 61
    //   57: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   60: athrow
    //   61: aload #10
    //   63: checkcast net/minecraft/class_2743
    //   66: astore #7
    //   68: aload #7
    //   70: invokevirtual method_11818 : ()I
    //   73: iload #6
    //   75: ifne -> 504
    //   78: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   81: getfield field_1724 : Lnet/minecraft/class_746;
    //   84: invokevirtual method_5628 : ()I
    //   87: if_icmpne -> 489
    //   90: goto -> 97
    //   93: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: invokestatic currentTimeMillis : ()J
    //   100: aload_0
    //   101: getfield e : J
    //   104: lsub
    //   105: sipush #32081
    //   108: ldc2_w 3770524577561098813
    //   111: lload_2
    //   112: lxor
    //   113: <illegal opcode> x : (IJ)J
    //   118: lcmp
    //   119: iload #6
    //   121: ifne -> 504
    //   124: goto -> 131
    //   127: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   130: athrow
    //   131: ifle -> 489
    //   134: goto -> 141
    //   137: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   140: athrow
    //   141: invokestatic currentTimeMillis : ()J
    //   144: aload_0
    //   145: getfield A : J
    //   148: lsub
    //   149: sipush #27327
    //   152: ldc2_w 7610075846117305808
    //   155: lload_2
    //   156: lxor
    //   157: <illegal opcode> x : (IJ)J
    //   162: lcmp
    //   163: iload #6
    //   165: ifne -> 504
    //   168: goto -> 175
    //   171: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   174: athrow
    //   175: ifle -> 489
    //   178: goto -> 185
    //   181: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: aload_0
    //   186: getfield G : Lwtf/opal/d;
    //   189: checkcast wtf/opal/jw
    //   192: getfield o : Lwtf/opal/ke;
    //   195: invokevirtual z : ()Ljava/lang/Object;
    //   198: checkcast java/lang/Boolean
    //   201: invokevirtual booleanValue : ()Z
    //   204: iload #6
    //   206: ifne -> 242
    //   209: goto -> 216
    //   212: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   215: athrow
    //   216: ifeq -> 488
    //   219: goto -> 226
    //   222: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   225: athrow
    //   226: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   229: getfield field_1724 : Lnet/minecraft/class_746;
    //   232: getfield field_6012 : I
    //   235: goto -> 242
    //   238: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   241: athrow
    //   242: iload #6
    //   244: ifne -> 299
    //   247: sipush #28386
    //   250: ldc2_w 1486855615432884059
    //   253: lload_2
    //   254: lxor
    //   255: <illegal opcode> t : (IJ)I
    //   260: if_icmple -> 488
    //   263: goto -> 270
    //   266: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   269: athrow
    //   270: invokestatic currentTimeMillis : ()J
    //   273: aload_0
    //   274: getfield F : J
    //   277: lsub
    //   278: sipush #28052
    //   281: ldc2_w 8937311158634556153
    //   284: lload_2
    //   285: lxor
    //   286: <illegal opcode> x : (IJ)J
    //   291: lcmp
    //   292: goto -> 299
    //   295: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: iload #6
    //   301: ifne -> 326
    //   304: ifle -> 488
    //   307: goto -> 314
    //   310: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   313: athrow
    //   314: aload #7
    //   316: invokevirtual method_11815 : ()I
    //   319: goto -> 326
    //   322: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   325: athrow
    //   326: iload #6
    //   328: ifne -> 420
    //   331: ifne -> 395
    //   334: goto -> 341
    //   337: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   340: athrow
    //   341: aload #7
    //   343: invokevirtual method_11816 : ()I
    //   346: iload #6
    //   348: ifne -> 420
    //   351: goto -> 358
    //   354: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   357: athrow
    //   358: ifne -> 395
    //   361: goto -> 368
    //   364: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   367: athrow
    //   368: aload #7
    //   370: invokevirtual method_11819 : ()I
    //   373: iload #6
    //   375: ifne -> 420
    //   378: goto -> 385
    //   381: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   384: athrow
    //   385: ifeq -> 488
    //   388: goto -> 395
    //   391: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   394: athrow
    //   395: lload #4
    //   397: iconst_1
    //   398: anewarray java/lang/Object
    //   401: dup_x2
    //   402: dup_x2
    //   403: pop
    //   404: invokestatic valueOf : (J)Ljava/lang/Long;
    //   407: iconst_0
    //   408: swap
    //   409: aastore
    //   410: invokestatic h : ([Ljava/lang/Object;)Z
    //   413: goto -> 420
    //   416: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   419: athrow
    //   420: ifne -> 488
    //   423: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   426: invokevirtual method_1566 : ()Lnet/minecraft/class_374;
    //   429: new net/minecraft/class_370
    //   432: dup
    //   433: getstatic net/minecraft/class_370$class_9037.field_47588 : Lnet/minecraft/class_370$class_9037;
    //   436: sipush #7298
    //   439: ldc2_w 2561709286360542232
    //   442: lload_2
    //   443: lxor
    //   444: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   449: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   452: sipush #14922
    //   455: ldc2_w 4243929839725071057
    //   458: lload_2
    //   459: lxor
    //   460: <illegal opcode> c : (IJ)Ljava/lang/String;
    //   465: invokestatic method_43470 : (Ljava/lang/String;)Lnet/minecraft/class_5250;
    //   468: invokespecial <init> : (Lnet/minecraft/class_370$class_9037;Lnet/minecraft/class_2561;Lnet/minecraft/class_2561;)V
    //   471: invokevirtual method_1999 : (Lnet/minecraft/class_368;)V
    //   474: aload_0
    //   475: invokestatic currentTimeMillis : ()J
    //   478: putfield F : J
    //   481: goto -> 488
    //   484: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   487: athrow
    //   488: return
    //   489: aload_1
    //   490: iconst_0
    //   491: anewarray java/lang/Object
    //   494: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   497: astore #10
    //   499: aload #10
    //   501: instanceof net/minecraft/class_8143
    //   504: iload #6
    //   506: ifne -> 582
    //   509: ifeq -> 555
    //   512: goto -> 519
    //   515: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   518: athrow
    //   519: aload #10
    //   521: checkcast net/minecraft/class_8143
    //   524: astore #8
    //   526: aload #8
    //   528: invokevirtual comp_1267 : ()I
    //   531: iload #6
    //   533: ifne -> 582
    //   536: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   539: getfield field_1724 : Lnet/minecraft/class_746;
    //   542: invokevirtual method_5628 : ()I
    //   545: if_icmpeq -> 609
    //   548: goto -> 555
    //   551: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   554: athrow
    //   555: aload_1
    //   556: iconst_0
    //   557: anewarray java/lang/Object
    //   560: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   563: astore #10
    //   565: aload #10
    //   567: iload #6
    //   569: ifne -> 587
    //   572: instanceof net/minecraft/class_8043
    //   575: goto -> 582
    //   578: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   581: athrow
    //   582: ifeq -> 623
    //   585: aload #10
    //   587: checkcast net/minecraft/class_8043
    //   590: astore #9
    //   592: aload #9
    //   594: invokevirtual comp_1202 : ()I
    //   597: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   600: getfield field_1724 : Lnet/minecraft/class_746;
    //   603: invokevirtual method_5628 : ()I
    //   606: if_icmpne -> 623
    //   609: aload_0
    //   610: invokestatic currentTimeMillis : ()J
    //   613: putfield A : J
    //   616: goto -> 623
    //   619: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   622: athrow
    //   623: iconst_0
    //   624: anewarray java/lang/Object
    //   627: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   630: iconst_0
    //   631: anewarray java/lang/Object
    //   634: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   637: ldc_w wtf/opal/jm
    //   640: iconst_1
    //   641: anewarray java/lang/Object
    //   644: dup_x1
    //   645: swap
    //   646: iconst_0
    //   647: swap
    //   648: aastore
    //   649: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   652: checkcast wtf/opal/jm
    //   655: astore #7
    //   657: aload_1
    //   658: iconst_0
    //   659: anewarray java/lang/Object
    //   662: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   665: astore #10
    //   667: aload #10
    //   669: instanceof net/minecraft/class_2743
    //   672: iload #6
    //   674: ifne -> 1776
    //   677: ifeq -> 1749
    //   680: goto -> 687
    //   683: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   686: athrow
    //   687: aload #10
    //   689: checkcast net/minecraft/class_2743
    //   692: astore #8
    //   694: aload #8
    //   696: checkcast wtf/opal/mixin/EntityVelocityUpdateS2CPacketAccessor
    //   699: astore #10
    //   701: aload #8
    //   703: invokevirtual method_11818 : ()I
    //   706: iload #6
    //   708: ifne -> 776
    //   711: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   714: getfield field_1724 : Lnet/minecraft/class_746;
    //   717: invokevirtual method_5628 : ()I
    //   720: if_icmpne -> 1746
    //   723: goto -> 730
    //   726: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   729: athrow
    //   730: iconst_0
    //   731: anewarray java/lang/Object
    //   734: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   737: iconst_0
    //   738: anewarray java/lang/Object
    //   741: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   744: ldc_w wtf/opal/h
    //   747: iconst_1
    //   748: anewarray java/lang/Object
    //   751: dup_x1
    //   752: swap
    //   753: iconst_0
    //   754: swap
    //   755: aastore
    //   756: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   759: checkcast wtf/opal/h
    //   762: iconst_0
    //   763: anewarray java/lang/Object
    //   766: invokevirtual D : ([Ljava/lang/Object;)Z
    //   769: goto -> 776
    //   772: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   775: athrow
    //   776: iload #6
    //   778: ifne -> 807
    //   781: ifne -> 1746
    //   784: goto -> 791
    //   787: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   790: athrow
    //   791: aload #7
    //   793: iconst_0
    //   794: anewarray java/lang/Object
    //   797: invokevirtual D : ([Ljava/lang/Object;)Z
    //   800: goto -> 807
    //   803: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   806: athrow
    //   807: iload #6
    //   809: ifne -> 965
    //   812: ifeq -> 907
    //   815: goto -> 822
    //   818: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   821: athrow
    //   822: aload #7
    //   824: getfield u : Lwtf/opal/ky;
    //   827: invokevirtual z : ()Ljava/lang/Object;
    //   830: checkcast wtf/opal/bn
    //   833: getstatic wtf/opal/bn.WATCHDOG : Lwtf/opal/bn;
    //   836: invokevirtual equals : (Ljava/lang/Object;)Z
    //   839: iload #6
    //   841: ifne -> 892
    //   844: goto -> 851
    //   847: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   850: athrow
    //   851: ifeq -> 1746
    //   854: goto -> 861
    //   857: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   860: athrow
    //   861: aload #7
    //   863: iconst_0
    //   864: anewarray java/lang/Object
    //   867: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/u_;
    //   870: checkcast wtf/opal/uo
    //   873: getfield D : Lwtf/opal/ke;
    //   876: invokevirtual z : ()Ljava/lang/Object;
    //   879: checkcast java/lang/Boolean
    //   882: invokevirtual booleanValue : ()Z
    //   885: goto -> 892
    //   888: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   891: athrow
    //   892: iload #6
    //   894: ifne -> 965
    //   897: ifne -> 1746
    //   900: goto -> 907
    //   903: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   906: athrow
    //   907: iconst_0
    //   908: anewarray java/lang/Object
    //   911: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   914: iconst_0
    //   915: anewarray java/lang/Object
    //   918: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   921: ldc_w wtf/opal/s
    //   924: iconst_1
    //   925: anewarray java/lang/Object
    //   928: dup_x1
    //   929: swap
    //   930: iconst_0
    //   931: swap
    //   932: aastore
    //   933: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   936: checkcast wtf/opal/s
    //   939: iload #6
    //   941: ifne -> 1004
    //   944: goto -> 951
    //   947: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   950: athrow
    //   951: iconst_0
    //   952: anewarray java/lang/Object
    //   955: invokevirtual D : ([Ljava/lang/Object;)Z
    //   958: goto -> 965
    //   961: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   964: athrow
    //   965: ifne -> 1746
    //   968: iconst_0
    //   969: anewarray java/lang/Object
    //   972: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   975: iconst_0
    //   976: anewarray java/lang/Object
    //   979: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   982: ldc_w wtf/opal/xc
    //   985: iconst_1
    //   986: anewarray java/lang/Object
    //   989: dup_x1
    //   990: swap
    //   991: iconst_0
    //   992: swap
    //   993: aastore
    //   994: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   997: goto -> 1004
    //   1000: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1003: athrow
    //   1004: checkcast wtf/opal/xc
    //   1007: astore #11
    //   1009: aload_0
    //   1010: getfield G : Lwtf/opal/d;
    //   1013: checkcast wtf/opal/jw
    //   1016: getfield J : Lwtf/opal/kt;
    //   1019: invokevirtual z : ()Ljava/lang/Object;
    //   1022: checkcast java/lang/Double
    //   1025: invokevirtual doubleValue : ()D
    //   1028: dconst_0
    //   1029: dcmpl
    //   1030: iload #6
    //   1032: ifne -> 1134
    //   1035: ifne -> 1088
    //   1038: goto -> 1045
    //   1041: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1044: athrow
    //   1045: aload_0
    //   1046: getfield G : Lwtf/opal/d;
    //   1049: checkcast wtf/opal/jw
    //   1052: getfield x : Lwtf/opal/kt;
    //   1055: invokevirtual z : ()Ljava/lang/Object;
    //   1058: checkcast java/lang/Double
    //   1061: invokevirtual doubleValue : ()D
    //   1064: dconst_0
    //   1065: dcmpl
    //   1066: iload #6
    //   1068: ifne -> 1358
    //   1071: goto -> 1078
    //   1074: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1077: athrow
    //   1078: ifeq -> 1324
    //   1081: goto -> 1088
    //   1084: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1087: athrow
    //   1088: iconst_0
    //   1089: anewarray java/lang/Object
    //   1092: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1095: iconst_0
    //   1096: anewarray java/lang/Object
    //   1099: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   1102: ldc_w wtf/opal/xw
    //   1105: iconst_1
    //   1106: anewarray java/lang/Object
    //   1109: dup_x1
    //   1110: swap
    //   1111: iconst_0
    //   1112: swap
    //   1113: aastore
    //   1114: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   1117: checkcast wtf/opal/xw
    //   1120: iconst_0
    //   1121: anewarray java/lang/Object
    //   1124: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1127: goto -> 1134
    //   1130: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1133: athrow
    //   1134: iload #6
    //   1136: ifne -> 1358
    //   1139: ifne -> 1324
    //   1142: goto -> 1149
    //   1145: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1148: athrow
    //   1149: aload #11
    //   1151: iconst_0
    //   1152: anewarray java/lang/Object
    //   1155: invokevirtual Z : ([Ljava/lang/Object;)Z
    //   1158: iload #6
    //   1160: ifne -> 1278
    //   1163: goto -> 1170
    //   1166: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1169: athrow
    //   1170: ifeq -> 1252
    //   1173: goto -> 1180
    //   1176: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1179: athrow
    //   1180: aload #11
    //   1182: iconst_0
    //   1183: anewarray java/lang/Object
    //   1186: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1189: iload #6
    //   1191: ifne -> 1278
    //   1194: goto -> 1201
    //   1197: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1200: athrow
    //   1201: ifeq -> 1252
    //   1204: goto -> 1211
    //   1207: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1210: athrow
    //   1211: aload_0
    //   1212: getfield G : Lwtf/opal/d;
    //   1215: checkcast wtf/opal/jw
    //   1218: getfield w : Lwtf/opal/ke;
    //   1221: invokevirtual z : ()Ljava/lang/Object;
    //   1224: checkcast java/lang/Boolean
    //   1227: invokevirtual booleanValue : ()Z
    //   1230: iload #6
    //   1232: ifne -> 1358
    //   1235: goto -> 1242
    //   1238: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1241: athrow
    //   1242: ifne -> 1324
    //   1245: goto -> 1252
    //   1248: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1251: athrow
    //   1252: aload_0
    //   1253: getfield G : Lwtf/opal/d;
    //   1256: checkcast wtf/opal/jw
    //   1259: getfield R : Lwtf/opal/ke;
    //   1262: invokevirtual z : ()Ljava/lang/Object;
    //   1265: checkcast java/lang/Boolean
    //   1268: invokevirtual booleanValue : ()Z
    //   1271: goto -> 1278
    //   1274: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1277: athrow
    //   1278: iload #6
    //   1280: ifne -> 1414
    //   1283: ifeq -> 1405
    //   1286: goto -> 1293
    //   1289: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1292: athrow
    //   1293: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1296: getfield field_1724 : Lnet/minecraft/class_746;
    //   1299: invokevirtual method_24828 : ()Z
    //   1302: iload #6
    //   1304: ifne -> 1414
    //   1307: goto -> 1314
    //   1310: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1313: athrow
    //   1314: ifne -> 1405
    //   1317: goto -> 1324
    //   1320: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1323: athrow
    //   1324: aload_1
    //   1325: iconst_0
    //   1326: anewarray java/lang/Object
    //   1329: invokevirtual Z : ([Ljava/lang/Object;)V
    //   1332: aload_0
    //   1333: getfield G : Lwtf/opal/d;
    //   1336: checkcast wtf/opal/jw
    //   1339: getfield R : Lwtf/opal/ke;
    //   1342: invokevirtual z : ()Ljava/lang/Object;
    //   1345: checkcast java/lang/Boolean
    //   1348: invokevirtual booleanValue : ()Z
    //   1351: goto -> 1358
    //   1354: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1357: athrow
    //   1358: iload #6
    //   1360: ifne -> 1389
    //   1363: ifeq -> 1404
    //   1366: goto -> 1373
    //   1369: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1372: athrow
    //   1373: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1376: getfield field_1724 : Lnet/minecraft/class_746;
    //   1379: invokevirtual method_24828 : ()Z
    //   1382: goto -> 1389
    //   1385: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1388: athrow
    //   1389: ifne -> 1404
    //   1392: aload_0
    //   1393: iconst_1
    //   1394: putfield C : Z
    //   1397: goto -> 1404
    //   1400: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1403: athrow
    //   1404: return
    //   1405: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1408: getfield field_1724 : Lnet/minecraft/class_746;
    //   1411: invokevirtual method_24828 : ()Z
    //   1414: iload #6
    //   1416: ifne -> 1527
    //   1419: ifeq -> 1499
    //   1422: goto -> 1429
    //   1425: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1428: athrow
    //   1429: aload_0
    //   1430: getfield G : Lwtf/opal/d;
    //   1433: checkcast wtf/opal/jw
    //   1436: getfield m : Lwtf/opal/ke;
    //   1439: invokevirtual z : ()Ljava/lang/Object;
    //   1442: checkcast java/lang/Boolean
    //   1445: invokevirtual booleanValue : ()Z
    //   1448: iload #6
    //   1450: ifne -> 1527
    //   1453: goto -> 1460
    //   1456: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1459: athrow
    //   1460: ifeq -> 1499
    //   1463: goto -> 1470
    //   1466: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1469: athrow
    //   1470: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1473: getfield field_1724 : Lnet/minecraft/class_746;
    //   1476: invokevirtual method_6043 : ()V
    //   1479: aload_1
    //   1480: iconst_0
    //   1481: anewarray java/lang/Object
    //   1484: invokevirtual Z : ([Ljava/lang/Object;)V
    //   1487: iload #6
    //   1489: ifeq -> 1746
    //   1492: goto -> 1499
    //   1495: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1498: athrow
    //   1499: aload_0
    //   1500: getfield G : Lwtf/opal/d;
    //   1503: checkcast wtf/opal/jw
    //   1506: getfield J : Lwtf/opal/kt;
    //   1509: invokevirtual z : ()Ljava/lang/Object;
    //   1512: checkcast java/lang/Double
    //   1515: invokevirtual doubleValue : ()D
    //   1518: dconst_0
    //   1519: dcmpl
    //   1520: goto -> 1527
    //   1523: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1526: athrow
    //   1527: iload #6
    //   1529: ifne -> 1578
    //   1532: ifne -> 1632
    //   1535: goto -> 1542
    //   1538: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1541: athrow
    //   1542: aload_1
    //   1543: iconst_0
    //   1544: anewarray java/lang/Object
    //   1547: invokevirtual Z : ([Ljava/lang/Object;)V
    //   1550: aload_0
    //   1551: getfield G : Lwtf/opal/d;
    //   1554: checkcast wtf/opal/jw
    //   1557: getfield x : Lwtf/opal/kt;
    //   1560: invokevirtual z : ()Ljava/lang/Object;
    //   1563: checkcast java/lang/Double
    //   1566: invokevirtual doubleValue : ()D
    //   1569: dconst_0
    //   1570: dcmpl
    //   1571: goto -> 1578
    //   1574: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1577: athrow
    //   1578: ifeq -> 1631
    //   1581: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1584: getfield field_1724 : Lnet/minecraft/class_746;
    //   1587: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1590: getfield field_1724 : Lnet/minecraft/class_746;
    //   1593: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1596: invokevirtual method_10216 : ()D
    //   1599: aload #8
    //   1601: invokevirtual method_11816 : ()I
    //   1604: i2d
    //   1605: ldc2_w 8000.0
    //   1608: ddiv
    //   1609: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1612: getfield field_1724 : Lnet/minecraft/class_746;
    //   1615: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   1618: invokevirtual method_10215 : ()D
    //   1621: invokevirtual method_18800 : (DDD)V
    //   1624: goto -> 1631
    //   1627: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1630: athrow
    //   1631: return
    //   1632: aload #10
    //   1634: aload #8
    //   1636: invokevirtual method_11815 : ()I
    //   1639: i2d
    //   1640: aload_0
    //   1641: getfield G : Lwtf/opal/d;
    //   1644: checkcast wtf/opal/jw
    //   1647: getfield J : Lwtf/opal/kt;
    //   1650: invokevirtual z : ()Ljava/lang/Object;
    //   1653: checkcast java/lang/Double
    //   1656: invokevirtual doubleValue : ()D
    //   1659: ldc2_w 100.0
    //   1662: ddiv
    //   1663: dmul
    //   1664: d2i
    //   1665: invokeinterface setVelocityX : (I)V
    //   1670: aload #10
    //   1672: aload #8
    //   1674: invokevirtual method_11816 : ()I
    //   1677: i2d
    //   1678: aload_0
    //   1679: getfield G : Lwtf/opal/d;
    //   1682: checkcast wtf/opal/jw
    //   1685: getfield x : Lwtf/opal/kt;
    //   1688: invokevirtual z : ()Ljava/lang/Object;
    //   1691: checkcast java/lang/Double
    //   1694: invokevirtual doubleValue : ()D
    //   1697: ldc2_w 100.0
    //   1700: ddiv
    //   1701: dmul
    //   1702: d2i
    //   1703: invokeinterface setVelocityY : (I)V
    //   1708: aload #10
    //   1710: aload #8
    //   1712: invokevirtual method_11819 : ()I
    //   1715: i2d
    //   1716: aload_0
    //   1717: getfield G : Lwtf/opal/d;
    //   1720: checkcast wtf/opal/jw
    //   1723: getfield J : Lwtf/opal/kt;
    //   1726: invokevirtual z : ()Ljava/lang/Object;
    //   1729: checkcast java/lang/Double
    //   1732: invokevirtual doubleValue : ()D
    //   1735: ldc2_w 100.0
    //   1738: ddiv
    //   1739: dmul
    //   1740: d2i
    //   1741: invokeinterface setVelocityZ : (I)V
    //   1746: goto -> 2227
    //   1749: aload_1
    //   1750: iconst_0
    //   1751: anewarray java/lang/Object
    //   1754: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   1757: astore #10
    //   1759: aload #10
    //   1761: iload #6
    //   1763: ifne -> 1781
    //   1766: instanceof net/minecraft/class_2664
    //   1769: goto -> 1776
    //   1772: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1775: athrow
    //   1776: ifeq -> 2227
    //   1779: aload #10
    //   1781: checkcast net/minecraft/class_2664
    //   1784: astore #9
    //   1786: iconst_0
    //   1787: anewarray java/lang/Object
    //   1790: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1793: iconst_0
    //   1794: anewarray java/lang/Object
    //   1797: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   1800: ldc_w wtf/opal/h
    //   1803: iconst_1
    //   1804: anewarray java/lang/Object
    //   1807: dup_x1
    //   1808: swap
    //   1809: iconst_0
    //   1810: swap
    //   1811: aastore
    //   1812: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   1815: checkcast wtf/opal/h
    //   1818: iconst_0
    //   1819: anewarray java/lang/Object
    //   1822: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1825: iload #6
    //   1827: ifne -> 1856
    //   1830: ifne -> 2227
    //   1833: goto -> 1840
    //   1836: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1839: athrow
    //   1840: aload #7
    //   1842: iconst_0
    //   1843: anewarray java/lang/Object
    //   1846: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1849: goto -> 1856
    //   1852: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1855: athrow
    //   1856: iload #6
    //   1858: ifne -> 2002
    //   1861: ifeq -> 1956
    //   1864: goto -> 1871
    //   1867: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1870: athrow
    //   1871: aload #7
    //   1873: getfield u : Lwtf/opal/ky;
    //   1876: invokevirtual z : ()Ljava/lang/Object;
    //   1879: checkcast wtf/opal/bn
    //   1882: getstatic wtf/opal/bn.WATCHDOG : Lwtf/opal/bn;
    //   1885: invokevirtual equals : (Ljava/lang/Object;)Z
    //   1888: iload #6
    //   1890: ifne -> 1941
    //   1893: goto -> 1900
    //   1896: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1899: athrow
    //   1900: ifeq -> 2227
    //   1903: goto -> 1910
    //   1906: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1909: athrow
    //   1910: aload #7
    //   1912: iconst_0
    //   1913: anewarray java/lang/Object
    //   1916: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/u_;
    //   1919: checkcast wtf/opal/uo
    //   1922: getfield D : Lwtf/opal/ke;
    //   1925: invokevirtual z : ()Ljava/lang/Object;
    //   1928: checkcast java/lang/Boolean
    //   1931: invokevirtual booleanValue : ()Z
    //   1934: goto -> 1941
    //   1937: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1940: athrow
    //   1941: iload #6
    //   1943: ifne -> 2002
    //   1946: ifne -> 2227
    //   1949: goto -> 1956
    //   1952: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   1955: athrow
    //   1956: iconst_0
    //   1957: anewarray java/lang/Object
    //   1960: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   1963: iconst_0
    //   1964: anewarray java/lang/Object
    //   1967: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   1970: ldc_w wtf/opal/s
    //   1973: iconst_1
    //   1974: anewarray java/lang/Object
    //   1977: dup_x1
    //   1978: swap
    //   1979: iconst_0
    //   1980: swap
    //   1981: aastore
    //   1982: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   1985: checkcast wtf/opal/s
    //   1988: iconst_0
    //   1989: anewarray java/lang/Object
    //   1992: invokevirtual D : ([Ljava/lang/Object;)Z
    //   1995: goto -> 2002
    //   1998: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2001: athrow
    //   2002: iload #6
    //   2004: ifne -> 2045
    //   2007: ifne -> 2227
    //   2010: goto -> 2017
    //   2013: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2016: athrow
    //   2017: aload_0
    //   2018: getfield G : Lwtf/opal/d;
    //   2021: checkcast wtf/opal/jw
    //   2024: getfield J : Lwtf/opal/kt;
    //   2027: invokevirtual z : ()Ljava/lang/Object;
    //   2030: checkcast java/lang/Double
    //   2033: invokevirtual doubleValue : ()D
    //   2036: dconst_0
    //   2037: dcmpl
    //   2038: goto -> 2045
    //   2041: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2044: athrow
    //   2045: iload #6
    //   2047: ifne -> 2137
    //   2050: ifne -> 2116
    //   2053: goto -> 2060
    //   2056: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2059: athrow
    //   2060: aload_0
    //   2061: getfield G : Lwtf/opal/d;
    //   2064: checkcast wtf/opal/jw
    //   2067: getfield x : Lwtf/opal/kt;
    //   2070: invokevirtual z : ()Ljava/lang/Object;
    //   2073: checkcast java/lang/Double
    //   2076: invokevirtual doubleValue : ()D
    //   2079: dconst_0
    //   2080: dcmpl
    //   2081: iload #6
    //   2083: ifne -> 2137
    //   2086: goto -> 2093
    //   2089: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2092: athrow
    //   2093: ifne -> 2116
    //   2096: goto -> 2103
    //   2099: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2102: athrow
    //   2103: aload_1
    //   2104: iconst_0
    //   2105: anewarray java/lang/Object
    //   2108: invokevirtual Z : ([Ljava/lang/Object;)V
    //   2111: return
    //   2112: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2115: athrow
    //   2116: aload_0
    //   2117: getfield G : Lwtf/opal/d;
    //   2120: checkcast wtf/opal/jw
    //   2123: getfield J : Lwtf/opal/kt;
    //   2126: invokevirtual z : ()Ljava/lang/Object;
    //   2129: checkcast java/lang/Double
    //   2132: invokevirtual doubleValue : ()D
    //   2135: dconst_0
    //   2136: dcmpl
    //   2137: iload #6
    //   2139: ifne -> 2188
    //   2142: ifne -> 2227
    //   2145: goto -> 2152
    //   2148: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2151: athrow
    //   2152: aload_1
    //   2153: iconst_0
    //   2154: anewarray java/lang/Object
    //   2157: invokevirtual Z : ([Ljava/lang/Object;)V
    //   2160: aload_0
    //   2161: getfield G : Lwtf/opal/d;
    //   2164: checkcast wtf/opal/jw
    //   2167: getfield x : Lwtf/opal/kt;
    //   2170: invokevirtual z : ()Ljava/lang/Object;
    //   2173: checkcast java/lang/Double
    //   2176: invokevirtual doubleValue : ()D
    //   2179: dconst_0
    //   2180: dcmpl
    //   2181: goto -> 2188
    //   2184: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2187: athrow
    //   2188: ifeq -> 2227
    //   2191: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   2194: getfield field_1724 : Lnet/minecraft/class_746;
    //   2197: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   2200: getfield field_1724 : Lnet/minecraft/class_746;
    //   2203: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   2206: dconst_0
    //   2207: aload #9
    //   2209: invokevirtual method_11473 : ()F
    //   2212: f2d
    //   2213: dconst_0
    //   2214: invokevirtual method_1031 : (DDD)Lnet/minecraft/class_243;
    //   2217: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   2220: goto -> 2227
    //   2223: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   2226: athrow
    //   2227: return
    // Exception table:
    //   from	to	target	type
    //   41	54	57	wtf/opal/x5
    //   68	90	93	wtf/opal/x5
    //   78	124	127	wtf/opal/x5
    //   97	134	137	wtf/opal/x5
    //   131	168	171	wtf/opal/x5
    //   141	178	181	wtf/opal/x5
    //   175	209	212	wtf/opal/x5
    //   185	219	222	wtf/opal/x5
    //   216	235	238	wtf/opal/x5
    //   242	263	266	wtf/opal/x5
    //   247	292	295	wtf/opal/x5
    //   299	307	310	wtf/opal/x5
    //   304	319	322	wtf/opal/x5
    //   326	334	337	wtf/opal/x5
    //   331	351	354	wtf/opal/x5
    //   341	361	364	wtf/opal/x5
    //   358	378	381	wtf/opal/x5
    //   368	388	391	wtf/opal/x5
    //   385	413	416	wtf/opal/x5
    //   420	481	484	wtf/opal/x5
    //   504	512	515	wtf/opal/x5
    //   526	548	551	wtf/opal/x5
    //   565	575	578	wtf/opal/x5
    //   592	616	619	wtf/opal/x5
    //   667	680	683	wtf/opal/x5
    //   701	723	726	wtf/opal/x5
    //   711	769	772	wtf/opal/x5
    //   776	784	787	wtf/opal/x5
    //   781	800	803	wtf/opal/x5
    //   807	815	818	wtf/opal/x5
    //   812	844	847	wtf/opal/x5
    //   822	854	857	wtf/opal/x5
    //   851	885	888	wtf/opal/x5
    //   892	900	903	wtf/opal/x5
    //   897	944	947	wtf/opal/x5
    //   907	958	961	wtf/opal/x5
    //   965	997	1000	wtf/opal/x5
    //   1009	1038	1041	wtf/opal/x5
    //   1035	1071	1074	wtf/opal/x5
    //   1045	1081	1084	wtf/opal/x5
    //   1078	1127	1130	wtf/opal/x5
    //   1134	1142	1145	wtf/opal/x5
    //   1139	1163	1166	wtf/opal/x5
    //   1149	1173	1176	wtf/opal/x5
    //   1170	1194	1197	wtf/opal/x5
    //   1180	1204	1207	wtf/opal/x5
    //   1201	1235	1238	wtf/opal/x5
    //   1211	1245	1248	wtf/opal/x5
    //   1242	1271	1274	wtf/opal/x5
    //   1278	1286	1289	wtf/opal/x5
    //   1283	1307	1310	wtf/opal/x5
    //   1293	1317	1320	wtf/opal/x5
    //   1314	1351	1354	wtf/opal/x5
    //   1358	1366	1369	wtf/opal/x5
    //   1363	1382	1385	wtf/opal/x5
    //   1389	1397	1400	wtf/opal/x5
    //   1414	1422	1425	wtf/opal/x5
    //   1419	1453	1456	wtf/opal/x5
    //   1429	1463	1466	wtf/opal/x5
    //   1460	1492	1495	wtf/opal/x5
    //   1470	1520	1523	wtf/opal/x5
    //   1527	1535	1538	wtf/opal/x5
    //   1532	1571	1574	wtf/opal/x5
    //   1578	1624	1627	wtf/opal/x5
    //   1759	1769	1772	wtf/opal/x5
    //   1786	1833	1836	wtf/opal/x5
    //   1830	1849	1852	wtf/opal/x5
    //   1856	1864	1867	wtf/opal/x5
    //   1861	1893	1896	wtf/opal/x5
    //   1871	1903	1906	wtf/opal/x5
    //   1900	1934	1937	wtf/opal/x5
    //   1941	1949	1952	wtf/opal/x5
    //   1946	1995	1998	wtf/opal/x5
    //   2002	2010	2013	wtf/opal/x5
    //   2007	2038	2041	wtf/opal/x5
    //   2045	2053	2056	wtf/opal/x5
    //   2050	2086	2089	wtf/opal/x5
    //   2060	2096	2099	wtf/opal/x5
    //   2093	2112	2112	wtf/opal/x5
    //   2137	2145	2148	wtf/opal/x5
    //   2142	2181	2184	wtf/opal/x5
    //   2188	2220	2223	wtf/opal/x5
  }
  
  public static void e(boolean paramBoolean) {
    a = paramBoolean;
  }
  
  public static boolean A() {
    return a;
  }
  
  public static boolean D() {
    boolean bool = A();
    try {
      if (!bool)
        return true; 
    } catch (x5 x5) {
      throw b(null);
    } 
    return false;
  }
  
  static {
    e(true);
    long l = b ^ 0xE09BC2E3948L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[2];
    boolean bool = false;
    String str;
    int i = (str = "=ó*j8\030?<\\~ü`ëð½¥\017§Cê«²7ð²|\030c}0$\"Ã*åX\0251a\027gyD\036iQ\003 u0i=\027\024À\017¢õ\002j½UAñRO!îmÂ»").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
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
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x213;
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
        throw new RuntimeException("wtf/opal/pm", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = c[i].getBytes("ISO-8859-1");
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
    //   66: ldc_w 'wtf/opal/pm'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6330;
    if (k[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = j[i];
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
        throw new RuntimeException("wtf/opal/pm", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      k[i] = Integer.valueOf(j);
    } 
    return k[i].intValue();
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
    //   66: ldc_w 'wtf/opal/pm'
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
  
  private static long c(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x35E4;
    if (o[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l1 = n[i];
      byte[] arrayOfByte2 = { (byte)(int)(l1 >>> 56L), (byte)(int)(l1 >>> 48L), (byte)(int)(l1 >>> 40L), (byte)(int)(l1 >>> 32L), (byte)(int)(l1 >>> 24L), (byte)(int)(l1 >>> 16L), (byte)(int)(l1 >>> 8L), (byte)(int)l1 };
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
        throw new RuntimeException("wtf/opal/pm", exception);
      } 
      long l2 = (arrayOfByte3[0] & 0xFFL) << 56L | (arrayOfByte3[1] & 0xFFL) << 48L | (arrayOfByte3[2] & 0xFFL) << 40L | (arrayOfByte3[3] & 0xFFL) << 32L | (arrayOfByte3[4] & 0xFFL) << 24L | (arrayOfByte3[5] & 0xFFL) << 16L | (arrayOfByte3[6] & 0xFFL) << 8L | arrayOfByte3[7] & 0xFFL;
      o[i] = Long.valueOf(l2);
    } 
    return o[i].longValue();
  }
  
  private static long c(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    long l2 = c(i, l1);
    MethodHandle methodHandle = MethodHandles.constant(long.class, Long.valueOf(l2));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return l2;
  }
  
  private static CallSite c(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
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
    //   66: ldc_w 'wtf/opal/pm'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pm.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */